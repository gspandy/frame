package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.Date;

public class ProdClsVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = -343160893687289601L;

	private String prodProp;

	private String prodSort;

	private String prodStyle;

	private String name;

	private String abbreviation;

	private String inputCode;

	private String anlyCode;

	private String description;

	private String advantage;

	private String pictFile;

	private String basicUom;

	private String model;

	private String orientation;

	private String forSeason;

	private String kind;

	private String isSpcProd;

	private String inCtrl;

	private Date fstStkDate;

	private Date lastStkDate;

	private Date prodDate;

	private Date onSaleDate;

	private Double unitLen;

	private Double unitWid;

	private Double unitHgt;

	private Double unitVol;

	private Double unitWgt;

	private String isPrivate;

	private String status;

	private String mainColor;

	private Integer prodYear;

	private String prodSeason;

	private String prodTheme;

	private String dispArea;

	private String prodClass;

	private String prodPartm;

	private String prodGroup;

	private String prodSubGroup;

	private String prodSorts;

	private String prodShopModel;

	private Double onBrandPrc;

	private String remark;

	private String series;

	private String planBatch;

	private String module;

	private String motif;

	private String year;

	private String remark1;

	private String remark2;

	private String remark3;

	private String prodClsId;

	private String prodClsNum;

	private String brandGrpId;

	private String brandId;

	private Date timeSign;

	private String sapWriteFlag;

	private Date updateTime;

	private String flag;

	private String isPresent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation == null ? null : abbreviation.trim();
	}

	public String getInputCode() {
		return inputCode;
	}

	public void setInputCode(String inputCode) {
		this.inputCode = inputCode == null ? null : inputCode.trim();
	}

	public String getAnlyCode() {
		return anlyCode;
	}

	public void setAnlyCode(String anlyCode) {
		this.anlyCode = anlyCode == null ? null : anlyCode.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage == null ? null : advantage.trim();
	}

	public String getPictFile() {
		return pictFile;
	}

	public void setPictFile(String pictFile) {
		this.pictFile = pictFile == null ? null : pictFile.trim();
	}

	public String getBasicUom() {
		return basicUom;
	}

	public void setBasicUom(String basicUom) {
		this.basicUom = basicUom == null ? null : basicUom.trim();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model == null ? null : model.trim();
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation == null ? null : orientation.trim();
	}

	public String getForSeason() {
		return forSeason;
	}

	public void setForSeason(String forSeason) {
		this.forSeason = forSeason == null ? null : forSeason.trim();
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind == null ? null : kind.trim();
	}

	public String getIsSpcProd() {
		return isSpcProd;
	}

	public void setIsSpcProd(String isSpcProd) {
		this.isSpcProd = isSpcProd == null ? null : isSpcProd.trim();
	}

	public String getInCtrl() {
		return inCtrl;
	}

	public void setInCtrl(String inCtrl) {
		this.inCtrl = inCtrl == null ? null : inCtrl.trim();
	}

	public Date getFstStkDate() {
		return fstStkDate;
	}

	public void setFstStkDate(Date fstStkDate) {
		this.fstStkDate = fstStkDate;
	}

	public Date getLastStkDate() {
		return lastStkDate;
	}

	public void setLastStkDate(Date lastStkDate) {
		this.lastStkDate = lastStkDate;
	}

	public Date getProdDate() {
		return prodDate;
	}

	public void setProdDate(Date prodDate) {
		this.prodDate = prodDate;
	}

	public Date getOnSaleDate() {
		return onSaleDate;
	}

	public void setOnSaleDate(Date onSaleDate) {
		this.onSaleDate = onSaleDate;
	}

	public Double getUnitLen() {
		return unitLen;
	}

	public void setUnitLen(Double unitLen) {
		this.unitLen = unitLen;
	}

	public Double getUnitWid() {
		return unitWid;
	}

	public void setUnitWid(Double unitWid) {
		this.unitWid = unitWid;
	}

	public Double getUnitHgt() {
		return unitHgt;
	}

	public void setUnitHgt(Double unitHgt) {
		this.unitHgt = unitHgt;
	}

	public Double getUnitVol() {
		return unitVol;
	}

	public void setUnitVol(Double unitVol) {
		this.unitVol = unitVol;
	}

	public Double getUnitWgt() {
		return unitWgt;
	}

	public void setUnitWgt(Double unitWgt) {
		this.unitWgt = unitWgt;
	}

	public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate == null ? null : isPrivate.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor == null ? null : mainColor.trim();
	}

	public Integer getProdYear() {
		return prodYear;
	}

	public void setProdYear(Integer prodYear) {
		this.prodYear = prodYear;
	}

	public String getProdSeason() {
		return prodSeason;
	}

	public void setProdSeason(String prodSeason) {
		this.prodSeason = prodSeason == null ? null : prodSeason.trim();
	}

	public String getProdTheme() {
		return prodTheme;
	}

	public void setProdTheme(String prodTheme) {
		this.prodTheme = prodTheme == null ? null : prodTheme.trim();
	}

	public String getDispArea() {
		return dispArea;
	}

	public void setDispArea(String dispArea) {
		this.dispArea = dispArea == null ? null : dispArea.trim();
	}

	public String getProdClass() {
		return prodClass;
	}

	public void setProdClass(String prodClass) {
		this.prodClass = prodClass == null ? null : prodClass.trim();
	}

	public String getProdPartm() {
		return prodPartm;
	}

	public void setProdPartm(String prodPartm) {
		this.prodPartm = prodPartm == null ? null : prodPartm.trim();
	}

	public String getProdGroup() {
		return prodGroup;
	}

	public void setProdGroup(String prodGroup) {
		this.prodGroup = prodGroup == null ? null : prodGroup.trim();
	}

	public String getProdSubGroup() {
		return prodSubGroup;
	}

	public void setProdSubGroup(String prodSubGroup) {
		this.prodSubGroup = prodSubGroup == null ? null : prodSubGroup.trim();
	}

	public String getProdSorts() {
		return prodSorts;
	}

	public void setProdSorts(String prodSorts) {
		this.prodSorts = prodSorts == null ? null : prodSorts.trim();
	}

	public String getProdShopModel() {
		return prodShopModel;
	}

	public void setProdShopModel(String prodShopModel) {
		this.prodShopModel = prodShopModel == null ? null : prodShopModel
				.trim();
	}

	public Double getOnBrandPrc() {
		return onBrandPrc;
	}

	public void setOnBrandPrc(Double onBrandPrc) {
		this.onBrandPrc = onBrandPrc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series == null ? null : series.trim();
	}

	public String getPlanBatch() {
		return planBatch;
	}

	public void setPlanBatch(String planBatch) {
		this.planBatch = planBatch == null ? null : planBatch.trim();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module == null ? null : module.trim();
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif == null ? null : motif.trim();
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year == null ? null : year.trim();
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1 == null ? null : remark1.trim();
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2 == null ? null : remark2.trim();
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3 == null ? null : remark3.trim();
	}

	public String getProdClsId() {
		return prodClsId;
	}

	public void setProdClsId(String prodClsId) {
		this.prodClsId = prodClsId == null ? null : prodClsId.trim();
	}

	public String getProdClsNum() {
		return prodClsNum;
	}

	public void setProdClsNum(String prodClsNum) {
		this.prodClsNum = prodClsNum == null ? null : prodClsNum.trim();
	}

	public String getBrandGrpId() {
		return brandGrpId;
	}

	public void setBrandGrpId(String brandGrpId) {
		this.brandGrpId = brandGrpId == null ? null : brandGrpId.trim();
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId == null ? null : brandId.trim();
	}

	public Date getTimeSign() {
		return timeSign;
	}

	public void setTimeSign(Date timeSign) {
		this.timeSign = timeSign;
	}

	public String getSapWriteFlag() {
		return sapWriteFlag;
	}

	public void setSapWriteFlag(String sapWriteFlag) {
		this.sapWriteFlag = sapWriteFlag == null ? null : sapWriteFlag.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	public String getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(String isPresent) {
		this.isPresent = isPresent == null ? null : isPresent.trim();
	}

	public String getProdProp() {
		return prodProp;
	}

	public void setProdProp(String prodProp) {
		this.prodProp = prodProp;
	}

	public String getProdSort() {
		return prodSort;
	}

	public void setProdSort(String prodSort) {
		this.prodSort = prodSort;
	}

	public String getProdStyle() {
		return prodStyle;
	}

	public void setProdStyle(String prodStyle) {
		this.prodStyle = prodStyle;
	}
}