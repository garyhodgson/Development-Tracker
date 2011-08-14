package userinfo
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.PreparedQuery
import com.google.appengine.api.datastore.Query

import entity.UserInfo


if (!params.username){
	request.session.message = "No username given."
	redirect '/'
	return
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to edit."
	request.continue = "/userinfo/edit/${params.username}"
	forward "/templates/access/login.gtpl"
	return
}
namespace.of("") {
	UserInfo userinfo = dao.ofy().query(UserInfo.class).filter('username', params.username).get()

	if (!userinfo || (userinfo.userId != user.userId && !users.isUserAdmin())){
		request.session.message = "No permission to edit userinfo for ${params.username}. If you feel this is in error please contact <a href=\"mailto:support@development-tracker.info\">support</a>."
		redirect '/'
		return
	}

	request.userinfo = userinfo
}
request.pageTitle = "Edit Userinfo: ${params.username}"
forward '/templates/userinfo/edit.gtpl'