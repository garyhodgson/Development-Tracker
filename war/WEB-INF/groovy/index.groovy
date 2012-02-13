import static com.google.appengine.api.datastore.FetchOptions.Builder.*

import com.google.appengine.api.NamespaceManager


def subdomain = request.properties.serverName.split(/\./).getAt(0)

//deal with GAE versions
if (subdomain.isNumber()){
	subdomain = request.properties.serverName.split(/\./).getAt(1)
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

forward '/templates/namespace/3dprint/index.gtpl'