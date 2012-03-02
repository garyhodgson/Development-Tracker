package api.v1

import static development.developmentHelper.*
import static enums.MemcacheKeys.*

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.*
import groovy.xml.MarkupBuilder

def devs = cacheManager.allDevelopments()
def collaborations = dao.ofy().query(Collaboration.class).list()
def relationships = dao.ofy().query(Relationship.class).list()

def baseURL = "${request.scheme}://${headers.Host}"
def writer = new StringWriter()

response.contentType = 'text/xml'
def xml = new MarkupBuilder(writer)
xml.mkp.xmlDeclaration(version:'1.0')
xml.setOmitEmptyAttributes(true)
xml.setOmitNullAttributes(true)

xml.developments("count":devs.size(), "api-version":"1") {
	
	devs.each { d ->
		
		def developmentKey = new Key(Development.class, d.id)
		
		development(id:"${d.id}", uri:"${baseURL}/development/${d.id}") {

			title(d.title)
			created(d.created)
			createdBy(d.createdBy)
			updated(d.updated)
			categories {
				d.categories?.each { category(id:it, it.title) }
			}
			
			def collabs = collaborations.findAll { it.development == developmentKey }
			if (collabs){
			collaborators("count":collabs.size()) {
				collabs.each { c ->
					collaborator(role:c.role, c.name)
				}
			}
			}
			def rels = relationships.findAll { it.from == developmentKey }
			if (rels){
			connections("count":rels.size()) {
				rels.each { c ->
					connection(id:c.to?c.to.getName():'', type:c.type, url:(c.to)?"${baseURL}/development/${c.to.name}": c.toUrl, c.description)					
				}
			}			
			}
			
			description(d.description)
			if (d.developmentType) {
				if (d.developmentType == DevelopmentType.Other){
					developmentType(id:d.developmentType, d.developmentTypeOther)
				} else {
					developmentType(id:d.developmentType, d.developmentType.title)
				}
			}
			goals {
				d.goals?.each {
					if (it == Goal.Other){
						goal(id:it, d.goalsOther)
					} else {
						goal(id:it, it.title)
					}
				}
			}
			goalsDescription(d.goalsDescription)
			if (d.license){
				if (d.license == License.Other){
					license(id:d.license, d.licenseOther)
				} else {
					license(id:d.license, d.license.description?:d.license.title)
				}
			}
			image(url: d.imageURL, thumbnail:d.thumbnailServingUrl)
			notice(d.notice?:'')
			image(url: d.imageURL, thumbnail:d.thumbnailServingUrl)
			projectVendors{
				d.projectVendor?.each {
					if (it == ProjectVendor.Other){
						projectVendor(id:it, d.projectVendorOther)
					} else {
						projectVendor(id:it, it.title)
					}
				}
			}			
			source(url:d.sourceURL, d.source)
			specifications(unit:d.specificationUnit?.title) {
				d.specificationName?.eachWithIndex { n,i ->
						specification {
							name(n)
							value(d.specificationValue[i]?:'')
						}
				}
			}
			signs {
				d.signs?.each { 
					sign(it.title)
				}
			}
			if (d.status){
				if (d.status == Status.Other){
					status(d.statusOther)
				} else {
					status(id:d.status, d.status.title)
				}
			}
			tags {
				d.tags?.each { tag(it)  }
			}
		}
	}
}

sout << writer.toString()
