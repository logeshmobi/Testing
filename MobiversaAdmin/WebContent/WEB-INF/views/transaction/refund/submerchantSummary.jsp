<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap"
	rel="stylesheet">

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<!-- Script tag for Datepicker -->

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

</head>

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
<style>
#merchantName:hover {
	color: 275ca8;
}

#agentName:hover {
	color: 275ca8;
}

.example_e1:focus {
	outline: none !important;
}

.example_e1 {
	display: inline-block;
	margin-bottom: 0;
	font-weight: 600;
	text-align: left;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	background-image: none;
	border: 0;
	color: rgb(39, 92, 168);
	letter-spacing: 1px;
	text-transform: uppercase;
	padding: 10px 15px;
	font-size: 13px;
	line-height: 1.428571429;
	border-radius: 25px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform
		0.2s cubic-bezier(0.35, 0, 0.25, 1), background-color 0.3s ease-in-out;
	font-style: Arial, Helvetica, sans-serif;
	border-radius: 15px;
}

.example_e1:hover {
	color: rgb(39, 92, 168);
	font-weight: 600 !important;
	-webkit-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	-moz-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	transition: all 0.3s ease 0s;
	border: 2px solid #cfcfd1;
	outline: 0 !important;
}

#pagination {
	display: inline-block;
	vertical-align: middle;
	border-radius: 1px;
	padding: 1px 2px 4px 2px;
	border-top: 1px solid transparent;
	border-bottom: 1px solid transparent;
	background-color: transparent;
	float: right;
	margin-right: 15px;
	margin-bottom: 10px;
	/* background-image: -webkit-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:    -moz-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:     -ms-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:      -o-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:         linear-gradient(top, #DBDBDB, #E2E2E2); */
	/*  position:absolute;
      left:62rem;
      bottom:1rem; */
	font-family: 'Poppins', sans-serif;
	/* width:20%;
      height:6%; */
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
	/*  color: #7D7D7D; */
	text-align: center;
	font-size: 16px;
	padding: 2.5px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	-o-user-select: none;
	user-select: none;
}

#pagination a {
	/* margin: 0 2px 0 2px; */
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	/* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
	/* text-shadow: 0 1px 1px #FFF; */
	background-color: white;
	color: #005baa;
	height: 2.3rem;
	vertical-align: middle;
	padding-top: 4px;
}

#pagination i {
	/*  margin: 0 3px 0 3px; */

}

#pagination a.current {
	border: 1px solid #005baa;
	box-shadow: 0 1px 1px #999;
	background-color: #005baa;
	color: white;
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

.test {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 10;
	background-color: rgba(0, 0, 0, 0.5);
}

#close {
	color: #fff;
	background-color: #005baa;
}

#overlay_text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 50px;
	color: #FFF;
	transform: translate(-50%, -50%);
}

#overlay_text .img-fluid {
	max-width: 100%;
}

#overlay_text img {
	height: 150px;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 2;
	cursor: pointer;
}

.pop-body {
	border-radius: 25px;
}

.mb-0 {
	padding-bottom: 0px !important;
}
</style>
<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>



