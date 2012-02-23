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

<div class="grid_10 prefix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Activities"%></h2>
</div>

<div class="grid_12">
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
