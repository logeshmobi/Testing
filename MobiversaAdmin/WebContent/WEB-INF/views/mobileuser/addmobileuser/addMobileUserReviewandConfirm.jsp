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
	 
<style>

</style>

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
           <h3 class="text-white">  <strong> Add Mobile User </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	

 <form:form action="mobileUser/mobileUserDetailsConfirm" method="post"
		commandName="mobileUser" id="form-add">
	<div class="row">		 
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
         <h3>Merchant Details </h3>
         </div>
			<div class="row">	
			<div class="input-field col s12 m3 l3">
					<label for="name">Business Name</label>
						<input type="text"
						 id="merchantName" name="merchantName" path="merchantName"
							value="${mobileUser.merchantName}" readonly>									
			</div>	
			</div>
			</div>
							
			</div>	</div>
			</div>
			
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
		   <li class="tb_active" id="mobileuser">MobileUser Details</li>
		   <c:if test="${mobileUser.mid !=null && not empty mobileUser.tid }">
		   <li id="ezywire">Ezywire Details</li>
		   </c:if>
		   <c:if test="${mobileUser.motoMid !=null && not empty mobileUser.motoTid }">
		   <li id="ezymoto">Ezymoto Details</li>
		   </c:if>
		   <c:if test="${mobileUser.ezywayMid !=null && not empty mobileUser.ezywayTid }">
		   <li id="ezyway">Ezyway Details</li>
		   </c:if>
		   <c:if test="${mobileUser.ezyrecMid !=null && not empty mobileUser.ezyrecTid}">
		    <li id="ezyrec">Ezyrec Details</li>
		    </c:if>
		    <c:if test="${mobileUser.ezypassMid !=null && not empty mobileUser.ezypassTid }">
		   <li id="ezypass">Ezypass Details</li>
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
					<label for="name">Contact Name</label>
					</div>
					<div class="input-field col s12 m6 l6 ">
						<input type="text"
						id="remarks" name="remarks" path="remarks"
						value="${mobileUser.remarks}" readonly>
						<label for="name">Remarks</label>
					</div>
					
					<div class="input-field col s12 m6 l6 ">
						<input type="text"
						id="expiryDate" name="expiryDate" path="expiryDate"
						value="${mobileUser.expiryDate}" readonly>
						<label for="name">Expiry Date</label>
					</div>
				</div>
				
				<div class="row ezywire" style="display:none;">
				<input type="hidden" value="${mobileUser.mid }" name="mid" path="mid"/>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser Ezywire Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.mid }</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
							<label for="tid">TID</label>
						<input type="text" id="tid" path="tid"
							name="tid" value="${mobileUser.tid}" readonly>			 
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label for="Email">Device Id</label>
						<input type="text" id="deviceId" path="deviceId"
							name="deviceId" value="${mobileUser.deviceId}" readonly>		 
					</div>
					 
					<div class="input-field col s12 m6 l6 ">
						<input type="text" id="referenceNo" path="referenceNo"
							name="referenceNo" value="${mobileUser.referenceNo}"readonly>
						<label for="Email">Reference No</label>					 
					</div>
					
					<div class="input-field col s12 m6 l6 ">
						<label for="preAuth">Pre-Auth</label>
							<input type="text" id="preAuth" name="preAuth" path="preAuth"
							 value="${mobileUser.preAuth}" readonly>
					</div>
					  
				</div>
				
				<div class="row ezymoto" style="display:none;">
				<input type="hidden" value="${mobileUser.motoMid}" name="motoMid" path="motoMid"/>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser Ezymoto Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.motoMid}</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
					<label for="Email">TID</label>
						<input type="text" path="motoTid" 
						id="motoTid" name="motoTid" value="${mobileUser.motoTid}" readonly>				 
					</div>
					<div class="input-field col s12 m6 l6 ">
					<label for="Email">Device Id</label>
						<input type="text" path="motodeviceId" 
						id="motodeviceId" name="motodeviceId" value="${mobileUser.motodeviceId}" readonly>			 
					</div>
					 
					<div class="input-field col s12 m6 l6 ">
						<input type="text" id="motorefNo"
						 name="motorefNo" path="motorefNo"
							 value="${mobileUser.motorefNo}"readonly>
						<label for="Email">Reference No</label>				 
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label for="motopreAuth">Pre-Auth</label>
							<input type="text" id="motopreAuth" name="motopreAuth" path="motopreAuth"
							 value="${mobileUser.motopreAuth}" readonly>
					</div>
					  
				</div>
				
				<div class="row ezyway" style="display:none;">
				<input type="hidden" value="${mobileUser.ezywayMid}" name="ezywayMid" path="ezywayMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser Ezyway Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.ezywayMid}</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="ezywayTid">TID</label>
						<input type="text"  path="ezywayTid" id="ezywayTid" name="ezywayTid" 
							value="${mobileUser.ezywayTid}" readonly>
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label for="ezywaydeviceId">Device Id</label>
						<input type="text" path="ezywaydeviceId" id="ezywaydeviceId" name="ezywaydeviceId" 
							value="${mobileUser.ezywaydeviceId}" readonly> 
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">
				<label for="ezywayrefNo">Reference No</label>
				<input type="text" id="ezywayrefNo" name="ezywayrefNo" path="ezywayrefNo"
							 value="${mobileUser.ezywayrefNo}"readonly>
				</div>
				</div>
				
				<div class="row ezyrec" style="display:none;">
				<input type="hidden" value="${mobileUser.ezyrecMid}" name="ezyrecMid" path="ezyrecMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser Ezyrec Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.ezyrecMid}</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="ezyrecTid">TID</label>
						<input type="text" path="ezyrecTid" id="ezyrecTid" name="ezyrecTid" 
							value="${mobileUser.ezyrecTid}" readonly>
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label for="ezyrecdeviceId">Device Id</label>
						<input type="text" path="ezyrecdeviceId" id="ezyrecdeviceId" name="ezyrecdeviceId" 
							value="${mobileUser.ezyrecdeviceId}" readonly>
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">
				<input type="text" id="ezyrecrefNo" name="ezyrecrefNo" path="ezyrecrefNo"
							 value="${mobileUser.ezyrecrefNo}"readonly>
				<label for="ezyrecrefNo">Reference No</label>
				</div>
				
				<div class="input-field col s12 m6 l6 "> 
				<label for="ezypod">EzyPOD</label>
						<input type="text" class="form-control" id="ezypod" name="ezypod" path="ezypod"
							 value="${mobileUser.ezypod}" readonly>
                 		 
					</div>
				</div>
				
				<div class="row ezypass" style="display:none;">
				<input type="hidden" value="${mobileUser.ezypassMid}" name="ezypassMid" path="ezypassMid"/>
					<div class="col s12 m4 l6"><h5 style="color:#005baa">MobileUser Ezypass Details Mid: </h5></div>
				<div class="col s12 m4 l6"><h5 style="color:#005baa">${mobileUser.ezypassMid}</h5></div>
				
				
					<div class="input-field col s12 m6 l6 ">
						<label for="ezypassTid">TID</label>
						<input type="text" path="ezypassTid" id="ezypassTid"
						 name="ezypassTid" value="${mobileUser.ezypassTid}" readonly>				
						
					</div>
					<div class="input-field col s12 m6 l6 ">
						<label for="ezypassdeviceId">Device Id</label>
						<input type="text" path="ezypassdeviceId" id="ezypassdeviceId"
						 name="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}" readonly>		
					</div>
				
				
				<div class="input-field col s12 m6 l6 ">
				
				<input type="text" id="ezypassrefNo"
				 name="ezypassrefNo" path="ezypassrefNo" value="${mobileUser.ezypassrefNo}"readonly>
				<label for="ezypassrefNo">Reference No</label>
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
    </div>
	
	</form:form> 
	
	</div>
    </body>
	</html>
	
				




			
			
			