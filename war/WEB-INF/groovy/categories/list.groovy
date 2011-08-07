package categories

import entity.Development

log.info "Retrieving Categories"

def categoryStats = [:]

enums.Category.each {
	def count = dao.ofy().query(Development.class).filter('categories', it.name()).countAll()

	categoryStats[it] = ['id':it, 'title':it.title, 'count':count]
}

request.categoryStats = categoryStats
request.pageTitle = "Categories"
request.session.route = "/categories"


forward '/templates/categories/list.gtpl'