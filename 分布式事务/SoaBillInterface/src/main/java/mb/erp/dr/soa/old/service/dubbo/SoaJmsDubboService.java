package mb.erp.dr.soa.old.service.dubbo;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.SysUnitVo;
import mb.erp.dr.soa.vo.SfIdtVo;

/**
 * jms需要的dubbo服务
 * @author cyyu
 *
 */
public interface SoaJmsDubboService {

    /**
  	 * 根据商品读取品牌
  	 * @param map
  	 * @return
  	 */
     public String selectBrandByProdID(Map map);
     
     public AdnVo select (AdnVo adnVo);
    
     public List<AdnDtlVo> selectAdnDtl(AdnVo adnVo);
     
 	/**
	 * 查询贸易供货方和上级组织
	 * @param map
	 * @return
	 */
    public SysUnitVo selectCusIDAndOwnerID(Map map);
    
    public GdnVo selectGdn(GdnVo gdnVo);
    public IdtVo selectIdtByAdn(AdnVo adnVo);
    
    public List<GdnDtlVo> selectGdnDtl(GdnVo gdnVo);
    
    /**
     * 将新ERP的现货订单单号写入到老ERP现货订单
     * @param idtVo
     * @param newErpIdtCode
     * @return
     */
    public Integer modifyIdt_newErpIdtCode(IdtVo idtVo,String newErpIdtCode);
    
    /**
     * 将新ERP的出库单号写入到老ERP出库单
     * @param gdnVo
     * @param newErpGdnCode
     * @return
     */
    public Integer modifyGdn_newErpGdnCode(GdnVo gdnVo,String newErpGdnCode);
    
    /**
     * 将新ERP的入库单号写入到老ERP入库单
     * @param grnVo
     * @param newErpGrnCode
     * @return
     */
    public Integer modifyGrn_newErpGrnCode(GrnVo grnVo,String newErpGrnCode);
    
    /**
	 * 根据z查询
	 * @param tdCode
	 * @return
	 */
	public ScnVo getScnVoByTDCode(String tdCode);
}
