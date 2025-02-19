<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
	
<script>
function getMonths() {
	
	const monthNames = ["January", "February", "March", "April", "May", "June",
		  "July", "August", "September", "October", "November", "December"
		];

		const d = new Date();

		document.getElementById("fourthMon").innerHTML = monthNames[d.getMonth()];
		document.getElementById("thirdMon").innerHTML = monthNames[d.getMonth()-1];
		document.getElementById("secondMon").innerHTML = monthNames[d.getMonth()-2];
		document.getElementById("firstMon").innerHTML = monthNames[d.getMonth()-3]; 

}

function loadDropDate13() {
	//alert("loadDropDate13");
	var e = document.getElementById("export");

	var strUser = e.options[e.selectedIndex].value;
	document.getElementById("export1").value = strUser;
	

}

function loadExpData() {
	
	var e =document.getElementsByClassName('tb_active')[0].id;
	var e1 = document.getElementById("export1").value;
	var e2 = ${agent.id} ;
	
	document.location.href = '${pageContext.request.contextPath}/superagent/merchantGPVExportByAgent?month=' + e
	+'&export='+e1+'&agId='+e2;
	form.submit;
	
	
}
</script>


</head>
<body onload="getMonths();">
<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Merchant GPV Summary </strong></h3>
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
				<div class="input-field col s12 m3 l3">
										
				<select name="export" id="export" onchange="return loadDropDate13();"
					>
					<option selected value="">Choose</option>
					<option value="PDF">PDF</option>
					<option value="EXCEL">EXCEL</option>
				</select>
				<label for="name">Export Type</label> <input
					type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
			</div>
          <div class="input-field col s12 m3 l3">
			<button class="btn btn-primary" onclick="return loadExpData();">Export</button>		
			</div>
			</div>
 
        </div>
      </div>
    </div>
    </div>
    



	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
           
		   <ul class="tab_ul">
		   <!-- <li class="tb_active" id="fourthMon"></li>
		   
		   <li id="thirdMon"></li>
		   
		   <li id="secondMon"></li>
		   
		   <li id="firstMon"></li> -->
			
			<li  class="tb_active"  id="firstMon"></li>
		     <li id="secondMon"></li>
		     <li id="thirdMon"></li>
		   <li id="fourthMon"></li>
		   
		   
		   </ul>
		  
		  
          </div>
		  
		  <script>
 $(function() {                       
  $(".tab_ul li").click(function() {   
    $(".tab_ul li").removeClass("tb_active");      
    $(this).addClass("tb_active"); 
    id = $(this).attr("id"); 
   $('.content_area .row').hide();
   $('.'+id).show();
	
  });
});




