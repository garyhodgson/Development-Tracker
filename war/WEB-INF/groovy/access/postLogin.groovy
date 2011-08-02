package access
import javax.servlet.http.Cookie

import com.google.appengine.api.datastore.Query

import entity.UserInfo

log.info "postlogin"

if (!user){
	request.session.message = "No User Logged In"
	redirect params.continue?:"/"
	return
}

// For SSO
def acsid = request.cookies.find{ it.name == 'ACSID'}
if (acsid){
	acsid.setDomain('.development-tracker.info')
	acsid.setPath('/')
	response.addCookie(acsid)
}

def userinfo

namespace.of("") {
	userinfo = dao.ofy().find(UserInfo.class,user.userId)
}

if (!userinfo){
	redirect  "/access/first"
	return
}

request.session.setAttribute("userinfo", userinfo)



redirect params.continue?:"/"