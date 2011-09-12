package themes

import static paging.pagingHelper.*
import entity.Theme


def totalCount = dao.ofy().query(Theme.class).countAll()

def (offset,limit) = getOffsetAndLimit(params, totalCount)

request.themes = dao.ofy().query(Theme.class).order('created').offset(offset).limit(limit).list()

def resultsetCount = request.themes.size()

request.paging = createPaging(totalCount, limit, offset, resultsetCount)

request.pageTitle = "Themes"
request.session.route = "/themes"

forward '/templates/themes/list.gtpl'