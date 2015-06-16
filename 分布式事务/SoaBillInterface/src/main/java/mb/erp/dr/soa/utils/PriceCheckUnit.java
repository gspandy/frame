package mb.erp.dr.soa.utils;

/**
 * soa 价格验证工具类
 * 
 * @author     郭明帅
 * @version    1.0, 2014-10-31
 * @see         PriceCheckUnit
 * @since       全流通改造
 */
public class PriceCheckUnit {
    public static String getCurreny(String curreny)
    {
        if (curreny.toUpperCase().trim() == "DOLLAR")
        {
            return "USD";
        }
        return curreny;
    }
}
