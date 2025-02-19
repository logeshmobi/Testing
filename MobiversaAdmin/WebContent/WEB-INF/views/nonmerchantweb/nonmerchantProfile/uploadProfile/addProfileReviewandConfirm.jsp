<%@page import="com.mobiversa.payment.controller.MerchantPictureController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>

<%@page import="com.mobiversa.common.bo.Promotion"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!--  <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script> -->
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

<script src="croppie.js"></script>
 <style>
 

img {
    transition: -webkit-transform 0.25s ease;
    transition: transform 0.25s ease;
     cursor: zoom-in;
     cursor:-webkit-zoom-in;
    cursor:-moz-zoom-in;
     cursor: zoom-out;
     cursor:-webkit-zoom-out;
    cursor:-moz-zoom-out;
    overflow:hidden;
   
   
}
img:active {
    -webkit-transform: scale(2);
    transform: scale(2);
}
</style>



<script>
$('.form-control').croppie();

</script>

<script type="text/javascript">
function load1()
 {
	 var url = "${pageContext.request.contextPath}/merchantUpldProfile/list"; 
		$(location).attr('href',url);
 }
</script>
<script type="text/javascript">








function load()
{
	
 $('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to add this Merchant Promotion Details",
      		text: "it will be added..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false,
      	   confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger',
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
       			swal("Added!", "Your Merchant Promotion details added","success");
       			$("#form-add").submit();
      			
      			
      		} else {
      			 swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/merchantUpldProfile/list"; 
      			$(location).attr('href',url);
      			//return true;
      		}
      	});
      });
 
}
</script>

<body>
<form:form action="merchantProfileDetailsReviewAndConfirm"  method="post" commandName="regAddMerchant" id="form-add"  name="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />



<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-6 formContianer">
              <h3 class="card-title">My Profile</h3>

            <div class="card">
            	
                <h3 class="card-title userTitle">
                 <input type="hidden" class="form-control" id="merchantLogo" placeholder="merchantLogo" name="merchantLogo" value='${regAddMerchant.merchantLogo}' />	
                    <img width ="125" height="125" alt="User Image" src="data:image/jpg;base64,<c:out value='${regAddMerchant.merchantLogo}'/>" />Picture Profile</h3>
<%-- <img class="img-circle" src="${pageContext.request.contextPath}/resourcesNew/img/Wisepad2_admin.jpg" alt="User Image">Picture Profile</h3> --%>
            
              <div class="card-body">
                <form>
                  <div class="form-group">
                    <label class="control-label">Actual Picture</label>
                    <div class="profilePic"> <i class="fa fa-trash-o picDelete" aria-hidden="true">
                     <input type="hidden" class="form-control" id="merchantLogo" placeholder="merchantLogo" name="merchantLogo" value='${regAddMerchant.merchantLogo}' />	
                    </i><img width ="125" height="125" alt="User Image" src="data:image/jpg;base64,<c:out value='${regAddMerchant.merchantLogo}'/>" /> 
                    </div>
                  </div>
                  
                  
                  <div class="form-group">
									 <label class="control-label">Upload new profile Picture</label>
										
									   <input type="hidden" class="form-control" id="merchantProfile" placeholder="merchantProfile" name="merchantProfile" value='${regAddMerchant.merchantProfile}' />	
									 <div class="profilePic"><img width ="125" height="125"   alt="User Image" src="data:image/jpg;base64,<c:out value='${regAddMerchant.merchantProfile}'/>" />
                  </div></div>
                  </form>
                  </div>
                  </div>
                 
                  
                  
                  <button type="button"  id="testing" class="btn btn-primary" onclick="return load()" >Confirm</button>  <!-- id="testing" -->
 
 <button type="button"  class="btn btn-primary" onclick="load1()" >Cancel</button> <!--  id="testing1" -->
  </div>
                  </div>
                  </div>
                  </form:form>
                  
            </body>
                  
                  