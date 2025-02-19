<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%> 
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobile User Summary</title>    
   

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script lang="JavaScript">
function validateRenewal(){
	var renewalPeriod=document.getElementById('renewalPeriod').value;
	//alert(renewalPeriod);
	if(renewalPeriod==null || renewalPeriod==''){
		//alert('empty '+renewalPeriod);
		document.getElementById('renewalPeriod').value=0;
		//alert('empty '+renewalPeriod);
	}
	
	
}
 


function loadCancelData() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/mobileUser/list/1";
	form.submit;
}

function loadSelectData()
{
/* alert("test data"); */

 var a = document.getElementById("salutation").value;
       
       if (a == null || a == '' ) {
       
			alert("Please Select salutation");
			/* form.submit = false; */
			return false;
			}
   

if(!allLetterSpaceSpecialCharacter(document.form1.firstName, 3, 30)){
		
		return false;
		}
		
		
/* if(!allLetter(document.form1.lastName, 3, 30)){
		
		return false;
		} */
		
if(!allnumeric(document.form1.contact, 9, 11))
		  {
		    return false;
		  }
		
		
		if(!validateEmail(document.form1.email))
		{
		
		return false;
		}
}

    
    
    function allLetter(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[A-Za-z]+$/;
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphabet characters only');
			//uname.focus();  
			return false;
		}
	}
	
	
	function allnumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;

		var mnlen = minlength;
		var mxlen = maxlength;
		//var uzip = document.registration.zip;  
		var numbers = /^[0-9]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(numbers)) {
			// Focus goes to next field i.e. email.  
			//document.registration.email.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have numeric characters only');
			//uzip.focus();  
			return false;
		}
	}
	
	
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;
		} else {
			alert("You have entered an invalid email address!");
			//uemail.focus();  
			return false;
		}
	}
	
	
	function allLetterSpace(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters =  /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the Contact Name  between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric and space only');
			//uname.focus();  
			return false;
		}
	}
	
	function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
		//alert("TEste :"+ inputtxt);
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[ A-Za-z_()./&-@]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric and space and special characters with -,&,/,() only');
			//uname.focus();  
			return false;
		}
	}
	
	
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;
			
		} else if (field.length==0)
		 {
			alert("You have entered  "+inputtxt.name+" address!");
			//uemail.focus();  
			return false;
		} else {
		alert("You have entered an invalid "+inputtxt.name+" address!");
		}
	}
      
      
      
      
      function loadtype(type,type1){
	//alert(' type'+type);
	// var bustype = document.getElementById("businessType1").value;
	//alert('type1 : '+type1);
	//var bustype1 = document.getElementById("businessType");
	//alert('ddd :'+bustype1); 
	var i=0;
	for(i = 0 ; i<type1.options.length ; i++){
	
		if (type1.options[i].value == type)
			{

			// Item is found. Set its property and exit

			type1.options[i].selected = true;
		} /* else{
			type1.options[0].selected = true;
		}  */
	 }  

} 


function loadtype1(type,type1){
	
	var i=0;
	
	for(i = 0 ; i<type1.options.length ; i++){
	
	//alert(type1.options[i].value.toUpperCase() +": "+ type.toUpperCase());
		if (type1.options[i].value.toUpperCase() == type.toUpperCase())
			{
			type1.options[i].selected = true;
		}
	 }  

} 

function radioAuth(){
//var a= ${merchant.preAuth};
var a = document.getElementById("testAuth").value;
//alert("Auth :"+a);
if(a == 'Yes'){
	document.getElementById("preAuth1").checked = true;
}else{
	document.getElementById("preAuth2").checked = true;
}
/* if($)
	document.getElementById("preAuth").value =  */
}



