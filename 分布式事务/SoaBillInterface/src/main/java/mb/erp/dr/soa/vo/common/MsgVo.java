package mb.erp.dr.soa.vo.common;

import java.io.Serializable;

import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;

/**
 * 方法执行完毕后，返回消息vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         MsgVo
 * @since       全流通改造
 */
public class MsgVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3663296239587292086L;
	
	/**
	 * 消息编码
	 */
	private String code ;
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 老ERP单据类型
	 */
	private BillType billType;
	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 折率
	 */
	private Double discRate;
	/**
	 * 单据编码
	 */
	private String billNum;
	/**
	 * 新ERP单据类型
	 */
	private NewBillType newBillType;
	
	/**
	 * 新ERP单据ID
	 */
	private Long newBillId;
	
	/**
	 * 默认构造器
	 */
	public MsgVo(){}
	
	public MsgVo(String code){
		this.code = code;
	}
	
	public MsgVo(String code,String msg,String bizType,BillType billType){
		this.code = code;
		this.msg = msg;
		this.bizType = bizType;
		this.billType=billType;
	}
	
	public MsgVo(String code,String msg,NewBillType newBillType,String bizType){
		this.code = code;
		this.msg = msg;
		this.bizType = bizType;
		this.newBillType=newBillType;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}

	public NewBillType getNewBillType() {
		return newBillType;
	}

	public void setNewBillType(NewBillType newBillType) {
		this.newBillType = newBillType;
	}

	public Long getNewBillId() {
		return newBillId;
	}

	public void setNewBillId(Long newBillId) {
		this.newBillId = newBillId;
	}
	
}
