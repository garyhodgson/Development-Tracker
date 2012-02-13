import static com.google.appengine.api.capabilities.Capability.*
import static com.google.appengine.api.capabilities.CapabilityStatus.*


all "/_ah/warmup", forward: "/warmupRequestHandler.groovy"
all "/_ah/**", ignore: true


get "/robots.txt", forward: "/robots.txt"
get "/robots.txt/", forward: "/robots.txt"
get "/403/", forward: "/templates/static/403.gtpl"
get "/404/", forward: "/templates/static/404.gtpl"
get "/error/", forward: "/templates/static/error.gtpl"
get "/about", forward: "/templates/static/about.gtpl"
get "/future", forward: "/templates/static/future.gtpl"
get "/faq", forward: "/templates/static/faq.gtpl"
get "/terms", forward: "/templates/static/terms.gtpl"
get "/maintenance", forward: "/templates/static/maintenance.gtpl"
get "/blog", redirect: app.AppProperties.BLOG_ADDRESS
get "/favicon.ico", redirect: "/images/favicon.ico"

/* API */
get "/api/@version/developments", forward: "/api/@version/developments.groovy"

/* Developments */
get "/developments",  forward: "/developments/list.groovy"
get "/developments/latest",  forward: "/developments/latest.groovy"
get "/developments/latest/feed",  forward: "/developments/feed.groovy?feedtype=atom"
get "/developments/latest/feed.@feedtype",  forward: "/developments/feed.groovy?feedtype=@feedtype"
get "/developments/latest/@count",  forward: "/developments/latest.groovy?count=@count"

all "/developments/autocomplete",  forward: "/developments/autocomplete.groovy"

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

get "/development/add/cancel/@imageBlobKey", forward:  "/development/cancelAdd.groovy?imageBlobKey=@imageBlobKey"

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


/* Theme */
get "/themes", forward: "/themes/list.groovy"
get "/themes/latest", forward: "/themes/list.groovy"
get "/themes/latest/feed.@feedtype",  forward: "/themes/feed.groovy?feedtype=@feedtype"
get "/theme/add",  forward: {
	to "/theme/prepareAdd.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
} 
post "/theme/add", forward:  "/theme/add.groovy"
get "/theme/edit/@id",  forward: {
	to "/theme/prepareEdit.groovy?id=@id"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
post "/theme/update", forward: "/theme/update.groovy"
get "/theme/delete/@id",  forward: "/theme/delete.groovy?id=@id"
get "/theme/@id",  forward: "/theme/show.groovy?id=@id"


/* Activities */
get "/activities", forward: "/activities/list.groovy"

/* Admin */
get "/admin/memcache/clear", forward: "/admin/memcache/clear.groovy"
get "/admin/memcache/stats", forward: "/admin/memcache/stats.groovy"

get "/admin/upload", forward: "/templates/admin/upload.gtpl"

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


/* Kit */
get "/kits", forward: "/kits/list.groovy"
get "/kit/add",  forward: {
	to "/kit/prepareAdd.groovy"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
post "/kit/add", forward:  "/kit/add.groovy"
get "/kit/edit/@id",  forward: {
	to "/kit/prepareEdit.groovy?id=@id"
	to("/templates/static/maintenance.gtpl").on(DATASTORE).not(ENABLED)
	to("/templates/static/maintenance.gtpl").on(DATASTORE_WRITE).not(ENABLED)
}
post "/kit/update", forward: "/kit/update.groovy"
get "/kit/delete/@id",  forward: "/kit/delete.groovy?id=@id"
get "/kit/@id",  forward: "/kit/show.groovy?id=@id"



/* Userinfos */
get "/userinfos", forward: "/userinfos/list.groovy"

/* Other */
get "/start/@namespace", forward: "/index.groovy?namespace=@namespace"
get "/help/@topic", forward: "/help/help.groovy?topic=@topic"
get "/", forward: "/index.groovy"