<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.common.bo.BankUserRole"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!--add a merchant block start here  -->
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
           Edit Merchant Details
          </h1>  
       </section>
   </div>
<div><b>BankUser Details</b></div><br>
<div>
<form method="post" action="<%=AdminController.URL_BASE%>/editBankuser" >
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="id" value="${bankUser.id}" />

<div class="form-horizontal" >  
<div class="form-group">
<label class="col-md-2 control-label">BankUser Name</label>
<div class="col-md-4">
<input  type="text" name="username" <c:out value="${bankUser.username}"/> />
</div></div>

	<div class="form-group">
<label class="col-md-2 control-label">First Name:</label>
<div class="col-md-4">
<input  type="text" name="firstName" <c:out value="${bankUser.firstName}"/> />
</div></div>	

	<div class="form-group">
<label class="col-md-2 control-label"> Last Name:</label>
<div class="col-md-4">
<input  type="text" name="lastName" <c:out value="${bankUser.lastName}"/> />
</div></div>

	<div class="form-group">
<label class="col-md-2 control-label">Role:</label>
<div class="col-md-4">
<select name="role">
  <option value="<%=BankUserRole.BANK_ADMIN.name() %>">BANK_ADMIN</option>
   <option value="<%=BankUserRole.BANK_USER.name() %>">BANK_USER</option>
  
</select>
</div></div>
<div class="form-group">
<label class="col-md-2 control-label">Email Address:</label>
<div class="col-lg-6">
<input  type="text" name="email" <c:out value="${bankUser.email}"/> />
</div></div>
 
 <div class="form-group col-xs-2"> 
<div><input type="submit" class="btn btn-primary" value="submit"></div>
</div>

</form>
</div>
	
	 
		
		