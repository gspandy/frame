package mb.erp.dr.soa.old.dao.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.common.NDirectoryVo;

/**
 * 通用数据库接口
 * 包含接口：获取主键编码
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         CommonMapper
 * @since       全流通改造
 */
public interface CommonMapper {

	/**
	 * 获取主键编号
	 * @param params
	 * @return
	 */
	public String getPrimaryKey(Map<String, String> params);
	
	/**
	 * 查询出库单是否已封账
	 * @param params
	 * @return
	 */
	public Integer isClosedAccountByGdn(Map<String, String> params);
	
	/**
	 * 查询入库单是否已封账
	 * @param params
	 * @return
	 */
	public Integer isClosedAccountByGrn(Map<String, String> params);
	
	/**
	 * 获取主键id
	 * @param params
	 * @return
	 */
	public Long getPrimaryIdOld(Map params);
	
	/**
	 * 查询门店结转日期
	 * @param params
	 * @return
	 */
	public List<String> isSettle(String ownerId);
	
	/**
	 * 代理商是否加盟
	 * @return
	 */
	public Integer isOldFranchisee(String vendeeId);
	
	/**
	 * 代理商为加盟或直营
	 * @return
	 */
	public Integer isOldFranchiseeOrDirect(String vendeeId);
	
	/**
	 * 查询发货货位和收货货位
	 * @param warehId
	 * @return
	 */
	public Map selectLocId(String warehId);
	
	/**
	 * 查询单个字典值
	 * @param map
	 * @return
	 */
	public NDirectoryVo selectODirectory(Map map);
	
	/**
	 * 查询全部字典值
	 * @param map
	 * @return
	 */
	public List<NDirectoryVo> selectAllODirectory();
	
	/**
	 * 查询指定仓库的货位是否存在
	 * @param map
	 * @return
	 */
	public Integer selectByLocIdWarehId(Map<String, String> map);
}
