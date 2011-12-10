package entity
import javax.persistence.Id

import static enums.ActivityType.*
import enums.ActivityType

class Activity implements Serializable {

	@Id
	Long id
	ActivityType type
	String title
	String by
	Date created
	String link
	
	@Override
	public String toString() {
		
		switch (this.type) {
			case NewDevelopment:
				return "${by} created a new development: ${title}"
			case DevelopmentUpdated:
				return "${by} updated development: ${title}"
			case NewUser:
				return "New user: ${by}"
			case DevelopmentDeleted:
				return "${by} deleted development: ${title}"
			case NewKit:
				return "${by} add a new kit: ${title}"
			case KitUpdated:
				return "${by} updated kit: ${title}"
			case NewTheme:
				return "${by} created a new theme: ${title}"
			case ThemeUpdated:
				return "${by} updated theme: ${title}"
			default:
				return title
		}
	}
}
