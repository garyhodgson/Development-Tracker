package themes

import static paging.pagingHelper.*
import entity.Theme
import static enums.MemcacheKeys.*


def totalCount = memcache[TotalThemesCount]?:(memcache[TotalThemesCount] = dao.ofy().query(Theme.class).countAll())

def (offset,limit) = getOffsetAndLimit(params, totalCount)

def memcacheKey = "${LatestThemes}:${offset}:${limit}"

request.themes = memcache[memcacheKey] ?:
		(memcache[memcacheKey] = dao.ofy().query(Theme.class).order('-created').offset(offset).limit(limit).list())

def resultsetCount = request.themes?.size()?:0

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Themes"
request.session.route = "/themes"

forward '/templates/themes/list.gtpl'