package mb.mba.bean;

import java.io.Serializable;
import java.util.List;

import mb.mba.entity.DataDictionary;

public class DataDictionaryBean implements Serializable{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 渲染id
     */
    private String rendToId;
    
    /**
     * 数据字典词组编码
     */
    private String codeType;
    
    /**
     * 选择数据字典中某几个
     */
    private List<String> includeType;
    
    /**
     * 是否包含全部
     */
    private Boolean all;
    
    /**
     * 指定默认值
     */
    private String defaultValue;
    
    /**
     * 数据字典明细列表
     */
    List<DataDictionary> list;

    public String getRendToId() {
        return rendToId;
    }

    public void setRendToId(String rendToId) {
        this.rendToId = rendToId;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public List<DataDictionary> getList() {
        return list;
    }

    public void setList(List<DataDictionary> list) {
        this.list = list;
    }

    public List<String> getIncludeType() {
        return includeType;
    }

    public void setIncludeType(List<String> includeType) {
        this.includeType = includeType;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
}
