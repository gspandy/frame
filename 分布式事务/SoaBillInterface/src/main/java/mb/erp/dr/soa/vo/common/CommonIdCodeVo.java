package mb.erp.dr.soa.vo.common;

import java.io.Serializable;

/**
 * 公用ID转CODE
 * 
 * @author     郭明帅
 * @version    1.0, 2014-12-08
 * @see         CommonIdCodeVo
 * @since       全流通改造
 */
public class CommonIdCodeVo implements Serializable{

	private static final long serialVersionUID = -631506872432202415L;
	
	private Long prodId; //商品ID
	
	private Long bfOrgVendeeId; //购货方ID
	
	private Long bfOrgVenderId; //供货方ID
	
    private Long bfOrgWarehId; //发货仓库ID
    
    private Long bfOrgRcvWarehId; //接收仓库ID
    
    private String brandId; //品牌ID
    
    private Long bfOrgShopId;  //门店ID
    
    private Long lowShopId; //下级门店ID
    
    private Long rcptLocId; //收货货位ID
    
    private Long factSenWarehId; //实际发货仓库id
    
    private Long factRcvWarehId; //实际接收仓库id
    
    private Long oprId; //操作员ID
	
	private String prodCode; //商品编码
	
	private String bfOrgVendeeCode; //购货方
	
	private String bfOrgVenderCode; //供货方
    
    private String bfOrgWarehCode; //发货仓库编码
    
    private String bfOrgRcvWarehCode; //接收仓库编码
    
    private String brandCode; //品牌编码
    
    private String bfOrgShopCode;  //门店编码
    
    private String lowShopCode;  //下级门店编码
    
    private String rcptLocCode; //收货货位编码
    
    private String factSenWarehCode; //实际发货仓库code
    
    private String factRcvWarehCode; //实际接收仓库code
    
    private String oprCode; //操作员Code

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getBfOrgVendeeCode() {
		return bfOrgVendeeCode;
	}

	public void setBfOrgVendeeCode(String bfOrgVendeeCode) {
		this.bfOrgVendeeCode = bfOrgVendeeCode;
	}

	public String getBfOrgVenderCode() {
		return bfOrgVenderCode;
	}

	public void setBfOrgVenderCode(String bfOrgVenderCode) {
		this.bfOrgVenderCode = bfOrgVenderCode;
	}

	public String getBfOrgWarehCode() {
		return bfOrgWarehCode;
	}

	public void setBfOrgWarehCode(String bfOrgWarehCode) {
		this.bfOrgWarehCode = bfOrgWarehCode;
	}

	public String getBfOrgRcvWarehCode() {
		return bfOrgRcvWarehCode;
	}

	public void setBfOrgRcvWarehCode(String bfOrgRcvWarehCode) {
		this.bfOrgRcvWarehCode = bfOrgRcvWarehCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBfOrgShopCode() {
		return bfOrgShopCode;
	}

	public void setBfOrgShopCode(String bfOrgShopCode) {
		this.bfOrgShopCode = bfOrgShopCode;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public Long getBfOrgVendeeId() {
		return bfOrgVendeeId;
	}

	public void setBfOrgVendeeId(Long bfOrgVendeeId) {
		this.bfOrgVendeeId = bfOrgVendeeId;
	}

	public Long getBfOrgVenderId() {
		return bfOrgVenderId;
	}

	public void setBfOrgVenderId(Long bfOrgVenderId) {
		this.bfOrgVenderId = bfOrgVenderId;
	}

	public Long getBfOrgWarehId() {
		return bfOrgWarehId;
	}

	public void setBfOrgWarehId(Long bfOrgWarehId) {
		this.bfOrgWarehId = bfOrgWarehId;
	}

	public Long getBfOrgRcvWarehId() {
		return bfOrgRcvWarehId;
	}

	public void setBfOrgRcvWarehId(Long bfOrgRcvWarehId) {
		this.bfOrgRcvWarehId = bfOrgRcvWarehId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public Long getBfOrgShopId() {
		return bfOrgShopId;
	}

	public void setBfOrgShopId(Long bfOrgShopId) {
		this.bfOrgShopId = bfOrgShopId;
	}

	public Long getLowShopId() {
		return lowShopId;
	}

	public void setLowShopId(Long lowShopId) {
		this.lowShopId = lowShopId;
	}

	public Long getRcptLocId() {
		return rcptLocId;
	}

	public void setRcptLocId(Long rcptLocId) {
		this.rcptLocId = rcptLocId;
	}

	public String getLowShopCode() {
		return lowShopCode;
	}

	public void setLowShopCode(String lowShopCode) {
		this.lowShopCode = lowShopCode;
	}

	public String getRcptLocCode() {
		return rcptLocCode;
	}

	public void setRcptLocCode(String rcptLocCode) {
		this.rcptLocCode = rcptLocCode;
	}

	public Long getFactSenWarehId() {
		return factSenWarehId;
	}

	public void setFactSenWarehId(Long factSenWarehId) {
		this.factSenWarehId = factSenWarehId;
	}

	public String getFactSenWarehCode() {
		return factSenWarehCode;
	}

	public void setFactSenWarehCode(String factSenWarehCode) {
		this.factSenWarehCode = factSenWarehCode;
	}

	public Long getFactRcvWarehId() {
		return factRcvWarehId;
	}

	public void setFactRcvWarehId(Long factRcvWarehId) {
		this.factRcvWarehId = factRcvWarehId;
	}

	public String getFactRcvWarehCode() {
		return factRcvWarehCode;
	}

	public void setFactRcvWarehCode(String factRcvWarehCode) {
		this.factRcvWarehCode = factRcvWarehCode;
	}

	public Long getOprId() {
		return oprId;
	}

	public void setOprId(Long oprId) {
		this.oprId = oprId;
	}

	public String getOprCode() {
		return oprCode;
	}

	public void setOprCode(String oprCode) {
		this.oprCode = oprCode;
	}

}
