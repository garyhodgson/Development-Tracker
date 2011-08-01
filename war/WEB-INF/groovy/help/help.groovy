
session = session?:request.getSession(true)


session.message = "The context sensitive help is not yet written so here is the FAQ page. If this does not answer your question feel free to send a message to <a href='mailto:support@development-info.info'>support</a>"

forward '/templates/help/faq.gtpl'