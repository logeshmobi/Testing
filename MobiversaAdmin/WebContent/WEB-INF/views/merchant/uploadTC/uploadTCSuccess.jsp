
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
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "upTC/");
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
              alert(window.location);
          }
          return false;
      });
    });
</script>
</head>

<body>
<form method="GET" id="form1" name="form1" 
action="${pageContext.request.contextPath}/upTC/uploadTermsandCond">

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		<p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.msg}</b></p>
		 <div class="d-flex align-items-center">
				<h3 class="card-title" style="color:blue;">${requestScope.responseData}</h3>
				<h3 class="card-title" style="color:red;">${requestScope.responseData1}</h3>
				</div>
					
					<div class="row">
				<div class="input-field col s12 m6 l6">
						<label for="Business Name">Business Name</label>
					 <input type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 style="width:240px;"/>
					
				</div>
			
				<div class="input-field col s12 m6 l6">
						<label for="User Name">User Name</label>
					 <input  type="text" placeholder="userName" name="userName"  
					 id="userName" value="${merchant.username}" readonly="readonly"
					 style="width:240px;"/>
					
				</div>
			<%-- <div class="input-field col s12 m6 l6">
						<label for="MID">Terms & Conditions Uploaded using ${requestScope.midType}</label>
				<input type="text" placeholder="mid" name="mid"  id="mid"  
				value="${requestScope.mid}" readonly="readonly" style="width:240px;"/>	
					
				</div> --%>

</div>


<button class="submitBtn"  id="buttonSub" type="submit" > Back </button>
<style>
		.select-wrapper .caret { fill: #005baa;}				
		 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
		.button-class { float:right;}
		.float-right {float:right; }
	</style>
</div></div></div>
 
 </div>
  </form> 
</body>



	
			</html>
		
		