package development

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship

session = session?:request.getSession(true)

log.info "Showing Development"

if (!params.id){
	session.message = "No Id given."
	redirect '/developments'
	return
}

request.development = dao.ofy().get(Development.class, params.id as Long)
def fromKey = new Key(Development.class, params.id as int)

request.relationships = dao.ofy().query(Relationship.class).filter('from', fromKey).list()
request.collaborations = dao.ofy().query(Collaboration.class).filter('development', fromKey).list()

request.pageTitle = request.development.title

forward '/templates/development/show.gtpl'