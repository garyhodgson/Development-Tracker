package developments.browse

import org.apache.commons.lang.StringEscapeUtils

import app.MemcacheKeys
import entity.Development
import enums.*

if (!params.field){
	request.session.message = "Browse field missing."
	redirect '/developments/browse'
	return
}

def field = StringEscapeUtils.escapeHtml(params.field)

def browseStats = [:]
def memcacheKey = "${MemcacheKeys.BROWSE_STATS}:${field}" 

if (memcache[memcacheKey]) {
	
	browseStats = memcache[memcacheKey]

} else {

	def values = null

	switch (params.field){
		case 'categories':
			values = Category.values()
			break
		case 'status':
			values = Status.values()
			break
		case 'goal':
			values = Goal.values()
			break
		case 'projectVendor':
			values = ProjectVendor.values()
			break
		case 'source':
			values = Source.values()
			break
		case 'developmentType':
			values = DevelopmentType.values()
			break
		case 'license':
			values = License.values()
			break
	}

	if (!values){
		request.session.message = "Browse field values missing."
		redirect '/developments/browse'
		return
	}

	values.each {
		def count = dao.ofy().query(Development.class).filter(field, it.name()).countAll()
		browseStats[it] = ['id':it, 'title':it.title, 'count':count]
	}
	
	memcache[memcacheKey] = browseStats
}

request.browseStats = browseStats
request.browseField = field
request.pageTitle = "Browsing ${field.capitalize()}"
request.session.route = "/browse/${field}"


forward '/templates/developments/browse/field.gtpl'