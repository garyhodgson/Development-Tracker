<%
	import app.AppProperties
	import enums.*
	def development = request?.getAttribute('development')  
	def relationships = request?.getAttribute('relationships')
	def collaborations = request?.getAttribute('collaborations')
%>

<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/smartwizard/SmartWizard.js"></script>
<script type="text/javascript" src="/js/plupload/plupload.full.js"></script>
<script>
	jQuery(function() {

		var titleIsValid = false
		var originalTitle = "${development?.title?:''}"
		var	originalSourceURL = "${development?.sourceURL?:''}"
		var isEditing = <%println (request.getAttribute('action')=="/development/update")? 'true': 'false'%>
		var autocompleteOptions = {
			minLength: 2,
			source: '/developments/autocomplete',
			select: function( event, ui ) {
				if (ui.item.id){
					jQuery(event.target.parentElement).next().find('#relationshipTo').val(ui.item.id)	
				}
			}
		}
	
		function validate() {
			if (titleIsValid){
				jQuery('#submitForm').attr('disabled', false);
				jQuery('#submitForm li').removeClass('disabled-button')
				jQuery('#submitForm').attr('title', '')
			} else {
				jQuery('#submitForm').attr('disabled', true);
				jQuery('#submitForm li').addClass('disabled-button')
				
				if (!titleIsValid){
					jQuery('#submitForm').attr('title', 'Please choose another title to continue.')	
				}
			}
		}
		
		jQuery("input[name=status]").change(function() {
			
			if (jQuery("input[name='status']:checked").val() == 'Other') {
				jQuery("#statusOtherRow").show()
			} else {
				jQuery("#statusOtherRow").hide()
				jQuery("#statusOther").val('')
			}
			if (jQuery("input[name='status']:checked").val() == 'WorkInProgress') {
				jQuery("#signs_WorkInProgress").attr('checked', 'checked')
			} else {
				jQuery("#signs_WorkInProgress").attr('checked', false)
			} 
			if (jQuery("input[name='status']:checked").val() == 'Abandoned') {
				jQuery("#signs_Abandoned").attr('checked', 'checked')
			} else {
				jQuery("#signs_Abandoned").attr('checked', false)
			} 
			if (jQuery("input[name='status']:checked").val() == 'Obsolete') {
				jQuery("#signs_Obsolete").attr('checked', 'checked')
			} else {
				jQuery("#signs_Obsolete").attr('checked', false)
			} 
			if (jQuery("input[name='status']:checked").val() == 'Experimental') {
				jQuery("#signs_Experimental").attr('checked', 'checked')
			} else {
				jQuery("#signs_Experimental").attr('checked', false)
			}
		})
		
		jQuery("#license").change(function() {
			if (jQuery(this).val() == "Other") {
				jQuery("#licenseOtherRow").show()
			} else {
				jQuery("#licenseOtherRow").hide()
				jQuery("#licenseOther").val('')
			}
		})
		
		jQuery("input[name=developmentType]").change(function() {
			if (jQuery("input[name='developmentType']:checked").val() == 'Other') {
				jQuery("#developmentTypeOtherRow").show()
			} else {
				jQuery("#developmentTypeOtherRow").hide()
				jQuery("#developmentTypeOther").val('')
			}
		})
				
		jQuery("#goals_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#goalsOtherRow").show()
			} else {
				jQuery("#goalsOtherRow").hide()
				jQuery("#goalsOther").val('')
			}
		})
		
		jQuery("#projectVendor_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#projectVendorOtherRow").show()
			} else {
				jQuery("#projectVendorOtherRow").hide()
				jQuery("#projectVendorOther").val('')
			}
		})
		
		jQuery("select[name=collaboratorRole]").change(function() {			
			switch(jQuery(this).val())
			{
				case 'Other':
					jQuery(this).next("#collaboratorRoleOther").show()
				  	break;
				default:
					jQuery(this).next("#collaboratorRoleOther").val('')
					jQuery(this).next("#collaboratorRoleOther").hide()
			}
		})
		
		jQuery('#submitForm').click(function() {
			jQuery('#addDevelopmentForm').submit();	
		})
		
		jQuery('#cancelAdd').click(function(){
			if (jQuery('#imageBlobKey').val() != ''){
				window.location = "/development/add/cancel/"+jQuery('#imageBlobKey').val()	
			} else {
				window.location = "/developments/latest"
			}
		})

		jQuery('#smartwizard').smartWizard({
			enableAll : true
		})
		
		jQuery('#removeRelationship').live('click', function(){
			if (jQuery('#relationshipTable tbody>tr').size() > 2){
				jQuery(this).parent().parent().remove()
			} else {
				jQuery('#relationshipTable tbody>tr:last #relationshipTo').val('')
				jQuery('#relationshipTable tbody>tr:last #relationshipId').val('')
				jQuery('#relationshipTable tbody>tr:last #relationshipDescription').val('')
			}			
		})
				
		jQuery('#addRelationship').click(function(){
			jQuery('#relationshipTable tbody>tr:last').clone().insertAfter('#relationshipTable tbody>tr:last');
			jQuery('#relationshipTable tbody>tr:last #relationshipTo').val('')
			jQuery('#relationshipTable tbody>tr:last #relationshipDescription').val('')
			jQuery('#relationshipTable tbody>tr:last #relationshipDescription').autocomplete(autocompleteOptions)
			jQuery('#relationshipTable tbody>tr:last #relationshipId').val('')
		
		})
		
		jQuery('#removeCollaborator').live('click', function(){
			if (jQuery('#collaboratorTable tbody>tr').size() > 2){
				jQuery(this).parent().parent().remove()
				
				jQuery('#collaboratorTable tbody>tr #collaboratorIsUsername').each(function(i){
					jQuery(this).attr('value', i)
				})
				jQuery('#collaboratorTable tbody>tr #collaboratorMayEdit').each(function(i){
					jQuery(this).attr('value', i)
				})
				
			} else {
				jQuery('#collaboratorTable tbody>tr:last #collaboratorName').val('')
				jQuery('#collaboratorTable tbody>tr:last #collaboratorId').val('')
				jQuery('#collaboratorTable tbody>tr:last #collaboratorRoleOther').val('')
				jQuery('#collaboratorTable tbody>tr:last #collaboratorIsUsername').attr('checked', '')
				jQuery('#collaboratorTable tbody>tr:last #collaboratorMayEdit').attr('checked', '').attr('disabled', true)
			}
		})
				
		jQuery('#addCollaborator').click(function(){
			jQuery('#collaboratorTable tbody>tr:last').clone().insertAfter('#collaboratorTable tbody>tr:last');
			jQuery('#collaboratorTable tbody>tr:last #collaboratorName').val('')
			jQuery('#collaboratorTable tbody>tr:last #collaboratorId').val('')
			jQuery('#collaboratorTable tbody>tr:last #collaboratorRoleOther').val('')
			
			var index = jQuery('#collaboratorTable tbody>tr').size() - 2;
			jQuery('#collaboratorTable tbody>tr:last #collaboratorIsUsername').attr('checked', false).attr('value', index)
			jQuery('#collaboratorTable tbody>tr:last #collaboratorMayEdit').attr('checked', false).attr('value', index).attr('disabled', true)			
		})
		
		jQuery('#removeSpecification').live('click', function(){
			if (jQuery('#specificationTable tbody>tr').size() > 2){
				jQuery(this).parent().parent().remove()
			} else {
				jQuery('#specificationTable tbody>tr:last #specificationName').val('')
				jQuery('#specificationTable tbody>tr:last #specificationValue').val('')
			}			
		})
				
		jQuery('#addSpecification').click(function(){
			jQuery('#specificationTable tbody>tr:last').clone().insertAfter('#specificationTable tbody>tr:last');
			jQuery('#specificationTable tbody>tr:last #specificationName').val('')
			jQuery('#specificationTable tbody>tr:last #specificationValue').val('')
		})
		
		jQuery('input[name=collaboratorIsUsername]').live('change', function(){
			var associatedMayEdit = jQuery(this).parent().next().children('input[name=collaboratorMayEdit]')
			if (!jQuery(this).attr('checked')){
				associatedMayEdit.attr('checked', '').attr('disabled', true)
			} else {
				associatedMayEdit.attr('disabled', false)
			}
		})
		
		jQuery('input[name=title]').blur(function() {
			if (jQuery(this).val() == "") {
				jQuery('#titleMessage')
						.text("Title is required.")
				titleIsValid = false
				validate()
			} else {
				
				var currentTitle = jQuery(this).val().replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;");
				if (isEditing && (currentTitle == originalTitle)){
					titleIsValid = true
					jQuery('#titleMessage').html("")
					validate()
				} else {
				
					jQuery.get('/development/exists/title/' + encodeURIComponent(jQuery(this).val()),
						function(data) {
							if (data == "") {
								titleIsValid = true
								jQuery('#titleMessage').html("")
								validate()
							} else {
								titleIsValid = false
								jQuery('#titleMessage').html("<span class='red'>A development with this name already exists. (<a href='/development/"+data+"' title='Open in new page' target='_blank'>view</a>). Please check if this will be a duplicate or please choose another title.</span>")
								validate()
							}
						})
				}
			}
		})
		
		jQuery('input[name=sourceURL]').blur(function() {

			if (jQuery(this).val() != ""){
				if (isEditing && (jQuery(this).val() != originalSourceURL)){
				} else {
					jQuery.get('/development/exists/sourceURL/' + encodeURIComponent(jQuery(this).val()),
						function(data) {
							if (data == "") {
								jQuery('#sourceURLMessage').html("")
							} else {								
								jQuery('#sourceURLMessage').html("<span class='red'>A development with this URL already exists. (<a href='/development/"+data+"' title='Open in new page' target='_blank'>view</a>). Please check if this will a duplicate.</span>")
							}
						})
				}
			}
		})
		
		jQuery("#license").change();
		jQuery("#projectVendor_Other").change()
		jQuery("#goals_Other").change()		
		jQuery("select[name=collaboratorRole]").change()
		jQuery('input[name=title]').blur()
		jQuery('input[name=status]').change()
		jQuery('input[name=developmentType]').change()
		jQuery('input[name=collaboratorIsUsername]').change()
		
		
		jQuery('#fileUploadContainer').hide();
		String.prototype.startsWith = function (a) {
		    return this.substr(0, a.length) === a;
		}
		jQuery('#imageBlobKey').blur(function(){
			if (jQuery('#imageURL').val().startsWith('Uploaded File:')){
				jQuery('#imageURL').attr("readonly", true);
				jQuery('#imageURL').addClass("readonly-field");				
			} else {
				jQuery('#imageURL').attr("readonly", false);
				jQuery('#imageURL').removeClass("readonly-field");
			}
		});
		jQuery('#imageBlobKey').blur();
		
		var uploader = new plupload.Uploader({
			runtimes : 'html5,flash,html4',
			browse_button : 'pickFile',
			container : 'fileUploadContainer',
			max_file_size : '<%=AppProperties.THUMBNAIL_MAXSIZE?:5%>mb',
			url : "<%=blobstore.createUploadUrl('/development/fileUpload.groovy')%>",
			flash_swf_url : '/js/plupload/plupload.flash.swf',
			filters : [
				{title : "Image files", extensions : "jpg,gif,png"},
			]
		});
		
		uploader.bind('Init', function(up, params) {
			jQuery('#fileUploadContainer').show();
		});

		jQuery('#clearFile').click(function(e) {
			jQuery('#imageURL').val("");
			jQuery('#imageBlobKey').val("");
			jQuery('#imageBlobKey').blur();
		});

		uploader.init();

		uploader.bind('FilesAdded', function(up, files) {
			jQuery.each(files, function(i, file) {
				jQuery('#imageURL').val('Uploaded File: '+file.name);
				jQuery('#imageBlobKey').blur();
			});
			up.refresh(); // Reposition Flash/Silverlight
			uploader.start();
		});

		uploader.bind('Error', function(up, err) {
			if (err.code = plupload.FILE_SIZE_ERROR){
				alert("File is too large. Limit is <%=AppProperties.THUMBNAIL_MAXSIZE%>Mb");
			} else {
				alert(err.message);
			}
				
			up.refresh(); // Reposition Flash/Silverlight
		});

		uploader.bind('FileUploaded', function(up, file, info) {		
			jQuery('#imageBlobKey').val(info.response);
		});

		jQuery("input[name=relationshipDescription]").autocomplete(autocompleteOptions);
	});
