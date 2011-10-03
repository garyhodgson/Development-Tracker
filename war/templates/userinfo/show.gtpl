<% 
	import enums.Source
	def userinfo = request.getAttribute('userinfo')
%>

<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/jquery.paginator.js"></script>
<script type="text/javascript">
	jQuery(function() {
		
		jQuery("#tabs").tabs();
		
		if (document.location.toString().split('#')[1]){
			jQuery('a[href=#'+document.location.toString().split('#')[1]+']').click()
		}
		
		jQuery("#registeredDevelopmentsTable").paginate(12, 300);
		jQuery("#watchedDevelopmentsTable").paginate(12, 300);
		jQuery("#collaborationsTable").paginate(12, 300);
		
	});
</script>

<h2 class="pageTitle">User Info: ${userinfo.username}</h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
	
		<% if (users.isUserLoggedIn()) { %>
			<% if (users.isUserAdmin() || user.userId == userinfo.userId) { %>
				<a href="/userinfo/edit/<%=userinfo.username%>"><li>Edit</li></a>
				<br>
				<a href="/kit/add"><li>Add Kit</li></a>
				
			<% } %>	
		<% } %>	
			
	</ul>
</nav>

<div class="userinfo-thumb left">
	<img src="http://www.gravatar.com/avatar/${userinfo.getGravatarHash()}?s=150&d=mm" />
</div>

<div class="content thumbnailed">
	<div id="tabs">
		<ul class="tabs" >
			<li><a href="#details">Details</a></li>
			<li><a href="#developments">Developments Registered <span class="heading-count">(${request.userDevelopments?.size()?:0})</span></a></li>
			<li><a href="#watching">Watching <span class="heading-count">(${request.watchedDevelopments?.size()?:0})</span></a></li>
			<li><a href="#collaborations">Collaborations <span class="heading-count">(${request.collaborations?.size()?:0})</span></a></li>
		</ul>

		<div class="userinfo" id="details">
			<fieldset>
				<table border=0 cellspacing="0" cellpadding="5px">
					<tr>
						<td class="label-column">Username</td>
						<td>${userinfo.username}</td>
					</tr>
					<tr>
						<td class="label-column">Registered</td>
						<td>${prettyTime.format(userinfo.created)}</td>
					</tr>
				</table>
			</fieldset>
			<br>
			<fieldset>
				<legend>Associations</legend>
				<table border=0 cellspacing="0" cellpadding="5px">
					<% userinfo.associations?.each { association ->
						def description = (association.source == enums.Source.Other)? association.sourceOther?:'' : association.source.title
					%>
					<tr>
						<td class="label-column">${description}</td>
						<td>
						<% 
							def url = association.getURL()
							if (url?.startsWith("http")) { 
						%>
							<a target="_blank" href="${url}">${association.sourceId?:''}</a>
						<% } else { %>
							${association.sourceId}
						<% } %>
						
						</td>
					</tr>
					<% } %>
				</table>
			</fieldset>
			<% if (request.kits) { %>
			<br>
			<fieldset>
				<legend>Kits</legend>
				<table border=0 cellspacing="0" cellpadding="5px">
					<% request.kits?.each { kit -> %>
					<tr>
						<% if (kit.thumbnailServingUrl){ %>
						<td>
							<a class="nohint" href="${kit.thumbnailServingUrl}" target="_blank"><img width="100px" src="${kit.thumbnailServingUrl}"></a>
						</td>
						<% } %>
						<td class="value-column"><a href="/kit/${kit.id}">${kit.title}</a></td>
						
						<% if (users.isUserLoggedIn() && (users.isUserAdmin() || session.userinfo.username == kit.ownerUsername)) { %>
							<td  class="linkAction"><input class="action" type="button"
								onclick="javascript:location='/kit/edit/<%=kit.id%>'" value="Edit"
							>
							</td>
						<% } else { %>
							<td class="linkAction"></td>
						<% } %>
	
					</tr>
					<% } %>
				</table>
			</fieldset>
			<% } %>
		</div>
		
		<div class="userinfo" id="developments">
				<table border=0 cellspacing="0" cellpadding="5px" width=75% id="registeredDevelopmentsTable">
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

		<div class="userinfo" id="watching">
			<table border=0 cellspacing="0" cellpadding="5px" width=75% id="watchedDevelopmentsTable">
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
		
		
		<div class="userinfo" id="collaborations">
			<table border=0 cellspacing="0" cellpadding="5px" width=75% id="collaborationsTable">
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
