<% def userinfo = request.getAttribute('userinfo') %>

<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/md5.js"></script>

<script type="text/javascript">
	jQuery(function() {
		jQuery('table td:nth-child(1)').addClass('form-label-column')
		jQuery('table td:nth-child(2)').addClass('form-value-column')
		jQuery('table td:nth-child(3)').addClass('form-message-column')
		
		function getGravatar(){
			var email = (jQuery('#useGravatar').attr('checked') == 'checked') ? jQuery('#email').val() : 'null'
			jQuery('#gravatar').attr('src', 'http://www.gravatar.com/avatar/'+hex_md5(email.trim()) + '?s=150&d=mm')
		}
		
		jQuery('#useGravatar').change(function(){
			getGravatar()
		})
		
		jQuery('#submitForm').click(function() {
				jQuery('#updateUserinfoForm').submit();	
		})
		
		jQuery('#removeAssociation').live('click', function(){
			if (jQuery('#associationTable tbody>tr').size() > 2){
				jQuery(this).parent().parent().remove()
			} else {
				jQuery('#associationTable tbody>tr:last #associationSourceOther').val('')
				jQuery('#associationTable tbody>tr:last #associationSourceId').val('')
				jQuery('#associationTable tbody>tr:last #associationSource').val(0)
			}
		})
				
		jQuery('#addAssociation').click(function(){
			jQuery('#associationTable tbody>tr:last').clone(true).insertAfter('#associationTable tbody>tr:last');
			jQuery('#associationTable tbody>tr:last #associationSourceOther').val('')
			jQuery('#associationTable tbody>tr:last #associationSourceId').val('')
			jQuery('#associationTable tbody>tr:last #associationSource').val(0)
		})
		
		jQuery("select[name=associationSource]").change(function() {
			switch(jQuery(this).val())
			{
				case 'Other':
					jQuery(this).next("#associationSourceOther").show()
				  	break;
				default:
					jQuery(this).next("#associationSourceOther").val('')
					jQuery(this).next("#associationSourceOther").hide()
			}
		})
		
		jQuery("select[name=associationSource]").change()
	});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Edit Userinfo"%></h2>

<nav>
	<ul>
		<a href="javascript://" id="submitForm"><li>OK</li> </a>
		<a href="/userinfo/<%=userinfo.username%>"><li>Cancel</li> </a>
	</ul>
</nav>

<div class="userinfo-thumb left">
	<img id="gravatar" src="http://www.gravatar.com/avatar/${userinfo.getGravatarHash()}?s=150&d=mm" />
</div>

<div class="content thumbnailed">

	<form id="updateUserinfoForm" action="/userinfo/update" method="post">

		<fieldset>
			<legend>Your Details</legend>
			<table border=0 cellspacing="0" cellpadding="5px">
				<tr id="usernameRow">
					<td>Username</td>
					<td><input type="hidden" id="username" name="username" value="<%=userinfo?.username?:''%>"> <%=userinfo?.username?:''%></td>
					<td>Please contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> if you would like to change
						your username. <span id="usernameMessage"></span></td>
				</tr>
				<tr>
					<td>Contact Email</td>
					<td><input type="text" id="email" name="email" value="<%=userinfo?userinfo.email:user.email%>">
					</td>
					<td id="emailMessage" class="field-help"></td>
				</tr>

				<tr>
					<td>Use Gravatar?</td>
					<%def checked = (userinfo.useGravatar)? 'checked=checked':'' %>
					<td><input type="checkbox" value="true" id="useGravatar" name="useGravatar" <%=checked %> />
					</td>
					<td class="field-help">Allow the application to use <a href="http://www.gravatar.com/" target="_blank">Gravatar</a>
						for your avatar image.</td>
				</tr>
			</table>
		</fieldset>
				
		<fieldset>
			<legend>Associations</legend>
			<table border=0 cellspacing="0" cellpadding="5px" id="associationTable">
				<tr>
					<th class="linkType">Source</th>
					<th class="linkDescription">Id or URL</th>
					<th class="linkAction"></th>
				</tr>
			
				<%  if (userinfo.associations){ 
					userinfo.associations.eachWithIndex { a, i ->
				%>
						<tr>
							<td class="linkType">
								<select id="associationSource" name="associationSource">
									<% enums.Source.each { key -> 
									def selected = (a.source == key) ? 'selected=selected':'' %>
									<option value="${key}" label="${key.title}" <%=selected%> />
									<% } %>
								</select>
								<input type="text" id="associationSourceOther" name="associationSourceOther" value="${a.sourceOther?:''}" />
							</td>									
							<td>
								<input class="linkURL" type="text" id="associationSourceId" name="associationSourceId" value="${a.sourceId}" />
							</td>
							<td class="linkAction"><a href="javascript://" id="removeAssociation">remove</a></td>
						</tr>
						<% 	}
				} else {
				%>
				<tr>
					<td class="linkType">
						<select id="associationSource" name="associationSource">
							<% enums.Source.each { key -> %>
								<option value="${key}" label="${key.title}"/>
							<% } %>
						</select>
						<input type="text" id="associationSourceOther" name="associationSourceOther" />
					</td>
					<td>
						<input class="linkURL" type="text" id="associationSourceId" name="associationSourceId"/>
					</td>
					<td class="linkAction"><a href="javascript://" id="removeAssociation">remove</a></td>
				</tr>
				<% } %>
			</table>
			<input class="action" id="addAssociation" type="button" value="Add Association">
		</fieldset>
		<br />
		<div id="warnings"></div>
		<br />
	</form>

</div>

<% include '/templates/includes/footer.gtpl' %>
