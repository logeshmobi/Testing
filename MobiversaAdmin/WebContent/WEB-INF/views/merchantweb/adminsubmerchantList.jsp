<%@page import="com.mobiversa.payment.controller.AdminController" %>
<%@page import="com.mobiversa.common.bo.Merchant" %>
<%@page import="com.mobiversa.common.bo.MID" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.ResourceBundle" %>

<%
    ResourceBundle resource = ResourceBundle.getBundle("config");
    String actionimg = resource.getString("NEWACTION");
    String voidimg = resource.getString("NEWVOID");
    String refundimg = resource.getString("NEWREFUND");
    String eyeimg = resource.getString("NEWEYE");
%>
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sub Merchant Summary</title>

    <script lang="JavaScript">


        function loadSelectDatasubmerchant() {
            $("#overlay").show();
            var type = document.getElementById("type").value;
            if (type === '' || type.toUpperCase() === "ALL") {
                alert("please select a merchant to search ");
                $("#overlay").hide();
                return false;
            } else {
                document.location.href = '${pageContext.request.contextPath}/admin/adminsubmerchantsearch?type='
                    + type;
                form.submit;
            }
        }

        function loadExportdata() {
            var type = document.getElementById("type").value;
            var expt = document.getElementById("export").value;
            if (type === '' || type.toUpperCase() === "ALL") {
                alert("Please select a merchant to export ")
                return false;
            }else if (expt === '') {
                alert("please choose export type")
                return false;
            } else {
                document.location.href = '${pageContext.request.contextPath}/admin/adminsubmerchantexport?type='
                    + type + '&export=' + expt;
                form.submit();
            }
        }

        function loadSelectDatasubmerchant1() {
            $("#overlay").show();
            var type = document.getElementById("type").value;

            var PageNumber = document.getElementById("pgnum").value;
            console.log("Page no: " + PageNumber);
            console.log("type: " + type);

            // Get the selected option's value
            //var selectType = type.value;
            //console.log("Selected value:", selectType);
            if (type == 'All') {
                document.location.href = '${pageContext.request.contextPath}/admin/adminsubmerchant?type=' +
                    type + '&currPage=' + PageNumber;
            } else {
                document.location.href = '${pageContext.request.contextPath}/admin/adminsubmerchantsearch?type=' +
                    type + '&currPage=' + PageNumber;
            }

            form.submit;
        }

        function openDialog1(bussinesName, indexId) {

            document.getElementById("bussinessName").value = bussinesName;
            document.getElementById("index").value = indexId;
            var bussinessname = document.getElementById("bussinessName").value
            var indexId = document.getElementById("index").value

            document.location.href = '${pageContext.request.contextPath}/admin/submerchantSummaryDetails?bussinessname='
                + bussinessname + '&indexId=' + indexId;
            form.submit;
        }

        function loadSelectData() {
            //alert("test");
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

            var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/'
                + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
            var todateString = (today <= 9 ? '0' + today : today) + '/'
                + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

            //alert("test"+e + " "+e1);
            if (e == null || e == '' || e1 == null || e1 == '') {
                alert("Please Select date(s)");
                //form.submit == false;
            } else {
                //alert("test1212"+e + " "+e1);
                document.getElementById("dateval1").value = fromdateString;
                document.getElementById("dateval2").value = todateString;
                document.location.href = '${pageContext.request.contextPath}/admin/submersearch1?date='
                    + fromdateString + '&date1=' + todateString;
                //alert("test1212 "+document.getElementById("dateval1").value);
                //alert("test1212 "+document.getElementById("dateval2").value);
                form.submit;
                document.getElementById("dateval1").value = e;
                document.getElementById("dateval2").value = e1;

            }
        }

        function loadData(num) {
            var pnum = num;
            //alert("page :"+pnum);
            var e = document.getElementById("from").value;
            var e1 = document.getElementById("to").value;
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
            } else */
            if ((e == null || e == '') && (e1 == null || e1 == '')) {
                //alert('both $$ ##');
                document.location.href = '${pageContext.request.contextPath}/admin/submerchantSum1/'
                    + pnum;
                form.submit;
            } else {
                //alert("else : "+e+" "+e1);
                document.location.href = '${pageContext.request.contextPath}/admin/submersearch1?date='
                    + e + '&date1=' + e1 + '&currPage=' + pnum;

                //document.forms["myform"].submit();
                form.submit;// = true;

            }

        }

        //export changes start
        function loadDropDate13() {
            //alert("loadDropDate13");
            var e = document.getElementById("export").value;
            //alert("loadDropDate13 : "+ e);
            //var strUser = e.options[e.selectedIndex].value;
            document.getElementById("export1").value = e;
            //alert("data :" + e + " "+ document.getElementById("export1").value);

        }

        function loaddata() {
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

            var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/'
                + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
            var todateString = (today <= 9 ? '0' + today : today) + '/'
                + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
            //alert("e2" + e2);
            //alert("e1" + e1);
            //alert("e" + e);

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
                document.location.href = '${pageContext.request.contextPath}/admin/submerexport1?date='
                    + fromdateString
                    + '&date1='
                    + todateString
                    + '&export='
                    + e2;
                //alert(e);
                form.submit();

            }
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


