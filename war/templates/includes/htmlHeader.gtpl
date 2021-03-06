<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	
	<title>Development Tracker${request.getAttribute('pageTitle')?" - "+request.pageTitle:""}</title>
	
	<link rel="shortcut icon" href="/images/favicon.ico">
	<link rel="icon" href="/images/favicon.ico">
	
	<link rel="stylesheet" type="text/css" href="/css/fluid960gs/reset.css" media="screen" />
	
	<link rel="stylesheet" type="text/css" href="/css/fluid960gs/text.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="/css/fluid960gs/grid.css" media="screen" />
	
	<!--[if IE 6]><link rel="stylesheet" type="text/css" href="/css/fluid960gs/ie6.css" media="screen" /><![endif]-->
	<!--[if IE 7]><link rel="stylesheet" type="text/css" href="/css/fluid960gs/ie.css" media="screen" /><![endif]-->

	<link rel="stylesheet/less" type="text/css" href="/css/fluid.less">
	<link rel="stylesheet/less" type="text/css" href="/css/main.less">
	
	<link rel="stylesheet/less" type="text/css" href="/css/nav.less">
	<script type="text/javascript" src="/js/less.min.js"></script>
	
	<script type="text/javascript" src="/js/modernizr.min.js"></script>
	<script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>	
	<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="/js/jquery-fluid16.js"></script>
	<script type="text/javascript" src="/js/jquery.fittext.js"></script>
	
	<!--[if IE]>
	    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	
	<script type="text/javascript">
		/*
			Remember: there is more javascript in htmlFooter.gtpl!		
		*/
		
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