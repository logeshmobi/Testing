<%@page
	import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>

<html lang="en-US">
<head>

	  <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	
		<!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	
</head>


<style>
#merchantName:hover {
	color: 275ca8;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

.w24 {
	width: 24px;
}

.hide_key {
	display: none;
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





.key_hover:hover {
	cursor: pointer;
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
<script type="text/javascript">

//Fpx Sales Slip - End



    </script>


<script lang="JavaScript">

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
							<h3 class="text-white">
								<strong>BOOST Transaction Summary</strong>
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
								<label for="from" style="margin: 0px;">From </label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="fromDate" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
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

							<div class="input-field col s12 m3 l3">
								<input type="hidden" name="export1" id="export1"
									<c:out value="${status}"/>> <select name="export"
									id="export" onchange="return loadDropDate13();">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
								</select> <label class="control-label">Export Type</label>
							</div>

							<div class="input-field col s12 m3 l3">
								<div class="button-class" style="float: left;">

									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2">
									<button class="btn btn-primary icon-btn" type="button"
										onclick="return loadSelectData();">Search</button>


									<input type="hidden" name="dateex1" id="datevalex1"> <input
										type="hidden" name="dateex2" id="datevalex2">
									<button class="btn btn-primary icon-btn" type="button"
										onclick="return loadExpData();">Export</button>
								</div>
							</div>

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
      <!-- <input type="hidden" id="TXNType1" > -->
      <script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
         
       //  var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/
         
         if(${paginationBean.itemList.size()}==0){
         document.getElementById("exampleModalCenter").style.display ="block";
          document.getElementById("pop-bg-color").style.display ="block";
          document.getElementById("page-table").style.display ="none";
          document.getElementById("searchBoxDiv").style.display ="none";
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