package userinfos

import entity.UserInfo
import static paging.pagingHelper.*

log.info "Retrieving User Info"

session = session?:request.getSession(true)

def totalCount = dao.ofy().query(UserInfo.class).countAll()

def (offset,limit) = getOffsetAndLimit(params)

if (offset > totalCount){
	session.message = "Offset too large"
	forward '/templates/userinfos/list.gtpl'
	return
}
request.userinfos = dao.ofy().query(UserInfo.class).order('-created').offset(offset).limit(limit).list()

def resultsetCount = request.userinfos.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)


request.route = "/userinfos"

forward '/templates/userinfos/list.gtpl'