<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<script type="text/javascript">
	function load1() {
		var url = "${pageContext.request.contextPath}/device/notificationList";
		$(location).attr('href', url);
	}
	
	//minute changes start
	function loadDropstatus() {
		var e = document.getElementById("status1");
		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("status").value = strUser;
		
		
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
</script>
<script type="text/javascript">
	function load() {
		
		var status =document.getElementById("status").value ;
		var remarks =document.getElementById("remark").value ;
		
		if (status == "") {
			alert("Please update Status");
			return false;
		} 
		
		if(status != "REJECTED"){
			swal(
					{
						title : "Are you sure? you want to update this Notification Details",
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
							/* swal("Cancelled", "Your agent details not added", "error"); */
							var url = "${pageContext.request.contextPath}/device/notificationList";
							$(location).attr('href', url);
							//return true;
						}
					});
			
		}else if(remarks != ""){
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
							/* swal("Cancelled", "Your agent details not added", "error"); */
							var url = "${pageContext.request.contextPath}/device/notificationList";
							$(location).attr('href', url);
							//return true;
						}
					});
			
		}else{
			alert("Please add remark for rejection");
		}
		
		// });

	}
</script>

</head>
<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<body>
	<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Notification Details  </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
						
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
					
						<table class="table table-striped">
							<tbody>
								<tr>
									<td style="width: 25%">Notification Title</td>
									<td>${preview.msgTitle}</td>
								</tr>
								<tr>
									<td>Notification</td>
									<td>${preview.msgDetail}</td>
								</tr>
								<tr>
									<td>Trigger Date</td>
									<td>${preview.date}</td>
								</tr>
								<tr>
									<td>Trigger Time</td>
									<td>${preview.hour}</td>
								</tr>
								<tr>
									<td>Notification Status</td>
									<td>${preview.status}</td>
								</tr>
								<tr>
									<td>Products</td>

									<td> Selected
										<ul>
											<c:if test="${preview.ezywire == 'yes'}">
												<li>EZYWIRE</li>
											</c:if>
											<c:if test="${preview.ezyMoto == 'yes'}">
												<li>EZYMOTO</li>
											</c:if>
											<c:if test="${preview.ezyRec == 'yes'}">
												<li>EZYREC</li>
											</c:if>
											<c:if test="${preview.grabPay == 'yes'}">
												<li>GRABPAY</li>
											</c:if>
										</ul>
									</td>
									</tr>
								
							</tbody>
						</table>
		</div></div></div></div>				
				<form method="post" id="form-add" name="form1"
		action="${pageContext.request.contextPath}/device/editPN?${_csrf.parameterName}=${_csrf.token}">
		<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">	
					
				<div class="row">
  					<div class="input-field col s12 m3 l3">
								<input type="hidden" name="status" id="status" value="">
								<input type="hidden" name="id" id="notifyid" value="${preview.id}">
								<select name="status1" id="status1" onchange="loadDropstatus()"
									 style="width: 100%">
									<option value="">- Update Status -</option>
									<option value="APPROVED">APPROVED</option>
									<option value="REJECTED">REJECTED</option>
								</select>
								<label class="control-label">Status</label> 
							</div>
						
						<div class="input-field col s12 m3 l3">

								<label for="Business Address">Remark</label>
								<textarea rows="5" cols="80"  name="remark"
									id="remark" value=""></textarea>						
						</div>
						
					</div>
					<div class="row">
				<div class="input-field col s12 m3 l3">
					  <div class="button-class"  style="float:left;">

				<button type="submit" id="testing" class="btn btn-primary blue-btn"
				value="" onclick=" return load()">Confirm </button> 
				<button type="submit" id="" 
				class="export-btn waves-effect waves-light btn btn-round indigo" value="" onclick="load1()">
				Cancel</button> 
				</div> 
					 </div> 
				</div>
				<style>
				.export_div .select-wrapper { width:65%;float:left;}
				
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
				</div></div></div></div>
      </form>
		</div>
		
	



</body>
</html>


