package paging

import app.AppProperties

public static def createPaging(int totalCount, int limit, int offset, int resultsetCount) {
	def firstPage = 0
	
	def lastPage = (resultsetCount == totalCount)? 0 : totalCount-(totalCount % limit)
	def start = Math.min(offset+1, totalCount)
	def end =  Math.min(offset+resultsetCount, totalCount)
	def nextPage = Math.min(offset+limit, lastPage)
	def previousPage = Math.max(offset-limit, 0)

	return ['total':totalCount, 'start':start, 'end':end, 'next':nextPage, 'previous':previousPage, 'last':lastPage, 'first':firstPage, 'limit':limit, 'offset':offset]
}

public static def getOffsetAndLimit(def params, def totalCount) {
	def limit = AppProperties.PAGE_LIMIT
	if (params.limit && params.limit.isNumber()){
		limit = Math.min(params.limit as int, totalCount)
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