<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en-US">
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<style>
  
  input:focus { 
    outline: none !important;
    border:1px solid red;
    box-shadow: 0 0 10px #719ECE;
}
</style>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>

<script type="text/javascript">
	jQuery(document).ready(function() {
	/* 	$('#mid1').select2(); */
		$('#merchantName').select2();
	});
</script>

<script lang="JavaScript">

/* function loadSelectData(){ 
	
	
	var mid = document.getElementById("mid").value;
	var chBack = document.getElementById("chBack").value;
	
		if (mid == '' || mid == null) {
			alert("please select MID");
			document.getElementById("mid").focus();
			return false;

		}

		if (chBack == null || chBack == '') {

			alert("Please Fill Charge Back Amount");
			document.getElementById("chBack").focus();
			return false;
		}
		

		return true;

	} */
	
	
$(function(){
	  // bind change event to select
	  $('#merchantName'). on('change', function () {
	      var url = $(this).val(); // get selected value
	      //alert(url);
	      if (url) { // require a URL
	          window.location = url; // redirect
	         // alert(window.location);
	      }
	      return false;
	  });
	});

   
</script>



</head>

<body>
	<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Charge Back </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
 <div class="row">
<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
						<div class="row">
							<div class="input-field col s12 m5 l5">
									<select  name="merchantName" class="browser-default select-filter"
										id="merchantName" path="merchantName">
										<optgroup label="Business Names" style="width: 100%">
											<option selected value=""><c:out
													value="business Name" /></option>

											<c:forEach items="${merchant1}" var="merchant1">
												<option
													value="${pageContext.request.contextPath}/MDR/chargeProMID?id=${merchant1.id}">
													${merchant1.businessName}~${merchant1.username}~${merchant1.role}

												</option>

											</c:forEach>
										</optgroup>
									</select>
															</div>	
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

        <!-- Script -->
        <script>
        $(document).ready(function(){
            
            // Initialize select2
            $("#selUser").select2();
            $(".select-filter").select2();
            
            
        });
        </script>
        
        
					
							<div class="input-field col s12 m3 l3 select-search">
									
<style>
.select2-dropdown {    border: 2px solid #2e5baa; }
.select2-container--default .select2-selection--single {border:none;}
 .select-search .select-wrapper input {
	display:none !important;
}

.select-search .select-wrapper input {
	display:none !important;
}
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

ul.select2-results__options li{
	max-height:250px;
	
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
}

</style>
									 <input type="text" class="shownSearch" id="businessName"
										readonly Placeholder="businessName"
										value="${mobileUser.businessName }" />
								</div>
							</div>
						</div>
					</div>
				</div></div>
				</div>
				

				<c:if test="${mobileUser.businessName !=null }">
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">         
		   <div class="table-responsive m-b-20 m-t-15">    
           	<table id="data_list_table" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Date</th>
										<th>MID</th>
										<th>Status</th>
										<th>Amount</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr>

											<fmt:parseDate value="${dto.timeStamp}"
												pattern="yyyy-MM-dd HH:mm:ss" var="myDate" />

											<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${myDate}" /></td>
											<td>${dto.mid}</td>

											<c:if test="${dto.status =='ACTIVE'}">
												<td>NOT SETTLED</td>
											</c:if>

											<c:if test="${dto.status =='TERMINATED'}">
												<td>SETTLED</td>
											</c:if>

											<td>${dto.mobiMdr}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div></div></div></div>
				</c:if>
				
				<c:if test="${mobileUser.businessName ==null }">
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">         
		   <div class="table-responsive m-b-20 m-t-15">    
           	<table id="data_list_table" class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Date</th>
										<th>Buisness Name</th>
										<th>MID</th>
										<th>Status</th>
										<th>Type</th>
										<th>Actual Amount</th>
										<th>Payable Amount</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr>

											<fmt:parseDate value="${dto.activateDate}"
												pattern="yyyy-MM-dd HH:mm:ss" var="myDate" />

											<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${myDate}" /></td>
													
											<td>${dto.businessName}</td>
											<td>${dto.mid}</td>

											<c:if test="${dto.status =='ACTIVE'}">
												<td>NOT SETTLED</td>
											</c:if>

											<c:if test="${dto.status =='TERMINATED'}">
												<td>SETTLED</td>
											</c:if>
											<td>${dto.deviceType}</td>

											<td>${dto.ch_amount}</td>
											<td>${dto.repayableAmt}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>

					</div></div></div></div>
				</c:if>
 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

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
