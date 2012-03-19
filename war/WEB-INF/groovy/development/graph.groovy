package development

request.id = params.id
request.allDevelopmentsLastUpdated = cacheManager.allDevelopmentsLastUpdated()
request.allDevelopmentsHash = cacheManager.allDevelopmentsHash()

forward '/templates/development/graph.gtpl'