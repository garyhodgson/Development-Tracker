package development

import com.google.appengine.api.blobstore.BlobKey

if (params.imageBlobKey){
	try {
		(new BlobKey(params.imageBlobKey)).delete()
	} catch (Exception e){
		e.printStackTrace()
		System.err.println "Exception deleting blob after cancel: ${params.imageBlobKey}"
	}
}

redirect '/developments/latest'
