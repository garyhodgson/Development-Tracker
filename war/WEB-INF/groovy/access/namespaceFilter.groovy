package access
import javax.servlet.*

import com.google.appengine.api.NamespaceManager

class namespaceFilter implements Filter {

	FilterConfig filterConfig

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (NamespaceManager.get() == null) {
			NamespaceManager.set('3dprint');
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
