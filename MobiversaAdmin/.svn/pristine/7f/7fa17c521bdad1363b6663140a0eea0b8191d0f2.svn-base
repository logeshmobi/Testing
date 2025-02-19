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

	
<body >
<form:form method="get" id="form1" name="form1" commandName="txnDet"
		action="${pageContext.request.contextPath}/transactionweb/motoLinklist" >

<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">

<p><h3 style="color: blue;font-weight: bold;">${responseData}</h3></p>
</div>
			
<div class="row">
  
          <div class="d-flex align-items-center">
						
						
							<h3 class="card-title">EZYLINK Payment has been Cancelled</h3>
							</div>
							<table class="table table-striped" width="100%">
								<tbody>
								
									<tr>
										<td><label class="control-label">Transaction Date/Time:</label></td>
										<td>${txnDet.expectedDate}</td>
										
									</tr>

									
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${txnDet.amount}</td>
										
										
									</tr>									<tr>
										<td><label class="control-label">Card Holder Name</label></td>
										<td>${txnDet.contactName}</td>
										 
									</tr>
									<tr>
										<td><label class="control-label">Card No:</label></td>
										<td>${txnDet.pan}</td>
										 
									</tr>


								</tbody>
							</table>
							
							
				</div>
						
								<div class="row">
						<div class="input-field col s12 m6 l6 ">

											<button type="submit" class="btn btn-primary">Done</button>
										</div>
									
					</div>
				</div>

			
				
			</div>
		</div>
	
	
	</div></div>
	
	</form:form>
	
	</body>
	</html>
	
	