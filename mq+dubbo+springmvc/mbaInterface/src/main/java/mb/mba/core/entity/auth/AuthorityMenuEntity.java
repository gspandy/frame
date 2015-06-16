package mb.mba.core.entity.auth;

import java.util.List;

import mb.mba.core.entity.BaseEntity;

/**
 * @类描述：菜单和按钮实体
 * @author  毛建强
 * @2015年5月29日
 * @version
 */
public class AuthorityMenuEntity extends BaseEntity{
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -20132844464685710L;
    
    /**
     * id
     */
    private Integer id;
    /**
     * name
     */
    private String name;
    
    /**
     * 菜单或按钮对应的图片
     */
    private String itemIcon;
    
    /**
     * 菜单对应的url
     */
    private String url;
    
    /**
     * 是否为叶子节点
     */
    private Boolean isLeaf;
    
    /**
     *  0--按钮元素   1--页面级别    2--菜单级别
     */
    private String level;

    /**
     * 父节点
     */
    private AuthorityMenuEntity parent;
    
    /**
     * 子节点
     */
    private List<AuthorityMenuEntity> childrens;
    
    /**
     * 功能描述：构造方法
     * 作者：毛建强
     * 2015年5月20日
     */
    public AuthorityMenuEntity(){}
    
    /**
     * 构造方法
     * @param id
     * @param name
     * @param itemIcon
     * @param url
     * @param isLeaf
     * @param parentId
     */
    public AuthorityMenuEntity(Integer id, String name, String itemIcon, String url,
            Boolean isLeaf,String level, AuthorityMenuEntity parent) {
        this.id = id;
        this.name = name;
        this.itemIcon = itemIcon;
        this.url = url;
        this.isLeaf = isLeaf;
        this.parent = parent;
        this.level = level;
    }

    /**
     * 构造方法
     * @param id
     * @param name
     * @param itemIcon
     * @param url
     * @param isLeaf
     * @param childrens
     * @param parentId
     */
    public AuthorityMenuEntity(Integer id, String name, String itemIcon, String url,
            Boolean isLeaf,String level, List<AuthorityMenuEntity> childrens, AuthorityMenuEntity parent) {
        this.id = id;
        this.name = name;
        this.itemIcon = itemIcon;
        this.url = url;
        this.isLeaf = isLeaf;
        this.level = level;
        this.childrens = childrens;
        this.parent = parent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setChildrens(List<AuthorityMenuEntity> childrens) {
        this.childrens = childrens;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getItemIcon() {
        return this.itemIcon;
    }

    public String getUrl() {
        return this.url;
    }

    public List<AuthorityMenuEntity> getChildrens() {
        return this.childrens;
    }

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public AuthorityMenuEntity getParent() {
        return parent;
    }

    public void setParent(AuthorityMenuEntity parent) {
        this.parent = parent;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
