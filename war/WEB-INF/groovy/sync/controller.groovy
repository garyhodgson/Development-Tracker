import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Syncing Development with Generic Web Page"
session = session?:request.getSession(true)

if (!params.id){
	session.message = "No Id given."
	redirect '/developments'
}

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("development", id)
def development = datastore.get(key)


if (development.source == "reprapwiki"){
	redirect "reprapWiki.groovy?id=${development.key.id}"
	return
} else if (development.source == "github"){
	redirect "github.groovy?id=${development.key.id}"
	return
} else {
	redirect "genericWebsite.groovy?id=${development.key.id}"
	return
}