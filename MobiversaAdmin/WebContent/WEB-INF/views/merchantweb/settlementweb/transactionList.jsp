<%@page
	import="com.mobiversa.payment.controller.SettlementWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

</head>


<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>


<script lang="JavaScript">
	function loaddata() {

		//display a confirmation box asking the visitor if they want to get a message
		var theAnswer = confirm("Do you need to do Web Setttlement?");

		//if the user presses the "OK" button display the message "Javascript is cool!!"
		if (theAnswer) {
			document.location.href = '/details/' + $;
			//alert(e);

			alert("Connecting for Web Settlement.");
			form.submit();
		}

		//otherwise display another message
		else {
			//alert("Here is a message anyway.");
		}

	}
</script>
<script type="text/javascript">
	var elems = document.getElementsByClassName('confirmation');
	var confirmIt = function(e) {
		if (!confirm('Due you need to do Web Setttlement?'))
			e.preventDefault();
	};
	for (var i = 0, l = elems.length; i < l; i++) {
		elems[i].addEventListener('click', confirmIt, false);
	}
</script>

<body>

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Settlement Transaction</strong></h3>
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


								<thead>
									<tr>
										<th>TID</th>
										<th>No Of Transaction</th>
										<th>Batch No</th>
										<th>Amount(RM)</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%-- <form> --%>
									<%--  action="<%=SettlementWebController.URL_BASE%>/details/" method="get"> --%>
									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr class="confirmation">
											<td  align="center">${dto.tid}</td>
											<td  align="center">${dto.status}</td>
											<td  align="center">${dto.batchNo}</td>
											<td style="text-align:right;">${dto.amount}</td>
											<td>
				<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=SettlementWebController.URL_BASE%>/details" > 
	 
	       <input type="hidden" name="tid" value="${dto.tid}" />
	       <%--  <input type="text" name="id" value="${merchantPromo.id}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<button type="submit"  value="Click to Settle " class="btn btn-primary blue-btn"
				onclick="return confirm('Due you need to do Web Setttlement?')">Click
													to Settle </button>
				</form>

													
								</td>
											
										</tr>
									</c:forEach>

								</tbody>
							</table>
							<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

$(document).ready(function() {
    $('#data_list_table').DataTable( {
    	"bSort" : false
    } );
} );

</script>
</body>
</html>
























