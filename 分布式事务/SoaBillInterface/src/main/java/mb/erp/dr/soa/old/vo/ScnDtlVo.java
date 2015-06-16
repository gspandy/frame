package mb.erp.dr.soa.old.vo;

import java.util.Date;

/**
 * 退货单明细实体类
 * 
 * @author     陈志杰
 * @version    1.0, 2015-03-18
 * @see         ScnDtlVo
 * @since       全流通改造
 */
public class ScnDtlVo extends BaseBizDtlVo {
	private static final long serialVersionUID = 8119678456952158183L;

	private String vendeeId;

	private String scnNum;

	private Double crQty;

	private Double delivQty;

	private Double rcvQty;

	private Double idtfQlgdQty;

	private Double idtfFxrqQty;

	private Double idtfDmgdQty;

	private Double idtfInfrQty;

	private Double accQty;

	private Double fee;

	private String vendeeRemark;

	private String venderRemark;

	private Double passedQty;

	private String iszp;

	private String iszpcl;

	private String iscp;

	private String iswsp;

	private Date docDate;

	private Double scnLockedQty;

	private Double idtfLaacQty;

	public String getVendeeId() {
		return vendeeId;
	}

	public void setVendeeId(String vendeeId) {
		this.vendeeId = vendeeId;
	}

	public String getScnNum() {
		return scnNum;
	}

	public void setScnNum(String scnNum) {
		this.scnNum = scnNum;
	}

	public Double getCrQty() {
		return crQty;
	}

	public void setCrQty(Double crQty) {
		this.crQty = crQty;
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

	public Double getIdtfQlgdQty() {
		return idtfQlgdQty;
	}

	public void setIdtfQlgdQty(Double idtfQlgdQty) {
		this.idtfQlgdQty = idtfQlgdQty;
	}

	public Double getIdtfFxrqQty() {
		return idtfFxrqQty;
	}

	public void setIdtfFxrqQty(Double idtfFxrqQty) {
		this.idtfFxrqQty = idtfFxrqQty;
	}

	public Double getIdtfDmgdQty() {
		return idtfDmgdQty;
	}

	public void setIdtfDmgdQty(Double idtfDmgdQty) {
		this.idtfDmgdQty = idtfDmgdQty;
	}

	public Double getIdtfInfrQty() {
		return idtfInfrQty;
	}

	public void setIdtfInfrQty(Double idtfInfrQty) {
		this.idtfInfrQty = idtfInfrQty;
	}

	public Double getAccQty() {
		return accQty;
	}

	public void setAccQty(Double accQty) {
		this.accQty = accQty;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getVendeeRemark() {
		return vendeeRemark;
	}

	public void setVendeeRemark(String vendeeRemark) {
		this.vendeeRemark = vendeeRemark == null ? null : vendeeRemark.trim();
	}

	public String getVenderRemark() {
		return venderRemark;
	}

	public void setVenderRemark(String venderRemark) {
		this.venderRemark = venderRemark == null ? null : venderRemark.trim();
	}

	public Double getPassedQty() {
		return passedQty;
	}

	public void setPassedQty(Double passedQty) {
		this.passedQty = passedQty;
	}

	public String getIszp() {
		return iszp;
	}

	public void setIszp(String iszp) {
		this.iszp = iszp == null ? null : iszp.trim();
	}

	public String getIszpcl() {
		return iszpcl;
	}

	public void setIszpcl(String iszpcl) {
		this.iszpcl = iszpcl == null ? null : iszpcl.trim();
	}

	public String getIscp() {
		return iscp;
	}

	public void setIscp(String iscp) {
		this.iscp = iscp == null ? null : iscp.trim();
	}

	public String getIswsp() {
		return iswsp;
	}

	public void setIswsp(String iswsp) {
		this.iswsp = iswsp == null ? null : iswsp.trim();
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Double getScnLockedQty() {
		return scnLockedQty;
	}

	public void setScnLockedQty(Double scnLockedQty) {
		this.scnLockedQty = scnLockedQty;
	}

	public Double getIdtfLaacQty() {
		return idtfLaacQty;
	}

	public void setIdtfLaacQty(Double idtfLaacQty) {
		this.idtfLaacQty = idtfLaacQty;
	}
}