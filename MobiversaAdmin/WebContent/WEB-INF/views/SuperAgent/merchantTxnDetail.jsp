<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.TerminalDetails"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
</head>

<script lang="JavaScript">

	function loadSelectData() {
		//alert("test");
		/* var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value; */
		
		var e = document.getElementById("period").value;
		var e1 = document.getElementById("productType").value;
		var e2 = ${merchant.id};
		var e3 = document.getElementById("year").value;
		
		//alert("e1"+e1);
		
		if (e == null || e == '' || e1 == null || e1 == ''||e3 == null||e3 == '') {
			alert("Please Select Conditions(s)");
			//form.submit == false;
		} else {
			//alert("test1212"+e + " "+e1);
			document.getElementById("period").value = e;
			document.getElementById("productType").value = e1;
			document.getElementById("year").value = e3;
			
			document.location.href = '${pageContext.request.contextPath}/superagent/searchTxn?period='
					+ e + '&productType=' + e1 +'&mercid='+ e2 + '&year='+e3 ;
			//alert("test1212 "+document.getElementById("dateval1").value);
			//alert("test1212 "+document.getElementById("dateval2").value);
			form.submit;
			document.getElementById("period").value = e;
			document.getElementById("productType").value = e1;
			document.getElementById("year").value = e3;

		}
	}
	</script>

<body>

 <div class="container-fluid">    
  <div class="row">  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">Merchant Details</h3>
          </div>
          
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>${merchant.businessName}</strong> </h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
   
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	 <div class="d-flex align-items-center">
           <h3 class="text-white">Transaction Details</h3>
          </div>
        	<div class="row">
					<div class="input-field col s12 m3 l3">
						<select name="period" id="period" onchange="loadDropDatetype()">
							<option selected value="">Choose</option>
							<option value="Q1">JAN-MAR</option>
							<option value="Q2">APR-JUN</option>
							<option value="Q3">JUL-SEP</option>
							<option value="Q4">OCT-DEC</option>
						</select> 
						<label for="from" style="margin:0px;"> Select Period </label>
					
					</div>
					
					<div class="input-field col s12 m3 l3">
						<select  name="year" id="year">
						<option value="">Choose</option>	
								<c:forEach items="${yearList}" var="year">
								<option  value="${year}">${year}</option>
							</c:forEach>
							</select>	 
						<label for="from" style="margin:0px;"> Select Year </label>
					
					</div>
					<div class="input-field col s12 m3 l3">
							<select  name="productType" id="productType">
							<option value="">Choose</option>								
								<c:forEach items="${productTypeList}" var="productType">
								<option  value="${productType}">${productType}</option>
							</c:forEach>
							</select>						
						<label for="to" style="margin:0px;">Select Product</label>
						
					</div>
					
					<div class="input-field col s12 m3 l3">
						<button type="button" class="btn btn-primary blue-btn" 
						onclick="loadSelectData()">Search</button> 
					</div></div>
					
        </div></div></div></div>
      
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">  
        
             <div class="table-responsive m-b-20 m-t-15">
            
           <table  id="data_list_table" class="display table table-striped table-bordered">
              <thead>
                <tr>
                  <th>Product<br/>Name</th>
                  <th>MID</th> 
                   <th>TID</th>   
                  <th>Activation Date</th>                 
                  <th>Suspension Date</th>
                  <th>Amount</th>
                  <th>Month</th>
                </tr> 

              </thead>
              <tbody>
               <c:forEach items="${paginationBean.itemList}" var="termDet">
              <tr>
				 <td>${termDet.deviceType}</td>				
                 <td>${termDet.merchantId}</td>  
                  <td>${termDet.tid}</td>                   
                 <td>${termDet.connectType}</td> 
				<td>${termDet.activeStatus}</td> 
				<td>${termDet.remarks}</td> 
				<td>${termDet.keyStatus}</td> 
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
										
             </div></div> 
             
             
             
             
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
     

		
