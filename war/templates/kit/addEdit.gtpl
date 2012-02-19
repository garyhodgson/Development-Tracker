<%
	import app.AppProperties
	import enums.*
	def kit = request?.getAttribute('kit')
	def parts = request?.getAttribute('parts')
%>

<% include '/templates/includes/header.gtpl' %>
<script type="text/javascript" src="/js/plupload/plupload.full.js"></script>
<script>
	jQuery(function() {
		jQuery('#submitForm').click(function() {
			jQuery('#addKitForm').submit();	
		})
		
		
		jQuery("input[name=developmentTitle]").autocomplete({
			minLength: 2,
			source: '/developments/autocomplete',
			select: function( event, ui ) {
				if (ui.item.id){
					
					jQuery('#partsTable').append("<tr>"+
							"<td>"+
							ui.item.label+
							"</td>"+
							"<td><input type=\"hidden\" name=\"partId\" value=\""+
							ui.item.id+
							"\" />"+
							ui.item.id+
							"</td>"+
							"<td><a href=\"javascript://\" id=\"removePart\">remove</a></td>"+
							"</tr>")
				}
			}
		});
		
		jQuery('#cancelAdd').click(function(){
			if (jQuery('#imageBlobKey').val() != ''){
				window.location = "/kit/add/cancel/"+jQuery('#imageBlobKey').val()
			} else {
				window.location = "/kits"
			}
		})
		
		jQuery('#removePart').live('click', function(){
			jQuery(this).parent().parent().remove();	
		})
		
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
			url : "<%=blobstore.createUploadUrl('/fileUpload.groovy')%>",
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

	});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.getAttribute('pageTitle')?:'Add Kit'%></h2>
</div>

<div class="grid_2">
	<div class="kit-thumbnail">
		<% if (kit?.thumbnailServingUrl) { %>
			<a class="nohint" href="${kit?.thumbnailServingUrl}" target="_blank"><img src="${kit?.thumbnailServingUrl}"></a>
		<% } else { %>
			<p class="noimage" >No Image Available</p>
		<% } %>
	</div>
</div>	

<div class="grid_9">

	<form action="<%=request.getAttribute('action')?:'/kit/add'%>" method="post" id="addKitForm">

		<div id="descriptions">
			<input type="hidden" id="id" name="id" value="<%=kit?.id %>">

			<table border=0 cellspacing="0" cellpadding="5px" class="form-table">

				<tr id="titleRow">
					<td>Title</td>
					<td><input type="text" id="title" name="title" value="<%=kit?.title?:''%>" />
					</td>
					<td><span id="titleMessage"></span>
					</td>
				</tr>

				<tr id="descriptionRow">
					<td>Description</td>
					<td><textarea rows="5" cols="66" id="description" name="description"><%=kit?.description?:'' %></textarea></td>
					<td><a href="http://daringfireball.net/projects/markdown/syntax" target="_blank">Markdown</a> is accepted.&nbsp;<span id="descriptionMessage"></span>
					</td>
				</tr>
				
				<tr id="imageURLRow">
					<td>Image URL</td>
					<td>
						<input type="text" id="imageURL" name="imageURL" value="<%=kit?.imageURL?:''%>" />
						<div id="fileUploadContainer">
							or&hellip;&nbsp;<a class="nohint" id="pickFile" href="#">[Upload File]</a>
							&nbsp;&nbsp;<a class="nohint" id="clearFile" href="#">[Clear]</a>
						</div>
						<input type="hidden" id="imageBlobKey" name="imageBlobKey" value="<%=kit?.imageBlobKey?:''%>" />
					</td>
					<td><span id="imageURLMessage"></span></td>
				</tr>
			</table>
		</div>
		
		<br>
		<hr>
		
		<div id="parts">
			<table border=0 cellspacing="0" cellpadding="5px" class="form-table" >
			<tr>
					<td>Add Development as Part</td>		
					<td><input type="text" name="developmentTitle" /></td>							
					<td>Type to search by title</span>
				</tr>
			</table>
			
			<br>
			
			<table border=0 cellspacing="0" cellpadding="5px" id="partsTable" class="form-list-table">
				<tr>
					<th width="70%">Part</th>
					<th width="20%">Id</th>
					<th width="10%"></th>
				</tr>

				<%  parts?.each { part -> %>
				<tr>
					
					<td width="70%">${part.title}</td>
					<td width="20%"><input type="hidden" name="partId" value="${part.id}" />${part.id}</td>							
					<td width="10%"><a href="javascript://" id="removePart">remove</a></td>
				</tr>
				<% } %>
			</table>
		</div>
		
	</form>
</div>

<div id="actions" class="grid_1">
	<ul>
		<a href="javascript://" id="submitForm"><li>Save</li> </a>
		<% if (kit?.id){ %>
			<a href="/kit/${kit?.id}"><li>Cancel</li> </a>
		<% } else { %>
			<a href="javascript://" id="cancelAdd"><li>Cancel</li> </a>
		<% } %>
	</ul>
</div>

<% include '/templates/includes/footer.gtpl' %>
