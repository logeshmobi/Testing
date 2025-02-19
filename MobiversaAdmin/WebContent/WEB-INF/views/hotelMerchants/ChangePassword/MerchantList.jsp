
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
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
  <%--  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
  --%>
 
 
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
$().ready(function() {
 
    $('#mailFile').on( 'change', function() {
   myfile= $( this ).val();
   var ext = myfile.split('.').pop();
   if(ext=="pdf"){
       return true;
   } else{
   alert("Please Select Valid PDF File..");
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
         // alert(url);
          if (url) { // require a URL
              window.location = url; // redirect
             // alert(window.location);
          }
          return false;
      });
    });
</script>
</head>

<body>

 <div class="container-fluid">  

 <form method="post" id="form1"
			name="form1" action="">
	 <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Merchant Credentials</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
            <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									<label for="name">Business Name</label>
									
							</div>	
					<div class="row">
							<div class="input-field col s12 m3 l3">
								
									<select  name="merchantName"
									
								id="merchantName" path="merchantName" onchange="javascript:location.href = this.value;">
								<!-- onclick="javascript: locate();"> -->
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="businessName" /></option>
								
								<c:forEach items="${merchantNameList}" var="businessName">
									<option value="${pageContext.request.contextPath}/admmerchant/MerchantDetailsToChangePassword?mid=${businessName}">
									${businessName}
									</option>


								</c:forEach>
								</optgroup>
								</select>
							
							
							</div>
							</div>
				
			
				</div>
	
	<p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.msg}</b></p>

 
 
 </div>
		
			
		</div>
		
	</div>
	
	

 
  </form>
 </div>
 

		
 
 

</body>



	
			</html>