<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantPictureController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>

<%-- <%@page import="com.mobiversa.common.bo.MerchantPromoType"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>

 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.error {
	color: red;
	font-weight: bold;
}	
<style>  
  .tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black;
}


err: {
    container: 'tooltip'
}


.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 1s;
}

.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
}
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}


</style>
<script >

function upLoadPict()
{
var a1 = document.getElementById("imageFile").value;

if (a1 == null || a1 == '' ) {

		alert("Please upload new Profile Picture");
		// form.submit = false; 
		return false;
		}
		
}
</script>

</head>





<body class="sidebar-mini fixed">

<form:form method="post" action="addMerchantPicture?${_csrf.parameterName}=${_csrf.token}"	 commandName="regAddMerchant"
					name="form1" id="form" enctype="multipart/form-data" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-6 formContianer">
              <h3 class="card-title">My Profile</h3>

            <div class="card">
            	
            	<input type="hidden" class="form-control" id="merchantProfile" placeholder="merchantLogo" name="merchantLogo" value='${regAddMerchant.merchantLogo}' />
                <h3 class="card-title userTitle"><img class="img-circle" alt="User Image" src="data:image/jpg;base64,<c:out value='${regAddMerchant.merchantLogo}'/>" /> Picture Profile</h3>
                 <div class="form-group">
                <label class="control-label">Merchant Name</label>
                <%--  Merchant Name: ${regAddMerchant.businessName}	 --%>			
						<input type="text" class="form-control" id="businessName" name="businessName" value="${regAddMerchant.businessName}" readonly="readonly">
            </div>
            
            
            <div class="form-group">
                <label class="control-label">Mid</label>
                <%--  Merchant Name: ${regAddMerchant.businessName}	 --%>			
						<input type="text" class="form-control" id="mid" name="mid" value="${regAddMerchant.mid}" readonly="readonly">
            </div>
              <div class="card-body">
                <form>
                  <div class="form-group">
                    <label class="control-label">Actual Picture</label>
                    <%-- <div class="profilePic"> <i class="fa fa-trash-o picDelete" aria-hidden="true"></i><img width ="100%" height="100%" class="" src="${pageContext.request.contextPath}/resourcesNew/img/Wisepad2_admin.jpg" alt="User Image"> --%>
                   
                     <input type="hidden" class="form-control" id="merchantLogo" placeholder="merchantLogo" name="merchantLogo" value='${regAddMerchant.merchantLogo}' />
		 <div class="profilePic"><img align="left" width="125" height="125" src="data:image/jpg;base64,<c:out value='${regAddMerchant.merchantLogo}'/>" />
                    </div>
                  </div>


 <!-- <div class="form-group">
                    <label class="control-label">Upload new photo</label>
                    <input class="form-control" type="file">
                  </div>
                   -->
                  
                  <div class="form-group">
                    <label class="control-label">Upload new profile Picture</label>
                  
					<form:input type="file" class="form-control" id="imageFile"
							placeholder="imageFile" name="imageFile" path="imageFile" /> 
                  </div>
                    </form>
                  </div>
                  
              
              </div>
             
              </div>
            </div>
                
           <div class="card-footer">
               <button class="btn btn-primary icon-btn" type="submit" onclick="return upLoadPict()">Submit</button>
             <!--   &nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-default icon-btn" href="#">Cancel</a> -->
            </div>  
        </div>
   
    </form:form>