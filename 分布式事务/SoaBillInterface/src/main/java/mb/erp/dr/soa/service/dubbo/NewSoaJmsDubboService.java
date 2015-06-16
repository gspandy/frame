package mb.erp.dr.soa.service.dubbo;

import java.util.List;
import java.util.Map;

import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;

/**
 * jms需要的dubbo服务
 * @author cyyu
 *
 */
public interface NewSoaJmsDubboService {

     /**
 	 * 根据新ERP现货订单生成交货单
 	 * @param sfIdtVo
 	 */
 	public SfDgnVo createSfDgnBySfIdt(Long sfIdtId);
 	
    /**
	 * 根据新ERP现货订单详情生成交货单详情
	 * @param sfIdtId
	 * @param sfDgnId
	 */
	public List<SfDgnDtlVo> createDgnDtlBySfIdtDtl(Long sfIdtId);
	
	/**
	 * 根据交货单生成出库单
	 * @param id, sfGdnId, createUser, docState(已确认)
	 */
	public SfGdnVo createSfGdnBySfDgn(SfDgnVo dgn);
	
	/**
	 * 根据交货单生成出库单明细
	 * @param id, sfGdnId, 
	 */
	public List<SfGdnDtlVo> createSfGdnDtlBySfDgnDtl(SfDgnVo dgn);
	
	/**
	 * 根据交货单code查询
	 * @param code
	 * @return
	 */
	public SfDgnVo selectDgnByCode(String code);
	
	/**
	 * 根据交货单查询交货单详情
	 * @param sfDgnVo
	 * @return
	 */
	public List<SfDgnDtlVo> createSfDgnDtlByDgn(SfDgnVo sfDgnVo);
	
	/**
	 * 根据出库单code查询
	 * @param code
	 * @return
	 */
	public SfGdnVo selectSfGdnByCode(String code);
	
	/**
	 * 根据现货单code查询
	 * @param code
	 * @return
	 */
	public SfIdtVo getSfIdtByCode(String code);
	
	/**
	 * 根据入库单ID查询
	 * @param code
	 * @return
	 */
	public SfGrnVo selectSfGrnById(Long id);
	
	/**
	 * 根据出库单查询详情
	 * @param code
	 * @return
	 */
	public List<SfGdnDtlVo> selectGdnDtlById(SfGdnVo sfGdnVo);
 	
	/**
	 * 根据到货通知单生成入库单
	 * @param id, sfGrnId
	 */
	public Integer insertByRVD(SfRvdVo rvd);
	
	/**
	 * 根据到货通知单生成入库单明细
	 * @param id, sfGrnId, 
	 */
	public Integer insertDtlByRVD(SfRvdVo rvd);
	
	/**
	 * 根据新ERP调配单生成交货单
	 * @param tbnId
	 * @return
	 */
	public SfDgnVo createDgnByDrTbn(Long tbnId);
	
	/**
	 * 根据新ERP调配单生成交货单明细
	 * @param tbnId dgnId
	 * @return
	 */
	public List<SfDgnDtlVo> createDgnDtlByDrTbn(Map<String, Long> params);
	
	/**
	 * 根据入库单ID查询明细
	 * @param code
	 * @return
	 */
	public List<SfGrnDtlVo> selectSfGrnDtlById(Long id);
	
	/**
	 * 更新新ERP现货订单状态
	 * @param map
	 * @return
	 */
	public int updateIdtDocState(Map map);
	
}
