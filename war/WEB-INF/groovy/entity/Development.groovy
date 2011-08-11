package entity

import javax.persistence.Id
import javax.persistence.PrePersist

import com.googlecode.objectify.annotation.Unindexed

import enums.*

class Development implements Serializable {

	@Id
	Long id
	String title
	String description
	Date created
	Date updated
	@Unindexed
	String imageURL
	String subdomain
	String createdBy
	@Unindexed
	String thumbnailServingUrl
	@Unindexed
	String thumbnailPath
	String source
	String sourceURL
	Status status
	String statusOther
	DevelopmentType developmentType
	String developmentTypeOther
	List<Category> categories
	List<Goal> goals
	String goalsOther
	String goalsDescription
	List<String> tags
	List<ProjectVendor> projectVendor
	String projectVendorOther
	List<String> specificationName
	List<String> specificationValue
	SpecificationUnit specificationUnit
	License license
	String licenseOther

	def Development(){
		created = new Date()
	}

	@PrePersist
	def prePersist() {
		updated = new Date()
	}

	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		this.properties.sort().each {
			if (![
				'metaClass',
				'subdomain',
				'class',
				'thumbnailPath',
				'thumbnailServingUrl',
				'imageURL'
			].contains(it.key) && it.value){
				sb.append("${it.key.capitalize()}: ${it.value}\n")
			}
		}
		return sb.toString()
	}
}
