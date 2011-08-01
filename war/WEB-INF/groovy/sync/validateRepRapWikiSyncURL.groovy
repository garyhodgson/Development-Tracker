package sync

log.info "Validating sync url"

if (!params.syncURL){
	log.warn "syncURL parameter missing from request"
	return
}

URL url = new URL(params.syncURL)

def response = url.get()

if (response.responseCode != 200){
	out << "false|Wrong response from URL (${response.responseCode})"
	return
} 
def text = response.text

if (!text.contains("{{Development\n")){
	out << "false|Unable to find Development tags in page at the given URL"
	return
}

def m = text.subSequence(text.indexOf("{{Development"), text.indexOf("}}",text.indexOf("{{Development"))) =~ /\|(.*?)[ ]?=[ ]?(.*?)\n/

if (m.count > 0){
	out << "true|Found ${m.count} parameters"
	return
} else {
	out << "true|The page at the URL did not contain any parameters. Please check the URL, or contact support."
	return
}


