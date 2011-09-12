package entity

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.PrePersist;

import com.googlecode.objectify.Key;


class Theme implements Serializable {

	@Id
	Long id
	
	def title
	def description
	Date created
	Date updated
	String createdBy
	def developmentIds
	
	def Theme(){
		created = new Date()
	}

	@PrePersist
	def prePersist() {
		updated = new Date()
	}
	
}
