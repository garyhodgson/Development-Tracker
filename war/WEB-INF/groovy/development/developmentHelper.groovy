package development

import java.text.SimpleDateFormat
import java.util.List

import com.google.appengine.api.NamespaceManager
import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.files.FileServiceFactory
import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService

import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.UserInfo
import enums.*
import exceptions.ValidationException


public static void processRelationships(def relationships, def params, def fromDevelopmentKey){

	if(!params.relationshipType) return;

	if(!params.relationshipURL && !params.relationshipDescription) return;

	def relationshipType = (params.relationshipType instanceof String)? [params.relationshipType]: params.relationshipType

	relationshipType.eachWithIndex { type, i ->
		def r = new Relationship()

		if (params.relationshipTo){
			def to = (params.relationshipTo instanceof String) ? params.relationshipTo : params.relationshipTo[i]
			if (to){
				if (to.isLong()){
					r.to = new Key(Development.class, to as Long)
				} else {
					r.toUrl = to
				}
			}
		}

		if (params.relationshipDescription){
			def description = (params.relationshipDescription instanceof String) ? params.relationshipDescription : params.relationshipDescription[i]
			r.description = description
		}

		if (params.relationshipId){
			def relationshipId = (params.relationshipId instanceof String) ? params.relationshipId : params.relationshipId[i]
			if (!relationshipId.isEmpty() && relationshipId.isLong()){
				r.id = relationshipId as Long
			}
		}

		r.type = enums.RelationshipType.valueOf(type)
		r.from = fromDevelopmentKey

		relationships << r
	}
}


public static void processCollaborations(def collaborations, def params, def fromDevelopmentKey){

	if(!params.collaboratorRole || !params.collaboratorName) return;

	def collaboratorRole = (params.collaboratorRole instanceof String)? [params.collaboratorRole]: params.collaboratorRole

	collaboratorRole.eachWithIndex { role, i ->

		def collaboration = new Collaboration()

		def collaboratorName = (params.collaboratorName instanceof String) ? params.collaboratorName : params.collaboratorName[i]
		collaboration.name = collaboratorName

		if (params.collaboratorIsUsername){
			def list = []
			list.addAll(params.collaboratorIsUsername)
			collaboration.isUsername = list.contains(i.toString())
			if (collaboration.isUsername){
				def userinfoKey
				String oldNamespace = NamespaceManager.get();
				NamespaceManager.set("");
				try {
					userinfoKey = ObjectifyService.begin().query(UserInfo.class).filter('username', collaboratorName).getKey()
				} finally {
					NamespaceManager.set(oldNamespace);
				}
				collaboration.userInfo = userinfoKey
			}
		}

		collaboration.role = Role.valueOf(role)

		if ((collaboration.role == Role.Other) && params.collaboratorRoleOther){
			def collaboratorRoleOther = (params.collaboratorRoleOther instanceof String) ? params.collaboratorRoleOther : params.collaboratorRoleOther[i]
			collaboration.otherRole = collaboratorRoleOther
		}

		if (params.collaboratorMayEdit){
			def list = []
			list.addAll(params.collaboratorMayEdit)
			collaboration.mayEdit = list.contains(i.toString())
		}

		if (params.collaboratorId){
			def collaboratorId = (params.collaboratorId instanceof String) ? params.collaboratorId : params.collaboratorId[i]
			if (!collaboratorId.isEmpty() && collaboratorId.isLong()){
				collaboration.id = collaboratorId as Long
			}
		}

		collaboration.development = fromDevelopmentKey

		collaborations << collaboration
	}
}

public static void validateDevelopment(def development) throws ValidationException {
	if (!development.title) throw new ValidationException('Title is required.')
	if (development.specificationName && development.specificationName instanceof List && development.specificationName.size() > 200) throw new ValidationException('Specification is limited to 200 entries.')
}

