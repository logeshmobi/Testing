
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

<script>
	function popOut() {
		pdffile = document.getElementById("mailFile").files[0];
		//alert("pdffile: "+pdffile);
		pdffile_url = URL.createObjectURL(pdffile);
//alert("pdffile_url: "+pdffile_url);
		var src = document.getElementById('popOutiFrame').src;
		src = pdffile_url;
		
		
		
		/* window.open(src,
'newWin','fullscreen=no,width=1000,height=800,toolbar=yes,scrollbars=yes,resizable=yes'); */
		//window.open(src,'_blank');
		
		var h = 600;
		var w = 1000;
		var title = "Terms and Conditions ";
		var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    var newWindow = window.open(src, null, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
	newWindow.document.title = "Terms and Conditions ";
	 //newWindow.document.write("<title>Terms and Conditions</title>");
	//$(newWindow.document).find('html').append('<head><title>Terms and Conditions</title></head>');
    // Puts focus on the newWindow
  
    if (window.focus) {
        newWindow.focus();
    }
		
		
		
	}
</script>

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
   alert("Please Select valid pdf file..");
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
$().ready(function() {
$("#mailFile").click(function(){
    $("p").hide();
});
});
</script>

</head>

<body>
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Upload TC Details </strong></h3>
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
           <h3 class="card-title">Merchant List</h3>
          </div>

        	<div class="row">
							<div class="input-field col s12 m3 l3">
								
									Business Name
									
							</div>	
									
							<div class="input-field col s12 m5 l5">

				<select  class="browser-default select-filter" name="merchantName"
									
								id="merchantName" path="merchantName" onchange="javascript:location.href = this.value;">
								<!-- onclick="javascript: locate();"> -->
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="businessName" /></option>
								
								<c:forEach items="${merchant1}" var="merchant1">
									
								<c:if test="${merchant1.mid.mid!=null || merchant1.mid.motoMid!=null || merchant1.mid.ezypassMid!=null
								|| merchant1.mid.ezywayMid!=null || merchant1.mid.ezyrecMid!=null || merchant1.mid.umMotoMid != null 
								 || merchant1.mid.umEzyrecMid != null || merchant1.mid.umEzywayMid != null || merchant1.mid.umEzypassMid != null
								  || merchant1.mid.umMid != null}">
									<option value="${pageContext.request.contextPath}/upTC/merchantDetailsTC?id=${merchant1.id}">
									${merchant1.businessName}
									  <c:choose>
	         							<c:when test = "${merchant1.mid.mid!=null}">
         									 ~${merchant1.mid.mid }
                                        </c:when>
	         							<c:when test = "${merchant1.mid.motoMid!=null}">
            								 ~${merchant1.mid.motoMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.ezywayMid!=null}">
            								 ~${merchant1.mid.ezywayMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.ezyrecMid!=null}">
            								 ~${merchant1.mid.ezyrecMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.umMotoMid!=null}">
            								 ~${merchant1.mid.umMotoMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.umEzyrecMid!=null}">
            								 ~${merchant1.mid.umEzyrecMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.umEzywayMid!=null}">
            								 ~${merchant1.mid.umEzywayMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.umEzypassMid!=null}">
            								 ~${merchant1.mid.umEzypassMid}
        								</c:when>
        								<c:when test = "${merchant1.mid.umMid!=null}">
            								 ~${merchant1.mid.umMid}
        								</c:when>
         								<c:otherwise>
          									 ~${merchant1.mid.ezypassMid}
        								</c:otherwise>
     								 </c:choose>
									
									
									</option>
</c:if>


								</c:forEach>
								</optgroup>
								</select></div>	
							
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
							
					 </div>
			 </div>
			 
