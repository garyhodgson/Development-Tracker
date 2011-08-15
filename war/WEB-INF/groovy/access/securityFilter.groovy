package access

import entity.UserInfo
import groovyx.gaelyk.GaelykBindings

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper
import javax.servlet.http.HttpSession

import org.apache.commons.lang.StringEscapeUtils

import com.google.appengine.api.NamespaceManager
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

@GaelykBindings
class securityFilter implements Filter {

	FilterConfig filterConfig

	def userinfoExceptionsList = [
		'/favicon.ico',
		'/access/first',
		'/access/logout',
		'/access/postLogin',
		'/userinfo/add',
		'/userinfo/exists/',
		'/development/exists/'
	]

	def namespaceExceptionsList = [
		'/about',
		'/faq',
		'future',
		'/start',
		'/'
	]

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		def namespace = NamespaceManager.get()
		def subdomain = request.properties.serverName.split(/\./).getAt(0)
		def requestURI = request.getRequestURI()
		//UserService userService = UserServiceFactory.getUserService();
		HttpSession session = ((HttpServletRequest) request).getSession(true);

		if (namespace == 'default' && (request.getRequestURI() != '/' && !namespaceExceptionsList.find{request.getRequestURI().startsWith(it)})){
			// We should not go anywhere apart from home
			session.setAttribute("message", "Please choose your preferred project/vendor.");
			response.sendRedirect("/");
			return
		}

		if (users.isUserLoggedIn() && !session.getAttribute('userinfo')){

			if (!userinfoExceptionsList.contains(requestURI)){

				NamespaceManager.set("");
				try {
					Objectify ofy = ObjectifyService.begin();
					def userinfo = ofy.find(UserInfo.class, users.currentUser.userId)
					if (userinfo) {
						session.setAttribute("userinfo", userinfo)
					} else {
						System.err.println("SecurityFilter: Unable to find userinfo for a logged in user:${users.currentUser.userId} and requestURI: ${requestURI}")
					}
				} finally {
					NamespaceManager.set(namespace);
				}
			}
		}

		chain.doFilter(new SanitisedRequest(request), response);
		return;
	}

	class SanitisedRequest extends HttpServletRequestWrapper {

		public SanitisedRequest(ServletRequest request) {
			super((HttpServletRequest)request);
		}

		public String getParameter(String paramName) {
			return StringEscapeUtils.escapeHtml(super.getParameter(paramName))
		}

		public String[] getParameterValues(String paramName) {
			def values = super.getParameterValues(paramName);
			for (int index = 0; index < values.length; index++) {
				values[index] = StringEscapeUtils.escapeHtml(values[index]);
			}
			return values;
		}
	}


	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy(){
	}
}
