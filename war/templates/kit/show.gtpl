
<% 
	def kit = request.getAttribute("kit")
	def parts = request.getAttribute("parts")
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
		buttons: [{text: "Yes",click: function() { document.location = "/kit/delete/<%=kit.id%>"; }},
		          {text: "No",click: function() { jQuery(this).dialog("close"); }}
				 ] 
	});
	
	jQuery("#deleteKit").click(function(){
		jQuery("#confirm").dialog('open')
	});
});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Kit: " + kit.title%></h2>
</div>

<div class="grid_4">
	<% if (kit.thumbnailServingUrl){ %>
	<div class="kit-large-thumbnail">
		<a class="nohint" href="${kit.thumbnailServingUrl}" target="_blank"><img src="${kit.thumbnailServingUrl}"></a>
	</div>
	<% } %>
	
	<fieldset class="parts-list">
		<legend>Parts</legend>
		<ul>
			<% parts?.each { part -> %>
				<a href="/development/${part.id}"><li>${part.title}</li></a>
			<% } %>
		</ul>
	</fieldset>
</div>	

<div class="grid_7">
	<% def text = markdown.markdown(kit.description?:'') %>	
	<div class="kit-description">${text}</div>
</div>

<div id="actions" class="grid_1">
	<ul>
		<a href="/userinfo/${kit.ownerUsername}"><li>Owner</li> </a>
		<br>
		<% if (user) { %>
		<%if (users.isUserAdmin() || session.userinfo.username == kit.ownerUsername) { %>
		<a href="/kit/edit/<%=kit.id%>"><li>Edit</li> </a>
		<br>
		<a href="#" id="deleteKit" rel="#confirm"><li>Delete</li> </a>
		<% } %>
		<% } %>

	</ul>
</div>


<div id="confirm">
	<p>Are you sure you wish to delete kit ${kit.id}?</p>
</div>

<br class="clear">

<% include '/templates/includes/footer.gtpl' %>
