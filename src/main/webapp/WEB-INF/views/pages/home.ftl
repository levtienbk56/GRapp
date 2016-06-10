<#ftl encoding='UTF-8'>

<#include "../layout_base.ftl">
<#macro title> 
</#macro>

<#macro css_custom>
    <link href="/GRapp/resources/css/shop-item.css" rel="stylesheet">
    <link href="/GRapp/resources/css/custom.css" rel="stylesheet">
</#macro>
	
<#macro script_custom>
	<script src="/GRapp/resources/js/home.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCJjR5v4RZQBNl1CJJitVFskGzNupL8GiA&callback=initMap"
    async defer></script>
</#macro>
<#macro page_header>
	<#include "../static/header.ftl">
</#macro>

<#macro page_content>
	<!-- process bar -->
	<div class="progress-bar progress-bar-striped active center" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width:40%">
      <p>Processing...</p>
    </div>
	 <!-- Page Content -->
    <div class="container">

        <div class="row">
            <div class="col-md-3">
                <div class="caption-full">
                    <h4><p>File to upload</p></h4>
                    <form method="POST" enctype="multipart/form-data" action="/GRapp/process">
						<p><input type="file" name="file" /></p>
						<p><button type="submit"  class="btn btn-info" onClick="requestProcess()">Request</button><p>
					</form>
                </div>
            </div>

            <div class="col-md-9">
            	<div class="panel panel-default">
				  	<div class="panel-heading">
				    	<h3 class="panel-title">Map</h3>
				  	</div>
				  	<div class="panel-body">
				    	<!-- test map -->
						<div id="map" style="height:800px;"></div>
				  	</div>
				</div>
            </div>

        </div>

    </div>
    <!-- /.container -->
</#macro>

<#macro page_footer>
	<#include "../static/footer.ftl">
</#macro>


<!-- compile code to view -->
<@display/>