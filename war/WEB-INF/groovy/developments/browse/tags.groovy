package developments.browse

import app.MemcacheKeys
import entity.Development

def tags = [:]

if (memcache[MemcacheKeys.BROWSE_STATS_TAGS]) {
	tags = memcache[MemcacheKeys.BROWSE_STATS_TAGS]
} else {

	def developments = dao.ofy().query(Development.class).filter('tags != ', '').list()
	developments.each{ development ->
		if (development.tags){
			development.tags.each{ t ->
				if (!tags[t]) tags[t] = 0
				tags[t]++
			}
		}
	}
	memcache[MemcacheKeys.BROWSE_STATS_TAGS] = tags
}

request.tags = tags
request.pageTitle = "Tags"

forward '/templates/developments/browse/tags.gtpl'