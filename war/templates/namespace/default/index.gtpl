<% include '/templates/includes/htmlHeader.gtpl' %>

<% if (request.session.getAttribute('message') != null) { %>
	<div class="message"><%=request.session.getAttribute('message')?:''%></div>
<% request.session.setAttribute('message',null) } %>
	
<div class="index-container bordered site-default-bg">

	<div class="left" style="width: 45%">
		<div class="redirect-block  bordered white-bg">
			<h1>Development Tracker</h1>
			<h4>Development Tracker is a simple online, open registry where development projects and ideas can be submitted, linked and followed.</h4>
			<p>Keeping track of what is happening in your favourite online community can be tricky. Development projects can span Wikis, Blogs, Project Collaboration Sites and more.  RSS feeds and mailing lists help, but tying these dissparate sources together, either to get a feel for the state of the art, or to discover who else has had the same amazing idea, can be tricky.</p>
			<br clear="both">
		</div>
		<div class="redirect-block bordered white-bg">
			<div class="">
				<h2>My Development Tracker</h2>
			</div>
			<div id="benefits">
				<p>Signing into Development Tracker via Open ID brings several benefits&hellip;</p>
				<ul>
					<li>add developments to the tracker</li>
					<li>add a project to your watch list</li>
					<li>personalise your view and feeds</li>
				</ul>
				<p>Simply choose a preferred project/vendor from the selection on the right and click the "sign in" link.</p>
			</div>
			<br clear="both">
		</div>
	</div>
	<div class="redirect-block bordered right index-block white-bg" style="width: 45%">
		<div class="left">
			<h1>3D Printing</h1>
			<ul class="left">
				<a href="/start/reprap"><li class="redirect-subblock hover-link light-bordered site-default-bg"><h2>RepRap</h2></li> </a>
				<a href="/start/makerbot"><li class="redirect-subblock hover-link light-bordered site-default-bg"><h2>Makerbot</h2></li> </a>
				<a href="/start/3dprint"><li class="redirect-subblock hover-link light-bordered site-default-bg"><h2>Vendor Agnostic</h2></li> </a>
			</ul>
		</div>
		<h3>Choose your preferred project/vendor to provide customised news and updates.</h3>

		<p>
			The <em>3D Print</em> Development Tracker is project and vendor agnostic. Many developments can be used across the 3D
			printing ecosystem. Choosing a project or vendor simply customises the news and updates shown.
		</p>
		<br>
		<p>
			<strong>Vendor/Project not listed?</strong> Contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> to suggest a project or vendor you would like to see.
		</p>
		<br clear="both">
	</div>

	<div class=" bordered right index-block white-bg" style="width: 45%">
		<div>
			<a href="/about" style="border-style: none;"><div style="display: inline-block;width: 20%" class="redirect-subblock hover-link light-bordered site-default-bg">About</div></a>
			<a href="/faq" style="border-style: none;"><div style="display: inline-block;width: 20%" class="redirect-subblock hover-link light-bordered site-default-bg">FAQ</div></a>
			<a href="/future" style="border-style: none;"><div style="display: inline-block;width: 20%" class="redirect-subblock hover-link light-bordered site-default-bg">Future</div></a>
		</div>
	</div>

	<br clear="both">
</div>
<% include '/templates/includes/htmlFooter.gtpl' %>
