package access
import javax.servlet.http.Cookie

import com.google.appengine.api.datastore.Query

import entity.UserInfo

log.info "postlogin"

session = session?:request.getSession(true)

if (!user){
	session.message = "No User Logged In"
	redirect params.continue?:"/"
	return
}

def query = new Query("userinfo")
query.addFilter("userId", Query.FilterOperator.EQUAL, user.userId)

def userinfo = dao.ofy().find(UserInfo.class,user.userId)

if (!userinfo){
	redirect  "/access/first"
	return
}

session.setAttribute("userinfo", userinfo)

redirect params.continue?:"/"