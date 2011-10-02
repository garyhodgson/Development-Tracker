package kit

import com.googlecode.objectify.NotFoundException

import entity.Development
import entity.Kit

if (!params.id){
	request.session.message = "No Id given."
	redirect '/'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/'
	return
}

try {
	request.kit = dao.ofy().get(Kit.class, params.id as Long)
		
	if (request.kit.parts) {
		def keys = request.kit.parts.collect { it as Long }
		request.parts = dao.ofy().get(Development.class, keys)?.values()
		
	}
} catch (NotFoundException nfe){
	log.severe nfe.getLocalizedMessage()
	request.session.message = "No kit with id ${params.id} found."
	redirect "/"
	return
}

request.pageTitle = request.kit.title

forward '/templates/kit/show.gtpl'