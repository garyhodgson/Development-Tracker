package developments

import static paging.pagingHelper.*

import org.apache.commons.lang.StringEscapeUtils

import app.MemcacheKeys

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development

if (!params.searchKey){
	request.session.message = "Search key required"
	forward '/templates/developments/search.gtpl'
	return
}

def searchKey = StringEscapeUtils.escapeHtml(params.searchKey).toLowerCase()
def results = []

def memcacheKey = "${MemcacheKeys.SEARCH_KEY}:${searchKey}"
if (memcache[memcacheKey]) {
	results = memcache[memcacheKey]
} else {
	//Brute force - eugh!

	def developments = dao.ofy().query(Development.class).list()

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
	memcache[memcacheKey] = results
}
request.developments = results
request.pageTitle = "Developments Search : ${searchKey}"

forward '/templates/developments/list.gtpl'