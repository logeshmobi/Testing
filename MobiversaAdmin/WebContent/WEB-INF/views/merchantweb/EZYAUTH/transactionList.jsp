<%@page
	import="com.mobiversa.payment.controller.MerchantPreAuthController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>


<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">


<script type="text/javascript">

        history.pushState(null, null, "");
window.addEventListener('popstate', function ()
 {
    history.pushState(null, null, "");
 
   
});
</script>
<!-- 	<script type="text/javascript">
jQuery(document).ready(function() {

$('#tid').select2();
});
</script> -->

<script lang="JavaScript">
		function loadSelectData() {
			//alert("test"); 
			/* DateCheck(); */
			
			var e = document.getElementById("from").value;
			var e1 = document.getElementById("to").value;
			var e2 = document.getElementById("tid").value;
			var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
			var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
			
			var fromday = fromDate.getDate();
			var frommon = fromDate.getMonth() + 1;
			var fromyear = fromDate.getFullYear();
			
			var today = toDate.getDate();
			var tomon = toDate.getMonth() + 1;
			var toyear = toDate.getFullYear();

			var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
			var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
			 var eDate = new Date(todateString);
			  var sDate = new Date(fromdateString);
			  if(e== '' && e== ''){
				  alert("Please Select Date to Search Transaction.");
			  }
			  if(e!= '' && e!= '' && sDate> eDate)
			    {
			    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			    return false;
			    }
			var e2 = document.getElementById("tid1").value;
			if(e2== '' && e2== ''){
				  alert("Please Select TID to Search Transaction.");
				  return false;
			  }
			/*  vare3=document.getElementById("devid1").value; */
			//var e4 = document.getElementById("status1").value;

		document.getElementById("date11").value = fromdateString;
			document.getElementById("date12").value = todateString;

			document.getElementById("tid1").value = e2;
			/* document.getElementById("devid1").value=e3;  */
			//document.getElementById("status1").value = e4;

			/* document.location.href = '${pageContext.request.contextPath}/merchantpreauth/search?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2+ '&txnType=MOTO'; */ /*  + '&status=' + e4 */
					document.location.href = '${pageContext.request.contextPath}/merchantpreauth/search?fromDate=' + fromdateString
					+ '&toDate=' + todateString + '&tid=' + e2;
			//form.submit;
			document.getElementById("date11").value = fromdateString;
			document.getElementById("date12").value = todateString;
			form.submit;

		}
		function loaddata() {
			
			var e = document.getElementById("from").value;
			var e1 = document.getElementById("to").value;
			var e2 = document.getElementById("tid").value;
			var e3 = document.getElementById("export1").value;
			
			var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
			var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	

			var fromday = fromDate.getDate();
			var frommon = fromDate.getMonth() + 1;
			var fromyear = fromDate.getFullYear();

			var today = toDate.getDate();
			var tomon = toDate.getMonth() + 1;
			var toyear = toDate.getFullYear();

			var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
			var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

			
			if (fromdateString == null || fromdateString == '' || todateString == null || todateString == '') {
				
				e = document.getElementById("dateval1").value;
				e1 = document.getElementById("dateval2").value;
			
				if (e == null || e1 == null || e == '' || e1 == '') {
					alert("Please select date(s)");
				}
			} else {

				document.getElementById("dateval1").value = fromdateString;
				document.getElementById("dateval2").value = todateString;
				document.getElementById("tid1").value = e2;

				/*  e = document.getElementById("dateval").value; */
				//alert("test2: " + e + " " + e1);
				document.location.href = '${pageContext.request.contextPath}/merchantpreauth/export?fromDate=' + fromdateString
						+ '&toDate=' + todateString+'&tid='+ e2 + '&export=' + e3;
				//alert(e);
				form.submit();
				
			}
		}

		function loadDate(inputtxt, outputtxt) {
			var field = inputtxt.value;
			//var field1 = outputtxt.value;
			//alert(field+" : "+outputtxt.value);
			//document.getElementById("date11").value=field;
			outputtxt.value = field;
			//alert(outputtxt.value);
			// alert(document.getElementById("date11").value);
		}

		function loadDropDate() {
			 //alert("strUser.value"); 
			var e = document.getElementById("tid");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("tid1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("tid1").value);

		}

	
		function loadData(num) {
			var pnum = num;
			//alert("page :" + pnum);
			var e = document.getElementById("datepicker").value;
			var e1 = document.getElementById("datepicker1").value;
			var e2 = document.getElementById("tid").value;
			/* var e3=document.getElementById("devId").value; */
			//alert(document.getElementById("date11").value);
			//alert(document.getElementById("date12").value);
			e = document.getElementById("date11").value;
			e1 = document.getElementById("date12").value;
			e2 = document.getElementById("tid1").value;
			if ((e == null || e == '') && (e1 == null || e1 == '')
					&& (e2 == null || e2 == '') && (e4 == null || e4 == '')) {
				/* alert('both $$ ##'); */
				document.location.href = '${pageContext.request.contextPath}/merchantpreauth/list/' + pnum;
				form.submit;
			} else {
				//alert("else : " + e + " " + e1);
				document.location.href = '${pageContext.request.contextPath}/merchantpreauth/search?fromDate=' + e
						+ '&toDate=' + e1 + '&tid=' + e2 + '&txnType=MOTO'+ '&currPage=' + pnum; /* +'&status=' + e4 */
						 

				//document.forms["myform"].submit();
				form.submit;// = true; 

			}

		}
		
		function DateCheck()
		{
		  var StartDate= document.getElementById('datepicker').value;
		  var EndDate= document.getElementById('datepicker1').value;
		  var eDate = new Date(EndDate);
		  var sDate = new Date(StartDate);
		  if(StartDate!= '' && StartDate!= '' && sDate> eDate)
		    {
		    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
		    return false;
		    }
		}
	</script>

<script type="text/javascript">

function openNewWin(txnID){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'merchantpreauth/details/'+txnID;
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
							<h3 class="text-white">
								<strong>EZYAUTH Transaction Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<form method="get" name="form1" action="#">
			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">

							<div class="row">
								<div class="input-field col s12 m3 l3">

									<label for="from"> From </label> <input type="hidden"
										name="date11" id="date11" <c:out value="${fromDate}"/>>
									<input type="text" id="from" name="date1"
										class="validate datepicker"
										onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
									<i class="material-icons prefix">date_range</i>

								</div>



								<div class="input-field col s12 m3 l3">

									<label for="to">To</label> <input type="hidden" name="date12"
										id="date12" <c:out value="${toDate}"/>> <input id="to"
										type="text" class="datepicker"
										onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
									<i class="material-icons prefix">date_range</i>


								</div>

								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="tid1" id="tid1"
										<c:out value="${tid}"/>> <select name="tid" id="tid"
										onchange="loadDropDate()" style="width: 100%">
										<option selected value=""><c:out value="TID" /></option>
										<c:forEach items="${tidList}" var="tid">

											<option <c:out value="${tid}"/>>${tid}</option>

										</c:forEach>
									</select> <label for="tid">TID </label>

								</div>
								<div class="input-field col s12 m3 l3">
									<select name="export" id="export"
										onchange="return loadDropDate13();">
										<option selected value="">Choose</option>
										<option value="PDF">PDF</option>
										<option value="EXCEL">EXCEL</option>
									</select> <label for="name">Export Type</label> <input type="hidden"
										name="export1" id="export1" <c:out value="${status}"/>>
								</div>
							</div>

							<div class="row">


								<div class="input-field col s12 m3 l3">
									<button class="btn btn-primary blue-btn" type="button"
										onclick="loadSelectData()">Search</button>
									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2"> <input
										type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2">
									<button
										class="export-btn waves-effect waves-light btn btn-round indigo"
										type="button" onclick="return loaddata();">Export</button>

								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<style>
.export_div .select-wrapper {
	width: 65%;
	float: left;
}

.datepicker {
	width: 80% !important;
}

.select-wrapper .caret {
	fill: #005baa;
}

.addUserBtn, .addUserBtn:hover {
	background-color: #fff;
	border: 1px solid #005baa;
	border-radius: 20px;
	color: #005baa;
	font-weight: 600;
}

.blue-btn {
	background-color: #005baa;
	color: #fff;
	border-radius: 20px;
}

.button-class {
	float: right;
}

.float-right {
	float: right;
}
</style>

			<script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});
