package kit

import development.ThumbnailHelper
import entity.Kit

if (!params.id){
	request.session.message = "No Id given."
	redirect "/"
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect "/"
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to delete."
	redirect "/templates/access/login.gtpl?continue=/kit/${params.id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect "/kit/${params.id}"
	return
}

def kit = dao.ofy().find(Kit.class, params.id as Long);
if (!kit) {
	request.session.message = "Unable to find kit with id: ${params.id}"
	redirect "/userinfo/${userinfo.username}"
	return
}

if (!(users.isUserAdmin() || kit.ownerUsername == userinfo.username)){
	request.session.message = "You do not have permission to delete this Kit."
	redirect "/userinfo/${userinfo.username}"
	return
}

(new ThumbnailHelper()).deleteThumbnail(kit.thumbnailPath)

dao.ofy().delete(kit)


cacheManager.resetKitCache()
cacheManager.resetActivityCache()

request.session.message = "Kit deleted."
redirect "/userinfo/${userinfo.username}"