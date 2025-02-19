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

<link rel="icon" type="image/gif" sizes="16x16"
	href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png" />
<title>Mobiversa Receipt</title>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=edge">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<script src="/resourcesNew/js/jQuery-2.1.4.min.js"
	type="text/javascript"></script>
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
<body style="font-family: Helvetica; color: #666;">
	<center>
		<table class="container" width="380" border="0"
			style="border-radius: 10px; background-color: #005baa;">

			<tr>
				<td>
					<table style="width: 100%;">
						<tr>

							<c:set var="txnType" value="${dto.txnType}" />
							<c:if test="${fn:contains(txnType, 'VOID')}">
								<td style="color: #FF0000; padding-left: 10px; font-size: 18px;">${dto.txnType}</td>
							</c:if>

							<c:if test="${dto.txnType == 'GRABPAY SALE'}">
								<td style="color: #42cc15; padding-left: 10px; font-size: 18px;">${dto.txnType}</td>
							</c:if>


							<td align="right"><img style="height: 45px !important"
								; src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF8AAAAtCAYAAADIprc/AAAAAXNSR0IArs4c6QAABAxJREFUeAHtWb1uE0EQthG0YAoKKq6DVPEbcJFoqGKegEtDTUkX8wbJE9h5AkiFqHx+glyUBtHkQo9wKhASMt9nbtHdePZ8P4niJDPS6Ha++WZ297u79SnpdMxMAVPAFDAFTAFTwBQwBUwBU8AUMAVMgZugQPcmbKLqHubzeQzuPM/vdrtb+bjuGD0foeYl/CH8M/p9qdvjVvAh1JK12TiavYD/FE3ftel5Y2uFSIuwzWbR4ETp+RvY/Sp971QhGcerwIaSuQfsmYIvQSb+kiS1gERh/wH2VcFvN6QcEYUf37rqoN8G/Fuu7y+MB1X73LavnSWx8XXSSgOIza+czUzwFO1SE19RgE+ohNuKL/vVie9WJWPdAbhPBP/M3Wnko1x+hvEBcrwWDDy+lu5JYe4QvKRAKglQ30N6Gx7kaCnGU/ThtZFl+3uO4iBrwLVzbWkWL11Q8xjgU5H4jpoTgbULMdEQLo1YH34qE4h/wCM3K8Yh/BSu2QQgRfUa8gF8BC+zCZKhr4mnsAd85MkRnsD7Wk/gb+DSPmncVhhm8Ik/k7OLOEI8EJgWHvkWmNXzZla1Xa2Xp/jIg0s4kj1BuFLxT+UKlZiiVRVuqGyQb1YT08Rq0idfU3gDkGglftvv/ECKpcQ8TkqPlFzN69y4g82x7kMeqzEeob4gVo1aH7XpWtR+bcVn03P4+8ynBErsEDly9+Gsk8ZzPciBEcb52KWOMXgF38rcN+8Q+ap2AKLrue8p4voGntzlwZh0CJfG8z7Iz4o4lqQs3hO80MMLHQ/5ROEkLp+/gvdR4RLqOZ4nTzh0HHcF5jvuxjnOlR47ifIpNnSLE9eC+KiLkT8THIb/xcJ4U8kPFYxQ5MH7HtzB+9laXLy4Aksw0N6AoEBsEbQ9duKqcys3iaWpUr8QC09eoOQIxRqO/jPg51puBUaRfRYrCe2BUGirobbir56hIcNzs9gtKGn5oCTnS/V8CeBarskNVqdYW/HV1f4DQy2nndsZj29EmW2XJCMllypYI2jdxT9UdrUrhc6OqJHCPcMbVHassIQ//CN44SlHvIcc/9wgbVU/yffGlf+24+1wuQkKIJ9MijSBODGu9D48hBOXNpaAJ46A8yYkWZ49g2wsL2MJNI3XWnw8tfxsnWJz2hMYAqf7jF9Se76kggfA6GV2UOFNKqsv5Nb92OFiB/DjwqpXB/xRHECo2WpqZQbX8LYyuwJx7cXPBAyxF+3817ZIkfo1ntAdrYnA2DO84JvZWXvxKQI3DecbsAXnTTiHS5sC2AGPwqcy6YvBHSPn+koaRXc9L/ItWszTlbNdlxi/BQHWSq/1rzvwSw19QxJwU2JezUwBU8AUMAVMAVPAFDAFTAFTwBQwBa6JAn8BgcK/gljfEOkAAAAASUVORK5CYII=">

							</td>


						</tr>
					</table>
				</td>

			</tr>

			<tr>
				<td>

					<table style="width: 100%;">
						<tr>
							<td
								style="color: #FFF; padding-left: 10px; font-size: 18px; font-weight: bold;">RM
								${dto.total}</td>
							<td style="color: #FFF; text-align: right; font-size: 11px;">${dto.date}
								, ${dto.time}</td>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td>

					<table align="center" width=" 380" border="0"
						style="border-bottom-left-radius: 15px; border-bottom-right-radius: 15px; background: #EDFBFF; border: 2px solid #005baa; border-style: solid; padding: 10px;">

						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica;">
								<b style="color: #3e4152;">Hi </b><BR />

							</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #909090;">

								You authorised a payment of RM ${dto.total} to
								${dto.merchantName}</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

								<p></p>
							</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 13px; font-family: Helvetica; color: #9b9b9b;">

								PAID TO</td>
						</tr>
						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #d9d9d9; border-top: 1px;">

							</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-weight: bold; font-family: Helvetica; color: #484b5c;">

								${dto.merchantName}</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-weight: bold; font-family: Helvetica; color: #9b9b9b;">

								${dto.merchantAddr1} - ${dto.merchantPostCode}</td>
						</tr>




						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-weight: bold; font-family: Helvetica; color: #9b9b9b;">

								${dto.merchantCity},</td>
						</tr>



						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-weight: bold; font-family: Helvetica; color: #9b9b9b;">

								${dto.merchantState},</td>
						</tr>




						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-weight: bold; font-family: Helvetica; color: #9b9b9b;">

								${dto.merchantContNo}</td>
						</tr>



						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											MID</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.mid}</td>
									</tr>
								</table>
							</td>

						</tr>


						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											TID</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.tid}</td>
									</tr>
								</table>
							</td>

						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

								<p></p>
							</td>
						</tr>


						<!-- TRANSACTION DETAILS -->
						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 13px; font-family: Helvetica; color: #9b9b9b;">

								TRANSACTION DETAILS</td>
						</tr>

						<tr>
							<td>

								<table style="width: 100%">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											RRN</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.refNo}</td>
									</tr>
								</table>
							</td>

						</tr>


						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Approval Code</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.apprCode}</td>
									</tr>
								</table>
							</td>

						</tr>




						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Reference</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.invoiceNo}</td>
									</tr>
								</table>
							</td>

						</tr>



						<tr>
							<td>



								<table style="width: 100%">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Payment Method</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">


											<img style="height: 45px !important"
											; src="data:image/png;base64,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">


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
										<td
											style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

											<p></p>
										</td>
									</tr>

									<tr>
										<td
											style="color: #005baa; text-align: center; font-size: 10px;">

											Mobi Asia Sdn. Bhd."</td>

									</tr>

