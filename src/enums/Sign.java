package enums;

public enum Sign {

	Biohazard("Biohazard", "biohazard.png"),
	Blindness("Blindness", "blindness.png"),
	Caution("Caution", "caution.png"),
	Cold("Cold", "cold.png"),
	Corrosive("Corrosive", "corrosive.png"),
	Cutting("Cutting", "cutting.png"),
	Explosion("Explosion", "explosion.png"),
	Experimental("Experimental", "experimental.png"),
	Fire("Fire", "fire.png"),
	Handtrap("Handtrap", "handtrap.png"),
	Irritant("Irritant", "irritant.png"),
	Laser("Laser", "laser.png"),
	Magnetism("Magnetism", "magnetism.png"),
	MovingParts("Moving Parts", "movingparts.png"),
	NonIonisingRadiation("Non-Ionising Radiation", "nonionisingradiation.png"),
	Radiation("Radiation", "radiation.png"),
	Shock("High Voltage", "shock.png"),
	Skull("Toxic", "skull.png"),
	Sound("Deafness", "sound.png"),
	WorkInProgress("Work In Progress", "workinprogress.png");
	
	protected String title = null;
	protected String image = null;

	Sign(String title) {
		this.title = image;
	}

	Sign(String title, String image) {
		this.title = title;
		this.image = image;
	}

	Sign() {
		this.title = name();
	}

}