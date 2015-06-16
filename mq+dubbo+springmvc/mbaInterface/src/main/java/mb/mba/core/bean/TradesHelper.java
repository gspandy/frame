package mb.mba.core.bean;

import java.math.BigDecimal;

import mb.mba.core.entity.TradesEntity;

/**
 * 
 * @描述 出入库交易的辅助类
 * @author sun
 * @date 2015年6月1日
 */
public class TradesHelper {

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
	 * 交易量
	 */
	private Long quantity;
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
	 * 传入sap
	 */
	private boolean tosap;

	/**
	 * 批次信息
	 */
	private String bathInfo;
	/**
	 * 库存加or减:true=add,false=sub
	 */
	private boolean addorsub;
	/**
	 * 调拨
	 */
	private BigDecimal alloOntheway;
	private boolean calalloOntheway;
	/**
	 * 在购
	 */
	private BigDecimal purchOntheway;
	private boolean calpurchOntheway;
	/**
	 * 库存地
	 */
	private String inventoryPlace;
	/**
	 * 价格
	 */
	private BigDecimal price;
	private boolean calprice;
	/**
	 * 税率
	 */
	private BigDecimal taxrate;
	private boolean caltaxrate;
	/**
	 * 折让
	 */
	private BigDecimal disamount;
	private boolean caldisamount;
	/**
	 * 往来账
	 */
	private BigDecimal currentaccount;
	private boolean calcurrentaccount;
	/**
	 * 成本
	 */
	private BigDecimal unitcost;
	private boolean calunitcost;
	/**
	 * 源头单据
	 */
	private String srcDocCode;
	private String srcDocNum;
	/**
	 * 直接来源单据
	 */
	private String fromDocCode;
	private String fromDocNum;


	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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

	public boolean isTosap() {
		return tosap;
	}

	public void setTosap(boolean tosap) {
		this.tosap = tosap;
	}

	public String getBathInfo() {
		return bathInfo;
	}

	public void setBathInfo(String bathInfo) {
		this.bathInfo = bathInfo;
	}

	public boolean isAddorsub() {
		return addorsub;
	}

	public void setAddorsub(boolean addorsub) {
		this.addorsub = addorsub;
	}

	public BigDecimal getAlloOntheway() {
		return alloOntheway;
	}

	public void setAlloOntheway(BigDecimal alloOntheway) {
		this.alloOntheway = alloOntheway;
	}

	public boolean isCalalloOntheway() {
		return calalloOntheway;
	}

	public void setCalalloOntheway(boolean calalloOntheway) {
		this.calalloOntheway = calalloOntheway;
	}

	public BigDecimal getPurchOntheway() {
		return purchOntheway;
	}

	public void setPurchOntheway(BigDecimal purchOntheway) {
		this.purchOntheway = purchOntheway;
	}

	public boolean isCalpurchOntheway() {
		return calpurchOntheway;
	}

	public void setCalpurchOntheway(boolean calpurchOntheway) {
		this.calpurchOntheway = calpurchOntheway;
	}

	public String getInventoryPlace() {
		return inventoryPlace;
	}

