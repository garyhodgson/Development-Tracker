package developments

import static paging.pagingHelper.*
import static enums.MemcacheKeys.*
import entity.Development


def totalCount = memcache[TotalDevelopmentCount]?:(memcache[TotalDevelopmentCount] = dao.ofy().query(Development.class).countAll())

def (offset,limit) = getOffsetAndLimit(params, totalCount)

def memcacheKey = "${AlphabeticDevelopments}:${offset}:${limit}"

request.developments = memcache[memcacheKey] ?:
		(memcache[memcacheKey] = dao.ofy().query(Development.class).order('title').offset(offset).limit(limit).list())

def resultsetCount = request.developments?.size()?:0

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Developments"
request.session.route = "/developments"

forward '/templates/developments/list.gtpl'