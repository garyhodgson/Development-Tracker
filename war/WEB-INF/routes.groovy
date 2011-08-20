import static com.google.appengine.api.capabilities.Capability.*
import static com.google.appengine.api.capabilities.CapabilityStatus.*

all "/_ah/**", ignore: true

get "/templates/static/maintenance.gtpl", ignore: true
get "/templates/static/error.gtpl", ignore: true

/* Developments */
get "/developments",  forward: "/developments/list.groovy"
get "/developments/latest",  forward: "/developments/latest.groovy"
get "/developments/latest/feed",  forward: "/developments/feed.groovy?feedtype=atom"
get "/developments/latest/feed.@feedtype",  forward: "/developments/feed.groovy?feedtype=@feedtype"
get "/developments/latest/@count",  forward: "/developments/latest.groovy?count=@count"

get "/developments/search",  forward: "/templates/developments/search.gtpl"
get "/developments/search/@searchKey",  forward: "/developments/search.groovy?searchKey=@searchKey"
post "/developments/search",  forward: "/developments/search.groovy"

get "/developments/browse",  forward: "/templates/developments/browse.gtpl"
get "/developments/browse/tags",  forward: "/developments/browse/tags.groovy"
get "/developments/browse/@field",  forward: "/developments/browse/prepareBrowse.groovy?field=@field"
get "/developments/@searchField/@value",  forward: "/developments/browse/browse.groovy?searchField=@searchField&value=@value"
get "/developments/@searchField",  forward: "/developments/browse/browse.groovy?searchField=@searchField"


/* Development */
get "/development/add",  forward: {
	to "/development/prepareAdd.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
post "/development/add", forward:  "/development/add.groovy"
get "/development/exists/@field/", forward: "/development/exists.groovy?field=@field&value="
get "/development/exists/@field/@value", forward: "/development/exists.groovy?field=@field&value=@value"
get "/development/edit/@id",  forward: {
	to "/development/prepareEdit.groovy?id=@id"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
post "/development/update", forward: "/development/update.groovy"
get "/development/watch/@id",  forward: "/development/watch.groovy?id=@id"
get "/development/delete/@id",  forward: "/development/delete.groovy?id=@id"
get "/development/unwatch/@id",  forward: "/development/unwatch.groovy?id=@id"
get "/development/validateSyncURL/@syncURL",  forward: "/sync/validateRepRapWikiSyncURL.groovy?syncURL=@syncURL"
get "/development/sync/@id",  forward: "/sync/controller.groovy?id=@id"
get "/development/@id/watchers",  forward: "/development/watchers.groovy?id=@id"
get "/development/@id/history",  forward: "/history/history.groovy?id=@id"
get "/development/@id/history/@changeHistoryId",  forward: "/history/history.groovy?id=@id&changeHistoryId=@changeHistoryId"
get "/development/@id",  forward: "/development/show.groovy?id=@id"


/* Misc */
get "/activities", forward: "/activities/list.groovy"

/* Admin */
get "/admin/memcache/clear", forward: "/admin/memcache/clear.groovy"
get "/admin/memcache/stats", forward: "/admin/memcache/stats.groovy"
get "/admin/@target",  forward: "/admin/@target.groovy"

/* Access */
get "/access/first", forward: {
	to "/templates/access/first.gtpl"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
get "/access/login", forward: "/templates/access/login.gtpl"
get "/access/login?continue=@continue", forward: "/templates/access/login.gtpl?continue=@continue"
get "/access/postLogin", forward: "/access/postLogin.groovy"
get "/access/logout", forward: "/access/logout.groovy"


/* Userinfo */
post "/userinfo/add", forward: {
	to "/userinfo/add.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
get "/userinfo/exists/@username", forward: "/userinfo/exists.groovy?username=@username"
get "/userinfo/edit/@username",  forward: {
	to "/userinfo/prepareEdit.groovy?username=@username"
	to("/templates/static/maintenance.gtpl").on(DATASTORE)      .not(ENABLED)
	to("/templates/static/maintenance.gtpl")   .on(DATASTORE_WRITE).not(ENABLED)
}
get "/userinfo/@username", forward: "/userinfo/show.groovy?username=@username"
post "/userinfo/update", forward: "/userinfo/update.groovy"


/* Userinfos */
get "/userinfos", forward: "/userinfos/list.groovy"


/* Static */
get "/about", forward: "/templates/static/about.gtpl"
get "/future", forward: "/templates/static/future.gtpl"
get "/faq", forward: "/templates/static/faq.gtpl"
get "/terms", forward: "/templates/static/terms.gtpl"
get "/maintenance", forward: "/templates/static/maintenance.gtpl"
get "/blog", redirect: app.AppProperties.BLOG_ADDRESS

/* Other */
get "/start/@namespace", forward: "/index.groovy?namespace=@namespace"
get "/help/@topic", forward: "/help/help.groovy?topic=@topic"
get "/favicon.ico", redirect: "/images/favicon.ico"
get "/", forward: "/index.groovy"