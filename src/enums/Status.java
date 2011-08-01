package enums;

enum Status {

	Working, Concept, Experimental, WorkInProgress("Work In Progress"), Abandoned, Other;

	protected String title = null;

	Status(String title) {
		this.title = title;
	}

	Status() {
	}

	public String toString() {
		return (title != null) ? title : this.name();
	}
}