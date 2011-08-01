package development

import entity.Development

log.info "Checking Development"

if (!params.title){
	log.warn "title parameter missing from Development exists check"
	return
}

out << (dao.ofy().query(Development.class).filter('title', params.title).countAll() != 0)

return