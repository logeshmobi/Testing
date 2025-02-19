<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<style>
.web_dialog
{
   display: none;
   position: fixed;
   width: 380px;
   height: 200px;
   top: 50%;
   left: 50%;
   margin-left: -190px;
   margin-top: -100px;
   background-color: #ffffff;
   border: 2px solid #336699;
   padding: 0px;
   z-index: 102;
   font-family: Verdana;
   font-size: 10pt;
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
<%-- <div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing reader serial no: ${reader.serialNumber }</div>
	</div> --%>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Un-Suspend Reader
          </h1>  
       </section>
   </div>   
<!-- <div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="active"><a href="#" data-toggle="tab">Details</a>
				<div class="nav-arrow"></div></li>
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">All Done</a></li>

		</ul>
	</div> -->

	<form method="post" action="/readerweb/unsuspend/dounSuspend" id="form-suspend">
		<input type="hidden" name="id" value="${reader.id }"> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="col-xs-9">
			<div class="row">
				<div class=" panel panel-default">
					<div class="panel-heading">
						<!-- <h3 class="panel-title">Details</h3> -->
				
						<div class="row static-info">
			<div class="col-md-5 name">Reader Serial No:    ${reader.serialNumber}</div>
		<%-- 	<div class="col-md-7 value">${reader.serialNumber}</div> --%>
		</div>
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
								<label class="col-md-3 control-label">Effective Date: </label>
								<div class="col-md-9">
									<p class="form-control-static">
										<fmt:formatDate type="date" value="${now}" />
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-2 control-label">Reason: </label>
								<div class="col-md-9">
									<select class="form-control" name="reason">
										<c:forEach items="${reasonList}" var="reason">
											<c:choose>

												<c:when test="${readerStatusHistory.reason == reason }">
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
											value="${readerStatusHistory.description}" /></textarea>
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
		Are u sure u want to un-suspend this reader:${reader.serialNumber }
		<input type="button" id="test1" value="Ok">
		<input type="button" id="test2" value="Cancel">
	</div>