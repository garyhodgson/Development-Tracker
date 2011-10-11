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

	public def activityCount(){
		return memcache[TotalActivitiesCount]?:(memcache[TotalActivitiesCount] = ofy.query(Activity.class).countAll())
	}

	public def kitCount(){
		return memcache[TotalKitsCount]?:(memcache[TotalKitsCount] = ofy.query(Kit.class).countAll())
	}
	
	public def themeCount(){
		return memcache[TotalThemesCount]?:(memcache[TotalThemesCount] = ofy.query(Theme.class).countAll())
	}
}

