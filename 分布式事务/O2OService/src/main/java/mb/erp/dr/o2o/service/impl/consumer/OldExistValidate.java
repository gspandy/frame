package mb.erp.dr.o2o.service.impl.consumer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.old.service.impl.common.TmpInvalidService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BgrDtlVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.ScnDtlVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.old.vo.TmpInvalidDefVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 老ERP数据有效性验证
 * @author 余从玉
 */
@SuppressWarnings("rawtypes")
@Service
public class OldExistValidate {

	@Resource
    private TmpInvalidService invalidService;
	@Value("prodIdList.param.null")
	private String prodIdListParamNull;
	@Value("${save.param.null}")
    private String saveParamNull;
    @Value("${save.param.exist}")
    private String saveParamExist;
    @Value("${save.param.sta}")
    private String saveParamSta;
    
    /**
     * 现货订单验证
     * @param idtVo
     * @return
     */
	public String existValidateIdt(IdtVo idtVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		
		// 购货方编码
		TmpInvalidDefVo tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(idtVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(idtVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		
		// 供货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(idtVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(idtVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		
		// 订货门店
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(idtVo.getShopId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("订货门店");
		tmpInvalidDefVos.add(tDefVo3);
		tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(idtVo.getShopId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3.setRemark("订货门店");
		tmpInvalidDefVos.add(tDefVo3);
		
		// 接收仓库
		TmpInvalidDefVo tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(idtVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(idtVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(idtVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		tmpInvalidDefVos.add(tDefVo5);

		
		for (IdtDtlVo idtDtlVo : idtVo.getIdtDtlVos()) {
			// 商品
			TmpInvalidDefVo temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
			temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
				String codeType = errorVo.getType();
				String errMsg = "";
				if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
					errMsg = "组织码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
					errMsg = "品牌编码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
					errMsg = "商品编码:"+errorVo.getCode();
				}
				if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
					result = MessageFormat.format(saveParamExist, "现货订单", errMsg);
				}else {
					result = MessageFormat.format(saveParamSta, "现货订单", errMsg);
				}
			}
		}
		
		return result;
	}
    
    /**
     * 调配单保存验证
     * @param tbnVo
     * @return
     */
	public String existValidateTbn(TbnVo tbnVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		// 购货方编码
		TmpInvalidDefVo tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(tbnVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		TmpInvalidDefVo tDefVo1_1 = new TmpInvalidDefVo();
		tDefVo1_1.setCode(tbnVo.getVendeeId());
		tDefVo1_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1_1.setRemark("购货方编码");
		
		// 供货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(tbnVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		TmpInvalidDefVo tDefVo2_1 = new TmpInvalidDefVo();
		tDefVo2_1.setCode(tbnVo.getVenderId());
		tDefVo2_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2_1.setRemark("供货方编码");
		
		
		// 发货仓库
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(tbnVo.getDispWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("发货仓库");
		TmpInvalidDefVo tDefVo3_1 = new TmpInvalidDefVo();
		tDefVo3_1.setCode(tbnVo.getDispWarehId());
		tDefVo3_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3_1.setRemark("发货仓库");
		
		// 接收仓库
		TmpInvalidDefVo tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(tbnVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		TmpInvalidDefVo tDefVo4_1 = new TmpInvalidDefVo();
		tDefVo4_1.setCode(tbnVo.getRcvWarehId());
		tDefVo4_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4_1.setRemark("接收仓库");
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(tbnVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		
		tmpInvalidDefVos.add(tDefVo1);
		tmpInvalidDefVos.add(tDefVo1_1);
		tmpInvalidDefVos.add(tDefVo2);
		tmpInvalidDefVos.add(tDefVo2_1);
		tmpInvalidDefVos.add(tDefVo3);
		tmpInvalidDefVos.add(tDefVo3_1);
		tmpInvalidDefVos.add(tDefVo4);
		tmpInvalidDefVos.add(tDefVo4_1);
		tmpInvalidDefVos.add(tDefVo5);
		
		if (tbnVo.getTbnDtlVos() == null || tbnVo.getTbnDtlVos().size() == 0) {
			return prodIdListParamNull;
		}else {
			for (TbnDtlVo tbnDtlVo : tbnVo.getTbnDtlVos()) {
				// 商品
				TmpInvalidDefVo temp = new TmpInvalidDefVo();
				temp.setCode(tbnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
				temp.setRemark("商品编码");
				tmpInvalidDefVos.add(temp);
				temp = new TmpInvalidDefVo();
				temp.setCode(tbnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
				temp.setRemark("商品编码");
				tmpInvalidDefVos.add(temp);
			}
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode();
					}
					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "调配单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "调配单", errMsg);
					}
			}
		}
		
		return result;
	}
	
	/**
	 * 配货单的验证
	 * @param adnVo
	 * @return
	 */
	public String existValidateAdn(AdnVo adnVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		
		// 购货方编码
		TmpInvalidDefVo tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(adnVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(adnVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		
		// 供货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(adnVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(adnVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		
		// 接收仓库
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(adnVo.getTranRcvWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo3);
		tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(adnVo.getTranRcvWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo3);
		
		// 发货仓库
		TmpInvalidDefVo tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(adnVo.getWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("发货仓库");
		tmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(adnVo.getWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("发货仓库");
		tmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(adnVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		tmpInvalidDefVos.add(tDefVo5);

		
		for (AdnDtlVo adnDtlVo : adnVo.getAdnDtlVos()) {
			// 商品
			TmpInvalidDefVo temp = new TmpInvalidDefVo();
			temp.setCode(adnDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
			temp = new TmpInvalidDefVo();
			temp.setCode(adnDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			if (list!= null && list.size() > 0 ) {
				StringBuffer sBufferExt = new StringBuffer();
				StringBuffer sBufferSta = new StringBuffer();
				for (Object object : list) {
					TmpInvalidDefVo temp = (TmpInvalidDefVo) object;
					String codeType = temp.getType();
					String action = temp.getAction();
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType) && O2OBillConstant.INVALID_TYPE.EXT.equals(action)) {
						sBufferExt.append("组织码:"+temp.getCode()+" ");
					}
					if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType) && O2OBillConstant.INVALID_TYPE.EXT.equals(action)) {
						sBufferExt.append("品牌编码:"+temp.getCode()+" ");
					}
					if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType) && O2OBillConstant.INVALID_TYPE.EXT.equals(action)) {
						sBufferExt.append("商品编码:"+temp.getCode()+" ");
					}
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)  && O2OBillConstant.INVALID_TYPE.STA.equals(action)) {
						sBufferSta.append("组织码:"+temp.getCode()+" ");
					}
					if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)  && O2OBillConstant.INVALID_TYPE.STA.equals(action)) {
						sBufferSta.append("品牌编码:"+temp.getCode()+" ");
					}
					if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)  && O2OBillConstant.INVALID_TYPE.STA.equals(action)) {
						sBufferSta.append("商品编码:"+temp.getCode()+" ");
					}
				}
				
				if (sBufferExt.length() > 0 && sBufferSta.length() > 0) {
					result = MessageFormat.format(saveParamExist, "计划配货单", sBufferExt.toString()) + "\n" + 
								MessageFormat.format(saveParamSta, "计划配货单", sBufferSta.toString());
				}else if(sBufferExt .length() > 0){
					result = MessageFormat.format(saveParamExist, "计划配货单", sBufferExt.toString());
				}else {
					result = MessageFormat.format(saveParamSta, "计划配货单", sBufferSta.toString());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 出库单验证
	 * @param gdnVo
	 * @return
	 */
	public String existValidateGdn(GdnVo gdnVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		
		// 发货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(gdnVo.getUnitId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		TmpInvalidDefVo tDefVo2_1 = new TmpInvalidDefVo();
		tDefVo2_1.setCode(gdnVo.getUnitId());
		tDefVo2_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		
		// 发货仓库
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(gdnVo.getWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		TmpInvalidDefVo tDefVo3_1 = new TmpInvalidDefVo();
		tDefVo3_1.setCode(gdnVo.getWarehId());
		tDefVo3_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(gdnVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		
		tmpInvalidDefVos.add(tDefVo2);
		tmpInvalidDefVos.add(tDefVo2_1);
		tmpInvalidDefVos.add(tDefVo3);
		tmpInvalidDefVos.add(tDefVo3_1);
		tmpInvalidDefVos.add(tDefVo5);
		
		if (gdnVo.getGdnDtlVos() == null || gdnVo.getGdnDtlVos().size() == 0) {
			return prodIdListParamNull;
		}else {
			for (GdnDtlVo gdnDtlVo : gdnVo.getGdnDtlVos()) {
				// 商品
				TmpInvalidDefVo temp = new TmpInvalidDefVo();
				temp.setCode(gdnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
				tmpInvalidDefVos.add(temp);
				temp = new TmpInvalidDefVo();
				temp.setCode(gdnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
				tmpInvalidDefVos.add(temp);
			}
		}
		
		if (O2OBillConstant.BillType.TFO.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(gdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(gdnVo.getSrcDocType())) {
			// 接收组织
			TmpInvalidDefVo tDefVo6 = new TmpInvalidDefVo();
			tDefVo6.setCode(gdnVo.getRcvUnitId());
			tDefVo6.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			TmpInvalidDefVo tDefVo6_1 = new TmpInvalidDefVo();
			tDefVo6_1.setCode(gdnVo.getRcvUnitId());
			tDefVo6_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			// 接收仓库
			TmpInvalidDefVo tDefVo7 = new TmpInvalidDefVo();
			tDefVo7.setCode(gdnVo.getRcvWarehId());
			tDefVo7.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			TmpInvalidDefVo tDefVo7_1 = new TmpInvalidDefVo();
			tDefVo7_1.setCode(gdnVo.getRcvWarehId());
			tDefVo7_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			tmpInvalidDefVos.add(tDefVo6);
			tmpInvalidDefVos.add(tDefVo6_1);
			tmpInvalidDefVos.add(tDefVo7);
			tmpInvalidDefVos.add(tDefVo7_1);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode();
					}
					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "出库单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "出库单", errMsg);
					}
			}
		}
		
		return result;
	}
	
	/**
	 * 入库单验证
	 * @param grnVo
	 * @return
	 */
	public String existValidateGrn(GrnVo grnVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		
		// 发货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(grnVo.getUnitId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		TmpInvalidDefVo tDefVo2_1 = new TmpInvalidDefVo();
		tDefVo2_1.setCode(grnVo.getUnitId());
		tDefVo2_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		
		// 入库仓库
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(grnVo.getWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		TmpInvalidDefVo tDefVo3_1 = new TmpInvalidDefVo();
		tDefVo3_1.setCode(grnVo.getWarehId());
		tDefVo3_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(grnVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		
		tmpInvalidDefVos.add(tDefVo2);
		tmpInvalidDefVos.add(tDefVo2_1);
		tmpInvalidDefVos.add(tDefVo3);
		tmpInvalidDefVos.add(tDefVo3_1);
		tmpInvalidDefVos.add(tDefVo5);
		
		if (grnVo.getGrnDtlVos() == null || grnVo.getGrnDtlVos().size() == 0) {
			// System.out.println("-----------"+prodIdListParamNull);
			return prodIdListParamNull;
		}else {
			for (GrnDtlVo grnDtlVo : grnVo.getGrnDtlVos()) {
				// 商品
				TmpInvalidDefVo temp = new TmpInvalidDefVo();
				temp.setCode(grnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
				tmpInvalidDefVos.add(temp);
				temp = new TmpInvalidDefVo();
				temp.setCode(grnDtlVo.getProdId());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
				tmpInvalidDefVos.add(temp);
			}
		}
		
		if (O2OBillConstant.BillType.TFO.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(grnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(grnVo.getSrcDocType())) {
			// 发货组织
			TmpInvalidDefVo tDefVo6 = new TmpInvalidDefVo();
			tDefVo6.setCode(grnVo.getDispUnitId());
			tDefVo6.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			TmpInvalidDefVo tDefVo6_1 = new TmpInvalidDefVo();
			tDefVo6_1.setCode(grnVo.getDispUnitId());
			tDefVo6_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			// 发货仓库
			TmpInvalidDefVo tDefVo7 = new TmpInvalidDefVo();
			tDefVo7.setCode(grnVo.getDelivWarehId());
			tDefVo7.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			TmpInvalidDefVo tDefVo7_1 = new TmpInvalidDefVo();
			tDefVo7_1.setCode(grnVo.getDelivWarehId());
			tDefVo7_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			tmpInvalidDefVos.add(tDefVo6);
			tmpInvalidDefVos.add(tDefVo6_1);
			tmpInvalidDefVos.add(tDefVo7);
			tmpInvalidDefVos.add(tDefVo7_1);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode();
					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode();
					}
					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "入库单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "入库单", errMsg);
					}
			}
		}
		
		return result;
	}
	
	  /**
     * 退货申请单订单验证
     * @param idtVo
     * @return
     */
	public String existValidateBgr(BgrVo bgrVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		// 购货方编码
		TmpInvalidDefVo tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(bgrVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(bgrVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		
		// 供货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(bgrVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(bgrVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		
		// 订货门店
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(bgrVo.getDispWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("退货仓库");
		tmpInvalidDefVos.add(tDefVo3);
		tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(bgrVo.getDispWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3.setRemark("退货仓库");
		tmpInvalidDefVos.add(tDefVo3);
		
		// 接收仓库
		TmpInvalidDefVo tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(bgrVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(bgrVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(bgrVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		tmpInvalidDefVos.add(tDefVo5);

		
		for (BgrDtlVo idtDtlVo : bgrVo.getBgrDtlVos()) {
			// 商品
			TmpInvalidDefVo temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
			temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
				String codeType = errorVo.getType();
				String errMsg = "";
				if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
					errMsg = "组织码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
					errMsg = "品牌编码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
					errMsg = "商品编码:"+errorVo.getCode();
				}
				if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
					result = MessageFormat.format(saveParamExist, "现货订单", errMsg);
				}else {
					result = MessageFormat.format(saveParamSta, "现货订单", errMsg);
				}
			}
		}
		
		return result;
	}
	
	
    /**
     * 退货单订单验证
     * @param idtVo
     * @return
     */
	public String existValidateScn(ScnVo scnVo){
		String result = null;
		List<TmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<TmpInvalidDefVo>();
		// 购货方编码
		TmpInvalidDefVo tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(scnVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		tDefVo1 = new TmpInvalidDefVo();
		tDefVo1.setCode(scnVo.getVendeeId());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1.setRemark("购货方编码");
		tmpInvalidDefVos.add(tDefVo1);
		
		// 供货方编码
		TmpInvalidDefVo tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(scnVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new TmpInvalidDefVo();
		tDefVo2.setCode(scnVo.getVenderId());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		tmpInvalidDefVos.add(tDefVo2);
		
		// 订货门店
		TmpInvalidDefVo tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(scnVo.getDispWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("退货仓库");
		tmpInvalidDefVos.add(tDefVo3);
		tDefVo3 = new TmpInvalidDefVo();
		tDefVo3.setCode(scnVo.getDispWarehId());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3.setRemark("退货仓库");
		tmpInvalidDefVos.add(tDefVo3);
		
		// 接收仓库
		TmpInvalidDefVo tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(scnVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new TmpInvalidDefVo();
		tDefVo4.setCode(scnVo.getRcvWarehId());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		TmpInvalidDefVo tDefVo5 = new TmpInvalidDefVo();
		tDefVo5.setCode(scnVo.getBrandId());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		tmpInvalidDefVos.add(tDefVo5);

		
		for (ScnDtlVo idtDtlVo : scnVo.getScnDtlVos()) {
			// 商品
			TmpInvalidDefVo temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
			temp = new TmpInvalidDefVo();
			temp.setCode(idtDtlVo.getProdId());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			tmpInvalidDefVos.add(temp);
		}
		
		MsgVo msg = invalidService.isValidity(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = invalidService.findInvalid(tmpInvalidDefVos);
			TmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (TmpInvalidDefVo) list.get(0);
				String codeType = errorVo.getType();
				String errMsg = "";
				if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
					errMsg = "组织码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
					errMsg = "品牌编码:"+errorVo.getCode();
				}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
					errMsg = "商品编码:"+errorVo.getCode();
				}
				if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
					result = MessageFormat.format(saveParamExist, "退货单", errMsg);
				}else {
					result = MessageFormat.format(saveParamSta, "退货单", errMsg);
				}
			}
		}
		
		return result;
	}
}
