package developments
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import entity.Development
import groovy.xml.MarkupBuilder

import java.text.SimpleDateFormat

SimpleDateFormat sdf =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

def xml = new MarkupBuilder(out)
def limit = params.limit ? Math.min(params.limit, 100) : 25

def developments = dao.ofy().query(Development.class).limit(limit as int).order('-created').list() 

def serverName= headers.Host

out << "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
def feedTitle = "Development Tracker"
def feedDescription = "Latest Developments"
def feedLink = "http://${serverName}/developments/latest"
def lastUpdated = sdf.format(new Date())

if (params.feedtype == 'rss'){
	xml.rss(version:"2.0") {

		channel(){
			title(feedTitle)
			description(feedDescription)
			link(feedLink)
			lastBuildDate(lastUpdated)

			developments.each { development ->
				item() {
					title(development.title?:'')
					author(development.createdBy?:'')
					link("http://${serverName}/development/${development.id}")
					pubDate(development.created)
					description(development.description?:'')
					development.categories?.each{ category(it) }
				}
			}
		}
	}
} else {
	xml.feed(xmlns:'http://www.w3.org/2005/Atom') {

		// add the top level information about this feed.
		title(feedTitle)
		subtitle(feedDescription)
		id("url:http://${serverName}/developments/latest")
		link(href:feedLink)
		author { name("Development Tracker") }
		updated(lastUpdated)

		developments.each { development ->
			entry {
				title development.title?:''
				link("http://${serverName}/development/${development.id}")
				id "url:http://${serverName},entry,${development.id}"
				updated sdf.format(development.updated)
				summary development.description?:''
			}
		}
	}
}