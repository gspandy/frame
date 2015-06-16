package mb.erp.dr.soa.vo.common;

import java.io.Serializable;

/**
 * 公用ID转CODE
 * 
 * @author     郭明帅
 * @version    1.0, 2014-12-08
 * @see         CommonCodeVo
 * @since       全流通改造
 */
public class CommonCodeVo implements Serializable{

	private static final long serialVersionUID = -631506872432202415L;
	
	private String prodCode; //商品编码
	
	private String bfOrgVendeeCode; //购货方
	
	private String bfOrgVenderCode; //供货方
	
    private String bfOrgUnitCode; //组织编码
    
    private String bfOrgWarehCode; //仓库编码
    
    private String bfOrgRcvUnitCode; //接收组织编码

    private String bfOrgRcvWarehCode; //接收仓库编码
    
    private String brandCode; //品牌编码
    
    private String bfOrgShopCode;  //门店编码

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

	public String getBfOrgUnitCode() {
		return bfOrgUnitCode;
	}

	public void setBfOrgUnitCode(String bfOrgUnitCode) {
		this.bfOrgUnitCode = bfOrgUnitCode;
	}

	public String getBfOrgWarehCode() {
		return bfOrgWarehCode;
	}

	public void setBfOrgWarehCode(String bfOrgWarehCode) {
		this.bfOrgWarehCode = bfOrgWarehCode;
	}

	public String getBfOrgRcvUnitCode() {
		return bfOrgRcvUnitCode;
	}

	public void setBfOrgRcvUnitCode(String bfOrgRcvUnitCode) {
		this.bfOrgRcvUnitCode = bfOrgRcvUnitCode;
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
    
    
}
