package entity

import javax.persistence.Embedded
import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Parent

class ChangeHistory implements Serializable {

	@Id
	Long id
	@Parent
	Key parent
	String by
	Date on
	
	@Embedded
	List<Change> changes = new ArrayList<Change>()
	
	@Override
	public String toString() {
		"${by} ${on} ${changes}"
	}
}
