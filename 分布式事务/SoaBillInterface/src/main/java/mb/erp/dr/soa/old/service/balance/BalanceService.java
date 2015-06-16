package mb.erp.dr.soa.old.service.balance;

import java.util.List;

import mb.erp.dr.soa.bean.BalanceBean;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.vo.FreezeTran;
import mb.erp.dr.soa.old.vo.FsclAc;
import mb.erp.dr.soa.vo.common.MsgVo;

/**
 * 资金数据库接口
 * 包含接口：增加余额、正数扣减余额、负数扣减余额、正数冻结金额、负数冻结金额、释放冻结
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BalanceService
 * @since       全流通改造
 */
public interface BalanceService {

	/**
	 * 增加余额	给往来账户增加余额(BALANCE)
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo increaseBalance(BalanceBean balanceBean);
	/**
	 * 正数扣减余额	给往来账户扣减余额(BALANCE)，余额不允许出现负数
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo reduceBalancePositive(BalanceBean balanceBean);
	/**
	 * 负数扣减余额	给往来账户扣减余额(BALANCE)，余额允许出现负数
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo reduceBalanceNegative(BalanceBean balanceBean);
	/**
	 * 正数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，不允许冻结
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo increaseFreezePositive(BalanceBean balanceBean);
	/**
	 * 负数冻结金额	在往来账户冻结金额(FRZ_VAL),可支配金额不足，允许冻结
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo increaseFreezeNegative(BalanceBean balanceBean);
	/**
	 * 释放冻结	在往来账户冻结金额(FRZ_VAL),释放冻结
	 * @param balanceBean
	 * @return MsgVo
	 */
	public MsgVo reduceFreeze(BalanceBean balanceBean);
	
	/**
	 * 获取账户信息
	 * @param uid
	 * @param cpdUid
	 * @return FsclAc
	 */
	public List<FsclAc> getObjectCUByCpdUnitId(String uid , String cpdUid);
	
	/**
	 * 按新ERP订单号现货冻结
	 * @param venderId 发货方
	 * @param vendeeId 收货方
	 * @param docNum 新erp现货订单号
	 * @param amount 冻结金额
	 * @return MsgVo
	 */
	public MsgVo increaseFreezeByIdt(String venderId, String vendeeId,
			Double amount ,String docNum);
	/**
	 * 按新ERP订单号释放现货冻结
	 * @param venderId 发货方
	 * @param vendeeId 收货方
	 * @param docNum 新erp现货订单号
	 * @param amount 释放冻结金额
	 * @return MsgVo
	 */
	public MsgVo reduceFreezeByIdt(String venderId, String vendeeId,
			Double amount ,String docNum);
	/**
	 * 判断余额是否足够 , 输出是可支配金额
	 * @param venderId
	 * @param vendeeId
	 * @return MsgVo
	 */
	public MsgVo judgeBalance(String venderId , String vendeeId);
	
	/**
	 * 获取是否有冻结事务
	 * @param unitId 组织编码
	 * @param cpdUnitId 下级组织编码(代理商编码)
	 * @param docType
	 * @param docNum
	 * @return FsclAc
	 */
	public List<FreezeTran> selectFreezeTranByDocTypeNum(String unitId,String cpdUnitId ,BillType docType , String docNum);
}
