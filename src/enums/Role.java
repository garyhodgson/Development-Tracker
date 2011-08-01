package enums;

enum Role {
	
	Author,
	Contributer,
	Other;
	
	protected String title = null;
	
	Role(String title){
		this.title = title;
	}
	
	Role(){
	}

	public String toString(){
		return (title != null) ? title : this.name();
	}
}