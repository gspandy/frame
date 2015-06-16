package mb.erp.dr.soa.constant;

/**
 * 返回消息常量定义
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         O2OMsgConstant
 * @since       全流通改造
 */
public interface O2OMsgConstant {

	/**
	 * 错误编码
	 */
	interface MSG_CODE{
		static final String SUCCESS = "success"; //成功
		static final String ERROR = "error"; //失败
	}
	/**
	 * 单据业务类型定义
	 */
	interface BIZTYPE {
		/**保存 */
		static final String   SAVE  = "save";
		/**确认 */
		static final String   CONFIRM = "confirm";
		/**审核 */
		static final String   AUDIT = "audit";
		/**取消 */
		static final String   CANCEL  = "cancel";
		/**原始单据配货中 */
		static final String   ORDERAG = "orderAG";
		/**原始单据已配货 */
		static final String   ORDERAD = "orderAD";
		/**原始单据发货中 */
		static final String   ORDERDG = "orderDG";
		/**原始单据已发货 */
		static final String   ORDERDD = "orderDD";
		/**原始单据收货中 */
		static final String   ORDERRG = "orderRG";
		/**原始单据已收货 */
		static final String   ORDERRD = "orderRD";
		/**原始单据过账中*/
		static final String   ORDERFI = "orderFi";
		/**原始单据出库冲单 */
		static final String   ORDERGDDL = "orderGDDL";
		/**原始单据入库冲单 */
		static final String   ORDERGDRV = "orderGDRV";
		/**原始单据出库撤单 */
		static final String   ORDERGDDLCANCEL = "orderGDDLCancel";
		/**原始单据入库撤单 */
		static final String   ORDERGDRVCANCEL = "orderGDRVCancel";
		/**单据出库 */
		static final String   BILLGDN = "billGdn";
		/**单据入库 */
		static final String   BILLGRN = "billGrn";
		/**单据出库冲单 */
		static final String   BILLGDDL = "billGDDL";
		/**单据入库冲单 */
		static final String   BILLGDRV = "billGDRV";
	}
	
}
