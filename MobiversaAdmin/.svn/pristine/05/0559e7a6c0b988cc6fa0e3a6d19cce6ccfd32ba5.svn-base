<%@page import="com.mobiversa.payment.controller.AgentVolumeController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@ page import="java.io.*,java.util.*" %>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

 
  
 <script type="text/javascript">
 /* 
 function currentIndex(){
 alert('currentInde');
 var offset=document.getElementById('index');
 offset.style.color= '#fff';
     offset.style.background-color= '#1e5385';
     offset.style.border-color= 'transparent';
 
 }
 
  */
 
  
  function calPrevious(){
  	var offset=document.getElementById('index').value;
  	var merchantCount=document.getElementById('merchantCount').value;
  	var agentName=document.getElementById('agentName').value;
  	var url=window.location;
  	var a = document.getElementById('previousId'); //or grab it by tagname etc
  	if(offset=="1"){
  		//alert("You can not go to previous.. You are in 1st index");
  		swal("You can not go to previous index.. You are in 1st index");
  		a.href = "javascript:void(0)";
  		
  	}else{
   		
   		offset=offset-1;
   		a.href = url+"/superagent/merchantvolume/"+agentName+"/"+offset;
  		document.getElementById('myNav').style.display = 'block';
   	}
   }
   
  function calNext(){
   	var offset=parseInt(document.getElementById('index').value);
   	//alert(offset);
   	var agentName=document.getElementById('agentName').value;
   	var merchantCount=document.getElementById('merchantCount').value;
   	var url=window.location;
   	var a = document.getElementById('nextId'); //or grab it by tagname etc
   	if(merchantCount==offset){
   		//alert("You can not go to Next.. You are in Last index");
   		swal("You can not go to next index.. You are in Last index");
   		a.href = "javascript:void(0)";
  	}else{
    		
   		var offSetinc= parseInt(1);
   		var offSetvalue=offset + offSetinc;
  	 	a.href = url+"/superagent/merchantvolume/"+agentName+"/"+offSetvalue;
   		document.getElementById('myNav').style.display = 'block';
   	}
    }
   
   
   function calCurrent(curOffset){
   	var offset=parseInt(document.getElementById('index').value);
  	var curOffset=parseInt(curOffset);
  	//alert("curOffset: "+curOffset+" offset: "+offset);
  	var agentName=document.getElementById('agentName').value;
   	var url=window.location;
   	var a = document.getElementById('curId');
   	if(curOffset==offset){
   		//alert("Currently viewing "+curOffset);
   		swal("Currently viewing index "+curOffset);
   		a.href = "javascript:void(0)";
   		//alert(a.href);
   		return false;
   	}else{
   		a.href = url+"/superagent/merchantvolume/"+agentName+"/"+curOffset;
   		
   		//alert(a.href);
   		//document.getElementById('myNav').style.display = 'block';
   		return true;
   	}

   }
   
 
 
 </script>
  <style>
