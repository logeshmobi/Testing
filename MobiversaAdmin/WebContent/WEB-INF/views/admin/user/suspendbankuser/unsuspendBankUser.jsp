<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script>
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
.content{
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
		$("#form-suspend").submit();
    });
	$("#test2").click(function(e){
		$("#dialog").hide();
    });
});
</script>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Un-Suspend BankUser
          </h1>  
       </section>
   </div>   

	<form method="post" action="/admin/unsuspend/dounSuspend" id="form-suspend">
		<input type="hidden" name="id" value="${bankUser.id }"> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="col-xs-9">
			<div class="row">
				<div class=" panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Un-Suspension Detail</h3>
					</div>
					<div class="panel-body">


						<c:choose>
							<c:when test="${empty errorList }">
								<div class="Metronic-alerts alert alert-danger" style="display: none"></div>
							</c:when>
							<c:otherwise>
								<div class="Metronic-alerts alert alert-danger fade in">
									<button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
									<ul>
										<c:forEach items="${errorList}" var="error">
											<li><c:out escapeXml="true" value="${error}" /></li>
										</c:forEach>
									</ul>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-2 control-label" style="color:black;">Effective Date: </label>
								<div class="col-md-9">
									<p class="form-control-static">
										<fmt:formatDate type="date" value="${now}" />
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label"style="color:black;">Reason: </label>
								<div class="col-md-9">
									<select class="form-control" name="reason">
										<c:forEach items="${reasonList}" var="reason">
											<c:choose>

												<c:when test="${bankUserStatusHistory.reason == reason }">
													<option selected value="<c:out escapeXml="true" value="${reason }"/>"><c:out escapeXml="true" value="${reason}" /></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out escapeXml="true" value="${reason }"/>"><c:out escapeXml="true" value="${reason}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="col-md-9">
									<textarea class="form-control" rows="3" maxlength="250" name="description"><c:out escapeXml='true'
											value="${bankUserStatusHistory.description}" /></textarea>
									<span class="help-block">Max Characters: 250</span>
								</div>
							</div>

						</div>
					</div>
				</div>
<div class="padding-top-10">
					<input type="button" id="testing" class="btn btn-primary pull-right" value="Submit">
				</div>
			</div>
		</div>
	</form>

				<div id="dialog" class="web_dialog">
		<div class="content">Are you sure you want to un-suspend this user:${bankUser.username }
		<input class="ok_button" type="button" id="test1" value="Ok">
		<input class="cancel_button" type="button" id="test2" value="Cancel">
	</div>
	</div>
	