package mb.erp.dr.soa.vo.common;


/**
 * 返回值vo
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         MsgListVo
 * @since       全流通改造
 */
public class MsgListVo {
	/**
	 * 消息编码
	 */
	private String code ;
	
	/**
	 * 业务类型
	 */
	private String bizType;
	
	/**
	 * 商品编码
	 */
	private String prodId;
	
	/**
	 * 组织编码
	 */
	private String wunitId;
	
	/**
	 * 价格
	 */
	private Double price;
	
	/**
	 * 折率
	 */
	private Double discRate;
	
	/**
	 * 消息内容
	 */
	private String msg;
	
	/**
	 * 默认构造器
	 */
	public MsgListVo(){}
	
	public MsgListVo(String code,String msg,String bizType){
		this.code = code;
		this.msg = msg;
		this.bizType = bizType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getWunitId() {
		return wunitId;
	}

	public void setWunitId(String wunitId) {
		this.wunitId = wunitId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscRate() {
		return discRate;
	}

	public void setDiscRate(Double discRate) {
		this.discRate = discRate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
}
