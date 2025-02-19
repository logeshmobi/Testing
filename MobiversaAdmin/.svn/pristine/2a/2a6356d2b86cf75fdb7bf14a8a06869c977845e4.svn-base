<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

</head>
<body>
<div class="pageWrap">
<div style="overflow:auto;border:1px;width:100%">   
    <div class="content-wrapper">
        
    
        <div class="row">
			
            <div class="col-md-12 formContianer">
            
            
              <h3 class="card-title">AgentMerchant Details  <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/superagent/transactionSummary/3">Back</a></h3>
<div class="card">
              
    <table class="table table-hover table-bordered" id="sampleTable">
						
						
<tbody>
<tr>
<td>AgentName</td>
<td>${agentName}</td>
</tr>
<tr>
<td>AgentCode</td>
<td>${agentCode}</td>
</tr>
<tr>
<td>City</td>
<td>${agentCity}</td>
</tr>
<tr>
<td>PhoneNo</td>
<td>${agentPhone}</td>
</tr>



</tbody>
</table>

</div>

</div>


 </div> 


 <div class="col-md-12 formContianer">
			 <div class="card" style="width: 90rem;">
					<table class="table table-hover table-bordered" id="sampleTable">
		<thead>
				<tr>
					<th>Merchant Name</th>
					<th>Merchant Address1</th>
					<!--<th>Merchant Address2</th> -->
					<th>City</th>
					<th>Post Code</th>
					<th>NO Of Terminals</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paginationBean.itemList}" var="dto">
					<tr>
						<!-- <td><a
							href="/transaction/merchantdetails/${dto.merchantName}~${dto.merchantAddr1}~${dto.merchantCity}~${dto.merchantPostcode}">${dto.merchantName}</a></td> -->
						<td>${dto.merchantName}</td>
						<td>${dto.merchantAddr1}</td>
						<!-- <td>${dto.merchantAddr2}</td> -->
						<td>${dto.merchantCity}</td>
						<td>${dto.merchantPostcode}</td>
						<td>${dto.noOfTid}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div></div>
		
</div>
</div>


</div>

</body>

		</html>
	