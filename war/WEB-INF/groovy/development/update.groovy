package development

import static development.developmentHelper.*

import com.google.appengine.api.datastore.Text
import com.googlecode.objectify.Key

import entity.Activity
import entity.Collaboration
import entity.Development
import entity.Relationship
import exceptions.ValidationException

log.info "Update Development"

if (!params.id){
	session.message = "No Id given."
	redirect '/developments'
	return
}

def developmentKey = new Key<Development>(Development.class, params.id as int)
def development = dao.ofy().get(developmentKey);

if (!development) {
	session.message = "Unable to find development with Id ${params.id}."
	redirect params.referer?:'/developments'
	return
}

try {

	processParameters(development, params)
	validateDevelopment(development)

	development.updated = new Date()
	dao.ofy().put(development);

	def relationships = []
	processRelationships(relationships, params, developmentKey)
	validateRelationships(relationships)

	def existingRelationships = dao.ofy().query(Relationship.class).filter('from', developmentKey).list()
	def relationshipsToBeDeleted = existingRelationships - relationships
	relationshipsToBeDeleted.each { dao.ofy().delete(it) }
	relationships.each { dao.ofy().put(it) }

	def collaborations = []
	processCollaborations(collaborations, params, developmentKey)

	validateCollaborations(collaborations)

	def existingCollaborations = dao.ofy().query(Collaboration.class).filter('development', developmentKey).list()
	def collaborationsToBeDeleted = existingCollaborations - collaborations
	collaborationsToBeDeleted.each { dao.ofy().delete(it) }
	collaborations.each { dao.ofy().put(it) }
} catch (ValidationException e) {
	session.message = e.getMessage()
	request.development = development
	request.action = '/development/update'
	forward '/templates/development/addEdit.gtpl'
	return
}

dao.ofy().put(new Activity(type:enums.ActivityType.DevelopmentUpdated, title:"${development.title}",by:development.createdBy, created: new Date(), link :"/development/${development.id}"))

redirect params.referer?:"/development/${development.id}"