public static void validateCollaborations(List collaborations) throws ValidationException {
	if (collaborations && collaborations.size() > 200) throw new ValidationException('Collaborators are limited to 200 entries.')
}

public static void validateRelationships(List relationships) throws ValidationException {
	if (relationships && relationships.size() > 500) throw new ValidationException('Relationships are limited to 500 entries.')
}

public static void processParameters(def development, def params){

	params.each { key, value ->

		if (value) {

			switch (key){
				case 'categories':
					def categories = []
					if (value instanceof String){
						categories << Category.valueOf(value)
					} else {
						value.each { categories << Category.valueOf(it) }
					}
					development.categories = categories
					break
				case 'signs':
					def signs = []
					if (value instanceof String){
						signs << Sign.valueOf(value)
					} else {
						value.each { signs << Sign.valueOf(it) }
					}
					development.signs = signs
					break
				case 'status':
					development.status = Status.valueOf(value)
					break
				case 'goals':
					def goals = []
					if (value instanceof String){
						goals << Goal.valueOf(value)
					} else {
						value.each { g -> goals << Goal.valueOf(g) }
					}
					development.goals = goals
					break
				case 'tags':
					def tags = []
					value.split(',').each{ tags << it.trim() }
					development.tags = tags
					break
				case 'projectVendor':
					def projectVendor = []
					if (value instanceof String){
						projectVendor << ProjectVendor.valueOf(value)
					} else {
						value.each { projectVendor << ProjectVendor.valueOf(it) }
					}
					development.projectVendor = projectVendor
					break
				case 'description':
				case 'goalsDescription':
					development[key] = value
					break;
				case 'imageURL':
					development.imageURL = params.imageURL
					break
				case 'specificationName':
					def specificationName = []
					if (value instanceof String){
						specificationName << value
					} else {
						value.each { specificationName << it }
					}
					development.specificationName = specificationName
					break
				case 'specificationValue':
					def specificationValue = []
					if (value instanceof String){
						specificationValue << value
					} else {
						value.each { specificationValue << it }
					}
					development.specificationValue = specificationValue
					break
				case 'specificationUnit':
					if (value != SpecificationUnit.NA.name()){
						development.specificationUnit = value
					}
					break
				case 'license':
					development.license = License.valueOf(value)
					break
				case 'licenseOther':
					development.licenseOther = value
					break
				case 'updated':
				case 'created':
					development[key] = (new SimpleDateFormat("E MMM dd kk:mm:ss z yyyy")).parse(value)
					break
				case 'relationshipType':
				case 'relationshipTo':
				case 'relationshipDescription':
				case 'relationshipId':
				case 'referer':
				case 'id':
				case 'collaboratorRole':
				case 'collaboratorRoleOther':
				case 'collaboratorName':
				case 'collaboratorIsUsername':
				case 'collaboratorMayEdit':
				case 'collaboratorId':
				case 'collaboratorRoleOther':
				//	Do nothing
					break
				default:
					development[key] = value
			}
		} else {
			// process deletions
			switch (key){
				case 'imageURL':					
					development.imageURL = null
					development.thumbnailServingUrl = null
					development.thumbnailPath = null
					break

				case 'relationshipType':
				case 'relationshipTo':
				case 'relationshipDescription':
				case 'relationshipId':
				case 'referer':
				case 'id':
				case 'collaboratorRole':
				case 'collaboratorRoleOther':
				case 'collaboratorName':
				case 'collaboratorIsUsername':
				case 'collaboratorMayEdit':
				case 'collaboratorId':
				case 'collaboratorRoleOther':
				// do nothing
					break
				default:
					if (development[key]) {
						development[key] = null
					}
			}
		}

		// process deletions where no key was passed

		[
			'categories',
			'goals',
			'tags',
			'signs',
			'projectVendor',
			'specificationName',
			'specificationValue'
		].each {
			if (!params[it] && development[it]){
				development[it] = null
			}
		}


	}
}
