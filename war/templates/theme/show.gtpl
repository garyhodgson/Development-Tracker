<% 
	def theme = request.getAttribute("theme")
	def developments = request.getAttribute("developments")
%>
<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript">
jQuery(function() {	

	jQuery("#confirm").dialog({ 
		autoOpen: false,
		dialogClass:'modal',
		position: 'center',
		minHeight: 50,
		modal: true,
		resizable: false,
		title: 'Confirm Delete',
		closeText: '',
		buttons: [{text: "Yes",click: function() { document.location = "/theme/delete/<%=theme.id%>"; }},
		          {text: "No",click: function() { jQuery(this).dialog("close"); }}
				 ] 
	});
	
	jQuery("#deleteTheme").click(function(){
		jQuery("#confirm").dialog('open')
	});
});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Theme: " + theme.title%></h2>
</div>

<div class="grid_11">
		<% if (theme.description) { %>
			<div class="theme-description">${theme.description}</div>
		<% } %>

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
				<td width="50%" class="list-smaller-text">${development.description? org.apache.commons.lang.StringUtils.abbreviate(development.description, 200) : ''}</td>

				<td width="10%" class="list-smaller-text">
				<% if (development.sourceURL) {%>
					<a href="${development.sourceURL}" title="${development.sourceURL}">${development.source?:''}</a>
				<% } else { %>
					${development.source?:''}
				<% } %>					
				</td>
				<% def status = development.status? development.status.title : '' %>
				<td width="10%" class="list-smaller-text">${status}</td>
			</tr>
			<% } %>
		</table>
		<% if (request.developments) include '/templates/includes/paging.gtpl' %>		
</div>


<div id="actions" class="grid_1">
	<ul>
		<% if (user) { %> 
			<%if (users.isUserAdmin() || session.userinfo?.username == theme.createdBy) { %>
				<a href="/theme/edit/<%=theme.id%>"><li>Edit</li> </a>
			<% } %> 
		<% } %>
		<% if (user) { %> 
			<%if (users.isUserAdmin() || session.userinfo?.username == theme.createdBy) { %>
				<a href="#" id="deleteTheme" rel="#confirm"><li>Delete</li> </a>
			<% } %> 
		<% } %>
		
	</ul>
</div>

<div id="confirm">
	<p>Are you sure you wish to delete theme ${theme.id}?</p>
</div>

<% include '/templates/includes/footer.gtpl' %>
