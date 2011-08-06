package development

import entity.Development

log.info "Checking Development"

if (!params.field){
	log.warning "field parameter missing from Development exists check"
	return
}

if (!params.value){
	return
}

log.info "Checking for existence of development with ${params.field} of ${params.value}"

def result = dao.ofy().query(Development.class).filter(params.field, params.value).get()

if (result){
	out << result.id
}

return