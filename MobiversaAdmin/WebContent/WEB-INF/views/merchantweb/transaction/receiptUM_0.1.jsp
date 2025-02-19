<%@page
	import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<script async defer
  src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBvMCKdhSLSy_4FKtIZFZbHr2spqdxzNiQ&callback=initMap">
</script>

 <link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
<title>Mobiversa Receipt</title>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

<!-- <script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script> -->
<link href="/resourcesNew/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet"
	type="text/css" />
<style>
@media only screen and (max-width: 690px) { "
	.container {
		width: 100% !important;
	}
	"
}

"
.flexibleImage { "
	max-width: 100px !important;
	"
	width
	100%
	!important;
	"
}
"
</style>


<script lang="JavaScript">
	function displaySign() {
		var sign = document.getElementById("sign").value;
		//alert("Data :"+sign);
		document.getElementById("nsign").style.display = 'none';
		document.getElementById("tsign").style.display = 'none';
		//alert("Data :"+sign);
		if (sign == null || sign == '') {
			//alert("if :"+sign);
			document.getElementById("nsign").style.display = '';
		} else {
			//alert("else :"+sign);
			document.getElementById("tsign").style.display = '';

		}
	}
</script>
</head>
<body style="font-family: Helvetica;color:#666;"><center>
<table class="container" width= "380" border="0" style="border-radius:10px;background-color:#005baa;">

<tr><td>
<table style="width:100%;">
<tr>

<c:set var = "txnType" value = "${dto.txnType }"/>
<c:if test = "${fn:contains(txnType, 'VOID') || fn:contains(txnType, 'CANCEL')}">
<td style="color:#FF0000;padding-left:10px;font-size:18px;">${dto.txnType}</td>
</c:if>

<c:if test="${dto.txnType =='MOTO SALE' || dto.txnType =='SALE' || fn:contains(txnType, 'SALE')
|| dto.txnType =='EZYPASS SALE' || dto.txnType =='PREAUTH SALE'
|| dto.txnType =='PRE-AUTHORIZATION'
|| dto.txnType =='EZYAUTH SALE' || dto.txnType =='EZYAUTH'}">
<td style="color:#42cc15;padding-left:10px;font-size:18px;">${dto.txnType}</td>
</c:if>


<td align="right">
<img style="height: 45px !important"; src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF8AAAAtCAYAAADIprc/AAAAAXNSR0IArs4c6QAABAxJREFUeAHtWb1uE0EQthG0YAoKKq6DVPEbcJFoqGKegEtDTUkX8wbJE9h5AkiFqHx+glyUBtHkQo9wKhASMt9nbtHdePZ8P4niJDPS6Ha++WZ297u79SnpdMxMAVPAFDAFTAFTwBQwBUwBU8AUMAVMgZugQPcmbKLqHubzeQzuPM/vdrtb+bjuGD0foeYl/CH8M/p9qdvjVvAh1JK12TiavYD/FE3ftel5Y2uFSIuwzWbR4ETp+RvY/Sp971QhGcerwIaSuQfsmYIvQSb+kiS1gERh/wH2VcFvN6QcEYUf37rqoN8G/Fuu7y+MB1X73LavnSWx8XXSSgOIza+czUzwFO1SE19RgE+ohNuKL/vVie9WJWPdAbhPBP/M3Wnko1x+hvEBcrwWDDy+lu5JYe4QvKRAKglQ30N6Gx7kaCnGU/ThtZFl+3uO4iBrwLVzbWkWL11Q8xjgU5H4jpoTgbULMdEQLo1YH34qE4h/wCM3K8Yh/BSu2QQgRfUa8gF8BC+zCZKhr4mnsAd85MkRnsD7Wk/gb+DSPmncVhhm8Ik/k7OLOEI8EJgWHvkWmNXzZla1Xa2Xp/jIg0s4kj1BuFLxT+UKlZiiVRVuqGyQb1YT08Rq0idfU3gDkGglftvv/ECKpcQ8TkqPlFzN69y4g82x7kMeqzEeob4gVo1aH7XpWtR+bcVn03P4+8ynBErsEDly9+Gsk8ZzPciBEcb52KWOMXgF38rcN+8Q+ap2AKLrue8p4voGntzlwZh0CJfG8z7Iz4o4lqQs3hO80MMLHQ/5ROEkLp+/gvdR4RLqOZ4nTzh0HHcF5jvuxjnOlR47ifIpNnSLE9eC+KiLkT8THIb/xcJ4U8kPFYxQ5MH7HtzB+9laXLy4Aksw0N6AoEBsEbQ9duKqcys3iaWpUr8QC09eoOQIxRqO/jPg51puBUaRfRYrCe2BUGirobbir56hIcNzs9gtKGn5oCTnS/V8CeBarskNVqdYW/HV1f4DQy2nndsZj29EmW2XJCMllypYI2jdxT9UdrUrhc6OqJHCPcMbVHassIQ//CN44SlHvIcc/9wgbVU/yffGlf+24+1wuQkKIJ9MijSBODGu9D48hBOXNpaAJ46A8yYkWZ49g2wsL2MJNI3XWnw8tfxsnWJz2hMYAqf7jF9Se76kggfA6GV2UOFNKqsv5Nb92OFiB/DjwqpXB/xRHECo2WpqZQbX8LYyuwJx7cXPBAyxF+3817ZIkfo1ntAdrYnA2DO84JvZWXvxKQI3DecbsAXnTTiHS5sC2AGPwqcy6YvBHSPn+koaRXc9L/ItWszTlbNdlxi/BQHWSq/1rzvwSw19QxJwU2JezUwBU8AUMAVMAVPAFDAFTAFTwBQwBa6JAn8BgcK/gljfEOkAAAAASUVORK5CYII=">

