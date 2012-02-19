package cache

import static enums.MemcacheKeys.*

import com.google.appengine.api.memcache.LogAndContinueErrorHandler
import com.google.appengine.api.memcache.StrictErrorHandler
import com.googlecode.objectify.ObjectifyService

import entity.Activity
import entity.Development
import entity.Kit
import entity.Theme
import groovyx.gaelyk.GaelykBindings

@GaelykBindings
class CacheManager {

	def ofy = ObjectifyService.begin()

	private def rememberKey(String key){
		def keys = memcache[Keys]?:[]as Set<String>
		keys << key
		memcache[Keys] = keys
	}

	private def forgetKey(String key){
		def keys = memcache[Keys]?:[]as Set<String>
		keys.remove(key)
		memcache[Keys] = keys
	}

	private def deleteEntries(String startOfKey){
		(memcache[Keys]?:[]).each { String memcacheKey ->
			if (memcacheKey.startsWith(startOfKey)){
				if (!memcache.delete(memcacheKey)){
					println("Memcache failed to delete key: ${memcacheKey}")
				} else {
					forgetKey(memcacheKey)
				}
			}
		}
	}

	public def resetCache(){
		memcache.clearAll()
	}

	public def allDevelopments(){
		def memcacheKey = AllDevelopments.name()
		rememberKey(memcacheKey)
		if (!memcache[memcacheKey]){
			memcache[memcacheKey] = ofy.query(Development.class).order('title').list().sort{it.title.toLowerCase()}
		}		
		return  memcache[memcacheKey]
	}

	public def resetDevelopmentCache(){
		if (memcache.delete(AllDevelopments.name())){
			forgetKey(AllDevelopments.name())
		}
	}

	public def developmentCount(){
		return allDevelopments().size()
	}

	public def latestDevelopments(def offset, def limit){
		def devs = allDevelopments()
		devs.sort { it.created }		
		def from = Math.max(0, offset)
		def to = Math.min(from+limit, devs.size())
		return devs.reverse()[from..<to]
	}

	public def activityCount(){
		def memcacheKey = TotalActivitiesCount.name()
		rememberKey(memcacheKey)
		return memcache[memcacheKey]?:(memcache[memcacheKey] = ofy.query(Activity.class).count())
	}

	public def resetActivityCache(){
		deleteEntries(LatestActivities.name())
		deleteEntries(TotalActivitiesCount.name())
	}

	public def latestActivities(def offset, def limit){
		def memcacheKey = "${LatestActivities}:${offset}:${limit}"
		rememberKey(memcacheKey)
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Activity.class).order('-created').offset(offset).limit(limit).list())
	}

	public def kitCount(){
		def memcacheKey = TotalKitsCount.name()
		rememberKey(memcacheKey)
		return memcache[memcacheKey]?:(memcache[memcacheKey] = ofy.query(Kit.class).count())
	}

	public def resetKitCache(){
		deleteEntries(LatestKits.name())
		deleteEntries(TotalKitsCount.name())
	}

	public def latestKits(def offset, def limit){
		def memcacheKey = "${LatestKits}:${offset}:${limit}"
		rememberKey(memcacheKey)
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Kit.class).order('-created').offset(offset).limit(limit).list())
	}

	public def themeCount(){
		def memcacheKey = TotalThemesCount.name()
		rememberKey(memcacheKey)
		return memcache[memcacheKey]?:(memcache[memcacheKey] = ofy.query(Theme.class).count())
	}

	public def resetThemesCache(){
		deleteEntries(TotalThemesCount.name())
		deleteEntries(LatestThemes.name())
	}

	public def latestThemes(def offset, def limit){
		def memcacheKey = "${LatestThemes}:${offset}:${limit}"
		rememberKey(memcacheKey)
		return memcache[memcacheKey] ?:
		(memcache[memcacheKey] = ofy.query(Theme.class).order('-created').offset(offset).limit(limit).list())
	}
}

