package mb.erp.dr.soa.old.dao;

import java.util.List;

import mb.erp.dr.soa.old.vo.TmpInvalidDefVo;

public interface TmpInvalidDefMapper {
	public void save(List<TmpInvalidDefVo> tmpInvalidDefVo);
	public List<TmpInvalidDefVo> selectInvalid();
	
	/**
	 * 向临时表TMP_PRODUCT_NUM插入数据
	 * @param list
	 */
	public void saveTempProductNum(List<String> list);
	
	public List<String> selectTempProductNum(String prodNum);
}
