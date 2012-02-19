package theme

import com.googlecode.objectify.Key

import entity.Activity
import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.Theme

if (!params.id){
	request.session.message = "No Id given."
	redirect "/themes"
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/themes'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to delete."
	redirect "/templates/access/login.gtpl?continue=/theme/${params.id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect "/theme/${params.id}"
	return
}

def theme = dao.ofy().find(Theme.class, params.id as Long);
if (!theme) {
	request.session.message = "Unable to find theme with id: ${params.id}"
	redirect "/themes"
	return
}

if (!(users.isUserAdmin() || theme.createdBy == userinfo.username)){
	request.session.message = "You do not have permission to delete this Theme."
	redirect "/theme/${params.id}"
	return
}

dao.ofy().delete(theme)

dao.ofy().put(new Activity(type:enums.ActivityType.ThemeDeleted, title:"${theme.title}",by:userinfo.username, created: new Date()))

cacheManager.resetThemesCache()
cacheManager.resetActivityCache()


request.session.message = "Theme deleted."
redirect "/themes"