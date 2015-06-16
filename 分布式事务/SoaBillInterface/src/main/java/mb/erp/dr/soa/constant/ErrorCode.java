package mb.erp.dr.soa.constant;

/**
 * 错误码
 * 
 * @author sun
 * 
 */
public interface ErrorCode {

	// static final String SUCCESS = "success"; // 成功
	// static final String ERROR = "error"; // 失败
	/**
	 * 单据不存在
	 */
	static final String ER_ORDERNOTFOUND = "0";
	/**
	 * 组织不存在
	 */
	static final String ER_ORGNOTFOUND = "1";
	/**
	 * 商品不存在
	 */
	static final String ER_PRONOTFOUND = "2";
	/**
	 * 发货仓及接收门店问题
	 */
	static final String ER_WAREHORSHOP = "3";
	/**
	 * 加盟可支配金额不足
	 */
	static final String ER_INSUFFICIENTBALANCE = "4";
	/**
	 * 结算价格没有定义
	 */
	static final String ER_SETTLEMENTPRICE = "5";
	/**
	 * 往来账户不存在
	 */
	static final String ER_SETTLEMENTACCOUNT = "6";
	/**
	 * 默认值验证失败
	 */
	static final String ER_DEF = "7";
	/**
	 * 值为空
	 */
	static final String ER_NULLVALUE = "8";
	/**
	 * 订单进度异常
	 */
	static final String ER_PROGRESS = "9";
	/**
	 * 购货方下的所有门店是否已结转（结转日期往前推）
	 */
	static final String ER_CARRYOVER = "10";

	/**现货订单*/
	interface IDT {
		static final String DF = "100";
		//1xx
	}
	/**调配单*/
	interface TBN {
		static final String DF = "200";
		//2xx
	}
	/**计划配货单 */
	interface ADN {
		static final String DF = "300";
		//3xx
	}
	/**出库单*/
	interface GDN {
		static final String DF = "400";
		//4xx
	}
	/**到货通知单*/
	interface RVD {
		static final String DF = "500";
		//5xx
	}
	/**交货单*/
	interface DGN {
		static final String DF = "600";
		//6xx
	}
	/**入库单*/ 
	interface GRN {
		static final String DF = "700";
		//7xx
	}
	/**仓库*/ 
	interface WAREHOUSE {
		static final String DF = "800";
		//8xx
	}
	/**组织*/ 
	interface ORG {
		static final String DF = "900";
		/**加盟或直营*/
		//static final String DF_ = "900";
		/**入库组织不是代理商*/
		//static final String DF_ = "900";
		/**接收方组织不是代理商*/
		//static final String DF_ = "900";
	}
	/**价格及账户*/ 
	interface PRICE {
		static final String DF = "1000";
		//10xx
	}
//	interface ER_PROGRESS {
//		/**
//		 * （状态更新：AP 已审核 --> AG 配货中)
//		 */
//		static final String AG = "91"; 
//		/**
//		 * （状态更新：AG 配货中 --> AD 已配货)
//		 */
//		static final String AD = "92"; 
//		/**
//		 * （状态更新：AD 已配货 --> DG 发货中)
//		 */
//		static final String DG = "93"; 
//		/**
//		 * （状态更新：DG 发货中 --> DD 已发货)
//		 */
//		static final String DD = "94"; 
//		/**
//		 * （状态更新：DD 已发货 --> RG 收货中)
//		 */
//		static final String RG = "95"; 
//		/**
//		 * （状态更新：RG 收货中 --> RD 已收货)
//		 */
//		static final String RD = "96"; 
//	}
}
