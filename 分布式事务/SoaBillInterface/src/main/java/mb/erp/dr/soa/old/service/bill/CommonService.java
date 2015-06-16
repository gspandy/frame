package mb.erp.dr.soa.old.service.bill;

import java.util.Map;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.SysUnitVo;

/**
 * 通用接口服务
 * 包含服务：获取主键编号、获取账户信息
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         CommonService
 * @since       全流通改造
 */
public interface CommonService {

	
	/**
	 * 老ERP获取主键编号
	 * @param pcode 组织编码
	 * @param pnum 需要生成的字段名称
	 * @return
	 */
	public String getPrimaryKey(String pname,String pnum);
	
	/**
	 * 查询是否已封账 
	 * @param unitId		组织编码
	 * @param docNum	单据流水号
	 * @param billType	单据类型
	 * @return true 表示已封账，需要给出提示信息
	 */
	public Boolean isClosedAccount(String unitId,String docNum,BillType billType);
	
	/**
	 * 获取税率 老ERP
	 * @param unitId		组织编码
	 * @param docNum	单据流水号
	 * @param docType	单据类型
	 * @param srcDocType	原始单据类型
	 * @param gdnMode 出入库方式
	 */
	public Double receiveTaxRate(BillType docType, BillType srcDocType, String unitId,String srcUnitId, String docNum ,GdnMode gdnMode,AllocType allocType);
	
	/**
	 * 老ERP获取ID值
	 * @param pname
	 * @param count
	 * @return
	 */
	public Long getPrimaryIdOld(String table_name,int count);
	
	/**
	 * 获取流水号和批次号 - 结果信息表写入接口
	 * 一个B2C订单的明细可能由多仓配发，O2O单据流里一个B2C订单号就会有多组单据流，故在外面产出批号，保存在队列信息里一直传下去
	 * @return
	 */
	public PubO2oFlowVo getO2oSeqIdAndBatchNo();
	
	/**
	 * 保存单据流信息 - 结果信息表写入接口
	 * @return
	 */
	public Integer savePubO2oFlow(PubO2oFlowVo vo);
	
	/**
	 * 查询老ERP现货订单
	 * @param idtVo
	 * @return
	 */
	public IdtVo select (IdtVo idtVo);
	
	/**
	 * 代理商是否加盟
	 * @return
	 */
	public boolean isOldFranchisee(String vendeeId);
	
	/**
	 * 代理商为加盟或直营
	 * @return
	 */
	public boolean isOldFranchiseeOrDirect(String vendeeId);
	
    /**
     * 查询发货货位和收货货位
     * @return
     */
    public Map<String,String> selectLocId(String warehId);
    
    /**
     * 查询税率
     * @param unitId
     * @return
     */
    public Double selectTaxRate(String unitId);

    /**
     * 查询规则值
     * @param type
     * @param name
     * @return
     */
	public String selectDirectoryCode(String type,String name);
	
	/**
	 * 根据组织编码查询组织详细信息（获取地址，电话等信息填入单据）
	 * @param unitId
	 * @return
	 */
	public SysUnitVo getSysUnitByUnitid(String unitId);
	
	/**
	 * 
	 * 判断指定仓库的货位是否崔总
	 * @param warehId
	 * @param locId
	 * @return true 存在
	 */
	public Boolean isExistsLoc(String warehId , String locId);
}
