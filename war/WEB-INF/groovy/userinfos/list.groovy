package userinfos

import entity.UserInfo
import static paging.pagingHelper.*

log.info "Retrieving User Infos"

namespace.of("") {
	def totalCount = dao.ofy().query(UserInfo.class).countAll()

	def (offset,limit) = getOffsetAndLimit(params, totalCount)

	if (offset > totalCount){
		request.session.message = "Offset too large"
		forward '/templates/userinfos/list.gtpl'
		return
	}
	request.userinfos = dao.ofy().query(UserInfo.class).order('-created').offset(offset).limit(limit).list()

	if (request.userinfos){
		def resultsetCount = request.userinfos.size()

		request.paging = createPaging(totalCount, limit, offset, resultsetCount)
	}
}

request.route = "/userinfos"

forward '/templates/userinfos/list.gtpl'