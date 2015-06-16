package mb.erp.dr.soa.old.vo;

import java.io.Serializable;

/**
 * 系统参数vo
 * 
 * @author 郭明帅
 * @version 1.0, 2014-10-31
 * @see SysParameterVo
 * @since 全流通改造
 */
public class SysParameterVo implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 5336191834558610432L;

	private String parmId;

	private String description;

	private String parmVal;

	private String seqNum;

	public String getParmId() {
		return parmId;
	}

	public void setParmId(String parmId) {
		this.parmId = parmId == null ? null : parmId.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getParmVal() {
		return parmVal;
	}

	public void setParmVal(String parmVal) {
		this.parmVal = parmVal == null ? null : parmVal.trim();
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum == null ? null : seqNum.trim();
	}
}