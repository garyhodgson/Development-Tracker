package development

import entity.Development
import entity.UserInfo

log.info "Showing Watchers of Development ${params.id}"

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

def development = dao.ofy().find(Development.class, params.id as int)
if (!development) {
	request.session.message = "Development not found."
	redirect '/developments'
	return
}

namespace.of("") {
	request.developmentWatchers = dao.ofy().query(UserInfo.class).filter('watchedDevelopments = ', params.id as Long).list()
}
request.development = development
request.pageTitle = "Watchers of ${development.title}"

session.route="/development/${params.id}/watchers"

forward '/templates/development/watchers.gtpl'