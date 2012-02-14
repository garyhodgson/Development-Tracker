<% include '/templates/includes/header2.gtpl' %>

<% 
import org.apache.commons.lang.StringUtils

def subdomain = request.properties.serverName.split(/\./).getAt(0) 
%>

	<br>

	<div class="grid_7">

		<div class="box articles">
			<h2>
				<a href="#" id="toggle-articles">Latest Developments</a>
			</h2>
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
						<h4>${development.createdBy}</h4>
						<%
							def status = (development.status && development.status == enums.Status.Other) ?  development.statusOther?:'' : development.status?.title?:''
						%>
						<p class="meta">${status}</p>
						
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
			<h2>
				<a href="#" >Latest Activities</a>
			</h2>
			<div class="block" id="paragraphs">
				<%
					request.latestActivities.each { activity-> 
				%>
				<h5><a href="${activity.link}">${activity} - ${prettyTime.format(activity.created)}</a></h5>
				<div class="clear"></div>			
				<hr>
				<%
					} 
				%>
			</div>
		</div>
		
		<div class="box">
			<h2>
				<a href="#" >Latest Themes</a>
			</h2>
			<div class="block" id="paragraphs">
			
				<%
					request.latestThemes.each { theme -> 
				%>
				<h5><a href="/theme/${theme.id}">${theme.title}</a></h5>
				<p>${theme.description?:''}</p>
				<div class="clear"></div>
				<hr>
				<%
					} 
				%>
			</div>
		</div>

		<div class="box">
			<h2>
				<a href="#" >Latest Kits</a>
			</h2>
			<div class="block" id="paragraphs">
				<%
					request.latestKits.each { kit -> 
				%>
				<a href="/kit/${kit.id}"><h6>${kit.title}</h6></a>
				<% if (kit.thumbnailServingUrl){ %>
					<img src="${kit.thumbnailServingUrl}" width="60" height="60" alt="${kit.title}" >
				<% } %>
				<% def text = StringUtils.abbreviate(markdown.markdown(kit.description?:''),300) %>	
				<p>${text}</p>
				<div class="clear"></div>						
				<hr>
				<%
					} 
				%>
			</div>
		</div>

		

		
	</div>

<% include '/templates/includes/footer2.gtpl' %>