package developments

import com.googlecode.objectify.Query
import static paging.pagingHelper.*
import entity.Development

log.info "Retrieving Developments"
session = session?:request.getSession(true)

def totalCount = dao.ofy().query(Development.class).countAll()

def (offset,limit) = getOffsetAndLimit(params)

if (offset > totalCount){
	session.message = "Offset too large"
	forward '/templates/developments/list.gtpl'
	return
}

request.developments = dao.ofy().query(Development.class).order('title').offset(offset).limit(limit).list()

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Developments"
session.route = "/developments"

forward '/templates/developments/list.gtpl'
