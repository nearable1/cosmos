/*!
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * 
 * 通用公共方法
 * @author ThinkGem
 * @version 2014-4-29
 */
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select:not(.remote)").select2({allowClear: true});
		// 初始货物料号模糊查询下拉框
		$(".remote.material").select2(getMatSelectOption());
		// 客户模糊查询下拉框
		$(".remote.customer").select2(getCustSelectOption());
		// 员工模糊查询下拉框
		$(".remote.employee").select2(getEmplSelectOption());
	}catch(e){
		// blank
	}

	// ajax锁屏
	$(document).ajaxSend(function(event, xhr, options) {
		if ($("#mainFrame").length > 0) {
			return;
		}
		if (options.type.toLowerCase() == "post") {
			resetTip();
			loading();
			$.blockUI(getBlockOption());
		}
	});

	// ajax解除锁屏
	$(document).ajaxComplete(function(event, xhr, options) {
		if ($("#mainFrame").length > 0) {
			return;
		}
		if (options.type.toLowerCase() == "post") {
			closeLoading();
			$.unblockUI();
		}
	});
	
	$("[type='submit'][class='btn btn-default']").on("click", function() {
		if ($(this).val() == "退回") {
			var r=confirm("确认要退回给申请者？请确认！");
			if (r != true) {
				return false;
			}
			return true;
		}
		return true;
	});

	// post submit锁屏
	$("form[method='post']").submit(function(e) {
		if ($("#mainFrame").length > 0) {
			return;
		}

		var validator = $(this).validate();
		if (validator.form()) {
			submitLockView();
		}
	});

	// ajax错误消息处理
	$(document).ajaxError(function(event, xhr, options, exc) {
		if ($("#mainFrame").length > 0) {
			return;
		}
		if (xhr.responseText == null 
			|| xhr.responseText.indexOf("<html>") != -1) {
			return;
		}
		if (xhr.status == "500" || xhr.status == "400" 
			|| xhr.status == "403" || xhr.status == "404") {
			var message = xhr.responseText;
			showError(xhr.status + ":" + message);
		} else {
			showError(xhr.status + ":发生未知错误。");
		}
	});

	// ajax成功消息处理
	$(document).ajaxSuccess(function(event, xhr, options, data) {
		if ($("#mainFrame").length > 0) {
			return;
		}
		if (data == null || data == "" || typeof(data) == "undefined") {
			return;
		}
		if (typeof(data.message) == "undefined") {
			var jsonDate = JSON.stringify(data);
			if (jsonDate.message == null || jsonDate.message == ""
				|| typeof(jsonDate.message) == "undefined") {
				return;
			}
			showMessage(jsonDate.message);
		} else {
			if (data.message == null || data.message == "") {
				return;
			}
			showMessage(data.message);
		}
	});

		// 合同行编辑画面地区负责人变更事件
	$(".employeeId").on("change", function() {
		
		if ($(this).val() == "" || $(this).val() == null) {
			$(".organize").val("");
        	$(".organize").trigger("change");
		} else {

			$.ajax({
		    	url: ctx + "/common/getOrganize",
		        type: "get",
		        async: false,
		        data: {"employeeId":$(this).val()},
		        dataType: "json",
		        success: function (data) {

		        	$(".organize").val(data.organize);
		        	$(".organize").trigger("change");
		        },
		        error: function (msg) {
		        }
		    });
		}
	});
});

// submit锁屏，post submit时调用此方法
function submitLockView() {
    $.blockUI(getBlockOption());
    loading();
}

// 函数：获取blockUI的默认选项
function getBlockOption() {
    var option = {
        message: '',
        overlayCSS: {
            backgroundColor: '#000',
            opacity: 0.0,
            cursor: 'wait'
        }
    };
    return option;
}

//显示消息
function showMessage(msg) {
	if (msg.indexOf("失败") != -1) {
		showError(msg);
	} else {
		showSuccess(msg);
	}
}

// 显示错误消息
function showError(msg) {
	var msgHtml =  '<div id="messageBox" class="alert alert-error hide">';
		msgHtml += '<button data-dismiss="alert" class="close">×</button>';
		msgHtml += msg;
		msgHtml += '</div>';
	if ($("#messageBox").length == 0) {
		$("body").append(msgHtml);
	} else {
		$("#messageBox").replaceWith(msgHtml);
	}
	showTip(msg, "error", 2000, 200)
	$("#messageBox").show();
}

