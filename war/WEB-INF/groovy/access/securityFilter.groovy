package access
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

import com.google.appengine.api.NamespaceManager
import com.google.appengine.api.users.UserService
import com.google.appengine.api.users.UserServiceFactory
import com.googlecode.objectify.Objectify
import com.googlecode.objectify.ObjectifyService

import entity.UserInfo

class securityFilter implements Filter {

	FilterConfig filterConfig

	def userinfoExceptionsList = [
		'/',
		'/favicon.ico',
		'/access/first',
		'/access/logout',
		'/access/postLogin',
		'/userinfo/add'
	]

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		def namespace = NamespaceManager.get()
		def subdomain = request.properties.serverName.split(/\./).getAt(0)
		def requestURI = request.getRequestURI()
		UserService userService = UserServiceFactory.getUserService();

		HttpSession session = ((HttpServletRequest) request).getSession(true);

		if (userService.isUserLoggedIn() && !session.getAttribute('userinfo')){

			if (!requestURI.startsWith("/userinfo/exists/")){
				if (!userinfoExceptionsList.contains(requestURI)){
					Objectify ofy = ObjectifyService.begin();
					def userinfo = ofy.find(UserInfo.class, userService.currentUser.userId)
					if (!userinfo) {
						println("SecurityFilter: Unable to find userinfo for a logged in user. requestURI:${requestURI}")
						session.setAttribute "message", "Unable to find userinfo. If you feel this is in error please contact <a href=\"mailto:support@development-tracker.info\">support</a>"
						response.sendRedirect("/access/first")
						return
					}

					session.setAttribute("userinfo", userinfo)
				}
			}
		}
		chain.doFilter(request, response);
		return;
	}


	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy(){
	}
}
