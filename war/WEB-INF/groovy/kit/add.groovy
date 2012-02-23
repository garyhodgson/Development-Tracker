package kit

import static development.developmentHelper.*
import info.developmenttracker.ThumbnailException
import development.ThumbnailHelper
import entity.Activity
import entity.Kit

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a kit."
	forward "/templates/access/login.gtpl?continue=/kit/add"
	return
}
if (!request.session.userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	forward "/"
	return
}

def kit = new Kit()
def currentUsername = request.session.userinfo?.username?:''

kit.title = params.title
kit.imageURL = params.imageURL
kit.description= params.description
kit.ownerUsername = currentUsername
kit.parts = []

if (params.partId){
	if (params.partId instanceof String){
		kit.parts << params.partId
	} else {
		params.partId.each { kit.parts << it }
	}
}

dao.ofy().put(kit)

try {
	(new ThumbnailHelper()).generateThumbnail(null, params, kit)
} catch (ThumbnailException te){
	request.session.message = te.getLocalizedMessage()
}

dao.ofy().put(new Activity(type:enums.ActivityType.NewKit, title:"${kit.title}",by:currentUsername, created: new Date(), link :"/kit/${kit.id}"))

cacheManager.resetKitCache()
cacheManager.resetActivityCache()

redirect "/kit/${kit.id}"






