
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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


<script type="text/javascript">

function openNewWin(merchantName,id){
//alert("username: "+merchantName+"id"+id);
	var h = 600;
	var w = 1000;
	var title = merchantName+" Portal";
	
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    src='${pageContext.request.contextPath}/merchants/merchantsweb?username='+merchantName+'&id='+id;
    var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
    }
		
}
    </script>
<style>
.td {
	text-align: right;
}
</style>

<script lang="JavaScript">
 function submitForms()
 {
  document.getElementById("uploadForm").submit();
  /* alert(document.getElementById("mailFile").value);  */
   alert('Input can not be left blank');
 if($('#mailFile').val() == ''){
     
   }
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
          //alert(url);
          if (url) { // require a URL
              window.location = url; // redirect
             // alert(window.location);
          }
          return false;
      });
    });
</script>
<!-- <script lang="JavaScript">
$().ready(function() {
$("#mailFile").click(function(){
    $("p").hide();
});
});
</script> -->

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Merchant List </strong>
							</h3>
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
							<div class="input-field col s12 m3 l3">Business Name</div>

							<div class="input-field col s12 m5 l5">


								<select name="merchantName" id="merchantName"
									path="merchantName"
									onchange="javascript:location.href = this.value;"
									class="browser-default select-filter">
									<!-- onclick="javascript: locate();"> -->
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out value="businessName" /></option>

										<c:forEach items="${merchant1}" var="merchant1">
											<c:if
												test="${merchant1.mid.mid!=null || merchant1.mid.motoMid!=null || merchant1.mid.ezypassMid!=null
								|| merchant1.mid.ezywayMid!=null || merchant1.mid.ezyrecMid!=null || merchant1.mid.umMotoMid != null 
								 || merchant1.mid.umEzyrecMid != null || merchant1.mid.umEzywayMid != null || merchant1.mid.umEzypassMid != null
								  || merchant1.mid.umMid != null || merchant1.mid.splitMid!=null}">
												<option
													value="${pageContext.request.contextPath}/upTC/merchantDetails?id=${merchant1.id}">
													${merchant1.businessName}
													<c:choose>
														<c:when test="${merchant1.mid.mid!=null}">
         									 ~${merchant1.mid.mid }
                                        </c:when>
														<c:when test="${merchant1.mid.motoMid!=null}">
            								 ~${merchant1.mid.motoMid}
        								</c:when>
														<c:when test="${merchant1.mid.ezywayMid!=null}">
            								 ~${merchant1.mid.ezywayMid}
        								</c:when>
														<c:when test="${merchant1.mid.ezyrecMid!=null}">
            								 ~${merchant1.mid.ezyrecMid}
        								</c:when>
														<c:when test="${merchant1.mid.splitMid!=null}">
                                             ~${merchant1.mid.splitMid}
                                        </c:when>
														<c:when test="${merchant1.mid.umMotoMid!=null}">
            								 ~${merchant1.mid.umMotoMid}
        								</c:when>
														<c:when test="${merchant1.mid.umEzyrecMid!=null}">
            								 ~${merchant1.mid.umEzyrecMid}
        								</c:when>
														<c:when test="${merchant1.mid.umEzywayMid!=null}">
            								 ~${merchant1.mid.umEzywayMid}
        								</c:when>
														<c:when test="${merchant1.mid.umEzypassMid!=null}">
            								 ~${merchant1.mid.umEzypassMid}
        								</c:when>
														<c:when test="${merchant1.mid.umMid!=null}">
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
								</select>
							</div>

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
.select2-dropdown {
	border: 2px solid #2e5baa;
}

.select2-container--default .select2-selection--single {
	border: none;
}

.select-search .select-wrapper input {
	display: none !important;
}
/* .select2-container {
	 background-color: #fff !important;
    padding: 6px !important;
    border: 2px solid #005baa;
    z-index: 999;
	 border-radius:10px !important;
	width: 50% !important;
} */
.select-search .select-wrapper input {
	display: none !important;
}

