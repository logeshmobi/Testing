<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew/js/circle-progress.js"></script>
  <script src="${pageContext.request.contextPath}/resourcesNew/js/examples.js"></script>
 <script  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
    
     <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.1.6/Chart.js"></script>
     
     
    
    
    
    
   <!--    <script type="text/javascript">
     $(document).ready(function(){
     	var data = {
     		//labels: ["January", "February", "March", "April", "May"],
     		labels: [${threeMonthTxn.month}],
     		datasets: [
     			{
     				label: "My First dataset",
     				
     				//fillColor: "rgba(220,220,220,0.2)",
     				fillColor:"#005baa",
     				strokeColor: "rgba(220,220,220,1)",
     				pointColor: "rgba(220,220,220,1)",
     				pointStrokeColor: "#fff",
     				pointHighlightFill: "#fff",
     				pointHighlightStroke: "rgba(220,220,220,1)",
     				//data: [65, 59, 80, 81, 56]
     				data:[${threeMonthTxn.amountData}]
     			},
     			{
     				label: "My Second dataset",
     				//fillColor: "rgba(151,187,205,0.2)",
     				//fillColor:"#6dcff6",
     				fillColor:"#6dcff6",
     				strokeColor: "rgba(151,187,205,1)",
     				pointColor: "rgba(151,187,205,1)",
     				pointStrokeColor: "#fff",
     				pointHighlightFill: "#fff",
     				pointHighlightStroke: "rgba(151,187,205,1)",
     				//data: [28, 48, 40, 19, 86]
     				data: [ ${threeMonthTxn.countData}]
     			}
     		]
     	};
     	var ctxl = $("#barChartDemo").get(0).getContext("2d");
     	var lineChart = new Chart(ctxl).Bar(data);
     
     	
     });
   </script> -->
   
   <%--  var totalDevice = ${totalDevice};
    	  var device1= totalDevice/1800;
    	  var monthlyVolume =${totalTxn};
    	var volume1=  monthlyVolume/19000000;
    --%>
   <script type="text/javascript">
      $(document).ready(function(){
    	  
     var merchant = ${totalMerchant};
    	  
    	  var devices = ${totalDevice};
    	  var data = merchant/200;
    	  var data1= devices/1800;
    	  var monthlyVolume= ${totalTxn};
    	  var monthlyVolume1= monthlyVolume/50000;
		  
        /*  var merchant = ${totalMerchant}/1800;
    	  
    	  var devices = ${totalDevice};
    	  var monthlyVolume= ${totalTxn}; */
     $('.gr1.circle').circleProgress({
		value: data
	  }).on('circle-animation-progress', function(event, progress) {
		$(this).find('strong').html(Math.round(merchant * progress) + '<span>Merchant</span>');
	  });
	  
	  $('.gr2.circle').circleProgress({
		value: data1
	  }).on('circle-animation-progress', function(event, progress) {
		$(this).find('strong').html(Math.round(devices * progress) + '<span>Devices</span>');
	  });
	  
	  $('.gr3.circle').circleProgress({
		value: monthlyVolume1
	  }).on('circle-animation-progress', function(event, progress) {
		$(this).find('strong').html(Math.round(monthlyVolume * progress) + '<span>Monthly Volume</span>');
	  });
	  
	  
	 });

 </script>
 
<body>

		
		
		
		

 <div class="content-wrapper">
        
        
        <div class="row">
          
          <div class="col-md-10 circleBlock">
            <div class="card">
              <div class="card-body">
                
                <div class="circles">
    
    
    <div class="gr1 circle">

      <strong></strong>
      
    </div>

    <div class="gr2 circle">
  
      <strong></strong>
    </div>
    
    <div class="gr3 circle">
     <strong></strong>
   </div>
    

   
    
  </div>
                
                
              </div>
            </div>
          </div>
          
          <div class="col-md-10 chartBlock">
            <div class="card">
              <h3 class="card-title">Transaction</h3>
              <div class="card-body">
                <canvas class="" id="barChartDemo" style="height: 300px;"></canvas>
              </div>
            </div>
          </div>
          
     <script>
