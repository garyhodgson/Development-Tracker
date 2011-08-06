package developments

import com.googlecode.objectify.Query

import entity.Development


session = session?:request.getSession(true)

log.info "Searching for Developments: ${params}"

if (!params.searchField){
	session.message = "Search Field not found."
	redirect '/developments'
	return
}

Query<Development> query = dao.ofy().query(Development.class)

if (params.value){
	
	def values = params.value.split(";")
	
	if (values.size() > 1){
		query.filter("$params.searchField IN ", values)
		request.pageTitle = "Developments where ${params.searchField} includes ${values}"
	} else {
		query.filter("$params.searchField = ", params.value)
		
		def conjunction = (params.searchField.endsWith('ies'))?'includes': 'is'
		
		request.pageTitle = "Developments where ${params.searchField} ${conjunction} ${params.value}"
	}
} else {
	request.pageTitle = "Developments with field ${params.searchField}"
}

request.developments = query.order('title').list()

forward '/templates/developments/list.gtpl'