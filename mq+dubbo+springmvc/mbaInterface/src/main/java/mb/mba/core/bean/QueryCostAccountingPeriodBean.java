package mb.mba.core.bean;

import java.io.Serializable;

public class QueryCostAccountingPeriodBean implements Serializable {

	private static final long serialVersionUID = -5337070658363336438L;

	private Long costId;

	private Long periodId;

	public Long getCostId() {
		return costId;
	}

	public void setCostId(Long costId) {
		this.costId = costId;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
}
