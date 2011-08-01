import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.KeyFactory
import com.google.appengine.api.datastore.Key

log.info "Syncing Development with RepRap Wiki"
session = session?:request.getSession(true)
if (!params.id){
	session.message = "No Id given."
	redirect '/developments'
	return
}

def id = Long.parseLong(params.id)
Key key = KeyFactory.createKey("development", id)
def development = datastore.get(key)

log.info development.syncURL

URL url = new URL(development.syncURL)

def response = url.get()

assert response.responseCode == 200
def text = response.text

if (!text.contains("{{Development\n")){
	session.message = "Unable to find Development tags in page at the given URL: <a href=\"${development.syncURL}\" target=\"_blank\">${development.syncURL}</a>. Please check the URL, or contact support."
	redirect "/development/${development.key.id}"
	return
}

def m = text.subSequence(text.indexOf("{{Development"), text.indexOf("}}",text.indexOf("{{Development"))) =~ /\|(.*?)[ ]?=[ ]?(.*?)\n/

if (m.count > 0){
	log.info "Found parameters"

	(0..<m.count).each {
		switch (m[it][1].toLowerCase()){
			case 'categories':
				categoryMatcher = m[it][2] =~ /\[\[Category:([.[^\]]]*)\]\]/
				development[m[it][1]] = categoryMatcher.collect {  it[1].split(/\|/)[0] }
				break;
			case 'name':
				development.title = m[it][2]
				break;
			default:
				development[m[it][1]] = m[it][2]
		}
	}
	development.save()
	session.message = "Sync successful."
} else {
	session.message = "The page returned by the URL did not contain any parameters. Please check the URL, or contact support."
}
redirect "/development/${development.key.id}"


