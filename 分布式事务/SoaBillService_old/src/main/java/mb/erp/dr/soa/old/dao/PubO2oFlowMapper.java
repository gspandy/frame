package mb.erp.dr.soa.old.dao;

import java.util.Map;

import mb.erp.dr.soa.old.vo.PubO2oFlowVo;

/**
 * 队列信息处理接口 包含接口：保存，更新，查询队列处理信息
 * 
 * @author 余从玉
 * @version 1.0, 2014-11-14
 * @see PubO2oFlowMapper
 * @since 全流通改造
 */
public interface PubO2oFlowMapper {

	public Integer save(PubO2oFlowVo pof);

	/**
	 * 获取流水号和批次号 - 结果信息表写入接口
	 * @return
	 */
	public PubO2oFlowVo getO2oSeqIdAndBatchNo();
	
	/**
	 * 获取流水号 - 结果信息表写入接口
	 * @return
	 */
	public Long getO2oSeqId();
	
	/**
	 * 获取同批次的批次号 - 结果信息表写入接口
	 * @param b2cIdtNo
	 * @return
	 */
	public PubO2oFlowVo getO2oInfo(Map<String, String> params);
	
	/**
	 *  队列出错时，更新预处理单据流 
	 * @param pof 必传：dataNo(b2cCode) , remark
	 * @return
	 */
	public Integer updatePrepare(PubO2oFlowVo pof);
	
	/**
	 * 队列重处理成功后，更新预处理单据流
	 * @param pof
	 * @return
	 */
	public Integer updateCorrect(PubO2oFlowVo pof);
	
	public void deleteSql(PubO2oFlowVo vo);
	
}