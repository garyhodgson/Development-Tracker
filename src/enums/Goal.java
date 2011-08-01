package enums;

enum Goal {

	Economise(null, "Reducing costs."),
	Simplify(null, "Reducing complexity."),
	Amuse(null, "For the fun of it."),
	Educate(null, "To help and support."),
	Standardise(null, "Bringing designs together."),
	Minimise(null,"Shrinking parts."),
	Other(null, "Define your own goal.");
	
	protected String title = null;
	protected String description = null;
	
	Goal(String title) {
		this.title = title;
	}
	
	Goal(String title, String description) {
		this.title = title;
		this.description = description;
	}

	Goal() {
	}

	public String toString() {
		return (title != null) ? title : this.name();
	}
	
}