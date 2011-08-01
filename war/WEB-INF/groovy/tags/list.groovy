package tags

import entity.Development


log.info "Retrieving Tags"

def developments = dao.ofy().query(Development.class).filter('tags != ', '').list()

def tags = [:]

developments.each{ development ->
	if (development.tags){
		development.tags.each{ t ->
			if (!tags[t]) tags[t] = 0
			tags[t]++
		}
	}
}

request.tags = tags
request.pageTitle = "Tags"

forward '/templates/tags/list.gtpl'