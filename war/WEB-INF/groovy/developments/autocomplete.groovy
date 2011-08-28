package developments

import app.MemcacheKeys
import entity.Development


if (!params.term) {
	return
}
def term = params.term.toLowerCase()

def titleMap = [:]
def memcacheKey = MemcacheKeys.TITLE_AUTOCOMPLETE

if (memcache[memcacheKey]) {
	titleMap = memcache[memcacheKey]
} else {

	def devs = dao.ofy().query(Development.class).list()

	devs.each {
		titleMap[it.title] = it.id
	}
	memcache[memcacheKey] = titleMap
}

def list = []
		
titleMap.each {
	if (it.key.toLowerCase().contains(term)){
		list << [label:it.key, id:it.value]
	}
}

out << new groovy.json.JsonBuilder(list)

return