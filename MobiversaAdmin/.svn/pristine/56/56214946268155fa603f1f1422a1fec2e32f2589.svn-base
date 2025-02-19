<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
</head>

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}		
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
/* The container */
.container {
  display: block !important;
  position: relative !important;
  padding-left: 35px !important;
  margin-bottom: 12px !important;
  cursor: pointer !important;
  font-size: 22px !important;
  -webkit-user-select: none !important;
  -moz-user-select: none !important;
  -ms-user-select: none !important;
  user-select: none !important;
}

/* Hide the browser's default checkbox */
.container input {
  position: absolute !important;
  opacity: 0 !important;
  cursor: pointer !important;
  height: 0 !important;
  width: 0 !important;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  height: 25px !important;
  width: 25px !important;
  background-color: #eee !important;
  padding-left: 0px !important;
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
  background-color: #ccc !important;
}

/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
  background-color: #fff!important;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "" !important;
  position: absolute !important;
  display: none !important;
}

/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
  display: block !important;
}

/* Style the checkmark/indicator */
.container .checkmark:after {
  left: 9px !important;
  top: 5px !important;
  width: 5px !important;
  height: 10px !important;
  border: solid white !important;
  border-width: 0 3px 3px 0 !important;
  -webkit-transform: rotate(45deg) !important;
  -ms-transform: rotate(45deg) !important;
  transform: rotate(45deg) !important;
}
</style>	

<script type="text/javascript">
 var count = 14;
 var id ;
 var a1 = [2,3,4,5,6,7,8,9,10,11,12,13,14,15];
 var c1 = "NO";

// To press only numbers 

 function isNumberKey(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
     if (charCode > 31 && (charCode < 48 || charCode > 57))
         return false;
     return true;
 }    
 
 
 function isNumberKey1(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
     if (charCode > 31 && (charCode < 48 || charCode > 57)){
    	 return false;
     }
     
     $('.creditCardText').keyup(function() {
		  var foo = $(this).val().split("-").join(""); // remove hyphens
		  if (foo.length > 0) {
		    foo = foo.match(new RegExp('.{1,4}', 'g')).join("-");
		  }
		  $(this).val(foo);
		});	
         
     return true;
 }    
 
 
 function isNumberKey2(evt){
     var charCode = (evt.which) ? evt.which : event.keyCode
     if (charCode > 31 && (charCode < 48 || charCode > 57)){
    	 return false;
     }
     
     $('.creditCardExdt').keyup(function() {
		  var foo = $(this).val().split("-").join(""); // remove hyphens
		  if (foo.length > 0) {
		    foo = foo.match(new RegExp('.{1,2}', 'g')).join("-");
		  }
		  $(this).val(foo);
		});	
         
     return true;
 }    
 

	
 
//To add fields 
 
 function add_fields() {
	 
	/*  a1.forEach(myFunction);

	 function myFunction(item, index) {
		 
		 console.log(index + ":" + item + "<br>");
		 
	 } */
	
	 if(count != 0){
	 count--
	 id = a1[0];
	 //alert("id::::"+id);
     var index = a1.indexOf(id);
     if (index > -1) {
    	 a1.splice(index, 1);
     }
     
     var objTo = document.getElementById('add_fields')
     var divtest = document.createElement("tr");
 	divtest.setAttribute("class", "form-group removeclass"+id);
 	var rdiv = 'removeclass'+id;
    	 
    	 divtest.innerHTML ='<td style="text-align: center;"><input type="checkbox"  name="ckId" id="ckId'+ id +'" style="margin-top: 11px; width: 16px; height: 20px";"></td>'

    		 +'<td><input type="text" class="form-control c1" id="cardName'+ id +'" style="width: 100%;" maxlength="26"'
    		 +'name="cardName[]" value="" placeholder="Card Name"></td>'


    		 +'<td><input type="text" class="form-control creditCardText" id="cardNo'+ id +'" maxlength="19" onclick="return isNumberKey1(event)"'
    		 +'name="cardNo[]" value="" placeholder="Card Number"></td>' 

    		 +'<td><input type="password" class="form-control" id="cvv'+ id +'" maxlength="3" onkeypress="return isNumberKey(event)" onpaste="return false;"'
    		 +'name="cvv[]" value="" placeholder="CVV"></td>'


    		 +'<td><input type="text" class="form-control creditCardExdt" id="exdt'+ id +'"  maxlength="5" onkeypress="return isNumberKey2(event)" onpaste="return false;"'
    		 +'name="exdt[]" value="" placeholder="MM-YY"></td>'

    		 +'<td><input type="text" class="form-control" id="amount'+ id +'" maxlength="10"'
    		 +'name="amount[]" value="" placeholder="Amount"></td>'

    		 +'<td class="input-group-btn"><button class="btn btn-danger" type="button"'
    		 +'onclick="remove_fields('+ id +');" style="font-size: 5px;">'
    		 +'<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>'
    		 +'</button></td>';
    		 
     objTo.appendChild(divtest)
     
     }else{
    	 alert("You can add only fifteen cards in a time");
     }
     
 }
 
