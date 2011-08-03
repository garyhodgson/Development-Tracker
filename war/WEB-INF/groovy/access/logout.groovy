package access

import javax.servlet.http.Cookie


if (session && session.userinfo){
	session.userinfo = null
}

if (request.serverName.endsWith("development-tracker.info")){
	request.cookies.find{ it.name == 'ACSID'}.each { c ->
		c.setDomain(".development-tracker.info")
		
		log.info("invalidating sso cookie: ${c.getDomain()}")
		
		c.setPath('/')
		c.setValue('')
		c.setMaxAge(0)
		response.addCookie(c)
	}
}

redirect users.createLogoutURL("http://${headers.Host}")