<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script> -->
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

 <script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/mobileUser/addMobileUser/1";
	$(location).attr('href',url);
} 

</script>
 
 <script type="text/javascript">
 
	function disableRow() {

		// alert("test123");

		var i = document.getElementById("connectType").value;
		//alert(i);

		/* if (i == "WIFI") {
			//document.getElementById("agType").value;

			document.getElementById("preAuth1").style.display = 'none';
			//document.getElementById("BankDetails1").style.display = 'none';

		} else if (i == "BT") {
			document.getElementById("preAuth1").style.display = '';

			//document.getElementById("BankDetails1").style.display = '';

		} */

		/* document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none'; */
	}
	

function load()
{
	
 //$('#demoSwal').click(function(){
      	swal({
      		title: "Are you sure? you want to add this MobileUser ",
      		text: "it will be added..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false
      	}, function(isConfirm) {
      		if (isConfirm) {
      			//swal("Added!", "Your agent details added.", "success");
      			$("#form-add").submit();
      		} else {
      			//swal("Cancelled", "Your agent details not added", "error");
      			 var url = "${pageContext.request.contextPath}/mobileUser/addMobileUser"; 
		$(location).attr('href',url);
      			
      		}
      	});
    //  });
}
   </script>   
<body onload="">
	
<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Add UMobile User </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
 <form:form action="mobileUser/um_mobileUserDetailsConfirm" method="post"
		commandName="mobileUser" id="form-add">
		 
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
<div class="row">
	<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
			<h3 >Merchant Details </h3>
			</div>
			
            <div class="row">
				<div class="input-field col s12 m3 l3">
						<label for="mailId">Business Name</label>
  							<input type="text"
							id="merchantName" name="merchantName" path="merchantName"
							value="${mobileUser.merchantName}" readonly>

 					</div>
					</div>
					</div>	</div>
						</div></div>
	<style>
.drop-details .select-wrapper input.select-dropdown  { 
    border: 1px solid #005baa;     border-radius: 16px; padding: 0 12px; color:#005baa;
}

.drop-details .select-wrapper .caret {
    fill: #005baa;
}
</style> 
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
		 <ul class="tab_ul">
		   <c:if test="${mobileUser.um_mid !=null && !empty mobileUser.um_mid}">
		   <li id="umezywire">UM Ezywire Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_motoMid !=null && !empty mobileUser.um_motoMid}">
		    <li id="umezymoto">UM Ezymoto Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezywayMid !=null && !empty mobileUser.um_ezywayMid}">
		    <li id="umezyway">UM Ezyway Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezyrecMid !=null && !empty mobileUser.um_ezyrecMid}">
		    <li id="umezyrec">UM Ezyrec Details</li>
		    </c:if>
		    <c:if test="${mobileUser.um_ezypassMid !=null && !empty mobileUser.um_ezypassMid}">
		    <li id="umezypass">UM Ezypass Details</li>
		    </c:if>
		   </ul>
		   
          </div>
		  
		  <script>
 $(function() {                       
  $(".tab_ul li").click(function() {   
    $(".tab_ul li").removeClass("tb_active");      
    $(this).addClass("tb_active"); 
    id = $(this).attr("id"); 
   $('.content_area .row').hide();
   $('.'+id).show();
	
  });
});


 
$('.datepicker').pickadate();

