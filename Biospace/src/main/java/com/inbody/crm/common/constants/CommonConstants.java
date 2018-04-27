package com.inbody.crm.common.constants;

public interface CommonConstants {

	// start 字典常量定义 
	/** 采购类型: 机器 */
	public static final String PURCHASE_TYPE_1 = "1";

	/** 采购类型: 配件 */
	public static final String PURCHASE_TYPE_2 = "2";

	/** 采购定单 到货状态: 未到货 */
	public static final String ARRIVAL_STATUS_0 = "0";

	/** 采购定单 到货状态: 部分到货 */
	public static final String ARRIVAL_STATUS_1 = "1";

	/** 采购定单 到货状态: 已到货 */
	public static final String ARRIVAL_STATUS_2 = "2";
	
	/** 采购定单 支付状态: 未支付 */
	public static final String PAYMENT_STATUS_0 = "0";
	
	/** 采购定单 支付状态: 部分支付 */
	public static final String PAYMENT_STATUS_1 = "1";
	
	/** 采购定单 支付状态: 已支付 */
	public static final String PAYMENT_STATUS_2 = "2";

	/** 物料类型: 机器 */
	public static final String MATERIAL_TYPE_1 = "1";

	/** 物料类型: 通用配件 */
	public static final String MATERIAL_TYPE_2 = "2";

	/** 物料类型: 套餐 */
	public static final String MATERIAL_TYPE_3 = "3";

	/** 物料类型: 选件 */
	public static final String MATERIAL_TYPE_4 = "4";

	/** 物料类型: 软件 */
	public static final String MATERIAL_TYPE_5 = "5";

	/** 物料类型: 服务 */
	public static final String MATERIAL_TYPE_6 = "6";

	/** 物料类型: 普通配件 */
	public static final String MATERIAL_TYPE_7 = "7";
	
	/** 出入库类型: 采购入库 */
	public static final String STORAGE_TYPE_11 = "11";
	
	/** 出入库类型: 换货入库 */
	public static final String STORAGE_TYPE_12 = "12";
	
	/** 出入库类型: 归还入库 */
	public static final String STORAGE_TYPE_13 = "13";
	
	/** 出入库类型: 退货入库 */
	public static final String STORAGE_TYPE_14 = "14";
	
	/** 出入库类型: 移库入库 */
	public static final String STORAGE_TYPE_15 = "15";
	
	/** 出入库类型: 其他入库 */
	public static final String STORAGE_TYPE_16 = "16";
	
	/** 出入库类型: 销售出库 */
	public static final String STORAGE_TYPE_21 = "21";
	
	/** 出入库类型: 借出出库 */
	public static final String STORAGE_TYPE_22 = "22";
	
	/** 出入库类型: 移库出库 */
	public static final String STORAGE_TYPE_23 = "23";
	
	/** 出入库类型: 报废丢失 */
	public static final String STORAGE_TYPE_24 = "24";
	
	/** 出入库类型: 换货出库 */
	public static final String STORAGE_TYPE_25 = "25";
	
	/** 出入库类型: 其他出库 */
	public static final String STORAGE_TYPE_26 = "26";
	
	/** 库存状态: 在库 */
	public static final String IN_STOCK_STATUS_1 = "1";
	
	/** 库存状态: 出库 */
	public static final String IN_STOCK_STATUS_2 = "2";
	
	/** 库存状态: 借出 */
	public static final String IN_STOCK_STATUS_3 = "3";
	
	/** 机器状态: 未检 */
	public static final String MACHINE_STATUS_1 = "1";
	
	/** 机器状态: 待处理 */
	public static final String MACHINE_STATUS_2 = "2";
	
	/** 机器状态: 检测正常 */
	public static final String MACHINE_STATUS_3 = "3";
	
	/** 机器类型: 新机 */
	public static final String MACHINE_TYPE_1 = "1";
	
	/** 机器类型: 样机 */
	public static final String MACHINE_TYPE_2 = "2";
	
	/** 合同类型: 内贸 */
	public static final String ORDERE_TYPE_1 = "1";
	
	/** 合同类型: 外贸 */
	public static final String ORDERE_TYPE_2 = "2";
	
	/** 合同类型: 以旧换新 */
	public static final String ORDERE_TYPE_3 = "3";
	
	/** 合同类型: 其他业绩 */
	public static final String ORDERE_TYPE_4 = "4";
	
	/** 币种: RMB */
	public static final String CURRENCY_1 = "1";
	
	/** 币种: KRW */
	public static final String CURRENCY_2 = "2";
	
	/** 币种: USD */
	public static final String CURRENCY_3 = "3";

    /** 维修分类: 保内 */
    public static final String REPAIR_TYPE_1 = "1";

