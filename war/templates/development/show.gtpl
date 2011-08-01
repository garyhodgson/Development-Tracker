
<% 
	import org.apache.commons.lang.StringEscapeUtils
	def development = request.getAttribute("development") 
	def relationships = request?.getAttribute('relationships')
	def collaborations = request?.getAttribute('collaborations')
%>
<% include '/templates/includes/header.gtpl' %>


<script type="text/javascript">
jQuery(function() {	
	jQuery("ul.tabs").tabs("div.panes > div");	
	jQuery("table tr:even").addClass("oddrow");
	jQuery('table td:nth-child(1)').addClass('label-column')
	jQuery('table td:nth-child(2)').addClass('value-column')
});
</script>

<h2 class="pageTitle"><%=request.pageTitle?:"Development"%></h2>

<nav>
	<ul>
		<a href="/"><li>Home</li></a>
		<a href="/developments"><li>Developments</li> </a>
		<br>
		
		<a href="/development/<%=development.id%>/watchers"><li>Watchers</li> </a> 
		<% if (user) { %> 
			<% if ((session.userinfo?.watchedDevelopments?:[]).contains(development.id)) { %>
				<a href="/development/unwatch/<%=development.id%>"><li>Unwatch</li> </a> 
			<% } else { %> 
				<a href="/development/watch/<%=development.id%>"><li>Watch</li> </a> 
			<% } %> <br> 
			<%if (users.isUserAdmin() || session.userinfo?.username == development.createdBy) { %>
				<a href="/development/edit/<%=development.id%>"><li>Edit</li> </a>
			<% } %> 
		<% } %>		
	</ul>
</nav>

<% if (development.thumbnailServingUrl){ %>
<div class="development-thumb left">
	<img src="${development.thumbnailServingUrl}">
</div>
<% } %>
<div class="content ${(development.imageURL)?'thumbnailed':'' }">

	<ul class="tabs">
		<li><a href="#">Core</a></li>
		<li><a href="#">Connections</a></li>
		<li><a href="#">Collaborators</a></li>
		<li><a href="#">Specification</a></li>
	</ul>

	<!-- tab "panes" -->
	<div class="panes">
		<div class="development">
			<input type="hidden" id="id" name="id" value="${development.id}">
			<table border=0 cellspacing="0" cellpadding="5px">
				<tr>
					<td>Title</td>
					<td>${development.title}</td>
				</tr>
				<tr>
					<td>Description</td>
					<% def text = development.description?development.description.getValue():""
						def rows = text.length() < 512 ? 4 : 10 %>
					<td><textarea readonly="readonly" contenteditable="false" style="width: 100%" rows="${rows}">${text}</textarea>
					</td>
				</tr>
				<tr>
					<td>Source</td>
					<td>${development.source?.capitalize()?:''} <% if (development.source && development.sourceURL){ %>
						&nbsp;&colon;&nbsp; <% } %> <% if (development.sourceURL){ %> <a href="${development.sourceURL}"
						title="${development.sourceURL}"
					>${development.sourceURL}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Status</td>
					<% def status = (development.status && development.status == enums.Status.Other.name()) ?  development.statusOther?:'' : development.status?:'' %>
					<td>${status}</td>
				</tr>
				<tr>
					<td>License</td>
					<% def license = (development.license && development.license == enums.License.Other) ?  development.licenseOther ?:'' : development.license.description?:'' %>
					<td>${license}</td>
				</tr>
				<tr>
					<td>Development Type</td>
					<% def developmentType = (development.developmentType && development.developmentType == enums.DevelopmentType.Other.name()) ?  development.developmentTypeOther?:'' : development.developmentType?:''%>
					<td>${developmentType}</td>
				</tr>
				<tr>
					<td>Categories</td>
					<td>
						<% development.categories?.each{ %> <a href="/developments/categories/${it}">${it}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Goals</td>
					<td>
						<% development.goals?.each{ %> <a href="/developments/goals/${it}">${it}</a> <% } %>
					</td>
				</tr>
				<% if (development.goalsDescription) { %>
				<tr>
					<td>Goals Description</td>
					<% def goalsDescriptionText = development.goalsDescription?development.goalsDescription.getValue():""
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
							def projectVendor = (it == enums.ProjectVendor.Other.name()) ?  development.projectVendorOther?:'' : it %> <a
						href="/developments/projectVendor/${projectVendor}"
					>${projectVendor}</a> <% } %>
					</td>
				</tr>
				<tr>
					<td>Registered</td>
					<td>${development.created?:''}<% if (development.createdBy) { %> by <a href="/userinfo/${development.createdBy}">${development.createdBy}</a><% } %></td>
				</tr>
				<tr>
					<td>Updated</td>
					<td>${development.updated?:''}</td>
				</tr>

			</table>

		</div>

		<div id="relationships" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
				<%  relationships?.each { r ->
				%>
				<tr>
					<td class="linkType">${r.type}</td>
					<td class="linkDescription">
						<% if (r.to){ %>
							<a href="/development/${r.to}">${r.description?:'Link'}</a>
						<% } else if (r.toUrl.startsWith('http')) { %>
							<a href="${r.toUrl}" target="_blank">${r.description?:'Link'}</a>
						<% } else { %>
							${r.toUrl}
						<% }  %>
					</td>
				</tr>
				<% 	} %>
			</table>
		</div>
		
		<div id="collaborations" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
				<%  collaborations?.each { c ->
				%>
				<tr>
					<td class="linkType">
					<% if (c.role == enums.Role.Other && c.otherRole){ %>
						${c.otherRole}
					<% } else { %>
						${c.role}
					<% }  %>
					</td>
					<td class="linkDescription">
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
		
		<%  if (development?.specificationName){ %>
		<div id="specification" class="development">
			<table border=0 cellspacing="0" cellpadding="5px">
			<% if (development?.specificationUnit) { %>
				<tr>
					<td>Specification Units</td>
					<td>${development?.specificationUnit}</td>
				</tr>			
			<% } %>			
			
			<%  development?.specificationName.eachWithIndex { name, i ->
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
		<%}%>
	</div>
</div>
<% include '/templates/includes/footer.gtpl' %>