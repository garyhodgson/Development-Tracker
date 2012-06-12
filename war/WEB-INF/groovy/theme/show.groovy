package theme


import cache.MemcacheKeys;

import com.google.appengine.api.memcache.Expiration
import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.Theme
import enums.Source

if (!params.id){
	request.session.message = "No Id given."
	redirect '/themes'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/themes'
	return
}

try {
	request.theme = dao.ofy().get(Theme.class, params.id as Long)
	request.developments = []
	if (request.theme.developmentIds) {

		def keys = request.theme.developmentIds.collect { it as Long }
		request.developments = dao.ofy().get(Development.class, keys)?.values()
	}
} catch (NotFoundException nfe){
	request.session.message = "No theme with id ${params.id} found."
	redirect '/themes'
	return
}

request.pageTitle = request.theme.title

forward '/templates/theme/show.gtpl'