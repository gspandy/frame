package mb.erp.dr.soa.service.impl.common;

import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OMsgConstant;
import mb.erp.dr.soa.dao.NewTmpInvalidDefMapper;
import mb.erp.dr.soa.old.vo.TmpInvalidDefVo;
import mb.erp.dr.soa.vo.NewTmpInvalidDefVo;
import mb.erp.dr.soa.vo.common.MsgVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 订单合法性约束校验
 * @author gms
 *
 */
@Service
public class NewTmpInvalidService {
	private final Logger logger = LoggerFactory.getLogger(NewTmpInvalidService.class);
	@Resource
	private NewTmpInvalidDefMapper newTmpInvalidDefMapper;
	@Value("${param.error}")
	private String param_error;
	
	/**
	 * 参数为空校验
	 */
	public MsgVo isValidity(List<TmpInvalidDefVo> tmpInvalidDefVos){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		for(TmpInvalidDefVo tmpInvalid:tmpInvalidDefVos){
			if(StringUtils.isEmpty(tmpInvalid.getCode()) || StringUtils.isEmpty(tmpInvalid.getAction()) 
					|| StringUtils.isEmpty(tmpInvalid.getType())){
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setBizType(param_error);
				msg.setMsg(tmpInvalid.getRemark());
				return msg;
			}
		}
		return null;
	}
	
	/**
	 * 新EPR参数为空校验
	 */
	public MsgVo isValidityNew(List<NewTmpInvalidDefVo> newTmpInvalidDefVos){
		MsgVo msg = new MsgVo(O2OMsgConstant.MSG_CODE.SUCCESS,"","",null);
		for(NewTmpInvalidDefVo newTmpInvalid:newTmpInvalidDefVos){
			if(StringUtils.isEmpty(newTmpInvalid.getCode()) || StringUtils.isEmpty(newTmpInvalid.getAction()) 
					|| StringUtils.isEmpty(newTmpInvalid.getType())){
				msg.setCode(O2OMsgConstant.MSG_CODE.ERROR);
				msg.setBizType(param_error);
				msg.setMsg(newTmpInvalid.getRemark());
				return msg;
			}
		}
		return null;
	}
	
	/**
	 * 新ERP合法性校验
	 */
	public List findInvalidNew(List<NewTmpInvalidDefVo> newTmpInvalidDefVos){
		newTmpInvalidDefMapper.save(newTmpInvalidDefVos);
		List<NewTmpInvalidDefVo> list = newTmpInvalidDefMapper.selectInvalid();
		return list;
	}
}
