<%@page import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap" rel="stylesheet">

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>


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
      margin-bottom:10px;
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
      -webkit-user-select:none;
      -moz-user-select:none;
      -ms-user-select:none;
      -o-user-select:none;
      user-select:none;
      }
      #pagination a {
      /* margin: 0 2px 0 2px; */ 
      margin:0 2px;
      border-radius: 1px;
      border: 1px solid #005baa;
      cursor: pointer;
      /* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
      /* text-shadow: 0 1px 1px #FFF; */
      background-color: white;
      color: #005baa;
      height:2.3rem;
      vertical-align: middle;
      padding-top:4px;
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
      #exampleModalCenter{
      z-index:99;
      width:25%;
      font-size:24px;
      
      font-weight:400;
      font-family:'Poppins',sans-serif;
      text-align:center;
      }
      .test{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 10;
      background-color: rgba(0,0,0,0.5);
      }
      #close{
      color:#fff;
      background-color:#005baa;
      }
     
      #overlay_text{
      position:absolute; 
      top:50%;
      left:50%;
      font-size:50px;
      color:#FFF;
      transform:translate(-50%,-50%);
      }
      #overlay_text .img-fluid{max-width:100%;}
      #overlay_text img{
      height:150px;}
      #overlay {
      position: fixed;
      display: none;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0,0,0,0.5);
      z-index: 2;
      cursor: pointer;
      }
      
      .pop-body{
      border-radius:25px;
     
      }
      .mb-0{
      padding-bottom: 0px !important;
      }
</style>
<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#export').select2();
		$('#txnType').select2();
	});
</script> -->

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
	
	
	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

