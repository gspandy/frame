package mb.mba.entity;

import java.io.Serializable;

public class DataDictionary implements Serializable{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private String codeClass;
    
    private String codeName;
    
    private String code;
    
    private String name;

    public String getCodeClass() {
        return codeClass;
    }

    public void setCodeClass(String codeClass) {
        this.codeClass = codeClass;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
