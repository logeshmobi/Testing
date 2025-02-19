
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.mobiversa.payment.controller.MerchantWebUploadTCController"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "upTC/");
</script>
 <style>
 
 .td{
 text-align:right;
 
 
 }
  </style>
  <script lang="JavaScript">
  $(document).ready(function(){
$( "#uploadForm" ).submit(function( event )
 {
  alert( "Handler for .submit() called." );
  $('form1').submit(false);
  event.preventDefault();
});
});
</script>
<!-- <script lang="JavaScript">
  $(document).ready(function(){
$( "#buttonSub" ).click(function() {
$('form1').submit(false);
  $( "#uploadForm" ).submit();
});
});
</script> -->
 <script lang="JavaScript">
 function submitForms()
 {
  document.getElementById("uploadForm").submit();
  /* alert(document.getElementById("mailFile").value);  */
   alert('Input can not be left blank');
 if($('#mailFile').val() == ''){
     
   }
 }
 
 $('#buttonSub').click(function(){
   
});
</script>
 <script type="text/javascript">
$().ready(function() {
 
    $('#mailFile').on( 'change', function() {
   myfile= $( this ).val();
   var ext = myfile.split('.').pop();
   if(ext=="pdf"){
       return true;
   } else{
   alert("Please enter valid pdf file..");
      document.getElementById("mailFile").value="";
      
      return false;
   }
   
  }); 
});


</script>

  
  <script>
    $(function(){
      // bind change event to select
      $('#merchantName').on('change', function () {
          var url = $(this).val(); // get selected value
          alert(url);
          if (url) { // require a URL
              window.location = url; // redirect
             // alert(window.location);
          }
          return false;
      });
    });
</script>
<script lang="JavaScript">
$().ready(function() {
$("#mailFile").click(function(){
    $("p").hide();
});
});
</script>

</head>

<body>
 <form method="post"  id="form1"
			name="form1" action="up1/uploadmerchantTC?${_csrf.parameterName}=${_csrf.token}"
			enctype="multipart/form-data">
	
		
		
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.responseData}</b></p>
		<input type="hidden" value="${merchant.id}" id="merchantID" name="merchantID"/>
		 <div class="d-flex align-items-center">
					<h3 class="card-title">Upload data</h3>
		</div>
					
					
			<div class="row">
				<div class="input-field col s12 m6 l6">
						<label for="Business Name">Business Name</label>
					 <input  type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 path="businessName">
					
				</div>
				<div class="input-field col s12 m6 l6">
					
						<label for="Business Address">Business Address</label>
					 <input  type="text" placeholder="businessAddress1" name="businessAddress1"  
					 id="businessAddress1" value="${merchant.businessAddress1}" readonly="readonly" 
					 path="merchantAddress">
					
				</div>
				<div class="input-field col s12 m6 l6">
						<label for="ContactNumber">Contact Number</label>
					 <input  type="text" placeholder="businessContactNumber" name="businessContactNumber" 
					  id="businessContactNumber" value="${merchant.businessContactNumber}" readonly="readonly"
					  path="contactNo">
					
				</div>
				
				<div class="input-field col s12 m6 l6">
						<label for="Email">Email</label>
					<input type="email" placeholder="email" name="email"  
					id="email" value="${merchant.email}" readonly="readonly" path="email" >
					
				</div>
			<div class="input-field col s12 m6 l6">
						<label for="MID">Mid</label>
				<input  type="text" placeholder="mid" name="mid"  id="mid" 
				path="mid"
				value="${merchant.mid.mid}" readonly="readonly">	
					
				</div>
			<div class="input-field col s12 m6 l6">
						<label for="mailFile">Review Terms and Conditions</label>
					<a href="/viewPDF?mailFile=document.getElementById('mailFile').value"> Verify Selected Pdf File</a>
						
					</div>
			
			</div>
			



<button class="submitBtn"  id="buttonSub" type="submit" >
Submit</button>
<style>
		.select-wrapper .caret { fill: #005baa;}				
		 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
		.button-class { float:right;}
		.float-right {float:right; }
	</style>
	</div>

</div></div></div>
 
 
 </form>
</body>



	
			</html>
		
		