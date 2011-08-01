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

URL url = new URL(development.syncURL)

def response = url.get()

assert response.responseCode == 200
def text = response.text
def trackingId = "development-tracker.info:${key.id}"

log.info "trackingId = ${trackingId}"

if (text.contains(trackingId)){
	def m = text =~ /<td id="${trackingId}\|(.*)">(.*)<\/td>/

	log.info "${m.count}"
	
	if (m.count > 0) {
		for (i in 0..m.count-1){
			log.info "${m[i][1]} - ${m[i][2]}"
			
			development[m[i][1]] = m[i][2]
		}
	}
	
	development.save()
	session.message = "Sync OK"
	
} else {
	session.message = "The page returned by the URL did not contain the required tracking text: \"${trackingId}\"."
}

request.development = development
request.globalTrackerId = "development-tracker.info:${id}"
request.localTrackerId = "reprap.development-tracker.info:${id}"

redirect "/development/${development.key.id}"