.loader {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #3498db;
  width: 120px;
  height: 120px;
  -webkit-animation: spin 2s linear infinite; /* Safari */
  animation: spin 2s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
 	<style>
/* @media screen and (max-device-width: 590px) , screen and (max-width:
	590px) {
	.payment2 {
		align:center;
	}
	.card2 {
		align:center;
		
	}
	
} */
@media screen and (max-device-width: 590px) , screen and (max-width:
	590px) {
	.container{
	max-width:100%;
	align:center;
	}
	.navbar navbar-inverse navbar-fixed-top{
	max-width:100%;
	}
	.container {
    
    margin-right: auto;
    margin-left: auto;
    overflow-x:hidden;
}
}
</style>

<style type="text/css">
body {
	font-family: 'Lato', sans-serif;
	/* background-color:#224342; */
}

.overlay {
	height: 100%;
	width: 100%;
	position: fixed;
	z-index: 99;
	top: 0;
	left: 0;
	background-color:rgb(221, 221, 221);
	background-color: rgba(221, 221, 221, 0.6);
	overflow-x: hidden;
	transition: 0.9s;
	webkit-transition: 0.9s;
	-moz-transition: 0.9s;
	-o-transition: 0.9s;
}

.overlay-content {
	position: relative;
	top: 25%;
	width: 100%;
	text-align: center;
	margin-top: 30px;
}

.overlay a {
	padding: 8px;
	text-decoration: none;
	font-size: 36px;
	color: #818181;
	display: block;
	transition: 0.3s;
}

.overlay a:hover,.overlay a:focus {
	color: #f1f1f1;
}

.overlay .closebtn {
	position: absolute;
	top: 20px;
	right: 45px;
	font-size: 60px;
}

@media screen and (max-height: 450px) {
	.overlay a {
		font-size: 20px
	}
	.overlay .closebtn {
		font-size: 40px;
		top: 15px;
		right: 35px;
	}
}
</style>
<style>
.loader {
	border: 6px solid #f3f3f3;
	border-radius: 50%;
	border-top: 6px solid rgb(0, 191, 255);
	border-bottom: 6px solid #224342;
	border-left: 6px solid #224342;
	border-right: 6px solid #224342;
	width: 60px;
	height: 60px;
	-webkit-animation: spin 2s linear infinite; /* Safari */
	animation: spin 2s linear infinite;
}

/* Safari */
@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
		<style>
.loaderIn {
	border: 6px solid #f3f3f3;
	border-radius: 50%;
	border-top: 6px solid rgb(0, 191, 255);
	border-bottom: 6px solid rgb(0, 191, 255);
	border-left: 6px solid #224342;
	border-right: 6px solid #224342;
	width: 60px;
	height: 60px;
	-webkit-animation: spin 2s linear infinite; /* Safari */
	animation: spin 2s linear infinite;
}

/* Safari */
@
-webkit-keyframes spin { 0% {
	-webkit-transform: rotate(0deg);
}

100%
{
-webkit-transform
:
 
rotate
(0deg);
 
}
}
@
keyframes spin { 0% {
	transform: rotate(0deg);
}
100%
{
transform
:
 
rotate
(0deg);
 
}
}
</style>

<!-- <script type="text/javascript">
function formatNumber(num) {
	  return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
}</script> -->

<!-- <script type="text/javascript">
$(document).ready(function() {
	   $(".formatNum").each(function(){
	        // isValidIBAN($(this).html());
	         return ${dto1}.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
	         // do whatever validation you want inside $(this).html("VALIDATION OUTPUT")
	   });

	}</script>
  -->
 
</head>




<body onload="return currentIndex();" >

<div class="container-fluid">    
  <div class="row">
<div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Merchant Volume Summary</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
 
				<h3 class="card-title">
					Merchant Volume <a class="btn btn-primary btn-flat pull-right" href="${pageContext.request.contextPath}/superagent/agentVolumeSummary/1">Back</a></h3>
				
					<div class="table-responsive m-b-20 m-t-15">

           	<table id="data_list_table" class="table table-striped table-bordered">
						
						<c:set var="count" value="0" />
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<%-- <c:forEach begin="0" end="1" var="count"> --%>
						<c:if test="${count == 0}">
							<thead>
								<tr>
									<th >Merchant Name</th>
									<!-- <th >Txn_Type</th> -->

									<c:forEach items="${dto.date}" var="dtold">
										<th >${dtold}</th>

									</c:forEach>
								</tr>
							</thead>
							<c:set var="count" value="1" />
						</c:if>
						<%-- </c:forEach> --%>
					</c:forEach>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dtolda">
							<tr>
								<td>${dtolda.agentName}</td>
								<%-- <td>${dtolda.txnType}</td> --%>
								<c:forEach items="${dtolda.amount}" var="dto1">
									<td style="text-align:right;padding-right:40px;">${dto1}</td> 
								</c:forEach>
							</tr>

						</c:forEach>
						
						</tbody>
						
			
       
	
	</table>
<style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
	
	  <div align="right">
<input type="hidden" name="index" id="index" value="${offset }"/>
<input type="hidden" name="merchantCount" id="merchantCount" value="${merchantCount }"/>
<input type="hidden" name="agentName" id="agentName" value="${agentName}"/>
<table class="paginationTable" style="margin-left:-400px !important" border="0">
<tr><td>
<c:forEach items="${count}" var="count"> 
  <tr>
    <td></td>
    
  </tr>
</c:forEach>
</td></tr>

<tr>
<td>
<a href="${pageContext.request.contextPath}/superagent/merchantvolume/${agentName}/${count}" 
onclick="calPrevious();" id="previousId"
style="color: #707070;margin-left: 734px !important;">
 << Previous </a></td>

<c:forEach items="${count}" var="count">  
<td>
<input type="hidden" name="curOffset" id="curOffset" value="${count }"/>
<button style="background-color:#3f51b5;" class="" onclick="return calCurrent(${count});">
<a href="${pageContext.request.contextPath}/superagent/merchantvolume/${agentName}/${count}" 
 id="curId"
style="color: #fff;"><c:out value="${count}" /></a></button></td>
</c:forEach>
<td>

<a href="${pageContext.request.contextPath}/superagent/merchantvolume/${agentName}/${count}" 
onclick="calNext();" id="nextId"
style="color: #707070;">Next >> </a></td>
</tr>
</table>

<style>
#data_list_table_paginate{
display: none !important;
}
.paginationTable tr{
	border-bottom:none !important;
}
</style>
</div>
	
</div>
</div>
</div>


</div>
</div>


<!-- 			<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

$(document).ready(function() {
    $('#data_list_table').DataTable( {
    	"bSort" : false
    } );
} );

</script> -->

<script>
	$(document).ready(function() {
	    $('#data_list_table').DataTable( {
	        columnDefs: [
	            {
	                targets: [ 0, 1, 2 ],
	                className: 'mdl-data-table__cell--non-numeric'
	            }
	        ]
	    } );
	} );

	</script>

</div>
</body>
</html>




















