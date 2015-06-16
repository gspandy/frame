package mb.erp.dr.soa.constant;

/**
 * 单据常量定义
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         O2OBillConstant
 * @since       全流通改造
 */
public interface O2OBillConstant {
	
	interface CONSTANT{
		static final String SP = "SP";
		/** 上层转配的配货方式为F*/
		static final String UP_ALLOCTION = "F"; 
		/**直配*/  
		static final String ALLOCTION_S = "S"; 
		/**转配*/  
		static final String ALLOCTION_Z = "Z"; 
		/**调配*/  
		static final String ALLOCTION_T = "T"; 
	}
	
	/** 全流通类型 */
	interface IS_OOS{
		/** 线上类型 */
		static final String M = "M";
		/** 线下类型 */
		static final String T = "T";
		/** 非全流通类型 */
		static final String F = "F";
	}
	
	/**
	 * 老ERP单据类型
	 */
	public static enum BillType{
		/**计划配货单*/AAD,
		/**计划配货单  出库单配货模式为SHOR时，原始单据类型为SAD*/SAD,
		/**调配单*/		TBN,	
		/**调拨单*/		TFO,	
		/**物资领用单*/AWN, 
		/**期货订单*/	FON,	
		/**退货申请单*/BGR,	
		/**代理商退货申请单*/ABG,
		/**出库单*/		GDN,	
		/**入库单 */		GRN,	
		/**货位搬移单*/GMN, 
		/**盘存调整单*/STT,	
		/**零售汇总单*/RLS,	
		/**装箱单*/		BOX,	
		/**库存调整单 */STK,	
		/**分拣单*/		PPK,	
		/**计划配货单 */ADN,	
		/**代理商扣款单*/ALB,	
		/**供应商扣款单*/VLB,	
		/**付款单*/		PYB,	
		/**收款单*/		RCB,	
		/**补贴单*/		SDB,	
		/**现货订单*/	IDT,	
		/**新ERP订单*/NDT,
		/**直营门店退货申请单**/ SBG,
		/**直营门店退货单**/ SSC,
	}
	
	/**
	 * 新ERP单据类型
	 */
	public static enum NewBillType{
		/**现货订单*/	IDT,
		/**交货单*/	DGN,
		/**到货通知单*/ RVD,
		/**调配单*/ TBN,
		/**出库单*/ GDN,
		/**入库单*/ GRN,
		
		/**计划配货单*/AAD,
		/**期货订单*/	FON,
		/**调拨单*/		TFO,
		/**直营门店订单*/ SAD,
	}
	
	/**
	 * 是或非 设定
	 */
	interface TORF{
		/** 是 */
		static final  String TRUE = "T";
		/** 否 */
		static final  String FALSE = "F";
	}
	
	/**
	 * 是或非 设定
	 */
	interface ONEORZERO{
		/** 是 */
		static final  String ONE = "1";
		/** 否 */
		static final  String ZERO = "0";
	}
	
	/**
	 * 老ERP订单进度状态
	 */
	interface PROGRESS{
		/**录入中 */
		static final  String PG = "PG"; 
		/**已确认 */
		static final  String CN = "CN"; 
		/**已审核 */
		static final  String AP = "AP"; 
		/**配货中 */
		static final  String AG = "AG"; 
		/**已配货 */
		static final  String AD = "AD"; 
		/**发货中 */
		static final  String DG = "DG"; 
		/**已发货 */
		static final  String DD = "DD"; 
		/**收货中 */
		static final  String RG = "RG"; 
		/**已收货 */
		static final  String RD = "RD"; 
		/**过账中 */
		static final  String FI = "FI"; 
	}
	
	/**
	 * 新ERP单据流进度
	 */
	interface DOC_FLOW_STATE_PROGRESS{
		/**配货中  DP = 0x20000*/
		static final  int DP = 65536;
		static final  int DC = 131072;
		static final  int SR = 589824;
		static final  int NOA = 81920;
	}
	
