<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/process/deny.js${JS_SUFFIX}"></script>
<script type="text/html" id="tpl_deny">
<div class="page">
    <div class="weui-tab__panel">
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="matterNumber"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnDenySystemMatterId" value="${requestScope.pageData.matterSystemInfo.systemMatterId}"/>
                    <input type="hidden" name="hdnDenyUserDataId" value="${requestScope.pageData.matterSystemInfo.userDataId}"/>
                    <input type="hidden" name="hdnDenyNodeId" value="${requestScope.pageData.matterSystemInfo.nodeId}" />
                    <input type="hidden" name="hdnSf" value="${requestScope.pageData.sf}"/>
                    <input type="hidden" name="applyAuthUserCode" value="${requestScope.pageData.matterSystemInfo.applyAuthUserCode}" />
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterNumber}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="matterName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterName}">
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.deny" inm="applayInfo"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="applyUserName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyAuthUserName}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="applyBaseDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyBaseDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="applyDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="authUser"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnDenyAuthUserInfo" value='${requestScope.pageData.authUserAndOrgzListJsonBase64}' />
                    <input type="hidden" name="hdnDenyTenantId" value='${requestScope.pageData.accountInfo.tenantId}' />
                    <input type="hidden" name="hdnDenyExecutorCd" value='${requestScope.pageData.accountInfo.userCd}' />
                    <select class="weui-select" name="selDenyAuthUser"></select>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.deny" inm="authDepartment"></label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="selDenyAuthDept"></select>
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.deny" inm="comment"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea name="tarDenyComment" class="weui-textarea" rows="3"></textarea>
                </div>
            </div>
        </div>
        <br />
		<!-- loading toast -->
		    <div id="loadingToast2" style="display:none;">
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
            <p class="weui-tabbar__label" ins="common.workflow.process.deny" inm="btnBack"></p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on" id="btnDeny">
            <img src="${RESOURCE_PATH}/img/icon_nav_deny.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.process.deny" inm="btnDeny"></p>
        </a>
    </div>
</div>

<script type="text/javascript">
    // 页面初始化
    $(function(){
        // 设定处理者信息
        ychips.wxio.CmnUtil.setProcessUserInfoByBase64($("input[name='hdnDenyAuthUserInfo']").val());
        // 初始化用户组件
        ychips.wxio.CmnUtil.setAuthUserComponents(
            $("select[name='selDenyAuthUser']"),
            $("select[name='selDenyAuthDept']")
        );

        // 审批按钮按下
        $("#btnDeny").on("click", function() {
			// 点击直接触发toast效果，避免重复提交
            var $loadingToast = $('#loadingToast2');
			if ($loadingToast.css('display') != 'none') return;
        	$loadingToast.fadeIn(100);
        	
			// 用户代码
            var usercd = $("input[name='hdnDenyExecutorCd']").val();

            // TenantId
            var tenantid = $("input[name='hdnDenyTenantId']").val();

            // 权限者
            var aucd = $("select[name='selDenyAuthUser']").val();

            // 执行者
            var eucd = $("input[name='hdnDenyExecutorCd']").val();

            // 部门代码
            var dcd = $("select[name='selDenyAuthDept']").val();

            // 系统案件号
            var smid = $("input[name='hdnDenySystemMatterId']").val();

            // 用户数据号
            var udid = $("input[name='hdnDenyUserDataId']").val();

            // 节点编码
            var nodeid = $("input[name='hdnDenyNodeId']").val();

            // 备注
            var comment = $("textarea[name='tarDenyComment']").val();

            // 服务器类型
            var sf = $("input[name='hdnSf']").val();

			// 申请者cd
			var applyUserCd = $("input[name='applyAuthUserCode']").val();

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
                "comment": comment,
                "sf": sf,
                "data": JSON.stringify({})
            };
			
			// 添加申请者CD 用于借款冲销balance
			if (applyUserCd) {
          		postData.data = JSON.stringify({"applyUserCd" : applyUserCd});
			}
            $.ajax({
                type: 'POST',
                url: '${CONTEXT_PATH}/wx/view/cmn/wkf/process/deny',
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