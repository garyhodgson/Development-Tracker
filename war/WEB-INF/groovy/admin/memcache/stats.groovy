package admin.memcache

import static enums.MemcacheKeys.*

html.html {
	head { title("Stats") }
	body {
		p memcache.statistics
		a(href:'/admin/memcache/clear'){p 'Clear Memcache' }
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