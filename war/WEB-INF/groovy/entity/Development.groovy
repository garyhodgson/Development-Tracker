package entity
import javax.persistence.Id

import com.google.appengine.api.datastore.Text
import com.googlecode.objectify.annotation.Unindexed

import enums.License
import enums.SpecificationUnit

class Development implements Serializable {

	@Id
	Long id
	String title
	Text description
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
	String status
	String statusOther
	String developmentType
	String developmentTypeOther
	List<String> categories
	List<String> goals
	String goalsOther
	Text goalsDescription
	List<String> tags
	List<String> projectVendor
	String projectVendorOther
	List<String> specificationName
	List<String> specificationValue
	SpecificationUnit specificationUnit
	License license
	String licenseOther
}
