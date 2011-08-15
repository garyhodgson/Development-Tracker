package development

import org.apache.commons.lang.StringEscapeUtils

import com.googlecode.objectify.Key

import entity.Activity
import entity.Collaboration
import entity.Development
import entity.Relationship

if (!params.id){
	request.session.message = "No Id given."
	redirect "/developments"
	return
}
//params.id = StringEscapeUtils.escapeHtml(params.id)

def id
try {
	id = params.id as Long
} catch(NumberFormatException nfe){
	request.session.message = "Invalid Id: ${params.id}"
	redirect "/developments"
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to delete."
	redirect "/templates/access/login.gtpl?continue=/development/${id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect "/development/${id}"
	return
}

def key = new Key<Development>(Development.class, id)
def development = dao.ofy().find(key);
if (!development) {
	request.session.message = "Unable to find development with id: ${id}"
	redirect "/developments"
	return
}

def mayEdit = (users.isUserAdmin() || development.createdBy == userinfo.username);

if (!mayEdit){
	request.session.message = "You do not have permission to delete this Development."
	redirect "/development/${id}"
	return
}
def keysToDelete = []
keysToDelete += key
keysToDelete += dao.ofy().query(Collaboration.class).ancestor(key).listKeys() 
keysToDelete += dao.ofy().query(Relationship.class).ancestor(key).listKeys() 

dao.ofy().delete(keysToDelete)

dao.ofy().put(new Activity(type:enums.ActivityType.DevelopmentDeleted, title:"${development.title}",by:userinfo.username, created: new Date()))

// Extreme, but ensures all searches and browse data is up to date
memcache.clearAll()

request.session.message = "Development deleted."
redirect "/developments"