<%
	import org.apache.commons.lang.StringEscapeUtils
	def development = request?.getAttribute('development')  
	def relationships = request?.getAttribute('relationships')
	def collaborations = request?.getAttribute('collaborations')
%>

<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/smartwizard/SmartWizard.js"></script>

<script>
	jQuery(function() {

		var titleIsValid = false
		var originalTitle = '${development?.title?:''}'
		var isEditing = <%println (request.getAttribute('action')=="/development/update")? 'true': 'false'%>
		
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
		
		jQuery("#status").change(function() {
			if (jQuery(this).val() == 'other') {
				jQuery("#statusOtherRow").show()
			} else {
				jQuery("#statusOtherRow").hide()
			}
		})
		
		jQuery("#license").change(function() {
			if (jQuery(this).val() == "Other") {
				jQuery("#licenseOtherRow").show()
			} else {
				jQuery("#licenseOtherRow").hide()
			}
		})

		jQuery("#source").change(function() {
			switch(jQuery(this).val())
			{
				case 'reprapwiki':
					jQuery("#sourceURLMessage").text('e.g. http://reprap.org/wiki/Prusa_Mendel')
				  	break;
				case 'github':
					jQuery("#sourceURLMessage").text('e.g. https://github.com/amsler/skeinforge')
				  break;
				case 'thingiverse':
					jQuery("#sourceURLMessage").text('e.g. http://www.thingiverse.com/thing:6713')
				  break;
				case 'blog':
					jQuery("#sourceURLMessage").text('e.g. http://garyhodgson.com/reprap/prusa-mendel-visual-instructions')
				  break;
				default:
					jQuery("#sourceURLMessage").text('Enter the URL of the main page containing the development.')
			}
		})
		
		jQuery("#developmentType_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#developmentTypeOtherRow").show()
			} else {
				jQuery("#developmentTypeOtherRow").hide()
			}
		})
		
		jQuery("#status_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#statusOtherRow").show()
			} else {
				jQuery("#statusOtherRow").hide()
			}
		})
		
		jQuery("#goals_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#goalsOtherRow").show()
			} else {
				jQuery("#goalsOtherRow").hide()
			}
		})
		
		jQuery("#projectVendor_Other").change(function() {
			if (jQuery(this).attr('checked')) {
				jQuery("#projectVendorOtherRow").show()
			} else {
				jQuery("#projectVendorOtherRow").hide()
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
			jQuery('#relationshipTable tbody>tr:last').clone(true).insertAfter('#relationshipTable tbody>tr:last');
			jQuery('#relationshipTable tbody>tr:last #relationshipTo').val('')
			jQuery('#relationshipTable tbody>tr:last #relationshipDescription').val('')
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
			jQuery('#collaboratorTable tbody>tr:last').clone(true).insertAfter('#collaboratorTable tbody>tr:last');
			jQuery('#collaboratorTable tbody>tr:last #collaboratorName').val('')
			jQuery('#collaboratorTable tbody>tr:last #collaboratorId').val('')
			jQuery('#collaboratorTable tbody>tr:last #collaboratorRoleOther').val('')
			
			var index = jQuery('#collaboratorTable tbody>tr').size() - 2;
			jQuery('#collaboratorTable tbody>tr:last #collaboratorIsUsername').attr('checked', '').attr('value', index)
			jQuery('#collaboratorTable tbody>tr:last #collaboratorMayEdit').attr('checked', '').attr('value', index).attr('disabled', true)			
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
			jQuery('#specificationTable tbody>tr:last').clone(true).insertAfter('#specificationTable tbody>tr:last');
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
				//jQuery('#titleMessage').effect('highlight', null,500, null)
				titleIsValid = false
				validate()
			} else {
				
				if (isEditing && (jQuery(this).val() == originalTitle)){
					titleIsValid = true
					jQuery('#titleMessage').html("")
					validate()
				} else {
				
					jQuery.get('/development/exists/' + jQuery(this).val(),
						function(data) {
							if (data == "true") {
								titleIsValid = false
								jQuery('#titleMessage').html("<span class='red bold'>A development with this name already exists. Please choose another.</span>")
								validate()
							} else {
								titleIsValid = true
								jQuery('#titleMessage').html("")
								validate()
							}
						})
				}
			}
		})
		
		jQuery("#license").change();
		jQuery("#projectVendor_Other").change()
		jQuery("#goals_Other").change()
		jQuery("#status_Other").change()
		jQuery("#developmentType_Other").change()
		jQuery("select[name=collaboratorRole]").change()
		jQuery('input[name=title]').blur()
		jQuery('input[name=collaboratorIsUsername]').change()

	});
</script>

<h2 class="pageTitle"><%=request.getAttribute('pageTitle')?:'Add Development'%></h2>

