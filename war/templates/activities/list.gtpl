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
			<td><a href="${activity.link?:'#'}">${activity}</a></td>
			<td>${prettyTime.format(activity.created)?:''}</td>
		</tr>
		<% } %>
	</table>
	<% if (request.activities) include '/templates/includes/paging.gtpl' %>
</div>



<% include '/templates/includes/footer.gtpl' %>
