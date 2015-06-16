package mb.mba.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mb.mba.core.entity.auth.AuthorityMenuEntity;
import mb.mba.core.entity.auth.RoleEntity;
import mb.mba.core.entity.auth.UserEntity;
import mb.mba.util.AuthHelper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




/**
 * 用户登陆页面
 * @author    Administrator
 * @version   
 * @see       
 * @since
 */
@Controller

@RequestMapping("/login")
public class UserLoginController {
    /**
     * 登陆主页面
     * @return 
     */
    @RequestMapping("/index")
    public String loginIndex(){
        return "index";
    }
    
    /**
     * 用户登陆
     * @param u
     * @param map
     * @return
     */
    @RequestMapping(value="/userLogin",method=RequestMethod.POST)
    public ModelAndView userLogin(UserEntity u, ModelMap map,HttpServletRequest request){
        //登陆信息判断
        if(u==null||StringUtils.isBlank(u.getUserName())||StringUtils.isBlank(u.getPassword())){
            map.addAttribute("errorMessage","用户名或密码不能为空！" );
            return new ModelAndView("index");
        }
       /* String userName;
        try {
            userName = new String(u.getUserName().getBytes(), "UTF-8");
            map.addAttribute("userName",userName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } */
        //保存用户登陆后信息
        setAuthInfo(u,request);
        //登陆后，保存session
        return new ModelAndView("redirect:/login/main");
    }
    
    /**
     * 功能描述：用户登出操作
     * 作者：毛建强
     * 2015年5月20日
     */
    @RequestMapping(value="/logout")
    public String loginOut(HttpServletRequest request){
        //获取session
        HttpSession session = request.getSession(true);
        //失效session
        session.invalidate();
        return "redirect:/login/index";
    }
    
    /**
     * 功能描述:登陆后主框架界面
     * 作者：毛建强
     * 2015年5月20日
     */
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    
    /**
     * 设置菜单权限
     * @param 
     * @param session
     */
    private void setAuthInfo(UserEntity u,HttpServletRequest request) {
        
        UserEntity entity = new UserEntity(1,u.getUserName(),u.getUserName());
        
        RoleEntity role = new RoleEntity(1,"","内部员工");
        List<RoleEntity> roleList = new ArrayList<RoleEntity>();
        roleList.add(role);
        entity.setRoles(roleList);
        //菜单权限
        List<AuthorityMenuEntity> authorityMenus=new ArrayList<AuthorityMenuEntity>();
        
        //所有权限
        List<AuthorityMenuEntity> permissionMenus=new ArrayList<AuthorityMenuEntity>();
        
        AuthorityMenuEntity menu1=new AuthorityMenuEntity(1,"控制台","icon-dashboard", "/login/main",true,"1",null);
        
        AuthorityMenuEntity menu2=new AuthorityMenuEntity(2,"出入库","icon-text-width", "#",false,"1",null);
        List<AuthorityMenuEntity> children2=new ArrayList<AuthorityMenuEntity>();
        children2.add(new AuthorityMenuEntity(21, "交易查询", "icon-double-angle-right", "/transaction/index",true,"2",menu2));
        children2.add(new AuthorityMenuEntity(22, "流水查询", "icon-double-angle-right", "/transaction/jqgrid",true,"2",menu2));
        children2.add(new AuthorityMenuEntity(23, "交易查询2", "icon-double-angle-right", "/trades/trades",true,"2",menu2));
        children2.add(new AuthorityMenuEntity(24, "流水查询2", "icon-double-angle-right", "/trades/dtltrades",true,"2",menu2));
        menu2.setChildrens(children2);
       
        AuthorityMenuEntity menu3=new AuthorityMenuEntity(3,"多货主","icon-desktop", "#",false,"1",null);
        List<AuthorityMenuEntity> children3=new ArrayList<AuthorityMenuEntity>();
        children3.add(new AuthorityMenuEntity(31, "库存查询", "icon-double-angle-right", "#",true,"1",menu3));
        menu3.setChildrens(children3); 
        
        AuthorityMenuEntity menu4=new AuthorityMenuEntity(4,"基础资料","icon-tag", "#",false,"1",null);
        List<AuthorityMenuEntity> children4=new ArrayList<AuthorityMenuEntity>();
        children4.add(new AuthorityMenuEntity(41, "税率设置", "icon-double-angle-right", "#",true,"2",menu4));
        children4.add(new AuthorityMenuEntity(43, "出入库方式设置", "icon-double-angle-right", "#",true,"2",menu4));
        //设置菜单权限
        AuthorityMenuEntity menu42=new AuthorityMenuEntity(42, "贸易伙伴关系设置", "icon-double-angle-right", "/partnerShip/index",true,"2",menu4);
        List<AuthorityMenuEntity> children42=new ArrayList<AuthorityMenuEntity>();
        children42.add(new AuthorityMenuEntity(421, "贸易伙伴关系查询", "", "partnerShip/queryByParams",true,"0",menu4));
        //children42.add(new AuthorityMenuEntity(422, "贸易伙伴关系重置", "", "partnerShip/reset",true,"0",menu4));
        menu42.setChildrens(children42);
        
        children4.add(menu42);
        
        menu4.setChildrens(children4);
        
        authorityMenus.add(menu1);
        authorityMenus.add(menu2);
        authorityMenus.add(menu3);
        authorityMenus.add(menu4);
        
        //获取所有的节点权限，包含叶子节点和菜单节点
        setAuthorityList(authorityMenus,permissionMenus);
        //设置值
        entity.setAuthorityMenus(authorityMenus);
        entity.setAuthorityMenusList(permissionMenus);
        //将用户信息放到session中
        AuthHelper.setSessionUserEntity(request, entity);
    }
    
    /**
     * @功能描述：获取权限列表
     * @author  毛建强
     * @2015年6月3日
     * @param
     * @version
     */
    private void setAuthorityList(List<AuthorityMenuEntity> srcList,List<AuthorityMenuEntity> tarList){
        for(AuthorityMenuEntity me: srcList){
            tarList.add(me);
            if(!CollectionUtils.isEmpty(me.getChildrens())){
                setAuthorityList(me.getChildrens(),tarList);
            }
        }
    }
}
