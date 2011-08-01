package enums;

enum DevelopmentType {

	Part, Printer, Document, Technique, Tool, Other;

	protected String title = null;

	DevelopmentType(String title) {
		this.title = title;
	}

	DevelopmentType() {
	}

	public String toString() {
		return (title != null) ? title : this.name();
	}
}