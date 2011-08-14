import static com.google.appengine.api.datastore.FetchOptions.Builder.*

import com.google.appengine.api.NamespaceManager


def subdomain = request.properties.serverName.split(/\./).getAt(0)

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

	if (NamespaceManager.get() == "my" && !users.isUserLoggedIn()){
		request.session.message = "Login Required"
		redirect '/access/login?continue=/'
		return
	}

	forward "/templates/namespace/${NamespaceManager.get()}/index.gtpl"
	return
}

forward '/templates/namespace/default/index.gtpl'