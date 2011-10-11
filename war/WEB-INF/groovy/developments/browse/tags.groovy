package developments.browse

import static enums.MemcacheKeys.*

def tags = [:]

if (BrowseStatsTags in memcache) {
	tags = memcache[BrowseStatsTags]
} else {

	def developments = cacheManager.allDevelopments()

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