package enums;

public enum Role {
	
	Author,
	Contributer,
	Other;
	
	protected String title = null;
	
	Role(String title){
		this.title = title;
	}
	
	Role(){
		this.title = name();
	}
}