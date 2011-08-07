<% 
	def diffLogs = request.getAttribute("diffLogs") 
	def development = request.getAttribute("development") 
	def dmp = new name.fraser.neil.plaintext.diff_match_patch()

	def hightlightIndex = diffLogs.findIndexOf { it.id == params.diffLogId as Long }
%>
<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/smartwizard/SmartWizard.js"></script>

<script type="text/javascript">
jQuery(function() {
	
	jQuery('#smartwizard').smartWizard({
		enableAll : true,
		selectedStep: ${hightlightIndex!=-1?hightlightIndex:0}
	})
	
});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Change History"%></h2>

<nav>
	<ul>
		<a href="/developments"><li>Developments</li> </a>
		<a href="/development/<%=development.id%>"><li>Back</li> </a>
		<br>

		<% if (user) { %>
		<% if ((session.userinfo?.watchedDevelopments?:[]).contains(development.id)) { %>
		<a href="/development/unwatch/<%=development.id%>"><li>Unwatch</li> </a>
		<% } else { %>
		<a href="/development/watch/<%=development.id%>"><li>Watch</li> </a>
		<% } %>
		<br>
		<%if (users.isUserAdmin() || session.userinfo?.username == development.createdBy) { %>
		<a href="/development/edit/<%=development.id%>"><li>Edit</li> </a>
		<% } %>
		<% } %>
	</ul>
</nav>


<% if (development.imageURL){ %>
<div class="development-thumb left">
	<a class="nohint" href="/development/${development.id}"><img src="${development.imageURL}">
	</a>
</div>
<% } %>

<div class="content ${(development.imageURL)?'thumbnailed':'' }">
	
	<div id="smartwizard" class="wiz-container change-history">

		<ul id="wizard-anchor">
		<% diffLogs.eachWithIndex { diffLog, i -> %>
			<li><a href="#wizard-${diffLog.id}">
			<div><b>${diffLog.by}</b><br><span title="${diffLog.on}">${diffLog.on.format("E dd MMM yyyy kk:mm:ss")}</span></div>
			</a></li>
		<% } %>
		</ul>

		<div id="wizard-body" class="wiz-body">

			<%  def patchList = []
				def previousVersions = [] 
			    diffLogs.eachWithIndex { diffLog, i -> %>
			   
				<div id="wizard-${diffLog.id}" class="wiz-content">
					<% 
					log.info "diffLog.id = ${diffLog.id}"
						
						def patch = dmp.patch_fromText(diffLog.patch)						   
						def currentDiff					
						def previousVersion = previousVersions?previousVersions.last():''
						
						def thisVersion = dmp.patch_apply(patch, previousVersion)[0]
						currentDiff =  dmp.diff_main(previousVersion,thisVersion);
						dmp.diff_cleanupSemantic(currentDiff)
						previousVersions << thisVersion
						
						out << "<p>"
						out << dmp.diff_prettyHtml(currentDiff)
						out << "</p>"
					%>
				</div>
				<% } %>
		</div>
	
	</div>

</div>

<% include '/templates/includes/footer.gtpl' %>