//To remove fields 
 
function remove_fields(rid) {
		//alert("rid::::"+rid);
		a1.push(rid);
		$('.removeclass'+rid).remove();
        count++
        if(count == 0){
        	count++
        }
	}
	
function enable() {
	//alert("inn");
	document.getElementById("cancel").disabled = false;
	document.getElementById("save").disabled = false;
	document.getElementById("sumbit").disabled = false; 
}
	
	
// To validate 	

function add(pro) {
	
	document.getElementById("cancel").disabled = true;
	document.getElementById("save").disabled = true;
	document.getElementById("sumbit").disabled = true; 
	
	/* alert(document.getElementById("mid").value);
	alert(document.getElementById("tid").value);
	alert(document.getElementById("job").value); */
	var j;
	
	for (j = 1; j <= 15; j++) {
		
		try {
			var ckId = document.getElementById("ckId"+ j +"").checked;
			var cardName = document.getElementById("cardName"+ j +"").value;
			var cardNo = document.getElementById("cardNo"+ j +"").value;
			var cvv = document.getElementById("cvv"+ j +"").value;
			var exdt = document.getElementById("exdt"+ j +"").value;
			var amount = document.getElementById("amount"+ j +"").value;
			//console.log("ckId="+ckId+"::::"+"cardName="+cardName+"::::"+"cardNo="+cardNo+"::::"+"cvv="+cvv+"::::"+"exdt="+exdt+"::::"+"amount="+amount);
			
			if (cardName == '') {
				alert("please fill card name");
				document.getElementById("cardName"+ j +"").focus();
				enable();
				return false;

			}
			
			if (cardNo == '') {
				alert("please fill card number");
				document.getElementById("cardNo"+ j +"").focus();
				enable();
				return false;

			}else{
				
				var string = cardNo; // just an example
				var card1 = string.replace(/-/g, ''); '123456'
				
				if (card1.length < 16) {
					alert("Invalid card number");
					document.getElementById("cardNo"+ j +"").focus();
					enable();
					return false;

				}else{
					var value = card1;
					//function valid_credit_card(cardNo) {
						  // Accept only digits, dashes or spaces
							if (/[^0-9-\s]+/.test(value)){
								alert("Invalid card number");
								document.getElementById("cardNo"+ j +"").focus();
								enable();
								 return false;
							}

							// The Luhn Algorithm. It's so pretty.
							let nCheck = 0, bEven = false;
							value = value.replace(/\D/g, "");

							for (var n = value.length - 1; n >= 0; n--) {
								var cDigit = value.charAt(n),
									  nDigit = parseInt(cDigit, 10);

								if (bEven && (nDigit *= 2) > 9) nDigit -= 9;

								nCheck += nDigit;
								bEven = !bEven;
							}
							
							if((nCheck % 10) != 0){
								alert("Invalid card number");
								document.getElementById("cardNo"+ j +"").focus();
								enable();
								return false;
							}

							//return (nCheck % 10) == 0;
						//}
				}
			}
			
			if (cvv == '') {
				alert("please fill CVV");
				document.getElementById("cvv"+ j +"").focus();
				enable();
				return false;

			}else{
				if (cvv.length < 3) {
					alert("Invalid CVV");
					document.getElementById("cvv"+ j +"").focus();
					enable();
					return false;

				}
			}
			
			if (exdt == '') {
				alert("please fill expiry date");
				document.getElementById("exdt"+ j +"").focus();
				enable();
				return false;

			}else{
				
				var string = exdt; // just an example
				var exdt1 = string.replace(/-/g, ''); '123456'
				if (exdt1.length < 4) {
					alert("Invalid Expiry date");
					document.getElementById("exdt"+ j +"").focus();
					enable();
					return false;

				}else {
					 var d1 = [01,02,03,04,05,06,07,08,09,10,11,12];
					 /* var mm = exdt.substr(exdt.length - 2); */
					 var mm = exdt1.substring(0, 2);
					 var n = d1.includes(Number(mm));
					 
					 if(n == false){
						 alert("Invalid Expiry date");
						 document.getElementById("exdt"+ j +"").focus();
						 enable();
						 return false;
					 }
					
				}
			}
			
			if (amount == '') {
				alert("please fill amount");
				document.getElementById("amount"+ j +"").focus();
				enable();
				return false;

			}else{
					
						if(amount.includes(",")){
						
								amount=amount.replace(/,/g , "");
						
						}
						if(!isNaN(amount)){
								if(amount.includes(".")){
					
										var array1 = [];
					   					array1= amount.split(".");
					    			    //alert("array1[1]: "+array1[1]);
					 					   if(array1[1].length>=2){
					    				      amount=array1[0]+"."+array1[1].substring(0,2);
					    					  //alert("Yours Requested Amount to do Transaction "+amount);
					    					  document.getElementById("amount"+ j +"").value=amount;
					    					  //return true;
					    	
					       					}
					     				   else if(array1[1].length>=1){
					      					 		amount=amount+"0";
			      					          		// alert("Yours Requested Amount to do Transaction "+amount);
			     				          			document.getElementById("amount"+ j +"").value=amount;
			          				       			//return true;
			          					    }
			               					else{
			              						  amount=amount+"00";
			              						   //alert("Yours Requested Amount to do Transaction "+amount);
			              						   document.getElementById("amount"+ j +"").value=amount;
			                						 //return true;
			            				   }
					   
								}
								else{
					    			 amount=amount+".00";
					    			 // alert("Yours Requested Amount to do Transaction "+amount);
					    			  document.getElementById("amount"+ j +"").value=amount;
					    		  //return true;
					       		}
					       }
					 else{
					    	 
					    	  alert("please enter proper amount");
					    	  document.getElementById("amount"+ j +"").focus();
					    	  enable();
					    	   return false;
					       }
				
			}
			
			if(ckId == true){
				document.getElementById("status"+ j +"").value = true ;
				
				if(pro == "submit"){
					c1 = "YES";
				}
				
			}else{
				document.getElementById("status"+ j +"").value = false ;
			}
			document.getElementById("cName"+ j +"").value = cardName ;
			document.getElementById("cNumber"+ j +"").value = cardNo ;
			document.getElementById("lcvv"+ j +"").value = cvv ;
			document.getElementById("lextd"+ j +"").value = exdt ;
			document.getElementById("lamount"+ j +"").value = amount ;
			
			//console.log("ckId="+document.getElementById("status"+ j +"").checked+"::::"+"cardName="+document.getElementById("cName"+ j +"").value+"::::"+"cardNo="+document.getElementById("cNumber"+ j +"").value+"::::"+"cvv="+document.getElementById("lcvv"+ j +"").value+"::::"+"exdt="+document.getElementById("lextd"+ j +"").value +"::::"+"amount="+document.getElementById("lamount"+ j +"").value);
			
			}
			catch(err) {
			//  alert(err.message);
			}
		
	}
	
	document.getElementById("job").value = pro ;
	
	//alert(document.getElementById("job").value);
//	return true;
	
	load("add");
 	  
}



