package development

import entity.UserInfo

if (!params.id){
	request.session.message = "No development id given."
	redirect "/developments"
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to unwatch a development."
	redirect = "/development/${params.id}"
	return
}

Long developmentId

try {
	developmentId = Long.parseLong(params.id)
} catch (NumberFormatException e) {
	request.session.message = "Unable to read development id. Please contact <a href=\"mailto:support@development-tracker.info\">support</a>."
	redirect = "/development/${params.id}"
	return
}

UserInfo userinfo

namespace.of("") {
	userinfo =  dao.ofy().find(UserInfo.class, user.userId)
}

if (!userinfo) {
	request.session.message = "Unable to find userinfo. If you feel this is in error please contact <a href=\"mailto:support@development-tracker.info\">support</a>"
	redirect = "/development/${params.id}"
	return
}

if (!userinfo.watchedDevelopments){
	userinfo.watchedDevelopments = []
}

userinfo.watchedDevelopments -= developmentId

namespace.of("") {
	dao.ofy().put(userinfo)
}

request.session.userinfo = userinfo

request.session.message = "Development removed from watch list."

redirect "/development/${params.id}"
