
<%	if (users.isUserLoggedIn()) {%>

<a href="/access/logout"><span id="signout-link" class="access hover-link">Sign Out</span></a>

<%
	if (users.isUserAdmin()) {
%>
<span class="access"><a href="/_ah/admin">admin</a> </span>
<%
	}
%>

<span class="access signed-in"> <%
 	if (session.getAttribute("userinfo") != null
 				&& session.getAttribute("userinfo").username != null) {
 %> Hi <a title="View user info" href="/userinfo/<%=session.getAttribute("userinfo").username%>"><%=session.getAttribute("userinfo").username%></a> <%
 	}
 %> </span>

<%
	} else {
%>

<a href="/access/login?continue=<%=request.getAttribute("javax.servlet.forward.request_uri")?:'/'%>">
<span class="access signed-out hover-link">
	
	Sign In&nbsp;<img width="20px" src="/images/openid/openidico.png" />

</span>
	</a>
<%
	}
%>