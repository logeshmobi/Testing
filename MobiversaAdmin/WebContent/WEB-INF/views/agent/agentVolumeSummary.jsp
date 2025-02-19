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
  
   <style>
.error {
	color: red;
	font-weight: bold;
}

</style>
</head>
</head>




<body >

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Agent Volume Summary </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
						 <div class="table-responsive m-b-20 m-t-15">
           	<table id="data_list_table" class="table table-striped table-bordered">
						
							<c:set var="count" value="0" />
							<c:forEach items="${paginationBean.itemList}" var="dto">

								<c:if test="${count == 0}">
								
								<thead>
						<tr>
							<th >Agent Name</th>
									<c:forEach items="${dto.date}" var="dtold">

										<th >${dtold}</th>

									</c:forEach>
									<c:set var="count" value="1" />
								</c:if>

							</c:forEach>
					<tbody>
							<c:forEach items="${paginationBean.itemList}" var="dtolda">

								<tr>
								<c:choose>
								<c:when test="${dtolda.txnPresent == 'Yes'}">
								<td>${dtolda.agentName}</td>
								<%-- <td><a href="${pageContext.request.contextPath}/agentVolume/merchantvolume/${dtolda.agentDet}/1" style="color:#4bae4f">${dtolda.agentName}</a></td> --%>
								</c:when> 
								<c:when test="${dtolda.txnPresent == 'No'}">
								<td>${dtolda.agentName}</td>
								</c:when> 
								</c:choose>
							
								<c:forEach items="${dtolda.amount}" var="dto1">
									<td>${dto1}</td>
								</c:forEach>

							</c:forEach> 
					</tbody>
					
					</table>
					</div>
	
	</div>
	</div>
	</div>
	
	</div>
</div>

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

			<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

$(document).ready(function() {
    $('#data_list_table').DataTable( {
    	"bSort" : false
    } );
} );

</script>




</body>
</html>
