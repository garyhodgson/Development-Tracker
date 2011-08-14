package admin.memcache

import app.MemcacheKeys

html.html {
  head {
      title("Stats")
  }
  body {
	  	h2 MemcacheKeys.BROWSE_STATS_TAGS
	  	p  memcache.get(MemcacheKeys.BROWSE_STATS_TAGS)?.toString()
	  ['categories', 'status', 'source', 'projectVendor', 'goals', 'developmentType', 'license'].each {
		  h2 "${MemcacheKeys.BROWSE_STATS}:${it}" 
		  p memcache.get("${MemcacheKeys.BROWSE_STATS}:${it}")?.toString()
	  }
	  
	  a(href:'/admin/memcache/clear'){p 'Clear Memcache' }
  }
}