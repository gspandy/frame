package mb.mba.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 表映射对象基类
 * @author cyyu
 */
public class BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 8408154459882968623L;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
     * 修改时间
     */
    private Date modifyTime;
    
    /**
     * 创建人
     */
    private String createOper;
    
    /**
     * 修改人
     */
    private String modifyOper;
    
    /**
     * 备注
     */
	private String remarks; 

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateOper() {
        return createOper;
    }

    public void setCreateOper(String createOper) {
        this.createOper = createOper;
    }

    public String getModifyOper() {
        return modifyOper;
    }

    public void setModifyOper(String modifyOper) {
        this.modifyOper = modifyOper;
    }

}
