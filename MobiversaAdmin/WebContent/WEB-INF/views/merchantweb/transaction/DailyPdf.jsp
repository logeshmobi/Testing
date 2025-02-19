<%@page import="com.mobiversa.payment.controller.MerchantWebTransactionController" %>
	<%@page import="com.mobiversa.common.bo.Merchant" %>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
					<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
						<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
							<%@ page language="java" import="java.util.*" %>
								<%@ page import="java.util.ResourceBundle" %>
									<% ResourceBundle resource=ResourceBundle.getBundle("config"); String
										actionimg=resource.getString("NEWACTION"); %>


										<html>

										<head>
										<!-- Script tag for Datepicker -->

										<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    									 <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
										
											<meta charset="UTF-8">
											<meta
												content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
												name="viewport">


											<script type="text/javascript">
												history.pushState(null, null, "");
												window.addEventListener('popstate', function () {
													history.pushState(null, null, "");

												});
											</script>

											<style>
												.export_div .select-wrapper {
													width: 65%;
													float: left;
												}


												.w24 {
													width: 24px;
												}

												.datepicker {
													width: 80% !important;
												}

												.select-wrapper .caret {
													fill: #005baa;
												}

												.addUserBtn,
												.addUserBtn:hover {
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
											<style>
												td,
												th {
													padding-top: 7px;
													padding-bottom: 7px;
													padding-right: 600px;
													color: #707070;
												}

												thead th {
													border-bottom: 1px solid #ffa500;
													color: #4377a2;
												}
											</style>

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

												.addUserBtn,
												.addUserBtn:hover {
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
											
											<style>

 

.test{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 10;
      background-color: rgba(0,0,0,0.5);
      display:none;
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

</style>
											


											<script lang="JavaScript">
												function loadSelectData() {
													//alert("test"); 
													/* DateCheck(); */
													 $("#overlay").show();
													var e = document.getElementById("from").value;
													var e1 = document.getElementById("to").value;
													var e2 = document.getElementById("txnType").value;
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
													/* var e2 = document.getElementById("tid1").value;
													var e3 = document.getElementById("devid1").value;
													var e4 = document.getElementById("status1").value;
											 */
													//alert("e"+ e);
													//alert("e1"+ e1);

													//Date Validation - Start	
													var fromdate = fromyear + '-' + (frommon <= 9 ? '0' + frommon : frommon) + '-' + (fromday <= 9 ? '0' + fromday : fromday);
													var todate = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon) + '-' + (today <= 9 ? '0' + today : today);


													var Checkdate = "2022-03-20";
													var from = fromdate;
													var to = todate;



													var eDate = new Date(todateString);
													var sDate = new Date(fromdateString);

													if (e == '' && e1 == '' && e2 == '') {
														alert("Please select conditions.");
														return false;
													} else if ((e == '' && e1 != '') || (e != '' && e1 == '')) {
														alert("Please enter both Date's.");
														return false;
													}

													else if (e == '' && e1 == '' && e2 != '') {
														alert("Please enter both Date's.");
														return false;
													}

													else if (e2 == '') {
														alert("Please select payment method");
														return false;
													} else if (sDate > eDate) {
														alert("Please ensure that the End Date is greater than or equal to the Start Date.");
														return false;
													}
													else
														if (new Date(from) <= new Date(Checkdate) && new Date(to) <= new Date(Checkdate)) {

															alert("PDF Available only From March 21 2022")
															return false;
														}

														else if (new Date(from) <= new Date(Checkdate)) {

															alert("PDF Available only From March 21 2022")
															return false;
														} else
															if (new Date(to) <= new Date(Checkdate)) {

																alert("PDF Available only From March 21 2022")
																return false;
															}







															else {
																/* if(e!= '' && e!= '' && sDate> eDate)
																  {
																  alert("Please ensure that the End Date is greater than or equal to the Start Date.");
																  return false;
																  } */
																/* var e2 = document.getElementById("tid1").value;
																 vare3=document.getElementById("devid1").value; 
																var e4 = document.getElementById("status1").value;
																 */
																document.getElementById("date11").value = fromdateString;
																document.getElementById("date12").value = todateString;

																/* document.getElementById("tid1").value = e2;
																document.getElementById("devid1").value = e3;
																document.getElementById("status1").value = e4; */

																/* document.location.href = '${pageContext.request.contextPath}/transactionweb/search?fromDate=' + e
																		+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4;
																form.submit; */

																document.getElementById("date11").value = fromdateString;
																document.getElementById("date12").value = todateString;
																//alert("from date:"+ e);
																//alert("todate:"+ e1);
																document.form1.action = "${pageContext.request.contextPath}/transactionweb/pdfdailysearch";
																//form.submit;
																document.form1.submit();
															}
												}





												function loadData(num) {
													var pnum = num;
													//alert("page :"+pnum);
													var e = document.getElementById("datepicker").value;
													var e1 = document.getElementById("datepicker1").value;
													var e2 = document.getElementById("txnType").value;
													/*var e3 = document.getElementById("devid1").value;
													var e4 = document.getElementById("status1").value; */

													e = document.getElementById("date11").value;
													e1 = document.getElementById("date12").value;
													e2 = document.getElementById("txnType").value;

		/*e3 = document.getElementById("devid1").value;
		e4 = document.getElementById("status1").value; */
		//alert(e + '  '+ e1);

		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */if ((e == null || e == '') && (e1 == null || e1 == '')
				/* && (e2 == null || e2 == '') && (e3 == null || e3 == '')
				&& (e4 == null || e4 == '') */) {
														//alert('both $$ ##');
														document.location.href = '${pageContext.request.contextPath}/transactionweb/cashTransactionList/'
															+ pnum;
														form.submit;
													} else {
														//alert("else : "+e+" "+e1);
														document.location.href = '${pageContext.request.contextPath}/transactionweb/pdfdailysearch?fromDate='
															+ e + '&toDate=' + e1 + '&currPage=' + pnum + '&txnType=' + e2;

														//document.forms["myform"].submit();
														form.submit;// = true; 

													}

												}

												function DateCheck() {
													var StartDate = document.getElementById('datepicker').value;
													var EndDate = document.getElementById('datepicker1').value;
													var eDate = new Date(EndDate);
													var sDate = new Date(StartDate);
													if (StartDate != '' && StartDate != '' && sDate > eDate) {
														alert("Please ensure that the End Date is greater than or equal to the Start Date.");
														return false;
													}
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


												function pdf(name) {
													var url = window.location;

													var src = document.getElementById('popOutiFrame').src;
													src = url + 'transactionweb/getdailypdf/' + name;

													var h = 600;
													var w = 1000;
													var title = "Mobiversa Receipt";

													var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
													var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

													var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
													var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

													var left = ((width / 2) - (w / 2)) + dualScreenLeft;
													var top = ((height / 2) - (h / 2)) + dualScreenTop;


													var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);


													if (window.focus) {
														newWindow.focus();
													}

													//   	document.form1.action = "${pageContext.request.contextPath}/transactionweb/getpdf/"+name;
													//   		document.form1.submit();


												}
											</script>


										</head>

										<body>
	<div id="overlay" id="loading-gif">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<div class="container-fluid">
												<div class="row">



													<div class="col s12">
														<div class="card blue-bg text-white">
															<div class="card-content">
																<div class="d-flex align-items-center">
																	<h3 class="text-white">
																		<strong> Daily Report </strong>
																	</h3>
																</div>


															</div>
														</div>
													</div>
												</div>


												<form method="post" name="form1">
													<!-- action="${pageContext.request.contextPath}/<%=MerchantWebTransactionController.URL_BASE%>/search">  onsubmit=" return loadSelectData()">    //onsubmit=" return loadSelectData()" -->
													<input type="hidden" name="${_csrf.parameterName}"
														value="${_csrf.token}" />
													<div class="row">
														<div class="col s12">
															<div class="card border-radius">
																<div class="card-content padding-card">

																	<div class="row">

																		<div class="input-field col s12 m3 l3">
																			<label for="from" style="margin: 0px;">
																				Settlement FromDate </label> <input
																				type="hidden" name="date11" id="date11"
																				<c:out value="${fromDate}" />> <input
																				type="text" id="from" name="fromDate"
																				class="validate datepicker"
																				onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
																			<i
																				class="material-icons prefix">date_range</i>
																		</div>

																		<div class="input-field col s12 m3 l3">
																			<label for="to"
																				style="margin: 0px;">Settlement
																				ToDate</label> <input type="hidden"
																				name="date12" id="date12" <c:out
																				value="${toDate}" />> <input id="to"
																				type="text" name="toDate"
																				class="datepicker"
																				onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
																			<i
																				class="material-icons prefix">date_range</i>
																		</div>

																		<div class="input-field col s12 m3 l3">

																			<select name="txnType" id="txnType"
																				onchange="return loadDropDate14();">
																				<option selected value="">Choose
																				</option>

																				<option value="BOOST">BOOST</option>
																				<option value="GRABPAY">GRABPAY</option>
																				<option value="FPX">FPX</option>
																				<option value="TNG">TNG</option>
																				<option value="SHOPEEPAY">SHOPEE PAY</option>
																				<option value="BNPL">BNPL</option>

																				

																			</select>
																			<label for="name">Payment Method</label>
																			<input type="hidden" name="txnType1"
																				id="txnType1">

																		</div>



																		<div class="input-field col s12 m3 l3">
																			<div class="button-class"
																				style="float: left;">

																				<button class="btn btn-primary blue-btn"
																					type="button"
																					onclick="return loadSelectData();">Search</button>


																			</div>

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
																	formatSubmit: 'dd/mm/yyyy',
																});
																

															</script>
														</div>
													</div>


													<div class="row">
														<div class="col s12">
															<div class="card border-radius">
																<div class="card-content padding-card">


																	<div class="table-responsive m-b-20 m-t-15">



																		<table id="data_list_table"
																			class="table table-striped table-bordered">
																			<thead>
																				<tr>
																					<th>File</th>
																					<th>View</th>




																				</tr>
																			</thead>

																			<tbody>
																				<c:forEach
																					items="${paginationBean.itemListt}"
																					var="dto" varStatus="count">
																					<tr>

																						<td style="font-size:20px;">
																							${paginationBean.filenamelist[count.index]}
																						</td>


																						<td><a href="javascript:void(0)"
																								id="openNewWin"
																								onclick="javascript: pdf('${paginationBean.itemListt[count.index]}')">
																								<img class="w24"
																									src='data:image/png;base64,<%=actionimg%> ' />
																							</a>

																							<div class="form-group col-md-4"
																								id="divviewer"
																								style="display: none;">
																								<div class="form-group">
																									<div
																										style="clear: both">
																										<iframe
																											id="popOutiFrame"
																											frameborder="0"
																											scrolling="no"
																											width="800"
																											height="600"></iframe>

																									</div>

																								</div>
																							</div>
																						</td>
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


											<script>
												$(document).ready(function () {
													// $('#data_list_table').DataTable();
												});

												$(document).ready(function () {
													$('#data_list_table').DataTable({
														"bSort": false

													});
												});

											</script>

										</body>

										</html>