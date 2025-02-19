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
 
thead th {
	text-align: center;
}
</style>

	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

<script lang="JavaScript">

function loadDropDate11() {
	//alert("strUser.value");
	var e = document.getElementById("brand");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("brand1").value = strUser;
	//alert("data :" + strUser + " "+ document.getElementById("status1").value);

}

$(document).ready(function() {
    $( '#sampleTable' ).dataTable();
} );

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

function loadSelectData(){ 
	
	var expiryDate = document.getElementById("expiryDate").value;
		if (expiryDate == '' || expiryDate == null) {
			alert("please fill Expiry Date");
			document.getElementById("expiryDate").focus();
			return false;

		}
		var renewalPeriod = document.getElementById("renewalPeriod").value;

		if (renewalPeriod == null || renewalPeriod == '') {

			alert("Please Fill Renewal Period");
			document.getElementById("renewalPeriod").focus();
			//form.submit = false; 
			return false;
		}
		var businessName = document.getElementById("businessName").value;
		if (businessName == '' || businessName == null) {

			alert("Please Fill BusinessName..");
			document.getElementById("businessName").focus();
			return false;

		}

		var contactName = document.getElementById("contactName").value;
		if (contactName == '' || contactName == null) {
			alert("Please Fill ContactName..");
			document.getElementById("contactName").focus();
			return false;

		}
		
		var tid = document.getElementById("tid").value;
		
		var motoTid = document.getElementById("motoTid").value;
		
		var ezypassTid = document.getElementById("ezypassTid").value;
		
		var ezyrecTid = document.getElementById("ezyrecTid").value;
		
		
		var ezywayTid = document.getElementById("ezywayTid").value;
		
		
		
		if((tid == '' || tid == null) && (motoTid == '' || motoTid == null) && 
				(ezypassTid == '' || ezypassTid == null) && (ezywayTid == '' || ezywayTid == null) &&
				(ezyrecTid == '' || ezyrecTid == null)){
			alert("No TID to Register..Please Fill Anyone TID Details..");
			return false;
		}
		
		
		

		
		var e = document.getElementById("referenceNo").value;
		var deviceId = document.getElementById("deviceId").value;

		if (tid != '' && tid != null) {
			//alert("check tid valid");
			 if (!allnumeric(document.form1.tid, 8, 8)) {
				document.getElementById("tid").focus();
				return false;
			} 

			if (deviceId != '' && deviceId != null) {
				if (!alphanumeric(document.form1.deviceId, 15, 16)) {
					document.getElementById("deviceId").focus();
					return false;
				}
			} else {
				alert("Please Select DeviceId");
				document.getElementById("deviceId").focus();
				return false;
			}

			if (e == null || e == '' || e == 'refNo') {

				alert("Please Select Reference No");
				document.getElementById("e").focus();
				//form.submit = false; 
				return false;
			}
		}
		
		
		var motorefNo = document.getElementById("motorefNo").value;
		var motodeviceId = document.getElementById("motodeviceId").value;

		if (motoTid != '' && motoTid != null) {
			if (!allnumeric(document.form1.motoTid, 8, 8)) {
				document.getElementById("motoTid").focus();
				return false;
			}

			if (motodeviceId != '' && motodeviceId != null) {
				if (!alphanumeric(document.form1.motodeviceId, 15, 16)) {
					document.getElementById("motodeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Moto DeviceId");
				document.getElementById("motodeviceId").focus();
				return false;
			}

			if (motorefNo == null || motorefNo == ''
					|| motorefNo == 'refNo') {

				alert("Please Select Moto Reference No");
				document.getElementById("motorefNo").focus();
				//form.submit = false; 
				return false;
			}

		}
		
		
		var ezypassrefNo = document.getElementById("ezypassrefNo").value;
		var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;

		if (ezypassTid != '' && ezypassTid != null) {
			if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
				document.getElementById("ezypassTid").focus();
				return false;
			}

			if (ezypassdeviceId != '' && ezypassdeviceId != null) {
				if (!alphanumeric(document.form1.ezypassdeviceId, 15, 16)) {
					document.getElementById("ezypassdeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezypass DeviceId");
				document.getElementById("ezypassdeviceId").focus();
				return false;
			}

			if (ezypassrefNo == null || ezypassrefNo == ''
					|| ezypassrefNo == 'refNo') {

				alert("Please Select Ezypass Reference No");
				document.getElementById("ezypassrefNo").focus();
				//form.submit = false; 
				return false;
			}

		}
		
		
		var ezywayrefNo = document.getElementById("ezywayrefNo").value;
		var ezywaydeviceId = document.getElementById("ezywaydeviceId").value;

		if (ezywayTid != '' && ezywayTid != null) {
			if (!allnumeric(document.form1.ezywayTid, 8, 8)) {
				document.getElementById("ezywayTid").focus();
				return false;
			}

			if (ezywaydeviceId != '' && ezywaydeviceId != null) {
				
				if (!alphanumeric(document.form1.ezywaydeviceId, 15, 16)) {
					document.getElementById("ezywaydeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezyway DeviceId");
				document.getElementById("ezywaydeviceId").focus();
				return false;
			}

			if (ezywayrefNo == null || ezywayrefNo == ''
					|| ezywayrefNo == 'refNo') {

				alert("Please Select Ezyway Reference No");
				document.getElementById("ezywayrefNo").focus();
				//form.submit = false; 
				return false;
			}
		}
		
		
		var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
		var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;

		if (ezyrecTid != '' && ezyrecTid != null) {
			if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
				document.getElementById("ezyrecTid").focus();
				return false;
			}

			if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
				if (!alphanumeric(document.form1.ezyrecdeviceId, 15, 16)) {
					document.getElementById("ezyrecdeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezyrec DeviceId");
				document.getElementById("ezyrecdeviceId").focus();
				return false;
			}

			if (ezyrecrefNo == null || ezyrecrefNo == ''
					|| ezyrecrefNo == 'refNo') {

				alert("Please Select Ezyrec Reference No");
				document.getElementById("ezyrecrefNo").focus();
				//form.submit = false; 
				return false;
			}

		}

		
		return true;

	}



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

function loadSelectData() {
	
	var e = document.getElementById("merchantName").value;

		
		document.form1.action = "${pageContext.request.contextPath}/MDR/updateMDRMerchant";
		//form.submit;
		document.form1.submit();
	}

	
	function openNewWin(txnID){
		//alert(txnID);
		
		var url=window.location;
		//alert(url);
		var src = document.getElementById('popOutiFrame').src;
		 src=url+'transactionweb/details/'+txnID;
		//    alert(src);
		//src = pdffile_url;
		//alert(src);
		var h = 600;
		var w = 1000;
		var title = "Mobiversa Receipt";
		
		var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
	    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

	    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
	    var top = ((height / 2) - (h / 2)) + dualScreenTop;
	   
	   // divviewer.style.display='block';
	    var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
	    
	    //alert(src);
	   // alert(newWindow);
	    // Puts focus on the newWindow
	    if (window.focus) {
	        newWindow.focus();
	    }
			
	}
</script> 


</head>


<body onclick="document.getElementById('emtpyTID').style.display='none';">
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> MDR details </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
	
	 <div class="row">
			<c:if test="${ responseEmptyMids!=null }">
			
  				<center>  
  					<p id="emtpyTID"> <h3 style="color:red;">* Empty MID Details.. Please Fill Anyone MID Details...</h3></p></center>
 			</c:if>
 			<c:if test="${responseEmptyDeviceID!=null }">
			
  				<center>  
  					<p id="emtpyDeviceID"> <h3 style="color:red;">* Empty DeviceID Details..</h3></p></center>
 			</c:if>
	
	<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
							
								<div class="row">
							<div class="input-field col s12 m5 l5">
									<select name="merchantName" class="browser-default select-filter"
								id="merchantName" path="merchantName">
								<!-- <optgroup label="Business Names" > --> 
								<option selected value=""><c:out value="business Name"/></option>
								
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/MDR/updateMDRMerchantByRateID?id=${merchant1.id}">
										${merchant1.businessName}~${merchant1.username}~${merchant1.role}
									
									</option>

								</c:forEach>
								<!-- </optgroup>  -->
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
							</div>
							
						</div>
				
							
						</div>	</div>
						</div></div></div>
						
						<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">
					        	 <div class="table-responsive m-b-20 m-t-15">
		   <table id="data_list_table" class="table table-striped table-bordered">
<!-- 		   rkviewmdrdetails -->
              <thead>
                <tr>
                  <th>MID</th>
                  <th>CARD_BRAND</th>
					<th>CR_FR_HOST_MDR</th>
					<th>CR_FR_MOBI_MDR</th>
					<th>CR_LO_HOST_MDR</th>
					<th>CR_LO_MRCH_MDR</th>
					<th>CR_LO_MOBI_MDR</th>
					<th>DR_FR_HOST_MDR</th>
					<th>DR_FR_MRCH_MDR</th>
					<th>DR_FR_MOBI_MDR</th>
					<th>DR_LO_HOST_MDR</th>
					<th>DR_LO_MRCH_MDR</th>
					<th>DR_LO_MOBI_MDR</th>
					<th>SETTLE_PERIOD</th>
					
                </tr> 

              </thead>
              <tbody>
              <c:forEach items="${paginationBean.itemList}" var="mdr">
                <tr>
				  
                 <td>${mdr.mid}</td>
                 <td>${mdr. cardBrand}</td>
					<td>${mdr.creditForeignHostMDR}</td>
					<td>${mdr.creditForeignMobiMDR}</td>
					<td>${mdr.creditLocalHostMDR}</td>
					<td>${mdr.creditLocalMerchantMDR}</td>
					<td>${mdr.creditLocalMobiMDR}</td>
					<td>${mdr.debitForeignHostMDR}</td>
					<td>${mdr.debitForeignMerchantMDR}</td>
					<td>${mdr.debitForeignMobiMDR}</td>
					<td>${mdr.debitLocalHostMDR}</td>
					<td>${mdr.debitLocalMerchantMDR}</td>
					<td>${mdr.debitLocalMobiMDR}</td>
					<td>${mdr.settlePeriod}</td>
<!--                   <th>MID</th> -->
<!--                   <th>FO_CR_VISA_HOST</th> -->
<!-- 					<th>FO_CR_VISA_MERCH</th> -->
<!-- 					<th>FO_CR_VISA_MOBI</th> -->
<!-- 					<th>LO_CR_VISA_HOST</th> -->
<!-- 					<th>LO_CR_VISA_MERCH</th> -->
<!-- 					<th>LO_CR_VISA_MOBI</th> -->
<!-- 					<th>FO_DR_VISA_HOST</th> -->
<!-- 					<th>FO_DR_VISA_MERCH</th> -->
<!-- 					<th>FO_DR_VISA_MOBI</th> -->
<!-- 					<th>LO_DR_VISA_HOST</th> -->
<!-- 					<th>LO_DR_VISA_MERCH</th> -->
<!-- 					<th>LO_DR_VISA_MOBI</th> -->
<!-- 					<th>FO_CR_MC_HOST</th> -->
<!-- 					<th>FO_CR_MC_MERCH</th> -->
<!-- 					<th>FO_CR_MC_MOBI</th> -->
<!-- 					<th>LO_CR_MC_HOST</th> -->
<!-- 					<th>LO_CR_MC_MERCH</th> -->
<!-- 					<th>LO_CR_MC_MOBI</th> -->
<!-- 					<th>FO_DR_MC_HOST</th> -->
<!-- 					<th>FO_DR_MC_MERCH</th> -->
<!-- 					<th>FO_DR_MC_MOBI</th> -->
<!-- 					<th>LO_DR_MC_HOST</th> -->
<!-- 					<th>LO_DR_MC_MERCH</th> -->
<!-- 					<th>LO_DR_MC_MOBI</th> -->
<!-- 					<th>FO_CR_UP_HOST</th> -->
<!-- 					<th>FO_CR_UP_MERCH</th> -->
<!-- 					<th>FO_CR_UP_MOBI</th> -->
<!-- 					<th>LO_CR_UP_HOST</th> -->
<!-- 					<th>LO_CR_UP_MERCH</th> -->
<!-- 					<th>LO_CR_UP_MOBI</th> -->
<!-- 					<th>FO_DR_UP_HOST</th> -->
<!-- 					<th>FO_DR_UP_MERCH</th> -->
<!-- 					<th>FO_DR_UP_MOBI</th> -->
<!-- 					<th>LO_DR_UP_HOST</th> -->
<!-- 					<th>LO_DR_UP_MERCH</th> -->
<!-- 					<th>LO_DR_UP_MOBI</th> -->
<!--                 </tr>  -->

<!--               </thead> -->
<!--               <tbody> -->
<%--               <c:forEach items="${paginationBean.itemList}" var="mdr"> --%>
<!--                 <tr> -->
				  
<%--                  <td>${mdr.midMapped}</td> --%>
<%--                  <td>${mdr.creditForeignVisaHost}</td> --%>
<%-- 					<td>${mdr.creditForeignVisaMerch}</td> --%>
<%-- 					<td>${mdr.creditForeignVisaMobi}</td> --%>
<%-- 					<td>${mdr.creditLocalVisaHost}</td> --%>
<%-- 					<td>${mdr.creditLocalVisaMerch}</td> --%>
<%-- 					<td>${mdr.creditLocalVisaMobi}</td> --%>
<%-- 					<td>${mdr.debitForeignVisaHost}</td> --%>
<%-- 					<td>${mdr.debitForeignVisaMerch}</td> --%>
<%-- 					<td>${mdr.debitForeignVisaMobi}</td> --%>
<%-- 					<td>${mdr.debitLocalVisaHost}</td> --%>
<%-- 					<td>${mdr.debitLocalVisaMerch}</td> --%>
<%-- 					<td>${mdr.debitLocalVisaMobi}</td> --%>
					
<%-- 					<td>${mdr.creditForeignMcHost}</td> --%>
<%-- 					<td>${mdr.creditForeignMcMerch}</td> --%>
<%-- 					<td>${mdr.creditForeignMcMobi}</td> --%>
<%-- 					<td>${mdr.creditLocalMcHost}</td> --%>
<%-- 					<td>${mdr.creditLocalMcMerch}</td> --%>
<%-- 					<td>${mdr.creditLocalMcMobi}</td> --%>
<%-- 					<td>${mdr.debitForeignMcHost}</td> --%>
<%-- 					<td>${mdr.debitForeignMcMerch}</td> --%>
<%-- 					<td>${mdr.debitForeignMcMobi}</td> --%>
<%-- 					<td>${mdr.debitLocalMcHost}</td> --%>
<%-- 					<td>${mdr.debitLocalMcMerch}</td> --%>
<%-- 					<td>${mdr.debitLocalMcMobi}</td> --%>
					
<%-- 					<td>${mdr.creditForeignUpHost}</td> --%>
<%-- 					<td>${mdr.creditForeignUpMerch}</td> --%>
<%-- 					<td>${mdr.creditForeignUpMobi}</td> --%>
<%-- 					<td>${mdr.creditLocalUpHost}</td> --%>
<%-- 					<td>${mdr.creditLocalUpMerch}</td> --%>
<%-- 					<td>${mdr.creditLocalUpMobi}</td> --%>
<%-- 					<td>${mdr.debitForeignUpHost}</td> --%>
<%-- 					<td>${mdr.debitForeignUpMerch}</td> --%>
<%-- 					<td>${mdr.debitForeignUpMobi}</td> --%>
<%-- 					<td>${mdr.debitLocalUpHost}</td> --%>
<%-- 					<td>${mdr.debitLocalUpMerch}</td> --%>
<%-- 					<td>${mdr.debitLocalUpMobi}</td> --%>
                 
                </tr> 
                </c:forEach>
                
              </tbody>
            </table>
     
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

<script type="text/javascript">
	jQuery(document).ready(function() {
		$('#brand').select2();
		$('#mid1').select2();
		$('#merchantName').select2();
	});
</script>					
							</div>
						</div>
					</div>			
				</div>
			</div>

</body>

