<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
</head>

<body>
<div style="overflow:auto;border:1px;width:100%">
<div class="content-wrapper">

  <div class="row" >
			<div class="col-md-6" >
            <div class="card" style="width: 50rem;">
              <h3 class="card-title">SubAgent Details</h3>
              <table class="table table-striped">
						
						<tbody>
						<tr>
						<td>Email</td>
						<td>${subagent.mailId}</td>
						</tr>
						<tr>
						<td>AgCode</td>
						<td>${subagent.code}</td>
						</tr>
						<tr>
						<td>Salutation</td>
						<td>${subagent.salutation}</td>
						</tr>
						<tr>
						<td>Name</td>
						<td>${subagent.name}</td>
						</tr>
						<tr>
						<td>Address</td>
						<td>${subagent.addr1}</td>
						</tr>
						<tr>
						<td>Address2</td>
						<td>${subagent.addr2}</td>
						</tr>
						<tr>
						<td>City</td>
						<td>${subagent.city}</td>
						</tr>
						<tr>
						<td>State</td>
						<td>${subagent.state}</td>
						</tr>
						<tr>
						<td>PostCode</td>
						<td>${subagent.postCode}</td>
						</tr>
						<tr>
						<td>PhoneNo</td>
						<td>${subagent.phoneNo}</td>
						
						</tr>
						<tr>
						<td>AgType</td>
						<td>${subagent.type}</td>
						</tr>
						
						
						
						</tbody>
						</table></div>
						
						
					<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=AgentSubMenuController.URL_BASE%>/edit" > 
	 
	       <input type="hidden" name="id" value="${subagent.id}" />
	       <%--  <input type="text" name="id" value="${merchantPromo.id}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<button type="submit"  value="Edit Details" class="btn btn-primary">Edit Details</button>	
				</form>
					
			<%-- <a href="${pageContext.request.contextPath}/<%=AgentSubMenuController.URL_BASE%>/edit/${subagent.id}" class="btn btn-primary">Edit Details</a>
			 --%>
			</div>
		</div> 
		</div>				
						</div>
	