<script lang="JavaScript">
var a1 = [];
var ids;

	function loadSelectData() {
		$("#overlay").show();
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var PageNumber = document.getElementById("pgnum").value;
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
		if ((e == '' && e1 != '') || (e != '' && e1 == '')) {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchMotoVC?date='
					+ fromdateString + '&date1=' + todateString +'&currPage='+PageNumber;
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
		var PageNumber = document.getElementById("pgnum").value;
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
		if ((e == '' && e1 != '') || (e != '' && e1 == '')) {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchMotoVC?date='
					+ fromdateString + '&date1=' + todateString +'&currPage='+PageNumber;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}
	
	
	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		/* alert("e"+e);
		alert("e1"+e1);
		alert("e2"+e2); */
		
		/* var e2 = document.getElementById("txnType").value; */
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = e;
			document.getElementById("datevalex2").value = e1;
			
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionMoto/MotoVCExport?fromDate=' + e
					+ '&toDate=' + e1 +'&export='+e2; 
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
	
	/* function cancel(id) {
		
		document.location.href = '${pageContext.request.contextPath}/transactionMoto/MotoVCCancel?id=' + id; 
		form.submit;
		
	} */
	
	function select(id) {

		var s1= "b"+id;
		var btn = document.getElementById(s1).value;
		
		if(btn == 'true'){
			document.getElementById(s1).value = "false";
			document.getElementById(s1).style.color = "navy";
			a1.push(id);
			
		}else{
			document.getElementById(s1).value = "true";
			document.getElementById(s1).style.color = "gray";
			removeA(a1, id);
		}
		
		/*  a1.forEach(myFunction);

		 function myFunction(item, index) {
			 
			 console.log(index + ":" + item );
			 
		 } */
		
	}
	
	function removeA(arr) {
	    var what, a = arguments, L = a.length, ax;
	    while (L > 1 && arr.length) {
	        what = a[--L];
	        while ((ax= arr.indexOf(what)) !== -1) {
	            arr.splice(ax, 1);
	        }
	    }	
	    return arr;
	}
	
	function enable() {
		alert("inn");
		document.getElementById("cancel").disabled = false;
		document.getElementById("submit").disabled = false; 
		document.getElementById("back").disabled = false;
	}
	
	function load(res) {
		
		document.getElementById("cancel").disabled = true;
		document.getElementById("submit").disabled = true; 
		document.getElementById("back").disabled = true;
		
		if(res == "submit"){
			
		var c1= a1.length;
		var title ="Are you sure? you want to proceed transaction";
		
		if(c1 == "0"){
			alert("please select atleast one card detail to proceed");
			enable(); 
			return false;
		}
		ids = a1.toString();
		//console.log("ids:::::"+ids);
		swal(
				{
					title : title,
					text : "it will be procedded..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, add it!",
					cancelButtonText : "No, cancel!",
					closeOnConfirm : false,
					closeOnCancel : false,
					confirmButtonClass : 'btn btn-success',
					cancelButtonClass : 'btn btn-danger',

				},
				function(isConfirm) {
					if (isConfirm) {
						swal("Procedded!", "Card details procedded","success");
						document.location.href = '${pageContext.request.contextPath}/transactionMoto/MotoVCTxn?id=' + ids; 
						form.submit;

					} else {
						swal("Cancelled", "Card details not procedded", "error"); 
						var url = "${pageContext.request.contextPath}/transactionMoto/vcSummary";
						$(location).attr('href', url);
					}
				}); 
		
		}else{
				var c1= a1.length;
				var title ="Are you sure? you want to cancel transaction";
				
				if(c1 == "0"){
					alert("please select atleast one card detail to cancel");
					enable(); 
					return false;
				}
				ids = a1.toString();
				//console.log("ids:::::"+ids);
				swal(
						{
							title : title,
							text : "it will be cancelled..!",
							type : "warning",
							showCancelButton : true,
							confirmButtonText : "Yes!",
							cancelButtonText : "No!",
							closeOnConfirm : false,
							closeOnCancel : false,
							confirmButtonClass : 'btn btn-success',
							cancelButtonClass : 'btn btn-danger',

						},
						function(isConfirm) {
							if (isConfirm) {
								swal("Procedded!", "Card details cancelled","success");
								document.location.href = '${pageContext.request.contextPath}/transactionMoto/MotoVCCancel?id=' + ids; 
								form.submit;

							} else {
								swal("Cancelled", "Card details not procedded", "error"); 
								var url = "${pageContext.request.contextPath}/transactionMoto/vcSummary";
								$(location).attr('href', url);
							}
						}); 
				
				}
		}

</script>
<body class="">


<div id="overlay">
         <div id="overlay_text"><img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif"></div>
      </div>
      <div class="test" id="pop-bg-color"></div>
      <div class="container-fluid mb-0" id="pop-bg"> 
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>EZYMOTO VCC Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

		<div class="modal fade bd-example-modal-xl pop-body" style="width:500px !important;height:270px !important;" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="text-align:center;">
            <div class="modal-dialog modal-xl" >
               <div class="modal-content " style="padding:0 !important;margin:0 !important;" >
                  <p class="pop-head" style="background-color:#f9f9f9;width:100%;height:60px;color:#005baa;padding-top:12px;border-bottom: 2px solid #ffa500;">Information</p>
                  <img src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png" width="60px !important; height:60px !important;">
                  <p id="innerText" style="font-size:22px;"></p>
                  <div class="modal-footer"><button type="button" class="btn btn-secondary" data-dismiss="modal" id="close" onclick="closepopup()" style="width:106px !important; height:38px !important;font-size:18px;border-radius:50px !important;margin-right:187px !important;letter-spacing:0.8px;font-family:'Poppins',sans-serif;font-weight:medium;transform:translateY(-10px);">Close</button></div>
               </div>
            </div>
         </div>


<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

          
								<div class="row">
									<div class="input-field col s12 m3 l3">
											<label for="from" style="margin:0px;">From </label>
											<input type="hidden"
												name="date11" id="date11" <c:out value="${fromDate}"/>> 
									<input type="text" id="from" name="date1"  class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
									<i class="material-icons prefix">date_range</i>
				
										
									</div>

									<div class="input-field col s12 m3 l3">

											<label for="to" style="margin:0px;">To</label>
										<input type="hidden"
													name="date12" id="date12" <c:out value="${fromDate}"/>>
										<input id="to" type="text" class="datepicker" 
										onchange="loadDate(document.getElementById('to'),document.getElementById('date11'))">
										<i class="material-icons prefix">date_range</i>
									</div>


								 
								
								
								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="return loadSelectData();">Search</button>

									</div>
									
								</div>
								
							</div>

						</div>
					</div></div>


<script>

jQuery(function() {		
	var date = new Date();
	var currentMonth = date.getMonth();
	var currentDate = date.getDate();
	var currentYear = date.getFullYear();
	
	$('.datepicker').datepicker({
		minDate: new Date(currentYear, currentMonth-3, currentDate),
		maxDate: new Date(currentYear, currentMonth, currentDate+1)
	});
});

	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});


