<%@page import="com.mobiversa.payment.controller.AgentController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

</head>
<body>

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Merchant Summary</strong></h3>
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
			
			
			   <th>Created Date</th>
				<th>Merchant Name</th>
				<th>Email</th>
				
				<th>Status</th>
				
				
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="merc">
				<tr>
				
				<td><fmt:formatDate value="${merc.createdDate}" pattern="dd-MMM-yyyy" /> </td>
					<td>${merc.businessName}</td>
					
					<td>${merc.email }</td>
					<%-- <td>${merc.contactPersonName}</td> --%>
					<td><span class="label label-success">${merc.status }</span></td>
					
					
               </tr>
			</c:forEach>
		</tbody>
	</table></div>
</div>
</div>
</div>
</div>
</div>


<style>
	td, th { padding: 7px 8px; color:#707070;}
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
