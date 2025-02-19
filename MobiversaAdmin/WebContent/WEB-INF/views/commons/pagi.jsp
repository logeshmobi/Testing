<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="pagination">
	<c:choose>
		<c:when test="${paginationBean.previousPageAvailable }">
			<li><a href="${paginationBean.currPage -1}">&laquo;</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><span>&laquo; <span class="sr-only">(previous page)</span></span></li>
		</c:otherwise>
	</c:choose>

	<c:forEach items="${paginationBean.paginationNumbers }" var="num">
		<c:choose>
			<c:when test="${paginationBean.currPage == num}">
				<li class="active"><span>${num} <span class="sr-only">(current page)</span></span></li>
			</c:when>
			<c:otherwise>
				<li><a href="${num}">${num}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${paginationBean.nextPageAvailable }">
			<li><a href="${paginationBean.currPage +1}">&raquo;</a></li>
		</c:when>
		<c:otherwise>
			<li class="disabled"><span>&raquo; <span class="sr-only">(next page)</span></span></li>
		</c:otherwise>
	</c:choose>
</ul>