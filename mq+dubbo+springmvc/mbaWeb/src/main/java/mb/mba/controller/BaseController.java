package mb.mba.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mb.mba.core.constant.MbaConstant;
import mb.mba.core.entity.auth.UserEntity;
import mb.mba.core.exception.MbaException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
			out.close();
		}
	}
	
	
	/**
	 * 统一异常处理
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler
    public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {  
	    //获取url
	    String requestType  = request.getHeader("X-Requested-With");
	    boolean isAjax = requestType.startsWith("XMLHttpRequest");
	    //如果为ajax
        if(isAjax){
            outPrintJson(response,e);
        }else{
         // 添加自己的异常处理逻辑，如日志记录
            request.setAttribute("exceptionMessage", e.getMessage()); 
            logger.error(e.getMessage());
            // 根据不同的异常类型进行不同处理
//            if(e instanceof SQLException) 
//                return "sqlerror";   
//            else
        }
        return "error";  
    } 
	
	/**
	 * @功能描述：获取当前登陆用户信息
	 * @author  毛建强
	 * @2015年6月11日
	 * @param
	 * @version
	 * @throws MbaException
	 */
	public UserEntity getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return (UserEntity)request.getSession().getAttribute(MbaConstant.USERLOGIN_SESSION);
	}
}