<input type="hidden" id="bussinessName" name="bussinessName" value="0">
<input type="hidden" id="index" name="index" value="0">

<div class="test" id="pop-bg-color"></div>
<div class="container-fluid">
    <div class="row">


        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class="d-flex align-items-center">
                        <h3 class="text-white">
                            <strong> Sub Merchant Summary</strong>
                        </h3>
                    </div>


                </div>
            </div>
        </div>
    </div>

    <div class="modal fade bd-example-modal-xl pop-body"
         style="width: 500px !important; height: 270px !important;"
         id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="mySmallModalLabel" aria-hidden="true"
         style="text-align:center;">
        <div class="modal-dialog modal-xl">
            <div class="modal-content "
                 style="padding: 0 !important; margin: 0 !important;">
                <p class="pop-head"
                   style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">
                    Information</p>
                <img
                        src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
                        width="60px !important; height:60px !important;">
                <p id="innerText" style="font-size: 22px;"></p>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-dismiss="modal" id="close" onclick="closepopup()"
                            style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content padding-card">

                    <div class="d-flex align-items-center">
                        <h5>Select Merchant</h5>
                    </div>


                    <%--                    <div class="row">--%>
                    <%--                        <div class="input-field col s12 m3 l3">--%>
                    <%--                            <select id="type" name="type_1">--%>
                    <%--                                <option value="All">-- Choose --</option>--%>
                    <%--                                <option value="MNTX">MNTX</option>--%>
                    <%--                                <option value="DEPANSUM">DEPANSUM MALAYSIA SDN BHD</option>--%>
                    <%--                                <option value="ZTP">ZOTAPAY</option>--%>

                    <%--                            </select>--%>

                    <%--                        </div>--%>

                    <%--                        <div class="input-field col s12 m3 l3 export_div">--%>
                    <%--                            <select name="export" id="export" onchange="loadDropDate13()"--%>
                    <%--                                    style="width:100%">--%>
                    <%--                                <option selected value="">Choose</option>--%>
                    <%--                                <option value="EXCEL">EXCEL</option>--%>
                    <%--                            </select>--%>
                    <%--                            <label for="name">Export Type</label>--%>
                    <%--                            <input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>--%>


                    <%--                        </div>--%>


                    <%--                        <br>--%>
                    <%--                        <button type="button"--%>
                    <%--                                class="btn btn-primary curved-btn move-left" onclick="loadSelectDatasubmerchant()">--%>
                    <%--                            search--%>
                    <%--                        </button>--%>

                    <%--                        <button type="button"--%>
                    <%--                                class="btn btn-primary curved-btn move-left" onclick="loadExportdata()">Export--%>
                    <%--                        </button>--%>


                    <%--                    </div>--%>


                    <div class="row">
                        <div class="input-field col s12 m3 l3">
                            <%--							<select id="type" name="type_1">--%>
                            <%--								<option value="All" selected  style="color: #ddd !important;">-- Choose --</option>--%>
                            <%--								<c:forEach items="${paginationBean.itemList}" var="item">--%>
                            <%--									<option value="${item.mmId}">${item.mmId}</option>--%>
                            <%--								</c:forEach>--%>
                            <%--							</select>--%>
                                <select id="type" name="type_1">
                                    <option value="All" selected style="color: #ddd !important;">-- Choose --</option>
                                    <c:forEach items="${DefaultQuerySubMer}" var="item">
                                        <option value="${item}">${item}</option>
                                    </c:forEach>
                                </select>

                        </div>

                        <style>
                            .dropdown-content.select-dropdown {

                                width: 258.25px;
                                left: 0px;
                                top: 0px !important;
                                height: auto;
                                transform-origin: 0px 100%;
                                opacity: 1;
                                transform: scaleX(1) scaleY(1);
                            }
                        </style>


                        <div class="input-field col s12 m3 l3 export_div">
                            <select name="export" id="export" onchange="loadDropDate13()"
                                    style="width:100%">
                                <option selected value="">Choose</option>
                                <option value="EXCEL">EXCEL</option>
                            </select>
                            <label for="name">Export Type</label>
                            <input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>


                        </div>


                        <br>
                        <button type="button"
                                class="btn btn-primary curved-btn move-left" onclick="loadSelectDatasubmerchant()">search
                        </button>

                        <button type="button"
                                class="btn btn-primary curved-btn move-left" onclick="loadExportdata()">Export
                        </button>


                    </div>
                </div>
            </div>
        </div>


    </div>


    <input type="hidden" id="selectedValue" value="${type}">


    <%--
