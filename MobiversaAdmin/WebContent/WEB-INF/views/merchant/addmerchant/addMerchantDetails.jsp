<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en"> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Merchant</title>    
 <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>  
	<!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#salutation').select2();
$('#businessState').select2();
 $('#ownerSalutation1').select2();
$('#ownerSalutation2').select2();
 $('#ownerSalutation3').select2();
$('#ownerSalutation4').select2();
$('#ownerSalutation5').select2();  
$('#businessType').select2();
$('#companyType').select2();
$('#natureOfBusiness').select2();
$('#documents').select2();
$('#signedPackage').select2();
$('#status').select2();
$('#agentName').select2();
$('#merchantType').select2();
});  
    </script> -->
 <style>
    .submitBtn{
	
    width: 140px;
    height: 55px;
  border-radius: 49px;
  box-shadow: 0 3px 6px 0 rgba(15, 88, 22, 0.55);
  background-color: #53b749;
  text-align: center;
  margin-bottom: 12px;
   margin-left: 60px;
}

.submitBtn span{
	width: 98px;
  height: 35px;
  text-shadow: 0 3px 6px rgba(0, 0, 0, 0.34);
  font-family: Helvetica;
  font-size: 15px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: 1.21;
  letter-spacing: normal;
  text-align: center;
  color: #ffffff;
  display: inline-block;
  margin-top: 18px;
}
    </style>
  <script type="text/javascript">
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

    </script> 
   
    <script type="text/javascript">
function changeErrorStyle(id){

var field=document.getElementById(id);


field.style.border = "1px solid #FB2002";
field.focus();
return false;

}  

    </script>    
    
    
    

    
    
    
<script type="text/javascript">
jQuery(document).ready(function() {
$('#checkmid').bind('change', function () {

   if ($(this).is(':checked'))
    $("#mid").attr('readonly',false);
   else
     $("#mid").attr("readonly", true);
});


});  
    </script>
    <script type="text/javascript">
jQuery(document).ready(function() {
$('#checkezymotomid').bind('change', function () {

   if ($(this).is(':checked'))
    $("#ezymotomid").attr('readonly',false);
   else
     $("#ezymotomid").attr("readonly", true);
});


});  
    </script>
    <script type="text/javascript">
jQuery(document).ready(function() {
$('#checkezypassmid').bind('change', function () {

   if ($(this).is(':checked'))
    $("#ezypassmid").attr('readonly',false);
   else
     $("#ezypassmid").attr("readonly", true);
});


});  
    </script>
<!-- To Check the Merchant Type  -->
<script lang="JavaScript">
	function check(data){
		var merchantType = document.getElementById('merchantType').value;
		//alert(merchantType);
		if(merchantType=='U'){
			document.getElementById('mid').disabled =true;
			document.getElementById('ezymotomid').disabled =true;
			document.getElementById('ezypassmid').disabled =true;
			document.getElementById('ezywaymid').disabled =true;
			document.getElementById('ezyrecmid').disabled =true;
			document.getElementById('paydeeInt').style.display = "none";
			
			document.getElementById('mid').value ='';
			document.getElementById('ezymotomid').value ='';
			document.getElementById('ezypassmid').value ='';
			document.getElementById('ezywaymid').value ='';
			document.getElementById('ezyrecmid').value ='';
			
			
			document.getElementById('umMid').disabled =false;
			document.getElementById('umMotoMid').disabled =false;
			document.getElementById('umEzypassMid').disabled =false;
			document.getElementById('umEzywayMid').disabled =false;
			document.getElementById('umEzyrecMid').disabled =false;
			document.getElementById('umobileInt').style.display = "block";
			
		}else if(merchantType=='P'){
			
			document.getElementById('mid').disabled =false;
			document.getElementById('ezymotomid').disabled =false;
			document.getElementById('ezypassmid').disabled =false;
			document.getElementById('ezywaymid').disabled =false;
			document.getElementById('ezyrecmid').disabled =false;
			document.getElementById('paydeeInt').style.display = "block";
			
			document.getElementById('umMid').disabled =true;
			document.getElementById('umMotoMid').disabled =true;
			document.getElementById('umEzypassMid').disabled =true;
			document.getElementById('umEzywayMid').disabled =true;
			document.getElementById('umEzyrecMid').disabled =true;
			document.getElementById('umobileInt').style.display = "none";
			
			document.getElementById('umMid').value ='';
			document.getElementById('umMotoMid').value ='';
			document.getElementById('umEzypassMid').value ='';
			document.getElementById('umEzywayMid').value ='';
			document.getElementById('umEzyrecMid').value ='';
		 }
			
		/*else if(merchantType=='S'){
			
			document.getElementById('mid').disabled =true;
			document.getElementById('ezymotomid').disabled =true;
			document.getElementById('ezypassmid').disabled =true;
			document.getElementById('ezywaymid').disabled =true;
			document.getElementById('ezyrecmid').disabled =true;
			document.getElementById('paydeeInt').style.display = "none";
			
			document.getElementById('umMid').disabled =true;
			document.getElementById('umMotoMid').disabled =true;
			document.getElementById('umEzypassMid').disabled =true;
			document.getElementById('umEzywayMid').disabled =true;
			document.getElementById('umEzyrecMid').disabled =true;
			document.getElementById('umobileInt').style.display = "none";
			
			
			document.getElementById('stMotoMid').disabled =false;
			document.getElementById('stripeInt').style.display = "block";
			
			
			document.getElementById('umMid').value ='';
			document.getElementById('umMotoMid').value ='';
			document.getElementById('umEzypassMid').value ='';
			document.getElementById('umEzywayMid').value ='';
			document.getElementById('umEzyrecMid').value ='';
			
			document.getElementById('mid').value ='';
			document.getElementById('ezymotomid').value ='';
			document.getElementById('ezypassmid').value ='';
			document.getElementById('ezywaymid').value ='';
			document.getElementById('ezyrecmid').value ='';
			
		} */else{
			
			document.getElementById('paydeeInt').style.display = "none";
			document.getElementById('umobileInt').style.display = "none";
			//document.getElementById('stripeInt').style.display = "none";
			
		}

	}
    </script>
