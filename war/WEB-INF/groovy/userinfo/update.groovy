package userinfo
import org.apache.commons.lang.StringEscapeUtils

import entity.UserInfo

log.info "Update Userinfo"

if (!params.username){
	context.message = "No username given."
	redirect '/'
}

if (!users.isUserLoggedIn()){
	session.message = "Must be logged in to edit."
	request.continue = "/userinfo/edit/${params.username}"
	forward "/templates/access/login.gtpl"
	return
}

namespace.of("") {


	UserInfo userinfo = dao.ofy().query(UserInfo.class).filter('username', params.username).get()

	if (!userinfo || (userinfo.userId != user.userId && !users.isUserAdmin())){
		session.message = "No permission to edit userinfo for ${params.username}. If you feel this is in error please contact <a href=\"mailto:support@development-tracker.info\">support</a>."
		redirect '/'
		return
	}

	params.each { k, v ->
		//sanitise
		if (v) {
			userinfo[k] = StringEscapeUtils.escapeHtml v
		}
	}

	//checkboxes
	[
		'useGravatar',
		'contactOnDevelopmentComment',
		'contactOnDevelopmentWatch',
		'acceptTermsOfUse',
		'githubIdVisible',
		'thingiverseIdVisible',
		'reprapWikiIdVisible'
	].each {
		userinfo[it] = params[it]?true:false
	}

	userinfo.updated = new Date()

	dao.ofy().put(userinfo)
}
redirect "/userinfo/${params.username}"