</td>


</tr>
</table>
</td>

</tr>

<tr><td>

<table style="width:100%;">
<tr>
<td style="color:#FFF;padding-left:10px;font-size:18px;font-weight:bold;">RM ${dto.total}</td>
<td style="color:#FFF;text-align:right;font-size:11px;">${dto.date} , ${dto.time} </td>
</tr>
</table>

</td></tr>

<tr><td>

<table align="center" width=" 380" border="0" style="border-bottom-left-radius: 15px;border-bottom-right-radius: 15px;background:#EDFBFF;border:2px solid #005baa;border-style: solid;padding:10px;">

<tr>
<td style="padding-left: 5px; font-size: 12px; font-family: Helvetica;">
<b style="color:#3e4152;">Hi ${dto.cardHolderName}</b><BR/>

</td>
</tr>

<tr>
<td style="padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#909090;">

You authorised a payment of RM ${dto.total} to ${dto.merchantName}
</td>
</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 13px; font-family: Helvetica;color:#9b9b9b;">

PAID TO
</td>
</tr>
<tr>
<td style="padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#d9d9d9;border-top:1px;">

</td>
</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px;font-weight: bold; font-family: Helvetica;color:#484b5c;">

${dto.merchantName}
</td>
</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px;font-weight: bold; font-family: Helvetica;color:#9b9b9b;">

${dto.merchantAddr1} - ${dto.merchantPostCode}
</td>
</tr>




<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px;font-weight: bold; font-family: Helvetica;color:#9b9b9b;">

${dto.merchantCity},
</td>
</tr>



 <tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px;font-weight: bold; font-family: Helvetica;color:#9b9b9b;">

${dto.merchantState},
</td>
</tr>




<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px;font-weight: bold; font-family: Helvetica;color:#9b9b9b;">

${dto.merchantContNo}
</td>
</tr>



 <tr>
<td>

<table style ="width:100%;" >
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

MID
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.mid}
</td>
</tr>
</table>
</td>

</tr>


<tr>
<td>

<table style ="width:100%;">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

TID
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.tid}
</td>
</tr>
</table>
</td>

</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>

<!-- CUSTOMER CARD DETAILS -->
<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 13px; font-family: Helvetica;color:#9b9b9b;">

CUSTOMER CARD DETAILS
</td>
</tr>

<tr>
<td>

<table style ="width:100%">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Card Holder
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px;; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.cardHolderName}
</td>
</tr>
</table>
</td>

</tr>



<tr>
<td>

<table style = "width:100%;">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Card Number
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.cardNo}

</td>
</tr>
</table>
</td>
</tr>



<tr>
<td>

<table style = "width:100%;">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Card Type
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

<c:set var = "cardType" value = "${dto.cardType }"/>
<c:if test = "${fn:contains(cardType, 'credit') || fn:contains(cardType, 'CREDIT')}">
Credit
</c:if>

