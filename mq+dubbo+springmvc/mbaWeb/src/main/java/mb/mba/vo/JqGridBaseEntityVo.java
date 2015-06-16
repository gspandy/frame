package mb.mba.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述：使用jqGrid时分页接受前台传入参数
 * @author  毛建强
 * @2015年5月29日
 * @version
 */
public class JqGridBaseEntityVo<T> implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 和jqGrid组件相关的参数属性  
    private List<T> gridModel = new ArrayList<T>();  
    private Integer rows = 0; //分页条数
    private Integer page = 0; //当前页
    private Integer total = 0; //总页数
    private Integer record = 0;//总记录数
    private String sord;  //表示采用的排序方式的参数名称
    private String sidx;  //表示用于排序的列名的参数名称
    private String search;//表示是否是搜索请求的参数名称
    public List<T> getGridModel() {
        return gridModel;
    }
    public void setGridModel(List<T> gridModel) {
        this.gridModel = gridModel;
    }
    public Integer getRows() {
        return rows;
    }
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getRecord() {
        return record;
    }
    public void setRecord(Integer record) {
        this.record = record;
    }
    public String getSord() {
        return sord;
    }
    public void setSord(String sord) {
        this.sord = sord;
    }
    public String getSidx() {
        return sidx;
    }
    public void setSidx(String sidx) {
        this.sidx = sidx;
    }
    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }  
    
    
}
