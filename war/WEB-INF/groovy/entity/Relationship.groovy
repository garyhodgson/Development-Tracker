package entity
import javax.persistence.Id

import com.googlecode.objectify.Key

import enums.RelationshipType
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Relationship implements Serializable {

	@Id
	Long id
	String description
	RelationshipType type
	Key<Development> from
	Key<Development> to
	String toUrl
}
