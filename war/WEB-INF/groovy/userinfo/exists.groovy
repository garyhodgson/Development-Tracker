package userinfo

import entity.UserInfo

if (!params.username){
	log.warn "username parameter missing from UserInfo exists check"
	return
}

out << (dao.ofy().query(UserInfo.class).filter('username', params.username).countAll() != 0)

return