	/**
	 * 新ERP订单进度状态
	 */
	interface NEW_PROGRESS{
		/**配货中 */
		static final  String DP = "DP"; 
		/**已配货 */
		static final  String DC = "DC"; 
		/**发货中 */
		static final  String SP = "SP"; 
		/**已发货 */
		static final  String SC = "SC"; 
		/**收货中 */
		static final  String RP = "RP"; 
		/**已收货 */
		static final  String RC = "RC"; 
	}
	
	/**
	 * 新ERP订单进度状态
	 */
	interface NEW_DOC_STATE{
		/**录入中 */
		static final  int PG_NEW = 0; 
		/**已确认 */
		static final  int CN_NEW = 1; 
		/**已审核 */
		static final  int AP_NEW = 2; 
		/**已撤销 */
		static final  int CC_NEW = 8; 
		/**已完成 */
		static final  int CS_NEW = 4; 
		/**已挂起 */
		static final  int CP_NEW = 16; 
//		/**配货中 */
//		static final  int AG_NEW = 65537; 
//		/**已配货 */
//		static final  int AD_NEW = 65538; 
//		/**已发货 */
//		static final  int DD_NEW = 65540; 
//		/**部分配货中 */
//		static final  int AG_NEW_P = 65552;
//		/**部分已配货 */
//		static final  int AD_NEW_P = 131088;
//		/**发货中 */
//		static final  int DG_NEW = 196608;
//		/**部分发货中 */
//		static final  int DG_NEW_P = 196624;
//		/**部分已发货 */
//		static final  int DD_NEW_P = 262160;
//		/**收货中 */
//		static final  int RG_NEW = 327680;
//		/**部分收货中 */
//		static final  int RG_NEW_P = 327696;
//		/**已收货 */
//		static final  int RD_NEW = 393216;
//		/**部分已收货 */
//		static final  int RD_NEW_P = 393232;
//		/**过账中 */
//		static final  int FI_NEW = 458752;
	}
	
	/**
	 * 新ERP现货订单进度状态
	 */
	interface IDT_PROGRESS{
		/**配货中  0x10001*/
		static final  int AG_NEW = 65537; 
		/**已配货  0x10002*/
		static final  int AD_NEW = 65538; 
		/**已发货  0x10004*/
		static final  int DD_NEW = 65540; 
		/**部分配货中  0x10010*/
		static final  int AG_NEW_P = 65552;
		/**部分已配货  0x20010*/
		static final  int AD_NEW_P = 131088;
		/**发货中  0x30000*/
		static final  int DG_NEW = 196608;
		/**部分发货中  0x30010*/
		static final  int DG_NEW_P = 196624;
		/**部分已发货  0x40010*/
		static final  int DD_NEW_P = 262160;
		/**收货中  0x50000*/
		static final  int RG_NEW = 327680;
		/**部分收货中  0x50010*/
		static final  int RG_NEW_P = 327696;
		/**已收货  0x60000*/
		static final  int RD_NEW = 393216;
		/**部分已收货  0x60010*/
		static final  int RD_NEW_P = 393232;
		/**过账中  0x70000*/
		static final  int FI_NEW = 458752;
	}
	
	/**
	 * 新ERP现货订单分配明细进度
	 */
	interface IDT_ALLOC_DTL_PROGRESS{
		/**已配货  DC = 0x10000*/
		static final  int DC = 65536;
	    /**配货中  DP = 0x20000*/
	    static final  int DP = 131072;
	    /**已发货  SC = 0x30000*/
	    static final  int SC = 196608;
	    /**发货中  SP = 0x40000*/
	    static final  int SP = 262144;
	    /**已收货  RC = 0x50000*/
	    static final  int RC = 327680;
	    /**收货中  RP = 0x60000*/
	    static final  int RP = 393216;
	}
	
