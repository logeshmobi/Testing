<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page
	import="com.mobiversa.payment.controller.NonMerchantWebAddPromoController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script> -->
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">


<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
</head>
<body>


	<div class="pageWrap">

		<!-- Page content -->

<div style="overflow:auto;border:1px;width:100%">
				
				<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">EZYAds Summary  <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/promotionwebNonmerchant1/addMerchantPromotion">Create EZYAds <i class="fa fa-lg fa-plus"></i></a></h3>
<div class="card" style="width: 112.5rem;">
              <div class="card-body">
                <table class="table table-hover table-bordered" id="sampleTable">

				<thead>
					<tr>
					
			            <th>Created Date</th>
						<th>EZYAds Code</th>
						<th>EZYAds Name</th>
						<!-- <th>PromoDesc</th> -->
					
						<th>Status</th>
						<th>Valid From</th>
						<th>Valid To</th>
						<th>Action</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<tr>
                           <td><span><fmt:formatDate
										value="${dto.createdDate }" pattern="dd-MMM-yyyy" /></span></td>
							<%-- <td>${dto.createdDate }</td> --%>	
							<td>${dto.promoCode}</td>
                            <td>${dto.promoName}</td>
                           <%--  <td >${dto.promoDesc }</td> --%>
                        
							<td>${dto.status}</td>
							<%--  <td>${dto.validityDate}</td> --%>
							<%-- <td>${dto.validFrom}</td>
							<td>${dto.validTo}</td>
							 --%>
							<td><fmt:parseDate value="${dto.validFrom}" pattern="dd/MM/yyyy" var="myDate"/>
<fmt:formatDate value="${myDate}" pattern="dd-MMM-yyyy"/></td>
								

<td><fmt:parseDate value="${dto.validTo}" pattern="dd/MM/yyyy" var="myDate1"/>
<fmt:formatDate value="${myDate1}" pattern="dd-MMM-yyyy"/></td>
								
							<td>
							<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=NonMerchantWebAddPromoController.URL_BASE%>/detail" > 
	 
	       <input type="hidden" name="status" value="${dto.status}" />
	       <input type="hidden" name="id" value="${dto.id}" />
	        <%-- <input type="text" name="staus" value="${dto.status}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<center><button type="submit"   style="border-style:none;color:blue;" > 
				<i class="fa fa-pencil" aria-hidden="true"></i></button></center>
				</form>
							
							
							
							
							</td>	
								
								
								<%-- <c:choose>
										<c:when test="${dto.status =='PENDING'}">			
						<td><a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/promotionwebNonmerchant1/detail/${dto.id}"></a></td>
						
						</c:when>
						
						
							<c:when test="${dto.status =='SENT'}">			
						<td><a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/promotionwebNonmerchant1/detail/${dto.id}"></a></td>
						
						</c:when>
						
							<c:when test="${dto.status =='REPOST'}">			
						<td><a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/promotionwebNonmerchant1/detail/${dto.id}"></a></td>
						
						</c:when>  
						
						
						
						
							<c:when test="${dto.status =='APPROVED'}">			
						<td><a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/promotionwebNonmerchant1/detail/${dto.id}"></a></td>
						<img src="${pageContext.request.contextPath}/resourcesNew/img/edit.png"></a></td>
						
						</c:when>  
										<c:otherwise>
										<td></td>
	    								</c:otherwise>
										</c:choose> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
</div>
</div>
</div>
</div>
</div>

</body>
</html>