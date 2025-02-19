<%@page import="com.mobiversa.payment.controller.TransactionController" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>

<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <meta
            content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
            name="viewport">
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<style>


    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

    .container-fluid {
        font-family: "Poppins", sans-serif !important;
    }

    #merchantName:hover {
        color: #275ca8;
    }

    #agentName:hover {
        color: #275ca8;
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
        transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform 0.2s cubic-bezier(0.35, 0, 0.25, 1),
        background-color 0.3s ease-in-out;
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


</style>
<script type="text/javascript">
    history.pushState(null, null, "");
    window.addEventListener('popstate', function () {
        history.pushState(null, null, "");

    });
</script>
<script type="text/javascript">
    jQuery(document).ready(function () {


    });
</script>


<script lang="JavaScript">
    function loadSelectData() {

        var businessName = document.getElementById('selectedMerchant').value;
        var type = document.getElementById('export').value;

        if (!type) {
            alert("Please select the export type");
            return;
        }
        window.location.href = '${pageContext.request.contextPath}/transaction/exportSettlementDetails?businessName=' + businessName + '&exportType=' + type;
    }

    function loadDropDate13() {

        var e = document.getElementById("export");
        var strUser = e.options[e.selectedIndex].value;
        document.getElementById("export1").value = strUser;

    }


    function loadDate(inputtxt, outputtxt) {
        var field = inputtxt.value;
        outputtxt.value = field;
    }

</script>
<body class="">
<div id="overlay">
    <div id="overlay_text">
        <img class="img-fluid"
             src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
    </div>
