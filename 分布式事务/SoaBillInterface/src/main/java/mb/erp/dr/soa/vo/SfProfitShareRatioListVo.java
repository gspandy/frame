package mb.erp.dr.soa.vo;

import java.util.Date;

/**
 * 利益分享vo
 * @author     余从玉
 * @version    1.0, 2014-11-28
 * @see         SfProfitShareRatioListVo
 * @since       全流通改造
 */
public class SfProfitShareRatioListVo  extends NewBaseBizVo {
    /**
	 * 
	 */
	private static final long serialVersionUID = 965930367109017943L;

	private Integer id;

    private String code;

    private Date efficientDate;

    private Date loseEfficientDate;

    private String businessSource;

    private String deliveryMode;

    private String isGlobal;

    private Date approveDate;

    private Integer bfOrgId;

    private Double dispRatio;

    private Double rcvRatio;

    private String docType;
    private Double serverRatio; // 中间方比例

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getEfficientDate() {
        return efficientDate;
    }

    public void setEfficientDate(Date efficientDate) {
        this.efficientDate = efficientDate;
    }

    public Date getLoseEfficientDate() {
        return loseEfficientDate;
    }

    public void setLoseEfficientDate(Date loseEfficientDate) {
        this.loseEfficientDate = loseEfficientDate;
    }

    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource == null ? null : businessSource.trim();
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode == null ? null : deliveryMode.trim();
    }

    public String getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(String isGlobal) {
        this.isGlobal = isGlobal == null ? null : isGlobal.trim();
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Integer getBfOrgId() {
        return bfOrgId;
    }

    public void setBfOrgId(Integer bfOrgId) {
        this.bfOrgId = bfOrgId;
    }

    public Double getDispRatio() {
        return dispRatio;
    }

    public void setDispRatio(Double dispRatio) {
        this.dispRatio = dispRatio;
    }

    public Double getRcvRatio() {
        return rcvRatio;
    }

    public void setRcvRatio(Double rcvRatio) {
        this.rcvRatio = rcvRatio;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

	public Double getServerRatio() {
		return serverRatio;
	}

	public void setServerRatio(Double serverRatio) {
		this.serverRatio = serverRatio;
	}
    
}