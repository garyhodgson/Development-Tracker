package developments

import static paging.pagingHelper.*

import static enums.MemcacheKeys.*

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development

if (!params.searchKey){
	request.session.message = "Search key required"
	forward '/templates/developments/search.gtpl'
	return
}

def searchKey = params.searchKey.toLowerCase()
def results = []


def developments = memcache[AllDevelopments] ?: (memcache[AllDevelopments] = dao.ofy().query(Development.class).list())

developments.each { development ->

	if (development.title?.toLowerCase()?.contains(searchKey)
	|| development.description?.toLowerCase()?.contains(searchKey)) {
		results << development
		return
	}

	development.specificationName?.each { specificationName ->
		if (specificationName.toLowerCase()?.contains(searchKey)){
			results << development
			return
		}
	}

	development.specificationValue?.each { specificationValue ->
		if (specificationValue.toLowerCase()?.contains(searchKey)){
			results << development
			return
		}
	}

	def developmentKey = new Key(Development.class, development.id as Long)
	def collaborators = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
	collaborators.each {  collaborator ->
		if (collaborator.name?.toLowerCase()?.contains(searchKey)){
			results << development
			return
		}
	}

}

request.developments = results
request.pageTitle = "Developments Search : ${searchKey}"

forward '/templates/developments/list.gtpl'