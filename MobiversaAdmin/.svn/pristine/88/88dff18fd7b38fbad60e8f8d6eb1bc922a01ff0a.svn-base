<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<%--  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script> --%>
<%-- <form action="${pageContext.request.contextPath}/subagent/detail" method="post">
 --%>
<div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Edit SubAgent for the following is successful</h3>
              <table class="table table-striped">
						<tbody>
						<tr>
						<td>SubAgent Name</td>
						<td>${subagent.name}</td>
						</tr>
						<tr>
						<td>Agent Code</td>
						<td>${subagent.code}</td>
						
					</tr>
					</tbody>
					</table>
					</div>
				
						
					</div>
					</div>
					<%-- <input type="hidden" name="id" value="${subagent.id}"/>
					<input type="submit" value="View Agent Detail" class="btn btn-primary"/> --%>
					
					
			<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}/<%=AgentSubMenuController.URL_BASE%>/detail" > 
	 
	
	       <input type="hidden" name="id" value="${subagent.id}" />
	       
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<input type="submit" value="View Agent Detail" class="btn btn-primary"/>
				</form>
		<%-- <a href="${pageContext.request.contextPath}/<%=AgentSubMenuController.URL_BASE%>/detail/${subagent.id}" class="btn btn-primary">View Agent Detail</a>
		 --%>
		</div>
		<!-- </form> -->
		
		
	
	
	



