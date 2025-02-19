<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing user: ${bankUser.username }</div>
		<div class="pull-right">
			<input name="search" type="button" value="Switch BankUser" class="btn btn-default" />
			<div class="clearfix"></div>
		</div>
	</div>


	<div class="col-xs-12 padding-10">
		<div class="h2">Suspend BankUser </div>
	</div>

	<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="active"><a href="#" data-toggle="tab">Suspension Detail</a>
				<div class="nav-arrow"></div></li>
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">All Done</a></li>

		</ul>
	</div>

	<form method="post" action="/admin/suspend/doSuspend" id="form-suspend">
		<input type="hidden" name="id" value="${bankUser.id }"/>
	    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="col-xs-12">
			<div class="row">
				<div class=" panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Suspension Detail</h3>
					</div>
					<div class="panel-body">
                            <c:choose>
							<c:when test="${empty errorList }">
								<div class="Metronic-alerts alert alert-danger" style="display: none"></div>
							</c:when>
							<c:otherwise>
								<div class="Metronic-alerts alert alert-danger fade in">
									<button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
									<ul>
										<c:forEach items="${errorList}" var="error">
											<li><c:out escapeXml="true" value="${error}" /></li>
										</c:forEach>
									</ul>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="form-body">
							<div class="form-group">
								<label class="col-md-3 control-label">Effective Date: </label>
								<div class="col-md-9">
									<p class="form-control-static">
										<fmt:formatDate type="date" value="${now}" />
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-3 control-label">Reason: </label>
								<div class="col-md-9">
									<select class="form-control" name="reason">
										<c:forEach items="${reasonList}" var="reason">
											<c:choose>

												<c:when test="${bankUserStatusHistory.reason == reason }">
													<option selected value="<c:out escapeXml="true" value="${reason }"/>"><c:out escapeXml="true" value="${reason}" /></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out escapeXml="true" value="${reason }"/>"><c:out escapeXml="true" value="${reason}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-md-3 control-label"></label>
								<div class="col-md-9">
									<textarea class="form-control" rows="3" maxlength="250" name="description"><c:out escapeXml='true'
											value="${bankUserStatusHistory.description}" /></textarea>
									<span class="help-block">Max Characters: 250</span>
								</div>
							</div>

						</div>
					</div>
				</div>

				<div class="padding-top-10">
					<input type="submit" class="btn btn-default pull-right" value="Submit">
				</div>
			</div>
		</div>
	</form>
</div>