<div class="row">
<div class="col s12">
  <div class="card border-radius">
    <div class="card-content padding-card">




            <div class="row">
                <div class="input-field col s12 m3 l3">
                    <label for="from" style="margin:0px;"> From </label>
                    <input type="hidden"
                                name="date11" id="date11" <c:out value="${fromDate}"/>>
                                <input  id="from" type="text" class="validate datepicker"
                                onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'));">
                    <i class="material-icons prefix">date_range</i>
                </div>
                <div class="input-field col s12 m3 l3">

                    <label for="to" style="margin:0px;">To</label>
                    <label for="From_Date">To</label><input type="hidden"
                                name="date11" id="date11" <c:out value="${toDate}"/>>
                    <input id="to" type="text" class="datepicker"
                    onchange="loadDate(document.getElementById('to'),document.getElementById('date11'))">
                    <i class="material-icons prefix">date_range</i>
                </div>
            <!--    <div class="input-field col s12 m3 l3">
                    <select name="type" id="type" onchange="loadDropDatetype()"
                                >
                                <option selected value="">Choose</option>
                                <option value="MERCHANT">MERCHANT</option>
                                <option value="NON_MERCHANT">NON_MERCHANT</option>
                    </select>
                    <label for="name">Merchant Type</label>
                    <input type="hidden" name="type1" id="type1" <c:out value="${type}"/>>
                </div>   -->

                <div class="input-field col s12 m3 l3 export_div">
                    <select name="export" id="export" onchange="loadDropDate13()"
                                >
                                <option selected value="">Choose</option>
                            <!--    <option value="PDF">PDF</option> -->
                                <option value="EXCEL">EXCEL</option>
                    </select>
                    <label for="name">Export Type</label>
                    <input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>




                </div>

                <div class="input-field col 112" style="float:right !important;" >
                  <div class="button-class" >

                    <input type="hidden" name="date1" id="dateval1"> <input
                                type="hidden" name="date2" id="dateval2">
                    <button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button>
                    <input type="hidden" name="date1" id="dateval1"> <input
                                type="hidden" name="date2" id="dateval2">
                    <a class="export-btn waves-effect waves-light btn btn-round indigo" onclick="loaddata()">Export</a>
                 </div>
                 </div>
            </div>
            <style>


            .datepicker { width:80% !important;}
            .select-wrapper .caret { fill: #005baa;}
         .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}

            </style>
    </div>
  </div>
