package mb.erp.dr.soa.service.bill;

import java.util.Map;

import mb.erp.dr.soa.vo.BfOrgVo;
import mb.erp.dr.soa.vo.SfDocFlowVo;
import mb.erp.dr.soa.vo.common.CommonIdCodeVo;



/**
 * 新ERP通用接口服务
 * 包含服务：获取主键编号、获取CODE值
 * @author     郭明帅
 * @version    1.0, 2014-11-19
 * @see         NewERPCommonService
 * @since       全流通改造
 */
public interface NewERPCommonService {
	/**
	 * 新ERP获取ID值
	 * @param pname
	 * @param count
	 * @return
	 */
	public Long getPrimaryIdNew(String table_name,int count);
	
	/**
	 * 新ERP获取CODE值
	 * @param pname
	 * @param count
	 * @return
	 */
	public String getPrimaryCode(String pname,int count);
	
	/**
	 * 新ERP根据CODE查询ID
	 * @return
	 */
	public Long getIdByTableCode(String table_name, String code);
	
	/**
	 * 新ERP根据组织ID查询CODE
	 * @return
	 */
	public String getUnitCodeByUnitId(Long unitId);
	
	/**
	 * 新ERP根据组织CODE查询ID
	 * @return
	 */
	public Long getUnitIdByUnitCode(String unitCode);
	
	/**
	 * 新ERP根据商品ID查询商品PROD_NUM
	 * @return
	 */
	public String getProdNumByProdId(Long prodId);
	
	/**
	 * 新ERP根据商品PROD_NUM查询商品ID
	 * @return
	 */
	public Long getProdIdByProdNum(String prodNum);
	
	/**
	 * 新ERP根据品牌组id查询品牌code
	 * @param brandId
	 * @return
	 */
	public String getBrandCodeByBrandId(String brandId);
	
	/**
	 * 新ERP根据品牌组code查询品牌组id
	 * @param brandId
	 * @return
	 */
	public String getBrandIdByBrandCode(String brandCode);
	
	/**
	 * 新ERP根据操作员code查询操作员id
	 * @param brandId
	 * @return
	 */
	public Long getOprIdByOprCode(String oprCode);
	
	/**
	 * 全局系统参数
	 * @param params
	 * @return
	 */
	public String getMainSysParamValue(String code);
	/**
	 * 判断组织是否代理商
	 * @param orgId
	 * @return
	 */
	public Boolean isAgent(Long orgId);
	
	/**
	 * 判断组织是否分部
	 * @param orgId
	 * @return
	 */
	public Boolean isBranch(Long orgId);
	
	/**
	 * 检测会计期间
	 * @param warehId  发货仓库ID
	 * @param delivMode  出库方式
	 * @param docDate  单据日期
	 */
	public void checkFiFslcMonth(Long warehId,String delivMode);
	
	/**
	 * 保存单据流信息 
	 * vo中必传参数 源头单据类型、源头单据编码、单据执行状态、单据组织编码、
	 * 单据类型（出库单、交货单、入库单、到货通知单）、单据编码、操作人、数据来源
	 */
	public Integer saveSfDocFlow(SfDocFlowVo vo);
	
	/**
	 * 是否启用新ERP
	 * @param unitCode
	 * @return
	 */
	public Boolean isEnableNewErp(String unitCode);
	
	
	/**
	 * 新ERP根据ID查询CODE
	 * @param list
	 */
	public CommonIdCodeVo getCodeById(CommonIdCodeVo idCodeVo);
	
	/**
	 * 新ERP根据CODE查询ID
	 * @param list
	 */
	public CommonIdCodeVo getIdByCode(CommonIdCodeVo idCodeVo);
	
	/**
	 * 根据locCode,warehId获取locId
	 * @param map
	 * @return
	 */
	public Long getLocIdByLocCode(Map map);
	
	/**
	 * 根据发货仓库id查询发货货位编码
	 * @return
	 */
	public Long getDispLocId(Long bfOrgId);
	
	/**
	 * 根据收货仓库id查询收货货位编码
	 * @return
	 */
	public Long getRcptLocId(Long bfOrgId);
	
	/**
	 * 生成B2C单据流之后，要更改B2C原始单据的状态（原始出库单）
	 * @param srcDocCode
	 * @return
	 */
	public Integer upSfGdnExFlag(String srcDocCode);
	
	/**
	 * 生成B2C单据流之后，要更改B2C原始单据的状态（原始门店发货单）
	 * @param srcDocCode
	 * @return
	 */
	public Integer upSfDegExFlag(String srcDocCode);
	
	/**
	 * 根据新ERPtbn_num获取税率
	 * @param tbn_num
	 * @return
	 */
	public Double selectTaxRateFromDrTbn(String tbn_num);
	
	/**
	 * 根据新ERP现货单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfIdt(String code);
	
	/**
	 * 根据新ERP交货单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfDgn(String code);
	
	/**
	 * 根据新ERP到货通知单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfRvd(String code);
	
	/**
	 * 查询单个字典值
	 * @param type
	 * @param name
	 * @return
	 */
	public String selectDirectoryCode(String type,String name);
	
	/**
	 * 根据code查询组织详情
	 * @param code
	 * @return
	 */
	public BfOrgVo getBfOrgByCode(String code);
	
}
