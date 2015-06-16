package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

public class SysUnitVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4516410140283021330L;

	private String unitId;

    private String ownerId; // 上级组织

    private String hierarchy;

    private String name;

    private String inputCode;

    private String sortCode;

    private String postcode;

    private String address;

    private String phNum;

    private String faxNum;

    private String emailAddr;

    private String website;

    private String remark;

    private String oldId;

    private String regionCode;

//    private String unitBaProp;

    private String simpleAddr;

    private String detaAddress;

    private String contactName;

    private String oprRemark;

    private Double idtCount;

    private String needSend;

    private String unitShortId;

    private String country;

    private String province;

    private String city;

    private String dispTspComId;

    private String dispRcvWarehId;

    private String unitNum;

    private Date updateTime;

    private String sapUnitId;

    private String isProject;

//    private String btsWmosFlag;

    private String profitCenter;

    private String businessRange;

    private String county;

    private String location;

    private String flag;
    
    private String cusId; //上级组织的贸易供货方

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy == null ? null : hierarchy.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode == null ? null : inputCode.trim();
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode == null ? null : sortCode.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum == null ? null : phNum.trim();
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum == null ? null : faxNum.trim();
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr == null ? null : emailAddr.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId == null ? null : oldId.trim();
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public String getSimpleAddr() {
        return simpleAddr;
    }

    public void setSimpleAddr(String simpleAddr) {
        this.simpleAddr = simpleAddr == null ? null : simpleAddr.trim();
    }

    public String getDetaAddress() {
        return detaAddress;
    }

    public void setDetaAddress(String detaAddress) {
        this.detaAddress = detaAddress == null ? null : detaAddress.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getOprRemark() {
        return oprRemark;
    }

    public void setOprRemark(String oprRemark) {
        this.oprRemark = oprRemark == null ? null : oprRemark.trim();
    }

    public Double getIdtCount() {
        return idtCount;
    }

    public void setIdtCount(Double idtCount) {
        this.idtCount = idtCount;
    }

    public String getNeedSend() {
        return needSend;
    }

    public void setNeedSend(String needSend) {
        this.needSend = needSend == null ? null : needSend.trim();
    }

    public String getUnitShortId() {
        return unitShortId;
    }

    public void setUnitShortId(String unitShortId) {
        this.unitShortId = unitShortId == null ? null : unitShortId.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDispTspComId() {
        return dispTspComId;
    }

    public void setDispTspComId(String dispTspComId) {
        this.dispTspComId = dispTspComId == null ? null : dispTspComId.trim();
    }

    public String getDispRcvWarehId() {
        return dispRcvWarehId;
    }

    public void setDispRcvWarehId(String dispRcvWarehId) {
        this.dispRcvWarehId = dispRcvWarehId == null ? null : dispRcvWarehId.trim();
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum == null ? null : unitNum.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSapUnitId() {
        return sapUnitId;
    }

    public void setSapUnitId(String sapUnitId) {
        this.sapUnitId = sapUnitId == null ? null : sapUnitId.trim();
    }

    public String getIsProject() {
        return isProject;
    }

    public void setIsProject(String isProject) {
        this.isProject = isProject == null ? null : isProject.trim();
    }

    public String getProfitCenter() {
        return profitCenter;
    }

    public void setProfitCenter(String profitCenter) {
        this.profitCenter = profitCenter == null ? null : profitCenter.trim();
    }

    public String getBusinessRange() {
        return businessRange;
    }

    public void setBusinessRange(String businessRange) {
        this.businessRange = businessRange == null ? null : businessRange.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

	public String getCusId() {
		return cusId;
	}

	public void setCusId(String cusId) {
		this.cusId = cusId;
	}
}