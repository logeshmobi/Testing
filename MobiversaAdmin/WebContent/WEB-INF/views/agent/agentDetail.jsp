<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>

<%@page import="com.mobiversa.common.bo.Agent"%>
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


	 <style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
     

</head>

<body>

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Agent Summary</strong></h3>
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
          		<thead>
								<tr>

									<th>Activate Date</th>
									<th>Agent name</th>
									<th>Ag Code</th>
									<th>Ag Type</th>
									<th>Bank Name</th>
									<th>Status</th>

									<th>Edit</th>
									<!-- <th>Suspension Date</th>
				<th>Edit</th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${paginationBean.itemList}" var="dto">
									
										
                                    <tr>
                                            
                                 <td><fmt:formatDate value="${dto.activateDate }"
                                                pattern="dd/MM/yyyy" /></td>
										
										<td>${dto.firstName}</td>
										<td>${dto.code}</td>
										<td>${dto.agType}</td>
										<td>${dto.bankName}</td>
										<td>${dto.status }</td>

										<%-- <td><fmt:formatDate value="${dto.suspendDate }" pattern="dd-MMM-yyyy" /></td>  --%>
										<%-- <td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}/agent1/detail/${dto.id}"></a></td> --%>
											<td><a href="${pageContext.request.contextPath}/agent1/detail/${dto.id}"><i class="material-icons">create</i></a></td>
									</tr>
								</c:forEach>
							</tbody>
							</table>
							</div>
							</div></div></div></div>

</div>
<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

$(document).ready(function() {
    $('#data_list_table').DataTable( {
        columnDefs: [
            {
                targets: [ 0, 1, 2 ],
                className: 'mdl-data-table__cell--non-numeric'
            }
        ]
    } );
} );

</script>


</body>
</html>
