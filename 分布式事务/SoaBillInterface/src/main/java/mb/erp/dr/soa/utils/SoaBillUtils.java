package mb.erp.dr.soa.utils;

import java.text.DecimalFormat;
import java.util.List;

import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.IdtDtlVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.DrTbnDtlVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.NewBaseBizVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfIdtDtlVo;
import mb.erp.dr.soa.vo.SfIdtVo;



/**
 * soa 单据工具类
 * 
 * @author     余从玉
 * @version    1.0, 2014-10-31
 * @see         SoaBillUtils
 * @since       全流通改造
 */
public class SoaBillUtils {

	/**
	 * 转化为字符串
	 * @param str
	 * @return
	 */
	public static String toObject(Object obj){
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}
	
	/**
	 * 转化为枚举、数字等对象
	 * @param str
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T>  T toObject(Object obj,Class<T> cls){
		if (obj == null) {
			return null;
		}
		if (cls.isEnum()) {
			Class<? extends Enum> enumType = (Class<Enum>) cls;
			obj = Enum.valueOf(enumType, toObject(obj));
		}
		return (T) obj;
	}
	
	public static String getParam(BaseBizVo vo,String key){
    	return toObject(vo.getExtraParams().get(key));
    }
	
	/**
	 * 验证字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		if (str == null
				|| str.trim().length()==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	/**
	 * 验证list是否为空
	 * @param <T>
	 * @param str
	 * @return
	 */
	public static <T> boolean isListBlank(List<T> list){
		if (list == null
				|| list.size() <=0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 字符串类型转化为Long类型
	 * @param str
	 * @return
	 */
	public static Long parseInteger(String str){
		if (str == null
				|| str.trim().length()==0) {
			return null;
		}
		return Long.parseLong(str);
	}
	
	public static Double formatPricePrecisionTwo(Double price){
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(price));
	}
	
	public static Double formatPricePrecisionFour(Double price){
		DecimalFormat df = new DecimalFormat("#.0000");
		return Double.valueOf(df.format(price));
	}
	
	/**
	 * 
	 * 老ERP重新获取总金额
	 * @param vo
	 * @see
	 * @since
	 */
	public static void resetOrderVal(BaseBizVo vo) {
		if (null == vo) {
			return ;
		}
		if (vo instanceof GdnVo) {
			GdnVo gdnVo = (GdnVo) vo;
			List<GdnDtlVo> gdnDtlVos = gdnVo.getGdnDtlVos();
			Double totalVal = 0d;
			for (GdnDtlVo dtlVo : gdnDtlVos) {
				double val = dtlVo.getQuantity()*dtlVo.getUnitPrice()*(dtlVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("gdn重算后的价格"+formatPricePrecisionTwo(totalVal));
			((GdnVo) vo).setTtlVal(formatPricePrecisionTwo(totalVal));
		}
		
		if (vo instanceof IdtVo) {
			List<IdtDtlVo> idtDtlVos = ((IdtVo) vo).getIdtDtlVos();
			Double totalVal = 0d;
			for (IdtDtlVo idtVo : idtDtlVos) {
				double val = idtVo.getAdmQty()*idtVo.getUnitPrice()*(idtVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("idt重算后的价格"+formatPricePrecisionTwo(totalVal));
			((IdtVo) vo).setOrderVal(formatPricePrecisionTwo(totalVal));
		}
		
		if (vo instanceof TbnVo) {
			List<TbnDtlVo> tbnDtlVos = ((TbnVo) vo).getTbnDtlVos();
			Double totalVal = 0d;
			for (TbnDtlVo tbnDtlVo : tbnDtlVos) {
				double val = tbnDtlVo.getExpdQty()*tbnDtlVo.getUnitPrice()*(tbnDtlVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("tbn重算后的价格"+formatPricePrecisionTwo(totalVal));
			((TbnVo) vo).setCrVal(formatPricePrecisionTwo(totalVal));
		}
		
	}
	
	/**
	 * 
	 * 新ERP重新获取总金额
	 * @param vo
	 * @see
	 * @since
	 */
	public static void resetSfOrderVal(NewBaseBizVo vo) {
		if (vo == null) {
			return;
		}
		
		if (vo instanceof SfGdnVo) {
			SfGdnVo gdnVo = (SfGdnVo) vo;
			List<SfGdnDtlVo> gdnDtlVos = gdnVo.getSfGdnDtlVos();
			Double totalVal = 0d;
			for (SfGdnDtlVo dtlVo : gdnDtlVos) {
				double val = dtlVo.getQuantity()*dtlVo.getUnitPrice()*(dtlVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("SfGdn重算后的价格"+formatPricePrecisionTwo(totalVal));
			((SfGdnVo) vo).setTtlVal(formatPricePrecisionTwo(totalVal));
		}
		
		if (vo instanceof SfIdtVo) {
			List<SfIdtDtlVo> idtDtlVos = ((SfIdtVo) vo).getSfIdtDtlVos();
			Double totalVal = 0d;
			for (SfIdtDtlVo idtVo : idtDtlVos) {
				double val = idtVo.getOrderQty()*idtVo.getUnitPrice()*(idtVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("SfIdt重算后的价格"+formatPricePrecisionTwo(totalVal));
			((SfIdtVo) vo).setOrderVal(formatPricePrecisionTwo(totalVal));
		}
		
		if (vo instanceof DrTbnVo) {
			List<DrTbnDtlVo> tbnDtlVos = ((DrTbnVo) vo).getDtlVos();
			Double totalVal = 0d;
			for (DrTbnDtlVo tbnDtlVo : tbnDtlVos) {
				double val = tbnDtlVo.getExpdQty()*tbnDtlVo.getUnitPrice()*(tbnDtlVo.getDiscRate()/100);
				totalVal += val;
			}
//			System.out.println("DrTbn重算后的价格"+formatPricePrecisionTwo(totalVal));
			((DrTbnVo) vo).setTtlVal(formatPricePrecisionTwo(totalVal));
		}
	}
}
