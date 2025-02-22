<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
</head>
<style>


</style>
<body>
 <div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Edit Merchant Details </strong> </h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
	
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
             <div class="table-responsive m-b-20 m-t-15">
            <table class="">
						<tbody>
						<tr>	
						
						<td>Mid</td>
						<td>${merchant.mid.mid}</td>
						
						</tr>
						<tr>	
						
						<td>EZYMOTO Mid</td>
						<td>${merchant.mid.motoMid}</td>
						
						</tr>
						<tr>	
						
						<td>EZYWAY Mid</td>
						<td>${merchant.mid.ezywayMid}</td>
						
						</tr>
						<tr>	
						
						<td>EZYREC Mid</td>
						<td>${merchant.mid.ezyrecMid}</td>
						
						</tr>
						<tr>	
						
						<td>EZYPASS Mid</td>
						<td>${merchant.mid.ezypassMid}</td>
						
						</tr>
				<tr>	
						
						<td>UM_Mid</td>
						<td>${merchant.mid.umMid}</td>
						
						</tr>
						<tr>	
						
						<td>UM_EZYMOTO Mid</td>
						<td>${merchant.mid.umMotoMid }</td>
						
						</tr>
						<tr>	
						
						<td>UM_EZYWAY Mid</td>
						<td>${merchant.mid.umEzywayMid }</td>
						
						</tr>
						<tr>	
						
						<td>UM_EZYREC Mid</td>
						<td>${merchant.mid.umEzyrecMid }</td>
						
						</tr>
						<tr>	
						
						<td>UM_EZYPASS Mid</td>
						<td>${merchant.mid.umEzypassMid }</td>
						
						</tr>
						<tr>	
						
						<td>Email</td>
						<td>${merchant.email}</td>
						
						</tr>
						
						
						<tr>	
						
						<td style="height:30%;width:30%">Registered Name</td>
						<td>${merchant.businessShortName}</td>
						
						</tr>
						
						
						
						<tr>	
						
						<td>Business Name</td>
						<td>${merchant.businessName}</td>
						
						</tr>
						
						
						
						<tr>	
						
						<td>BusinessReg No</td>
						<td>${merchant.businessRegistrationNumber}</td>
						
						</tr>
			
			
			
			<tr>	
						
						<td>Registered Address	</td>
						<td>${merchant.businessAddress1}</td>
						
						</tr>	
						
						
						
							<tr>	
						
						<td>Business Address</td>
						<td>${merchant.businessAddress2}</td>
						
						</tr>	
						
						
						
						
							<tr>	
						
						<td>Business City	</td>
						<td>${merchant.city}</td>
						
						</tr>		
						
						
						
							<tr>	
						
						<td>Business State	</td>
						<td>${merchant.state}</td>
						
						</tr>	
						
						
						<tr>	
						
						<td>Business PostCode	</td>
						<td>${merchant.postcode}</td>
						
						</tr>
						<tr>	
						
						<td>Bank OTP</td>
						<td>${merchant.auth3DS}</td>
						
						</tr>	
						</tbody>
					</table>
					</div>
					
			<a href="${pageContext.request.contextPath}/<%=MerchantWebController.URL_BASE%>/edit/${merchant.id}" class="export-btn waves-effect waves-light btn btn-round indigo">Edit Details</a>
			<a href="${pageContext.request.contextPath}/<%=MerchantWebController.URL_BASE%>/ChangePasswordByAdmin/${merchant.id}" class="btn btn-primary blue-btn">Reset Password</a>
				
					</div>
				<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
				
				.addUserBtn,.addUserBtn:hover {
				background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>	
				</div> 	
										
             </div>
           
            
             
            </div> </div>
     </body>
     </html>
     

		
