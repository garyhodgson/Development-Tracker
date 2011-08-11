<% if (request.paging) { 
	def paging = request.paging
	def limit = (request.paging.limit && request.paging.limit != app.AppProperties.PAGE_LIMIT) ? "&limit=${request.paging.limit}" : ''
%>
		<div class="paging">
			<span id="pagingStats">${paging.start?:''} - ${paging.end?:''} / ${paging.total?:''}</span>
			<span id="pagingActions">
				<a href="?offset=${paging.first?:0}${limit}">First</a>, 
				<a href="?offset=${paging.previous?:0}${limit}">Previous</a>, 
				<a href="?offset=${paging.next?:0}${limit}">Next</a>, 
				<a href="?offset=${paging.last?:0}${limit}">Last</a></span>
			
		</div>
<% } %>