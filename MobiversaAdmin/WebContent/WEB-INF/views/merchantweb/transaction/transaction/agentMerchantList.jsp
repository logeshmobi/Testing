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
 
 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>


</head>
<body class="">

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white"><strong>Transaction List </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    

    
     <div class="row">
	  <div class="col s12">
	      <div class="card border-radius">
	        <div class="card-content padding-card">
	        <div class="d-flex align-items-center" style="width:25%">
	        	<a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/transaction/list">Back</a>
	    </div>
		
            
              
<div class="table-responsive m-b-20 m-t-15">
<table class="">
						
<tbody>
<tr>
<td>Agent Name</td>
<td>${agentName}</td>
</tr>
<tr>
<td>Agent Code</td>
<td>${agentCode}</td>
</tr>
<tr>
<td>City</td>
<td>${agentCity}</td>
</tr>
<tr>
<td>Phone No</td>
<td>${agentPhone}</td>
</tr>



</tbody>
</table>

</div>

</div>


 </div> </div></div>



		
					<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">

								<div class="table-responsive m-b-20 m-t-15">
					            <table id="data_list_table" class="table table-striped table-bordered">
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
</div></div>
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
	