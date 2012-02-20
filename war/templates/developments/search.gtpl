<% include '/templates/includes/header.gtpl' %>

<script>
	jQuery(function() {
		jQuery('#searchKey').focus()
		
		jQuery('#submitForm').click(function() {
			jQuery('#searchDevelopmentForm').submit()	
		})
	})
</script>

<div class="grid_2 redirect-block bordered">
	<h1>Search</h1>
</div>

<div class="grid_9 directory bordered">
	
	<form action="/developments/search" method="post" id="searchDevelopmentForm">
		
		<div class="grid_10">
			<input type="text" id="searchKey" name="searchKey"/>
		</div>
		<div class="grid_2">
			<a href="javascript://" id="submitForm"><li>Go</li></a>
		</div>
		
	</form>
	
	<br><br>
	<div class="small-note">
		Searches title, description, goal description, specification keys and values, and collaborator names.
	</div>
	<div class="small-note">
		For other fields please see the <a href="/developments/browse">browse</a> functionality. 
	</div>
	
</div>


<% include '/templates/includes/footer.gtpl' %>
