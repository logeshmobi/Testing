

<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body style="min-height:900px">
<form action="###" method="post">
<div>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Suspend MobileUser
          </h1>  
       </section>
   </div>   
	<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class=""><a href="#" data-toggle="tab">Suspension Details</a>
				<div class="nav-arrow"></div></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
        </ul>
	</div>
 <div class="col-xs-9">
			<div class="row">
				<div class="panel panel-default">
				<div class="panel-heading">
						<h3 class="panel-title">Status:Successful</h3>
					</div>
					<div class="panel-body">
                        <div class="form-body">
							<div class="form-group">
								<div class="col-md-4"><b>Effective Date:</b>${mobileuserStatusHistory.createdDate} </div><br>
								<div class="col-md-5"><b>Reason:</b>${mobileuserStatusHistory.reason} </div><br>
								<div class="col-md-5"><b>Description:</b>${mobileuserStatusHistory.description}</div>
							   </div>
							</div>

						</div>
					</div>
				</div>
 </div>
 
<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div style="font-size:20px;">What you should do:</div>
		
		<input style="width:30%"class="btn btn-primary" value="Email">
		<input style="width:30%" class="btn btn-primary" value="Print">
		</div>
		</div>
	</div>
</form>
    
    




<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 
  <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Edit A MobileUser
          </h1>  
       </section>
       
 </head>    
<form action="/mobileUserViewDetails" method="post">
<div>
	
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Mobile User Detail</a>
				<div class="nav-arrow"></div></li>
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">Review & Confirm</a></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
	
	<div class="pull-left h4">Status: Successful</div>
	<div class="pull-left padding-10">
	<div>Your request to edit mobile user for the following is successful</div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Mobile User ID: ${mobileUser.username }</div>
	</div>
	
	
<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="View Mobile User Details"></div>
		</div>
	</div>
</div>
</div>
</form>
</html>