	/**
	 * 新ERP调配单进度
	 */
	interface TBN_PROGRESS{
		/**部分配货中  0x10010*/
		static final  int AG_NEW_P = 65552;
		/**配货中  0x60000*/
		static final  int AG_NEW = 393216; 
		/**部分已配货  0x20010*/
		static final  int AD_NEW_P = 131088;
		/**已配货  0x70000*/
		static final  int AD_NEW = 458752; 
		/**部分发货中  0x30010*/
		static final  int DG_NEW_P = 196624;
		/**发货中  0x30000*/
		static final  int DG_NEW = 196608;
		/**部分已发货  0x40010*/
		static final  int DD_NEW_P = 262160;
		/**已发货  0x11000*/
		static final  int DD_NEW = 69632; 
		/**部分收货中  0x50010*/
		static final  int RG_NEW_P = 327696;
		/**收货中  0x50000*/
		static final  int RG_NEW = 327680;
		/**部分已收货  0x60010*/
		static final  int RD_NEW_P = 393232;
		/**已收货  0x13000*/
		static final  int RD_NEW = 77824;
		/**过账中  0x80000*/
		static final  int FI_NEW = 524288;
	}
	
	
	/**
	 * 新ERP交货单进度
	 */
	interface DGN_PROGRESS{
		/**分拣中  0x10000*/
	    static final  int IN_PKNING = 65536;
	    
	    /**分拣完成  0x20000*/
	    static final  int COMPLETE_PKN = 131072;
	    
	    /**待出库  0x30000*/
	    static final  int ONE_MODE_OUT = 196608;
	    
	    /**分拣待处理  0x40000*/
	    static final  int WAIT_DO_WITH = 262144;
	    
	    /**移入集货地  0x50000*/
	    static final  int MOVE_TO_LOC = 327680;
	    
	    /**已出库  0x80000*/
	    static final  int HAVE_OUT_WAREH = 524288;
	    
	    /**已冲单  0x90000*/
	    static final  int HAVE_KILLED = 589824;
	    
	    /**短拣待处理  0x120000*/
	    static final  int STWAIT_DO_WITH = 1179648;
	    
	    /**短拣处理中  0x130000*/
	    static final  int ST_DOING = 1245184;
	    
	    /**发货中  0x160000*/
	    static final  int DELIVING = 1441792;
	    
	    /**完成  0x320000*/
	    static final  int CHECK_COMPLETE = 3276800;
	    
	    /**过账中  0x170000*/
	    static final  int PP = 1507328;
	}
	
	/**
	 * 新ERP出库单进度
	 */
	interface GDN_PROGRESS {
		/**已发货  0x10000*/
	    static final  int HAVE_DELIVERED = 65536;
	    
	    /**已冲单  0x20000*/
	    static final  int HAVE_KILLED = 131072;
	    
	    /**冲销单  0x40000*/
	    static final  int KILL = 262144;
	    
	    /**已托运  0x80000*/
	    static final  int HAVE_IN_CAR = 524288;
	    
	    /**待出库  0x90000*/
	    static final  int WAITING_OUT = 589824;
	
	    /**过账中  0x100000*/
	    static final  int PP = 1048576;
	}
	
	
	/**
	 * 新ERP到货通知单进度
	 */
	interface RVD_PROGRESS{
		/**已发货  0x10000*/
	    static final  int HASDELIVERGOODS = 65536;
	    
	    /**收货中  0x20000*/
	    static final  int WAREHING = 131072;
	    
	    /**已收货  0x30000*/
	    static final  int RECEIPTGOODS = 196608;
	    
	    /**已入库  0x40000*/
	    static final  int INWAREHOUSE = 262144;
	    
	    /**初核完毕  0x60000*/
	    static final  int INITCOMPLETE = 393216;
	    
	    /**已暂收  0x70000*/
	    static final  int HASTEMPCLOSED = 458752;
	    
	    /**已冲单  0x80000*/
	    static final  int HASKILLED = 524288;
	    
	    /**过账中  0x90000*/
	    static final  int PP = 589824;
	}
	

