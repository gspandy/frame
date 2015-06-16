package mb.erp.dr.soa.old.service.impl.dubbo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.old.dao.AdnMapper;
import mb.erp.dr.soa.old.dao.GdnMapper;
import mb.erp.dr.soa.old.dao.GrnMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.dao.ProdClsMapper;
import mb.erp.dr.soa.old.dao.ScnMapper;
import mb.erp.dr.soa.old.dao.SysUnitMapper;
import mb.erp.dr.soa.old.service.dubbo.SoaJmsDubboService;
import mb.erp.dr.soa.old.service.dubbo.SoaPriceDubboService;
import mb.erp.dr.soa.old.vo.AdnDtlVo;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.SysUnitVo;
import mb.erp.dr.soa.utils.SoaBillUtils;

import org.springframework.stereotype.Service;

/**
 * jms需要的dubbo服务
 * 包含接口：
 * @author     余从玉
 * @version    1.0, 2014-12-22
 * @see         SoaPriceDubboService
 * @since       全流通改造
 */
@Service
public class SoaJmsDubboServiceImpl implements SoaJmsDubboService {

	@Resource
    private ProdClsMapper prodClsMapper;
    @Resource
    private SysUnitMapper sysUnitMapper;
    @Resource
    private AdnMapper adnMapper;
    @Resource
	private GdnMapper gdnMapper;
    @Resource
    private IdtMapper idtMapper;
    @Resource
	private GrnMapper grnMapper;
    @Resource
	private ScnMapper scnMapper;
	
    /**
	 * 根据商品读取品牌
	 * @param map
	 * @return
	 */
   public String selectBrandByProdID(Map map){
	   return prodClsMapper.selectBrandByProdID(map);
   }
   
   public AdnVo select (AdnVo adnVo){
	   return adnMapper.select(adnVo);
   }
   
   public List<AdnDtlVo> selectAdnDtl(AdnVo adnVo){
	   return adnMapper.selectAdnDtl(adnVo);
   }
   
	/**
	 * 查询贸易供货方和上级组织
	 * @param map
	 * @return
	 */
    public SysUnitVo selectCusIDAndOwnerID(Map map){
    	return sysUnitMapper.selectCusIDAndOwnerID(map);
    }

	public GdnVo selectGdn(GdnVo gdnVo) {
		return gdnMapper.selectGdn(gdnVo);
	}

	public List<GdnDtlVo> selectGdnDtl(GdnVo gdnVo) {
		return gdnMapper.selectGdnDtl(gdnVo);
	}
	@Override
	public IdtVo selectIdtByAdn(AdnVo adnVo) {
		IdtVo vo = new IdtVo();
		vo.setIdtNum(adnVo.getSrcDocNum());
		vo.setVendeeId(adnVo.getVendeeId());
		vo = idtMapper.selectByPrimaryKey(vo);
		return vo;
	}

	@Override
	public Integer modifyGrn_newErpGrnCode(GrnVo grnVo, String newErpGrnCode) {
		if (SoaBillUtils.isBlank(newErpGrnCode)) {
			return 0;
		}else{
			grnVo.setNewGrnnum(newErpGrnCode);
			return grnMapper.updateNewErpGrnCode(grnVo);
		}
	}

	@Override
	public Integer modifyIdt_newErpIdtCode(IdtVo idtVo, String newErpIdtCode) {
		if (SoaBillUtils.isBlank(newErpIdtCode)) {
			return 0;
		}else{
			idtVo.setNewIdtId(newErpIdtCode);
			return idtMapper.updateNewErpIdtCode(idtVo);
		}
	}

	@Override
	public Integer modifyGdn_newErpGdnCode(GdnVo gdnVo, String newErpGdnCode) {
		if (SoaBillUtils.isBlank(newErpGdnCode)) {
			return 0;
		}else{
			gdnVo.setNewGdnnum(newErpGdnCode);
			return gdnMapper.updateNewErpGdnCode(gdnVo);
		}
	}

	@Override
	public ScnVo getScnVoByTDCode(String tdCode) {
		List<ScnVo> list= scnMapper.getScnVoByTDCode(tdCode);
		if (list == null || list.size() == 0) {
			return null;
		}
		return  list.get(0);
	}
}