</div>
</div>
 --%>
    <script>
        $('.pickadate-clear-buttons').pickadate({
            close: 'Close Picker',
            formatSubmit: 'dd/MM/yyyy',

        });
        $('.datepicker').pickadate();
    </script>

    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content padding-card">


                    <div class="table-responsive m-b-20 m-t-15" id="page-table">
                        <table id="data_list_table"
                               class="table table-striped table-bordered">

                            <thead>
                            <tr>
                                <th>Activation Date</th>
                                <th>BusinessName</th>
                                <th>Email</th>
                                <th>City</th>
                                <th>State</th>
                                <th>MID</th>
                                <th>Main Merchant</th>
                                <th Style="text-align: center;">Summary</th>
                            </tr>

                            </thead>
                            <tbody>
                            <c:forEach items="${paginationBean.itemList}" var="merc" varStatus="loop">
                                <tr class="dto-${loop.index}">
                                    <td>${merc.createdBy}</td>
                                    <td>${merc.businessName}</td>
                                    <td>${merc.email}</td>
                                    <td>${merc.city}</td>
                                    <td>${merc.state}</td>
                                    <td>${merc.mobiId}</td>
                                    <td>${merc.mmId}</td>
                                    <%-- <td Style="text-align: center;">

                                        <a href="javascript:void(0)" id="openFpxslip"
                                           onclick="javascript: openDialog1('${merc.businessName}','${loop.index}')">
                                            <img class="w24"
                                                 src='data:image/png;base64,<%=actionimg%> '/>
                                        </a>
                                        <div class="form-group col-md-4" id="divviewer"
                                             style="display: none;">
                                            <div class="form-group">
                                                <div style="clear: both">
                                                    <iframe id="popOutiFrame" frameborder="0" scrolling="no"
                                                            width="800" height="600"></iframe>

                                                </div>

                                            </div>
                                        </div>


                                            <button class="btn btn-primary  w-10"
                                                                 type="submit"
                                                                onclick="openDialog1('${merc.businessName}','${loop.index}')">
                                                                Summary</button>
                                    </td> --%>

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

        .editBtn {
            text-decoration: none;
            color: #039be5;
            -webkit-tap-highlight-color: transparent !important;
            border: none;
            cursor: pointer;
            background: none;
        }

        #exampleModalCenter {
            z-index: 99;
            width: 25%;
            font-size: 24px;
            font-weight: 400;
            font-family: 'Poppins', sans-serif;
            text-align: center;
        }

    </style>


</div>


<script>
    // Get the value from the hidden input
    var selectedValue = document.getElementById('selectedValue').value;

    // Get the dropdown select element
    var dropdown = document.getElementById('type');

    // Loop through the options and set the selected attribute on the matching one
    for (var i = 0; i < dropdown.options.length; i++) {
        if (dropdown.options[i].value === selectedValue) {
            dropdown.options[i].selected = true;
            break;
        }
    }
    document.getElementById("pop-bg-color").style.display = "none";
    if (${paginationBean.itemList.size()}==0
    )
    {
        document.getElementById("exampleModalCenter").style.display = "block";
        document.getElementById("pop-bg-color").style.display = "block";
        document.getElementById("page-table").style.display = "none";
        document.getElementById("innerText").innerHTML = "Sorry, No Records Found";
        document.getElementById("innerText").style.fontWeight = "400";
        document.getElementById("innerText").style.color = "#171717";
        document.getElementById("nxt").style.cursor = "not-allowed";
        document.getElementById("nxt").style.opacity = "0.6";
        document.getElementById("nxt").disabled = "disabled";
    }

    function closepopup() {
        document.getElementById("exampleModalCenter").style.display = "none";
        document.getElementById("pop-bg-color").style.display = "none";
    }

</script>


<div id="pagination"></div>
<input type="hidden" id="pgnum">


<%--<script>--%>
<%--    /* * * * * * * * * * * * * * * * *--%>
<%--     * Pagination--%>
<%--     * javascript page navigation--%>
<%--     * * * * * * * * * * * * * * * * */--%>