<script lang="JavaScript">
	function loadSelectData() {
		$("#overlay").show();

		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;

		var PageNumber = document.getElementById("pgnum").value;
		//var type = document.getElementById("type").value;
		var bussinessname = document.getElementById("bussinessname").value;



		var fromDate = new Date(e);
		var toDate = new Date(e1);

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select Date");
            $("#overlay").hide();
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

	//		document.location.href = '${pageContext.request.contextPath}/transaction/searchFpxTxnSummary?fromDate='
	//			+ fromdateString + '&toDate=' + todateString + '&id=' + e2 ;

			document.location.href = '${pageContext.request.contextPath}/admin/searchsubmerchantSummaryDetails?fromDate='
				+ fromdateString + '&toDate=' + todateString +'&currPage=' + PageNumber +'&bussinessname='+bussinessname;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
		}


	function loadSelectData1() {
		$("#overlay").show();
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("FromDate").value;
		var e1 = document.getElementById("From1Date").value;
	//	var e2 = document.getElementById("merchantName").value;
		var PageNumber = document.getElementById("pgnum").value;
	//	var type = document.getElementById("type1").value;
		var bussinessname = document.getElementById("bussinessname").value;

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

		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select Date");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

	//		document.location.href = '${pageContext.request.contextPath}/transaction/searchFpxTxnSummary?fromDate='
	//			+ fromdateString + '&toDate=' + todateString + '&id=' + e2 ;

			document.location.href = '${pageContext.request.contextPath}/admin/searchsubmerchantSummaryDetails?fromDate='
				+ fromdateString + '&toDate=' + todateString + '&id=' + ""+'&currPage=' + PageNumber +'&bussinessname='+bussinessname ;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}


	function loadDropSearch(){
	  	  var e = document.getElementById("drop_search");
	      	var strUser = e.options[e.selectedIndex].value;
	      	document.getElementById("drop_val").value = strUser;

	      	/* For Dynamic Placeholder in SEARCH */

	      	if (strUser == "Ref") {
	      		document.getElementsByName('search')[0].placeholder = 'Ex: SellerOrderNo';
	      	  } else if (strUser == "Ap_Code") {
	      		  document.getElementsByName('search')[0].placeholder = 'Ex: FpxTxnID';
	      	  }
	      	  else if(strUser == ""){
	      		  document.getElementsByName('search')[0].placeholder = 'Please select type to search ';
	      	  }
	    }


	 function loadSearch()
     {
   		$("#overlay").show();
   		var Value = document.getElementById("searchApi").value;
   		var TXNTYPE = 'FPX1';
   		var type = document.getElementById("drop_val").value;

   		document.location.href = '${pageContext.request.contextPath}/adminsearchNew/find?VALUE='
 				+ Value + '&TXNTYPE='+ TXNTYPE +'&Type=' + type;
 			form.submit;

     }


	function loadDate(inputtxt, outputtxt) {

		// alert("test data123");
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		//alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		//alert(outputtxt.value);
		// alert(document.getElementById("date11").value);
	}

	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
	//	var e3 = document.getElementById("merchantName").value;

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

		/* var e2 = document.getElementById("txnType").value; */
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select Date");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
		//	document.location.href = '${pageContext.request.contextPath}/transaction/exportFpxTxnSummary?fromDate='
		//		+ fromdateString +'&toDate=' + todateString + '&id=' + e3 +'&export='+e2;

			document.location.href = '${pageContext.request.contextPath}/transaction/exportFpxTxnSummary?fromDate='
				+ fromdateString +'&toDate=' + todateString + '&id=' + "" +'&export='+e2;
			//form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}



</script>

<body>

<input type="hidden" id="bussinessname" name="bussinessname" value="${bussinessname}">

	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid mb-0" id="pop-bg">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Sub merchant Transaction Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<!-- NO RECORDS UI CHANGES -->

		<div class="modal fade bd-example-modal-xl pop-body"
			style="width: 500px !important; height: 270px !important;"
			id="exampleModalCenter" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true"
			style="text-align:center;">
			<div class="modal-dialog modal-xl">
				<div class="modal-content "
					style="padding: 0 !important; margin: 0 !important;">
					<p class="pop-head"
						style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Information</p>
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
						width="60px !important; height:60px !important;">
					<p id="innerText" style="font-size: 22px;"></p>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closepopup()"
							style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
					</div>
				</div>
			</div>
		</div>


		<!-- NO RECORDS UI CHANGES -->







		<script>
        $(document).ready(function(){

            // Initialize select2
            $("#selUser").select2();
            $(".select-filter").select2();


        });
        </script>


		<style>
.select2-dropdown {
	border: 2px solid #2e5baa;
}

