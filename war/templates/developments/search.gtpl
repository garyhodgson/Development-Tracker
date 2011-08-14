<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {
		jQuery('#searchKey').focus()
		
		jQuery('#submitForm').click(function() {
			jQuery('#searchDevelopmentForm').submit()	
		})
	})
</script>
	<dir class="directory bordered right">
		
		<form action="/developments/search" method="post" id="searchDevelopmentForm">
			<a href="javascript://" id="submitForm"><li>Go</li> </a>
			<input type="text" id="searchKey" name="searchKey"/>
		</form>
		
		<br><br>
		<div class="small-note">
			Searches title, description, goal description, specification keys and values, and collaborator names.
		</div>
		<div class="small-note">
			For other fields please see the <a href="/developments/browse">browse</a> functionality. 
		</div>
		
	</dir>
	
	<div class="redirect-block bordered">
		<h1>Search</h1>
	</div>

<% include '/templates/includes/footer.gtpl' %>
