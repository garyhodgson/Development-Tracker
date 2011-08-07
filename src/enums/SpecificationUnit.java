package enums;

public enum SpecificationUnit {

	NA("Not Applicable"),
	M("Metric"),
	I("Imperial");
	
	protected String title = null;
	
	SpecificationUnit(String title){
		this.title = title;
	}
	
	SpecificationUnit(){
		this.title = name();
	}
}