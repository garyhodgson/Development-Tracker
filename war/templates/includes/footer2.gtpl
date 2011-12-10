
	<div class="clear"></div>
	
	<div class="grid_12" id="site_info">
	
		<footer class="box">
			<p>
				<% if (request.getAttribute("javax.servlet.forward.request_uri") == '/'){ %>
					<a href="${app.AppProperties.LAUNCH_URL}" style="border-style: none;">Launch Page</a>
				<% } else { %>
					<a href="/" style="border-style: none;">Home</a>
				<% } %>
				|
				<a href="/about" style="border-style: none;">About</a>
				|
				<a href="/faq" style="border-style: none;">FAQ</a>
				|
				<a href="/future" style="border-style: none;">Future</a>
				|
				<a href="/blog" style="border-style: none;">Blog</a>
				|
				<a href="/terms" style="border-style: none;">Terms of Use</a>
			</p>
		</footer>
	
	</div>

	<div class="clear"></div>
	
</div> <!-- // container_12  -->
		
<% include '/templates/includes/htmlFooter2.gtpl' %>