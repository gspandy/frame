package mb.erp.dr.soa.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 新ERP现货订单分配明细实体类
 * 
 * @author 郭明帅
 * @version 1.0, 2014-11-17
 * @see SfIdtAllocDtlVo
 * @since 全流通改造
 */
public class SfIdtAllocDtlVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 514312670536572619L;

	private Long id;

	private Long sfIdtId;

	private Long warehId; // 仓库ID

	private Long prodId; // 商品ID

	private Double ohAllocQty; // 预配数量

	private Double allocQty; // 配货数量

	private Double actQty; // 发货数量

	private String remark; //

	private Date allocDate; // 配货日期

	private Double rcvQty; // 接收数量

	private Integer docState; // 进度

	private String sfGdnCode; // 出库单号

	private String sfGrnCode; // 入库单号

	private String sfDgnCode; // 交货单号

	private String dataSource; // 数据来源

	private String progress; // 字符进度

	private Date allocTime; // 配货时间

	private Date gdnTime; // 出库时间

	private Date grnTime; // 入库时间

	private String orderId; // 操作员编码

	private String unitId; // 组织编码

	private Double distQty; // 统一配货分配数量

	private Double preDistQty; // 预配

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSfIdtId() {
		return sfIdtId;
	}

	public void setSfIdtId(Long sfIdtId) {
		this.sfIdtId = sfIdtId;
	}

	public Long getWarehId() {
		return warehId;
	}

	public void setWarehId(Long warehId) {
		this.warehId = warehId;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Double getOhAllocQty() {
		return ohAllocQty;
	}

	public void setOhAllocQty(Double ohAllocQty) {
		this.ohAllocQty = ohAllocQty;
	}

	public Double getAllocQty() {
		return allocQty;
	}

	public void setAllocQty(Double allocQty) {
		this.allocQty = allocQty;
	}

	public Double getActQty() {
		return actQty;
	}

	public void setActQty(Double actQty) {
		this.actQty = actQty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getAllocDate() {
		return allocDate;
	}

	public void setAllocDate(Date allocDate) {
		this.allocDate = allocDate;
	}

	public Double getRcvQty() {
		return rcvQty;
	}

	public void setRcvQty(Double rcvQty) {
		this.rcvQty = rcvQty;
	}

	public Integer getDocState() {
		return docState;
	}

	public void setDocState(Integer docState) {
		this.docState = docState;
	}

	public String getSfGdnCode() {
		return sfGdnCode;
	}

	public void setSfGdnCode(String sfGdnCode) {
		this.sfGdnCode = sfGdnCode == null ? null : sfGdnCode.trim();
	}

	public String getSfGrnCode() {
		return sfGrnCode;
	}

	public void setSfGrnCode(String sfGrnCode) {
		this.sfGrnCode = sfGrnCode == null ? null : sfGrnCode.trim();
	}

	public String getSfDgnCode() {
		return sfDgnCode;
	}

	public void setSfDgnCode(String sfDgnCode) {
		this.sfDgnCode = sfDgnCode == null ? null : sfDgnCode.trim();
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource == null ? null : dataSource.trim();
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress == null ? null : progress.trim();
	}

	public Date getAllocTime() {
		return allocTime;
	}

	public void setAllocTime(Date allocTime) {
		this.allocTime = allocTime;
	}

	public Date getGdnTime() {
		return gdnTime;
	}

	public void setGdnTime(Date gdnTime) {
		this.gdnTime = gdnTime;
	}

	public Date getGrnTime() {
		return grnTime;
	}

	public void setGrnTime(Date grnTime) {
		this.grnTime = grnTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId == null ? null : unitId.trim();
	}

	public Double getDistQty() {
		return distQty;
	}

	public void setDistQty(Double distQty) {
		this.distQty = distQty;
	}

	public Double getPreDistQty() {
		return preDistQty;
	}

	public void setPreDistQty(Double preDistQty) {
		this.preDistQty = preDistQty;
	}
}