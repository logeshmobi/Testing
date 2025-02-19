<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<title>Status Check</title>


<style>
h3 {
	font-size: 30px;
	line-height: 110%;
	margin: 0;
}

.invoiceidtext {
	font-size: 18px !important;
}

.statuscheckbtn {
	padding: 8px 22px;
	border-radius: 25px;
	background-color: #005baa;
	border: 2px solid #005baa !important;
	color: #fff;
	cursor: pointer;
}

.invoiceidfield {
	display: flex !important;
	/*  flex-direction: row !important; */
	align-items: flex-end !important;
}

}
.btnfield {
	height: 100% !important;
}

.responsecontent {
	flex-direction: column !important;
	align-items: stretch !important;
	justify-content: space-between !important;
}

.responsedata {
	margin-bottom: 0px !important;
	margin-top: 10px !important;
}

input::placeholder {
	color: #9e9e9e !important;
}

h5 {
	color: #333 !important;
	font-weight: 500 !important;
}

.responsedatacontent {
	margin-left: 3px !important
}
</style>

</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Status Check</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col s12">
					<div class="card blue-bg text-white">
						<div class="card-content status-check-cardcontent">
							<div class="d-flex lign-items-center">
								<h3 class="text-white">
									<strong>Check Shopee Pay Transaction Status</strong>
								</h3>
							</div>

							<div class="row invoiceidfield">
								<div class=" col s12 m5 l5 ">
									<br> <label for="invoiceid" class="invoiceidtext">Invoice ID
										</label> <br> <input type="text"
										placeholder="000678584893821" name="invoiceid"
										id="invoiceid" value="">
								</div>

								<div class="col s12 m6 l6 offset-m1 offset-l1 btnfield">
									<button class="statuscheckbtn" type="button">
										<font color="white">Check</font>
									</button>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col s12">
					<div class="card blue-bg text-white">
						<div class="card-content d-flex responsecontent">
							<div class="d-flex align-items-center">
								<h3 class="text-white mb-2">
									<strong>Response</strong>
								</h3>

							</div>

							<div class="responsedatacontent">
								<h5 class="responsedata">"Interruption from host side,
									Please try again.."</h5>
							</div>
						</div>
					</div>
				</div>


			</div>
</body>
</html>