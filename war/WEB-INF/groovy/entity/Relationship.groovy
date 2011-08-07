package entity
import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Parent;

import enums.RelationshipType
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Relationship implements Serializable {

	@Id
	Long id
	
	@Parent
	Key<Development> from

	String description
	RelationshipType type
	Key<Development> to
	String toUrl
	
	@Override
	String toString() {
		return "${type.title} ${description} (${to?to.name:toUrl})"
	}
}
