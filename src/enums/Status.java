package enums;

public enum Status {

	Working, Concept, Experimental, WorkInProgress("Work In Progress"), Abandoned, Obsolete, Other;

	protected String title = null;

	Status(String title) {
		this.title = title;
	}

	Status() {
		this.title = name();
	}
}