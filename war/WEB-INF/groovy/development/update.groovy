package development

import static development.developmentHelper.*
import history.ChangeHelper
import info.developmenttracker.ThumbnailException

import com.googlecode.objectify.Key
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.ChangeHistory
import entity.Collaboration
import entity.Development
import entity.Relationship
import exceptions.ValidationException

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/developments'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to update a development."
	forward "/templates/access/login.gtpl?continue=/development/edit/${params.id}"
	return
}

def developmentKey = new Key<Development>(Development.class, params.id as Long)
def development = dao.ofy().get(developmentKey)
if (!development) {
	request.session.message = "Unable to find development with Id ${params.id}."
	redirect params.referer?:'/developments'
	return
}

def originalDevelopmentThumbnailPath = development.thumbnailPath
def updateThumbnail = development.imageURL != params.imageURL

def now = new Date()
def currentUsername = request.session.userinfo?.username?:''
def changeHelper = new ChangeHelper();

Objectify ofyTxn = ObjectifyService.beginTransaction();

try {
	def originalDevelopmentProperties = development.properties

	processParameters(development, params)
	validateDevelopment(development)

	def changes = changeHelper.getDevelopmentChanges(originalDevelopmentProperties, development.properties)

	ofyTxn.put(development);

	/*****************************************/

	def relationships = []
	processRelationships(relationships, params, developmentKey)
	validateRelationships(relationships)

	def existingRelationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()

	changes += changeHelper.getListChanges(existingRelationships, relationships, "ConnectionId")?:[]

	def relationshipsToBeDeleted = existingRelationships.minus(relationships)
	relationshipsToBeDeleted.each { ofyTxn.delete(it) }
	relationships.each { ofyTxn.put(it) }

	/*****************************************/


	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)
	validateCollaborations(collaborations)

	def existingCollaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()

	changes += changeHelper.getListChanges(existingCollaborations, collaborations, "CollaboratorId")?:[]

	def collaborationsToBeDeleted = existingCollaborations.minus(collaborations)
	collaborationsToBeDeleted.each { ofyTxn.delete(it) }
	collaborations.each { ofyTxn.put(it) }

	/*******************************************/

	if (changes) {
		ofyTxn.put(new ChangeHistory(parent:developmentKey, by:currentUsername, on:now, changes:changes))
	}

	ofyTxn.getTxn().commit()

} catch (ValidationException e) {
	request.session.message = e.getMessage()
	request.development = development
	request.action = '/development/update'
	forward '/templates/development/addEdit.gtpl'
	return
} finally {
	if (ofyTxn.getTxn().isActive())
		ofyTxn.getTxn().rollback();
}

if (updateThumbnail){
	try {
		(new ThumbnailHelper()).generateThumbnail(originalDevelopmentThumbnailPath, params, development)
	} catch (ThumbnailException te){
		request.session.message = te.getLocalizedMessage()
	}
}

dao.ofy().put(new Activity(type:enums.ActivityType.DevelopmentUpdated, title:"${development.title}",by:currentUsername, created: now, link :"/development/${development.id}"))

cacheManager.resetCache()

redirect params.referer?:"/development/${development.id}"
