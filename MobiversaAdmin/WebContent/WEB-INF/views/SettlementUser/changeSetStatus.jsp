<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.SettlementUserController"%>
<%@page import="com.mobiversa.common.bo.SettlementMDR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}


</style>
</head>

<script lang="JavaScript">

	function loadSelectData()
	{
               
		document.getElementById('form1').submit();

	}

	

</script>

<body>
	<form:form method="post" action="settlementDataUser/updateStatus/${rrn}" commandName="setData"
		name="form1" id="form1">
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Settlement Status</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for=name>MID</label>
									<input type="text" id="mid"
										placeholder="mid" name="mid" path="mid" 
										 value= "${setData.mid}" readonly="readonly"/>
								</div>
					
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">Txn Amount</label>
									<input type="text" id="txnAmount"
										placeholder="txnAmount" name="txnAmount" path="txnAmount" 
										 value= "${setData.txnAmount}" readonly="readonly"/>
								</div>
								
								<div class="input-field col s12 m6 l6 ">
									<label for=name>Host MDR</label>
									<input type="text" id="hostMdrAmt"
										placeholder="hostMdrAmt" name="hostMdrAmt" path="hostMdrAmt" 
										 value= "${setData.hostMdrAmt}" readonly="readonly"/>
								</div>
								
							<div class="input-field col s12 m6 l6 ">
									<label for=name>Mobi MDR</label>
									<input type="text" id="mobiMdrAmt"
										placeholder="mobiMdrAmt" name="mobiMdrAmt" path="mobiMdrAmt" 
										 value= "${setData.mobiMdrAmt}" readonly="readonly"/>
								</div>
								
							<div class="input-field col s12 m6 l6 ">
									<label for=name>Net Amount</label>
									<input type="text" id="netAmount"
										placeholder="netAmount" name="netAmount" path="netAmount" 
										 value= "${setData.netAmount}" readonly="readonly"/>
								</div>
							
							
					<!--  		<div class="input-field col s12 m6 l6 ">
									<label for="status">Status</label>
									<form:input type="text" class="" id="status"
										placeholder="status" name="status" path="status" />
							</div>
							-->
						
                            <div class="input-field col s12 m6 l6 ">
                            <form:select path="status" class="form-control" value="${Status1}" name="status">
                            <form:option value="H">HOLD</form:option>
                            <form:option value="S">SETTLE</form:option>
                            </form:select>
                            <label for="name">Status</label>
                            </div>

							
							
							
					
				

						<div class="row">
						<div class="input-field col s12 m6 l6 ">
						<div class="button-class" >
						<button class="submitBtn" type="submit"
					onclick=" return loadSelectData()">Submit</button>
					</div></div></div>
</div>
	<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
				.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}
				</style>			


				</div>
			</div>
		</div>

	</div>
</div>
</form:form>
</body>
</html>
