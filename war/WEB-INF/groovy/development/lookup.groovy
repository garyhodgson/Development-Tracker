package development

import org.cyberneko.html.parsers.SAXParser
import static enums.MemcacheKeys.*
import entity.Development
import static enums.License.*

if (!params.url){
	log.warning "url parameter missing from Development lookup"
	return
}
if (params.url.contains("thingiverse.com")){
	sout << thingiverseLookup(params.url)
}

return


def thingiverseLookup(def url){
	
	def licenseMap = [
		"Attribution - Creative Commons":CCBY,
		"Creative Commons - GNU GPL":GPLv2,
		"Attribution - Share Alike - Creative Commons":CCBYSA,
		"Attribution - Non-Commercial - Share Alike":CCBYNCSA,
		"Public Domain":PD,
		"Attribution - No Derivatives - Creative Commons":CCBYND,
		"Attribution - Non-Commercial - Creative Commons":CCBYNC,
		"Attribution - Non-Commercial - No Derivatives":CCBYNCND,
		"Creative Commons - LGPL":LGPLv3,
		"BSD License":BSD2
	]

	try {
		def developments = cacheManager.allDevelopments()
		def parser=new SAXParser()
		parser.setFeature("http://cyberneko.org/html/features/balance-tags/document-fragment",true)
		parser.setFeature("http://xml.org/sax/features/namespaces",false)
		parser.setProperty('http://cyberneko.org/html/properties/default-encoding', 'UTF-8')
		parser.setProperty('http://cyberneko.org/html/properties/names/elems','lower')
		
		def htmlPage = new XmlSlurper(parser).parse(params.url)
		
		def nodes = htmlPage.depthFirst().collect{ it }
		
		def thingMeta = nodes.find{ it.@id.text() == 'thing-meta'}
		def t = thingMeta.h1.text()
		def a = nodes.find{ it.@id.text() == 'thing-creator'}.div.a.text()
		def d = nodes.find{ it.@id.text() == 'thing-description'}.text().trim()
		def licenseString = nodes.find{ it.@id.text() == 'thing-license'}.a.img.@alt.text().trim()
		def l = licenseMap[licenseString]?:Unknown
		def s = "Working"
		def stat = nodes.find{ it.@class.text() == 'BaseStatus'}
		if (stat && stat.text().contains("This thing is a Work in Progress")){
			s = "WorkInProgress"
		}
		def i = nodes.find{ it.@id.text() == 'thing-gallery-main'}.a.img.@src.text()
	
		def ders = []
	
		thingMeta.'**'.each {
			if (it.toString().trim().startsWith("Derived from")){
				def title = it.toString().trim().replace("Derived from ","")
				title = title.substring(0, title.lastIndexOf(" by"))
				def link = it.a[0]?.@href.text()
				
				def development = developments.find { it.sourceURL == link || it.title == title }
				if (development){
					link = development?.id
				}
	
				ders += ['title':title, 'link':link]
			}
		}
		
		def builder = new groovy.json.JsonBuilder()
		
		def root = builder.development {
				title t
				author a
				license l
				description d
				status s
				derivatives ders
				image i
			}
		
		return builder.toString()
		
	} catch (Exception e){
		log.severe e.getMessage()
	}
}