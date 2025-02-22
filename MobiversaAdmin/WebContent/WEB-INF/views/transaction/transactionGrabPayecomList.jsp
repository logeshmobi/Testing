<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
</head>

<style>


#merchantName:hover
{
   color:275ca8;
}
#agentName:hover
{
   color:275ca8;
}

.example_e1:focus {
    outline:none !important;
}

.example_e1  {
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
   color:rgb(39, 92, 168);
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
    transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform 0.2s cubic-bezier(0.35, 0, 0.25, 1),
     background-color 0.3s ease-in-out;
  font-style:Arial, Helvetica, sans-serif;
  border-radius:15px;
  
   }

.example_e1:hover {
 color:rgb(39, 92, 168);
font-weight: 600 !important;

-webkit-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
-moz-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
transition: all 0.3s ease 0s;
border:2px solid #cfcfd1;
 outline:0 !important;
}
</style>
<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>
<script type="text/javascript">
	jQuery(document).ready(function() {
/* 
		$('#export').select2();
		$('#txnType').select2(); */
	});
</script>

<script type="text/javascript">
function openNewWin(mrn){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'transaction/UMdetails/'+mrn;
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


<script lang="JavaScript">
	function loadSelectData() {
		//alert("test"+document.getElementById("from").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
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

		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transaction/searchgrabpayecom?fromDate='
					+ fromdateString + '&toDate=' + todateString;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}

	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	function loadDropDate14() {
		//alert("loadDropDate13");
		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;
		//alert("txntype: "+strUser);
		//document.getElementById("searchTxnType").value=strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
function checkTxnType()
{
//alert("check"+document.getElementById("txnType1").value);
var txnType=document.getElementById("txnType1").value;
if(txnType=="Choose" || txnType=='')
{
alert("please select txnType field..");
return false;
}

}
	/* function loaddata() {
		loadDropDate13();
		loadDropDate14();
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("txnType1").value;

		//alert("e2" + e2);

		if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			/* document.location.href = '${pageContext.request.contextPath}/transaction/export?date='
					+ e + '&date1=' + e1 + '&export=' + e2 + '&txnType=' + e3; */
			//alert(e);
			//form.submit();

		/* }
	}  */
	
	function loadExpData() {
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		
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
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transaction/exportgrabpayecom?fromDate=' + fromdateString
					+ '&toDate=' + todateString +'&export='+e2; 
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
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

	
	function loadChData(amt,mid,mrn) {
		//alert("test"+document.getElementById("txnType").value);
		/* var e = document.getElementById("chBack").value;
		var e1 = document.getElementById("mid").value; */
		/* alert("amt:::::::::"+amt);
		alert("mid:::::::::"+mid);
		alert("mrn:::::::::"+mrn); */
			document.location.href = '${pageContext.request.contextPath}/MDR/regChBack?chBack=' + amt
					+ '&mid=' + mid + '&mrn=' + mrn ; 
			form.submit;
	}

</script>
<body class="">

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>GRABPAY Ecom Transaction Summary </strong></h3>
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
						<label for="from" style="margin:0px;"> From </label><input type="hidden"
									name="date11" id="date11" <c:out value="${fromDate}"/>> 
						<input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
						<i class="material-icons prefix">date_range</i>
							
							</div>
							
						<div class="input-field col s12 m3 l3">
						
						<label for="to" style="margin:0px;">To</label>
						<input type="hidden"
									name="date12" id="date12" <c:out value="${toDate}"/>>
						<input id="to" type="text" class="datepicker" 
						onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
						<i class="material-icons prefix">date_range</i>
					</div>
					<%-- <div class="input-field col s12 m3 l3">
						<select name="txnType" id="txnType"
											onchange="return loadDropDate14();" 
											style="width: 100%">
											<option selected value="">Choose</option>
											
											<option value="ALL">ALL</option>
											<option value="EZYWIRE">EZYWIRE</option>
											<option value="EZYCASH">EZYCASH</option>
											<option value="BOOST">BOOST</option>
											<option value="GRABPAY">GRABPAY</option>
											<option value="EZYMOTO">EZYMOTO</option>
											<option value="EZYWAY">EZYWAY</option>
											<option value="EZYREC">EZYREC</option>
											<option value="EZYRECPLUS">EZYREC+</option>
											<!-- <option value="MOTO">EZYPASS</option> -->
										</select>
										<label>Txn Type</label> <input
											type="hidden" name="txnType1" id="txnType1" <c:out value="${status}"/>>
					</div> --%>
					
					<div class="input-field col s12 m3 l3">
										
										<input
											type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
										<select name="export" id="export" onchange="return loadDropDate13();"
											>
											<option selected value="">Choose</option>
											<option value="PDF">PDF</option>
											<option value="EXCEL">EXCEL</option>
										</select>
										<label class="name">Export Type</label> 
									</div>
					
					
									<div class="input-field col s12 m3 l3" style="float:right !important;">
					  <div class="button-class">
						<input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button>
						<input type="hidden" name="dateex1" id="datevalex1"> <input
											type="hidden" name="dateex2" id="datevalex2">
						<button class="export-btn waves-effect waves-light btn btn-round indigo"  type="button"  onclick="return loadExpData();">Export</button>
					 </div> 
					 </div>
					
					</div>
					
						
						</div>
					</div></div>
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
									<th>Date</th>
										<th>Time</th>
										<th>Status</th>
										<!-- <th>Card Number</th> -->
										<th>Stan</th>
										<th>Amount(RM)</th>

										<th align="right" width="75px">Business Name</th>
										<!-- <th>Location</th> -->

										<!-- <th>Action</th> -->
										<!-- <th>Void</th> -->
									
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${paginationBean.itemList}" var="dto">
									<tr>
										
											<td>${dto.date}</td>
											<td>${dto.time}</td>
                                          
											<td>${dto.status}</td>
											<%-- <td align="center">${dto.pan}</td> --%>
											<td>${dto.stan}</td>
											<td style="text-align:right;">${dto.amount}</td>
											<!-- style="padding: 14px;" align="right" class="separate 10px;" -->

											<td><a style="color: #4bae4f" href="#"
												title="Tid:${dto.tid}">${dto.merchantName}</a></td>
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
  <style>
	td, th { padding: 7px 8px;color:#707070; }
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