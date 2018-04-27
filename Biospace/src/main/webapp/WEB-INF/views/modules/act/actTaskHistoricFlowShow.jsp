<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>操作履历</title>
</head>
<body>
	<form:form id="inputForm" modelAttribute="act" class="breadcrumb form-search">
		<c:if test="${!empty act.procInsId}">
	        <act:histoicFlow procInsId="${act.procInsId}"/>
	    </c:if>
	</form:form>
</body>
</html>