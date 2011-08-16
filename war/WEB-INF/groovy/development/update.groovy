package development

import static development.developmentHelper.*
import history.ChangeHelper

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

def developmentKey = new Key<Development>(Development.class, params.id as int)
def development = dao.ofy().get(developmentKey)
def updateImageURL = false
def now = new Date()
def currentUsername = request.session.userinfo?.username?:''
def changeHelper = new ChangeHelper();

if (!development) {
	request.session.message = "Unable to find development with Id ${params.id}."
	redirect params.referer?:'/developments'
	return
}

Objectify ofyTxn = ObjectifyService.beginTransaction();

try {
	updateImageURL = (params.imageURL && development.imageURL != params.imageURL)

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
	collaborations.each {
		ofyTxn.put(it)
	}

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

if (updateImageURL){

	def thumbnailFile = generateThumbnail(development.imageURL)
	if (thumbnailFile){

		// Delete existing thumbnail
		if (development.thumbnailPath){
			def file = files.fromPath(development.thumbnailPath)
			if (file) file.delete()
		}

		development.thumbnailPath = thumbnailFile.getFullPath()
		development.thumbnailServingUrl = images.getServingUrl(thumbnailFile.blobKey)

		log.info "development.thumbnailServingUrl: ${development.thumbnailServingUrl}"
		log.info "development.thumbnailPath: ${development.thumbnailPath}"
	}
	dao.ofy().put(development)
}

dao.ofy().put(new Activity(type:enums.ActivityType.DevelopmentUpdated, title:"${development.title}",by:currentUsername, created: now, link :"/development/${development.id}"))

// Extreme, but ensures all searches and browse data is up to date
memcache.clearAll()

redirect params.referer?:"/development/${development.id}"