// 显示成功消息
function showSuccess(msg) {
	var msgHtml =  '<div id="messageBox" class="alert alert-success hide">';
		msgHtml += '<button data-dismiss="alert" class="close">×</button>';
		msgHtml += msg;
		msgHtml += '</div>';
	if ($("#messageBox").length == 0) {
		$("body").append(msgHtml);
	} else {
		$("#messageBox").replaceWith(msgHtml);
	}
	showTip(msg, "success", 2000, 200)
	$("#messageBox").show();
}

//select2 物料号模糊查询option
var matSelectOpt = null;
function getMatSelectOption() {
    if (matSelectOpt != null) {
        return matSelectOpt;
    }
    matSelectOpt = {
        placeholder: " ",
        allowClear: true,
        ajax: {
            url: ctx + "/common/materials",
            contentType: 'charset=UTF-8',
            dataType: 'json',
            quietMillis: 500,
            data: function (params, pageNum) {
                var query = {};
                query.pageNum = pageNum;
                query.pageSize = 10;
                query.keyword = encodeURIComponent(params);
                query.type = $(this[0]).attr("data-type");
                return query;
            },
            results: function (data, pageNum) {
                return { results: data.materials, more: (pageNum * 10) < data.totalCount };
            },
            cache: true
        },
        escapeMarkup: function (markup) { return markup; }, 
        minimumInputLength: 1,
        formatResult: function (item) {
            var markup = '<div><div class="">' + item.id + '</div>' + 
                '<div class="select2-result-item-text">' + item.text + '</div></div>';
            return markup;
        }, 
        formatSelection: function (item) {
            var show = $(this.element[0]).attr("data-show");
            if (show == "id") {
                return item.id;
            } else if (show == "text") {
                return item.text;
            } else if (show == "model") {
                return item.model;
            } else {
                return item.id;
            }
        },
        initSelection: function (element, callback) {
            if (element.attr("data-show") == "text" || element.attr("data-show") == "model") {
                var id = $(element).val();
                if (id !== "") {
                    $.ajax(ctx + "/common/materials/" + id, {
                        dataType: "json"
                    }).done(function(data) { callback(data); });
                }
            } else {
                callback({id: element.val(), text: element.val()});//这里初始化
            }
        }
    };
    return matSelectOpt;
}

//select2 客户模糊查询option
var custSelectOpt = null;
function getCustSelectOption() {
	if (custSelectOpt != null) {
		return custSelectOpt;
	}
	custSelectOpt = {
		placeholder:" ",
		allowClear: true,
		ajax: {
		    url: ctx + "/common/customers",
		    contentType: 'charset=UTF-8',
		    dataType: 'json',
		    quietMillis: 500,
		    data: function (params) {
		    	var query = {};
		    	var type = $(this[0]).attr("data-type");
		    	query.keyword = encodeURIComponent(params);
		    	if (type != "" || type != null || typeof(type) != "undefined") {
		    		query.type = type; // 客户类型，代理商、经销商等
		    	}
		        return query;
		    },
		    results: function (data,params) {
		        return { results: data.customers};
		    },
			cache: true
		},
		escapeMarkup: function (markup) { return markup; }, 
		minimumInputLength: 1,
		formatResult: function (item) {
			var markup = '<div><div class="">' + item.id + '</div>' + 
	        '<div class="select2-result-item-text">' + item.text + '</div></div>';
			return markup;
		}, 
		formatSelection: function (item) {
			var show = $(this.element[0]).attr("data-show");
			if (show == "id") {
				return item.id;
			} else if (show == "text") {
				return item.text;
			} else {
				return item.id;
			}
		},
		initSelection: function (element, callback) {
			if (element.attr("data-show") == "text") {
				var id = $(element).val();
				if (id !== "") {
		            $.ajax(ctx + "/common/customers/" + id, {
	                    dataType: "json"
	                }).done(function(data) { callback(data); });
				}
	        } else {
				callback({id: element.val(), text: element.val()});//这里初始化
	        }
		}
	};
	return custSelectOpt;
}

