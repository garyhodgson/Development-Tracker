package development

import static development.developmentHelper.*
import history.ChangeHelper

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
def updateImageURL = false
def development = new Development(
		subdomain:request.properties.serverName.split(/\./).getAt(0),
		createdBy:session.userinfo.username)

Objectify ofyTxn = ObjectifyService.beginTransaction();

try {
	
	updateImageURL = (params.imageURL && development.imageURL != params.imageURL)
	
	processParameters(development,  params)
	validateDevelopment(development)
	
	def developmentKey = ofyTxn.put(development)
	
	def changes = changeHelper.getDevelopmentChanges([:], development.properties)

	/*****************************************/
	
	def relationships = []
	processRelationships(relationships, params, developmentKey)
	validateRelationships(relationships)

	changes += changeHelper.getListChanges([], relationships, "ConnectionId")?:[]
	
	relationships.each { 
		ofyTxn.put(it) 
	}

	/*****************************************/
	
	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)
	validateCollaborations(collaborations)
	
	changes += changeHelper.getListChanges([], collaborations, "CollaboratorId")?:[]
	
	collaborations.each { 
		ofyTxn.put(it)
	}
	
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

dao.ofy().put(new Activity(type:enums.ActivityType.NewDevelopment, title:"${development.title}",by:development.createdBy, created: new Date(), link :"/development/${development.id}"))

// Extreme, but ensures all searches and browse data is up to date
memcache.clearAll()

redirect "/development/${development.id}"






