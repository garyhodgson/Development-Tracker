package kit


if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to add a kit."
	redirect "/access/login?continue=/setup/add"
	return
}

forward '/templates/kit/addEdit.gtpl'