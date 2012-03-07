<% 
	def development = request.getAttribute("development") 
	def relationships = request.getAttribute('relationships')
	def reverseRelationships = request.getAttribute('reverseRelationships')
	def collaborations = request.getAttribute('collaborations')
	def supplementary = request.getAttribute('supplementary')
%>
<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript">
jQuery(function() {	
	jQuery("#tabs").tabs();	
	jQuery("table tr:even").addClass("oddrow");
	jQuery('table td:nth-child(1)').addClass('label-column')
	jQuery('table td:nth-child(2)').addClass('value-column')
	
	jQuery("#confirm").dialog({ 
		autoOpen: false,
		dialogClass:'modal',
		position: 'center',
		minHeight: 50,
		modal: true,
		resizable: false,
		title: 'Confirm Delete',
		closeText: '',
		buttons: [{text: "Yes",click: function() { document.location = "/development/delete/<%=development.id%>"; }},
		          {text: "No",click: function() { jQuery(this).dialog("close"); }}
				 ] 
	});
	
	jQuery("#deleteDevelopment").click(function(){
		jQuery("#confirm").dialog('open')
	});
});
</script>

<div class="grid_10 prefix_1 suffix_1">
	<h2 class="pageTitle"><%=request.pageTitle?:"Development"%></h2>
</div>

<div class="grid_2">
	<div class="development-thumb">
	<% if (development.thumbnailServingUrl){ %>
		<a class="nohint" href="${development.thumbnailServingUrl}" target="_blank"><img src="${development.thumbnailServingUrl}"></a>
	<% } else { %>
		<p class="noimage" >No Image Available</p>
	<% } %>
	</div>
	
	<% if (development.signs){ %>	
		<%if (!development.thumbnailServingUrl){%>
		<% } %>
		<div class="signs">
			<% development.signs.each { sign ->%>
				<img src="/images/signs/50/${sign.image}" title="${sign.title}">
			<% } %>
		</div>
	<% } %>
	
	<% if (development.notice){ %>
		<div class="notice site-default-bg">${development.notice}</div>
	<% } %>
</div>	

