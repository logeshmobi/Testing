<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
 <%@page import="com.mobiversa.common.bo.Merchant"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <style>
.web_dialog
{
   display: none;
   position: fixed;
   width: 410px;
   height: 100px;
   top: 30%;
   left: 50%;
   margin-left: -190px;
   margin-top: -4px;
   background-color: #39c6f0;
   border: 1px solid #336699;
   padding: 15px;
   z-index: 102;
   font-family: Verdana;
   font-size: 10pt;
}
.content1{
margin-top:-18px;
color:white;
}
.ok_button
{
box-shadow:none;
border:1px solid transparent;
display:inline-block;
float:right;
margin-top:34px;
background-color:green;
border-radius: 4px;
moz-border-radius: 4px;
webkit-border-radius: 4px;
padding-right:33px;
padding-top:1px;
width:57px;
 text-align: center;
 line-height: 1em;
}
.cancel_button
{
background-color:#dd4b39;
box-shadow:none;
border:1px solid transparent;
display:inline-block;
float:right;
margin-top:34px;

margin:33px;
border-radius: 4px;
moz-border-radius: 4px;
webkit-border-radius: 4px;
padding-right:33px;
padding-top:1px;
width:57px;
 text-align: center;
 line-height: 1em;

}
}
</style>

<div>
 <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Add A MobileUser
          </h1>  
       </section>
   </div>      
  <script type="text/javascript">
$(document).ready(function() {
	$("#testing").click(function(){
		$("#dialog").show();
    });
	$("#test1").click(function(){
		//$("#form-suspend").action("/mobileUserweb/list")
		$("#form-add").submit();
    });
	$("#test2").click(function(e){
		var url = "/mobileUserweb/addMobileUser"; 
		$(location).attr('href',url);
		//$("#dialog").hide();
    });
});
</script>

<form:form action="${MerchantWebMobileController.URL_BASE}mobileUserDetailsConfirm"  method="post" commandName="mobileUser" id="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 
 
	<div style="font-size:14px;margin-bottom:10px;margin-top:15px;margin-left:-19px;"class="padding-10 col-xs-1">
         
      </div>
<div style="margin-top:15px;margin-left:5px;">
<table style="width:30%" class="table table-striped">
<tr>
<td style="font-size:75%;">MobileUser ID</td>
<td style="font-size:75%;" >${mobileUser.id}</td></tr>
<tr>
<td style="font-size:75%;">First Name</td>
<td style="font-size:75%;">${mobileUser.firstName}</td></tr>

<tr>
<td style="font-size:75%;">Last Name</td>
<td style="font-size:75%;">${mobileUser.lastName}</td></tr>
<tr>
<td style="font-size:75%;">Contact No</td>
<td style="font-size:75%;">${mobileUser.contact}</td></tr>
<tr>
<td style="font-size:75%;">Email</td>
<td style="font-size:75%;">${mobileUser.email}</td></tr>
<tr>
</table>
</div>

   <div class="form-group">  
    <div class="col-xs-1  padding-top-10" style="margin-rigt:10px;">  
      <button type="button" id="testing" class="btn btn-primary">Confirm</button></div>
     <div class="col-xs-1  padding-top-10"> 
      <button type="submit" class="btn btn-primary">Cancel</button>
    </div>  
  </div> 
</form:form>

<div id="dialog" class="web_dialog">
		<div class="content1">Are you sure you want to add this user:?${mobileUser.username }</div>
		<input class="ok_button" type="button" id="test1" value="Ok">
		<input class="cancel_button" type="button" id="test2" value="Cancel">
	</div>
	






 