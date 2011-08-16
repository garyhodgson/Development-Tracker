package history

import entity.Change
import enums.ChangeType

class ChangeHelper {
	
	
	def getListChanges(def originalList, def newList, def label){
		def newId = 1
		def originalMap = originalList.collectEntries{[it.id?"${label}-${it.id}":"New-${label}-${newId++}", it.toString()]}
		def newMap = newList.collectEntries{[it.id?"${label}-${it.id}":"New-${label}-${newId++}", it.toString()]}
		return getChangesFromMap(originalMap, newMap)
	}
	
	def getDevelopmentChanges(def originalDevelopmentProperties, def newDevelopmentProperties){
	
		def changes = []
		def propertyExceptionsList = ['metaClass','thumbnailPath','class','thumbnailServingUrl','subdomain','id','specificationName','specificationValue']
		
		if (originalDevelopmentProperties.specificationName instanceof String){
			originalDevelopmentProperties.specificationName = [
				originalDevelopmentProperties.specificationName
			]
		}
		if (originalDevelopmentProperties.specificationValue instanceof String){
			originalDevelopmentProperties.specificationValue = [
				originalDevelopmentProperties.specificationValue
			]
		}
		if (newDevelopmentProperties.specificationName instanceof String){
			newDevelopmentProperties.specificationName = [
				newDevelopmentProperties.specificationName
			]
		}
		if (newDevelopmentProperties.specificationValue instanceof String){
			newDevelopmentProperties.specificationValue = [
				newDevelopmentProperties.specificationValue
			]
		}
	
		def originalSpecificationMap = [
			originalDevelopmentProperties.specificationName?:[],
			originalDevelopmentProperties.specificationValue?:[]
		].transpose().inject([:]) { a, b -> a[b[0]] = b[1]; a }
		def newSpecificationMap = [
			newDevelopmentProperties.specificationName?:[],
			newDevelopmentProperties.specificationValue?:[]
		].transpose().inject([:]) { a, b -> a[b[0]] = b[1]; a }
	
		def specChanges = getChangesFromMap(originalSpecificationMap, newSpecificationMap)
	
		//clean properties
		originalDevelopmentProperties = originalDevelopmentProperties.findAll {
			!propertyExceptionsList.contains(it.key) && it.value
		}
		newDevelopmentProperties = newDevelopmentProperties.findAll {
			!propertyExceptionsList.contains(it.key) && it.value
		}

		changes += getChangesFromMap(originalDevelopmentProperties, newDevelopmentProperties)
		changes += specChanges
	
		return changes
	}
	
	def getChangesFromMap(def oldMap, def newMap){
		def changes = []
		def adds = newMap.minus(oldMap)
		def dels = oldMap.minus(newMap)
	
		adds.each {
			if (!oldMap.keySet().contains(it.key) || !oldMap.get(it.key)){
				changes += new Change(name:it.key, value:it.value, type: ChangeType.C)
			} else if (it.value) {
				changes += new Change(name:it.key, value:it.value, type: ChangeType.U)
			}
		}
	
		dels.each {
			if (!newMap.keySet().contains(it.key) || !newMap.get(it.key)){
				changes += new Change(name:it.key, value:it.value, type: ChangeType.D)
			}
	
		}
		return changes
	}
}
