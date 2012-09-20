
<% 	include '/templates/includes/htmlHeader.gtpl' 
	def hideAccess = ["/templates/access/login.gtpl", "/templates/access/first.gtpl"].contains(request.requestURI)
%>

<script>

jQuery(function() {
	
	function search(){
		var searchKey = jQuery('#searchKey').val()
		if (searchKey != ''){
			location = "/developments/search/"+searchKey	
		}
	}
	
	
	jQuery('#searchButton').click(function(){
		search()
	})
	
	jQuery('#searchDevelopmentForm').submit(function(){
		search()
		return false
	})
});

</script>

<div id="wrapper">
	<div class="container_12" id="header">

		<div class="grid_12">
			<a href="/"><img id="dtlogo" src="/images/DTLogo-50px.png"> </a>
			<h1 id="branding">Development Tracker</h1>
		</div>
		
		<div class="clear"></div>
		
		<div class="grid_12">
			<ul class="nav main">
				<li>
					<a href="#">Developments</a>
					<ul>
						<li>
							<a href="/developments/latest">Latest</a>
						</li>
						<li>
							<a href="/developments">A-Z</a>
						</li>
						<% if (users.isUserLoggedIn()) {%>
						<li>
							<a href="/development/add">New...</a>
						</li>
						<% } %>
					</ul>
				</li>
				<li>
					<a href="#">Browse</a>
					<ul>
						<li>
							<a href="/developments/browse/categories">Categories</a>
						</li>
						<li>
							<a href="/developments/browse/tags">Tags</a>
						</li>
						<li>
							<a href="/developments/browse/status">Status</a>
						</li>
						<li>
							<a href="/developments/browse/goals">Goals</a>
						</li>
						<li>
							<a href="/developments/browse/projectVendor">Project / Vendor</a>
						</li>
						<li>
							<a href="/developments/browse/developmentType">Development Type</a>
						</li>					
						<li>
							<a href="/developments/browse/source">Source</a>
						</li>
						<li>
							<a href="/developments/browse/license">License</a>
						</li>						
					</ul>
				</li>
				<li>
					<a href="#">Themes</a>
					<ul>
						<li>
							<a href="/themes">Latest</a>
						</li>
						<% if (users.isUserLoggedIn()) {%>
						<li>
							<a href="/theme/add">New...</a>
						</li>
						<% } %>
					</ul>
				</li>
				<li>
					<a href="#">Setups</a>
					<ul>
						<li>
							<a href="/setups">Latest</a>
						</li>
						<% if (users.isUserLoggedIn()) {%>
						<li>
							<a href="/setup/add">New...</a>
						</li>
						<% } %>
					</ul>
				</li>
				<li>
					<a href="#">Activities</a>
					<ul>
						<li>
							<a href="/activities">Latest</a>
						</li>
					</ul>
				</li>
				<% if (users.isUserLoggedIn() && session.getAttribute("userinfo")) {%>
				<li>
					<a href="#">My Info</a>
					<ul>
						<li>
							<a href="/userinfo/<%=session.getAttribute("userinfo")?.username%>">View</a>
						</li>
					</ul>
				</li>
				
				<% } %>
							
				
				<li class="secondary">
					<%	if (users.isUserLoggedIn()) {%>
					
						<a href="/access/logout">Sign Out</a>
					
					<%
						} else {
					%>				
						<a href="/access/login?continue=<%=request.getAttribute("javax.servlet.forward.request_uri")?:'/'%>">Sign In</a>
					<%
						}
					%>
				</li>
				
				
				<li class="secondary">
					<div class="block" id="search">
			
						<form id="searchDevelopmentForm" class="search">
							<p>
								<input class="search text" id="searchKey" name="searchKey" type="text" />
								<input id="searchButton" class="search button" value="Search" type="button" />
							</p>
						</form>
					</div>
				</li>
			</ul>
		</div>
		<div class="clear"></div>
	
		<% if (request.session.getAttribute('message') != null) { %>
		<div class="message"><%=request.session.getAttribute('message')?:''%></div>
		<% request.session.setAttribute('message',null) } %>
		
	</div>
	<div class="container_12" id="content">
	