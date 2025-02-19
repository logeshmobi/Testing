<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en"> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>    
   
  </head>
  <body>


<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
     
          <div class="d-flex align-items-center">
          
			<h5 style="color:#fff">Dashboard </h5> 
          </div>

    </div>
	
	<script src="${pageContext.request.contextPath}/resourcesNew1/dist/barchart/js/jquery.js"></script> 
    <script>
	$(document).ready(function() {
		var merchant = ${totalMerchant};
  	  
  	  var devices = ${totalDevice};
  	  
  	var monthlyVolume=${totalTxn};
  	var  monthlyVolume1 = monthlyVolume/10000000;
	var exampleBarChartData = {
		"datasets": {
			"values": [merchant, devices, monthlyVolume1],
			"labels": [
				"Merchant", 
				"Device", 
				"Monthly Value"
				 
			],
			"color": "blue"
		},
		"title": "  Chart",
		"noY": true,
		"height": "300px",
		"width": "500px",
		"background": "#FFFFFF",
		"shadowDepth": "1"
	};

	MaterialCharts.bar("#bar-chart-example", exampleBarChartData) 
});
	</script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resourcesNew1/dist/barchart/css/material-charts.css">
    <script src="${pageContext.request.contextPath}/resourcesNew1/dist/barchart/js/material-charts.js"></script> 
				
 <div class="col s12"> 
<div class="example-chart">
    <div id="bar-chart-example"></div>
 </div>
 <script>
 function load()
 {
	var url = "${pageContext.request.contextPath}/admin/getRecentTxn";
 $(location).attr('href',url);
 }
 </script> 
  

    <div class="col s12">
      <div class="card dashboardCard">
        <div class="card-content">
          <div class="d-flex align-items-center">
            <div>
              <h5 class="card-title" style="color:#105fa5;margin-bottpm:0px;">Last 5 Transactions</h5> 
            </div> 
          </div>
          <div class="table-responsive m-b-20 m-t-15">
            <table class="">
            <thead>
					<tr>
						<th>Transaction Date and Time</th>
						<!-- <th>Merchant Name</th> -->
						<th>MID</th>
						<th>Type </th>
						<th>Amount</th>
					</tr>
			</thead>
              <tbody>
              <c:forEach items="${fiveTxnList.itemList}" var="dto">
					<tr>
						<td>${dto.txnDate}</td>
						<%-- <td>${dto.merchantName}</td> --%>
						<td>${dto.MID}</td>
						<td><span class="label label-info">${dto.txnType}</span></td>
						<td class="blue-grey-text text-darken-4 font-medium">${dto.amount}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>               
          </div>
          <a href="#" onclick="load()"><i class="fas fa-angle-right"></i> View Complete Report</a>
        </div>
      </div>
    </div>
	
	<style>
	td, th { padding: 0px 8px; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
     
    </div>
    
     
  </div>
   
  
</div>
  
  
<div class="chat-windows "></div>
</body> 
</html>