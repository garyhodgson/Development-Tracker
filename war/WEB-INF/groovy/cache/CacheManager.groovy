package cache

import static enums.MemcacheKeys.*

import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.Development
import entity.Kit
import entity.Theme
import groovyx.gaelyk.GaelykBindings


@GaelykBindings
class CacheManager {

	def ofy = ObjectifyService.begin()

	public def resetCache(){
		memcache.clearAll()

		developmentCount()
	}

	public def allDevelopments(){
		return memcache[AllDevelopments] ?: (memcache[AllDevelopments] = ofy.query(Development.class).order('title').list())
	}

	public def developmentCount(){
		return allDevelopments().size()
	}
	
	public def latestDevelopments(def offset, def limit){
		def memcacheKey = "${LatestDevelopments}:${offset}:${limit}"
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Development.class).order('-created').offset(offset).limit(limit).list())
	}

	public def activityCount(){
		return memcache[TotalActivitiesCount]?:(memcache[TotalActivitiesCount] = ofy.query(Activity.class).countAll())
	}

	public def refreshActivityCount(){
		memcache[TotalActivitiesCount] = ofy.query(Activity.class).countAll()
	}
	
	public def latestActivities(def offset, def limit){
		def memcacheKey = "${LatestActivities}:${offset}:${limit}"
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Activity.class).order('-created').offset(offset).limit(limit).list())
	}

	public def kitCount(){
		return memcache[TotalKitsCount]?:(memcache[TotalKitsCount] = ofy.query(Kit.class).countAll())
	}

	public def refreshKitCount(){
		memcache[TotalKitsCount] = ofy.query(Kit.class).countAll()
	}

	public def latestKits(def offset, def limit){
		def memcacheKey = "${LatestKits}:${offset}:${limit}"
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Kit.class).order('-created').offset(offset).limit(limit).list())
	}

	public def themeCount(){
		return memcache[TotalThemesCount]?:(memcache[TotalThemesCount] = ofy.query(Theme.class).countAll())
	}

	public def refreshThemesCount(){
		memcache[TotalThemesCount] = ofy.query(Theme.class).countAll()
	}

	public def latestThemes(def offset, def limit){
		def memcacheKey = "${LatestThemes}:${offset}:${limit}"
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Theme.class).order('-created').offset(offset).limit(limit).list())
	}
}

