package mb.erp.dr.soa.old.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.drools.utils.KieSessionFactory;
import mb.erp.dr.soa.old.dao.AdnMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 计划配货单生成
 * 包括生成计划配货单，确认现货订单和撤销现货订单相关操作
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         AdnService
 * @since       全流通改造
 */
@Service("adnService")
public class AdnService extends BillService<AdnVo> {
	private final Logger logger = LoggerFactory.getLogger(AdnService.class);
	@Resource
	private AdnMapper adnMapper;
	@Resource
	private IdtMapper idtMapper;
    @Resource
    private CommonService commonService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private WarehService warehService;
    @Value("${save.param.null}")
    private String saveParamNull;
    @Value("${save.param.defValue}")
    private String saveParamDefValue;
    
	/**
	 * 生成计划配货单
	 * @param args
	 */
	public MsgVo save(AdnVo adnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.SAVE,O2OBillConstant.BillType.ADN);
		//默认值验证
		String defValueProperty = defValueValidate(adnVo);
    	if (defValueProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(defValueProperty);
			throw new RuntimeException(defValueProperty);
		}
		
		// 验证参数是否为空
    	String nullProperty = nullValidate(adnVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
    	// 得到idtNum的值
        String adnNum = commonService.getPrimaryKey(adnVo.getVenderId(),"ADN_NUM");
    	msg.setBillNum(adnNum);
    	adnVo.setAdnNum(adnNum);
    	if (!AllocType.XXUZ.equals(adnVo.getAllocType())) {
    		adnVo.setOriginAdnNum(adnNum); //转配会用到
		}
        adnMapper.save(adnVo);
		for(AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()){
			adnDtlVo.setAdnNum(adnNum);
			adnMapper.saveDtl(adnDtlVo);
		}
		logger.warn("保存计划配货单,供货方:{},adnNum:{}",adnVo.getVenderId(),adnNum);
		return msg;
	}
	
	/**
	 * 确认计划配货单
	 * 修改现货订单的进度为CN，必须是PG->CN
	 * @param args
	 */
	public MsgVo confirm(AdnVo adnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CONFIRM,O2OBillConstant.BillType.ADN);
		String progress = adnMapper.selectProgress(adnVo);
		if(!O2OBillConstant.PROGRESS.PG.equals(progress)){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("确认计划配货单异常，供货方:"+adnVo.getVenderId()+"，订单编号："+adnVo.getAdnNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			// 规则判断
			IdtVo idtVo = new IdtVo();
			if(!AllocType.XXUZ.equals(adnVo.getAllocType())){
				IdtVo vo = new IdtVo();
				vo.setVendeeId(adnVo.getVendeeId());
				vo.setIdtNum(adnVo.getIdtNum());
				idtVo = idtMapper.select(vo);
			}
			String controlCode = commonService.selectDirectoryCode("ADN","CONFIRM");//"1111"
//			String controlCode = "1011";// TODO 为了测试专项资金，需调用正数冻结
			logger.warn("统一配货是否已经锁定过已分配库存 : "+adnVo.getHadLockWareh());
			if(adnVo.getWarehId().equals(adnVo.getLastFactDispWarehId())
					&& "1".equals(adnVo.getHadLockWareh())){
				// 如果是实际发货仓，且已经锁定过已分配库存，则不锁定已分配库存(实际发货仓在物流模块已经被锁定过已分配库存)
	            logger.warn( adnVo.getWarehId()+"是实际发货仓，且已经锁定过已分配库存 , 不锁定已分配库存" );
	            controlCode="1001";
			}
			
			adnVo.setControlStatus(controlCode);
			KieSession kieSession = KieSessionFactory.getKieSession("adn-rules-confirm");
			kieSession.insert(adnVo);
			kieSession.insert(balanceService);
			kieSession.insert(warehService);
			kieSession.insert(idtVo.getOrderVal());
			kieSession.fireAllRules();
			kieSession.dispose();
			adnVo.setProgress(O2OBillConstant.PROGRESS.CN);
			List<String> progressList = new ArrayList<String>();
			progressList.add(O2OBillConstant.PROGRESS.PG);
			adnVo.setProgressList(progressList);
			adnMapper.update(adnVo);
		}
		return msg;
	}

	/**
	 * 撤销计划配货单
	 * 进度在录入中 PG、已确认 CN 才可以撤销
	 * @param args
	 */
	public MsgVo cancel(AdnVo adnVo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",O2OMsgConstant.BIZTYPE.CANCEL,O2OBillConstant.BillType.ADN);
		String progress = adnMapper.selectProgress(adnVo);
		if(!(O2OBillConstant.PROGRESS.PG.equals(progress)
				|| O2OBillConstant.PROGRESS.CN.equals(progress))){
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("撤销计划配货单异常，供货方:"+adnVo.getVenderId()+"，订单编号："+adnVo.getAdnNum()+"，当前状态为"+progress);
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			adnVo.setCancelled("T");
			adnMapper.update(adnVo);
		}
		return msg;
	}
	
	/**
	 * 验证保存的计划配货单默认值是否匹配
	 * @param tbnVo
	 * @return
	 */
	private String defValueValidate(AdnVo adnVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (!O2OBillConstant.PROGRESS.PG.equals(adnVo.getProgress())) {
			property.append("处理进度  ");
		}
		if (!"F".equals(adnVo.getSuspended())) {
			property.append("是否挂起  ");
		}
		if (!"F".equals(adnVo.getCancelled())) {
			property.append("是否撤销  ");
		}
		if (property.length() > 0) {
			result = MessageFormat.format(saveParamDefValue, property);
		}
		return result;
		
	}
	
	/**
	 * 验证需要保存的现货订单的必要参数是否为空
	 * @param tbnVo
	 * @return
	 */
	private String nullValidate(AdnVo adnVo){
		StringBuffer property = new StringBuffer();
		String result = null;
		if (StringUtils.isEmpty(adnVo.getVendeeId())) {
			property.append("购货方编码  ");
		}
		if (StringUtils.isEmpty(adnVo.getVenderId())) {
			property.append("供货方编码  ");
		}
		if (StringUtils.isEmpty(adnVo.getWarehId())) {
			property.append("发货仓库  ");
		}
		if (StringUtils.isEmpty(adnVo.getBrandId())) {
			property.append("品牌编码  ");
		}
		if (SoaBillUtils.isNotBlank(property.toString())) {
			result = MessageFormat.format(saveParamNull, "计划配货单", property);
		}
		return result;
		
	}

	
}
