package api.v1


import static enums.MemcacheKeys.*

import com.googlecode.objectify.Key

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.*
import groovy.xml.MarkupBuilder

import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.Source

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

if (!params.id.isLong()){
	request.session.message = "Invalid Id given: ${params.id}"
	redirect '/developments'
	return
}

def developmentKey = new Key(Development.class, params.id as Long)

def d = dao.ofy().get(developmentKey)
def relationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()


def reverseRelationshipDevelopments = []
def reverseRelationships = dao.ofy().query(Relationship.class).filter("to", developmentKey).list()
if (reverseRelationships){
	def reverseDevs = dao.ofy().get( reverseRelationships.collect { it.from } ).values()
	reverseDevs.eachWithIndex { dev, i ->
		def rel = reverseRelationships.getAt(i)
		log.info rel.toString()
		reverseRelationshipDevelopments << [type:rel.type, development:dev]
	}
}

def collaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()

def baseURL = "${request.scheme}://${headers.Host}"
def writer = new StringWriter()

response.contentType = 'text/xml'
def xml = new MarkupBuilder(writer)
xml.mkp.xmlDeclaration(version:'1.0')
xml.setOmitEmptyAttributes(true)
xml.setOmitNullAttributes(true)

xml.development(id:"${d.id}", uri:"${baseURL}/development/${d.id}", "api-version":"1") {

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
				def ref = (c.to) ? c.to.id?:'' : ''
				connection(type:c.type.title, to:ref, url:(c.to)?"${baseURL}/development/${ref}": c.toUrl, c.description)
			}
		}
	}
	
	if (reverseRelationshipDevelopments){
		reverseConnections("count":reverseRelationshipDevelopments.size()){
			reverseRelationshipDevelopments.each { c ->
				reverseConnection(type:c.type.reverseTitle, from:c.development.id, url:"${baseURL}/development/${c.development.id}", c.development.title)
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


sout << writer.toString()

