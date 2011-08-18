package development

import entity.Development
import entity.UserInfo

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/developments'
	return
}

def developmentId = params.id as Long

def development = dao.ofy().find(Development.class, developmentId)
if (!development) {
	request.session.message = "Development not found."
	redirect '/developments'
	return
}

namespace.of("") {
	request.developmentWatchers = dao.ofy().query(UserInfo.class).filter('watchedDevelopments = ', developmentId).list()
}
request.development = development
request.pageTitle = "Watchers of ${development.title}"

session.route="/development/${developmentId}/watchers"

forward '/templates/development/watchers.gtpl'