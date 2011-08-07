package enums;


public enum RelationshipType {

	DerivedFrom("Derived From"),
	RelatedTo("Related To"),
	PartOf("Part Of"),
	ConsistsOf("Consists Of"),
	InspiredBy("Inspired By"),
	EvolvedFrom("Evolved From"),
	Link("Link"),
	Other("Other");
	
	protected String title = null;
	protected String description = null;
	
	RelationshipType(String title) {
		this.title = title;
	}
	
	RelationshipType(String title, String description) {
		this.title = title;
		this.description = description;
	}

	RelationshipType() {
		this.title = name();
	}
	
}