	/**
	 * 新ERP入库单进度
	 */
	interface GRN_PROGRESS {
		/**已收货  0x10000*/
	    static final  int RECEIVED = 65536;
	    /**待入库  0x11000*/
	    static final  int WAITING = 69632;
	    /**已冲单  0x20000*/
	    static final  int CANCELED = 131072;
	    /**冲销单  0x40000*/
	    static final  int OFFSET = 262144;
	    /**分储中  0x50000*/
	    static final  int PANING = 327680;
	    /**分储完成  0x60000*/
	    static final  int PANED = 393216;
	    /**B2B分储  0x70000*/
	    static final  int B2BPANING = 458752;
	    /**冲销已入库  0x80000*/
	    static final  int OFFSETINWARE = 524288;
	    /**过账中  0x90000*/
	    static final  int Checked = 589824;
	}
	
	/**
	 * 合法性校验类型
	 */
	interface INVALID_TYPE{
		/** 商品 */
		static final String GOODS = "GOODS";
		/** 组织 */
		static final String UNIT = "UNIT";
		/** 品牌 */
		static final String BRAND = "BRAND";
		/** 是否存在 */
		static final String EXT = "EXT";
		/** 是否活动 */
		static final String STA = "STA";
	}

	/**
	 * 交易方式编码解释
	 */
	interface TRADE_TYPE{
		/** 出库 */
		static final String GDDL = "GDDL";
		/** 入库 */
		static final String GDRV= "GDRV";
		/** 收款 */
		static final String _01= "01";
	}
	
	/**
	 * 配货模式
	 */
	public static enum AllocType{
//		/**其他*/	O,
//		/**转账*/	ZZ,
//		/**直配(调拨，配发给下属代理商门店)*/	TFO,
//		/**直配(配发给下属直营门店)**/	S,
//		/**调配*/	T,
//		/**转配*/	Z,
//		/**上层转配*/	UPZ,
//		/**三方调配*/	T3,
//		/**调配+调配*/	TT,
//		/**直配+转配*/	SZ,
////		/**三方调配+直配*/	T3S,
//		/**调配+转配*/	TZ,
//		/**三方调配+转配*/	T3Z,
//		/**调配+三方调配*/	TT3,
//		/**调配+三方调配+转配*/	TT3Z,
//		/**调配+调配+调配*/	TTT,
//		/**调配+调配+三方调配*/	TTT3,
//		/**调配+调配+转配*/	TTZ,
//		/**调配+调配+三方调配+直配*/	TTT3S,
//		/**调配+调配+三方调配+转配*/	TTT3Z,
//		/**三方调配的下层直配*/	S_T3,
		//---------------------------------
		/**直配(配发给下属直营门店)**/	XXXS,
		/**直配(调拨，配发给下属代理商门店)*/	XTFO,
		/**调配*/	XXXT,
		/**转配*/	XX3Z,
		/**三方调配*/	XX3T,
		/**直配+转配*/	XS3Z,
		/**调配+转配*/	XT3Z,
		/**调配+调配+转配*/	XTTZ,
		/**三方调配+转配*/	X3TZ,
		/**调配+调配+三方调配*/	TT3T,
		/**调配+调配+三方调配+直配*/	TT3TS,
		/**调配+调配+三方调配+转配*/	TT3TZ,
		/**调配+调配*/	XXTT,
		/**调配+三方调配*/	XT3T,
		/**调配+三方调配+转配*/	T3TZ,
		/**调配+调配+调配*/	XTTT,
		/**转账*/	XXXZ,
		/**上层转配*/	XXUZ,
		/**三方调配的下层直配*/	XXLS,
		/**直营门店退货*/ XXTH,
		/**其他*/	XXXO,
	}
	
	/**
	 * 老ERP出库方式
	 */
	public static enum GdnMode{
		/**直配(调拨) SAD**/	SHOR,
		/**调配 TBN*/	AGRT,
		/**直配, 转配, 三方调配 AAD*/	AGOR,
		/**调拨出库**/ TRAN,
		/**门店退货到宿主代理商出库**/SHCR
	}
	
