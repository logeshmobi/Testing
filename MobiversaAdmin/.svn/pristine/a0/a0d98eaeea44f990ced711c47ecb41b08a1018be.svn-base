
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
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
 
 
 <style>
 
 .td{
 text-align:right;
 
 
 }
  </style>
 <script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();

/* $('#motoTid').select2(); */
//$('#renewalPeriod').select2();
});  
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
<%-- <form method="GET" id="form1" name="form1" action="${pageContext.request.contextPath}/transactionweb/motoSubmitTransaction"
		 --%>		
		 
<!--action="<c:url value='/motoSubmitTransaction' />">
		 action="/motoSubmitTransaction"  onsubmit="return phonenumber();">  
 -->
 <form method="post" id="form1"
			name="form1" action="">
	 <div class="content-wrapper">
        
        
        <div class="row" style="width: 100rem;">
			
            <div class="col-md-6 formContianer" >
             <!--  <h3 class="card-title">My Profile</h3> -->
		<h3 class="card-title">Upload Merchant Terms and Conditons</h3>
            <div class="card" style="width: 80rem;">
         
              <div class="card-body" >
              <div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Business Name</label>
									</div>
									</div>
            
               <div class="form-group col-md-4">
								<div class="form-group">
								
									<select class="form-control" name="merchantName"
									
								id="merchantName" path="merchantName" onchange="javascript:location.href = this.value;">
								<!-- onclick="javascript: locate();"> -->
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="businessName" /></option>
								
								<c:forEach items="${merchantNameList}" var="businessName">
									<option value="${pageContext.request.contextPath}/upTC/merchantDetailsTC?mid=${businessName}">
									${businessName}
									</option>


								</c:forEach>
								</optgroup>
								</select>
							</select>
							<%-- </form:form> --%>
							</div>
							</div>
				<br/>
				<br/>
				<br/>
				<br/>
			
				</div>
				
			</div>
		
			
		</div>
		
	</div>
	
	<p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.msg}</b></p>

 
 
 </div>
 

		
 
 
 </form>
</body>



	
			</html>
		
		