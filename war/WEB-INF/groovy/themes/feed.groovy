package themes
import static com.google.appengine.api.datastore.FetchOptions.Builder.*
import entity.Theme
import groovy.xml.MarkupBuilder

import java.text.SimpleDateFormat

SimpleDateFormat sdf =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

def xml = new MarkupBuilder(out)
def limit = params.limit ? Math.min(params.limit, 100) : 25

def themes = dao.ofy().query(Theme.class).limit(limit as int).order('-created').list() 

def serverName= headers.Host

out << "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
def feedTitle = "Development Tracker - Latest Themes"
def feedDescription = "Latest Themes"
def feedLink = "http://${serverName}/themes/latest"
def lastUpdated = sdf.format(new Date())

if (params.feedtype == 'rss'){
	xml.rss(version:"2.0") {

		channel(){
			title(feedTitle)
			description(feedDescription)
			link(feedLink)
			lastBuildDate(lastUpdated)

			themes.each { theme ->
				item() {
					title(theme.title?:'')
					author(theme.createdBy?:'')
					link("http://${serverName}/theme/${theme.id}")
					pubDate(theme.created)
					description(theme.description?:'')
				}
			}
		}
	}
} else {
	xml.feed(xmlns:'http://www.w3.org/2005/Atom') {

		// add the top level information about this feed.
		title(feedTitle)
		subtitle(feedDescription)
		id("url:http://${serverName}/themes/latest")
		link(href:feedLink)
		author { name("Development Tracker") }
		updated(lastUpdated)

		themes.each { theme ->
			entry {
				title theme.title?:''
				link("http://${serverName}/theme/${theme.id}")
				id "url:http://${serverName},entry,${theme.id}"
				updated sdf.format(theme.updated)
				summary theme.description?:''
			}
		}
	}
}