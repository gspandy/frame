package mb.erp.dr.soa.dao;

import mb.erp.dr.soa.vo.SfWarehouseVo;

/**
 * 新ERP仓库接口
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2014-12-17
 * @see         SfWarehouseVo
 * @since       全流通改造
 */
public interface SfWarehouseMapper {
	
	/**
	 * 根据仓库id查询仓库实体
	 * @param id
	 * @return
	 */
    public SfWarehouseVo selectById(Long id);
    
    /**
     * 查询货位是否存在
     * @param id
     * @return Long 1：存在 
     */
    public Long selectLocById(Long id);

}