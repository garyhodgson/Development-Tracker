package enums;

public enum Goal {

	Economy("Economy", "Reducing costs."),
	Simplification("Simplification", "Reducing complexity."),
	Fun("Fun", "For the fun of it."),
	Education("Education", "To help and support."),
	Standardisation("Standardisation", "Bringing designs together."),
	Minimilism("Minimilism","Shrinking parts or part count."),
	Reliability("Reliability", "Producing consistent, repeatable results."),
	Speed("Speed", "Doing it faster."),
	Accuracy("Accuracy", "Improved precision."),
	Other("Other", "Define your own goal.");
	
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
		this.title = name();
	}
	
}