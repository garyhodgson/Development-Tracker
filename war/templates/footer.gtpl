
<br clear="both">
</div>
<footer class="main-footer">
	<% if (request.getAttribute("javax.servlet.forward.request_uri") == '/'){ %>
		<a href="${app.AppProperties.LAUNCH_URL}" style="border-style: none;"><div style="display: inline-block;" class="footer-link">Launch Page</div></a>
	<% } else { %>
		<a href="/" style="border-style: none;"><div style="display: inline-block;" class="footer-link">Home</div></a>
	<% } %>
	
	<a href="/about" style="border-style: none;"><div style="display: inline-block;" class="footer-link">About</div></a>
	<a href="/faq" style="border-style: none;"><div style="display: inline-block;" class="footer-link">FAQ</div></a>
	<a href="/future" style="border-style: none;"><div style="display: inline-block;" class="footer-link">Future</div></a>
	<a href="/blog" style="border-style: none;"><div style="display: inline-block;" class="footer-link">Blog</div></a>
</footer>

<footer class="legal-footer">
	<a href="/terms" style="border-style: none;">Terms of Use</a>
</footer>


<% include '/templates/includes/htmlFooter.gtpl' %>