<script lang="JavaScript">
	function loadSelectData() {
	
	var mid	=document.getElementById("mid").value;
var ezymotomid=document.getElementById("ezymotomid").value;
var ezypassmid=document.getElementById("ezypassmid").value;
var ezywaymid=document.getElementById("ezywaymid").value;
var ezyrecmid=document.getElementById("ezyrecmid").value;
//alert("mid: "+mid+" ezymotomid: "+ezymotomid+" ezymotomid"+ezymotomid);
if((mid==null && ezymotomid==null && ezypassmid==null && ezywaymid==null && ezyrecmid==null) ||
		(mid=='' && ezymotomid=='' && ezypassmid=='' && ezywaymid=='' && ezyrecmid=='') ){
alert("Please fill anyone of the mid fields...!");

document.getElementById("mid").focus();
changeErrorStyle("mid");

return false;
}
		
	if(document.form1.merchantType.value == '')	{
	alert("Please Select MerchantType/Host Type..");
	}
		
	if (document.form1.mid.value != '') {
			if (!allnumeric(document.form1.mid, 10, 15)) {
			changeErrorStyle("mid");
			document.getElementById("mid").focus();
				return false;
			}
		}

		/* var ezymotomid = document.getElementById("ezymotomid").value;
		alert("moto mid: " + ezymotomid); */
		if (document.form1.ezymotomid.value != '') {
			if (!allnumeric(document.form1.ezymotomid, 10, 15)) {
//document.getElementById("ezymotomid").focus();
changeErrorStyle("ezymotomid");
				return false;
			}
		}
		if (document.form1.ezypassmid.value != '') {
		if (!allnumeric(document.form1.ezypassmid, 10, 15)) {
//document.getElementById("ezypassmid").focus();
changeErrorStyle("ezypassmid");
			return false;
		}
		}
		
		if (document.form1.ezywaymid.value != '') {
			if (!allnumeric(document.form1.ezywaymid, 10, 15)) {
	//document.getElementById("ezypassmid").focus();
	changeErrorStyle("ezywaymid");
				return false;
			}
			}


		if (document.form1.ezyrecmid.value != '') {
			if (!allnumeric(document.form1.ezyrecmid, 10, 15)) {
	//document.getElementById("ezypassmid").focus();
	changeErrorStyle("ezyrecmid");
				return false;
			}
			}
		
		if (!allLetterSpaceSpecialCharacter(document.form1.registeredName, 3,
				100)) {
				changeErrorStyle("registeredName");
//document.getElementById("registeredName").focus();

			return false;
		}

		if (!allLetterSpaceSpecialCharacter(document.form1.businessName, 3, 100)) {
		changeErrorStyle("businessName");
//document.getElementById("businessName").focus();

			return false;
		}
		if (!alphanumeric(document.form1.businessRegNo, 6, 15)) {
		changeErrorStyle("businessRegNo");
//document.getElementById("businessRegNo").focus();

			return false;
		}
		if (!stringlength(document.form1.registeredAddress, 3, 100)) {
		//document.getElementById("Registered Address").focus();
		changeErrorStyle("Registered Address");
			return false;
		}

		if (!allLetter(document.form1.businessCity, 3, 15)) {
		//document.getElementById("businessCity").focus();
		changeErrorStyle("businessCity");
			return false;
		}
		if (!alphanumeric(document.form1.businessPostCode, 5, 5)) {
		changeErrorStyle("businessPostCode");
		//document.getElementById("businessPostCode").focus();
			return false;
		}

		var e8 = document.getElementById("businessState").value;

		if (e8 == null || e8 == '') {

			alert("Please Select state");
			changeErrorStyle("businessState");
			//document.getElementById("businessState").focus();
			// form.submit = false; 
			return false;
		}

		if (!allLetter(document.form1.salutation, 2, 10)) {
		changeErrorStyle("salutation");
		//document.getElementById("salutation").focus();
			return false;
		}

		if (!allLetterSpaceSpecialCharacter(document.form1.name, 3, 30)) {
			changeErrorStyle("name");
			//document.getElementById("name").focus();
			return false;
		}

		if (!validateEmail(document.form1.email, 10, 20)) {
			changeErrorStyle("email");
			//document.getElementById("email").focus();
			return false;
		}

		if (!allnumeric(document.form1.contactNo, 9, 11)) {
			changeErrorStyle("contactNo");
			//document.getElementById("contactNo").focus();
			return false;
		}

		var e1 = document.getElementById("ownerCount").value;
		var i = 0;
		if (e1 == 1) {

//alert("owner count: "+e1);
			if (!allLetter(document.form1.ownerSalutation1, 2, 10)) {
			changeErrorStyle("ownerSalutation1");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3,
					30)) {
					changeErrorStyle("ownerName1");
				return false;
			}

			if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
			changeErrorStyle("ownerContactNo1");
				return false;
			}

			if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
			changeErrorStyle("passportNo1");
				return false;
			}

			if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
			changeErrorStyle("residentialAddress1");
				return false;
			}
		} else if (e1 == 2) {

//alert("owner count: "+e1);
			if (!allLetter(document.form1.ownerSalutation1, 2, 10)) {
			changeErrorStyle("ownerSalutation1");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3,
					30)) {
					changeErrorStyle("ownerName1");
				return false;
			}

			if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
			changeErrorStyle("ownerContactNo1");
				return false;
			}

			if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
			changeErrorStyle("passportNo1");
				return false;
			}

			if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
			changeErrorStyle("residentialAddress1");
				return false;
			}

			if (!allLetter(document.form1.ownerSalutation2, 2, 10)) {
			changeErrorStyle("ownerSalutation2");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3,
					30)) {
					changeErrorStyle("ownerName2");
				return false;
			}

			if (document.form1.ownerContactNo2.value != '')

			{
				if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
				changeErrorStyle("ownerContactNo2");
					return false;
				}
			}
			if (document.form1.passportNo2.value != '')

			{
				if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
				changeErrorStyle("passportNo2");
					return false;
				}
			}
			if (document.form1.residentialAddress2.value != '')

			{
				if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
				changeErrorStyle("residentialAddress2");
					return false;
				}
			}
		} else if (e1 == 3) {

alert("owner count: "+e1);
			if (!allLetter(document.form1.ownerSalutation1, 2, 10)) {
			changeErrorStyle("ownerSalutation1");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3,
					30)) {
					changeErrorStyle("ownerName1");
				return false;
			}

			if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
			changeErrorStyle("ownerContactNo1");
				return false;
			}

			if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
			changeErrorStyle("passportNo1");
				return false;
			}

			if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
			changeErrorStyle("residentialAddress1");
				return false;
			}

			if (!allLetter(document.form1.ownerSalutation2, 2, 10)) {
			changeErrorStyle("ownerSalutation2");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3,
					30)) {
					changeErrorStyle("ownerName2");
				return false;
			}
			if (document.form1.ownerContactNo2.value != '')

			{
				if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
				changeErrorStyle("ownerContactNo2");
					return false;
				}
			}
			if (document.form1.passportNo2.value != '')

			{
				if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
				changeErrorStyle("passportNo2");
					return false;
				}
			}
			if (document.form1.residentialAddress2.value != '')

			{
				if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
				changeErrorStyle("residentialAddress2");
					return false;
				}

			}

			if (!allLetter(document.form1.ownerSalutation3, 2, 10)) {
			changeErrorStyle("ownerSalutation3");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3,
					30)) {
					changeErrorStyle("ownerName3");
				return false;
			}
			if (document.form1.ownerContactNo3.value != '')

			{
				if (!allnumeric(document.form1.ownerContactNo3, 9, 11)) {
				changeErrorStyle("ownerContactNo3");
					return false;
				}
			}

			if (document.form1.passportNo3.value != '') {
				if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
				changeErrorStyle("passportNo3");
					return false;
				}
			}
			if (document.form1.residentialAddress3.value != '') {
				if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
				changeErrorStyle("residentialAddress3");
					return false;
				}
			}
		} else if (e1 == 4) {

alert("owner count: "+e1);
			if (!allLetter(document.form1.ownerSalutation1, 2, 10)) {
			changeErrorStyle("ownerSalutation1");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3,
					30)) {
					changeErrorStyle("ownerName1");
				return false;
			}

			if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
			changeErrorStyle("ownerContactNo1");
				return false;
			}

			if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
			changeErrorStyle("passportNo1");
				return false;
			}

			if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
			changeErrorStyle("residentialAddress1");
				return false;
			}

			if (!allLetter(document.form1.ownerSalutation2, 2, 10)) {
			changeErrorStyle("ownerSalutation2");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3,
					30)) {
					changeErrorStyle("ownerName2");
				return false;
			}

			if (document.form1.ownerContactNo2.value != '') {
				if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
				changeErrorStyle("ownerContactNo2");
					return false;
				}
			}
			if (document.form1.passportNo2.value != '') {
				if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
				changeErrorStyle("passportNo2");
					return false;
				}
			}
			if (document.form1.residentialAddress2.value != '') {
				if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
				changeErrorStyle("residentialAddress2");
					return false;
				}
			}

			if (!allLetter(document.form1.ownerSalutation3, 2, 10)) {
			changeErrorStyle("ownerSalutation3");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3,
					30)) {
					changeErrorStyle("ownerName3");
				return false;
			}
			if (document.form1.ownerContactNo3.value != '') {
				if (!allnumeric(document.form1.ownerContactNo3, 9, 11)) {
				changeErrorStyle("ownerContactNo3");
					return false;
				}
			}
			if (document.form1.passportNo3.value != '') {
				if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
				changeErrorStyle("passportNo3");
					return false;
				}
			}

			if (document.form1.residentialAddress3.value != '') {
				if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
				changeErrorStyle("residentialAddress3");
					return false;
				}
			}

			if (!allLetter(document.form1.ownerSalutation4, 2, 10)) {
			changeErrorStyle("ownerSalutation4");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName4, 3,
					30)) {
					changeErrorStyle("ownerName4");
				return false;
			}
			if (document.form1.ownerContactNo4.value != '') {
				if (!allnumeric(document.form1.ownerContactNo4, 9, 11)) {
				changeErrorStyle("ownerContactNo4");
					return false;
				}
			}

			if (document.form1.passportNo4.value != '') {
				if (!alphanumeric(document.form1.passportNo4, 9, 15)) {
				changeErrorStyle("passportNo4");
					return false;
				}
			}
			if (document.form1.residentialAddress4.value != '') {
				if (!stringlength(document.form1.residentialAddress4, 3, 100)) {
				changeErrorStyle("residentialAddress4");
					return false;
				}
			}
		} else if (e1 == 5) {
alert("owner count: "+e1);
			if (!allLetter(document.form1.ownerSalutation1, 2, 10)) {
			changeErrorStyle("ownerSalutation1");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3,
					30)) {
					changeErrorStyle("ownerName1");
				return false;
			}

			if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
			changeErrorStyle("ownerContactNo1");
				return false;
			}

			if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
			changeErrorStyle("passportNo1");
				return false;
			}

			if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
			changeErrorStyle("residentialAddress1");
				return false;
			}

			if (!allLetter(document.form1.ownerSalutation2, 2, 10)) {
			changeErrorStyle("ownerSalutation2");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3,
					30)) {
					changeErrorStyle("ownerName2");
				return false;
			}
			if (document.form1.ownerContactNo2.value != '') {
				if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
				changeErrorStyle("ownerContactNo2");
					return false;
				}
			}

			if (document.form1.passportNo2.value != '') {
				if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
				changeErrorStyle("passportNo2");
					return false;
				}
			}
			if (document.form1.residentialAddress2.value != '') {
				if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
				changeErrorStyle("residentialAddress2");
					return false;
				}

			}

			if (!allLetter(document.form1.ownerSalutation3, 2, 10)) {
			changeErrorStyle("ownerSalutation3");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3,
					30)) {
					changeErrorStyle("ownerName3");
				return false;
			}
			if (document.form1.ownerContactNo3.value != '') {

				if (!allnumeric(document.form1.ownerContactNo3, 9, 11)) {
					changeErrorStyle("ownerContactNo3");
					return false;
				}
			}
			if (document.form1.passportNo3.value != '') {
				if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
				changeErrorStyle("passportNo3");
					return false;
				}
			}

			if (document.form1.residentialAddress3.value != '') {
				if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
				changeErrorStyle("residentialAddress3");
					return false;
				}
			}

			if (!allLetter(document.form1.ownerSalutation4, 2, 10)) {
			changeErrorStyle("ownerSalutation4");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName4, 3,
					30)) {
					changeErrorStyle("ownerName4");
				return false;
			}
			if (document.form1.ownerContactNo4.value != '') {
				if (!allnumeric(document.form1.ownerContactNo4, 9, 11)) {
				changeErrorStyle("ownerContactNo4");
					return false;
				}
			}
			if (document.form1.passportNo4.value != '') {
				if (!alphanumeric(document.form1.passportNo4, 9, 15)) {
				changeErrorStyle("passportNo4");
					return false;
				}
			}
			if (document.form1.residentialAddress4.value != '') {
				if (!stringlength(document.form1.residentialAddress4, 3, 100)) {
				changeErrorStyle("residentialAddress4");
					return false;
				}

			}

			if (!allLetter(document.form1.ownerSalutation5, 2, 10)) {
			changeErrorStyle("ownerSalutation5");
				return false;
			}

			if (!allLetterSpaceSpecialCharacter(document.form1.ownerName5, 3,
					30)) {
					changeErrorStyle("ownerName5");
				return false;
			}

			if (document.form1.ownerContactNo5.value != '') {
				if (!allnumeric(document.form1.ownerContactNo5, 9, 11)) {
				changeErrorStyle("ownerContactNo5");
					return false;
				}
			}
			if (document.form1.passportNo5.value != '') {
				if (!alphanumeric(document.form1.passportNo5, 9, 15)) {
				changeErrorStyle("passportNo5");
					return false;
				}
			}
			if (document.form1.residentialAddress5.value != '') {
				if (!stringlength(document.form1.residentialAddress5, 3, 100)) {
				changeErrorStyle("residentialAddress5");
					return false;
				}
			}

		}
		if (!validateEmail(document.form1.officeEmail, 10, 20)) {
			//document.getElementById("OfficeEmail").focus();
			changeErrorStyle("OfficeEmail");
			return false;
		}

		if (!allnumeric(document.form1.officeNo, 9, 11)) {
			//document.getElementById("officeNo").focus();
			changeErrorStyle("officeNo");
			return false;
		}

		if (document.form1.faxNo.value != '') {
			if (!allnumeric(document.form1.faxNo, 9, 11)) {
				//document.getElementById("faxNo").focus();
				changeErrorStyle("faxNo");
				return false;
			}
		}

		var e1 = document.getElementById("businessType").value;

		if (e1 == null || e1 == '') {

			alert("Please Select BusinessType");
			//form.submit = false; 
			//document.getElementById("businessType").focus();
			changeErrorStyle("businessType");
			return false;
		}

		var e2 = document.getElementById("companyType").value;

		if (e2 == null || e2 == '') {

			alert("Please Select companyType");
			//form.submit = false; 
			//document.getElementById("companyType").focus();
			changeErrorStyle("companyType");
			return false;
		}

		var e3 = document.getElementById("natureOfBusiness").value;

		if (e3 == null || e3 == '') {

			alert("Please Select natureOfBusiness");
			changeErrorStyle("natureOfBusiness");
			//document.getElementById("natureOfBusiness").focus();
			//form.submit = false; 
			return false;
		}

		if (!allLetter(document.form1.bankName, 3, 15)) {
			//document.getElementById("bankName").focus();
			changeErrorStyle("bankName");
			return false;
		}
		if (!allnumeric(document.form1.bankAccNo, 10, 16)) {
			//document.getElementById("bankAccNo").focus();
			changeErrorStyle("bankAccNo");
			return false;
		}

		var e4 = document.getElementById("documents").value;

		if (e4 == null || e4 == '') {

			alert("Please Select documentts");
			//form.submit = false; 
			//document.getElementById("documents").focus();
			changeErrorStyle("documents");
			return false;
		}

		if (!allLetterSpaceSpecialCharacter(document.form1.tradingName, 3, 100)) {
			//document.getElementById("tradingName").focus();
			changeErrorStyle("tradingName");
			return false;
		}
		if (!allLetterSpace(document.form1.referralId, 3, 30))

		{
			//document.getElementById("referralId").focus();
			changeErrorStyle("referralId");
			return false;

		}

		if (!alphanumeric(document.form1.noOfReaders, 1, 3))

		{
			//document.getElementById("noOfReaders").focus();
			changeErrorStyle("noOfReaders");
			return false;
		}

		var e5 = document.getElementById("signedPackage").value;

		if (e5 == null || e5 == '') {

			alert("Please Select SignedPackage");
			//form.submit = false; 
			//document.getElementById("signedPackage").focus();
			changeErrorStyle("signedPackage");
			return false;
		}
		if (!allLetterSpace(document.form1.wavierMonth, 3, 30)) {
		document.getElementById("wavierMonth").focus();
			return false;
		}

		var a1 = document.getElementById("status").value;

		if (a1 == null || a1 == '') {

			alert("Please Select Status");
			//document.getElementById("status").focus();
			changeErrorStyle("status");
			// form.submit = false; 
			return false;
		}

		if (!allLetterSpace(document.form1.yearIncorporated, 3, 30)) {
			changeErrorStyle("yearIncorporated");
			//document.getElementById("yearIncorporated").focus();
			return false;
		}

		var e = document.getElementById("agentName").value;

		if (e == null || e == '') {

			alert("Please Select agentName");
				//document.getElementById("agentName").focus();
				changeErrorStyle("agentName");
			/* form.submit = false; */
			return false;
		}
		
		
		var a11 = document.getElementById("accType").value;

		if (a11 == null || a11 == '') {

			alert("Please Select Accounttype");
			//document.getElementById("status").focus();
			changeErrorStyle("accountType");
			// form.submit = false; 
			return false;
		}

	}
	function checkBox() {
		//alert("check condition" + checkbox);
		if (document.getElementById("preAuth").checked = Enabled) {

			document.getElementById("preAuth").value = "Yes";

			//alert("check preAuth value:");

		} else if (document.getElementById("preAuth").checked = Disabled) {

			//alert("check preAuth value:");
			document.getElementById("preAuth").value = "No";

		}

	}
	function phonenumber(inputtxt) {
		var phoneno = /^\d{10}$/;
		if (inputtxt.value.match(phoneno)) {
			return true;
		} else {
			alert("Not a valid Phone Number");
			return false;
		}
	}

	function postcode(inputtxt) {
		/* alert(len1);
		int len =len1; */
		var postcode = /^\d{5}$/;
		if (inputtxt.value.match(postcode)) {
			return true;
		} else if (inputtxt.value.length == 0) {
			alert("Please enter " + inputtxt.name);
			return false;
		} else {
			alert("Not a valid " + inputtxt.name);
			return false;
		}
	}

	// This function will validate Name.  
	function allLetter(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[A-Za-z- ]+$/;
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

	// This function will validate Name.  
	function allLetterSpace(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
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
			alert(inputtxt.name
					+ ' must have alphanumeric and space and special characters with -,&,/,() only');
			//uname.focus();  
			return false;
		}
	}
	// This function will validate Address.  
	function alphanumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uadd = document.registration.address;  
		var letters = /^[0-9a-zA-Z-]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Country.  
			//document.registration.country.focus();  
			return true;
		} else {
			alert(inputtxt.name
					+ ' must have alphanumeric with - characters only');
			// uadd.focus();  
			return false;
		}
	}

	// This function will validate ZIP Code.  
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

	// This function will validate Email.  
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,5})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;

		} else if (field.length == 0) {
			alert("You have entered  " + inputtxt.name + " address!");
			//uemail.focus();  
			return false;
		} else {
			alert("You have entered an invalid " + inputtxt.name + " address!");
		}
	}

	function stringlength(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;

		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else {
			//alert('Your userid have accepted.');  
			return true;
		}

	}

	function addrow() {

		//alert(field.value);
		//alert("test data1111"); 
		var x = document.getElementById("ownerCount").value;
		//alert("test data 123456");
		disableRow();

		if (x > 5) {
			alert("Data must maximum 5");
			return false;
		}

		for (a = 2; a <= x; a++) {
			//alert(a);
			document.getElementById("onwer" + a).style.display = '';
			document.getElementById("onwer" + a + a).style.display = '';
		}

	}
	function disableRow() {

		//alert("owner details ");
		document.getElementById("onwer2").style.display = 'none';
		document.getElementById("onwer22").style.display = 'none';
		document.getElementById("onwer3").style.display = 'none';
		document.getElementById("onwer33").style.display = 'none';
		document.getElementById("onwer4").style.display = 'none';
		document.getElementById("onwer44").style.display = 'none';
		document.getElementById("onwer5").style.display = 'none';
		document.getElementById("onwer55").style.display = 'none';
	}

	function copyAddr1() {
		//alert(document.getElementById("addr1").checked);
		var copy = document.getElementById("addr1").checked;
		if (copy) {
			document.getElementById("Business address").value = document
					.getElementById("Registered Address").value;
		} else {
			document.getElementById("Business address").value = '';
		}
	}
	function copyAddr2() {
		//alert(document.getElementById("addr2").checked);
		var copy = document.getElementById("addr2").checked;
		if (copy) {
			document.getElementById("Mailing address").value = document
					.getElementById("Registered Address").value;
		} else {
			document.getElementById("Mailing address").value = '';
		}
	}