<%--    function dynamic(pgNo) {--%>
<%--        /* alert("Page Number:"+pgNo); */--%>
<%--        document.getElementById("pgnum").value = pgNo;--%>
<%--        loadSelectDatasubmerchant1();--%>

<%--    }--%>

<%--    function previous(pgNo) {--%>
<%--        /* alert("Page Number:"+pgNo); */--%>
<%--        pgNo--;--%>
<%--        document.getElementById("pgnum").value = pgNo;--%>
<%--        loadSelectDatasubmerchant1();--%>

<%--    }--%>

<%--    function next(pgNo) {--%>
<%--        /* alert("Page Number:"+pgNo); */--%>
<%--        pgNo++;--%>
<%--        document.getElementById("pgnum").value = pgNo;--%>
<%--        loadSelectDatasubmerchant1();--%>
<%--    }--%>


<%--    var Pagination = {--%>

<%--        code: '',--%>

<%--        // ----------------------%>
<%--        // Utility--%>
<%--        // ----------------------%>

<%--        // converting initialize data--%>
<%--        Extend: function (data) {--%>
<%--            data = data || {};--%>
<%--            // Pagination.size = data.size || 300;--%>
<%--            //console.log(Pagination.size);--%>
<%--            // Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;--%>

<%--            Pagination.size = ((${paginationBean.currPage}) + 4) || 100;--%>
<%--            /* Pagination.page = data.page || 1; */--%>
<%--            Pagination.page =--%>
<%--            ${paginationBean.currPage} ||--%>
<%--            1;--%>
<%--            Pagination.step = ((data.step) - 4) || 3;--%>
<%--        },--%>

<%--        // add pages by number (from [s] to [f])--%>
<%--        Add: function (s, f) {--%>
<%--            for (var i = s; i < f; i++) {--%>
<%--                Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';--%>
<%--            }--%>
<%--        },--%>

<%--        // add last page with separator--%>
<%--        /*   Last: function() {--%>
<%--              Pagination.code += '<i>...</i>';--%>
<%--          },--%>
<%--           */--%>
<%--        Last: function () {--%>
<%--            Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">' + ((Pagination.page) + 1) + '</a>' + '<a onclick="dynamic(((Pagination.page)+2))">' + ((Pagination.page) + 2) + '</a>' + '<a onclick="dynamic(((Pagination.page)+3))">' + ((Pagination.page) + 3) + '</a>' + '<i>...</i>';--%>
<%--        },--%>

<%--        // add first page with separator--%>
<%--        First: function () {--%>
<%--            if (Pagination.page == 1) {--%>
<%--                Pagination.code += '<i>...</i>' + '<a onclick="dynamic(Pagination.page)">' + Pagination.page + '</a>';--%>

<%--            } else {--%>
<%--                Pagination.code += '<a>1</a>' + '<i>...</i>' + '<a onclick="dynamic(((Pagination.page)-1))">' + ((Pagination.page) - 1) + '</a>' + '<a onclick="dynamic(Pagination.page)">' + Pagination.page + '</a>';--%>
<%--            }--%>
<%--        },--%>


<%--        // ----------------------%>
<%--        // Handlers--%>
<%--        // ----------------------%>

<%--        // change page--%>
<%--        Click: function () {--%>
<%--            Pagination.page = +this.innerHTML;--%>
<%--            Pagination.Start();--%>
<%--            dynamic(page);--%>
<%--        },--%>

<%--        // previous page--%>
<%--        Prev: function () {--%>

<%--            Pagination.page--;--%>
<%--            if (Pagination.page < 1) {--%>
<%--                Pagination.page = 1;--%>
<%--            }--%>
<%--            Pagination.Start();--%>
<%--            dynamic(page);--%>
<%--        },--%>


<%--        // next page--%>


<%--        Next: function () {--%>
<%--            Pagination.page++;--%>
<%--            if (Pagination.page > Pagination.size) {--%>
<%--                Pagination.page = Pagination.size;--%>
<%--            }--%>
<%--            Pagination.Start();--%>
<%--            dynamic(page);--%>
<%--        },--%>


<%--        // ----------------------%>
<%--        // Script--%>
<%--        // ----------------------%>

<%--        // binding pages--%>
<%--        Bind: function () {--%>
<%--            var a = Pagination.e.getElementsByTagName('a');--%>
<%--            for (var i = 0; i < a.length; i++) {--%>
<%--                if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';--%>
<%--                a[i].addEventListener('click', Pagination.Click, false);--%>
<%--            }--%>
<%--        },--%>

<%--        // write pagination--%>
<%--        Finish: function () {--%>
<%--            Pagination.e.innerHTML = Pagination.code;--%>
<%--            Pagination.code = '';--%>
<%--            Pagination.Bind();--%>
<%--        },--%>

<%--        // find pagination type--%>
<%--        Start: function () {--%>
<%--            if (Pagination.size < Pagination.step * 2 + 6) {--%>
<%--                Pagination.Add(1, Pagination.size + 1);--%>
<%--            } else if (Pagination.page < Pagination.step * 2 + 1) {--%>
<%--                Pagination.Add(1, Pagination.step * 2 + 4);--%>
<%--                Pagination.Last();--%>
<%--            } else if (Pagination.page > Pagination.size - Pagination.step * 2) {--%>
<%--                Pagination.First();--%>
<%--                Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);--%>
<%--            } else {--%>
<%--                Pagination.First();--%>
<%--                Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);--%>
<%--                Pagination.Last();--%>
<%--            }--%>
<%--            Pagination.Finish();--%>
<%--        },--%>


