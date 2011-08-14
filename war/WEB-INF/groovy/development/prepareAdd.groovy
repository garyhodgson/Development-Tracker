package development

import com.googlecode.objectify.Key

import entity.Development
import entity.Relationship

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a development."
	redirect "/access/login?continue=/development/add"
	return
}

forward '/templates/development/addEdit.gtpl'