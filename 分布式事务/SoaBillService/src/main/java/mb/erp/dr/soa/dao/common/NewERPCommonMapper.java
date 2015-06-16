package mb.erp.dr.soa.dao.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.common.CommonIdCodeVo;
import mb.erp.dr.soa.vo.common.NDirectoryVo;

/**
 * 新ERP通用数据库接口
 * 包含接口：获取主键编码
 * @author     郭明帅
 * @version    1.0, 2014-11-19
 * @see         NewERPCommonMapper
 * @since       全流通改造
 */
public interface NewERPCommonMapper {
	
	/**
	 * 获取主键id
	 * @param params
	 * @return
	 */
	public Long getPrimaryIdNew(Map params);
	
	/**
	 * 新ERP获取CODE值
	 * @param params
	 * @return
	 */
	public String getPrimaryCode(Map params);
	
	/**
	 * 新ERP根据CODE查询ID
	 * @return
	 */
	public Long getIdByTableCode(Map params);
	
	/**
	 * 新ERP根据组织ID查询CODE
	 * @return
	 */
	public String getUnitCodeByUnitId(Map params);
	
	/**
	 * 新ERP根据组织CODE查询ID
	 * @return
	 */
	public Long getUnitIdByUnitCode(Map params);
	
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
	public String getBrandCodeByBrandId(Map params);
	
	/**
	 * 新ERP根据品牌组code查询品牌组id
	 * @param brandId
	 * @return
	 */
	public String getBrandIdByBrandCode(Map params);
	
	/**
	 * 新ERP根据操作员code查询操作员id
	 * @param brandId
	 * @return
	 */
	public Long getOprIdByOprCode(Map params);
	
	/**
	 * 全局系统参数
	 * @param params
	 * @return
	 */
	public String getMainSysParamValue(String code);
	
	/**
	 * 向新ERP临时表TMP_BF_ORG_CODE插入数据
	 * @param list
	 */
	public void saveProdToTmpBfOrgCode(List<String> list);
	
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
	 * 判断组织是否代理商
	 * @param orgId
	 * @return
	 */
	public Integer isAgent(Long orgId);
	
	/**
	 * 判断组织是否分部
	 * @param orgId
	 * @return
	 */
	public Integer isBranch(Long orgId);
	
	/**
	 * 获取数据库时间
	 * @param orgId
	 * @return
	 */
	public Date getSysDate();
	
	/**
	 * 是否启用新ERP
	 * @param unitCode
	 * @return
	 */
	public Long isEnableNewErp(String unitCode);
	
	/**
	 * 根据收货货位或发货货位查询对应的ID
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
	 * 查询单个字典值
	 * @param map
	 * @return
	 */
	public NDirectoryVo selectNDirectory(Map map);
	
	/**
	 * 查询全部字典值
	 * @param map
	 * @return
	 */
	public List<NDirectoryVo> selectAllNDirectory();
}
