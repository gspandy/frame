package mb.erp.dr.soa.old.service.impl.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.old.dao.BalanceMapper;
import mb.erp.dr.soa.old.dao.BfTaxRateMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.dao.PubO2oFlowMapper;
import mb.erp.dr.soa.old.dao.SysParameterMapper;
import mb.erp.dr.soa.old.dao.SysUnitMapper;
import mb.erp.dr.soa.old.dao.TbnMapper;
import mb.erp.dr.soa.old.dao.common.CommonMapper;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.SysUnitVo;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.NDirectoryVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 通用接口服务
 * 包含接口：获取主键编号、获取账户信息
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         CommonServiceImpl
 * @since       全流通改造
 */
@Service
public class CommonServiceImpl implements CommonService{
	private final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	@Resource
	private CommonMapper commonMapper;
	@Resource
	private BalanceMapper balanceMapper;
	@Resource
	private BfTaxRateMapper bfTaxRateMapper;
	@Resource
	private TbnMapper tbnMapper;
	@Resource
	private IdtMapper idtMapper;
	@Resource
	private SysParameterMapper sysParameterMapper;
	@Resource
	private PubO2oFlowMapper pubO2oFlowMapper;
	@Resource
	private SysUnitMapper sysUnitMapper;

	/**
	 * 获取主键编号
	 * @param pcode 组织编码
	 * @param pnum 需要生成的字段名称
	 * @return
	 */
	public String getPrimaryKey(String pcode,String pnum){
		Map<String, String> params = new HashMap<String, String>();
        params.put("PK_NAME", pcode);
        params.put("PK_NUM", pnum);
		return commonMapper.getPrimaryKey(params);
	}
	
	/**
	 * 查询是否已封账
	 */
	public Boolean isClosedAccount(String unitId, String docNum,
			BillType billType) {
		Integer result = null;
		Map<String, String> params = new HashMap<String, String>();
        params.put("unitId", unitId);
        params.put("docNum", docNum);
		if (BillType.GDN.equals(billType)) {
			result = commonMapper.isClosedAccountByGdn(params);
		}else if (BillType.GRN.equals(billType)) {
			result = commonMapper.isClosedAccountByGrn(params);
		}
		if (result != null && -1 == result ) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取税率 老ERP
	 * @param unitId		组织编码
	 * @param srcUnitId		原始单据组织编码
	 * @param docNum	单据流水号
	 * @param docType	单据类型
	 * @param srcDocType	原始单据类型
	 */
	public Double receiveTaxRate(BillType docType, BillType srcDocType, String unitId, String srcUnitId, String docNum ,GdnMode gdnMode,AllocType allocType) {
		Double defaultTaxRate = 17d;
		Double taxRate = 0d;
		//调拨税率为0
		if (GdnMode.SHOR.equals(gdnMode)) {
			return taxRate;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("unit_id", unitId);
		map.put("docNum", docNum);
		if (BillType.IDT.equals(docType)) {//现货订单:unitId->购货方ID：VENDEE_ID
			taxRate = bfTaxRateMapper.selectTaxRate(map);
		}else if(BillType.TBN.equals(docType)){//调配单:unitId->购货方
			taxRate = bfTaxRateMapper.selectTaxRate(map);
		}else if(BillType.GDN.equals(docType)){//出库单:unitId->原始单据类型=TBN，则为调配单的主键VENDER_ID，否则出库单主键
			//从原始单据（调配单）中获取税率
			if (BillType.TBN.equals(srcDocType)) {
				map.put("unit_id", srcUnitId);
				taxRate = tbnMapper.selectTaxRateFromTbn(map);
			}else {
				 //E_DELIV_MODE.AGOR 配发出库:根据发货方到税率表中获取税率
                //发货方：unitId
				taxRate = bfTaxRateMapper.selectTaxRate(map);
			}
		}else if(docType.equals(BillType.GRN)){//入库单:unitId->原始单据类型=AAD，则为计划配货单的主键VENDER_ID，否则入库单主键
			if (BillType.AAD.equals(srcDocType) && !AllocType.XXUZ.equals(allocType)) {
				//根据计划配货单关联到现货订单-〉从现货订单中获取税率
				map.put("unit_id", srcUnitId);
				taxRate = idtMapper.selectTaxRateFromIdt(map);
			}else {
				//根据unitId到税率表中获取税率
				taxRate = bfTaxRateMapper.selectTaxRate(map);
			}
		}else {
			taxRate = bfTaxRateMapper.selectTaxRate(map);
		}
		
		//无法从数据库获取税率
		if (taxRate < 0) {
			//获取系统参数税率SYS_PARAMETER
			map.put("parm_id", "TAX_RATE_VALUE_ADDED");
			String taxRateStr = sysParameterMapper.selectSysParamValue(map);
			taxRate = SoaBillUtils.isNotBlank(taxRateStr) ? Double.parseDouble(taxRateStr) : defaultTaxRate;
		}
		return taxRate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Long getPrimaryIdOld(String table_name, int count) {
		Map params = new HashMap();
        params.put("table_name", table_name);
        params.put("count", count);
		return commonMapper.getPrimaryIdOld(params);
	}
	
	/**
	 * 获取单据流的流水号和批次号 - 结果信息表写入接口
	 * @return
	 */
	public PubO2oFlowVo getO2oSeqIdAndBatchNo(){
		return pubO2oFlowMapper.getO2oSeqIdAndBatchNo();
	}
	
	/**
	 * 保存单据流信息 - 结果信息表写入接口
	 * @return
	 */
	public Integer savePubO2oFlow(PubO2oFlowVo vo){
		return pubO2oFlowMapper.save(vo);
	}

	/**
	 * 查询老ERP现货订单
	 * @param idtVo
	 * @return
	 */
	public IdtVo select(IdtVo idtVo) {
		return idtMapper.select(idtVo);
	}

	/**
	 * 代理商是否加盟
	 * @return
	 */
	public boolean isOldFranchisee(String vendeeId) {
		return commonMapper.isOldFranchisee(vendeeId) > 0;
	}
	
	/**
	 * 代理商为加盟或直营
	 * @return
	 */
	public boolean isOldFranchiseeOrDirect(String vendeeId) {
		return commonMapper.isOldFranchiseeOrDirect(vendeeId) > 0;
	}

    /**
     * 查询发货货位和收货货位
     * @return
     */
	public Map<String,String> selectLocId(String warehId) {
		return commonMapper.selectLocId(warehId);
	}

	/**
	 * 查询税率
	 */
	public Double selectTaxRate(String unitId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("unit_id", unitId);
		Double taxRate = bfTaxRateMapper.selectTaxRate(map);
		return taxRate;
	}
	
	public String selectDirectoryCode(String type,String name) {
    	if(type==null||name==null)return null;
		Map<String,String> nmap=new HashMap<String,String>();
    	nmap.put("type",type);
    	nmap.put("name",name);
    	NDirectoryVo nvo=commonMapper.selectODirectory(nmap);
    	if(nvo==null||nvo.getCode()==null){
    		throw new RuntimeException("老ERP , 找不到相应的规则码 , type:"+type+" , name:"+name);
    	}
    	return nvo.getCode();
	}

	public SysUnitVo getSysUnitByUnitid(String unitId) {
		return sysUnitMapper.getByPrimaryKey(unitId);
	}
	
	/**
	 * 判断指定仓库的货位是否存在
	 */
	public Boolean isExistsLoc(String warehId, String locId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("warehId", warehId);
		map.put("locId", locId);
		Integer mount = commonMapper.selectByLocIdWarehId(map);
		if(mount != null && mount == 1) {
			return true;
		}
		return false;
	}
}
