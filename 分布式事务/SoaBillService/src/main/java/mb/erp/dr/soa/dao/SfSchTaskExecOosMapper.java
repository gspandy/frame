package mb.erp.dr.soa.dao;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

/**
 * MQ数据库接口
 * 包含接口：
 * @author     郭明帅
 * @version    1.0, 2014-11-10
 * @see         SfSchTaskExecOosMapper
 * @since       全流通改造
 */
public interface SfSchTaskExecOosMapper {

	public Integer save(SfSchTaskExecOosVo vo);
	
	public Integer saveDtl(SfSchTaskExecOosDtlVo dtlVo);
	
	public SfSchTaskExecOosVo selectVo(Map map);
	
	public List<SfSchTaskExecOosDtlVo> selectDtlVo(Map map);
	
}
