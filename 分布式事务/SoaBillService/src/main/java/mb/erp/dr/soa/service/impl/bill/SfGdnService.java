package mb.erp.dr.soa.service.impl.bill;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.GDN_PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.NEW_DOC_STATE;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.service.bill.NewBillService;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.utils.StatusChangeUtils;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 出库单服务
 * 包含接口：保存，确认，撤销
 * @author     郭明帅
 * @version    1.0, 2014-12-07
 * @see         SfGdnService
 * @since       全流通改造
 */
@Service("sfGdnService")
public  class SfGdnService extends NewBillService<SfGdnVo> {
	private final Logger logger = LoggerFactory.getLogger(SfGdnService.class);
	
    @Resource
    private SfGdnMapper sfGdnMapper;
    @Resource
    private NewERPCommonService newERPCommonService;
    @Value("${save.param.null}")
    private String saveParamNull;
    
    final static NewBillType NEW_BILL_TYPE = NewBillType.GDN;
    
    public MsgVo save(SfGdnVo sfGdnVo)  {
    	MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.SAVE);
    	
    	// 验证参数是否为空
    	String nullProperty = nullValidate(sfGdnVo);
    	if (nullProperty != null) {
    		msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg(nullProperty);
			throw new RuntimeException(nullProperty);
		}
    	
        //获取主键ID
    	Long id = newERPCommonService.getPrimaryIdNew("SF_GDN",1);
    	sfGdnVo.setId(id);
    	//获取订单CODE
    	String code = newERPCommonService.getPrimaryCode(NewBillType.GDN.toString(), 1);
    	sfGdnVo.setCode(code);
    	
        sfGdnMapper.save(sfGdnVo);
        for (SfGdnDtlVo sfGdnDtlVo : sfGdnVo.getSfGdnDtlVos()) {
        	Long dtlId = newERPCommonService.getPrimaryIdNew("SF_GDN_DTL",1);
        	sfGdnDtlVo.setId(dtlId);
        	sfGdnDtlVo.setSfGdnId(id);
        	sfGdnMapper.saveDtl(sfGdnDtlVo);
		}
        msg.setCode(code);
        msg.setNewBillId(id);
        logger.warn("保存新ERP出库单,id:{},code:{}",id,code);
        return msg;
    }

    /**
	 * 确认
	 * 修改出库单的进度为CN，必须是PG->CN
	 */
	public MsgVo confirm(SfGdnVo sfGdnVo){
		logger.debug("确认出库单,组织编码:{},ID:{}",sfGdnVo.getBfOrgUnitId(),sfGdnVo.getId());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CONFIRM);
		// 查询出库单当前状态
		int docState = sfGdnMapper.selectDocState(sfGdnVo);
		if (NEW_DOC_STATE.PG_NEW != docState) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP出库单状态更新为【已确认】时发生异常：订单编号："+sfGdnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfGdnVo.setDocState(NEW_DOC_STATE.CN_NEW);
			sfGdnVo.setProgress(O2OBillConstant.PROGRESS.CN);
			
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			sfGdnVo.setDocStateList(docStateList);
			
			sfGdnMapper.updateSfGdn(sfGdnVo);
		}
		return msg;
	}
	
	/**
	 * 撤销	
	 * 可撤销出库单，进度在录入中、已确认、已审核才可以撤销
	 */
	public MsgVo cancel(SfGdnVo sfGdnVo){
		logger.debug("撤销出库单,组织编码:{},ID:{}",sfGdnVo.getBfOrgUnitId(),sfGdnVo.getId());
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.CANCEL);
		// 查询出库单当前状态
		int docState = sfGdnMapper.selectDocState(sfGdnVo);
		if (NEW_DOC_STATE.PG_NEW != docState
				|| NEW_DOC_STATE.CN_NEW != docState) {
			msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
			msg.setMsg("新ERP出库单状态更新为【已撤销】时发生异常：订单编号："+sfGdnVo.getCode()+" 当前订单状态："+StatusChangeUtils.getStatus(docState,NEW_BILL_TYPE));
			logger.error(msg.getMsg());
			throw new RuntimeException(msg.getMsg());
		}else {
			sfGdnVo.setDocState(NEW_DOC_STATE.CC_NEW);
			sfGdnVo.setProgress(O2OBillConstant.PROGRESS.AG);
			List<Integer> docStateList = new ArrayList<Integer>();
			docStateList.add(NEW_DOC_STATE.PG_NEW);
			docStateList.add(NEW_DOC_STATE.CN_NEW);
			sfGdnVo.setDocStateList(docStateList);
			sfGdnMapper.updateSfGdn(sfGdnVo);
		}
		return msg;
	}
	
	/**
	 * 验证需要保存的出库单
	 * @param gdnVo
	 * @return
	 */
	private String nullValidate(SfGdnVo sfGdnVo){
		String property = null;
		String result = null;
		if (null == sfGdnVo.getBfOrgUnitId() || sfGdnVo.getBfOrgUnitId() == 0) {
			property = "发货方编码";
		}else if (null == sfGdnVo.getBfOrgWarehId() || sfGdnVo.getBfOrgWarehId() == 0) {
			property = "发货仓库";
		}else if (null == sfGdnVo.getBrandId() || sfGdnVo.getBrandId() == 0) {
			property = "品牌编码";
		}else if (null == sfGdnVo.getSrcDocType()) {
			property = "订单类型";
		}else if (SoaBillUtils.isBlank( sfGdnVo.getDelivMode())) {
			property = "出库方式";
		}
		if (O2OBillConstant.BillType.TFO.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(sfGdnVo.getSrcDocType())) {
			 if (sfGdnVo.getBfOrgRcvUnitId() == null || sfGdnVo.getBfOrgRcvUnitId() == 0) {
				property = "接收组织";
			}else if (sfGdnVo.getBfOrgRcvWarehId() == null || sfGdnVo.getBfOrgRcvWarehId() == 0) {
				property = "接收仓库";
			}
		}
		if (property != null) {
			result = MessageFormat.format(saveParamNull, "出库单", property);
		}
		return result;
		
	}
    
	@Override
	public MsgVo orderDD(Long gdnId,String code,Long sfGdrnId,GdnVo vo) {
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"",NEW_BILL_TYPE,O2OMsgConstant.BIZTYPE.ORDERDD);
		SfGdnVo sfGdnVo = new SfGdnVo();
		if (SoaBillUtils.isNotBlank(code)) {
			sfGdnVo = sfGdnMapper.selectGdnByCode(code);
		}else {
			sfGdnVo = sfGdnMapper.selectGdnById(gdnId);
		}
		sfGdnVo.setDocState(GDN_PROGRESS.HAVE_DELIVERED);
		List<Integer> docStateList = new ArrayList<Integer>();
		docStateList.add(NEW_DOC_STATE.CN_NEW);
		sfGdnVo.setDocStateList(docStateList);
		sfGdnMapper.updateSfGdn(sfGdnVo);
		return msg;
	}

}