</div>
  </div></div>

	
			 <form method="post"  id="form1"
			name="form1" action="upTC/up1/uploadmerchantTC?${_csrf.parameterName}=${_csrf.token}"
			enctype="multipart/form-data">

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		<input type="hidden" value="${merchant.id}" id="merchantID" name="merchantID"/>
		 <div class="d-flex align-items-center">
				           <h5>Upload data </h5>
				          </div>
			
			<div class="row">
				<div class="input-field col s12 m6 l6">
						<label for="Business Name">Business Name</label>
					 <input type="text" placeholder="businessName" name="businessName"  
					 id="businessName" value="${merchant.businessName}" readonly="readonly"
					 path="businessName">
					
				</div>
				<div class="input-field col s12 m6 l6">
						<label for="Business Address">Business Address</label>
					 <input type="text" placeholder="businessAddress1" name="businessAddress1"  
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
			</div>
							<div class="row">
							
							
							<c:if test="${merchant.mid.mid!=null}">
								
									<div class="input-field col s12 m6 l6">

										<label for="MID">Mid</label> <input 
											type="text" placeholder="mid" name="mid" id="mid" path="mid"
											value="${merchant.mid.mid}" readonly="readonly">
									
								</div>
							</c:if>
							<c:if test="${merchant.mid.motoMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">Moto Mid</label> <input
											type="text" placeholder="motoMid" name="motoMid" id="motoMid" path="motoMid"
											value="${merchant.mid.motoMid}" readonly="readonly">
									</div>
								
							</c:if>
							
							<c:if test="${merchant.mid.ezywayMid!=null}">
								<div class="input-field col s12 m6 l6">
										<label for="MID">EzyWay Mid</label> <input
											class="form-control" type="text" placeholder="ezywayMid" name="ezywayMid"
											id="ezywayMid" path="ezywayMid" value="${merchant.mid.ezywayMid}"
											readonly="readonly">

									</div>
								
							</c:if>
							<c:if test="${merchant.mid.ezypassMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">EzyPass Mid</label> <input
											 type="text" placeholder="ezypassMid" name="ezypassMid"
											id="ezypassMid" path="ezypassMid" value="${merchant.mid.ezypassMid}"
											readonly="readonly">

									</div>
								
							</c:if>
						
							<c:if test="${merchant.mid.umMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">UM_Mid</label> <input
											type="text" placeholder="umMid" name="umMid"
											id="umMid" path="umMid" value="${merchant.mid.umMid}"
											readonly="readonly">

									</div>
								
							</c:if>
							<c:if test="${merchant.mid.umMotoMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">UM_EzyMoto Mid</label> <input
											class="form-control" type="text" placeholder="umMotoMid" name="umMotoMid"
											id="umMotoMid" path="umMotoMid" value="${merchant.mid.umMotoMid}"
											readonly="readonly">

									
								</div>
							</c:if>
							<c:if test="${merchant.mid.umEzypassMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">UM_EzyPass Mid</label> <input
										 type="text" placeholder="umEzypassMid" name="umEzypassMid"
											id="umEzypassMid" path="umEzypassMid" value="${merchant.mid.umEzypassMid}"
											readonly="readonly">

									</div>
								
							</c:if>
							<c:if test="${merchant.mid.umEzyrecMid!=null}">
								<div class="input-field col s12 m6 l6">

										<label for="MID">UM_EzyRec Mid</label> <input
											type="text" placeholder="umEzyrecMid" name="umEzyrecMid"
											id="umEzyrecMid" path="umEzyrecMid" value="${merchant.mid.umEzyrecMid}"
											readonly="readonly">

									</div>
								
							</c:if>
							<c:if test="${merchant.mid.umEzywayMid!=null}">
								<div class="input-field col s12 m6 l6">
										<label for="MID">UM_EzyWay Mid</label> <input
											class="form-control" type="text" placeholder="umEzywayMid" name="umEzywayMid"
											id="umEzywayMid" path="umEzywayMid" value="${merchant.mid.umEzywayMid}"
											readonly="readonly">

									</div>
								
							</c:if>
							
							<div class="input-field col s12 m6 l6 ">				
					
						
						<c:if test="${merchant.mid.mid!=null || merchant.mid.motoMid!=null || merchant.mid.ezypassMid!=null
								|| merchant.mid.ezywayMid!=null || merchant.mid.ezyrecMid!=null || merchant.mid.umMotoMid != null 
								 || merchant.mid.umEzyrecMid != null || merchant.mid.umEzywayMid != null || merchant.mid.umEzypassMid != null
								  || merchant.mid.umMid != null}">
								  <select name="selectedMid"  class="browser-default select-filter" path="selectedMid"
						id="selectedMid"  style="width:100%" value="">
						<option selected value=""><c:out value="Select MID" /></option>
						<c:if test = "${merchant.mid.mid!=null}">
         									<option value="${merchant.mid.mid}">${merchant.mid.mid}</option>
                                        </c:if>
	         							<c:if test = "${merchant.mid.motoMid!=null}">
            								<option value="${merchant.mid.motoMid}">${merchant.mid.motoMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.ezywayMid!=null}">
            								 <option value="${merchant.mid.ezywayMid}">${merchant.mid.ezywayMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.ezyrecMid!=null}">
            								 <option value="${merchant.mid.ezyrecMid}">${merchant.mid.ezyrecMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.umMotoMid!=null}">
            								<option value="${merchant.mid.umMotoMid}">${merchant.mid.umMotoMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.umEzyrecMid!=null}">
            								<option value="${merchant.mid.umEzyrecMid}">${merchant.mid.umEzyrecMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.umEzywayMid!=null}">
            								 <option value="${merchant.mid.umEzywayMid}">${merchant.mid.umEzywayMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.umEzypassMid!=null}">
            								 <option value="${merchant.mid.umEzywayMid}">${merchant.mid.umEzywayMid}</option>
        								</c:if>
        								<c:if test = "${merchant.mid.umMid!=null}">
            								 <option value="${merchant.mid.umMid}">${merchant.mid.umMid}</option>
        								</c:if>
         								<c:if test = "${merchant.mid.ezypassMid!=null}">
          									<option value="${merchant.mid.ezypassMid}">${merchant.mid.ezypassMid}</option>
        								</c:if>
        								</select>	
	</c:if>
					
					
	
					</div>
							
						</div>
			<div class="row">
				<div class="input-field col s12 m6 l6">
						<label for="mailFile">Pdf File</label>
						</div></div>
						<div class="row">
					<div class="input-field col s12 m6 l6">
						<input type="file" id="mailFile" autofocus="autofocus"
							placeholder="mailFile" name="mailFile" path="mailFile" accept="*" required enctype="multipart/form-data"
							onclick=""/>
							</div>
							</div>
							<div class="row">
							<div class="input-field col s12 m6 l6">
						  <span class="tooltiptext" style="color: #ff4000;" >Upload pdf files only </span>
						  </div></div>
						  <div class="row">
							<div class="input-field col s12 m6 l6">
						 <span class="tooltiptext" style="color: blue;font-weight: bold;" >
						 <input type="button" value="Preview" onclick="popOut()" />
						 </span>
						 
						 <!-- <a href="/viewPDF?mailFile=document.getElementById('mailFile').value"> Verify Selected Pdf File</a> </span>
						<embed src="http://example.com/the.pdf" width="500" height="375" type='application/pdf'>	 -->
					
				
					</div></div>
					 <div class="row">
					<div class="col s12 m4 l4"></div>
				 <div class="col s12 m4 l4">	
					<button class="submitBtn"  id="buttonSub" type="submit" >
				Submit</button>
				</div>
				<div class="col s12 m4 l4"></div>
				</div>
			
			 <div class="row">
			<div class="input-field col s12 m6 l6">
					<div style="clear:both"> 
           <iframe id="popOutiFrame" frameborder="0" scrolling="no" width="800" height="600"></iframe>
           
        </div></div></div>
					

<%-- <p id="msg" name="msg" style="color:#1B0AEA;font-size:20px;"><b>${requestScope.responseData}</b></p> --%>

<style>
		.select-wrapper .caret { fill: #005baa;}				
		 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
		.button-class { float:right;}
		.float-right {float:right; }
	</style>
</div>
			</div>

</div></div>
 
 
 </form>
 </div>
 <script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();

});  
    </script>
</body>



	
			</html>
		
		