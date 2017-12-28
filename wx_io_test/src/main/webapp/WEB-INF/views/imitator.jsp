<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/taglibs.jsp"%> 
<html>
    <head>
        <meta charset="UTF-8">
        <script src="${RESOURCE_PATH}/js/common/jquery-2.1.0.min.js${JS_SUFFIX}"></script>
        <script type="text/javascript">
	        // domain
	        var domain = "http://127.0.0.1:8080";
	        $(function(){
	        	$("input[pgtype='apv']").click(function() {
	        		// domain
	        		var prefix = "/wx_io/wx/view/";
	        		var url = domain + prefix;
	        		url += ($("select[name='tenantid']").val() + "/yexp/");
	        		url += ($(this).attr("id") + "/approveview?");

	        	    // 参数
	        		url += ("sf=" + $("select[name='sf']").val())
                    url += ("&tenantid=" + $("select[name='tenantid']").val())
                    url += ("&usercd=" + $("input[name='usercd']").val())
                    url += ("&smid=" + $("input[name='smid']").val())
                    url += ("&udid=" + $("input[name='udid']").val())
                    url += ("&nodeid=" + $("input[name='nodeid']").val())
                    url += ("&nc_token=" + $("input[name='nc_token']").val())
                    window.open(url, "_blank")
	        	});

	        	$("select[name='tenantid']").change(function() {
	        		releaseAllApvButton();
	        		disableApvButton();
	        	});

	            $("#unprocessList").click(function() {
                    // domain
                    var prefix = "/wx_io/wx/view/cmn/wkf/user/unprocesslist/index?";
                    var url = domain + prefix;
                    // 参数
                    url += ("sf=" + $("select[name='sf']").val())
                    url += ("&tenantid=" + $("select[name='tenantid']").val())
                    url += ("&usercd=" + $("input[name='usercd']").val())
                    url += ("&nc_token=" + $("input[name='nc_token']").val())
                    window.open(url, "_blank")
	            });

	        	// 按钮初始化
                releaseAllApvButton();
                disableApvButton();

                // 开始获取消息
                setInterval("getMessage()", 2000);
	        });

	        function getMessage() {
                $.ajax({
                    type: 'POST',
                    url: 'imitator/get_messages',
                    data: {},
                    dataType: 'json',
                    success: function(data) {
                    	if (data && data.length > 0) {
                    		$("#messagesArea").empty();

                    		var _now = new Date();

                    		// 遍历
                    		for (i in data) {
                    			var msgObj = $("<ol style='background-color:#EEE;margin-top:10px;'>");
                    			msgObj.append($("<li style='list-style-type: none;'>").text(data[i].touser));
                                msgObj.append($("<li style='list-style-type: none;'>").text(data[i].receive_time));
                    			if (data[i].msgtype == "text") {
                    				msgObj.append($("<li style='list-style-type: none;'>").text(data[i].text.content));
                    			}

                                if (data[i].msgtype == "news") {
                                	msgObj.append($("<li style='list-style-type: none;'>").text(data[i].news.articles[0].title));
                                	msgObj.append($("<li style='list-style-type: none;'>").text(data[i].news.articles[0].description));
                                	msgObj.append($("<li style='list-style-type: none;'>").append($("<a>").text("跳转").attr("href", data[i].news.articles[0].url+ "&usercd=" + data[i].touser + "&nc_token=" + $("input[name='nc_token']").val())));
                                }
                                $("#messagesArea").prepend(msgObj);
                    		}
                    	}
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                    }
                });
	        }

	        function releaseAllApvButton() {
	        	$("input[pgtype='apv']").removeAttr("disabled");
	        }

            function disableApvButton() {
            	if ($("select[name='tenantid']").val() == "ysw"
            			|| $("select[name='tenantid']").val() == "ysz") {
            		$("#exp0601").attr("disabled", "disabled");            		
            		$("#exp0901").attr("disabled", "disabled");
            		$("#swm007").attr("disabled", "disabled");
            		$("#swm008").attr("disabled", "disabled");
            	}
            }
        </script>
    </head>
    <body>
        <table width="100%" height="100%" border="1">
            <tr>
                <th>页面</th>
                <th>消息</th>
            </tr>
            <tr>
                <td width="50%" height="100%" valign="top">
                    <table>
                      <tr>
                            <td>Y-CHIPS域：</td>
                            <td>
                                <select name="sf">
                                    <option selected value=""></option>
                                    <option value="uat">uat</option>
                                    <option value="cloud">cloud</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>公司：</td>
                            <td>
                                <select name="tenantid">
                                    <option value="yci">yci</option>
                                    <option value="ysw">ysw</option>
                                    <option value="ysz">ysz</option>
                                    <option selected value="syz">syz</option>
                                    <option value="mdw">mdw</option>
                                    <option value="hkg">hkg</option>
                                    <option value="dyz">dyz</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>用户CD：</td>
                            <td>
                                <input type="text" name="usercd"></input>
                            </td>
                        </tr>
                        <tr>
                            <td>系统案件ID：</td>
                            <td>
                                <input type="text" name="smid"></input>
                            </td>
                        </tr>
                        <tr>
                            <td>用户数据ID：</td>
                            <td>
                                <input type="text" name="udid"></input>
                            </td>
                        </tr>
                        <tr>
                            <td>节点ID：</td>
                            <td>
                                <input type="text" name="nodeid"></input>
                            </td>
                        </tr>
                        <tr><td></td><td></td></tr>
                        <tr>
                            <td>出差申请：</td>
                            <td><input id="exp0301" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>出差报销：</td>
                            <td><input id="exp0401" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>交际费申请：</td>
                            <td><input id="exp0701" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>交际费报销：</td>
                            <td><input id="exp0801" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>其他经费报销：</td>
                            <td><input id="exp0501" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>市内交通费报销：</td>
                            <td><input id="exp0601" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>印章申请：</td>
                            <td><input id="exp0901" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>员工借款：</td>
                            <td><input id="exp1201" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>加班申请：</td>
                            <td><input id="swm007" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr>
                            <td>休假申请：</td>
                            <td><input id="swm008" pgtype="apv" type="button" style="width:200px;" value="迁移"></input></td>
                        </tr>
                        <tr><td></td><td></td></tr>
                        <tr><td colspan="2">
                            <input id="unprocessList" type="button" style="width:330px;" value="未处理一览">
                        </td></tr>
                    </table>
                </td>
                <td width=50% height=100%>
                    <div id="messagesArea" style="height:100%;overflow:scroll;"></div>
                </td>
            </tr>
        </table>
        <input type="hidden" name="nc_token" value="1f3b990a5a66f42349508d39d56f80d33f483abe9e191549e72a994f8b6e404f"></input>
    </body>
</html>