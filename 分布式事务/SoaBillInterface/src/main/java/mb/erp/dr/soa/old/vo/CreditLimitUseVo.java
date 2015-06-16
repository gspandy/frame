package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

import mb.erp.dr.soa.constant.O2OBillConstant.LimitType;

/**
 * 专项资金表
 * 
 * @author 陈志杰
 * @version 1.0, 2014-10-31
 * @see CreditLimitUseVo
 * @since 全流通改造
 */
public class CreditLimitUseVo implements Serializable {

	private static final long serialVersionUID = -5826138619158813219L;
	private String docNum; // 流水号
	private String unitId; // 上级组织编码
	private String vendeeId; // 下级组织编码
	private Date beginDate; // 开始日期
	private Date endDate; // 结束时间
	private LimitType limitType; // 资金类型
	private Double limitMoney; // 剩余金额
	private String remark; // 备注
	private Double allLimitMoney; // 金额额度
	private String docSetUpNum; // 编号
	
	private Double tranMoney;//发生金额，记录事务用

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public LimitType getLimitType() {
		return limitType;
	}

	public void setLimitType(LimitType limitType) {
		this.limitType = limitType;
	}

	public Double getLimitMoney() {
		return limitMoney;
	}

	public void setLimitMoney(Double limitMoney) {
		this.limitMoney = limitMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAllLimitMoney() {
		return allLimitMoney;
	}

	public void setAllLimitMoney(Double allLimitMoney) {
		this.allLimitMoney = allLimitMoney;
	}

	public String getDocSetUpNum() {
		return docSetUpNum;
	}

	public void setDocSetUpNum(String docSetUpNum) {
		this.docSetUpNum = docSetUpNum;
	}

	public Double getTranMoney() {
		return tranMoney;
	}

	public void setTranMoney(Double tranMoney) {
		this.tranMoney = tranMoney;
	}
}