package enums;

enum SpecificationUnit {

	NA("Not Applicable"),
	M("Metric"),
	I("Imperial");
	
	protected String title = null;
	
	SpecificationUnit(String title){
		this.title = title;
	}
	
	SpecificationUnit(){
	}

	public String toString(){
		return (title != null) ? title : this.name();
	}
}