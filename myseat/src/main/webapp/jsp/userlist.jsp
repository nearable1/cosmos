<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/jsp/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>用户管理页面</span>
            </div>
            <div class="search">
           		<form method="get" action="#">
					<input name="method" value="query" class="input-text" type="hidden">
					 <span>用户名：</span>
					 <input name="queryname" class="input-text"	type="text" value="${#}">
					 
					 <span>用户角色：</span>
					 <select name="queryUserRole">
						<c:if test="${#}">
						   <option value="0">--请选择--</option>
						   <c:forEach var="#" items="${#}">
						   		<option <c:if test="${#}">selected="selected"</c:if>
						   		value="${#}">${#}</option>
						   </c:forEach>
						</c:if>
	        		</select>
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="#" >添加用户</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">用户编码</th>
                    <th width="20%">用户名称</th>
                    <th width="10%">性别</th>
                    <th width="10%">年龄</th>
                    <th width="10%">电话</th>
                    <th width="10%">用户角色</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="lstr" items="${listrole}" varStatus="status">
                    <!-- 相当于Iterator -->
                   <c:forEach items="${lstr.smbmuser }" var="ltr">
					<tr>
						<td>
						<span>${ltr.usercode}</span>
						</td>
						<td>
						<span>${ltr.username }</span>
						</td>
						<td>
							<span>
								<c:if test="${ltr.gender eq '1'}">男</c:if>
								<c:if test="${ltr.gender eq '2'}">女</c:if>
							</span>
						</td>
						<td>
						<span>${ltr.age}</span>
						</td>
						<td>
						<span>${ltr.phone}</span>
						</td>
						<td>
							<span>${lstr.rolename}</span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:;" userid=${# } username=${# }><img src="${pageContext.request.contextPath }/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="javascript:;" userid=${# } username=${# }><img src="${pageContext.request.contextPath }/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="<%=path %>/DelBillServlet2?id=${ltr.id }"><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
			</table>
		<%--<input type="hidden" id="totalPageCount" value="${totalPageCount}"/> 
		  	<c:import url="rollpage.jsp">
 	          	<c:param name="totalCount" value="${totalCount}"/> 
 	          	<c:param name="currentPageNo" value="${currentPageNo}"/> 
 	          	<c:param name="totalPageCount" value="${totalPageCount}"/> 
           	</c:import> --%>
				<a href="page?currentpage=1">▲</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="page?currentpage=${pg.currentpage-1 }">&lt;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="page?currentpage=${pg.currentpage+1 }">&gt;</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="#">●</a>&nbsp;&nbsp;
				
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userlist.js"></script>
