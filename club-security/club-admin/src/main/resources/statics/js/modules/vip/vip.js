$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dict/list',
        datatype: "json",
        colModel: [			
			{ label: '姓名', name: 'name', index: 'name', width: 80 },
			{ label: '电话号码', name: 'phone', index: 'type', width: 80 },
			{ label: '身份证', name: 'cardNo', index: 'code', width: 80 },
			{ label: '余额', name: 'balance', index: 'value', width: 80 },
			{ label: '注册门店', name: 'store', index: 'order_num', width: 80 },
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }
		],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

//部门结构树
var product_ztree;
var product_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "producrId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

var vm = new Vue({
	el:'#rrapp',
	data:{
        q:{
            name: null
        },
		showList: true,
		title: null,
		dict: {},
        role:{
            productId:null,
            productName:null
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.dict = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getDept();

            vm.getDataTree();
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.dict),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/dict/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "sys/dict/info/"+id, function(r){
                vm.dict = r.dict;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'name': vm.q.name},
                page:page
            }).trigger("reloadGrid");
		},
        getProduct: function(){
            //加载部门树
            $.get(baseURL + "product/product/list", function(r){
                product_ztree = $.fn.zTree.init($("#productTree"), product_setting, r);
                //展开所有节点
                product_ztree.expandAll(true);
            })
        },
        productTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择产品",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#productLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = product_ztree.getSelectedNodes();
                    //选择上级部门
                    vm.role.productId = 1;//node[0].productId;
                    vm.role.productName = '消费';//node[0].name;

                    layer.close(index);
                }
            });
        }
	}
});