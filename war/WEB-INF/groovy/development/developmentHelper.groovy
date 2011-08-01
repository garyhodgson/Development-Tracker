package development

import java.util.List;

import org.apache.commons.lang.StringEscapeUtils

import com.google.appengine.api.datastore.Text
import com.google.appengine.api.files.FileServiceFactory
import com.google.appengine.api.images.ImagesServiceFactory
import com.googlecode.objectify.Key
import com.googlecode.objectify.ObjectifyService

import entity.Collaboration
import entity.Development
import entity.Relationship
import entity.UserInfo
import enums.SpecificationUnit;
import exceptions.ValidationException


public static def generateThumbnail(def imageURL) {

	if (!imageURL) return null

	URL url = new URL(imageURL)
	def image
	
	try {
		def response = url.get()

		if (response.responseCode != 200) return null

		image = response.content.image
	} catch (SocketTimeoutException ste){
		System.err.println(ste.getLocalizedMessage())
	}
	def ratio = image.height / image.width

	def thumbnail = (image.width > 400)? image.resize(400,400*ratio as int) : image

	def filename = url.getFile().substring(url.getFile().lastIndexOf('/')+1)
	def file = FileServiceFactory.getFileService().createNewBlobFile("image/xyz", filename)

	file.withOutputStream { stream  ->
		stream  << thumbnail.getImageData()
	}

	return file
}

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
					r.to = new Key(Development.class, to)
				} else {
					r.toUrl = StringEscapeUtils.escapeHtml(to)
				}
			}
		}

		if (params.relationshipDescription){
			def description = (params.relationshipDescription instanceof String) ? params.relationshipDescription : params.relationshipDescription[i]
			r.description = StringEscapeUtils.escapeHtml(description)
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
		collaboration.name = StringEscapeUtils.escapeHtml(collaboratorName)

		if (params.collaboratorIsUsername){
			def list = []
			list.addAll(params.collaboratorIsUsername)
			collaboration.isUsername = list.contains(i.toString())
			if (collaboration.isUsername){
				collaboration.userInfo = ObjectifyService.begin().query(UserInfo.class).filter('username', collaboratorName).getKey()
			}
		}

		collaboration.role = enums.Role.valueOf(role)

		if ((collaboration.role == enums.Role.Other) && params.collaboratorRoleOther){
			def collaboratorRoleOther = (params.collaboratorRoleOther instanceof String) ? params.collaboratorRoleOther : params.collaboratorRoleOther[i]
			collaboration.otherRole = StringEscapeUtils.escapeHtml(collaboratorRoleOther)
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
						categories << StringEscapeUtils.escapeHtml(value)
					} else {
						value.each { categories << StringEscapeUtils.escapeHtml(it) }
					}
					development.categories = categories
					break

				case 'goals':
					def goals = []
					if (value instanceof String){
						goals << StringEscapeUtils.escapeHtml(value)
					} else {
						value.each { g -> goals << StringEscapeUtils.escapeHtml(g) }
					}
					development.goals = goals
					break
				case 'tags':
					def tags = []
					StringEscapeUtils.escapeHtml(value).split(',').each{ tags << it.trim() }
					development.tags = tags
					break
				case 'projectVendor':
					def projectVendor = []
					if (value instanceof String){
						projectVendor << StringEscapeUtils.escapeHtml(value)
					} else {
						value.each { projectVendor << it }
					}
					development.projectVendor = projectVendor
					break
				case 'description':
				case 'goalsDescription':
					development[key] = StringEscapeUtils.escapeHtml(value) as Text
					break;
				case 'imageURL':
					if (development.imageURL != value) {
						
						def thumbnailFile = generateThumbnail(value)
						if (thumbnailFile){
							
							// Delete existing thumbnail
							if (development.thumbnailPath){
								def file = FileServiceFactory.getFileService().fromPath(development.thumbnailPath)
								file.delete()
							}
						
							development.thumbnailPath = thumbnailFile.getFullPath()
							development.thumbnailServingUrl = ImagesServiceFactory.getImagesService().getServingUrl(thumbnailFile.blobKey)
						}
						development.imageURL = params.imageURL
					}
					break
				case 'specificationName':
					def specificationName = []
					if (value instanceof String){
						specificationName << StringEscapeUtils.escapeHtml(value)
					} else {
						value.each { specificationName << StringEscapeUtils.escapeHtml(it) }
					}
					development.specificationName = specificationName
					break
				case 'specificationValue':
					def specificationValue = []
					if (value instanceof String){
						specificationValue << StringEscapeUtils.escapeHtml(value)
					} else {
						value.each { specificationValue << StringEscapeUtils.escapeHtml(it) }
					}
					development.specificationValue = specificationValue
					break
				case 'specificationUnit':
					if (value != SpecificationUnit.NA.name()){
						development.specificationUnit = value
					}
					break
				case 'license':
					development.license = enums.License.valueOf(value)
					break
				case 'licenseOther':
					development.licenseOther = StringEscapeUtils.escapeHtml(value)
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
				//sanitise and store
					development[key] = StringEscapeUtils.escapeHtml(value)
			}
		} else {
			// process deletions
			switch (key){
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
						println "deleting ${key}"
						development[key].clear()
					}
			}
		}
		
		// process deletions where no key was passed
		
		['categories','goals', 'tags', 'projectVendor', 'specificationName','specificationValue'].each {
			if (!params[it] && development[it]){
				println "deleting ${it}"
				development[it].clear()
			}
		}
		
		
	}
}

