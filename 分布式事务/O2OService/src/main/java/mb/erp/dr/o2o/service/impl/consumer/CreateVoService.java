package mb.erp.dr.o2o.service.impl.consumer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.APPROVED;
import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.constant.O2OBillConstant.DATA_SOURCE;
import mb.erp.dr.soa.constant.O2OBillConstant.GdnMode;
import mb.erp.dr.soa.constant.O2OBillConstant.PROGRESS;
import mb.erp.dr.soa.constant.O2OBillConstant.REASON_CODE;
import mb.erp.dr.soa.constant.O2OBillConstant.TORF;
import mb.erp.dr.soa.constant.O2OMsgConstant.MSG_CODE;
import mb.erp.dr.soa.old.service.bill.CommonService;
import mb.erp.dr.soa.old.service.dubbo.SoaJmsDubboService;
import mb.erp.dr.soa.old.service.dubbo.SoaPriceDubboService;
import mb.erp.dr.soa.old.service.price.BrandPriceService;
import mb.erp.dr.soa.old.service.price.RetailPriceRateService;
import mb.erp.dr.soa.old.service.price.SettlementPriceRateService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
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
import mb.erp.dr.soa.old.vo.SysUnitVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.utils.DateUtil;
import mb.erp.dr.soa.utils.SoaBillUtils;
import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class CreateVoService {
	private final Logger logger = LoggerFactory.getLogger(CreateVoService.class);
    @Resource
    private CommonService commonService;
    @Resource
    private BrandPriceService brandPriceService;
    @Resource
    private SoaPriceDubboService soaPriceDubboService;
    @Resource
	private SoaJmsDubboService soaJmsDubboService;
    @Resource
    private SettlementPriceRateService settlementPriceRateService;
    @Resource
    private RetailPriceRateService retailPriceRateService;
    @Resource
    private OldExistValidate oldExistValidate;
    @Resource
    private WarehService warehService;
    @Value("${select.discRate.error}")
    private String selectDiscRateError;
    @Value("${wareh.locId.error}")
    private String warehLocIdError;
    @Value("${rcvWareh.locId.error}")
    private String rcvWarehLocIdError;
    /**
     * 生成现货订单 idt
     * @param vo
     * @param vendeeId
     * @param venderId
     * @param brandID
     * @return
     */
	public IdtVo genIdtVoValue(SfSchTaskExecOosVo vo,String vendeeId, String venderId, String brandID){
		IdtVo idtVo = new IdtVo();
		idtVo.setVendeeId(vendeeId);
		idtVo.setVenderId(venderId);
		idtVo.setUpVendeeId(vo.getUpVendeeId()); // 保存最终供货方，方便三方调配的ADN->TBN 和转配的ADN->ADN
		idtVo.setBillType(BillType.IDT);
		idtVo.setDocDate(DateUtil.today());// 查询专项资金需要用到
        idtVo.setOprId("SA");
        idtVo.setBuyerId("SA");
        idtVo.setSellerId("SA");
        idtVo.setInvsgId("SA");
        idtVo.setRcvWarehId(vo.getShopCode());
        //当前接收仓库为最终接收仓库 设置送货地址
        if(vo.getLastFactRcvWarehId().equals(vo.getShopCode())){
        	SysUnitVo sysUnitVo = commonService.getSysUnitByUnitid(vo.getLastFactRcvWarehId());
        	idtVo.setDelivAddr(sysUnitVo.getAddress());
        }
        //获取税率 老ERP
        Double taxRate = commonService.receiveTaxRate(BillType.IDT, BillType.IDT, vendeeId,"", "",vo.getGdnMode(),null);
        logger.warn("-----现货单税率-----："+taxRate +", "+ BillType.IDT +", "+ BillType.IDT +", "+ vendeeId);
        idtVo.setTaxRate(taxRate);
        idtVo.setCurrency("RMB");
        idtVo.setProgress(PROGRESS.PG);
        idtVo.setSuspended(TORF.FALSE);
        idtVo.setCancelled(TORF.FALSE);
        idtVo.setIdtType(O2OBillConstant.CONSTANT.SP);
        idtVo.setShopId(vo.getShopCode());
        idtVo.setLowIdtFlag(TORF.FALSE);
        idtVo.setBrandId(brandID);
        idtVo.setTopicSeqId((long)0);
        idtVo.setDataSource(DATA_SOURCE.NEWERP);
        idtVo.setApproved(APPROVED.NEWERP); 
        idtVo.setIsOos(SoaBillUtils.isNotBlank(vo.getIsOnline()) ? O2OBillConstant.IS_OOS.M : (SoaBillUtils.isNotBlank(vo.getIsOos()) ? TORF.TRUE : TORF.FALSE));
		idtVo.setCitType(vo.getSrcDocType());
		idtVo.setPidNum(vo.getSrcDocCode());
		idtVo.setGdnMode(vo.getGdnMode()); //出库方式
		idtVo.setOsDocCode(vo.getOsDocCode());
		idtVo.setAllocType(vo.getAllocType()); //配货模式
		idtVo.setRemark("缺色断码生成\r\nOS订单号："+vo.getOsDocCode());
		idtVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		idtVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
		idtVo.setHadLockWareh(vo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
		idtVo.setPubB2cDocCode(vo.getB2cDocCode());// 保存B2C单据号，单据流用到
		idtVo.setExtraParams(vo.getExtraParams());// 保存额外参数（单据流-流程编号）
		// 生成现货单明细
		genIdtDtlVoValue(vo, idtVo);
		// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateIdt(idtVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
        return idtVo;
	}
	
    /**
     * 生成现货订单详情 idtDtl
     * @param vo
     * @param vendeeId
     * @param venderId
     * @param brandID
     * @return
     */
	public IdtVo genIdtDtlVoValue(SfSchTaskExecOosVo vo,IdtVo idtVo) {
		List<IdtDtlVo> idtDtlVos = new ArrayList<IdtDtlVo>();
		Double orderQty = 0d;
		Double orderVal = 0d;
		for(SfSchTaskExecOosDtlVo dtlVo : vo.getLstSfSchTaskExecOosDtls()){
			IdtDtlVo idtDtl = new IdtDtlVo();
			idtDtl.setVendeeId(idtVo.getVendeeId());
			idtDtl.setIdtNum(idtVo.getIdtNum());
			idtDtl.setProdId(dtlVo.getProdNum());
			idtDtl.setOrderQty((double)dtlVo.getQty());
			idtDtl.setLocId(dtlVo.getLocId()); //发货货位编码
			idtDtl.setRcptLocId(dtlVo.getRcptLocId()); //收货货位编码
			logger.warn("*******现货订单：商品："+dtlVo.getProdNum() +" 发货货位："+dtlVo.getLocId()+" 收货货位："+dtlVo.getRcptLocId());
			//线上订单  直营的直接取传过来的值 加盟的取结算价格+利益分享比例
			if (vo.getIsOnline().equals("1")) {
				//获取吊牌价格
				MsgVo msgVo = brandPriceService.getProductOnBrandPrice(dtlVo.getProdNum());
				idtDtl.setUnitPrice(msgVo.getPrice());
				//通过价格接口获取合同折率
				Map<String, String> map =  new HashMap<String,String>();
				map.put("prod_id", dtlVo.getProdNum());
				map.put("agent_id", idtVo.getVendeeId());
				map.put("unit_id", idtVo.getVenderId());
				Double discRate = soaPriceDubboService.selectObject(map);
				if (discRate == null || discRate == 0 ) {
					throw new RuntimeException(MessageFormat.format(selectDiscRateError, idtVo.getVenderId() , idtVo.getVendeeId(),dtlVo.getProdNum()));
				}
				idtDtl.setDiscRate(discRate);
			}else {
				MsgVo msgVo = new MsgVo();
				//直营的取零售价格 加盟的取结算价格   +利益分享比例
		    	if (null == vo.getRcvRatio()) {
		    		throw new RuntimeException("利息分享比例不能为空！");
		    	}
		    	if (vo.getShopCode().equals(vo.getLastFactRcvWarehId())) {
		    		if (idtVo.getVendeeId().equals(idtVo.getVenderId())) {
		    			msgVo = getPrice(dtlVo.getProdNum(), idtVo.getVenderId(), idtVo.getVendeeId(), idtVo.getRcvWarehId(), idtVo.getCurrency());
		    			//如果购货方=供货方，则取零售价格，如果零售价取不到则取吊牌价,折率定100
		    			if (MSG_CODE.SUCCESS.equals(msgVo.getCode())) {
		    				if (msgVo.getPrice() == 0 || msgVo.getDiscRate() == 0) {
		    					//获取吊牌价格
		    					try {
		    						msgVo = brandPriceService.getProductOnBrandPrice(dtlVo.getProdNum());
								} catch (Exception e) {
									if (msgVo.getPrice() == 0) {
										throw new RuntimeException(MessageFormat.format("商品{0}零售价和吊牌价不能为0", dtlVo.getProdNum()));
									}else{
										throw new RuntimeException(e.getMessage());
									}
										
								}
		    				}
		    				idtDtl.setUnitPrice(SoaBillUtils.formatPricePrecisionFour(msgVo.getPrice()));
		    				idtDtl.setDiscRate(msgVo.getDiscRate());
		    			}else {
		    				throw new RuntimeException(msgVo.getMsg());
		    			}
		    		}else {
		    			msgVo = validatePrice(idtDtl.getProdId(),idtVo.getVenderId(),idtVo.getVendeeId(),idtVo.getRcvWarehId(),idtVo.getCurrency(),vo);
		    			idtDtl.setUnitPrice(SoaBillUtils.formatPricePrecisionFour(msgVo.getPrice()));
		    			idtDtl.setDiscRate(msgVo.getDiscRate());
		    		}
				}else {
					msgVo = settlementPriceRateService.getSettlementPriceRateByProdId(dtlVo.getProdNum(), idtVo.getVenderId(), idtVo.getRcvWarehId(), idtVo.getCurrency());
					idtDtl.setUnitPrice(SoaBillUtils.formatPricePrecisionFour(msgVo.getPrice()));
	    			idtDtl.setDiscRate(msgVo.getDiscRate());
				}
			}
			orderQty += idtDtl.getOrderQty();
			orderVal += idtDtl.getOrderQty() * idtDtl.getUnitPrice() * (idtDtl.getDiscRate() / 100);
			logger.warn("现货单：商品："+dtlVo.getProdNum()+"价格:"+idtDtl.getUnitPrice()+"折率："+idtDtl.getDiscRate()+"价格*折率："+idtDtl.getUnitPrice() * (idtDtl.getDiscRate() / 100));
			idtDtlVos.add(idtDtl);
		}
		idtVo.setOrderQty(orderQty); //总数量
		idtVo.setOrderVal(SoaBillUtils.formatPricePrecisionTwo(orderVal)); //总金额
		logger.warn("现货单总价："+orderVal);
		idtVo.setProductCount(Double.valueOf(vo.getLstSfSchTaskExecOosDtls().size())); //品项数
		idtVo.setIdtDtlVos(idtDtlVos);
        return idtVo;
	}
	

	 /**
    * 生成调配单
    * @param vo
    * @param vendeeId
    * @param venderId
    * @param brandID
    * @return
    */
	public TbnVo genTbnBySfSchTaskExecOosVo(SfSchTaskExecOosVo vo,String vendeeId, String venderId, String brandID) {
		TbnVo tbnVo = new TbnVo();
		tbnVo.setDispWarehId(vo.getDispWarehCode());
		tbnVo.setRcvWarehId(vo.getShopCode());
		// 调配单的供求关系要反过来；
		tbnVo.setVenderId(vendeeId);
		tbnVo.setVendeeId(venderId);
		tbnVo.setBillType(BillType.TBN);
		tbnVo.setGdnMode(vo.getGdnMode()); // 出库方式
		tbnVo.setOprId("SA"); // 操作员编码
		tbnVo.setBuyerId("SA");
		tbnVo.setSellerId("SA");
		tbnVo.setInvsgId("SA");
		tbnVo.setBrandId(brandID);
		tbnVo.setReasonCode(REASON_CODE.AB);
		//接收仓库和实际接收仓库相等
		if(vo.getLastFactRcvWarehId().equals(vo.getShopCode())){
			SysUnitVo sysUnitVo = commonService.getSysUnitByUnitid(vo.getLastFactRcvWarehId());
			tbnVo.setRcvAddr(sysUnitVo.getAddress());
			tbnVo.setRcvPhoneNo(sysUnitVo.getPhNum());
		}
		// 获取税率 老ERP，调配单用购货方取税率
		Double taxRate = commonService.receiveTaxRate(BillType.TBN, BillType.TBN,venderId,"", "",vo.getGdnMode(),null);
		logger.warn("-----调配单税率-----："+taxRate +", "+ BillType.TBN +", "+ BillType.TBN +", "+ venderId);
		tbnVo.setTaxRate(taxRate);
		tbnVo.setCurrency("RMB");
		tbnVo.setProgress(PROGRESS.PG);
		tbnVo.setSuspended(TORF.FALSE);
		tbnVo.setCancelled(TORF.FALSE);
		tbnVo.setDataSource(DATA_SOURCE.NEWERP);
		tbnVo.setOsDocCode(vo.getOsDocCode());
		tbnVo.setApproved(O2OBillConstant.APPROVED.NEWERP);// 不用Biztalk传，老ERP的决策方是新ERP，新ERP单子的决策方是老ERP
		tbnVo.setIsOos(SoaBillUtils.isNotBlank(vo.getIsOnline()) ? O2OBillConstant.IS_OOS.M
				: (SoaBillUtils.isNotBlank(vo.getIsOos()) ? TORF.TRUE
						: TORF.FALSE));
		tbnVo.setRemark("缺色断码生成\r\nOS订单号："+tbnVo.getOsDocCode());
		tbnVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		tbnVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
		tbnVo.setHadLockWareh(vo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
		tbnVo.setPubB2cDocCode(vo.getB2cDocCode());// 保存B2C单据号，单据流用到
		tbnVo.setExtraParams(vo.getExtraParams());// 保存额外参数（单据流-流程编号）
		
		virtualWarehTbn(tbnVo);
		List<TbnDtlVo> tbnDtlVos = genTbnDtlVosBySfSchTaskExecOosVo(vo, tbnVo);
		Double crQty = 0d, crVal = 0d;
		for (TbnDtlVo tbnDtlVo : tbnDtlVos) {
			crQty += tbnDtlVo.getExpdQty();
			crVal += tbnDtlVo.getUnitPrice()*tbnDtlVo.getExpdQty()* (tbnDtlVo.getDiscRate() / 100);
		}
		tbnVo.setCrQty(crQty); // 设置调配总数
		tbnVo.setCrVal(SoaBillUtils.formatPricePrecisionTwo(crVal)); // 设置金额总数
		logger.warn("调配单总价："+crVal);
		tbnVo.setAllocType(vo.getAllocType());
		tbnVo.setTbnDtlVos(tbnDtlVos);
		
		// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateTbn(tbnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return tbnVo;
	}
	
   /**
    * 生成调配单详情 tbnDtl
	* @param vo
	* @param tbnVo
    * @return
    */
	public List<TbnDtlVo> genTbnDtlVosBySfSchTaskExecOosVo(SfSchTaskExecOosVo vo,TbnVo tbnVo) {
		List<TbnDtlVo> tbnDtlVos = new ArrayList<TbnDtlVo>();
		for(SfSchTaskExecOosDtlVo dtlVo : vo.getLstSfSchTaskExecOosDtls()){
			TbnDtlVo tbnDtl = new TbnDtlVo();
			tbnDtl.setVenderId(tbnVo.getVenderId());
			tbnDtl.setTbnNum(tbnVo.getTbnNum());
			tbnDtl.setProdId(dtlVo.getProdNum());
			tbnDtl.setExpdQty((double)dtlVo.getQty()); // 调配数量
			tbnDtl.setLocId(dtlVo.getLocId()); //发货货位编码
			tbnDtl.setRcptLocId(dtlVo.getRcptLocId()); //收货货位编码
			logger.warn("*******调配单：商品："+dtlVo.getProdNum() +" 发货货位："+dtlVo.getLocId()+" 收货货位："+dtlVo.getRcptLocId());
			//线上订单
			if (vo.getIsOnline().equals("1")) {
				//获取吊牌价格
				MsgVo msgVo = brandPriceService.getProductOnBrandPrice(dtlVo.getProdNum());
				tbnDtl.setUnitPrice(msgVo.getPrice());
				//通过价格接口获取合同折率
				Map<String, String> map =  new HashMap<String,String>();
				map.put("prod_id", dtlVo.getProdNum());
				map.put("agent_id", tbnVo.getVendeeId());
				map.put("unit_id", tbnVo.getVenderId());
				Double	discRate = soaPriceDubboService.selectObject(map);
				if (discRate == null || discRate == 0 ) {
					throw new RuntimeException(MessageFormat.format(selectDiscRateError, tbnVo.getVenderId() , tbnVo.getVendeeId(),dtlVo.getProdNum()));
				}
				tbnDtl.setDiscRate(discRate);
			}else {
		    	if (null == vo.getRcvRatio()) {
		    		throw new RuntimeException("利息分享比例必能为空！");
		    	}
		    	MsgVo msgVo = getPrice(dtlVo.getProdNum(), tbnVo.getVenderId(), tbnVo.getVendeeId(), tbnVo.getDispWarehId(), tbnVo.getCurrency());
		    	if (msgVo.getPrice() == 0) {
		    		throw new RuntimeException(MessageFormat.format("供货方{0}-购货方{1}-币别{2}-商品{3}没有取到结算价格！", tbnVo.getVendeeId(),tbnVo.getVenderId(),"RMB",dtlVo.getProdNum()));
		    	}else {
		    		tbnDtl.setUnitPrice(SoaBillUtils.formatPricePrecisionFour(msgVo.getPrice()));
					tbnDtl.setDiscRate(msgVo.getDiscRate());
				}
			}
			logger.warn("调配单：商品："+dtlVo.getProdNum()+"价格:"+tbnDtl.getUnitPrice()+"折率："+tbnDtl.getDiscRate()+"价格*折率："+tbnDtl.getUnitPrice() * (tbnDtl.getDiscRate() / 100));
			tbnDtlVos.add(tbnDtl);
		}
       return tbnDtlVos;
	}
	
   /**
    * 根据配货单生成调配单
    * @param vo
    * @return
    */
	public TbnVo genTbnByAdnVo(AdnVo vo) {
		TbnVo tbnVo = new TbnVo();
		IdtVo idtvo = soaJmsDubboService.selectIdtByAdn(vo);
		tbnVo.setDispWarehId(vo.getUpDispWarehId()); // 原始发货仓库
        tbnVo.setRcvWarehId(vo.getWarehId()); // 配货单里面发货仓库
		// 调配单的供求关系要反过来；
		tbnVo.setVenderId(vo.getVenderId());
		tbnVo.setVendeeId(vo.getUpVendeeId()); 
		tbnVo.setBillType(BillType.TBN);
		tbnVo.setOprId("SA"); //	操作员编码
        tbnVo.setBuyerId("SA");
        tbnVo.setSellerId("SA");
        tbnVo.setInvsgId("SA");
        tbnVo.setAdnNum(vo.getAdnNum());
        tbnVo.setGdnMode(vo.getGdnMode()); // 出库方式
        tbnVo.setAllocType(vo.getAllocType());
        tbnVo.setExtraParams(vo.getExtraParams());
        tbnVo.setBrandId(vo.getBrandId());
        tbnVo.setReasonCode(REASON_CODE.AB);
        tbnVo.setRcvAddr(idtvo.getDelivAddr());
        //获取税率 老ERP，调配单用购货方取税率
        Double taxRate = commonService.receiveTaxRate(BillType.TBN, BillType.TBN, tbnVo.getVendeeId(),"", "",vo.getGdnMode(),null);
        logger.warn("-----调配单税率adn-----："+taxRate +", "+ BillType.TBN +", "+ BillType.TBN +", "+ tbnVo.getVendeeId());
        tbnVo.setTaxRate(taxRate);
        tbnVo.setCurrency("RMB");
        tbnVo.setProgress(PROGRESS.PG);
        tbnVo.setSuspended(TORF.FALSE);
        tbnVo.setCancelled(TORF.FALSE);
        tbnVo.setApproved(O2OBillConstant.APPROVED.NEWERP);//不用Biztalk传，老ERP的决策方是新ERP，新ERP单子的决策方是老ERP
        tbnVo.setIsOos(TORF.TRUE);
        tbnVo.setOsDocCode(vo.getOsDocCode());
        tbnVo.setDataSource(DATA_SOURCE.NEWERP);
		tbnVo.setRemark("缺色断码生成\r\nOS订单号："+tbnVo.getOsDocCode());
		tbnVo.setOriginAdnNum(vo.getOriginAdnNum());
		tbnVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		tbnVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
		tbnVo.setHadLockWareh(vo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
		tbnVo.setPubB2cDocCode(vo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		tbnVo.setFactIdtNum(vo.getSrcDocNum());//现货订单编号
		tbnVo.setFactVendeeId(vo.getVendeeId());//实际收货方
		tbnVo.setFactWarehId(vo.getLastFactRcvWarehId());//实际收货仓
		virtualWarehTbn(tbnVo);
		List<TbnDtlVo> tbnDtlVos = genTbnDtlVosByAdnVo(vo, tbnVo);
		tbnVo.setTbnDtlVos(tbnDtlVos);
		Double crQty = 0d,crVal=0d;
		for (TbnDtlVo tbnDtlVo : tbnDtlVos) {
			crQty += tbnDtlVo.getExpdQty();
			crVal += tbnDtlVo.getUnitPrice()*tbnDtlVo.getExpdQty()* (tbnDtlVo.getDiscRate() / 100);
		}
		tbnVo.setCrQty(crQty); // 设置调配总数
		tbnVo.setCrVal(SoaBillUtils.formatPricePrecisionTwo(crVal)); // 设置金额总数
		
		// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateTbn(tbnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return tbnVo;
	}
	
   /**
    * 根据配货单生成调配单详情 tbnDtl
    * @param vo
    * @param tbnVo
    * @return
    */
	public List<TbnDtlVo> genTbnDtlVosByAdnVo(AdnVo vo,TbnVo tbnVo) {
		List<TbnDtlVo> tbnDtlVos = new ArrayList<TbnDtlVo>();
		for(AdnDtlVo dtlVo : vo.getAdnDtlVos()){
			TbnDtlVo tbnDtl = new TbnDtlVo();
			tbnDtl.setVenderId(tbnVo.getVenderId());
			tbnDtl.setTbnNum(tbnVo.getTbnNum());
			tbnDtl.setProdId(dtlVo.getProdId());
			tbnDtl.setExpdQty(dtlVo.getAdmQty()); // 调配数量
	    	MsgVo msgVo = getPrice(dtlVo.getProdId(), tbnVo.getVenderId(), tbnVo.getVendeeId(), tbnVo.getDispWarehId(), tbnVo.getCurrency());
	    	if (msgVo.getPrice() == 0) {
	    		throw new RuntimeException(MessageFormat.format("供货方{0}-购货方{1}-币别{2}-商品{3}没有取到结算价格！", tbnVo.getVendeeId(),tbnVo.getVenderId(),"RMB",dtlVo.getProdId()));
	    	}else {
	    		tbnDtl.setUnitPrice(SoaBillUtils.formatPricePrecisionFour(msgVo.getPrice()));
				tbnDtl.setDiscRate(msgVo.getDiscRate());
			}
			tbnDtl.setLocId(dtlVo.getLocId()); //发货货位编码
			tbnDtl.setRcptLocId(dtlVo.getRcptLocId()); //收货货位编码
			logger.warn("*******调配单adn：商品："+tbnDtl.getProdId() +" 发货货位："+dtlVo.getLocId()+" 收货货位："+dtlVo.getRcptLocId());
			tbnDtlVos.add(tbnDtl);
		}
       return tbnDtlVos;
	}
	
	/**
	 * 根据调配单生成出库单
	 * @param tbnVo
	 * @return
	 */
	public GdnVo genGdnByTbn(TbnVo tbnVo){
		GdnVo gdn = new GdnVo();
    	gdn.setBillType(BillType.GDN);
    	gdn.setDelivMode(tbnVo.getGdnMode().name());
    	// 调配单的供求关系要反过来；
    	gdn.setUnitId(tbnVo.getVendeeId()); // 发货方
    	gdn.setSrcUnitId(tbnVo.getVenderId()); // 联合主键组织
    	gdn.setRcvUnitId(tbnVo.getVenderId()); // 收货方
    	gdn.setRcvWarehId(tbnVo.getRcvWarehId()); // 接收仓库
    	gdn.setWarehId(tbnVo.getDispWarehId()); // 发货仓库
    	gdn.setSrcDocType(BillType.TBN);
    	gdn.setSrcDocNum(tbnVo.getTbnNum());
    	gdn.setCurrency(tbnVo.getCurrency());
    	gdn.setTtlQty(tbnVo.getCrQty());
    	gdn.setTtlVal(tbnVo.getCrVal());
    	Double taxRate = tbnVo.getTaxRate();
    	//Double taxRate = commonService.receiveTaxRate(BillType.GDN, BillType.TBN, tbnVo.getVenderId(), tbnVo.getTbnNum(),tbnVo.getGdnMode());
    	logger.warn("-----出库单税率tbn-----："+taxRate +", "+ BillType.GDN +", "+ BillType.TBN +", "+ tbnVo.getVendeeId());
    	gdn.setTaxRate(taxRate);
    	gdn.setTaxVal(SoaBillUtils.formatPricePrecisionTwo((tbnVo.getCrVal()/(100+taxRate))*taxRate)); // 税额保留两位小数
    	gdn.setCost(1d); // 成本
    	gdn.setAddtCost(0d);//附加成本总计
    	gdn.setPsnVal(0d);//折让金额
    	gdn.setOprId(tbnVo.getOprId());
    	gdn.setCtrlrId(tbnVo.getOprId());
    	gdn.setGradId(tbnVo.getOprId()); // 出库操作人员
    	gdn.setDelivMthd(tbnVo.getDelivMthd()); // 发货方式
    	gdn.setDelivAddr(tbnVo.getRcvAddr());
    	gdn.setDelivPstd(tbnVo.getDelivPstd());
    	gdn.setEfficient(O2OBillConstant.TORF.TRUE);// 是否有效
    	gdn.setStruck(O2OBillConstant.TORF.FALSE);
    	gdn.setCostChg(O2OBillConstant.TORF.FALSE);
    	gdn.setProgress(PROGRESS.PG);
    	gdn.setSuspended(O2OBillConstant.TORF.FALSE);
    	gdn.setCancelled(O2OBillConstant.TORF.FALSE);
    	gdn.setRemark("缺色断码生成\r\nOS订单号："+tbnVo.getOsDocCode());
    	gdn.setBrandId(tbnVo.getBrandId());
    	gdn.setDataSource(tbnVo.getDataSource());
    	gdn.setApproved(tbnVo.getApproved());
    	gdn.setOsDocCode(tbnVo.getOsDocCode());
    	gdn.setOriginAdnNum(tbnVo.getOriginAdnNum());
    	gdn.setLastFactDispWarehId(tbnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
    	gdn.setLastFactRcvWarehId(tbnVo.getLastFactRcvWarehId());// 最终收货仓库
    	gdn.setPubB2cDocCode(tbnVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
    	gdn.setHadLockWareh(tbnVo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
    	gdn.setExtraParams(tbnVo.getExtraParams());
    	gdn.setReqdAt(DateUtil.now());//要求发货时间
    	List<GdnDtlVo> list = new ArrayList<GdnDtlVo>();
    	for (TbnDtlVo tDtlVo : tbnVo.getTbnDtlVos()) {
    		GdnDtlVo gdnDtlVo = new GdnDtlVo();
    		gdnDtlVo.setUnitId(gdn.getUnitId());
    		gdnDtlVo.setProdId(tDtlVo.getProdId());
    		gdnDtlVo.setUnitPrice(tDtlVo.getUnitPrice());
    		gdnDtlVo.setDiscRate(tDtlVo.getDiscRate());
    		gdnDtlVo.setQuantity(tDtlVo.getExpdQty());
    		gdnDtlVo.setUnitAddtCost(0d); // 单位附加成本
    		if (SoaBillUtils.isNotBlank(tDtlVo.getLocId())) {
    			if (gdn.getWarehId().equals(gdn.getLastFactDispWarehId())) {
        			if (commonService.isExistsLoc(gdn.getWarehId(), tDtlVo.getLocId())) {
        				gdnDtlVo.setLocId(tDtlVo.getLocId()); //发货货位编码
        			} else {
        				throw new RuntimeException(MessageFormat.format(warehLocIdError, gdn.getWarehId(),tDtlVo.getLocId()));
        			}
        		} else {
        			gdnDtlVo.setLocId(""); //发货货位编码
        		}
    		}
    		gdnDtlVo.setRcptLocId(tDtlVo.getRcptLocId()); //收货货位编码
    		logger.warn("*******出库单tbn：商品："+gdnDtlVo.getProdId() +" 发货货位："+gdnDtlVo.getLocId()+" 收货货位："+gdnDtlVo.getRcptLocId());
        	list.add(gdnDtlVo);
		}
    	gdn.setProductCount(Double.valueOf(tbnVo.getTbnDtlVos().size()));// 品项数
    	gdn.setGdnDtlVos(list);
    	// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateGdn(gdn);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return gdn;
	}
	
	/**
	 * 根据现货订单生成计划配货单VO
	 * @param vo
	 * @param idtVo
	 * @return
	 */
	public AdnVo genAdnByIdt(IdtVo idtVo,SfSchTaskExecOosVo vo){
		AdnVo adnVo = new AdnVo();
		adnVo.setVendeeId(idtVo.getVendeeId());
		adnVo.setVenderId(idtVo.getVenderId());
		adnVo.setIdtNum(idtVo.getIdtNum());
		adnVo.setDocDate(DateUtil.today());// 查询专项资金需要用到
		adnVo.setBillType(BillType.ADN);
		adnVo.setWarehId(vo.getDispWarehCode()); //发货仓库编码
		adnVo.setDispUnitId(idtVo.getVenderId()); //配发组织编码
		adnVo.setDispUnitType("AG"); //配发组织类型编码
		adnVo.setAdmQty(idtVo.getOrderQty()); //配货数量
		adnVo.setAdmVal(idtVo.getOrderVal()); //配货金额
		adnVo.setDelivQty(idtVo.getDelivQty()); //发货数量
		adnVo.setDelivVal(idtVo.getDelivVal()); //发货金额
		adnVo.setRcvQty(idtVo.getRcvQty()); //收货数量
		adnVo.setRcvVal(idtVo.getRcvVal()); //收货金额
		adnVo.setCtrlrId(idtVo.getOprId()); //操作人编码
		adnVo.setProgress(PROGRESS.PG); //进度
		adnVo.setSuspended(O2OBillConstant.TORF.FALSE); //是否挂起
		adnVo.setCancelled(O2OBillConstant.TORF.FALSE); //是否撤销
		adnVo.setProductCount(idtVo.getProductCount()); //品项数
		adnVo.setBrandId(idtVo.getBrandId()); //品牌ID
		adnVo.setCurrency(idtVo.getCurrency());//币种
		String allocType = "";
		if (AllocType.XX3T.equals(idtVo.getAllocType())) {
			allocType = O2OBillConstant.CONSTANT.ALLOCTION_T;
			adnVo.setDispUnitId(idtVo.getUpVendeeId());
		}else {
			allocType = O2OBillConstant.CONSTANT.ALLOCTION_S;
		}
		adnVo.setAllocationType(allocType); //配货类型
		if (AllocType.XX3Z.equals(idtVo.getAllocType())) {
			adnVo.setDispUnitId(idtVo.getUpVendeeId());
			adnVo.setAllocationType(O2OBillConstant.CONSTANT.UP_ALLOCTION);
		}
		adnVo.setFactWarehId(vo.getUpDispWarehId());//实际发货仓库编码
		adnVo.setTranRcvWarehId(idtVo.getRcvWarehId()); //实际接收仓库编码
		adnVo.setSrcUnitId(idtVo.getVendeeId()); //原始组织编码
		adnVo.setSrcDocNum(idtVo.getIdtNum()); //原始单据编码
		adnVo.setSrcDocType(BillType.IDT); //原始单据类型
		adnVo.setFactVendeeId(idtVo.getVendeeId()); //实际购货方编码
		adnVo.setFactIdtNum(idtVo.getIdtNum()); //实际订单编码
		adnVo.setRemark(idtVo.getRemark());
		adnVo.setGdnMode(idtVo.getGdnMode()); //出库方式
		adnVo.setUpDocCode(idtVo.getIdtNum()); // 上级原始单据编号 (队列使用)
		adnVo.setUpDocType(BillType.IDT); // 上级原始单据类型 (队列使用)
		adnVo.setUpVendeeId(idtVo.getUpVendeeId());// 上级原始购货方 (队列使用) TODO 保存最终供货方，方便三方调配的ADN->TBN 和转配的ADN->ADN
		adnVo.setAllocType(idtVo.getAllocType()); //配货模式
		adnVo.setUpDispWarehId(vo.getUpDispWarehId()); // 原始发货仓，传到计划配货单
		adnVo.setDataSource(idtVo.getDataSource());
		adnVo.setApproved(idtVo.getApproved());
		adnVo.setOsDocCode(idtVo.getOsDocCode());
		adnVo.setLastFactDispWarehId(idtVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		adnVo.setLastFactRcvWarehId(idtVo.getLastFactRcvWarehId());// 最终收货仓库
		adnVo.setHadLockWareh(idtVo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
		adnVo.setPubB2cDocCode(idtVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		adnVo.setExtraParams(idtVo.getExtraParams());// 保存额外参数（单据流-流程编号）
		List<AdnDtlVo> adnDtlVos = new ArrayList<AdnDtlVo>();
		for(IdtDtlVo idtDtlVo : idtVo.getIdtDtlVos()){
			AdnDtlVo adnDtlVo = new AdnDtlVo();
			adnDtlVo.setVenderId(idtVo.getVenderId());
			adnDtlVo.setProdId(idtDtlVo.getProdId()); //商品编码
			adnDtlVo.setUnitPrice(idtDtlVo.getUnitPrice()); //单价
			adnDtlVo.setDiscRate(idtDtlVo.getDiscRate()); //折率
			adnDtlVo.setDelivQty(idtDtlVo.getDelivQty()); //发货数量
			adnDtlVo.setRcvQty(idtDtlVo.getRcvQty()); //收货数量
			adnDtlVo.setAdmQty(idtDtlVo.getOrderQty() == null ? 0 : idtDtlVo.getOrderQty()); //配货数量
			adnDtlVo.setLocId(idtDtlVo.getLocId()); //发货货位编码
			adnDtlVo.setRcptLocId(idtDtlVo.getRcptLocId()); //收货货位编码
			logger.warn("*******计划配货单：商品："+adnDtlVo.getProdId() +" 发货货位："+adnDtlVo.getLocId()+" 收货货位："+adnDtlVo.getRcptLocId());
			adnDtlVos.add(adnDtlVo);
		}
		adnVo.setAdnDtlVos(adnDtlVos);
    	// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateAdn(adnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return  adnVo;
	}
	
	/**
	 * 根据计划配货单生成出库单
	 * @param adnVo
	 * @return
	 */
	public GdnVo genGdnByAdn(AdnVo adnVo){
		GdnVo gdnVo = new GdnVo();
		IdtVo idtVo =  soaJmsDubboService.selectIdtByAdn(adnVo);
		if (idtVo != null) { // 转配的上层转配没有现货订单
			gdnVo.setDelivAddr(idtVo.getDelivAddr());
		}
		gdnVo.setUnitId(adnVo.getVenderId());
		gdnVo.setDocDate(DateUtil.today()); // 查询专项资金需要用到
		gdnVo.setWarehId(adnVo.getWarehId());
		gdnVo.setOprId(adnVo.getCtrlrId()); // 操作人员
		gdnVo.setCtrlrId(adnVo.getCtrlrId());
		gdnVo.setGradId(adnVo.getCtrlrId()); // 出库人员id
		gdnVo.setBrandId(adnVo.getBrandId());
		gdnVo.setRcvUnitId(adnVo.getVendeeId());
		gdnVo.setRcvWarehId(adnVo.getTranRcvWarehId());
		gdnVo.setDispTime(new Date());
		gdnVo.setCurrency("RMB");
		gdnVo.setTtlQty(adnVo.getAdmQty());
		gdnVo.setTtlVal(adnVo.getAdmVal());
		gdnVo.setTaxVal(0d);
		gdnVo.setPsnVal(0d);
		gdnVo.setAddtCost(0d);
		gdnVo.setCost(0d);
		gdnVo.setSrcDocType(BillType.AAD);
		gdnVo.setSrcUnitId(adnVo.getVenderId());
		gdnVo.setSrcDocNum(adnVo.getAdnNum());
		gdnVo.setEfficient("T");
		gdnVo.setStruck("F");
		gdnVo.setCostChg("F");
		gdnVo.setProgress("PG");
		gdnVo.setSuspended("F");
		gdnVo.setCancelled("F");
		gdnVo.setCreDate(new Date());
		gdnVo.setRemark(adnVo.getRemark());
		gdnVo.setReqdAt(new Date());
		gdnVo.setBillType(BillType.GDN);
		gdnVo.setGdnMode(adnVo.getGdnMode());
		Double taxRate = commonService.receiveTaxRate(BillType.GDN, gdnVo.getSrcDocType(), gdnVo.getUnitId(),"", "",adnVo.getGdnMode(),null);
		logger.warn("-----出库单税率adn-----："+taxRate +", "+ BillType.GDN +", "+ gdnVo.getSrcDocType() +", "+ gdnVo.getUnitId());
		gdnVo.setTaxRate(taxRate);
    	gdnVo.setTaxVal(SoaBillUtils.formatPricePrecisionTwo((gdnVo.getTtlVal()/(100+taxRate))*taxRate)); // 税额保留两位小数
		gdnVo.setDelivMode(adnVo.getGdnMode().toString()); //出库方式
		gdnVo.setAllocType(adnVo.getAllocType()); //配货模式 (队列使用)
		gdnVo.setDataSource(adnVo.getDataSource());
		gdnVo.setApproved(adnVo.getApproved());
		gdnVo.setOsDocCode(adnVo.getOsDocCode());
		gdnVo.setUpDocCode(adnVo.getIdtNum()); //上级原始单据编号 (队列使用)
		gdnVo.setOriginAdnNum(adnVo.getOriginAdnNum());
		gdnVo.setLastFactDispWarehId(adnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		gdnVo.setLastFactRcvWarehId(adnVo.getLastFactRcvWarehId());// 最终收货仓库
		gdnVo.setPubB2cDocCode(adnVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		gdnVo.setHadLockWareh(adnVo.getHadLockWareh()); // 判断老erp是否增加已分配库存用
    	gdnVo.setExtraParams(adnVo.getExtraParams());
		List<GdnDtlVo> gdnDtlVos = new ArrayList<GdnDtlVo>();
		for (AdnDtlVo adnDtl : adnVo.getAdnDtlVos()) {
			GdnDtlVo gdnDtlVo = new GdnDtlVo();
			gdnDtlVo.setUnitId(adnDtl.getVenderId());
			gdnDtlVo.setProdId(adnDtl.getProdId());
			gdnDtlVo.setQuantity(adnDtl.getAdmQty());
			gdnDtlVo.setUnitPrice(adnDtl.getUnitPrice());
			gdnDtlVo.setDiscRate(adnDtl.getDiscRate());
			gdnDtlVo.setUnitAddtCost(0d);
			gdnDtlVo.setUnitCost(0d);
			gdnDtlVo.setRemark(adnDtl.getRemark());
			if (SoaBillUtils.isNotBlank(adnDtl.getLocId())) {
				if (gdnVo.getWarehId().equals(adnVo.getLastFactDispWarehId())) {
					if (commonService.isExistsLoc(gdnVo.getWarehId(), adnDtl.getLocId())) {
						gdnDtlVo.setLocId(adnDtl.getLocId()); //发货货位编码
					} else {
						throw new RuntimeException(MessageFormat.format(warehLocIdError, gdnVo.getWarehId(),adnDtl.getLocId()));
					}
				}else {
					gdnDtlVo.setLocId(""); //发货货位编码
				}
			}
			gdnDtlVo.setRcptLocId(adnDtl.getRcptLocId()); //收货货位编码
			logger.warn("*******出库单：商品："+adnDtl.getProdId() +" 发货货位："+adnDtl.getLocId()+" 收货货位："+adnDtl.getRcptLocId());
			gdnDtlVos.add(gdnDtlVo);
		}
		gdnVo.setGdnDtlVos(gdnDtlVos);
		gdnVo.setProductCount(Double.valueOf(adnVo.getAdnDtlVos().size()));// 品项数
		// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateGdn(gdnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
    	
		return  gdnVo;
	}
	
	/**
     * 根据出库单生成入库单
     * @param gdnVo
     * @return
     */
    public GrnVo genGrn(GdnVo gdnVo){
        GrnVo grn = new GrnVo();
        grn.setUnitId(gdnVo.getRcvUnitId()); // 收货方
        grn.setDispUnitId(gdnVo.getUnitId()); // 发货方
        grn.setBillType(BillType.GRN);
        grn.setRcptMode(gdnVo.getDelivMode());// 11月做的几种业务类型，出库模式和入库模式相同
        grn.setWarehId(gdnVo.getRcvWarehId()); // 入库仓库编码
        grn.setOprId(gdnVo.getOprId());
        grn.setCtrlrId(gdnVo.getCtrlrId());
        grn.setGradId(gdnVo.getGradId()); // 入库操作员id
        grn.setSrcDocType(gdnVo.getSrcDocType());
        grn.setSrcDocNum(gdnVo.getSrcDocNum());
        grn.setSrcUnitId(gdnVo.getSrcUnitId());
        grn.setCurrency(gdnVo.getCurrency());
        grn.setTtlQty(gdnVo.getTtlQty());
        grn.setTtlVal(gdnVo.getTtlVal());
        Double taxRate = commonService.receiveTaxRate(BillType.GRN, grn.getSrcDocType(),grn.getUnitId(), grn.getSrcUnitId(), gdnVo.getSrcDocNum(),gdnVo.getGdnMode(),gdnVo.getAllocType());
        logger.warn("----- 入库单获取税率-----："+taxRate+", "+BillType.GRN +", "+ grn.getSrcDocType()+", "+ grn.getUnitId() +", "+ grn.getSrcUnitId() +", "+ gdnVo.getSrcDocNum()+", "+ gdnVo.getAllocType());
        grn.setTaxRate(taxRate);
    	grn.setTaxVal(SoaBillUtils.formatPricePrecisionTwo((gdnVo.getTtlVal()/(100+taxRate))*taxRate)); // 税额保留两位小数
        grn.setPsnVal(gdnVo.getPsnVal()); // 折让金额
        grn.setAddtCost(gdnVo.getAddtCost()); // 附加成本
        grn.setCost(gdnVo.getCost()); // 成本
        grn.setEfficient(TORF.TRUE);// 是否有效
        grn.setStruck(TORF.FALSE);
        grn.setCostChg(TORF.FALSE);
        grn.setProgress(PROGRESS.PG); // 出库单进度录入中
        grn.setSuspended(TORF.FALSE);
        grn.setCancelled(TORF.FALSE);
        grn.setRemark(gdnVo.getRemark());
        grn.setRcvState(REASON_CODE.AB);
        grn.setBrandId(gdnVo.getBrandId()); // 品牌
        grn.setDelivWarehId(gdnVo.getWarehId()); // 发货仓库
        grn.setUpDocCode(gdnVo.getUpDocCode()); //上级原始单据编号 (队列使用)
        grn.setOriginAdnNum(gdnVo.getOriginAdnNum());
        grn.setDataSource(gdnVo.getDataSource());
        grn.setApproved(gdnVo.getApproved());
        grn.setOsDocCode(gdnVo.getOsDocCode());
        grn.setDrTbnId(gdnVo.getDrTbnId());
        grn.setSfDgnCode(gdnVo.getSfDgnCode());
        grn.setSfDgnCodeUPZ(gdnVo.getSfDgnCodeUPZ());
        grn.setGdnNum(gdnVo.getGdnNum());
        grn.setExtraParams(gdnVo.getExtraParams());
        
        grn.setLastFactDispWarehId(gdnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用），有个出库是在入库发起的
        grn.setHadLockWareh(gdnVo.getHadLockWareh()); // 判断老erp是否增加已分配库存用，有个出库是在入库发起的
        grn.setLastFactRcvWarehId(gdnVo.getLastFactRcvWarehId());// 最终收货仓库
        grn.setPubB2cDocCode(gdnVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
        
        List<GrnDtlVo> list = new ArrayList<GrnDtlVo>();
        for (GdnDtlVo gdnDtlVo : gdnVo.getGdnDtlVos()) {
            GrnDtlVo grnDtlVo = new GrnDtlVo();
            grnDtlVo.setUnitId(gdnVo.getRcvUnitId());
            grnDtlVo.setProdId(gdnDtlVo.getProdId());
            grnDtlVo.setUnitPrice(gdnDtlVo.getUnitPrice());
            grnDtlVo.setDiscRate(gdnDtlVo.getDiscRate());
            grnDtlVo.setQuantity(gdnDtlVo.getQuantity());
            grnDtlVo.setUnitAddtCost(gdnDtlVo.getUnitAddtCost()); // 单位附加成本
            grnDtlVo.setDelivQty(gdnDtlVo.getQuantity()); // 发货数量
            if (SoaBillUtils.isNotBlank(gdnDtlVo.getRcptLocId())) {
            	if (grn.getWarehId().equals(grn.getLastFactRcvWarehId())) {
            		if(commonService.isExistsLoc(grn.getWarehId(), gdnDtlVo.getRcptLocId())){
            			grnDtlVo.setRcptLocId(gdnDtlVo.getRcptLocId()); //收货货位编码
            		} else {
            			throw new RuntimeException(MessageFormat.format(rcvWarehLocIdError, grn.getWarehId(),gdnDtlVo.getRcptLocId()));
            		}
            	}else {
            		grnDtlVo.setRcptLocId(""); //收货货位编码
            	}
            }
            logger.warn("*******入库单：商品："+grnDtlVo.getProdId() +" 发货货位："+grnDtlVo.getLocId()+" 收货货位："+grnDtlVo.getRcptLocId());
            grnDtlVo.setLocId(gdnDtlVo.getLocId());
            list.add(grnDtlVo);
        }
        grn.setGrnDtlVos(list);
       
        
     // 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateGrn(grn);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
        return grn;
    }
    
    /**
     * 生成退货申请单
     * @param vo
     * @return BgrVo
     */
	public BgrVo genBgrVoValue(SfSchTaskExecOosVo vo) {
		BgrVo bgrVo = new BgrVo();
		//根据商品读取品牌
		String brandId = getBrandId(vo.getLstSfSchTaskExecOosDtls().get(0).getProdNum());
		bgrVo.setVendeeId(vo.getVendeeCode());
		bgrVo.setVenderId(vo.getVenderCode());
		bgrVo.setRcvWarehId(vo.getShopCode());
		bgrVo.setDispWarehId(vo.getDispWarehCode());
		bgrVo.setBrandId(brandId);
		bgrVo.setBillType(BillType.SBG);
		bgrVo.setDocDate(DateUtil.now());
		bgrVo.setOprId("SA");
		bgrVo.setBuyerId("SA");
		bgrVo.setSellerId("SA");
		bgrVo.setInvsgId("SA");
		bgrVo.setDispOprId("SA");
		bgrVo.setProgress(PROGRESS.PG);
		bgrVo.setCurrency("RMB");
		bgrVo.setCancelled(TORF.FALSE);
		bgrVo.setSuspended(TORF.FALSE);
		bgrVo.setTaxRate(0.0);
		bgrVo.setAllocType(vo.getAllocType());
		bgrVo.setOsDocCode(vo.getOsDocCode());
		bgrVo.setRemark("缺色断码生成\r\nOS订单号："+vo.getOsDocCode());
		//出库方式
		bgrVo.setGdnMode(GdnMode.SHCR);
		//送货地址
        SysUnitVo sysUnitVo = commonService.getSysUnitByUnitid(vo.getShopCode());
        bgrVo.setDelivAddr(sysUnitVo.getAddress());
        
        bgrVo.setLastFactDispWarehId(vo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
        bgrVo.setLastFactRcvWarehId(vo.getLastFactRcvWarehId());// 最终收货仓库
        
        bgrVo.setPubB2cDocCode(vo.getB2cDocCode());// 保存B2C单据号，单据流用到
        bgrVo.setExtraParams(vo.getExtraParams());// 保存额外参数（单据流-流程编号）
        //生成退货申请单明细
        genBgrDtlVoValue(vo,bgrVo);
		
        //参数有效性验证
        String existProperty = oldExistValidate.existValidateBgr(bgrVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return bgrVo;
	}
	
	public GdnVo genGdnByBgr(BgrVo bgrVo, SfSchTaskExecOosVo vo) {
		GdnVo gdnVo = new GdnVo();
		gdnVo.setUnitId(bgrVo.getVendeeId());
		gdnVo.setRcvUnitId(bgrVo.getVenderId());
		gdnVo.setWarehId(bgrVo.getDispWarehId());
		gdnVo.setRcvWarehId(bgrVo.getRcvWarehId());
		gdnVo.setDelivMode(bgrVo.getGdnMode().name());
		gdnVo.setSrcUnitId(bgrVo.getVendeeId());
		
		gdnVo.setDocDate(DateUtil.today());
		gdnVo.setCreDate(DateUtil.today());
		gdnVo.setDispTime(DateUtil.now());
		gdnVo.setOprId(bgrVo.getOprId());
		gdnVo.setCtrlrId(bgrVo.getOprId());
		
		gdnVo.setProgress(PROGRESS.PG);
		gdnVo.setBillType(BillType.GDN);
		gdnVo.setSrcDocType(bgrVo.getBillType());
		gdnVo.setDelivAddr(bgrVo.getDelivAddr());
		gdnVo.setDelivMthd(bgrVo.getDelivMthd());
		gdnVo.setAllocType(bgrVo.getAllocType());
		gdnVo.setBrandId(bgrVo.getBrandId());
		gdnVo.setEfficient(O2OBillConstant.TORF.TRUE);// 是否有效
		gdnVo.setStruck(O2OBillConstant.TORF.FALSE);
		gdnVo.setCostChg(O2OBillConstant.TORF.FALSE);
		gdnVo.setCancelled(TORF.FALSE);
		gdnVo.setSuspended(TORF.FALSE);
		gdnVo.setCurrency(bgrVo.getCurrency());
		gdnVo.setTtlQty(bgrVo.getCrQty());
		gdnVo.setTtlVal(bgrVo.getCrVal());
		gdnVo.setTaxRate(bgrVo.getTaxRate());
		gdnVo.setTaxVal(0d);
		gdnVo.setCost(0d);
		gdnVo.setAddtCost(0d);
		gdnVo.setPsnVal(0d);
		gdnVo.setGdnMode(bgrVo.getGdnMode());
		gdnVo.setSrcDocNum(bgrVo.getBgrNum());
		gdnVo.setOsDocCode(bgrVo.getOsDocCode());
		gdnVo.setRemark(bgrVo.getRemark());
		
		gdnVo.setLastFactDispWarehId(bgrVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		gdnVo.setLastFactRcvWarehId(bgrVo.getLastFactRcvWarehId());// 最终收货仓库
		
		gdnVo.setPubB2cDocCode(bgrVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		gdnVo.setExtraParams(bgrVo.getExtraParams());
    	gdnVo.setReqdAt(DateUtil.now());//要求发货时间
    	
    	List<GdnDtlVo> gdnDtlVos = new ArrayList<GdnDtlVo>();
    	for (BgrDtlVo bgrDtlVo : bgrVo.getBgrDtlVos()) {
    		GdnDtlVo dtlVo = new GdnDtlVo();
    		dtlVo.setUnitId(gdnVo.getUnitId());
    		dtlVo.setGdnNum(gdnVo.getGdnNum());
    		dtlVo.setProdId(bgrDtlVo.getProdId());
    		dtlVo.setQuantity(bgrDtlVo.getCrQty());
    		dtlVo.setUnitPrice(bgrDtlVo.getUnitPrice());
    		dtlVo.setDiscRate(bgrDtlVo.getDiscRate());
    		dtlVo.setUnitAddtCost(0d);
    		dtlVo.setRcptLocId(bgrDtlVo.getRcptLocId());
    		if (SoaBillUtils.isNotBlank(bgrDtlVo.getLocId())) {
    			if (gdnVo.getWarehId().equals(gdnVo.getLastFactDispWarehId())) {
    				if (commonService.isExistsLoc(gdnVo.getWarehId(), bgrDtlVo.getLocId())) {
    					dtlVo.setLocId(bgrDtlVo.getLocId());
    				} else {
    					throw new RuntimeException(MessageFormat.format(warehLocIdError, gdnVo.getWarehId(),bgrDtlVo.getLocId()));
    				}
    			} else {
    				dtlVo.setLocId(""); //发货货位编码
    			}
    		}
    		gdnDtlVos.add(dtlVo);
    	}
    	gdnVo.setProductCount((double) gdnDtlVos.size());
    	gdnVo.setGdnDtlVos(gdnDtlVos);
    	// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateGdn(gdnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return gdnVo;
	}
   
	/**
     * 根据出库单生成退货单
     * @param gdnVo
     * @return ScnVo
     */
	public ScnVo genScnByGdn(GdnVo gdnVo) {
		ScnVo scnVo = new ScnVo();
		scnVo.setVendeeId(gdnVo.getUnitId());
		scnVo.setVenderId(gdnVo.getRcvUnitId());
		scnVo.setDispWarehId(gdnVo.getWarehId());
		scnVo.setRcvWarehId(gdnVo.getRcvWarehId());
		scnVo.setDocDate(DateUtil.today());
		scnVo.setOprId(gdnVo.getOprId());
		scnVo.setInvsgId(gdnVo.getOprId());
		scnVo.setDelivAddr(gdnVo.getDelivAddr());
		scnVo.setGdnMode(gdnVo.getGdnMode());
		scnVo.setDispTime(gdnVo.getDispTime());
		scnVo.setReqdAt(gdnVo.getReqdAt());
		scnVo.setTaxRate(gdnVo.getTaxRate());
		scnVo.setAllocType(gdnVo.getAllocType());
		scnVo.setCurrency(gdnVo.getCurrency());
		scnVo.setCrQty(gdnVo.getTtlQty());
		scnVo.setCrVal(gdnVo.getTtlVal());
		scnVo.setDelivQty(gdnVo.getTtlQty());
		scnVo.setDelivVal(gdnVo.getTtlVal());
		scnVo.setGrossQty(gdnVo.getTtlQty());
		scnVo.setBrandId(gdnVo.getBrandId());
		scnVo.setProgress(PROGRESS.PG);
		scnVo.setSuspended(TORF.FALSE);
		scnVo.setCancelled(TORF.FALSE);
		scnVo.setScnCheckerId(gdnVo.getOprId());
		scnVo.setScnOprId(gdnVo.getOprId());
		scnVo.setDispOprId(gdnVo.getOprId());
		scnVo.setBgrNum(gdnVo.getSrcDocNum());
		scnVo.setPubB2cDocCode(gdnVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		scnVo.setExtraParams(gdnVo.getExtraParams());
		scnVo.setBillType(BillType.SSC);
		scnVo.setOsDocCode(gdnVo.getOsDocCode());
		scnVo.setRemark(gdnVo.getRemark());
		scnVo.setLastFactDispWarehId(gdnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		scnVo.setLastFactRcvWarehId(gdnVo.getLastFactRcvWarehId());// 最终收货仓库
		
		List<ScnDtlVo> scnDtlVos = new ArrayList<ScnDtlVo>();
		for(GdnDtlVo dtlVo : gdnVo.getGdnDtlVos()){
			ScnDtlVo scnDtlVo = new ScnDtlVo();
			scnDtlVo.setVendeeId(scnVo.getVendeeId());
			scnDtlVo.setProdId(dtlVo.getProdId());
			scnDtlVo.setCrQty(dtlVo.getQuantity());
			scnDtlVo.setDelivQty(dtlVo.getQuantity());
			scnDtlVo.setDiscRate(dtlVo.getDiscRate());
			scnDtlVo.setUnitPrice(dtlVo.getUnitPrice());
			scnDtlVo.setLocId(dtlVo.getLocId());
			scnDtlVo.setRcptLocId(dtlVo.getRcptLocId());
			scnDtlVos.add(scnDtlVo);
		}
		scnVo.setProductCount((double)scnDtlVos.size());
		scnVo.setScnDtlVos(scnDtlVos);
		
		// 验证参数是否有效
    	String existProperty = oldExistValidate.existValidateScn(scnVo);
    	if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return scnVo;
	}
	
	/**
	 * 根据退货单生成退货入库单
	 * @param scnVo
	 * @return GrnVo
	 */
	public GrnVo genGrnByScn(ScnVo scnVo) {
		GrnVo grnVo = new GrnVo();
		grnVo.setUnitId(scnVo.getVenderId());
		grnVo.setDispUnitId(scnVo.getVendeeId());
		grnVo.setSrcUnitId(scnVo.getVendeeId());
		grnVo.setDocDate(DateUtil.today());
		grnVo.setCreDate(DateUtil.today());
		grnVo.setWarehId(scnVo.getRcvWarehId());
		grnVo.setDelivWarehId(scnVo.getDispWarehId());
		grnVo.setRcptMode(scnVo.getGdnMode().name());//SHCR
		grnVo.setSrcDocType(scnVo.getBillType());//SSC
		grnVo.setSrcDocNum(scnVo.getScnNum());
		grnVo.setOprId(scnVo.getOprId());
		grnVo.setAllocType(scnVo.getAllocType());
		grnVo.setCurrency(scnVo.getCurrency());
		grnVo.setTtlQty(scnVo.getCrQty());
		grnVo.setTtlVal(scnVo.getCrVal());
		grnVo.setTaxRate(scnVo.getTaxRate());
		grnVo.setTaxVal(0d);
		grnVo.setPsnVal(0d);
		grnVo.setAddtCost(0d);
		grnVo.setRcptTime(DateUtil.now());
		grnVo.setSuspended(TORF.FALSE);
		grnVo.setCancelled(TORF.FALSE);
		grnVo.setEfficient(TORF.TRUE);// 是否有效
		grnVo.setStruck(TORF.FALSE);
		grnVo.setCostChg(TORF.FALSE);
		grnVo.setProgress(PROGRESS.PG);
		grnVo.setBrandId(scnVo.getBrandId());
		grnVo.setLastFactDispWarehId(grnVo.getLastFactDispWarehId());// 最终发货仓库（出库规则使用）
		grnVo.setLastFactRcvWarehId(grnVo.getLastFactRcvWarehId());// 最终收货仓库
		grnVo.setOsDocCode(scnVo.getOsDocCode());
		grnVo.setRemark(scnVo.getRemark());
		
		List<GrnDtlVo> grnDtlVos = new ArrayList<GrnDtlVo>();
		for(ScnDtlVo dtlVo : scnVo.getScnDtlVos()){
			 GrnDtlVo grnDtlVo = new GrnDtlVo();
			 grnDtlVo.setUnitId(scnVo.getVenderId());
	         grnDtlVo.setProdId(dtlVo.getProdId());
	         grnDtlVo.setUnitPrice(dtlVo.getUnitPrice());
	         grnDtlVo.setDiscRate(dtlVo.getDiscRate());
	         grnDtlVo.setQuantity(dtlVo.getCrQty());
	         grnDtlVo.setUnitAddtCost(0d); // 单位附加成本
	         grnDtlVo.setDelivQty(dtlVo.getDelivQty()); // 发货数量
	         grnDtlVo.setLocId(dtlVo.getLocId());
	         if (SoaBillUtils.isNotBlank(dtlVo.getRcptLocId())) {
	        	if (grnVo.getWarehId().equals(grnVo.getLastFactRcvWarehId())) {
	        		if (commonService.isExistsLoc(grnVo.getWarehId(), dtlVo.getRcptLocId())){
	        			grnDtlVo.setRcptLocId(dtlVo.getRcptLocId()); //收货货位编码
	        		} else {
	        			throw new RuntimeException(MessageFormat.format(rcvWarehLocIdError, grnVo.getWarehId(),grnDtlVo.getRcptLocId()));
	        		}
	        	} else {
	        		grnDtlVo.setRcptLocId(""); //收货货位编码
	        	}
	         }
	         grnDtlVos.add(grnDtlVo);
		}
		grnVo.setGrnDtlVos(grnDtlVos);
		grnVo.setPubB2cDocCode(scnVo.getPubB2cDocCode());// 保存B2C单据号，单据流用到
		grnVo.setExtraParams(scnVo.getExtraParams());    
	     
		// 验证参数是否有效
	    String existProperty = oldExistValidate.existValidateGrn(grnVo);
	    if (existProperty != null) {
			throw new RuntimeException(existProperty);
		}
		return grnVo;
	}
	
	
   /**
    * 生成退货申请单明细
    * @param vo
    * @param bgrVo
    */
   private void genBgrDtlVoValue(SfSchTaskExecOosVo vo, BgrVo bgrVo) {
		List<BgrDtlVo> bgrDtlVos = new ArrayList<BgrDtlVo>();
		Double crQty = 0d;
		Double crVal = 0d;
		for(SfSchTaskExecOosDtlVo dtlVo : vo.getLstSfSchTaskExecOosDtls()){
			BgrDtlVo bgrDtlVo = new BgrDtlVo();
			bgrDtlVo.setVendeeId(bgrVo.getVendeeId());
			bgrDtlVo.setBgrNum(bgrVo.getBgrNum());
			bgrDtlVo.setCrQty((double)dtlVo.getQty());
			bgrDtlVo.setLockQty((double)dtlVo.getQty());
			bgrDtlVo.setProdId(dtlVo.getProdNum());
			bgrDtlVo.setLocId(dtlVo.getLocId()); //发货货位编码
			bgrDtlVo.setRcptLocId(dtlVo.getRcptLocId()); //收货货位编码
			logger.warn("*******现货订单：商品："+dtlVo.getProdNum() +" 发货货位："+dtlVo.getLocId()+" 收货货位："+dtlVo.getRcptLocId());
			//直营退货 取传递过来的价格
			if(dtlVo.getPrice() == null || dtlVo.getPrice() == 0) {
				throw new RuntimeException(MessageFormat.format("商品{0}退货价格不存在", dtlVo.getProdNum()));
			}
			bgrDtlVo.setUnitPrice(dtlVo.getPrice());
			bgrDtlVo.setDiscRate(100d);
			crQty += bgrDtlVo.getCrQty();
			crVal += bgrDtlVo.getCrQty() * bgrDtlVo.getUnitPrice() * (bgrDtlVo.getDiscRate() / 100);
			logger.warn("退货申请单：商品："+dtlVo.getProdNum()+"价格:"+bgrDtlVo.getUnitPrice()+"折率："+bgrDtlVo.getDiscRate()+"价格*折率："+bgrDtlVo.getUnitPrice() * (bgrDtlVo.getDiscRate() / 100));
			bgrDtlVos.add(bgrDtlVo);
		}
		bgrVo.setCrQty(crQty);//总数量
		bgrVo.setCrVal(SoaBillUtils.formatPricePrecisionTwo(crVal));//总金额
		bgrVo.setProductCount(Double.valueOf(vo.getLstSfSchTaskExecOosDtls().size())); //品项数
		logger.warn("退货申请单总价："+SoaBillUtils.formatPricePrecisionTwo(crVal));
		bgrVo.setBgrDtlVos(bgrDtlVos);
	}

/**
    * 根据商品编码返回品牌 
    * @param prodNum
    * @return String
    */
    private String getBrandId(String prodNum) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("prod_id", prodNum);
		return soaJmsDubboService.selectBrandByProdID(map);
	}

	/**
     * 调配单设置虚拟仓库
     * @param tbnVo
     */
    private void virtualWarehTbn(TbnVo tbnVo){
    	String rcvWarehId = tbnVo.getRcvWarehId(); // 收货仓库
    	String dispWarehId = tbnVo.getDispWarehId(); // 发货仓库
    	if (SoaBillUtils.isBlank(rcvWarehId)) { // 取虚拟收货仓
    		rcvWarehId = warehService.searchVirtualWarehouse(tbnVo.getVenderId(), tbnVo.getBrandId());
    		tbnVo.setRcvWarehId(rcvWarehId);
		}
    	if (SoaBillUtils.isBlank(dispWarehId)) {// 取虚拟发货仓
    		dispWarehId = warehService.searchVirtualWarehouse(tbnVo.getVendeeId(), tbnVo.getBrandId());
    		tbnVo.setDispWarehId(dispWarehId);
		}
    }
    
    private MsgVo getPrice(String prodNum, String venderCode, String vendeeCode, String rcvWarehId, String currency){
    	MsgVo msgVo = new MsgVo();
    	if (!venderCode.equals(vendeeCode)) {
			//结算价格
    		logger.warn("-----获取结算价格方法-----");
    		msgVo = settlementPriceRateService.getSettlementPriceRateByProdId(prodNum, venderCode, rcvWarehId, currency);
		}else {
			//零售价格
			try {
				logger.warn("-----获取零售价格方法-----");
				msgVo = retailPriceRateService.getRetailPriceRateByProdId(prodNum, rcvWarehId, currency);
			} catch (Exception e) {
				e.printStackTrace();
				//获取吊牌价格
				logger.error(e.getMessage());
				logger.warn("-----获取吊牌价格方法-----");
				try {
					msgVo = brandPriceService.getProductOnBrandPrice(prodNum);
				} catch (Exception e2) {
					throw new RuntimeException(e.getMessage()+","+e2.getMessage());
				}
			}
		}
    	return msgVo;
    }
    
    private MsgVo validatePrice(String prodNum, String venderCode, String vendeeCode, String rcvWarehId, String currency,SfSchTaskExecOosVo vo){
    	MsgVo msgVo = getPrice(prodNum, venderCode, vendeeCode, rcvWarehId, currency);
    	if (MSG_CODE.SUCCESS.equals(msgVo.getCode())) {
			if (msgVo.getPrice() == 0) {
				throw new RuntimeException(MessageFormat.format("供货方{0}-购货方{1}-币别{2}-商品{3}结算价格不能为0！", venderCode, vendeeCode, currency, prodNum));
			}else if (msgVo.getDiscRate() == 0) {
				throw new RuntimeException(MessageFormat.format("供货方{0}-购货方{1}-币别{2}-商品{3}折率不能为0！", venderCode, vendeeCode, currency, prodNum));
			}
		}else {
			throw new RuntimeException(msgVo.getMsg());
		}
    	
    	 //如果购货方！=供货方，如果折率为100%，则价格=结算价+吊牌价*利益分享比例
        //如果购货方！=供货方，如果折率为非100%，则价格不变 折率=折率+利益分享比例
    	boolean flg = commonService.isOldFranchisee(vendeeCode);
    	logger.warn("-----是否加盟-----："+flg);
    	if (flg) {
			if (msgVo.getDiscRate() == 100.0) {
				//获取吊牌价格
				MsgVo msg = brandPriceService.getProductOnBrandPrice(prodNum);
				if (MSG_CODE.ERROR.equals(msg.getCode())) {
					throw new RuntimeException(msg.getMsg());
				}
				msgVo.setPrice(msgVo.getPrice() + msg.getPrice()*vo.getRcvRatio()*0.01);
			}else {
				msgVo.setDiscRate(msgVo.getDiscRate() + vo.getRcvRatio());
			}
		}
    	return msgVo;
    }
}
