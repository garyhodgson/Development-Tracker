package paging

import app.AppProperties

public static def createPaging(int itemCount, int limit, int offset, int resultsetCount) {
	def firstPage = 0
	
	def lastPage = (resultsetCount == itemCount)? 0 : itemCount-(itemCount % limit)
	def start = Math.min(offset+1, itemCount)
	def end =  Math.min(offset+resultsetCount, itemCount)
	def nextPage = Math.min(offset+limit, lastPage)
	def previousPage = Math.max(offset-limit, 0)

	return ['total':itemCount, 'start':start, 'end':end, 'next':nextPage, 'previous':previousPage, 'last':lastPage, 'first':firstPage, 'limit':limit, 'offset':offset]
}

public static def getOffsetAndLimit(def params, def totalCount) {
	def limit = AppProperties.PAGE_LIMIT
	if (params.limit && params.limit.isNumber()){
		limit = Math.min(params.limit as int, AppProperties.PAGE_LIMIT)
	}
	def offset = 0
	if (params.offset && params.offset.isNumber()){
		offset = params.offset as int
	}
	if (offset > totalCount){
		offset = Math.max(totalCount - limit, 0)
	}
	return [offset,limit]
}