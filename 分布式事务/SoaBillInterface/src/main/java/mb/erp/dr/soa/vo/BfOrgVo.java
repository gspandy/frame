package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP组织表实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-11-17
 * @see BfOrgVo
 * @since 全流通改造
 */
public class BfOrgVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1288039842420401966L;

	private Long id;

	private String name;

	private String code;

	private String oldId;

	private String inputCode;

	private String sortCode;

	private Long bfOrgTypeId;

	private String country;

	private String province;

	private String city;

	private String address;

	private String postcode;

	private String phNum;

	private String faxNum;

	private String emailAddr;

	private String website;

	private String regionCode;

	private String simpleAddr;

	private String detaAddress;

	private String status;

	private Long ownerId;

	private String remark;

	private Date lastModifiedDate;

	private String county;

	private String toUser;

	private String toUserTel;

	private String toUserPhone;

	private String fromUser;

	private String fromUserTel;

	private String actProvince;

	private String actCity;

	private String actCounty;

	private String actDtlAddress;

	private String businessRange;

	private String actCountry;

	private String profitCenter;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId == null ? null : oldId.trim();
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

	public Long getBfOrgTypeId() {
		return bfOrgTypeId;
	}

	public void setBfOrgTypeId(Long bfOrgTypeId) {
		this.bfOrgTypeId = bfOrgTypeId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county == null ? null : county.trim();
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser == null ? null : toUser.trim();
	}

	public String getToUserTel() {
		return toUserTel;
	}

	public void setToUserTel(String toUserTel) {
		this.toUserTel = toUserTel == null ? null : toUserTel.trim();
	}

	public String getToUserPhone() {
		return toUserPhone;
	}

	public void setToUserPhone(String toUserPhone) {
		this.toUserPhone = toUserPhone == null ? null : toUserPhone.trim();
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser == null ? null : fromUser.trim();
	}

	public String getFromUserTel() {
		return fromUserTel;
	}

	public void setFromUserTel(String fromUserTel) {
		this.fromUserTel = fromUserTel == null ? null : fromUserTel.trim();
	}

	public String getActProvince() {
		return actProvince;
	}

	public void setActProvince(String actProvince) {
		this.actProvince = actProvince == null ? null : actProvince.trim();
	}

	public String getActCity() {
		return actCity;
	}

	public void setActCity(String actCity) {
		this.actCity = actCity == null ? null : actCity.trim();
	}

	public String getActCounty() {
		return actCounty;
	}

	public void setActCounty(String actCounty) {
		this.actCounty = actCounty == null ? null : actCounty.trim();
	}

	public String getActDtlAddress() {
		return actDtlAddress;
	}

	public void setActDtlAddress(String actDtlAddress) {
		this.actDtlAddress = actDtlAddress == null ? null : actDtlAddress
				.trim();
	}

	public String getBusinessRange() {
		return businessRange;
	}

	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange == null ? null : businessRange
				.trim();
	}

	public String getActCountry() {
		return actCountry;
	}

	public void setActCountry(String actCountry) {
		this.actCountry = actCountry == null ? null : actCountry.trim();
	}

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter == null ? null : profitCenter.trim();
	}
}