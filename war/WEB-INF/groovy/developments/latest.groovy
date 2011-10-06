package developments

import static paging.pagingHelper.*
import static enums.MemcacheKeys.*
import app.MemcacheKeys
import entity.Development


def totalCount = memcache[TotalDevelopmentCount]?:(memcache[TotalDevelopmentCount] = dao.ofy().query(Development.class).countAll())

def (offset,limit) = getOffsetAndLimit(params, totalCount)

def memcacheKey = "${LatestDevelopments}:${offset}:${limit}"

request.developments = memcache[memcacheKey] ?:
		(memcache[memcacheKey] = dao.ofy().query(Development.class).order('-created').offset(offset).limit(limit).list())

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Latest Developments"

forward '/templates/developments/list.gtpl'