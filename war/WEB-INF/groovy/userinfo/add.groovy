package userinfo

import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import app.AppProperties

import entity.Activity
import entity.UserInfo

if (!params.username){
	request.session.message = "Username is required."
	forward: "/templates/access/first.gtpl"
	return
}

def userinfo

namespace.of("") {
	
	if (dao.ofy().query(UserInfo.class).filter('username', params.username).countAll() != 0) {
		request.session.message = "Username already taken. Please choose another."
		forward: "/templates/access/first.gtpl"
		return
	}

	userinfo = new UserInfo(userId:user.getUserId(), created: new Date(), updated: new Date())

	params.each { k, v ->
		//sanitise - happens in security filter
		if (v) {
			userinfo[k] = v
		}
	}

	//checkboxes
	[
		'useGravatar',
		'acceptTermsOfUse'
	].each {
		userinfo[it] = params[it]?true:false
	}


	if (params.useGravatar){
		// saves having to generate it every time.
		userinfo.gravatarHash = UserInfo.calculateGravatarHash(userinfo.email)
	}

	dao.ofy().put(userinfo)
}

dao.ofy().put(new Activity(type:enums.ActivityType.NewUser, title:"${userinfo.username}",by:userinfo.username, created: new Date(), link :"/userinfo/${userinfo.username}"))

mail.sendToAdmins from: AppProperties.ADMIN_EMAIL,
		subject: "New userinfo: ${userinfo.username}",
		textBody: "A new user registered. ${headers.Host}/userinfo/${userinfo.username}"

request.session.userinfo = userinfo

redirect "/userinfo/${params.username}"