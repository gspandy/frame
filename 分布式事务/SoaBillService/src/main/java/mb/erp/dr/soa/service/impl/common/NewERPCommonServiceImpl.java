package mb.erp.dr.soa.service.impl.common;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.ONEORZERO;
import mb.erp.dr.soa.dao.BfOrgMapper;
import mb.erp.dr.soa.dao.DrTbnMapper;
import mb.erp.dr.soa.dao.FiCostGrpMapper;
import mb.erp.dr.soa.dao.FiFsclMonthMapper;
import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfDocFlowMapper;
import mb.erp.dr.soa.dao.SfIdtMapper;
import mb.erp.dr.soa.dao.SfRvdMapper;
import mb.erp.dr.soa.dao.common.NewERPCommonMapper;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.vo.BfOrgVo;
import mb.erp.dr.soa.vo.FiCostGrpVo;
import mb.erp.dr.soa.vo.FiFsclMonthVo;
import mb.erp.dr.soa.vo.SfDocFlowVo;
import mb.erp.dr.soa.vo.common.CommonIdCodeVo;
import mb.erp.dr.soa.vo.common.NDirectoryVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class NewERPCommonServiceImpl implements NewERPCommonService{
	@Resource
	private NewERPCommonMapper newERPCommonMapper;
	@Resource
	private FiCostGrpMapper fiCostGrpMapper;
	@Resource
	private FiFsclMonthMapper fiFsclMonthMapper;
	@Resource
	private DrTbnMapper drTbnMapper;
	@Resource
	private SfIdtMapper sfIdtMapper;
	@Resource
	private SfDgnMapper sfDgnMapper;
	@Resource
	private SfRvdMapper sfRvdMapper;
    @Resource
    private SfDocFlowMapper sfDocFlowMapper;
    @Resource
    private BfOrgMapper bfOrgMapper;
	@Value("${msg.costgrp.isclosed}")
	private String costgrpIsclosed; //提示：成本组{0}的会计期间已经关闭,不能进行加帐
	@Value("${msg.costgrp.nofsclmonth}")
	private String CostgrpNofsclmonth; //提示：成本组{0}未设置会计期间
	
	public Long getPrimaryIdNew(String table_name, int count) {
		Map params = new HashMap();
        params.put("table_name", table_name.toUpperCase());
        params.put("count", count);
		return newERPCommonMapper.getPrimaryIdNew(params);
	}

	/**
	 * 新ERP获取CODE值
	 * @param pname
	 * @param count
	 * @return
	 */
	public String getPrimaryCode(String pname, int count) {
		Map params = new HashMap();
        params.put("bill_type", pname);
        params.put("count", count);
		return newERPCommonMapper.getPrimaryCode(params);
	}
	
	/**
	 * 新ERP根据CODE查询ID
	 * @return
	 */
	public Long getIdByTableCode(String table_name, String code) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("table_name", table_name);
        params.put("code", code);
		return newERPCommonMapper.getIdByTableCode(params);
	}
	
	/**
	 * 新ERP根据组织ID查询CODE
	 * @return
	 */
	public String getUnitCodeByUnitId(Long unitId) {
		Map params = new HashMap();
        params.put("unid_id", unitId);
		return newERPCommonMapper.getUnitCodeByUnitId(params);
	}
	
	/**
	 * 新ERP根据组织CODE查询ID
	 * @return
	 */
	public Long getUnitIdByUnitCode(String unitCode) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("code", unitCode);
		return newERPCommonMapper.getUnitIdByUnitCode(params);
	}
	
	/**
	 * 新ERP根据商品ID查询商品PROD_NUM
	 * @return
	 */
	public String getProdNumByProdId(Long prodId) {
		return newERPCommonMapper.getProdNumByProdId(prodId);
	}
	
	/**
	 * 新ERP根据商品PROD_NUM查询商品ID
	 * @return
	 */
	public Long getProdIdByProdNum(String prodNum) {
		return newERPCommonMapper.getProdIdByProdNum(prodNum);
	}
	
	/**
	 * 新ERP根据品牌组id查询品牌code
	 * @param brandId
	 * @return
	 */
	public String getBrandCodeByBrandId(String brandId) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("key_code", brandId);
		return newERPCommonMapper.getBrandCodeByBrandId(params);
	}
	
	/**
	 * 新ERP根据品牌组code查询品牌组id
	 * @param brandId
	 * @return
	 */
	public String getBrandIdByBrandCode(String brandCode) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("at_code", brandCode);
		return newERPCommonMapper.getBrandIdByBrandCode(params);
	}
	
	/**
	 * 新ERP根据操作员code查询操作员id
	 * @param brandId
	 * @return
	 */
	public Long getOprIdByOprCode(String oprCode) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("code", oprCode);
		return newERPCommonMapper.getOprIdByOprCode(params);
	}
	
	/**
	 * 全局系统参数
	 * @param params
	 * @return
	 */
	public String getMainSysParamValue(String code) {
		return newERPCommonMapper.getMainSysParamValue(code);
	}
	
	/**
	 * 新ERP根据ID查询CODE
	 * @param map
	 * @return
	 */
	public CommonIdCodeVo getCodeById(CommonIdCodeVo idCode){
		return newERPCommonMapper.getCodeById(idCode);
	}
	
	/**
	 * 新ERP根据CODE查询ID
	 * @param list
	 */
	public CommonIdCodeVo getIdByCode(CommonIdCodeVo idCodeVo) {
		String brandCode = idCodeVo.getBrandCode();
		CommonIdCodeVo commonIdCodeVo = newERPCommonMapper.getIdByCode(idCodeVo);
		String brandId = 	commonIdCodeVo.getBrandId();
		if (brandCode != null && brandId == null) {
			throw new RuntimeException("新ERP的商品品牌编码："+brandCode+"未设置。");
		}
		return commonIdCodeVo;
	}
	
	public Boolean isAgent(Long orgId){
		Integer result = newERPCommonMapper.isAgent(orgId);
		if (result != null && result > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean isBranch(Long orgId){
		Integer result = newERPCommonMapper.isBranch(orgId);
		if (result != null && result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检测会计期间
	 * @param warehId  发货仓库ID
	 * @param delivMode  出库方式
	 * @param docDate  单据日期
	 */
	public void checkFiFslcMonth(Long warehId, String delivMode) {
		//仓库是否参与成本
		int count = fiCostGrpMapper.isHaveCost(warehId);
		if (count > 0) {
			//根据仓库获取成本组
			FiCostGrpVo fiCostGrpVo = fiCostGrpMapper.getByWarehId(warehId);
			//成本组是否启用
			if (fiCostGrpVo != null && ONEORZERO.ONE.equals(fiCostGrpVo.getOpMode())) {
				//成本组 会计期间
				List<FiFsclMonthVo> fiFsclMonthVos = fiFsclMonthMapper.getByCostGrp(fiCostGrpVo.getId());
				FiFsclMonthVo fiFsclMonthVo = null;
				if (fiFsclMonthVos != null && fiFsclMonthVos.size() > 0) {
					Date date = newERPCommonMapper.getSysDate();
					for (FiFsclMonthVo fiFsclMonth : fiFsclMonthVos) {
						if (fiFsclMonth.getFromDate().getTime() < date.getTime() && date.getTime() < fiFsclMonth.getUntilDate().getTime()) {
							fiFsclMonthVo = fiFsclMonth;
							break;
						}
					}
					if (fiFsclMonthVo != null) {
						//判断是否封账
						if(ONEORZERO.ONE.equals(fiFsclMonthVo.getIsClosed())){
							throw new RuntimeException(MessageFormat.format(costgrpIsclosed,fiFsclMonthVo.getCostGrpId()));
						}
					}
				}else {
					throw new RuntimeException(MessageFormat.format(CostgrpNofsclmonth,fiCostGrpVo.getCode()));
				}
			}
		}
	}
	
	/**
	 * 保存单据流信息 
	 * vo中必传参数 源头单据类型、源头单据编码、单据执行状态、单据组织编码、
	 * 单据类型（出库单、交货单、入库单、到货通知单）、单据编码、操作人、数据来源
	 */
	public Integer saveSfDocFlow(SfDocFlowVo vo) {
		return sfDocFlowMapper.saveSfDocFlow(vo);
	}

	/**
	 * 是否启用新ERP
	 * @param unitCode
	 * @return
	 */
	public Boolean isEnableNewErp(String unitCode) {
		Long count = newERPCommonMapper.isEnableNewErp(unitCode);
		if (count != null && count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据locCode,warehId获取locId
	 * @param map
	 * @return
	 */
	public Long getLocIdByLocCode(Map map) {
		return newERPCommonMapper.getLocIdByLocCode(map);
	}

	/**
	 * 根据发货仓库id查询发货货位编码
	 * @return
	 */
	public Long getDispLocId(Long bfOrgId) {
		return newERPCommonMapper.getDispLocId(bfOrgId);
	}

	/**
	 * 根据收货仓库id查询收货货位编码
	 * @return
	 */
	public Long getRcptLocId(Long bfOrgId) {
		return newERPCommonMapper.getRcptLocId(bfOrgId);
	}

	/**
	 * 生成B2C单据流之后，要更改B2C原始单据的状态（原始出库单）
	 * @param srcDocCode
	 * @return
	 */
	public Integer upSfGdnExFlag(String srcDocCode){
		return newERPCommonMapper.upSfGdnExFlag(srcDocCode);
	}
	
	/**
	 * 生成B2C单据流之后，要更改B2C原始单据的状态（原始门店发货单）
	 * @param srcDocCode
	 * @return
	 */
	public Integer upSfDegExFlag(String srcDocCode){
		return newERPCommonMapper.upSfDegExFlag(srcDocCode);
	}

	/**
	 * 根据新ERPtbn_num获取税率
	 * @param tbn_num
	 * @return
	 */
	public Double selectTaxRateFromDrTbn(String tbn_num) {
		return drTbnMapper.selectTaxRateFromDrTbn(tbn_num);
	}
	
	/**
	 * 根据新ERP现货单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfIdt(String code) {
		return sfIdtMapper.selectTaxRateFromSfIdt(code);
	}

	/**
	 * 根据新ERP交货单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfDgn(String code) {
		return sfDgnMapper.selectTaxRateFromSfDgn(code);
	}
	
	/**
	 * 根据新ERP到货通知单code获取税率
	 * @param code
	 * @return
	 */
	public Double selectTaxRateFromSfRvd(String code) {
		return sfRvdMapper.selectTaxRateFromSfRvd(code);
	}

	/**
	 * 查询规则控制的值
	 * @param type
	 * @param name
	 * @return
	 */
	public String selectDirectoryCode(String type,String name) {
		if(type==null||name==null)return null;
		Map<String,String> nmap=new HashMap<String,String>();
    	nmap.put("type",type);
    	nmap.put("name",name);
    	NDirectoryVo nvo=newERPCommonMapper.selectNDirectory(nmap);
    	if(nvo==null||nvo.getCode()==null){
    		throw new RuntimeException("新ERP , 找不到相应的规则码 , type:"+type+" , name:"+name);
    	}
    	return nvo.getCode();
	}

	public BfOrgVo getBfOrgByCode(String code) {
		return bfOrgMapper.getBfOrgByCode(code);
	}

}
