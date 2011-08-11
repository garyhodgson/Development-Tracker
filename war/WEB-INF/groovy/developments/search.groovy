package developments

import com.googlecode.objectify.Query
import entity.Development
import static paging.pagingHelper.*

log.info "Searching for Developments: ${params}"

if (!params.searchField){
	request.session.message = "Search Field not found."
	redirect '/developments'
	return
}

def query = dao.ofy().query(Development.class)

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

def totalCount = query.countAll()
def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.developments = query.order('title').offset(offset).limit(limit).list()

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

forward '/templates/developments/list.gtpl'