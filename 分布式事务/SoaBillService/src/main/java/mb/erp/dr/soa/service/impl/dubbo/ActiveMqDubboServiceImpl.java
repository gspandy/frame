package mb.erp.dr.soa.service.impl.dubbo;

import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.dao.SfSchTaskExecOosMapper;
import mb.erp.dr.soa.service.bill.NewERPCommonService;
import mb.erp.dr.soa.service.dubbo.ActiveMqDubboService;
import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import org.springframework.stereotype.Service;

@Service
public class ActiveMqDubboServiceImpl implements ActiveMqDubboService {

	@Resource
	public SfSchTaskExecOosMapper schTaskExecOosMapper;
	
	@Resource
	private NewERPCommonService newERPCommonService;
	
	public Integer saveSfSchTaskExecOos(SfSchTaskExecOosVo vo) {
		Integer result = null;
		Long tempId = newERPCommonService.getPrimaryIdNew("SF_SCH_TASK_EXEC", 1);
		vo.setId(tempId);
		vo.setSfSchTaskExecId(tempId);
		result = schTaskExecOosMapper.save(vo);
		List<SfSchTaskExecOosDtlVo> list = vo.getLstSfSchTaskExecOosDtls();
		if (list != null && list.size() > 0) {
			for (SfSchTaskExecOosDtlVo dtlVo : list) {
				dtlVo.setId(newERPCommonService.getPrimaryIdNew("SF_SCH_TASK_EXEC_OOS_DTL", 1));
				schTaskExecOosMapper.saveDtl(dtlVo);
			}
		}
		return result;
	}

}
