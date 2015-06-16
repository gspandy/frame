package mb.erp.dr.o2o.handler;

import java.util.Date;

import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;
import mb.erp.dr.soa.vo.common.NestedException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.bizmonitor.interfaces.bizdata.BmBizDataDubboService;
import com.bizmonitor.interfaces.bizdata.base.ERP.TakeAccountEntity;

public class ExceptionHandler {
	private BmBizDataDubboService bmBizDataDubboService;
	private final Logger logger = LoggerFactory
			.getLogger(ExceptionHandler.class);

	public Object doException(ProceedingJoinPoint joinPoint) {
		Signature s = joinPoint.getSignature();
		String method = s.getName();
		Object[] args = joinPoint.getArgs();
		Object result = null;
		try {
			result = joinPoint.proceed(args);
		} catch (Throwable e) {
			// 订单处理失败
			result = e;
		}
		TakeAccountEntity entity = this.getEntity(args, result);
		// 走账
		bmBizDataDubboService.sendERP_TakeAccountInfo(entity);
		logger.error(method + " " + JSON.toJSONString(entity));
		return result;
	}

	private TakeAccountEntity getEntity(Object[] args, Object result) {
		TakeAccountEntity entity = new TakeAccountEntity();
		String osCode = "";
		String unitId = "";
		String reason = "";
		String status = "1";
		Date createDate = new Date();
		if (null != args && args.length >= 1) {
			Object ovo = args[0];
			//先转换一次
			if(ovo instanceof String){
				ovo=JSON.parseObject(ovo.toString(), SfSchTaskExecOosVo.class);
			}
			if (ovo instanceof SfSchTaskExecOosVo) {
				SfSchTaskExecOosVo vo = (SfSchTaskExecOosVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getVendeeCode();// 买方
				// createDate = vo.getdoc();// 创建时间
			} else if (ovo instanceof BaseBizVo) {
				BaseBizVo vo = (BaseBizVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getUpVendeeId();// 买方
				// createDate = vo.getCreateDate();// 创建时间
			} else if (ovo instanceof AdnVo) {
				AdnVo vo = (AdnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getVendeeId();// 买方
				createDate = vo.getDocDate();// 创建时间
			} else if (ovo instanceof GdnVo) {
				GdnVo vo = (GdnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getUnitId();// 买方
				createDate = vo.getCreDate();// 创建时间
			} else if (ovo instanceof GrnVo) {
				GrnVo vo = (GrnVo) ovo;
				osCode = vo.getOsDocCode();// OS订单号
				unitId = vo.getUnitId();// 买方
				createDate = vo.getCreDate();// 创建时间
			} else {
				logger.error("转换失败");
			}
		}
		if (null != result && result instanceof Throwable) {
			if (result instanceof NestedException) {
				NestedException msg = (NestedException) result;
				status = msg.getEcode();// 失败
				reason = msg.getMessage();
			} else {
				Throwable e = (Throwable) result;
				status = "2";// 失败
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
		}
		entity.setOsOrderNum(osCode);
		entity.setTakeAccountFaileReason(reason);
		entity.setHappenTime(createDate);
		entity.setOrganizations(unitId);
		entity.setStatus(Integer.parseInt(status));
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