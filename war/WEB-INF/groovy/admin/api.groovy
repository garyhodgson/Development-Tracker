package admin

import static development.developmentHelper.*
import entity.Development
import enums.*
import groovy.xml.MarkupBuilder


def devs = dao.ofy().query(Development.class).list()

def writer = new StringWriter()

response.contentType = 'text/xml'
def xml = new MarkupBuilder(writer)
xml.mkp.xmlDeclaration(version:'1.0')
xml.setOmitEmptyAttributes(true)
xml.setOmitNullAttributes(true)
xml.developments("count":devs.size()) {
	devs.each { d ->

		development(uri:"${request.scheme}://${headers.Host}/development/${d.id}") {

			title(d.title)
			created(d.created)
			createdBy(d.createdBy)
			updated(d.updated)
			categories {
				d.categories?.each { category(it.title) }
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
			id(d.id)
			if (d.license){
				if (d.license == License.Other){
					license(d.licenseOther)
				} else {
					license(d.license.description?:d.license.title)
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
			source(d.source)
			sourceURL(d.sourceURL)
			specificationUnit(d.specificationUnit?.title)
			specifications {
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
