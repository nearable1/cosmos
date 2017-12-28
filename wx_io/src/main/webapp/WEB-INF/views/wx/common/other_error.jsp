<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title></title>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.css"/>
        <link rel="stylesheet" href="${RESOURCE_PATH}/css/weui.io.css"/>
        <script src="${RESOURCE_PATH}/js/common/definer.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/zepto.min.js"></script>
        <script src="${RESOURCE_PATH}/js/wx/common/cmnUtil.js${JS_SUFFIX}"></script>
        <script src="${RESOURCE_PATH}/js/common/weui.io.js${JS_SUFFIX}"></script>
    </head>
    <body ontouchstart>
        <div class="container" id="container"></div>
        <script type="text/html" id="tpl_home">
            <div class="page">
                <div class="weui-msg">
                    <div class="weui-msg__icon-area">
                        <i class="weui-icon-info weui-icon_msg"></i>
                    </div>
                    <div class="weui-msg__text-area">
                        <h4 class="weui_msg_title">请确认是否从微信访问，且为企业号用户。</h4>
                    </div>
                </div>
            </div>
        </script>
    </body>
</html>