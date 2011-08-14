package entity

import java.io.Serializable
import java.security.MessageDigest

import javax.persistence.Embedded
import javax.persistence.Id

import com.googlecode.objectify.Key
import com.googlecode.objectify.annotation.AlsoLoad
import com.googlecode.objectify.annotation.Unindexed

import enums.Source

class UserInfo implements Serializable {

	@Id
	String userId
	String username
	String email
	Date created
	Date updated
	@Unindexed
	String imageURL
	@Unindexed
	String gravatarHash
	boolean useGravatar
	boolean acceptTermsOfUse
	
	@Embedded List<Association> associations = new ArrayList<Association>();
	
	List<Long> watchedDevelopments	
	
	void importGithubId(@AlsoLoad("githubId") String githubId) {
		if (!this.associations) this.associations = []
		this.associations << new Association(source:Source.Github, sourceId: githubId)
	}
	void importThingiverseId(@AlsoLoad("thingiverseId") String thingiverseId) {
		if (!this.associations) this.associations = []
		this.associations << new Association(source:Source.Thingiverse, sourceId: thingiverseId)
	}
	void importReprapWikiId(@AlsoLoad("reprapWikiId") String reprapWikiId) {
		if (!this.associations) this.associations = []
		this.associations << new Association(source:Source.RepRapWiki, sourceId: reprapWikiId)
	}

	def getGravatarHash() {
		if (!useGravatar) return ""
		return gravatarHash?:calculateGravatarHash(email)
	}

	def static String calculateGravatarHash(String email){
		MessageDigest digest = MessageDigest.getInstance("MD5")
		digest.update(email.trim().bytes)
		BigInteger big = new BigInteger(1,digest.digest())
		return big.toString(16).padLeft(32,"0")
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append(username)
		sb.append(": associations:[")
		associations.each {
			sb.append(it.toString())
			sb.append(";")
		}
		sb.append("]")
		return sb.toString()
	}
}
