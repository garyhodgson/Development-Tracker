package entity

import javax.persistence.Id

import com.googlecode.objectify.Key

import enums.Role
import groovy.transform.EqualsAndHashCode;

@EqualsAndHashCode
class Collaboration implements Serializable {

	@Id
	Long id
	Key<UserInfo> userInfo;
	Key<Development> development;
	String name
	Role role
	String otherRole
	boolean mayEdit
	boolean isUsername
}
