package entity

import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Parent
import com.googlecode.objectify.annotation.Unindexed

class DiffLog implements Serializable {

	@Id
	Long id
	@Parent
	Key parent
	String by
	Date on
	@Unindexed
	String patch
	
	@Override
	String toString() {
		"id:${id}; parent:${parent}; by:${by}; on:${on}; patch:${patch}"
	}
}
