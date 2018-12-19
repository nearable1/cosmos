$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/log/list',
        datatype: "json",
        colModel: [			
			{ label: 'vip名', name: 'username',sortable: false, width: 50 },
			{ label: '电话', name: 'phone',sortable: false, width: 70 },
			{ label: '身份证', name: 'idCard',sortable: false, width: 150 },
			{ label: '操作', name: 'operation',sortable: false, width: 80 },
            { label: '发生金额', name: 'money',sortable: false, width: 80 },
			{ label: '余额', name: 'balance',sortable: false, width: 70 },
            { label: '消费门店', name: 'store',sortable: false, width: 70 },
			{ label: '创建时间', name: 'createDate', sortable: false, width: 90 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});