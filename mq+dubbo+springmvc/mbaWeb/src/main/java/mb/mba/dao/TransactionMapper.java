package mb.mba.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import mb.mba.bean.TransactionBean;

public interface TransactionMapper {
   
    /**
     * 功能描述：根据条件查询数据
     * 作者：毛建强
     * 2015年5月22日
     */
    public List<TransactionBean>  queryListByParams(TransactionBean dto,RowBounds rb);
    
    /**
     * 功能描述：根据条件查询总条数
     * 作者：毛建强
     * 2015年5月25日
     */
    public int queryListByParamsCount(TransactionBean dto);
}
