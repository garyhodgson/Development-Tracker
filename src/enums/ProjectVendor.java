package enums;

enum ProjectVendor {
	
	Any("Any","Suitable for any project or vendor.", null),
	RepRap("RepRap","Creator of Darwin, Mendel and Huxley printers, plus many variations.","http://reprap.org"),
	MakerBot("MakerBot","Creator of the Thing-O-Matic.","http://makerbot.com"),
	Ultimaker("Ultimaker","Maker of the Ultimaker.","http://ultimaker.com"),
	Pp3dp("PP3DP", "Maker of the UP! printer.", "http://pp3dp.com/"),
	Other("Other", "", "");
		
	protected String title = null;
	protected String description = null;
	protected String url = null;
	
	ProjectVendor(String title) {
		this.title = title;
	}
	
	ProjectVendor(String title, String description, String url) {
		this.title = title;
		this.description = description;
		this.url = url;
	}

	ProjectVendor() {
	}

	public String toString() {
		return (title != null) ? title : this.name();
	}
}