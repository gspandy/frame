package mb.mba.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/**
 * controller父类
 * 包含方法：跳转页面，输出json流，异常拦截等
 * @version    1.0, 2015-4-15
 * @author 余从玉
 * @since 
 */
public abstract class BaseController {

	private  Logger logger = LoggerFactory.getLogger(BaseController.class);
	/**
	 * 返回页面渲染
	 * @param data
	 * @param viewName
	 * @return
	 */
	public ModelAndView render(Map<String, Object> data, String viewName){
		ModelAndView mView = new ModelAndView();
		mView.setViewName(viewName);
		mView.addAllObjects(data);
		return mView;
	}
	
	
	/**
	 * 输出JSON字符串
	 * 
	 * @param response
	 * @param obj
	 */
	public static void outPrintJson(HttpServletResponse response, Object obj) {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("CacheControl", "no-cache");
		response.addHeader("Content-Type", "application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String jsonStr = JSON.toJSONString(obj);
			out.print(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
		}
	}
	
	/**
	 * 统一异常处理
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler
    public String exception(HttpServletRequest request, Exception e) {  
          
		// 添加自己的异常处理逻辑，如日志记录
        request.setAttribute("exceptionMessage", e.getMessage());  
        logger.error(e.getMessage());
        // 根据不同的异常类型进行不同处理
//        if(e instanceof SQLException) 
//            return "sqlerror";   
//        else
            return "error";  
    } 
	
	
}
