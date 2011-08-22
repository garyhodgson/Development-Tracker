package userinfo

import entity.Association
import entity.UserInfo
import enums.Source

if (!params.username){
	request.session.message = "No username given."
	redirect '/'
}

if (!users.isUserLoggedIn()){
	request.session.message = "Must be logged in to edit."
	request.continue = "/userinfo/edit/${params.username}"
	forward "/templates/access/login.gtpl"
	return
}

namespace.of("") {

	UserInfo userinfo = dao.ofy().query(UserInfo.class).filter('username', params.username).get()

	if (!userinfo || (userinfo.userId != user.userId && !users.isUserAdmin())){
		request.session.message = "No permission to edit userinfo for ${params.username}. If you feel this is in error please contact <a href=\"mailto:${app.AppProperties.SUPPORT_EMAIL}\">support</a>."
		redirect '/'
		return
	}
	
	params.each { k, v ->
		//sanitise - happens in securityfilter
		if (v && v instanceof String && !k.startsWith('association')){
			userinfo[k] = v
		} 
	}

	//checkboxes
	[
		'useGravatar',
		'acceptTermsOfUse',
	].each {
		userinfo[it] = params[it]?true:false
	}
	
	userinfo.associations = processAssociations(params) 

	userinfo.updated = new Date()

	dao.ofy().put(userinfo)
	
	request.session.userinfo = userinfo
}

redirect "/userinfo/${params.username}"


def processAssociations(def params){
	def associations = []
	
	if(!params.associationSource) return associations;

	if(!params.associationSourceId && !params.associationSourceOther) return associations;

	def associationSource = (params.associationSource instanceof String)? [params.associationSource]: params.associationSource

	associationSource.eachWithIndex { source, i ->
		def a = new Association()

		a.source = Source.valueOf(source)

		if (params.associationSourceId){
			def associationSourceId = (params.associationSourceId instanceof String) ? params.associationSourceId : params.associationSourceId[i]
			if (associationSourceId){
				a.sourceId = associationSourceId
			}
		}

		if (a.source == Source.Other){
			def associationSourceOther = (params.associationSourceOther instanceof String) ? params.associationSourceOther : params.associationSourceOther[i]
			a.sourceOther = associationSourceOther
		}

		associations << a
	}
	
	return associations
}