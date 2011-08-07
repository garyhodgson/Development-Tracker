package enums;

public enum ActivityType {
	NewDevelopment("New Development"),
	DevelopmentUpdated("Development Updated"),
	NewUser("New User"),
	StartedWatching("Started Watching");	
	
	protected String title = null;
	
	ActivityType(String title){
		this.title = title;
	}
	
	ActivityType(){
		this.title = name();
	}

}
