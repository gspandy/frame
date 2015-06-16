package mb.erp.dr.soa.drools.utils;

import mb.erp.dr.soa.old.vo.TbnVo;

public class TestDroolsUtils {

	/**
	 * 调配单 - 锁库存
	 * @param tbnVo
	 * @return
	 */
	public String lockStore(TbnVo tbnVo){
		System.out.println("TestDroolsUtils ----->>> 调配单 规则 -> 锁库存");
		return tbnVo.getTbnNum();
	}
	
	/**
	 * 调配单 - 重新获取价格
	 * @param tbnVo
	 * @return
	 */
	public Integer getPrice(TbnVo tbnVo){
		System.out.println("TestDroolsUtils ----->>> 调配单 规则 -> 重新获取价格");
		return 1;
	}
	 // -----------------------------------------------------------------------------------------------------------------
	/**
	 * 出库单 - 按发货组织获取成本价
	 * @return
	 */
	public Double getPricebyUnit(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 按发货组织获取成本价");
		return 1d;
	}
	
	/**
	 *  出库单 - 根据订单购货方+购货方明细，重新获取结算价格和折率
	 * @return
	 */
	public Double getPriceAndRace(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 根据订单购货方+购货方明细，重新获取结算价格和折率");
		return 1d;
	}
	
	/**
	 * 出库单 - 根据原始单据冻结的金额进行释放
	 */
	public void ridByfreeze(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 根据原始单据冻结的金额进行释放");
	}
	
	/**
	 * 出库单 - 负数扣减余额借口
	 */
	public void minusToRemainer(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 调用负数扣减余额借口");
	}
	
	/**
	 * 出库单 - 正数扣减余额借口
	 */
	public void plusToRemainer(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 调用正数扣减余额借口");
	}
	
	/**
	 * 出库单 - 释放已分配库存
	 */
	public void ridStore(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 释放已分配库存");
	}
	
	/**
	 * 出库单 - 扣减库存
	 */
	public void minusStore(){
		System.out.println("TestDroolsUtils ----->>> 出库单 规则 -> 扣减库存 ");
	}
	
	// -----------------------------------------------------------------------------------------------------------------
	
	/**
	 * 入库单 - 增加余额接口
	 */
	public void increaseRemainer(){
		System.out.println("TestDroolsUtils ----->>> 入库单 规则 -> 增加余额接口");
	}
	
	/**
	 * 入库单 - 增加库存
	 */
	public void increaseStore(){
		System.out.println("TestDroolsUtils ----->>> 入库单 规则 -> 增加库存 ");
	}
}
