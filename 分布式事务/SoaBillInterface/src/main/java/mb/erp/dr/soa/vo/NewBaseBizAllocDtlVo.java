package mb.erp.dr.soa.vo;

import java.io.Serializable;

public class NewBaseBizAllocDtlVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4393671930192684270L;
	
	private Long prodId; // 商品编码
	private String progress ; // 进度
	private String remark ; // 备注
	
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
