package access
import javax.servlet.http.Cookie

import com.google.appengine.api.datastore.Query

import entity.UserInfo

if (!user){
	request.session.message = "No User Logged In"
	redirect params.continue?:"/"
	return
}

if (request.serverName.endsWith("development-tracker.info")){
	request.cookies.find{ it.name == 'ACSID' && !it.value.isEmpty() }.each { c->

		c.setDomain(".development-tracker.info")
		log.info("adding sso cookie: ${c.getDomain()}")
		c.setPath('/')
		c.setMaxAge(-1)
		response.addCookie(c)
	}
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