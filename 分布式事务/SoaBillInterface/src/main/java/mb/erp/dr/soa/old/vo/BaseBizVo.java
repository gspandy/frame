package mb.erp.dr.soa.old.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;

/**
 * 表头信息 - 基类vo
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BaseBizVo
 * @since       全流通改造
 */
public class BaseBizVo implements Serializable {

	private static final long serialVersionUID = -6628342462580386322L;
	
	private String progress; // 处理进度
	private String suspended;  // 是否挂起
	private String cancelled; // 是否撤销
	private String remark; // 备注
	private String currency; // 币种
	private String controlStatus; // 规则编码，调用规则前，对其赋值
	private BillType billType; //单据类型
	private String bizType; //业务类型 不同业务走的规则不同
	// 下面这些字段，后期可以通过json参数单独传输------------------------------------------------------------------------------
	private AllocType allocType; // 配货模式 (队列使用)
	private String upVendeeId; // 上级原始购货方 (队列使用)
	private BillType upDocType; // 上级原始单据类型 (队列使用)
	private String upDocCode; // 上级原始单据编号 (队列使用)
	private GdnMode gdnMode; //出库方式 (队列使用)
	private String originAdnNum; //原始adnNum
	private String upRcvWarehId; // 实际收货仓库（预处理队列使用）
	private String upDispWarehId; // 实际发货仓库（预处理队列使用）
    private String dataSource;  //数据来源
    private String approved;  //决策方
    private String osDocCode;
    private String sfGdnCode; //新ERP出库单code
    private String gdnNum; //老ERP出库单num 
    private String sfDgnCode; //交货单code 
    private String sfDgnCodeUPZ; //上层转配交货单code
    private String lastFactDispWarehId; // 最终发货仓库（出库规则使用）
    private String lastFactRcvWarehId; // 最终收货仓库（判断生成现货单时是否根据公式算价格）
    private String hadLockWareh; // SfSchTaskExecOosVo的一个字段，在老erp调配单和计划分配单判断是否增加已分配库存的时候用到 
    private String pubB2cDocCode; // B2C订单号，保存单据流用到
    private Map<String, Object> extraParams = new HashMap<String, Object>(); // 队列参数
	private List<String> rulesGroup = new ArrayList<String>();
	private List<String> progressList = new ArrayList<String>();
	
	public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended == null ? null : suspended.trim();
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled == null ? null : cancelled.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		 this.currency = currency == null ? null : currency.trim();
	}
	public String getControlStatus() {
		return controlStatus;
	}
	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}

	public List<String> getRulesGroup() {
		return rulesGroup;
	}

	public void setRulesGroup(List<String> rulesGroup) {
		this.rulesGroup = rulesGroup;
	}
	
	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	public AllocType getAllocType() {
		return allocType;
	}

	public void setAllocType(AllocType allocType) {
		this.allocType = allocType;
	}

	public String getUpVendeeId() {
		return upVendeeId;
	}

	public void setUpVendeeId(String upVendeeId) {
		this.upVendeeId = upVendeeId;
	}

	public String getUpDocCode() {
		return upDocCode;
	}

	public void setUpDocCode(String upDocCode) {
		this.upDocCode = upDocCode;
	}

	public BillType getUpDocType() {
		return upDocType;
	}

	public void setUpDocType(BillType upDocType) {
		this.upDocType = upDocType;
	}

	public GdnMode getGdnMode() {
		return gdnMode;
	}

	public void setGdnMode(GdnMode gdnMode) {
		this.gdnMode = gdnMode;
	}

	public String getOriginAdnNum() {
		return originAdnNum;
	}

	public void setOriginAdnNum(String originAdnNum) {
		this.originAdnNum = originAdnNum;
	}

	public String getUpRcvWarehId() {
		return upRcvWarehId;
	}

	public void setUpRcvWarehId(String upRcvWarehId) {
		this.upRcvWarehId = upRcvWarehId;
	}

	public String getUpDispWarehId() {
		return upDispWarehId;
	}

	public void setUpDispWarehId(String upDispWarehId) {
		this.upDispWarehId = upDispWarehId;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getOsDocCode() {
		return osDocCode;
	}

	public void setOsDocCode(String osDocCode) {
		this.osDocCode = osDocCode;
	}

	public String getSfGdnCode() {
		return sfGdnCode;
	}

	public void setSfGdnCode(String sfGdnCode) {
		this.sfGdnCode = sfGdnCode;
	}

	public String getGdnNum() {
		return gdnNum;
	}

	public void setGdnNum(String gdnNum) {
		this.gdnNum = gdnNum;
	}

	public String getSfDgnCode() {
		return sfDgnCode;
	}

	public void setSfDgnCode(String sfDgnCode) {
		this.sfDgnCode = sfDgnCode;
	}

	public String getSfDgnCodeUPZ() {
		return sfDgnCodeUPZ;
	}

	public void setSfDgnCodeUPZ(String sfDgnCodeUPZ) {
		this.sfDgnCodeUPZ = sfDgnCodeUPZ;
	}

	public String getLastFactDispWarehId() {
		return lastFactDispWarehId;
	}

	public void setLastFactDispWarehId(String lastFactDispWarehId) {
		this.lastFactDispWarehId = lastFactDispWarehId;
	}

	public String getLastFactRcvWarehId() {
		return lastFactRcvWarehId;
	}

	public void setLastFactRcvWarehId(String lastFactRcvWarehId) {
		this.lastFactRcvWarehId = lastFactRcvWarehId;
	}

	public String getHadLockWareh() {
		return hadLockWareh;
	}

	public void setHadLockWareh(String hadLockWareh) {
		this.hadLockWareh = hadLockWareh;
	}

	public String getPubB2cDocCode() {
		return pubB2cDocCode;
	}

	public void setPubB2cDocCode(String pubB2cDocCode) {
		this.pubB2cDocCode = pubB2cDocCode;
	}

	public Map<String, Object> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(Map<String, Object> extraParams) {
		this.extraParams = extraParams;
	}

	public List<String> getProgressList() {
		return progressList;
	}

	public void setProgressList(List<String> progressList) {
		this.progressList = progressList;
	}
}
