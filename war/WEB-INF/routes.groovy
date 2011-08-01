import static com.google.appengine.api.capabilities.Capability.*
import static com.google.appengine.api.capabilities.CapabilityStatus.*

all "/_ah/**", ignore: true

get "/developments",  forward: "/developments/list.groovy"
get "/developments/latest",  forward: "/developments/latest.groovy"
get "/developments/latest/feed",  forward: "/developments/feed.groovy?feedtype=atom"
get "/developments/latest/feed.@feedtype",  forward: "/developments/feed.groovy?feedtype=@feedtype"
get "/developments/latest/@count",  forward: "/developments/latest.groovy?count=@count"

get "/categories",  forward: "/categories/list.groovy"
get "/tags",  forward: "/tags/list.groovy"


get "/developments/@searchField/@value",  forward: "/developments/search.groovy?searchField=@searchField&value=@value"
get "/developments/@searchField",  forward: "/developments/search.groovy?searchField=@searchField"

get "/activities", forward: "/activities/list.groovy"

get "/development/add",  forward: {
	to "/development/prepareAdd.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/readonly.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}
post "/development/add", forward:  "/development/add.groovy"
get "/development/exists/@title", forward: "/development/exists.groovy?title=@title"

get "/development/test",  forward: "/development/test.groovy"
get "/development/test@id",  forward: "/development/test@id.groovy"

get "/development/edit/@id",  forward: {
	to "/development/prepareEdit.groovy?id=@id"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/readonly.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}
post "/development/update", forward: "/development/update.groovy"

get "/development/watch/@id",  forward: "/development/watch.groovy?id=@id"
get "/development/unwatch/@id",  forward: "/development/unwatch.groovy?id=@id"

get "/development/validateSyncURL/@syncURL",  forward: "/sync/validateRepRapWikiSyncURL.groovy?syncURL=@syncURL"

get "/development/sync/@id",  forward: "/sync/controller.groovy?id=@id"
get "/development/@id/watchers",  forward: "/development/watchers.groovy?id=@id"
get "/development/@id",  forward: "/development/show.groovy?id=@id"


get "/access/first", forward: {
	to "/templates/access/first.gtpl"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/readonly.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}
get "/access/login", forward: "/templates/access/login.gtpl"
get "/access/login?continue=@continue", forward: "/templates/access/login.gtpl?continue=@continue"
get "/access/postLogin", forward: "/access/postLogin.groovy"

get "/access/logout", forward: "/access/logout.groovy"

post "/userinfo/add", forward: {
	to "/userinfo/add.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/readonly.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}

get "/userinfo/exists/@username", forward: "/userinfo/exists.groovy?username=@username"
get "/userinfo/edit/@username",  forward: {
	to "/userinfo/prepareEdit.groovy?username=@username"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/readonly.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}
get "/userinfo/@username", forward: "/userinfo/show.groovy?username=@username"
post "/userinfo/update", forward: "/userinfo/update.groovy"

get "/userinfos", forward: "/userinfos/list.groovy"

get "/about", forward: "/templates/static/about.gtpl"
get "/future", forward: "/templates/static/future.gtpl"
get "/faq", forward: "/templates/static/faq.gtpl"
get "/terms", forward: "/templates/static/terms.gtpl"
get "/maintenance", forward: "/templates/static/maintenance.gtpl"
get "/start/@namespace", forward: "/index.groovy?namespace=@namespace"

get "/help/@topic", forward: "/help/help.groovy?topic=@topic"

get "/favicon.ico", redirect: "/images/favicon.ico"
get "/", forward: "/index.groovy"