<table border=1 cellspacing=0 cellpadding=2 id="${request.globalTrackerId}">
<tr><th colspan="2"><a href="http://localhost:8888/development/${request.development.key.id}">${request.localTrackerId}</a></th></tr>
<tr><td>Title</td><td id="${request.globalTrackerId}|title">${request.development.title}</td></tr>
<tr><td>Status</td><td id="${request.globalTrackerId}|status">${request.development.status}</td></tr>
</table>