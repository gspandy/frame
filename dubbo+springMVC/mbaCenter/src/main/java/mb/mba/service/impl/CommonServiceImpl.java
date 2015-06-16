package mb.mba.service.impl;

import javax.annotation.Resource;

import mb.mba.dao.CommonMapper;
import mb.mba.service.ICommonService;
import mb.mba.vo.Product;

public class CommonServiceImpl implements ICommonService {

	@Resource
	private CommonMapper commonMapper;
	
	public Product getProductById(Integer id) {
		return commonMapper.getProductById(id);
	}

}
