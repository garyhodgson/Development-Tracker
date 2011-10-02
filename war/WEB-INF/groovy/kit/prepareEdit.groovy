package kit

import com.googlecode.objectify.NotFoundException

import entity.Development
import entity.Kit

if (!params.id){
	request.session.message = "No Id given."
	redirect params.referer?:"/"
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to edit."
	redirect "/templates/access/login.gtpl?continue=/kit/edit/${params.id}"
	return
}

def userinfo = request.session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect params.referer?:"/kit/${params.id}"
	return
}

try {
	request.kit = dao.ofy().get(Kit.class, params.id as Long)

	if (!users.isUserAdmin() && request.kit.ownerUsername != userinfo.username){
		request.session.message = "You do not have permission to edit this kit."
		redirect params.referer?:"/kit/${params.id}"
		return
	}
	if (request.kit.parts){
		def keys = request.kit.parts.collect { it as Long }
		request.parts = dao.ofy().get(Development.class, keys)?.values()
	}
} catch (NotFoundException nfe){
	request.session.message = "No kit with id ${params.id} found."
	redirect "/userinfo/${userinfo.username}"
	return
}


request.pageTitle = "Edit Kit: ${request.kit.title}"
request.action="/kit/update"

forward '/templates/kit/addEdit.gtpl'