<%@page import="com.mobiversa.payment.controller.PreAuthTxnController"%>
<%@page import="com.mobiversa.common.bo.Agent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
  <script type="text/javascript">

    function PrintElem(elem)
    {
        Popup($(elem).html());
    }

    function Popup(data) 
    {
        var mywindow = window.open('', 'Settlement', 'height=400,width=600');
        mywindow.document.write('<html><head><title>Settlement</title>');
        /*optional stylesheet*/ //mywindow.document.write('<link rel="stylesheet" href="main.css" type="text/css" />');
        mywindow.document.write('</head><body >');
        mywindow.document.write(data);
        mywindow.document.write('</body></html>');

        mywindow.print();
        mywindow.close();

        return true;
    }
    
    

</script>
</head>
 <body>
 <div style="overflow:auto;border:1px;width:100%">
<!-- <div class="pageWrap">  -->  
    <div class="content-wrapper">
        
    
        <div class="row">
			
            <div class="col-md-12 formContianer">
            
            
              <h3 class="card-title">EZYAUTH Transaction List  <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/preauthtxn/ezyauthlist/1">Back</a></h3>
<div class="card">
      <div class="card-body">         
    <table class="table table-hover table-bordered" id="sampleTable1">
			
						<tbody>
						<tr>
						<td>Merchant Name</td>
						<td>${namedd}</td>
						</tr>
						<tr>
						<td>State</td>
						<td>${nameyy}</td>
						</tr>
						</tbody>
						</table>
						
			</div>
			
			 
			 
			
		</div>	
			
		</div>	
			
		
					<div class="col-md-12 formContianer">
		<div class="card" style="width: 90rem;">
              <div class="card-body">
              
                <table class="table table-hover table-bordered" id="sampleTable">
					<thead>
						
						<tr>
							<th>Date</th>
							<th>Time</th>
							<th>TID</th>
							<th>Status</th>
							<th>Location</th>
							<!-- <th align="right" width="25">Amount(RM)</th> -->
							<th>Amount(RM)</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
								<td>${dto.date}</td>
								<td>${dto.time}</td>
								<td>${dto.tid}</td>
								<td>${dto.status}</td>
								<td>${dto.location}</td>
								<%-- <td style="padding: 14px;" align="right" class="separate 10px;">${dto.amount}</td> --%>
								<td style="text-align:right;">${dto.amount}</td>
									<%-- <c:choose>
										<c:when test="${dto.status !='SETTLED'}"> --%>
										<td><a class="fa fa-pencil "aria-hidden="true"
											href="${pageContext.request.contextPath}<%=PreAuthTxnController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
												
												</tr>
												
												
													
					
						</c:forEach>
					</tbody>
					</table>
					</div>
					</div>
					</div>
					
					</div>
					</div>
					</div>
					
					
			
			</body>
			
			
			
			
			
			
			
			
			
			
			
			
