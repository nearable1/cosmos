<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>input页面</title>
    <link rel="stylesheet" href="${ctxStatic}/css/weui.css"/>
	<link rel="stylesheet" href="${ctxStatic}/css/example.css"/>
</head>
<body ontouchstart>

  <div class="container" id="container"></div>

<script type="text/html" id="tpl_home">
<div class="page">
  	<div class="page__hd">
        <h1 class="page__title">
            <img src="./images/logo.png" alt="" height="21px" />
        </h1>
        <p class="page__desc">WeUI 这是一个form界面</p>
  	</div>
    <div class="page__bd page__bd_spacing">
        <ul>
            <li>
                <div class="weui-flex js_category">
                    <p class="weui-flex__item">表单</p>
                    <img src="" alt="">
                </div>
                <div class="page__category js_categoryInner">
                    <div class="weui-cells page__category-content">
                        <a class="weui-cell weui-cell_access js_item" data-id="button" href="javascript:;">
                            <div class="weui-cell__bd">
                                <p>Button</p>
                            </div>
                            <div class="weui-cell__ft"></div>
                        </a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        var winH = $(window).height();
        var categorySpace = 10;

		//点击button按钮，实现页面自跳转
        $('.js_item').on('click', function(){
            var id = $(this).data('id');
            window.pageManager.go(id);
        });
		//点击home界面的表单
        $('.js_category').on('click', function(){
            var $this = $(this),
                $inner = $this.next('.js_categoryInner'),
                $page = $this.parents('.page'),
                $parent = $(this).parent('li');
            var innerH = $inner.data('height');
            bear = $page;

            if(!innerH){
                $inner.css('height', 'auto');
                innerH = $inner.height();
                $inner.removeAttr('style');
                $inner.data('height', innerH);
            }
			//父类列表是否展开
            if($parent.hasClass('js_show')){
                $parent.removeClass('js_show');
            }else{
				//兄弟列表不显示
                $parent.siblings().removeClass('js_show');

                $parent.addClass('js_show');
                if(this.offsetTop + this.offsetHeight + innerH > $page.scrollTop() + winH){
                    var scrollTop = this.offsetTop + this.offsetHeight + innerH - winH + categorySpace;

                    if(scrollTop > this.offsetTop){
                        scrollTop = this.offsetTop - categorySpace;
                    }

                    $page.scrollTop(scrollTop);
                }
            }
        });
    });
</script>
</script>
<script type="text/html" id="tpl_button">
<div class="page">
    <div class="page__hd">
        <h1 class="page__title">Button</h1>
        <p class="page__desc">按钮</p>
    </div>
    <div class="page__bd page__bd_spacing">
        <a href="javascript:;" class="weui-btn weui-btn_primary">页面主操作 Normal</a>
		<a href="javascript:;" class="weui-btn weui-btn_primary weui-btn_loading"><i class="weui-loading"></i>页面主操作 Loading</a>
        <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_primary">页面主操作 Disabled</a>
        <a href="javascript:;" class="weui-btn weui-btn_default">页面次要操作 Normal</a>
		<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_loading"><i class="weui-loading"></i>页面次操作 Loading</a>
        <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_default">页面次要操作 Disabled</a>
        <a href="javascript:;" class="weui-btn weui-btn_warn">警告类操作 Normal</a>
		<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_loading"><i class="weui-loading"></i>警告类操作 Loading</a>
        <a href="javascript:;" class="weui-btn weui-btn_disabled weui-btn_warn">警告类操作 Disabled</a>
        
        <div class="button-sp-area">
            <a href="javascript:;" class="weui-btn weui-btn_plain-default">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_plain-default weui-btn_plain-disabled">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_plain-primary">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_plain-primary weui-btn_plain-disabled">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_primary">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_default">按钮</a>
            <a href="javascript:;" class="weui-btn weui-btn_mini weui-btn_warn">按钮</a>
        </div>
    </div>
	//返回home菜单
    <div class="page__ft">
        <a href="javascript:home()"><img src="" /></a>
    </div>
</div>
</script>
</script>
    <script src="${ctxStatic}/js/common/zepto.min.js"></script>
    <script src="${ctxStatic}/js/common/example.js"></script>
</body>
</html>
