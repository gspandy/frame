package mb.mba.util;

import javax.servlet.http.HttpServletRequest;

import mb.mba.core.constant.MbaConstant;
import mb.mba.core.entity.auth.AuthorityMenuEntity;
import mb.mba.core.entity.auth.UserEntity;

/**
 * @功能： 将用户权限放到session中
 * @author    maojq
 * @data 2015年5月20日  
 * @see       
 * @since
 */
public class AuthHelper {
	
    /**
	 * 功能描述：将用户权限放到session中
	 * 作者：毛建强
	 * 2015年5月20日
	 */
	public static void setSessionUserEntity(HttpServletRequest request, UserEntity user){
		request.getSession().setAttribute(MbaConstant.USERLOGIN_SESSION, user);
	}
	
	/**
     * 功能描述：获取用户权限
     * 作者：毛建强
     * 2015年5月20日
     */
	public static UserEntity getSessionUserEntity(HttpServletRequest request){
		return (UserEntity)request.getSession().getAttribute(MbaConstant.USERLOGIN_SESSION);
	}
	
	/**
	 * 功能描述：讲用户当前操作菜单放到request中
	 * 作者：毛建强
	 * 2015年5月20日
	 */
	public static void setRequestPermissionMenu(HttpServletRequest request, AuthorityMenuEntity menu){
		request.setAttribute(MbaConstant.PERMISSION_MENUS_REQUEST, menu);
	}
	
	/**
     * 功能描述：获取用户当前操作的菜单
     * 作者：毛建强
     * 2015年5月20日
     */
	public static AuthorityMenuEntity getRequestPermissionMenu(HttpServletRequest request){
		return (AuthorityMenuEntity)request.getAttribute(MbaConstant.PERMISSION_MENUS_REQUEST);
	}
	
}
