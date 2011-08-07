package development

import static development.developmentHelper.*
import history.PatchBuilder;

import com.googlecode.objectify.Key
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.Collaboration
import entity.Development
import entity.DiffLog
import entity.Relationship
import exceptions.ValidationException

log.info "Update Development"

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

def developmentKey = new Key<Development>(Development.class, params.id as int)
def development = dao.ofy().get(developmentKey)
def updateImageURL = false
if (!development) {
	request.session.message = "Unable to find development with Id ${params.id}."
	redirect params.referer?:'/developments'
	return
}

Objectify ofyTxn = ObjectifyService.beginTransaction();

try {
	def patchBuilder = new PatchBuilder()
	patchBuilder.addOldDevelopment(development)

	updateImageURL = (params.imageURL && development.imageURL != params.imageURL)
	
	processParameters(development, params)
	validateDevelopment(development)

	ofyTxn.put(development);

	patchBuilder.addNewDevelopment(development)
	
	/*****************************************/

	def relationships = []
	processRelationships(relationships, params, developmentKey)
	validateRelationships(relationships)

	def existingRelationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()
	
	existingRelationships.each {
		patchBuilder.addOldRelationship(it)
	}
	
	def relationshipsToBeDeleted = existingRelationships - relationships
	relationshipsToBeDeleted.each { ofyTxn.delete(it) }
	relationships.each { 
		ofyTxn.put(it)
		patchBuilder.addNewRelationship(it)
	}

	/*****************************************/


	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)
	validateCollaborations(collaborations)

	def existingCollaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
	existingCollaborations.each {
		patchBuilder.addOldCollaboration(it)
	}
	def collaborationsToBeDeleted = existingCollaborations - collaborations
	collaborationsToBeDeleted.each { ofyTxn.delete(it) }
	collaborations.each { 
		ofyTxn.put(it) 
		patchBuilder.addNewCollaboration(it)
	}
		
	/*******************************************/
	
	def patch = patchBuilder.build()
	if (patch){
		ofyTxn.put(new DiffLog(by:session.userinfo.username, on:development.updated, patch:patch, parent:developmentKey))
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

dao.ofy().put(new Activity(type:enums.ActivityType.DevelopmentUpdated, title:"${development.title}",by:development.createdBy, created: new Date(), link :"/development/${development.id}"))

redirect params.referer?:"/development/${development.id}"
