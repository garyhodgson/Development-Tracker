<% include '/templates/includes/header.gtpl' %>

<div class="grid_2">
	<div class="redirect-block bordered">
		<h1>FAQ</h1>
	</div>
	<br />
		<img width="100%" alt="Prusa Mendel" src="/images/PrusaMendel.png">
</div>

<div class="light-bordered grid_9 faq">
	<p>
		If the answer to your question is not below then please email <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a>.
	</p>
	<br>
	<p class="faq-question">What is a Setup (was previously called Kit)?</p>
	<p class="faq-answer">Setups are way of telling the world about your particular printer or printers. It allows you to describe your hardware, add a photo, and list the components that go to make it up.</p>
	<br>
	
	<p class="faq-question">What is a Theme?</p>
	<p class="faq-answer">Themes are a way of grouping a collection of developments together that have something in common.  
	A way of connecting developments together which is looser than using links, and more focussed than using categories or tags.</p>
	<br>

	<p class="faq-question">How much does it cost to use Development Tracker?</p>
	<p class="faq-answer">The application has been written as a contribution to the community and at this time is offered as a free service. This will hopefully continue as long
		as it remains viable. The service runs on Google App Engine which has an allowance of resources for small scale applications. Should these quotas be reached then the functionality or 
		availability of the application may be affected. However, the main aim of the application at the time of writing is
		to provide a useful service to the community, and not to generate money.</p>
	<br>

	<p class="faq-question">Someone else has already registered the username I am known in the community by. What can I do?</p>
	<p class="faq-answer">
		Contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> who will act as liason between yourself and the other user and will try and reach an acceptable solution
		for all parties. The administrator's decision in all disputes is final. Please also see the <a href="/terms">Terms of Use</a>.
	</p>
	<br>

	<p class="faq-question">Someone else registered my development and I want to update it. What can I do?</p>
	<p class="faq-answer">
		Contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> who will act as liason between yourself and the submitter in order to ensure the information is updated
		and organise sharing of editing rights. Future iterations of the application may allow direct private messaging of other users who grant permission.
	</p>
	<br>

	<p class="faq-question">What happens to the Image URL I give when adding a development? Are you Hot-Linking?</p>
	<p class="faq-answer">Development tracker makes a thumbnail copy of the image and uses this throughout the lifetime of the entry. No hot-linking occurs. Please be sure to have
		the correct rights to the image used.</p>
	<br>

	<p class="faq-question">I'm not happy that my project or development is featured, or an image of mine has been used as a thumbnail and the right to do this was not given. What can I do?</p>
	<p class="faq-answer">Contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> and we will check your claim and if necessary delete the offending entry or image.</p>
	<br>

	<p class="faq-question">How can I delete my account?</p>
	<p class="faq-answer">
		Currently there is no self-service for account deletion. Please contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> who will perform the removal. Please be
		aware of the data retention policy under <a href="/terms">Terms of Use</a>
	</p>
	<br>

</div>

<% include '/templates/includes/footer.gtpl' %>
