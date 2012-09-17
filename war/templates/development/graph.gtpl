<% include '/templates/includes/header.gtpl' %>

<script type="text/javascript" src="/js/modernizr.min.js"></script>
<script type="text/javascript" src="/js/jquery.svg.js"></script>
<script type="text/javascript" src="/js/vivagraph.min.js"></script>

<script type="text/javascript">
	var allDevelopmentsHash = '<%=request.allDevelopmentsHash%>'
	var targetDevelopmentId = <%=request.id%>
</script>
<script type="text/javascript" src="/js/graph.js"></script>

<div id='graph' class="grid_9 very-light-bordered"></div>

<div class="grid_2 right">
		
	<div id="controls" class="very-light-bordered">
		Connections: <input id="connectionsRange" type="range" min="0" max="10" value="3"/><span id="connectionsRangeDisplay">3</span>
	</div>
	
	<div id="details" class="very-light-bordered" >	
	</div>
	
	<div id="legend" class="very-light-bordered">
		<h5>Legend</h5>
		<ul>
			<li style="color: lightblue">Derived From / Derived</li>
			<li style="color: red">Related To</li>
			<li style="color: green">Designed For / Has Design</li>
			<li style="color: yellow">Implementation Of / Implemented By</li>
			<li style="color: orange">Part Of / Consists Of</li>
			<li style="color: maroon">Inspired By / Inspired</li>
			<li style="color: olive">Evolved From / Evolved Into</li>
			<li style="color: purple">In Answer To / Is Answered By</li>
			<li style="color: teal">Created By</li>
			<li style="color: grey">Link</li>
			<li style="color: grey">Other</li>
		</ul>
	</div>
</div>

<div id="vis-load">
	<h2>Loading Data&hellip;</h2>
	<img src="/images/ajax-loader-bar.gif"/>	
</div>

<% include '/templates/includes/footer.gtpl' %>
