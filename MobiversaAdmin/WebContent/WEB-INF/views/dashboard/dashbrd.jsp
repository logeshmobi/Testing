<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<%
response.setHeader("Content-Security-Policy", "default-src 'self'");
%>

<html lang="en"> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>    
   <link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resourcesNew1/dist/css/simple-chart.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,600|Lato:300, 400i,700,700i" rel="stylesheet">
    <style>
        body {  background-color:#eaeff5 }
        section { float:left;
         width:100%; 
        padding:10px; margin:40px 0;  
        background-color:#fff; 
        box-shadow:0 15px 40px rgba(0,0,0,.1) 
        }
        h1 { margin-top:150px; 
        text-align:center;}
        
        .sc-chart 
{
   /*  width: 500px !important; */
    height: 250px !important;
    margin: auto !important;
    display: table !important;
}
.sc-column {
float:inherit !important;
}
.sc-column .sc-item {width: 26% !important; }

.barchartsection{
border-radius:15px !important;
}

.hr-line{margin: 0;
    position: relative;
    top: -60px;  
    height:1px;
    background-color: #ffa500;}
    
  

@media screen and (max-width:768px) {

.sc-chart {
    width: 100% !important;}
    </style>
    <style>
 @-moz-document url-prefix() {
  
 .hr-line{
  top: -70px !important;  
		
  } 
  
  .sc-column .sc-canvas{
  height: 120px !important;
  	}
  }
</style>
  </head>

  <body>


<!-- <div class="container-fluid">    
  <div class="row">
  <div class="col s12">
     
          <div class="d-flex align-items-center">
          	
			<h5 style="color:#005baa">Dashboard </h5> 
          </div>

     <div class="sc-wrapper">
        <section class="sc-section barchartsection">
            <div class="column-chart"></div>
            <hr class="hr-line"/>
        </section> 
    </div></div> -->
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/resourcesNew1/dist/css/simple-chart.js"></script> 
   
  <script>
 	
        var merchant = ${totalMerchant};
    	  
    	  var devices = ${totalDevice};
    	  
    	var monthlyVolume=${totalTxn};
    
    	var  monthlyVolume1 = monthlyVolume/10000000; 
    	
        var labels = ["Merchant", "Devices", "Monthly Volume"];
        var values = [merchant, devices, monthlyVolume]; 
       /*  var outputValues = abbreviateNumber(values); */
        var outputValues=values;

        $('.column-chart').simpleChart({
            title: {
                text: '',
                align: 'center'
            },
            type: 'column',
            layout: {
                width: '100%',
                height: '250px'
            },
            item: {
                label: labels,
                value: values,	
                outputValue: outputValues,
                color: ['#005baa'],
                prefix: '',
                suffix: '',
                render: {
                    margin: 0.2,
                    size: 'relative'
                }
            }
        });

        $('.bar-chart').simpleChart({
            title: {
                text: '',
                align: 'center'
            },
            type: 'bar',
            layout: {
                width: '100%'
            },
            item: {
                label: labels,
                value: values,
                outputValue: outputValues,
                color: ['#005baa'],
                prefix: '',
                suffix: '',
                render: {
                    margin: 0,
                    size: 'relative'
                }
            }
        });

         

        
    </script> 
    <script>
 function load()
 {
	var url = "${pageContext.request.contextPath}/admin/getRecentTxn";
	
 $(location).attr('href',url);
 }
 </script> 
 <!--  <div class="col s12">
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
						
						<th>MID</th>
						<th>Type </th>
						<th>Amount</th>
						
					</tr>
			</thead>
              <tbody>
              <c:forEach items="${fiveTxnList.itemList}" var="dto">
					<tr>
						<td>${dto.txnDate}</td>
						<td>${dto.MID}</td>
						<td><span class="label label-info">${dto.txnType}</span></td>
						<td style="padding-left: 25px;" class="blue-grey-text text-darken-4 font-medium">${dto.amount}</td>
						
					</tr>
				</c:forEach>
				</tbody>
			</table>               
          </div>
          <a href="#" onclick="load()"><i class="fas fa-angle-right"></i> View Complete Report</a>
        </div>
      </div>
    </div> -->
	
	<style>
	td, th { padding: 0px 8px; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
     
    
    
     
  
 
   
  
</div></div>

</body> 
</html>