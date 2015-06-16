package mb.erp.dr.soa.vo;

import java.util.Date;

/**
 * 新ERP调配单配货明细vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-11-21
 * @see         DrTbnAllocDtlVo
 * @since       全流通改造
 */
public class DrTbnAllocDtlVo extends NewBaseBizAllocDtlVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2770940911474165071L;
	
	private Long id ; // 内码
	private Long drTbnId ; // 外键ID
	private Long warehId ; // 仓库ID
	private Double ohAllocQty ; // 原配数量
	private Double allocQty ; // 已分配库存
	private Date allocDate ; // 分配日期
	private Double actQty ; // 发货数量
	private Double rcvQty ; // 收货数量
	private Double docState ; // 分配进度
	private String gdnCode ; // 出库单编码
	private String grnCode ; // 入库单编码
	private String dgnCode ; // 交货单编码
	private Date gdnTime ; // 出库时间
	private Date grnTime ; // 入库时间
	private String orderId ; // 订单ID
	private String unitId ; // 组织ID
	private Double distQty ; // 分配数量
	private Double preDistQty ; // 预分配数量

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDrTbnId() {
        return drTbnId;
    }

    public void setDrTbnId(Long drTbnId) {
        this.drTbnId = drTbnId;
    }

    public Long getWarehId() {
        return warehId;
    }

    public void setWarehId(Long warehId) {
        this.warehId = warehId;
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

    public Date getAllocDate() {
        return allocDate;
    }

    public void setAllocDate(Date allocDate) {
        this.allocDate = allocDate;
    }

    public Double getActQty() {
        return actQty;
    }

    public void setActQty(Double actQty) {
        this.actQty = actQty;
    }

    public Double getRcvQty() {
        return rcvQty;
    }

    public void setRcvQty(Double rcvQty) {
        this.rcvQty = rcvQty;
    }

    public Double getDocState() {
        return docState;
    }

    public void setDocState(Double docState) {
        this.docState = docState;
    }

    public String getGdnCode() {
        return gdnCode;
    }

    public void setGdnCode(String gdnCode) {
        this.gdnCode = gdnCode == null ? null : gdnCode.trim();
    }

    public String getGrnCode() {
        return grnCode;
    }

    public void setGrnCode(String grnCode) {
        this.grnCode = grnCode == null ? null : grnCode.trim();
    }

    public String getDgnCode() {
        return dgnCode;
    }

    public void setDgnCode(String dgnCode) {
        this.dgnCode = dgnCode == null ? null : dgnCode.trim();
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