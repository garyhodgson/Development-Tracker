<% include '/templates/includes/header.gtpl' %>

<%
	def activities = request.activities
	def paging = request.paging
%>
<script>
jQuery(function() {	
	jQuery("table tr:even").addClass("oddrow");
});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Activities"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
	</ul>
</nav>

<div class="content">
	<table border=0 cellspacing="0" cellpadding="5px" id="activityTable">
		<% request.activities?.each { activity ->
		%>
		<tr>
			<% if (activity.type == enums.ActivityType.NewDevelopment) {%>
				<td><a href="/userinfo/${activity.by}">${activity.by}</a> created a new development called <a href="${activity.link}">${activity.title}</a></td>

			<% } else if (activity.type == enums.ActivityType.DevelopmentUpdated) {%>
				<td><a href="${activity.link}">${activity.title}</a> was updated by <a href="/userinfo/${activity.by}">${activity.by}</a></td>

			<% } else if (activity.type == enums.ActivityType.NewUser) {%>
				<td>New User: <a href="/userinfo/${activity.by}">${activity.by}</a></td>

			<% } else if (activity.type == enums.ActivityType.DevelopmentDeleted) {%>
				<td>Development ${activity.title} was deleted by ${activity.by}</a></td>
			
			<% } else if (activity.type == enums.ActivityType.NewKit) {%>
				<td><a href="/userinfo/${activity.by}">${activity.by}</a> added a new kit: <a href="${activity.link}">${activity.title}</a></a></td>
			
			<% } else if (activity.type == enums.ActivityType.KitUpdated) {%>
				<td><a href="/userinfo/${activity.by}">${activity.by}</a> updated kit <a href="${activity.link}">${activity.title}</a></a></td>
			
			<% } else if (activity.link) { %>
				<td><a href="${activity.link}">${activity.title}</a></td>

			<% } else { %>		
				<td>${activity.title}</td>
			<% } %>
	
			<td>${prettyTime.format(activity.created)?:''}</td>
		</tr>
		<% } %>
	</table>
	<% if (request.activities) include '/templates/includes/paging.gtpl' %>
</div>



<% include '/templates/includes/footer.gtpl' %>