</script>
				
					<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">

					          <div class="table-responsive m-b-20 m-t-15" id="page-table">
					            <table id="data_list_table1" class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Select</th>
									<th>Date</th>
									<th>Name On Card</th>
									<!-- <th>F005_EXPDATE</th> -->
									<th>Card No</th>
									<th>Status</th>
									<th>Amount</th>
									<!-- <th>RES CODE</th> -->
									<!-- <th>F263_MRN</th> -->
									<th>RES MSG</th>
									<!-- <th>TXN ID</th> -->
									<!-- <th style="text-align: end;">Cancel</th> -->
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${paginationBean.itemList}" var="dto">
									<tr>
										<td style="text-align: center;"><c:if test="${dto.status =='SUBMITTED'}">
										<button class="btn editBtn" id="b${dto.id}"  type="button" onclick="select('${dto.id}');" value="true"><i class="material-icons">check_box</i>
										</button></c:if></td>
										<td>${dto.createdBy}</td>
										<td>${dto.nameOnCard}</td>
										<%-- <td>${dto.f005_EXPDATE}</td> --%>
										<td>${dto.txnDetails}</td>
										<%-- <c:if test="${dto.status !='PENDING' or dto.status !='SENT'}"><td>${dto.status}</td></c:if>
										<c:if test="${dto.status =='PENDING' or dto.status =='SENT'}"><td>PROCESSING</td></c:if> --%>
										<c:choose>
										<c:when test="${dto.status =='PENDING' or dto.status =='SENT'}"><td>PROCESSING</td></c:when> 
										<c:otherwise><td>${dto.status}</td></c:otherwise>
										</c:choose>
										<td style="text-align:right;">${dto.amount}</td>
										<%-- <td>${dto.respCode}</td> --%>
										<%-- <td>${dto.f263_MRN}</td> --%>
										<td>${dto.respMsg}</td>
										<%-- <td>${dto.txnId}</td> --%>

											<%-- <td align="center">
												
												<c:if
													test="${dto.status =='SUBMITTED'}">
												
												<button class="btn btn-danger" type="button"
											onclick="cancel('${dto.id}');"><i class="fa fa-history"></i></button></c:if>
												</td> --%>


										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
	<style>
			.editBtn{
			color: #039be5;	
			background:none !important;		
    		text-decoration: none !important;
    		border:none;
    		-webkit-tap-highlight-color: transparent;
			}
			</style>		
			
						<div class="row">
				<div class="input-field col s12 m12 l12"> 
				<span class="right" style="float:left !important;">
				  <button class="btn btn-primary blue-btn" id="back">
                  <a style="color:white !important;"  href="${pageContext.request.contextPath}/transactionMoto/vcSummary"
					>Back</a></button>
				 <button class="btn btn-primary blue-btn" id="cancel"
					onclick="load('cancel')">Cancel</button>
				   <button class="btn btn-primary blue-btn" id="submit"
					onclick="return load('submit')">Proceed</button>
				   </span>
				</div>
				</div>
	
					</div>
				</div>

			</div>
			</div>
		
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
	
      <div id="pagination"></div>
      <input type="hidden" id="pgnum" >
      <input type="hidden" id="FromDate" >
      <input type="hidden" id="From1Date" >
     <!--  <input type="hidden" id="TXNType1" > -->
      <script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
         
      //   var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/
         
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
      <script>
         /* * * * * * * * * * * * * * * * *
          * Pagination
          * javascript page navigation
          * * * * * * * * * * * * * * * * */
         
          
          function dynamic(pgNo){
         	/* alert("Page Number:"+pgNo); */
         	document.getElementById("pgnum").value=pgNo;
         	 loadSelectData1();
         	
         }
         
          function previous(pgNo){
         		/* alert("Page Number:"+pgNo); */
         		pgNo--;
         		document.getElementById("pgnum").value=pgNo;
         		 loadSelectData1();
         		
         	}
          
          function next(pgNo){
         		/* alert("Page Number:"+pgNo); */
         		pgNo++;
         		document.getElementById("pgnum").value=pgNo;
         		 loadSelectData1();
         	}
          
          
         var Pagination = {
         
             code: '',
         
             // --------------------
             // Utility
             // --------------------
         
             // converting initialize data
             Extend: function(data) {
                 data = data || {};
                // Pagination.size = data.size || 300; 
                 //console.log(Pagination.size);
               // Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;
               
                Pagination.size = ((${paginationBean.currPage})+4) ||100;
                 /* Pagination.page = data.page || 1; */
                 Pagination.page = ${paginationBean.currPage} || 1;
                 Pagination.step = ((data.step)-4) || 3;
             },
         
             // add pages by number (from [s] to [f])
             Add: function(s, f) {
                 for (var i = s; i < f; i++) {
                     Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';
                 }
             },
         
             // add last page with separator
           /*   Last: function() {
                 Pagination.code += '<i>...</i>';
             },
              */
         	 Last: function() {
                 Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
             }, 
         
             // add first page with separator
             First: function() {
             	if(Pagination.page==1){
             		 Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
         			 
         		 }
             	else{
                 Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
             	}
             },
         
         
         
             // --------------------
             // Handlers
             // --------------------
         
             // change page
             Click: function() {
                 Pagination.page = +this.innerHTML;
                 Pagination.Start();
                 dynamic(page);
             },
         
             // previous page
             Prev: function() { 
             		
                 Pagination.page--;
                 if (Pagination.page < 1) {
                     Pagination.page = 1;
                 }
                 Pagination.Start();
                 dynamic(page);
              }, 
             
             
         
             // next page
             
             
             Next: function() {
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
             Bind: function() {
                 var a = Pagination.e.getElementsByTagName('a');
                 for (var i = 0; i < a.length; i++) {
                     if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
                     a[i].addEventListener('click', Pagination.Click, false);
                 }
             },
         
             // write pagination
             Finish: function() {
                 Pagination.e.innerHTML = Pagination.code;
                 Pagination.code = '';
                 Pagination.Bind();
             },
         
             // find pagination type
             Start: function() {
                 if (Pagination.size < Pagination.step * 2 + 6) {
                     Pagination.Add(1, Pagination.size + 1);
                 }
                 else if (Pagination.page < Pagination.step * 2 + 1) {
                     Pagination.Add(1, Pagination.step * 2 + 4);
                     Pagination.Last();
                 }
                 else if (Pagination.page > Pagination.size - Pagination.step * 2) {
                     Pagination.First();
                     Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
                 }
                 else {
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
             Buttons: function(e) {
                 var nav = e.getElementsByTagName('a');
                 nav[0].addEventListener('click', Pagination.Prev, false);
                 nav[1].addEventListener('click', Pagination.Next, false);
             },
         
             // create skeleton
             Create: function(e) {
         
                 var html = [
                     '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;">&#60;&#60; Previous</a>', // previous button
                     '<span></span>',  // pagination container
                     '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
                 ];
         
                 e.innerHTML = html.join('');
                 Pagination.e = e.getElementsByTagName('span')[0];
                 Pagination.Buttons(e);
             },
         
             // init
             Init: function(e, data) {
                 Pagination.Extend(data);
                 Pagination.Create(e);
                 Pagination.Start();
             }
         };
         
         
         
         /* * * * * * * * * * * * * * * * *
         * Initialization
         * * * * * * * * * * * * * * * * */
         
         var init = function() {
             Pagination.Init(document.getElementById('pagination'), {
                 size: 100, // pages size
                 page: 1,  // selected page
                 step: 3   // pages before and after current
             });
         };
         
         document.addEventListener('DOMContentLoaded', init, false);
      </script>
</body>

</html>