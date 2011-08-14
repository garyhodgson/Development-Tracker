package development

import com.github.api.v2.services.GitHubServiceFactory
import com.github.api.v2.services.RepositoryService
import com.google.appengine.api.memcache.Expiration
import com.googlecode.objectify.Key
import com.googlecode.objectify.NotFoundException

import entity.Collaboration
import entity.Development
import entity.Relationship
import enums.Source

log.info "Showing Development"

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

def developmentKey = new Key(Development.class, params.id as Long)

try {
	request.development = dao.ofy().get(developmentKey)
	request.relationships = dao.ofy().query(Relationship.class).ancestor(developmentKey).list()
	request.collaborations = dao.ofy().query(Collaboration.class).ancestor(developmentKey).list()
} catch (NotFoundException nfe){
	request.session.message = "No development with id ${params.id} found."
	redirect '/developments'
	return
}

if (request.development.source == Source.Github){
	request.supplementary = getGithubSupplementary(request.development.sourceURL);
}

request.pageTitle = request.development.title

forward '/templates/development/show.gtpl'

def getGithubSupplementary(def url){
	def m =  url =~ /https:\/\/github.com\/(.*)\/(.*)/
	if (!m.matches() || m[0].size() != 3 ) return null

	//check memcache first
	def memcacheKey = "GithubSupplementary:${m[0][1]}-${m[0][2]}"
	if (memcache[memcacheKey]) {
		return memcache[memcacheKey]
	}

	RepositoryService service = GitHubServiceFactory.newInstance().createRepositoryService()
	def repo = service.getRepository(m[0][1], m[0][2])

	def supplementary = [:]
	supplementary.Owner = repo.owner
	supplementary.Description = repo.description
	supplementary.Created = prettyTime.format(repo.createdAt)
	if (repo.homepage) supplementary.Homepage = repo.homepage
	if (repo.fork && repo.parent) supplementary.'Forked From' = repo.parent
	supplementary.Watchers = repo.watchers
	if (repo.forks) supplementary.Forks = repo.forks
	supplementary.'Last Pushed' = prettyTime.format(repo.pushedAt)
	if (repo.hasIssues) supplementary.'Open Issues' = repo.openIssues

	memcache.put(memcacheKey.toString(), supplementary, Expiration.byDeltaSeconds(86400)) // 24hrs
	
	return supplementary
}