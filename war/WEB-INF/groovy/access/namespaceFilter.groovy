package access
import javax.servlet.*

import com.google.appengine.api.NamespaceManager

class namespaceFilter implements Filter {

	FilterConfig filterConfig
	def namespaceMap = ['3dprint':[
			'reprap',
			'makerbot',
			'3dprint'
		]]

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (NamespaceManager.get() == null) {
			def subdomain = request.properties.serverName.split(/\./).getAt(0)

			def namespace = namespaceMap.find{it.value.contains(subdomain)}?.getKey()?:'default'

			NamespaceManager.set(namespace);
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