.select2-results__options li {
	list-style-type: none;
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

ul.select2-results__options li {
	max-height: 250px;
	curser: pointer;
}

li ul .select2-results__option:hover {
	background-color: #005baa !important;
	color: #fff !important;
}

.select-search-hidden .select2-container {
	display: none !important;
}
</style>



							</div>

						</div>
					</div>

				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">


						<p id="msg" name="msg" style="color: #1B0AEA; font-size: 20px;">
							<b>${requestScope.responseData}</b>
						</p>

						<div class="d-flex align-items-center">
							<h5>Search Merchant Details</h5>
						</div>



						<div class="row">


							<div class="input-field col s12 m6 l6 ">
								<label for="Email">USER NAME</label> <input type="text"
									placeholder="username" name="username" id="username"
									value="${merchant.username}" readonly="readonly"
									path="username">

							</div>

							<div class="input-field col s12 m6 l6 ">
								<label for="Name">MERCHANT NAME</label> <input type="text"
									placeholder="merchantname" name="merchantname"
									id="merchantname" value="${merchant.businessName}"
									readonly="readonly" path="merchantname">

							</div>

							<c:if test="${merchant.mid.mid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">EZYWIRE MID</label> <input
										class="form-control" type="text" placeholder="mid" name="mid"
										id="mid" path="mid" value="${merchant.mid.mid}"
										readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.motoMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">EZYMOTO MID</label> <input
										class="form-control" type="text" placeholder="motoMid"
										name="motoMid" id="motoMid" path="motoMid"
										value="${merchant.mid.motoMid}" readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.ezyrecMid!=null}">
								<div class="input-field col s12 m6 l6 ">


									<label for="MID">EZYREC MID</label> <input class="form-control"
										type="text" placeholder="ezyrecMid" name="ezyrecMid"
										id="ezyrecMid" path="ezyrecMid"
										value="${merchant.mid.ezyrecMid}" readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.ezywayMid!=null}">
								<div class="input-field col s12 m6 l6 ">


									<label for="MID">EZYWAY MID</label> <input class="form-control"
										type="text" placeholder="ezywayMid" name="ezywayMid"
										id="ezywayMid" path="ezywayMid"
										value="${merchant.mid.ezywayMid}" readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.ezypassMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">EZYPASS MID</label> <input
										class="form-control" type="text" placeholder="ezypassMid"
										name="ezypassMid" id="ezypassMid" path="ezypassMid"
										value="${merchant.mid.ezypassMid}" readonly="readonly">

								</div>

							</c:if>


							<c:if test="${merchant.mid.splitMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">SPLIT MID</label> <input class="form-control"
										type="text" placeholder="splitMid" name="ezysplit"
										id="ezysplit" path="splitMid" value="${merchant.mid.splitMid}"
										readonly="readonly">

								</div>
							</c:if>

							<%-- <c:if test="${merchant.mid.umMid!=null}">
								<div class="form-group col-md-4">
									<div class="form-group">

										<label for="MID">UMOBILE EZYPASS MID</label> <input
											class="form-control" type="text" placeholder="mid" name="mid"
											id="mid" path="mid" value="${merchant.mid.ezypassMid}"
											readonly="readonly">

									</div>
								</div>
							</c:if> --%>
							<c:if test="${merchant.mid.umMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">UM-EZYWIRE MID</label> <input
										class="form-control" type="text" placeholder="umMid"
										name="umMid" id="umMid" path="umMid"
										value="${merchant.mid.umMid}" readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.umMotoMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">UM-EZYMOTO MID</label> <input
										class="form-control" type="text" placeholder="umMotoMid"
										name="umMotoMid" id="umMotoMid" path="umMotoMid"
										value="${merchant.mid.umMotoMid}" readonly="readonly">

								</div>

							</c:if>
							<c:if test="${merchant.mid.umEzypassMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">UM-EZYPASS MID</label> <input
										class="form-control" type="text" placeholder="umEzypassMid"
										name="umEzypassMid" id="umEzypassMid" path="umEzypassMid"
										value="${merchant.mid.umEzypassMid}" readonly="readonly">


								</div>
							</c:if>
							<%-- 	<c:if test="${merchant.mid.umEzyrecMid!=null}">
								<div class="form-group col-md-4">
									<div class="form-group">

										<label for="MID">UM-EZYREC MID</label> <input
											class="form-control" type="text" placeholder="umEzyrecMid" name="umEzyrecMid"
											id="umEzyrecMid" path="umEzyrecMid" value="${merchant.mid.umEzyrecMid}"
											readonly="readonly">

									</div>
								</div>
							</c:if> --%>

							<c:if test="${merchant.mid.umEzyrecMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">UM-EZYREC MID</label> <input
										class="form-control" type="text" placeholder="umEzyrecMid"
										name="umEzyrecMid" id="umEzyrecMid" path="umEzyrecMid"
										value="${merchant.mid.umEzyrecMid}" readonly="readonly">


								</div>
							</c:if>


							<c:if test="${merchant.mid.umEzywayMid!=null}">
								<div class="input-field col s12 m6 l6 ">

									<label for="MID">UM-EZYWAY MID</label> <input
										class="form-control" type="text" placeholder="umEzywayMid"
										name="umEzywayMid" id="umEzywayMid" path="umEzywayMid"
										value="${merchant.mid.umEzywayMid}" readonly="readonly">


								</div>
							</c:if>


							<%-- </c:forEach> --%>
						</div>


						<%-- <a href="javascript:void(0)" class="btn btn-primary"  id="buttonSub"
onclick="window.open('${pageContext.request.contextPath}/merchants/merchantsweb?username=${merchant.username }',
'newWin','fullscreen=yes,width=1300,height=1000,toolbar=yes,scrollbars=yes,resizable=yes');myWindow.location.reload(true);">
<font color="white">Submit</font></a> --%>



						<a href="javascript:void(0)" class="submitBtn" id="openNewWin"
							onclick="javascript: openNewWin('${merchant.username}','${merchant.id }')">
							<font color="white">Submit</font>
						</a>

					</div>
				</div>
			</div>
		</div>
	</div>


	<%--  </form> --%>

</body>

<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();

});  
    </script>


</html>