<c:if test = "${fn:contains(cardType, 'debit') || fn:contains(cardType, 'DEBIT')}">
Debit
</c:if>


</td>
</tr>
</table>
</td>
</tr>


<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>


<!-- TRANSACTION DETAILS -->
<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 13px; font-family: Helvetica;color:#9b9b9b;">

TRANSACTION DETAILS
</td>
</tr>

<tr>
<td>

<table style ="width:100%" >
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

RRN
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px;font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.rrn}
</td>
</tr>
</table>
</td>

</tr>


<tr>
<td>

<table style ="width:100%;" >
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Approval Code
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.apprCode}

</td>
</tr>
</table>
</td>

</tr>




<tr>
<td>

<table style = "width:100%;">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Reference
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

${dto.refNo}

</td>
</tr>
</table>
</td>

</tr>


<tr>
<td>



 <table style ="width:100%;">
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">
3D-Secure Authenticated
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">



 :
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

<c:set var = "txnType" value = "${dto.txnType }"/>
<c:if test = "${fn:contains(txnType, 'EZYLINK') || fn:contains(txnType, 'EZYWAY') || fn:contains(txnType, 'EZYAUTH')
||fn:contains(txnType, 'ezylink')|| fn:contains(txnType, 'ezyway')|| fn:contains(txnType, 'ezyauth')}">
Yes
</c:if>
<c:if test = "${fn:contains(txnType, 'EZYWIRE') || fn:contains(txnType, 'EZYMOTO') || fn:contains(txnType, 'EZYREC')

||fn:contains(txnType, 'EZYPOD')|| fn:contains(txnType, 'EZYMOTO-VCC')|| fn:contains(txnType, 'ezywire')

||fn:contains(txnType, 'ezymoto')|| fn:contains(txnType, 'ezyrec')|| fn:contains(txnType, 'ezypod')

||fn:contains(txnType, 'ezymoto-vcc') || dto.txnType =='SALE' || dto.txnType =='sale' || fn:contains(txnType, 'EZYSPLIT')
|| fn:contains(txnType, 'ezysplit') || fn:contains(txnType, 'AUTHSALE') }">


No
</c:if>


</td>
</tr>
</table>
</td>

</tr>






<tr>
<td>



 <table style ="width:100%" >
<tr>
<td style="width:50%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

Payment Method
</td>
<td style="width:5%;padding-right: 5px;padding-left: 5px; font-size: 12px;font-family: Helvetica;color:#3e4152;">

:
</td>
<td style="width:45%;padding-right: 5px;padding-left: 5px; font-size: 12px; font-family: Helvetica;color:#3e4152;">

<c:set var = "cardType" value = "${dto.cardType }"/>
<c:if test = "${fn:contains(cardType, 'visa') || fn:contains(cardType, 'VISA')}">
<img style="height: 45px !important"; src="data:image/png;base64,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">
</c:if>

<c:if test = "${fn:contains(cardType, 'mastercard') || fn:contains(cardType, 'MASTERCARD')}">
<img style="height: 45px !important"; src="data:image/png;base64,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">
</c:if>

<c:if test = "${fn:contains(cardType, 'unionpay') || fn:contains(cardType, 'UNIONPAY')}">
<img style="height: 45px !important"; src="data:image/png;base64,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">
</c:if>



 </td>
</tr>
</table>
</td>



 </tr>




<!-- TRANSACTION DETAILS -->
<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>

<tr>
<td style="color:#8c8c8c;text-align:center;font-size:10px;">

For questions about this email contact: <span style=color:#005baa;">contact@gomobi.io</span>
</td>

</tr>

<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>

<tr>
<td style="color:#005baa;text-align:center;font-size:10px;">

Mobi Asia Sdn. Bhd. (1105429-U)"

</td>

</tr>

<tr>
<td style="color:#005baa;text-align:center;font-size:10px;">

Suite #07-01, Wisma UOA Damansara II, No. 6, Changkat Semantan, "

</td>

</tr>

<tr>
<td style="color:#005baa;text-align:center;font-size:10px;">

Damansara Heights, 50490 Kuala Lumpur."

</td>

</tr>


