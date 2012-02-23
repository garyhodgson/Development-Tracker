package developments.browse

import com.googlecode.objectify.Query
import entity.Development
import static paging.pagingHelper.*


if (!params.searchField){
	request.session.message = "Browse field not found."
	redirect '/developments'
	return
}

def query = dao.ofy().query(Development.class)

if (params.value){

	if (params.value.contains("_AND_")){
		
		def values = params.value.split("_AND_")
		values.each {
			query.filter("$params.searchField = ", it)
		}
		request.pageTitle = "Developments where ${params.searchField} must include ${values}"
		
	} else if (params.value.contains("_OR_")){
		def values = params.value.split("_OR_")
		query.filter("$params.searchField IN ", values)
		request.pageTitle = "Developments where ${params.searchField} may include ${values}"
		
	} else {
		query.filter("$params.searchField = ", params.value)
		def conjunction = (params.searchField.endsWith('ies'))?'includes': 'is'
		request.pageTitle = "Developments where ${params.searchField} ${conjunction} ${params.value}"
	}

} else {
	request.pageTitle = "Developments with field ${params.searchField}"
}

def totalCount = query.count()
def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.developments = query.order('title').offset(offset).limit(limit).list()

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

forward '/templates/developments/list.gtpl'