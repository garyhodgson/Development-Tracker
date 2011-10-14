package theme

import static development.developmentHelper.*

import com.googlecode.objectify.Key

import entity.Activity
import entity.Development
import entity.Theme

if (!params.id){
	request.session.message = "No Id given."
	redirect '/themes'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/themes'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to update a theme."
	forward "/templates/access/login.gtpl?continue=/theme/edit/${params.id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect params.referer?:"/theme/${id}"
	return
}

def themeKey = new Key(Theme.class, params.id as Long)
def theme = dao.ofy().get(themeKey)
if (!theme) {
	request.session.message = "Unable to find theme with Id ${params.id}."
	redirect params.referer?:'/themes'
	return
}

if (!users.isUserAdmin() && theme.createdBy != userinfo.username){
	request.session.message = "You do not have permission to edit this theme."
	redirect "/themes"
	return
}

theme.title = params.title
theme.description= params.description
theme.developmentIds = new ArrayList<Long>()

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

dao.ofy().put(new Activity(type:enums.ActivityType.ThemeUpdated, title:"${theme.title}",by:theme.createdBy, created: new Date(), link :"/theme/${theme.id}"))

redirect "/theme/${theme.id}"


