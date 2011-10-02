package kit

import static development.developmentHelper.*
import info.developmenttracker.ThumbnailException
import development.ThumbnailHelper
import com.googlecode.objectify.Key

import entity.Activity
import entity.Development
import entity.Kit

if (!params.id){
	request.session.message = "No Id given."
	redirect '/themes'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to update a theme."
	forward "/templates/access/login.gtpl?continue=/kit/edit/${params.id}"
	return
}

def userinfo = request.session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect "/kit/${params.id}"
	return
}

def kit = dao.ofy().get(Kit.class, params.id as Long)
if (!kit) {
	request.session.message = "Unable to find kit with Id ${params.id}."
	redirect '/'
	return
}

if (!users.isUserAdmin() && kit.ownerUsername != userinfo.username){
	request.session.message = "You do not have permission to edit this kit."
	redirect "/kit/${params.id}"
	return
}

def originalThumbnailPath = kit.thumbnailPath

kit.title = params.title
kit.imageURL = params.imageURL
kit.description= params.description
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
	(new ThumbnailHelper()).generateThumbnail(originalThumbnailPath, params, kit)
} catch (ThumbnailException te){
	request.session.message = te.getLocalizedMessage()
}


dao.ofy().put(new Activity(type:enums.ActivityType.KitUpdated, title:"${kit.title}",by:userinfo.username, created: new Date(), link :"/kit/${kit.id}"))

// Extreme, but ensures all searches and browse data is up to date
memcache.clearAll()

redirect "/kit/${kit.id}"


