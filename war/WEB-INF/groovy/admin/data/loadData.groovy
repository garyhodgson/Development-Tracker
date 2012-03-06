package admin.data

import static development.developmentHelper.*
import development.ThumbnailHelper
import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.Role

/*
//def txt = new File('data/testdata.xml').getText()
def txt = new File('data/reprap.development-tracker.info-developments.xml').getText()

def developments = new XmlSlurper().parseText(txt)

def developmentList = developments.development

developmentList.each {
	def id = it.@id.text() as Long
	def d = new Development('id':id, title:it.title.text(),createdBy:it.createdBy.text(), description:it.description.text(), imageURL:it.image.@url.text())
	
	if (it.status.@id.text()){
		d.status = enums.Status.valueOf(it.status.@id.text())
	}
	
	def developmentKey = dao.ofy().put(d)
	def author = it.'**'.grep{ it.@role == 'Author' }[0]?.text()
	if (author){
		def c = new Collaboration(development:developmentKey, name:author, role:Role.Author)
		dao.ofy().put(c)
	}
	
	try {
		(new ThumbnailHelper()).generateThumbnail(null, [imageURL:it.image.@thumbnail.text()], d)
	} catch (Exception s){
	}

}
cacheManager.resetDevelopmentCache()
*/