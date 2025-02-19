
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 
 <style>
 
 .td{
 text-align:right;
 
 
 }
  </style>
  <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/");
</script>
  <script lang="JavaScript">
  $(document).ready(function(){
$( "#uploadForm" ).submit(function( event ) {
  //alert( "Handler for .submit() called." );
  $('form1').submit(false);
  event.preventDefault();
});
});
</script>
<script lang="JavaScript">
  $(document).ready(function(){
$( "#buttonSub" ).click(function() {
$('form1').submit(false);
  $( "#uploadForm" ).submit();
});
});
</script>
 <script lang="JavaScript">
 function submitForms()
 {
  document.getElementById("uploadForm").submit();
 
 }
</script>
<!-- <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "upTC/");
</script> -->
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
              alert(window.location);
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
      <div class="card border-radius">
        <div class="card-content padding-card">
        
			<div class="row">
 			<p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.msg}</b></p>

					
					
					<div class="input-field col s12 m3 l3">
					<c:if test="${merchant.businessName != null }">
					<label for="Business Name">Business Name</label>
					 <input  type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 style="width:240px;"/>
					</c:if>
						<c:if test="${mobileUserName != null }">
					<label for="Business Name">MobileUser Name</label>
					 <input type="text" placeholder="mobileUserName" name="mobileUserName"  
					 id="mobileUserName" value="${mobileUserName}" readonly="readonly"
					 style="width:240px;"/>
					</c:if>
					
				</div>

					
					<div class="input-field col s12 m3 l3">
						<label for="MID">New Password</label> <input 
							type="text" placeholder="newPassword" name="newPassword" id="newPassword" path="newPassword"
							value="${newPassword}" readonly="readonly" style="width: 240px;" />
					</div>
				</div> 


<button class="submitBtn"  id="buttonSub" type="submit" >
<a href="${pageContext.request.contextPath}/admmerchant/MerchantChangePassword" style="color:white;">Done</a>
</button>

		<style>
				.export_div .select-wrapper { width:65%;float:left;}
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>

</div></div></div>
</div></div>

</body>



	
			</html>
		
		