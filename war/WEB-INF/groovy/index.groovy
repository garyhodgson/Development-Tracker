import static com.google.appengine.api.datastore.FetchOptions.Builder.*

import com.google.appengine.api.NamespaceManager

log.info "Index.groovy"

session = session?:request.getSession(true)
def subdomain = request.properties.serverName.split(/\./).getAt(0) + "."

if (params.namespace){
	log.info "params.namespace: ${params.namespace}"
	log.info "subdomain: ${subdomain}"
	
	//strip subdomain
	def host = headers.Host.replaceAll(subdomain, "")
	
	log.info "headers.Host: ${headers.Host}"
	
	log.info "host: ${host}"
	
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
		log.info "Login Required"
		request.getSession(true).message = "Login Required"
		redirect '/access/login?continue=/'
		return
	}
	
	log.info "NamespaceManager.get(): ${NamespaceManager.get()}"
	forward "/templates/namespace/${NamespaceManager.get()}/index.gtpl"
	return
}

forward '/templates/namespace/default/index.gtpl'