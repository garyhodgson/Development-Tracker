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

def browseStats = [:]
def memcacheKey = "${MemcacheKeys.BROWSE_STATS}:${params.field}" 

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
		case 'goals':
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
		def count = dao.ofy().query(Development.class).filter(params.field, it.name()).countAll()
		browseStats[it] = ['count':count]
	}
	memcache[memcacheKey] = browseStats
}

request.browseStats = browseStats
request.browseField = params.field

request.pageTitle = "Browsing ${params.field.capitalize()}"


switch (params.field){
	case 'projectVendor':
		request.browseFieldTitle = "Project Vendor"
		break
	case 'developmentType':
		request.browseFieldTitle = "Development Type"
		break
	default:
		request.browseFieldTitle = params.field.capitalize()
}

request.session.route = "/browse/${params.field}"


forward '/templates/developments/browse/field.gtpl'