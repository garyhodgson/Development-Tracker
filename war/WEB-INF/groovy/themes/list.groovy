package themes

import static paging.pagingHelper.*
import entity.Theme
import static enums.MemcacheKeys.*


def totalCount = cacheManager.themeCount()

def (offset,limit) = getOffsetAndLimit(params, totalCount)


request.themes = cacheManager.latestThemes(offset,limit)

def resultsetCount = request.themes?.size()?:0

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Themes"
request.session.route = "/themes"

forward '/templates/themes/list.gtpl'