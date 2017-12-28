<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/process/reserve.js${JS_SUFFIX}"></script>
<script type="text/html" id="tpl_reserve">
<div class="page">
    <div class="weui-tab__panel">
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="matterNumber"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnReserveSystemMatterId" value="${requestScope.pageData.matterSystemInfo.systemMatterId}"/>
                    <input type="hidden" name="hdnReserveUserDataId" value="${requestScope.pageData.matterSystemInfo.userDataId}"/>
                    <input type="hidden" name="hdnReserveNodeId" value="${requestScope.pageData.matterSystemInfo.nodeId}"/>
                    <input type="hidden" name="hdnSf" value="${requestScope.pageData.sf}"/>
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterNumber}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="matterName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterName}">
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.reserve" inm="applayInfo"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="applyUserName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyAuthUserName}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="applyBaseDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyBaseDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="applyDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.reserve" inm="authUser"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnReserveAuthUserInfo" value='${requestScope.pageData.authUserAndOrgzListJsonBase64}' />
                    <input type="hidden" name="hdnReserveTenantId" value='${requestScope.pageData.accountInfo.tenantId}' />
                    <input type="hidden" name="hdnReserveExecutorCd" value='${requestScope.pageData.accountInfo.userCd}' />
                    <select class="weui-select" name="selReserveAuthUser"></select>
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.reserve" inm="comment"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea name="tarReserveComment" class="weui-textarea" rows="3"></textarea>
                </div>
            </div>
        </div>
        <br />
		<!-- loading toast -->
		    <div id="loadingToast3" style="display:none;">
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
            <p class="weui-tabbar__label" ins="common.workflow.process.reserve" inm="btnBack"></p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on" id="btnReserve">
            <img src="${RESOURCE_PATH}/img/icon_nav_reserve.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.process.reserve" inm="btnReserve"></p>
        </a>
    </div>
</div>

<script type="text/javascript">
    // 页面初始化
    $(function(){
        // 设定处理者信息
        ychips.wxio.CmnUtil.setProcessUserInfoByBase64($("input[name='hdnReserveAuthUserInfo']").val());
        // 初始化用户组件
        ychips.wxio.CmnUtil.setAuthUserComponents(
            $("select[name='selReserveAuthUser']")
        );

        // 审批按钮按下
        $("#btnReserve").on("click", function() {
			// 点击直接触发toast效果，避免重复提交
            var $loadingToast = $('#loadingToast3');
			if ($loadingToast.css('display') != 'none') return;
        	$loadingToast.fadeIn(100);
			// 用户代码
            var usercd = $("input[name='hdnReserveExecutorCd']").val();

            // TenantId
            var tenantid = $("input[name='hdnReserveTenantId']").val();

            // 权限者
            var aucd = $("select[name='selReserveAuthUser']").val();

            // 执行者
            var eucd = $("input[name='hdnReserveExecutorCd']").val();

            // 系统案件号
            var smid = $("input[name='hdnReserveSystemMatterId']").val();

            // 用户数据号
            var udid = $("input[name='hdnReserveUserDataId']").val();

            // 节点编码
            var nodeid = $("input[name='hdnReserveNodeId']").val();

            // 备注
            var comment = $("textarea[name='tarReserveComment']").val();

            // 服务器类型
            var sf = $("input[name='hdnSf']").val();

            // 提交的数据
            var postData = {
                "usercd": usercd,
                "tenantid": tenantid,
                "aucd": aucd,
                "eucd": eucd,
                "udid": udid,
                "smid": smid,
                "nodeid": nodeid,
                "comment": comment,
                "sf": sf,
                "data": JSON.stringify({})
            };

            $.ajax({
                type: 'POST',
                url: '${CONTEXT_PATH}/wx/view/cmn/wkf/process/reserve',
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
        });
    });
</script>
</script>