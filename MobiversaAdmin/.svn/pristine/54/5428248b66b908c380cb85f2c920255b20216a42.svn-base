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

<!-- <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#mid1').select2();
		$('#merchantName').select2();
	});
</script> -->

<script lang="JavaScript">

function loadSelectData(){ 
	
	
	var mid = document.getElementById("mid").value;
	var mobiMDR = document.getElementById("mobiMDR").value;
	var hostMDR = document.getElementById("hostMDR").value;
	
		if (mid == '' || mid == null) {
			alert("please select product");
			document.getElementById("mid").focus();
			return false;

		}

		if (mobiMDR == null || mobiMDR == '') {

			alert("Please Fill MOBI MDR");
			document.getElementById("mobiMDR").focus();
			return false;
		}
		
		if (hostMDR == '' || hostMDR == null) {

			alert("Please Fill HOST MDR..");
			document.getElementById("hostMDR").focus();
			return false;

		}

		return true;

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



</head>

<body>

<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Add Product MDR</strong></h3>
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
									<select name="merchantName"  class="browser-default select-filter"
										id="merchantName" path="merchantName">
										<optgroup label="Business Names">
											<option selected value=""><c:out
													value="business Name" /></option>

											<c:forEach items="${merchant1}" var="merchant1">
												<option
													value="${pageContext.request.contextPath}/MDR/findProMID?id=${merchant1.id}">
													${merchant1.businessName}~${merchant1.username}~${merchant1.role}

												</option>

											</c:forEach>
										</optgroup>
									</select>
									</div>	
							
							</div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>
					  <script>
        $(document).ready(function(){
            
            // Initialize select2
            $("#selUser").select2();
            $(".select-filter").select2();
            
            
        });
        </script>
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
<div class="input-field col s12 m3 l3 select-search">			
							<input type="text" class="shownSearch" id="businessName" readonly
								Placeholder="businessName" value="${mobileUser.businessName }"/>
							</div>
							
						</div>	</div>
</div></div></div></div>

<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/regProMDR" name="form1" commandName="mobileUser">	
<%-- <input type="hidden" class="form-control" path="businessName" id="businessName" readonly name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/> --%>
<c:if test="${mobileUser.businessName !=null }">
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> MDR Details</h5>
				          </div>
			<div class="row">

					<div class="input-field col s12 m6 l6 ">
						
					
						<input type="hidden" name="mid"
							id="mid" value="${mid}"> <select name="mid1"
							style="width: 100%" id="mid1"
							onchange="document.getElementById('mid').value=document.getElementById('mid1').value;">
							<option selected value=""><c:out value="MID" /></option>
							

							<c:if test="${not empty  mobileUser.um_mid }">
								<option value="${mobileUser.um_mid}~UM_EZYWIRE">UM_EZYWIRE</option>
							</c:if>

							<c:if test="${not empty  mobileUser.um_ezywayMid }">
								<option value="${mobileUser.um_ezywayMid}~UM_EZYWAY">UM_EZYWAY</option>
							</c:if>

							<c:if test="${not empty  mobileUser.um_motoMid }">
								<option value="${mobileUser.um_motoMid}~UM_EZYMOTO">UM_EZYMOTO</option>
							</c:if>

							<c:if test="${not empty  mobileUser.um_ezypassMid }">
								<option value="${mobileUser.um_ezypassMid}~UM_EZYPASS">UM_EZYPASS</option>
							</c:if>

							<c:if test="${not empty  mobileUser.um_ezyrecMid }">
								<option value="${mobileUser.um_ezyrecMid}~UM_EZYREC">UM_EZYREC</option>
							</c:if>

							<c:if test="${not empty  mobileUser.gPayMid }">
								<option value="${mobileUser.gPayMid}~Gpay">Gpay</option>
							</c:if>
							<option value="${merchantId}~Boost">Boost</option>
							<option value="${merchantId}~FPX">FPX</option>

						</select>
						<label>Product</label> 
					</div>
				</div>
			
					<div class="row">

					<div class="input-field col s12 m6 l6 ">

										
											<label for="MerchantMDR">Mobi MDR</label> 
											<!-- <input type ="text" class="form-control" id="domcreditmerchantMDR" placeholder="Merchant MDR"/>  -->
											<input type="number" step=".01" 
												id="mobiMDR" placeholder="Merchant MDR"
												name="mobiMDR" path="mobiMDR"
												value="0.03" />
										</div>

									<div class="input-field col s12 m6 l6 ">
											<label>Host MDR</label>
											<!-- <input type ="text" class="form-control" placeholder="Host MDR"> -->
											<input type="number" step=".01" 
												id="hostMDR" placeholder="Host MDR"
												name="hostMDR" path="hostMDR" value="0" readonly="readonly"
												 />
										</div>
									

								</div>
							

						<button class="submitBtn" type="submit"  onclick="return loadSelectData();">Submit</button>
		<style>
		.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}
		</style>		
						
						</div></div>
						</div></div>		 
					</c:if>
					
			
			</form:form>

</div>
<script type="text/javascript">
	jQuery(document).ready(function() {
		
		$('#merchantName').select2();
	});
</script>
</body>
