<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/process/sendback.js${JS_SUFFIX}"></script>
<script type="text/html" id="tpl_sendback">
<div class="page">
    <div class="weui-tab__panel">
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="matterNumber"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnSendBackSystemMatterId" value="${requestScope.pageData.matterSystemInfo.systemMatterId}"/>
                    <input type="hidden" name="hdnSendBackUserDataId" value="${requestScope.pageData.matterSystemInfo.userDataId}"/>
                    <input type="hidden" name="hdnSendBackNodeId" value="${requestScope.pageData.matterSystemInfo.nodeId}"/>
                    <input type="hidden" name="hdnSf" value="${requestScope.pageData.sf}"/>
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterNumber}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="matterName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterName}">
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.sendback" inm="applayInfo"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="applyUserName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyAuthUserName}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="applyBaseDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyBaseDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="applyDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="authUser"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnSendBackAuthUserInfo" value='${requestScope.pageData.authUserAndOrgzListJsonBase64}' />
                    <input type="hidden" name="hdnSendBackTenantId" value='${requestScope.pageData.accountInfo.tenantId}' />
                    <input type="hidden" name="hdnSendBackExecutorCd" value='${requestScope.pageData.accountInfo.userCd}' />
                    <select class="weui-select" name="selSendBackAuthUser"></select>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.sendback" inm="authDepartment"></label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="selSendBackAuthDept"></select>
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.sendback" inm="sendBackNodesInfo"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cells_checkbox" id="sendBackNodesArea">
                <c:forEach items="${requestScope.pageData.matterSystemInfo.nodesToSendBack}" var="node" varStatus="status">
                <label class="weui-cell weui-check__label" for="node_idx_${status.count}">
                    <div class="weui-cell__bd">
                        <div class="weui-flex">
                            <div class="weui-flex__item"><div class="placeholder">${node.nodeName}</div></div>
                            <div class="weui-flex__item"><div class="placeholder">${node.processTypeName}</div></div>
                            <div class="weui-flex__item"><div class="placeholder">${node.authUserName}</div></div>
                        </div>
                    </div>
                    <div class="weui-cell__ft">
                        <input type="radio" class="weui-check" data-nodeid="${node.nodeId}" name="rdoSendBackNode" id="node_idx_${status.count}">
                        <span class="weui-icon-checked"></span>
                    </div>
                </label>
                </c:forEach>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.sendback" inm="comment"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea name="tarSendBackComment" class="weui-textarea" rows="3"></textarea>
                </div>
            </div>
        </div>
        <br />
		<!-- loading toast -->
		    <div id="loadingToast5" style="display:none;">
		        <div class="weui-mask_transparent"></div>
		        <div class="weui-toast">
		            <i class="weui-loading weui-icon_toast"></i>
		            <p class="weui-toast__content" ins="common.workflow.process.approve" inm="dataCommiting"></p>
		    	</div>
			</div>
    </div>
    <div class="weui-tabbar">
        <a href="javascript:home();" class="weui-tabbar__item">
            <img src="${RESOURCE_PATH}/img/icon_nav_back.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.process.sendback" inm="btnBack"></p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on" id="btnSendBack">
            <img src="${RESOURCE_PATH}/img/icon_nav_sendback.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.process.sendback" inm="btnSendBack"></p>
        </a>
    </div>
</div>

<script type="text/javascript">
    // 页面初始化
    $(function(){
        // 设定处理者信息
        ychips.wxio.CmnUtil.setProcessUserInfoByBase64($("input[name='hdnSendBackAuthUserInfo']").val());

        // 初始化用户组件
        ychips.wxio.CmnUtil.setAuthUserComponents(
            $("select[name='selSendBackAuthUser']"),
            $("select[name='selSendBackAuthDept']")
        );

        // 审批按钮按下
        $("#btnSendBack").on("click", function() {
			// 点击直接触发toast效果，避免重复提交
			var $loadingToast = $('#loadingToast5');
			if ($loadingToast.css('display') != 'none') return;
        	$loadingToast.fadeIn(100);
            // 退回节点Id
            var sendBackNodeId = "";
            // 退回节点一览
            $("input[name='rdoSendBackNode']:checked").each(function() {
                sendBackNodeId = $(this).data("nodeid");
            });

            // 如果没有选中
            if (!sendBackNodeId) {
                var errMsg = ychips.wxio.CmnUtil.getMessage("common.workflow.process.sendback", "msgError001");
                alert(errMsg);
            } else {
                // 退回节点一览JSON字符串
                var sbnodeid = JSON.stringify([sendBackNodeId]);

                // 用户代码
                var usercd = $("input[name='hdnSendBackExecutorCd']").val();

                // TenantId
                var tenantid = $("input[name='hdnSendBackTenantId']").val();

                // 权限者
                var aucd = $("select[name='selSendBackAuthUser']").val();

                // 执行者
                var eucd = $("input[name='hdnSendBackExecutorCd']").val();

                // 部门代码
                var dcd = $("select[name='selSendBackAuthDept']").val();

                // 系统案件号
                var smid = $("input[name='hdnSendBackSystemMatterId']").val();

                // 用户数据号
                var udid = $("input[name='hdnSendBackUserDataId']").val();

                // 节点编码
                var nodeid = $("input[name='hdnSendBackNodeId']").val();

                // 备注
                var comment = $("textarea[name='tarSendBackComment']").val();

                // 服务器类型
                var sf = $("input[name='hdnSf']").val();

                // 提交的数据
                var postData = {
                    "usercd": usercd,
                    "tenantid": tenantid,
                    "aucd": aucd,
                    "eucd": eucd,
                    "dcd": dcd,
                    "udid": udid,
                    "smid": smid,
                    "nodeid": nodeid,
                    "sbnodeid": sbnodeid,
                    "comment": comment,
                    "sf": sf,
                    "data": JSON.stringify({})
                };

                $.ajax({
                    type: 'POST',
                    url: '${CONTEXT_PATH}/wx/view/cmn/wkf/process/sendback',
                    data: postData,
                    dataType: 'json',
                    success: function(data) {
                        if(data && !data.error) {
                            ychips.wxio.CmnUtil.toSuccessPage();
                        } else {
                            // 错误页面出现，Toast消失in 0.1s
                            setTimeout(function () {
                    			$loadingToast.fadeOut(100);
                            });
                            ychips.wxio.CmnUtil.toErrorPage(data && data.error_msg ? data.error_msg : "");
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        // 错误页面出现，Toast消失in 0.1s
                    	setTimeout(function () {
                    		$loadingToast.fadeOut(100);
                    	});
                        ychips.wxio.CmnUtil.toErrorPage(textStatus);
                    }
                });
            }
        });
    });
</script>
</script>