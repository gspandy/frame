package mb.mba.tag;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import mb.mba.core.entity.auth.AuthorityMenuEntity;
import mb.mba.core.entity.auth.UserEntity;
import mb.mba.util.AuthHelper;

/**
 * @类描述：按钮权限Tag
 * @author  毛建强
 * @2015年6月3日
 * @version
 */
public class FunctionAuthorityTag  extends TagSupport{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 3405156130710706963L;
    
    /**
     * 获取权限列表
     */
    private List<AuthorityMenuEntity> authorityMenusList;
    
    /**
     * 按钮的Url
     */
    private String url;
    
    /**
     * @功能描述：获取
     * @author  毛建强
     * @2015年6月3日
     * @param
     * @version
     */
    protected void init(){
       /* WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();*/
        UserEntity user = AuthHelper.getSessionUserEntity((HttpServletRequest) pageContext.getRequest()); 
        authorityMenusList = user.getAuthorityMenusList();
    }
    
    /**
     * @功能描述：判断用户有没有权限
     * @author  毛建强
     * @2015年6月3日
     * @param
     * @version
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            //获取权限列表
            init();
           boolean permission = false;
           //进行权限教研
           for(AuthorityMenuEntity menu:authorityMenusList){
               String menuUrl = menu.getUrl();
               Pattern pattern = Pattern.compile(menuUrl,Pattern.CASE_INSENSITIVE);
               Matcher matcher = pattern.matcher(url);
               if(matcher.find()){
                   permission=true;
               }
           }
           pageContext.getRequest().setAttribute("permission", permission);
           return EVAL_BODY_INCLUDE;
        } catch (Exception e) {
            pageContext.getRequest().setAttribute("permission", false);
            return SKIP_BODY;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