</script>  
     </head>
  <body>
  
<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Add Merchant  </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
<form:form method="post" action="admmerchant/addMerchant?${_csrf.parameterName}=${_csrf.token}" commandName="merchant"
					name="form1" id="form1" enctype="multipart/form-data">	
	
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
           <h5> Merchant Details</h5>
          </div>
          
				<div class="row">
					<div class="input-field col s12 m4 l4">
						<form:input type="text" id="registeredName" name="registeredName" path="registeredName" 
						placeholder="Registered Name" onchange="changeStyle(this);"/>
						<label for="first_name">Registered Name</label>
					</div>
					<div class="input-field col s12 m4 l4">
						<form:input type="text" id="businessName" name="businessName" path="businessName" 
						placeholder="Business Name" onchange="changeStyle(this);"/>
						<label for="name">Business Name</label>
					</div>
					<div class="input-field col s12 m4 l4">
						<form:input type="text" id="businessRegNo" name="businessRegNo" path="businessRegNo"
						 placeholder="Business Reg No" onchange="changeStyle(this);"/>
						<label for="name">Business Reg No</label>
					</div>
				</div>
		</div></div></div></div>
				
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        		<div class="d-flex align-items-center">
			           <h5> Merchant Type</h5>
			         </div>
			     <div class="row">
					
					<div class="input-field col s12 m4 l4">
					
									<form:select name="merchantType" path="merchantType" id="merchantType" 
									onChange="check(this);">
										<form:option value="">-Select MerchantType-</form:option>
										<form:option value="P">PAYDEE</form:option>
                                        <form:option value="U">UMOBILE</form:option>
                                      <%--   <form:option value="S">STRIPE</form:option> --%>
                                       
					</form:select>	
					<!-- <label for="name">Merchant Type</label> -->
				</div></div>
				
				<div>
				 <c:if test="${responseDataOfficeEmail != null}">
					<%-- <H4 style="color: #ff4000;" align="center">${responseDataOfficeEmail}</H4> --%>
					<script>
					Swal.fire({
					  type: 'error',
					  title: 'Oops...',
					  text: 'Office Email Already Exist..',
					  //footer: '<a href>Why do I have this issue?</a>'
					}).then((confirm) => {
					if (confirm) {
					    document.getElementById('OfficeEmail').style.border= "1px solid red";
					     document.getElementById('OfficeEmail').focus();
					  } 
					});
					
					
					
					</script> 
				</c:if>
				 <c:if test="${EmptyMid != null}">
					<%-- <H4 style="color: #ff4000;" align="center">${EmptyMid}</H4> --%>
					<script>
					
					Swal.fire({
					  type: 'error',
					  title: 'Oops...',
					  text: 'Empty MID Data',
					  //footer: '<a href>Why do I have this issue?</a>'
					}).then((confirm) => {
					if (confirm) {
					  // document.getElementById('mid').style.border= "1px solid red";
					     document.getElementById('mid').focus();
					  } 
					});
					
					</script>
				</c:if>
			</div>
				<div class="d-flex align-items-center">
			           <h5> Paydee Details <span class="hint" id="paydeeInt">[Should enter atleast one MID]</span></h5>
			         </div>
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
						 
						<form:input type="text" id="mid"
							path="mid" name="mid"
							onchange="changeStyle(this);" />
						<label for="name">EZYWIRE MID</label>

						<div>
							<c:if test="${responseDataMidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataMidExist}</H4>
							</c:if>
						</div>
					</div>
					
					<div class="input-field col s12 m4 l4">
						<form:input type="text"  id="ezymotomid"
							path="ezymotomid" name="ezymotomid"
							onchange="changeStyle(this);" />
						<label for="name">EZYMOTO MID</label>
						<div>
							<c:if test="${responseDataMotomidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataMotomidExist}</H4>
							</c:if>
						</div>
						
					</div>
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text" id="ezypassmid"
						path="ezypassmid" name="ezypassmid"
						onchange="changeStyle(this);" />
					
					<label for="name">EZYPASS MID</label>
					<div>
						<c:if test="${responseDataEzypassmidExist != null}">
							<H4 style="color: #ff4000;" align="center">${responseDataEzypassmidExist}</H4>
						</c:if>
					</div>
					
					</div>
					
				</div>
				
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text"  id="ezywaymid" path="ezywaymid" name="ezywaymid" 
						onchange="changeStyle(this);"/>
						
						<label for="name">EZYWAY MID</label>
						
					<div>
						<c:if test="${responseDataEzyWaymidExist != null}">
							<H4 style="color: #ff4000;" align="center">${responseDataEzyWaymidExist}</H4>
						</c:if>
					</div>
					</div>
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text"  id="ezyrecmid" path="ezyrecmid" name="ezyrecmid" 
								onchange="changeStyle(this);"/>
						<label for="name">EZYREC MID</label>		
						<div>
						<c:if test="${responseDataEzyRecmidExist != null}">
							<H4 style="color: #ff4000;" align="center">${responseDataEzyRecmidExist}</H4>
						</c:if>
						</div>
					
					</div>
					
				</div>
				
				<div class="d-flex align-items-center">
			           <h5> UMobile Details <span class="hint" id="umobileInt">[Should enter atleast one MID]</span></h5>
			         </div>
			    <div class="row">
					
					<div class="input-field col s12 m4 l4">
						 
						<form:input type="text" id="umMid"
							path="umMid" name="umMid"
							onchange="changeStyle(this);" />
						<label for="name">UM_EZYWIRE MID</label>

						<div>
							<c:if test="${responseDataUM_MidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataUM_MidExist}</H4>
							</c:if>
						</div>
					</div>
					
					<div class="input-field col s12 m4 l4">
						<form:input type="text"  id="umMotoMid"
							path="umMotoMid" name="umMotoMid"
							onchange="changeStyle(this);" />
						<label for="name">UM_EZYMOTO MID</label>
						<div>
							<c:if test="${responseDataUM_MotomidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataUM_MotomidExist}</H4>
							</c:if>
						</div>
						
					</div>
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text" id="umEzypassMid" path="umEzypassMid" 
						name="umEzypassMid" onchange="changeStyle(this);"/>
					
					<label for="name">UM_EZYPASS MID</label>
					<div>
						<c:if test="${responseDataUM_EzypassmidExist != null}">
							<H4 style="color: #ff4000;" align="center">${responseDataUM_EzypassmidExist}</H4>
						</c:if>
					</div>
					
					</div>
					
				</div>
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text" class="form-control" id="umEzywayMid" path="umEzywayMid" 
						name="umEzywayMid" onchange="changeStyle(this);"/>
						
						<label for="name">UM_EZYWAY MID</label>
						
					<div>
						<c:if test="${responseDataUM_EzyWaymidExist != null}">
							<H4 style="color: #ff4000;" align="center">${responseDataUM_EzyWaymidExist}</H4>
						</c:if>
					</div>
					</div>
					
					<div class="input-field col s12 m4 l4">
					<form:input type="text" class="form-control" id="umEzyrecMid" path="umEzyrecMid" 
						name="umEzyrecMid" onchange="changeStyle(this);"/>
						<label for="name">UM_EZYREC MID</label>		
						<div>
							<c:if test="${responseDataUM_EzyRecmidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataUM_EzyRecmidExist}</H4>
							</c:if>
						</div>
					
					</div>
					
				</div>
				
				
				<%-- <div class="d-flex align-items-center">
			           <h5> Stripe Details <span class="hint" id="stripeInt">[Should enter atleast one MID]</span></h5>
			         </div>
				<div class="row">
					
					<div class="input-field col s12 m4 l4">
						<form:input type="text"  id="stEzymotomid"
							path="stEzymotomid" name="stEzymotomid"
							onchange="changeStyle(this);" />
						<label for="name">EZYMOTO MID</label>
						<div>
							<c:if test="${responseDataMotomidExist != null}">
								<H4 style="color: #ff4000;" align="center">${responseDataMotomidExist}</H4>
							</c:if>
						</div>
						
					</div>
					</div> --%>
          
        </div>
      </div>
    </div>
    </div>
	
	
	
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
           <h5> Address Details</h5>
          </div>
          
				<div class="row">
					<div class="input-field col s12 ">
						<!-- <input placeholder="Registered Address" id="name" type="text" class="validate"> -->
						<label for="first_name">Registered Address</label>
						<form:textarea rows="3" class="form-control" id="Registered Address" name="Registered Address" 
									path="registeredAddress" onchange="changeStyle(this);"></form:textarea>
					</div>
					<div class="input-field col s12 ">
						<!-- <input id="name" type="text"> -->
						<label for="name">Business Address</label>
						<form:textarea rows="3" class="form-control" id="Business address" name="Business address" 
									path="businessAddress" onchange="changeStyle(this);"></form:textarea>
					</div>
					<div class="input-field col s12 ">
						<!-- <input id="name" type="text"> -->
						<label for="name">Mailing Address</label>
						<form:textarea rows="3" class="form-control" id="Mailing address" name="Mailing address" 
									path="mailingAddress" onchange="changeStyle(this);"></form:textarea>
					</div>
				</div>
				
				<div class="row">
					<div class="input-field col s12 m4 l4">
						<!-- <input id="name" type="text" class="validate"> -->
						<label for="first_name">Business City</label>
						<form:input type="text" class="form-control" id="businessCity" placeholder="Business City"
									 name="businessCity" path="businessCity" onchange="changeStyle(this);"/>
					</div>
					<div class="input-field col s12 m4 l4">
						<!-- <input id="name" type="text"> -->
						<label for="name">Business PostalCode</label>
						<form:input type="text" class="form-control" id="businessPostCode" placeholder="Business PostCode" 
									name="businessPostCode" path="businessPostCode" onchange="changeStyle(this);"/>
					</div>
					<div class="input-field col s12 m4 l4">
							<form:select class="form-control"  name="businessState" path="businessState" id="businessState"
						onchange="changeStyle(this);">
							
							<form:option value="">STATE</form:option>
							<c:forEach items="${stateList}" var ="stateList">
						<form:option value="${stateList}">${stateList}</form:option>
							</c:forEach>
						</form:select>
						<label for="name">Business State</label>
			
					</div>
				</div>
				
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius">					
					  <div class="d-flex align-items-center">
					   <h5> Contact Person Details</h5>
					  </div> 
						 <div class="input-field blue-input col s12 m4 l4">
							<form:select class="form-control"  name="salutation" path="salutation" id="salutation"
									onchange="changeStyle(this);">
										<form:option selected="true" disabled="disabled" value="">- Select Salutation -</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option> 
									</form:select>	
						  <label for="first_name">Title</label>
						  
						</div>
						
						 <div class="input-field col s12 m8 l8">
							<!--  <input id="Business_City" type="text" class="validate"> -->
							 <label for="Business_City">Contact Person Name</label>
							 <form:input type="text" class="form-control" id="name" placeholder="Contact Person Name" name="name" 
									path="name" onchange="changeStyle(this);"/>
						</div> 
					  </div>
				  </div> 
				</div>
				
				 
				 
				 <div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
						 <div class="input-field col s12 m5 l5">
							 <!-- <input id="Email" type="text" class="validate"> -->
							 <label for="Email">Email</label>
							 <form:input type="text" class="form-control" id="email" placeholder="Email" name="email" path="email"
									onchange="changeStyle(this);" />
						</div> 
						
						 <div class="input-field col s12 m4 l4">
							 <!-- <input id="contact_no" type="text" class="validate"> -->
							 <label for="contact_no">Contact No</label>
							 <form:input type="text" class="form-control" id="contactNo" placeholder="Contact No" name="contactNo" 
									path="contactNo" onchange="changeStyle(this);"/>
						</div> 
				  </div> 
				</div>
				</div>
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Owner's/Director's Details</h5>
					  </div> 
					  <div class="row">
						  <div class="input-field col s12 m4 l4">
								<form:input type="text" name ="ownerCount" id ="ownerCount" value="1" 
								onblur="return addrow()" path="ownerCount" onchange="changeStyle(this);"/>
							</div> 
					</div>
					
					<div class="row">
					<div class="input-field col s12 m4 l4">
					
						<form:select name="ownerSalutation1" path="ownerSalutation1" 
						 id="ownerSalutation1" onchange="changeStyle(this);">
						<form:option value="">-Select Salutation-</form:option>
						<form:option value="Miss">Miss</form:option>
						<form:option value="Mr">Mr</form:option>
						<form:option value="Mrs">Mrs</form:option>
						<form:option value="Dr">Dr</form:option>
						<form:option value="Dato"> Dato</form:option>
						<form:option value="Datin"> Datin</form:option> 
						</form:select>
						<label>Owner Salutation1</label>
					</div>
					<div class="input-field col s12 m4 l4">
					<label for="OwnerName1">Owner Name1</label>
					<form:input type="text" id="ownerName1" placeholder="OwnerName1" name="ownerName1"
					 path="ownerName1" onchange="changeStyle(this);"/>
					</div>
					<div class="input-field col s12 m4 l4">
						<label for="ContactNo1">Owner ContactNo1</label>
						<form:input type="text" id="ownerContactNo1" placeholder="OwnerContactNo1" 
						name="ownerContactNo1" path="ownerContactNo1" onchange="changeStyle(this);"/>
					</div></div>
					<div class="row">
					<div class="input-field col s12 m4 l4">
						<label for="NRIC1">NRIC/ Passport1</label>
						<form:input type="text" id="passportNo1" placeholder="PassportNo1"
					name="passportNo1" path="passportNo1" onchange="changeStyle(this);"/>
					</div>
					<div class="input-field col s12 m4 l4">
						<label for="ResidentialAddress1">Residential Address1</label>
						<form:input type="text"  id="residentialAddress1" placeholder="ResidentialAddress1"
						 path="residentialAddress1" name="residentialAddress1" onchange="changeStyle(this);"/>
					</div>
					</div>
					
					<div id="onwer2"  > 
						<div class="row">
						<div class="input-field col s12 m4 l4">
					
						<form:select  name="ownerSalutation2"  path="ownerSalutation2"  
							id="ownerSalutation2" onchange="changeStyle(this);">
								<form:option  value="">-Select Salutation-</form:option>
							<form:option value="Miss">Miss</form:option>
							<form:option value="Mr">Mr</form:option>
							<form:option value="Mrs">Mrs</form:option>
							<form:option value="Dr">Dr</form:option>
							<form:option value="Dato">Dato</form:option>
							<form:option value="Datin">Datin</form:option> 
							</form:select>									
						<label>Owner Salutation2</label>
					</div>
					<div class="input-field col s12 m4 l4">
					<label for="OwnerName1">Owner Name2</label>
					<form:input type="text" id="ownerName2" 
						name="ownerName2" 
						path="ownerName2" onchange="changeStyle(this);"/>
					</div>
					<div class="input-field col s12 m4 l4">
						<label for="ContactNo1">Owner ContactNo2</label>
						<form:input type="text" id="ownerContactNo2" 
						name="ownerContactNo2" path="ownerContactNo2" onchange="changeStyle(this);"/>
					</div></div></div>
					
					<div id="onwer22">
					<div class="row">
					<div class="input-field col s12 m4 l4">
									<label for="NRIC2">NRIC/ Passport2</label>
									<form:input type="text"  id="passportNo2" placeholder="PassportNo2" 
									name="passportNo2" path="passportNo2" onchange="changeStyle(this);"/>
								</div>
						<div class="input-field col s12 m4 l4">
									<label for="ResidentialAddress2">Residential Address2</label>
									<form:input type="text"  id="residentialAddress2"  onchange="changeStyle(this);"
									placeholder="ResidentialAddress2" path="residentialAddress2" name="residentialAddress2"/>
								
							</div>     
					</div></div>
					
						<div  id="onwer3"> 
						<div class="row" >												
                            <div class="input-field col s12 m4 l4">
									
									<form:select  name="ownerSalutation3" path="ownerSalutation3" 
									placeholder="OwnerSalutation3" id="ownerSalutation3" onchange="changeStyle(this);">
										<form:option value="">-Select Salutation-</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option> 
									</form:select>
									<label for="OwnerSalutation3">Owner Salutation3</label>
						</div>
						
						<div class="input-field col s12 m4 l4">
									<label for="OwnerName3">Owner Name3</label>
									<form:input type="text"  id="ownerName3" placeholder="OwnerName3" 
									name="ownerName3" path="ownerName3" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ContactNo3">Owner ContactNo3</label>
									<form:input type="text" id="ownerContactNo3" placeholder="OwnerContactNo3"
										name="ownerContactNo3" path="ownerContactNo3" onchange="changeStyle(this);"/>
								
							</div>
						</div>
						</div>
						
						<div  id="onwer33">  
                        <div class="row" >	 											                                                        
							<div class="input-field col s12 m4 l4">
									<label for="NRIC3">NRIC/ Passport3</label>
									<form:input type="text"  id="passportNo3" placeholder="PassportNo3" 
									name="passportNo3" path="passportNo3" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ResidentialAddress3">Residential Address3</label>
									<form:input type="text" id="residentialAddress3" onchange="changeStyle(this);"
									 placeholder="ResidentialAddress3" path="residentialAddress3" name="residentialAddress3"/>
								</div>
							          
							</div>
							
							</div>
							
						<div  id="onwer4">	
						<div class="row" >												
                           <div class="input-field col s12 m4 l4">
									
									<form:select   name="ownerSalutation4" path="ownerSalutation4" 
									 id="ownerSalutation4" onchange="changeStyle(this);">
										<form:option value="">-Select Salutation-</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option> 
									</form:select>
									<label for="OwnerSalutation4">Owner Salutation4</label>
							
						</div>
						
						
						<div class="input-field col s12 m4 l4">
									<label for="OwnerName4">Owner Name4</label>
									<form:input type="text"  id="ownerName4" placeholder="OwnerName4" 
									name="ownerName4" path="ownerName4" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ContactNo4">Owner ContactNo4</label>
									<form:input type="text"  id="ownerContactNo4" placeholder="OwnerContactNo4" 
									name="ownerContactNo4" path="ownerContactNo4" onchange="changeStyle(this);"/>
								
							</div>
						</div>
						</div>
						
					<div   id="onwer44"> 
                        <div class="row" >	                                                     
							<div class="input-field col s12 m4 l4">
									<label for="NRIC4">NRIC/ Passport4</label>
									<form:input type="text" id="passportNo4" placeholder="PassportNo4" 
									name="passportNo4" path="passportNo4" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ResidentialAddress4">Residential Address4</label>
									<form:input type="text"  id="residentialAddress4" onchange="changeStyle(this);"
									placeholder="ResidentialAddress4" path="residentialAddress4" name="residentialAddress4"/>
								</div>
						</div>
						
                </div>
                
               <div   id="onwer5">	
                
                <div class="row" >													
                           <div class="input-field col s12 m4 l4">
									
									<form:select name="ownerSalutation5" path="ownerSalutation5" 
									placeholder="OwnerSalutation5" id="ownerSalutation5" onchange="changeStyle(this);">
										<form:option value="">-Select Salutation-</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option> 
									</form:select>
									<label for="OwnerSalutation5">Owner Salutation5</label>
							</div>
						<div class="input-field col s12 m4 l4">
									<label for="OwnerName5">Owner Name5</label>
									<form:input type="text"  id="ownerName5" placeholder="OwnerName5" 
									name="ownerName5" path="ownerName5" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ContactNo5">Owner ContactNo5</label>
									<form:input type="text" id="ownerContactNo5" placeholder="OwnerContactNo5" 
									name="ownerContactNo5" path="ownerContactNo5" onchange="changeStyle(this);"/>
								</div>
							</div>
						
						
						
					</div>
					
					
					<div  id="onwer55">	
                        <div class="row" >												                                                        
							<div class="input-field col s12 m4 l4">
									<label for="NRIC5">NRIC/ Passport5</label>
									<form:input type="text"  id="passportNo5" placeholder="PassportNo5" 
									name="passportNo5" path="passportNo5" onchange="changeStyle(this);"/>
								</div>
							<div class="input-field col s12 m4 l4">
									<label for="ResidentialAddress5">Residential Address5</label>
									<form:input type="text"  id="residentialAddress5" onchange="changeStyle(this);"
									placeholder="ResidentialAddress5" path="residentialAddress5" name="residentialAddress5"/>
								</div>
						

                </div>
                
               </div>
            
					  
				</div></div></div>
				
					  
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Office Details </h5>
					  </div> 
						 <div class="input-field col s12 m6 l6">
							 <!-- <input id="Email" type="text" class="validate"> -->
							 <label for="Email">Office Email</label>
							 <form:input type="text" id="OfficeEmail" placeholder="OfficeEmail" 
									name="officeEmail" path="officeEmail" onchange="changeStyle(this);"/>
						</div> 
						
						 <div class="input-field col s12 m6 l6">
							 <!-- <input id="Website" type="text" class="validate"> -->
							 <label for="Website">Website</label>
							 <form:input type="text" class="form-control" id="website" placeholder="WebSite" name="website" 
									path="website" onchange="changeStyle(this);"/>
						</div> 
						
						<div class="input-field col s12 m6 l6">
							 <!-- <input id="Office_No" type="text" class="validate"> -->
							 <label for="Office_No">Office No</label>
							 <form:input type="text" class="form-control" id="officeNo" placeholder="Office No" name="officeNo"
									 path="officeNo" onchange="changeStyle(this);"/>
						</div> 
						
						<div class="input-field col s12 m6 l6">
							 <!-- <input id="Fax No" type="text" class="validate"> -->
							 <label for="Fax No">Fax No</label>
							 <form:input type="text" id="faxNo" placeholder="FaxNo" name="faxNo" 
									path="faxNo" onchange="changeStyle(this);"/>
						</div> 
				  </div> 
				</div>
				</div>
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>  Business Details & Documents</h5>
					  </div> 
						 <div class="input-field blue-input col s12 m4 l4">
							  <form:select id="businessType" name="businessType" path="businessType" onchange="changeStyle(this);">
									<form:option value="">-Select Business Type-</form:option>
									<form:option value="Sole Proprietor">Sole Proprietor</form:option>
									<form:option value="Partnership">Partnership</form:option>
									<form:option value="Private Limited">Private Limited</form:option>
									<form:option value="Limited">Limited</form:option>
									<form:option value="Association">Association</form:option>
									<form:option value="LLP">LLP</form:option>
							</form:select>
						  <label for="Business">Business Type</label>
					
						</div>
						
						 <div class="input-field blue-input col s12 m4 l4">
						 			   <form:select id="companyType"  path="companyType" name="companyType" onchange="changeStyle(this);">
                	<form:option value="">Category</form:option>
                	<c:forEach items="${CategoryList}" var ="CategoryList">
						<form:option value="${CategoryList}">${CategoryList}</form:option>
					 </c:forEach>									
							</form:select>
						  <label for="Company">Company Type</label>
			
						</div>
						
						 <div class="input-field blue-input col s12 m4 l4">
						    <form:select  id="natureOfBusiness"
								 path="natureOfBusiness" name="natureOfBusiness" onchange="changeStyle(this);">
									<form:option value="">-Select Nature of Business-</form:option>
									<c:forEach items="${natureOfBusinessList}" var ="natureOfBusinessList">
									<form:option value="${natureOfBusinessList}">${natureOfBusinessList}</form:option>											
										</c:forEach>
                			</form:select>
						  <label for="Nature">Nature of Business</label>
						
						</div> 
				  </div> 
				</div>
				</div>
				
				
				<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>Bank Details  & Other Details</h5>
					  </div> 
						 <div class="input-field col s12 m4 l4">
							 <!-- <input id="Email" type="text" class="validate"> -->
							 <label for="Bank Name">Bank Name</label>
							 <form:input type="text" id="bankName" placeholder="Bank Name" name="bankName" 
									path="bankName" onchange="changeStyle(this);"/>
						</div> 
						
						 <div class="input-field col s12 m4 l4">
							 <!-- <input id="Account No" type="text" class="validate"> -->
							 <label for="Account No">Account No</label>
							 <form:input type="text" class="form-control" id="bankAccNo" placeholder="Account No" name="bankAccNo" 
									path="bankAccNo" onchange="changeStyle(this);"/>
						</div> 
												
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="Refferral ID" type="text" class="validate"> -->
							 <label for="Refferral ID">Referral ID</label>
							 <form:input type="text"  id="referralId" placeholder="Referral Id" 
									name="referralId" path="referralId" onchange="changeStyle(this);"/>
						</div> 
						
						<div class="input-field col s12 m4 l4">
							
							 <label for="Trading Name">Trading Name</label>
							<form:input type="text" id="tradingName" placeholder="Trading Name" 
									name="tradingName" path="tradingName" onchange="changeStyle(this);"/>
						</div> 
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="No of Readers" type="text" class="validate"> -->
							 <label for="No of Readers">No of Readers</label>
							 <form:input type="text" id="noOfReaders" placeholder="No. Of Readers"
									 path="noOfReaders" name="noOfReaders" onchange="changeStyle(this);"/>
						</div> 
						
						
						<div class="input-field col s12 m4 l4">
										 <form:select  name="signedPackage" path="signedPackage" id="signedPackage" onchange="changeStyle(this);">
										<form:option value="">-Select Signed Package-</form:option> 
										<form:option value="EZYWIRE 1 YEAR">EZYWIRE 1 YEAR</form:option>
									<form:option value="EZYWIRE 2 YEARS">EZYWIRE 2 YEARS</form:option>
									<form:option value="EZYWIRE 3 YEARS">EZYWIRE 3 YEARS</form:option>
									</form:select>	
							 <label for="Refferral ID">Signed package</label>
					
						</div> 
						
						
						<div class="input-field col s12 m4 l4">
							 <!-- <input id="Waiver Month" type="text" class="validate"> -->
							 <label for="Waiver Month">Waiver Month</label>
							 <form:input type="text" id="wavierMonth" placeholder="Waiver Month" 
									name="wavierMonth" path="wavierMonth" onchange="changeStyle(this);"/>
						</div> 
						
						<div class="input-field col s12 m4 l4">
							<!--  <input id="Remarks" type="text" class="validate"> -->
							 <label for="Remarks">Remarks</label>
							 <form:input type="text" id="statusRemarks" placeholder="Remarks" name="statusRemarks"
									 path="statusRemarks" onchange="changeStyle(this);"/>
						</div> 
						
						
						<div class="input-field col s12 m4 l4">
								 <form:select  name="status" path="status" id="status" onchange="changeStyle(this);">
										<form:option value="">-Select Status-</form:option>
										<form:option value="Pending">Pending</form:option>
                                        <form:option value="Submitted">Submitted</form:option>
                                        <form:option value="Rejected">Rejected</form:option>
                                        <form:option value="Active">Active</form:option>
									</form:select>
							 <label for="Status">Status</label>
							
						</div> 
						
						<div class="input-field col s12 m4 l4">
							 <!-- <input id="Year Incorporated" type="text" class="validate"> -->
							 <label for="Year Incorporated">Year Incorporated</label>
							 <form:input type="text" id="yearIncorporated" placeholder="Year Incorporated" 
									name="yearIncorporated" path="yearIncorporated" onchange="changeStyle(this);"/>
						</div> 
						
						
						<div class="input-field col s12 m4 l4">
							 			 <form:select	name="agentName" id="agentName"  class="form-control" path="agentName" onchange="changeStyle(this);"> 
     						<option selected value=""><c:out value="agentName" /></option>
										<%-- <c:forEach items="${merchantNameList}" var="businessName"> --%>

										<c:forEach items="${agentNameList}" var ="agentName">
									
											<form:option   value="${agentName}">${agentName}</form:option>
	
										</c:forEach>	
											
											<%-- </c:forEach> --%>
									</form:select>
							 <label for="Agent Name">Agent Name</label>
				
						</div> 
						
						<div class="input-field col s12 m4 l4">
								 <form:select  name="accType" path="accType" id="accType" onchange="changeStyle(this);">
										<form:option value="">-Select Account Type-</form:option>
										<form:option value="Personal">PERSONAL</form:option>
                                        <form:option value="Business">BUSINESS</form:option>
                                        
									</form:select>
							 <label for="accType">Account Type</label>
							
						</div> 
				  </div> 
				</div>
				</div>
				
				 
				
				<div class="row">
                                <div class="col s12 m4 l4">
                                    <div class="h-form-label">                                      
                                    <p style="color: #000;font-weight: 600;font-size: 16px;"><strong>Pre-Auth </strong>
                                        <label>
                                           <form:radiobutton name="preAuth" path="preAuth"
								id="preAuth" value="Yes" />
                                            <span>Yes</span>
                                        </label>
                                     
                                        <label>
                                            <form:radiobutton checked="checked"
								name="preAuth" path="preAuth" id="preAuth" value="No" />
                                            <span>No</span>
                                        </label>
                                    </p>
                                </div>
                                </div>
								
								
								 <div class="col s12 m4 l4">
                                    <div class="h-form-label">                                      
                                    <p style="color: #000;font-weight: 600;font-size: 16px;"><strong>Auto Settled </strong>
                                        <label>
                                          <form:radiobutton name="autoSettled"
								path="autoSettled" id="autoSettled" value="Yes" /> 
                                            <span>Yes</span>
                                        </label>
                                     
                                        <label>
                                            <form:radiobutton checked="checked"
								name="autoSettled" path="autoSettled" id="autoSettled" value="No" />
                                            <span>No</span>
                                        </label>
                                    </p>
                                </div>
                                </div>
								
								 <div class="col s12 m4 l4">
                                    <div class="h-form-label">                                      
                                    <p style="color: #000;font-weight: 600;font-size: 16px;"><strong>3DS</strong>
                                        <label>
                                            <form:radiobutton name="auth3DS"
								path="auth3DS" id="auth3DS" value="Yes" /> 
                                            <span>Yes</span>
                                        </label>
                                     
                                        <label>
                                            <form:radiobutton checked="checked"
								name="auth3DS" path="auth3DS" id="auth3DS"
								value="No" />
                                            <span>No</span>
                                        </label>
                                    </p>
                                </div>
                                </div> 
                        </div>
						
						
						<div class="row">
				<div class="col s12">
				   <div class="card border-radius"> 
				    <div class="d-flex align-items-center">
					   <h5>  Upload Documents</h5>
					  </div> 
						 <div class="input-field blue-input col s12 m4 l4">
			 			  <form:select  name="documents" path="documents" id="documents" onchange="changeStyle(this);">
							<form:option value="">-Select Documents-</form:option>
							<c:forEach items="${documentsList}" var ="documentsList">
						<form:option value="${documentsList}">${documentsList}</form:option>
							

							</c:forEach>
						</form:select>
						
                               
								
						  <label for="Documents">Documents</label>
							
						</div>
						 <div class="input-field col s12 m4 l4">
						  <form:input style="margin-bottom:8px;" type="file" value="" name="formFile" id="formFile" path="formFile"/><br>
                                <form:input style="margin-bottom:8px;" type="file" value="" name="docFile" id="docFile" path="docFile"/><br>
								<form:input style="margin-bottom:8px;" type="file" value="" name="payFile" id="payFile" path="payFile"/>
								
								<p class="file-alert-para" style="color:#d92366;margin-bottom:20px;"> * only PDF / TEXT / IMAGES allowed</p>
						 </div>
						 

