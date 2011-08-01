package access

if (session)session.userinfo = null

forward "/_ah/openid_logout?continue=${app.AppProperties.LAUNCH_URL}" 