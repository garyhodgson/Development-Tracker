package admin.memcache

log.info "Admin memcache clear invoked"

memcache.clearAll()

redirect '/admin/memcache/stats'