<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Merchant Summary</title>

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#export').select2();
$('#type').select2();
});
</script> -->

<!-- <script>
$(document).ready(function() {
    $('#data_list_table').DataTable();
} );

</script> -->


<script
	src="
https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
	
</script>
<script
	src="
https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js">
	
</script>

<script lang="JavaScript">
	function loadSelectData() {
		//alert("test");
		/* var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value; */

		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("type1").value;

		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		var todateString = (today <= 9 ? '0' + today : today) + '/'
				+ (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
		//alert("test"+e + " "+e1);
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			$("#overlay").hide();
			//form.submit == false;
		} else {
			//alert("test1212"+e + " "+e1);
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			document.location.href = '${pageContext.request.contextPath}/admmerchant/search?date='
					+ fromdateString + '&date1=' + todateString + '&type=' + e2;
					
			/* Updated code...... */
			localStorage.setItem("fromDate", e);     
			localStorage.setItem("toDate",e1);		
			//alert("test1212 "+document.getElementById("dateval1").value);
			//alert("test1212 "+document.getElementById("dateval2").value);
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

		}
	}
	
	/* Updated code..... */
	// event listeners
	  window.addEventListener('load', function() {     
		var fromDate = localStorage.getItem("fromDate");     
		var toDate = localStorage.getItem("toDate");    
		
		if(fromDate && toDate){
		
			document.getElementById("from").value = fromDate;
			document.getElementById("to").value = toDate;
	
	 		
	 		document.getElementById("datef").style.transform = "translateY(-14px) scale(0.8)";
			document.getElementById("datet").style.transform = "translateY(-14px) scale(0.8)";
	 	}
	 		
		localStorage.removeItem("fromDate");
		localStorage.removeItem("toDate");
		//localStorage.clear();
	});  

	function loaddata() {
		/* var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value; */

		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("type1").value;
		//alert("e2" + e2);
		//alert("e1" + e1);
		//alert("e" + e);

		/* var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("type1").value;
 */
		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		var todateString = (today <= 9 ? '0' + today : today) + '/'
				+ (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

		if (e == null || e == '' || e1 == null || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/admmerchant/export?date='
					+ fromdateString
					+ '&date1='
					+ todateString
					+ '&export='
					+ e2 + '&type=' + e3;
			//alert(e);
			form.submit();

		}
	}
	function loadData(num) {
		var pnum = num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("type1").value;
		//alert(document.getElementById("date11").value);
		//alert(document.getElementById("date12").value);
		e = document.getElementById("date11").value;
		e1 = document.getElementById("date12").value;

		//alert(e + '  '+ e1);

		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */if ((e == null || e == '') && (e1 == null || e1 == '')) {
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/admmerchant/list/'
					+ pnum;
			form.submit;
		} else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/admmerchant/search?date='
					+ e + '&date1=' + e1 + '&type=' + e2 + '&currPage=' + pnum;

			//document.forms["myform"].submit();
			form.submit;// = true; 

		}

	}

	//export changes start
	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
	function loadDropDatetype() {
		//alert("loadDropDate13");
		var e = document.getElementById("type");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("type1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	function loadDate(inputtxt, outputtxt) {
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		// alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		// alert(outputtxt.value);
		// alert(document.getElementById("date11").value);
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
								<strong> Merchant Summary </strong>
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

						<div class="row">
							<div class="input-field col s12 m3 l3">
								<label id="datef" for="from" style="margin: 0px;"> From 
								</label> <input type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="date1" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>
							</div>
							<div class="input-field col s12 m3 l3">

								<label id="datet" for="to" style="margin: 0px;">To</label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${toDate}"/>> <input id="to" type="text"
									class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>
							</div>

							<div class="input-field col s12 m3 l3">
								<select name="type" id="type" onchange="loadDropDatetype()">
									<option selected value="">Choose</option>
									<option value="MERCHANT">MERCHANT</option>
									<option value="NON_MERCHANT">NON_MERCHANT</option>
								</select> <label for="name">Merchant Type</label> <input type="hidden"
									name="type1" id="type1" <c:out value="${type}"/>>
							</div>

							<div class="input-field col s12 m3 l3 export_div">
								<select name="export" id="export" onchange="loadDropDate13()"
									style="width: 100%">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
								</select> <label for="name">Export Type</label> <input type="hidden"
									name="export1" id="export1" <c:out value="${status}"/>>
							</div>
						</div>
						<div class="row">
							<div class="input-field col s12 m3 l3"
								style="float: right !important;">
								<div class="button-class">
									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2"> <a
										class="export-btn waves-effect waves-light btn btn-round indigo"
										onclick="loaddata()">Export</a> <input type="hidden"
										name="date1" id="dateval1"> <input type="hidden"
										name="date2" id="dateval2">
									<button type="button" class="btn btn-primary blue-btn"
										onclick="loadSelectData()">search</button>
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
					</div>
				</div>
			</div>
		</div>

		<script>
			$('.pickadate-clear-buttons').pickadate({
				close : 'Close Picker',
				formatSubmit : 'dd/mm/yyyy',

			});
			$('.datepicker').pickadate();
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
										<th>ACTIVTION<br />DATE
										</th>
										<th>BUSINESS NAME</th>
										<th>STAUS</th>
										<th>MODIFIED DATE</th>
										<th>CITY</th>
										<!--  <th>Integration platform</th>
                  <th>EZYWIRE MID</th>
                  <th>EZYMOTO MID</th>
                  <th>EZYPASS MID</th>
                  <th>EZYREC MID</th>
                  <th>EZYWAY MID</th> 
                  <th>EZYLINK MID</th> 
                  <th>EZYSPLIT MID</th> -->
										<!-- <th>UM_EZYWIRE MID</th>  -->
										<th>UM_EZYWAY MID</th>
										<th>UM_EZYMOTO MID</th>
										<th>UM EZYLINK MID</th>
										<!-- <th>UM EZYREC MID</th> -->
										<th>BOOST MID</th>
										<th>GRAB MID</th>
										<th>FPX MID</th>
										<th>TNG MID</th>
										<th>SHOPEE PAY MID</th>
										<th>FIUU MID</th>
										<!--    <th>BNPL MID</th> -->
										<th>PAYOUT SETTLEMENT</th>
										<th>FPX HOST</th>
										<th>Edit</th>
									</tr>

								</thead>
								<tbody>
									<c:forEach items="${paginationBean.itemList}" var="merc">
										<tr>
											<td>${merc.createdBy}</td>
											<td>${merc.businessName}</td>

											<td><span class="label label-success">${merc.state}</span></td>
											<td>${merc.modifiedBy}</td>
											<td>${merc.city}</td>
											<%--   <td>${merc.type}</td>
                 <td>${merc.mid}</td>
                 <c:choose>
	         		<c:when test = "${merc.auth3DS == null || merc.auth3DS == '' || merc.auth3DS == 'No' && merc.merchantType == null || merc.merchantType == 'P'}">
                 <td>${merc.motoMid}</td>
                 </c:when>
                 <c:otherwise>
                 <td></td>
                 </c:otherwise>
                 </c:choose>
                 <td>${merc.ezypassMid}</td>
                 <td>${merc.ezyrecMid}</td>
                 <td>${merc.ezywayMid}</td>
                   <c:choose>
	         		<c:when  test = "${merc.auth3DS == 'Yes' && merc.merchantType == null || merc.merchantType == 'P'}">
                 <td>${merc.motoMid}</td>
                 </c:when>
                 <c:otherwise>
                 <td></td>
                 </c:otherwise>
                 </c:choose>
                 <td>${merc.splitMid}</td>  
                 <td>${merc.umMid}</td>               --%>
											<td>${merc.umEzywayMid}</td>
											<c:choose>
												<c:when
													test="${merc.auth3DS == null || merc.auth3DS == '' || merc.auth3DS == 'No' && ( merc.merchantType == 'U' || merc.merchantType == 'FIUU')}">
													<td>${merc.umMotoMid}</td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose>


											<c:choose>
												<c:when
													test="${merc.auth3DS == 'Yes' && ( merc.merchantType == 'U' || merc.merchantType == 'FIUU')}">
													<td>${merc.umMotoMid}</td>
												</c:when>
												<c:otherwise>
													<td></td>
												</c:otherwise>
											</c:choose>


											<%-- <td>${merc.umEzyrecMid}</td> --%>
											<td>${merc.boostMid}</td>
											<td>${merc.grabMid}</td>
											<td>${merc.fpxMid}</td>
											<td>${merc.tngMid}</td>
											<td>${merc.shoppyMid}</td>
											<td>${merc.fiuuMid}</td>
											<%--   <td>${merc.bnplMid}</td> --%>
											<td>${merc.manualSettlement}</td>
											<td>${merc.fpxHostName}</td>											
											<!-- <td><i class="material-icons">edit</i></td> -->
											<td align="center"><a
												href="${pageContext.request.contextPath}/admmerchant/detail/${merc.id}?manualSettlement=${merc.manualSettlement}">
													<i class="material-icons">create</i>
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

	</div>
	<script>
		$(document).ready(function() {
			// $('#data_list_table').DataTable();
		});

		$(document).ready(function() {
			$('#data_list_table').DataTable({
				"bSort" : false
			});
		});
		
		<!-- jquery for datpicker -->
	    jQuery(function() {		
	        var date = new Date();
		var currentMonth = date.getMonth();
		var currentDate = date.getDate();
		var currentYear = date.getFullYear();
		$('.datepicker').datepicker({
			minDate: new Date(currentYear, currentMonth-2, currentDate),
			maxDate: new Date(currentYear, currentMonth, currentDate+1)
		});
	});
		
		
	</script>

</body>
</html>