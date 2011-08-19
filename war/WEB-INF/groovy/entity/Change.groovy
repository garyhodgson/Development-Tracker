package entity

import com.google.appengine.api.datastore.Text
import com.googlecode.objectify.annotation.AlsoLoad

import enums.ChangeType

class Change implements Serializable {

	ChangeType type
	String name
	Text value

	public Change(){
	}

	public Change(def name, def value, def type) {
		this.type = type
		this.name = name
		this.value = new Text(value)
	}

	void importValue(@AlsoLoad("value") String value) {
		this.value = new Text(value)
	}

	@Override
	public String toString() {
		"${type.symbol} ${name} ${value?:''}"
	}
}
