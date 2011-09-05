package enums;


public enum RelationshipType {

	DerivedFrom("Derived From", "Derived"),
	RelatedTo("Related To", "Related To"),
	DesignedFor("Designed For", "Has Design"),
	PartOf("Part Of", "Consists Of"),
	ConsistsOf("Consists Of", "Part Of"),
	InspiredBy("Inspired By", "Inspired"),
	EvolvedFrom("Evolved From", "Evolved Into"),
	InAnswerTo("In Answer To", "Is Answered By"),
	Link("Link", "Link"),
	Other("Other", "Other");
	
	protected String title = null;
	protected String reverseTitle = null;
	
	RelationshipType(String title) {
		this.title = title;
	}
	
	RelationshipType(String title, String reverseTitle) {
		this.title = title;
		this.reverseTitle = reverseTitle;
	}

	RelationshipType() {
		this.title = name();
	}
	
}