<tr>
<td>
<table align="center">
<tr>
<td><a href='https://www.instagram.com/mobi_asia'> <img style="width:25px;padding:5px;" src="data:image/png;base64,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"></a></td>
<td> <a href='https://facebook.com/mobiversa'> <img style="width:25px;padding:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuBAMAAACllzYEAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAwUExURUdwTDpZmDpYmDpYmDpYlzdWmDpYlzpYmDNWmDpYmDpYmDlYlzpYmDpZmDlYlztZmAlJkhIAAAAPdFJOUwCP6ziDFNnBCqdOI2PSc6JlMPUAAAEuSURBVDjLY2CAgij3////lyxlQAUcx/9DQE0DirDTfxjQQ5Yw+o8AyQjhxv/IwAEmzJ2PIv49ACq+6T8qSIUq10cT/wbREPQfHSSAxedjiH8BCXP9xwQGKMZ8E3GRh7AuAMXlkR3OD2H9YWBghQt/ZICLf2Ng4IOLKyDE/y9g4IWLT2DgmV4PZ++HiwsgsRUY3iOJ28PZn5F8hSz+j+E+VvGfDDDWB3CYwGS+o4mfh3kdTdwfyvsEF/+BGrYM+ljt/cSQj1X8L0M9DnfKYxX/yLAeq/gvhnis4j8Y2LGKA8Mcq/gCBg59LOKfkLyOLP4PKN4E45S4zEdOcLjSFa50iDPd4krnGPniKoF8hCvfMTAswp5PGViR8jVKOcD6FlYOoBUQDFHiwOAqWQHjAgDrxeljvr6QpgAAAABJRU5ErkJggg=="></a></td>
<td> <a href='https://gomobi.io'>  <img style="width:25px;padding:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABpUExURUdwTABaqgBaqgBRrgBbqQBaqgBZqgBbqgBaqgBaqgBaqQBbrABbqgBbqv///1WRxuHr9dTj8RBlr7bQ51COxSd0t8DX6/D2+jh/vAZfrESGwfn7/p/B383f74mz2Hen0medzK3K5Nvo8yFs720AAAANdFJOUwDGiw2fuDfX7FN3I0O1cVhpAAAB80lEQVRIx62W2ZKDIBBFs6iYODaKENkUzf9/5KiogElMUjP3BQpPUdDLxcNhq+QaxShNURxdk8MbXaI095RGlz0Y5Q9Cly/gUcefZ3SUv9TpAT7H+Y7ic0hnKN8VyoK939DDBfz94/ytYkef8g+03jcJlgWRgrRFS4Qkwv+w5Pjow8wUNTMKVMfqu2H+8ef0eEukawA0KQCgYBpU0xH30abLiwppawAlDYwyWAHUrePRSF83NDSsmPCSlRDylyD5ohtp0HKioZZ6GroViAbcVSxv7CE6i6uuncZmvW8aRFFWt0GV7G6TKkztAvdi6VIkOMWDJGHYihE5DpQLlyqXf1xO6nlvJ6VeZtRVggujtke+ybudQEPtZUC7TLmblvZbRR1+s5PSRd7dtJ5xvOAlnvF6Zbzd1YIXW1y5SKLH3YuXuyOvHD84+9GrgSUy9HVkIldhAheTel7ZyX2drXG/ekXwQVYzr8QE76tBPcfVpJ7KecEveHd4bi9rzFKRZlORUdh75nm9C789vMgTrYJuasJuQtvWJnrqVdsWhqrH3guNg5jh/JotTgC+E8zGcfjxzYe3RU1GnzGDz7Q83/rG1vQEw9bFCGXiuclvLVWIPUv9wLBR8CBk6T6dZn95bPZN/vTs6UuOL96Z5D+e4a8f+fe/EL+v0mICGsINWgAAAABJRU5ErkJggg=="></a></td>
<td> <a href='https://www.linkedin.com/company/4813011'> <img style="width:25px;padding:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvCAMAAABE+WOeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABIUExURUdwTAB5uQB5uQB6uQB5ugB5uQB6uQB5uQB6uAB5uwB5uQBlzwB6uf///0CbyoC93L/e7imPxFCjz7DW6ZTH4s/m8ufz+WCs0xOqFSMAAAAMdFJOUwD3V28zz+aLsRudBMZ4Ls0AAAFUSURBVEjHrZbZloMgDIYBWTW4YDt9/zedkYoNlM1z5r9qPV9ICCGBkFSGKS0kSKEVM6QhrgRgCcVrtIZv6YLFZHK0t8iGxSiURNk3PkBFdEhxBXWpe3hiwKAttAdOO3h65dXI85Nb7eqKBjKkNeR9tofmooE+own/H55/lEPi0fInv0DdwbU8bJ7foO4ApX5e131uHYKAfgkcTk5b4s7gOtvWQ3/xv/wP2HwCnthkICMKP+R/eW/8x9o0YyPRRT7g1jqUUVHkP3qiDcsyvzu32/hMJKFFfj++vQ1eV5FW1veLunjHshI/qpHlE7+u8xDzupL/HD/i8+3gB2Ju8QbXZ5sXUf23eRXdrzbPo/vb5HXcH5r82bHGzts4hv5Gu3Aa+tvEunjWOSyuo0Wz62b/b3tI8Kk27vIDj8ty58+OYKPyLqgqPQuyE1ubf3s/vJ0MtffJL7CKUzDXtiolAAAAAElFTkSuQmCC"></a></td>
<td> <a href='https://twitter.com/Mobiversa'><img style="width:25px;padding:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvCAMAAABE+WOeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABLUExURUdwTFOs9FSs71Ss7VOs71Wr7lSs71Ss7lSr7lWs7lKt8FSs7lSs7lWs7v///1+x73C58Y/J9MPi+qbU93/B8tru/PX6/rXb+Or1/XOCudUAAAANdFJOUwAYcfY05leLsM4l+p25++9ZAAABgUlEQVRIx6VWWbKEIAwERcFRFgGX+5/0OeVCoqA4r7+0bJMQmg6EnEFrLppKVo3gNSUPYLyREA1nd2xMXiFSf3yEbGUM4hOj16VMoayv9EKm0bbFmc7lPfg7+umHWj4DrIGVGfzy6CutZA6qfbeFzIPY9knmgr0KvyVgyc+9HWz/fdAwAWh9D9lmUF8Mxk8WbkLQpFE20PWodozmkDdarVfKHqltoE/2SEyhzha+mv32EsIrkLYgHah4/WjC8wqwrA51c12hGpzXGvBRR+ERBEHHOL8hQDt+cioGqCHSRsNDDPCkwfh7/Sc4FB/W76N8j+pHavPjlT5jxXXwVff2wkei6rCP6PE+/LK/FEv4PvyiH4I9sz9lmLD9Xq1HO5vo/dJ9fj1f2kWFCQ6wgP2ZxsROBYNg7R4Z1z6Ys+1uvt6FWg5FzJO5nP9uHxTADrV3zvXexAyRvrFbZLhFBr34h/8/ZyhejLv4wGPpKVBFRzDl8fHbcpqe2LnT+qf7Q8b95A8MgFqC3uKrqgAAAABJRU5ErkJggg=="></a></td>
<td> <a href='https://www.youtube.com/channel/UCsCvriJbbS0wpRAYXW-ugmg'><img style="width:25px;padding:5px;" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvBAMAAACBCY6fAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAtUExURUdwTOpALOpALOtALOpAK+pAK+tAKuxBLOpAK+pAK+9AKO9AIupALOtAK+pBLLn818oAAAAOdFJOUwDA7kCgMFk+kLEgEYBwEy5X+gAAARpJREFUOMtjYIAC1ity796JXGVABxF678BAIwBV3PAdDDw0QBZf+Q4BXi5AiLPpIUm8U0NI+L1DAQ0wcRZU8XdPcGh4984Bu4Z3715AJPIwJN4lgMS59DAltEASnJji716CJOywSLwDeV8Om4QAMFSxib97CnPso57DxmBwpgnmYCYwQxsePNxgRz5iYIhDOBzZWwEM98D0BoTEPrDABWhAIcUNHzS4wK59CPYpJO4Yoe59B5dggsQqROIBssS7hwU4JN7NxiEhjsMoYQZkCYSrmBlQXIXTHzh9jjOsmBCRjBq6HJD48DlWDgJlZ5xg8YEzBnHGOe5UgjNd4UyJONMu7tSOM3/gzlE48yDuXIszn+MuGXCXJZilDwDegnYx5JeH0wAAAABJRU5ErkJggg=="></a></td>

</tr>
</table>

</td>

</tr>

</table>
</td>

</tr>

<!--CUSTOMER CARD DETAILS -->

</table>

</td></tr>
</table>

</td>
</tr>

</table></center>



</body></html>

