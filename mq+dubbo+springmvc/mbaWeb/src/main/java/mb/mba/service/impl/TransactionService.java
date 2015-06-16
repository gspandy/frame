package mb.mba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mb.mba.bean.TransactionBean;
import mb.mba.dao.TransactionMapper;
import mb.mba.service.ITransactionService;

import org.apache.ibatis.session.RowBounds;

/**
 * 查询出入库列表service
 * @author    maojq
 * @version   
 * @see       
 * @since
 */
public class TransactionService implements ITransactionService{
    
    @Resource
    private TransactionMapper trancasctionMapper;
    /**
     * 
     * @see mb.mba.service.ITransactionService#queryListByParams(org.apache.ibatis.session.RowBounds)
     *
     */
    public List<TransactionBean> queryListByParams(TransactionBean dto,RowBounds rb) {
       return  this.trancasctionMapper.queryListByParams(new TransactionBean(),rb);
    }
    
    
    /**
     * 功能描述：查询总条数
     * 作者：毛建强
     * 2015年5月25日
     */
    public int queryListByParamsCount(TransactionBean dto) {
        return trancasctionMapper.queryListByParamsCount(dto);
    }
    
}
