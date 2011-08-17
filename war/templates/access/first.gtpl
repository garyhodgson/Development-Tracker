
<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/md5.js"></script>

<script type="text/javascript">
	jQuery(function() {
		jQuery('table td:nth-child(1)').addClass('form-label-column')
		jQuery('table td:nth-child(2)').addClass('form-value-column')
		jQuery('table td:nth-child(3)').addClass('form-message-column')
	
		function validate() {
			if (usernameIsValid
					&& (jQuery('#acceptTermsOfUse').attr('checked'))){
				jQuery('#submitForm').attr('disabled', false);
				jQuery('#submitForm li').removeClass('disabled-button')
				jQuery('#submitForm').attr('title', '')
				
			} else {
				jQuery('#submitForm').attr('disabled', true);
				jQuery('#submitForm li').addClass('disabled-button')
				
				if (!usernameIsValid){
					jQuery('#submitForm').attr('title', 'Please choose another username to continue.')	
				} else {
					jQuery('#submitForm').attr('title', 'Please accept the terms of use to continue.')
				}
			}
		}
		
		function getGravatar(){
			
			var email = (jQuery('#useGravatar').attr('checked')) ? jQuery('#email').val() : 'null'
			jQuery('#gravatar').attr('src', 'http://www.gravatar.com/avatar/'+hex_md5(email.trim()) + '?s=150&d=mm')
		}
		
		var usernameIsValid = false
		
		jQuery('#addUserinfoForm')
				.submit(
						function() {

							if (!jQuery('#acceptTermsOfUse').attr('checked')) {
								jQuery('#acceptTermsOfUseMessage').html(
										"Must be accepted to continue.")
								jQuery('#acceptTermsOfUseMessage').effect(
										'highlight', null, 500, null)
								return false
							}

							if (jQuery('#username').val() == "") {
								jQuery('#usernameMessage').html(
										"Username is required.")
								jQuery('#usernameMessage').effect('highlight',
										null, 500, null)
								return false
							}

							if (usernameIsValid == false) {
								jQuery('#usernameMessage').effect('highlight',
										null, 500, null)
								return false
							}

							return true
						})

		jQuery('#acceptTermsOfUse').change(function() {
			validate()
		})

		jQuery('#username').blur(
				function() {
					if (jQuery('#username').val() == "") {
						jQuery('#usernameMessage')
								.html("Username is required.")
						jQuery('#usernameMessage').effect('highlight', null,
								500, null)
						usernameIsValid = false
						validate()
					} else {
						jQuery.get('/userinfo/exists/' + jQuery(this).val(),
								function(data) {
									if (data == "true") {
										usernameIsValid = false
										jQuery('#usernameMessage').html(
												"<span class='red bold'>Username is taken.</span>")
										validate()
									} else {
										usernameIsValid = true
										jQuery('#usernameMessage').html(
												"<span class='green bold'>Username available.</span>")
										validate()
									}
								})
					}
				})
		
		jQuery('#username').blur()

		jQuery('#useGravatar').change(function(){
			getGravatar()
		})
		
		jQuery('#submitForm').click(function() {
				jQuery('#addUserinfoForm').submit();	
		})
		
		validate()
		getGravatar()
	});
</script>

<h2 class="pageTitle">User Information</h2>

<nav>
	<ul>
		<a href="javascript://" id="submitForm"><li>Submit</li> </a>
		<a href="/access/logout"><li>Cancel</li> </a>

	</ul>
</nav>

<div class="userinfo-thumb left">
	<img id="gravatar" src="http://www.gravatar.com/avatar/null?s=150&d=mm" />
</div>

<form action="/userinfo/add" method="post" id="addUserinfoForm">

<div class="content thumbnailed">

	<p><strong>As this appears to be your first time using Development Tracker please take the time to fill in the following details.</strong></p>
	
	<% def userinfo = request.getAttribute('userinfo') %>
	
		<fieldset>
			<legend>Your Details</legend>
			<table border=0 cellspacing="0" cellpadding="5px">

				<tr>
					<td>Username</td>
					<td><input type="text" id="username" name="username"
						value="<%=userinfo?userinfo.username:user.nickname.split('@')[0]%>"
					></td>
					<td id="usernameMessage"></td>
				</tr>
				<tr>
					<td>Contact Email</td>
					<td><input type="text" id="email" name="email" value="<%=userinfo?userinfo.email:user.email%>"></td>
					<td id="emailMessage" class="field-help"></td>
				</tr>

				<tr>
					<td>Use Gravatar?</td>
					<%def checked = (userinfo?userinfo.useGravatar:"true" == "true")? 'checked=checked':'' %>
					<td><input type="checkbox" value="true" id="useGravatar" name="useGravatar" <%=checked %> /></td>
					<td class="field-help">Allow the application to use <a href="http://www.gravatar.com/" target="_blank">Gravatar</a>
						for your avatar image.</td>
				</tr>
			</table>
		</fieldset>

		<br />
		<div id="warnings"></div>
		
	
</div>

<br clear="both">

<fieldset>
	<legend>Terms of Use</legend>
	
	<div style="border: 1px solid gray;overflow-y:scroll;height:200px" >
	<% include '/templates/includes/termsOfUse.gtpl' %>
	</div>
	<p><input type="checkbox" value="true" id="acceptTermsOfUse" name="acceptTermsOfUse"
				<%=userinfo?.acceptTermsOfUse=='true'?'checked=checked':''%>> &nbsp;Accept Terms of Use</p>
				<p id="acceptTermsOfUseMessage"></p>
</fieldset>

</form>

<% include '/templates/includes/htmlFooter.gtpl' %>