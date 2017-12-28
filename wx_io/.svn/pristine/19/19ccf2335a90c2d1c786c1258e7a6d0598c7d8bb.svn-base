definer('wxio.workflow.user.unprocessList', {
	/**
	 * 翻页
	 * @param params 请求参数
	 * @param container 容器
	 */
	'paging': function(url, container) {
		// 提交数据
		var postData = {};

		// 起始行
		postData.offset = parseInt(container.find("input[name='pageOffset']").val());

		// 获取件数
		postData.count = parseInt(container.find("input[name='pageRows']").val());

		// TenantID
		postData.tenantid = container.find("input[name='processTenantId']").val();

		// 用户代码
		postData.usercd = container.find("input[name='processUserCd']").val();

		// 总件数
		var total = parseInt(container.find("input[name='pageTotal']").val());

		// 服务器标签
		postData.sf = container.find("input[name='sf']").val();

		// 是否是测试
		postData.nc_token = container.find("input[name='nc_token']").val();

		// 如果到底
		if (postData.offset < total) {

	        $.ajax({
	            type: 'POST',
	            url: url,
	            data: postData,
	            dataType: 'json',
	            success: function(data) {
	            	
	            	// 取消加载更多
	            	container.clearInfinite();
	
	            	// 如果无错
	                if(data && !data.error) {
	                	// 添加行
	                	if (data.unprocessNodeList && data.unprocessNodeList.length > 0) {
	                		// 遍历
	                		for (var i in data.unprocessNodeList) {
	
	                    		// 内容项目
	                    		var contentTag = $('<span style="vertical-align: middle">').text(
	                    				data.unprocessNodeList[i].matterNumber + "-"
	                    				+ data.unprocessNodeList[i].matterName + "-"
	                    				+ data.unprocessNodeList[i].applyDate + "-"
	                    				+ data.unprocessNodeList[i].applyAuthUserName + "-"
	                    				);
	
	                    		// 单个项目
	                    		var itemTag = $('<div class="weui-cell weui-cell_access">')
	                    			.append($('<div class="weui-cell__bd">').append(contentTag))
	                    			.append($('<div class="weui-cell__ft">'));
	
	                    		container.append(itemTag);
	                		}
	
	                		// 页面状态设定
	                		// 起始位置重置
	                		container.find("input[name='pageOffset']").val(postData.offset + data.count);
	
	                		// 总件数重置
	                		container.find("input[name='pageTotal']").val(data.total);

	                		// 如果无数据
	                		if (data.total == 0) {
	                			container.closest("div.weui-tab__panel").showNoDataItem();
	                		} else {
	                			container.closest("div.weui-tab__panel").clearNoDataItem();
	                		}


	                		// 取消加载更多
	                		container.closest("div.weui-tab__panel").clearInfinite();
	                	}
	                }
	            },
	            error: function(XMLHttpRequest, textStatus, errorThrown) {
	            	// 取消加载更多
	            	container.closest("div.weui-tab__panel").clearInfinite();

	    			// 取消无数据
	    			container.closest("div.weui-tab__panel").clearNoDataItem();
	            }
	        });
		} else {
        	// 取消加载更多
			container.closest("div.weui-tab__panel").clearInfinite();

			// 取消无数据
			container.closest("div.weui-tab__panel").clearNoDataItem();
		}
	}
});