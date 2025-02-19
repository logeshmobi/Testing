
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.mobiversa.payment.controller.SuperAgentController"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/");
</script>
<script type="text/javascript">
function changeValue(){
	 var data= document.getElementById("mobileUserIds").value;
	//alert(data);
	var fields = data.split(',');
	document.getElementById('mobileUserId').value=fields[0].replace(/\s/g,'').trim();
	document.getElementById('tid').value=fields[1].replace(/\s/g,'').trim();
	document.getElementById('motoTid').value=fields[2].replace(/\s/g,'').trim();
	document.getElementById('ezypassTid').value=fields[3].replace(/\s/g,'').trim();
	document.getElementById('ezyrecTid').value=fields[4].replace(/\s/g,'').trim();
	document.getElementById('ezywayTid').value=fields[5].replace(/\s/g,'').trim();
	
	var id = document.getElementById('mobileUserId').value;
	var tid = document.getElementById('tid').value;
	var motoTid = document.getElementById('motoTid').value;
	
	var ezypassTid = document.getElementById('ezypassTid').value;
	var ezyrecTid = document.getElementById('ezyrecTid').value;
	var ezywayTid = document.getElementById('ezywayTid').value;
	
	
/* 	
	alert("tid-"+tid.length+"-");
	alert("motoTid-"+motoTid.length+"-");
	alert("ezypassTid-"+ezypassTid.length+"-");
	alert("ezyrecTid-"+ezyrecTid.length+"-");
	alert("ezywayTid-"+ezywayTid.length+"-"); */
	
	if(tid.length>0 ){
		
	document.getElementById('tid1').style.display='block';
	}else{
		document.getElementById('tid1').style.display='none';
	}
	
	
	if(motoTid.length>0){
	document.getElementById('motoTid1').style.display='block';
	}else{
		document.getElementById('motoTid1').style.display='none';
	}
	
	
	if(ezypassTid.length >0){
	document.getElementById('ezypassTid1').style.display='block';
	}else{
		document.getElementById('ezypassTid1').style.display='none';
	}
	
	
	if(ezyrecTid.length>0){
	document.getElementById('ezyrecTid1').style.display='block';
	}else{
		document.getElementById('ezyrecTid1').style.display='none';
	}
	
	if(ezywayTid.length>0){
	document.getElementById('ezywayTid1').style.display='block';
	}else{
		document.getElementById('ezywayTid1').style.display='none';
	}
	
	
	
	
}
</script>



 <style>
 
 .td{
 text-align:right;
 
 
 }
  </style>


  
<script type="text/javascript">
	function displayMerchant(){
		var merchantDiv=document.getElementById('merchantDiv');
		var mobileuserDiv=document.getElementById('mobileuserDiv');
		merchantDiv.style.display='block';
		mobileuserDiv.style.display='none';
	}
	
	function displayMobileuser(){
		var merchantDiv=document.getElementById('merchantDiv');
		var mobileuserDiv=document.getElementById('mobileuserDiv');
		merchantDiv.style.display='none';
		mobileuserDiv.style.display='block';
	}
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


<script lang="JavaScript">

function validatePassword()
{
	var password=document.getElementById("merchantPass").value;
	var len=password.length;
	//alert("length: "+len);
	//var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}/;
	if(password!=null){
	/* if(len >= 8 && re.test(password)) */
	if(len >= 6)
	{
		
		
   		 return true;
	}
	else
	{
		alert("Please fulfill the password conditons..");
		document.getElementById("message").style.display = "block";
		document.getElementById("merchantPass").focus();
		return false;
	
	}
	}
}
</script>
 <style>
.select2-dropdown li:first-child{
  display:none !important;
}


/* Style the container for inputs */
.container {
    background-color: #f1f1f1;
    padding: 20px;
}

/* The message box is shown when the user clicks on the password field */
#message {
    display:none;
    background: #f1f1f1;
    color: #000;
    position: relative;
    padding: 0px;
    margin-top: 0px;
}

