package admin.memcache

import cache.MemcacheKeys;
import static enums.MemcacheKeys.*

html.html {
	head { title("Stats") }
	body {
		p memcache.statistics
		a(href:'/admin/memcache/clear'){p 'Clear Memcache' }
		hr()

		h2 "Tags Stats..."
		p  memcache.get(MemcacheKeys.BROWSE_STATS_TAGS)?.toString()
		[
			'categories',
			'status',
			'source',
			'projectVendor',
			'goals',
			'developmentType',
			'license'
		].each {
			h2 "${MemcacheKeys.BROWSE_STATS}:${it}"
			p memcache.get("${MemcacheKeys.BROWSE_STATS}:${it}")?.toString()
		}

		hr()

		enums.MemcacheKeys.each {
			h2 it
			p memcache.get(it)?.toString()
		}

		hr()
		
		memcache.get(Keys)?.each {
			h2 it
			p memcache.get(it)?.toString()
		}
		
		hr()
		
	}
}