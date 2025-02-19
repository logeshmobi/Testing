<%@page
	import="com.mobiversa.payment.controller.SettlementUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
td, th {
	padding: 7px 8px;
	color: #707070;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}
</style>
<style>
#merchantName:hover {
	color: 275ca8;
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
<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>



<script lang="JavaScript">
	function loadSelectData() {

		var e = document.getElementById("from").value;

		if (document.getElementById("csvFile").checked) {

			var u = document.getElementById("csvFile").value;

		} else {
			var u = "No";
		}

		if (document.getElementById("merFile").checked) {

			var me = document.getElementById("merFile").value;

		} else {
			var me = "No";
		}

		var fromDate = new Date(e);

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();
		var fromdateString = fromyear + '-'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
				+ (fromday <= 9 ? '0' + fromday : fromday);

		if (e == null || e == '') {
			alert("Please Select date");

		} else {

			document.getElementById("dateval1").value = fromdateString;

			document.location.href = "${pageContext.request.contextPath}/settlementDataUser/FpxgenerateFileCall?csvFile="
					+ u + "&merFile=" + me + "&date=" + fromdateString;

			form.submit;

		}
	}

	function loadDate(inputtxt, outputtxt) {

		var field = inputtxt.value;
		outputtxt.value = field;

	}
</script>
<body class="">

	<%-- <form:form method="POST" id="form1" action="${pageContext.request.contextPath}/settlementDataUser/generateFileCall" name="form1" > --%>
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>FPX File Generation</strong>
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

							<div class="input-field col s12 m6 l6 ">
								CSV FILE :
								<p>
									<label><input type="radio" name="csvFile"
										path="csvFile" value="Yes" id="csvFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="csvFile" value="No"
										id="csvFile" path="csvFile" /> <span>No</span></label>
								</p>

							</div>

							<div class="input-field col s12 m6 l6 ">
								AMBANK FILE :
								<p>
									<label><input type="radio" name="merFile"
										path="merFile" value="Yes" id="merFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="merFile" value="No"
										id="merFile" path="merFile" /> <span>No</span></label>
								</p>

							</div>


						</div>

						<div class="row">
							<%-- <div class="input-field col s12 m6 l6 ">
								<input type="hidden" name="stPeriod1" id="stPeriod1"
									value="${stPeriod}"> <select name="stPeriod"
									id="stPeriod" onchange="return loadDropDate11();">
									<option selected value="">Choose</option>
									<option value="1">1</option>
									<option value="3">3</option>
									<option value="5">5</option>
								</select> <label for="tid">Settlement Period</label>

							</div> --%>


							<div class="input-field col s12 m3 l3">
								<label for="from" style="margin: 0px;">Payment Date</label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="date1" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>


							</div>

						</div>


						<div class="row">
							<div class="input-field col s12 m6 l6 ">
								<div class="button-class">
									<input type="hidden" name="date1" id="dateval1">
									<button class="submitBtn" type="button"
										onclick="loadSelectData()">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>


	</div>



</body>

</html>