function loadDropData()
{
 

//alert("test data");

//contact sal
	//alert('sal');
	
	
	 var sal = document.getElementById("contsalutation").value;
	 
	
	
	var salu = document.getElementById("salutation");
	
	
	
	loadtype1(sal,salu);
	//}
	
	}
	
	
	 function checkRenewal()
       {
       
       var monthNames = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
		
		var deviceStatus = document.getElementById("status").value;
		
		if(deviceStatus=='ACTIVE'){
		
			var exp = document.getElementById("expiry").value;
			//alert(" test : "+exp);
			var renewal = document.getElementById("renewalPeriod").value;
			var CurrentDate = new Date(exp);
			//alert(" test : "+CurrentDate);
			CurrentDate.setMonth(CurrentDate.getMonth() + parseInt(renewal));
			var day = CurrentDate.getDate();
	  		var monthIndex = monthNames[CurrentDate.getMonth()];
	  		var year = CurrentDate.getFullYear();
			
	  		exp=day+"-"+monthIndex+"-"+year;
	  		//alert("expirydate: "+exp)
	  		if(exp!=null && exp!='NaN-undefined-NaN'){
	  			document.getElementById("expiryDate").value=exp; 
	  			//alert("expirydate1: "+exp)
	  		}else if(exp=='NaN-undefined-NaN'){
	  			
	  			document.getElementById("expiryDate").value=document.getElementById("expiry").value;
	  			//alert("expirydate2: "+document.getElementById("expiryDate").value)
	  		}
	  		
	  		//alert(exp)
	  		
			//alert(" Active TEST : "+document.getElementById("expiryDate").value);
		}else{
		
			var renewal = document.getElementById("renewalPeriod").value;
			var CurrentDate = new Date();
			CurrentDate.setMonth(CurrentDate.getMonth() + parseInt(renewal));
			var day = CurrentDate.getDate();
	  		var monthIndex = monthNames[CurrentDate.getMonth()];
	  		var year = CurrentDate.getFullYear();
			
			document.getElementById("expiryDate").value=day+"-"+monthIndex+"-"+year;
			document.getElementById("status").value='ACTIVE';
			//alert(" Suspend TEST : "+document.getElementById("expiryDate").value);
			//alert(" Suspend TEST : "+document.getElementById("status").value);
		}
		
       }
	
	
</script>

<script lang="JavaScript">
function checkRadio()
{
//alert("check radio");
 var Moto1=document.getElementById("Moto1").checked;
  var Moto2=document.getElementById("Moto2").checked;
 var Boost1=document.getElementById("Boost1").checked;
 var Boost2=document.getElementById("Boost2").checked;
 //alert("check radio1: "+radio1+" check radio2"+radio2);
if(Moto1==false || Moto2==false)
{
if(Boost1==false || Boost2==false)
{
alert("please select Boost Enable/Disable and Moto Enable/Disable ..");
return false;
}
}
else
{

return true;
}
return false;
}

function validateCheckRadio()
{
var form1=document.getElementById("form1");
//alert("checkRaio"+checkRadio());
if(!checkRadio())
{
//alert("checked false");
return false;

}
else
{
//alert("checked true");
document.getElementById("form1").submit();
return true;
}

}
</script>
</head>

  <body onload="radioAuth()">
  <form action="${pageContext.request.contextPath}<%=MobileUserController.URL_BASE%>/editMobileuser" method="post"
		 id="form1" name="form1" commandName="mobileUser">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="tid" value="${mobileUser.tid}" />
<div class="container-fluid">    
  <div class="row"> 
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Mobile User Summary</strong></h3>
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
							<div class="input-field col s12 m6 l6 ">
									<label for="firstName">Activation Date</label>
									<input type="text"  name="activationDate"  readonly="readonly"
									value="${mobileUser.activationDate}" />
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="firstName">Mobile UserName</label>
									<input type="text"  name="mobileUserName"   readonly="readonly"
									value="${mobileUser.mobileUserName}" />
								</div>
							

</div>


