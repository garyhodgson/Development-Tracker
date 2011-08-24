package api.v1

import static development.developmentHelper.*

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.*
import groovy.xml.MarkupBuilder

def devs = dao.ofy().query(Development.class).list()
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
				d.categories?.each { category(it.title) }
			}
			
			def collaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
			collaborators("count":collaborations.size()) {
				collaborations.each { c ->
					collaborator(role:c.role, c.name)
				}
			}
			def relationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()
			connections("count":relationships.size()) {
				relationships.each { c ->
					connection(type:c.type, url:(c.to)?"${baseURL}/development/${c.to}": c.toUrl, c.description)					
				}
			}			
			description(d.description)
			if (d.developmentType) {
				if (d.developmentType == DevelopmentType.Other){
					developmentType(d.developmentTypeOther)
				} else {
					developmentType(d.developmentType.title)
				}
			}
			goals {
				d.goals?.each {
					if (it == Goal.Other){
						goal(d.goalsOther)
					} else {
						goal(it.title)
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
			projectVendors{
				d.projectVendor?.each {
					if (it == ProjectVendor.Other){
						projectVendor(d.projectVendorOther)
					} else {
						projectVendor(it.title)
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
			if (d.status){
				if (d.status == Status.Other){
					status(d.statusOther)
				} else {
					status(d.status.title)
				}
			}
			tags {
				d.tags?.each { tag(it)  }
			}
		}
	}
}

sout << writer.toString()