#message p {
    padding: 0px 35px;
    font-size: 15px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
    color: green;
}

.valid:before {
    position: relative;
    left: -35px;
    content:  url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAC01BMVEUAAAD///7////z+e7l8tn////////////z+e7////x+Ozu9+f////////////////9/vxvtzHs9eT////////////0+vBVqQtOpgH////////////////t9uVYqw9Xqw/+/v3////////////////q9OFyuDVbrRRYqxD///7+//7////////////r9eJxtzNcrRZZrBH9/vz////////////////////////////////////u9+dbrRRdrhf1+vH////////////4+/Xs9eX9/v3////////0+vBZqxFjsB9msiTl8tn////////////////////////////////9/vz////7/flWqgxXqg7////////////9/vym0n/4+/T////y+e3///9TqQlVqQv////6/Pfn897////r9eJYqxRQpwVZqxG63Jz9/vyr1YZUqQperhjw+Or////8/vv///9crRZksSFdrRZVqQtxtzNgrxtksSFWqg3///////////////9Xqg5lsSJerhlisB5ksSFZqxH////g79Ls9uX////7/fn///9UqQpjsB9isB5jsB9crRX////+/v3////9/vxrtCr0+fD///9YqxBksSFXqw/////0+vDh79T///////7////k8dhhrxxksSBcrRX////9/vz///////////9Wqg1crRX////o8970+e/////3+/P////5/PZNpQD////9/v3////////////3+/P////7/fn////r9eL8/fr5/Pf////9/v3+//683Z9stSyRx2GZzG17vEKYy2yWymmNxlt1uTl7vUJ9vkWJxFZnsybI47C93qHP57qazG5osyeeznV5uz5msiRxuDTM5bZisB7F4qzE4atlsSKKxFeu1otlsSLV6sKKxFdksSGSyGNlsSLU6cK12pWKxFd9vkXH46/K5LJ3uzzx+Ov////wO0ibAAAAxXRSTlMAAAAAAAAAAweA7fx/AAQiyf34AAQ77Pz6xQAESPb8/NEaAARK+f79/NQXAARE+P/9/NgZAwEEBAMBBDjz/f3mIwMBAAEEBCnq/f/++j4EABBoeDwEARja/PxrBQC9/dhoEsL7/KkDAAD5+vr9//H9/v7mGQKP/P/9/P79//xgBACI+//+///8vgIAAAOg+/////49AwABAQfK/f/8qwMAAAMw9/7//zgCAAR9+/y0AgAAAQjU+kwDAANQ0wQBAAACp2ADADdtVJUAAAABYktHRAJmC3xkAAAAB3RJTUUH4gMHFx04gygzNwAAAV9JREFUGNNjYIADVjZGdiYOTi5uHiQxXiY+Jn6Bo4LHhBBiwkwiTKJix8UlTkjCxJikmKSZZGRPysmfUlCEiSkxKTOpqKqpa5zW1GKCimkz6TDp6ukbGJ4xMmYyYTA1M7ewZGKyYrK2OWtrd87egcmRicGJyZnZxdWNyd3jvKeXt48vkx+TP0NAYFBwSChTWPiFiIuRl6KYoplimBhiL8ddiU9ITLqafC3leipTWnoG0J7MrOycG7l5+QU3C28VFTOVMLECLS69XVZeUVlVfaem9m4dUz1TA9g1jfea7je3tLa1P+hg6uzqBjuyh6n3Yd+j/gkTJz2ezDRlKsTh06bPmPlk1tPZc57NZZo3fwHUMwuZFi1+vmTpshfLmVashPowg2kV0+qXa9a+Wse0fsNGWFB0b2LavOX11jfbmLbvYIIHJNNOpl1v3+3ew7R3IyLI9+0/wHLw/SGmw0dgCgHI3nIzcXxkogAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxOC0wMy0wN1QyMzoyOTo1Ni0wNTowMK//FQoAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTgtMDMtMDdUMjM6Mjk6NTYtMDU6MDDeoq22AAAAAElFTkSuQmCC");
    
}

