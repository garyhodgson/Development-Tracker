package developments

import static enums.MemcacheKeys.*
import entity.Development


if (!params.term) {
	return
}

def term = params.term.toLowerCase()

def titleMap = [:]

if (memcache[TitleAutocomplete]) {
	titleMap = memcache[TitleAutocomplete]
} else {

	def devs = dao.ofy().query(Development.class).list()

	devs.each {
		titleMap[it.title] = it.id
	}
	memcache[TitleAutocomplete] = titleMap
}

def list = []

titleMap.each {
	if (it.key.toLowerCase().contains(term)){
		list << [label:it.key, id:it.value]
	}
}

out << new groovy.json.JsonBuilder(list)

return