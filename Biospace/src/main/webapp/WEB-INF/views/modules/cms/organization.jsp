<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="blank"/>

<link href="${ctxStatic}/ztree/css/demo.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/ztree/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/ztree/js/jquery.ztree.all.min.js" type="text/javascript"></script>

<title>zTree test</title>
<style type="text/css">
	div#rMenu{position:absolute;visibility:hidden;top:0;background-color:#555;text-align:left;padding:2px;}
	div#rMenu ul li{
		margin: 1px 0;
		padding: 0 5px;
		cursor: pointer;
		list-style: none outside none;
		background-color: #DFDFDF;
	}
	#dataTree{
		height:100%;width:100%;
	}
	.diyTreeTableText{
		position:absolute;left:500px;
	}
	.diyTreeTableCb{
		position:absolute;left:900px;
	}
</style>
</head>
<body>
<div>
	<input type="button" value="全部展开" onclick="allNode();"/>
	<input type="button" value="全部关闭" onclick="closeNode();"/>
	<input type="button" value="刷新" onclick="refshNode();"/>
</div><br/>
<span id="title1">责任中心名称</span>
<span id="title2">责任中心编号</span>
<span id="title3">是否有效</span>
<ul id="dataTree" class="ztree"></ul>
<div id="rMenu">
	<ul>
		<li id="m_add" >右键菜单1</li>
		<li id="m_del" >右键菜单2</li>
		<li id="m_check" >右键菜单3</li>
		<li id="m_unCheck" >右键菜单4</li>
		<li id="m_reset" >右键菜单5</li>
	</ul>
</div>

