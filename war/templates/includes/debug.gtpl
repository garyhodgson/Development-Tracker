<div class="debug">
	<div>
		Request:
		<%=request.toString() %></div>
	<div>
		Session:
		<% session.properties.each{if (it.value) out << "${it.key}=${it.value}&nbsp;&nbsp;&nbsp;"} %>
	</div>
	<div>
		Headers:
		<% out << headers %> 
	</div>
	<div>
		Session Attributes:
		<% session.getAttributeNames().each{out << "${it}=${session.getAttribute(it)}" }   %>
	</div>
</div>