<script>

  /* UPLOAD FILES VALIDATION STARTS */

$(document).ready(function(){
    $("#docFile").change(function(){
    	
    	  const fileUpload = document.getElementById("docFile");
    	  const file = fileUpload.files[0]; 
    	  const maxSize = 5120 * 5120; // 5MB
    	  const allowedTypes = ["image/jpeg", "image/png", "image/gif", "application/pdf", "text/plain"];

    	  if (!file) {
    	    alert("Please select a file to upload.");
    	    return;
    	  }

    	  if (file.size > maxSize) {
    	    alert("File size must be less than 5MB.");
    	    fileUpload.value='';
    	    return;
    	  }

    	  if (!allowedTypes.includes(file.type)) {
    	    alert("Only Text, PDF, JPG, PNG, and GIF files are allowed.");
    	    fileUpload.value='';
    	    return;
    	  }
    });
    
    $("#formFile").change(function(){
    	
  	  const fileUpload = document.getElementById("formFile");
  	  const file = fileUpload.files[0]; 
  	  const maxSize = 5120 * 5120; // 5MB
  	  const allowedTypes = ["image/jpeg", "image/png", "image/gif", "application/pdf", "text/plain"];

  	  if (!file) {
  	    alert("Please select a file to upload.");
  	    return;
  	  }

  	  if (file.size > maxSize) {
  	    alert("File size must be less than 5MB.");
  	    fileUpload.value='';
  	    return;
  	  }

  	  if (!allowedTypes.includes(file.type)) {
  	    alert("Only Text, PDF, JPG, PNG, and GIF files are allowed.");
  	  fileUpload.value='';
  	    return;
  	  }
  });
    
    $("#payFile").change(function(){
    	
    	  const fileUpload = document.getElementById("payFile");
    	  const file = fileUpload.files[0]; 
    	  const maxSize = 5120 * 5120; // 5MB
    	  const allowedTypes = ["image/jpeg", "image/png", "image/gif", "application/pdf", "text/plain"];

    	  if (!file) {
    	    alert("Please select a file to upload.");
    	    return;
    	  }

    	  if (file.size > maxSize) {
    	    alert("File size must be less than 5MB.");
    	    fileUpload.value='';
    	    return;
    	  }

    	  if (!allowedTypes.includes(file.type)) {
    	    alert("Only Text, PDF, JPG, PNG, and GIF files are allowed.");
    	    fileUpload.value='';
    	    return;
    	  }
    });
});

</script>
						
				
						
						 
				  </div> 
				</div>
				
				
				
				</div>
				
				<div class="row">
				<div class="col s12 m4 l4"></div>
				 <div class="col s12 m4 l4">		 
					<button class="submitBtn" style="margin-top:15px !important" type="submit"  onclick="return loadSelectData();">Submit</button>		 
					</div>
				<div class="col s12 m4 l4"></div>
				</div>
	<style>
				
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
			
	 
       </div></div></div></div>
   
    
   

</form:form>
</div>
</body> 
</html>