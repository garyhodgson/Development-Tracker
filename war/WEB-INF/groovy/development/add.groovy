package development

import static development.developmentHelper.*

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.Development
import exceptions.ValidationException

log.info "New Development"
session = session?:request.getSession(true)

if (!users.isUserLoggedIn()){
	session.message = "Must be logged in to add a development."
	forward "/templates/access/login.gtpl?continue=/development/add"
	return
}

def now = new Date()
def development = new Development(
	created:now,
	updated:now,
	subdomain:request.properties.serverName.split(/\./).getAt(0),
	createdBy:session.userinfo.username)


try {

	processParameters(development,  params)

	validateDevelopment(development)

	def fromDevelopmentKey = dao.ofy().put(development)
	
	def relationships = []
	processRelationships(relationships, params, fromDevelopmentKey)
	
	validateRelationships(relationships)
	
	relationships.each { dao.ofy().put(it) }
	
	def collaborations = []
	processCollaborations(collaborations, params, fromDevelopmentKey)

	validateCollaborations(collaborations)

	collaborations.each { dao.ofy().put(it) }
	
} catch (ValidationException e) {
	session.message = e.getMessage()
	request.development = development
	forward '/templates/development/addEdit.gtpl'
	return
} 



dao.ofy().put(new Activity(type:enums.ActivityType.NewDevelopment, title:"${development.title}",by:development.createdBy, created: new Date(), link :"/development/${development.id}"))

redirect "/development/${development.id}"






