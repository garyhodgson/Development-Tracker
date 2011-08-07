package development

import static development.developmentHelper.*
import history.PatchBuilder;

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.Development
import entity.DiffLog
import exceptions.ValidationException

log.info "New Development"

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a development."
	forward "/templates/access/login.gtpl?continue=/development/add"
	return
}

def now = new Date()
def updateImageURL = false
def development = new Development(
		subdomain:request.properties.serverName.split(/\./).getAt(0),
		createdBy:session.userinfo.username)

Objectify ofyTxn = ObjectifyService.beginTransaction();

try {
	def patchBuilder = new PatchBuilder()
	
	updateImageURL = (params.imageURL && development.imageURL != params.imageURL)
	
	processParameters(development,  params)
	validateDevelopment(development)
	
	def developmentKey = ofyTxn.put(development)
	patchBuilder.addNewDevelopment(development)

	/*****************************************/
	
	def relationships = []
	processRelationships(relationships, params, developmentKey)

	validateRelationships(relationships)

	relationships.each { 
		ofyTxn.put(it) 
		patchBuilder.addNewRelationship(it)
	}

	/*****************************************/
	
	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)

	validateCollaborations(collaborations)
	collaborations.each { 
		ofyTxn.put(it)
		patchBuilder.addNewCollaboration(it)
	}
	
	/*****************************************/
	
	def patch = patchBuilder.build()
	if (patch){
		ofyTxn.put(new DiffLog(by:session.userinfo.username, on:now, patch:patch, parent:developmentKey))
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


redirect "/development/${development.id}"






