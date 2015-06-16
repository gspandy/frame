package mb.erp.dr.o2o.service.impl.consumer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.INVALID_TYPE;
import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;
import mb.erp.dr.soa.service.impl.common.NewTmpInvalidService;
import mb.erp.dr.soa.vo.DrTbnDtlVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.NewTmpInvalidDefVo;
import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtDtlVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdDtlVo;
import mb.erp.dr.soa.vo.SfRvdVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NewExistValidate {
    @Resource
    private NewTmpInvalidService newTmpInvalidService;
    @Value("${save.param.exist}")
    private String saveParamExist; //提示：生成{0}所需的{1}不存在，请核实
    @Value("${save.param.sta}")
    private String saveParamSta; //提示：生成{0}所需的{1}不活动，请核实
    @Value("prodIdList.param.null")
    private String prodIdListParamNull;
    
	/**
	 * 现货单校验
	 * @param sfIdtVo
	 * @return
	 */
	public String existValidateSfIdt(SfIdtVo sfIdtVo){
		String result = null;
		List<NewTmpInvalidDefVo> newTmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		
		// 购货方编码
		NewTmpInvalidDefVo tDefVo1 = new NewTmpInvalidDefVo();
		tDefVo1.setCode(sfIdtVo.getVendeeCode());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		newTmpInvalidDefVos.add(tDefVo1);
		tDefVo1 = new NewTmpInvalidDefVo();
		tDefVo1.setCode(sfIdtVo.getVendeeCode());
		tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo1.setRemark("购货方编码");
		newTmpInvalidDefVos.add(tDefVo1);
		
		// 供货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfIdtVo.getVenderCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfIdtVo.getVenderCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		
		// 订货门店
		NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
		tDefVo3.setCode(sfIdtVo.getShopCode());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo3.setRemark("订货门店");
		newTmpInvalidDefVos.add(tDefVo3);
		tDefVo3 = new NewTmpInvalidDefVo();
		tDefVo3.setCode(sfIdtVo.getShopCode());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo3.setRemark("订货门店");
		newTmpInvalidDefVos.add(tDefVo3);
		
		// 接收仓库
		NewTmpInvalidDefVo tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfIdtVo.getRcvWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfIdtVo.getRcvWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("接收仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(sfIdtVo.getBrandCode());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		newTmpInvalidDefVos.add(tDefVo5);

		
		for (SfIdtDtlVo sfIdtDtl : sfIdtVo.getSfIdtDtlVos()) {
			// 商品
			NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
			temp.setCode(sfIdtDtl.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
			temp = new NewTmpInvalidDefVo();
			temp.setCode(sfIdtDtl.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
		}
			
		MsgVo msg = newTmpInvalidService.isValidityNew(newTmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(newTmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
				String codeType = errorVo.getType();
				String errMsg = "";
				if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
					errMsg = "组织码:"+errorVo.getCode() + "组织ID:"+errorVo.getId();
				}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
					errMsg = "品牌编码:"+errorVo.getCode() + "品牌ID:"+errorVo.getId();
				}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
					errMsg = "商品编码:"+errorVo.getCode() + "商品ID:"+errorVo.getId();
				}
				if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
					result = MessageFormat.format(saveParamExist, "新ERP现货订单", errMsg);
				}else {
					result = MessageFormat.format(saveParamSta, "新ERP现货订单", errMsg);
				}
			}
		}
		return result;
	}
	
	/**
	 * 交货单校验
	 * @param sfDgnVo
	 * @return
	 */
	public String existValidateSfDgn(SfDgnVo sfDgnVo){
		String result = null;
		List<NewTmpInvalidDefVo> newTmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		
		// 供货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfDgnVo.getVenderCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfDgnVo.getVenderCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("供货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		
		// 发货仓库
		NewTmpInvalidDefVo tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfDgnVo.getDispWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("发货仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfDgnVo.getDispWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("发货仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(sfDgnVo.getBrandCode());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		newTmpInvalidDefVos.add(tDefVo5);

		for (SfDgnDtlVo sfDgnDtlVo : sfDgnVo.getSfDgnDtlVos()) {
			// 商品
			NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
			temp.setCode(sfDgnDtlVo.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
			temp = new NewTmpInvalidDefVo();
			temp.setCode(sfDgnDtlVo.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
		}
		
		if (NewBillType.TFO.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.TBN.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.FON.name().equals(sfDgnVo.getSrcDocType())
				|| NewBillType.AAD.name().equals(sfDgnVo.getSrcDocType())) {
			// 接收仓库
			NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
			tDefVo3.setCode(sfDgnVo.getRcvWarehCode());
			tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			tDefVo3.setRemark("接收仓库");
			newTmpInvalidDefVos.add(tDefVo3);
			tDefVo3 = new NewTmpInvalidDefVo();
			tDefVo3.setCode(sfDgnVo.getRcvWarehCode());
			tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
			tDefVo3.setRemark("接收仓库");
			newTmpInvalidDefVos.add(tDefVo3);
			
			// 购货方编码
			NewTmpInvalidDefVo tDefVo1 = new NewTmpInvalidDefVo();
			tDefVo1.setCode(sfDgnVo.getVendeeCode());
			tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			tDefVo1.setRemark("购货方编码");
			newTmpInvalidDefVos.add(tDefVo1);
			tDefVo1 = new NewTmpInvalidDefVo();
			tDefVo1.setCode(sfDgnVo.getVendeeCode());
			tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			tDefVo1.setRemark("购货方编码");
			newTmpInvalidDefVos.add(tDefVo1);
		}
		
		MsgVo msg = newTmpInvalidService.isValidityNew(newTmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(newTmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode() + "组织ID:"+errorVo.getId();
					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode() + "品牌ID:"+errorVo.getId();
					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode() + "商品ID:"+errorVo.getId();
					}
					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "新ERP交货单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "新ERP交货单", errMsg);
					}
			}
		}
		return result;
	}
	
	/**
	 * 调配单校验
	 * @param tbnVo
	 * @return
	 */
	public String existValidateDrTbn(DrTbnVo tbnVo){
		String result = null;
		List<NewTmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		// 购货方编码
		NewTmpInvalidDefVo tDefVo1 = new NewTmpInvalidDefVo();
		tDefVo1.setCode(tbnVo.getVendeeCode());
		tDefVo1.setType(INVALID_TYPE.UNIT);
		tDefVo1.setAction(INVALID_TYPE.EXT);
		tDefVo1.setRemark("购货方编码");
		NewTmpInvalidDefVo tDefVo1_1 = new NewTmpInvalidDefVo();
		tDefVo1_1.setCode(tbnVo.getVendeeCode());
		tDefVo1_1.setType(INVALID_TYPE.UNIT);
		tDefVo1_1.setAction(INVALID_TYPE.STA);
		tDefVo1_1.setRemark("购货方编码");
		
		// 供货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(tbnVo.getVenderCode());
		tDefVo2.setType(INVALID_TYPE.UNIT);
		tDefVo2.setAction(INVALID_TYPE.EXT);
		tDefVo2.setRemark("供货方编码");
		NewTmpInvalidDefVo tDefVo2_1 = new NewTmpInvalidDefVo();
		tDefVo2_1.setCode(tbnVo.getVenderCode());
		tDefVo2_1.setType(INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(INVALID_TYPE.STA);
		tDefVo2_1.setRemark("供货方编码");
		
		
		// 发货仓库
		NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
		tDefVo3.setCode(tbnVo.getDispWarehCode());
		tDefVo3.setType(INVALID_TYPE.UNIT);
		tDefVo3.setAction(INVALID_TYPE.EXT);
		tDefVo3.setRemark("发货仓库");
		NewTmpInvalidDefVo tDefVo3_1 = new NewTmpInvalidDefVo();
		tDefVo3_1.setCode(tbnVo.getDispWarehCode());
		tDefVo3_1.setType(INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(INVALID_TYPE.STA);
		tDefVo3_1.setRemark("发货仓库");
		
		// 接收仓库
		NewTmpInvalidDefVo tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(tbnVo.getRcvWarehCode());
		tDefVo4.setType(INVALID_TYPE.UNIT);
		tDefVo4.setAction(INVALID_TYPE.EXT);
		tDefVo4.setRemark("接收仓库");
		tmpInvalidDefVos.add(tDefVo4);
		NewTmpInvalidDefVo tDefVo4_1 = new NewTmpInvalidDefVo();
		tDefVo4_1.setCode(tbnVo.getRcvWarehCode());
		tDefVo4_1.setType(INVALID_TYPE.UNIT);
		tDefVo4_1.setAction(INVALID_TYPE.STA);
		tDefVo4_1.setRemark("接收仓库");
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(tbnVo.getBrandCode());
		tDefVo5.setType(INVALID_TYPE.BRAND);
		tDefVo5.setAction(INVALID_TYPE.EXT);
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
		
		if (tbnVo.getDtlVos() == null || tbnVo.getDtlVos().size() == 0) {
			return prodIdListParamNull;
		}else {
			for (DrTbnDtlVo tbnDtlVo : tbnVo.getDtlVos()) {
				// 商品
				NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
				temp.setCode(tbnDtlVo.getProdCode());
				temp.setType(INVALID_TYPE.GOODS);
				temp.setAction(INVALID_TYPE.EXT);
				temp.setRemark("商品编码");
				tmpInvalidDefVos.add(temp);
				temp = new NewTmpInvalidDefVo();
				temp.setCode(tbnDtlVo.getProdCode());
				temp.setType(INVALID_TYPE.GOODS);
				temp.setAction(INVALID_TYPE.STA);
				temp.setRemark("商品编码");
				tmpInvalidDefVos.add(temp);
			}
		}
		
		MsgVo msg = newTmpInvalidService.isValidityNew(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(tmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode();
					}else if (INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode();
					}else if (INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode();
					}
					if (INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "新ERP调配单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "新ERP调配单", errMsg);
					}
			}
		}
		return result;
	}
	
	/**
	 * 出库单校验
	 * @param sfGdnVo
	 * @return
	 */
	public String existValidateSfGdn(SfGdnVo sfGdnVo){
		String result = null;
		List<NewTmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		// 发货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfGdnVo.getVenderCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		NewTmpInvalidDefVo tDefVo2_1 = new NewTmpInvalidDefVo();
		tDefVo2_1.setCode(sfGdnVo.getVenderCode());
		tDefVo2_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		// 发货仓库
		NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
		tDefVo3.setCode(sfGdnVo.getDispWarehCode());
		tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		NewTmpInvalidDefVo tDefVo3_1 = new NewTmpInvalidDefVo();
		tDefVo3_1.setCode(sfGdnVo.getDispWarehCode());
		tDefVo3_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
		
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(sfGdnVo.getBrandCode());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		
		tmpInvalidDefVos.add(tDefVo2);
		tmpInvalidDefVos.add(tDefVo2_1);
		tmpInvalidDefVos.add(tDefVo3);
		tmpInvalidDefVos.add(tDefVo3_1);
		tmpInvalidDefVos.add(tDefVo5);
		
		if (sfGdnVo.getSfGdnDtlVos() == null || sfGdnVo.getSfGdnDtlVos().size() == 0) {
			return prodIdListParamNull;
		}else {
			for (SfGdnDtlVo gdnDtlVo : sfGdnVo.getSfGdnDtlVos()) {
				// 商品
				NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
				temp.setCode(gdnDtlVo.getProdCode());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
				tmpInvalidDefVos.add(temp);
				temp = new NewTmpInvalidDefVo();
				temp.setCode(gdnDtlVo.getProdCode());
				temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
				temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
				tmpInvalidDefVos.add(temp);
			}
		}
		
		if (O2OBillConstant.BillType.TFO.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.TBN.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.FON.equals(sfGdnVo.getSrcDocType())
				|| O2OBillConstant.BillType.AAD.equals(sfGdnVo.getSrcDocType())) {
			// 接收组织
			NewTmpInvalidDefVo tDefVo6 = new NewTmpInvalidDefVo();
			tDefVo6.setCode(sfGdnVo.getVendeeCode());
			tDefVo6.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			NewTmpInvalidDefVo tDefVo6_1 = new NewTmpInvalidDefVo();
			tDefVo6_1.setCode(sfGdnVo.getVendeeCode());
			tDefVo6_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo6_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			// 接收仓库
			NewTmpInvalidDefVo tDefVo7 = new NewTmpInvalidDefVo();
			tDefVo7.setCode(sfGdnVo.getRcvWarehCode());
			tDefVo7.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			NewTmpInvalidDefVo tDefVo7_1 = new NewTmpInvalidDefVo();
			tDefVo7_1.setCode(sfGdnVo.getRcvWarehCode());
			tDefVo7_1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo7_1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			
			tmpInvalidDefVos.add(tDefVo6);
			tmpInvalidDefVos.add(tDefVo6_1);
			tmpInvalidDefVos.add(tDefVo7);
			tmpInvalidDefVos.add(tDefVo7_1);
		}
		
		MsgVo msg = newTmpInvalidService.isValidityNew(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(tmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
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
						result = MessageFormat.format(saveParamExist, "新ERP出库单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "新ERP出库单", errMsg);
					}
			}
		}
		return result;
	}
	
	/**
	 * 入库单校验
	 * @param grnVo
	 * @return
	 */
	public String existValidateSfGrn(SfGrnVo grnVo){
		String result = null;
		List<NewTmpInvalidDefVo> tmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		
		// 收货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(grnVo.getVendeeCode());
		tDefVo2.setType(INVALID_TYPE.UNIT);
		tDefVo2.setAction(INVALID_TYPE.EXT);
		NewTmpInvalidDefVo tDefVo2_1 = new NewTmpInvalidDefVo();
		tDefVo2_1.setCode(grnVo.getVendeeCode());
		tDefVo2_1.setType(INVALID_TYPE.UNIT);
		tDefVo2_1.setAction(INVALID_TYPE.STA);
		
		
		// 入库仓库
		NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
		tDefVo3.setCode(grnVo.getRcvWarehCode());
		tDefVo3.setType(INVALID_TYPE.UNIT);
		tDefVo3.setAction(INVALID_TYPE.EXT);
		NewTmpInvalidDefVo tDefVo3_1 = new NewTmpInvalidDefVo();
		tDefVo3_1.setCode(grnVo.getRcvWarehCode());
		tDefVo3_1.setType(INVALID_TYPE.UNIT);
		tDefVo3_1.setAction(INVALID_TYPE.STA);
		
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(grnVo.getBrandCode());
		tDefVo5.setType(INVALID_TYPE.BRAND);
		tDefVo5.setAction(INVALID_TYPE.EXT);
		
		tmpInvalidDefVos.add(tDefVo2);
		tmpInvalidDefVos.add(tDefVo2_1);
		tmpInvalidDefVos.add(tDefVo3);
		tmpInvalidDefVos.add(tDefVo3_1);
		tmpInvalidDefVos.add(tDefVo5);
		
		if (grnVo.getDtlVos() == null || grnVo.getDtlVos().size() == 0) {
			// System.out.println("-----------"+prodIdListParamNull);
			return prodIdListParamNull;
		}else {
			for (SfGrnDtlVo grnDtlVo : grnVo.getDtlVos()) {
				// 商品
				NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
				temp.setCode(grnDtlVo.getProdCode());
				temp.setType(INVALID_TYPE.GOODS);
				temp.setAction(INVALID_TYPE.EXT);
				tmpInvalidDefVos.add(temp);
				temp = new NewTmpInvalidDefVo();
				temp.setCode(grnDtlVo.getProdCode());
				temp.setType(INVALID_TYPE.GOODS);
				temp.setAction(INVALID_TYPE.STA);
				tmpInvalidDefVos.add(temp);
			}
		}
		
		if (NewBillType.TFO.equals(grnVo.getSrcDocType())
				|| NewBillType.TBN.equals(grnVo.getSrcDocType())
				|| NewBillType.FON.equals(grnVo.getSrcDocType())
				|| NewBillType.AAD.equals(grnVo.getSrcDocType())) {
			// 发货组织
			NewTmpInvalidDefVo tDefVo6 = new NewTmpInvalidDefVo();
			tDefVo6.setCode(grnVo.getVenderCode());
			tDefVo6.setType(INVALID_TYPE.UNIT);
			tDefVo6.setAction(INVALID_TYPE.EXT);
			NewTmpInvalidDefVo tDefVo6_1 = new NewTmpInvalidDefVo();
			tDefVo6_1.setCode(grnVo.getVenderCode());
			tDefVo6_1.setType(INVALID_TYPE.UNIT);
			tDefVo6_1.setAction(INVALID_TYPE.STA);
			
			// 发货仓库
			NewTmpInvalidDefVo tDefVo7 = new NewTmpInvalidDefVo();
			tDefVo7.setCode(grnVo.getDispWarehCode());
			tDefVo7.setType(INVALID_TYPE.UNIT);
			tDefVo7.setAction(INVALID_TYPE.EXT);
			NewTmpInvalidDefVo tDefVo7_1 = new NewTmpInvalidDefVo();
			tDefVo7_1.setCode(grnVo.getDispWarehCode());
			tDefVo7_1.setType(INVALID_TYPE.UNIT);
			tDefVo7_1.setAction(INVALID_TYPE.STA);
			
			tmpInvalidDefVos.add(tDefVo6);
			tmpInvalidDefVos.add(tDefVo6_1);
			tmpInvalidDefVos.add(tDefVo7);
			tmpInvalidDefVos.add(tDefVo7_1);
		}
		
		MsgVo msg = newTmpInvalidService.isValidityNew(tmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(tmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode();
					}else if (INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode();
					}else if (INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode();
					}
					if (INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "入库单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "入库单", errMsg);
					}
			}
		}
		return result;
	}
	
	/**
	 * 到货通知单校验
	 * @param sfRvdVo
	 * @return
	 */
	public String existValidateSfRvd(SfRvdVo sfRvdVo){
		String result = null;
		List<NewTmpInvalidDefVo> newTmpInvalidDefVos = new ArrayList<NewTmpInvalidDefVo>();
		
		//收货方编码
		NewTmpInvalidDefVo tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfRvdVo.getVendeeCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo2.setRemark("收货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		tDefVo2 = new NewTmpInvalidDefVo();
		tDefVo2.setCode(sfRvdVo.getVendeeCode());
		tDefVo2.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo2.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo2.setRemark("收货方编码");
		newTmpInvalidDefVos.add(tDefVo2);
		
		// 收货仓库
		NewTmpInvalidDefVo tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfRvdVo.getRcvWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo4.setRemark("收货仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		tDefVo4 = new NewTmpInvalidDefVo();
		tDefVo4.setCode(sfRvdVo.getRcvWarehCode());
		tDefVo4.setType(O2OBillConstant.INVALID_TYPE.UNIT);
		tDefVo4.setAction(O2OBillConstant.INVALID_TYPE.STA);
		tDefVo4.setRemark("收货仓库");
		newTmpInvalidDefVos.add(tDefVo4);
		
		// 品牌
		NewTmpInvalidDefVo tDefVo5 = new NewTmpInvalidDefVo();
		tDefVo5.setCode(sfRvdVo.getBrandCode());
		tDefVo5.setType(O2OBillConstant.INVALID_TYPE.BRAND);
		tDefVo5.setAction(O2OBillConstant.INVALID_TYPE.EXT);
		tDefVo5.setRemark("品牌编码");
		newTmpInvalidDefVos.add(tDefVo5);

		for (SfRvdDtlVo sfRvdDtlVo : sfRvdVo.getSfRvdDtlVos()) {
			// 商品
			NewTmpInvalidDefVo temp = new NewTmpInvalidDefVo();
			temp.setCode(sfRvdDtlVo.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
			temp = new NewTmpInvalidDefVo();
			temp.setCode(sfRvdDtlVo.getProdCode());
			temp.setType(O2OBillConstant.INVALID_TYPE.GOODS);
			temp.setAction(O2OBillConstant.INVALID_TYPE.STA);
			temp.setRemark("商品编码");
			newTmpInvalidDefVos.add(temp);
		}
		
		if (NewBillType.TFO.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.TBN.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.FON.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.AAD.name().equals(sfRvdVo.getSrcDocType())
				|| NewBillType.SAD.name().equals(sfRvdVo.getSrcDocType())) {
			// 发货仓库
			NewTmpInvalidDefVo tDefVo3 = new NewTmpInvalidDefVo();
			tDefVo3.setCode(sfRvdVo.getDispWarehCode());
			tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			tDefVo3.setRemark("接收仓库");
			newTmpInvalidDefVos.add(tDefVo3);
			tDefVo3 = new NewTmpInvalidDefVo();
			tDefVo3.setCode(sfRvdVo.getDispWarehCode());
			tDefVo3.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo3.setAction(O2OBillConstant.INVALID_TYPE.STA);
			tDefVo3.setRemark("接收仓库");
			newTmpInvalidDefVos.add(tDefVo3);
			
			// 发货方编码
			NewTmpInvalidDefVo tDefVo1 = new NewTmpInvalidDefVo();
			tDefVo1.setCode(sfRvdVo.getVenderCode());
			tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.EXT);
			tDefVo1.setRemark("购货方编码");
			newTmpInvalidDefVos.add(tDefVo1);
			tDefVo1 = new NewTmpInvalidDefVo();
			tDefVo1.setCode(sfRvdVo.getVenderCode());
			tDefVo1.setType(O2OBillConstant.INVALID_TYPE.UNIT);
			tDefVo1.setAction(O2OBillConstant.INVALID_TYPE.STA);
			tDefVo1.setRemark("购货方编码");
			newTmpInvalidDefVos.add(tDefVo1);
		}
		
		MsgVo msg = newTmpInvalidService.isValidityNew(newTmpInvalidDefVos);
		if (msg != null) {
			return msg.getMsg() + "的参数填写有误。" ;
		}else {
			List list = newTmpInvalidService.findInvalidNew(newTmpInvalidDefVos);
			NewTmpInvalidDefVo errorVo = null;
			if (list!= null && list.size() > 0 ) {
				errorVo = (NewTmpInvalidDefVo) list.get(0);
					String codeType = errorVo.getType();
					String errMsg = "";
					if (O2OBillConstant.INVALID_TYPE.UNIT.equals(codeType)) {
						errMsg = "组织码:"+errorVo.getCode() + "组织ID:"+errorVo.getId();
					}else if (O2OBillConstant.INVALID_TYPE.BRAND.equals(codeType)) {
						errMsg = "品牌编码:"+errorVo.getCode() + "品牌ID:"+errorVo.getId();
					}else if (O2OBillConstant.INVALID_TYPE.GOODS.equals(codeType)) {
						errMsg = "商品编码:"+errorVo.getCode() + "商品ID:"+errorVo.getId();
					}
					if (O2OBillConstant.INVALID_TYPE.EXT.equals(errorVo.getAction())) {
						result = MessageFormat.format(saveParamExist, "新ERP到货通知单", errMsg);
					}else {
						result = MessageFormat.format(saveParamSta, "新ERP到货通知单", errMsg);
					}
			}
		}
		return result;
	}
}
