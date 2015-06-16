package mb.mba.core.service;

import java.util.List;

import mb.mba.core.bean.Message;
import mb.mba.core.entity.DataDirectoryEntity;
import mb.mba.core.exception.MbaException;

public interface IDictionaryService {

	/**
	 * 添加数据字典
	 * @param dictionaryDto
	 * @return
	 */
	public Message addDictionary(DataDirectoryEntity dictionaryDto) throws MbaException;
	
	/**
	 * 更新数据字典
	 * @param dictionaryDto
	 * @return
	 */
	public Message updateDictionary(DataDirectoryEntity dictionaryDto) throws MbaException;
	
	
	/**
	 * 删除数据字典
	 * @param dictionaryDto
	 * @return
	 */
	public Message deleteDictionary(DataDirectoryEntity dictionaryDto) throws MbaException;
	
	/**
	 * 查询所有字典信息
	 * @return
	 */
	public List<DataDirectoryEntity> queryAllDictionary() throws MbaException;
	
	/**
	 * 查询字典信息
	 * @return
	 */
	public List<DataDirectoryEntity> queryDictionary(String codeClass) throws MbaException;
	
	
}
