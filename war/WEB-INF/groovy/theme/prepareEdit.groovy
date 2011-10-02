package theme

import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Development
import entity.Theme

if (!params.id){
	request.session.message = "No Id given."
	redirect params.referer?:"/themes"
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/themes'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to edit."
	redirect "/templates/access/login.gtpl?continue=/theme/edit/${id}"
	return
}

def userinfo = session.userinfo
if (!userinfo){
	request.session.message = "Unable to identify user. Please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
	redirect params.referer?:"/theme/${params.id}"
	return
}

try {
	request.theme = dao.ofy().get(Theme.class, params.id as Long)
	
	if (!users.isUserAdmin() && request.theme.createdBy != userinfo.username){
		request.session.message = "You do not have permission to edit this theme."
		redirect "/themes"
		return
	}
	
	request.developments = []
	if (request.theme.developmentIds) {
		def keys = request.theme.developmentIds.collect { it as Long }
		dao.ofy().get(Development.class, keys).each { key, development  ->
			request.developments << [title: development.title, id:development.id]
		}
	}
} catch (NotFoundException nfe){
	request.session.message = "No theme with id ${params.id} found."
	redirect '/themes'
	return
}


request.pageTitle = "Edit Theme: ${request.theme.title}"
request.action="/theme/update"

forward '/templates/theme/addEdit.gtpl'