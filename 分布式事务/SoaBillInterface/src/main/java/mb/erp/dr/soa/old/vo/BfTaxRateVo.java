package mb.erp.dr.soa.old.vo;

import java.io.Serializable;


/**
 * 税率vo
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         BfTaxRateVo
 * @since       全流通改造
 */
public class BfTaxRateVo implements Serializable{
    /**
	 */
	private static final long serialVersionUID = -1818280670268326715L;

	private String unitId;

    private Double taxRate;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }
}