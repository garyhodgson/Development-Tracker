import static enums.MemcacheKeys.*
import static paging.pagingHelper.*

import com.google.appengine.api.NamespaceManager

import entity.Collaboration
import enums.Role

def subdomain = request.getServerName().split(/\./).getAt(0)

//deal with GAE versions
if (subdomain.isNumber()){
	subdomain = request.getServerName().split(/\./).getAt(1)
}

if (params.namespace){
	//strip subdomain
	def host = headers.Host.replaceAll(subdomain + ".", "")

	if (params.namespace == "default"){
		//redirect to start page
		redirect "${request.scheme}://${host}"
	} else {
		//redirect to index page with correct subdomain
		redirect "${request.scheme}://${params.namespace}.${host}"
	}
	return
}

if (NamespaceManager.get() != null && !NamespaceManager.get().isEmpty()){
	
	def latestDevs = cacheManager.latestDevelopments(0,4)
		
	latestDevs.each { dev ->
		def collaborators = dao.ofy().query(Collaboration.class).ancestor(dev).filter('role = ',Role.Author).list()
		
		if (collaborators){
			dev.author = collaborators[0].name
		}
	}
		
	request.latestDevelopments = latestDevs
	
	request.latestThemes = cacheManager.latestThemes(0,2)
	request.latestKits = cacheManager.latestKits(0,2)
	request.latestActivities = cacheManager.latestActivities(0,4)

	forward "/templates/namespace/${NamespaceManager.get()}/index.gtpl"
	return
}

forward '/templates/namespace/3dprint/index.gtpl'
