package development

import cache.MemcacheKeys

import com.google.appengine.api.memcache.Expiration
import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.Source

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

def developmentKey = new Key(Development.class, params.id as Long)

try {
	request.development = dao.ofy().get(developmentKey)
	request.relationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()

	def reverseRelationships = dao.ofy().query(Relationship.class).filter("to", developmentKey).list()
	if (reverseRelationships){
		
		def reverseDevs = dao.ofy().get( reverseRelationships.collect { it.from } ).values()
	
		request.reverseRelationships = []
		
		reverseDevs.eachWithIndex { dev, i ->
			def rel = reverseRelationships.getAt(i)
			request.reverseRelationships << [type:rel.type, development:dev]			
		}		 
	}
	
	request.collaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
	
} catch (NotFoundException nfe){
	request.session.message = "No development with id ${params.id} found."
	redirect '/developments'
	return
}

request.pageTitle = request.development.title

forward '/templates/development/show.gtpl'
