package mb.erp.dr.soa.old.dao;

import java.util.Map;


public interface SysParameterMapper {
	/**
	 * 获取系统参数税率SYS_PARAMETER
	 * @param map
	 * @return
	 */
	public String selectSysParamValue(Map map);
	
	/**
	 * 获取系统参数-门店结账允许延迟天数
	 * @return
	 */
	public String getSettleDelay();
	
}