
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
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
  <%--  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
  --%>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
 
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
<%-- <form method="GET" id="form1" name="form1" action="${pageContext.request.contextPath}/transactionweb/motoSubmitTransaction"
		 --%>		
		 
<!--action="<c:url value='/motoSubmitTransaction' />">
		 action="/motoSubmitTransaction"  onsubmit="return phonenumber();">  
 -->
<%--  <form method="post"  id="form1"
			name="form1" action="${pageContext.request.contextPath}/admmerchant/uploadmerchantTC"
			enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>

			
			<%-- <form:form method="post"
		action="merCustMailUpld?${_csrf.parameterName}=${_csrf.token}"
		commandName="merchtCustMail" name="form1" id="form1"
		enctype="multipart/form-data" > --%>
		
			
			
			 <form method="post"  id="form1"
			name="form1" action="up1/uploadmerchantTC?${_csrf.parameterName}=${_csrf.token}"
			enctype="multipart/form-data">
			<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> --%>
			
	<%-- <form:form method="post"  id="form1"
			name="form1" action="${pageContext.request.contextPath}/admmerchant/uploadmerchantTC"
			enctype="multipart/form-data"> --%>
	<br/>
	<br/>
	

 
 
 

		<div class="content-wrapper" style="">
		 <p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.responseData}</b></p>
		
			<div class="row">

				<div class="col-md-12 formContianer">
				<%-- <form:form method="post" action="${pageContext.request.contextPath}/admmerchant/uploadmerchantTC"
				commandName="merchantuser" id="uploadForm" name="uploadForm"> --%>
					<h3 class="card-title">Upload data</h3>

					<div class="card">
					
			<div class="row">
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="Business Name">Business Name</label>
					 <input class="form-control" type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 path="businessName">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
					
						<label for="Business Address">Business Address</label>
					 <input class="form-control" type="text" placeholder="businessAddress1" name="businessAddress1"  
					 id="businessAddress1" value="${merchant.businessAddress1}" readonly="readonly" 
					 path="merchantAddress">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="ContactNumber">Contact Number</label>
					 <input class="form-control" type="text" placeholder="businessContactNumber" name="businessContactNumber" 
					  id="businessContactNumber" value="${merchant.businessContactNumber}" readonly="readonly"
					  path="contactNo">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="Email">Email</label>
					<input class="form-control" type="email" placeholder="email" name="email"  
					id="email" value="${merchant.email}" readonly="readonly" path="email" >
					
				</div>
			</div>
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="MID">Mid</label>
				<input class="form-control" type="text" placeholder="mid" name="mid"  id="mid" 
				path="mid"
				value="${merchant.mid.mid}" readonly="readonly">	
					
				</div>
			</div>
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="mailFile">Review Terms and Conditions</label>
					<a href="/viewPDF?mailFile=document.getElementById('mailFile').value"> Verify Selected Pdf File</a>
						<!-- <input type="file" class="form-control" id="mailFile" autofocus="autofocus"
							placeholder="mailFile" name="mailFile" path="mailFile" accept="*" enctype="multipart/form-data"
							onclick=""/>
						  <span class="tooltiptext" style="color: #ff4000;" >Upload pdf files only </span>
						 <span class="tooltiptext" style="color: blue;" > -->
						 <!-- <a href="/viewPDF?mailFile=document.getElementById('mailFile').value"> Verify Selected Pdf File</a> </span>
						<embed src="http://example.com/the.pdf" width="500" height="375" type='application/pdf'>	 -->
					</div>
			
			</div>
			
			
			<%-- </c:forEach> --%>
</div></div>

<%-- <p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.responseData}</b></p> --%>
<button class="submitBtn"  id="buttonSub" type="submit" >
Submit</button>

</div></div></div>
 
 
 </form>
</body>



	
			</html>
		
		