<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
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


<style>
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

.form-control {
	width: 100%;
}
</style>


</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong> Agent Volume Summary </strong>
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
						  <div class="table-responsive m-b-20 m-t-15">
		   					<table id="data_list_table" class="table table-striped table-bordered">
								<c:set var="count" value="0" />
								<c:forEach items="${paginationBean.itemList}" var="dto">

									<c:if test="${ count == 0}">

										<thead>
											<tr>
												<th>Agent Name</th>

												<c:forEach items="${dto.date}" var="dtold">

													<th style="text-align:right;padding-right:50px;">${dtold}</th>

												</c:forEach>
												<c:set var="count" value="1" />
											</tr>
											</thead>
									</c:if>

								</c:forEach>
			
							
					               

								<tbody>
									<c:forEach items="${paginationBean.itemList}" var="dtolda">

										<tr>

											<c:choose>
												<c:when test="${dtolda.txnPresent == 'Yes'}">
													<%-- <td><a
														href="${pageContext.request.contextPath}/superagent/merchantvolume/${dtolda.agentDet}/1"
														style="color: #4bae4f">${dtolda.agentName}</a></td>
														 --%>
													<td><a
														href="${pageContext.request.contextPath}/superagent/listMerchantGPVByAgent/${dtolda.agId}"
														style="color: #4bae4f">${dtolda.agentName}</a></td>
														
												</c:when>
												<c:when test="${dtolda.txnPresent == 'No'}">
													<td>${dtolda.agentName}</td>
												</c:when>
											</c:choose>

											<c:forEach items="${dtolda.amount}" var="dto1">										
												<td style="text-align:right;">${dto1}</td>	
											
											</c:forEach>
											
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>

<style>
td, th {
	padding: 7px 8px;
	color: #707070;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}

/* #data_list_table_paginate {
	display: none !important;
} */
</style>

	</div>


	<script>
	$(document).ready(function() {
	    $('#data_list_table').DataTable( {
	        columnDefs: [
	            {
	                targets: [ 0, 1, 2 ],
	                className: 'mdl-data-table__cell--non-numeric'
	            }
	        ]
	    } );
	} );

	</script>

</body>
</html>