function load(res) {
	
	if(res == "add"){
	var check = document.getElementById("job").value;
	var title;
	if(check == "save"){
		title ="Are you sure? you want to add this card details";
	}else{
		title ="Are you sure? you want to add this card details & proceed transaction";
		
		if(c1 == "NO"){
			alert("please select atleast one card detail to proceed");
			enable();
			return false;
		}
		
	}
	
	swal(
			{
				title : title,
				text : "it will be added..!",
				type : "warning",
				showCancelButton : true,
				confirmButtonText : "Yes, add it!",
				cancelButtonText : "No, cancel!",
				closeOnConfirm : false,
				closeOnCancel : false,
				confirmButtonClass : 'btn btn-success',
				cancelButtonClass : 'btn btn-danger',

			},
			function(isConfirm) {
				if (isConfirm) {
					swal("Added!", "Card details added","success");
					$("#myform").submit();

				} else {
					swal("Cancelled", "Card details not added", "error"); 
					var url = "${pageContext.request.contextPath}/transactionMoto/motovc";
					$(location).attr('href', url);
				}
			});
	
	}else{
		title ="Are you sure? you want to cancel";
		swal(
				{
					title : title,
					text : "it will be cancelled..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes!",
					cancelButtonText : "No!",
					closeOnConfirm : false,
					closeOnCancel : true,
					confirmButtonClass : 'btn btn-success',
					cancelButtonClass : 'btn btn-danger',

				},
				function(isConfirm) {
					
					if (isConfirm) {
						swal("Cancelled", "Card details not added", "error"); 
						var url = "${pageContext.request.contextPath}/transactionMoto/motovc";
						$(location).attr('href', url);

					}
				});
	}
	}