.select2-container--default .select2-selection--single {
	border: none;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select2-results__options li {
	list-style-type: none;
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

.select2-results ul {
	max-height: 250px;
	curser: pointer;
}

.select2-results ul li:hover {
	background-color: #005baa !important;
	color: #fff !important;
}

.select-search-hidden .select2-container {
	display: none !important;
}
</style>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="row">
							<div class="input-field col s12 m3 l3">
								<label for="from" style="margin: 0px;">From</label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="date1" class="validate datepicker"
									onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>

							</div>

							<div class="input-field col s12 m3 l3">

								<label for="to" style="margin: 0px;">To</label> <input
									type="hidden" name="date12" id="date12"
									<c:out value="${toDate}"/>> <input id="to" type="text"
									name="toDate" class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
								<i class="material-icons prefix">date_range</i>
							</div>

							<!-- <div class="input-field col s12 m3 l3">
								<select id="type" name="type_1">
									<option value="All">-- Choose --</option>
									<option value="cards">Cards</option>
									<option value="fpx">FPX</option>
									<option value="boost">Boost</option>
									<option value="grab">Grab</option>
									<option value="tng">TNG</option>
									<option value="spp">SPP</option>

								</select>

							</div> -->



							<input type="hidden" name="date1" id="dateval1"> <input
								type="hidden" name="date2" id="dateval2">


							<div class="input-field col s12 m3 l3"
								style="width: 10%; margin-left: 3%;">
								<div class="button-class" style="float: left;">
									<button class="btn btn-primary blue-btn" type="button"
										onclick="loadSelectData()"
										style="font-family: 'Poppins', sans-serif; width: 100%; font-size: 14px;">Search</button>
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
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker',
	formatSubmit: 'MM/dd/yyyy',

});


</script>


<!--
		MAIN DIV SEARCH TEST
		<div class="row" id="searchBoxDiv">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">

						SEARCH TEST
						<div class="row"
							style="display: flex; align-items: center; justify-content: space-between; margin-left: 01%;">
							<div class="col s12">
								<div class="input-field col s12 m3 l3"
									style="font-family: 'Poppins', sans-serif; width: 30%;">
									<select name="drop_search" id="drop_search"
										onchange="return loadDropSearch();">
										<option selected value="" id="choose">Choose Type</option>
										<option value="Ref">Reference No</option>
										<option value="Ap_Code">Approval Code</option>
									</select> <input type="hidden" id="drop_val">
								</div>

								<div class="input-field col s12 m3 l3"
									style="margin-left: 07%; width: 30%;">
									<input type="text" id="searchApi" name="search" class=""
										style="font-family: 'Poppins', sans-serif;"
										placeholder="Please select type to search">
								</div>
								<div class="input-field col s12 m3 l3"
									style="width: 10%; margin-left: 07%;">
									<div class="button-class" style="float: left;">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="loadSearch()"
											style="font-family: 'Poppins', sans-serif; width: 100%; font-size: 14px;">Search</button>
									</div>
								</div>
							</div>
						</div>
						 SEARCH TEST ENDS
					</div>
				</div>
			</div>
		</div>

		MAIN SEARCH TEST ENDS
 -->








		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">


						<div class="table-responsive m-b-20 m-t-15" id="page-table">
							<table id="data_list_table1"
								class="table table-striped table-bordered">
								<thead align="center">

									<tr>
										<th>Transaction Date</th>
										<th>Transaction Time</th>
										<th>Sub Merchant Name</th>
										<th>RRN</th>
										<th>Transaction Amount</th>
										<th>TXN Type</th>
										<th>Invoice Id</th>
										<th>Status</th>
										<th>AID response</th>




										<!-- <th>Action</th>  -->

									</tr>
								</thead>

								<tbody id="prodReportTable">
									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr>

											<td>${dto.date}</td>
											<td>${dto.time}</td>
											<td>${dto.numOfSale}</td>
											<td>${dto.rrn}</td>
											<td style="text-align: right;">${dto.amount}</td>
											<td>${dto.txnType}</td>
											<td>${dto.invoiceId}</td>
											<td>${dto.status}</td>
											<td>${dto.aidResponse}</td>




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

	<!--  <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#merchantName').select2();
	/* 	$('#export').select2(); */
	});
