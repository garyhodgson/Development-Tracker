package kits

import static enums.MemcacheKeys.*
import static paging.pagingHelper.*
import entity.Development
import entity.Kit


def totalCount = cacheManager.kitCount()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

def memcacheKey = "${LatestKits}:${offset}:${limit}"

request.kits = memcache[memcacheKey] ?:
		(memcache[memcacheKey] = dao.ofy().query(Kit.class).order('-created').offset(offset).limit(limit).list())


def resultsetCount = request.kits?.size()?:0

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Kits"
request.session.route = "/kits"

forward '/templates/kits/list.gtpl'