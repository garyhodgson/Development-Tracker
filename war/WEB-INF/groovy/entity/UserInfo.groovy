package entity

import java.io.Serializable
import java.security.MessageDigest

import javax.persistence.Id

import com.googlecode.objectify.annotation.Unindexed

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
	boolean contactOnDevelopmentComment
	boolean contactOnDevelopmentWatch
	boolean acceptTermsOfUse
	String githubId
	boolean githubIdVisible
	String thingiverseId
	boolean thingiverseIdVisible
	String reprapWikiId
	boolean reprapWikiIdVisible
	List<Long> watchedDevelopments

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
}
