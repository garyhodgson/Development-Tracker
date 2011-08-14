package development

import entity.Development

if (!params.field){
	log.warning "field parameter missing from Development exists check"
	return
}

if (!params.value){
	return
}

def result = dao.ofy().query(Development.class).filter(params.field, params.value).get()

if (result){
	out << result.id
}

return