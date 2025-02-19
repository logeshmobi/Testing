<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en-US">
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
</script>

<script lang="JavaScript">




function loadDropDate11() {
	//alert("strUser.value");
	var e = document.getElementById("brand");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("brand1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}

function loadDropDate() {
	// alert("strUser.value"); 
	var e = document.getElementById("mid");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("mid1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("tid1").value);
 
}

function loadDropDate12() {
	//alert("strUser.value");
	var e = document.getElementById("cardtype");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("cardtype1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}

function changeStyle(id){
//alert("changeing --"+id.value+"--");
var field=id.value;
//alert(" changeing --"+field.length+"--");
//document.getElementById("mid").focus();
if(field.length!=0){
//alert(" changeing --"+field.length+"--");
id.style.border = "1px solid #3FCADB";
}else{
id.style.border = "1px solid #B5B9B9";
}
} 




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
<script>
function mainDet(){
	alert("maindet");
	var businessName=document.getElementById('businessName').value;
	alert("maindet--"+businessName+"---");
	var mainDiv=document.getElementById('mainDet');
	var mainDivt=document.getElementById('mainDett');
	if(businessName!=null && businessName!=''){
		alert("maindet1--"+businessName+"---");
		mainDiv.style.display='block';
		mainDivt.style.display='block';
		}else{
			alert("maindet0--"+businessName+"---");
			mainDiv.style.display='none';
			mainDivt.style.display='none';
		}
}
</script> 

<style>
.select2-dropdown li:first-child{
  display:none !important;
}
</style>

</head>


<body >

<div class="container-fluid">  
<div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Merchant Volume Summary</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
			<c:if test="${ responseEmptyMids!=null }">
			
  				<center>  
  					<p id="emtpyTID"> <h3 style="color:red;">* Empty MID Details.. Please Fill Anyone MID Details...</h3></p></center>
 			</c:if>
 			<c:if test="${responseEmptyDeviceID!=null }">
			
  				<center>  
  					<p id="emtpyDeviceID"> <h3 style="color:red;">* Empty DeviceID Details..</h3></p></center>
 			</c:if>
 			
		<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
					<h3 >Merchant Details</h3>
					</div>
					
						<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
							
							<div class="input-field col s12 m5 l5">
					   <select   id="merchantName" path="merchantName"
					    class="browser-default select-filter">									
								<!-- <optgroup label="Business Names" >  -->
								<option selected value=""><c:out value="Business Name"/></option>
								
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/agentVolume/listHotelMerchantvolumesummary?id=${merchant1.id}">
										${merchant1.businessName}
									
									</option>

								</c:forEach>
								</optgroup> 
							</select>
													</div>	
						
						<div class="input-field col s12 m5 l5">
						<c:if test="${responseErrorData  != null}">
							<H4 style="color: #ff4000;" align="center">${responseErrorData}</H4>
						</c:if>
						</div>
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>

      
        <script>
        $(document).ready(function(){
            
       
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
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}
ul.select2-results__options li:hover{
	/* overflow:scroll;   */
}
ul.select2-results__options li{
	max-height:250px;
	/* overflow:auto;   */
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	/* overflow: auto; */
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
} 

.dataTables_wrapper .dataTables_length{
	display:none !important;
}
.dataTables_wrapper .dataTables_filter {
	display:none !important;
}
#data_list_table_info{
display:none !important;
}

#data_list_table_paginate{
display:none !important;
}
</style>

							</div>
							<div class="cover-bar"></div>
							
						</div>	</div>
						</div></div></div>
	
         					
					<c:if test="${paginationBean !=null }">
					<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">
					         <div class="d-flex align-items-center">
					         <h3>Merchant Volume Summary</h3>
					         </div>
						<div class="table-responsive m-b-20 m-t-15">
           	<table id="data_list_table" class="table table-striped table-bordered">
						
						<c:set var="count" value="0" />
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<%-- <c:forEach begin="0" end="1" var="count"> --%>
						<c:if test="${count == 0}">
							<thead>
								<tr>
									<th >Merchant Name</th>
									<!-- <th >Txn_Type</th> -->

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
								<%-- <td>${dtolda.txnType}</td> --%>
								<c:forEach items="${dtolda.amount}" var="dto1">
									<td>${dto1}</td> 
								</c:forEach>
							</tr>

						</c:forEach>
						
						</tbody>
						
			
       
	
	</table>
	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
	</div>
	</div></div></div>
</div>
</c:if>

		



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
</div>
</body>

