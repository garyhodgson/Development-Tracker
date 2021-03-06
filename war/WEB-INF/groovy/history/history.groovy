package history

import com.googlecode.objectify.Key

import entity.ChangeHistory
import entity.Development

if (!params.id){
	request.session.message = "No Id given."
	redirect '/developments'
	return
}

def developmentKey = new Key(Development.class, params.id as Long)

request.development = dao.ofy().get(developmentKey)

request.changeHistories = dao.ofy().query(ChangeHistory.class).ancestor(developmentKey).order('-on').list()

request.pageTitle = "Change History : ${request.development.title}"


forward "/templates/history/history.gtpl"