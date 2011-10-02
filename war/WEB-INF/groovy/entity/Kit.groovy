package entity

import java.util.Date

import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Transient

import com.googlecode.objectify.annotation.Unindexed

import enums.*

class Kit implements Serializable {

	@Id
	Long id

	String ownerUsername;

	String title
	String description
	Date created
	Date updated

	@Unindexed
	String imageURL

	@Unindexed
	String thumbnailServingUrl
	String thumbnailPath

	def parts

	@Transient
	String imageBlobKey

	def Kit(){
		created = new Date()
	}

	@PrePersist
	def prePersist() {
		updated = new Date()
	}
}