var ctx = document.getElementById('barChartDemo').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'horizontalBar',
    data: {
       labels:[${threeMonthTxn.month}],
        datasets: [{
            label: 'Volume',
            fillColor:"#005baa",
     		strokeColor: "rgba(220,220,220,1)",
			pointColor: "rgba(220,220,220,1)",
			pointStrokeColor: "#fff",
			pointHighlightFill: "#fff",
			pointHighlightStroke: "rgba(220,220,220,1)",
        // data:[92090,95000,91000],
         data: [${threeMonthTxn.amountData}],
            backgroundColor: [
                '#005baa',
                '#005baa',
                '#005baa'
               
            ],
            borderColor: [
                'rgba(220,220,220,1)',
                'rgba(220,220,220,1)',
                'rgba(220,220,220,1)'
               
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
         	xAxes: [{
                // Change here
               
            	barPercentage: 0.7,
            	stacked: true,ticks: {
                                    min: 0,
                                    max: ${max},
                 					stepSize: ${stepsize},
                                    beginAtZero: true
                                },
	            gridLines: {
			        display: true,
			        lineWidth: 1,
			       // color: "yellow",
			        // offsetGridLines: true
			        
			        },
			barThickness: 6,
            maxBarThickness: 10,
            minBarLength: 9
            	//barPercentage: 0.5,
            /* barThickness: 6,
            maxBarThickness: 8,
            minBarLength: 2,
            gridLines: {
                offsetGridLines: true
            } */
            	}],
         	yAxes: [{
                ticks: {
                    beginAtZero: true
                },
                 gridLines: {
			        display: true,
			        lineWidth: 1,
			         offsetGridLines: true,
			       // color: "yellow"
			        
			        }
           	 }] },
        legend: {
        	display: false
   				 },
    		tooltips: {
        		callbacks: {
           			label: function(tooltipItem) {
                 	 return tooltipItem.xLabel;
          		 		}
        			}
    			}
    		}	
});
</script>
        </div>
</div>		
			<%-- 	<!-- Statsbar -->
				<div class="statsBar">
					<div class="row">
					<div class="col-xs-12 col-md-4 i green">
							<a href="${pageContext.request.contextPath}/agent2/merchantSummaryList" title="Total Merchant" class="c">
								<h3 class="title">Total Merchant </h3>
								<div class="num">${totalMerchant}</div>
								<!-- <i class="icon zmdi zmdi-card"></i> -->
								<i class="icon zmdi zmdi-accounts-add zmdi-hc-fw"></i>
							</a>
						</div>
						<div class="col-xs-12 col-md-4 i yellow">
							<a  title="Monthly Volume" class="c"> 
								<h5 class="title">Total Device </h5>
								<div class="num">${totalDevice}</div>
								<!-- <i class="icon zmdi zmdi-shopping-basket"></i> -->
								<i class="icon zmdi zmdi-devices zmdi-hc-fw"></i>
							</a>
						</div>
						<div class="col-xs-12 col-md-4 i pink">
							<a href="${pageContext.request.contextPath}/agentVolume/list" title="Monthly Volume" class="c">
								<h5 class="title">Monthly Volume</h5>
								<div class="num">${totalTxn}</div>
								<!-- <i class="icon zmdi zmdi-shopping-cart"></i> -->
								<i class="icon zmdi zmdi-trending-up zmdi-hc-fw"></i> 
							</a>
						</div>
 						
						<div class="col-xs-12 col-md-4 i green"> 
							<a href="#" title="#" class="c">
								<h3 class="title">Mobiversa Cashback </h3>
								<div class="num">${cnt} - ${amt}</div>
								<i class="icon zmdi zmdi-card"></i>
							</a>
						</div>
					</div>
				</div>
				</div>
		<!--	</div>
		</div>
	 </div> --> --%>
	
	</body>
</html>