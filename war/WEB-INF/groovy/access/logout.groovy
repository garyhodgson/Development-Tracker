package access

if (session){
	session.userinfo = null
}

def acsid = request.cookies.find{ it.name == 'ACSID'}
if (acsid){
	acsid.setValue('')
	acsid.setMaxAge(0)
	response.addCookie(acsid)

	acsid.setDomain('.development-tracker.info')
	acsid.setValue('')
	acsid.setMaxAge(0)
	response.addCookie(acsid)
}

redirect '/'