    /** 维修分类: 保外 */
    public static final String REPAIR_TYPE_2 = "2";

    /** 维修分类: 内部维修 */
    public static final String REPAIR_TYPE_3 = "3";

    /** 维修分类: 初期不良 */
    public static final String REPAIR_TYPE_4 = "4";

	/** 维修处理方式: 咨询 */
	public static final String REPAIR_METHOD_1 = "1";

	/** 维修处理方式 : 上门 */
	public static final String REPAIR_METHOD_2 = "2";

	/** 维修处理方式 : 远程操作 */
	public static final String REPAIR_METHOD_3 = "3";

	/** 维修处理方式 : 返厂 */
	public static final String REPAIR_METHOD_4 = "4";
	
	/** 维修处理状态 : 已取消 */
	public static final String REPAIR_STATUS_1 = "1";
	
	/** 维修处理状态 : 待处理 */
	public static final String REPAIR_STATUS_2 = "2";
	
	/** 维修处理状态 : 处理完毕 */
	public static final String REPAIR_STATUS_3 = "3";

	/** 报价单类型 : 预报价单 */
	public static final String QUOTATION_TYPE_1 = "1";
	
	/** 报价单类型 : 最终报价单 */
	public static final String QUOTATION_TYPE_2 = "2";

    /** 生成履历类型 : 销售计划 */
    public static final String JOB_DATA_TYPE_1 = "1";

    /** 生成履历类型 : 库存履历 */
    public static final String JOB_DATA_TYPE_2 = "2";

    /** 生成履历类型 : 自动 */
    public static final String GEN_METHOD_1 = "1";

    /** 履历生成方式 : 手动 */
    public static final String GEN_METHOD_2 = "2";

    /** 发票开票状态: 未开票 */
    public static final String INVOICE_STATUS_10 = "10";

    /** 发票开票状态: 部分开票 */
    public static final String INVOICE_STATUS_20 = "20";

    /** 发票开票状态: 已开票 */
    public static final String INVOICE_STATUS_30 = "30";

	/** 报价单明细分类 : 配件 */
	public static final String QUOTATION_DTL_ITEM_TYPE_1 = "1";
	
	/** 报价单明细分类 : 服务 */
	public static final String QUOTATION_DTL_ITEM_TYPE_2 = "2";
	
	/** 价格体系 : 代理 */
	public static final String PRICE_SYSTEM_1 = "1";

	/** 价格体系 : 直销 或 一般直销*/
	public static final String PRICE_SYSTEM_2 = "2";

	/** 价格体系 : 协议直销 */
	public static final String PRICE_SYSTEM_3 = "3";

	/** 价格体系 : 经销 或 一般经销*/
	public static final String PRICE_SYSTEM_4 = "4";

	/** 价格体系 : 协议经销 */
	public static final String PRICE_SYSTEM_5 = "5";

	/** 价格体系 : 配件 */
	public static final String PRICE_SYSTEM_6 = "6";

	/** 是 */
	public static final String YES = "1";

	/** 否 */
	public static final String NO = "0";
	
	/** 代理商目标目标类型: 金额 */
	public static final String TARGET_TYPE_1 = "1";

	/** 代理商目标目标类型: 数量 */
	public static final String TARGET_TYPE_2 = "2";

	/** 新建维修信息查询类型：合同 */
	public static final String REPAIR_SN_SEARCH_TYPE_1 = "1";

	/** 新建维修信息查询类型：维修信息 */
	public static final String REPAIR_SN_SEARCH_TYPE_2 = "2";

	/** 新建维修信息查询类型：检测信息 */
	public static final String REPAIR_SN_SEARCH_TYPE_3 = "3";

    /** 发票来源：销售合同 */
    public static final String INVOICE_SOURCE_10 = "10";

    /** 发票来源：报价单 */
    public static final String INVOICE_SOURCE_20 = "20";

    /** 库存操作类型：占用库存 */
    public static final String OCCUPANCY_TYPE_1 = "1";

    /** 库存操作类型：取消占用库存 */
    public static final String OCCUPANCY_TYPE_0 = "0";

    /** 物料标记：血压计 */
    public static final String MATERIAL_MARK_1 = "1";

    /** 物料标记：LB软件 */
    public static final String MATERIAL_MARK_2 = "2";

    /** 物料标记：其他 */
    public static final String MATERIAL_MARK_3 = "3";

	/** 附件分类：客户/代理商附件 */
	public static final String ATTACHMENT_TYPE_CUSTOMER = "CUSTOMER";

	/** 附件分类：出入库附件 */
	public static final String ATTACHMENT_TYPE_STORAGE = "STORAGE";

	/** 附件分类：检测附件 */
	public static final String ATTACHMENT_TYPE_TESTING = "TESTING";