</script>

<body >
<div class="container-fluid"> 
  <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">        
          
				<div class="row">
				<div class="col s12 m6 l6"> <div class="d-flex align-items-center">
           <h5>EZYMOTO Virtual Credit Card</h5>
          </div></div>
				<div class="col s12 m6 l6"> 
				<button class="btn right waves-effect waves-light btn blue" onclick="AddMore()"> Add New Card +</button></div>
				
				</div>
				
				<div class="row">
					<div class="input-field col s12 m3 l2">
					
				        <label>
				            <input type="checkbox" name="ckId" id="ckId1" />
				            <span>Select</span>
				        </label>
				   
					
					</div>
					<div class="input-field col s12 m3 l3">
						
						<input type="text" id="cardName1" style="width: 100%;"  maxlength="26" 
						name="cardName[]" value="" placeholder="Card Name">
						<!-- <label for="card">Card Name</label> -->
					</div>
					<div class="input-field col s12 m3 l3">
						
						<input type="text" class="creditCardText" id="cardNo1"  maxlength="19" onclick="return isNumberKey1(event)" 
						name="cardNo[]" value="" placeholder="Card Number">
						<!-- <label for="Number">Card Number</label> -->
					</div>
					
					<div class="input-field col s12 m1 l1">
						
						<input type="text" class="creditCardExdt" id="exdt1" " maxlength="5" onkeypress="return isNumberKey2(event)" 
						onpaste="return false;"
						name="exdt[]" value="" placeholder="MM-YY">
						
					</div>
					<div class="input-field col s12 m1 l1">
						
						<input type="password" id="cvv1"  maxlength="3" onkeypress="return isNumberKey(event)" onpaste="return false;"
							name="cvv[]" value="" placeholder="CVV">
						<!-- <label for="CVV">CVV</label> -->
					</div>
					
					<div class="input-field col s12 m2 l2">
						<input type="text" id="amount1"  maxlength="10" 
											name="amount[]" value="" placeholder="Amount">
						<!-- <label for="Amount">Amount</label> -->
					</div>

				</div>
				
				<form method="post" id="myform" name="myform" action="${pageContext.request.contextPath}/transactionMoto/addvc?${_csrf.parameterName}=${_csrf.token}">
							<input type="hidden" name="mid" id="mid" value="${mid}">
				<input type="hidden" name="tid" id="tid" <c:out value="${tid}"/>> 
				<input type="hidden" name="mType" id="mType" value="${mType}">
				<input type="hidden" name="job" id="job" value="save">

				<!-- 1st card -->
				<input type="hidden" name="status1" id="status1" value=" ">
				<input type="hidden" name="cName1" id="cName1" value=" "> 
				<input type="hidden" name="cNumber1" id="cNumber1" value=" "> 
				<input type="hidden" name="lcvv1" id="lcvv1" value=" "> 
				<input type="hidden" name="lextd1" id="lextd1" value=" "> 
				<input type="hidden" name="lamount1" id="lamount1" value=" "> 
				<input type="hidden" name="cref1" id="cref1" value=" ">

				<!-- 2nd card -->
				<input type="hidden" name="status2" id="status2" value=" ">
				<input type="hidden" name="cName2" id="cName2" value=" "> 
				<input type="hidden" name="cNumber2" id="cNumber2" value=" "> 
				<input type="hidden" name="lcvv2" id="lcvv2" value=" "> 
				<input type="hidden" name="lextd2" id="lextd2" value=" "> 
				<input type="hidden" name="lamount2" id="lamount2" value=" "> 
				<input type="hidden" name="cref2" id="cref2" value=" ">

				<!-- 3rd card -->
				<input type="hidden" name="status3" id="status3" value=" ">
				<input type="hidden" name="cName3" id="cName3" value=" "> 
				<input type="hidden" name="cNumber3" id="cNumber3" value=" "> 
				<input type="hidden" name="lcvv3" id="lcvv3" value=" "> 
				<input type="hidden" name="lextd3" id="lextd3" value=" "> 
				<input type="hidden" name="lamount3" id="lamount3" value=" "> 
				<input type="hidden" name="cref3" id="cref3" value=" ">

				<!-- 4th card -->
				<input type="hidden" name="status4" id="status4" value=" ">
				<input type="hidden" name="cName4" id="cName4" value=" "> 
				<input type="hidden" name="cNumber4" id="cNumber4" value=" "> 
				<input type="hidden" name="lcvv4" id="lcvv4" value=" "> 
				<input type="hidden" name="lextd4" id="lextd4" value=" "> 
				<input type="hidden" name="lamount4" id="lamount4" value=" "> 
				<input type="hidden" name="cref4" id="cref4" value=" ">
				
				<!-- 5th card -->
				<input type="hidden" name="status5" id="status5" value=" ">
				<input type="hidden" name="cName5" id="cName5" value=" "> 
				<input type="hidden" name="cNumber5" id="cNumber5" value=" "> 
				<input type="hidden" name="lcvv5" id="lcvv5" value=" "> 
				<input type="hidden" name="lextd5" id="lextd5" value=" "> 
				<input type="hidden" name="lamount5" id="lamount5" value=" "> 
				<input type="hidden" name="cref5" id="cref5" value=" ">
				
				<!-- 6th card -->
				<input type="hidden" name="status6" id="status6" value=" ">
				<input type="hidden" name="cName6" id="cName6" value=" "> 
				<input type="hidden" name="cNumber6" id="cNumber6" value=" "> 
				<input type="hidden" name="lcvv6" id="lcvv6" value=" "> 
				<input type="hidden" name="lextd6" id="lextd6" value=" "> 
				<input type="hidden" name="lamount6" id="lamount6" value=" "> 
				<input type="hidden" name="cref6" id="cref6" value=" ">

				<!-- 7th card -->
				<input type="hidden" name="status7" id="status7" value=" ">
				<input type="hidden" name="cName7" id="cName7" value=" "> 
				<input type="hidden" name="cNumber7" id="cNumber7" value=" "> 
				<input type="hidden" name="lcvv7" id="lcvv7" value=" "> 
				<input type="hidden" name="lextd7" id="lextd7" value=" "> 
				<input type="hidden" name="lamount7" id="lamount7" value=" "> 
				<input type="hidden" name="cref7" id="cref7" value=" ">


				<!-- 8th card -->
				<input type="hidden" name="status8" id="status8" value=" ">
				<input type="hidden" name="cName8" id="cName8" value=" "> 
				<input type="hidden" name="cNumber8" id="cNumber8" value=" "> 
				<input type="hidden" name="lcvv8" id="lcvv8" value=" "> 
				<input type="hidden" name="lextd8" id="lextd8" value=" "> 
				<input type="hidden" name="lamount8" id="lamount8" value=" "> 
				<input type="hidden" name="cref8" id="cref8" value=" ">

				<!-- 9th card -->
				<input type="hidden" name="status9" id="status9" value=" ">
				<input type="hidden" name="cName9" id="cName9" value=" "> 
				<input type="hidden" name="cNumber9" id="cNumber9" value=" "> 
				<input type="hidden" name="lcvv9" id="lcvv9" value=" "> 
				<input type="hidden" name="lextd9" id="lextd9" value=" "> 
				<input type="hidden" name="lamount9" id="lamount9" value=" "> 
				<input type="hidden" name="cref9" id="cref9" value=" ">

				<!-- 10th card -->
				<input type="hidden" name="status10" id="status10" value=" ">
				<input type="hidden" name="cName10" id="cName10" value=" ">
				<input type="hidden" name="cNumber10" id="cNumber10" value=" ">
				<input type="hidden" name="lcvv10" id="lcvv10" value=" "> 
				<input type="hidden" name="lextd10" id="lextd10" value=" "> 
				<input type="hidden" name="lamount10" id="lamount10" value=" "> 
				<input type="hidden" name="cref10" id="cref10" value=" ">

				<!-- 11th card -->
				<input type="hidden" name="status11" id="status11" value=" ">
				<input type="hidden" name="cName11" id="cName11" value=" ">
				<input type="hidden" name="cNumber11" id="cNumber11" value=" ">
				<input type="hidden" name="lcvv11" id="lcvv11" value=" "> 
				<input type="hidden" name="lextd11" id="lextd11" value=" "> 
				<input type="hidden" name="lamount11" id="lamount11" value=" "> 
				<input type="hidden" name="cref11" id="cref11" value=" ">
				
				<!-- 12th card -->
				<input type="hidden" name="status12" id="status12" value=" ">
				<input type="hidden" name="cName12" id="cName12" value=" ">
				<input type="hidden" name="cNumber12" id="cNumber12" value=" ">
				<input type="hidden" name="lcvv12" id="lcvv12" value=" "> 
				<input type="hidden" name="lextd12" id="lextd12" value=" "> 
				<input type="hidden" name="lamount12" id="lamount12" value=" "> 
				<input type="hidden" name="cref12" id="cref12" value=" ">

				<!-- 13th card -->
				<input type="hidden" name="status13" id="status13" value=" ">
				<input type="hidden" name="cName13" id="cName13" value=" ">
				<input type="hidden" name="cNumber13" id="cNumber13" value=" ">
				<input type="hidden" name="lcvv13" id="lcvv13" value=" "> 
				<input type="hidden" name="lextd13" id="lextd13" value=" "> 
				<input type="hidden" name="lamount13" id="lamount13" value=" "> 
				<input type="hidden" name="cref13" id="cref13" value=" ">

				<!-- 14th card -->
				<input type="hidden" name="status14" id="status14" value=" ">
				<input type="hidden" name="cName14" id="cName14" value=" ">
				<input type="hidden" name="cNumber14" id="cNumber14" value=" ">
				<input type="hidden" name="lcvv14" id="lcvv14" value=" "> 
				<input type="hidden" name="lextd14" id="lextd14" value=" "> 
				<input type="hidden" name="lamount14" id="lamount14" value=" "> 
				<input type="hidden" name="cref14" id="cref14" value=" ">

				<!-- 15th card -->
				<input type="hidden" name="status15" id="status15" value=" ">
				<input type="hidden" name="cName15" id="cName15" value=" ">
				<input type="hidden" name="cNumber15" id="cNumber15" value=" ">
				<input type="hidden" name="lcvv15" id="lcvv15" value=" "> 
				<input type="hidden" name="lextd15" id="lextd15" value=" "> 
				<input type="hidden" name="lamount15" id="lamount15" value=" "> 
				<input type="hidden" name="cref15" id="cref15" value=" ">
							
							</form>
							
							 <span id="card_html"></span>
 
				<div class="row">
				<div class="input-field col s12 m12 l12"> 
				<span class="right">
				 <button class="btn waves-effect waves-light btn indigo" id="cancel"
				  onclick="load('cancel')"> Cancel</button>
				  <button class="btn waves-effect waves-light btn orange " id="save"
				onclick="return add('save')"> Save</button>
				   <button class="btn waves-effect waves-light btn btn-round green" id="sumbit"
				onclick="return add('submit')"> Submit</button>
				   </span>
				</div>
				</div>
    </div></div></div></div></div>    
 
  <script>
  var x = 0; 
