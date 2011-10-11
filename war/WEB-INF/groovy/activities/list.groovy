package activities

import static paging.pagingHelper.*
import static enums.MemcacheKeys.*
import entity.Activity

def totalCount = cacheManager.activityCount()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

def memcacheKey = "${LatestActivities}:${offset}:${limit}"

request.activities = memcache[memcacheKey] ?:
		(memcache[memcacheKey] = dao.ofy().query(Activity.class).order('-created').offset(offset).limit(limit).list())

def resultsetCount = request.activities?.size()?:0

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Recent Activities"
request.session.route = "/activities"

forward '/templates/activities/list.gtpl'