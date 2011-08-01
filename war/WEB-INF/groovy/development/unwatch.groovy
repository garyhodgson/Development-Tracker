package development

import entity.UserInfo

log.info "Unwatch Development"

if (!params.id){
	session.message = "No development id given."
	redirect "/developments"
	return
}

if (!users.isUserLoggedIn()){
	session.message = "Must be logged in to unwatch a development."
	redirect = "/development/${params.id}"
	return
}

def session = session?:request.getSession(true)
def developmentId

try {
	developmentId = Long.parseLong(params.id)
} catch (NumberFormatException e) {
	session.message = "Unable to read development id. Please contact <a href=\"mailto:support@development-tracker.info\">support</a>."
	redirect = "/development/${params.id}"
	return
}

UserInfo userinfo =  dao.ofy().find(UserInfo.class, user.userId)

if (!userinfo) {
	session.message = "Unable to find userinfo. If you feel this is in error please contact <a href=\"mailto:support@development-tracker.info\">support</a>"
	redirect = "/development/${params.id}"
	return
}

if (!userinfo.watchedDevelopments){
	userinfo.watchedDevelopments = []
}

userinfo.watchedDevelopments -= developmentId

dao.ofy().put(userinfo)

session.userinfo = userinfo

session.message = "Development removed from watch list."

redirect "/development/${params.id}"
