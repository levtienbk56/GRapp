<#ftl encoding='UTF-8'>

<#include "../layout_base.ftl">
<#macro title> 
</#macro>

<#macro css_custom>
    <link href="/GRapp/resources/css/shop-item.css" rel="stylesheet">
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
				  	
				  	<div class="panel-body">
						<table class="table" id="label-of-staypoint">
						    <thead>
						      <tr>
						        <th class="col-lg-2 text-left">Time</th>
						        <th class="col-lg-2 text-left">Coordinate</th>
						        <th class="col-lg-8 text-left">labels</th>
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
				  	
				  	<div class="panel-body">
						<table class="table" id="sequence">
						    <thead>
						      <tr>
						        <th class="col-lg-2 text-left">Date</th>
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
				  	
				  	<div class="panel-body">
						<table class="table" id="sequence-pattern">
						    <thead>
						      <tr>
						        <th>Firstname</th>
						        <th>Lastname</th>
						        <th>Email</th>
						      </tr>
						    </thead>
						    <tbody>
						      <tr>
						        <td>John</td>
						        <td>Doe</td>
						        <td>john@example.com</td>
						      </tr>
						    </tbody>
						 </table>
				  	</div>
				</div>
				
                <div class="thumbnail">
                    <img class="img-responsive" src="http://placehold.it/800x300" alt="">
                    <div class="caption-full">
                        <h4 class="pull-right">$24.99</h4>
                        <h4><a href="#">Product Name</a>
                        </h4>
                        <p>See more snippets like these online store reviews at <a target="_blank" href="http://bootsnipp.com">Bootsnipp - http://bootsnipp.com</a>.</p>
                        <p>Want to make these reviews work? Check out
                            <strong><a href="http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/">this building a review system tutorial</a>
                            </strong>over at maxoffsky.com!</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>
                    </div>
                    <div class="ratings">
                        <p class="pull-right">3 reviews</p>
                        <p>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            4.0 stars
                        </p>
                    </div>
                </div>

                <div class="well">

                    <div class="text-right">
                        <a class="btn btn-success">Leave a Review</a>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">10 days ago</span>
                            <p>This product was great in terms of quality. I would definitely buy another!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">12 days ago</span>
                            <p>I've alredy ordered another one!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">15 days ago</span>
                            <p>I've seen some better than this, but not at this price. I definitely recommend this item.</p>
                        </div>
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