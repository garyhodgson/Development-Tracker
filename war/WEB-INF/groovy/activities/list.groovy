package activities

import static paging.pagingHelper.*
import entity.Activity


log.info "Retrieving Activities"
session = session?:request.getSession(true)


def totalCount = dao.ofy().query(Activity.class).countAll()

def (offset,limit) = getOffsetAndLimit(params)

if (offset > totalCount){
	session.message = "Offset too large"
	forward params.referer?:'/templates/activities/list.gtpl'
	return
}

request.activities = dao.ofy().query(Activity.class).order('-created').offset(offset).limit(limit).list()

def resultsetCount = request.activities.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Recent Activities"
session.route = "/activities"

forward '/templates/activities/list.gtpl'