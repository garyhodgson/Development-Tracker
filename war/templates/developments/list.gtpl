<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {	
		jQuery("table tr:even").addClass("oddrow");
	});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Developments"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
		<% if (users.isUserLoggedIn()) { %>
			<a href="/development/add"><li>New &hellip;</li></a>
		<% } %>
		<br>
		<a href="/developments/latest"><li>Latest</li></a>
		<a href="/developments"><li>A-Z</li></a>
		<br>
		<a href="/developments/latest/feed.rss"><li>RSS</li></a>
	</ul>
</nav>

<div class="content" >
		<table border=0 cellspacing="0" cellpadding="5px">
			<tr>
				<th width="30%">Title</th>
				<th width="50%">Description</th>
				<th width="10%">Source</th>
				<th width="10%">Status</th>
			</tr>
			<% request.developments?.each { development -> %>
			<tr>
				<td width="30%"><a href="/development/${development.id}">${development.title}</a></td>
				<td width="50%">${development.description? org.apache.commons.lang.StringUtils.abbreviate(development.description, 200) : ''}</td>

				<td width="10%">
				<% if (development.sourceURL) {%>
					<a href="${development.sourceURL}" title="${development.sourceURL}">${development.source?:''}</a>
				<% } else { %>
					${development.source?:''}
				<% } %>					
				</td>
				<% def status = development.status? development.status.title : '' %>
				<td width="10%">${status}</td>
			</tr>
			<% } %>
		</table>
		<% if (request.developments) include '/templates/includes/paging.gtpl' %>
		
</div>

<% include '/templates/includes/footer.gtpl' %>