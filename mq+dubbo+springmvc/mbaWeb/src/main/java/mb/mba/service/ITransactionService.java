package mb.mba.service;

import java.util.List;

import mb.mba.bean.TransactionBean;

import org.apache.ibatis.session.RowBounds;

/**
 * 出入库交易查询service
 * @author    maojq
 * @version   
 * @see       
 * @since
 */
public interface ITransactionService {
    
    /**
     * 功能描述：查询出库入交易
     * 作者：毛建强
     * 2015年5月22日
     */
    public List<TransactionBean>  queryListByParams(TransactionBean dto,RowBounds rb);
    
    /**
     * 功能描述：
     * 作者：毛建强
     * 2015年5月22日
     */
    public int  queryListByParamsCount(TransactionBean dto);
    
}