	public void setInventoryPlace(String inventoryPlace) {
		this.inventoryPlace = inventoryPlace;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isCalprice() {
		return calprice;
	}

	public void setCalprice(boolean calprice) {
		this.calprice = calprice;
	}

	public BigDecimal getTaxrate() {
		return taxrate;
	}

	public void setTaxrate(BigDecimal taxrate) {
		this.taxrate = taxrate;
	}

	public boolean isCaltaxrate() {
		return caltaxrate;
	}

	public void setCaltaxrate(boolean caltaxrate) {
		this.caltaxrate = caltaxrate;
	}

	public BigDecimal getDisamount() {
		return disamount;
	}

	public void setDisamount(BigDecimal disamount) {
		this.disamount = disamount;
	}

	public boolean isCaldisamount() {
		return caldisamount;
	}

	public void setCaldisamount(boolean caldisamount) {
		this.caldisamount = caldisamount;
	}

	public BigDecimal getCurrentaccount() {
		return currentaccount;
	}

	public void setCurrentaccount(BigDecimal currentaccount) {
		this.currentaccount = currentaccount;
	}

	public boolean isCalcurrentaccount() {
		return calcurrentaccount;
	}

	public void setCalcurrentaccount(boolean calcurrentaccount) {
		this.calcurrentaccount = calcurrentaccount;
	}

	public BigDecimal getUnitcost() {
		return unitcost;
	}

	public void setUnitcost(BigDecimal unitcost) {
		this.unitcost = unitcost;
	}

	public boolean isCalunitcost() {
		return calunitcost;
	}

	public void setCalunitcost(boolean calunitcost) {
		this.calunitcost = calunitcost;
	}

	public String getSrcDocCode() {
		return srcDocCode;
	}

	public void setSrcDocCode(String srcDocCode) {
		this.srcDocCode = srcDocCode;
	}

	public String getSrcDocNum() {
		return srcDocNum;
	}

	public void setSrcDocNum(String srcDocNum) {
		this.srcDocNum = srcDocNum;
	}

	public String getFromDocCode() {
		return fromDocCode;
	}

	public void setFromDocCode(String fromDocCode) {
		this.fromDocCode = fromDocCode;
	}

	public String getFromDocNum() {
		return fromDocNum;
	}

	public void setFromDocNum(String fromDocNum) {
		this.fromDocNum = fromDocNum;
	}

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

	/**
	 * 交易单据各数据是否需要处理
	 * @param tosap 传入sap
	 * @param addorsub 加减
	 * @param calpurchOntheway 在购
	 * @param calalloOntheway 调拨
	 * @param calprice 价格
	 * @param caltaxrate 税率
	 * @param caldisamount 折让
	 * @param calcurrentaccount 往来账
	 * @param calunitcost 成本
	 */
	public TradesHelper(boolean tosap, boolean addorsub,
			boolean calpurchOntheway, boolean calalloOntheway,
			boolean calprice, boolean caltaxrate, boolean caldisamount,
			boolean calcurrentaccount, boolean calunitcost) {
		super();
		this.tosap = tosap;
		this.addorsub = addorsub;
		this.calalloOntheway = calalloOntheway;
		this.calpurchOntheway = calpurchOntheway;
		this.calprice = calprice;
		this.caltaxrate = caltaxrate;
		this.caldisamount = caldisamount;
		this.calcurrentaccount = calcurrentaccount;
		this.calunitcost = calunitcost;
	}
	
	public TradesHelper() {
		super();
	}
	/**
	 * 根据出入库方式获得辅助类 :哪些业务逻辑需要处理
	 * @param entity
	 * @return TradesHelper
	 */
	public static TradesHelper getTradesHelper(TradesEntity entity) {
		String type=entity.getDocType();//单据类型:出库or入库
		if(type.equals("in")){
			return getInTypeTradesHelper(entity);
		}
		if(type.equals("out")){
			return getOutTypeTradesHelper(entity);
		}
		return null;
	}
	private static TradesHelper getInTypeTradesHelper(TradesEntity entity) {
		TradesHelper helper=null;
		String code=entity.getInoutwarehCode();
		switch(code){
			case "COMM":{//委托生产入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "PURC":{//采购入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGOR":{//代理商订货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGOF":{//[期货]代理商订货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGCR":{//下级代理商退货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGRT":{//下级代理商调回入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "WDRW":{//物资退领入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "SHOR":{//门店订货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "SHCR":{//下属直营门店退货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "TRAN":{//调拨入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "RTCR":{//顾客零售退货入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "ADJS":{//盘存调整增加
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "PRES":{//赠送入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "OTHR":{//其它入库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
		}
		return helper;
	}
	private static TradesHelper getOutTypeTradesHelper(TradesEntity entity) {
		TradesHelper helper=null;
		String code=entity.getInoutwarehCode();
		switch(code){
			case "AGOR":{//配发到下级代理商出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGOF":{//[期货]配发到下级代理商出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGCR":{//退货到上级代理商出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "AGRT":{//调配到上级代理商出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "CMCR":{//委托生产退货出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "PUCR":{//采购退货出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "APPL":{//物资领用出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "SHOR":{//配发到下属直营门店出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "TRAN":{//调拨出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "SHCR":{//门店退货到宿主代理商出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "RETL":{//门店零售给顾客出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "ADJS":{//盘存调整减少
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "PRES":{//赠送出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
			case "OTHR":{//其它出库
				helper=new TradesHelper(true, false, true, false, true, true, false, false, false);
				break;
			}
		}
		return helper;
	}
}
