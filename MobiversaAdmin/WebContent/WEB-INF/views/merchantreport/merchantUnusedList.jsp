<%@page import="com.mobiversa.payment.controller.UnuserMerchantController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
 
<!--  <script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#days').select2();


});
</script> -->

	

<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

	<script lang="JavaScript">
/* var date;*/

	
	function loadSelectData() {
		//alert("test");
		var e = document.getElementById("days1").value;
		if (e == null || e == '' ) {
			alert("Please Select month");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = e;
			document.location.href = '${pageContext.request.contextPath}/unusedmerchant/search?days=' + e;
			form.submit;
			document.getElementById("dateval1").value = e;
		}
		//alert(e + " " + e1);
	}

	function loaddata() {
		var e = document.getElementById("days1").value;
			
		if (e == null || e == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e == '') {
				alert("Please select days");
			}
		} else {

			document.getElementById("dateval1").value = e;
			
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/unusedmerchant/export?days=' + e;
			//alert(e);
			form.submit();
			
		}
	}


	function loadDropDate11() {
			//alert("strUser.value");
			var e = document.getElementById("days");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("days1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}



	
	
	function loadData(num){
		var pnum= num;
		//alert("page :"+pnum);
		var e = document.getElementById("days").value;
		
		
		e= document.getElementById("date11").value;
		e1=document.getElementById("date12").value;
		
		//alert(e + '  '+ e1);
		
		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */ if((e == null || e == '') && (e1 == null || e1 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/unusedmerchant/list/'+pnum;
			form.submit;
		}else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/unusedmerchant/search?days=' + e
					+ '&currPage='+pnum;
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
			

		} 
	
	} 
	

	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>
<body class="">
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Unused Merchant Summary</strong></h3>
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
					<div class="input-field col s12 m4 l4 drop-details">

						<select name="days"
							id="days" onchange="loadDropDate11()" >


							<option selected value="">Choose</option>
							<option value="30">1 Month</option>
							<option value="90">3 Month</option>
							<option value="150">5 Month</option>
							<option value="180">6 Month</option>
						</select>
						<label for="name">No Of Days</label> <input type="hidden" name="days1"
							id="days1" <c:out value="${status}"/>> 

					</div>

						
							<div class="input-field col s12 m3 l3">
						<button class="btn btn-primary blue-btn" type="button" onclick="loadSelectData()">Search</button>
						<input type="hidden" name="date1" id ="dateval1">					
						</div>
						</div>
						
						</div>	
						</div>
					
						</div>
						
						<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
				
				.addUserBtn,.addUserBtn:hover {
				background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
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
					<th>MID</th>
					<th>Merchant Name</th>
					<th>Date</th> 
					<!-- <th>Amount</th>	 -->
					<th>No of Days Unused</th>	
					<!-- <th>AgentName</th> -->
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paginationBean.itemList}" var="dto">
					<tr>
						<td>${dto.tid}</td>
						<td>${dto.mid} </td>
						<td>${dto.merchantName} </td>
						
						<fmt:parseDate value="${dto.date}" pattern="yyyy-MM-dd HH:mm:ss"
                                                var="myDate" />
                        <td><fmt:formatDate pattern="dd/MM/yyyy"
                                                    value="${myDate}" /></td>
						
						<%--  <td>${dto.date} </td>  --%>
						<%-- <td>${dto.amount} </td>  --%>
						<td>${dto.noofDays} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
		</div></div>
			</div>
</div>
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
			
			