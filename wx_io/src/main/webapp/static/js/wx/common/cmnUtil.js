/**
 * 微信IO项目共通操作类
 */
definer('wxio.CmnUtil', {
    /**
     * 设定命名空间
     */
    'setNameSpaces': function(nss) {
        window.ychips.ns = nss;
    },

    /**
     * 设置当前区域
     */
    'setLocale': function(locale) {
        if (locale) {
            window.ychips.locale = locale;
        } else {
            window.ychips.locale = "zh_CN";
        }
    },

    /**
     * 迁移到错误页面
     */
    'toErrorPage': function(message) {
    	// 页面参数
    	var params = {
    		extraMsg: {"#processErrorMsg": message}
    	};

    	window.pageManager.go('page_error', params);
    },

    /**
     * 迁移到成功页面
     */
    'toSuccessPage': function() {
    	window.pageManager.go('page_success');
    },

    /**
     * 向需要Message的标签添加消息
     * @param ins 命名空间
     * @param inm 消息ID 
     */
    'getMessage': function(ins, inm) {
    	// 返回值
    	var result = "";

        // 命名空间
        var msgPath =
        	"i18n.wx." + ins + "." + window.ychips.locale + "." + inm;

        // 生成层级
        var path = msgPath.split(".");

        // 临时
        var temp = window.ychips;

        // 递归路径
        for (var i = 0; i < path.length; i++) {
            // 取得当前对象
            if (temp[path[i]]) {
                temp = temp[path[i]];
            } else {
                break;
            }

            // 如果是末端则赋值
            if (i == path.length - 1) {
            	result = temp;
            }
        }

        // 返回
        return result;
    },

    /**
     * 向需要Message的标签添加消息
     * @param dom 页面DOM对象
     * @param extraMsg 标签ID和消息的键值对 
     */
    'fillMessage': function(dom, extraMsg) {
        // 国际化前缀
        var i18nPrefix = "i18n.wx.";

        // 遍历所有带有消息特征的元素
        dom.find("[ins]").each(function() {
            // 命名空间
            var msgPath =
                i18nPrefix + $(this).attr("ins") + "." + window.ychips.locale + "." + $(this).attr("inm");

            // 生成层级
            var path = msgPath.split(".");

            // 临时
            var temp = window.ychips;

            // 递归路径
            for (var i = 0; i < path.length; i++) {
                // 取得当前对象
                if (temp[path[i]]) {
                    temp = temp[path[i]];
                } else {
                    break;
                }
                // 如果是末端则赋值
                if (i == path.length - 1) {
                    $(this).html($(this).html() + temp);
                }
            }
        });

        // 其他信息
        if (extraMsg) {
        	for (var i in extraMsg) {
        		dom.find(i).text(extraMsg[i]);
        	}
        }
    },

    /**
     * 页面按钮动作初始化
     */
    'initAtcionSheet': function(pageMask, actionSheet, btnProcess) {
        // 灰色层点击
        pageMask.on('click', function() {
            actionSheet.removeClass('weui-actionsheet_toggle');
            pageMask.fadeOut(200);
        });

        // 取消按钮
        actionSheet.find('#btnCancel').on('click', function() {
            actionSheet.removeClass('weui-actionsheet_toggle');
            pageMask.fadeOut(200);
        });

        // 向共同页面传递参数
        var pageParams = {};
        pageParams.userParams = {};

        // 遍历隐藏域
        actionSheet.find("input").each(function() {
        	pageParams.userParams[$(this).attr("name")] = $(this).val();
        });

        // 处理按钮按下
        btnProcess.on("click", function(){
            actionSheet.addClass('weui-actionsheet_toggle');
            pageMask.fadeIn(200);
        });

        // 按钮处理点击
        actionSheet.find('.js_item').on('click', function(){
        	
            // 按钮ID
            var id = $(this).data('id');

            // 迁移到相应的页面
            window.pageManager.go(id, pageParams);
        });
    },

    /**
     * 页面履历按钮初始化
     */
    'initHistory': function(bthHistory) {
    	// 履历按钮事件
    	bthHistory.on("click", function() {
            // 迁移到相应的页面
            window.pageManager.go("history");
    	});
    },
    
    /**
     * 根据Base64字符串获取处理用户对象
     */
    'setProcessUserInfoByBase64': function(base64Str) {
        var result = [];
        // 如果存在字符串
        if (base64Str) {
            // 转换JSONString
            var jsonStr = decodeURIComponent(escape(window.atob(base64Str)));

            // 转换Object
            result = JSON.parse(jsonStr);
        }

        // 设定
        definer('wxio.common.workflow.process.processAuthUserInfo', result)
    },

    /**
     * 获取处理用户信息
     */
    'getProcessUserInfoByBase64': function() {
        // 返回数据
        return ychips.wxio.common.workflow.process.processAuthUserInfo;
    },

    /**
     * 处理者控件生成
     */
    'setAuthUserComponents': function(selAuthUser, selAuthDept, stamp) {
        var _this = this;

        // 处理者信息
        var authUserInfo = ychips.wxio.common.workflow.process.processAuthUserInfo;

        // 如果存在
        if (authUserInfo && authUserInfo.length > 0) {
            // 遍历用户
            for (var i in authUserInfo) {
                // 如果存在用户
                if (authUserInfo[i].authUserCode) {
                    selAuthUser.append($("<option>").val(authUserInfo[i].authUserCode).text(authUserInfo[i].authUserName));
                }
            }

            // 审批人变更
            selAuthUser.on("change", function() {
                // 获得审批部门一览
                var userDeptMap = {};

                // 电子印章数据
                var stampList = {};

                // 遍历用户一览
                for (var i in authUserInfo) {
                    // 如果存在用户
                    if (authUserInfo[i].authUserCode == $(this).val()) {
                        // 用户对应部门
                        userDeptMap = authUserInfo[i].orgzList;

                        // 电子印章
                        stampList = authUserInfo[i].stampInfo;

                        break;
                    }
                }

                // 如果有部门下拉框
                if (selAuthDept && selAuthDept.length > 0) {
                    // 清空部门List
                    selAuthDept.empty();

                    // 遍历部门
                    if (userDeptMap) {
                        for (var j in userDeptMap) {
                            // 添加部门元素
                            selAuthDept.append(
                                    $("<option>").val(j).text(userDeptMap[j]));
                        }
                    }
                }

                // 遍历电子印章
                if (stampList && stamp && stamp.length > 0) {
                    // 电子印章对象
                    var stampTag = {};

                    for (var k in stampList) {
                        // 电子印章对象
                        stampTag  = $(
                                '<canvas class="canvas" width="100" height="100" style="margin-left: 0px;">',
                                {'id': stampList[k].stampId, 'isDefault': stampList[k].defaultFlag});

                        // 印章点击
                        stampTag.on("click", function() {
                            // 所有span背景色清空
                        	stamp.find("span[data-type='stamp']").css("background-color", "");
                        	stamp.find("span[data-type='stamp']").attr("selected", "0");

                        	// 当前背景色
                        	$(this).closest("span[data-type='stamp']").css("background-color", "#DDD");

                        	// 设置选中印章
                        	$(this).closest("span[data-type='stamp']").attr("selected", "1");
                        	
                        });

                        // 画印章
                        _this.drawStamp(stampList[k], stampTag[0]);

                        // 如果是默认印章
                        if (stampList[k].defaultFlag && stampList[k].defaultFlag == "1") {
                            // 添加画板组件
                            stamp.append($('<span data-type="stamp" data-imwStampId="' + stampList[k].stampId + '" selected="1" style="border-radius:50px;with:100px;height:100px;margin-left:10px;float:left;background-color:#DDD">').append(stampTag));
                        } else {
                            // 添加画板组件
                            stamp.append($('<span data-type="stamp" data-imwStampId="' + stampList[k].stampId + '" selected="0" style="border-radius:50px;with:100px;height:100px;margin-left:10px;float:left;">').append(stampTag));
                        }

                    }
                }
            });

            // 触发处理者变更
            selAuthUser.trigger("change");
        }
    },

    /**
     * 画印章
     */
    'drawStamp': function(stampInfo, cvs) {
    	var _this = this;

        // 获得canvas上下文
        var context = cvs.getContext('2d');

        // 绘制印章边框   
        var width = cvs.width / 2;
        var height = cvs.height / 2;

        context.lineWidth = 4;
        context.strokeStyle = "#FF0000";
        context.beginPath();
        context.arc(width, height, 46, 0, Math.PI * 2);//宽、高、半径
        context.stroke();

        // 三段内容取得
        var str1 = "";
        var str2 = "";
        var str3 = "";

        // 第一段判断是否是日期
        if (stampInfo.stampStr1Type == "1") {
        	str1 = _this.formatDate(new Date(), stampInfo.stampStr1);
        } else {
        	str1 = stampInfo.stampStr1;
        }

        // 三段式
        if (stampInfo.stampType == "1") {

            // 第二段判断是否是日期
            if (stampInfo.stampStr2Type == "1") {
            	str2 = _this.formatDate(new Date(), stampInfo.stampStr2);
            } else {
            	str2 = stampInfo.stampStr2;
            }

            // 第三段判断是否是日期
            if (stampInfo.stampStr3Type == "1") {
            	str3 = _this.formatDate(new Date(), stampInfo.stampStr3);
            } else {
            	str3 = stampInfo.stampStr3;
            }

            //上横线
            context.moveTo(5, cvs.width / 3);
            context.lineTo(93, cvs.width / 3);
            context.stroke();

            //下横线
            context.moveTo(5, cvs.width / 3 *2);
            context.lineTo(93, cvs.width / 3 *2);
            context.stroke();

            // 绘制印章名称   
            context.font = '15px sans-serif';

            //设置文本的垂直对齐方式
            context.textBaseline = 'middle';

            //设置文本的水平对对齐方式
            context.textAlign = 'center';
            context.lineWidth = 1;
            context.strokeStyle = '#FF0000';

            // 判断是否为日期
            context.strokeText(str3, width, height + 30);
            context.strokeText(str1, width, height - 30);
            context.strokeText(str2, width, height);
        } else {
            // 按照文字数竖显示
        	if (str1) {

                //设置文本的垂直对齐方式
                context.textBaseline = 'middle';

                //设置文本的水平对对齐方式
                context.textAlign = 'center';

                // 线宽
                context.lineWidth = 1;

                // 颜色
                context.strokeStyle = '#FF0000';

            	// 临时字符串
            	var strTemp = "";

            	// 字体大小数值
            	var fontSize = 15;

            	// 当前高度
            	var fontHeight = 0;

        		// 设置字体大小
        		if (str1.length <= 3) {
        			fontSize = 60 / str1.length;
        		}

        		fontHeight = parseInt(fontSize > 50 ? fontSize - 10 : fontSize + 5);

        		// 设置字体大小
        		context.font = fontSize + "px sans-serif";

        		// 遍历文字
        		for (var i = 0; i < str1.length; i ++) {
        			// 单个文字
        			strTemp = str1.substring(i, i + 1);

                    // 判断是否为日期
                    context.strokeText(strTemp, width, fontHeight * (i + 1));
        		}
        	}
        }
    },

    /**
     * 格式化日期
     */
    'formatDate': function(dt, fmt) {
        // 返回值
        var result = "";

        // 格式
        var o = {
                "M+": dt.getMonth() + 1, //月份 
                "d+": dt.getDate(), //日 
                "h+": dt.getHours(), //小时 
                "m+": dt.getMinutes(), //分 
                "s+": dt.getSeconds(), //秒 
                "q+": Math.floor((dt.getMonth() + 3) / 3), //季度 
                "S": dt.getMilliseconds() //毫秒 
            };

        	// 年的位数
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (dt.getFullYear() + "").substr(4 - RegExp.$1.length));

            // 其他格式
            for (var k in o)
            	if (new RegExp("(" + k + ")").test(fmt))
            		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    },
});