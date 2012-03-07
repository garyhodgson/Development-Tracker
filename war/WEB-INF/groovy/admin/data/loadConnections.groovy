package admin.data

import com.googlecode.objectify.Key

import entity.Development
import entity.Relationship

/*
def txt = new File('data/testdata.xml').getText()
//def txt = new File('data/reprap.development-tracker.info-developments.xml').getText()

def developments = new XmlSlurper().parseText(txt)

def developmentList = developments.development

developmentList.each {
	def id = it.@id.text() as Long
	def developmentKey = new Key(Development.class, id)
	
	it.connections.connection.each {
		def typeName = it.@type?.text()
		
		def type = (typeName)? enums.RelationshipType.valueOf(typeName) : enums.RelationshipType.Link		
		def url = it.@url.text()
		def title = it.text()

		def r = new Relationship(from:developmentKey, toUrl:url, 'type':type, description:title)

		if (url.startsWith("http://reprap.development-tracker.info/development/")){
			def toId = (url.substring(url.lastIndexOf("/")+1))
			r.to = new Key(Development.class, toId)			
		}		
		
		dao.ofy().put(r)
	}
	

}
cacheManager.resetDevelopmentCache()
*/