<script type="text/javascript">
	var setting = {
		view : {
			selectedMulti: true,//是否能多选
			showIcon:true, //设置 zTree 是否显示节点的图标。
			fontCss: setFontCss, //字体设置
			addHoverDom: addHoverDom, //添加
			addDiyDom: addDiyDom,
			removeHoverDom:removeHoverDom //删除
		},
		/* check:{
			enable: true
		}, */
		data: {
			simpleData: {
			 	enble: true, //是否用Array
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0 
			}
		},
		edit:{
			enable:true,
			removeTitle:"remove",
			renameTitle:"rename",
			showRemoveBtn:true,//显示删除
			showRenameBtn:true,//显示编辑
			editNameSelectAll:true
		},
 		async:{
			enable: true,
			url: "${ctx}/cms/organization/getZtreeData", 
			autoParam: ["id"]
		}, 
		 callback: {
		 		beforeClick: beforeClick,//捕获单击节点之前的事件回调函数
		        beforeCollapse: beforeCollapse,//用于捕获父节点折叠之前的事件回调函数
		        beforeExpand: beforeExpand,//用于捕获父节点展开之前的事件回调函数
		        onCollapse: onCollapse,//用于捕获节点被折叠的事件回调函数
		        onExpand: onExpand,//用于捕获节点被展开的事件回调函数
		        beforeAsync : beforeAsync,
		        onAsyncSuccess: onAsyncSuccess, //异步成功事件回调
		        beforeRemove : beforeRemove,
		        onRemove : zTreeOnRemove,
		        onDrop : onDrop, //拖拽操作结束事件回调
		        onRename : zTreeOnRename,
		        onRightClick : zTreeOnRightClick
		 }
	}; 
 	var zTreeNodes;
    $(document).ready(function(){
    	$.fn.zTree.init($('#dataTree'), setting, zTreeNodes);
    	$('#rMenu').css('visibility','hidden');
    	var style1="position:absolute;left:"+window.screen.width/3+"px;";
    	var style2="position:absolute;left:"+window.screen.width/1.5+"px;";
    	$('#title2').css({"position":"absolute","left":window.screen.width/3+"px"});
    	$('#title3').css({"position":"absolute","left":window.screen.width/1.5+"px"});
    });
    function beforeClick(treeId, treeNode) {
        //alert("节点点击之前事件"+treeId+treeNode.name);
        return true;
    }
    function beforeCollapse(treeId, treeNode) {
        //alert("父节点折叠之前事件"+treeId+treeNode.name);
        return true;
    }
    function beforeExpand(treeId, treeNode) {
        //alert("父节点展开之前事件"+treeId+treeNode.name);
        return true;
    }
    function onCollapse(event, treeId, treeNode) {
        //alert("节点折叠事件"+treeId+treeNode.name);
    }        
    function onExpand(event, treeId, treeNode) {
        //alert("节点展开事件"+treeId+treeNode.name);
    }
    function onAsyncSuccess(event, treeId, treeNode, msg) {
    	curAsyncCount--;
    	 if (curStatus == "expand") {  
             expandNodes(treeNode.children);  
         } else if (curStatus == "async") {  
             asyncNodes(treeNode.children);  
         }  
         if(curAsyncCount<=0){
        	 if(curStatus!="init" && curStatus!=""){
        		 asyncForAll = true;
        	 }
        	 curStatus="";
         }
    }
    function beforeAsync(){
    	curAsyncCount++;
    }
    //字体控制
    function setFontCss(treeId,treeNode){
    	//return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
    	treeNode.level==0?{color:"red"}:{};
    }
    function onDrop(event,treeId,treeNodes,targetNode,moveType){
    	//alert("目标id"+treeId+"---移动joson"+treeNodes[0].id+"目标父节点"+targetNode.name);
    	$.ajax({
            url:'../emp/moveNode',
            data:{
            	pId:targetNode.id,
            	id:treeNodes[0].id
            },
            type:'post',
            dataType:'json',
            success:function(data){
            }
        })
    }
    function beforeRemove(treeId, treeNode) {
    	if(treeNode.isParent){
    		return confirm("确认删除父节点"+ treeNode.name + "以及他所有子节点吗?");
    	}
		return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
	}
    function addHoverDom(treeId,treeNode){
    	var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj("dataTree");
			var tnode;
			$.ajax({
                url:'${ctx}/cms/organization/addNode',
                data:{
                	pId:treeNode.id,
                	name:"new node"
                },
                async:false,
                type:'post',
                dataType:'json',
                success:function(data){
                	tnode = data;
                }
            })
			zTree.addNodes(treeNode, {id:tnode.id, pId:tnode.pId, name:tnode.name});
			return false;
		});
    }
    //删除
    function removeHoverDom(treeId,treeNode){
    	$("#addBtn_"+treeNode.tId).unbind().remove();
    }
    function zTreeOnRemove(event,treeId,treeNode){
    	$.ajax({
            url:'${ctx}/cms/organization/removeNode',
            data:{id:treeNode.id},
            type:'post',
            dataType:'json',
            success:function(data){
            }
        })
    }
    //修改名称
    function zTreeOnRename(event,treeId,treeNode,isCancel){
    	//alert("节点id"+treeNode.id+"name:"+treeNode.name);
    	$.ajax({
            url:'${ctx}/cms/organization/renameNode',
            data:{id:treeNode.id,name:treeNode.name},
            type:'post',
            dataType:'json',
            success:function(data){
            }
        })
    }
    //异步展开所有节点
    var curStatus = "init",curAsyncCount =0,goAsync=false,asyncForAll=false;
    function allNode(){
    	if(!check()){
    		return;
    	}
    	var treeObj = $.fn.zTree.getZTreeObj("dataTree");
    	if(asyncForAll){
    		treeObj.expandAll(true);
    	}else{
    		expandNodes(treeObj.getNodes());  
            if (!goAsync) {  
                curStatus = "";  
            } 
    	}
    }
    function expandNodes(nodes){
    	if (!nodes) return;  
        curStatus = "expand";  
        var treeObj = $.fn.zTree.getZTreeObj("dataTree");  
        for (var i=0, l=nodes.length; i<l; i++) {  
        	treeObj.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点  
            if (nodes[i].isParent && nodes[i].zAsync) {  
                expandNodes(nodes[i].children);//递归  
            } else {  
                goAsync = true;  
            }  
        }  
    }
    function check(){
    	if(curAsyncCount>0){
    		return false;
    	}
    	return true;
    }
    function asyncNodes(nodes){
    	if (!nodes) return;
        curStatus = "async";
        var treeObj = $.fn.zTree.getZTreeObj("dataTree");
        for (var i=0, l=nodes.length; i<l; i++) {
            if (nodes[i].isParent && nodes[i].zAsync) {
                asyncNodes(nodes[i].children);
            } else {
                goAsync = true;
                treeObj.reAsyncChildNodes(nodes[i], "refresh", true);
            }
        }
    }
    //关闭所有
    function closeNode(){
    	var treeObj = $.fn.zTree.getZTreeObj("dataTree"); 
    	treeObj.expandAll(false);
    }
    //刷新
    function refshNode(){
    	$.fn.zTree.init($('#dataTree'), setting, zTreeNodes);
    	$('#dataTree').css('height','100%');
    	//$('#dataTree').css('overflow','auto');
    	$('#rMenu').css('visibility','hidden');
    }
    //右键菜单
    function zTreeOnRightClick(event,treeId,treeNode){
    	var treeObj = $.fn.zTree.getZTreeObj("dataTree");
    	treeObj.selectNode(treeNode);
    	$('#rMenu ul').show();
    	$('#rMenu').css({"top":event.clientY+"px","left":event.clientX+"px","visibility":"visible"});
    	$("body").bind("mousedown",onBodyMouseDown);
    }
    function onBodyMouseDown(event){
    	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
			$('#rMenu').css({"visibility" : "hidden"});
		}
    }
    //自定义table节点
    function addDiyDom(treeId,treeNode){
    	var aObj = $("#" + treeNode.tId + "_a");
    	if ($("#diyBtn_"+treeNode.id).length>0) return;
    	var editStr = getHtml(treeNode);
    	aObj.append(editStr);
    	var btn = $("#diyBtn_"+treeNode.id);
    	if (btn) btn.bind("click", function(){alert("diy Button for "+document.body.scrollWidth + treeNode.name);});
    }
    function getHtml(treeNode){
    	var style1="position:absolute;left:"+window.screen.width/3+"px;";//class='diyTreeTableText'
    	var style2="position:absolute;left:"+window.screen.width/1.5+"px;";//class='diyTreeTableCb'
    	var lineData = "<span id='diyBtn_space_" +treeNode.id+ "' style='"+style1+"'>"+treeNode.id+"</span>"
			 + "<input type='checkbox'  id='diyBtn_" + treeNode.id+ "' style='"+style2+"' />";
		return lineData;
    }
</script>
</body>
</html>