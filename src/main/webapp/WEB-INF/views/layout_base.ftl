<#ftl encoding='UTF-8'>
<#import "/spring.ftl" as spring/>

<!-- meta data -->
<#macro meta>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
 </#macro>
 
<!-- title-->
<#macro title>
	<title>GR app demo</title>
</#macro>

<!-- global css-->
<#macro css_global>
	<!-- Bootstrap Core CSS -->
    <link href="/GRapp/resources/css/bootstrap.min.css" rel="stylesheet">
</#macro>

<!-- custom css -->
<#macro css_custom>
</#macro>

<#macro html_custom>
</#macro>

<!-- page content-->
<#macro page_header></#macro>
<#macro page_content></#macro>
<#macro page_footer></#macro>

<!-- global script -->
<#macro script_global>
	<!-- jQuery -->
    <script src="/GRapp/resources/js/jquery.js"></script>
   	<!-- Bootstrap Core JavaScript -->
    <script src="/GRapp/resources/js/bootstrap.min.js"></script>
</#macro>

<!-- custom script-->
<#macro script_custom></#macro>

<!-- navbar as menu in admin page -->
<#macro navbar></#macro>

<#macro display> 
	<!DOCTYPE html>
	<html>
	<head>
		<@meta/>
    	<@title/>
		<@css_global/>    
		<@script_global/>
		<@css_custom/>
	</head>
	<body>
		<@html_custom/>
		<@page_header/>
		<@navbar/>
		<@page_content/>
		<@page_footer/>
		<@script_custom/>
	</body>
	</html>
</#macro>