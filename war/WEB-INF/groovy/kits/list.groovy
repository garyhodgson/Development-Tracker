package kits

import static paging.pagingHelper.*
import entity.Kit
import entity.Theme


def totalCount = dao.ofy().query(Kit.class).countAll()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.kits = dao.ofy().query(Kit.class).order('created').offset(offset).limit(limit).list()

def resultsetCount = request.kits.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Kits"
request.session.route = "/kits"

forward '/templates/kits/list.gtpl'