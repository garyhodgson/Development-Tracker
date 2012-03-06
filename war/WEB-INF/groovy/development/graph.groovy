package development

request.id = params.id
request.allDevelopmentsHash = cacheManager.allDevelopmentsHash()

forward '/templates/development/graph.gtpl'