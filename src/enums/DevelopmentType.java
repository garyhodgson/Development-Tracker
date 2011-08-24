package enums;

public enum DevelopmentType {

	Part, Printer, Document, Technique, Tool, Modification,  Other;

	protected String title;

	DevelopmentType(String title) {
		this.title = title;
	}

	DevelopmentType() {
		this.title = name();
	}
}