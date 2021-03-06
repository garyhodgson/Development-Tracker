package development

import entity.UserInfo

if (!params.id){
	request.session.message = "No development id given."
	redirect "/developments"
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/developments'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to watch a development."
	redirect = "/development/${params.id}"
	return
}

def developmentId = Long.parseLong(params.id)

UserInfo userinfo = null
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

userinfo.watchedDevelopments << developmentId

namespace.of("") {
	dao.ofy().put(userinfo)
}

session.userinfo = userinfo

session.message = "Development added to watch list."

redirect "/development/${params.id}"
