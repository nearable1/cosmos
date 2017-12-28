<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../common/taglib.jsp"%> 
<!DOCTYPE html>
<html>
  <head>
    <title>jQuery WeUI</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/jquery-weui.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/demos.css">
	
	
	<script src="${ctxStatic}/js/common/jquery-2.1.4.js"></script>
	<script src="${ctxStatic}/js/common/fastclick.js"></script>
	<script src="${ctxStatic}/js/common/jquery-weui.js"></script>
	
	<script type="text/javascript">
	  $(function() {
	    FastClick.attach(document.body);
	    $("#panel").bind("click",function(){
		    var $content = $("#content_1");
		    if($content.is(":visible")){
				$content.hide();
				$("#panel").html("展开");
			}else{
				$content.show();
				$("#panel").html("收起");
			}
		});
	    $("#panel_1").bind("click",function(){
		    var $content = $("#content_2");
		    if($content.is(":visible")){
				$content.hide();
				$("#panel_1").html("展开");
			}else{
				$content.show();
				$("#panel_1").html("收起");
			}
		});
	    $("#panel_3").bind("click",function(){
		    var $content = $("#content_3");
		    if($content.is(":visible")){
				$content.hide();
				$("#panel_3").html("展开");
			}else{
				$content.show();
				$("#panel_3").html("收起");
			}
		});
	  });
	  
	</script>
