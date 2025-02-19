<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/sweetalert.min.js"></script>
 <%-- <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
  --%>
 <style>
.swal-wide{
    width:500px !important;
    
}
</style>
<style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script type="text/javascript">
	function load1() {
		var url = "${pageContext.request.contextPath}/device/notificationReq";
		$(location).attr('href', url);
	}
</script>
<script type="text/javascript">
	function load() {

		swal(
				{
					title : "Are you sure? you want to add this Notification Details",
					text : "it will be added..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, add it!",
					cancelButtonText : "No, cancel!",
					closeOnConfirm : false,
					closeOnCancel : false,
					confirmButtonClass : 'btn btn-success',
					cancelButtonClass : 'btn btn-danger',

				},
				function(isConfirm) {
					if (isConfirm) {

						//swal("Added!", "Your agent details added","success");
						$("#form-add").submit();

					} else {
						swal("Cancelled", "Your notification details not added", "error"); 
						var url = "${pageContext.request.contextPath}/device/notificationReq";
						$(location).attr('href', url);
						//return true;
					}
				});
		// });

	}
</script>

<body>


	<form method="post" id="form-add" name="form1"
		action="${pageContext.request.contextPath}/device/uploadPN?${_csrf.parameterName}=${_csrf.token}">

		<div class="content-wrapper" style="">
			<div class="col-md-12 formContianer">
				<h3 class="card-title">Product Details</h3>

				<div class="card">
					<div class="row">
						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="Business Name">Title<span class="req">
										*</span></label> <input class="form-control" type="text" placeholder="Title"
									name="title" id="title" value="${preview.msgTitle}"
									maxlength="250" readonly="readonly">
							</div>
						</div>

						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="From_Date">Date<span class="req"> *</span></label> <input
									type="text" class="form-control" id="date1" name="date"
									placeholder="dd/mm/yyyy" value="${preview.date}"
									readonly="readonly">

							</div>
						</div>


						<div class="form-group col-md-4 ">
							<div class="col-sm-5 ">
								<div class="form-group i">

									<label class="control-label">Hour<span class="req">
											*</span></label> <input type="text" class="form-control" name="hour"
										id="hour" value="${preview.hour}" readonly="readonly">
								</div>
							</div>
							<div class="col-sm-5">
								<div class="form-group i">

									<label class="control-label">Minute<span class="req">
											*</span></label> <input class="form-control" type="text" name="minute"
										id="minute" value="${preview.minute}" readonly="readonly">
									<input type="hidden" name="products" id="products"
										value="${preview.products}">
								</div>
							</div>

						</div>

					</div>
					<div class="row">
						<div class="form-group col-md-4">
							<div class="form-group">

								<label for="Business Address">Notification<span class="req">
										*</span></label>
								<textarea type="text" rows="15" cols="35" class="form-control"
									name="msg" id="msg" readonly="readonly"><c:out
										value="${preview.msgDetail}" /></textarea>

							</div>
						</div>
					</div>

					<div class="row">

						<c:if test="${preview.ezywire == 'yes'}">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="EZYWIRE">EZYWIRE <input
										class="form-control" type="checkbox" placeholder="Ezywire"
										name="ezywire" id="ezywire" checked disabled="disabled"/>
									</label>
								</div>
							</div>
						</c:if>
						<c:if test="${preview.ezyMoto == 'yes'}">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="EZYMOTO">EZYMOTO <input
										class="form-control" type="checkbox" placeholder="EzyMoto"
										name="ezyMoto" id="ezyMoto" value="" checked disabled="disabled"/></label>
								</div>
							</div>
						</c:if>
						<c:if test="${preview.ezyRec == 'yes'}">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="EZYREC">EZYREC <input class="form-control"
										type="checkbox" placeholder="EzyRec" name="ezyRec" id="ezyRec"
										value="" checked disabled="disabled"/></label>
								</div>
							</div>
						</c:if>
						<c:if test="${preview.grabPay == 'yes'}">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="GRABPAY">GRABPAY <input
										class="form-control" type="checkbox" placeholder="GrabPay"
										name="grabPay" id="grabPay" value="" checked disabled="disabled"/></label>
								</div>
							</div>
						</c:if>

					</div>

				</div>
			</div>

			<input type="button" id="testing" class="btn btn-primary icon-btn"
				value="Confirm" onclick="load()"> <input type="button" id=""
				class="btn btn-default icon-btn" value="Cancel" onclick="load1()">
		</div>

	</form>

</body>
 </html>
						




		
	

	