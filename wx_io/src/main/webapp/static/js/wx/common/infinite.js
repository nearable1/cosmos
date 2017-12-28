/**
 * 滚动加载专用方法
 * @param distance 离底部像素值
 * @param func 执行方法
 */
$.fn.infinite = function(distance, func) {
	// 离底部的像素
	distance = distance || 50;

	// 添加事件
	$(this).on("scroll", function() {
		// 位置
		var offset = $(this).get(0).scrollHeight - ($(window).height() + $(this).scrollTop());

		// 达到底部
        if (offset <= distance && !$(this).data("loading")) {

        	// 载入更多的消息
        	var msg =
        		ychips.wxio.CmnUtil.getMessage(
        				"common.workflow.cmnMsg", "msgLoadMore");

        	// 载入更多的标签
        	var loadMoreDiv = $("<div>").addClass("weui-loadmore")
        		.append($("<i>").addClass("weui-loading"))
        		.append($("<span>").addClass("weui-loadmore__tips").html(msg));

        	$(this).append(loadMoreDiv);

        	// 设定正在loading的标志位
        	$(this).data("loading", "true");

        	// 调用方法
        	func();
        }
	});
};

/**
 * 取消Loading的标签
 */
$.fn.clearInfinite = function() {
	// 清空标志位
	$(this).data("loading", "");

	// 移除标签
	$(this).find(".weui-loadmore").remove();
}

/**
 * 取消Loading的标签
 */
$.fn.showNoDataItem = function() {
	// 无数据
	var noDataDiv = $("<div>").addClass("weui-loadmore weui-loadmore_line weui-loadmore_dot")
		.append($("<span>").addClass("weui-loadmore__tips").css("background-color", "#F8F8F8"));

	$(this).append(noDataDiv);
}

/**
 * 取消Loading的标签
 */
$.fn.clearNoDataItem = function() {
	// 移除标签
	$(this).find(".weui-loadmore_dot").remove();
}
