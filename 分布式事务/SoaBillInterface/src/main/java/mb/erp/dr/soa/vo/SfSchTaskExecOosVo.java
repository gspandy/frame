package mb.erp.dr.soa.vo;

import java.util.List;

import mb.erp.dr.soa.old.vo.BaseBizVo;

/**
 * MQ实体类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         SfSchTaskExecOosVo
 * @since       全流通改造
 */
public class SfSchTaskExecOosVo extends BaseBizVo{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7894349409896484853L;

	private Long id;

    private Long sfSchTaskExecId;

    private String dispWarehCode;

    private String shopCode; //最终收货仓库

    private String isNeedLockWareh;

    private String isAtuoGdn;

    private String isAtuoGrn;

    private Double fillRate;

    private String isOldErp;

    private String rcvUser;

    private String rcvAddress;

    private String rcvPhoneno;

    private String dataSource;

    private String venderCode;

    private String vendeeCode;

    private String idtCode;

    private Double orderVal;

    private String hadLockWareh;

    private String isOos;

    private String srcDocType;

    private String srcUnitCode;

    private String srcDocCode;

    private String isOnline;

    private String b2cDocCode;

    private String osDocCode;
    
    private Double rcvRatio;
    
    private List<SfSchTaskExecOosDtlVo> lstSfSchTaskExecOosDtls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSfSchTaskExecId() {
        return sfSchTaskExecId;
    }

    public void setSfSchTaskExecId(Long sfSchTaskExecId) {
        this.sfSchTaskExecId = sfSchTaskExecId;
    }

    public String getDispWarehCode() {
        return dispWarehCode;
    }

    public void setDispWarehCode(String dispWarehCode) {
        this.dispWarehCode = dispWarehCode == null ? null : dispWarehCode.trim();
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getIsNeedLockWareh() {
        return isNeedLockWareh;
    }

    public void setIsNeedLockWareh(String isNeedLockWareh) {
        this.isNeedLockWareh = isNeedLockWareh == null ? null : isNeedLockWareh.trim();
    }

    public String getIsAtuoGdn() {
        return isAtuoGdn;
    }

    public void setIsAtuoGdn(String isAtuoGdn) {
        this.isAtuoGdn = isAtuoGdn == null ? null : isAtuoGdn.trim();
    }

    public String getIsAtuoGrn() {
        return isAtuoGrn;
    }

    public void setIsAtuoGrn(String isAtuoGrn) {
        this.isAtuoGrn = isAtuoGrn == null ? null : isAtuoGrn.trim();
    }

    public Double getFillRate() {
        return fillRate;
    }

    public void setFillRate(Double fillRate) {
        this.fillRate = fillRate;
    }

    public String getIsOldErp() {
        return isOldErp;
    }

    public void setIsOldErp(String isOldErp) {
        this.isOldErp = isOldErp == null ? null : isOldErp.trim();
    }

    public String getRcvUser() {
        return rcvUser;
    }

    public void setRcvUser(String rcvUser) {
        this.rcvUser = rcvUser == null ? null : rcvUser.trim();
    }

    public String getRcvAddress() {
        return rcvAddress;
    }

    public void setRcvAddress(String rcvAddress) {
        this.rcvAddress = rcvAddress == null ? null : rcvAddress.trim();
    }

    public String getRcvPhoneno() {
        return rcvPhoneno;
    }

    public void setRcvPhoneno(String rcvPhoneno) {
        this.rcvPhoneno = rcvPhoneno == null ? null : rcvPhoneno.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public String getVenderCode() {
        return venderCode;
    }

    public void setVenderCode(String venderCode) {
        this.venderCode = venderCode == null ? null : venderCode.trim();
    }

    public String getVendeeCode() {
        return vendeeCode;
    }

    public void setVendeeCode(String vendeeCode) {
        this.vendeeCode = vendeeCode == null ? null : vendeeCode.trim();
    }

    public String getIdtCode() {
        return idtCode;
    }

    public void setIdtCode(String idtCode) {
        this.idtCode = idtCode == null ? null : idtCode.trim();
    }

    public Double getOrderVal() {
        return orderVal;
    }

    public void setOrderVal(Double orderVal) {
        this.orderVal = orderVal;
    }

    public String getHadLockWareh() {
        return hadLockWareh;
    }

    public void setHadLockWareh(String hadLockWareh) {
        this.hadLockWareh = hadLockWareh == null ? null : hadLockWareh.trim();
    }

    public String getIsOos() {
        return isOos;
    }

    public void setIsOos(String isOos) {
        this.isOos = isOos == null ? null : isOos.trim();
    }

    public String getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(String srcDocType) {
        this.srcDocType = srcDocType == null ? null : srcDocType.trim();
    }

    public String getSrcUnitCode() {
        return srcUnitCode;
    }

    public void setSrcUnitCode(String srcUnitCode) {
        this.srcUnitCode = srcUnitCode == null ? null : srcUnitCode.trim();
    }

    public String getSrcDocCode() {
        return srcDocCode;
    }

    public void setSrcDocCode(String srcDocCode) {
        this.srcDocCode = srcDocCode == null ? null : srcDocCode.trim();
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline == null ? null : isOnline.trim();
    }

    public String getB2cDocCode() {
        return b2cDocCode;
    }

    public void setB2cDocCode(String b2cDocCode) {
        this.b2cDocCode = b2cDocCode == null ? null : b2cDocCode.trim();
    }

    public String getOsDocCode() {
        return osDocCode;
    }

    public void setOsDocCode(String osDocCode) {
        this.osDocCode = osDocCode == null ? null : osDocCode.trim();
    }

	public List<SfSchTaskExecOosDtlVo> getLstSfSchTaskExecOosDtls() {
		return lstSfSchTaskExecOosDtls;
	}

	public void setLstSfSchTaskExecOosDtls(
			List<SfSchTaskExecOosDtlVo> lstSfSchTaskExecOosDtls) {
		this.lstSfSchTaskExecOosDtls = lstSfSchTaskExecOosDtls;
	}

	public Double getRcvRatio() {
		return rcvRatio;
	}

	public void setRcvRatio(Double rcvRatio) {
		this.rcvRatio = rcvRatio;
	}

}