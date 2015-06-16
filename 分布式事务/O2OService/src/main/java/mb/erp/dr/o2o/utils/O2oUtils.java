package mb.erp.dr.o2o.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * O2O工具类
 * 
 * @author     余从玉
 * @version    1.0, 2014-12-23
 * @see         O2oUtils
 * @since       全流通改造
 */
public class O2oUtils {

	/**
	 * 转化为字符串
	 * @param str
	 * @return
	 */
	public static String ToString(Object obj){
		if (obj == null) {
			return null;
		}
		return obj.toString();
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
	
	/**
	 * 将字节码转换成javaBean
	 * @param bytes
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(byte[] bytes,Class<T> clazz){
		Type type = clazz.getGenericSuperclass();
		T t = JSON.parseObject(bytes, type, Feature.IgnoreNotMatch);
		return t;
	}
	
	/**
	 * 将javaBean转化为字节码
	 * @param o
	 * @return
	 */
	public static byte[] toJSONBytes(Object o){
		return JSON.toJSONBytes(o,SerializerFeature.SortField,SerializerFeature.WriteClassName);
	}
	
	/**
	 * 重新拼接json
	 * @param json
	 * @return
	 */
	public static SfSchTaskExecOosVo parseSfSchTaskExecOosVo(String json){
		Map<String, Object> maps = new HashMap<String, Object>();
		Map<String, Object> map = JSON.parseObject(json);
		for (String key : map.keySet()) {
			Object value = map.get(key);
			if (key.equals("lstSfSchTaskExecOosDtls")) {
				List<Object> list = JSON.parseArray(value.toString());
				List<Map<String, Object>> listr = new ArrayList<Map<String, Object>>();
				for (Object object : list) {
					Map<String, Object> tempmap = JSON.parseObject(object.toString());
					Map<String, Object> tempmaps = new HashMap<String, Object>();
					for (String tempkey : tempmap.keySet()) {
						Object tempvalue = tempmap.get(tempkey);
						tempkey = convert(tempkey);
						tempmaps.put(tempkey, tempvalue);
					}
					listr.add(tempmaps);
				}
				maps.put("lstSfSchTaskExecOosDtls", JSON.toJSON(listr));
				continue;
			}
			key = convert(key);
			maps.put(key, value);
		}
		String pjson =  JSON.toJSONString(maps);
		return JSON.parseObject(pjson, SfSchTaskExecOosVo.class);
	}
	
	/**
	 * 字段值转换成驼峰，首字母也需要大写
	 * @param str
	 * @return
	 */
	private static String convert(String str){
		str = str.toLowerCase();
		StringBuffer result = new StringBuffer();
		String[] temps = str.split("_");
		result.append(temps[0]);
		for (int i = 1; i < temps.length; i++) {
			String temp = temps[i];
			String startChar = temp.substring(0,1);
			temp = temp.replaceFirst(startChar, startChar.toUpperCase());
			result.append(temp);
		}
		return result.toString();
	}
	
}
