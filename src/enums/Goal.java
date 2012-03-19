package enums;

public enum Goal {

	Accuracy("Accuracy", "Improved precision."),
	Economy("Economy", "Reducing costs."),
	Education("Education", "To help and support."),
	Fun("Fun", "For the fun of it."),
	Improvement("Improvement", "A stepwise update to a previous design."), 
	Maintainability("Maintainability", "Easier to keep running."), 
	Minimilism("Minimilism","Shrinking parts or part count."),
	Necessity("Necessity", "Simply needed."),
	Quality("Quality", "Improving Quality."),
	Reliability("Reliability", "Producing consistent, repeatable results."),
	Reprapability("Reprapability", "Leading to self-replication."),
	Reproducability("Reproducability", "Easier to reproduce."),
	Simplification("Simplification", "Reducing complexity."),
	Size("Size", "Smaller, or bigger."),
	Speed("Speed", "Doing it faster."),
	Standardisation("Standardisation", "Bringing designs together."),
	Weight("Weight", "Reducing weight."),
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