package mb.mba.core.bean;

import java.math.BigDecimal;
import java.util.Date;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;

/**
 * 
 * @描述 多货主库存增减的辅助类
 * @author sun
 * @date 2015年6月1日
 */
public class InventoryHelper {

	private Long id;
	/**
	 * 出库or入库
	 */
	private String type;
	/**
	 * 出入库方式
	 */
	private String code;
	/**
	 * 商品code
	 */
	private String goodsCode;
	/**
	 * 卖家
	 */
	private String venderCode;
	private String venderWarehCode;
	/**
	 * 买家
	 */
	private String vendeeCode;
	private String vendeeWarehCode;

	/**
	 * 批次信息
	 */
	private String bathInfo;
	/**
	 * 交易量
	 */
	private BigDecimal inventoryQuantity;
	/**
	 * 库存加or减:true=add,false=sub
	 */
	private boolean calcquantity;
	/**
	 * 调拨
	 */
	private BigDecimal alloOntheway;
	private boolean calcalloOntheway;
	/**
	 * 在购
	 */
	private BigDecimal purchOntheway;
	private boolean calcpurchOntheway;
	/**
	 * 库存地
	 */
	private String inventoryPlace;// inventory_place
	/**
	 * 记账仓
	 */
	private String acwarehCode;

	private Date modTime;
	
	private String modOper;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getVenderCode() {
		return venderCode;
	}

	public void setVenderCode(String venderCode) {
		this.venderCode = venderCode;
	}

	public String getVenderWarehCode() {
		return venderWarehCode;
	}

	public void setVenderWarehCode(String venderWarehCode) {
		this.venderWarehCode = venderWarehCode;
	}

	public String getVendeeCode() {
		return vendeeCode;
	}

	public void setVendeeCode(String vendeeCode) {
		this.vendeeCode = vendeeCode;
	}

	public String getVendeeWarehCode() {
		return vendeeWarehCode;
	}

	public void setVendeeWarehCode(String vendeeWarehCode) {
		this.vendeeWarehCode = vendeeWarehCode;
	}

	public String getBathInfo() {
		return bathInfo;
	}

	public void setBathInfo(String bathInfo) {
		this.bathInfo = bathInfo;
	}

	public boolean isCalcquantity() {
		return calcquantity;
	}

	public void setCalcquantity(boolean calcquantity) {
		this.calcquantity = calcquantity;
	}

	public BigDecimal getAlloOntheway() {
		return alloOntheway;
	}

	public void setAlloOntheway(BigDecimal alloOntheway) {
		this.alloOntheway = alloOntheway;
	}

	public boolean isCalcalloOntheway() {
		return calcalloOntheway;
	}

	public void setCalcalloOntheway(boolean calcalloOntheway) {
		this.calcalloOntheway = calcalloOntheway;
	}

	public BigDecimal getPurchOntheway() {
		return purchOntheway;
	}

	public void setPurchOntheway(BigDecimal purchOntheway) {
		this.purchOntheway = purchOntheway;
	}

	public boolean isCalcpurchOntheway() {
		return calcpurchOntheway;
	}

	public void setCalcpurchOntheway(boolean calcpurchOntheway) {
		this.calcpurchOntheway = calcpurchOntheway;
	}

	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String getInventoryPlace() {
		return inventoryPlace;
	}

	public void setInventoryPlace(String inventoryPlace) {
		this.inventoryPlace = inventoryPlace;
	}

	public String getAcwarehCode() {
		return acwarehCode;
	}

	public void setAcwarehCode(String acwarehCode) {
		this.acwarehCode = acwarehCode;
	}

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public String getModOper() {
		return modOper;
	}

	public void setModOper(String modOper) {
		this.modOper = modOper;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public InventoryHelper() {
		super();
	}

	/**
	 * 
	 * @param goodsCode 商品编码
	 * @param bathInfo 批次
	 * @param quantity 库存数量
	 * @param calcquantity 库存数量处理?
	 * @param alloOntheway 调拨在途量
	 * @param calcalloOntheway 调拨在途量处理?
	 * @param purchOntheway 在购在途量
	 * @param calcpurchOntheway 在购在途量处理?
	 * @param inwareh_code 库存仓
	 * @param acwareh_code 记账仓
	 */
	public InventoryHelper(String goodsCode, String bathInfo,
			BigDecimal quantity, boolean calcquantity, BigDecimal alloOntheway,
			boolean calcalloOntheway, BigDecimal purchOntheway,
			boolean calcpurchOntheway, String inwareh_code, String acwareh_code) {
		super();
		this.goodsCode = goodsCode;
		this.bathInfo = bathInfo;
		this.inventoryQuantity = quantity;
		this.calcquantity = calcquantity;
		this.alloOntheway = alloOntheway;
		this.calcalloOntheway = calcalloOntheway;
		this.purchOntheway = purchOntheway;
		this.calcpurchOntheway = calcpurchOntheway;
		this.inventoryPlace = inwareh_code;
		this.acwarehCode = acwareh_code;
	}

	public static InventoryHelper getInventoryHelper(TradesEntity trade,TradesDtlEntity tradedtl){
		String type=trade.getDocType();//单据类型:出库or入库
		if(type.equals("in")){
			return getAddTypeInventoryHelper(trade,tradedtl);
		}
		if(type.equals("out")){
			return getSubTypeInventoryHelper(trade,tradedtl);
		}
		return null;
	}


	private static InventoryHelper getSubTypeInventoryHelper(TradesEntity trade,TradesDtlEntity tradedtl) {
		String code=trade.getInoutwarehCode();
		InventoryHelper helper=null;
		switch(code){
			case "code1":{//
				helper=new InventoryHelper(tradedtl.getGoodsCode(),null,tradedtl.getQuantity().negate(),true,null,false,null,false,trade.getRelTradeWarehCode(),trade.getVenderWarehCode());
				break;
			}
			case "code2":{//
				helper=new InventoryHelper(tradedtl.getGoodsCode(),null,tradedtl.getQuantity().negate(),true,null,false,null,false,trade.getRelTradeWarehCode(),trade.getVenderWarehCode());
				break;
			}
		}
		return helper;
	}

	private static InventoryHelper getAddTypeInventoryHelper(TradesEntity trade,TradesDtlEntity tradedtl) {
		String code=trade.getInoutwarehCode();
		InventoryHelper helper=null;
		switch(code){
			case "code1":{//寄售采购入库
				helper=new InventoryHelper(tradedtl.getGoodsCode(),null,tradedtl.getQuantity(),true,null,false,null,false,trade.getRelTradeWarehCode(),trade.getVendeeWarehCode());
				break;
			}
			case "code2":{//寄售采购退货
				helper=new InventoryHelper(tradedtl.getGoodsCode(),null,tradedtl.getQuantity(),true,null,false,null,false,trade.getRelTradeWarehCode(),trade.getVendeeWarehCode());
				break;
			}
		}
		return helper;
	}
	public static void main(String[] args) {
		TradesDtlEntity dtl=new TradesDtlEntity();
		dtl.setQuantity(new BigDecimal(15));
		System.out.println(dtl.getQuantity().negate());
	}
}
