package mb.erp.dr.soa.vo;

import java.io.Serializable;

/**
 * 新ERP单位成本实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-12-16
 * @see RpAgentUnitCostVo
 * @since 全流通改造
 */
public class RpAgentUnitCostVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 6667495418169781262L;

	private Long id;

	private Long agentId;

	private Long prodId;

	private Double inqty;

	private Double inval;

	private Long prodClsid;

	private Long brandId;

	private String prodSeason;

	private Integer prodYear;

	private String prodSex;

	private String prodStyle;

	private String prodDept;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Double getInqty() {
		return inqty;
	}

	public void setInqty(Double inqty) {
		this.inqty = inqty;
	}

	public Double getInval() {
		return inval;
	}

	public void setInval(Double inval) {
		this.inval = inval;
	}

	public Long getProdClsid() {
		return prodClsid;
	}

	public void setProdClsid(Long prodClsid) {
		this.prodClsid = prodClsid;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public String getProdSeason() {
		return prodSeason;
	}

	public void setProdSeason(String prodSeason) {
		this.prodSeason = prodSeason == null ? null : prodSeason.trim();
	}

	public Integer getProdYear() {
		return prodYear;
	}

	public void setProdYear(Integer prodYear) {
		this.prodYear = prodYear;
	}

	public String getProdSex() {
		return prodSex;
	}

	public void setProdSex(String prodSex) {
		this.prodSex = prodSex == null ? null : prodSex.trim();
	}

	public String getProdStyle() {
		return prodStyle;
	}

	public void setProdStyle(String prodStyle) {
		this.prodStyle = prodStyle == null ? null : prodStyle.trim();
	}

	public String getProdDept() {
		return prodDept;
	}

	public void setProdDept(String prodDept) {
		this.prodDept = prodDept == null ? null : prodDept.trim();
	}
}