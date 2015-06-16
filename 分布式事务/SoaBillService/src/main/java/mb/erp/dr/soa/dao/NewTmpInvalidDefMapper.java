package mb.erp.dr.soa.dao;

import java.util.List;

import mb.erp.dr.soa.vo.NewTmpInvalidDefVo;

public interface NewTmpInvalidDefMapper {
	public void save(List<NewTmpInvalidDefVo> newTmpInvalidDefVo);
	public List<NewTmpInvalidDefVo> selectInvalid();
	
	/**
	 * 向临时表TMP_PRODUCT_NUM插入数据
	 * @param list
	 */
	public void saveTempProductNum(List<String> list);
}
