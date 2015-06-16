package mb.mba.core.bean;

import java.io.Serializable;

import mb.mba.core.entity.CostAccountingGroupEntity;
import mb.mba.core.entity.CostAccountingPeriodEntity;

/**
 * 月加权成本核算传参用
 * @author czj
 * 2015/6/1
 */
public class CostAccountBean implements Serializable {

	private static final long serialVersionUID = -5337070658363336438L;

	private CostAccountingGroupEntity costAccountingGroupEntity;

	private CostAccountingPeriodEntity costAccountingPeriodEntity;

	public CostAccountingGroupEntity getCostAccountingGroupEntity() {
		return costAccountingGroupEntity;
	}

	public void setCostAccountingGroupEntity(
			CostAccountingGroupEntity costAccountingGroupEntity) {
		this.costAccountingGroupEntity = costAccountingGroupEntity;
	}

	public CostAccountingPeriodEntity getCostAccountingPeriodEntity() {
		return costAccountingPeriodEntity;
	}

	public void setCostAccountingPeriodEntity(
			CostAccountingPeriodEntity costAccountingPeriodEntity) {
		this.costAccountingPeriodEntity = costAccountingPeriodEntity;
	}

}
