package development

import static development.developmentHelper.*
import history.ChangeHelper
import info.developmenttracker.ThumbnailException

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.ChangeHistory
import entity.Development
import exceptions.ValidationException

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a development."
	forward "/templates/access/login.gtpl?continue=/development/add"
	return
}
def currentUsername = request.session.userinfo?.username?:''
def changeHelper = new ChangeHelper();
def now = new Date()
def development = new Development(
		subdomain:request.properties.serverName.split(/\./).getAt(0),
		createdBy:session.userinfo.username)

Objectify ofyTxn = ObjectifyService.beginTransaction()

try {

	processParameters(development,  params)
	validateDevelopment(development)

	def developmentKey = ofyTxn.put(development)

	def changes = changeHelper.getDevelopmentChanges([:], development.properties)

	/*****************************************/

	def relationships = []
	processRelationships(relationships, params, developmentKey)
	validateRelationships(relationships)

	changes += changeHelper.getListChanges([], relationships, "ConnectionId")?:[]

	relationships.each {  ofyTxn.put(it)  }

	/*****************************************/

	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)
	validateCollaborations(collaborations)

	changes += changeHelper.getListChanges([], collaborations, "CollaboratorId")?:[]

	collaborations.each {  ofyTxn.put(it) }

	/*****************************************/

	if (changes) {
		ofyTxn.put(new ChangeHistory(parent:developmentKey, by:currentUsername, on:now, changes:changes))
	}

	ofyTxn.getTxn().commit()
} catch (ValidationException e) {
	request.session.message = e.getMessage()
	request.development = development
	forward '/templates/development/addEdit.gtpl'
	return
} finally {
	if (ofyTxn.getTxn().isActive())
		ofyTxn.getTxn().rollback();
}

try {
	(new ThumbnailHelper()).generateThumbnail(null, params, development)
} catch (ThumbnailException te){
	request.session.message = te.getLocalizedMessage()
}

dao.ofy().put(new Activity(type:enums.ActivityType.NewDevelopment, title:"${development.title}",by:development.createdBy, created: new Date(), link :"/development/${development.id}"))

cacheManager.resetDevelopmentCache()
cacheManager.resetActivityCache()

redirect "/development/${development.id}"






