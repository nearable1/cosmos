<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/process/approve.js${JS_SUFFIX}"></script>
<script type="text/html" id="tpl_approve">
<div class="page">
    <div class="weui-tab__panel">
		<div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="matterNumber"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnApproveSystemMatterId" value="${requestScope.pageData.matterSystemInfo.systemMatterId}"/>
                    <input type="hidden" name="hdnApproveFlowId" value="${requestScope.pageData.matterSystemInfo.flowId}"/>
                    <input type="hidden" name="hdnApproveUserDataId" value="${requestScope.pageData.matterSystemInfo.userDataId}"/>
                    <input type="hidden" name="hdnApproveNodeId" value="${requestScope.pageData.matterSystemInfo.nodeId}"/>
                    <input type="hidden" name="accountingFlag" />
                    <input type="hidden" name="hdnSf" value="${requestScope.pageData.sf}"/>
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterNumber}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="matterName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.matterName}">
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.approve" inm="applayInfo"></div>
            <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="applyUserName"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyAuthUserName}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="applyBaseDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyBaseDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="applyDate"></label>
                </div>
                <div class="weui-cell__bd">
                    <input class="weui-input" readonly="readonly" style="color:grey;" value="${requestScope.pageData.matterSystemInfo.applyDate}">
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="authUser"></label>
                </div>
                <div class="weui-cell__bd">
                    <input type="hidden" name="hdnApproveAuthUserInfo" value='${requestScope.pageData.authUserAndOrgzListJsonBase64}' />
                    <input type="hidden" name="hdnApproveTenantId" value='${requestScope.pageData.accountInfo.tenantId}' />
                    <input type="hidden" name="hdnApproveExecutorCd" value='${requestScope.pageData.accountInfo.userCd}' />
                    <select class="weui-select" name="selApproveAuthUser"></select>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__hd">
                    <label class="weui-label" ins="common.workflow.process.approve" inm="authDepartment"></label>
                </div>
                <div class="weui-cell__bd">
                    <select class="weui-select" name="selApproveAuthDept"></select>
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.approve" inm="stampInfo"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div id="approveStampArea" class="weui-cell__bd">
                </div>
            </div>
        </div>
        <div class="weui-cells__title" ins="common.workflow.process.approve" inm="comment"></div>
        <div class="weui-cells weui-cells_form">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <textarea name="tarApproveComment" class="weui-textarea" rows="3"></textarea>
                </div>
            </div>
        </div>
        <br />
		<!-- loading toast -->
		    <div id="loadingToast1" style="display:none;">
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
            <p class="weui-tabbar__label" ins="common.workflow.process.approve" inm="btnBack"></p>
        </a>
        <a href="javascript:;" class="weui-tabbar__item weui-bar__item_on" id="btnApprove">
            <img src="${RESOURCE_PATH}/img/icon_nav_approve.png" alt="" class="weui-tabbar__icon">
            <p class="weui-tabbar__label" ins="common.workflow.process.approve" inm="btnApprove"></p>
        </a>
    </div>
</div>

<script type="text/javascript">
    // 页面初始化
    $(function(){
        // 设定处理者信息
        ychips.wxio.CmnUtil.setProcessUserInfoByBase64($("input[name='hdnApproveAuthUserInfo']").val());
        // 初始化用户组件
        ychips.wxio.CmnUtil.setAuthUserComponents(
            $("select[name='selApproveAuthUser']"),
            $("select[name='selApproveAuthDept']"),
            $("#approveStampArea")
        );
	
        // 审批按钮按下
        $("#btnApprove").on("click", function() {
			// 点击直接触发toast效果，避免重复提交
			var $loadingToast = $('#loadingToast1');
			if ($loadingToast.css('display') != 'none') return;
        	$loadingToast.fadeIn(100);

            // 用户代码
            var usercd = $("input[name='hdnApproveExecutorCd']").val();

            // TenantId
            var tenantid = $("input[name='hdnApproveTenantId']").val();

            // 权限者
            var aucd = $("select[name='selApproveAuthUser']").val();

            // 执行者
            var eucd = $("input[name='hdnApproveExecutorCd']").val();

            // 部门代码
            var dcd = $("select[name='selApproveAuthDept']").val();

            // 系统案件号
            var smid = $("input[name='hdnApproveSystemMatterId']").val();

            // 用户数据号
            var udid = $("input[name='hdnApproveUserDataId']").val();

            // 节点编码
            var nodeid = $("input[name='hdnApproveNodeId']").val();
			
			// 工作流ID
			var flowid = $("input[name='hdnApproveFlowId']").val();

            // 备注
            var comment = $("textarea[name='tarApproveComment']").val();

            // 服务器类型
            var sf = $("input[name='hdnSf']").val();
			
			// 财务支付标记
			var accountingFlag = $("input[name='accountingFlag']").val();
			
			if (!accountingFlag) {
				accountingFlag = '0';
			}
			// 印章ID
            var stampId = "";

            // 印章
            $("#approveStampArea").find("span[data-type='stamp']").each(function() {
                if ($(this).attr("selected")
                    && $(this).attr("selected") == "1") {
                    // 获得选中的印章
                    stampId = $(this).data("imwstampid");
                }
            });

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
                "flowid": flowid,
                "accountingFlag": accountingFlag,
                "comment": comment,
                "sf": sf,
                "data": JSON.stringify({})
            };

            // 如果有印章
            if (stampId) {
                postData.data = JSON.stringify({"imwStampId" : stampId});
				// 无印章 不进行Ajax传输，防止传输成功，跳转进入成功页面
	            $.ajax({
	                type: 'POST',
	                url: '${CONTEXT_PATH}/wx/view/cmn/wkf/process/approve',
					data: postData,
	                dataType: 'json',
	                success: function(data) {
	                    if(data && !data.error) {
	                        ychips.wxio.CmnUtil.toSuccessPage();
	                    } else {
							if (data && data.finEmptyFlag) {
								// 错误页面出现，Toast消失in 0.1s
	                    		setTimeout(function () {
	            					$loadingToast.fadeOut(100);
	        					});
								ychips.wxio.CmnUtil.toErrorPage(ychips.wxio.CmnUtil.getMessage("common.workflow.process.approve", "financialErrMsg"));
							} else {
								// 错误页面出现，Toast消失in 0.1s
	                    		setTimeout(function () {
	            					$loadingToast.fadeOut(100);
	        					});
	                        	ychips.wxio.CmnUtil.toErrorPage(data && data.error_msg ? data.error_msg : "");
							}
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
			} else {
                // 错误页面出现，Toast消失in 0.1s
                setTimeout(function () {
                    $loadingToast.fadeOut(100);
                });
				// 跳转设置印章提醒页面
				ychips.wxio.CmnUtil.toErrorPage(ychips.wxio.CmnUtil.getMessage("common.workflow.process.approve", "stampMsg"));
			}
        });
    });
</script>
</script>