<% def userinfo = request.getAttribute('userinfo') %>

<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript">
	jQuery(function() {
		
		jQuery("#flowtabs").tabs("#flowpanes > div", { history: true });
		
		if (document.location.toString().split('#')[1]){
			jQuery('a[href=#'+document.location.toString().split('#')[1]+']').click()
		}
		
	});
</script>

<h2 class="pageTitle">User Info: ${userinfo.username}</h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
	
		<% if (users.isUserLoggedIn()) { %>
			<% if (users.isUserAdmin() || user.userId == userinfo.userId) { %>
				<a href="/userinfo/edit/<%=userinfo.username%>"><li>Edit</li></a>
			<% } %>	
		<% } %>	
			
	</ul>
</nav>

<div class="userinfo-thumb left">
	<img src="http://www.gravatar.com/avatar/${userinfo.getGravatarHash()}?s=150&d=mm" />
</div>

<div class="content thumbnailed">
	<ul class="tabs" id="flowtabs">
		<li><a id="t1" class="links_with_history" href="#details">Details</a></li>
		<li><a id="t2" class="links_with_history" href="#developments">Developments Registered</a></li>
		<li><a id="t3" class="links_with_history" href="#watching">Watching</a></li>
		<li><a id="t4" class="links_with_history" href="#collaborations">Collaborations</a></li>
	</ul>

	<!-- tab "panes" -->
	<div class="panes" id="flowpanes">
	
		<div class="userinfo">
			<table border=0 cellspacing="0" cellpadding="5px">
				<tr>
					<td class="label-column">Username</td>
					<td>${userinfo.username}</td>
				</tr>
				<tr>
					<td class="label-column">Registered</td>
					<td>${userinfo.created}</td>
				</tr>
				
				<% if (userinfo.githubIdVisible) { %>
				<tr><td class="label-column">Github</td><td><a target="_blank" href="https://github.com/${userinfo.githubId}">${userinfo.githubId}</a></td></tr>
				<% } %>
				<% if (userinfo.thingiverseIdVisible) { %>
				<tr><td class="label-column">Thingiverse</td><td><a target="_blank" href="http://www.thingiverse.com/${userinfo.thingiverseId}">${userinfo.thingiverseId}</a></td></tr>
				<% } %>
				<% if (userinfo.reprapWikiIdVisible) { %>
				<tr><td class="label-column">RepRap Wiki</td><td><a target="_blank" href="http://reprap.org/wiki/User:${userinfo.reprapWikiId}">${userinfo.reprapWikiId}</a></td></tr>
				<% } %>
			</table>
		</div>

		<div class="userinfo">
				<table border=0 cellspacing="0" cellpadding="5px" width=75%>
					<% request.userDevelopments?.each { development -> %>
					<tr>
						<td class="value-column"><a href="/development/${development.id}">${development.title}</a></td>
						
						<% if (users.isUserLoggedIn() && (users.isUserAdmin() || session.userinfo.username == development.createdBy)) { %>
							<td  class="linkAction"><input class="action" type="button"
								onclick="javascript:location='/development/edit/<%=development.id%>'" value="Edit"
							>
							</td>
						<% } else { %>
							<td class="linkAction"></td>
						<% } %>

					</tr>
					<% } %>
				</table>
		</div>

		<div class="userinfo">
			<table border=0 cellspacing="0" cellpadding="5px" width=75%>
				<% request.watchedDevelopments?.each { development -> %>
				<tr>
					<td class="value-column"><a href="/development/${development.id}">${development.title}</a></td>
					
					<% if (users.isUserLoggedIn() && (users.isUserAdmin() || session.userinfo.username == development.createdBy)) { %>
						<td  class="linkAction"><input class="action" type="button"
							onclick="javascript:location='/development/edit/<%=development.id%>'" value="Edit"
						>
						</td>
					<% } else { %>
						<td class="linkAction"></td>
					<% } %>

				</tr>
				<% } %>
			</table>
		</div>
		
		
		<div class="userinfo">
			<table border=0 cellspacing="0" cellpadding="5px" width=75%>
				<% request.collaborations?.each { %>
				<tr>
					<td class="linkDescription">(${it.role})</td>
					<td class="linkDescription"><a href="/development/${it.developmentId}">${it.developmentTitle}</a></td>
					
					
				</tr>
				<% } %>
			</table>
		</div>
	</div>
</div>

<% include '/templates/includes/footer.gtpl' %>