	/** 决策方*/
	interface APPROVED{
		static final String NEWERP = "02";
		static final String OLDERP = "01";
		static final String SAP = "03";
	}
	
	/** 老ERP数据来源*/
	interface DATA_SOURCE{
		/** 本系统 */
		static final String OLDERP = "01"; 
		/** 新ERP系统 */
		static final String NEWERP = "02";
	}
	
	/**
	 * 业务类型
	 */
	public static enum POF_BIZTYPE{
		O2O,
		OOS,
	}
	
	/**
	 * 单据类型编号-记录单据流 PubO2oFlow
	 */
	interface POF_DataTypeNo{
		/**	新ERP B2C现货订单	*/ 
		static final String B2C_IDT = "01";
		/**老ERP_现货订单	*/ 
		static final String OLD_ERP_IDT = "02";
		/**	老ERP_计划配货单	*/ 
		static final String OLD_ERP_ADN = "03";
		/**	老ERP_调配单	*/ 
		static final String OLD_ERP_TBN = "04";
		/**	老ERP_退货申请单	*/ 
		static final String OLD_ERP_BGR = "05";
		/**	老ERP_出库单	*/ 
		static final String OLD_ERP_GDN = "06";
		/**	老ERP_退货单	*/ 
		static final String OLD_ERP_SCN = "07";
		/**	老ERP_入库单	*/ 
		static final String OLD_ERP_GRN = "08";
		/**	新ERP_现货订单	*/ 
		static final String NEW_ERP_IDT = "09";
		/**	新ERP_调配单	*/ 
		static final String NEW_ERP_TBN = "10";
		/**	新ERP_B2B交货单	*/ 
		static final String NEW_ERP_DGN = "11";
		/**	新ERP B2B出库单	*/ 
		static final String NEW_ERP_GDN = "12";
		/**	新ERP_B2B到货通知单	*/ 
		static final String NEW_ERP_RVD = "13";
		/**	新ERP B2B入库单	*/ 
		static final String NEW_ERP_GRN = "14";

	}
	
	/**
	 * 状态值-记录单据流 PubO2oFlow
	 */
	public static enum POF_DocStatus{
		/**已接受B2C订单消息**/	RV,
		/**需重新执行*/	RE,
		/**执行成功*/	SU
	}
	
	/**
	 * 调配原因
	 */
	interface REASON_CODE{
		static final String AB = "AB";
	}
	
	/**
	 * 新ERP出库方式
	 */
	public static enum  DelivMode{
		/**配发到下级代理商出库*/
		AGOR ,
	    /**配发到下属直营门店出库*/
		SHOR ,
	    /**委托生产退货出库*/
		CMCR ,
	    /**采购退货出库*/
		PUCR ,
	    /**制造商成品发货出库*/
		PROD ,
	    /**工厂还给客户余料出库*/
		MTRT ,
	    /**退货到上级代理商出库*/
		AGCR ,
	    /**调配到上级代理商出库*/
		AGRT ,
	    /**客户发料给工厂原料出库*/
		MTSP ,
	    /**物资领用出库*/
		APPL ,
	    /**门店退货到宿主代理商出库*/
		SHCR ,
	    /**门店零售给顾客出库*/
		RETL ,
	    /**调拨出库*/
		TRAN ,
	    /**赠送出库*/
		PRES ,
	    /**特殊出库*/
		SPEC ,
	    /**其它出库*/
		OTHR ,
	    /**盘存调整减少*/
		ADJS ,
	    /**采购调配到上级代理商出库*/
		BUBK ,
	    /**[期货]配发到下级代理商出库*/
		AGOF ,
	    /**质检退货*/
		QUNL ,
	    /**期初调整出库*/
		INIT ,
	    /**内调出库*/
		ITBN ,
	}
	
