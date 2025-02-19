<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.NonMerchantWebPromotionController"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant/");
</script>
<script type="text/javascript">

function load1()
{
var url = "${pageContext.request.contextPath}/promotionwebNonmerchant/list";
$(location).attr('href',url);
}

function load()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to Add Email File Details",
      		text: "it will be added..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false,
      	   confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger',
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
      		
      			
      			
      			//swal("Added!", "Your agent details added","success");
      			 $("#form-add").submit();
      			
      			
      		} else {
      			/* swal("Cancelled", "Your agent details not added", "error"); */
      			 var url = "${pageContext.request.contextPath}/promotionwebNonmerchant/list";
 $(location).attr('href',url);
      			//return true;
      		}
      	});
    //  });
 
}
</script>

<body >



<form:form action="merchtCustMailReviewAndConfirm" method="post" 
commandName="merchtCustMail" id="form-add" name="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

  <div class="content-wrapper">
			<div class="row">

				<div class="col-md-12 formContianer">
					<h3 class="card-title">Cust MailUpload </h3>

					<div class="card">
<!-- <div class="card-body formbox"> -->


						<div class="row">
						<%--  	<tr>
					<td> MerchantLogo</td>
					<td><input type="hidden" class="form-control" id="imageFile"
							placeholder="imageFile" name="imageFile" value="${merchtCustMail.imageFile}" /></td>
							
							
							<td>"${merchtCustMail.merchantLogo}"</td>
							
							<img alt="${merchtCustMail.merchantLogo}" src="< value="${merchtCustMail.merchantLogo}>">
							<img src="${merchtCustMail.merchantLogo}"/> width="150"
							
							<td valign="top" style="padding-top: 5px;"><img width ="100"  src="data:image/jpg;base64,<c:out value='${merchtCustMail.mLogo}'/>" /></td>
						<td valign="top" style="padding-top: 5px;"><img width="150" src="data:image/jpg;base64,<c:out value='${merchtCustMail.mLogo}'/>" /></td>
							
							
							
				</tr>	 --%>
				
				<div class="row">
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="Address1">Business Name</label>
									<input type="text" class="form-control" id="merchantName"
										placeholder="merchantName" name="merchantName" path="merchantName"  value="${merchtCustMail.merchantName}" readonly="readonly"/>
								</div>
							</div>
				
				
				
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="merchantAddress">Business Address</label>
									<input type="text" class="form-control" id="merchantAddress"
										placeholder="merchantAddress" name="merchantAddress" path="merchantAddress"  value="${merchtCustMail.merchantAddress}" readonly="readonly"/>
								</div>
							</div>
							
							
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="contactNo">Contact Number</label>
									<input type="text" class="form-control" id="contactNo"
										placeholder="contactNo" name="contactNo" path="contactNo" value="${merchtCustMail.contactNo}" readonly="readonly"/>
								</div>
							</div></div>
							
							
							<div class="row">
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="email">Email</label>
									<input type="text" class="form-control" id="merchantName"
										placeholder="email" name="email" path="email"  value="${merchtCustMail.email}" readonly="readonly"/>
								</div>
							</div>
							
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="Mid">Mid</label>
									<input type="text" class="form-control" id="mid"
										placeholder="mid" name="mid" path="mid"  value="${merchtCustMail.mid}" readonly="readonly"/>
								</div>
							</div>
							<div class="col-xs-12 col-sm-4">
								<div class="form-group">
									<label for="Address1">Email List</label>
									<form:textarea  readonly="true" class="form-control" id="custMailList"   style="word-break:break-all;" rows="2" cols="2" maxlength="120"
										placeholder="custMailList" name="custMailList" path="custMailList"  value="${merchtCustMail.custMailList}" ></form:textarea>
								
								
								
										
								</div>
							</div>
							
							
				
				
			
			
				</div>
				</div>
				</div>
				</div>
				
				
						<button type="button" id="testing" class="btn btn-primary icon-btn" onclick="return load()">Confirm</button> 
						 <button type="button" class="btn btn-default icon-btn" onclick="return load1();">Cancel</button> 
						</div>
				</div>
					
						</form:form>	
							
				
	
	
 </body>
 </html>
						



	<%-- 	<tr>
					<td> MerchantLogo</td>
					<td><input type="hidden" class="form-control" id="imageFile"
							placeholder="imageFile" name="imageFile" value="${merchtCustMail.imageFile}" /></td>
							
							
							<td>"${merchtCustMail.merchantLogo}"</td>
							
							<td><img alt="${merchtCustMail.merchantLogo}" src="< value="${merchtCustMail.merchantLogo}>">
							<img src="${merchtCustMail.merchantLogo}"/> width="150"
							<img width="150" src="data:image/jpg;base64,<c:out value='${merchtCustMail.mLogo}'/>" />
							
							
							
				</tr>	 --%>

		
	

	