package activities

import static paging.pagingHelper.*
import entity.Activity

def totalCount = dao.ofy().query(Activity.class).countAll()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.activities = dao.ofy().query(Activity.class).order('-created').offset(offset).limit(limit).list()

def resultsetCount = request.activities.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Recent Activities"
request.session.route = "/activities"

forward '/templates/activities/list.gtpl'