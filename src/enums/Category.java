package enums;


public enum Category {

	Concept,
	Commercial,
	Documentation,
	Electronics,
	Extruder,
	Firmware,
	GCode,
	Host,
	HotEnd("Hot-End"),
	Modelling,
	Movement,
	Printer,
	Process,
	Software,
	Toolchain;

	protected String title = null;

	Category(String title) {
		this.title = title;
	}

	Category() {
		this.title = name();
	}
}