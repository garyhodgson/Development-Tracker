<% include '/templates/includes/header.gtpl' %>

<link rel="stylesheet" type="text/css" href="/css/openid/openid.css" />

<div class="grid_8 prefix_2 suffix_2">
	
	<div id="signin" class="bordered">

		<div class="grid_6 openidform light-bordered">
			<form class="openid" method="get" action="/_ah/login_redirect">
				<div>
					<ul class="providers">
						<li class="openid" title="OpenID"><img src="/images/openid/openidW.png" alt="icon" /> <span><strong>http://{your-openid-url}</strong>
						</span>
						</li>
						<li class="direct" title="Google"><img src="/images/openid/googleW.png" alt="icon" /><span>https://www.google.com/accounts/o8/id</span>
						</li>
						<li class="direct" title="Yahoo"><img src="/images/openid/yahooW.png" alt="icon" /><span>http://yahoo.com/</span>
						</li>
						<li class="username" title="Wordpress blog name"><img src="/images/openid/wordpress.png" alt="icon" /><span>http://<strong>username</strong>.wordpress.com</span>
						</li>
						<li class="username" title="Blogger blog name"><img src="/images/openid/blogger.png" alt="icon" /><span>http://<strong>username</strong>.blogspot.com/</span>
						</li>
						<li class="username" title="MyOpenID user name"><img src="/images/openid/myopenidW.png" alt="icon" /><span>http://<strong>username</strong>.myopenid.com/</span>
						</li>
					</ul>
				</div>
				<input type="hidden" name="continue"
					value="<%=request.getParameter("continue")?: "/"%>"
				/>
				<fieldset>
					<label for="openid_username">Enter your <span>Provider user name</span> </label>
					<div>
						<span></span><input type="text" name="openid_username" /><span></span> <input type="submit" value="Log In" />
					</div>
				</fieldset>
				<fieldset>
					<label for="openid_identifier">Enter your <a class="openid_logo" href="http://openid.net">OpenID</a> </label>
					<div>
	
						<input type="text" name="openid_identifier" /> <input type="submit" value="Log In" />
					</div>
				</fieldset>
			</form>
	
			<script type="text/javascript" src="/js/openid/jquery.openid.js"></script>
			<script type="text/javascript">
				jQuery(function() {
					jQuery("form.openid").openid();
				});
			</script>
		</div>
	
		<div id="text" class="grid_5">
			<h2>Please sign in to continue.</h2>
			<p>Signing in gives you the ability to add developments and create a personal watch list.</p>
			<p>Your security credentials are not stored in Development Tracker. Your email address is never shown, and you
				have the opportunity to select a username upon first login.</p>
		</div>
		<br clear="both">
	</div>
</div>


<% include '/templates/includes/footer.gtpl' %>