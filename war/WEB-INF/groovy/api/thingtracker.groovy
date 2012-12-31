package api

import static enums.MemcacheKeys.*
import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.*
import com.googlecode.objectify.Key
import groovy.json.JsonBuilder
import org.apache.commons.lang.StringEscapeUtils

response.contentType = 'application/json'
def data = []

if (ThingTrackerCache.name() in memcache) {
	data = memcache[ThingTrackerCache.name()]
} else {
	
	def devs = cacheManager.allDevelopments()
	def collaborations = dao.ofy().query(Collaboration.class).list() 
	def things = []
	
	devs.each { dev ->
		def developmentKey = new Key(Development.class, dev.id as Long)
		def thing = ["title": dev.title, "URL": dev.sourceURL]
		
		if (dev.description){
			thing.description = StringEscapeUtils.escapeJavaScript(dev.description)
		}
		
		def authors = collaborations.findAll { it.development == developmentKey && it.role == Role.Author }
		if (authors){
			thing.author = authors.collect { it.name }.join(',')
		}
		
		if (dev.license){
			thing.license = dev.license.title
		}
		
		if (dev.tags){
			thing.tags = dev.tags
		}
		
		if (dev.thumbnailServingUrl){
			thing.thumbnailURL = dev.thumbnailServingUrl + "=s64"
		}
		
		things << thing
	}
	
	data = [
		things:things
	]
	
	memcache[ThingTrackerCache.name()] = data
}

def json = new JsonBuilder(data)
sout << json.toString()
