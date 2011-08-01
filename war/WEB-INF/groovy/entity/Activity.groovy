package entity
import javax.persistence.Id

import enums.ActivityType

class Activity implements Serializable {

	@Id
	Long id
	ActivityType type
	String title
	String by
	Date created
	String link
	
}