//select2 员工模糊查询option
var emplSelectOpt = null;
function getEmplSelectOption() {
	if (emplSelectOpt != null) {
		return emplSelectOpt;
	}
	emplSelectOpt = {
		placeholder:" ",
		allowClear: true,
		ajax: {
		    url: ctx + "/common/employees",
		    contentType: 'charset=UTF-8',
		    dataType: 'json',
		    quietMillis: 500,
		    data: function (params) {
		    	var query = {};
		    	var type = $(this[0]).attr("data-type");
		    	var shiro = $(this[0]).attr("data-shiro");
		    	query.keyword = encodeURIComponent(params);
		    	if (type != "" || type != null || typeof(type) != "undefined") {
		    		query.type = type; // 员工类型，10：业务员、20：工程师、99：其他
		    	}
		    	if (shiro != "" || shiro != null || typeof(shiro) != "undefined") {
		    		query.shiro = shiro; // 有权限限制：1
		    	}
		        return query;
		    },
		    results: function (data,params) {
		        return { results: data.employees};
		    },
			cache: true
		},
		escapeMarkup: function (markup) { return markup; }, 
		minimumInputLength: 1,
		formatResult: function (item) {
			// var markup = '<div><div class="">' + item.id + '</div>' + 
			// '<div class="select2-result-item-text">' + item.text + '</div></div>';
			var markup = '<div><div class="select2-result-item-text">' + item.text + '</div></div>';
			return markup;
		}, 
		formatSelection: function (item) {
			var show = $(this.element[0]).attr("data-show");
			if (show == "id") {
				return item.id;
			} else if (show == "text") {
				return item.text;
			} else {
				return item.text;
			}
		},
		initSelection: function (element, callback) {
			if (element.attr("data-show") == "text") {
				var id = $(element).val();
				if (id !== "") {
		            $.ajax(ctx + "/common/employees/" + id, {
	                    dataType: "json"
	                }).done(function(data) { callback(data); });
				}
	        } else {
				callback({id: element.val(), text: element.val()});//这里初始化
	        }
		}
	};
	return emplSelectOpt;
}

// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	resetTip();
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

// 确认对话框
function confirmx(mess, href, closed){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			if (typeof href == 'function') {
				href();
			}else{
				resetTip(); //loading();
				location = href;
			}
		}
	},{buttonsFocus:1, closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}

// 提示输入对话框
function promptx(title, lable, href, closed){
	top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
	    if (f.txt == '') {
	        top.$.jBox.tip("请输入" + lable + "。", 'error');
	        return false;
	    }
		if (typeof href == 'function') {
			href();
		}else{
			resetTip(); //loading();
			location = href + encodeURIComponent(f.txt);
		}
	},closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

// 销售合同&报价单的价格取得逻辑
// 参数说明：
// priceSystem：价格体系
// customerId：客户ID
// materialNo：物料号
// region：地区
// industry：行业
function getStandardPrice(priceSystem, customerId, materialNo, region, industry){
	var standardPrice;
	$.ajax({
    	url: ctx + "/common/getStandardPrice",
        type: "get",
        async: false,
        data: {"priceSystem":priceSystem,
        		"customerId":customerId,
        		"materialNo":materialNo,
        		"region":region,
        		"industry":industry},
        dataType: "json",
        success: function (data) {
        	
        	standardPrice = data.standardPrice;
        },
        error: function (msg) {
        }
    });
	
	return standardPrice;
}

function toThousands(num) {
	if (num == null || num == '') {
		return '';
	}

    var num = (num || 0).toString(), result = '';

	var sign = '';
	if(num.indexOf("-") == 0 ){
		sign = '-';
	}
	
     num = num.trim().replace(/,/g, "");
     var reg = /[^0-9.]*/g;
     num = num.trim().replace(reg, "");
     var nums = num.split("."); 
     while (nums[0].length > 3) {
         result = ',' + nums[0].slice(-3) + result;
         nums[0] = nums[0].slice(0, nums[0].length - 3);
     }
     if (nums[0]) { result = nums[0] + result; }
     if (nums.length > 1) {
    	 result = result + "." + nums[1];
     } else {
    	 result = result + ".00";
     }
     return sign + result;
 }

function numToStr(obj) {
	var num = obj.value;
	
	obj.value = toThousands(num);
 }

function strToNum(obj) {
	var num = obj.value;
	if (num == null || num == '') {
		obj.value = '';
	} else {
		obj.value = num.trim().replace(/,/g, "");
	}
 }
