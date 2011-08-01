package info.developmenttracker.access;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * Copied from https://code.google.com/p/gae-java-openid/
 * 
 * User: Ishara Samantha Date: Jun 23, 2010 Time: 11:36:34 PM
 */
public class LoginRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(LoginRedirectServlet.class.getName());

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url;
		
		if (request.getParameter("continue") != null) {
			url = "/access/postLogin?continue=" + request.getParameter("continue");
		} else {
			url = "/access/postLogin?continue=" + request.getRequestURI();
		}

		UserService userService = UserServiceFactory.getUserService();

		String openIdUrl = null;
		String openIdUserName = null;
		if (request.getParameter("openid_identifier") != null) {
			openIdUrl = request.getParameter("openid_identifier");
		}
		if (request.getParameter("openid_username") != null) {
			openIdUserName = request.getParameter("openid_username");
		}

		String openIdRedirectUrl = userService.createLoginURL(url, "OpenID",
				openIdUrl, null);

		log.finer("continue = " + url);
		log.finer("openIdUserName = " + openIdUserName);
		log.finer("openIdUrl = " + openIdUrl);
		log.finer("openIdRedirectUrl = " + openIdRedirectUrl);

		response.sendRedirect(openIdRedirectUrl);
	}

}