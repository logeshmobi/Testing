<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
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
<script type="text/javascript">
$(document).ready(function() {
	$("#testing").click(function(){
		$("#dialog").show();
    });
	$("#test1").click(function(){
		//$("#form-suspend").action("/mobileUserweb/list")
		$("#form-edit").submit();
    });
	$("#test2").click(function(e){
		//var url = "/mobileUserweb/editMobileuser"; 
		//$(location).attr('href',url);
		 $("#dialog").hide(); 
    });
});
</script>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
           Edit MobileUser Details
          </h1>  
       </section>
   </div>   
<form action="<%=MerchantWebMobileController.URL_BASE%>/confirmEditMobileUserDetails" method="post" id="form-edit">
<%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
 <%-- <input type="hidden" name="id" value="${mobileUser.id}" /> --%>
<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;"class="padding-10 col-xs-12">
         <b>Terminal Details</b>
      </div>    	
<div>	
<table style="width:30%"class="table table-striped">
<tbody>
<tr >
<td style="font-size:75%;">MobileUser ID</td>
<td style="font-size:75%;">${mobileUser.username}</td></tr>
<tr>
<td style="font-size:75%;">Terminal ID</td>
<td style="font-size:75%;">${tid.tid}</td></tr>
</tbody>
</table>
</div>

	<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;" class="padding-10 col-xs-12"> 
		<b>Mobile User Details</b></div>
<div>
<table style="width:30%" class="table table-striped">
<tr>
<td style="font-size:75%;">MobileUser ID</td>
<td style="font-size:75%;" >${mobileUser.id}</td></tr>
<tr>
<td style="font-size:75%;">Full Name</td>
<td style="font-size:75%;">${mobileUser.firstName}${mobileUser.lastName}</td></tr>
<tr>
<td style="font-size:75%;">Contact No</td>
<td style="font-size:75%;">$${mobileUser.contact}</td></tr>
<tr>
<td style="font-size:75%;">Email</td>
<td style="font-size:75%;">${mobileUser.email}</td></tr>
<tr>
</table>
</div>
<div class="form-group">  
    <div class="col-xs-1  padding-top-10">  
      <input type="button" id="testing" class="btn btn-primary" value="Confirm"></div>
     <div class="col-xs-2  padding-top-10"> 
      <input type="button" id="testing"class="btn btn-primary" value="Cancel">
    </div>  
   </div>
</form>

<div id="dialog" class="web_dialog">
		<div class="content1">Are you sure you want to edit this mobile user:${mobileUser.username}?
		<input class="ok_button" type="button" id="test1" value="Ok">
		<input class="cancel_button" type="button" id="test2" value="Cancel">
	</div>
	</div>

