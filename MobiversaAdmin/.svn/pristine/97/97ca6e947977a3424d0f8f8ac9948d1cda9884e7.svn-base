<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "subagent/");
</script>
<%-- <form action="/agent1/user/detail/${item.id}" method="post"> --%>
<%-- <form action="${pageContext.request.contextPath}/subagent/detail/${subagent.id}" method="post">
 --%><div class="content-wrapper">
        
        
        <div class="row">
			<div class="col-md-6">
            <div class="card">
              <h3 class="card-title">Your request to Add SubAgent for the following is successful</h3>
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
		<%-- <a href="${pageContext.request.contextPath}<%=AgentSubMenuController.URL_BASE%>/detail/${subagent.id}" class="btn btn-primary">View Agent Detail</a>
		 --%>
		 
		 
		 <form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=AgentSubMenuController.URL_BASE%>/detail" > 
	 
	       <input type="hidden" name="id" value="${subagent.id}" />
	       <%--  <input type="text" name="id" value="${merchantPromo.id}" /> --%>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<button type="submit"  value="View Agent Detail" class="btn btn-primary">View Agent Detail</button>	
				</form>
	</div>	
	<!-- </form> -->
	
		

