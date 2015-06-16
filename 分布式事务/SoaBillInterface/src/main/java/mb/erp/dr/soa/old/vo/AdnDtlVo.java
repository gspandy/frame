package mb.erp.dr.soa.old.vo;

import java.util.Date;

/**
 * 计划配货单详情实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         AdnDtlVo
 * @since       全流通改造
 */
public class AdnDtlVo extends BaseBizDtlVo {

	private static final long serialVersionUID = -6345073181429718218L;

	private String venderId; //供货方编码

    private String adnNum; //计划配货单编号

    private Double admQty; //配货数量

    private Double delivQty; //发货数量

    private Double rcvQty; //收货数量

    private String remark; //备注

    private Double qtyCurComm; //现货预留数量

    private Date docDate; //

    public Double getAdmQty() {
        return admQty;
    }

    public void setAdmQty(Double admQty) {
        this.admQty = admQty;
    }

    public Double getDelivQty() {
        return delivQty;
    }

    public void setDelivQty(Double delivQty) {
        this.delivQty = delivQty;
    }

    public Double getRcvQty() {
        return rcvQty;
    }

    public void setRcvQty(Double rcvQty) {
        this.rcvQty = rcvQty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Double getQtyCurComm() {
        return qtyCurComm;
    }

    public void setQtyCurComm(Double qtyCurComm) {
        this.qtyCurComm = qtyCurComm;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

	public String getVenderId() {
		return venderId;
	}

	public void setVenderId(String venderId) {
		this.venderId = venderId;
	}

	public String getAdnNum() {
		return adnNum;
	}

	public void setAdnNum(String adnNum) {
		this.adnNum = adnNum;
	}
}