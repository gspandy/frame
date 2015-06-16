/*
 * 文件名：PermissionInterceptor.java
 * 版权：Copyright 2014 MetersBonwe. All Rights Reserved.
 * 描述：TODO
 * 修改人：Administrator
 * 修改时间：上午11:11:39
 * 修改内容：
 */

package mb.mba.intercepors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mb.mba.core.constant.MbaConstant;
import mb.mba.core.entity.auth.AuthorityMenuEntity;
import mb.mba.core.entity.auth.UserEntity;
import mb.mba.util.AuthHelper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/**
 * 验证用户登陆权限
 * @author    Administrator
 * @version   
 * @see       
 * @since     
 */

public class PermissionInterceptor extends HandlerInterceptorAdapter{
    
    /**
     * 登陆权限验证
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     *
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取当前访问URL
        String path = request.getServletPath();
        //获取用户信息
        UserEntity user = AuthHelper.getSessionUserEntity(request); 
        //判断是否为ajax请求
        boolean isAjaxRequest = isAjaxRequest(request); 
        //如果用户注册或者找回密码，也需要放过；
        
        //说明在进行登陆操作或者登出操作
        if(path.indexOf(MbaConstant.LOGIN_INDEX_URL)>=0
                ||path.indexOf(MbaConstant.LOGIN_OUT_URL)>=0){
            return true;
        //说明已经登陆过了
        }else if(path.indexOf(MbaConstant.LOGINING_URL)>=0){
            if(user!=null){
                response.sendRedirect(request.getContextPath()+MbaConstant.MAIN_URL);
                return false;
            }
            return true;
        }
        
        //未登录拦截
        if (user == null && path.indexOf(MbaConstant.LOGINING_URL)==-1) {  
           //ajax处理
           if (isAjaxRequest) {  
                request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
                response.setContentType("text/html;charset=utf-8");
                response.setHeader("Cache-Control", "no-cache");  
                response.setHeader("errorStatus", HttpStatus.UNAUTHORIZED.value()+"");
                /*response.sendError(HttpStatus.UNAUTHORIZED.value(), 
                        "您已经太长时间没有操作,请刷新页面");  */
                return false;
            } 
           //普通请求
            response.sendRedirect(request.getContextPath()+MbaConstant.LOGINING_URL);  
            return false;   
        } 
         //判断用户权限
        if(user!=null && !path.equals("/")){
            boolean  hasPermission = false;
            for(AuthorityMenuEntity menu:user.getAuthorityMenusList()){
                String url = menu.getUrl();
                Pattern pattern = Pattern.compile(url,Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(path);
                if(matcher.find()){
                    hasPermission=true;
                    AuthHelper.setRequestPermissionMenu(request, menu);
                    break;
                }
            }
           //有权限
           if(hasPermission){
               return true; 
           //没权限择返回
           }else{
        	   ///dataDictionary/onLoadGetDictionary
        	   if(user!=null && path.equalsIgnoreCase("/dataDictionary/onLoadGetDictionary")){
        		   return true;
        	   }
               request.setCharacterEncoding("utf-8");  //这里不设置编码会有乱码
               response.setContentType("text/html;charset=utf-8");
               response.setHeader("Cache-Control", "no-cache"); 
               response.setHeader("errorStatus", HttpStatus.FORBIDDEN.value()+"");
               /*response.sendError(HttpStatus.FORBIDDEN.value(), 
                       "对不起，您没有操作权限！");  */
               return false;
           }
        }
        return true;
    }
    
   /** 
     * 判断是否为Ajax请求 
     * 
     * @param request HttpServletRequest 
     * @return 是true, 否false 
     */ 
    public static boolean isAjaxRequest(HttpServletRequest request) { 
        //请求类型
        String requestType  = request.getHeader("X-Requested-With");
        if(StringUtils.isNotEmpty(requestType)
                && requestType.startsWith("XMLHttpRequest") ){
            return true;
        }else{
            return false;
        }
    }  
}
