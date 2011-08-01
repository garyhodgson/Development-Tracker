import org.apache.commons.lang.StringEscapeUtils

import entity.Development


def spec = ["width&height":"~20", 'other':'<script>300=299</script>']

def d = new Development()

log.info d.specification as String

d.specification = spec

log.info d.specification as String
//dao.ofy().put(d)


html.html {
	body {
		h2('test')	
	}
}