</script> -->

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

	<div id="pagination"></div>
	<input type="hidden" id="pgnum">
	<input type="hidden" id="FromDate">
	<input type="hidden" id="From1Date">
	<input type="hidden" id="type1">
	<!--  <input type="hidden" id="TXNType1" > -->
	<script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
       //  var selectType = document.getElementById("type1").value="${paginationBean.selectType}";

        // var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/

         if(${paginationBean.itemList.size()}==0){
         document.getElementById("exampleModalCenter").style.display ="block";
          document.getElementById("pop-bg-color").style.display ="block";
          document.getElementById("page-table").style.display ="none";
                  document.getElementById("innerText").innerHTML = "Sorry, No Records Found";
                  document.getElementById("innerText").style.fontWeight ="400";
                  document.getElementById("innerText").style.color ="#171717";
                  document.getElementById("nxt").style.cursor ="not-allowed";
                  document.getElementById("nxt").style.opacity ="0.6";
                  document.getElementById("nxt").disabled ="disabled";
         }

         function closepopup(){
         document.getElementById("exampleModalCenter").style.display ="none";
          document.getElementById("pop-bg-color").style.display ="none";
         }

      </script>



<%--	<script>--%>
<%--         /* * * * * * * * * * * * * * * * *--%>
<%--          * Pagination--%>
<%--          * javascript page navigation--%>
<%--          * * * * * * * * * * * * * * * * */--%>
<%--         --%>
<%--          --%>
<%--          function dynamic(pgNo){--%>
<%--         	/* alert("Page Number:"+pgNo); */--%>
<%--         	document.getElementById("pgnum").value=pgNo;--%>
<%--         	 loadSelectData1();--%>
<%--         	--%>
<%--         }--%>
<%--         --%>
<%--          function previous(pgNo){--%>
<%--         		/* alert("Page Number:"+pgNo); */--%>
<%--         		pgNo--;--%>
<%--         		document.getElementById("pgnum").value=pgNo;--%>
<%--         		 loadSelectData1();--%>
<%--         		--%>
<%--         	}--%>
<%--          --%>
<%--          function next(pgNo){--%>
<%--         		/* alert("Page Number:"+pgNo); */--%>
<%--         		pgNo++;--%>
<%--         		document.getElementById("pgnum").value=pgNo;--%>
<%--         		 loadSelectData1();--%>
<%--         	}--%>
<%--          --%>
<%--          --%>
<%--         var Pagination = {--%>
<%--         --%>
<%--             code: '',--%>
<%--         --%>
<%--             // ----------------------%>
<%--             // Utility--%>
<%--             // ----------------------%>
<%--         --%>
<%--             // converting initialize data--%>
<%--             Extend: function(data) {--%>
<%--                 data = data || {};--%>
<%--                // Pagination.size = data.size || 300; --%>
<%--                 //console.log(Pagination.size);--%>
<%--               // Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;--%>
<%--               --%>
<%--                Pagination.size = ((${paginationBean.currPage})+4) ||100;--%>
<%--                 /* Pagination.page = data.page || 1; */--%>
<%--                 Pagination.page = ${paginationBean.currPage} || 1;--%>
<%--                 Pagination.step = ((data.step)-4) || 3;--%>
<%--             },--%>
<%--         --%>
<%--             // add pages by number (from [s] to [f])--%>
<%--             Add: function(s, f) {--%>
<%--                 for (var i = s; i < f; i++) {--%>
<%--                     Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';--%>
<%--                 }--%>
<%--             },--%>
<%--         --%>
<%--             // add last page with separator--%>
<%--           /*   Last: function() {--%>
<%--                 Pagination.code += '<i>...</i>';--%>
<%--             },--%>
<%--              */--%>
<%--         	 Last: function() {--%>
<%--                 Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';--%>
<%--             }, --%>
<%--         --%>
<%--             // add first page with separator--%>
<%--             First: function() {--%>
<%--             	if(Pagination.page==1){--%>
<%--             		 Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';--%>
<%--         			 --%>
<%--         		 }--%>
<%--             	else{--%>
<%--                 Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';--%>
<%--             	}--%>
<%--             },--%>
<%--         --%>
<%--         --%>
<%--         --%>
<%--             // ----------------------%>
<%--             // Handlers--%>
<%--             // ----------------------%>
<%--         --%>
<%--             // change page--%>
<%--             Click: function() {--%>
<%--                 Pagination.page = +this.innerHTML;--%>
<%--                 Pagination.Start();--%>
<%--                 dynamic(page);--%>
<%--             },--%>
<%--         --%>
<%--             // previous page--%>
<%--             Prev: function() { --%>
<%--             		--%>
<%--                 Pagination.page--;--%>
<%--                 if (Pagination.page < 1) {--%>
<%--                     Pagination.page = 1;--%>
<%--                 }--%>
<%--                 Pagination.Start();--%>
<%--                 dynamic(page);--%>
<%--              }, --%>
<%--             --%>
<%--             --%>
<%--         --%>
<%--             // next page--%>
<%--             --%>
<%--             --%>
<%--             Next: function() {--%>
<%--                 Pagination.page++;--%>
<%--                 if (Pagination.page > Pagination.size) {--%>
<%--                     Pagination.page = Pagination.size;--%>
<%--                 }--%>
<%--                 Pagination.Start();--%>
<%--                 dynamic(page);--%>
<%--              }, --%>
<%--             --%>
<%--              --%>
<%--         --%>
<%--             // ----------------------%>
<%--             // Script--%>
<%--             // ----------------------%>
<%--         --%>
<%--             // binding pages--%>
<%--             Bind: function() {--%>
<%--                 var a = Pagination.e.getElementsByTagName('a');--%>
<%--                 for (var i = 0; i < a.length; i++) {--%>
<%--                     if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';--%>
<%--                     a[i].addEventListener('click', Pagination.Click, false);--%>
<%--                 }--%>
<%--             },--%>
<%--         --%>
<%--             // write pagination--%>
<%--             Finish: function() {--%>
<%--                 Pagination.e.innerHTML = Pagination.code;--%>
<%--                 Pagination.code = '';--%>
<%--                 Pagination.Bind();--%>
<%--             },--%>
<%--         --%>
<%--             // find pagination type--%>
<%--             Start: function() {--%>
<%--                 if (Pagination.size < Pagination.step * 2 + 6) {--%>
<%--                     Pagination.Add(1, Pagination.size + 1);--%>
<%--                 }--%>
<%--                 else if (Pagination.page < Pagination.step * 2 + 1) {--%>
<%--                     Pagination.Add(1, Pagination.step * 2 + 4);--%>
<%--                     Pagination.Last();--%>
<%--                 }--%>
<%--                 else if (Pagination.page > Pagination.size - Pagination.step * 2) {--%>
<%--                     Pagination.First();--%>
<%--                     Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);--%>
<%--                 }--%>
<%--                 else {--%>
<%--                     Pagination.First();--%>
<%--                     Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);--%>
<%--                     Pagination.Last();--%>
<%--                 }--%>
<%--                 Pagination.Finish();--%>
<%--             },--%>
<%--         --%>
<%--         --%>
<%--         --%>
<%--             // ----------------------%>
<%--             // Initialization--%>
<%--             // ----------------------%>
<%--         --%>
<%--             // binding buttons--%>
<%--             Buttons: function(e) {--%>
<%--                 var nav = e.getElementsByTagName('a');--%>
<%--                 nav[0].addEventListener('click', Pagination.Prev, false);--%>
<%--                 nav[1].addEventListener('click', Pagination.Next, false);--%>
<%--             },--%>
<%--         --%>
<%--             // create skeleton--%>
<%--             Create: function(e) {--%>
<%--         --%>
<%--                 var html = [--%>
<%--                     '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;">&#60;&#60; Previous</a>', // previous button--%>
<%--                     '<span></span>',  // pagination container--%>
<%--                     '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button--%>
<%--                 ];--%>
<%--         --%>
<%--                 e.innerHTML = html.join('');--%>
<%--                 Pagination.e = e.getElementsByTagName('span')[0];--%>
<%--                 Pagination.Buttons(e);--%>
<%--             },--%>
<%--         --%>
<%--             // init--%>
<%--             Init: function(e, data) {--%>
<%--                 Pagination.Extend(data);--%>
<%--                 Pagination.Create(e);--%>
<%--                 Pagination.Start();--%>
<%--             }--%>
<%--         };--%>
<%--         --%>
<%--         --%>
<%--         --%>
<%--         /* * * * * * * * * * * * * * * * *--%>
<%--         * Initialization--%>
<%--         * * * * * * * * * * * * * * * * */--%>
<%--         --%>
<%--         var init = function() {--%>
<%--             Pagination.Init(document.getElementById('pagination'), {--%>
<%--                 size: 100, // pages size--%>
<%--                 page: 1,  // selected page--%>
<%--                 step: 3   // pages before and after current--%>
<%--             });--%>
<%--         };--%>
<%--         --%>
<%--         document.addEventListener('DOMContentLoaded', init, false);--%>
<%--      </script>--%>

