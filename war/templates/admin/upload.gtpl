
<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/plupload/plupload.full.js"></script>

<script type="text/javascript">
// Convert divs to queue widgets when the DOM is ready
jQuery(function() {
	
	jQuery('#fileUploadContainer').hide();
	
	var uploader = new plupload.Uploader({
		runtimes : 'html4,flash',
		browse_button : 'pickFile',
		container : 'fileUploadContainer',
		max_file_size : '1mb',
		url : "<%=blobstore.createUploadUrl('/admin/processUpload.groovy')%>",
		flash_swf_url : '/js/plupload/plupload.flash.swf',
		filters : [
			{title : "Image files", extensions : "jpg,gif,png"},
		],
		resize : {width : 400, quality : 90}
	});
	
	uploader.bind('Init', function(up, params) {
		jQuery('#fileUploadContainer').show();
	});

	jQuery('#submitForm').click(function(e) {
		uploader.start();
	});
	
	jQuery('#clearFile').click(function(e) {
		jQuery('#imageURL').val("");
		jQuery('#imageURL').attr("disabled", false);
		jQuery('#blobKey').val("");		
	});

	uploader.init();

	uploader.bind('FilesAdded', function(up, files) {
		jQuery.each(files, function(i, file) {
			jQuery('#imageURL').val("Upload File: "+file.name);
			jQuery('#imageURL').attr("disabled", true);
		});

		up.refresh(); // Reposition Flash/Silverlight
	});

	uploader.bind('Error', function(up, err) {
		alert("Error: " + err.code + ", Message: " + err.message +
			(err.file ? ", File: " + err.file.name : "") );

		up.refresh(); // Reposition Flash/Silverlight
	});

	uploader.bind('FileUploaded', function(up, file, info) {
		console.log('[FileUploaded] File:', file, "Info:", info);
		
		jQuery('#blobKey').val(info.response);
		jQuery("#mainForm").submit();
		
	});
		
});
</script>
<div id="fileUploadContainer">
	<a id="pickFile" href="#">[Upload File]</a>
	<a id="clearFile" href="#">[Clear]</a>
</div>

<form action="/admin/upload.groovy" method="post" id="mainForm">
	
	<br> <br> 
	Filename:<input type="text" name="imageURL" id="imageURL"> 
	<br> <br> 
	uploadedFile:<input type="text" name="blobKey" id="blobKey"> 
	
	<a href="#" id="submitForm">Submit</a>
	
</form>


<% include '/templates/includes/footer.gtpl' %>