var max_fields = 100;

  function AddMore() {   
		
		if(x < max_fields){
    x++; 
	var html='';
	          html+='<div class="row inner_row_'+x+'">';
	          
				html+='<div class="input-field col s12 m2 l2">';
				html+='<label>';
				html+='<input type="checkbox" name="ckId" id="ckId1" >';
				html+= '<span>Select</span>';
				html+='</label>';
				html+='</div>';
					html+='<div class="input-field col s12 m2 l2">';
						html+='<input type="text" class="form-control c1" id="cardName'+ id +'" style="width: 100%;" maxlength="26"'
			    		 +'name="cardName[]" value="" placeholder="Card Name">';
						/* html+='<label for="card">Card Name</label>'; */
					html+='</div>';
					html+='<div class="input-field col s12 m3 l3">';
						html+='<input type="text" class="form-control creditCardText" id="cardNo'+ id +'" maxlength="19" onclick="return isNumberKey1(event)"'
			    		 +'name="cardNo[]" value="" placeholder="Card Number">';
						/* html+='<label for="name">Card Number</label>'; */
					html+='</div>';
					html+='<div class="input-field col s12 m1 l1">';
						html+='<input type="password" class="form-control" id="cvv'+ id +'" maxlength="3" onkeypress="return isNumberKey(event)" onpaste="return false;"'
			    		 +'name="cvv[]" value="" placeholder="CVV">';
					/* 	html+='<label for="name">CVV</label>'; */
					html+='</div>';
					html+='<div class="input-field col s12 m1 l1">';
						html+='<input type="text" class="form-control creditCardExdt" id="exdt'+ id +'"  maxlength="5" onkeypress="return isNumberKey2(event)" onpaste="return false;"'
			    		 +'name="exdt[]" value="" placeholder="MM-YY">';
						/* html+='<label for="name">MM-YY</label>'; */
					html+='</div>';
					
					html+='<div class="input-field col s12 m2 l2">';
						html+='<input type="text" class="form-control" id="amount'+ id +'" maxlength="10"'
			    		 +'name="amount[]" value="" placeholder="Amount">';
						/* html+='<label for="name">Amount</label>'; */
					html+='</div>';
					
			
					
					html+='<div class="input-field col s12 m1 l1">';
						html+='<button class="btn btn-danger waves-effect waves-light btn btn-round red" onclick="removeRow('+x+')">Remove</button>';
						html+='';
					html+='</div>';
				html+='</div>';
			

 
		 
		 $('#card_html').append(html); 
		 $('.mm-yy').formatter({'pattern': '{{99}}-{{99}}','persistent': true});
		 $('.cvv').formatter({'pattern': '{{999}}','persistent': true});
		}
  } 		

  function removeRow(id){   $('.inner_row_'+id).remove();   x--; }
  
  

  </script>        
</body>
</html>
