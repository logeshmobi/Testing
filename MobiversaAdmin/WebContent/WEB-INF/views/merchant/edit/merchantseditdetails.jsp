<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html lang="en-US">
<head>

<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<style>
.error {
	color: red;
	font-weight: bold;
}

.select-dropdown li:first-child {
	/* visibility: hidden !important; */
	/*  opacity: 0 !important; */
	display: none !important;
}
</style>

<!-- <script type="text/javascript">
        jQuery(document).ready(function() {


        $('#salutation').select2();
        $('#businessState').select2();
         $('#ownerSalutation1').select2();
        $('#ownerSalutation2').select2();
         $('#ownerSalutation3').select2();
        $('#ownerSalutation4').select2();
        $('#ownerSalutation5').select2();
        $('#businessType').select2();
        $('#companyType').select2();
        $('#natureOfBusiness').select2();
        $('#documents').select2();
        $('#signedPackage').select2();
        $('#status').select2();
        $('#agentName').select2();


        });
            </script> -->


<script lang="JavaScript">


        function loadSelectData() {

            var payoutAsyncHandlerYes = document.getElementById('payoutAsyncHandlerYes');
            var payoutAsyncHandlerNo = document.getElementById('payoutAsyncHandlerNo');

            var payoutNotificationUrlElement = document.getElementById("payoutNotificationUrl");
            var payoutNotificationUrl = payoutNotificationUrlElement ? payoutNotificationUrlElement.value : "";
            if (payoutAsyncHandlerYes.checked && payoutNotificationUrl.trim() === "") {
                alert("Please provide a valid payout notification URL.");
                return false;
            }

            var reasonForAsyncPayoutElement = document.getElementById("reasonForAsyncPayout");
            var reasonForAsyncPayout = reasonForAsyncPayoutElement ? reasonForAsyncPayoutElement.value : "";
            if (payoutAsyncHandlerYes.checked && reasonForAsyncPayout.trim() === "") {
                alert("Please provide a reason for the asynchronous payout.");
                return false;
            }

            var motomidElement = document.getElementById("ezymotomid");
            var motomid = motomidElement ? motomidElement.value : "";

            if (motomid != '') {
                if (!allnumeric(motomid, 10, 15)) {

                    return false;
                }
            }

            var ezypassmidElement = document.getElementById("ezypassmid");
            var ezypassmid = ezypassmidElement ? ezypassmidElement.value : "";

            if (ezypassmid != '') {
                if (!allnumeric(ezypassmid, 10, 15)) {
                    return false;
                }
            }


            // if (document.form1.ezypassmid.value != '') {
            // if (!allnumeric(document.form1.ezypassmid, 10, 15)) {
            //
            // 	return false;
            // }
            //
            // }
            if (!allLetterSpaceSpecialCharacter(document.form1.registeredName, 3, 100)) {
                return false;
            }
            if (!allLetterSpaceSpecialCharacter(document.form1.businessName, 3, 100)) {

                return false;
            }

            if (!alphanumeric(document.form1.businessRegNo, 6, 15)) {

                return false;
            }
            if (!stringlength(document.form1.registeredAddress, 3, 100)) {
                return false;
            }

            if (!allLetter(document.form1.businessCity, 3, 35)) {
                return false;
            }
            if (!alphanumeric(document.form1.businessPostCode, 5, 5)) {

                return false;
            }

            var e8 = document.getElementById("txtBusinessState").value;
            if (e8 == null || e8 == '') {

                alert("Please Select state");
                return false;
            }


            if (!allLetterSpaceSpecialCharacter(document.form1.name, 3, 30)) {

                return false;
            }

            if (!validateEmail(document.form1.email, 10, 30)) {

                return false;
            }

            if (!allnumeric(document.form1.contactNo, 9, 15)) {

                return false;
            }

            var e1 = document.getElementById("ownerCount").value;
            var i = 0;

            if (e1 == 1) {

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3, 50)) {
                    return false;
                }

                if (!allnumeric(document.form1.ownerContactNo1, 9, 15)) {
                    return false;
                }

                if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
                    return false;
                }

                if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
                    return false;
                }
            } else if (e1 == 2) {

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3, 50)) {
                    return false;
                }

                if (!allnumeric(document.form1.ownerContactNo1, 9, 15)) {
                    return false;
                }

                if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
                    return false;
                }

                if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
                    return false;
                }


                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3, 50)) {
                    return false;
                }


                if (document.form1.ownerContactNo2.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
                        return false;
                    }
                }


                if (document.form1.passportNo2.value != '') {
                    if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress2.value != '') {
                    if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
                        return false;
                    }
                }
            } else if (e1 == 3) {

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3, 50)) {
                    return false;
                }

                if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
                    return false;
                }

                if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
                    return false;
                }

                if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
                    return false;
                }


                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3, 50)) {
                    return false;
                }


                if (document.form1.ownerContactNo2.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo2, 9, 15)) {
                        return false;
                    }
                }


                if (document.form1.passportNo2.value != '') {
                    if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress2.value != '') {
                    if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
                        return false;
                    }
                }


                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3, 50)) {
                    return false;
                }


                if (document.form1.ownerContactNo3.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo3, 9, 15)) {
                        return false;
                    }
                }


                if (document.form1.passportNo3.value != '') {
                    if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress3.value != '') {

                    if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
                        return false;
                    }
                }
            } else if (e1 == 4) {

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3, 50)) {
                    return false;
                }

                if (!allnumeric(document.form1.ownerContactNo1, 9, 15)) {
                    return false;
                }

                if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
                    return false;
                }

                if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
                    return false;
                }

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3, 50)) {
                    return false;
                }


                if (document.form1.ownerContactNo2.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo2, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.passportNo2.value != '') {
                    if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress2.value != '') {
                    if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
                        return false;
                    }

                }
                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3, 50)) {
                    return false;
                }

                if (document.form1.ownerContactNo3.value != '') {

                    if (!allnumeric(document.form1.ownerContactNo3, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.passportNo3.value != '') {
                    if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress3.value != '') {
                    if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
                        return false;
                    }
                }


                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName4, 3, 50)) {
                    return false;
                }


                if (document.form1.ownerContactNo4.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo4, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.passportNo4.value != '') {
                    if (!alphanumeric(document.form1.passportNo4, 9, 15)) {
                        return false;
                    }
                }
                if (document.form1.residentialAddress4.value != '') {
                    if (!stringlength(document.form1.residentialAddress4, 3, 100)) {
                        return false;
                    }
                }
            } else if (e1 == 5) {

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName1, 3, 50)) {
                    return false;
                }

                if (!allnumeric(document.form1.ownerContactNo1, 9, 11)) {
                    return false;
                }

                if (!alphanumeric(document.form1.passportNo1, 9, 15)) {
                    return false;
                }

                if (!stringlength(document.form1.residentialAddress1, 3, 100)) {
                    return false;
                }


                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName2, 3, 50)) {
                    return false;
                }
                if (document.form1.ownerContactNo2.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo2, 9, 11)) {
                        return false;
                    }
                }
                if (document.form1.passportNo2.value != '') {
                    if (!alphanumeric(document.form1.passportNo2, 9, 15)) {
                        return false;
                    }
                }
                if (document.form1.residentialAddress2.value != '') {

                    if (!stringlength(document.form1.residentialAddress2, 3, 100)) {
                        return false;
                    }
                }

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName3, 3, 50)) {
                    return false;
                }
                if (document.form1.ownerContactNo3.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo3, 9, 11)) {
                        return false;
                    }
                }

                if (document.form1.passportNo3.value != '') {
                    if (!alphanumeric(document.form1.passportNo3, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress3.value != '') {
                    if (!stringlength(document.form1.residentialAddress3, 3, 100)) {
                        return false;
                    }
                }

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName4, 3, 50)) {
                    return false;
                }
                if (document.form1.ownerContactNo4.value != '') {
                    if (!allnumeric(document.form1.ownerContactNo4, 9, 11)) {
                        return false;
                    }
                }
                if (document.form1.passportNo4.value != '') {
                    if (!alphanumeric(document.form1.passportNo4, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress4.value != '') {
                    if (!stringlength(document.form1.residentialAddress4, 3, 100)) {
                        return false;
                    }
                }

                if (!allLetterSpaceSpecialCharacter(document.form1.ownerName5, 3, 50)) {
                    return false;
                }
                if (document.form1.ownerContactNo5.value != '') {

                    if (!allnumeric(document.form1.ownerContactNo5, 9, 11)) {
                        return false;
                    }
                }
                if (document.form1.passportNo5.value != '') {

                    if (!alphanumeric(document.form1.passportNo5, 9, 15)) {
                        return false;
                    }
                }

                if (document.form1.residentialAddress5.value != '') {
                    if (!stringlength(document.form1.residentialAddress5, 3, 100)) {
                        return false;
                    }
                }

            }

            if (!allnumeric(document.form1.officeNo, 9, 11)) {

                return false;
            }

            if (document.form1.faxNo.value != '') {
                if (!allnumeric(document.form1.faxNo, 9, 11)) {

                    return false;
                }
            }
            if (!allLetter(document.form1.bankName, 3, 25)) {

                return false;
            }
            if (!allnumeric(document.form1.bankAccNo, 10, 16)) {

                return false;
            }

            var e1 = document.getElementById("businessType").value;

            if (e1 == null || e1 == '') {

                alert("Please Select BusinessType");
                //form.submit = false;
                return false;
            }

            var e2 = document.getElementById("companyType").value;

            if (e2 == null || e2 == '') {

                alert("Please Select companyType");
                //form.submit = false;
                return false;
            }


            var e3 = document.getElementById("natureOfBusiness").value;

            if (e3 == null || e3 == '') {

                alert("Please Select natureOfBusiness");
                //form.submit = false;
                return false;
            }

            if (!allLetterSpaceSpecialCharacter(document.form1.tradingName, 3, 100)) {
                return false;
            }
            if (!allLetterSpace(document.form1.referralId, 3, 30)) {

                return false;

            }

            if (!alphanumeric(document.form1.noOfReaders, 1, 3)) {
                return false;
            }

            if (!allLetterSpace(document.form1.wavierMonth, 3, 30)) {
                return false;
            }


            if (!allLetterSpace(document.form1.yearIncorporated, 3, 30)) {
                return false;
            }

            var e11 = document.getElementById("accType").value;

            var e6 = document.getElementById("preAuth").value;

            if (e6 == null || e6 == '') {

                alert("Please Select preauth");
                // form.submit = false;
                return false;
            }


//    rk add

            // var e12 = document.getElementById("foreignCard").value;
            // e12 = e12 ? e12 : "";
            //
            // if (e12 == null || e12 == '') {
            //
            //     alert("Please Select ForeignCardAcess");
            //     // form.submit = false;
            //     return false;
            // }

            var e12Element = document.getElementById("foreignCard");
            if (e12Element) {
                var e12 = e12Element.value;
            } else {
                e12 = "";
            }
            if ((e12 == null || e12 === '') && adminusername.toUpperCase() === "ETHAN") {
                alert("Please Select ForeignCardAccess");
                return false;
            }


//     rk add

            var e13Element = document.getElementById("ezysettle");
            if (e13Element) {
                var e13 = e13Element.value;
            } else {
                e13 = "";
            }
            if ((e13 == null || e13 === '') && adminusername.toUpperCase() === "ETHAN") {
                alert("Please Select ForeignCardAccess");
                return false;
            }


            var e7 = document.getElementById("txtMdr").value;

            if (e7 == null || e7 == '') {

                alert("Please Select mdr");
                //form.submit = false;
                return false;
            }


            var e9 = document.getElementById("autoSettled").value;

            if (e9 == null || e9 == '') {
                alert("Please Select autoSettled");
                return false;
            }
            return true;


            document.getElementById("form1").submit();
            return true;


        }

        /*
        function loadCancelData()
        {
            //alert("fcancel data");


             document.location.href = "${pageContext.request.contextPath}/admmerchant/list/1";
	form.submit;
}
 */




        /* function addrow(){ */
        //alert(field.value);
        /* 	disableRow();
            var i=document.getElementById("ownerCount").value; */
        /* alert(i);
        if(i>5){
            alert("Data must maximum 5");
            return false;
        } */
        /* 	var a=0; */
        //alert(i);
        /* for(a=2;a<=i;a++){
            //alert(a);
            document.getElementById("owner"+a).style.display = '';
            document.getElementById("owner"+a+a).style.display = '';
        } */
        //document.getElementById(a).style.display = "true";
        //}
        //var ele = document.getElementById(showHideDiv);
        //var text = document.getElementById(switchTextDiv);
        /* } */
        /* function disableRow(){
                document.getElementById("owner2").style.display = 'none';
                document.getElementById("owner22").style.display = 'none';
                document.getElementById("owner3").style.display = 'none';
                document.getElementById("owner33").style.display = 'none';
                document.getElementById("owner4").style.display = 'none';
                document.getElementById("owner44").style.display = 'none';
                document.getElementById("owner5").style.display = 'none';
                document.getElementById("owner55").style.display = 'none';
        } */

        function phonenumber(inputtxt) {
            var phoneno = /^\d{10}$/;
            if (inputtxt.value.match(phoneno)) {
                return true;
            } else {
                alert("Not a valid Phone Number");
                return false;
            }
        }

        function postcode(inputtxt) {
            /* alert(len1);
            int len =len1; */
            var postcode = /^\d{5}$/;
            if (inputtxt.value.match(postcode)) {
                return true;
            } else if (inputtxt.value.length == 0) {
                alert("Please enter " + inputtxt.name);
                return false;
            } else {
                alert("Not a valid " + inputtxt.name);
                return false;
            }
        }

        // This function will validate Name.
        function allLetter(inputtxt, minlength, maxlength) {

            var field = inputtxt.value;
            var mnlen = minlength;
            var mxlen = maxlength;
            // var uname = document.registration.username;
            var letters = /^[A-Za-z- ]+$/;
            if ((field.length == 0) || (field.length < mnlen)
                || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else if (field.match(letters)) {
                // Focus goes to next field i.e. Address.
                //document.form1.address.focus();
                return true;
            } else {
                alert(inputtxt.name + ' must have alphabet characters only');
                //uname.focus();
                return false;
            }
        }


        // This function will validate Name.
        function allLetterSpace(inputtxt, minlength, maxlength) {

            var field = inputtxt.value;
            var mnlen = minlength;
            var mxlen = maxlength;
            // var uname = document.registration.username;
            var letters = /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
            if ((field.length == 0) || (field.length < mnlen)
                || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else if (field.match(letters)) {
                // Focus goes to next field i.e. Address.
                //document.form1.address.focus();
                return true;
            } else {
                alert(inputtxt.name + ' must have alphanumeric and space only');
                //uname.focus();
                return false;
            }
        }

        function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
            //alert("TEste :"+ inputtxt);
            var field = inputtxt.value;
            var mnlen = minlength;
            var mxlen = maxlength;
            // var uname = document.registration.username;
            var letters = /^[ A-Za-z0-9_()./&-]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
            if ((field.length == 0) || (field.length < mnlen)
                || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else if (field.match(letters)) {
                // Focus goes to next field i.e. Address.
                //document.form1.address.focus();
                return true;
            } else {
                alert(inputtxt.name + ' must have alphanumeric and space and special characters with -,&,/,() only');
                //uname.focus();
                return false;
            }
        }

        // This function will validate Address.
        function alphanumeric(inputtxt, minlength, maxlength) {
            var field = inputtxt.value;
            var mnlen = minlength;
            var mxlen = maxlength;
            // var uadd = document.registration.address;
            var letters = /^[0-9a-zA-Z-]+$/;
            if ((field.length < mnlen) || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else if (field.match(letters)) {
                // Focus goes to next field i.e. Country.
                //document.registration.country.focus();
                return true;
            } else {
                alert(inputtxt.name + ' must have alphanumeric with - characters only');
                // uadd.focus();
                return false;
            }
        }

        // This function will validate ZIP Code.
        function allnumeric(inputtxt, minlength, maxlength) {
            var field = inputtxt.value;

            var mnlen = minlength;
            var mxlen = maxlength;
            //var uzip = document.registration.zip;
            var numbers = /^[0-9]+$/;
            if ((field.length < mnlen) || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else if (field.match(numbers)) {
                // Focus goes to next field i.e. email.
                //document.registration.email.focus();
                return true;
            } else {
                alert(inputtxt.name + ' must have numeric characters only');
                //uzip.focus();
                return false;
            }
        }


        // This function will validate Email.
        function validateEmail(inputtxt) {

            //alert(inputtxt);
            var field = inputtxt.value;
            //var uemail = document.registration.email;
            var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,5})+$/;
            if (field.match(mailformat)) {
                //document.registration.desc.focus();
                return true;
            } else {
                alert("You have entered an invalid email address!");
                //uemail.focus();
                return false;
            }
        }

        function stringlength(inputtxt, minlength, maxlength) {
            var field = inputtxt.value;
            var mnlen = minlength;
            var mxlen = maxlength;

            if ((field.length < mnlen) || (field.length > mxlen)) {
                alert("Please input the " + inputtxt.name + " between " + mnlen
                    + " and " + mxlen + " characters");
                return false;
            } else {
                //alert('Your userid have accepted.');
                return true;
            }

        }


        function copyAddr1() {
            //alert(document.getElementById("addr1").checked);
            var copy = document.getElementById("addr1").checked;
            if (copy) {
                document.getElementById("Business address").value = document.getElementById("Registered Address").value;
            } else {
                document.getElementById("Business address").value = '';
            }
        }

        function copyAddr2() {
//alert("copy addr:2");
            //alert(document.getElementById("addr2").checked);
            var copy = document.getElementById("addr2").checked;
            if (copy) {
                document.getElementById("Mailing address").value = document.getElementById("Registered Address").value;
            } else {
                document.getElementById("Mailing address").value = '';
            }
        }


        function radioAuth() {
//var a= ${merchant.preAuth};
            var a = document.getElementById("testAuth").value;
//alert("Auth :"+a);
            if (a == 'Yes') {
                document.getElementById("preAuth1").checked = true;
            } else {
                document.getElementById("preAuth2").checked = true;
            }
            /* if($)
                document.getElementById("preAuth").value =  */
        }


        // rk add

        function radioForeign() {
            //var a= ${merchant.preAuth};
            var a = document.getElementById("testForeign").value;
            //alert("Auth :"+a);
            if (a == 'Yes') {
                document.getElementById("preAuth1").checked = true;
            } else {
                document.getElementById("preAuth2").checked = true;
            }
            /* if($)
                document.getElementById("preAuth").value =  */
        }


        //rk add


        function radioSettled() {
//var a= ${merchant.preAuth};
            var a = document.getElementById("testSettle").value;
//alert("Settle :"+a);
            if (a == 'Yes') {
                document.getElementById("autoSettled1").checked = true;
            } else {
                document.getElementById("autoSettled2").checked = true;
            }
            /* if($)
                document.getElementById("preAuth").value =  */
        }

    </script>
<script lang="javascript">

        // function loadstatee() {
        //     //alert('dgfbfdggfg');
        //     var st = document.getElementById("businessState1").value;
        //     //alert('com val : '+st);
        //     var state = document.getElementById("businessState");
        //     //alert('ddd :'+state);
        //     var i = 0;
        //     for (i = 0; i < state.options.length; i++) {
        //         if (state.options[i].value == st) {
        //
        //             // Item is found. Set its property and exit
        //
        //             state.options[i].selected = true;
        //         }
        //     }
        //
        // }


        function loadtype(type, type1) {
            if (!type1 || !type1.options) {
                console.error("Invalid select element or options not found");
                return;
            }

            for (var i = 0; i < type1.options.length; i++) {
                if (type1.options[i].value == type) {
                    type1.options[i].selected = true;
                    break; // Exit after setting the option to avoid unnecessary iterations
                }
            }
        }


        function loadbusinesstype() {
            //alert('testbusiness type');
            var bustype = document.getElementById("businessType1").value;
            //alert('com val : '+bustype);
            var bustype1 = document.getElementById("businessType");
            //alert('ddd :'+bustype1);
            var i = 0;
            for (i = 0; i < bustype1.options.length; i++) {
                if (bustype1.options[i].value == bustype) {

                    // Item is found. Set its property and exit

                    bustype1.options[i].selected = true;
                }
            }

        }


        function loadbusinesstype() {
            //alert('testbusiness type');
            var bustype = document.getElementById("businessType1").value;
            //alert('com val : '+bustype);
            var bustype1 = document.getElementById("businessType");
            //alert('ddd :'+bustype1);
            var i = 0;
            for (i = 0; i < bustype1.options.length; i++) {
                if (bustype1.options[i].value == bustype) {

                    // Item is found. Set its property and exit

                    bustype1.options[i].selected = true;
                }
            }

        }


        // function loadtype(type, type1) {
        //     //alert(' type'+type);
        //     // var bustype = document.getElementById("businessType1").value;
        //     //alert('type1 : '+type1);
        //     //var bustype1 = document.getElementById("businessType");
        //     //alert('ddd :'+bustype1);
        //     var i = 0;
        //     for (i = 0; i < type1.options.length; i++) {
        //         if (type1.options[i].value == type) {
        //
        //             // Item is found. Set its property and exit
        //
        //             type1.options[i].selected = true;
        //         }/* else{
        // 	type1.options[0].selected = true;
        // } */
        //     }
        //
        // }

        function loadDropData() {
//alert("locadDropData..")
            /* <input type="hidden" value="

            ${merchant.ownerSalutation2}" id="salutation2"/>
						<select class="form-control" name="ownerSalutation2" path="ownersalutation2" id="ownerSalutation2" style="width: 195px;"> */

            //state
            //alert('state');
            var st = "hello";
            //alert('state val : '+st);
            var state = document.getElementById("businessState");
            loadtype(st, state);

            //contact sal
            //alert('sal');
            var sal = document.getElementById("contsalutation").value;
            //alert('sal val : '+sal);
            var salu = document.getElementById("salutation");
            loadtype(sal, salu);

            //owner sal1
            //alert('sal1');
            var sal1 = document.getElementById("salutation1").value;
            //alert('sal1 val : '+sal1);
            var salu1 = document.getElementById("ownerSalutation1");
            loadtype(sal1, salu1);

            //owner sal2
            //alert('sal2');
            var sal2 = document.getElementById("salutation2").value;
            //alert('sal2 val : '+sal2);
            var salu2 = document.getElementById("ownerSalutation2");
            loadtype(sal2, salu2);

            //owner sal3
            //alert('sal3');
            var sal3 = document.getElementById("salutation3").value;
            //alert('sal3 val : '+sal3);
            var salu3 = document.getElementById("ownerSalutation3");
            loadtype(sal3, salu3);

            //owner sal4
            //alert('sal4');
            var sal4 = document.getElementById("salutation4").value;
            //alert('sal4 val : '+sal4);
            var salu4 = document.getElementById("ownerSalutation4");
            loadtype(sal4, salu4);

            //owner sal5
            //alert('sal5');
            var sal5 = document.getElementById("salutation5").value;
            //alert('sal5 val : '+sal5);
            var salu5 = document.getElementById("ownerSalutation5");
            loadtype(sal5, salu5);


            //bus type
            //alert('bustype');
            var bustype = document.getElementById("businessType1").value;
            //alert('business val : '+bustype);
            var bustype1 = document.getElementById("businessType");
            loadtype(bustype, bustype1);

            //company type
            //alert('comptype');
            var comptype = document.getElementById("companyType1").value;
            //alert('com val : '+comptype);
            var comptyp1 = document.getElementById("companyType");
            loadtype(comptype, comptyp1);

            //NOB
            //alert('nob');
            var nob = document.getElementById("natureOfBusiness1").value;
            //alert('nob val : '+nob);
            var nob1 = document.getElementById("natureOfBusiness");
            loadtype(nob, nob1);

            //Doc
            //alert('documents');
            var doc = document.getElementById("documents1").value;
            //alert('doc val : '+doc);
            var doc1 = document.getElementById("documents");
            loadtype(doc, doc1);

            //signed package
            //alert('signed package');
            var sigpack = document.getElementById("signedPackage1").value;
            //alert('com val : '+sigpack);
            var sigpack1 = document.getElementById("signedPackage");
            loadtype(sigpack, sigpack1);

            //status
            //alert('status');
            // var status = document.getElementById("status1").value;
            //alert('status val : '+status);
            //var status1 = document.getElementById("status");
            //loadtype(status,status1);

            //agentName
            //alert('agent');
            var agent = document.getElementById("AgentUserName").value;
            //alert('agent val : '+agent);
            var agent1 = document.getElementById("agentName");
            loadtype(agent, agent1);


            //NOB
            //alert('nob');
            var accT = document.getElementById("accType1").value;
            //alert('nob val : '+nob);
            var accT1 = document.getElementById("accType");
            loadtype(accT, accT1);


        }


        //}
    </script>
</head>
<body onload="addrow();loadDropData();">
	<!--  radioAuth();radioSettled();addrow();loadstatee(); /// loadstatee();loadsal1();loadsal2();loadbusinesstype()-->


	<div class="container-fluid">
		<div class="row">


			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Edit Merchant Details </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		<form
			action="${pageContext.request.contextPath}<%=MerchantWebController.URL_BASE%>/editMerchant?${_csrf.parameterName}=${_csrf.token}"
			method="post" name="form1" id="form1" commandName="merchant"
			enctype="multipart/form-data">
			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="hidden" name="id"
								value="${merchant.id}" />

							<div>
								<c:if test="${responseData != null}">
									<H4 style="color: #ff4000;" align="center">${responseData}</H4>
								</c:if>
							</div>

							<div class="d-flex align-items-center">
								<%-- <a class="btn btn-primary blue-btn"
                            href="${pageContext.request.contextPath}/admmerchant/addMerchant">Add
                            New <i class="material-icons">add</i>
                        </a> --%>
								</a>

							</div>

							<div class="d-flex align-items-center">
								<h3>
									Office Email: ${merchant.officeEmail} <input type="hidden"
										class="form-control" id="txtMid" name="officeEmail"
										value="${merchant.officeEmail}">
								</h3>
							</div>

							<div class="d-flex align-items-center">
								<h4>Merchant Details</h4>
							</div>

							<%-- <div class="row">

                        <c:choose>
                            <c:when test="${empty merchant.mid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzywireMID">EZYWIRE MID</label> <input
                                        type="text" id="mid" name="mid" path="mid"
                                        value="${merchant.mid}" placeholder="EZYWIRE MID" />
                                </div>

                            </c:when>
                            <c:otherwise>
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzywireMID">EZYWIRE MID</label> <input
                                        type="text" id="mid" name="mid" readonly="true" path="mid"
                                        value="${merchant.mid}" placeholder="EZYWIRE MID" />

                                </div>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${empty merchant.ezymotomid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyMotoMID">EZYMOTO MID</label> <input
                                        type="text" id="ezymotomid" name="ezymotomid"
                                        path="ezymotomid" value="${merchant.ezymotomid}"
                                        placeholder="EZYMOTO MID" />

                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyMotoMID">EZYMOTO MID</label> <input
                                        type="text" id="ezymotomid" name="ezymotomid"
                                        readonly="true" path="ezymotomid"
                                        value="${merchant.ezymotomid}" placeholder="EZYMOTO MID" />

                                </div>

                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${empty merchant.ezypassmid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyPassMID">EZYPASS MID</label> <input
                                        type="text" id="ezypassmid" name="ezypassmid"
                                        path="ezypassmid" value="${merchant.ezypassmid}"
                                        placeholder="EZYPASS MID" />

                                </div>
                            </c:when>

                            <c:otherwise>

                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyPassMID">EZYPASS MID</label> <input
                                        type="text" id="ezypassmid" name="ezypassmid"
                                        readonly="true" path="ezypassmid"
                                        value="${merchant.ezypassmid}" placeholder="EZYPASS MID" />
                                </div>




                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="row">
                        <c:choose>
                            <c:when test="${empty merchant.ezywaymid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyWayMID">EZYWAY MID</label> <input type="text"
                                        id="ezywaymid" name="ezywaymid" path="ezywaymid"
                                        value="${merchant.ezywaymid}" placeholder="EZYWAY MID" />

                                </div>
                            </c:when>

                            <c:otherwise>

                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyWayMID">EZYWAY MID</label> <input type="text"
                                        id="ezywaymid" name="ezywaymid" readonly="true"
                                        path="ezywaymid" value="${merchant.ezywaymid}"
                                        placeholder="EZYWAY MID" />

                                </div>



                            </c:otherwise>
                        </c:choose>


                        <c:choose>
                            <c:when test="${empty merchant.ezyrecmid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyRecMID">EZYREC MID</label> <input type="text"
                                        id="ezyrecmid" name="ezyrecmid" path="ezyrecmid"
                                        value="${merchant.ezyrecmid}" placeholder="EZYREC MID" />
                                </div>

                            </c:when>

                            <c:otherwise>

                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyRecMID">EZYREC MID</label> <input type="text"
                                        id="ezyrecmid" name="ezyrecmid" readonly="true"
                                        path="ezyrecmid" value="${merchant.ezyrecmid}"
                                        placeholder="EZYREC MID" />
                                </div>




                            </c:otherwise>
                        </c:choose>

                    </div>

--%>

							<!-- umobile mid details  -->

							<div class="row">
								<c:choose>
									<c:when test="${empty merchant.umMid }">
										<div class="input-field col s12 m4 l4">
											<label for="umMid">UM_MID</label> <input type="text"
												id="umMid" name="umMid" path="umMid"
												value="${merchant.umMid}" placeholder="UM_MID" />
										</div>

									</c:when>
									<c:otherwise>
										<div class="input-field col s12 m4 l4">
											<label for="umMid">UM_MID</label> <input type="text"
												id="umMid" name="umMid" readonly="true" path="umMid"
												value="${merchant.umMid}" placeholder="UM_MID" />
										</div>

									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty merchant.umMotoMid }">

										<div class="input-field col s12 m4 l4">
											<label for="umMotoMid">UM_EZYMOTO MID</label> <input
												type="text" id="umMotoMid" name="umMotoMid" path="umMotoMid"
												value="${merchant.umMotoMid}" placeholder="UM_EZYMOTO MID" />
										</div>

									</c:when>
									<c:otherwise>
										<div class="input-field col s12 m4 l4">
											<label for="umMotoMid">UM_EZYMOTO MID</label> <input
												type="text" id="umMotoMid" name="umMotoMid" readonly="true"
												path="umMotoMid" value="${merchant.umMotoMid}"
												placeholder="UM_EZYMOTO MID" />
										</div>


									</c:otherwise>
								</c:choose>

								<%-- <c:choose>
                            <c:when test="${empty merchant.umEzypassMid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="umEzypassMid">UM_EZYPASS MID</label> <input
                                        type="text" id="umEzypassMid" name="umEzypassMid"
                                        path="umEzypassMid" value="${merchant.umEzypassMid}"
                                        placeholder="UM_EZYPASS MID" />
                                </div>

                            </c:when>

                            <c:otherwise>

                                <div class="input-field col s12 m4 l4">
                                    <label for="umEzypassMid">UM_EZYPASS MID</label> <input
                                        type="text" id="umEzypassMid" name="umEzypassMid"
                                        readonly="true" path="umEzypassMid"
                                        value="${merchant.umEzypassMid}"
                                        placeholder="UM_EZYPASS MID" />
                                </div>




                            </c:otherwise>
                        </c:choose> --%>
							</div>

							<div class="row">
								<c:choose>
									<c:when test="${empty merchant.umEzywayMid }">
										<div class="input-field col s12 m4 l4">
											<label for="umEzywayMid">UM_EZYWAY MID</label> <input
												type="text" id="umEzywayMid" name="umEzywayMid"
												path="umEzywayMid" value="${merchant.umEzywayMid}"
												placeholder="UM_EZYWAY MID" />
										</div>

									</c:when>

									<c:otherwise>

										<div class="input-field col s12 m4 l4">
											<label for="umEzywayMid">UM_EZYWAY MID</label> <input
												type="text" id="umEzywayMid" name="umEzywayMid"
												readonly="true" path="umEzywayMid"
												value="${merchant.umEzywayMid}" placeholder="UM_EZYWAY MID" />
										</div>


									</c:otherwise>
								</c:choose>


								<%-- <c:choose>
                            <c:when test="${empty merchant.umEzyrecMid }">
                                <div class="input-field col s12 m4 l4">
                                    <label for="umEzyrecMid">UM_EZYREC MID</label> <input
                                        type="text" id="umEzyrecMid" name="umEzyrecMid"
                                        path="umEzyrecMid" value="${merchant.umEzyrecMid}"
                                        placeholder="UM_EZYREC MID" />
                                </div>

                            </c:when>

                            <c:otherwise>

                                <div class="input-field col s12 m4 l4">
                                    <label for="EzyRecMID">UM_EZYREC MID</label> <input
                                        type="text" id="ezyrecmid" name="ezyrecmid" readonly="true"
                                        path="ezyrecmid" value="${merchant.ezyrecmid}"
                                        placeholder="UM_EZYREC MID" />
                                </div>




                            </c:otherwise>
                        </c:choose> --%>
                        
                        
                        		<c:choose>
									<c:when test="${empty merchant.fiuuMid }">
										<div class="input-field col s12 m4 l4">
											<label for="fiuuMid">FIUU MID</label> <input
												type="text" id="fiuuMid" name="fiuuMid"
												path="fiuuMid" value="${merchant.fiuuMid}"
												placeholder="FIUU MID" />
										</div>

									</c:when>

									<c:otherwise>

										<div class="input-field col s12 m4 l4">
											<label for="fiuuMid">FIUU MID</label> <input
												type="text" id="fiuuMid" name="fiuuMid"
												readonly="true" path="fiuuMid"
												value="${merchant.fiuuMid}" placeholder="FIUU MID" />
										</div>


									</c:otherwise>
								</c:choose>

							</div>

							<!-- umobile end -->

						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="RegName">Registered Name</label> <input type="text"
										id="registeredName" name="registeredName"
										value="${merchant.registeredName}"
										placeholder="Registered Name" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="BusinessName">Business Name</label> <input
										type="text" id="txtBusName" name="businessName"
										value="${merchant.businessName}" placeholder="Business Name" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="BusinessRegNo">Business Reg No</label> <input
										type="text" id="txtBusinessRegNo" name="businessRegNo"
										path="businessRegNo" value="${merchant.businessRegNo}"
										placeholder="Business Reg No" />
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
								<h4>Address Details</h4>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<div class="checkboxes i">
										<label> <span>&nbsp;</span>
										</label>
									</div>
									<label for="RegAddress">Registered Address</label>
									<textarea rows="3" id="Registered Address"
										name="registeredAddress" path="registeredAddress"
										onblur="copyAddr1();copyAddr2()">${merchant.registeredAddress}</textarea>
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<div class="checkboxes i">
										<label> <input type="checkbox" name="addr1" id="addr1"
											onclick="copyAddr1()"> <span>Same Reg Address</span>
										</label>
									</div>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="BusAddress">Business Address</label>
									<textarea rows="3" id="Business address" name="businessAddress">${merchant.businessAddress}</textarea>
								</div>
							</div>
							<div class="row">

								<div class="input-field col s12 m4 l4">
									<div class="checkboxes i">
										<label> <input type="checkbox" name="addr2" id="addr2"
											onclick="copyAddr2()"> <span>Same Reg Address</span>
										</label>
									</div>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="MailAddress">Mailing address</label>
									<textarea rows="3" id="Mailing address" name="mailingAddress">${merchant.mailingAddress}</textarea>
								</div>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="BusinessCity">Business City</label> <input
										type="text" id="txtBusinessCity" placeholder="Business City"
										name="businessCity" value="${merchant.businessCity}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="BusinessPostCode">Business PostCode</label> <input
										type="text" id="txtBusinessPostCode"
										placeholder="Business PostCode" name="businessPostCode"
										value="${merchant.businessPostCode}" />
								</div>
								<div class="input-field col s12 m4 l4">

									<input type="text" id="txtBusinessState"
										placeholder="Business State" name="businessState"
										value="${merchant.businessState}" /> <label>Business
										State</label>
								</div>
							</div>
							<div class="d-flex align-items-center">
								<h4>Contact Person Details</h4>
							</div>
							<div class="row">

								<div class="input-field col s12 m4 l4">

									<input type="hidden" value="${merchant.salutation}"
										id="contsalutation" /> <select name="salutation"
										id="salutation">


										<option value="">${merchant.salutation}</option>
										<option value="">Salutation</option>
										<option value="Ms.">Ms.</option>
										<option value="Madam.">Madam.</option>
										<option value="Mr.">Mr.</option>
										<option value="Mrs.">Mrs.</option>
										<option value="Dr.">Dr.</option>
										<option value="Dato.">Dato.</option>
										<option value="Datin.">Datin.</option>
										<option value="Datin.">Datin.</option>
										<option value="Sri.">Sri.</option>
										<option value="Tan Sri.">Tan Sri.</option>
									</select> <label>Salutation</label>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Name">Contact Person Name</label> <input
										type="text" class="form-control" id="name"
										placeholder="Contact Person Name" name="name"
										value="${merchant.name}" />
								</div>

								<div class="input-field col s12 m4 l4">
									<label for="email">Email</label> <input type="text"
										class="form-control" id="email" placeholder="Email"
										name="email" path="email" value="${merchant.email}"
										oninput="checkEmail()" /> <span id="emailError"
										style="color: red;"></span>
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="secondary_email">Secondary Email</label> <input type="text"
																								class="form-control"
																								id="secondary_email"
																								placeholder="Secondary email"
																								name="secondary_email"
																								path="secondary_email"
																								value="${merchantInfo.secoundaryEmail}"
																								oninput="checkSecEmail()"/>
									<span id="emailError2" style="color: red;"></span>
								</div>



							<%-- 								<div class="input-field col s12 m4 l4">
									<label for="secondary_email">Secondary Email submit</label> <input
										type="text" class="form-control" id="secondary_email"
										placeholder="Secondary email" name="secondary_email"
										path="secondary_email" value="${merchantInfo.secoundaryEmail}" />
								</div> --%>


								<div class="input-field col s12 m4 l4">
									<label for="ContactNo">Contact No</label> <input type="text"
										class="form-control" id="contactNo" placeholder="Contact No"
										name="contactNo" value="${merchant.contactNo}">
								</div>
							</div>


							<div class="d-flex align-items-center">
								<h4>Owner's/Director's Details</h4>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<!-- <label>Owner's/Director's Details</label> -->
									<input type="text" path="ownerCount" id="ownerCount"
										name="ownerCount" value="${merchant.ownerCount}"
										onblur="return addrow()" />

									<%-- path="ownerCount" id="ownerCount" name="ownerCount" value="${merchant.ownerCount}" onblur="return addrow()" --%>
									<%-- 	<form:input type="text" class="form-control" id="ownerCount" value="1" onblur="return addrow()" name="ownerCount" path="ownerCount"/> --%>
								</div>
							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">

									<%-- 	<form:select class="js-select" name="ownerSalutation1" path="ownerSalutation1" placeholder="OwnerSalutation1"> --%>
									<input type="hidden" value="${merchant.ownerSalutation1}"
										id="salutation1" /> <select name="ownerSalutation1"
										id="ownerSalutation1">


										<option value="">${merchant.ownerSalutation1}</option>
										<option value="">Salutation</option>
										<option value="Ms.">Ms.</option>
										<option value="Madam.">Madam.</option>
										<option value="Mr.">Mr.</option>
										<option value="Mrs.">Mrs.</option>
										<option value="Dr.">Dr.</option>
										<option value="Dato.">Dato.</option>
										<option value="Datin.">Datin.</option>
										<option value="Datin.">Datin.</option>
										<option value="Sri.">Sri.</option>
										<option value="Tan Sri.">Tan Sri.</option>
									</select> <label>Owner Salutation1</label>
								</div>


								<div class="input-field col s12 m4 l4">
									<!-- style="display: none;" -->
									<label for="OwnerName1">Owner Name1</label> <input type="text"
										id="ownerName1" placeholder="OwnerName1" name="ownerName1"
										value="${merchant.ownerName1}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="ContactNo1">Owner ContactNo1</label> <input
										type="text" id="ownerContactNo1" placeholder="OwnerContactNo1"
										name="ownerContactNo1" value="${merchant.ownerContactNo1}" />
								</div>
							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="NRIC1">NRIC/ Passport1</label> <input type="text"
										id="passportNo1" placeholder="PassportNo1" name="passportNo1"
										value="${merchant.passportNo1}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="ResidentialAddress1">Residential Address1</label> <input
										type="text" id="residentialAddress1"
										placeholder="ResidentialAddress1"
										value="${merchant.residentialAddress1}"
										name="residentialAddress1" />
								</div>
							</div>


							<div id="owner2">
								<div class="row">


									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">


										<input type="hidden" value="${merchant.ownerSalutation2}"
											id="salutation2" /> <select name="ownerSalutation2"
											id="ownerSalutation2">


											<option value="">${merchant.ownerSalutation2}</option>
											<option value="">Salutation</option>
											<option value="Ms.">Ms.</option>
											<option value="Madam.">Madam.</option>
											<option value="Mr.">Mr.</option>
											<option value="Mrs.">Mrs.</option>
											<option value="Dr.">Dr.</option>
											<option value="Dato.">Dato.</option>
											<option value="Datin.">Datin.</option>
											<option value="Datin.">Datin.</option>
											<option value="Sri.">Sri.</option>
											<option value="Tan Sri.">Tan Sri.</option>

										</select> <label for="OwnerSalutation2">Owner Salutation2</label>
									</div>

									<div class="input-field col s12 m4 l4">
										<label for="OwnerName2">Owner Name2</label> <input type="text"
											id="ownerName2" placeholder="OwnerName2" name="ownerName2"
											value="${merchant.ownerName2}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ContactNo2">Owner ContactNo2</label> <input
											type="text" id="ownerContactNo2"
											placeholder="OwnerContactNo2" name="ownerContactNo2"
											value="${merchant.ownerContactNo2}" />
									</div>
								</div>
							</div>


							<div id="owner22">
								<div class="row">
									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">
										<label for="NRIC2">NRIC/ Passport2</label> <input type="text"
											id="passportNo2" placeholder="PassportNo2" name="passportNo2"
											value="${merchant.passportNo2}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ResidentialAddress2">Residential Address2</label>
										<input type="text" id="residentialAddress2"
											placeholder="ResidentialAddress2"
											value="${merchant.residentialAddress2}"
											name="residentialAddress2" />
									</div>
								</div>
							</div>


							<div id="owner3">

								<div class="row">

									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">

										<input type="hidden" value="${merchant.ownerSalutation3}"
											id="salutation3" /> <select name="ownerSalutation3"
											id="ownerSalutation3">

											<option value="">${merchant.ownerSalutation3}</option>
											<option value="">Salutation</option>
											<option value="Ms.">Ms.</option>
											<option value="Madam.">Madam.</option>
											<option value="Mr.">Mr.</option>
											<option value="Mrs.">Mrs.</option>
											<option value="Dr.">Dr.</option>
											<option value="Dato.">Dato.</option>
											<option value="Datin.">Datin.</option>
											<option value="Datin.">Datin.</option>
											<option value="Sri.">Sri.</option>
											<option value="Tan Sri.">Tan Sri.</option>

										</select> <label for="OwnerSalutation3">Owner Salutation3</label>
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="OwnerName3">Owner Name3</label> <input type="text"
											id="ownerName3" placeholder="OwnerName3" name="ownerName3"
											value="${merchant.ownerName3}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ContactNo3">Owner ContactNo3</label> <input
											type="text" id="ownerContactNo3"
											placeholder="OwnerContactNo3" name="ownerContactNo3"
											value="${merchant.ownerContactNo3}" />
									</div>
								</div>
							</div>


							<div id="owner33">

								<div class="row">
									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">
										<label for="NRIC3">NRIC/ Passport3</label> <input type="text"
											id="passportNo3" placeholder="PassportNo3" name="passportNo3"
											value="${merchant.passportNo3}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ResidentialAddress3">Residential Address3</label>
										<input type="text" id="residentialAddress3"
											placeholder="ResidentialAddress3"
											value="${merchant.residentialAddress3}"
											name="residentialAddress3" />
									</div>
								</div>
							</div>


							<!-- --owner details 4 -->
							<div id="owner4">
								<div class="row">

									<div class="input-field col s12 m4 l4">

										<input type="hidden" value="${merchant.ownerSalutation4}"
											id="salutation4" /> <select name="ownerSalutation4"
											id="ownerSalutation4">
											<!--  <option  value="">Salutation</option> -->
											<option value="">${merchant.ownerSalutation4}</option>
											<option value="">Salutation</option>
											<option value="Ms.">Ms.</option>
											<option value="Madam.">Madam.</option>
											<option value="Mr.">Mr.</option>
											<option value="Mrs.">Mrs.</option>
											<option value="Dr.">Dr.</option>
											<option value="Dato.">Dato.</option>
											<option value="Datin.">Datin.</option>
											<option value="Datin.">Datin.</option>
											<option value="Sri.">Sri.</option>
											<option value="Tan Sri.">Tan Sri.</option>

										</select> <label for="OwnerSalutation4">Owner Salutation4</label>
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="OwnerName4">Owner Name4</label> <input type="text"
											id="ownerName4" placeholder="OwnerName4" name="ownerName4"
											value="${merchant.ownerName4}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ContactNo4">Owner ContactNo4</label> <input
											type="text" id="ownerContactNo4"
											placeholder="OwnerContactNo4" name="ownerContactNo4"
											value="${merchant.ownerContactNo4}" />
									</div>
								</div>
							</div>

							<div id="owner44">
								<div class="row">
									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">
										<label for="NRIC3">NRIC/ Passport4</label> <input type="text"
											id="passportNo4" placeholder="PassportNo4" name="passportNo4"
											value="${merchant.passportNo4}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ResidentialAddress4">Residential Address4</label>
										<input type="text" id="residentialAddress4"
											placeholder="ResidentialAddress4"
											value="${merchant.residentialAddress4}"
											name="residentialAddress4" />
									</div>
								</div>
							</div>


							<div id="owner5">

								<!-- --owner details 4 -->
								<div class="row">

									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">

										<input type="hidden" value="${merchant.ownerSalutation5}"
											id="salutation5" /> <select name="ownerSalutation5"
											id="ownerSalutation5">
											<!--  <option  value="Ms">Salutation</option> -->
											<option value="">${merchant.ownerSalutation5}</option>
											<option value="">Salutation</option>
											<option value="Ms.">Ms.</option>
											<option value="Madam.">Madam.</option>
											<option value="Mr.">Mr.</option>
											<option value="Mrs.">Mrs.</option>
											<option value="Dr.">Dr.</option>
											<option value="Dato.">Dato.</option>
											<option value="Datin.">Datin.</option>
											<option value="Datin.">Datin.</option>
											<option value="Sri.">Sri.</option>
											<option value="Tan Sri.">Tan Sri.</option>

										</select> <label for="OwnerSalutation5">Owner Salutation5</label>
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="OwnerName5">Owner Name5</label> <input type="text"
											id="ownerName5" placeholder="OwnerName5" name="ownerName5"
											value="${merchant.ownerName5}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ContactNo5">Owner ContactNo5</label> <input
											type="text" id="ownerContactNo5"
											placeholder="OwnerContactNo5" name="ownerContactNo5"
											value="${merchant.ownerContactNo5}" />
									</div>
								</div>

								<!-- </div> -->
							</div>
							<div id="owner55">
								<div class="row">
									<!-- style="display: none;" id="onwer2"	 -->
									<div class="input-field col s12 m4 l4">
										<label for="NRIC5">NRIC/ Passport5</label> <input type="text"
											id="passportNo5" placeholder="PassportNo5" name="passportNo5"
											value="${merchant.passportNo5}" />
									</div>
									<div class="input-field col s12 m4 l4">
										<label for="ResidentialAddress5">Residential Address5</label>
										<input type="text" class="form-control"
											id="residentialAddress5" placeholder="ResidentialAddress5"
											value="${merchant.residentialAddress5}"
											name="residentialAddress5" />
									</div>
								</div>
							</div>

							<div class="d-flex align-items-center">
								<h4>Office Details</h4>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="WebSite">WebSite</label> <input type="text"
										id="website" placeholder="WebSite" name="website"
										value="${merchant.website}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="OfficeNo">Office No</label> <input type="text"
										id="officeNo" placeholder="Office No" name="officeNo"
										value="${merchant.officeNo}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="FaxNo">FaxNo</label> <input type="text" id="faxNo"
										placeholder="FaxNo" name="faxNo" value="${merchant.faxNo}" />
								</div>
							</div>


							<div class="d-flex align-items-center">
								<h4>Business Details & Documents</h4>
							</div>
							<div class="row">
								<!-- style="display: none;" id="onwer2"	 -->
								<div class="input-field col s12 m4 l4">

									<input type="hidden" value="${merchant.businessType}"
										id="businessType1" /> <select id="businessType"
										name="businessType" path="businessType">

										<option value="">${merchant.businessType}</option>
										<option value="">Select Business Type</option>
										<option value="Agent">Agent</option>
										<option value="Distributor/Reseller">Distributor/Reseller</option>
										<option value="Export/Import">Export/Import</option>
										<option value="Manufacturer">Manufacturer</option>
										<option value="Professional">Professional</option>
										<option value="Service">Service</option>
										<option value="Trader">Trader</option>
										<option value="Retail">Retail</option>
										<option value="Others">Others</option>
									</select> <label for="businessType">Business Type</label>
								</div>

								<div class="input-field col s12 m4 l4">


									<%--  <form:select  id="companyType"  path="companyType" name="companyType"> --%>
									<input type="hidden" value="${merchant.companyType}"
										id="companyType1" /> <select class="form-control"
										name="companyType" id="companyType" path="companyType">

										<option value="">${merchant.companyType}</option>
										<option value="">Select Category</option>
										<option value="Private Limited Company">Private
											Limited Company</option>
										<option value="Sole-Proprietor">Sole-Proprietor</option>
										<option value="Partnership">Partnership</option>
										<option value="Limited Liability Partnership">Limited
											Liability Partnership</option>
										<option value="Public Limited Company">Public Limited
											Company</option>
										<option value="Petty Traders">Petty Traders</option>
										<option value="Professional">Professional</option>
										<option value="Association">Association</option>
										<option value="Distributor">Distributor</option>
										<option value="Joint Management Body">Joint
											Management Body</option>
									</select> <label for="businessType">Company Type</label>
								</div>
								<div class="input-field col s12 m4 l4">


									<input type="hidden" id="natureOfBusiness1"
										value="${merchant.natureOfBusiness}" /> <select
										id="natureOfBusiness" name="natureOfBusiness"
										path="natureOfBusiness">
										<option value="">${merchant.natureOfBusiness}</option>
										<option value="">Select Category</option>
										<option value="AgricultureService & Products">AgricultureService
											& Products</option>
										<option value="Apparels,Fashion & Accessories">Apparels,Fashion
											& Accessories</option>
										<option value="Art, Gift & Craft">Art, Gift & Craft</option>
										<option value="Automobiles Component & Parts">Automobiles
											Component & Parts</option>
										<option value="Baby product & Child Care">Baby
											product & Child Care</option>
										<option value="Beauty & Personal Care">Beauty &
											Personal Care</option>
										<option value="Books & Magazines">Books & Magazines</option>
										<option value="Computer Hardware, Software & Accessories">Computer
											Hardware, Software & Accessories</option>
										<option value="Construction & Construction Material">Construction
											& Construction Material</option>
										<option value="DIY tools, equipment & Accessories">DIY
											tools, equipment & Accessories</option>
										<option value="Educational Training">Educational
											Training</option>
										<option value="Electronic component & supplies">Electronic
											component & supplies</option>
										<option value="Energy & Solar Energy">Energy & Solar
											Energy</option>
										<option value="Entertainment Services">Entertainment
											Services</option>
										<option
											value="Entertainment Equipment, Products & Accessories">Entertainment
											Equipment, Products & Accessories</option>
										<option value="Environment & Green Technology & Products">Environment
											& Green Technology & Products</option>
										<option value="Food & Beverages">Food & Beverages</option>
										<option value="Furniture & Furnishings">Furniture &
											Furnishings</option>
										<option value="Hardware">Hardware</option>
										<option
											value="Health, HealthCare, Medical, Products & Supplies">Health,
											HealthCare, Medical, Products & Supplies</option>
										<option value="Home Appliances">Home Appliances</option>
										<option
											value="Home & Garden improvement, maintenance & products">Home
											& Garden improvement, maintenance & products</option>
										<option value="Hotel & Lodging">Hotel & Lodging</option>
										<option value="ICT solutions & services">ICT
											solutions & services</option>
										<option value="Lights & lightings">Lights & lightings</option>
										<option value="Machinery, machinery products & supplies">Machinery,
											machinery products & supplies</option>
										<option value="Maritime supplies & services">Maritime
											supplies & services</option>
										<option value="Metal & metalluformrgy">Metal &
											metalluformrgy</option>
										<option value="Office automation">Office automation</option>
										<option value="Premium souvenirs products & supplies">Premium
											souvenirs products & supplies</option>
										<option value="Packing materials & solutions">Packing
											materials & solutions</option>
										<option value="Printing & publication">Printing &
											publication</option>
										<option value="Real Estate / Property">Real Estate /
											Property</option>
										<option value="Safety, Security & Protection">Safety,
											Security & Protection</option>
										<option value="sport apparel, equipment, event & services">sport
											apparel, equipment, event & services</option>
										<option value="Stationery & office supplies">Stationery
											& office supplies</option>
										<option value="Storage">Storage</option>
										<option value="Telecommunication product & service">Telecommunication
											product & service</option>
										<option value="Textile & Leather product">Textile &
											Leather product</option>
										<option value="Timepiece, jewelry, eyewear">Timepiece,
											jewelry, eyewear</option>
										<option value="Transportation">Transportation</option>
										<option value="Travel & traveling products & services">Travel
											& traveling products & services</option>
										<option value="Toys & Hobbies">Toys & Hobbies</option>
										<option value="Law firm">Law firm</option>
										<option value="Advertising, PR, Event, Promotion & Services">Advertising,
											PR, Event, Promotion & Services</option>
										<option value="Grocery & Mart">Grocery & Mart</option>
										<option value="Pets, Grooming & Services">Pets,
											Grooming & Services</option>
										<option value="Direct Selling">Direct Selling</option>
										<option value="Insurance">Insurance</option>
										<option value="Employment, HR, Workforce & Services">Employment,
											HR, Workforce & Services</option>
										<option value="Photography Equipment & Services">Photography
											Equipment & Services</option>
										<option value="Non-Profit Organization/Charity">Non-Profit
											Organization/Charity</option>
										<option value="Business Consultancy">Business
											Consultancy</option>
										<option
											value="Air Conditioning and other appliance repair and services">Air
											Conditioning and other appliance repair and services</option>
										<option value="CLEANING OF PEST CONTROL SERVICES">CLEANING
											OF PEST CONTROL SERVICES</option>
										<option value="Food Equipment">Food Equipment</option>

									</select> <label for="NatureOfBusiness">NatureOf Business</label>
								</div>
							</div>

							<div class="d-flex align-items-center">
								<h4>Bank Details & Other Details</h4>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="BankName">Bank Name</label> <input type="text"
										id="txtBankName" placeholder="Bank Name" name="bankName"
										value="${merchant.bankName}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="AccountNo">Account No</label> <input type="text"
										id="txtAccountNo" placeholder="Account No" name="bankAccNo"
										value="${merchant.bankAccNo}" />
								</div>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">


									<input type="text" id="txtdocuments" placeholder="Documents"
										name="documents" value="${merchant.documents}" /> <label>Documents</label>
								</div>
								<div class="input-field col s12 m4 l4">

									<c:choose>

										<c:when test="${merchant.formFName == null}">
											<input type="file" name="formFile" style="width: 150px;" />
											<br>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" id="formFName"
												name="formFName" value="${merchant.formFName}"
												readonly="readonly" />
											<!-- readonly="readonly" -->
										</c:otherwise>

									</c:choose>

									<c:choose>

										<c:when test="${merchant.docFName == null}">
											<input type="file" name="docFile" style="width: 150px;" />
											<br>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" id="docFName"
												name="docFName" value="${merchant.docFName}"
												readonly="readonly" />
										</c:otherwise>

									</c:choose>

									<c:choose>

										<c:when test="${merchant.payFName == null}">
											<input type="file" name="payFile" style="width: 150px;" />
										</c:when>
										<c:otherwise>
											<input type="text" id="payFName" name="payFName"
												value="${merchant.payFName}" readonly="readonly" />
										</c:otherwise>

									</c:choose>
									<!-- <input type="file" value="" name="formFile" id="formFile"><br>
                        <input type="file" value="" name="docFile" id="docFile"><br>
                        <input type="file" value="" name="payFile" id="payFile"> -->
								</div>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="TradingName">Trading Name</label> <input
										type="text" id="tradingName" placeholder="Trading Name"
										name="tradingName" value="${merchant.tradingName}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="ReferralId">Referral Id</label> <input type="text"
										id="referralId" placeholder="Referral Id" name="referralId"
										value="${merchant.referralId}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="NoOfReaders">No. Of Readers</label> <input
										type="text" id="noOfReaders" placeholder="No. Of Readers"
										value="${merchant.noOfReaders}" name="noOfReaders" />
								</div>
							</div>


							<div class="row">
								<%-- <div class="input-field col s12 m4 l4">


                <input type="hidden" id="signedPackage1" value="${merchant.signedPackage}"/>


                            <input type="text" id="signedPackage"
                                placeholder="Signed Package" name="signedPackage"
                                value="${merchant.signedPackage}" /> <label>Signed
                                Package</label>
                        </div> --%>
								<div class="input-field col s12 m4 l4">
									<label for="WaiverMonth">Waiver Month</label> <input
										type="text" id="txtWaiverMonth" placeholder="Waiver Month"
										name="wavierMonth" value="${merchant.wavierMonth}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Remarks">Remarks</label> <input type="text"
										id="txtRemarks" placeholder="Remarks" name="statusRemarks"
										value="${merchant.statusRemarks}" />
								</div>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="YearIncorporated">Year Incorporated</label> <input
										type="text" id="txtYearIncorporated"
										placeholder="Year Incorporated" name="yearIncorporated"
										value="${merchant.yearIncorporated}" />
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="auth3DS">Bank OTP</label> <input type="text"
										id="auth3DS" value="${merchant.auth3DS}" name="auth3DS"
										readonly />

								</div>

								<div class="input-field col s12 m4 l4">
									<label for="Mdr">Mdr</label> <input type="text"
										class="form-control" id="txtMdr" value="1.70" name="mdr"
										${merchant.mdr} />
								</div>
							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									Agent Name <input type="hidden" id="AgentUserName"
										value="${merchant.agentName}" /> <select
										class="browser-default select-filter" name="agentName"
										id="agentName" path="agentName" required>

										<option value="${merchant.agentName}">${merchant.agentName}</option>
										<c:forEach items="${agentNameList}" var="agentName1">
											<option value="${agentName1}">${agentName1}</option>
										</c:forEach>
									</select>

								</div>

								<div class="input-field col s12 m4 l4">

									<input type="hidden" id="accType1" value="${merchant.accType}" />
									<select id="accType" name="accType">

										<option value="">Select Account Type</option>
										<option value="Personal">Personal</option>
										<option value="Business">Business</option>
									</select> <label>Account Type</label>

								</div>


								<script>

                                function checkEmail() {
                                    var email = document.getElementById('email').value.trim();
                                    var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                                    if (!regex.test(email)) {
                                        document.getElementById('emailError').textContent = 'Please enter a valid email address.';
                                    } else {
                                        document.getElementById('emailError').textContent = '';
                                    }
                                }

                                $(document).ready(function () {
                                    $(".select-filter").select2();
                                });


                     /*            document.getElementById('maxPayoutLimit').addEventListener('blur', function() {
                                    if (this.value === '' || parseFloat(this.value) < 0) {
                                        this.value = '0.0';
                                    }
                                }); */

                                document.getElementById('maxPayoutLimit').addEventListener('input', function() {
                                    // Restrict input to two decimal places
                                    let value = this.value;
                                    if (value.includes('.')) {
                                        let parts = value.split('.');
                                        if (parts[1].length > 2) {
                                            this.value = parts[0] + '.' + parts[1].substring(0, 2);
                                        }
                                    }
                                });

                                document.getElementById('maxPayoutLimit').addEventListener('blur', function() {
                                    if (this.value === '' || parseFloat(this.value) < 0) {
                                        this.value = '0.0';
                                    }
                                });

                            </script>

								<style>
.select2-dropdown {
	border: 2px solid #2e5baa;
}

.select2-container--default .select2-selection--single {
	border: none;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select2-results__options li {
	list-style-type: none;
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

.select2-results ul {
	max-height: 250px;
	curser: pointer;
}

.select2-results ul li:hover {
	background-color: #005baa !important;
	color: #fff !important;
}

.select-search-hidden .select2-container {
	display: none !important;
}
</style>

								<div class="input-field col s12 m4 l4">
									Pre-Auth: <input type="hidden" id="testAuth"
										value="${merchant.preAuth}" />
									<p>
										<c:choose>
											<c:when
												test="${merchant.preAuth=='Yes' || merchant.preAuth=='yes'}">
												<label> <input type="radio" name="preAuth"
													id="preAuth" value="Yes" path="preAuth" checked="checked" /><span>Yes</span>
												</label>
												<label><input type="radio" name="preAuth"
													id="preAuth" value="No" path="preAuth" /> <span>No</span>
												</label>
											</c:when>
											<c:otherwise>
												<label> <input type="radio" name="preAuth"
													id="preAuth" value="Yes" path="preAuth" /> <span>Yes</span>
												</label>
												<label> <input type="radio" name="preAuth"
													id="preAuth" value="No" path="preAuth" checked="checked" />
													<span>No</span>
												</label>
											</c:otherwise>
										</c:choose>
									</p>

								</div>
							</div>

							<div class="row">


								<div class="input-field col s12 m4 l4">
									Auto Settled: <input type="hidden" id="testSettle"
										value="${merchant.autoSettled}" />
									<p>
										<c:choose>

											<c:when
												test="${merchant.autoSettled=='Yes' || merchant.autoSettled=='yes'}">
												<label> <input type="radio" name="autoSettled"
													id="autoSettled" value="Yes" path="autoSettled"
													checked="checked" /> <span>Yes</span>
												</label>
												<label><input type="radio" name="autoSettled"
													id="autoSettled" value="No" path="autoSettled" /> <span>No</span>
												</label>
											</c:when>
											<c:otherwise>
												<label> <input type="radio" name="autoSettled"
													id="autoSettled" value="Yes" path="autoSettled" /> <span>Yes</span>
												</label>
												<label> <input type="radio" name="autoSettled"
													id="autoSettled" value="No" path="autoSettled"
													checked="checked" /> <span>No</span>
												</label>
											</c:otherwise>

										</c:choose>
									</p>
								</div>


								<!-- 		rk add -->

								<!-- Manual Settlement -->

								<div class="input-field col s12 m4 l4">
									Payout Settlement: <input type="hidden" id="testSettle"
										value="${manualSettlement}" />
									<p>
										<c:choose>
											<c:when
												test="${manualSettlement=='Yes' || manualSettlement=='yes' || manualSettlement=='YES'}">
												<label> <input type="radio" name="manualSettlement"
													id="manualSettlement" value="Yes" checked="checked" /> <span>Yes</span>
												</label>
												<label> <input type="radio" name="manualSettlement"
													id="manualSettlement" value="No" /> <span>No</span>
												</label>
											</c:when>
											<c:otherwise>
												<label> <input type="radio" name="manualSettlement"
													id="manualSettlement" value="Yes" /> <span>Yes</span>
												</label>
												<label> <input type="radio" name="manualSettlement"
													id="manualSettlement" value="No" checked="checked" /> <span>No</span>
												</label>
											</c:otherwise>
										</c:choose>
									</p>
								</div>


								<div class="input-field col s12 m4 l4">
									Payout Async Handler: <input type="hidden" id="testSettle"
										value="${payoutAsyncHandler}" />
									<p>
										<c:choose>
											<c:when test="${merchant.isAsyncPayoutEnabled =='YES'}">
												<label> <input type="radio"
													name="payoutAsyncHandler" id="payoutAsyncHandlerYes"
													value="Yes" checked="checked" /> <span>Yes</span>
												</label>
												<label> <input type="radio"
													name="payoutAsyncHandler" id="payoutAsyncHandlerNo"
													value="No" /> <span>No</span>
												</label>
											</c:when>
											<c:otherwise>
												<label> <input type="radio"
													name="payoutAsyncHandler" id="payoutAsyncHandlerYes"
													value="Yes" /> <span>Yes</span>
												</label>
												<label> <input type="radio"
													name="payoutAsyncHandler" id="payoutAsyncHandlerNo"
													value="No" checked="checked" /> <span>No</span>
												</label>
											</c:otherwise>
										</c:choose>
									</p>
								</div>
                            </div>

                            <div class="row">
                                <div class="input-field col s12 m4 l4">
                                    Account Enquiry: <input type="hidden" id="testSettle"
                                        value="${accountEnquiry}" />
                                    <p>
                                        <c:choose>
                                            <c:when test="${merchant.isAccountEnquiryEnabled =='YES'}">
                                                <label> <input type="radio"
                                                    name="accountEnquiry" id="accountEnquiryYes"
                                                    value="Yes" checked="checked" /> <span>Yes</span>
                                                </label>
                                                <label> <input type="radio"
                                                    name="accountEnquiry" id="accountEnquiryNo"
                                                    value="No" /> <span>No</span>
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <label> <input type="radio"
                                                    name="accountEnquiry" id="accountEnquiryYess"
                                                    value="Yes" /> <span>Yes</span>
                                                </label>
                                                <label> <input type="radio"
                                                    name="accountEnquiry" id="accountEnquiryNoo"
                                                    value="No" checked="checked" /> <span>No</span>
                                                </label>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>

                                <div class="input-field col s12 m4 l4">
                                    Quick Payout: <input type="hidden" id="testSettle"
                                        value="${quickPayout}" />
                                    <p>
                                        <c:choose>
                                            <c:when test="${merchant.isQuickPayoutEnabled =='YES'}">
                                                <label> <input type="radio"
                                                    name="quickPayout" id="quickPayoutYes"
                                                    value="Yes" checked="checked" /> <span>Yes</span>
                                                </label>
                                                <label> <input type="radio"
                                                    name="quickPayout" id="quickPayoutNo"
                                                    value="No" /> <span>No</span>
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <label> <input type="radio"
                                                    name="quickPayout" id="quickPayoutYess"
                                                    value="Yes" /> <span>Yes</span>
                                                </label>
                                                <label> <input type="radio"
                                                    name="quickPayout" id="quickPayoutNoo"
                                                    value="No" checked="checked" /> <span>No</span>
                                                </label>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>


								<div class="input-field col s12 m4 l4">
									<label for="Name">Payout Notification Url</label> <input
										type="text" class="form-control" id="payoutNotificationUrl"
										placeholder="Payout Notification Url"
										name="payoutNotificationUrl" value="${merchant.payoutIpnUrl}" />
								</div>
                            </div>


                            <div class="row">


								<div class="input-field col s12 m4 l4">
									<label for="reasonForAsyncPayout">Reason for Enabling
										Async Payout</label> <input type="text" id="reasonForAsyncPayout"
										placeholder="Reason for Enabling Async Payout"
										name="reasonForAsyncPayout"
										value="${merchant.asyncEnableReason}" />
								</div>

								<div class="input-field col s12 m4 l4">
									<input type="hidden" id="merchantStatusHidden"
										value="${merchant.status}" /> <select
										id="merchantStatusSelect" name="merchantStatus"
										onchange="updateHiddenInput()">

										<!-- Remove the placeholder option -->
										<option value="Active"
											${merchant.status.toUpperCase() == 'ACTIVE' ?
                                        'selected' : ''}>Active
										</option>
										<%-- 	<option value="Terminated" ${merchant.status.toUpperCase() == 'TERMINATED' ?
                                 'selected' : ''}>Terminated
                                </option> --%>
										<option value="Suspended"
											${merchant.status.toUpperCase() == 'SUSPENDED' ? 'selected' : ''}>Suspended
										</option>
										<option value="Active"
											${merchant.status.toUpperCase() == 'ACTIVE' ? 'selected' : ''}>Active
										</option>
									</select> <label for="merchantStatusSelect">Merchant Status</label>

								</div>

								<div class="input-field col s12 m4 l4">
								    <label for="maxPayoutLimit">Max Payout Limit Amount</label>
								    <input type="number" id="maxPayoutLimit"
								           placeholder="Enter amount, e.g., 100.00"
								           name="maxPayoutLimit"
								           value="${merchant.maxPayoutTxnLimit}"
								           step="0.01"
								           min="0" />
								</div>

								<%--
								<div class="input-field col s12 m4 l4">
								    <label for="maxPayoutLimit">Max Payout Limit Amount</label>
								    <input type="number" id="maxPayoutLimit"
								           placeholder="Enter amount, e.g., 100.00"
								           name="maxPayoutLimit"
								           value="${merchant.maxPayoutTxnLimit}"
								           step="0.01" />
								</div> --%>
                               <%--
                               <div class="input-field col s12 m4 l4">

                                    <select id="quickPayoutUrl" name="quickPayoutUrl">

                                        <option value="${merchant.quickPayoutUrl}">${merchant.quickPayoutUrl}</option>
                                        <option value="https://example.com/url/111">https://example.com/url/111</option>
                                        <option value="https://example.com/url/222">https://example.com/url/222</option>
                                        <option value="https://example.com/url/333">https://example.com/url/333</option>
                                    </select> <label>Quick Payout URL</label>

                               </div>
                               --%>
                            </div>


							<div class="row">

								<c:choose>
									<c:when test="${adminusername.toLowerCase()=='ethan'}">

										<div class="input-field col s12 m4 l4">
											Foreign Card Access: <input type="hidden" id="testForeign"
												value="${merchant.foreignCard}">
											<p>
												<c:choose>
													<c:when
														test="${merchant.foreignCard=='Yes' || merchant.foreignCard=='yes'}">
														<label> <input type="radio" name="foreignCard"
															id="foreignCard" value="Yes" path="foreignCard"
															checked="checked"><span>Yes</span>
														</label>
														<label><input type="radio" name="foreignCard"
															id="foreignCard" value="No" path="foreignCard"><span>No</span>
														</label>
													</c:when>
													<c:otherwise>
														<label> <input type="radio" name="foreignCard"
															id="foreignCard" value="Yes" path="foreignCard"><span>Yes</span>
														</label>
														<label> <input type="radio" name="foreignCard"
															id="foreignCard" value="No" path="foreignCard"
															checked="checked"><span>No</span>
														</label>
													</c:otherwise>
												</c:choose>
											</p>

										</div>

									</c:when>
								</c:choose>

								<!-- 	rk add -->


								<c:choose>
									<c:when test="${adminusername.toLowerCase()=='ethan'}">
										<div class="input-field col s12 m4 l4">
											Ezysettle Access: <input type="hidden" id="testEzysettle1"
												value="${merchant.ezysettle}">
											<p>
												<c:choose>
													<c:when
														test="${merchant.ezysettle=='Yes' || merchant.ezysettle=='yes'}">
														<label> <input type="radio" name="ezysettle"
															id="ezysettle" value="Yes" path="ezysettle"
															checked="checked"><span>Yes</span>
														</label>
														<label><input type="radio" name="ezysettle"
															id="ezysettle" value="No" path="ezysettle"><span>No</span>
														</label>
													</c:when>
													<c:otherwise>
														<label> <input type="radio" name="ezysettle"
															id="ezysettle" value="Yes" path="ezysettle"><span>Yes</span>
														</label>
														<label> <input type="radio" name="ezysettle"
															id="ezysettle" value="No" path="ezysettle"
															checked="checked"><span>No</span>
														</label>
													</c:otherwise>
												</c:choose>
											</p>
										</div>
									</c:when>
								</c:choose>


								<%-- <input type="hidden" id="auth3DS"  name ="auth3DS" value="${merchant.auth3DS}"/> --%>

							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									<div class="button-class" style="float: left;">
										<button type="submit" class="btn btn-primary blue-btn"
											onclick="return loadSelectData()">Submit</button>
										<button type="button" class="btn btn-primary blue-btn"
											onclick="return loadCancelData()">Cancel</button>
									</div>
								</div>
							</div>


							<style>
.select-wrapper .caret {
	fill: #005baa;
}

.blue-btn {
	background-color: #005baa;
	color: #fff;
	border-radius: 20px;
}

.button-class {
	float: right;
}

.float-right {
	float: right;
}
</style>


						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>


<script type="text/javascript">

function updateHiddenInputUrl() {
        const selectElement = document.getElementById("urlSelect");
        const hiddenInput = document.getElementById("urlHidden");
        hiddenInput.value = selectElement.value;
    }


    function loadCancelData() {
        /* 	alert("fcancel data"); */
        document.location.href = "${pageContext.request.contextPath}/admmerchant/list/1";
        form.submit;
    }


    function addrow() {
//alert(field.value);
        disableRow();
        var i = document.getElementById("ownerCount").value;

        var a = 0;
        /* alert(i); */


        if (i < 0 || i > 6) {
            alert("Owner count should be 1 to 5");
        }


        for (a = 2; a <= i; a++) {
            //alert(a);

            document.getElementById("owner" + a).style.display = '';
            document.getElementById("owner" + a + a).style.display = '';
        }

    }

    function disableRow() {
        document.getElementById("owner2").style.display = 'none';
        document.getElementById("owner22").style.display = 'none';
        document.getElementById("owner3").style.display = 'none';
        document.getElementById("owner33").style.display = 'none';
        document.getElementById("owner4").style.display = 'none';
        document.getElementById("owner44").style.display = 'none';
        document.getElementById("owner5").style.display = 'none';
        document.getElementById("owner55").style.display = 'none';
    }

    // Merchant Status
    var dropdown = document.getElementById("merchantStatusSelect");

    for (var i = 0; i < dropdown.options.length; i++) {
        if (dropdown.options[i].value === initialMerchantStatus) {
            dropdown.selectedIndex = i;
            break;
        }
    }

    // Function to update the hidden input when the dropdown value changes
    function updateHiddenInput() {
        var selectedValue = dropdown.options[dropdown.selectedIndex].value;
        document.getElementById("merchantStatusHidden").value = selectedValue;
    }


</script>


<script>
    var adminusername = "${adminusername}";
    console.log(adminusername);
</script>


</html>