</script>

<h2 class="pageTitle"><%=request.getAttribute('pageTitle')?:'Add Development'%></h2>

<nav>
	<ul>
		<a href="javascript://" id="submitForm"><li>Save</li> </a>
		<% if (development?.id){ %>
			<a href="/development/${development?.id}"><li>Cancel</li> </a>
		<% } else { %>
			<a href="javascript://" id="cancelAdd"><li>Cancel</li> </a>
		<% } %>
		<a href="/help/development/edit"><li>Help</li> </a>
	</ul>
</nav>

<div class="content">

	<form action="<%=request.getAttribute('action')?:'/development/add'%>" method="post" id="addDevelopmentForm">

		<div id="smartwizard" class="wiz-container">

			<ul id="wizard-anchor">
				<li><a href="#wizard-1"><h2>Describe</h2> </a>
				</li>
				<li><a href="#wizard-2"><h2>Categorise</h2> </a>
				</li>
				<li><a href="#wizard-3"><h2>Connect</h2> </a>
				</li>
				<li><a href="#wizard-4"><h2>Collaborate</h2> </a>
				</li>
				<li><a href="#wizard-5"><h2>Specify</h2> </a>
				</li>
				<li><a href="#wizard-6"><h2>Warn</h2> </a>
				</li>
			</ul>

			<div id="wizard-body" class="wiz-body">

				<div id="wizard-1" class="wiz-content">
					<input type="hidden" id="id" name="id" value="<%=development?.id %>"> 
					<input type="hidden" id="referer" name="referer" value="<%=headers.Referer?:''%>">

					<table border=0 cellspacing="0" cellpadding="5px" class="form-table">
					
						<tr id="titleRow">
							<td>Title</td>
							<td><input type="text" id="title" name="title" value="<%=development?.title?:''%>" /></td>
							<td><span id="titleMessage"></span></td>
						</tr>


						<tr id="descriptionRow">
							<td>Description</td>
							<td><textarea rows="5" cols="47" id="description" name="description"><%=development?.description?:'' %></textarea>
							</td>
							<td><a href="http://daringfireball.net/projects/markdown/syntax" target="_blank">Markdown</a> is accepted.<span id="descriptionMessage"></span></td>
						</tr>

						<tr id="sourceRow">
							<td>Source</td>
							<td><select id="source" name="source">
								<% enums.Source.each { key ->
										def selected = (development?.source && development?.source == key) ? 'selected=selected':''
									%>
									<option ${selected} value="${key}">${key.title}</option>
									<% } %>
							</select></td>
							<td><span id="sourceMessage"></span></td>
						</tr>

						<tr id="sourceURLRow">
							<td>URL</td>
							<td><input type="text" id="sourceURL" name="sourceURL" value="<%=development?.sourceURL?:''%>" /></td>
							<td><span id="sourceURLMessage"></span></td>
						</tr>

						<tr id="statusRow">
							<td>Status</td>
							<td>
								<div class="left">
									<%
										def count = Status.values().length -1
																	def c1 = (count)/2 as int
																	Status.values()[0..c1].each { key ->
																	def checked = (development?.status && development?.status == key) ?  'checked=checked':''
									%>
									<input type="radio" value="${key}" id="status_${key}" name="status" <%=checked%> />&nbsp;${key.title}
									<br>
									<%  } %>
								</div>
								<div class="left">					
									<% Status.values()[c1+1..count].each { key ->
										def checked = (development?.status && development?.status == key) ?  'checked=checked':'' %>
									<input type="radio" value="${key}" id="status_${key}" name="status" <%=checked%> />&nbsp;${key.title}
									<br>
									<% } %>
								</div>
							</td>
							<td><span id="statusMessage"></span></td>
						</tr>

						<tr id="statusOtherRow">
							<td>Other Status</td>
							<td><input type="text" id="statusOther" name="statusOther" value="<%=development?.statusOther?:''%>" /></td>
							<td><span id="statusOtherMessage"></span></td>
						</tr>

						<tr id="imageURLRow">
							<td>Image URL</td>
							<td>
								<input type="text" id="imageURL" name="imageURL" value="<%=development?.imageURL?:''%>" />
								<div id="fileUploadContainer">
									or&hellip;&nbsp;<a class="nohint" id="pickFile" href="#">[Upload File]</a>
									&nbsp;&nbsp;<a class="nohint" id="clearFile" href="#">[Clear]</a>
								</div>
								<input type="hidden" id="imageBlobKey" name="imageBlobKey" value="<%=development?.imageBlobKey?:''%>" />
							</td>
							<td><span id="imageURLMessage"></span></td>
						</tr>
												
						<tr id="licenseRow">
							<td>License</td>
							<td><select  id="license" name="license">
									<% enums.License.each { 
										def selected = (development?.license && development?.license == it) ? 'selected=selected':''
									%>
									<option ${selected} value="${it}" title="${it.description?:it.title}">${it.title}</option>
									<% } %>
							</select></td>
							<td><span id="licenseMessage"></span></td>
						</tr>

						<tr id="licenseOtherRow">
							<td>Other License</td>
							<td><input type="text" id="licenseOther" name="licenseOther" value="<%=development?.licenseOther?:''%>" /></td>
							<td><span id="licenseOtherMessage"></span></td>
						</tr>
					</table>
				</div>

				<div id="wizard-2" class="wiz-content">
					<table border=0 cellspacing="0" cellpadding="5px" class="form-table">

						<tr id="developmentTypeRow">
							<td>Development Type</td>
							<td>
								<div class="left">
									<%  
										count = DevelopmentType.values().length -1
										c1 = (count)/2 as int
										DevelopmentType.values()[0..c1].each { key ->
										def selected = (development?.developmentType && development?.developmentType == key) ? 'checked=checked':'' %>
									<input type="radio" value="${key}" id="developmentType_${key}" name="developmentType" <%=selected%> />&nbsp;${key.title}
									<br>
									<%  } %>
								</div>
								<div class="left">					
									<% DevelopmentType.values()[c1+1..count].each { key ->
										def selected = (development?.developmentType && development?.developmentType == key) ? 'checked=checked':'' %>
									<input type="radio" value="${key}" id="developmentType_${key}" name="developmentType" <%=selected%> />&nbsp;${key.title}
									<br>
									<% } %>
								</div>
							</td>
							<td><span id="developmentTypeMessage"></span>
							</td>
						</tr>

						<tr id="developmentTypeOtherRow">
							<td>Other Development Type</td>
							<td><input type="text" id="developmentTypeOther" name="developmentTypeOther"
								value="<%=development?.developmentTypeOther?:''%>"
							/></td>
							<td id="message"></td>
						</tr>

						<tr id="projectVendorRow">
							<td>Project/Vendor</td>
							<td>
								<div class="left">
									<%  
										count = ProjectVendor.values().length -1
										c1 = (count)/2 as int
										def projVendors = development?.projectVendor?:[]
										ProjectVendor.values()[0..c1].each { key ->
										def checked = projVendors.contains(key) ? 'checked=checked':'' %>
									<input type="checkbox" title="${key.description?:''}" value="${key}" id="projectVendor_${key}" name="projectVendor" <%=checked%>/>&nbsp;${key.title}
									<br>
									<%  } %>
								</div>
								<div class="left">					
									<% ProjectVendor.values()[c1+1..count].each { key ->
									def checked = projVendors.contains(key) ? 'checked=checked':''  %>
									<input type="checkbox" title="${key.description?:''}" value="${key}" id="projectVendor_${key}" name="projectVendor" <%=checked%>/>&nbsp;${key.title}
									<br>
									<% } %>
								</div>
							</td>
							<td><span id="projectVendorMessage"></span></td>
						</tr>

						<tr id="projectVendorOtherRow">
							<td>Other Project/Vendor</td>
							<td><input type="text" id="projectVendorOther" name="projectVendorOther"
								value="<%=development?.projectVendorOther?:''%>"
							/></td>
							<td><span id="projectVendorOtherMessage"></span></td>
						</tr>

						<tr id="categoriesRow">
							<td>Category</td>
							<td>
								<div class="left">
									<%  count = Category.values().length -1
										c1 = (count)/3 as int
										c2 = (count)/3 * 2 as int
										def devCats = development?.categories?:[]
										Category.values()[0..c1].each { key -> %>
									<input type="checkbox" value="${key}" name="categories" <%=devCats.contains(key)? 'checked=checked':''%>/>&nbsp;${key.title}
									<br>
									<%  } %>
								</div>
								<div class="left">					
									<% Category.values()[c1+1..c2].each { key -> %>
									<input type="checkbox" value="${key}" name="categories" <%=devCats.contains(key)? 'checked=checked':''%>/>&nbsp;${key.title}
									<br>
									<% } %>
								</div>
								<div class="left">					
									<% Category.values()[c2+1..count].each { key -> %>
									<input type="checkbox" value="${key}" name="categories" <%=devCats.contains(key)? 'checked=checked':''%>/>&nbsp;${key.title}
									<br>
									<% } %>
								</div>
								
							</td>
							<td>Category missing?<br>Use Tags, or contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a><span
								id="categoriesMessage"
							></span>
							</td>
						</tr>

						<tr id="tagsRow">
							<td>Tags</td>
							<td><input type="text" id="tags" name="tags"
								value="<%=(development?.tags)?development?.tags.join(', '):''%>"
							/></td>
							<td>Comma separated list of tags <span id="message"></span>
							</td>
						</tr>


						<tr id="goalsRow">
							<td>Goals</td>
							<td>
								<div class="left">
									<% count = Goal.values().length -1
										c1 = (count)/3 as int
										c2 = (count)/3 * 2 as int
										def devGoals = development?.goals?:[]
										Goal.values()[0..c1].each { key -> %>
									<input type="checkbox" title="${key.description?:key.title}" value="${key}" id="goals_${key}" name="goals" <%=devGoals.contains(key)? 'checked=checked':''%>/>&nbsp;${key}
									<br>
									<%  } %>
								</div>
								<div class="left">					
									<% Goal.values()[c1+1..c2].each { key ->%>
									<input type="checkbox" title="${key.description?:key.title}" value="${key}" id="goals_${key}" name="goals" <%=devGoals.contains(key)? 'checked=checked':''%>/>&nbsp;${key}
									<br>
									<% } %>
								</div>
								<div class="left">					
									<% Goal.values()[c2+1..count].each { key ->%>
									<input type="checkbox" title="${key.description?:key.title}" value="${key}" id="goals_${key}" name="goals" <%=devGoals.contains(key)? 'checked=checked':''%>/>&nbsp;${key}
									<br>
									<% } %>
								</div>
							</td>
							<td><span id="goalsMessage"></span></td>
						</tr>

						<tr id="goalsOtherRow">
							<td>Other Goal</td>
							<td><input type="text" id="goalsOther" name="goalsOther" value="<%=development?.goalsOther?:''%>" /></td>
							<td><span id="goalsOtherMessage"></span></td>
						</tr>


						<tr id="goalsDescriptionRow">
							<td>Goals Description</td>
							<td><textarea rows="3" cols="47" id="goalsDescription" name="goalsDescription"><%=development?.goalsDescription?:'' %></textarea>
							</td>
							<td><span id="goalsDescriptionMessage"></span></td>
						</tr>

					</table>
				</div>

				<div id="wizard-3" class="wiz-content">
					<table border=0 cellspacing="0" cellpadding="5px" id="relationshipTable" class="form-list-table">
						<tr>
							<th class="linkType">Type</th>
							<th class="linkDescription">Description</th>
							<th class="linkURL">URL or Id</th>
							<th class="linkAction"></th>
						</tr>
						<%  if (relationships){ 
								relationships.each { r ->
							%>
						<tr>
							<input type="hidden" id="relationshipId" name="relationshipId" value="${r.id}" />
							<td class="linkType"><select name="relationshipType">
									<% enums.RelationshipType.each { key -> 
									def selected = (r.type == key) ? 'selected=selected':'' %>
									<option value="${key}" <%=selected%> >${key.title}</option>
									<% } %>
							</select></td>
							<td class="linkDescription"><input type="text" id="relationshipDescription" name="relationshipDescription" value="${r.description}"/></td>
							
							<td>
							<% if (r.to) { %>
								<input class="linkURL" type="text" id="relationshipTo" name="relationshipTo" value="${r.to.name}" />
							<%} else { %>
								<input class="linkURL" type="text" id="relationshipTo" name="relationshipTo" value="${r.toUrl?:''}" />
							<% } %>
							</td>							
							
							<td class="linkAction"><a href="javascript://" id="removeRelationship">remove</a></td>
						</tr>
						<% 	}
								} else {
						%>
						<tr>
							<input type="hidden" id="relationshipId" name="relationshipId" />
							<td class="linkType"><select name="relationshipType">
									<% enums.RelationshipType.each { key -> %>									
									<option value="${key}">${key.title}</option>									
									<% } %>
							</select>
							</td>
							<td class="linkDescription"><input type="text" id="relationshipDescription" name="relationshipDescription" />
							</td>
							<td class="linkURL"><input type="text" id="relationshipTo" name="relationshipTo" /></td>
							<td class="linkAction"><a href="javascript://" id="removeRelationship">remove</a></td>
						</tr>
						<% } 
						%>
					</table>

					<input class="action" id="addRelationship" type="button" value="Add Connection">
				</div>

				<div id="wizard-4" class="wiz-content">
				
					<table border=0 cellspacing="0" cellpadding="5px" id="collaboratorTable" class="form-list-table">
						<tr>
							<th class="linkType">Role</th>
							<th class="linkDescription">Name</th>
							<th class="linkAction">Username?</th>
							<th class="linkAction">Editor?</th>
							<th class="linkAction"></th>
						</tr>

						<%  if (collaborations){ 
							collaborations.eachWithIndex { c, i ->
							%>
						<tr>
							<input type="hidden" id="collaboratorId" name="collaboratorId" value="${c.id}" />
							<td class="linkType">
								<select id="collaboratorRole" name="collaboratorRole">
									<% enums.Role.each { key -> 
									def selected = (c.role == key) ? 'selected=selected':'' %>
									<option value="${key}" <%=selected%> >${key.title}</option>
									<% } %>
								</select>
								<input type="text" id="collaboratorRoleOther" name="collaboratorRoleOther" value="${c.otherRole?:''}" />
							</td>
							
							<td>
								<input class="linkURL" type="text" id="collaboratorName" name="collaboratorName" value="${c.name}" />
							</td>							
							
								
							<td class="linkAction">
								<input type="checkbox" title="Is the name a Development-Tracker username?" value="${i}" id="collaboratorIsUsername" name="collaboratorIsUsername" <%=c.isUsername? 'checked=checked':''%>/>
							</td>
							<td class="linkAction">
								<input type="checkbox" title="May this user edit the development?" value="${i}" id="collaboratorMayEdit" name="collaboratorMayEdit" <%=c.mayEdit? 'checked=checked':''%>/>
							</td>
							
							<td class="linkAction"><a href="javascript://" id="removeCollaborator">remove</a></td>
						</tr>
						<% 	}
								} else {
						%>
						<tr>
							<input type="hidden" id="collaboratorId" name="collaboratorId" />
							<td class="linkType"><select id="collaboratorRole" name="collaboratorRole">
									<% enums.Role.each { key -> %>
									<option value="${key}">${key.title}</option>
									<% } %>
							</select>
							<input type="text" id="collaboratorRoleOther" name="collaboratorRoleOther" />
							</td>
							</td>
							<td class="linkDescription"><input type="text" id="collaboratorName" name="collaboratorName"/>
							</td>
							
							<td class="linkAction"><input type="checkbox" title="Is the name a Development-Tracker username?" value="0" id="collaboratorIsUsername" name="collaboratorIsUsername" /></td>
							<td class="linkAction"><input type="checkbox" title="May this user edit the development?" value="0" id="collaboratorMayEdit" name="collaboratorMayEdit" /></td>
							<td class="linkAction"><a href="javascript://" id="removeCollaborator">remove</a></td>
						</tr>
						<% } 
						%>
					</table>

					<input class="action" id="addCollaborator" type="button" value="Add Collaborator">
				</div>

				<div id="wizard-5" class="wiz-content">
					<table border=0 cellspacing="0" cellpadding="5px" id="specificationTable" class="form-list-table">
						<tr>
							<th width="20%">Name</th>
							<th width="70%">Value</th>
							<th width="10%"></th>
						</tr>

						<%  if (development?.specificationName){ 
								development?.specificationName.eachWithIndex { name, i ->
								def specificationValue = (development?.specificationValue) ? development?.specificationValue[i] : ''
						%>
						<tr>
							<td width="20%">
								<input type="text" id="specificationName" name="specificationName" value="${name}" />
							</td>
							<td width="70%">
								<input type="text" id="specificationValue" name="specificationValue" value="${specificationValue}" />
							</td>							
							<td width="10%"><a href="javascript://" id="removeSpecification">remove</a></td>
						</tr>
						<% 	}
								} else {
						%>
						<tr>							
							<td width="20%">
								<input type="text" id="specificationName" name="specificationName"/>
							</td>
							<td width="70%">
								<input type="text" id="specificationValue" name="specificationValue"/>
							</td>	
							<td width="10%"><a href="javascript://" id="removeSpecification">remove</a></td>
						</tr>
						<% } 
						%>
					</table>
					<input class="action" id="addSpecification" type="button" value="Add New Line">
					<br>
					<br>
					
					<table border=0 cellspacing="0" cellpadding="5px" class="form-table">
						<tr id="specificationUnitRow">
							<td>Specification Units</td>
							<td><select id="specificationUnit" name="specificationUnit">
									<% enums.SpecificationUnit.each { 
										def selected = (development?.specificationUnit && development?.specificationUnit == it) ? 'selected=selected':''
									%>
									<option ${selected} value="${it}">${it.title}</option>
									<% } %>
							</select></td>
							<td><span id="specificationUnitMessage"></span></td>
						</tr>
					</table>
				</div>
				
				<div id="wizard-6" class="wiz-content">
				
					<table border=0 cellspacing="0" cellpadding="5px" class="form-table">
						<tr id="noticeRow">
							<td>Notice</td>
							<td><textarea rows="4" cols="47" id="notice" name="notice"><%=development?.notice?:''%></textarea></td>
							<td><span id="noticeMessage"></span></td>
						</tr>
					</table>
					<br>
					<div class="" style="width:100%;text-align:left">
						<%  Sign.each { key -> %>
						<div class="sign-select left">
							<img src="/images/signs/50/${key.image}" title="${key.title}"/><br>
							<input type="checkbox" value="${key}" id="signs_${key}" name="signs" <%=development?.signs?.contains(key)? 'checked=checked':''%>/>
							
						</div>
						<%  } %>
					</div>
								
				</div>
			</div>
		</div>
	</form>
</div>


<% include '/templates/includes/footer.gtpl' %>
