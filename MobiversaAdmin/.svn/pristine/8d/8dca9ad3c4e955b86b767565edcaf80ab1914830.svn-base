<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
     <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">


<script type="text/javascript">
function load()
{
	
// $('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to edit this SubAgent Details",
      		text: "it will be edited..!",
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
      			$("#form-edit").submit();
      			
      			
      		} else {
      			/* swal("Cancelled", "Your agent details not added", "error"); */
      			 var url = "${pageContext.request.contextPath}/subagent/list";
$(location).attr('href',url);
      			//return true;
      		}
      	});
    //  });
 
}
</script>

<script lang="JavaScript">
/* function load()
{
$("#dialog").show();
} */

function load1()
{
var url = "${pageContext.request.contextPath}/subagent/list";
$(location).attr('href',url);
}


function webDialog()
{
$("#form-edit").submit();
}


function webDialog1()

{
var url = "${pageContext.request.contextPath}/subagent/list"; 
		$(location).attr('href',url);
}

</script>


 <form:form action="${pageContext.request.contextPath}/subagent/editReviewandConfirm"  method="post" commandName="subagent" id="form-edit" >
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <div class="content-wrapper">
        
        
        <div class="row">
		
            <div class="col-md-12 formContianer">
            
           
              <h3 class="card-title">Edit SubAgent</h3>
           <!--  <a class="btn btn-primary btn-flat pull-right" href="#">Add New <i class="fa fa-lg fa-plus"></i></a>   -->
              <div class="col-md-12 formContianer tableEdit">
           
            	  <div class="card">	
           <!--    <h3 class="card-title userTitle">Edit<i class="fa fa-pencil pull-left" aria-hidden="true"></i> --> <!--  <h3 class="card-title userTitle"><span class="pull-right"> -->
                <h3 class="card-title userTitle">SubAgCode: ${subagent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${subagent.code}" >
					MailId:	${subagent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${subagent.mailId}">
						<!-- </span> --> </h3>	
					 <div class="row">
 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Salutation</label>
							
									<input type="text" class="form-control" id="salutation"
										placeholder="salutation" name="salutation" path="salutation"  value= "${subagent.salutation}" readonly="readonly"/>
									</div>
									</div>


                           <div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Name</label>
							
									<input type="text" class="form-control" id="name"
										placeholder="name" name="name" path="name"  value= "${subagent.name}" readonly="readonly"/>
									</div>
									</div>
									
									
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Address1</label>
							
									<input type="text" class="form-control" id="addr1"
										placeholder="addr1" name="addr1" path="addr1"  value= "${subagent.addr1}" readonly="readonly"/>
									</div>
									</div>
									
									
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Address2</label>
							
									<input type="text" class="form-control" id="addr2"
										placeholder="addr2" name="addr2" path="addr2"  value= "${subagent.addr2}" readonly="readonly"/>
									</div>
									</div>
								
								
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">City</label>
							
									<input type="text" class="form-control" id="city"
										placeholder="city" name="city" path="city"  value= "${subagent.city}" readonly="readonly"/>
									</div>
									</div>	
					
					
					<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">State</label>
							
									<input type="text" class="form-control" id="state"
										placeholder="state" name="state" path="state"  value= "${subagent.state}" readonly="readonly"/>
									</div>
									</div>	
									</div>
									
					<div class="row">
 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">PostCode</label>
							
									<input type="text" class="form-control" id="postCode"
										placeholder="postCode" name="postCode" path="postCode"  value= "${subagent.postCode}" readonly="readonly"/>
									</div>
									</div>
									
										<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">PhoneNo</label>
							
									<input type="text" class="form-control" id="postCode"
										placeholder="phoneNo" name="phoneNo" path="phoneNo"  value= "${subagent.phoneNo}" readonly="readonly"/>
									</div>
									</div>
										<div class="form-group col-md-4">
								<div class="form-group">
									<label for="type">AgType</label>
							
									<input type="text" class="form-control" id="type"
										placeholder="type" name="type" path="type"  value= "${subagent.type}" readonly="readonly"/>
									</div>
									</div>
								</div>	
									
							<div class="row">
 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="agentName">AgentName</label>
							
									<input type="text" class="form-control" id="agentName"
										placeholder="agentName" name="agentName" path="agentName"  value= "${subagent.agentName}" readonly="readonly"/>
									</div>
									</div>		
					
					</div>
					</div>
					

        <button type="button" id="testing" onclick="load()" class="btn btn-primary icon-btn">Confirm</button> <!-- id="testing" -->
      
    
      <button type="button" onclick="load1()" class="btn btn-default icon-btn">Cancel</button> <!-- id="testing1" -->
   </div>
	</div>
		</div>			</div>
		</div>							
 </form:form>

 </body>
 </html>
	

    