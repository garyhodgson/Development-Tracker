package development

def blobKeys = blobstore.getUploadedBlobs(request)
def blobKey = blobKeys["file"]

response.status = 302

if (blobKey) {
	sout << blobKey.keyString
} else {
	sout << "error"
}
