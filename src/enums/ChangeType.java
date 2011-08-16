package enums;

public enum ChangeType {

	C("Create", "+"), U("Update", "~"), D("Delete", "-");

	protected String title = null;
	protected String symbol = null;

	ChangeType(String title, String symbol) {
		this.title = title;
		this.symbol = symbol;
	}

	ChangeType() {
		this.title = name();
	}
}