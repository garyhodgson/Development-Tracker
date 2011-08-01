package development

import entity.Development
import entity.UserInfo

session = session?:request.getSession(true)

log.info "Showing Watchers of Development ${params.id}"

if (!params.id){
	session.message = "No Id given."
	redirect '/developments'
	return
}

def development = dao.ofy().find(Development.class, params.id as int)
if (!development) {
	session.message = "Development not found."
	redirect '/developments'
	return
}

request.developmentWatchers = dao.ofy().query(UserInfo.class).filter('watchedDevelopments = ', params.id).list()
request.development = development
request.pageTitle = "Watchers of ${development.title}"

session.route="/development/${params.id}/watchers"

forward '/templates/development/watchers.gtpl'