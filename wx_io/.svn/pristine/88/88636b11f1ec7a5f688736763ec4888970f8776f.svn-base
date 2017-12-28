<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%>
<body>
	
</body>
<script
	src="${RESOURCE_PATH}/js/i18n/wx/common/workflow/unit/loading.js"></script>
<script>

 function loadingToast(){
	console.log("loadingToast中");
	var msg = '......wait'
    var loadingToast;
    loadingToast = '\
       <div id="loadingToast" class="weui_loading_toast" style="display:none;">\
         <div class="weui_mask_transparent"></div>\
         <div class="weui_toast">\
             <div class="weui_loading">\
                 <div class="weui_loading_leaf weui_loading_leaf_0"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_1"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_2"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_3"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_4"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_5"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_6"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_7"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_8"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_9"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_10"></div>\
                 <div class="weui_loading_leaf weui_loading_leaf_11"></div>\
             </div>\
             <p class="weui_toast_content" ins="common.workflow.process.approve" inm="matterProcessing"></p>\
          </div>\
       </div>\
    ';
    if($('#loadingToast').length){
       $('body').append(loadingToast);
    }else{
       $('#loadingToast.weui_toast_content').html(msg);
    }
    $('#loadingToast').fadeIn('fast');
 }
</script>