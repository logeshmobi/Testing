<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%@page import="com.mobiversa.payment.controller.TransactionController" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<head>
  <meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

  .container-fluid {
    font-family: "Poppins", sans-serif !important;
  }
</style>
<style>
  .container-fluid {
    font-family: 'Poppins';
  }

  .center-text {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    margin: 20px 0;
    /* Adjust margin as needed */
    line-height: 1.2px;
  }

  .poppins_font {
    font-family: "Poppins", sans-serif !important;
  }

  .page-link {
    font-weight: 500;
    /*font-family: "Poppins", sans-serif !important;*/
  }

  .select2-container--default .select2-selection--single .select2-selection__rendered {
    color: #A7A7A7;
     !important;
  }

  /* Custom CSS for pagination buttons */
  .pagination {
    display: flex;
    justify-content: end;
    align-items: center;
    margin-top: 20px;
    font-family: "Poppins", sans-serif !important;
    font-weight: 500 !important;
  }

  .pagination button,
  .pagination span {
    background-color: #fff;
    color: #005baa;
    border: 0px solid #ddd;
    border-radius: 50%;
    padding: 5px 10px;
    margin: 0px 0px;
    cursor: pointer;
    font-family: "Poppins", sans-serif !important;
  }

  .pagination button.active,
  .pagination span.active {
    background-color: #005baa;
    color: white;
    border: 1px solid #005baa;
    font-weight: 500;
    font-family: "Poppins", sans-serif !important;
  }

  .pagination .prev {
    background-color: #fff;
    border-radius: 0;
    padding: 5px 10px !important;
    margin: 0px 5px;
    color: #005baa;
    font-family: "Poppins", sans-serif !important;
  }

  .pagination .next {
    background-color: #fff;
    border-radius: 0;
    /*padding: 5px 10px !important;*/
    padding: 8px 10px 5px 10px !important;
    margin: 0px 5px;
    color: #005baa;
    font-family: "Poppins", sans-serif !important;
  }

  .next,
  .prev {
    font-size: 14px !important;
  }

  /*for prev */
  button.prev {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }

  button.prev.disabled {
    color: #a9a9a9;
    cursor: not-allowed;
  }

  .

  /*for next */
  button.next {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }

  button.next.disabled {
    color: #a9a9a9;
    cursor: not-allowed;
  }

  button.next.disabled .symbol {
    color: #a9a9a9;
  }

  .left-arrow {
    display: inline-block;
    width: 24px;
    /* Adjust size as needed */
    height: 24px;
    /* Adjust size as needed */
    background-image: url('../resourcesNew1/assets/Chevron-backward-blue.svg');
    background-size: contain;
    background-repeat: no-repeat;
    vertical-align: middle;
    /* Align with text */
    /*margin-right: 5px; !* Space between icon and text *!*/
    margin: 0px 6px 3px 1px !important;
    /*margin-bottom: -1px !important;*/
  }

  .right-arrow {
    display: inline-block;
    width: 24px;
    /* Adjust size as needed */
    height: 24px;
    /* Adjust size as needed */
    background-image: url('../resourcesNew1/assets/Chevron-forward-blue.svg');
    background-size: contain;
    background-repeat: no-repeat;
    vertical-align: middle;
    /* Align with text */
    /*margin-right: 5px;*/
    margin: 0px 1px 3px 6px !important;
    /*margin-bottom: -1px !important;*/
  }

  button.prev.disabled .left-arrow {
    display: inline-block;
    width: 24px;
    /* Adjust size as needed */
    height: 24px;
    /* Adjust size as needed */
    background-image: url('../resourcesNew1/assets/Chevron-backward-grey.svg');
    background-size: contain;
    background-repeat: no-repeat;
    vertical-align: middle;
    /* Align with text */
    vertical-alignddle: middle;
    /* Align with text */
    margin-right: 5px;
    /* Space between icon and text */
    cursor: not-allowed;
  }

  button.next.disabled .right-arrow {
    display: inline-block;
    width: 24px;
    /* Adjust size as needed */
    height: 24px;
    /* Adjust size as needed */
    background-image: url('../resourcesNew1/assets/Chevron-forward-grey.svg');
    background-size: contain;
    background-repeat: no-repeat;
    vertical-align: middle;
    /* Align with text */
    margin-right: 5px;
    /* Space between icon and text */
    cursor: not-allowed;
  }

  .pagination {
    font-family: "Poppins", sans-serif !important;
  }

  .select-wrapper input.select-dropdown {
    color: #928979;
    font-family: "Poppins", sans-serif !important;
  }

  .text-white strong {
    font-family: "Poppins", sans-serif !important;
    font-weight: 600 !important;
  }

  td,
  th {
    padding: 7px 8px;
    color: #707070;
  }

  thead th {
    border-bottom: 1px solid #ffa500;
    color: #4377a2;
  }

  #reviewBtn {
    color: white;
    border-radius: 20px;
    background: #005baa;
    display: flex;
    flex-direction: row;
    padding: 6px 8.3px 6px 19px;
    box-sizing: border-box;
    height: 30px;
    width: 68px;
    border: none
  }

  #data_list_table th {
    padding: 15px 13px;
    text-align: left;
    white-space: nowrap;
  }

  #data_list_table td {
    padding: 5px 13px;
    white-space: nowrap;
  }

  .text-right {
    text-align: right !important;
  }

  @media only screen and (min-width: 993px) {
    .row .col.offset-l1 {
      margin-left: 5.33333%;
    }
  }

  .more_info_details {
    color: #858585;
  }
