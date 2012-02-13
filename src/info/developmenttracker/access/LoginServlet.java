package info.developmenttracker.access;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copied from https://code.google.com/p/gae-java-openid/
 * 
 * User: Ishara Samantha Date: Jun 25, 2010 Time: 12:09:19 PM
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url;
		if (request.getParameter("continue") != null) {
			url = request.getParameter("continue");
		} else {
			url = request.getRequestURI();
		}

		response.sendRedirect("/access/login?continue=" + url);

	}
}