</head>
  <body ontouchstart>
    <div class="weui-cells__title">申请者信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">申请者</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="金一">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">代理者</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">主所属</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="经营企划室">
        </div>
      </div>
    </div>
      <div class="weui-cells__title">基本信息</div>
    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="" class="weui-label">申请日</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="date" value="">
        </div>	
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">项目代码</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">项目名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">项目负责人</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="" class="weui-label">出差期间</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="date" value="">
        </div>至
        <div class="weui-cell__bd">
          <input class="weui-input" type="date" value="">
        </div>
      </div>	
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">出差申请案件名</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">国内国外区分</label></div>
        <div class="weui-cell weui-cell_select">
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">国内</option>
	            <option value="2">国外</option>
	          </select>
        	</div>
      	</div>
      </div>
       <div class="weui-cells weui-cells_form">
	     <div class="weui-cells__title">出差的内容和目的</div>
	      <div class="weui-cell">
	        <div class="weui-cell__bd">
	          <textarea class="weui-textarea" placeholder="请输入文本" rows="2"></textarea>
	          <div class="weui-textarea-counter"><span>0</span>/200</div>
	        </div>
	      </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">公司名称</label></div>
        <div class="weui-cell weui-cell_select">
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">apple公司</option>
	            <option value="2">IBM公司</option>
	          </select>
        	</div>
      	</div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">事业部</label></div>
        <div class="weui-cell weui-cell_select">
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">统售事业部</option>
	            <option value="2">IBM公司</option>
	          </select>
        	</div>
      	</div>
      </div>
       <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">部门</label></div>
        <div class="weui-cell weui-cell_select">
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">经营企划室</option>
	            <option value="2"></option>
	          </select>
        	</div>
      	</div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">成本中心</label></div>
        <div class="weui-cell weui-cell_select">
	        <div class="weui-cell__bd">
	          <select class="weui-select" name="select1">
	            <option selected="" value="1">经营企划（803423423）</option>
	            <option value="2"></option>
	          </select>
        	</div>
      	</div>
      </div>
      
    <div class="weui-cells__title">出差日程</div>
    <div class="weui-cells weui-cells_checkbox">
      <label class="weui-cell weui-check__label" for="s11">
        <div class="weui-cell__hd">
          <input type="checkbox" class="weui-check" name="checkbox1" id="s11" checked="checked">
          <i class="weui-icon-checked"></i>
        </div>
        <div class="weui-cell__bd">
          <p>有出差日程</p>
        </div>
      </label>
    </div>
    <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>2016/09-2017/01(天津)</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;" id="panel">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div id="content_1" style="display:none;">
	   	   <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">出差区分</label></div>
	        <div class="weui-cell weui-cell_select">
		        <div class="weui-cell__bd">
		          <select class="weui-select" name="select1">
		            <option selected="" value="1">北京</option>
		            <option value="2"></option>
		          </select>
	        	</div>
	      	</div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">距离</label></div>
	        <div class="weui-cell weui-cell_select">
		        <div class="weui-cell__bd">
		          <select class="weui-select" name="select1">
		            <option selected="" value="1">200Km以外</option>
		            <option value="2">0-200Km</option>
		          </select>
	        	</div>
	      	</div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">出差地</label></div>
	        <div class="weui-cell weui-cell_select">
		        <div class="weui-cell__bd">
		          <select class="weui-select" name="select1">
		            <option selected="" value="1">大连</option>
		            <option value="2">青岛</option>
		          </select>
	        	</div>
	      	</div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">开始日</label></div>
	        <div class="weui-cell">
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="date" value="">
		        </div>	
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">结束日</label></div>
	        <div class="weui-cell">
		        <div class="weui-cell__bd">
		          <input class="weui-input" type="date" value="">
		        </div>	
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">天数</label></div>
	        <div class="weui-cell">
	         	<div class="weui-cell__bd">
	         	  <input class="weui-input" type="" pattern="" placeholder="">
	        	</div>
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">补贴天数</label></div>
	        <div class="weui-cell">
	         	<div class="weui-cell__bd">
	         	  <input class="weui-input" type="" pattern="" placeholder="">
	        	</div>
	        </div>
	      </div>
     </div>
     
     <div class="weui-cells__title">交通费报销</div>
     <div class="weui-cells weui-cells_checkbox">
      <label class="weui-cell weui-check__label" for="s12">
        <div class="weui-cell__hd">
          <input type="checkbox" class="weui-check" name="checkbox1" id="s12" checked="checked">
          <i class="weui-icon-checked"></i>
        </div>
        <div class="weui-cell__bd">
          <p>有交通费报销</p>
        </div>
      </label>
     </div>
     <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>报销一</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div class="weui-cells__title">住宿报销</div>
     <div class="weui-cells weui-cells_checkbox">
      <label class="weui-cell weui-check__label" for="s13">
        <div class="weui-cell__hd">
          <input type="checkbox" class="weui-check" name="checkbox1" id="s13" checked="checked">
          <i class="weui-icon-checked"></i>
        </div>
        <div class="weui-cell__bd">
          <p>有住宿报销</p>
        </div>
      </label>
     </div>
     <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>报销一</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div class="weui-cells__title">出差其他报销</div>
     <div class="weui-cells weui-cells_checkbox" id="panel">
      <label class="weui-cell weui-check__label" for="s14">
        <div class="weui-cell__hd">
          <input type="checkbox" class="weui-check" name="checkbox1" id="s14" checked="checked">
          <i class="weui-icon-checked"></i>
        </div>
        <div class="weui-cell__bd">
          <p>有其他费用报销</p>
        </div>
      </label>
     </div>
     <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>报销一</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div class="weui-cells__title">补贴</div>
     <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>美元</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;" id="panel_1">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div id="content_2" style="display:none;">
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">项目</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="日补贴">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">小计</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="800.00">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">币种</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="美元">
	        </div>
	      </div>
	       <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">参考汇率</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="1.0000">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">小计(元)</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="800.00">
	        </div>
	      </div>
     </div>
     <div class="weui-cell">
          <div class="weui-cell__hd"><img src="${ctxStatic}/img/icon.png" alt="" style="width:20px;margin-right:5px;display:block"></div>
          <div class="weui-cell__bd">
            <p>人民币</p>
          </div>
          <a class="weui-cell weui-cell_access" href="javascript:;">
           <div class="weui-cell__bd">
             <p style="color: #999;" id="panel_3">展开</p>
           </div>
           <div class="weui-cell__ft"></div>
          </a>
     </div>
     <div id="content_3" style="display:none;">
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">项目</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="日补贴">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">小计</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="800.00">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">币种</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="人民币">
	        </div>
	      </div>
	       <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">参考汇率</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="1.0000">
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">小计(元)</label></div>
	        <div class="weui-cell__bd">
	          <input class="weui-input" type="" pattern="" placeholder="800.00">
	        </div>
	      </div>
     </div>
     
     <div class="weui-cells__title">出差报销合计</div>
	 <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">借款冲销金额</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div>   
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">报销支付金额</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="number" pattern="" placeholder="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">公司信用卡</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="" pattern="" placeholder="">
        </div>
      </div> 
      <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">本次出差总费用</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="number" pattern="" placeholder="">
        </div>
      </div>      
   </div>
    <div class="weui-btn-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="showTooltips">确定</a>
    </div>
    <script>
      $("#showTooltips").click(function() {
        var tel = $('#tel').val();
        var code = $('#code').val();
        if(!tel || !/1[3|4|5|7|8]\d{9}/.test(tel)) $.toptip('请输入手机号');
        else if(!code || !/\d{6}/.test(code)) $.toptip('请输入六位手机验证码');
        else $.toptip('提交成功', 'success');
      });
    </script>
  </body>
</html>
