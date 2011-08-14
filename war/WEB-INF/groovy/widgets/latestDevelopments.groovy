package widgets 

import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.Development

static def getLatest(def subdomain){
	return ObjectifyService.begin().query(Development.class).order('-created').limit(5).list()
}