<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	
	<title>Development Tracker${request.getAttribute('pageTitle')?" - "+request.pageTitle:""}</title>
	
	<link rel="shortcut icon" href="/images/favicon.ico">
	<link rel="icon" href="/images/favicon.ico">
	
	<script src="/js/jquery-1.6.1.min.js"></script>
	<script src="/js/jquery-ui-1.8.13.custom.min.js"></script>
	<script src="http://cdn.jquerytools.org/1.2.5/jquery.tools.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="/css/smoothness/jquery-ui-1.8.13.custom.css" />
	<link rel="stylesheet/less" type="text/css" href="/css/main.less">
	<script src="/js/less.min.js"></script>
	
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