</div>
<div class="container-fluid">
    <div class="row">


        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content" style="padding-bottom: 13px!important;">
                    <div class="d-flex align-items-center">
                        <h3 class="text-white" style="font-family: Poppins, sans-serif !important;"><strong>Current Day
                            Balance</strong></h3>
                    </div>


                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="row">
                    <div class="col s12">
                        <div class="blue-bg text-white">
                            <div class="card-content" style="padding-bottom: 2px !important;">
                                <div class="row" style="margin-right: 5.25rem !important;">
                                    <%-- merchant choose --%>


                                    <div class="col s12 m4 l4 input-field businessname"
                                         style="margin: 0px 0px !important;">
                                        <label for="merchantName" id="label_businessname">Select Business Name</label>
                                        <div class="select-wrapper">

                                            <select name="merchantName" id="merchantName" path="merchantName"
                                                    onchange="handleSelectChange(this)"
                                                    class="browser-default select-filter">
                                                <i class="icon fas fa-caret-down"></i>

                                                <optgroup label="Business Names" style="width: 100%"><br>

                                                    <c:forEach items="${merchantDetails.itemList}" var="dto">
                                                        <c:if test="${dto.businessName != null}">
                                                            <option value="${pageContext.request.contextPath}/transaction/settlementDetailsByBusinessName?businessName=${dto.businessName}">
                                                                <c:choose>
                                                                    <c:when test="${dto.businessName != null}">
                                                                        ${dto.businessName}
                                                                    </c:when>
                                                                </c:choose>
                                                            </option>
                                                        </c:if>
                                                    </c:forEach>
                                                </optgroup>
                                            </select>

                                        </div>

                                    </div>


                                    <div class="input-field col s12 m3 l3">
                                        <select name="export" id="export"
                                                class="form-control" style="width:100%">
                                            <option selected value="">Select type</option>
                                            <%--                                            <option value="PDF">As PDF</option>--%>
                                            <option value="EXCEL">As EXCEL</option>
                                        </select>
                                        <label for="export" style="font-size: 14px!important; color:#858585">Choose
                                            type</label>
                                    </div>

                                    <div class=" input-field col s12 m3 l3 exp">
                                        <input type="hidden" name="selectedMerchant" id="selectedMerchant"
                                               value="${selectedMerchant}">

                                        <button class=" btn btn-primary export-blue-btn" type="button"
                                                style="padding:0px 0px!important; font-family: 'Poppins', sans-serif !important;
                                        font-size: 15px !important; font-weight: 400!important;border-radius: 28px !important;"
                                                onclick="return loadSelectData();"> Export
                                        </button>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <script type="text/javascript">


                function handleSelectChange(selectElement) {
                    var selectedValue = selectElement.value;
                    document.getElementById('overlay').style.display = 'block';
                    if (selectedValue) {
                        window.location.href = selectedValue;
                    }
                }

                $(document).ready(function () {
                    $("#merchantName").select2({
                        allowClear : true,
                        // placeholder : "Business Name"

                    }).removeAttr('title')

                    $('#merchantName').val(null);
                });
            </script>

            <style>
                #label_businessname {
                    position: static !important;
                    color: #858585;
                    font-size: 14px;
                }

                #select2-merchantName {
                    color: red !important;
                }

                .row .select2-container {
                    padding: 0px !important;
                    border: none !important;
                    margin: 10px 0 !important;
                    z-index: 0;
                }

                .select2-container--default .select2-selection--single .select2-selection__arrow {
                    top: 0px;
                    height: 20px !important;
                }

                .select2-container--default .select2-selection--single {
                    background-color: #fff;
                    border: none;
                    border-bottom: 1.5px solid #ffa500;
                    border-radius: 0px;
                }

                .select2-container--default .select2-selection--single .select2-selection__rendered {
                    color: #333;
                    line-height: 20px;
                    font-size: 13px;
                }

                .select2-dropdown {
                    border: 1px solid #F5F5F5 !important;
                    box-shadow: 4px 0px 5px -2px #F5F5F5, /* Right shadow */ -4px 0px 5px -2px #F5F5F5, /* Left shadow */ 0px 4px 15px -2px #F5F5F5; /* Bottom shadow */
                    z-index: 1 !important;
                }

                #businessname_select ul:not(.browser-default) {
                    padding-left: 0;
                    list-style-type: none;
                    border-bottom-left-radius: 8px;
                    border-bottom-right-radius: 8px;
                }

                .select2-container--default .select2-search--dropdown .select2-search__field {
                    font-size: 13px;
                    font-family: "Poppins";
                    color: #333739;
                    border-bottom-color: #ffa50095 !important;
                }

                .select2-container--default .select2-results__option .select2-results__option {
                    padding-left: 1em;
                    font-size: 13px !important;
                }


                input[type=search]:not(.browser-default):focus:not([readonly]) {
                    box-shadow: none;
                }

                .select2-container--default .select2-results__option--highlighted[aria-selected] {
                    background-color: #005BAA;
                    color: white;
                    font-family: "Poppins", sans-serif;
                }

                .select2-results__message {
                    font-family: "Poppins", sans-serif;
                    font-size: 13px;
                }

                .select2-results__group {
                    display: none !important;
                }

                .select-wrapper svg {
                    display: block !important;
                    fill: #888888 !important;
                    height: 22px !important;
                    width: 22px !important;

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

                .select2-search__field {
                    margin-bottom: 0px !important;
                }


                .export-blue-btn {
                    background-color: #005baa;
                    border-radius: 100% !important;
                    color: #fff;
                    width: 108px !important;
                    height: 47px !important;
                    font-weight: bold;
                }

                .exp {
                    width: 100px !important;
                    height: 43px !important;
                }

                .button-class {
                    float: right;
                }

                .float-right {
                    float: right;
                }

                .card .padding-card {
                    padding: 4px 22px 0px 22px !important;
                    font-size: 14px !important;
                }
            </style>
            <script>
                $('.pickadate-clear-buttons').pickadate({
                    close: 'Close Picker',
                    formatSubmit: 'dd/mm/yyyy',


                });


            </script>
        </div>
    </div>
    <div id="pagination"></div>
    <input type="hidden" id="pgnum">

    <style>

        .page-link {
            font-weight: 600;
            font-family: "Poppins", sans-serif !important;
        }

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
        }


        .pagination button.active,
        .pagination span.active {
            background-color: #005baa;
            color: white;
            border: 1px solid #005baa;
            font-weight: normal;
        }

        .pagination .prev {
            background-color: #fff;
            border-radius: 0;
            padding: 5px 10px !important;
            margin: 0px 5px;
            color: #005baa;
        }

        .pagination .next {
            background-color: #fff;
            border-radius: 0;

            padding: 8px 10px 5px 10px !important;
            margin: 0px 5px;
            color: #005baa;
        }


        .next, .prev {
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
            height: 24px;
            background-image: url('../resourcesNew1/assets/Chevron-backward-blue.svg');
            background-size: contain;
            background-repeat: no-repeat;
            vertical-align: middle;
            margin: 0px 6px 3px 1px !important;

        }

        .right-arrow {
            display: inline-block;
            width: 24px;
            height: 24px;
            background-image: url('../resourcesNew1/assets/Chevron-forward-blue.svg');
            background-size: contain;
            background-repeat: no-repeat;
            vertical-align: middle;
            margin: 0px 1px 3px 6px !important;
        }

        button.prev.disabled .left-arrow {
            display: inline-block;
            width: 24px;
            height: 24px;
            background-image: url('../resourcesNew1/assets/Chevron-backward-grey.svg');
            background-size: contain;
            background-repeat: no-repeat;
            vertical-align: middle;
            vertical-alignddle: middle;
            margin-right: 5px;
            cursor: not-allowed;
        }

        button.next.disabled .right-arrow {
            display: inline-block;
            width: 24px;
            height: 24px;
            background-image: url('../resourcesNew1/assets/Chevron-forward-grey.svg');
            background-size: contain;
            background-repeat: no-repeat;
            vertical-align: middle;
            margin-right: 5px;
            cursor: not-allowed;
        }

        .pagination {
            font-family: "Poppins", sans-serif !important;
        }

        .select-wrapper input.select-dropdown {
            color: #A7A7A7;
            font-size: 13px !important;
            font-family: "Poppins", sans-serif !important;
        }

        .text-white strong {
            font-family: "Poppins", sans-serif !important;
            font-weight: 600 !important;
        }

        .select2-results__options {
            font-family: "Poppins", sans-serif !important;
        }

        .amount-field {
            text-align: right !important;
        }

        .right-padding {
            padding-right: 50px !important;
        }

    </style>

    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content padding-card">

                    <div class="table-responsive m-b-20 m-t-15">
                        <table id="data_list_table">
                            <thead>
                            <tr>
                                <th>Business Name</th>
                                <th>Merchant Id</th>
                                <th class="amount-field">Previous Balance(RM)</th>
                                <th class="amount-field right-padding">Settlement Amount(RM)</th>
                                <th>Settlement Date</th>
                                <th class="amount-field">Current Balance(RM)</th>

                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${paginationBean.itemList}" var="dto">
                                <tr>
                                    <td>${dto.businessName}</td>
                                    <td>${dto.merchantId}</td>
                                    <td class="amount-field">${dto.previousBalance}</td>
                                    <td class="amount-field right-padding">${dto.settlementAmount}</td>
                                    <td>${dto.settlementDate}</td>
                                    <td class="amount-field">${dto.runningBalance}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <div class="card border-radius">
                <div class="pagination" style="padding-bottom: 10px !important;padding-top: 10px !important;"></div>
            </div>
            <style>
                td, th {
                    padding: 10px 8px;
                    color: #707070;
                }

                thead th {
                    border-bottom: 1px solid #ffa500;
                    color: #4377a2;
                    font-weight: 600 !important;
                }
            </style>
        </div>
    </div>
