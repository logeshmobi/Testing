<%@page
	import="com.mobiversa.payment.controller.MerchantWebUploadTCController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 <link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resourcesNew1/dist/css/simple-chart.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,400,600|Lato:300, 400i,700,700i" rel="stylesheet">
 
 <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "upTC/");
</script>

 <style>
        body {  background-color:#eaeff5 }
        section { float:left;
         width:100%; 
        padding:10px; margin:40px 0;  
        background-color:#fff; 
        box-shadow:0 15px 40px rgba(0,0,0,.1) 
        }
        h1 { margin-top:150px; 
        text-align:center;}
        
        .sc-chart 
{
 
    height: 250px !important;
    margin: auto !important;
    display: table !important;
}
.sc-column {
float:inherit !important;
}
.sc-column .sc-item {width: 26% !important; }

.barchartsection{
border-radius:15px !important;
}

.hr-line{margin: 0;
    position: relative;
    top: -60px;  
    height:1px;
    background-color: #ffa500;}

@media screen and (max-width:768px) {
.hr-line{
 top: -87px !important;  
}
.sc-chart {
    width: 100% !important;}
    </style>

    
    
    
    <script type="text/javascript">

function openNewWin(merchantName,id){
//alert("username: "+merchantName+"id"+id);
	var h = 600;
	var w = 1000;
	var title = merchantName+" Portal";
	
	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
    var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

    var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
    var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

    var left = ((width / 2) - (w / 2)) + dualScreenLeft;
    var top = ((height / 2) - (h / 2)) + dualScreenTop;
    src='${pageContext.request.contextPath}/merchants/merchantsweb?username='+merchantName+'&id='+id;
    var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus();
    }
		
}
    </script>
 <style>
 
 .td{
 text-align:right;
 
 
 }
  </style>

 <script type="text/javascript">
function changeStyle(id){
//alert("changeing --"+id.value+"--");
var field=id.value;
//alert(" changeing --"+field.length+"--");
//document.getElementById("mid").focus();
if(field.length!=0){
//alert(" changeing --"+field.length+"--");
id.style.border = "1px solid #3FCADB";
}else{
id.style.border = "1px solid #B5B9B9";
}
}  

    </script> 
 <script lang="JavaScript">
 function submitForms()
 {
  document.getElementById("uploadForm").submit();
  /* alert(document.getElementById("mailFile").value);  */
   alert('Input can not be left blank');
 if($('#mailFile').val() == ''){
     
   }
 }
 
 
</script>
 <script type="text/javascript">
$().ready(function() {
 
    $('#mailFile').on( 'change', function() {
   myfile= $( this ).val();
   var ext = myfile.split('.').pop();
   if(ext=="pdf"){
       return true;
   } else{
   alert("Please enter valid pdf file..");
      document.getElementById("mailFile").value="";
      
      return false;
   }
   
  }); 
});


</script>

  
  <script>
    $(function(){
      // bind change event to select
      $('#merchantName').on('change', function () {
    	 // encodeURIComponent(this.value )
          var url = $(this).val(); // get selected value
         // var url=encodeURIComponent(url1);
         
          var res = encodeURI(url);

          if (res) { // require a URL
              window.location = res; // redirect
             // alert(window.location);
          }
          return false;
      });
    });
</script>
<script lang="JavaScript">
$().ready(function() {
$("#mailFile").click(function(){
    $("p").hide();
});
});
</script>



</head>

<body>

<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>NOB Dashboard</strong></h3>
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
									Nature Of Business
									</div>
									
									
							<div class="input-field col s12 m5 l5">
	
								<select  class="browser-default select-filter" name="merchantName"		
								id="merchantName" path="merchantName" onchange="javascript:location.href = this.value;">
								
								<optgroup label="Nature Of Business" style="width:100%">
								<option selected value=""><c:out value="Nature Of Business" /></option>
									
								<c:forEach items="${nobList}" var="nobList">
								<c:if test="${nobList != null}">
									<option value="${pageContext.request.contextPath}/admin/merchantTxnDetails?nob=${nobList}">
									${nobList}	
									</option>
</c:if>

								</c:forEach>
								</optgroup>
								</select>
								 <script>
        $(document).ready(function(){
            
          
           
            $(".select-filter").select2();
            
            
        });
        </script>
        
								</div></div>
					
					<div class="row">
					 <div class="input-field col s12 m3 l3"></div>
					 
<style>
.select2-dropdown {    border: 2px solid #2e5baa; }
.select2-container--default .select2-selection--single {border:none;}
 .select-search .select-wrapper input {
	display:none !important;
}


.select-search .select-wrapper input {
	display:none !important;
}
.select2-results__options li{
	list-style-type: none;												
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}
ul.select2-results__options li:hover{
	
}
ul.select2-results__options li{
	max-height:250px;
	
	curser:pointer;
 }
li ul .select2-results__option:hover{
	background-color: #005baa !important;
	
	color:#fff !important; 
}
.select-search-hidden .select2-container{
	display:none !important;
}

</style>
						<div class="input-field col s12 m3 l3 select-search">
								<input type="text" class="form-control" id="businessName" readonly
								Placeholder="Nature Of Business" value="${nob}"/>
								 </div></div></div></div></div></div>
								
			<c:if test="${nob!=null }"> 
					 <div class="row">
	 <div class="col s12">
	 <div class="sc-wrapper">
        <section class="sc-section barchartsection">
            <div class="column-chart"></div>
            <hr class="hr-line"/>
        </section> 
    </div></div>
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/resourcesNew1/dist/css/simple-chart.js"></script> 
   
  <script>
        
		  var data = ${dailytxn};
			 
		  var data1= ${weeklytxn};
		  var monthlyVolume=${totalMonTxn};
    	 var  monthlyVolume1 = monthlyVolume/10000000; 
        var labels = ["Daily Volume", "Weekely Volume", "Monthly Volume"];
        var values = [data, data1, monthlyVolume]; 
       /*  var outputValues = abbreviateNumber(values); */
        var outputValues=values;

        $('.column-chart').simpleChart({
            title: {
                text: '',
                align: 'center'
            },
            type: 'column',
            layout: {
                width: '100%',
                height: '250px'
            },
            item: {
                label: labels,
                value: values,	
                outputValue: outputValues,
                color: ['#005baa'],
                prefix: '',
                suffix: '',
                render: {
                    margin: 0.2,
                    size: 'relative'
                }
            }
        });

        $('.bar-chart').simpleChart({
            title: {
                text: '',
                align: 'center'
            },
            type: 'bar',
            layout: {
                width: '100%'
            },
            item: {
                label: labels,
                value: values,
                outputValue: outputValues,
                color: ['#005baa'],
                prefix: '',
                suffix: '',
                render: {
                    margin: 0,
                    size: 'relative'
                }
            }
        });

         

        
    </script> 
   
			            </div>
			          </c:if> 
			        
			          
			          
			         
									
							 
		
			 

</div>
	<script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();

});  
    </script>		
</body>




	
			</html>
		
		