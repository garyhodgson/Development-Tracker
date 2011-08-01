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
					<td>Please contact <a href=\"mailto:support@development-tracker.info\">support</a> if you would like to change
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
			<legend>Contact</legend>
			<table border=0 cellspacing="0" cellpadding="5px">

				<tr>
					<td>Contact me by email when...</td>
					<td><input type="checkbox" id="contactOnDevelopmentComment" value="true" name="contactOnDevelopmentComment"
						<%=userinfo?.contactOnDevelopmentComment?'checked="checked"':''%>
					>&nbsp;someone comments on my development.<br> <input type="checkbox" id="contactOnDevelopmentWatch"
						value="true" name="contactOnDevelopmentWatch" <%=userinfo?.contactOnDevelopmentWatch?'checked="checked"':''%>
					>&nbsp;starts watching my development.<br>
					</td>
					<td id="contactByEmailMessage" class="field-help">Can Developer Tracker contact you by email when one of these
						activities occurs?</td>
				</tr>

			</table>
		</fieldset>
		
		<fieldset>
			<legend>Other Places</legend>
			<table border=0 cellspacing="0" cellpadding="5px">

				<tr>
					<td>Github Id</td>
					<td><input type="text" id="githubId" name="githubId" value="<%=userinfo.githubId?:''%>"></td>
					<td><input type="checkbox" id="githubIdVisible" name="githubIdVisible" value="true" <%=userinfo.githubIdVisible?'checked=checked':''%>>&nbsp;visible to others?</td>
				</tr>
				
				<tr>
					<td>Thingiverse Username</td>
					<td><input type="text" id="thingiverseId" name="thingiverseId" value="<%=userinfo.thingiverseId?:''%>"></td>
					<td><input type="checkbox" id="thingiverseIdVisible" name="thingiverseIdVisible" value="true" <%=userinfo.thingiverseIdVisible?'checked=checked':''%>>&nbsp;visible to others?</td>
				</tr>
				
				<tr>
					<td>Reprap Wiki Id</td>
					<td><input type="text" id="reprapWikiId" name="reprapWikiId" value="<%=userinfo.reprapWikiId?:''%>"></td>
					<td><input type="checkbox" id="reprapWikiIdVisible" name="reprapWikiIdVisible" value="true" <%=userinfo.reprapWikiIdVisible?'checked=checked':''%>>&nbsp;visible to others?</td>
				</tr>			

			</table>
		</fieldset>
		<br />
		<div id="warnings"></div>
		<br />
	</form>

</div>

<% include '/templates/includes/footer.gtpl' %>
