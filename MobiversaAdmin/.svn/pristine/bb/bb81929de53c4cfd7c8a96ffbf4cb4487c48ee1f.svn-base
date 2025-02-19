<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en"> 
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>    
  <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>  
	
 <script>
 function testing(e)
 {
	 if (e.value === 'paydee') {
		var url = "${pageContext.request.contextPath}/superagent/getSuperAgentRecentTxn";
	 }
	 if (e.value === 'umobile') {
		 var url = "${pageContext.request.contextPath}/superagent/getSuperAgentRecentTxnUmobile";
	 }
 $(location).attr('href',url);
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
           <h3 class="text-white">  <strong> Transaction Report  </strong></h3>
          </div>         
        </div>
      </div>
    </div>
    </div>
    
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="row">
			<div class="input-field col s12 m3 l3">
			<select name="merchantType" id="merchantType" onchange="testing(this)"
					style="width:100%">
					<option selected value="">Choose</option>
					<option value="paydee">Paydee</option>
					<option value="umobile">Umobile</option>
				</select>
						<label for="name">Merchant Type</label>
				</div>
				</div></div></div></div></div> 
  
  <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        
         <div class="table-responsive m-b-20 m-t-15">
		   <table id="data_list_table" class="table table-striped table-bordered">
            <thead>
					<tr>
						<th>Transaction Date and Time</th>
						<!-- <th>Merchant Name</th> -->
						<th>MID</th>
						<th>Type </th>
						<th>Amount</th>
					</tr>
			</thead>
              <tbody>
              <c:forEach items="${fiveTxnList.itemList}" var="dto">
					<tr>
						<td>${dto.txnDate}</td>
						<%-- <td>${dto.merchantName}</td> --%>
						<td>${dto.MID}</td>
						<td><span class="label label-info">${dto.txnType}</span></td>
						<td class="blue-grey-text text-darken-4 font-medium">${dto.amount}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>    
          </div>
	</div></div></div></div>
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