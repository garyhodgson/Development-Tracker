<% 
	def changeHistories = request.getAttribute("changeHistories") 
	def development = request.getAttribute("development")

	def hightlightIndex = changeHistories.findIndexOf { it.id == params.changeHistoryId as Long }
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
		<% changeHistories.eachWithIndex { changeHistory, i -> %>
			<li><a href="#wizard-${changeHistory.id}">
			<div><b>${changeHistory.by}</b><br><span title="${changeHistory.on.format("E dd MMM yyyy kk:mm:ss")}">${prettyTime.format(changeHistory.on)}</span></div>
			</a></li>
		<% } %>
		</ul>

		<div id="wizard-body" class="wiz-body">

			<%  changeHistories.eachWithIndex { changeHistory, i -> %>
			   
				<div id="wizard-${changeHistory.id}" class="wiz-content">
					<table cellspacing=0>
						<% changeHistory.changes.each { change -> %>
							<tr class="change-${change.type}">
								<td title="${change.type.title}"><span class="symbol">${change.type.symbol}</span></td>
								<td>${change.name}</td>
								<td>${change.value}</td>
							</tr>
						<% } %>
					</table>
				</div>
				<% } %>
		</div>
	
	</div>

</div>

<% include '/templates/includes/footer.gtpl' %>