/* Add a red text color and an "x" when the requirements are wrong */
.invalid {
    color: red;
}

.invalid:before {
    position: relative;
    left: -35px;
    content:  url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAIAAAAC64paAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABmJLR0QA/wD/AP+gvaeTAAAAB3RJTUUH4gMHFx86XxAwmQAAAjpJREFUOMuNkztIa0EQhv+T53kkEcTCIiCKKFiJYGdnG9RaELRMZ2ehrcYuYKethQEhiCm0FdFCOwsL0whiJUFBkaP5d+YWZz3n+uLeZZsd5tuZ+WcG+j/HGFXV62tTqejzc2zBVz8R7XbtJa2RVFXZ3ydgZmc1DCNP/COmiKpqt6uqplolQMBUKvr+/jly5Hd7K42GWVszCwvSaMRhNQw5NkaAPT0EZG/vMxx9v7FBgNksAabTcnZmfz4+JkDPYz7PXE6urj7DkSo3NyyV6PsslQhwfFxfXlTVzMwQYLFIwExN/VRzJEy9ToBBwCAgYFZX5fycAF03ssjOTpTpN8GMUVVTqVg+l2MQsK+P2Sw9j47DwUF9evpF7Sj5uzuWy3QcW2Q2m4St1eIcf2pVlPzhIR2HrpvcVIrlsj48xK35pc9hqKpmbo4AfZ/5PH2fgNTrSfO+wvF4qWq7zZERm7nrMpNhb6/e3ycTYWERJS0joqrSbLK/35K+zyCg5xGQZvP3yK+v0mqZ6Wk7Jx+MTR7g8LB2On/VTGqnI62WqVY5NESAjsNikYUCUykCZn7erKwQYKFAwCwtJWrL1lZkjeaRnsd02j4HBmR316pRq8W8HB1FPNQYOTgwExOW8TyOjprFRWk27eqS0Q7J+rrdqsnJj62KpAtDubiQkxNtt/XtLRE/XuluV1Vle5uuS0A2Nz8EI2P1kzn5boz401OWy8zl5PLSUVUAUIUIADiOvT8eEpkMOh1ZXsbj4x/lT28M0q2caQAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxOC0wMy0wN1QyMzozMTo1OC0wNTowMC2Z8WMAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTgtMDMtMDdUMjM6MzE6NTgtMDU6MDBcxEnfAAAAAElFTkSuQmCC");
   
}
</style>
</head>

<body>
 
 <div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Password</strong></h3>
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
         <h3 >Merchant List</h3>
         </div>
  

				<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
					<div class="input-field col s12 m5 l5">
				
				
				<select name="merchantName" style="width:100%"  class="browser-default select-filter"
									
								id="merchantName" path="merchantName"  onchange="javascript:location.href = this.value;">
								<!-- onclick="javascript: locate();"> -->
								<!-- <optgroup label="Business Names" style="width:100%"> -->
								<option selected value="">
								 <c:out value="Business Name" /> 
								</option>
								
								
								<c:forEach items="${merchant1}" var="merchant1">
									
									
									
									
									<c:if test="${merchant1.mid.mid!=null || merchant1.mid.motoMid!=null || merchant1.mid.ezypassMid!=null
									|| merchant1.mid.ezywayMid!=null || merchant1.mid.ezyrecMid!=null}">
									<option value="${pageContext.request.contextPath}/superagent/MerchantDetailsToChangePassword?mid=${merchant1.id}">
									${merchant1.businessName}~MID
									 <c:choose>
	         							<c:when test = "${merchant1.mid.mid!=null}">
         									 ~${merchant1.mid.mid }
                                        </c:when>
	         							<c:when test = "${merchant1.mid.motoMid!=null}">
            								 ~${merchant1.mid.motoMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.ezywayMid!=null}">
         									 ~${merchant1.mid.ezywayMid }
                                        </c:when>
	         							<c:when test = "${merchant1.mid.ezyrecMid!=null}">
            								 ~${merchant1.mid.ezyrecMid}
        								</c:when>
         								<c:otherwise>
          									 ~${merchant1.mid.ezypassMid}
        								</c:otherwise>
     								 </c:choose>
									
									
									</option>
