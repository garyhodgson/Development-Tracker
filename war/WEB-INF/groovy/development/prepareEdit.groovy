package development

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.UserInfo

if (!params.id){
	request.session.message = "No Id given."
	redirect params.referer?:"/developments"
	return
}

def id = params.id as Long

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to edit."
	redirect "/templates/access/login.gtpl?continue=/development/edit/${id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect params.referer?:"/development/${id}"
	return
}


def key = new Key<Development>(Development.class, id)
def development = dao.ofy().find(key);
if (!development) {
	request.session.message = "Unable to find development with id: ${id}"
	redirect "/developments"
	return
}

request.development = development
request.collaborations = dao.ofy().query(Collaboration.class).ancestor(key).list()

def mayEdit = (development.createdBy == userinfo.username);

request.collaborations.each {
	def userInfoKey = new Key(UserInfo.class, userinfo.userId)
	if (it.userInfo == userInfoKey && it.mayEdit){
		mayEdit = true
	}
}

if (!mayEdit){
	request.session.message = "You do not have permission to edit this Development."
	redirect params.referer?:"/development/${id}"
	return
}

request.relationships = dao.ofy().query(Relationship.class).ancestor(key).list()

request.pageTitle = "Edit Development: ${development.title}"
request.action="/development/update"
request.referer = headers.Referer?:'/development/${id}'

forward '/templates/development/addEdit.gtpl'