<div class="grid_9">
	<div id="tabs">
		<ul class="tabs">
			<li><a href="#core">Core</a></li>
			<li><a href="#connections">Connections <span class="heading-count">(${relationships?.size()?:0}/${reverseRelationships?.size()?:0})</span></a></li>
			<li><a href="#collaborators">Collaborators <span class="heading-count">(${collaborations?.size()?:0})</span></a></li>
			<li><a href="#specification">Specification <span class="heading-count">(${development.specificationName?.size()?:0})</span></a></li>
			<li><a href="#more">More <span class="heading-count">(${supplementary?.values()?.size()?:0})</span></a></li>
		</ul>

		<div class="development" id="core">
			<input type="hidden" id="id" name="id" value="${development.id}">
			<table border=0 cellspacing="0" cellpadding="5px">
				<tr>
					<td>Title</td>
					<td>${development.title?:''}</td>
				</tr>
				<tr>
					<td>Description</td>
					<% 	def text = markdown.markdown(development.description?:'') %>
					<td>${text}</td>
				</tr>
				<tr>
					<td>Source</td>
					<td>${development.source?.title?:''} <% if (development.source && development.sourceURL){ %>
						&nbsp;:&nbsp; <% } %> <% if (development.sourceURL){ %> <a target="_blank" href="${development.sourceURL}"
						title="${development.sourceURL}"
					>${development.sourceURL}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Status</td>
					<%
						def status = (development.status && development.status == enums.Status.Other) ?  development.statusOther?:'' : development.status?.title?:''
					%>
					<td>${status}</td>
				</tr>
				<tr>
					<td>License</td>
					<% def license = (development.license && development.license == enums.License.Other) ?  development.licenseOther ?:'' : development.license?.description?:'' %>
					<td>${license}</td>
				</tr>
				<tr>
					<td>Development Type</td>
					<% def developmentType = (development.developmentType && development.developmentType == enums.DevelopmentType.Other) ?  development.developmentTypeOther?:'' : development.developmentType?.title?:'' %>
					<td>${developmentType}</td>
				</tr>
				<tr>
					<td>Categories</td>
					<td>
						<% development.categories?.each{ %> <a href="/developments/categories/${it}">${it.title?:''}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Goals</td>
					<td>
						<% development.goals?.each{ %> <a href="/developments/goals/${it}">${it.title?:''}</a> <% } %>

						<% if (development.goalsOther){ %> ${development.goalsOther} <% } %>
						
					</td>
				</tr>
				<% if (development.goalsDescription) { %>
				<tr>
					<td>Goals Description</td>
					<% def goalsDescriptionText = development.goalsDescription?development.goalsDescription:""
						def goalsDescriptionRows = goalsDescriptionText.length() < 1024 ? 2 : 4 %>
					<td><textarea readonly="readonly" contenteditable="false" style="width: 100%" rows="${goalsDescriptionRows}">${goalsDescriptionText}</textarea>
					</td>
				</tr>
				<% } %>
				<tr>
					<td>Tags</td>
					<td>
						<% development.tags?.each{ %> <a href="/developments/tags/${it}">${it}</a> <% } %>
					</td>
				</tr>

				<tr>
					<td>Project/Vendor</td>
					<td>
						<% development.projectVendor?.each{
							def projectVendor = (it == enums.ProjectVendor.Other) ?  development.projectVendorOther?:'' : it %> <a
						href="/developments/projectVendor/${projectVendor}"
					>${projectVendor}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Registered</td>
					<td><span title="${development.created}">${development.created?prettyTime.format(development.created):''}</span><% if (development.createdBy) { %> by <a href="/userinfo/${development.createdBy}">${development.createdBy}</a><% } %></td>
				</tr>
				<tr>
					<td>Updated</td>
					<td><span title="${development.updated}">${development.updated?prettyTime.format(development.updated):''}</span></td>
				</tr>

			</table>

		</div>

		<div id="connections" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
				<%  relationships?.each { r ->
				%>
				<tr>
					<td >${r.type.title}</td>
					<td >
						<% if (r.to){ %>
							<a href="/development/${r.to.id}">${r.description?:'Link'}</a>
						<% } else if (r.toUrl.startsWith('http')) { %>
							<a href="${r.toUrl}" target="_blank">${r.description?:'Link'}</a>
						<% } else { %>
							${r.toUrl}
						<% }  %>
					</td>
				</tr>
				<% 	} %>
			</table>
			<hr>
			<table border=0 cellspacing="0" cellpadding="5px">
				<%  reverseRelationships?.each { rr ->
				%>
				<tr>
					<td >${rr.type.reverseTitle}</td>
					<td >
						<a href="/development/${rr.development.id}">${rr.development.title}</a>
					</td>
				</tr>
				<% 	} %>
			</table>
		</div>
		
		<div id="collaborators" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
				<%  collaborations?.each { c ->
				%>
				<tr>
					<td>
					<% if (c.role == enums.Role.Other && c.otherRole){ %>
						${c.otherRole}
					<% } else { %>
						${c.role.title}
					<% }  %>
					</td>
					<td>
						<% if (c.userInfo){ %>
							<a href="/userinfo/${c.name}">${c.name?:''}</a>
						<% } else { %>
							${c.name}
						<% }  %>
					</td>
				</tr>
				<% 	} %>
			</table>
		</div>
		
		<div id="specification" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
			<% if (development?.specificationUnit) { %>
				<tr>
					<td>Specification Units</td>
					<td>${development?.specificationUnit?.title?:''}</td>
				</tr>			
			<% } %>			
			
			<%  development?.specificationName?.eachWithIndex { name, i ->
					def specificationName = name
					def specificationValue = (development.specificationValue) ? development.specificationValue[i] : ''
			%>
				<tr>
					<td>${specificationName}</td>
			   		<td>${specificationValue}</td>
			   	</tr>
			<% } %>
			</table>
		</div>
		
		
		<div id="more" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
			<% supplementary?.each { %>
				<tr>
					<td>${it.key}</td>
			   		<td>${it.value}</td>
			   	</tr>
			<% } %>
			</table>
		</div>
		
	</div>
</div>

<div id="actions" class="grid_1">
	<ul>
		<% if (request.history?:false) { %> 
			<a href="/development/<%=development.id%>"><li>Back</li> </a>
			<br>
		<% } %>		
		<a href="/development/<%=development.id%>/watchers"><li>Watchers</li> </a> 
		<% if (user && !request.history?:false) { %> 
			<% if ((session.userinfo?.watchedDevelopments?:[]).contains(development.id)) { %>
				<a href="/development/unwatch/<%=development.id%>"><li>Unwatch</li> </a> 
			<% } else { %> 
				<a href="/development/watch/<%=development.id%>"><li>Watch</li> </a> 
			<% } %> <br> 
		<% } %>		
			
		<% if (user && !request.history?:false) { %> 
			<%if (users.isUserAdmin() || session.userinfo?.username == development.createdBy) { %>
				<a href="/development/edit/<%=development.id%>"><li>Edit</li> </a>
			<% } %> 
		<% } %>		
		
		<a href="/development/<%=development.id%>/history"><li>History</li> </a>
		<br>
		<a href="/development/<%=development.id%>/graph"><li>Graph</li> </a>
		<br>
		<% if (user && !request.history?:false) { %> 
			<%if (users.isUserAdmin() || session.userinfo?.username == development.createdBy) { %>
				<a href="#" id="deleteDevelopment" rel="#confirm"><li>Delete</li> </a>
			<% } %> 
		<% } %>	
	</ul>
</div>

<div id="confirm">
	<p>Are you sure you wish to delete development ${development.id}?</p>
</div>
<% include '/templates/includes/footer.gtpl' %>
