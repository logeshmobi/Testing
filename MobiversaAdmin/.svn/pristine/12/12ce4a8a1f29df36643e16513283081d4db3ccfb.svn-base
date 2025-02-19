<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.io.*,java.util.*"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


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


<script type="text/javascript">

function calPrevious(){
  	var offset=document.getElementById('index').value;
  	if(offset=="1"){
  		swal("You cannot go to previous index.. You are in 1st index");
  	}else{
   		offset=offset-1;
   		document.location.href = '${pageContext.request.contextPath}/superagent/agentVolumeSummary/'+offset;
		form.submit;
   	}
   }
   
  function calNext(){
   	var offset=parseInt(document.getElementById('index').value);
   	var lastPage=parseInt(document.getElementById('lastPage').value);
   	if(lastPage==offset){
   		swal("You can not go to next index.. You are in Last index");
  	}else{
   		var offSetvalue= offset + 1;
   		document.getElementById('index').value = offSetvalue;
   		document.location.href = '${pageContext.request.contextPath}/superagent/agentVolumeSummary/'+offSetvalue;
		form.submit;
   	}
    }
   
   
   function calCurrent(curOffset){
   	var offset=parseInt(document.getElementById('index').value);
  	var curOffset=parseInt(curOffset);
   	if(curOffset==offset){
   		swal("Currently viewing index "+curOffset);
   	}else{
   		document.location.href = '${pageContext.request.contextPath}/superagent/agentVolumeSummary/'+curOffset;
		form.submit;
   	}

   }

</script>

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong> AgentVolume Summary </strong>
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
							<table id="data_list_table"
								class="table table-striped table-bordered">
								<c:set var="count" value="0" />
								<c:forEach items="${paginationBean.itemList}" var="dto">

									<c:if test="${ count == 0}">

										<thead>
											<tr>
												<th>Agent Name</th>

												<c:forEach items="${dto.date}" var="dtold">

													<th>${dtold}</th>

												</c:forEach>
												<c:set var="count" value="1" />
											</tr>
									</c:if>

								</c:forEach>

								<tbody>
									<c:forEach items="${paginationBean.itemList}" var="dtolda">

										<tr>

											<c:choose>
												<c:when test="${dtolda.txnPresent == 'Yes'}">
													<td><a
														href="${pageContext.request.contextPath}/superagent/merchantvolume/${dtolda.agentDet}/1"
														style="color: #4bae4f">${dtolda.agentName}</a></td>
												</c:when>
												<c:when test="${dtolda.txnPresent == 'No'}">
													<td>${dtolda.agentName}</td>
												</c:when>
											</c:choose>

											<c:forEach items="${dtolda.amount}" var="dto1">
												<td>${dto1}</td>
											</c:forEach>
										</tr>

									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="mdl-cell mdl-cell--8-col" style="text-align: right">
							<div class="dataTables_paginate paging_simple_numbers"
								id="data_list_table_paginate1">
								<input type="hidden" name="index" id="index" value="${offset}" />
								<input type="hidden" name="lastPage" id="lastPage"
									value="${lastPage}" />
								<div class="pagination">
									<button class="mdl-button previous"
										id="data_list_table_previous" aria-controls="data_list_table"
										data-dt-idx="0" tabindex="0" onclick="calPrevious();">Previous</button>
									<c:forEach items="${pageNumbers}" var="pageNumbers">
										<button
											class="mdl-button  mdl-button--raised mdl-button--colored"
											aria-controls="data_list_table" data-dt-idx="1" tabindex="0"
											onclick="calCurrent(${pageNumbers});">${pageNumbers}</button>
									</c:forEach>
									<button class="mdl-button next" id="data_list_table_next"
										aria-controls="data_list_table" data-dt-idx="2" tabindex="0"
										onclick="calNext();">Next</button>
								</div>
							</div>
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

#data_list_table_paginate {
	display: none !important;
}
</style>

	</div>


	<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

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