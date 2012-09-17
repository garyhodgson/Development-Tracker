package developments

import static paging.pagingHelper.*
import static enums.MemcacheKeys.*
import entity.Development


def totalCount = cacheManager.developmentCount()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.developments = cacheManager.latestDevelopments(offset,limit)

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Latest Developments"

forward '/templates/developments/list.gtpl'