</script>
		  
		   <style>
		   .tab_ul { display:flex;width: 100%;}
		   .tab_ul li { display: flex;
    /* margin-left: 10px; */
    padding: 10px 20px;
    background-color: #eeeae9;
    width: 100%; cursor:pointer;
	 margin: auto;
    display: table;
	}
	
	.tb_active {  background-color: #005baa !important;
    color: #fff;  text-align:center}
	
	.content_area { padding:30px; }
	
	  .page-wrapper {
    min-height: calc(50vh - 64px);
}  
#submit-btn { background-color:#54b74a;color:#fff; border-radius:10px; margin:auto; display:table;  }
.mob-br { display:none; }


@-moz-document url-prefix() {
   .tab_ul li{
       width: 26% !important; 
    }
}

@media screen and (max-width:768px){
.tab_ul { display: block !important; width: auto;}
.tab_ul li { float: left; display:block;width:auto;  }
.mob-br { display:block;  white-space: pre-line; }

@-moz-document url-prefix() {
   .tab_ul li{
       width: 24% !important; 
    }
}

}
		   </style>
		<div style="clear:both;"></div>
		  
          <div class="content_area">
		  
				<div class="row fourthMon" style="display:none;">
					<div class="table-responsive m-b-20 m-t-15">
		 <table id="data_list_table" class="table table-striped table-bordered display">

							<thead>
								<tr>
								
									<th>Merchant Name</th>
									<th>Merchant Type</th>
									<th style="padding-right: 30px;">EZYLINK</th>
									<th  style="padding-right: 30px;">EZYMOTO</th>
									<th style="padding-right: 30px;">EZYMOTO-VCC</th>
									<th  style="padding-right: 30px;">EZYWAY</th>
									<th style="padding-right: 30px;">EZYREC</th>
									<th style="padding-right: 30px;">RECPLUS</th>									
									<th  style="padding-right: 30px;">EZYWIRE</th>
									<th  style="padding-right: 30px;">Total GPV</th>
				
									<!-- <th style="padding-right: 30px;">EZYAUTH</th> -->
									<!-- <th style="padding-right: 30px;">RECURRING</th> -->
									
								</tr>
								
							</thead>
							<tbody>

								<c:forEach items="${fourthMonList}" var="merchant">
									<tr>
									
										<td align="right">${merchant.merchantName}</td> 
										<td>${merchant.merchantType}</td>                                         
                                         <c:choose>
                                            <c:when test="${merchant.isEzylink != null && merchant.isEzylink == 'EZYLINK'}">
                                            <td style="text-align:right;">${merchant.ezylinkAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymoto != null && merchant.isEzymoto == 'EZYMOTO'}">
                                            <td style="text-align:right;">${merchant.ezymotoAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymotoVcc != null && merchant.isEzymotoVcc == 'EZYMOTO-VCC'}">
                                            <td style="text-align:right;">${merchant.ezymotoVccAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                         <c:choose>
                                            <c:when test="${merchant.isEzyway != null && merchant.isEzyway == 'EZYWAY'}">
                                            <td style="text-align:right;">${merchant.ezywayAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isEzyrec!= null && merchant.isEzyrec == 'EZYREC'}">
                                            <td style="text-align:right;">${merchant.ezyrecAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                              <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isRecplus != null && merchant.isRecplus == 'RECPLUS'}">
                                            <td style="text-align:right;">${merchant.recplusAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                        
										<c:choose>
                                            <c:when test="${merchant.isEzywire != null && merchant.isEzywire == 'EZYWIRE'}">
                                            <td style="text-align:right;">${merchant.ezywireAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>

                                       <%--  <c:choose>
                                            <c:when test="${merchant.isEzyauth != null && merchant.isEzyauth == 'EZYAUTH'}">
                                            <td style="text-align:right;">${merchant.ezyauthAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>
 
                                     <%--    
                                        <c:choose>
                                            <c:when test="${merchant.isRecurring != null && merchant.isRecurring == 'RECURRING'}">
                                            <td style="text-align:right;">${merchant.recurringAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>                                    
										<td>${merchant.totalGpv}</td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
				</div>
				
				
						<div class="row thirdMon" style="display:none;">
					<div class="table-responsive m-b-20 m-t-15">
		  <table id="data_list_table" class="table table-striped table-bordered display">

							<thead>
								<tr>
								<th>Merchant Name</th>
									<th>Merchant Type</th>
									<th style="padding-right: 30px;">EZYLINK</th>
									<th  style="padding-right: 30px;">EZYMOTO</th>
									<th style="padding-right: 30px;">EZYMOTO-VCC</th>
									<th  style="padding-right: 30px;">EZYWAY</th>
									<th style="padding-right: 30px;">EZYREC</th>
									<th style="padding-right: 30px;">RECPLUS</th>									
									<th  style="padding-right: 30px;">EZYWIRE</th>
									<th  style="padding-right: 30px;">Total GPV</th>
				
									<!-- <th style="padding-right: 30px;">EZYAUTH</th> -->
									<!-- <th style="padding-right: 30px;">RECURRING</th> -->
								</tr>
								
								
							</thead>
							<tbody>

								<c:forEach items="${ThirdMonList}" var="merchant">
									<tr>
									
										<td align="right">${merchant.merchantName}</td> 
										<td>${merchant.merchantType}</td>                                         
                                         <c:choose>
                                            <c:when test="${merchant.isEzylink != null && merchant.isEzylink == 'EZYLINK'}">
                                            <td style="text-align:right;">${merchant.ezylinkAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymoto != null && merchant.isEzymoto == 'EZYMOTO'}">
                                            <td style="text-align:right;">${merchant.ezymotoAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymotoVcc != null && merchant.isEzymotoVcc == 'EZYMOTO-VCC'}">
                                            <td style="text-align:right;">${merchant.ezymotoVccAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                         <c:choose>
                                            <c:when test="${merchant.isEzyway != null && merchant.isEzyway == 'EZYWAY'}">
                                            <td style="text-align:right;">${merchant.ezywayAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isEzyrec!= null && merchant.isEzyrec == 'EZYREC'}">
                                            <td style="text-align:right;">${merchant.ezyrecAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                              <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isRecplus != null && merchant.isRecplus == 'RECPLUS'}">
                                            <td style="text-align:right;">${merchant.recplusAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                        
										<c:choose>
                                            <c:when test="${merchant.isEzywire != null && merchant.isEzywire == 'EZYWIRE'}">
                                            <td style="text-align:right;">${merchant.ezywireAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>

                                       <%--  <c:choose>
                                            <c:when test="${merchant.isEzyauth != null && merchant.isEzyauth == 'EZYAUTH'}">
                                            <td style="text-align:right;">${merchant.ezyauthAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>
 
                                     <%--    
                                        <c:choose>
                                            <c:when test="${merchant.isRecurring != null && merchant.isRecurring == 'RECURRING'}">
                                            <td style="text-align:right;">${merchant.recurringAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>   
                                        <td>${merchant.totalGpv}</td> 
										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
				</div>
				
						<div class="row secondMon" style="display:none;">
					<div class="table-responsive m-b-20 m-t-15">
		   <table id="data_list_table" class="table table-striped table-bordered display">

							<thead>
								<tr>
								<th>Merchant Name</th>
									<th>Merchant Type</th>
									<th style="padding-right: 30px;">EZYLINK</th>
									<th  style="padding-right: 30px;">EZYMOTO</th>
									<th style="padding-right: 30px;">EZYMOTO-VCC</th>
									<th  style="padding-right: 30px;">EZYWAY</th>
									<th style="padding-right: 30px;">EZYREC</th>
									<th style="padding-right: 30px;">RECPLUS</th>									
									<th  style="padding-right: 30px;">EZYWIRE</th>
									<th  style="padding-right: 30px;">Total GPV</th>
				
									<!-- <th style="padding-right: 30px;">EZYAUTH</th> -->
									<!-- <th style="padding-right: 30px;">RECURRING</th> -->
								</tr>
								
							</thead>
							<tbody>

								<c:forEach items="${SecondMonList}" var="merchant">
									<tr>
									
										<td align="right">${merchant.merchantName}</td> 
										<td>${merchant.merchantType}</td>                                         
                                         <c:choose>
                                            <c:when test="${merchant.isEzylink != null && merchant.isEzylink == 'EZYLINK'}">
                                            <td style="text-align:right;">${merchant.ezylinkAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymoto != null && merchant.isEzymoto == 'EZYMOTO'}">
                                            <td style="text-align:right;">${merchant.ezymotoAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymotoVcc != null && merchant.isEzymotoVcc == 'EZYMOTO-VCC'}">
                                            <td style="text-align:right;">${merchant.ezymotoVccAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                         <c:choose>
                                            <c:when test="${merchant.isEzyway != null && merchant.isEzyway == 'EZYWAY'}">
                                            <td style="text-align:right;">${merchant.ezywayAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isEzyrec!= null && merchant.isEzyrec == 'EZYREC'}">
                                            <td style="text-align:right;">${merchant.ezyrecAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                              <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isRecplus != null && merchant.isRecplus == 'RECPLUS'}">
                                            <td style="text-align:right;">${merchant.recplusAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                        
										<c:choose>
                                            <c:when test="${merchant.isEzywire != null && merchant.isEzywire == 'EZYWIRE'}">
                                            <td style="text-align:right;">${merchant.ezywireAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>

                                       <%--  <c:choose>
                                            <c:when test="${merchant.isEzyauth != null && merchant.isEzyauth == 'EZYAUTH'}">
                                            <td style="text-align:right;">${merchant.ezyauthAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>
 
                                     <%--    
                                        <c:choose>
                                            <c:when test="${merchant.isRecurring != null && merchant.isRecurring == 'RECURRING'}">
                                            <td style="text-align:right;">${merchant.recurringAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>    
										<td>${merchant.totalGpv}</td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
				</div>
				
						<div class="row firstMon" >
					<div class="table-responsive m-b-20 m-t-15">
		   
		   
          
           	 <table id="data_list_table" class="table table-striped table-bordered display">

							<thead>
								<tr>
								<th>Merchant Name</th>
									<th>Merchant Type</th>
									<th style="padding-right: 30px;">EZYLINK</th>
									<th  style="padding-right: 30px;">EZYMOTO</th>
									<th style="padding-right: 30px;">EZYMOTO-VCC</th>
									<th  style="padding-right: 30px;">EZYWAY</th>
									<th style="padding-right: 30px;">EZYREC</th>
									<th style="padding-right: 30px;">RECPLUS</th>									
									<th  style="padding-right: 30px;">EZYWIRE</th>
									<th  style="padding-right: 30px;">Total GPV</th>
				
									<!-- <th style="padding-right: 30px;">EZYAUTH</th> -->
									<!-- <th style="padding-right: 30px;">RECURRING</th> -->
								</tr>
								
							</thead>
							<tbody>

								<c:forEach items="${FirstMonList}" var="merchant">
									<tr>
										<td align="right">${merchant.merchantName}</td> 
										<td>${merchant.merchantType}</td>                                         
                                         <c:choose>
                                            <c:when test="${merchant.isEzylink != null && merchant.isEzylink == 'EZYLINK'}">
                                            <td style="text-align:right;">${merchant.ezylinkAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymoto != null && merchant.isEzymoto == 'EZYMOTO'}">
                                            <td style="text-align:right;">${merchant.ezymotoAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${merchant.isEzymotoVcc != null && merchant.isEzymotoVcc == 'EZYMOTO-VCC'}">
                                            <td style="text-align:right;">${merchant.ezymotoVccAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                         <c:choose>
                                            <c:when test="${merchant.isEzyway != null && merchant.isEzyway == 'EZYWAY'}">
                                            <td style="text-align:right;">${merchant.ezywayAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isEzyrec!= null && merchant.isEzyrec == 'EZYREC'}">
                                            <td style="text-align:right;">${merchant.ezyrecAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                              <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                         <c:choose>
                                            <c:when test="${merchant.isRecplus != null && merchant.isRecplus == 'RECPLUS'}">
                                            <td style="text-align:right;">${merchant.recplusAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>
                                        
                                        
										<c:choose>
                                            <c:when test="${merchant.isEzywire != null && merchant.isEzywire == 'EZYWIRE'}">
                                            <td style="text-align:right;">${merchant.ezywireAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose>

                                       <%--  <c:choose>
                                            <c:when test="${merchant.isEzyauth != null && merchant.isEzyauth == 'EZYAUTH'}">
                                            <td style="text-align:right;">${merchant.ezyauthAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>
 
                                     <%--    
                                        <c:choose>
                                            <c:when test="${merchant.isRecurring != null && merchant.isRecurring == 'RECURRING'}">
                                            <td style="text-align:right;">${merchant.recurringAmt}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td style="text-align:right;">0.00</td>
                                              </c:otherwise>
                                        </c:choose> --%>    
										<td>${merchant.totalGpv}</td>
										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
				</div>
				
				 
        </div>
      </div>
    </div>
    </div>
    </div>
    
	</div>

<script>

$(document).ready(function() {
    $('table.display').DataTable({
    	 columnDefs: [
	            {
	                targets: [ 0, 1, 2 ],
	                className: 'mdl-data-table__cell--non-numeric'
	            }
	        ]
    });
} );

</script>
 
 
    </body>
	</html>
	
