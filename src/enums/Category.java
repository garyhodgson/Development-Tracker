package enums;


enum Category {

	Concept,
	Documentation,
	Electronics,
	Extruder,
	Firmware,
	GCode,
	Host,
	HotEnd("Hot-End"),
	Modelling,
	Printer,
	Process,
	Toolchain;



	protected String title = null;

	Category(String title) {
		this.title = title;
	}

	Category() {
	}

	public String toString() {
		return (title != null) ? title : this.name();
	}
}