</div>

<style>

    .page-link {
        font-weight: normal !important;
        font-size: 14px !important;
    }

    .crossbutton {
        margin-left: 10px;
        padding: 0;
        border: none;
        position: absolute;
        right: 7%;
        top: 17%;
        background-color: transparent;
        cursor: pointer;
    }

    .crossbutton:focus, .crossbutton:hover {
        background-color: transparent !important;
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

    /*.crossmark {*/
    /*    color: #c5c5c5;*/
    /*}*/

    /*.crossmark:hover {*/
    /*    color: red;*/
    /*}*/

    .crossbutton .crossmark {
        transition: 0.3s; /* Smooth transition */
    }

    .crossbutton:hover .crossmark {
        content: url('${pageContext.request.contextPath}/resourcesNew1/assets/red_cancel.svg'); /* Red SVG on hover */
    }

</style>
<script>


    $(document).ready(function () {
        $('select').formSelect();

        $('#export').on('change', function () {

            var selectedValue = $(this).val();


            var dropdownLabel = $(this).parent().find('input.select-dropdown');

            if (selectedValue === "") {
                dropdownLabel.css('color', '#A7A7A7');
            } else {
                dropdownLabel.css('color', '#333');
            }
        });
        $('#export').trigger('change');
    });


    $(document).ready(function () {
        console.log("null" + "${currentMerchant}");
        console.log(document.getElementById("merchantName").value)
        if ("${currentMerchant}" == null || "${currentMerchant}" === "") {
            const placeholder = document.querySelector(".select2-selection__rendered");
            placeholder.innerText = "Business name";
            placeholder.removeAttribute('title');


            if (placeholder.innerText == "Business name") {
                document.querySelector(".select2-container--default .select2-selection--single .select2-selection__rendered").style.color = '#A7A7A7';

            }

        } else {
            document.querySelector(".select2-selection__rendered").innerText = "${currentMerchant}";
            document.querySelector(".select2-selection__rendered").removeAttribute('title');

            const renderedElement = document.querySelector('.select2-selection__rendered');
            renderedElement.style.position = 'relative'
            if (renderedElement) {
                const button = document.createElement('button');
                button.setAttribute('type', 'button');
                button.id = 'crossBtn'
                button.style.marginLeft = '10px';
                button.classList.add('crossbutton');

                const img = document.createElement('img');
                img.classList.add('crossmark');
                <%--src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">--%>
                img.src = "${pageContext.request.contextPath}/resourcesNew1/assets/grey_cancel.svg" // Update this path to your actual SVG file location
                img.alt = 'Cancel';
                img.style.width = '14px'; // Adjust the size as needed
                img.style.height = '14px'; // Adjust the size as needed


                // const xmark = document.createElement('span');
                // xmark.classList.add('crossmark');
                // xmark.textContent = 'x';
                // xmark.style.padding = '4px 8px';
                // xmark.style.borderRadius = '20px';
                button.appendChild(img);
                renderedElement.appendChild(button);
            }

            $('#merchantName').on('select2:opening', function (e) {
                e.preventDefault();
            });
            document.getElementById('crossBtn').addEventListener('click', function () {
                document.location.href = "${pageContext.request.contextPath}/transaction/settlementDetails?businessName=''";
                document.getElementById('overlay').style.display = 'block';
            });
        }


        var rowsPerPage = 10;
        var rows = $('#data_list_table tbody tr');
        var rowsCount = rows.length;
        var pageCount = Math.ceil(rowsCount / rowsPerPage);
        var numbers = $('.pagination');
        var displayPages = 3;

        function renderPagination(currentPage) {
            numbers.empty();

            if (currentPage > 1) {
                numbers.append('<button class="prev"><span class="left-arrow"></span>Previous</button>');
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

            if (startPage > 1) {
                numbers.append('<button class="page-link">1</button>');
                if (startPage > 2) {
                    numbers.append('<span class="ellipsis">...</span>');
                }
            }

            for (var i = startPage; i <= endPage; i++) {
                if (i === currentPage) {
                    numbers.append('<button class="page-link active">' + i + '</button>');
                } else {
                    numbers.append('<button class="page-link">' + i + '</button>');
                }
            }

            if (endPage < pageCount) {
                if (endPage < pageCount - 1) {
                    numbers.append('<span class="ellipsis">...</span>');
                }
                numbers.append('<button class="page-link">' + pageCount + '</button>');
            }

            if (currentPage < pageCount) {
                numbers.append('<button class="next">Next<span class="right-arrow"></span></button>');
            } else {
                numbers.append('<button class="next disabled">Next<span class="right-arrow"></span></button>');
            }
        }

        function displayRows(pageNum) {
            var start = (pageNum - 1) * rowsPerPage;
            var end = start + rowsPerPage;
            rows.hide();
            rows.slice(start, end).show();
        }

        function updateNavigation(pageNum) {
            renderPagination(pageNum);
            displayRows(pageNum);
        }

        updateNavigation(1);

        numbers.on('click', 'button.page-link', function () {
            var pageNum = parseInt($(this).text(), 10);
            updateNavigation(pageNum);
        });

        numbers.on('click', '.prev', function () {
            var currentPage = parseInt(numbers.find('button.page-link.active').text(), 10);
            if (currentPage > 1) {
                updateNavigation(currentPage - 1);
            }
        });

        numbers.on('click', '.next', function () {
            var currentPage = parseInt(numbers.find('button.page-link.active').text(), 10);
            if (currentPage < pageCount) {
                updateNavigation(currentPage + 1);
            }
        });
    });

</script>


</body>

</html>

