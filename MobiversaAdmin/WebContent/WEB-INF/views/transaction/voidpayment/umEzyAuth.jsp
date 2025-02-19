<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

	
<script type="text/javascript">

function cancel(res){
	
	var amount = document.getElementById("amount").value;
	var oldamount = document.getElementById("oldamount").value;
	
	if(res =="sale"){
		document.getElementById('status').value ="sale";
		
			if (amount == '') {
				alert("please fill amount");
				document.getElementById("amount").focus();
				return false;

			}else{
					
						if(amount.includes(",")){
						
								amount=amount.replace(/,/g , "");
						
						}
						if(!isNaN(amount)){
								if(amount.includes(".")){
					
										var array1 = [];
					   					array1= amount.split(".");
					    			    //alert("array1[1]: "+array1[1]);
					 					   if(array1[1].length>=2){
					    				      amount=array1[0]+"."+array1[1].substring(0,2);
					    					  //alert("Yours Requested Amount to do Transaction "+amount);
					    					  document.getElementById("famount").value=amount;
					    					  //return true;
					    	
					       					}
					     				   else if(array1[1].length>=1){
					      					 		amount=amount+"0";
			      					          		// alert("Yours Requested Amount to do Transaction "+amount);
			     				          			document.getElementById("famount").value=amount;
			          				       			//return true;
			          					    }
			               					else{
			              						  amount=amount+"00";
			              						   //alert("Yours Requested Amount to do Transaction "+amount);
			              						   document.getElementById("famount").value=amount;
			                						 //return true;
			            				   }
					   
								}
								else{
					    			 amount=amount+".00";
					    			 // alert("Yours Requested Amount to do Transaction "+amount);
					    			  document.getElementById("famount").value=amount;
					    		  //return true;
					       		}
					       }
					 else{
					    	 
					    	  alert("please enter proper amount");
					    	  document.getElementById("amount").focus();
					    	   return false;
					       }
				
			}
		
		
		
	}else{
		
		alert("Pre-Auth amount to be void :"+oldamount );
		document.getElementById('status').value ="void";
		document.getElementById("famount").value = oldamount;
	}
	
	//alert("Final Amount :"+document.getElementById("famount").value)
	
	$("#form1").submit();
	
	//document.getElementById('form1').action = '${pageContext.request.contextPath}/transaction/umAuthByAdmin';
	//document.getElementById('form1').submit();
	
}

</script>

<body class="">
<div class="container-fluid">    
  <div class="row">
	 <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
			<c:choose>
			<c:when test="${responseData != null }">
			<p><h3 style="color: blue;font-weight: bold;">${responseData}</h3></p>
			</c:when>
			<c:otherwise>
			<p><h3 style="color: blue;font-weight: bold;">Transaction Details</h3></p>
			</c:otherwise>
			</c:choose>
			</div>

				 <div class="d-flex align-items-center">
						
							<h3 class="card-title">EZYAUTH Transaction Details</h3>
							</div>
							
							<table class="table table-striped" width="100%">
								<tbody>
								<tr>
										<td><label class="control-label">TID:</label></td>
										<td>${txnDet.f354_TID}</td>
									</tr>
									<tr>
										<td><label class="control-label">MID:</label></td>
										<td>${txnDet.f001_MID}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Transaction Date/Time:</label></td>
										<td>${txnDet.h003_TDT}/${txnDet.h004_TTM}</td>
										
									</tr>

									<tr>
										<td><label class="control-label">Approve Code:</label></td>
										<td>${txnDet.f011_AuthIDResp}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td><input type="hidden" name="oldamount" id="oldamount" value="${txnDet.f007_TxnAmt}"><input type="text" name="amount" id="amount" value="${txnDet.f007_TxnAmt}"></td>
										
										
									</tr>									<tr>
										<td><label class="control-label">Card Holder Name</label></td>
										<td>${txnDet.f268_ChName}</td>
										 
									</tr>
									<tr>
										<td><label class="control-label">Card No:</label></td>
										<td>${txnDet.maskedPan}</td>
										 
									</tr>


								</tbody>
							</table>
							
							
				
						<div class="row">
										<div class="input-field col s12 m3 l3">
											<button onclick="cancel('sale')" class="btn btn-primary" >Convert to sale</button>
										
											<button onclick="cancel('void')" class="btn btn-primary" >Void Payment</button>
										

											<button  class="btn btn-primary" >
											<a href="${pageContext.request.contextPath}/transaction/umEzyauthList/1"
											style="color:white;">Cancel</a>
											
											</button>
										</div>
					

	<form method="post" id="form1" name="form1" action="${pageContext.request.contextPath}/transaction/umAuthByAdmin?${_csrf.parameterName}=${_csrf.token}">
		<input type="hidden" name="f354_TID" id="f354_TID" value="${txnDet.f354_TID}">
		<input type="hidden" name="f001_MID" id="f001_MID" value="${txnDet.f001_MID}">
		<input type="hidden" name="h001_MTI" id="h001_MTI" value="${txnDet.h001_MTI}">
		<input type="hidden" name="h003_TDT" id="h003_TDT" value="${txnDet.h003_TDT}">
		<input type="hidden" name="h004_TTM" id="h004_TTM" value="${txnDet.h004_TTM}">
		<input type="hidden" name="f011_AuthIDResp" id="f011_AuthIDResp" value="${txnDet.f011_AuthIDResp}"> 
		<input type="hidden" name="f007_TxnAmt" id="famount" value=""> 
		<input type="hidden" name="f268_ChName" id="f268_ChName" value="${txnDet.f268_ChName}"> 
		<input type="hidden" name="maskedPan" id="maskedPan" value="${txnDet.maskedPan}"> 
		<input type="hidden" name="f263_MRN" id="f263_MRN" value="${txnDet.f263_MRN}"> 
		<input type="hidden" name="status" id="status" value="">
	</form>

	
					</div>
					</div>
				</div>

			
				
			</div>
		</div>
</body>

</html>