	/**
	 * 新ERP入库方式
	 */
	public static enum  RcptMode{
		/**自力生产入库*/
		PROD ,
        /**工厂收到客户原材料入库*/
		MTRH ,
        /**委托生产入库*/
		COMM,
        /**采购入库*/
		PURC,
        /**代理商订货入库*/
		AGOR ,
        /**门店订货入库*/
		SHOR ,
        /**下级代理商退货入库*/
		AGCR,
        /**下级代理商调回入库*/
		AGRT ,
        /**下属直营门店退货入库*/
		SHCR,
        /**顾客零售退货入库*/
		RTCR ,
        /**客户收到工厂余料退回入库*/
		MTRT ,
        /**物资退领入库*/
		WDRW ,
        /** 调拨入库*/
		TRAN,
        /**赠送入库*/
		PRES ,
        /**特殊入库*/
		SPEC,
        /**其它入库*/
		OTHR,
        /**盘存调整增加*/
		ADJS,
        /**下级代理商采购调配入库*/
		BUBK,
        /**[期货]代理商订货入库*/
		AGOF,
        /**期初调整入库*/
		INIT,
        /**内调入库*/
		ITBN,
	}
	
	/**
	 * 生成方式
	 */
	interface CREATE_TYPE{
		static final String HANDLY = "01";
		static final String AUTOMATION = "02";
	}
	
	/**
	 * 单据流 DATA_TYPE
	 */
	public static enum PubO2OFlowType{
		 /**新ERP B2C现货订单*/
		B2C_NIDT ,
		/**新ERP B2C出库单 */
		B2C_NGDN ,
		/**新ERP B2C发货单 */
		B2C_NDVN ,
		/**老ERP 现货订单*/
		B2B_OIDT ,
		/**老ERP 调拨单 */
		B2B_OTFO ,
		/**老ERP 调配单 */
		B2B_OTBN ,
		/**老ERP 配货单 */
		B2B_OADN ,
		/**老ERP 退货申请单*/
		B2B_OBGR ,
		/**老ERP 退货单*/
		B2B_OSCN ,
		/**老ERP 出库单 */
		B2B_OGDN ,
		/**老ERP 入库单 */
		B2B_OGRN ,
		/**新ERP 现货订单 */
		B2B_NIDT ,
		/**新ERP 调配单 */
		B2B_NTBN ,
		/**新ERP 交货单 */
		B2B_NDGN ,
		/**新ERP 到货通知单 */
		B2B_NRVD ,
		/**新ERP 出库单 */
		B2B_NGDN ,
		/**新ERP 入库单 */
		B2B_NGRN ,
	}
	
	/**
	 * 老ERP出库原因 - 老ERP不记录出库原因，因为这个与SAP同步有冲突
	 */
	interface RCV_STATE{
		static final String AN = "AN";
		static final String K = "K";
		static final String AM = "AM";
		static final String AT = "AT";
	}
	
	/**
	 * 新ERP出库原因
	 */
	interface RCV_STATE_NEW{
		static final String N="N";   //   正常货品  
		static final String T="T";   //   承运商失货 
		static final String D="D";   //   返修出库  
		static final String C="C";   //   无偿借用  
		static final String AM="AM";  //   固定资产领用
		static final String AN="AN";  //   在建工程领用
		static final String AT="AT";  //   无形资产领用
		static final String K="K";   //   店铺开业前装修  
	}

	/**
	 * 专项资金类型
	 */
	public static enum LimitType{
		XE,
		OO,
	}
	
	/**
	 * 借款事务类型
	 */
	public static enum TranType{
		LA,
		PY,
	}
	
	/**
	 * 成本核算方式
	 */
	interface CalType {
		static final int CAL_TYPE_0 = 0;//月末加权
		static final int CAL_TYPE_1 = 1;//末次进价法
		static final int CAL_TYPE_2 = 2;//移动加权平均
	}
	
