<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>depositsummary</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">

<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

/* body {
	font-family: "Poppins", sans-serif;
} */
.container-fluid {
	padding: 20px 18px !important;
	font-family: "Poppins", sans-serif !important;
}

.select2-dropdown {
	font-family: "Poppins", sans-serif !important;
	z-index: 1;
}

.select2-container--default .select2-search--dropdown .select2-search__field
	{
	font-family: "Poppins", sans-serif !important;
}

.heading_text {
	font-family: "Poppins", sans-serif !important;
	font-weight: 600 !important;
	font-size: 20px !important;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.select2-container--default .select2-selection--single {
	border: none !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

.businessname .select2-container {
	margin: 7px 0 !important;
	border: 1.7px solid #005baa !important;
}

.row .select2-container {
	padding: 4px 10px !important;
	font-size: 14px !important;
	z-index: 1 !important;
}

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 4px;
	right: 10px;
}

.align-right {
	text-align: right !important;
}

.align-center {
	text-align: center;
}

.heading_row {
	border-bottom: 1.5px solid orange !important;
}

input[type=search]:not(.browser-default) {
	border-bottom: 1.5px solid orange !important;
}

input[type=search]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none !important;
}

#data_list_table tr td {
	font-weight: 400;
	font-size: 14px;
	color: #929292;
	padding: 12px;
}

#data_list_table tr th {
	font-weight: 600;
	font-size: 14px;
	padding: 12px;
	white-space: nowrap;
}

.complete_status {
	color: #41b441;
	font-weight: 500;
	margin-left: 5px;
}

.failed_status {
	color: red;
	font-weight: 500;
	margin-left: 5px;
}

.btn_more {
	border: none;
	background-color: transparent;
	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: none !important;
}

.btn_more:hover, .btn_more:focus {
	background-color: transparent;
	!
	important;
}

#more_details {
	/*cursor: pointer;*/
	display: flex;
	align-items: center;
	justify-content: center;
}

