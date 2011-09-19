<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	
	<title>Development Tracker${request.getAttribute('pageTitle')?" - "+request.pageTitle:""}</title>
	
	<link rel="shortcut icon" href="/images/favicon.ico">
	<link rel="icon" href="/images/favicon.ico">
	<script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>	
	<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>
	<link rel="stylesheet/less" type="text/css" href="/css/main.less">
	<script type="text/javascript" src="/js/less.min.js"></script>
	
	<!--[if IE]>
	    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	
	<script type="text/javascript">
	
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-107923-7']);
		_gaq.push(['_setDomainName', '.development-tracker.info']);
		_gaq.push(['_trackPageview']);
		
		(function() {
		  var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		  ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		  var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
		})();
	
	</script>

</head>
<body>