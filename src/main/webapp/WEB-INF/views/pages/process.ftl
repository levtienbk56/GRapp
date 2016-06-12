<#ftl encoding='UTF-8'>

<#include "../layout_base.ftl">
<#macro title> 
</#macro>

<#macro css_custom>
    <link href="/GRapp/resources/css/shop-item.css" rel="stylesheet">
	<link href="/GRapp/resources/css/custom.css" rel="stylesheet">
</#macro>
	
<#macro script_custom>
	<script src="/GRapp/resources/js/map.js"></script>
	<script src="/GRapp/resources/js/process.js"></script>
</#macro>
<#macro page_header>
	<#include "../static/header.ftl">
</#macro>

<#macro page_content>
	 <!-- Page Content -->
    <div class="container">
        <div class="row">
            <div class="col-md-10">
				<div class="panel panel-primary" id="section1">
				  	<div class="panel-heading">
				    	<h3 class="panel-title"><b>Staypoint Extraction</b></h3>
				  	</div>
				  	<div class="panel-body">
				    	<!-- test map -->
						<div id="map" style="height:750px;"></div>
				  	</div>
				</div>
				
				<div class="panel panel-primary"  id="section2">
				  	<div class="panel-heading">
				    	<h3 class="panel-title"><b>Reverse Geocoding</b></h3>
				  	</div>
				  	
				  	<div class="panel-body tablescroll">
						<table class="table" id="label-of-staypoint">
						    <thead>
						      <tr>
						        <th class=" text-left">Time</th>
						        <th class=" text-left">Coordinate</th>
						        <th class=" text-left">labels</th>
						      </tr>
						    </thead>
						    <tbody>
						      <tr class="anchor"></tr>
						    </tbody>
						 </table>
				  	</div>
				</div>
				
				<div class="panel panel-primary"  id="section3">
				  	<div class="panel-heading">
				    	<h3 class="panel-title"><b>Sequence Creation</b></h3>
				  	</div>
				  	
				  	<div class="panel-body tablescroll">
						<table class="table" id="sequence">
						    <thead>
						      <tr>
						        <th class="col-lg-1 text-center">#</th>
						        <th class="col-lg-10 text-left">Sequence</th>
						      </tr>
						    </thead>
						    <tbody>
						        <tr class="anchor"></tr>
						    </tbody>
						 </table>
				  	</div>
				</div>
				
				<div class="panel panel-primary"  id="section4">
				  	<div class="panel-heading">
				    	<h3 class="panel-title"><b>Sequence Pattern Mining</b></h3>
				  	</div>
				  	
				  	<div class="panel-body tablescroll">
						<table class="table" id="sequence-pattern">
						    <thead>
						      <tr>
						        <th>#</th>
						        <th>Support Count</th>
						        <th>Sequential Pattern</th>
						      </tr>
						    </thead>
						    <tbody>
						        <tr class="anchor"></tr>
						    </tbody>
						 </table>
				  	</div>
				</div>
            </div>

        </div>
		<!-- /.row -->
    </div>
    <!-- /.container -->
</#macro>

<#macro page_footer>
	<#include "../static/footer.ftl">
</#macro>


<!-- compile code to view -->
<@display/>