package theme

import com.googlecode.objectify.Key

import entity.Development
import entity.Relationship

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a theme."
	redirect "/access/login?continue=/theme/add"
	return
}

forward '/templates/theme/addEdit.gtpl'