<!-- 									<tr> -->
<!-- 										<td -->
<!-- 											style="color: #005baa; text-align: center; font-size: 10px;"> -->

<!-- 											Suite #07-01, Wisma UOA Damansara II, No. 6, Changkat -->
<!-- 											Semantan, "</td> -->

<!-- 									</tr> -->

<!-- 									<tr> -->
<!-- 										<td -->
<!-- 											style="color: #005baa; text-align: center; font-size: 10px;"> -->

<!-- 											Damansara Heights, 50490 Kuala Lumpur."</td> -->

<!-- 									</tr> -->


<!-- 									<tr> -->
<!-- 										<td> -->
<!-- 											<table align="center"> -->
<!-- 												<tr> -->
<!-- 													<td><a href='https://www.instagram.com/mobi_asia'> -->
<!-- 															<img style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,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"> -->
<!-- 													</a></td> -->
<!-- 													<td><a href='https://facebook.com/mobiversa'> <img -->
<!-- 															style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuBAMAAACllzYEAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAwUExURUdwTDpZmDpYmDpYmDpYlzdWmDpYlzpYmDNWmDpYmDpYmDlYlzpYmDpZmDlYlztZmAlJkhIAAAAPdFJOUwCP6ziDFNnBCqdOI2PSc6JlMPUAAAEuSURBVDjLY2CAgij3////lyxlQAUcx/9DQE0DirDTfxjQQ5Yw+o8AyQjhxv/IwAEmzJ2PIv49ACq+6T8qSIUq10cT/wbREPQfHSSAxedjiH8BCXP9xwQGKMZ8E3GRh7AuAMXlkR3OD2H9YWBghQt/ZICLf2Ng4IOLKyDE/y9g4IWLT2DgmV4PZ++HiwsgsRUY3iOJ28PZn5F8hSz+j+E+VvGfDDDWB3CYwGS+o4mfh3kdTdwfyvsEF/+BGrYM+ljt/cSQj1X8L0M9DnfKYxX/yLAeq/gvhnis4j8Y2LGKA8Mcq/gCBg59LOKfkLyOLP4PKN4E45S4zEdOcLjSFa50iDPd4krnGPniKoF8hCvfMTAswp5PGViR8jVKOcD6FlYOoBUQDFHiwOAqWQHjAgDrxeljvr6QpgAAAABJRU5ErkJggg=="></a></td> -->
<!-- 													<td><a href='https://gomobi.io'> <img -->
<!-- 															style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC4AAAAuCAMAAABgZ9sFAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABpUExURUdwTABaqgBaqgBRrgBbqQBaqgBZqgBbqgBaqgBaqgBaqQBbrABbqgBbqv///1WRxuHr9dTj8RBlr7bQ51COxSd0t8DX6/D2+jh/vAZfrESGwfn7/p/B383f74mz2Hen0medzK3K5Nvo8yFs720AAAANdFJOUwDGiw2fuDfX7FN3I0O1cVhpAAAB80lEQVRIx62W2ZKDIBBFs6iYODaKENkUzf9/5KiogElMUjP3BQpPUdDLxcNhq+QaxShNURxdk8MbXaI095RGlz0Y5Q9Cly/gUcefZ3SUv9TpAT7H+Y7ic0hnKN8VyoK939DDBfz94/ytYkef8g+03jcJlgWRgrRFS4Qkwv+w5Pjow8wUNTMKVMfqu2H+8ef0eEukawA0KQCgYBpU0xH30abLiwppawAlDYwyWAHUrePRSF83NDSsmPCSlRDylyD5ohtp0HKioZZ6GroViAbcVSxv7CE6i6uuncZmvW8aRFFWt0GV7G6TKkztAvdi6VIkOMWDJGHYihE5DpQLlyqXf1xO6nlvJ6VeZtRVggujtke+ybudQEPtZUC7TLmblvZbRR1+s5PSRd7dtJ5xvOAlnvF6Zbzd1YIXW1y5SKLH3YuXuyOvHD84+9GrgSUy9HVkIldhAheTel7ZyX2drXG/ekXwQVYzr8QE76tBPcfVpJ7KecEveHd4bi9rzFKRZlORUdh75nm9C789vMgTrYJuasJuQtvWJnrqVdsWhqrH3guNg5jh/JotTgC+E8zGcfjxzYe3RU1GnzGDz7Q83/rG1vQEw9bFCGXiuclvLVWIPUv9wLBR8CBk6T6dZn95bPZN/vTs6UuOL96Z5D+e4a8f+fe/EL+v0mICGsINWgAAAABJRU5ErkJggg=="></a></td> -->
<!-- 													<td><a href='https://www.linkedin.com/company/4813011'> -->
<!-- 															<img style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvCAMAAABE+WOeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABIUExURUdwTAB5uQB5uQB6uQB5ugB5uQB6uQB5uQB6uAB5uwB5uQBlzwB6uf///0CbyoC93L/e7imPxFCjz7DW6ZTH4s/m8ufz+WCs0xOqFSMAAAAMdFJOUwD3V28zz+aLsRudBMZ4Ls0AAAFUSURBVEjHrZbZloMgDIYBWTW4YDt9/zedkYoNlM1z5r9qPV9ICCGBkFSGKS0kSKEVM6QhrgRgCcVrtIZv6YLFZHK0t8iGxSiURNk3PkBFdEhxBXWpe3hiwKAttAdOO3h65dXI85Nb7eqKBjKkNeR9tofmooE+own/H55/lEPi0fInv0DdwbU8bJ7foO4ApX5e131uHYKAfgkcTk5b4s7gOtvWQ3/xv/wP2HwCnthkICMKP+R/eW/8x9o0YyPRRT7g1jqUUVHkP3qiDcsyvzu32/hMJKFFfj++vQ1eV5FW1veLunjHshI/qpHlE7+u8xDzupL/HD/i8+3gB2Ju8QbXZ5sXUf23eRXdrzbPo/vb5HXcH5r82bHGzts4hv5Gu3Aa+tvEunjWOSyuo0Wz62b/b3tI8Kk27vIDj8ty58+OYKPyLqgqPQuyE1ubf3s/vJ0MtffJL7CKUzDXtiolAAAAAElFTkSuQmCC"> -->
<!-- 													</a></td> -->
<!-- 													<td><a href='https://twitter.com/Mobiversa'><img -->
<!-- 															style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvCAMAAABE+WOeAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAABLUExURUdwTFOs9FSs71Ss7VOs71Wr7lSs71Ss7lSr7lWs7lKt8FSs7lSs7lWs7v///1+x73C58Y/J9MPi+qbU93/B8tru/PX6/rXb+Or1/XOCudUAAAANdFJOUwAYcfY05leLsM4l+p25++9ZAAABgUlEQVRIx6VWWbKEIAwERcFRFgGX+5/0OeVCoqA4r7+0bJMQmg6EnEFrLppKVo3gNSUPYLyREA1nd2xMXiFSf3yEbGUM4hOj16VMoayv9EKm0bbFmc7lPfg7+umHWj4DrIGVGfzy6CutZA6qfbeFzIPY9knmgr0KvyVgyc+9HWz/fdAwAWh9D9lmUF8Mxk8WbkLQpFE20PWodozmkDdarVfKHqltoE/2SEyhzha+mv32EsIrkLYgHah4/WjC8wqwrA51c12hGpzXGvBRR+ERBEHHOL8hQDt+cioGqCHSRsNDDPCkwfh7/Sc4FB/W76N8j+pHavPjlT5jxXXwVff2wkei6rCP6PE+/LK/FEv4PvyiH4I9sz9lmLD9Xq1HO5vo/dJ9fj1f2kWFCQ6wgP2ZxsROBYNg7R4Z1z6Ys+1uvt6FWg5FzJO5nP9uHxTADrV3zvXexAyRvrFbZLhFBr34h/8/ZyhejLv4wGPpKVBFRzDl8fHbcpqe2LnT+qf7Q8b95A8MgFqC3uKrqgAAAABJRU5ErkJggg=="></a></td> -->
<!-- 													<td><a -->
<!-- 														href='https://www.youtube.com/channel/UCsCvriJbbS0wpRAYXW-ugmg'><img -->
<!-- 															style="width: 25px; padding: 5px;" -->
<!-- 															src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAC8AAAAvBAMAAACBCY6fAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAtUExURUdwTOpALOpALOtALOpAK+pAK+tAKuxBLOpAK+pAK+9AKO9AIupALOtAK+pBLLn818oAAAAOdFJOUwDA7kCgMFk+kLEgEYBwEy5X+gAAARpJREFUOMtjYIAC1ity796JXGVABxF678BAIwBV3PAdDDw0QBZf+Q4BXi5AiLPpIUm8U0NI+L1DAQ0wcRZU8XdPcGh4984Bu4Z3715AJPIwJN4lgMS59DAltEASnJji716CJOywSLwDeV8Om4QAMFSxib97CnPso57DxmBwpgnmYCYwQxsePNxgRz5iYIhDOBzZWwEM98D0BoTEPrDABWhAIcUNHzS4wK59CPYpJO4Yoe59B5dggsQqROIBssS7hwU4JN7NxiEhjsMoYQZkCYSrmBlQXIXTHzh9jjOsmBCRjBq6HJD48DlWDgJlZ5xg8YEzBnHGOe5UgjNd4UyJONMu7tSOM3/gzlE48yDuXIszn+MuGXCXJZilDwDegnYx5JeH0wAAAABJRU5ErkJggg=="></a></td> -->

<!-- 												</tr> -->
<!-- 											</table> -->

<!-- 										</td> -->

<!-- 									</tr> -->

								</table>
							</td>

						</tr>

						<!--CUSTOMER CARD DETAILS -->

					</table>

				</td>
			</tr>
		</table>

		</td>
		</tr>

		</table>
	</center>



</body>
</html>
