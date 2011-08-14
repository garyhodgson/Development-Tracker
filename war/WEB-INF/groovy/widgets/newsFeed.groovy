package widgets 

import com.sun.syndication.fetcher.impl.FeedFetcherCache
import com.sun.syndication.fetcher.impl.HashMapFeedInfoCache
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher
import com.sun.syndication.log.info.*

static def getNews(def subdomain){
	def newsFeedMap = ['makerbot': 'http://www.makerbot.com/feed/','reprap': 'http://blog.reprap.org/feeds/posts/default']
	
	if (!newsFeedMap[subdomain]) return null
	
	FeedFetcherCache feedInfoCache = HashMapFeedInfoCache.getInstance()
	
	return new HttpURLFeedFetcher(feedInfoCache).retrieveFeed(new URL(newsFeedMap[subdomain]));
}