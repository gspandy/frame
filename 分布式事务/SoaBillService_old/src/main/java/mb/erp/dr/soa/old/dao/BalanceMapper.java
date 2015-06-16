package mb.erp.dr.soa.old.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.bean.BalanceBean;
import mb.erp.dr.soa.old.vo.AcTran;
import mb.erp.dr.soa.old.vo.AcTranDtl;
import mb.erp.dr.soa.old.vo.CreditLimitUseVo;
import mb.erp.dr.soa.old.vo.CreditTranDtlVo;
import mb.erp.dr.soa.old.vo.CreditTranVo;
import mb.erp.dr.soa.old.vo.FreezeTran;
import mb.erp.dr.soa.old.vo.FsclAc;
import mb.erp.dr.soa.old.vo.LoanTranVo;
import mb.erp.dr.soa.old.vo.PubLoanDoc;

/**
 * 资金数据库接口
 * 包含接口：查询账户信息、更新账户金额、更新账户冻结金额、保存往来事务、保存往来事务明细、保存冻结事务
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         BalanceMapper
 * @since       全流通改造
 */
public interface BalanceMapper {

	/**
	 * 查询账户信息
	 * @param params
	 * @return
	 */
    public List<FsclAc> getFsclInfo(Map<String, String> params);
    
    /**
   	 * 获取是否有冻结事务
   	 * @param params
   	 * @return FsclAc
   	 */
   	public List<FreezeTran> selectFreezeTranByDocTypeNum(Map<String, Object> params);
   	/**
	 * 按新ERP订单号现货冻结
	 * @param venderId
	 * @param vendeeId
	 * @param docNum
	 * @param amount
	 * 
	 * @return 
	 */
	public Map<String, Object> increaseFreezeByIdt(Map<String, Object> params);
	/**
	 * 按新ERP订单号释放现货冻结
	 * @param venderId
	 * @param vendeeId
	 * @param docNum
	 * @param amount
	 * 
	 * @return 
	 */
	public Map<String, Object> reduceFreezeByIdt(Map<String, Object> params);
	/**
	 * 判断余额是否足够 , 输出是可支配金额
	 * @param venderId
	 * @param vendeeId
	 * @return amount
	 */
	public Map<String, Object> judgeBalance(Map<String, Object> params);
    /**
     * 更新账户金额
     * @param fsclAc
     * @return
     */
    public Integer updateBalance(FsclAc fsclAc);
    
    /**
     * 更新账户冻结金额
     * @param fsclAc
     * @return
     */
    public Integer updateFrzbal(FsclAc fsclAc);
    
    /**
     * 保存往来事务
     * @param acTran
     * @return
     */
    public Integer saveActran(AcTran acTran);
    
    /**
     * 保存往来事务明细
     * @param acTranDtl
     * @return
     */
    public Integer saveActranDtl(AcTranDtl acTranDtl);
    
    /**
     * 保存冻结事务
     * @param freezeTran
     * @return
     */
    public Integer saveFreezeTran(FreezeTran freezeTran);
    
    /**
     * 根据组织关系查询可用专项资金总额
     * @param map
     * @return Double
     */
    public Double searchCreditMoney(Map<String, Object> map);
    
    /**
     * 根据组织关系查询专项资金记录
     * @param map
     * @return List<CreditLimitUseVo>
     */
    public List<CreditLimitUseVo> searchCredit(Map<String, Object> map);
    
    /**
     * 保存信用额度事务
     * @param vo
     * @return Integer
     */
    public Integer saveCreditTran(CreditTranVo vo);
    
    /**
     * 保存信用额度明细事务
     * @param creditTranDtlVos
     * @return Integer
     */
    public Integer saveCreditTranDtl(Map<String, Object> map);
    
    /**
     * 保存借款事务
     * @param vo
     * @return Integer
     */
    public Integer saveLoanTran(LoanTranVo vo);
    
    /**
     * 批量更新专项资金
     * @param creditLimitUseVos
     * @return Integer
     */
    public Integer creditBatchUpdate(List<CreditLimitUseVo> creditLimitUseVos);

    /**
     * 查询信用额度事务
     * @param map
     * @return
     * List<CreditTranVo>
     */
    public List<CreditTranVo> selectCreditTranByDocTypeNum(Map<String, Object> map);

    /**
     * 根据信用额度明细更新专项资金
     * @param creditTranVo
     * @return Integer
     */
    public Integer updateCreditLimitUse(CreditTranVo creditTranVo);
    
	/**
	 * 根据组织编码和信用额度事务编码查询信用额度明细事务信息
	 * @param params
	 * @return
	 * List<CreditTranDtlVo>
	 */
	public List<CreditTranDtlVo> selectCreditTranDtl(Map<String, Object> params);
	
	/**
	 * 保存借款单(不用保存明细，还款的时候生成明细)
	 * @param pubLoanDoc
	 * @return
	 */
	public Integer savePubLoanDoc(PubLoanDoc pubLoanDoc);
	
	/**
	 * 判断是否启用新ERP
	 * @return
	 */
	public Integer isCreditEnable();
	
	/**
	 * 判断帐户余额是否足够 大于零就使用专项资金
	 * @param BalanceBean balanceBean
	 * @return
	 */
	public Double useAmountEnable(BalanceBean balanceBean);
}