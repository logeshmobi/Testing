<%@page import="com.mobiversa.payment.controller.AgentVolumeController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ page import="java.io.*,java.util.*" %>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

   
</head>




<body >

<div style="overflow:auto;border:1px;width:100%">
<div class="content-wrapper">
	<div class="row">

			<div class="col-md-12 formContianer">
				<h3 class="card-title">
					Merchant Volume Summary <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/agentVolume/list">Back</a></h3>
				
				<div class="card" style="width: 90rem;">
					<div class="card-body">
						<table class="table table-hover table-bordered" id="sampleTable">
						
						
						
						<c:set var="count" value="0" />
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<%-- <c:forEach begin="0" end="1" var="count"> --%>
						<c:if test="${ count == 0}">
							<thead>
								<tr>
									<th >Merchant Name</th>

									<c:forEach items="${dto.date}" var="dtold">
										<th >${dtold}</th>

									</c:forEach>
								</tr>
							</thead>
							<c:set var="count" value="1" />
						</c:if>
						<%-- </c:forEach> --%>
					</c:forEach>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dtolda">
							<tr>
								<td>${dtolda.agentName}</td>
								<c:forEach items="${dtolda.amount}" var="dto1">
									<td style="text-align:right;">${dto1}</td>
								</c:forEach>
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
</body>
</html>




