/*    modal */
.modal_container {
	display: none;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.more_details_modal_class {
	/* min-width: 400px !important;
             width: fit-content !important;
             max-width: 35% !important;*/
	background-color: #fff !important;
	border-radius: 10px !important;
	width: 80%;
	margin: 0 auto;
}

.modal_row {
	width: 100%;
	height: 100%;
	align-content: center;
}

.modal-header {
	color: #005BAA;
	text-align: center;
	padding: 10px;
	border-bottom: 1.5px solid orange;
	font-weight: 500;
	font-size: 16px;
}

.modal-content {
	padding: 15px 24px;
}

.modal-footer {
	background-color: #EFF8FF;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	padding: 10px 0;
}

.modal-content {
	padding: 10px 30px;
	font-family: "Poppins", sans-serif;
}

.refno_info, .type_info {
	display: flex;
	margin-bottom: 10px !important;
}

.left_content {
	flex: 0.22;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.colon {
	flex: 0.05;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.right_content {
	flex: 1;
	color: #586570;
}

.reason_info {
	color: #005baa;
	margin-bottom: 10px !important;
	font-weight: 500;
}

.withdraw_reason_text::placeholder {
	color: #d0d0d0;
	font-size: 13px;
	padding: 5px;
}

.withdraw_reason_text {
	width: 100%;
	height: 6rem;
	padding: 5px;
	background-color: transparent;
	resize: none;
	border-radius: 5px;
	border: 1px solid #70707070;
	scrollbar-width: thin;
	color: #586570;
	font-family: "Poppins", sans-serif;
}

.closebtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 30px;
	font-size: 12px;
}

.closebtn:hover, .closebtn:focus {
	background-color: #005baa !important;
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
	font-family: 'Poppins', sans-serif;
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
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
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
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
</style>
</head>
<body>

	<div class="test" id="pop-bg-color"></div>
	<div id="overlay-popup"></div>

	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="/MobiversaAdmin/resourcesNew1/assets/loader.gif">
		</div>
	</div>

	<div class="container-fluid mb-0" id="pop-bg">

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white mb-0 ">
								<strong class="heading_text">Deposit Details</strong>

							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="row">


							<%-- merchant  choose--%>
							<div class="col s12 m4 l4 input-field businessname">
								<label for="merchantName" id="label_businessname">Business
									Name</label> <select name="merchantName" id="merchantName"
									path="merchantName"
									<%--                                    onchange="javascript:location.href = this.value;"--%>
                                    class="browser-default select-filter">

									<!-- onclick="javascript: locate();"> -->
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out
												value="Business Name" /></option>


										<c:forEach var="merchant" items="${businessNamesAndIds}">
											<c:if test="${merchant[0] != ''}">
												<option
													value="${pageContext.request.contextPath}/transaction/searchdepositDetailsUsingId?merchantId=${merchant[1]}">${merchant[0]}</option>
											</c:if>
										</c:forEach>

									</optgroup>
								</select>


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<%--   table content --%>

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="">

							<div class="table-responsive m-b-20 m-t-15" id="page-table">
								<table id="data_list_table"
									class="table table-striped table-bordered">
									<thead>
										<tr class="heading_row">
											<th>Date & Time</th>
											<th>Business Name</th>
											<th class="align-right">Previous Balance</th>
											<th class="align-right">Deposit Amount</th>
											<th class="align-right">Available Balance</th>
								<!-- 			<th>Email Status</th> -->
											<th>More Details</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${paginationBean.itemList}" var="dto">
											<tr>
												<td>${dto.timeStamp}</td>

												<td>${dto.merchantName}</td>

												<td style="text-align: right;" class="amount">RM ${dto.previousBalance}</td>
												<td style="text-align: right;" class="amount">RM ${dto.depositAmount}</td>
												<td style="text-align: right;" class="amount">RM ${dto.availableBalance}</td>

												<%-- 	<td><c:if test="${dto.emailStatus == 'Sent'}">
														<div
															style="display: flex; align-items: center; justify-content: flex-start;">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/Successful.svg"
																width="18" height="18" alt=""><span
																class="complete_status">Sent</span>
														</div>
													</c:if> <c:if test="${dto.emailStatus == 'Failed'}">
														<div
															style="display: flex; align-items: center; justify-content: flex-start;">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg"
																width="20" height="20" alt=""><span
																class="failed_status">Failed</span>
														</div>
													</c:if></td> --%>
													
													
												<td style="text-align: center;" id="more_details">
													<button class="btn_more"
														onclick="openMoreDetailsPopup('<c:out value="${dto.referenceNo}"/>','deposited')">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/more.svg"
															width="20" height="20" alt="">
													</button>
												</td>


											</tr>

										</c:forEach>
										<c:if test="${paginationBean.itemList.size() == 0 }">
											<tr id="nodata_row">
												<td colspan="7" style="text-align: center;">
													<div id="no-data">
														<p class="mb-0">No data Available</p>
													</div>
												</td>
											</tr>
										</c:if>

									</tbody>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>

			<!-- modal for more details -->
			<div class="modal_container" id="modalContainer">

				<div class="row modal_row">
					<div class="col offset-l3 offset-m2 s12 m8 l6">
						<div id="more_details_modal" class="more_details_modal_class">
							<div class="modal-header">
								<p class="mb-0">More Details</p>
							</div>
							<div class="modal-content ">
								<div class="align-center">
									<img
										src="${pageContext.request.contextPath}/resourcesNew1/assets/doc_lens.svg"
										width="50" height="50">
								</div>

								<p class="refno_info">
									<span class="left_content">Ref. No.</span> <span class="colon">:</span>
									<span class="right_content" id="referenceNo"></span>
								</p>


								<!-- <p class="reason_info ">
									Deposit Reason<span style="margin-left: 3px;">:</span>
								</p>
								<textarea rows="4" cols="4" id="deposit_reason_text"
									class="withdraw_reason_text"
									placeholder="Show reason from while Deposit" readonly></textarea> -->


							</div>
							<div class=" align-center modal-footer footer">
								<button id="closedeclined" class=" btn blue-btn closebtn"
									type="button" onclick="closeMoredetailsModal()" name="action">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div id="pagination"></div>
			<input type="hidden" id="pgnum"> <input type="hidden"
				id="FromDate"> <input type="hidden" id="ToDate"> <input
				type="hidden" id="merchantId">
				
				<input type="hidden" id="merchant_name" value="${merchantName}">


		</div>




		<script>
		
		$(function(){
			  // bind change event to select
			  $('#merchantName').on('change', function () {
			      var url = $(this).val(); // get selected value
			      //alert(url);
			      if (url) { // require a URL
			          window.location = url; // redirect
			         // alert(window.location);
			      }
			      return false;
			  });
			});
		
		


		$(document).ready(function () {
            $(".select-filter").select2();

            // Get the merchant_name from the hidden input field
            var merchant_name = document.getElementById("merchant_name").value;
            //console.log("merchant name is: ", merchant_name);


            if (merchant_name) {
                //console.log("Setting merchant name: ", merchant_name);

                $('#merchantName option').each(function() {
                    if ($(this).text().trim() === merchant_name.trim()) {
                        $(this).prop('selected', true);
                        // Trigger change to update Select2 display
                        $('#merchantName').val($(this).val()).trigger('change.select2');
                        return false;
                    }
                });

            }

   });


        var more_detail_modal = document.getElementById("modalContainer");
        function openMoreDetailsPopup(referenceNo,depositReason){
            more_detail_modal.style.display = "block";
            document.getElementById('referenceNo').innerText = referenceNo;
           	document.getElementById('deposit_reason_text').innerText = depositReason;

        }

        function closeMoredetailsModal(){
            more_detail_modal.style.display = "none";
        }

        <%--if(${paginationBean.itemList.size()} == 0){--%>

        <%--    document.getElementById("nodata_row").style.display = "table-row";--%>
        <%--    document.getElementById("no-data").innerText = "No data available";--%>
        <%--}--%>
        
        $(document).ready(function() {
            
            function numberWithCommas(x) {
               //console.log("comma seperate")
               return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }

            $('.amount').each(function() {
               var amount = $(this).text();
               var formattedAmount = numberWithCommas(amount);
               $(this).text(formattedAmount);
            });
        });

    </script>


		<script>

        console.log("size ; ",${paginationBean.itemList.size()});

        function loadSelectData2() {

            $("#overlay").show();

            var e = document.getElementById("FromDate").value;
            var e1 = document.getElementById("ToDate").value;
            var currPage = document.getElementById("pgnum").value;
            var merchantId = document.getElementById("merchantId").value;

            console.log(e+" "+e1+" "+currPage+" "+merchantId);
          

            var fromDate = new Date(e); //.toDateString("yyyy-MM-dd");
            var toDate = new Date(e1); //.toDateString("yyyy-MM-dd");

            var fromday = fromDate.getDate();
            var frommon = fromDate.getMonth() + 1;
            var fromyear = fromDate.getFullYear();

            var today = toDate.getDate();
            var tomon = toDate.getMonth() + 1;
            var toyear = toDate.getFullYear();

            var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
            var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;


            if (merchantId !== null && merchantId !== '') { 
    			
    			document.location.href = '${pageContext.request.contextPath}/transaction/searchdepositDetailsUsingId?merchantId='
    				+ merchantId + '&currPage=' + currPage;
    			
    		} else {
    		
    			document.location.href = '${pageContext.request.contextPath}/transaction/depositDetails?date='
    					+ fromdateString + '&date1=' + todateString + '&currPage=' + currPage;

    		}


        }


        /* * * * * * * * * * * * * * * * *
         * Pagination
         * javascript page navigation
         * * * * * * * * * * * * * * * * */
         
         var fromDateServer = document.getElementById("FromDate").value="${fromDate}";
         var from1DateServer = document.getElementById("ToDate").value="${toDate}";
         var merchant_Id = document.getElementById("merchantId").value="${merchantId}";


         var size ="${paginationBean.querySize}";

         console.log("size "+size)


         //Assuming you have a function to add a class to elements

         function addClass(element, className) {

             if (element.classList) {

                 element.classList.add(className);

             } else {

                 element.className += ' ' + className;

             }

         }




             function dynamic(pgNo){

                /* alert("Page Number:"+pgNo); */

                document.getElementById("pgnum").value=pgNo;

                loadSelectData2();

             }

             function previous(pgNo){

                /* alert("Page Number:"+pgNo); */

                pgNo--;

                document.getElementById("pgnum").value=pgNo;

                loadSelectData2();

             }

             function next(pgNo){

                /* alert("Page Number:"+pgNo); */

                pgNo++;

                document.getElementById("pgnum").value=pgNo;

                loadSelectData2();

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

//                 Pagination.size = Math.ceil(${paginationBean.querySize}/10) ||100;

         //set max page number

                 Pagination.size = Math.ceil(${paginationBean.querySize} / 20);

//                 Pagination.size = ((${paginationBean.currPage})+4) ||100;

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

//              Last: function() {

//                 Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';

//              },

         //

//              // add first page with separator

//              First: function() {

//                 if(Pagination.page==1){

         //

//                    Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';

         //

//                 }

//                 else{

//                    Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';

//                 }

//              },


         // newer chnagess

         First: function() {

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

         Last: function() {

             // total page

             var lastPage = Math.ceil(${paginationBean.querySize} / 20);

             // three pg no after 1st pg no

             if (lastPage > Pagination.page + 3) {

             // generate <a> tag for 3 pg no

                 for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {

                     Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';

                 }

                 Pagination.code += '<i>...</i>';

//                  Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';

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

                      '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button

                      '<span></span>',  // pagination container

                      '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button

                   ];


                   e.innerHTML = html.join('');

                   Pagination.e = e.getElementsByTagName('span')[0];

                   Pagination.Buttons(e);

                   if (${paginationBean.currPage} == 1) {

                                 var previousButton = document.getElementById("previous");

                                 previousButton.style.pointerEvents = "none";

                                 previousButton.style.opacity = "0.5";

                             }

                                 // my chnages

                             if (${paginationBean.currPage} == Pagination.size) {

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

                   size: 20, // pages size

                   page: 1,  // selected page

                   step: 3   // pages before and after current

                });

             };

             document.addEventListener('DOMContentLoaded', init, false);

          
    </script>
</body>
</html>