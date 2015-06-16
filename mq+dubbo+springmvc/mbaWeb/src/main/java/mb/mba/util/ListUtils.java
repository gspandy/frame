package mb.mba.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述：集合帮助类
 * @author  毛建强
 * @2015年6月11日
 * @version
 */
public class ListUtils {
    /**
     * @功能描述：List<String> to List<Integer>
     * @author  毛建强
     * @2015年6月11日
     * @param
     * @version
     */
    public static List<Integer> StringToIntegerLst(List<String> inList){
        List<Integer> iList =new ArrayList<Integer>(inList.size());
           for(int i=0,j=inList.size();i<j;i++){
             iList.add(Integer.parseInt(inList.get(i)));   
           }   
        return iList;
    }
}