	/** 附件分类：报价单附件 */
	public static final String ATTACHMENT_TYPE_QUOTATION = "QUOTATION";

	/** 附件分类：招标附件 */
	public static final String ATTACHMENT_TYPE_TENDER = "TENDER";

    /** 附件分类：销售计划履历 */
    public static final String ATTACHMENT_TYPE_SD = "SD";

    /** 附件分类：库存履历 */
    public static final String ATTACHMENT_TYPE_MM = "MM";
    // End 字典常量定义

	// start 流水号取得用业务id
	/** 流水号取得用业务id: 采购订单呈 */
	public static final String TRANSACTION_NUMBER_PURCHASE = "purchase_no";

	/** 流水号取得用业务id: 出入库履历编号 */
	public static final String TRANSACTION_NUMBER_STORAGE = "storage_id";

	/** 流水号取得用业务id: 咨询编号 */
	public static final String TRANSACTION_CONSULTING = "consulting_no";
	
	/** 流水号取得用业务id: 维修编号 */
	public static final String TRANSACTION_REPAIR = "repair_no";

    /** 流水号取得用业务id: 虚拟sn */
    public static final String TRANSACTION_VIRTUAL_SN = "virtual_sn";

    /** 流水号取得用业务id: 销售履历 */
    public static final String TRANSACTION_SALES_REPORT = "sales_report";

    /** 流水号取得用业务id: 库存履历 */
    public static final String TRANSACTION_STORAGE_REPORT = "storage_report";
	// end 流水号取得用业务id

	// start 工作流常量定义
	/** 工作流状态: 申请中 */
	public static final String WORKFLOW_STATUS_10 = "10";

	/** 工作流状态: 审批中 */
	public static final String WORKFLOW_STATUS_20 = "20";

	/** 工作流状态: 退回 */
	public static final String WORKFLOW_STATUS_30 = "30";

	/** 工作流状态: 撤回 */
	public static final String WORKFLOW_STATUS_40 = "40";

	/** 工作流状态: 审批完成 */
	public static final String WORKFLOW_STATUS_50 = "50";

	/** 工作流状态: 临时保存 */
	public static final String WORKFLOW_STATUS_60 = "60";

	/** 工作流状态: 删除 */
	public static final String WORKFLOW_STATUS_70 = "70";

	/** 流程标题: 机器采购订单 */
	public static final String PROCESS_TITLE_MC_PURCHASE = "机器采购订单申请";

    /** 流程标题: 配件采购订单 */
    public static final String PROCESS_TITLE_AC_PURCHASE = "配件采购订单申请";
    
    /** 流程标题: 销售部员工评价申请*/
    public static final String PROCESS_TITLE_AC_SALE = "销售部员工评价申请";
    
    /** 流程标题: 非销售部员工评价申请*/
    public static final String PROCESS_TITLE_AC_NON_SALE = "非销售部员工评价申请";
	
	/** 流程标题: 合同申请*/
	public static final String PROCESS_TITLE_SO_ORDER = "合同申请";
	
	/** 流程标题: 归还入库申请*/
	public static final String PROCESS_TITLE_SM_IN_SENDBACK = "归还入库申请";
	
	/** 流程标题: 退货入库申请*/
	public static final String PROCESS_TITLE_SM_IN_REFUND = "退货入库申请";
	
	/** 流程标题: 换货入库申请*/
	public static final String PROCESS_TITLE_SM_IN_EXCHANGE = "换货入库申请";
	
	/** 流程标题: 其他入库申请*/
	public static final String PROCESS_TITLE_SM_IN_OTHER = "其他入库申请";
	
	/** 流程标题: 发票管理 */
	public static final String PROCESS_TITLE_IM_INVOICE = "发票管理";
	
	/** 流程标题: 发货申请 */
	public static final String PROCESS_TITLE_SO_APPLY_DELIVER = "发货申请";
	
	/** 流程标题: 报价单发票申请 */
    public static final String PROCESS_TITLE_QUOTA_INVOICE = "报价单发票申请";
	
	/**其他出库申请*/
//	public static final String OUT_STORAGE_APPLY = "其他出库申请";
	public static final String OUT_STORAGE_APPLY = "借出申请";
	
	public static final String SCRAP_STORAGE = "报废出库";
	
	public static final String OTHER_STORAGE = "其他出库";
	
	public static final String DELAY_STORAGE = "借出延长";

	
	public static final String SCRAP_CHANGE = "换货出库";

	public static final String TENDER_AUTHOR = "招标授权";


	/** 流程标题: 配件采购订单 */
	public static final String PROCESS_TITLE_ACC_PURCHASE = "配件采购订单";
	// end 工作流常量定义

}
