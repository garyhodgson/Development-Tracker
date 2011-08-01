package developments
import entity.Development


log.info "Retrieving Latest Developments"

def limit = params.count?: 10 

request.developments = dao.ofy().query(Development.class).limit(limit as int).order('-created').list() 
request.pageTitle = "Latest Developments"

forward '/templates/developments/list.gtpl'