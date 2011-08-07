package development

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.UserInfo

log.info "Editing Development"

session = session?:request.getSession(true)

if (!users.isUserLoggedIn()){
	session.message = "Must be logged in to edit."
	redirect "/templates/access/login.gtpl?continue=/development/edit/${params.id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	session.message = "Unable to identify user. Please contact <a href=\"mailto:support@development-tracker.info\">support</a>."
	redirect params.referer?:"/development/${params.id}"
	return
}

if (!params.id){
	session.message = "No Id given."
	redirect params.referer?:"/development/${params.id}"
	return
}

def key = new Key<Development>(Development.class, params.id as int)
def development = dao.ofy().get(key);

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
	session.message = "You do not have permission to edit this Development."
	redirect params.referer?:"/development/${params.id}"
	return
}

request.relationships = dao.ofy().query(Relationship.class).ancestor(key).list()

request.pageTitle = "Edit Development: ${development.title}"
request.action="/development/update"
request.referer = headers.Referer?:'/development/${params.id}'

forward '/templates/development/addEdit.gtpl'