<nav>
	<ul>
		<a href="javascript://" id="submitForm"><li>Save</li> </a>
		<% if (development?.id){ %>
			<a href="/development/${development?.id}"><li>Cancel</li> </a>
		<% } else { %>
			<a href="/developments"><li>Cancel</li> </a>
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
							<td><textarea rows="5" cols="47" id="description" name="description"><%=development?.description?.getValue()?:'' %></textarea>
							</td>
							<td><span id="descriptionMessage"></span></td>
						</tr>

						<tr id="sourceRow">
							<td>Source</td>
							<td><select id="source" name="source">
									<% ['':'None','reprapwiki':'RepRapWiki','github':'Github','thingiverse':'Thingiverse','blog':'Blog','other':'Other'].each { key, value -> 
										def selected = (development?.source && development?.source == key) ? 'selected=selected':''
									%>
									<option ${selected} value="${key}">${value}</option>
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
								<% enums.Status.eachWithIndex { key, i -> 
								def checked = (development?.status && development?.status == key.name()) ?  'checked=checked':''
										if (i % 2 == 0) print "<br>"
								%> <input type="radio" value="${key.name()}" id="status_${key.name()}" name="status" <%=checked%> />&nbsp;${key}
								<% } %>
							</td>
							<td id="message"></td>
						</tr>

						<tr id="statusOtherRow">
							<td>Other Status</td>
							<td><input type="text" id="statusOther" name="statusOther" value="<%=development?.statusOther?:''%>" /></td>
							<td><span id="statusOtherMessage"></span></td>
						</tr>

						<tr id="imageURLRow">
							<td>Image URL</td>
							<td><input type="text" id="imageURL" name="imageURL" value="<%=development?.imageURL?:''%>" /></td>
							<td><span id="imageURLMessage"></span></td>
						</tr>
						
						<tr id="licenseRow">
							<td>License</td>
							<td><select  id="license" name="license">
									<% enums.License.each { 
										def selected = (development?.license && development?.license == it) ? 'selected=selected':''
									%>
									<option ${selected} value="${it.name()}" title="${it.description}">${it.title}</option>
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
								<% enums.DevelopmentType.eachWithIndex { key, i ->
									
									def className = (i % 2 == 1)? 'rightCol':'leftCol'
									def selected = (development?.developmentType && development?.developmentType == key.name()) ? 'checked=checked':''
									
								%> 
									<input class="${className}" type="radio" value="${key.name()}" id="developmentType_${key.name()}" name="developmentType" <%=selected%> />&nbsp;${key}
							 
							<% if(i % 2 == 1){ print "<br>" }
							} %>
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
								<% enums.ProjectVendor.eachWithIndex { key, i ->
								
									def checked = ''
									if (development?.projectVendor?:null){
										if (development.projectVendor.contains(key.name())){
											checked = 'checked=checked'
										}
									} 
								%> <input type="checkbox" title="${key.description?:''}" value="${key.name()}" id="projectVendor_${key}"
								name="projectVendor" <%=checked%>
							/>&nbsp;${key} <% if(i % 2 == 1){ print "<br>" }
								} %>
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
								<% enums.Category.eachWithIndex { key, i -> 
								def devCats = development?.categories?:[]
								
							%> <input type="checkbox" value="${key.name()}" name="categories"
								<%=devCats.contains(key.name())? 'checked=checked':''%>
							/>&nbsp;${key} <% if(i % 2 == 1){ print "<br>" }
							} %>
							</td>
							<td>Category missing?<br>Use Tags, or contact <a href="mailto:support@development-tracker.info">support</a><span
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
								<% enums.Goal.eachWithIndex { key, i -> 
								def devGoals = development?.goals?:[]
								
							%> <input type="checkbox" title="${key.description?:key}" value="${key.name()}" id="goals_${key}" name="goals"
								<%=devGoals.contains(key.name())? 'checked=checked':''%>
							/>&nbsp;${key} <% if(i % 2 == 1){ print "<br>" }
							} %>
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
							<td><textarea rows="3" cols="47" id="goalsDescription" name="goalsDescription"><%=development?.goalsDescription?.getValue()?:'' %></textarea>
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
									<option value="${key.name()}" label="${key}" <%=selected%> />
									<% } %>
							</select></td>
							<td class="linkDescription"><input type="text" id="relationshipDescription" name="relationshipDescription" value="${r.description}"/></td>
							
							<td>
							<% if (r.to) { %>
								<input class="linkURL" type="text" id="relationshipTo" name="relationshipTo" value="${r.to}" />
							<%} else { %>
								<input class="linkURL" type="text" id="relationshipTo" name="relationshipTo" value="${r.toUrl}" />
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
									<option value="${key.name()}" label="${key}" />
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
									<option value="${key.name()}" label="${key}" <%=selected%> />
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
									<option value="${key.name()}" label="${key}" />
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
								def specificationName = StringEscapeUtils.unescapeHtml(name)
								def specificationValue = StringEscapeUtils.unescapeHtml((development?.specificationValue) ? development?.specificationValue[i] : '')
						%>
						<tr>
							<td width="20%">
								<input type="text" id="specificationName" name="specificationName" value="${specificationName}" />
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
										def selected = (development?.specificationUnit && development?.specificationUnit == it.name()) ? 'selected=selected':''
									%>
									<option ${selected} value="${it.name()}">${it}</option>
									<% } %>
							</select></td>
							<td><span id="specificationUnitMessage"></span></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</div>


<% include '/templates/includes/footer.gtpl' %>