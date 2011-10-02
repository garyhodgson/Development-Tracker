package development

import groovyx.gaelyk.GaelykBindings
import info.developmenttracker.ThumbnailException

import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.files.FileServiceFactory
import com.googlecode.objectify.ObjectifyService

@GaelykBindings
public class ThumbnailHelper {
	
	public static final String UPLOADED_FILE = "Uploaded File:"
	
	private def generateThumbnailFromBlob(def imageBlobKey) throws ThumbnailException{
		def thumbnailFile
		try {
			def blobKey = new BlobKey(imageBlobKey)
			def image = blobKey.image
			def filename = blobKey.filename
			thumbnailFile = resizeThumbnail(image, filename, 400,400)
			blobKey.delete()
		} catch (FileNotFoundException fnfe){
			throw new ThumbnailException("There was an error processing the thumbnail image.")
		}
		return thumbnailFile
	}
	

	private def generateThumbnailFromURL(def imageURL) throws ThumbnailException{
		def thumbnailFile
		def image = getImageFromURL(imageURL)
		if (image){
			def ratio = image.height / image.width
			def width = 400
			def height = 400*ratio as int
			def filename = imageURL.substring(imageURL.lastIndexOf('/')+1)
			thumbnailFile = resizeThumbnail(image, filename, width,height)
		} else {
			throw new ThumbnailException("There was an error retrieving the thumbnail image from the URL given.")
		}
		return thumbnailFile
	}

	public def generateThumbnail(def originalThumbnailPath, def params, def newEntity) throws ThumbnailException {

		if (!params.imageBlobKey && !params.imageURL){
			if (originalThumbnailPath){
				deleteThumbnail(originalThumbnailPath)
			}
			return
		}
		
		def thumbnailFile
		if (params.imageBlobKey){
			thumbnailFile = generateThumbnailFromBlob(params.imageBlobKey)
		} else if (params.imageURL && !params.imageURL.startsWith(UPLOADED_FILE)) {
			thumbnailFile = generateThumbnailFromURL(params.imageURL)
		} else {
			// no new data
			return
		}
	
		if (!thumbnailFile){
			throw new ThumbnailException("There was an error generating the thumbnail image.")
		}

		if (originalThumbnailPath) {
			deleteThumbnail(originalThumbnailPath)
		}

		try {
			newEntity.thumbnailPath = thumbnailFile.getFullPath()
			newEntity.thumbnailServingUrl = images.getServingUrl(thumbnailFile.blobKey)

			ObjectifyService.begin().put(newEntity)
			
		} catch (Exception e){
			e.printStackTrace()
			System.err.println e.getLocalizedMessage()
			throw new ThumbnailException("There was an error generating the thumbnail image.")
		}
	}

	def deleteThumbnail(def path){
		if (!path){
			return
		}

		try {
			def file = files.fromPath(path)
			if (file) {
				file.delete()
			}
		} catch (Exception e){
			e.printStackTrace()
			System.err.println "Unable to delete thumbnail: ${path}"
		}
	}

	public def getImageFromURL(def imageURL) {

		if (!imageURL) return null

		URL url = new URL(imageURL)
		def image = null

		try {
			def response = url.get()
			if (response.responseCode != 200) return null
			image = response.content.image
		} catch (SocketTimeoutException ste){
			ste.printStackTrace()
			System.err.println  ste.getLocalizedMessage()
		}
		return image
	}

	public def resizeThumbnail(def image, def filename, def width, def height) {

		def thumbnail = image.resize(width, height)
		def file = FileServiceFactory.getFileService().createNewBlobFile("image/xyz", filename)

		file.withOutputStream { stream  ->
			stream  << thumbnail.getImageData()
		}
		return file
	}
}