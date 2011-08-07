package entity

import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Parent

import enums.Role
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Collaboration implements Serializable {

	@Id
	Long id
	
	@Parent
	Key<Development> development;

	Key<UserInfo> userInfo;
	String name
	Role role
	String otherRole
	boolean mayEdit
	boolean isUsername
	
	@Override
	String toString() {
		return "${(role==Role.Other)?otherRole:role.title}: ${name}"
	}
}
