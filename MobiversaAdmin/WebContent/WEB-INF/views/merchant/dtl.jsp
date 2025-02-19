<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- <html lang="en-US">
<head> -->
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
 <style>
.error {
	color: red;
	font-weight: bold;
}


</style>
</head>

    </script>
 -->


 <script lang="JavaScript">

	</script>
       
       <script>
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
</script>
    
<body >
	
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Update DTL</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>	
	
			
<div class="row">			
			
			
			
			<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> Merchant Details</h5>
				          </div>
						<div class="row">
							<div class="input-field col s12 m3 l3">
								
									MID
									
							</div>	
							
				<c:choose> 
			<c:when test="${adminusername1=='ethan'}"> 
							<div class="input-field col s12 m5 l5">
									<select name="merchantName"
								id="merchantName" path="merchantName"  class="browser-default select-filter">
								<optgroup label="MID" style="width:100%">
								<option selected value=""><c:out value="MID" /></option>
								
								

									<c:forEach items="${ummidtxn}" var="ummidtxn">
									<option value="${pageContext.request.contextPath}/admmerchant/finduser?id=${ummidtxn.id}">
										${ummidtxn.mid}
									</option>

								</c:forEach>
							
								</optgroup>
							</select></div>	
							
							</div>
					</c:when>	
				</c:choose>
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
			<input type="text" class="shownSearch"  id="businessName" readonly
								Placeholder="Mid" value="${mobileUser.mid }"/>
							</div>
							</div>
							
							
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
							
							
						</div>	</div></div></div>
						
				<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/admmerchant/updatedtl/${mobileUser.mid}"
			name="form1" commandName="mobileUser">	
					
					
					
					<input type="hidden" class="form-control" path="businessName" id="businessName" readonly
								name="businessName" Placeholder="MID" value="${mobileUser.mid }"/>
				
				
					<c:if test="${mobileUser.mid !=null }">
					
					
					
					
					<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
				           <h5>DTL Details</h5>
				          </div>
					
						
					<div class="row">
			
						<div id="currentDTL">
							
						<div class="input-field col s6 ">
									<label for="contactName" >Current DTL</label>
						
							<input type="text"  path="contactName" id="contactName"
								name="contactName" disabled="true" Placeholder="Current DTL"  value="${mobileUser.DTL}"/>

								</div>
						
						</div>
						
						<div class="input-field col s6 ">
								<label for="Remarks" >New DTL <span style="color: red;">*</span></label>
								<input type="text" id="remarks" path="remarks"
									placeholder="New DTL" name="remarks"  value="0.00"  />
							</div>
							
						
						
							</div>	
							
					


						
	
	<button class="submitBtn" type="submit">Update</button>
	</div></div></div></div>
		</c:if>


	</form:form>
	
					</div>
					<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/validate.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
/* $('#gPayrefNo').select2();
$('#motorefNo').select2();
$('#ezypassrefNo').select2();
$('#ezyrecrefNo').select2();
$('#mobileId').select2();

$('#connectType').select2();
$('#deviceType').select2();
 */
});  

$('#form1').validate({
	 rules: {
		 remarks: {
               required: true,
               min:0.01,
               number:true,
           },
           
       },
       errorPlacement: function(){
           return false;
       },
       submitHandler: function (form) {
          //if(grecaptcha.getResponse() == "") return false;
          //else return true;
          return true;
       }
   });
    </script>

	</body>
	</html>
	
