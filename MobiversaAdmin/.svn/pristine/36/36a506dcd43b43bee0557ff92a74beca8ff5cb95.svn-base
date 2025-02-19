<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
</head>
<body class="">
<div>
 <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Add A AgentUser
          </h1>  
       </section>
       </div>
       
       <div>
       <section class="content">
<form:form  action="reviewandconfirm" method="post"  commandName="agent">
 
<table class="table table-striped"> 
<tr>
<td>FirstName</td>&nbsp;&nbsp;
<td><form:input type="text" path="firstName"/>
</td>
<td>LastName</td>&nbsp;&nbsp;
<td><form:input type="text" path="lastName"/>
</td>
</tr>
 <tr>
 
<td>Address1</td>&nbsp;&nbsp;
<td><form:input type="textarea" path="addr1"/>

</td>
<td>Address2</td>&nbsp;&nbsp;
<td><form:input type="textarea" path="addr2"/>

<tr>
<td>City</td>&nbsp;&nbsp;
<td><form:input type="text" path="City"/>
</td>
<td>postCode</td>&nbsp;&nbsp;
<td><form:input type="text" path="postCode"/>
</td>
 <tr>
<td>PhoneNumber</td>&nbsp;&nbsp;
<td><form:input type="text" path="phoneNo"/>
</td>
 <td>BankName</td>&nbsp;&nbsp;
<td><form:input type="text" path="bankName"/>
</td>
</tr>
 
<tr>
<td>BankAccunt</td>&nbsp;&nbsp;
<td><form:input type="text" path="bankAcc"/>
</td>
<%--  <td>AG_mailId</td>&nbsp;&nbsp;
<td><form:input type="text" path="mailId"/>
</td>  --%>
</tr> 
</table>
 </form:form>

</section>
</div>
 
 <%-- 
<div>
<div class=""><b>Agent User Details</b></div><br>
</div>
<div class="form-horizontal" > 
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">User Name:</label>
<div class="col-md-1">
<form:input type="textarea"  path="username"/>
</div>
</div>
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">First Name:</label>
<div class="col-md-1">
<form:input type="text"  path="firstName"/>
</div>
</div>
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">Last Name:</label>
<div class="col-md-1">
<form:input type="text"  path="lastName"/>
</div>
</div>
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">Password:</label>
<div class="col-md-1">
<form:input type="password"  path="password"/>
</div>
</div>
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">E-mail:</label>
<div class="col-md-1">
<form:input type="email"  path="email"/>
</div>
</div>	 
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">Role:</label>
<div class="col-md-1">
<form:select path="role">
  <option value="<%=AgentUserRole.AGENT_USER.name() %>">BANK_ADMIN</option>
   <option value="<%=AgentUserRole.AGENT_USER.name() %>">BANK_USER</option>
  <form:option value="<%=AgentUserRole.AGENT_USER.name() %>">Bank_Admin</form:option>
  <form:option value="<%=AgentUserRole.AGENT_USER.name() %>">Bank_User</form:option></form:select>
</div>
</div>
</div>	--%>
	
 <div class="form-group"> 
<div>
 <input style="float:left:30px;"type="submit" class="btn btn-primary" value="submit"></div>
  </div> 

</body>
</html>