package theme

import static development.developmentHelper.*
import entity.Activity
import entity.Theme

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a theme."
	forward "/templates/access/login.gtpl?continue=/theme/add"
	return
}
def currentUsername = request.session.userinfo?.username?:''
def theme = new Theme(createdBy:session.userinfo.username)


theme.title = params.title
theme.description= params.description
theme.developmentIds = new ArrayList<Long>()

log.info "${params.developmentId}"

if (params.developmentId){
	if (params.developmentId instanceof String){
		theme.developmentIds << params.developmentId
	} else {
		params.developmentId.each {
			theme.developmentIds << it
		}
	}
}

dao.ofy().put(theme)

dao.ofy().put(new Activity(type:enums.ActivityType.NewTheme, title:"${theme.title}",by:theme.createdBy, created: new Date(), link :"/theme/${theme.id}"))

// Extreme, but ensures all searches and browse data is up to date
memcache.clearAll()

redirect "/theme/${theme.id}"






