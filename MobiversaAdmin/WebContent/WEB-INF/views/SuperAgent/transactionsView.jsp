<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
<%@page import="com.mobiversa.common.bo.Agent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

 
</head>
 <body>
<div class="pageWrap">  
<div style="overflow:auto;border:1px;width:100%"> 
    <div class="content-wrapper">
        
    
        <div class="row">
			
            <div class="col-md-12 formContianer">
            
            
              <h3 class="card-title">Transaction List  <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/superagent/transactionSummary/3">Back</a></h3>
<div class="card">
              
    <table class="table table-hover table-bordered" id="sampleTable">
						
						<tbody>
						<tr>
						<td>Merchant Name</td>
						<td>${namedd}</td>
						</tr>
						<tr>
						<td>State</td>
						<td>${nameyy}</td>
						</tr>
						</tbody>
						</table>
						
			</div></div></div>			
				
		
		  <div class="col-md-12 formContianer">
			  <div class="card" style="width: 90rem;">
					<table class="table table-hover table-bordered" id="sampleTable">
					<thead>
						<tr>
							<th>Date</th>
							<th>Time</th>
							<th>TID</th>
							<th>Status</th>
							<th>Location</th>
							<!-- <th align="right" width="25">Amount(RM)</th> -->
							<th>Amount(RM)</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
								<td>${dto.date}</td>
								<td>${dto.time}</td>
								<td>${dto.tid}</td>
								<td>${dto.status}</td>
								<td>${dto.location}</td>
								<%-- <td style="padding: 14px;" align="right" class="separate 10px;">${dto.amount}</td> --%>
								<td style="text-align:right;">${dto.amount}</td>
									<c:choose>
										<c:when test="${dto.status =='SETTLED'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when> 
										<c:when test="${dto.status =='COMPLETED'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when> 
										<c:when test="${dto.status =='VOID'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when>  
										<c:when test="${dto.status =='CASH'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when>  
										<c:otherwise>
	    									<td></td>
	    								</c:otherwise>
	    							</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
	</div>
		</div>	
	