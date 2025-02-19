<%@page
	import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
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

<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");
	});
</script>

<style>
	td, th { padding: 7px 8px;color:#707070; }
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
           <h3 class="text-white">  <strong> Reader Summary  </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
	
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
  
           
		   <div class="table-responsive m-b-20 m-t-15" style=" overflow: auto;">
		   
		   
          
           	<table id="data_list_table" class="table table-striped table-bordered">

								<thead>
									<tr>
										<th>Device Id</th>
										<th>TID</th>
										<th>Device Type</th>
										<th align="right" width="10px">DeviceHolder Name</th>
										<!-- <th>Ref No </th> -->
										<th>Mobile UserName</th>
										<th>Status</th>
										<th>Activation Date</th>
										<th>ExpiryDate</th>
										<th>Action</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr>

											<td>${dto.deviceId}</td>
											<td>${dto.tid}</td>
											<td>${dto.deviceType}</td>
											<td>${dto.deviceHolderName}</td>
											<%-- <td>${dto.refNo}</td> --%>

											<td>${dto.mobileUserName}</td>
											<td><span class="label label-success">${dto.status}</span></td>
											<td>${dto.activationDate}</td>
											<td>${dto.expiryDate}</td>

											<td>
												<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}/readerweb/detail" > 
	 
	       <input type="hidden" name="deviceId" value="${dto.deviceId}" />
	      			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<center><button type="submit" class="editBtn" > 
				<i class="material-icons">create</i></button></center>
				</form>
			<style>
			.editBtn{
			color: #039be5;	
			background:none !important;		
    		text-decoration: none !important;
    		border:none;
    		-webkit-tap-highlight-color: transparent;
			}
			</style>									
												
												
												</td>
											
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
