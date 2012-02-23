package userinfo

import entity.UserInfo

if (!params.username){
	log.warn "username parameter missing from UserInfo exists check"
	return
}
namespace.of("") {
	out << (dao.ofy().query(UserInfo.class).filter('username', params.username).count() != 0)
}
return