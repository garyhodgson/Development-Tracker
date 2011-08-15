
<% include '/templates/includes/header.gtpl' %>

<div class="directory bordered right">
	<p>
		If the answer to your question is not below then please email <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a>.
	</p>
	<br>

	<p class="faq-question">How much does it cost to use Development Tracker?</p>
	<p class="faq-answer">The application has been written as a contribution to the community and at this time is offered as a free service. This will hopefully continue as long
		as it remains viable. The service runs on Google App Engine which has a generous allowance of resources for small scale applications. Should these quotas be reached, and costs
		introduced, then it may be necessary to investigate ways of financing the ongoing running of the application. However, the main aim of the application at the time of writing is
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

	<p class="faq-question">How can I delete my account?</p>
	<p class="faq-answer">
		Currently there is no self-service for account deletion. Please contact <a href="mailto:${app.AppProperties.SUPPORT_EMAIL}">support</a> who will perform the removal. Please be
		aware of the data retention policy under <a href="/terms">Terms of Use</a>
	</p>
	<br>

	<p class="faq-question">Why are there different subdomains (reprap, makerbot) for the 3dprint namespace? Isn't that simply confusing?</p>
	<p class="faq-answer">
		The idea is to be able to tailor parts of the application to the preferences of the user. If someone has more interest in the reprap project then they can go to <a
			href="/start/reprap"
		>reprap.development-tracker.info</a> and receive information weighted for this project.
	</p>
	<p class="faq-answer">At this stage there is little tailoring to the site - but future iterations should bring news feeds, priority listings and other project or vendor
		specific information.</p>
	<br>

</div>

<div class="redirect-block bordered">
	<h1>FAQ</h1>
</div>

<br>

<div class="">
	<img width="26%" alt="Prusa Mendel" src="/images/PrusaMendel.png">
</div>

<br clear="both">

<% include '/templates/includes/footer.gtpl' %>
