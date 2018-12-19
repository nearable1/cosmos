$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/dict/list',
        datatype: "json",
        colModel: [			
			{ label: '姓名', name: 'name', index: 'name',sortable: false, width: 80 },
			{ label: '电话号码', name: 'phone', index: 'type',sortable: false, width: 80 },
			{ label: '身份证', name: 'cardNo', index: 'code',sortable: false, width: 80 },
			{ label: '余额', name: 'balance', index: 'value',sortable: false, width: 80 }
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
            idKey: "id",
            pIdKey: null,
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
        showConsume: true,
		title: null,
		dict: {},
        goods:{
            id:null,
            name:null,
			price: null
        },
        charge: null,
        target: null
	},
	methods: {
		query: function () {
			vm.reload();
		},
		update: function (event) {
		    console.log("source:");
		    console.log(event.target.id);
		    vm.target = event.target.id;
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
			if(vm.target==='consume') {
                vm.showConsume = true;
                vm.title = '消费'
            }else {
                vm.showConsume = false;
                vm.title = '充值';
            }
            vm.getProduct();
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.dict.id == null ? "sys/dict/save" : "sys/dict/update";
            /*充值*/
            if(vm.target==='recharge') {
                if(vm.charge>0) {
                    vm.dict.balance = parseInt(vm.dict.balance)+parseInt(vm.charge);
                    vm.dict.latest = '充值';
                    vm.dict.money = vm.charge;
                } else if(vm.charge<0) {
                    alert("金额不能为负数！");
                    return;
                }
            }else if(vm.target==='consume') {
                /*消费*/
                if((vm.dict.balance-vm.goods.price)<0) {
                    alert("余额不足，请充值！");
                    return;
                }else {
                    vm.dict.balance=parseInt(vm.dict.balance)-parseInt(vm.goods.price);
                    vm.dict.latest = vm.goods.name;
                    vm.dict.money = 0-vm.goods.price;
                }
            }

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
                vm.getProduct();
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
            //加载商品树
            $.get(baseURL + "product/product/list2", function(r){
                product_ztree = $.fn.zTree.init($("#productTree"), product_setting, r);

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
                    vm.goods.id = node[0].id;
                    vm.goods.name = node[0].name;
					vm.goods.price = node[0].price;
                    layer.close(index);
                }
            });
        }
	}
});