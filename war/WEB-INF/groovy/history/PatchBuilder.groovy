package history

import static development.developmentHelper.*

public class PatchBuilder {
	def StringBuilder oldDevelopmentSB = new StringBuilder()
	def StringBuilder newDevelopmentSB = new StringBuilder()

	def StringBuilder oldRelationshipSB = new StringBuilder()
	def StringBuilder newRelationshipSB = new StringBuilder()
	
	def StringBuilder oldCollaborationSB = new StringBuilder()
	def StringBuilder newCollaborationSB = new StringBuilder()

	def addOldDevelopment(def d){
		oldDevelopmentSB.append(d.toString())
		oldDevelopmentSB.append("\n")
	}
	def addNewDevelopment(def d){
		newDevelopmentSB.append(d.toString())
		newDevelopmentSB.append("\n")
	}
	def addOldRelationship(def r){
		oldRelationshipSB.append(r.toString())
		oldRelationshipSB.append("\n")
	}
	def addNewRelationship(def r){
		newRelationshipSB.append(r.toString())
		newRelationshipSB.append("\n")
	}
	def addOldCollaboration(def c){
		oldCollaborationSB.append(c.toString())
		oldCollaborationSB.append("\n")
	}
	def addNewCollaboration(def c){
		newCollaborationSB.append(c.toString())
		newCollaborationSB.append("\n")
	}

	def build(){
		def StringBuilder oldSB = new StringBuilder()
		def StringBuilder newSB = new StringBuilder()
	
		oldSB.append(oldDevelopmentSB.toString())
		
		if (oldRelationshipSB.length() > 0){
			oldSB.append("Connections:\n")
			oldSB.append(oldRelationshipSB.toString())
			oldSB.append("\n")
		}
		if (oldCollaborationSB.length() > 0){
			oldSB.append("Collaborators:\n")
			oldSB.append(oldCollaborationSB.toString())
			oldSB.append("\n")
		}

		newSB.append(newDevelopmentSB.toString())
		if (newRelationshipSB.length() > 0){
			newSB.append("Connections:\n")
			newSB.append(newRelationshipSB.toString())
			newSB.append("\n")
		}
		if (newCollaborationSB.length() > 0){
			newSB.append("Collaborators:\n")
			newSB.append(newCollaborationSB.toString())
			newSB.append("\n")
		}
		
		createDiffLogPatch(oldSB.toString(), newSB.toString())
	}
}