<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title></title>
<link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css" />
<link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.io.css" />
<script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
<script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
<script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/cmnMsg.js${JS_SUFFIX}"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/yci/yexp/exp0901/approveview.js"></script>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/yci/yexp/common/applyUserInfo.js"></script>
<style type="text/css">
.label_th {
	text-align: center;
	color: #888;
}

.label_td {
	text-align: center;
	color: blue;
}

.weui-label-0901 {
	display: block;
	width: 120px;
	word-wrap: break-word;
	word-break: break-all;
}
</style>
<script type="text/javascript">
	// 设置页面区域
	ychips.wxio.CmnUtil
			.setLocale('${requestScope.pageData.accountInfo.localId}');
</script>
</head>
<body ontouchstart>
	<div class="container" id="container"></div>
	<script type="text/html" id="tpl_home">
	<div class="page">
		<div class="weui-tab__panel">
			<!-- 01.申请者信息 -->
			<%@include file="/WEB-INF/views/wx/yci/yexp/common/applyUserInfo.jsp"%>
			<!-- 02.印章申请信息 -->
			<!-- 印章种类 -->
			<div class="weui-cells__title" ins="yci.yexp.exp0901.approveview"
				inm="stampsType"></div>
			<div class="weui-cells weui-cells_form">
				<!-- 统括事业部所属 -->
				<c:if test="${requestScope.pageData.matterUserInfo.viewFlag01}">
					<div class="wxio-record">
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p class="weui-label-0901" ins="yci.yexp.exp0901.approveview"
									inm="stamps_1"></p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>

						<div class="weui-cells wxio-record-content">
							<c:forEach
								items="${requestScope.pageData.matterUserInfo.stampList}"
								begin="0" end="6" var="stampList">
								<c:if test="${stampList.checked.equals(true)}">
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="yci.yexp.exp0901.approveview"
												inm="stampsName"></label>
										</div>
										<div class="label_td">
											<input class="weui-input" value="${stampList.label}"
												readonly="readonly" />
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:if>

				<!-- AP事业部所属 -->
				<c:if test="${requestScope.pageData.matterUserInfo.viewFlag02}">
					<div class="wxio-record">
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p class="weui-label-0901" ins="yci.yexp.exp0901.approveview"
									inm="stamps_2"></p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>
						<div class="weui-cells wxio-record-content">
							<c:forEach
								items="${requestScope.pageData.matterUserInfo.stampList}"
								begin="7" end="10" var="stampList">
								<c:if test="${stampList.checked.equals(true)}">
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="yci.yexp.exp0901.approveview"
												inm="stampsName"></label>
										</div>
										<div class="label_td">
											<input class="weui-input" value="${stampList.label}"
												readonly="readonly" />
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<!--苏州研发所属 -->
				<c:if test="${requestScope.pageData.matterUserInfo.viewFlag03}">
					<div class="wxio-record">
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p class="weui-label-0901" ins="yci.yexp.exp0901.approveview"
									inm="stamps_3"></p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>

						<div class="weui-cells wxio-record-content">
							<c:forEach
								items="${requestScope.pageData.matterUserInfo.stampList}"
								begin="11" end="12" var="stampList">
								<c:if test="${stampList.checked.equals(true)}">
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="yci.yexp.exp0901.approveview"
												inm="stampsName"></label>
										</div>
										<div class="label_td">
											<input class="weui-input" value="${stampList.label}"
												readonly="readonly" />
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<!-- 大连研发所属 -->
				<c:if test="${requestScope.pageData.matterUserInfo.viewFlag04}">
					<div class="wxio-record">
						<a class="weui-cell weui-cell_access" href="javascript:;">
							<div class="weui-cell__bd">
								<p class="weui-label-0901" ins="yci.yexp.exp0901.approveview"
									inm="stamps_4"></p>
							</div>
							<div class="weui-cell__ft"></div>
						</a>

						<div class="weui-cells wxio-record-content">
							<c:forEach
								items="${requestScope.pageData.matterUserInfo.stampList}"
								begin="13" end="15" var="stampList">
								<c:if test="${stampList.checked.equals(true)}">
									<div class="weui-cell">
										<div class="label_th">
											<label class="weui-label" ins="yci.yexp.exp0901.approveview"
												inm="stampsName"></label>
										</div>
										<div class="label_td">
											<input class="weui-input" value="${stampList.label}"
												readonly="readonly" />
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
			<!--内容  -->
			<div class="weui-cells weui-cells_form">
				<div class="weui-cells__title" ins="yci.yexp.exp0901.approveview"
					inm="contents"></div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" value="" rows="2" readonly="ture"
							style="color: grey;">${requestScope.pageData.matterUserInfo.contents}</textarea>
						<div class="weui-textarea-counter"></div>
					</div>
				</div>
			</div>
			<!--资料名-->
			<div class="weui-cells weui-cells_form">
				<div class="weui-cells__title" ins="yci.yexp.exp0901.approveview"
					inm="fileName"></div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" value="" rows="2" readonly="ture"
							style="color: grey;">${requestScope.pageData.matterUserInfo.fileName}</textarea>
						<div class="weui-textarea-counter"></div>
					</div>
				</div>
			</div>
			<!--资料番号/日期  -->
			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label-0901" ins="yci.yexp.exp0901.approveview"
							inm="fileCd"></label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input"
							value="${requestScope.pageData.matterUserInfo.fileCd}"
							readonly="readonly" style="color: grey;" />
					</div>
				</div>
			</div>
			<br />
			<!--按鈕-->
			<c:if
				test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
				<div>
					<div class="weui-mask" id="pageMask" style="display: none"></div>
					<div class="weui-actionsheet" id="processActionsheet">
						<div class="weui-actionsheet__title">
							<p class="weui-actionsheet__title-text"
								ins="common.workflow.cmnMsg" inm="processActionSheetTitle"></p>
						</div>
						<div class="weui-actionsheet__menu">
							<c:forEach
								items="${requestScope.pageData.matterSystemInfo.processType}"
								var="entry">
								<a class="js_item" data-id="${entry.key}" href="javascript:;">
									<div class="weui-actionsheet__cell"
										style="color: rgb(4, 190, 2); background: rgb(255, 255, 255)">
										<label>${entry.value}</label>
									</div>
								</a>
							</c:forEach>
						</div>
						<div class="weui-actionsheet__action">
							<div class="weui-actionsheet__cell" id="btnCancel">
								<label ins="common.workflow.cmnMsg" inm="cancel"></label>
							</div>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<div class="weui-tabbar">
			<a href="javascript:;" class="weui-tabbar__item" id="btnHistory">
				<img src="${RESOURCE_PATH}/img/icon_nav_history.png" alt=""
				class="weui-tabbar__icon">
				<p class="weui-tabbar__label" ins="common.workflow.cmnMsg"
					inm="history"></p>
			</a>
			<c:if
				test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
				<a href="javascript:;" class="weui-tabbar__item weui-bar__item_on"
					id="btnProcess"> <img
					src="${RESOURCE_PATH}/img/icon_nav_process.png" alt=""
					class="weui-tabbar__icon">
					<p class="weui-tabbar__label" ins="common.workflow.cmnMsg"
						inm="process"></p>
				</a>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			// 初始化按钮
			if ($('#btnProcess').length > 0) {
				ychips.wxio.CmnUtil.initAtcionSheet($('#pageMask'),
						$('#processActionsheet'), $('#btnProcess'));
			}
			// 初始化履历按钮
			ychips.wxio.CmnUtil.initHistory($("#btnHistory"));

			// 展开关闭
			$("div.wxio-record").find("a.weui-cell_access").click(
					function() {
						// 内容
						var record = $(this).closest(
								"div.wxio-record.record_show");
						if (record && record.length > 0) {
							$(this).closest("div.wxio-record").removeClass(
									"record_show");
						} else {
							$(this).closest("div.wxio-record").addClass(
									"record_show");
						}
					});
		});
	</script>
	</script>
	<c:if
		test="${requestScope.pageData.matterSystemInfo.isPossibleToProcess}">
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/approve.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/process/deny.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/reserve.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/reservecancel.jsp"%>
		<%@include
			file="/WEB-INF/views/wx/common/workflow/process/sendback.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/success.jsp"%>
		<%@include file="/WEB-INF/views/wx/common/workflow/error.jsp"%>
	</c:if>
	<%@include file="/WEB-INF/views/wx/common/workflow/unit/history.jsp"%>
</body>
</html>