package entity

import enums.ChangeType

class Change implements Serializable {

	ChangeType type
	String name
	String value
	
	@Override
	public String toString() {
		"${type.symbol} ${name} ${value?:''}"
	}
}