</style>

<script type="text/javascript">
  history.pushState(null, null, "");
  window.addEventListener('popstate', function() {
    history.pushState(null, null, "");
  });

  function loadDropSearch() {
    var e = document.getElementById("drop_search");
    var strUser = e.options[e.selectedIndex].value;
    document.getElementById("drop_val").value = strUser;
    /* For Dynamic Placeholder in SEARCH */
    if (strUser == "Transaction ID") {
      document.getElementsByName('search')[0].placeholder = 'Enter Transaction ID';
    } else if (strUser == "Payout ID") {
      document.getElementsByName('search')[0].placeholder = 'Enter Payout ID';
    } else if (strUser == "") {
      document.getElementsByName('search')[0].placeholder = 'Please select type to search ';
    }
  }

  function loadSearch() {
    $("#overlay").show();
    var Value = document.getElementById("searchApi").value;
    var type = document.getElementById("drop_val").value;
    
    if (Value.trim() === '' || type.trim() === '') {
        alert("Please choose a value before submitting");
        $("#overlay").hide();
        return;
    }
    
    if (Value.trim() === '' || type.trim() === '' || Value.trim().length < 3) {
        alert("Please choose a value with at least 3 characters before submitting");
        $("#overlay").hide();
        return;
    }
    
    document.location.href = '${pageContext.request.contextPath}/transaction/payout/transactions/search/exceeded-limit?searchValue=' + Value + '&searchType=' + type;
    form.submit;
  }
</script>

<body>
  <div class="test" id="pop-bg-color"></div>
  <div id="overlay-popup"></div>
  <div id="overlay">
    <div id="overlay_text">
      <img class="img-fluid" src="/MobiversaAdmin/resourcesNew1/assets/loader.gif">
    </div>
  </div>
  <div class="container-fluid">
    <div class="row">
      <div class="col s12">
        <div class="card blue-bg text-white">
          <div class="card-content">
            <div class="d-flex align-items-center">
              <h3 class="text-white" style="margin:4px 8px; font-family: Poppins, sans-serif !important; ">
                <strong>Payout Transaction Approval </strong>
              </h3>
            </div>
          </div>
        </div>
      </div>
    </div><%--    search filter --%> <div class="row" id="searchBoxDiv">
      <div class="col s12">
        <div class="card blue-bg text-white">
          <div class="card-content">
            <!-- SEARCH TEST -->
            <div class="row" style="display:flex;align-items:center;justify-content:space-between;">
              <div class="col s12" style="padding: 0 8px !important;">
                <div class="input-field col s12 m4 l3" style="font-family:'Poppins',sans-serif;">
                  <select name="drop_search" id="drop_search" onchange="return loadDropSearch();">
                    <option selected value="" id="choose">Choose Type</option>
                    <option value="Transaction ID">Transaction ID</option>
                    <option value="Payout ID">Payout ID</option>
                  </select>
                  <input type="hidden" id="drop_val">
                </div>
                <div class="input-field col s12 offset-l1 offset-m1 m4 l3">
                  <input type="text" id="searchApi" name="search" class="" style="font-family:'Poppins',sans-serif;" placeholder="Please select type to search">
                </div>
                <div class="input-field col offset-12 s12 m2 l2">
                  <div class="button-class" style="padding-top: 10px !important; float: right">
                    <button class="btn btn-primary blue-btn" type="button" onclick="loadSearch()" style="font-family:'Poppins',sans-serif;font-size:14px;">Search</button>
                  </div>
                </div>
              </div>
            </div>
            <!--  SEARCH TEST ENDS -->
          </div>
        </div>
      </div>
    </div>
    
    <style>
      .blue-btn:hover,
      #approve:hover,
      #reject:hover {
        box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2),
          /* Right shadow */
          -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
          /* Left shadow */
          0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
      }
    </style>
    
    <div id="pagination"></div>
    <input type="hidden" id="pgnum">
    <div class="row">
      <div class="col s12">
        <div class="card border-radius">
          <div class="">
            <div class="table-responsive m-b-20 m-t-15">
              <table id="data_list_table">
                <thead>
                  <tr>
                    <th></th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Merchant Name</th>
                    <th>Payout ID</th>
                    <th>Transaction ID</th>
                    
                    <th class="text-right">Amount(RM)</th>
                    <th style="text-align: center">More Info</th>
                    <th>Action</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <c:if test="${not empty paginationBean.itemList}">
                    <c:forEach var="enquiry" items="${paginationBean.itemList}">
                      <tr>
                        <td></td>
                        <td>${enquiry.date}</td>
                        <td>${enquiry.time}</td>
                        <td>${enquiry.merchantName}</td>
                        <td>${enquiry.payoutId}</td>
                        <td>${enquiry.invoiceId}</td>
                        
                        <td class="text-right">${enquiry.amount}</td>
                        <td style="text-align: center;">
                          <img style="cursor: pointer;" onclick="openInfo('${enquiry.nameInBank}', '${enquiry.payoutId}', '${enquiry.name}', '${enquiry.accountNumber}', '${enquiry.amount}', '${enquiry.invoiceId}')" src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" />
                        </td>
                        <td style="display: flex;align-items: center;justify-content: flex-start">
                          <div style="cursor: pointer " class="acceptClass">
                            <img onclick="openApprove('${enquiry.invoiceId}')" src="${pageContext.request.contextPath}/resourcesNew1/assets/Btn_green_shadow.svg" style="margin-left: -5px" />
                          </div>
                          <div style="margin-left: 16px;cursor:pointer;" class="rejectTh">
                            <img onclick="openReject('${enquiry.invoiceId}')" src="${pageContext.request.contextPath}/resourcesNew1/assets/Btn.svg" />
                          </div>
                        </td>
                        <td></td>
                      </tr>
                    </c:forEach>
                  </c:if>
                  <c:if test="${empty paginationBean.itemList}">
                    <tr>
                      <td colspan="10" style="text-align: center;">No Records Found</td>
                    </tr>
                  </c:if>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="card border-radius">
          <div class="pagination" style="padding-bottom: 10px !important;padding-top: 10px !important;"></div>
        </div>
      </div>
    </div><%--action pop up --%> <div class="more_info modal-container" id="modalContainerCard">
      <div id="moredetails" class="moredetails-modal-class">
        <div class="outerborder" style="padding: 0;">
          <div class="modal-header header_txndetails">
            <div class="color-blue " style="font-size: 16px; font-weight: 600;"> More Info <button id="closecardmore_xmark" onclick="closeCard()" style="float: right; margin: 2px; background-color: transparent; border: none;">
                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg" width="20" height="20" class="cursor-pointer " alt="">
              </button>
            </div>
          </div>
          <div class="message" id="messageId">
            <p style="margin: 0"></p>
          </div>
          <div class="modal-content content-moredetails">
            <div class="color-blue" style="font-size: 16px; font-weight: 500; margin: 10px 0;padding: 0px 8px">Transaction Details </div>
            <div class="txn-table">
              <div class="transaction-details">
                <input type="hidden" id="info-invoiceId">
                <table class="info_table">
                  <tbody>
                    <tr>
                      <td class="label info_label">Customer Name</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-customerName">Muhamad Shafaad Zakaria</td>
                    </tr>