<%--        // ----------------------%>
<%--        // Initialization--%>
<%--        // ----------------------%>

<%--        // binding buttons--%>
<%--        Buttons: function (e) {--%>
<%--            var nav = e.getElementsByTagName('a');--%>

<%--            nav[0].addEventListener('click', Pagination.Prev, false);--%>
<%--            nav[1].addEventListener('click', Pagination.Next, false);--%>
<%--        },--%>

<%--        // create skeleton--%>
<%--        Create: function (e) {--%>

<%--            var html = [--%>
<%--                '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button--%>
<%--                '<span></span>',  // pagination container--%>
<%--                '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button--%>
<%--            ];--%>

<%--            e.innerHTML = html.join('');--%>
<%--            Pagination.e = e.getElementsByTagName('span')[0];--%>
<%--            Pagination.Buttons(e);--%>

<%--            if (${paginationBean.currPage} == 1--%>
<%--        )--%>
<%--            {--%>
<%--                var previousButton = document.getElementById("previous");--%>
<%--                previousButton.style.pointerEvents = "none";--%>
<%--                previousButton.style.opacity = "0.5";--%>
<%--            }--%>

<%--        },--%>

<%--        // init--%>
<%--        Init: function (e, data) {--%>
<%--            Pagination.Extend(data);--%>
<%--            Pagination.Create(e);--%>
<%--            Pagination.Start();--%>
<%--        }--%>
<%--    };--%>


<%--    /* * * * * * * * * * * * * * * * *--%>
<%--    * Initialization--%>
<%--    * * * * * * * * * * * * * * * * */--%>

<%--    var init = function () {--%>
<%--        Pagination.Init(document.getElementById('pagination'), {--%>
<%--            size: 100, // pages size--%>
<%--            page: 1,  // selected page--%>
<%--            step: 3   // pages before and after current--%>
<%--        });--%>
<%--    };--%>

<%--    document.addEventListener('DOMContentLoaded', init, false);--%>
<%--</script>--%>


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
        loadSelectDatasubmerchant1();

    }

    function previous(pgNo) {
        /* alert("Page Number:"+pgNo); */
        pgNo--;
        document.getElementById("pgnum").value = pgNo;
        loadSelectDatasubmerchant1();

    }

    function next(pgNo) {
        /* alert("Page Number:"+pgNo); */
        pgNo++;
        document.getElementById("pgnum").value = pgNo;
        loadSelectDatasubmerchant1();
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