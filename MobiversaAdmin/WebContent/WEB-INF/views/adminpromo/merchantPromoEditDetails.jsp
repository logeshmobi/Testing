<%@page
	import="com.mobiversa.payment.controller.PromotionAdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
 <link rel="stylesheet" type="text/css" href="engine1/style.css" />
<script type="text/javascript" src="engine1/jquery.js"></script>
</head>
<style>
img {
    transition: -webkit-transform 0.25s ease;
    transition: transform 0.25s ease;
     cursor: zoom-in;
     cursor:-webkit-zoom-in;
    cursor:-moz-zoom-in;
     cursor: zoom-out;
     cursor:-webkit-zoom-out;
    cursor:-moz-zoom-out;
    overflow:hidden;
   
   
}
img:active {
    -webkit-transform: scale(2);
    transform: scale(2);
}
</style>


<body>
<div style="overflow:auto;border:1px;width:100%">
	<div class="content-wrapper">
  <div class="row">
			<div class="col-md-6">
           <div class="card" style="width: 50rem;">
              <h3 class="card-title">Merchant EZYAds Details</h3>
              <table class="table table-striped">

					<tbody>
						

						<tr >

							<td>BusinessName</td>
							<td>${mobiPromo.merchantName}</td>

						</tr>


						<tr>

							<td>Mid</td>
							<td>${mobiPromo.mid}</td>

						</tr>

						<tr>

							<td>EZYAds Code</td>
							<td>${mobiPromo.pCode}</td>

						</tr>

						<tr>

							<td>EZYAds Name</td>
							<td>${mobiPromo.promoName}</td>

						</tr>




						<tr >

							<td>EZYAds Desc</td>
							<td style="width:100%;word-break:break-all;">${mobiPromo.promoDesc}</td>

						</tr>

<!-- style="width:100%;word-break:break-all;" -->


						<tr>

							<td>EZYAds Image</td>
							 <td ><img width="100" height="100"
								src="data:image/jpg;base64,<c:out value='${mobiPromo.promoLogo1}'/>" />

<c:choose>
	<c:when test="${mobiPromo.promoLogo2 != null}">
							<img width="100"  height="100"
								src="data:image/jpg;base64,<c:out value='${mobiPromo.promoLogo2}'/>" />

</c:when>
</c:choose>

</td>
						</tr>

<tr>

<td>Mobile User Name</td>
							<td>${mobiPromo.username}</td>

						</tr>

<tr>

							<td>Message Count</td>
							<td>${mobiPromo.points}</td>

						</tr>
<tr>
							<td>Valid_From</td>
							<td>${mobiPromo.validFrom}</td>

<%-- ><fmt:formatDate value="${mobiPromo.validityDate}" pattern="dd-MMM-yyyy" /> --%>

						</tr>
						
						<tr>

							<td>Valid_To</td>
							<td>${mobiPromo.validTo}</td>

<%-- ><fmt:formatDate value="${mobiPromo.validityDate}" pattern="dd-MMM-yyyy" /> --%>

						</tr>


						<tr>

							<td>Status</td>
							<td>${mobiPromo.status}</td>

						</tr>

<tr>






					</tbody>
				</table>
				
				</div>
				</div>
				
		
		</div>
		
			<a
				href="${pageContext.request.contextPath}/<%=PromotionAdminController.URL_BASE%>/edit/${mobiPromo.id}"
				class="btn btn-primary">Edit Details</a>
			</div>	
				</div>
</body>
</html>