</c:if>

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
									
							</div>
							
						</div>	</div>
						</div></div></div>

			
			 
<div class="row">
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
			<center><button class="btn btn-primary blue-btn"  id="buttonSub1" type="submit" onclick="return displayMerchant();">
Change Merchant Password</button>
<button class="btn btn-primary blue-btn"  id="buttonSub1" type="submit" onclick="return displayMobileuser();">
Change MobileApp User Password</button></center>
			
			</div>
  </div></div></div>
  
  
  <style>
				
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
  

  
  
  
 
 	<form method="get"  id="form"
			name="form" action="${pageContext.request.contextPath}/superagent/ChangeMerchPass" >
	<div class="content-wrapper"  id="merchantDiv" style="display:none;">
		 <p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;margin-top:-100px;"><b>${requestScope.responseData}</b></p>
		
		<div class="row" >
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
			 <div class="d-flex align-items-center">	
			 <h3 >Merchant Details</h3>
				</div>

					
					<input type="hidden" name="id" id="id" value="${merchant.id}"/>
			<div class="row">
				<div class="input-field col s12 m6 l6 ">
						<label for="Business Name">Business Name</label>
					 <input  type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 path="businessName">
					
				</div><div class="input-field col s12 m6 l6 ">
						<label for="Business Name">User Name</label>
					 <input type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.username}" readonly="readonly"
					 path="businessName">
					
				</div><div class="input-field col s12 m6 l6 ">
						<label for="Email">Email</label>
					<input  type="email" placeholder="email" name="email"  
					id="email" value="${merchant.email}" readonly="readonly" path="email" >
					
				
			</div>
	
		<%-- 	<c:if test="${merchant.mid.umMid!=null}">
			<div class="input-field col s12 m6 l6 ">
								
									<label for="MID">UM_EZYWIRE MID</label>
									<input  type="text" placeholder="UM_EZYWIRE MID"
										name="umMid" id="umMid" path="umMid" value="${merchant.mid.umMid}"
										readonly="readonly">
							</div>
						
				</c:if> --%>
				<c:if test="${merchant.mid.umMotoMid!=null}">
				<div class="input-field col s12 m6 l6 ">
					
					
						<label for="MID">EZYMOTO MID</label>
						<input  type="text" placeholder="UM_EZYMOTO MID"
							name="umMotoMid" id="umMotoMid" path="umMotoMid" value="${merchant.mid.umMotoMid}"
							readonly="readonly">
				</div>
			</c:if>	
			 
		<%-- 	 <c:if test="${merchant.mid.umEzywayMid!=null}">
					<div class="form-group col-md-4">
						<div class="form-group">
						
						
							<label for="MID">UM_EZYWAY MID</label>
							<input class="form-control" type="text" placeholder="UM_EZYWAY MID"
								name="umEzywayMid" id="umEzywayMid" path="umEzywayMid"
								value="${merchant.mid.umEzywayMid}" readonly="readonly">
						


				
					</div>
				</div>
			 </c:if> --%>
	
	</div>
			
			<div class="row">
			
			<div class="input-field col s12 m6 l6 ">
						<label for="MID">Enter Merchant Password</label>
				<input  type="text" placeholder="Enter Password To change" name="merchantPass"  
				id="merchantPass"   value="" onchange="return validatePassword();"
				style="border-color: #5AAAFA ;" >	
					
				</div>
			
			</div>



<button class="submitBtn"  id="buttonSub1" type="submit" onclick="return submitMerchantPass();">
Submit</button>


