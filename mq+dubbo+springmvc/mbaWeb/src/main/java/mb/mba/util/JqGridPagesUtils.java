package mb.mba.util;

import java.util.List;

import mb.mba.vo.JqGridBaseEntityVo;

import org.springframework.util.CollectionUtils;

/**
 * 分页需要的帮助类
 * @author    maojq
 * @version   
 * @see       
 * @since
 */
public class JqGridPagesUtils {
    
    /**
     * 功能描述：分页参数设置
     * 作者：毛建强
     * 2015年5月25日
     * @param <T>
     * @param <T>
     * @param entity 分页实体
     * @throws Exception 
     * @totalCount  分页总条数
     */
    public static <T> void setPagingParams(JqGridBaseEntityVo<T> entity,List<T> gridModel,int totalCount){
        //如果没传入分页条数，查询结果为空，总条数为0等都直接返回
        if(entity.getRows()==0 || CollectionUtils.isEmpty(gridModel)
                ||totalCount==0|| entity.getPage()==0){
            return ;
        }
        //计算总页数
        int totalPage = (int)Math.ceil((double)totalCount/entity.getRows());

        entity.setGridModel(gridModel); //设置返回结果
        entity.setRecord(totalCount);//设置总条数
        entity.setTotal(totalPage);//设置总条数
    }
}
