package api.v1

import static enums.MemcacheKeys.*
import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.*
import com.googlecode.objectify.Key
import groovy.xml.MarkupBuilder

def clientLastUpdated = null
if (params.lastUpdated && params.lastUpdated != "0"){
	clientLastUpdated = Date.parse('yyyyMMddHHmmssS',params.lastUpdated)
}

def devs = clientLastUpdated ? cacheManager.allDevelopments().findAll {it.updated > clientLastUpdated } : cacheManager.allDevelopments()

def devsHash = cacheManager.allDevelopmentsHash()

def collaborations = dao.ofy().query(Collaboration.class).list()
def relationships = dao.ofy().query(Relationship.class).list()

def baseURL = "${request.scheme}://${headers.Host}"
def writer = new StringWriter()

response.contentType = 'text/xml'
def xml = new MarkupBuilder(writer)
xml.mkp.xmlDeclaration(version:'1.0')
xml.setOmitEmptyAttributes(true)
xml.setOmitNullAttributes(true)

def lastUpdated = (devs.size() == 0) ? params.lastUpdated : devs.sort{ it.updated }.last().updated.format( 'yyyyMMddHHmmssS')

xml.developments("count":devs.size(), "api-version":"1", "updated":lastUpdated, "hash":devsHash) {
	
	devs.each { d ->
		
		def developmentKey = new Key(Development.class, d.id as Long)
		
		development(id:"${d.id}", uri:"${baseURL}/development/${d.id}") {

			title(d.title)
						
			def rels = relationships.findAll { it.from == developmentKey }
			if (rels){
				connections("count":rels.size()) {
					rels.each { c ->
						def ref = (c.to) ? c.to.id?:'' : ''
						connection(id:c.id,type:c.type.title, to:ref, url:(c.to)?"${baseURL}/development/${ref}": c.toUrl, c.description)					
					}
				}			
			}
			
			def reverseRels = relationships.findAll { it.to == developmentKey }	
			if (reverseRels){
				reverseConnections("count":reverseRels.size()) {
					reverseRels.each { c ->
						def ref = (c.from) ? c.from.id?:'' : ''
						
						def reverseDev = devs.find { it.id.toString() == ref.toString() }
						if (reverseDev){
							reverseConnection(id:c.id, type:c.type.reverseTitle, from:reverseDev.id, url:"${baseURL}/development/${reverseDev.id}", reverseDev.title)
						}
					}
				}
			}

			description(d.description)
			
			image(url: d.imageURL, thumbnail:d.thumbnailServingUrl)
		}
	}
}

sout << writer.toString()
