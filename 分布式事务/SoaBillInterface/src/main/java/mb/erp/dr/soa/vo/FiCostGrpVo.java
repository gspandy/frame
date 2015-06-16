package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

public class FiCostGrpVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 79904636977903630L;

	private Long id;

	private Long unitId;

	private String code;

	private String name;

	private String currency;

	private Date cfAt;

	private Integer docState;

	private String createUser;

	private Date createDate;

	private String lastModifiedUser;

	private Date lastModifiedDate;

	private String activity;

	private String remark;

	private Integer caltype;

	private String grntype;

	private String gdntype;

	private String opMode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public Date getCfAt() {
		return cfAt;
	}

	public void setCfAt(Date cfAt) {
		this.cfAt = cfAt;
	}

	public Integer getDocState() {
		return docState;
	}

	public void setDocState(Integer docState) {
		this.docState = docState;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser == null ? null
				: lastModifiedUser.trim();
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity == null ? null : activity.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getCaltype() {
		return caltype;
	}

	public void setCaltype(Integer caltype) {
		this.caltype = caltype;
	}

	public String getGrntype() {
		return grntype;
	}

	public void setGrntype(String grntype) {
		this.grntype = grntype == null ? null : grntype.trim();
	}

	public String getGdntype() {
		return gdntype;
	}

	public void setGdntype(String gdntype) {
		this.gdntype = gdntype == null ? null : gdntype.trim();
	}

	public String getOpMode() {
		return opMode;
	}

	public void setOpMode(String opMode) {
		this.opMode = opMode == null ? null : opMode.trim();
	}
}