<!--                     <tr>
                      <td class="label">Name in bank Ac</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-nameInBank"></td>
                    </tr> -->
                    <tr>
                      <td class="label">Payout ID</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-payoutId">192929292</td>
                    </tr>
                    <tr>
                      <td class="label">Account No</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-accountNumber">0987654321</td>
                    </tr>
                    <tr>
                      <td class="label">Amount</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-amount">MYR 500</td>
                    </tr>
                    <tr>
                      <td class="label">Pending Reason</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-pendingReason">Transaction exceeds maximum payout limit</td>
                    </tr>
                    <tr>
                      <td class="label">Transaction ID</td>
                      <td class="separator">-</td>
                      <td class="value more_info_details" id="info-invoiceId-display">Invoice Id</td>
                    </tr>
                    <tr></tr>
                  </tbody>
                </table>
              </div>
              <div style="margin: 15px 0; padding: 3px 10px;">
                <p class="comment-box" style="color: #6d6868; font-weight: 500; margin-bottom: 5px;">Comment Box <span class="required-text">(Required for Rejection)</span>
                </p>
                <textarea placeholder="Enter Your comments here... " id="commentsTextarea" required style="width: 60%; min-height: 6rem; background-color: transparent; font-family: 'Poppins';
                                       color: #858585; resize: none; border-color: #ADADAD; padding: 10px ;
                                      border-radius: 6px; margin-top: 4px; height: auto; content:'';"></textarea>
                <p id="error_message" style="color: red;font-size: 12px;"></p>
              </div>
            </div>
          </div>
        </div>
        <div class=" align-center modal-footer footer  moredetails_footer" style="gap: 18px">
          <button id="reject" style="color: red !important;background-color: transparent !important;border-style: solid;border-width: 1px !important;font-family: 'Poppins' !important;" class="btn waves-effect waves-light blue-btn closebtn" type="button" onclick="more_info_reject(document.getElementById('info-invoiceId').value)" name="action">Reject </button>
          <button id="approve" class="btn waves-effect waves-light blue-btn closebtn" style="font-family: 'Poppins' !important;" type="button" onclick="this.disabled = true; more_info_approve(document.getElementById('info-invoiceId').value)" name="action">Approve </button>
        </div>
      </div>
    </div>
    
    <style>
      #commentsTextarea::placeholder {
        color: #ecebeb;
      }

      .info_table tr {
        border: none;
      }

      .info_table tr td {
        white-space: nowrap;
      }

      .separator {
        font-weight: 500;
      }

      .label {
        /*display: inline-block;*/
        /*width: 150px; !* Adjust width to your preference *!*/
        text-align: left;
        font-weight: 500;
        color: #6d6868;
        font-size: 14px;
        /*padding: 3px 10px 3px 0px;*/
        white-space: nowrap;
        width: 25%;
        padding: 0 10px;
      }

      .value {
        margin-left: 10px;
        /* Adjust space between dash and value */
        white-space: nowrap;
      }
    </style>
    <script>
      function openApprove(invoiceId) {
        document.getElementById("modalContainerCard").style.display = "none";
        document.getElementById("confirmation-modal-id").style.display = "block";
        document.getElementById("invoice-for-approve").value = invoiceId;
      }

      function openReject(invoiceId) {
        document.getElementById("modalContainerCard").style.display = "none";
        document.getElementById("reject-modal-id-confirmation").style.display = "block";
        document.getElementById("commentsTextareaforReject").value = '';
        document.getElementById("invoice-for-rejecting-the-Payout").value = invoiceId;
      }

      function closeCard() {
        document.getElementById("modalContainerCard").style.display = "none";
      }
      
      function more_info_approve(invoiceId) {
        document.getElementById("modalContainerCard").style.display = "none";
        //  document.getElementById("approve-modal-id").style.display = "block";
        $("#overlay").show();
        // document.getElementById("dynamic_message").innerText = "The Transaction has been aprroved successfully";
        var invoiceID = document.getElementById("info-invoiceId").value;
        var encodedInvoiceID = btoa(invoiceID);
        document.location.href = '${pageContext.request.contextPath}/transaction/payout/exceeded-limit/transactionApproval?invoiceID=' + encodedInvoiceID;
      }

      function more_info_reject(invoiceID) {
        var comment = document.getElementById('commentsTextarea').value.trim();
        var error_message = document.getElementById('error_message');
        console.log('comment : ', comment);
        // Check if the comment box is empty
        if (comment === "") {
          //  alert("Please provide a reason for rejection.");
          error_message.innerText = 'Please provide a reason for rejection';
          error_message.style.display = 'block';
          setTimeout(() => {
            error_message.style.display = 'none';
          }, 3000);
          return;
        }
        document.getElementById("reject").diable = true;
        document.getElementById("modalContainerCard").style.display = "none";
        $("#overlay").show();
        //   document.getElementById("reject-modal-id").style.display = "block";
        // document.getElementById("dynamic_message").innerText = "The Transaction has been rejected";
        console.log('invoice id : ', invoiceID);
        console.log('comment : ', comment);
        var encodedInvoiceID = btoa(invoiceID);
        document.location.href = '${pageContext.request.contextPath}/transaction/payout/exceeded-limit/transactionReject?invoiceID=' + encodedInvoiceID + '&reason=' + comment;
      }

      function closeApprove() {
        document.getElementById("approve-modal-id").style.display = "none";
      }

      function closeReject() {
        document.getElementById("reject-modal-id").style.display = "none";
      }

      function close_confirmation() {
        document.getElementById("reject-modal-id-confirmation").style.display = "none";
      }

      function close_aproval() {
        document.getElementById("confirmation-modal-id").style.display = "none";
      }

      function approvePayout(invoiceId) {
        document.getElementById("confirmation-modal-id").style.display = "none";
        // document.getElementById("approve-modal-id").style.display = "block";
        $("#overlay").show();
        // document.getElementById("dynamic_message").innerText = "The Transaction has been aprroved successfully";
        var encodedInvoiceID = btoa(invoiceId);
        document.location.href = '${pageContext.request.contextPath}/transaction/payout/exceeded-limit/transactionApproval?invoiceID=' + encodedInvoiceID;
      }

      function rejectPayout(invoiceID) {
        var comment = document.getElementById('commentsTextareaforReject').value.trim();
        var error_message_reject_confirm = document.getElementById('error_message_reject_confirm');
        if (comment === "") {
          error_message_reject_confirm.innerText = 'Please provide a reason for rejection';
          error_message_reject_confirm.style.display = 'block';
          setTimeout(() => {
            error_message_reject_confirm.style.display = 'none';
          }, 3000);
          // alert("please provide a reason to reject");
          return;
        }
        console.log('invoice id for rejection ', invoiceID);
        document.getElementById("reject-modal-id-confirmation").style.display = "none";
        //document.getElementById("reject-modal-id").style.display = "block";
        $("#overlay").show();
        // document.getElementById("dynamic_message").innerText = "The Transaction has been rejected";
        var encodedInvoiceID = btoa(invoiceID);
        document.location.href = '${pageContext.request.contextPath}/transaction/payout/exceeded-limit/transactionReject?invoiceID=' + encodedInvoiceID + '&reason=' + comment;
      }
    </script>
    
    <div id="approve-modal-id" class="approve-modal-class">
      <div class="approve-modal-content">
        <div>
          <div style="border-bottom: 2.5px solid orange; padding: 10px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 600"> Notification <%--
																	
															<span style="float: right">X</span>--%> </div>
          <div class="reason-div" style="padding: 15px 30px 0px 30px; text-align: center;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Done 2.svg" width="40" height="40" />
            <p id="reason-id" class="reason-class"></p>
          </div>
          <div class="center-text" style="font-size: 18px;font-weight: 400; color: #858585">
            <p>The Transaction has been successfully <br>
            </p>
            <p>approved</p>
          </div>
          <div class="approve-button" style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
            <button type="button" class="close-Approve" id="closeApprove-id" style="font-family: 'Poppins'; font-weight: 400" onclick="closeApprove()">Close </button>
          </div>
        </div>
      </div>
    </div>
    
    <div id="rare-scenario-model" class="approve-modal-class">
      <div class="approve-modal-content">
        <div>
          <div style="border-bottom: 2.5px solid orange; padding: 10px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 600"> Notification <%--
																		
																<span style="float: right">X</span>--%> </div>
          <div class="reason-div" style="padding: 15px 30px 0px 30px; text-align: center;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Cancel 2.svg" width="40" height="40" /><%--                    
																		
																<p id="reason-id" class="reason-class"></p>--%>
          </div>
          <div class="center-text" style="font-size: 18px;font-weight: 400; color: #858585">
            <p>This Transaction has already been ${rareScenarioAction} <br>
            </p>
          </div>
          <div class="approve-button" style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
            <button type="button" class="close-Approve" id="closeRare-id" style="font-family: 'Poppins'; font-weight: 400" onclick="closeRare()">Close </button>
          </div>
        </div>
      </div>
    </div>
    
    <div id="reject-modal-id" class="approve-modal-class">
      <div class="approve-modal-content">
        <div>
          <div style="border-bottom: 1px solid orange; padding: 10px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500"> Notification <%--
																			
																	<span style="float: right">X</span>--%> </div>
          <div class="reason-div" style="padding: 10px 30px 0px 30px; text-align: center;margin-top: 12px">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Cancel 2.svg" width="40" height="40" />
          </div>
          <div class="center-text" style="font-size: 18px;font-weight: 400; color: #858585">
            <p id="message">The transaction has been rejected</p>
          </div>
          <div class="approve-button" style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
            <button type="button" class="close-Approve" id="closeReject" style="font-family: 'Poppins'; font-weight: 400" onclick="closeReject()">Close </button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="approve-modal-class" id="confirmation-modal-id">
      <input type="hidden" id="invoice-for-approve">
      <div class="approve-modal-content">
        <div>
          <div style="border-bottom: 2.5px solid orange; padding: 10px 24px; text-align: center; font-size: 17px; color: #005baa; font-weight: 600"> Confirmation </div>
          <div class="reason-div" style="padding: 15px 30px 0px 30px; text-align: center;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Done 2.svg" width="40" height="40" /><%--                    
																				
																		<p id="reason-id" class="reason-class"></p>--%>
          </div>
          <div style="padding: 0 30px; text-align: center;">
            <p style="
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    margin-bottom: 30px; font-size: 18px; color: #858585">Are you sure want to approve this transaction ?</p>
          </div>
          <div class="buttonsHorizontally"></div>
          <div class="approve-button" style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
            <button type="button" class="close-Approve" id="approval_confirmation" onclick="close_aproval()" style="background-color: transparent !important; font-family: 'Poppins';color: #005baa !important;border-style: solid !important; font-weight: 400;border-width: 1px !important;"> Cancel </button>
            <button type="button" class="close-Approve" id="approval_cancel" style="font-family: 'Poppins'; font-weight: 400" onclick="this.disabled = true; approvePayout(document.getElementById('invoice-for-approve').value);"> Confirm </button>
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="approve-modal-class" id="reject-modal-id-confirmation">
      <input type="hidden" id="invoice-id-for-reject">
      <div class="approve-modal-content" style="margin-top: 80px">
        <div>
          <div style="border-bottom: 2.5px solid orange; padding: 12px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 600"> Confirmation </div>
          <div class="reason-div" style="padding: 15px 30px 0px 30px; text-align: center;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Cancel 2.svg" width="40" height="40" />
          </div>
          <div class="center-text" style="font-size: 18px; color: #858585; margin:20px 0 5px 0">
            <p>Please provide a reason for rejecting <br>
            </p>
            <p>this transaction</p>
          </div>
          <input type="hidden" id="invoice-for-rejecting-the-Payout">
          <div style="margin-bottom: 3%; text-align: center">
            <div>
              <textarea placeholder="Enter the reason for rejection..." id="commentsTextareaforReject" required style="width: 85%; font-family: 'Poppins';padding: 10px; min-height: 6rem; background-color: transparent; color: #586570; resize: none; border-color: #ADADAD; border-radius: 6px; margin-top: 4px; height: auto; font-size: 14px;"></textarea>
              <p id="error_message_reject_confirm" style="color: red;font-size: 12px;"></p>
            </div>
          </div>
          <div class="approve-button" style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
            <button type="button" class="close-Approve" id="reject_confirmation" onclick="close_confirmation()" style="background-color: transparent !important; font-family: 'Poppins';font-weight: 400; color: #005baa !important;border-style: solid !important;border-width: 1px !important; font-size: 13px;"> cancel </button>
            <button type="button" class="close-Approve" id="reject_cancel" style="font-size: 13px;font-family: 'Poppins';font-weight: 400;" onclick="rejectPayout(document.getElementById('invoice-for-rejecting-the-Payout').value)">confirm </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <style>
    .required-text {
      font-size: 12px;
      color: red;
      font-weight: 400;
    }

    .btnbtn {
      padding: 0 !important;
      color: #2ECC71 !important;
      background-color: white !important;
      border-style: solid;
      border-width: 1px !important;
      display: flex;
      align-items: center;
      justify-content: space-between;
      transition: background-color 0.3s ease;
    }

    .btnbtn:hover {
      background-color: #CEFFCB !important;
    }

    #data_list_table th {
      font-weight: 600;
      /* Semi-bold */
    }

    #commentsTextareaforReject::placeholder {
      color: #ADADAD;
      /* Color of the placeholder text */
      opacity: 1;
      /* Ensures the color is not faded */
    }

    .acceptClass :hover {
      border-color: green !important;
      box-shadow: 0 0 10px green !important;
      border-radius: 50%;
    }

    .rejectTh :hover {
      border-color: red !important;
      box-shadow: 0 0 10px red !important;
      border-radius: 50%;
    }

    .approve-modal-class {
      display: none;
      position: fixed;
      z-index: 999;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: hidden;
      background-color: rgba(0, 0, 0, 0.4);
    }

    .approve-modal-content {
      background-color: #fefefe;
      margin: 15% auto;
      border: 1px solid #888;
      width: 92%;
      max-width: 460px;
      border-radius: 15px;
      height: auto;
    }

    .approve-modal-content {
      position: relative;
    }

    .yellow-line-approve {
      background-color: #f0c207;
      height: 0.9px;
      position: absolute;
      top: 51px;
      width: calc(100% - 1px);
      left: 1px;
    }

    .approve-reason-head {
      color: #005baa;
      font-size: 18px;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .close-Approve {
      background-color: #005baa;
      color: white;
      border-radius: 25px;
      border: none;
      /*padding: 10px 27px;*/
      padding: 10px 15px;
      font-size: 12px;
      height: 35px;
      outline: none;
      cursor: pointer;
    }

    .close-Approve:hover,
    .close-Approve:focus,
    .close-Approve:active {
      background-color: #005baa;
      /* Same color as default */
      box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2),
        /* Right shadow */
        -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
        /* Left shadow */
        0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
    }
  </style>
  <style>
    .transaction-details p {
      font-family: "Poppins", sans-serif !important;
      margin: 0;
      padding: 5px 0;
      font-size: 14px;
      color: #333333;
    }

    .row .col {
      padding: 0 1.2rem !important;
    }

    .input-field label {
      left: 1.2rem !important;
    }

    .moredetails-open {
      overflow-y: scroll !important;
    }

    .align-center {
      text-align: center !important;
    }

    .align-right {
      text-align: right !important;
    }

    .box-shadow-none {
      box-shadow: none !important;
    }

    .shadow-sm {
      box-shadow: 0px 5px 16px 2px #f3f2f2 !important;
    }

    .color-blue {
      color: #005baa;
    }

    .color-green {
      color: #0C9F02;
    }

    .color_lightgreen {
      color: #45da3b;
    }

    .color-red {
      color: red;
    }

    .color-orange {
      color: orange;
    }

    .color-skyblue {
      color: #49CCF9;
    }

    .color-grey {
      color: #858585;
    }

    .fw-600 {
      font-weight: 600;
    }

    .fw-500 {
      font-weight: 500;
    }

    .ml-1 {
      margin-left: 4px !important;
    }

    .m-0 {
      margin: 0;
    }

    .cursor-pointer {
      cursor: pointer;
    }

    .radius-10 {
      border-radius: 10px !important;
    }

    .font-size-md {
      font-size: 1.4rem;
    }

    .input-field>label {
      font-size: 1.3rem !important;
    }

    .mt-0 {
      margin-top: 0 !important;
    }

    .mb-0 {
      margin-bottom: 0 !important;
    }

    .mb-1 {
      margin-bottom: 4px !important;
    }

    .mt-2 {
      margin-top: 2rem !important;
    }

    .mt-1 {
      margin-top: 1rem !important;
    }

    input[type=text]:not(.browser-default) {
      margin: 8px 0 8px 0 !important;
      font-size: 15px !important;
      border-bottom: 1.5px solid #F5A623 !important;
    }

    .select-wrapper input.select-dropdown {
      border-bottom: 1.5px solid #F5A623 !important;
      font-family: "Poppins", sans-serif !important;
    }

    .blue-btn-for-Approve-green {
      background-color: white !important;
      border-radius: 50px !important;
      text-transform: capitalize !important;
      font-size: 0.9rem !important;
      padding: 0 20px !important;
    }

    .blue-btn {
      background-color: #005baa !important;
      border-radius: 50px !important;
      text-transform: capitalize !important;
      font-size: 0.9rem !important;
      padding: 0 20px !important;
    }

    .select-wrapper .caret {
      fill: #005baa !important;
    }

    input[type=text]:not(.browser-default):focus:not([readonly]) {
      box-shadow: none !important;
    }

    input[type=text]:not(.browser-default):focus:not([readonly])+label {
      color: #929292 !important;
    }

    .dropdown-content li>span {
      color: #929292 !important;
    }

    .dropdown-content li:hover {
      background-color: #005baa !important;
    }

    .dropdown-content li:hover span {
      color: #fff !important;
    }

    .dropdown-content {
      top: 10px !important;
    }

    .scrollable-table {
      overflow-x: auto;
      text-wrap: nowrap;
      scrollbar-width: thin;
    }

    .summary_table th {
      color: #005baa;
      font-weight: 600 !important;
      border-bottom: 2px solid #F5A623;
      font-size: 13px !important;
      padding: 10px 25px;
    }

    .summary_table td {
      color: #929292;
      font-weight: 400 !important;
      /* border-bottom: 1.5px solid #ddd ; */
      font-size: 13px !important;
      padding: 10px 25px;
    }

    /* modal style */
    .modal-overlay {
      opacity: 0.3 !important;
    }

    .modal-header {
      padding: 10px 6px;
      height: auto;
      width: 100%;
      text-align: center;
      border-bottom: 1.5px solid #F5A623;
      font-size: 17px;
    }

    .footer {
      background-color: #EFF8FF !important;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .closebtn {
      height: 32px !important;
      line-height: 32px !important;
      min-width: 15%;
      width: fit-content;
      font-size: 12px !important;
    }

    .content-declinedreason {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 7px 30px !important;
    }

    .content-moredetails {
      padding: 7px 24px !important;
    }

    .txn-table {
      /*height: 65vh;*/
      overflow-y: auto;
      scrollbar-width: none;
    }

    .content-moredetails table thead tr th {
      padding: 10px 0px;
      font-size: 18px;
    }

    .content-moredetails table thead tr {
      border-bottom: none !important;
    }

    .header_txndetails {
      padding: 10px 6px 10px 6px;
      border-bottom: 1.5px solid #F5A623;
    }

    .header_paymentslip {
      padding: 12px 6px 12px 6px;
      border-bottom: 2px solid #F5A623;
    }

    /* more details modal   */
    .modal-container {
      display: none;
      position: fixed;
      z-index: 10;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgb(0, 0, 0);
      background-color: rgba(0, 0, 0, 0.4);
      scrollbar-width: none;
    }

    .moredetails-modal-class {
      min-width: 55%;
      width: fit-content;
      max-width: 65%;
      background-color: #fff;
      border-radius: 10px !important;
      margin: 3% auto;
    }

    .moredetails_footer,
    .footer_slip,
    .footer_reason {
      border-radius: 0 0 10px 10px;
      background-color: #fafafa;
      padding: 4px 6px;
      height: 56px;
      width: 100%;
      text-align: right;
    }

    /* slip modal */
    .modal-container_slip {
      display: none;
      position: fixed;
      z-index: 10;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgb(0, 0, 0);
      background-color: rgba(0, 0, 0, 0.4);
      scrollbar-width: none;
    }

    .viewreceipt-modal-class {
      min-width: 360px !important;
      width: fit-content !important;
      max-width: 33% !important;
      background-color: #fff !important;
      border-radius: 10px !important;
      margin: 3% auto;
    }

    /* decline reason modal */
    .modal_container_declinereason {
      display: none;
      position: fixed;
      z-index: 10;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      overflow: auto;
      background-color: rgb(0, 0, 0);
      background-color: rgba(0, 0, 0, 0.4);
      scrollbar-width: none;
    }

    .decline-reason-modal-class {
      min-width: 407px !important;
      width: fit-content !important;
      max-width: 35% !important;
      background-color: #fff !important;
      border-radius: 10px !important;
      margin: 10% auto;
    }

    .txn-popuptable tr {
      border-bottom: none !important;
    }

    .txn-details-table tr {
      display: flex;
    }

    .txn-details-table tr td {
      padding: 2px 0px;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      justify-content: center;
      overflow-wrap: anywhere;
    }

    .hyphen {
      text-align: center;
      font-size: 23px;
      font-weight: 400;
      color: #858585;
      flex: 0.3;
    }

    .colan {
      text-align: left;
      font-size: 13px;
      font-weight: 400;
      color: #858585;
      flex: 0.1 !important;
    }

    .data_option {
      flex: 0.9;
      font-weight: 600 !important;
      color: #858585 !important;
      font-size: 14px;
    }

    .data_value {
      flex: 1;
      color: #858585;
      font-size: 14px;
    }

    .slip_outer_border {
      padding: 15px 28px;
    }

    .slip_inner_border {
      padding: 10px 15px;
      border: 1.5px solid #ffc15d;
      border-radius: 10px;
      background-color: rgb(237, 250, 255) !important;
    }

    .watermark {
      background: url(Images/mobi-watermark.svg);
      background-repeat: no-repeat;
      background-position: center;
    }

    .slip_payment_details tbody tr {
      border-bottom: none !important;
      display: flex;
      padding: 4px 0px;
    }

    .slip_payment_details tbody tr td {
      padding: 2px 0px;
      flex: 1;
      font-size: 12px;
      overflow-wrap: anywhere;
    }

    .content-viewreceipt {
      font-size: 12px !important;
    }

    .slip_option {
      flex: 0.7 !important;
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
  </style>
  
  <input type="hidden" value="${rareScenario}" id="rare-scenario">
  <input type="hidden" value="${action}" id="action_result">
  <input type="hidden" value="${rareScenario}" id="reject_result">
  
  <script>
    window.onload = function() {
      var rareScenario = document.getElementById("rare-scenario").value;
      var action_result = document.getElementById('action_result').value;
      var reject_result = document.getElementById('reject_result').value;
      console.log('rare : ', rareScenario);
      if (rareScenario === 'Yes') {
        document.getElementById("rare-scenario-model").style.display = "block";
      } else {
        if (action_result === 'reject') {
          document.getElementById("reject-modal-id").style.display = "block";
        } else if (action_result === 'approve') {
          document.getElementById("approve-modal-id").style.display = "block";
        }
      }
    }

    function openInfo(nameInBank, payoutId, customerName, accountNumber, amount, invoiceId) {


      console.log('PID', payoutId);
      console.log('cusName', customerName);
      console.log('accNo', accountNumber);
      console.log('Amount', amount);
      console.log('Invoice ID', invoiceId);
      // document.getElementById("info-nameInBank").innerText = nameInBank;
      document.getElementById("info-payoutId").innerText = payoutId;
      document.getElementById("info-customerName").innerText = customerName;
      document.getElementById("info-accountNumber").innerText = accountNumber;
      document.getElementById("info-amount").innerText = 'MYR ' + amount;
      document.getElementById("info-invoiceId-display").innerText = invoiceId;
      document.getElementById("info-invoiceId").value = invoiceId;
      document.getElementById("commentsTextarea").value = '';

      document.getElementById("modalContainerCard").style.display = "block";

    }

    function closeInfo() {
      var modal = document.getElementById("pending-modal-id");
      modal.style.display = "none";
    }

    function closeRare() {
      document.getElementById("rare-scenario-model").style.display = "none";
    }
  </script>
  
  <style>
    #modal {
      display: none;
      /* Hidden by default */
      position: fixed;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      width: 400px;
      /* Set the width of the modal */
      background: #fff;
      /* White background */
      border-radius: 8px;
      /* Rounded corners */
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
      /* Subtle shadow */
      z-index: 1000;
      /* Ensure it's on top */
      max-height: 80vh;
      /* Limit height */
      overflow-y: auto;
      /* Scroll if content overflows */
    }
  </style>
  
  <script>

    // Function to navigate to a specific page
    function goToPage(pageNum) {
        document.location.href = '${pageContext.request.contextPath}/transaction/payout/list/exceeded-limit-approvals?currentPage=' + pageNum;
    }

    $(document).ready(function () {
        var rowsPerPage = 20; // Updated to show 20 transactions per page
        var pageCount = ${paginationBean.querySize}; // Total number of pages
        var numbers = $('.pagination');

        // Number of page links to display
        var displayPages = 3;

        // Function to render pagination with ellipses
        function renderPagination(currentPage) {
            numbers.empty();

            // Create the previous button
            if (currentPage > 1) {
                numbers.append('<button class="prev" onclick="goToPage(' + (currentPage - 1) + ')"><span class="left-arrow"></span>Previous</button>');
            } else {
                numbers.append('<button class="prev disabled"><span class="left-arrow"></span>Previous</button>');
            }

            var halfDisplay = Math.floor(displayPages / 2);
            var startPage = Math.max(1, currentPage - halfDisplay);
            var endPage = Math.min(pageCount, currentPage + halfDisplay);

            if (currentPage - halfDisplay < 1) {
                endPage = Math.min(pageCount, endPage + (halfDisplay - currentPage + 1));
            }
            if (currentPage + halfDisplay > pageCount) {
                startPage = Math.max(1, startPage - (currentPage + halfDisplay - pageCount));
            }

            // Add first page and ellipsis if necessary
            if (startPage > 1) {
                numbers.append('<button class="page-link" onclick="goToPage(1)">1</button>');
                if (startPage > 2) {
                    numbers.append('<span class="ellipsis">...</span>');
                }
            }

            // Add page numbers
            for (var i = startPage; i <= endPage; i++) {
                if (i === currentPage) {
                    numbers.append('<button class="page-link active" onclick="goToPage(' + i + ')">' + i + '</button>');
                } else {
                    numbers.append('<button class="page-link" onclick="goToPage(' + i + ')">' + i + '</button>');
                }
            }

            // Add last page and ellipsis if necessary
            if (endPage < pageCount) {
                if (endPage < pageCount - 1) {
                    numbers.append('<span class="ellipsis">...</span>');
                }
                numbers.append('<button class="page-link" onclick="goToPage(' + pageCount + ')">' + pageCount + '</button>');
            }

            // Create the next button
            if (currentPage < pageCount) {
                numbers.append('<button class="next" onclick="goToPage(' + (currentPage + 1) + ')">Next<span class="right-arrow"></span></button>');
            } else {
                numbers.append('<button class="next disabled">Next<span class="right-arrow"></span></button>');
            }
        }


        // Get current page from URL
        function getCurrentPage() {
            var urlParams = new URLSearchParams(window.location.search);
            console.log(urlParams,'url params');
            console.log(urlParams.get('currentPage'),'url page num');

            var page = parseInt(urlParams.get('currentPage'), 10);
            console.log('CURRENT PAGE FROM URL : ',page);
            console.log('is Nan : ',isNaN(page));
            return isNaN(page) ? 1 : page;
        }

        // Initialize the pagination
        // var currentPage = getCurrentPage();
        var currentPage = ${currentPageNumber};
        renderPagination(currentPage);
    });
</script>
  
</body>
</html>