	/**
	 * 公用变量
	 */
	interface BASE_EXTRA {
//		static final String  ALLOC_TYPE = "ALLOC_TYPE";  //  配货模式  (队列使用) AllocType
//		static final String  UP_VENDEEID = "UP_VENDEEID";  //  上级原始购货方 (队列使用)
//		static final String  UP_DOC_TYPE = "UP_DOC_TYPE";  //  上级原始单据类型  (队列使用) BillType
//		static final String  UP_DOC_CODE = "UP_DOC_CODE";  //  上级原始单据编号  (队列使用)
//		static final String  GDN_MODE = "GDN_MODE";   //出库方式  (队列使用)   GdnMode
//		static final String  ORIGIN_ADN_NUM = "ORIGIN_ADN_NUM";   //原始adnNum    
//		static final String  UP_RCV_WAREHID = "UP_RCV_WAREHID";   //  实际收货仓库（预处理队列使用） 
//		static final String  UP_DISP_WAREHID = "UP_DISP_WAREHID";  //  实际发货仓库（预处理队列使用） 
//		static final String  DATA_SOURCE = "DATA_SOURCE";    //数据来源  
//		static final String  APPROVED = "APPROVED";     //决策方 
//		static final String  OS_DOCCODE = "OS_DOCCODE";       
//		static final String  SF_GDN_CODE = "SF_GDN_CODE";  //新ERP出库单code   
//		static final String  GDN_NUM = "GDN_NUM";  //老ERP出库单num    规则用到
//		static final String  SF_DGN_CODE = "SF_DGN_CODE";  //交货单code 规则用到  
//		static final String  SF_DGN_CODE_UPZ = "SF_DGN_CODE_UPZ";  //上层转配交货单code   
//		static final String  LAST_FACT_DISP_WAREHID = "LAST_FACT_DISP_WAREHID";   //  最终发货仓库（出库规则使用）规则用到 
//		static final String  LAST_FACT_RCV_WAREHID = "LAST_FACT_RCV_WAREHID";  //  最终收货仓库（判断生成现货单时是否根据公式算价格） 
//		static final String  HAD_LOCK_WAREH = "HAD_LOCK_WAREH";   //  SfSchTaskExecOosVo的一个字段，在老erp调配单和计划分配单判断是否增加已分配库存的时候用到  规则用到
//		static final String  PUB_B2C_DOC_CODE = "PUB_B2C_DOC_CODE";   //  B2C订单号，保存单据流用到  
		//-----
	    static final String ORIGIN_DOC_TYPE_FIRST = "ORIGIN_DOC_TYPE_FIRST"; //新ERP源头单据类型
	    static final String ORIGIN_DOC_NUM_FIRST = "ORIGIN_DOC_NUM_FIRST"; //新ERP源头单据code
	    static final String ORIGIN_UNIT_ID_FIRST = "SFORIGIN_UNIT_ID_FIRST"; //新ERP源头单据组织
	    static final String ORIGIN_DOC_TYPE = "ORIGIN_DOC_TYPE"; //新ERP源头单据类型
	    static final String ORIGIN_DOC_NUM = "ORIGIN_DOC_NUM"; //新ERP源头单据code
	    static final String ORIGIN_UNIT_ID = "SFORIGIN_UNIT_ID"; //新ERP源头单据组织
	    static final String AMOUNT_PRECISION = "AMOUNT_PRECISION"; //金额精确度
	    static final String DATA_FLOW_SEQID = "DATA_FLOW_SEQID"; //流程编号，单据流会用到
	    static final String DATA_FLOW_BATCH_NO = "DATA_FLOW_BATCH_NO"; //单据流批号，单据流会用到
	    static final String TH_GDN_NUM = "TH_GDN_NUM"; //退货出库单据号
	}
	
	interface DIFFER_FLAG {
		static final String IS_DIFFER_IN = "1";//可配差
		static final String IS_DIFFER_OVER = "2";//已完成
	}
	
	interface SRC_DOC_TYPE {
		static final String IS_TH = "2";//直营退货
	}
	
	/**
	 * 公用变量 - 新ERP
	 */
	interface BASE_EXTRA_NEW {
		
	}
	
}
