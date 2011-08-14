package admin

import app.MemcacheKeys

html.html {
  head {
      title("Stats")
  }
  body {
	  	h2 MemcacheKeys.BROWSE_STATS_TAGS
	  	p  memcache.get(MemcacheKeys.BROWSE_STATS_TAGS).toString()
	  ['categories', 'status', 'source', 'projectVendor', 'goal', 'developmentType', 'license'].each {
		  h2 "${MemcacheKeys.BROWSE_STATS}:${it}" 
		  p memcache.get("${MemcacheKeys.BROWSE_STATS}:${it}")?.toString()
	  }
  }
}