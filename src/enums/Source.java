package enums;


public enum Source {

	None,
	RepRapWiki,
	Thingiverse,
	Github,
	Blog,
	Other;

	protected String title = null;

	Source(String title) {
		this.title = title;
	}

	Source() {
		this.title = name();
	}
}