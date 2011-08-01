package development

import com.googlecode.objectify.Key

import entity.Development
import entity.Relationship

log.info "Adding Development"

session = session?:request.getSession(true)

if (!users.isUserLoggedIn()){
	session.message = "Must be logged in to add a development."
	redirect "/access/login?continue=/development/add"
	return
}

forward '/templates/development/addEdit.gtpl'