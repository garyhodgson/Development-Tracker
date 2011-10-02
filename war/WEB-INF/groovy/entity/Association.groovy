package entity

import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.Parent

import enums.Source
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Association implements Serializable {

	Source source
	String sourceOther
	String sourceId

	def getURL(){
		switch (source){
			case Source.Github:
				return "https://github.com/${sourceId}"
				break
			case Source.Thingiverse:
				return "http://www.thingiverse.com/${sourceId}"
				break
			case Source.RepRapWiki:
				return "http://reprap.org/wiki/User:${sourceId}"
				break
			default :
				if (sourceId.startsWith("http")){
					return sourceId
				}
				return null
		}
	}
	
	@Override
	public String toString() {
		return "${source.title}: ${sourceId}"
	}
}