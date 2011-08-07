package enums;


public enum Category {

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
		this.title = name();
	}
}