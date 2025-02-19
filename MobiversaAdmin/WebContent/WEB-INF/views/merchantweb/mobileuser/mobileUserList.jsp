<%@page	import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script> -->
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">



</head>
<body>

		<div style="overflow:auto;border:1px;width:100%">
				<div class="content-wrapper">
        
        
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Mobile User Summary</h3>
              <%-- <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/mobileUser/addMobileUser/1">Add New <i class="fa fa-lg fa-plus"></i></a></h3> --%>

				<div class="card" style="width: 90rem;">
              <div class="card-body">
                <table class="table table-hover table-bordered" id="sampleTable">


				<thead>
					<tr>
					
					<th>Activation Date</th>
						<th>Sr.No</th>
						<th>Full Name</th>
						<!-- <th>TID</th> -->
						<th>Status</th>
						
						<th>Action</th>
						<!-- <th>Edit</th> -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<tr>

	<td><span><fmt:formatDate
										value="${dto.mobileUser.activateDate }" pattern="dd-MMM-yyyy" /></span></td>
							<td>${dto.mobileUser.id}</td>
                            <td>${dto.mobileUser.username}</td>
							<td>${dto.mobileUser.status}</td>
						
							<td><a href="${pageContext.request.contextPath}/mobileUserweb/changepwd/${dto.mobileUser.id}"
								style="color: #4bae4f">Change Password</a></td>
							<!-- (#4bae4f ,#fe5621 class="btn btn-primary" -->

						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
	</div>
</div>
</div></div>
</div>

</body>
</html>

