package developments

import static paging.pagingHelper.*
import entity.Development


def totalCount = dao.ofy().query(Development.class).countAll()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.developments = dao.ofy().query(Development.class).order('title').offset(offset).limit(limit).list()

def resultsetCount = request.developments.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Developments"
request.session.route = "/developments"

forward '/templates/developments/list.gtpl'