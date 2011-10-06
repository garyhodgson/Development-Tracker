package developments.browse

import app.MemcacheKeys
import entity.Development
import static enums.MemcacheKeys.*

def tags = [:]

if (BrowseStatsTags in memcache) {
	tags = memcache[BrowseStatsTags]
} else {

	def developments = memcache[AllDevelopments] ?: (memcache[AllDevelopments] = dao.ofy().query(Development.class).list())

	developments.each{ development ->
		if (development.tags){
			development.tags.each{ t ->
				if (!tags[t]) tags[t] = 0
				tags[t]++
			}
		}
	}
	memcache[BrowseStatsTags] = tags
}

request.tags = tags
request.pageTitle = "Tags"

forward '/templates/developments/browse/tags.gtpl'