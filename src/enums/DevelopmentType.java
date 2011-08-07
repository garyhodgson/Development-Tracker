package enums;

public enum DevelopmentType {

	Part, Printer, Document, Technique, Tool, Other;

	protected String title = null;

	DevelopmentType(String title) {
		this.title = title;
	}

	DevelopmentType() {
		this.title = name();
	}
}