/* $('.datepicker').pickadate(); */

</script>

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">

							<div class="table-responsive m-b-20 m-t-15">
								<table id="data_list_table"
									class="table table-striped table-bordered">
									<thead>
										<tr>
											<th>Date</th>
											<th>Time</th>
											<th>Status</th>
											<th>Amount(RM)</th>
											<th>Card No</th>
											<th>TID</th>
											<th>Approval Code</th>
											<th>Reference</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${paginationBean.itemList}" var="dto">
											<tr>
												<td>${dto.date}</td>
												<td>${dto.time}</td>
												<%-- <td>${dto.trxId}</td> --%>
												<td>${dto.status}</td>
												<td style="text-align: right;">${dto.amount}</td>
												<td>${dto.numOfSale}</td>
												<%-- <td>${dto.approvalCode}</td> --%>
												<td>${dto.tid}</td>
												<td>${dto.aidResponse}</td>
												<td>${dto.invoiceId}</td>
												

												<td><c:if
														test="${dto.status =='EZYAUTH' || dto.status =='EZYAUTH SALE' ||dto.status =='EZYAUTH CANCEL'  ||
	dto.status =='PREAUTH' || dto.status =='PRE-AUTHORIZATION' || dto.status =='PREAUTH SALE'|| dto.status =='PREAUTH CANCEL'
								}">
														<a href="javascript:void(0)" id="openNewWin"
															onclick="javascript: openNewWin('${dto.trxId}')"> <i
															class="material-icons">create</i></a>

													</c:if>
													<div class="form-group col-md-4" id="divviewer"
														style="display: none;">
														<div class="form-group">
															<div style="clear: both">
																<iframe id="popOutiFrame" frameborder="0" scrolling="no"
																	width="800" height="600"></iframe>

															</div>

														</div>
													</div></td>
												<td style="text-align: center;"><a
													href="${pageContext.request.contextPath}/merchantpreauth/preauthvoid/${dto.f263_MRN}">
														<img class="w24"
														src='data:image/png;base64,<%=actionimg%> ' />
												</a></td>




											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
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

