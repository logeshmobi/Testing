<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<html lang="en-US">
<head>

<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script> 
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->



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
 
thead th {
	text-align: center;
}
</style>

<!-- <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#mid1').select2();
		$('#merchantName').select2();
	});
</script> -->

<script lang="JavaScript">

$(document).ready(function() {
    $( '#sampleTable' ).dataTable();
} );

$(function(){
  // bind change event to select
  $('#merchantName').on('change', function () {
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


<body onclick="document.getElementById('emtpyTID').style.display='none';">

<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Product MDR Details</strong></h3>
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
					
							<div class="input-field col s12 m5 l5">
									<select  name="merchantName" class="browser-default select-filter"
								id="merchantName" path="merchantName" >
								<option selected value=""><c:out value="business Name"/></option>
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/MDR/proMDRListbyId?id=${merchant1.id}">
										${merchant1.businessName}~${merchant1.username}~${merchant1.role}
									
									</option>

								</c:forEach>
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
.select2-results ul{
	max-height:250px;

	curser:pointer;
}
.select2-results ul li:hover{
	background-color: #005baa !important;

	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
}

</style>					
							<input type="text" class="shownSearch" id="businessName" readonly
								Placeholder="businessName" value="${mobileUser.businessName }"/>
							</div>	</div>
						</div></div></div></div>
		
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
							<div class="table-responsive m-b-20 m-t-15">
            					<table id="data_list_table" class="table table-striped table-bordered">
									<thead align="center">
											
											<tr >
											<th>Created Date</th>
											<th>Mid</th>
											<th>Product Type</th>
											<th>Host MDR</th>
											<th>Mobi MDR</th>
											<th>Status</th>
											<th>Action</th> 

										</tr>
									</thead>
									
									<tbody id="prodReportTable">
										<c:forEach items="${paginationBean.itemList}" var="dto">
											<tr>
											<fmt:parseDate value="${dto.createdDate}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/>
			
													<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${myDate}" /></td>
													 <td>${dto.mid}</td> 
													<td>${dto.prodType}</td>
													<td>${dto.hostMdr}</td>
													<td>${dto.mobiMdr}</td>	
													<td>${dto.status}</td>										
											<td align="center"><a 
											href="${pageContext.request.contextPath}/MDR/updateProMDR/${dto.mid}/${dto.prodType}">
											<i class="material-icons">create</i></a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>								
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
			
<script type="text/javascript">
	jQuery(document).ready(function() {
		$('#merchantName').select2();
	});
</script>
</body>