</script>
		  
		   <style>
		   .tab_ul { display:flex;width: 100%;}
		   .tab_ul li { display: flex;
    /* margin-left: 10px; */
    padding: 10px 20px;
    background-color: #eeeae9;
    width: 100%; cursor:pointer;
	 margin: auto;
    display: table;
	}
	
	.tb_active {  background-color: #005baa !important;
    color: #fff;  text-align:center}
	
	.content_area { padding:30px; }
	
	 .page-wrapper {
    min-height: calc(50vh - 64px);
} 
#submit-btn { background-color:#54b74a;color:#fff; border-radius:10px; margin:auto; display:table;  }
.mob-br { display:none; }


@media screen and (max-width:768px){
.tab_ul { display: block !important; width: auto;}
.tab_ul li { float: left; display:block;width:auto;  }
.mob-br { display:block;  white-space: pre-line; }

}
		   </style>
		  <div style="clear:both;"></div>
		  
          <div class="content_area">
				
				  <div class="row mobileuser">
					<div class="input-field col s12 m6 l6 " id="name">
					<input type="text" id="contactName" path="contactName"
					name="contactName" value="${mobileUser.contactName}" readonly>
						<label for="first_name">Contact Name</label>
					</div>
					<div class="input-field col s12 m6 l6 ">
						<input type="text" id="remarks" name="remarks" path="remarks"
						value="${mobileUser.remarks}" readonly>
						<label for="name">Remarks</label>
					</div>
					
					<div class="input-field col s12 m6 l6 ">
						<input type="text" id="expiryDate" name="expiryDate" path="expiryDate"
						value="${mobileUser.expiryDate}" readonly>
						<label for="name">Expiry Date</label>
					</div>
				</div>
				
				<div class="row umezywire" style="display:none;">
				<input type="hidden" value="${mobileUser.um_mid }" name="um_mid" path="um_mid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezywire Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_mid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_tid">TID</label>
						<input type="text" id="um_tid" path="um_tid"
							name="um_tid" value="${mobileUser.um_tid}" readonly>
						
					 </div>
					
					<div class="input-field col s12 m6 l6 ">
						<label for="um_deviceId">Device Id</label>
						<input type="text" id="um_deviceId" path="um_deviceId"
							name="um_deviceId" value="${mobileUser.um_deviceId}" readonly>
						 
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">
				<input type="text" id="um_refNo" path="um_refNo"
							name="um_refNo" value="${mobileUser.um_refNo}"readonly>
				<label for="um_refNo">Reference No</label>
				</div>
				
				<div class="input-field col s12 m6 l6 ">
						<label for="preAuth">Pre-Auth</label>
						<input type="text" class="form-control" id="preAuth" name="preAuth" path="preAuth"
						 value="${mobileUser.preAuth}" readonly>
					</div>
				</div>
				
				<div class="row umezymoto" style="display:none;">
				<input type="hidden" value="${mobileUser.um_motoMid}" name="um_motoMid" path="um_motoMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezymoto Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_motoMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_motoTid">TID</label>
						<input type="text" path="um_motoTid"
						 id="um_motoTid" name="um_motoTid" value="${mobileUser.um_motoTid}" readonly>					
					 </div>
					
					<div class="input-field col s12 m6 l6 ">
						
						<label for="um_motodeviceId">Device Id</label>
						<input type="text" path="um_motodeviceId" id="um_motodeviceId" name="um_motodeviceId"
						 value="${mobileUser.um_motodeviceId}" readonly>					
					</div>
					
					<div class="input-field col s12 m6 l6 ">
						<label for="hashKey">HashKey</label>
						<input type="text" id="hashkey1" 
						value="${mobileUser.hashkey}"
						placeholder="hashkey" name="hashkey1"  path="hashkey1"/>
					</div>
					<div class="input-field col s12 m6 l6 ">
					<label for="DTL">DTL</label>
					<input type="text" id="DTL1" value="${mobileUser.DTL}"
					placeholder="DTL" name="DTL1"  path="DTL1"/>

					</div>
					
				
				<div class="input-field col s12 m6 l6 "> 
						<label for="motopreAuth">Pre-Auth</label>
							<input type="text" id="motopreAuth" name="motopreAuth" path="motopreAuth"
							 value="${mobileUser.motopreAuth}" readonly>
					</div>
					
					<div class="input-field col s12 m6 l6 "> 
						<label for="motopreAuth">EZYMOTO-VCC</label>
							<input type="text"  id="vcc" name="vcc" path="vcc"
							 value="${mobileUser.vcc}" readonly>
					</div>
				</div>
				
				<div class="row umezyway" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezywayMid}" name="um_ezywayMid" path="um_ezywayMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezyway Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezywayMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezywayTid">TID</label>
						<input type="text" path="um_ezywayTid" id="um_ezywayTid" name="um_ezywayTid" 
							value="${mobileUser.um_ezywayTid}" readonly>								
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezywaydeviceId">Device Id</label>
						<input type="text" path="um_ezywaydeviceId" id="um_ezywaydeviceId" name="um_ezywaydeviceId" 
							value="${mobileUser.um_ezywaydeviceId}" readonly>							
					</div>
								
				
				<div class="input-field col s12 m6 l6 ">
						<label for="hashKey">HashKey</label>
						<input type="text" id="hashkey" value="${mobileUser.hashkey}"
						placeholder="hashkey" name="hashkey"  path="hashkey"/>									
					</div>
					
					<div class="input-field col s12 m6 l6 ">
					<label for="DTL">DTL</label>
					<input type="text" id="DTL" value="${mobileUser.DTL}"
						placeholder="DTL" name="DTL"  path="DTL"/>
					</div>
					
					<div class="input-field col s12 m6 l6 ">
					<label for="domainUrl">Domain URL</label>
					<input type="text" id="domainUrl" value="${mobileUser.domainUrl}"
						placeholder="domainUrl" name="domainUrl"  path="domainUrl"/>
					</div>

				</div>
				
				<div class="row umezyrec" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezyrecMid}" name="um_ezyrecMid" path="um_ezyrecMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezyrec Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezyrecMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezyrecTid">TID</label>
						<input type="text" path="um_ezyrecTid" id="um_ezyrecTid" name="um_ezyrecTid" 
							value="${mobileUser.um_ezyrecTid}" readonly>													
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezyrecdeviceId">Device Id</label>
						<input type="text" path="um_ezyrecdeviceId" id="um_ezyrecdeviceId" name="um_ezyrecdeviceId" 
							value="${mobileUser.um_ezyrecdeviceId}" readonly>										
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">					
					
					<input type="text" id="um_ezyrecrefNo" name="um_ezyrecrefNo" path="um_ezyrecrefNo"
							 value="${mobileUser.um_ezyrecrefNo}"readonly>
					<label for="um_ezyrecrefNo">Reference No</label>
				</div>
				
				<div class="input-field col s12 m6 l6 ">
						<label for="hashKey">HashKey</label>
						<input type="text" id="recHashkey" 
						value="${mobileUser.recHashkey}"
						placeholder="recHashkey" name="recHashkey"  path="recHashkey"/>
					</div>
					<div class="input-field col s12 m6 l6 ">
					<label for="DTL">DTL</label>
					<input type="text" id="recDTL" value="${mobileUser.recDTL}"
					placeholder="recDTL" name="recDTL"  path="recDTL"/>

					</div>

				</div>
				<div class="row umezypass" style="display:none;">
				<input type="hidden" value="${mobileUser.um_ezypassMid}" name="um_ezypassMid" path="um_ezypassMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser UM Ezypass Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.um_ezypassMid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="um_ezypassTid">TID</label>
						<input type="text" path="um_ezypassTid" 
						id="um_ezypassTid" name="um_ezypassTid" value="${mobileUser.um_ezypassTid}" readonly>														
					 </div>
					
					<div class="input-field col s12 m6 l6 ">					
						<label for="um_ezypassdeviceId">Device Id</label>
						<input type="text" path="um_ezypassdeviceId" id="um_ezypassdeviceId" 
								name="um_ezypassdeviceId" value="${mobileUser.um_ezypassdeviceId}" readonly>													
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">										
					<input type="text" id="um_ezypassrefNo" name="um_ezypassrefNo" 
					path="um_ezypassrefNo" value="${mobileUser.um_ezypassrefNo}"readonly>
					<label for="um_ezypassrefNo">Reference No</label>
				</div>

				</div>
				
				
				
				</div>
				
				<div class="button-class"  style="float:left;">		
				 <input type="button" id="demoSwal" class="btn btn-primary blue-btn"   onclick="return load()" value="Confirm">
				<input type="button" style="color:#fff !important;" class="export-btn waves-effect waves-light btn btn-round indigo"  onclick="load1()" value="Cancel">
				 <br/>
				 </div>
	<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>	
				
 </div>
      </div>
    </div>
    </div>

	</form:form> 
	
	</div>
    </body>
	</html>		