<div class="row">
							<div class="input-field col s12 m6 l6 ">
									
									<%-- <input type="text"  name="status" id="status" class="form-control" value="${mobileUser.status}" />
									 --%>
									<select name="status"  value="${mobileUser.status}"
										id="status"  style="width:100%">
									<%-- 	<option selected value=""><c:out value="Status" /></option> --%>
										
										<c:choose>
										    <c:when test="${mobileUser.status == 'ACTIVE'}">
										     <option value="ACTIVE" selected="selected">ACTIVE</option>
										    </c:when>    
										    <c:otherwise>
										       <option value="ACTIVE" >ACTIVE</option>
										    </c:otherwise>
										</c:choose>
									
									<c:choose>
									    <c:when test="${mobileUser.status == 'SUSPENDED'}">
									      <option value="SUSPENDED" selected="selected">SUSPENDED</option>
									    </c:when>    
									    <c:otherwise>
									       <option value="SUSPENDED" >SUSPENDED</option>
									    </c:otherwise>
									</c:choose>
										
										


										
									</select>
									<label for="contact">Status</label>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="email">Renewal Date</label>
									<input type="text"  name="renewalDate"  readonly="readonly"
									 value="${mobileUser.renewalDate}" 
									/>
								</div>
							</div>

<div class="row">						
							<div class="input-field col s12 m6 l6 ">
								<label for="Renewal Period">Renewal Period</label>
								<input type="text"  name="renewalPeriod" id="renewalPeriod"  value="1" onblur="checkRenewal()"
								onchange="validateRenewal()"/>
		
							</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="email">Expiry Date <font style="color:red;">*${responseErr}</font></label>
									<input type="hidden"  id="expiry" name="expiry" value="${mobileUser.expiryDate}" />
									<input type="text"  id="expiryDate" name="expiryDate" class="form-control" value="${mobileUser.expiryDate}" 
									onchange="validateExpDate();"/>
								</div>
							

</div>



<div class="row">			
 						<div class="input-field col s12 m6 l6 ">
									<label for="email">Remarks</label>
									<input type="text"  id="remarks" name="remarks" class="form-control" value="${mobileUser.remarks}" />
								</div>											
                           
                         <div class="input-field col s12 m6 l6 ">Pre-Auth:
                            		
                            		
                            		<input type="hidden" id="testAuth" value="${mobileUser.preAuth}"/>
                            		<p><label> <input type="radio" name ="preAuth" id="preAuth1"  value="Yes" />
                            		<span>Yes</span></label>   
									<label><input type="radio" name ="preAuth" id="preAuth2" value="No"  />
									<span>No</span></label></p>
									

     							 </div>
     
     						
							
      </div>
     
     <div class="row">														
                           
                           <div class="input-field col s12 m6 l6 ">Boost Enable:

                            		<input type="hidden" id="testAuth" value="${mobileUser.enableBoost}"/>
                            		<p><label>  <input type="radio" name ="Boost" id="Boost1"  value="Yes"  
                                  <c:if test="${mobileUser.enableBoost=='Yes'}">checked</c:if>/>
                            		<span>Yes</span></label>  
                            		 
									<label><input type="radio" name ="Boost" id="Boost1" value="No"  
                                           <c:if test="${mobileUser.enableBoost=='No'}">checked</c:if>/>
									<span>No</span></label></p>
                                    
						
      </div>
     							 <div class="input-field col s12 m6 l6 ">EZYMOTO Enable:
                            		
									
                            		<input type="hidden" id="testMoto" value="${mobileUser.enableMoto}"/>
                            		
                            		<p><label>  <input type="radio" name ="Moto" id="Moto1"  value="Yes" 
                                           <c:if test="${mobileUser.enableMoto=='Yes'}">checked</c:if>/>
                            		<span>Yes</span></label>  
                            		 
									<label> <input type="radio" name ="Moto" id="Moto1" value="No"  
                                           <c:if test="${mobileUser.enableMoto=='No'}">checked</c:if>/>
									<span>No</span></label></p>
                            		
                                    
							
							
      </div>
      
		 
        </div>
             <div class="row">														
                           
                          <div class="input-field col s12 m6 l6 " >
                         <div class="button-class">
      <button type="submit" class="btn btn-primary blue-btn" onclick="return validateCheckRadio();">Submit</button>
 
      <button type="button"  class="btn btn-primary blue-btn" onclick="return loadCancelData()">Cancel</button>
      
                           </div>
                           </div>
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
  </form>
</body> 
</html>