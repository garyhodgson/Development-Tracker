
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

<h2 class="pageTitle"><%=request.pageTitle?:"Kit: " + kit.title%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
		<br>
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
</nav>

<div class="left" style="width:40%">
	<% if (kit.thumbnailServingUrl){ %>
	<div class="kit-thumbnail">
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

<div class="content left" style="width:45%">
	<% if (kit.description) { %>
	<div class="left kit-description">${kit.description}</div>
	<% } %>
</div>

<div id="confirm">
	<p>Are you sure you wish to delete kit ${kit.id}?</p>
</div>
<% include '/templates/includes/footer.gtpl' %>
