package userinfo

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Kit
import entity.UserInfo

if (!params.username){
	request.session.message = "No Username given."
	redirect '/'
	return
}

UserInfo userinfo
def userinfoKey
namespace.of("") {
	userinfo = dao.ofy().query(UserInfo.class).filter('username', params.username).get()
}
if (!userinfo){
	request.session.message = "Unable to find user with username ${params.username}."
	redirect '/userinfos'
	return
}

namespace.of("") {
	userinfoKey = new Key(UserInfo.class, userinfo.userId)
}

request.userinfo = userinfo
request.userDevelopments = dao.ofy().query(Development.class).filter('createdBy', params.username).order('title').list()

if (userinfo.watchedDevelopments){
	Map<Long, Development> watchedDevelopmentsMap = dao.ofy().get(Development.class, userinfo.watchedDevelopments)
	request.watchedDevelopments = watchedDevelopmentsMap.values()
} else {
	request.watchedDevelopments = []
}

request.kits = dao.ofy().query(Kit.class).filter('ownerUsername', params.username).order('title').list()

def collaborations = []

dao.ofy().query(Collaboration.class).filter('userInfo', userinfoKey).list().each {

	def collaborationDevelopment = dao.ofy().find(it.development)
	if (collaborationDevelopment){
		def role = it.role == enums.Role.Other ? it.otherRole : it.role
		collaborations << [developmentId:collaborationDevelopment.id, developmentTitle: collaborationDevelopment.title, role: it.role]
	}
}
request.collaborations = collaborations

request.pageTitle = "User Info: ${userinfo.username}"

forward '/templates/userinfo/show.gtpl'