<script>


	var size = "${paginationBean.querySize}";
	console.log(size)


	//Assuming you have a function to add a class to elements
	function addClass(element, className) {
		if (element.classList) {
			element.classList.add(className);
		} else {
			element.className += ' ' + className;
		}
	}


	function dynamic(pgNo) {
		/* alert("Page Number:"+pgNo); */
		document.getElementById("pgnum").value = pgNo;
		loadSelectData1();

	}

	function previous(pgNo) {
		/* alert("Page Number:"+pgNo); */
		pgNo--;
		document.getElementById("pgnum").value = pgNo;
		loadSelectData1();

	}

	function next(pgNo) {
		/* alert("Page Number:"+pgNo); */
		pgNo++;
		document.getElementById("pgnum").value = pgNo;
		loadSelectData1();
	}


	var Pagination = {

		code: '',

		// --------------------
		// Utility
		// --------------------

		// converting initialize data
		Extend: function (data) {
			data = data || {};
			Pagination.size = Math.ceil(${paginationBean.querySize} / 20);


			<%--Pagination.size = ((${paginationBean.currPage})+4) ||100;--%>
			/* Pagination.page = data.page || 1; */
			Pagination.page =
			${paginationBean.currPage} ||
			1;
			Pagination.step = ((data.step) - 4) || 3;
		},

		// add pages by number (from [s] to [f])
		Add: function (s, f) {
			for (var i = s; i < f; i++) {
				Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
			}
		},

		// add last page with separator
		/*   Last: function() {
              Pagination.code += '<i>...</i>';
          },
           */
		// Last: function() {
		//     Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
		// },
		//
		// // add first page with separator
		// First: function() {
		//     if(Pagination.page==1){
		//
		//         Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';
		//
		//     }
		//     else{
		//         Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
		//     }
		// },


		// newer chnagess

		First: function () {
			Pagination.code += '<a onclick="dynamic(1)">1</a>';
			if (Pagination.page > 3) {
				Pagination.code += '<i>...</i>';
			}
			for (var i = Math.max(2, Pagination.page - 1); i <= Pagination.page; i++) {
				//directly goes to 1 instead of re-arranging
				Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
			}
		},

// arranges ...
		Last: function () {

			// total page
			var lastPage = Math.ceil(${paginationBean.querySize} / 20);
			// three pg no after 1st pg no
			if (lastPage > Pagination.page + 3) {
				// generate <a> tag for 3 pg no
				for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
					Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
				}
				Pagination.code += '<i>...</i>';
//         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
			} else {
				// if less than 3 page generate <a> tag
				for (var i = Pagination.page + 1; i <= lastPage; i++) {
					Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
				}
			}
		},


		// --------------------
		// Handlers
		// --------------------

		// change page
		Click: function () {
			Pagination.page = +this.innerHTML;
			Pagination.Start();
			dynamic(page);
		},

		// previous page
		Prev: function () {

			Pagination.page--;
			if (Pagination.page < 1) {
				Pagination.page = 1;
			}
			Pagination.Start();
			dynamic(page);
		},


		// next page


		Next: function () {
			Pagination.page++;
			if (Pagination.page > Pagination.size) {
				Pagination.page = Pagination.size;
			}
			Pagination.Start();
			dynamic(page);
		},


		// --------------------
		// Script
		// --------------------

		// binding pages
		Bind: function () {
			var a = Pagination.e.getElementsByTagName('a');
			for (var i = 0; i < a.length; i++) {
				if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
				a[i].addEventListener('click', Pagination.Click, false);
			}
		},

		// write pagination
		Finish: function () {
			Pagination.e.innerHTML = Pagination.code;
			Pagination.code = '';
			Pagination.Bind();
		},

		// find pagination type
		Start: function () {
			if (Pagination.size < Pagination.step * 2 + 6) {
				Pagination.Add(1, Pagination.size + 1);
			} else if (Pagination.page < Pagination.step * 2 + 1) {
				Pagination.Add(1, Pagination.step * 2 + 4);
				Pagination.Last();
			} else if (Pagination.page > Pagination.size - Pagination.step * 2) {
				Pagination.First();
				Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
			} else {
				Pagination.First();
				Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);
				Pagination.Last();
			}
			Pagination.Finish();
		},


		// --------------------
		// Initialization
		// --------------------

		// binding buttons
		Buttons: function (e) {
			var nav = e.getElementsByTagName('a');

			nav[0].addEventListener('click', Pagination.Prev, false);
			nav[1].addEventListener('click', Pagination.Next, false);
		},

		// create skeleton
		Create: function (e) {

			var html = [
				'<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
				'<span></span>',  // pagination container
				'<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
			];

			e.innerHTML = html.join('');
			Pagination.e = e.getElementsByTagName('span')[0];
			Pagination.Buttons(e);

			<%--if (${paginationBean.currPage} == 1) {--%>
			//     var previousButton = document.getElementById("previous");
			//     previousButton.style.pointerEvents = "none";
			//     previousButton.style.opacity = "0.5";
			// }

			if (${paginationBean.currPage} == 1
		)
			{
				var previousButton = document.getElementById("previous");
				previousButton.style.pointerEvents = "none";
				previousButton.style.opacity = "0.5";
			}
			// my chnages
			if (${paginationBean.currPage} == Pagination.size
		)
			{
				var nextButton = document.getElementById("nxt");
				nextButton.style.pointerEvents = "none";
				nextButton.style.opacity = "0.5";
			}
			if (Pagination.size == 0) {
				var nextButton = document.getElementById("nxt");
				nextButton.style.pointerEvents = "none";
				nextButton.style.opacity = "0.5";
			}
			if (Pagination.size == 0) {
				var paginationContainer = document.getElementById("pagination");
				paginationContainer.style.display = "none";
			}

		},

		// init
		Init: function (e, data) {
			Pagination.Extend(data);
			Pagination.Create(e);
			Pagination.Start();
		}
	};


	/* * * * * * * * * * * * * * * * *
    * Initialization
    * * * * * * * * * * * * * * * * */

	var init = function () {
		Pagination.Init(document.getElementById('pagination'), {
			size: 100, // pages size
			page: 1,  // selected page
			step: 3   // pages before and after current
		});
	};

	document.addEventListener('DOMContentLoaded', init, false);


	var PageNumber = document.getElementById("pgnum").value;


</script>
</body>
</html>
