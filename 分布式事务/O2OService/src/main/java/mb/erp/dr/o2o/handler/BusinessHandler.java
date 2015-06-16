package mb.erp.dr.o2o.handler;

import java.util.Date;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.bizmonitor.interfaces.bizdata.BmBizDataDubboService;
import com.bizmonitor.interfaces.bizdata.base.ERP.InventoryDeductionEntity;

/**
 * 库存扣减监听
 * 
 * @author sun
 * 
 */
public class BusinessHandler {
	private BmBizDataDubboService bmBizDataDubboService;
	private final Logger logger = LoggerFactory
			.getLogger(ExceptionHandler.class);

	public Object doBusiness(ProceedingJoinPoint joinPoint) {
		Signature s = joinPoint.getSignature();
		String method = s.getName();
		Object[] args = joinPoint.getArgs();
		Object result = null;
		try {
			result = joinPoint.proceed(args);
		} catch (Throwable e) {
			// 扣减失败
			result = e;
		}
		InventoryDeductionEntity entity = this.getEntity(args, result);
		// 库存扣减
		bmBizDataDubboService.sendERP_InventoryDeductionInfo(entity);
		logger.error(method + " " + JSON.toJSONString(entity));
		return result;
	}

	private InventoryDeductionEntity getEntity(Object[] args, Object result) {
		InventoryDeductionEntity entity = new InventoryDeductionEntity();
		String osCode = "";
		String unitId = "";
		String reason = "";
		int type = 1;
		Date createDate = new Date();
		if (null != args && args.length >= 2) {
			Object ovo = args[0];
			// Object otype = args[1];订单类型
			if (ovo instanceof GrnVo) {
				GrnVo vo = (GrnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getUnitId();// 买方
				createDate = vo.getCreDate();// 创建时间
			} else if (ovo instanceof GdnVo) {
				GdnVo vo = (GdnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getUnitId();// 买方
				createDate = vo.getCreDate();// 创建时间
			} else if (ovo instanceof SfGrnVo) {
				SfGrnVo vo = (SfGrnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = "" + vo.getUnitId();// 买方
				createDate = vo.getCreateDate();// 创建时间
			} else if (ovo instanceof SfGdnVo) {
				SfGdnVo vo = (SfGdnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = "" + vo.getBfOrgUnitId();// 买方
				createDate = vo.getCreateDate();// 创建时间
			} else {
				logger.error("转换失败");
			}
		}
		if (null != result && result instanceof MsgVo) {
			MsgVo msg = (MsgVo) result;
			if (O2OMsgConstant.MSG_CODE.ERROR.equals(msg.getCode())) {
				type = 2;// 失败
				reason = msg.getMsg();
			}
		} else {
			Throwable e = (Throwable) result;
			type = 2;// 失败
			reason = e.getMessage();
			if(reason!=null&&reason.length()>100){
				Throwable t=e.getCause();
				if(t!=null){
					reason=t.getMessage();
				}else{
					reason=subStringException(reason);
				}
			}
		}
		entity.setOsOrderNum(osCode);
		entity.setFaileReason(reason);
		entity.setHappenTime(createDate);
		entity.setOrganizations(unitId);
		entity.setType(type);
		return entity;
	}
    public String subStringException(String exception){
    	String e = "";
		if (exception.startsWith("java")) {
			e = exception.replaceAll("^java.*Exception:", "");
		}else {
			e = exception.replaceAll("^Exception.*Exception:", "");
		}
		return e;
    }
	public void setBmBizDataDubboService(
			BmBizDataDubboService bmBizDataDubboService) {
		this.bmBizDataDubboService = bmBizDataDubboService;
	}
}
