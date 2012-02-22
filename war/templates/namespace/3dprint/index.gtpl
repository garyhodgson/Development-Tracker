<% include '/templates/includes/header.gtpl' %>

<% 
import org.apache.commons.lang.StringUtils

def subdomain = request.properties.serverName.split(/\./).getAt(0) 
%>

	<div class="grid_7">

		<div class="box articles">
		
			<a href="/developments/latest"><h2>Latest Developments</h2></a>
			
			<div class="block" id="articles">				
				<%
					def latestDevelopmentsCount = request.latestDevelopments.size() 
					request.latestDevelopments.eachWithIndex { development, i -> 
						def cssClass = "article"
						if (i == 0) cssClass = "first article"
						if (i == latestDevelopmentsCount-1) cssClass = "last article"
				%>				
					<div class="${cssClass}">
						<h3>
							<a href="/development/${development.id}">${development.title}</a>
						</h3>
						<%
							def status = (development.status && development.status == enums.Status.Other) ?  development.statusOther?:'None' : development.status?.title?:'None'
						%>
						
						<p>by: <b>${development.author?:'[undefined]'}</b>;&nbsp;&nbsp;status: <b>${status}</b></p>
						
						
						
						<% if (development.thumbnailServingUrl){ %>
						<a href="${development.thumbnailServingUrl}" class="image">
							<img src="${development.thumbnailServingUrl}" width="60" height="60" alt="${development.title}" />
						</a>
						<% } %>
						
						<% 	def text = StringUtils.abbreviate(markdown.markdown(development.description?:''),500) %>
						<p>${text}</p>
						<div class="clear"></div>
					</div>
				
				<% } %>
			</div>
		</div>
	</div>
	<div class="grid_5">
	
		<div class="box">
			
			<a href="/activities"><h2>Latest Activities</h2></a>
			
			<div class="block" id="paragraphs">
				<%
					request.latestActivities.each { activity-> 
				%>
				<p><a class="nohint" href="${activity.link}">${activity} - ${prettyTime.format(activity.created)}</a></p>		
				<hr>
				<%
					} 
				%>
			</div>
		</div>
		
		<div class="box">
			
			<a href="/themes"><h2>Latest Themes</h2></a>
			<div class="block" id="paragraphs">			
				<%
					request.latestThemes.each { theme -> 
				%>
				<h5><a href="/theme/${theme.id}">${theme.title}</a> by ${theme.createdBy}</h5>
				<p>${StringUtils.abbreviate(markdown.markdown(theme.description?:''),300)}</p>
				<div class="clear"></div>
				<hr>
				<%
					} 
				%>
			</div>
		</div>

		<div class="box">

			<a href="/kits"><h2>Latest Kits</h2></a>

			<div class="block" id="">
				<%
					request.latestKits.each { kit -> 
				%>
				<h6><a href="/kit/${kit.id}">${kit.title}</a> by ${kit.ownerUsername}</h6>
				<% if (kit.thumbnailServingUrl){ %>
					<img class="index-thumbnail" src="${kit.thumbnailServingUrl}" width="60" height="60" alt="${kit.title}" >
				<% } %>
				<p>${StringUtils.abbreviate(markdown.markdown(kit.description?:''),300)}</p>
				<div class="clear"></div>						
				<hr>
				<%
					} 
				%>
			</div>
		</div>
		
	</div>

<% include '/templates/includes/footer.gtpl' %>