</div></div>

</div>

 </div>
 </div>
 
 </form>
 
  <form method="get"  id="form"
			name="form" action="${pageContext.request.contextPath}/superagent/ChangeMobileuserPass">
 
			<input type="hidden" name="merchant_Id" id="merchant_Id" value="${merchant.id}"/>
	<div class="content-wrapper" id="mobileuserDiv" style="margin-top:-10px;display:none">
		<div class="row">
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
			 <div class="d-flex align-items-center">	
			 <h3 >Mobile User Details</h3>
				</div>
					
					
			<div class="row">
				<div class="input-field col s12 m3 l3">



									
									<select name="mobileUserIds" id="mobileUserIds" onchange="changeValue();"
										path="username" style="width:100%">
										<option selected value=""><c:out value="mobUsername" /></option>
										<%-- <c:forEach items="${mobUsername}" var="mobUsername">
											<option value="${mobUsername}" title="${mobUsername}">${mobUsername}</option>


										</c:forEach> --%>
										<c:forEach items="${mobileuser}" var="mobileuser">
											<option value="${mobileuser.id},${mobileuser.tid},${mobileuser.motoTid},
											${mobileuser.ezypassTid},${mobileuser.ezyrecTid},${mobileuser.ezywayTid}" 
											title="${mobileuser.id}">${mobileuser.username}</option>


										</c:forEach>
										
									</select>
									
									
									<label for="deviceId">Mobile User Name</label>
								</div>
							
							
							</div>
							<div class="row">
								<div class="input-field col s12 m6 l6 " style="display:none;" id="tid1">
										<label for="TID">TID</label> <input
											type="text"
											placeholder="TID" name="tid"
											id="tid" value="" readonly="true"
											>

									</div>
								
								
								<div class="input-field col s12 m6 l6 " style="display:none;" id="motoTid1">
										<label for="MOTO TID">EZYMOTO TID</label> <input
											 type="text"
											placeholder="MOTO TID" name="motoTid"
											id="motoTid"  value="" readonly="true"
											>

									</div>
								
								<div class="input-field col s12 m6 l6 " style="display:none;" id="ezypassTid1">
										<label for="EZYPASS TID">EZYPASS TID</label> <input
											type="text"
											placeholder="ezypassTid" name="ezypassTid"
											id="ezypassTid"  value="" readonly="true"
											>

									
								</div>
								<div class="input-field col s12 m6 l6 "  style="display:none;" id="ezywayTid1">
										<label for="EZYWAY TID">EZYWAY TID</label> <input
											type="text"
											placeholder="ezywayTid" name="ezywayTid"
											id="ezywayTid" value="" readonly="true"
											>

									</div>
								<div class="input-field col s12 m6 l6 " style="display:none;" id="ezyrecTid1">
										<label for="EZYREC TID">EZYREC TID</label> <input
											 type="text"
											placeholder="ezyrecTid" name="ezyrecTid"
											id="ezyrecTid"  value="" readonly="true"
											>

									</div>
								
								
								<input
											class="form-control" type="hidden"
											placeholder="mobileUserId" name="mobileUserId"
											id="mobileUserId"  value=""
											>

							</div>
							
							
							<div class="row">
						<div class="input-field col s12 m3 l3">
						<label for="MID">Enter MobileUser Password</label>
				<input type="text" placeholder="Enter Password To change" name="mobileuserPass"  
				id="mobileuserPass"  path="mobileuserPass" value="" style="border-color: #5AAAFA ;">	
					
				</div>	
				
					
		
						</div>
						<button class="submitBtn"  id="buttonSub2" type="submit" onclick="return submitMobileuserPass();">
Submit</button>
</div>
			
</div>





</div>

</div>
 </div>
</form>
</div>
  <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#merchantName').select2();
		/* $('#mobileUserIds').select2(); */
		//$('#txnType').select2();
	});
</script>
  
</body>

</html>
		
		