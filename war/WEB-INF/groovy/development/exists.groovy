package development

import entity.Development

if (!params.field){
	log.warning "field parameter missing from Development exists check"
	return
}

if (!params.value){
	return
}

log.info params.value.toString()

try {
	def result = dao.ofy().query(Development.class).filter(params.field, params.value).get()

	if (result){
		out << result.id
	}
} catch (Exception e){
	log.severe e.getMessage()
}
return