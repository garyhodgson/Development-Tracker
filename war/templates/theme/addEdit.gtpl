
<%
	import app.AppProperties
	import enums.*
	def theme = request?.getAttribute('theme')
	def developments = request?.getAttribute('developments')
%>

<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {
		jQuery('#submitForm').click(function() {
			jQuery('#addThemeForm').submit();	
		})
		
		
		jQuery("input[name=developmentTitle]").autocomplete({
			minLength: 2,
			source: '/developments/autocomplete',
			select: function( event, ui ) {
				if (ui.item.id){
					
					jQuery('#developmentsTable').append("<tr>"+
							"<td>"+
							ui.item.label+
							"</td>"+
							"<td><input type=\"hidden\" name=\"developmentId\" value=\""+
							ui.item.id+
							"\" />"+
							ui.item.id+
							"</td>"+
							"<td><a href=\"javascript://\" id=\"removeDevelopment\">remove</a></td>"+
							"</tr>")
				}
			}
		});
		
		jQuery('#removeDevelopment').live('click', function(){
			jQuery(this).parent().parent().remove();	
		})

	});
</script>

<h2 class="pageTitle"><%=request.getAttribute('pageTitle')?:'Add Theme'%></h2>

<nav>
	<ul>
		<a href="javascript://" id="submitForm"><li>Save</li> </a>
		<% if (theme?.id){ %>
		<a href="/theme/${theme?.id}"><li>Cancel</li> </a>
		<% } else { %>
		<a href="javascript://" id="cancelAdd"><li>Cancel</li> </a>
		<% } %>
	</ul>
</nav>

<div class="content">

	<form action="<%=request.getAttribute('action')?:'/theme/add'%>" method="post" id="addThemeForm">

		<div id="descriptions">
			<input type="hidden" id="id" name="id" value="<%=theme?.id %>">

			<table border=0 cellspacing="0" cellpadding="5px" class="form-table">

				<tr id="titleRow">
					<td>Title</td>
					<td><input type="text" id="title" name="title" value="<%=theme?.title?:''%>" />
					</td>
					<td><span id="titleMessage"></span>
					</td>
				</tr>

				<tr id="descriptionRow">
					<td>Description</td>
					<td><textarea rows="5" cols="47" id="description" name="description"><%=theme?.description?:'' %></textarea></td>
					<td><span id="descriptionMessage"></span>
					</td>
				</tr>
				
				<tr>
					<td>Add Development (By Title)</td>		
					<td><input type="text" name="developmentTitle" /></td>							
					<td>Type to search</span>
				</tr>
			</table>
		</div>
		
		<div id="developments">
			<table border=0 cellspacing="0" cellpadding="5px" id="developmentsTable" class="form-list-table">
				<tr>
					<th width="70%">Development</th>
					<th width="20%">Id</th>
					<th width="10%"></th>
				</tr>

				<%  developments?.each { development -> %>
				<tr>
					
					<td width="70%">${development.title}</td>
					<td width="20%"><input type="hidden" name="developmentId" value="${development.id}" />${development.id}</td>							
					<td width="10%"><a href="javascript://" id="removeDevelopment">remove</a></td>
				</tr>
				<% } %>
			</table>
		</div>
		
	</form>
</div>


<% include '/templates/includes/footer.gtpl' %>
