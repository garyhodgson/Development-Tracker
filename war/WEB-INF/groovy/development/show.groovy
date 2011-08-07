package development

import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Collaboration
import entity.Development
import entity.Relationship

log.info "Showing Development"

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

def developmentKey = new Key(Development.class, params.id as Long)

try {
	request.development = dao.ofy().get(developmentKey)
	request.relationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()
	request.collaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
} catch (NotFoundException nfe){
	request.session.message = "No development with id ${params.id} found."
	redirect '/developments'
	return
}

request.pageTitle = request.development.title

forward '/templates/development/show.gtpl'