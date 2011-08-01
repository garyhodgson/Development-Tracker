<% if (request.paging) { 
	def paging = request.paging
%>
		<div class="paging">
			<span id="pagingStats">${paging.start?:''} - ${paging.end?:''} / ${paging.total?:''}</span>
			<span id="pagingActions">
				<a href="?offset=${paging.first?:0}">First</a>, 
				<a href="?offset=${paging.previous?:0}">Previous</a>, 
				<a href="?offset=${paging.next?:0}">Next</a>, 
				<a href="?offset=${paging.last?:0}">Last</a></span>
			
		</div>
<% } %>