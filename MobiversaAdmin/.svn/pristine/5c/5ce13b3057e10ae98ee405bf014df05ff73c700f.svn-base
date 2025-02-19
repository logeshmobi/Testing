<%@page import="com.mobiversa.payment.controller.TransactionController"%>
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

<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
 <script type="text/javascript">

function openNewWin(txnID){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'transaction/details/'+txnID;
	//    alert(src);
	//src = pdffile_url;
	//alert(src);
	var h = 600;
	var w = 1000;
	var title = "Mobiversa Receipt";
	
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
   
   // divviewer.style.display='block';
    var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
    
    //alert(src);
   // alert(newWindow);
    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
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
           <h3 class="text-white">  <strong> Transaction List  </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
 
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
			<div class="d-flex align-items-center" style="width:25%">
					          <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/transaction/umList/1">Back</a>
											
					          </div>

					          <div class="table-responsive m-b-20 m-t-15">
					            <table class="">

                  <thead>
					
						<tbody>
						<tr>
						<td>Merchant Name</td>
						<td>${merchantName}</td>
						</tr>
						<tr>
						<td>State</td>
						<td>${state}</td>
						</tr>
						</tbody>
						</table>
						
			</div></div></div>
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
							<th>Date</th>
							<th>Time</th>
							<th>TID</th>
							<th>Card No</th>
							<th>Status</th>
							<!-- <th>Location</th> -->
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
								<td>${dto.pan}</td>
								<td>${dto.status}</td>
								<%-- <td>${dto.location}</td> --%>
								<%-- <td style="padding: 14px;" align="right" class="separate 10px;">${dto.amount}</td> --%>
								<td style="text-align:right;">${dto.amount}</td>
								<td>
								<c:if test="${dto.status =='SETTLED' || dto.status =='COMPLETED' || dto.status =='VOID' ||
								dto.status =='CASH SALE' || dto.status =='CASH CANCELLED'}">
								<a href="javascript:void(0)" id="openNewWin"
											onclick="javascript: openNewWin('${dto.trxId}')">
											<i class="material-icons">create</i></a>
								
								</c:if>
								
								
								</td>
									
	    							<div class="form-group col-md-4" id="divviewer" style="display: none;">
					<div class="form-group">
					<div style="clear:both"> 
           <iframe id="popOutiFrame" frameborder="0" scrolling="no" width="800" height="600"></iframe>
           
        </div>
					
			</div></div>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				</div>
				</div>
			</div>
			</div></div>
						<style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
						
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