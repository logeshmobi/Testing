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
											 $("#overlay").show();
											var e = document.getElementById("from").value;
											var e1 = document.getElementById("to").value;

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


											//Date Validation - Start	
											var fromdate = fromyear + '-' + (frommon <= 9 ? '0' + frommon : frommon) + '-' + (fromday <= 9 ? '0' + fromday : fromday);
											var todate = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon) + '-' + (today <= 9 ? '0' + today : today);


											var Checkdate = "2022-03-14";
											var from = fromdate;
											var to = todate;


											if (e == null || e == '' || e1 == null || e1 == '') {
												alert("Please Select date(s)");

											} else
												if (new Date(from) <= new Date(Checkdate) && new Date(to) <= new Date(Checkdate)) {

													alert("PDF Available only From March 15 2022")
													return false;
												}

												else if (new Date(from) <= new Date(Checkdate)) {

													alert("PDF Available only From March 15 2022")
													return false;
												} else
													if (new Date(to) <= new Date(Checkdate)) {

														alert("PDF Available only From March 15 2022")
														return false;
													}



													else {

														document.location.href = '${pageContext.request.contextPath}/DailyReport/SearchCardPDF?fromDate='
															+ fromdateString + '&toDate=' + todateString;

														form.submit;

													}

										}

										function pdf(name) {
											var url = window.location;

											var src = document.getElementById('popOutiFrame').src;
											src = url + 'DailyReport/ViewCardPDF/' + name;

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

										}

									</script>


								</head>

								<body>
								<div id="overlay" id="loading-gif">
										<div id="overlay_text"><img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif"></div>
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



										<div class="row">
											<div class="col s12">
												<div class="card border-radius">
													<div class="card-content padding-card">

														<div class="row">

															<div class="input-field col s12 m3 l3">
																<label for="from" style="margin: 0px;">Transaction
																	FromDate
																</label> <input type="hidden" name="date11" id="date11"
																	<c:out value="${fromDate}" />> <input type="text"
																	id="from" name="fromDate"
																	class="validate datepicker"
																	onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
																<i class="material-icons prefix">date_range</i>


															</div>

															<div class="input-field col s12 m3 l3">

																<label for="to" style="margin: 0px;">Transaction ToDate
																</label>
																<input type="hidden" name="date12" id="date12" <c:out
																	value="${toDate}" />> <input id="to" type="text"
																	name="toDate" class="datepicker"
																	onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
																<i class="material-icons prefix">date_range</i>
															</div>




															<div class="input-field col s12 m3 l3">
																<div class="button-class" style="float: left;">

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
																	<c:forEach items="${paginationBean.nameList}"
																		var="dto" varStatus="count">
																		<tr>

																			<td style="font-size: 20px;">
																				${paginationBean.dateList[count.index]}
																			</td>


																			<td><a href="javascript:void(0)"
																					id="openNewWin"
																					onclick="javascript: pdf('${paginationBean.nameList[count.index]}')">
																					<img class="w24"
																						src='data:image/png;base64,<%=actionimg%> ' />
																				</a>

																				<div class="form-group col-md-4"
																					id="divviewer"
																					style="display: none;">
																					<div class="form-group">
																						<div style="clear: both">
																							<iframe id="popOutiFrame"
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




									</div>


									<script>


										$(document).ready(function () {
											$('#data_list_table').DataTable({
												"bSort": false

											});
										});

									</script>

								</body>

								</html>