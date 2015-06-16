package mb.mba.core.constant;

/**
 * @类描述：数据字典实体类
 * @author  毛建强
 * @2015年5月29日
 * @version
 */
public class MbaDictionaryConstant {

    /**
     * 是否生效
     */
    public static final String MBA__IS_VALID__YES = "Y"; // 红单

    public static final String MBA__IS_VALID__NO = "N"; // 非红单

    /**
     * 单据类型
     */
    public static final String MBA__STORAGE_TYPE__IN = "IN";//入库单
    
    public static final String MBA__STORAGE_TYPE__OUT = "OUT";//出库单

    /**
     * 成本核算方式
     */
    public static final int MBA__CAL_TYPE__0 = 0;//月末加权
    
    public static final int MBA__CAL_TYPE__1 = 1;//末次进价
    
    public static final int MBA__CAL_TYPE__2 = 2;//移动加权
    
    /**
     * 会计期间状态
     */
    public static final String MBA__COST_PERIOD_STATUS__OPEN = "1";//会计期间打开
    
    public static final String MBA__COST_PERIOD_STATUS__CLOSE = "0";//会计期间关闭

    /**
     * 成本结转状态
     */
    public static final String MBA__COST_CHECKOUT_STATUS__YES = "1";//成本已结转
   
    public static final String MBA__COST_CHECKOUT_STATUS__NO = "0";//成本未结转
}
