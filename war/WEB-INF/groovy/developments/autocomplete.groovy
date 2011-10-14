package developments

import static enums.MemcacheKeys.*
import entity.Development


if (!params.term) {
	return
}

def term = params.term.toLowerCase()

def developments = cacheManager.allDevelopments()

def list = []

developments.each {
	if (it.title.toLowerCase().contains(term)){
		list << [label:it.title, id:it.id]
	}
}

out << new groovy.json.JsonBuilder(list)

return