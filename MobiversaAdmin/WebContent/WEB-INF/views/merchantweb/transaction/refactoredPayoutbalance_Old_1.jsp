<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>payout balance</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <style>
      .container-fluid,
      strong,
      .btn {
        font-family: "Poppins", sans-serif !important;
        font-weight: 600 !important;
      }

      .card-content {
        padding: 18px 24px !important;
      }

      .m-4 {
        margin: 4px 0px !important;
      }

      .mb-0 {
        margin-bottom: 0 !important;
      }

      .mb-10 {
        margin-bottom: 10px !important;
      }

      .fs-15 {
        font-size: 15px;
      }

      .fs-18 {
        font-size: 18px !important;
      }

      .fw-400 {
        font-weight: 400;
      }

      .fw-600 {
        font-weight: 600;
      }

      .fw-500 {
        font-weight: 500;
      }

      .fs-30 {
        font-size: 30px !important;
      }

      .fs-22 {
        font-size: 22px !important;
      }

      .color_787878 {
        color: #787878 !important;
      }

      .color_2e2e2e {
        color: #2e2e2e !important;
      }

      .space {
        width: 100%;
        height: 9.5vh !important;
      }

      .button_group {
        display: flex;
        align-items: center;
        justify-content: space-between;
      }

      .clearbtn,
      .cancelbtn {
        background-color: #fff;
        color: #005baa;
        font-size: 15px;
        font-weight: 500;
        border: 1px solid #005baa;
        font-family: 'Poppins';
        border-radius: 50px;
        padding: 12px;
        width: 25%;
        cursor: pointer;
      }

      .clearbtn:hover,
      .cancelbtn:hover {
        background-color: #eff8ff !important;
      }

      .withdrawbtn:hover,
      .closebtn:hover {
        background-color: #254d8c !important;
      }

      .withdrawbtn,
      .closebtn {
        background-color: #005baa;
        color: #fff;
        font-size: 15px;
        font-weight: 400;
        padding: 12px;
        border: 1px solid #005baa;
        font-family: 'Poppins';
        border-radius: 50px;
        width: 70%;
        cursor: pointer;
      }

      .select-wrapper+label {
        position: absolute;
        top: -30px;
        font-size: 1.0rem;
      }

      .input-field>label {
        color: #787878;
        font-weight: 400;
      }

      .input-field .label_input {
        font-size: 18px !important;
      }

      .input-field .label_select {
        font-size: 1rem !important;
      }

      .input-field>label:not(.label-icon).active {
        transform: translateY(-18px) scale(0.8) !important;
        transform-origin: 0 0;
      }

      input[type=text]:not(.browser-default),
      input[type=text]:not(.browser-default)::placeholder {
        font-size: 13px !important;
        color: #787878 !important;
        font-family: 'Poppins';
        text-indent: 0.2cm;
      }

      .select-wrapper {
        font-family: 'Poppins';
        font-size: 13px !important;
      }

      .dropdown-content li>a,
      .dropdown-content li>span {
        font-size: 13px !important;
        font-weight: 400;
      }

      .align-right {
        text-align: right;
      }

      .fs-25 {
        font-size: 25px !important;
      }

      .fs-13 {
        font-size: 13px !important;
      }

      .color_9d9d9d {
        color: #9d9d9d;
      }

      .fs-16 {
        font-size: 16px !important;
      }

      @media screen and (max-width: 768px) {
        .input-field .label_input {
          font-size: 18px !important;
        }

        .input-field .label_select {
          font-size: 1rem !important;
        }
      }

      @media screen and (max-width: 768px) {
        .input-field.col label {
          left: 0.75rem !important;
          font-size: 14px !important;
        }
      }

      /*modal style*/
      /* Modal styling */
      .modal {
        display: none;
        /* Hidden by default */
        position: fixed;
        z-index: 100;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: #7d7d7d60;
        max-height: 100%;
      }

      #overlay-popup {
        position: fixed;
        display: none;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: #000;
        opacity: 0.5;
        z-index: 1000;
        cursor: pointer;
      }

      .withdrawbtn:focus {
        background-color: #005baa;
      }

      .clearbtn:focus {
        background-color: #fff;
      }

      /* Modal content */
      .modal-content {
        background-color: #fff;
        margin: 10% auto;
        border-radius: 10px;
        max-width: 470px;
        width: 90%;
        padding: 0 !important;
      }

      /* Close button */
      .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
      }

      .close:hover,
      .close:focus {
        color: #000;
      }

      /* Responsive media query */
      @media screen and (max-width: 600px) {
        .modal-content {
          width: 95%;
        }
      }

      textarea {
        border: 1px solid #cdcdcd;
        border-radius: 8px !important;
        resize: none;
      }

      .text-box {
        height: 85px;
        font-size: 14px;
        margin-top: 4px;
        padding: 10px;
        font-weight: 400;
        color: #9d9d9d;
        font-family: 'Poppins';
      }

      textarea::placeholder {
        color: #9d9d9d !important;
      }

      input[type=text]:not(.browser-default):focus:not([readonly])+label {
        color: #9d9d9d !important;
      }

      input[type=text]:not(.browser-default):focus:not([readonly]) {
        box-shadow: none !important;
      }

      .errorMsg {
        color: red;
        font-size: 12px;
        text-align: center;
        font-weight: 400;
      }

      .tooltip-container {
        position: relative;
        display: inline-block;
      }

      .tooltip-text {
        visibility: hidden;
        background-color: #2E2E2E;
        color: #fff;
        border-radius: 6px;
        padding: 5px;
        position: absolute;
        z-index: 100;
        right: 0;
        margin-left: -100px;
        opacity: 0;
        transition: opacity 0.3s;
        font-size: 10px;
        font-weight: 400;
        white-space: nowrap;
        font-family: 'Poppins';
      }

      .tooltip-container:hover .tooltip-text {
        visibility: visible;
        opacity: 1;
      }

      .tooltip-text tr {
        border: none !important;
      }

      .tooltip-text td,
      .tooltip-text th {
        padding: 1.5px 6.5px !important;
      }
    </style>
  </head>
  <body>
    <div id="overlay">
      <div id="overlay_text"><img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif"></div>
    </div>
    <div class="test" id="pop-bg-color"></div>
    <div class="container-fluid">
      <div class="row">
        <div class="col s12">
          <div class="card blue-bg text-white">
            <div class="card-content">
              <div class="d-flex align-items-center">
                <h3 class="text-white m-4"><strong>Payout Balance </strong></h3>
              </div>
            </div>
          </div>
        </div>
      </div><%--        available balance option --%><div class="row">
        <div class="col s12 m12 l5">
          <div class="card blue-bg text-white">
            <div class="card-content">
              <div class="">
                <div class="">
                  <div class="mb-10"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/availablebalanceicon.png" width="70" height="70"></div>
                  <div class="">
                    <p class="color_787878 fs-15 fw-400">Available Balance</p>
                    <h1 class="color_2e2e2e fs-30 fw-600">RM ${rawPayoutBalancePageResponse.available_balance}</h1>
                  </div>
                  <div class="space"></div>
                  <div style="display: flex; justify-content: space-between;align-items: center;">
                    <p class="color_787878 fs-18 fw-400 mb-0">Updated Date : <span>${rawPayoutBalancePageResponse.available_balance_last_fetched}</span></p>
                    <c:if test="${rawPayoutBalancePageResponse.is_overdraft_available}">
                      <div class="tooltip-container"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/info_grey.svg" width="25" height="25" style="cursor:pointer;"><%--                                        <span >Eligible for Settlement: <br>Overdraft Limit: 1,000.00 RM</span>--%><table class="tooltip-text">
                          <tbody>
                            <tr>
                              <td>Eligible for Settlement</td>
                              <td>:</td>
                              <td>${rawPayoutBalancePageResponse.eligible_settlement_amount} RM</td>
                              <td></td>
                            </tr>
                            <tr>
                              <td>Overdraft Limit</td>
                              <td>:</td>
                              <td>${rawPayoutBalancePageResponse.overdraft_amount} RM</td>
                              <td></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </c:if>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <c:if test="${rawPayoutBalancePageResponse.enable_currency_exchange}">
          <div class="col s12 m12 l7">
            <div class="card blue-bg text-white">
              <div class="card-content">
                <div class="">
                  <div class="header_text">
                    <h1 class="color_2e2e2e fw-600 fs-22">Withdraw Amount</h1>
                  </div>
                  <div class="row">
                    <div class="col s12 m6 l6">
                      <div class="input-field col s12"><input type="text" name="amount" id="amount" placeholder="e.g. RM 50,000"><label class="label_input">Enter Amount</label></div>
                      <div class="input-field col s12 currencyCodeSelect"><select name="currencyCode" id="currencyCode">
                          <option value="" selected>Select Currency</option>
                          <c:forEach var="entry" items="${rawPayoutBalancePageResponse.currency_codes_map_with_abbreviation}">
                            <option value="${entry.key}">${entry.value}</option>
                          </c:forEach>
                        </select><label class="label_select">Select Currency</label></div>
                    </div><%--                                    <c:if test="${totalWithdrawAmount}">--%><div class="col s12 m6 l6">
                      <div class="withdrawamttext align-right">
                        <p class="fs-13 fw-400 color_9d9d9d mb-0">Withdrawal Amount</p>
                        <p class="fw-400 color_2e2e2e fs-15" id="withdrawamttext">RM 0.00</p>
                      </div>
                      <div class="withdrawfee_text align-right">
                        <p class="fs-13 fw-400 color_9d9d9d mb-0">Withdrawal Fee</p>
                        <p class="fw-400 color_2e2e2e fs-15" id="withdrawfee_text">RM 0.00</p>
                      </div>
                      <div class="totalamt_text align-right">
                        <p class="fs-15 fw-400 color_9d9d9d mb-0">Total Amount</p>
                        <p class="fw-500 color_2e2e2e fs-25" id="totalamt_text">RM 0.00</p>
                      </div>
                    </div><%--                                    </c:if>--%><div>
                      <p id="errorMsg" class="errorMsg"></p>
                    </div>
                  </div>
                  <div class="button_group"><button class="clearbtn" type="button" id="clearBtn">Clear</button><button class="withdrawbtn" type="button" id="requestWithdrawBtn">Request Withdrawal</button></div>
                </div>
              </div>
            </div>
          </div>
        </c:if>
        <c:if test="${loginname.toLowerCase() == 'pcitest' || loginname.toLowerCase() == 'mobi'}"><%--            finance login withdraw option --%><div class="col s12 m12 l7">
            <div class="card blue-bg text-white">
              <div class="card-content">
                <div class="">
                  <div class="header_text">
                    <div class="mb-10"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/withdraw_icon_finance.png" width="70" height="70"></div>
                    <h1 class="color_2e2e2e fw-600 fs-16">Manage Balance</h1>
                  </div>
                  <div class="row">
                    <div class="col s12 m6 l5">
                      <div class="input-field col s12"><input type="text" name="requestAmount" id="requestAmount" placeholder="e.g. RM 50,000"><label class="label_input">Enter Amount (RM)</label></div>
                    </div>
                    <div class="col s12 m6 l7">
                      <div class="input-field col s12 withdrawOption_Col" id="withdrawOption_Col"><select name="withdrawOption" id="withdrawOption">
                          <option value="" selected>Choose Option</option>
                          <option value="Withdraw">Withdraw Amount</option>
                          <c:if test="${rawPayoutBalancePageResponse.is_overdraft_available}">
                            <option value="Overdraft">Overdraft Fee</option>
                          </c:if>
                        </select><label class="label_select">Choose Adjustment Type</label></div>
                    </div>
                    <div>
                      <p id="errorMsgForFinance" class="errorMsg"></p>
                    </div>
                  </div>
                  <div class="button_group"><button class="clearbtn" type="button" id="clearBtnForFinance">Clear</button><button class="withdrawbtn" type="button" id="requestWithdrawBtnForFinance">Request Withdrawal</button></div>
                </div>
              </div>
            </div>
          </div>
        </c:if>
      </div><%--       popup for currency exchange confirm popup --%><div id="reqestWithdraw" class="modal">
        <div class="modal-content">
          <div class="modal-header" style="padding: 12px 24px;border-bottom: 1.5px solid orange;">
            <h3 style="margin: 0;color: #005baa;font-size: 16px !important;font-weight: 500;text-align: center;">Confirm Withdrawal Request</h3>
          </div>
          <div class="modal_body" style="padding:15px 24px;">
            <div style="margin-bottom: 5px">
              <p class="fs-13 color_9d9d9d fw-400 mb-0">Total Amount After Fee</p>
              <p class="fs-15 color_2e2e2e fw-500 mb-0" id="total_Amount_After_Fee"></p>
            </div>
            <div style="margin-bottom: 12px">
              <p class="fs-13 color_9d9d9d fw-400 mb-0">Selected Country</p>
              <p class="fs-15 color_2e2e2e fw-500 mb-0" id="selectedCurrency"></p>
            </div>
            <div style="background-color: #fafafa;border: 1px solid #cdcdcd;padding: 10px;border-radius: 10px;">
              <div style="display: flex;align-items: center;justify-content: space-between;">
                <div>
                  <p class="color_2e2e2e mb-0 fs-13 fw-500">Total Amount</p>
                  <p style="font-size: 10px;" class="color_9d9d9d fw-400">(to be withdrawn)</p>
                </div>
                <p class="fs-15 color_2e2e2e fw-500" id="converted_Amount"></p>
              </div>
              <div style="display: flex;align-items: center;justify-content: space-between;">
                <p class="fw-400 mb-0" style="color: #005baa;font-size: 11px">1 MYR = <span id="selectedCurrencyRate"></span></p>
                <p class="mb-0 color_9d9d9d fw-400" style="font-size: 11px;">Last updated <span id="lastUpdateOfRate"></span></p>
              </div>
            </div>
            <div style="display: flex; margin: 8px 0" class=""><img style="margin:0 8px 0 0;" src="${pageContext.request.contextPath}/resourcesNew1/assets/info_grey.svg" width="15" height="15"><span class="color_9d9d9d fw-400" style="font-size: 11px;">The amount is converted to your selected currency, with fees deducted.</span></div>
            <div class="note">
              <p class="color_2e2e2e fw-500 mb-0" style="font-size: 11.5px;">Note:</p>
              <p class="mb-0 color_9d9d9d fw-400" style="font-size: 11.5px;"> By confirming, your withdrawal request will be processed immediately. Please ensure all details are accurate. Processing time are vary by currency and bank, typically within 24 hours. </p>
            </div>
          </div>
          <div class="modal_footer" style="padding: 12px 24px;background: #eff8ff;border-bottom-right-radius: 10px;
                border-bottom-left-radius: 10px; display: flex; align-items: center;justify-content: center;"><button class="cancelbtn" type="button" id="cancelBtn" style="font-size: 12px;width: 23%;padding: 10px; margin: 0 5px;">Cancel</button><button class="withdrawbtn " type="button" id="confirmWithdrawBtn" style="font-size: 12px; width: 45%;padding: 10px; margin: 0 5px;">Confirm Withdrawal</button></div>
        </div>
      </div><%--        result popup --%><div id="resultOfCurrencyExchangeWithdrawModal" class="modal">
        <div class="modal-content">
          <div class="modal-header" style="padding: 12px 24px;border-bottom: 1.5px solid orange;">
            <h3 style="margin: 0;color: #005baa;font-size: 15px !important;font-weight: 500; text-align: center;">Notification</h3>
          </div>
          <div class="modal_body" style="padding:15px 24px;">
            <div style=" margin: 8px 0" class="">
              <div style="text-align: center"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/Successful.svg" width="50" height="50"></div>
              <p class="color_9d9d9d fw-400" style="font-size: 16px; text-align: center;" id="resultContent"></p>
            </div>
          </div>
          <div class="modal_footer" style="padding: 12px 24px;background: #eff8ff;border-bottom-right-radius: 10px;
                border-bottom-left-radius: 10px; display: flex; align-items: center;justify-content: center;"><button class="closebtn " type="button" id="closeBtn" style="font-size: 12px; width: 25%;padding: 10px; margin: 0 5px;">Close</button></div>
        </div>
      </div><%--        withdraw confirm popup for finance --%><div id="confirmWithdrawRequest" class="modal">
        <div class="modal-content">
          <div class="modal-header" style="padding: 12px 24px;border-bottom: 1.5px solid orange;">
            <h3 style="margin: 0;color: #005baa;font-size: 16px !important;font-weight: 500; text-align: center;">Confirmation</h3>
          </div>
          <div class="modal_body" style="padding:10px 24px;">
            <div style=" margin: 8px 0" class="">
              <div style="text-align: center"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/notify.svg" width="50" height="50"></div>
              <p class="color_9d9d9d fw-400" style="font-size: 16px; text-align: center;"> Confirm Withdraw for the amount of ? </p>
              <div><textarea name="reasonforWithdraw" id="reasonforWithdraw" placeholder="Enter reason here.." class="text-box"></textarea></div>
              <div>
                <p id="popupErrorMsgForWithdraw" class="mb-0 errorMsg"></p>
              </div>
            </div>
          </div>
          <div class="modal_footer" style="padding: 12px 24px;background: #eff8ff;border-bottom-right-radius: 10px;
                border-bottom-left-radius: 10px; display: flex; align-items: center;justify-content: center;"><button class="cancelbtn" type="button" id="cancel_Btn_Fin" style="font-size: 12px;width: 23%;padding: 10px; margin: 0 5px;">Cancel</button><button class="withdrawbtn " type="button" id="confirmWithdrawBtn_Fin" style="font-size: 12px; width: 45%;padding: 10px; margin: 0 5px;">Confirm Withdrawal</button></div>
        </div>
      </div><%--       confirm overdraft popup for finance --%><div id="confirmOverdraftRequest" class="modal">
        <div class="modal-content">
          <div class="modal-header" style="padding: 12px 24px;border-bottom: 1.5px solid orange;">
            <h3 style="margin: 0;color: #005baa;font-size: 16px !important;font-weight: 500; text-align: center;">Confirmation</h3>
          </div>
          <div class="modal_body" style="padding:10px 24px;">
            <div style=" margin: 8px 0" class="">
              <div style="text-align: center"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/notify.svg" width="50" height="50"></div>
              <p class="color_9d9d9d fw-400" style="font-size: 16px; text-align: center;"> Confirm Overdraft for the amount of ? </p>
              <div><textarea name="reasonforOverdraft" id="reasonforOverdraft" placeholder="Enter reason here.." class="text-box"></textarea></div>
              <div>
                <p id="popupErrorMsgForOverdraft" class="mb-0 errorMsg"></p>
              </div>
            </div>
          </div>
          <div class="modal_footer" style="padding: 12px 24px;background: #eff8ff;border-bottom-right-radius: 10px;
                border-bottom-left-radius: 10px; display: flex; align-items: center;justify-content: center;"><button class="cancelbtn" type="button" id="cancel_Overdraft_Btn" style="font-size: 12px;width: 23%;padding: 10px; margin: 0 5px;">Cancel</button><button class="withdrawbtn" type="button" id="confirmOverdraftBtn_Fin" style="font-size: 12px; width: 45%;padding: 10px; margin: 0 5px;">Confirm Withdrawal</button></div>
        </div>
        <div><input type="hidden" id="available_balance" value="${rawPayoutBalancePageResponse.eligible_settlement_amount}" /><input type="hidden" id="withdrawRequestResult" value="" /><input type="hidden" id="merchant_id" value="${rawPayoutBalancePageResponse.merchant_id}" /><input type="hidden" id="finalEnteredAmount" value="" /><input type="hidden" id="finalEnteredAmountForFinance" value="" /><input type="hidden" id="totalAmountAfterFee" value="" /><input type="hidden" name="exchange_rate_fee" id="exchange_rate_fee" value="${rawPayoutBalancePageResponse.exchange_rate_fee}" />
		<form id="withdrawForm" action="${pageContext.request.contextPath}/transactionUmweb/currency-exchange/confirm-notify" method="POST">
		    <input type="hidden" name="totalAmount" id="formTotalAmount" />
 		    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		    <input type="hidden" name="selectedCurrency" id="formSelectedCurrency" />
		    <input type="hidden" name="convertedAmount" id="formConvertedAmount" />
		    <input type="hidden" name="selectedCurrencyRate" id="formSelectedCurrencyRate" />
		</form>
		
        <input type="hidden" id="responseForCurrencyExchange" value='${payoutBalancePageResponse}' /><input type="hidden" id="rawResponseForCurrencyExchange" value='${rawPayoutBalancePageResponse}' />
        </div>
      </div><%--for finance --%><script>
        const requestWithdrawBtnForFinance = document.getElementById('requestWithdrawBtnForFinance');
        const cancel_Btn_Fin = document.getElementById('cancel_Btn_Fin');
        const clearBtnForFinance = document.getElementById('clearBtnForFinance');
        const cancel_Overdraft_Btn = document.getElementById('cancel_Overdraft_Btn');
        const confirmWithdrawBtn_Fin = document.getElementById('confirmWithdrawBtn_Fin');
        const confirmOverdraftBtn_Fin = document.getElementById('confirmOverdraftBtn_Fin');
        if (requestWithdrawBtnForFinance) {
          requestWithdrawBtnForFinance.onclick = function() {
            var adjustmentType = document.getElementById('withdrawOption').value;
            if (adjustmentType === 'Withdraw') {
              const confirmWithdrawRequest = document.getElementById('confirmWithdrawRequest');
              confirmWithdrawRequest.style.display = 'block';
            } else if (adjustmentType === 'Overdraft') {
              const confirmOverdraftRequest = document.getElementById('confirmOverdraftRequest');
              confirmOverdraftRequest.style.display = 'block';
            }
          }
        }
        if (cancel_Btn_Fin) {
          cancel_Btn_Fin.onclick = function() {
            const confirmWithdrawRequest = document.getElementById('confirmWithdrawRequest');
            confirmWithdrawRequest.style.display = 'none';
          }
        }
        if (cancel_Overdraft_Btn) {
          cancel_Overdraft_Btn.onclick = function() {
            const confirmOverdraftRequest = document.getElementById('confirmOverdraftRequest');
            confirmOverdraftRequest.style.display = 'none';
          }
        }
        var requestAmountInput = document.getElementById('requestAmount');
        if (requestAmountInput) {
          requestAmountInput.addEventListener('input', function() {
            var eligiblePayoutAmountElement = parseFloat(document.getElementById("available_balance").value.replace(/,/g, ''));
            var amountInputRM = document.getElementById("requestAmount");
            var finalEnteredAmount = document.getElementById("finalEnteredAmountForFinance");
            var btn = document.getElementById("requestWithdrawBtnForFinance");
            var enteredAmount = amountInputRM.value.replace(/[^\d]/g, '');
            while (enteredAmount.length < 3) {
              enteredAmount = '0' + enteredAmount;
            }
            enteredAmount = enteredAmount.slice(0, -2) + '.' + enteredAmount.slice(-2);
            finalEnteredAmount.value = enteredAmount;
            amountInputRM.value = 'RM ' + parseFloat(enteredAmount).toLocaleString('en-US', {
              minimumFractionDigits: 2,
              maximumFractionDigits: 2
            });
          });
        }
        if (requestAmountInput) {
          requestAmountInput.addEventListener('blur', function() {
            var eligiblePayoutAmountElement = parseFloat(document.getElementById("available_balance").value.replace(/,/g, ''));
            var finalEnteredAmount = parseFloat(document.getElementById("finalEnteredAmountForFinance").value);
            var btn = document.getElementById("requestWithdrawBtn");
            var available_balance = parseFloat(document.getElementById('available_balance').value.replace(/,/g, ''));
            console.log("available balance for validation : ", available_balance);
            if ((finalEnteredAmount - available_balance) > 0) {
              showError("Amount should not exceed the available balance", "errorMsgForFinance");
            } else if (finalEnteredAmount > 0.00 && finalEnteredAmount < 5.00) {
              showError("Minimum withdrawal amount is 10.00 RM", "errorMsgForFinance");
            }
          });
        }
        if (clearBtnForFinance) {
          clearBtnForFinance.addEventListener('click', function() {
            var amountInputRM = document.getElementById("requestAmount");
            amountInputRM.value = '';
            var finalEnteredAmount = document.getElementById("finalEnteredAmountForFinance");
            finalEnteredAmount.value = '0.00';
            var select = $('#withdrawOption');
            $(".withdrawOption_Col .select-dropdown").val("");
            select.prop('selectedIndex', 0);
            select.formSelect();
            var btn = document.getElementById("requestWithdrawBtnForFinance");
            btn.disabled = true;
            btn.style.opacity = '0.5';
            btn.style.pointerEvents = "none";
          });
        }

        function validateAndToggleButtonForFinance() {
          var withdrawOption = document.getElementById('withdrawOption').value;
          var available_balance = parseFloat(document.getElementById('available_balance').value.replace(/,/g, ''));
          var finalEnteredAmountForFinance = parseFloat(document.getElementById("finalEnteredAmountForFinance").value);
          var btn = document.getElementById("requestWithdrawBtnForFinance");
          console.log("available balance : ", available_balance);
          if (finalEnteredAmountForFinance <= available_balance && finalEnteredAmountForFinance >= 5.00 && withdrawOption !== '') {
            btn.disabled = false;
            btn.style.opacity = '1';
            btn.style.pointerEvents = "auto";
          } else {
            btn.disabled = true;
            btn.style.opacity = '0.5';
            btn.style.pointerEvents = "none";
          }
        }
        document.getElementById('withdrawOption').addEventListener('change', function() {
          validateAndToggleButtonForFinance();
        });
        requestAmountInput.addEventListener('input', function() {
          validateAndToggleButtonForFinance();
        });

        function submitWithdrawOptionRequest() {
          var withdrawOption = document.getElementById('withdrawOption').value;
          var merchantId = document.getElementById('merchant_id').value;
          var requestAmount = document.getElementById("requestAmount").value;
          var reasonforWithdraw = document.getElementById('reasonforWithdraw').value;
          var reasonforOverdraft = document.getElementById('reasonforOverdraft').value;
          var confirmWithdrawBtn_Fin = document.getElementById('confirmWithdrawBtn_Fin');
          var confirmOverdraftBtn_Fin = document.getElementById('confirmOverdraftBtn_Fin');
          if (withdrawOption === 'Withdraw' && reasonforWithdraw.trim() === '') {
            showError('Please provide a reason for withdrawal.', 'popupErrorMsgForWithdraw');
            return;
          } else if (withdrawOption === 'Overdraft' && reasonforOverdraft.trim() === '') {
            showError('Please provide a reason for overdraft.', 'popupErrorMsgForOverdraft');
            return;
          }
          if (withdrawOption === 'Withdraw') {
            $("#overlay").show();
            confirmWithdrawBtn_Fin.disabled = true;
            confirmWithdrawBtn_Fin.style.opacity = '0.5';
            confirmWithdrawBtn_Fin.style.pointerEvents = "none";
            const confirmWithdrawRequest = document.getElementById('confirmWithdrawRequest');
            confirmWithdrawRequest.style.display = 'none';
            document.location.href = '${pageContext.request.contextPath}/transactionUmweb/refactoredWithDraw?finalwithdrawamount=' + requestAmount + '&merchantId=' + merchantId + '&withdrawType=' + withdrawOption + '&withdrawalComment=' + reasonforWithdraw;
          } else if (withdrawOption === 'Overdraft') {
            confirmOverdraftBtn_Fin.disabled = true;
            confirmOverdraftBtn_Fin.style.opacity = '0.5';
            confirmOverdraftBtn_Fin.style.pointerEvents = "none";
            const confirmOverdraftRequest = document.getElementById('confirmOverdraftRequest');
            confirmOverdraftRequest.style.display = 'none';
            document.location.href = '${pageContext.request.contextPath}/transactionUmweb/refactoredWithDraw?finalwithdrawamount=' + requestAmount + '&merchantId=' + merchantId + '&withdrawType=' + withdrawOption + '&withdrawalComment=' + reasonforOverdraft;
          }
        }
        document.getElementById('confirmWithdrawBtn_Fin').addEventListener('click', function() {
          submitWithdrawOptionRequest();
        });
        document.getElementById('confirmOverdraftBtn_Fin').addEventListener('click', function() {
          submitWithdrawOptionRequest();
        });
      </script>
      <script>
        // Get the modal element
        const reqestWithdrawModal = document.getElementById('reqestWithdraw');
        const resultModal = document.getElementById('resultOfCurrencyExchangeWithdrawModal');
        const reqestWithdrawBtn = document.getElementById('requestWithdrawBtn');
        const clearBtn = document.getElementById('clearBtn');
        const confirmWithdrawBtn = document.getElementById('confirmWithdrawBtn');
        const closeModalBtn = document.getElementById('cancelBtn');
        const closeResultBtn = document.getElementById('closeBtn');
        reqestWithdrawBtn.onclick = function() {
          updateValuesInModal();
        }
        closeModalBtn.onclick = function() {
          reqestWithdrawModal.style.display = 'none';
        }
        closeResultBtn.onclick = function() {
          resultModal.style.display = 'none';
        }
        const amountInput = document.getElementById('amount');
        const currencyCodeSelect = document.getElementById('currencyCode');
        /* function updateValuesInModal() {
          var response = document.getElementById('responseForCurrencyExchange').value;
          var backendData = JSON.parse(response);
          console.log("backend data:::", backendData)
          var totalAmountAfterFee = parseFloat(document.getElementById('totalAmountAfterFee').value.replace(/,/g, '')) || 0;
          var currencyCodeValue = document.getElementById('currencyCode');
          var selectedCountry = currencyCodeValue.value;
          var selectedCurrencyText = currencyCodeValue.options[currencyCodeValue.selectedIndex].text;
          var convertionAmt;
          var convertionRate;
          var lastUpdatedDateOfRate;
          const reqestWithdrawModal = document.getElementById('reqestWithdraw');
          var total_Amount_After_Fee = document.getElementById('total_Amount_After_Fee');
          var selectedCurrency = document.getElementById('selectedCurrency');
          var converted_Amount = document.getElementById('converted_Amount');
          var selectedCurrencyRate = document.getElementById('selectedCurrencyRate');
          var lastUpdateOfRate = document.getElementById('lastUpdateOfRate');
          var rate = backendData.conversion_rates[selectedCountry] || 0;
          var convertedAmount = (totalAmountAfterFee * rate).toFixed(2);
          var formattedConvertedAmount = parseFloat(convertedAmount).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          convertionAmt = formattedConvertedAmount + ' ' + selectedCountry;
          // Update the conversion rate
          convertionRate = rate.toFixed(2) + ' ' + selectedCountry;
          var lastUpdatedDate = new Date(backendData.exchange_rate_last_fetched);
          var formattedDate = lastUpdatedDate.toLocaleString('en-US', {
            month: 'short',
            day: 'numeric',
            year: 'numeric'
          });
          lastUpdatedDateOfRate = formattedDate;
          var formattedTotalAmountAfterFee = parseFloat(totalAmountAfterFee).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          total_Amount_After_Fee.innerText = 'RM ' + formattedTotalAmountAfterFee;
          selectedCurrency.innerText = selectedCurrencyText;
          converted_Amount.innerText = convertionAmt;
          selectedCurrencyRate.innerText = convertionRate;
          lastUpdateOfRate.innerText = lastUpdatedDateOfRate;
          console.log("::" + lastUpdatedDateOfRate);
          reqestWithdrawModal.style.display = 'block';
        } */
        function updateValuesInModal() {
          var response = document.getElementById('responseForCurrencyExchange').value;
          var backendData = JSON.parse(response);
          console.log("backend data:::", backendData);
          var totalAmountAfterFee = parseFloat(document.getElementById('totalAmountAfterFee').value.replace(/,/g, '')) || 0;
          var currencyCodeValue = document.getElementById('currencyCode');
          var selectedCountry = currencyCodeValue.value;
          var selectedCurrencyText = currencyCodeValue.options[currencyCodeValue.selectedIndex].text;
          var convertionAmt;
          var convertionRate;
          var lastUpdatedDateOfRate;
          const reqestWithdrawModal = document.getElementById('reqestWithdraw');
          var total_Amount_After_Fee = document.getElementById('total_Amount_After_Fee');
          var selectedCurrency = document.getElementById('selectedCurrency');
          var converted_Amount = document.getElementById('converted_Amount');
          var selectedCurrencyRate = document.getElementById('selectedCurrencyRate');
          var lastUpdateOfRate = document.getElementById('lastUpdateOfRate');
          var rate = backendData.conversion_rates[selectedCountry] || 0;
          var convertedAmount = (totalAmountAfterFee * rate).toFixed(2);
          var formattedConvertedAmount = parseFloat(convertedAmount).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          convertionAmt = formattedConvertedAmount + ' ' + selectedCountry;
          // Update the conversion rate
          convertionRate = rate.toFixed(2) + ' ' + selectedCountry;
          // Parse the date string into a Date object
          var lastUpdatedDate = parseDateString(backendData.exchange_rate_last_fetched);
          console.log("1. " + backendData.exchange_rate_last_fetched + " :::: " + lastUpdatedDate + " :::: " + formattedDateString);
          // Manually format the date
          var options = {
            month: 'short',
            day: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
            hour12: false
          };
          var formattedDate = lastUpdatedDate.toLocaleString('en-US', options);
          var dateParts = formattedDate.split(', ');
          var timePart = dateParts[1].trim();
          var datePart = dateParts[0].split(' ');
          var formattedDateString = `${datePart[1]} ${datePart[0]}, ${datePart[2]} ${timePart}`;
          lastUpdatedDateOfRate = formattedDateString;
          var formattedTotalAmountAfterFee = parseFloat(totalAmountAfterFee).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          console.log("1. " + backendData.exchange_rate_last_fetched + " :::: " + lastUpdatedDate + " :::: " + formattedDateString);
          console.log(lastUpdatedDate + " :::: " + formattedDateString);
          total_Amount_After_Fee.innerText = 'RM ' + formattedTotalAmountAfterFee;
          selectedCurrency.innerText = selectedCurrencyText;
          converted_Amount.innerText = convertionAmt;
          selectedCurrencyRate.innerText = convertionRate;
          lastUpdateOfRate.innerText = lastUpdatedDateOfRate;
          console.log("::" + lastUpdatedDateOfRate);
          reqestWithdrawModal.style.display = 'block';
        }

        function parseDateString(dateString) {
          var dateParts = dateString.split(' ');
          var date = dateParts[0].split('-');
          var time = dateParts[1].split(':');
          return new Date(date[2], date[1] - 1, date[0], time[0], time[1], time[2]);
        }

        function confirmWithdrawalRequest() {
          var reqestWithdrawModal = document.getElementById('reqestWithdraw');
          document.getElementById('formTotalAmount').value = document.getElementById('total_Amount_After_Fee').innerText;
          document.getElementById('formSelectedCurrency').value = document.getElementById('selectedCurrency').innerText;
          document.getElementById('formConvertedAmount').value = document.getElementById('converted_Amount').innerText;
          document.getElementById('formSelectedCurrencyRate').value = document.getElementById('selectedCurrencyRate').innerText;
          var confirmWithdrawBtn = document.getElementById('confirmWithdrawBtn');
          confirmWithdrawBtn.disabled = true;
          confirmWithdrawBtn.style.opacity = '0.5'
          confirmWithdrawBtn.style.pointerEvents = 'none'
          reqestWithdrawModal.style.display = "none";
          $("#overlay").show();
          document.getElementById('withdrawForm').submit();
        }
        confirmWithdrawBtn.onclick = function() {
          confirmWithdrawalRequest();
        }
        clearBtn.addEventListener('click', function() {
          var amountInputRM = document.getElementById("amount");
          amountInputRM.value = '';
          var finalEnteredAmount = document.getElementById("finalEnteredAmount");
          finalEnteredAmount.value = '0.00';
          var select = $('#currencyCode');
          $(".currencyCodeSelect .select-dropdown").val("");
          select.prop('selectedIndex', 0);
          select.formSelect();
          updateWithdraw(0, 0);
          var btn = document.getElementById("requestWithdrawBtn");
          btn.disabled = true;
          btn.style.opacity = '0.5';
          btn.style.pointerEvents = "none";
        });
        document.addEventListener('DOMContentLoaded', function() {
          var btn = document.getElementById("requestWithdrawBtn");
          if (btn) {
            btn.disabled = true;
            btn.style.pointerEvents = "none";
            btn.style.opacity = '0.5';
          }
          var btnForFinance = document.getElementById("requestWithdrawBtnForFinance");
          if (btnForFinance) {
            btnForFinance.disabled = true;
            btnForFinance.style.pointerEvents = "none";
            btnForFinance.style.opacity = '0.5';
          }
          defaultAmount = 0.00;
          defaultFee = 0.00;
          updateWithdraw(defaultAmount, defaultFee);
          var result = document.getElementById('withdrawRequestResult').value;
          showResultModal(result);
        });
        amountInput.addEventListener('input', function() {
          var eligiblePayoutAmountElement = parseFloat(document.getElementById("available_balance").value.replace(/,/g, ''));
          var amountInputRM = document.getElementById("amount");
          var finalEnteredAmount = document.getElementById("finalEnteredAmount");
          var exchange_rate_fee = document.getElementById("exchange_rate_fee");
          var btn = document.getElementById("requestWithdrawBtn");
          var enteredAmount = amountInputRM.value.replace(/[^\d]/g, '');
          while (enteredAmount.length < 3) {
            enteredAmount = '0' + enteredAmount;
          }
          enteredAmount = enteredAmount.slice(0, -2) + '.' + enteredAmount.slice(-2);
          finalEnteredAmount.value = enteredAmount;
          amountInputRM.value = 'RM ' + parseFloat(enteredAmount).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          updateWithdraw(finalEnteredAmount.value, exchange_rate_fee);
        });
        amountInput.addEventListener('blur', function() {
          var eligiblePayoutAmountElement = parseFloat(document.getElementById("available_balance").value.replace(/,/g, ''));
          var finalEnteredAmount = parseFloat(document.getElementById("finalEnteredAmount").value);
          var btn = document.getElementById("requestWithdrawBtn");
          var available_balance = parseFloat(document.getElementById('available_balance').value.replace(/,/g, ''));
          var totalAmtAfterFee = parseFloat(document.getElementById('totalAmountAfterFee').value.replace(/,/g, ''));
          console.log("totalAmtAfterFee balance : ", totalAmtAfterFee);
          if ((totalAmtAfterFee - available_balance) > 0) {
            showError("Withdrawal amount is exceed than available amount.", "errorMsg");
          } else if (finalEnteredAmount > 0.00 && finalEnteredAmount < 5.00) {
            showError("Minimum withdrawal amount is 10.00 RM", "errorMsg");
          }
        });

        function updateWithdraw(finalEnteredAmount, fee) {
          var withdrawAmtText = document.getElementById("withdrawamttext");
          var withdrawFeeText = document.getElementById("withdrawfee_text");
          var totalAmountText = document.getElementById("totalamt_text");
          finalEnteredAmount = parseFloat(finalEnteredAmount) || 0.00;
          var fee = parseFloat(document.getElementById('exchange_rate_fee').value.replace(/,/g, '')) || 0.00;
          // Calculate the withdrawal fee and total amount
          var withdrawalFee = (finalEnteredAmount * fee).toFixed(2);
          var totalAmount = (finalEnteredAmount + parseFloat(withdrawalFee)).toFixed(2);
          // Format numbers with comma separators
          var formattedFinalEnteredAmount = finalEnteredAmount.toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          var formattedWithdrawalFee = parseFloat(withdrawalFee).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          var formattedTotalAmount = parseFloat(totalAmount).toLocaleString('en-US', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          // Update the innerText values
          withdrawAmtText.innerText = 'RM ' + formattedFinalEnteredAmount;
          withdrawFeeText.innerText = 'RM ' + formattedWithdrawalFee;
          totalAmountText.innerText = 'RM ' + formattedTotalAmount;
          var totalAmtAfterFee = document.getElementById('totalAmountAfterFee');
          totalAmtAfterFee.value = formattedTotalAmount;
        }
        document.getElementById('currencyCode').addEventListener('change', function() {
          validateAndToggleButton();
        });
        amountInput.addEventListener('input', function() {
          validateAndToggleButton();
        });

        function validateAndToggleButton() {
          var selectedCurrency = document.getElementById('currencyCode').value;
          var available_balance = parseFloat(document.getElementById('available_balance').value.replace(/,/g, ''));
          var finalEnteredAmount = parseFloat(document.getElementById("finalEnteredAmount").value);
          var totalAmtAfterFee = parseFloat(document.getElementById('totalAmountAfterFee').value.replace(/,/g, ''));
          var btn = document.getElementById("requestWithdrawBtn");
          console.log("available balance : ", available_balance);
          if (totalAmtAfterFee <= available_balance && totalAmtAfterFee >= 10.00 && selectedCurrency !== '') {
            btn.disabled = false;
            btn.style.opacity = '1';
            btn.style.pointerEvents = "auto";
          } else {
            btn.disabled = true;
            btn.style.opacity = '0.5';
            btn.style.pointerEvents = "none";
          }
        }

        function showError(message, msgId) {
          var errorMsg = document.getElementById(msgId);
          errorMsg.innerText = message;
          errorMsg.style.display = 'block';
          setTimeout(function() {
            errorMsg.style.display = 'none';
          }, 3000);
        }

        function showResultModal(result) {
          var resultModal = document.getElementById('resultOfCurrencyExchangeWithdrawModal');
          var resultContent = document.getElementById('resultContent');
          if (result) {
            if (result === 'merchantWithdraw') {
              resultModal.style.display = 'block';
              resultContent.innerText = "Withdrawal request of USD 10,986.89 submitted successfully. You'll be notified once processing is complete.";
            } else if (result === 'financeWithdrawRequest') {
              resultModal.style.display = 'block';
              resultContent.innerText = "Withdrawal request has been processed successfully."
            } else if (result === 'financeOverdraftRequest') {
              resultModal.style.display = 'block';
              resultContent.innerText = "Overdraft request has been processed successfully."
            }
          } else {
            resultModal.style.display = 'none';
          }
        }
      </script>
  </body>
</html>