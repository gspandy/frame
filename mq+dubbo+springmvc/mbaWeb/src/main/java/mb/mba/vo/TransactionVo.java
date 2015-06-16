package mb.mba.vo;

import java.io.Serializable;
import java.util.Date;

public class TransactionVo  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private String note;
    
    private String stock;
    
    private String ship;
    
    private Date sdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }
    
}
