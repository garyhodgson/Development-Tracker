package developments

import static paging.pagingHelper.*
import static enums.MemcacheKeys.*
import entity.Development
import javax.servlet.ServletException


def totalCount = cacheManager.developmentCount()
	
if (totalCount > 0){
		
	def developments = cacheManager.allDevelopments()
	
	def (offset,limit) = getOffsetAndLimit(params, totalCount)
	
	def from = offset
	def to = Math.min(totalCount-1, (offset + limit)-1)
	
	request.developments = developments[from..to]
	
	def resultsetCount = request.developments?.size()?:0
	request.paging = createPaging(totalCount, limit, offset, resultsetCount)

}


request.pageTitle = "Developments"
request.session.route = "/developments"

forward '/templates/developments/list.gtpl'