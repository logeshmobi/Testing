
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
 
<style>
.td {
	text-align: right;
}

@midea screen(max-width:40%)
</style>

</head>

<body >

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>TestMQEmail</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
 
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
 
 <form method="POST" id="form1" name="form1" 
		action="${pageContext.request.contextPath}/transaction1/SendMQEmail" >
	
	
		

									<div class="row">

										<div class="input-field col s12 m6 l6 ">
												
												<input type="text" id="toAddress" value=""
													name="toAddress" placeholder="Enter name" />
												<label>ToAddress</label>

											</div>
										
										
										<div class="input-field col s12 m6 l6 ">
												
												<select name="type" id="type">
												<option value="merchantReg">TestMerchantReg</option>
												<option value="deviceReg">DeviceReg</option>
												<option value="reset">Reset</option>
												</select>
												<label class="control-label" style="">Email Type</label>
											</div>
										</div>
									
									



<div class="row">
						<div class="input-field col s12 m6 l6 ">

								
								<button class="btn btn-primary blue-btn" type="submit" onclick="return submitForm1();">Submit</button>
<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>

							</div>
						</div>
					</div>

</div>


</div>




					
					</form>
					</div></div>
					</div></div>
					</div>

		

</body>




</html>

