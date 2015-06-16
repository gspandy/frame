package mb.erp.dr.soa.service.impl.dubbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mb.erp.dr.soa.dao.SfDgnMapper;
import mb.erp.dr.soa.dao.SfGdnMapper;
import mb.erp.dr.soa.dao.SfGrnMapper;
import mb.erp.dr.soa.dao.SfIdtMapper;
import mb.erp.dr.soa.old.service.dubbo.SoaPriceDubboService;
import mb.erp.dr.soa.service.dubbo.NewSoaJmsDubboService;
import mb.erp.dr.soa.vo.SfDgnDtlVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnDtlVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnDtlVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;

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
public class NewSoaJmsDubboServiceImpl implements NewSoaJmsDubboService {
	@Resource
	private SfDgnMapper sfDgnMapper;
	@Resource
	private SfGdnMapper sfGdnMapper;
	@Resource
	private SfGrnMapper sfGrnMapper;
	@Resource
	private SfIdtMapper sfIdtMapper;
	
	 /**
 	 * 根据新ERP现货订单生成交货单
 	 * @param sfIdtVo
 	 */
	public SfDgnVo createSfDgnBySfIdt(Long sfIdtId) {
		return sfDgnMapper.genDgnBySfIdt(sfIdtId);
	}

    /**
	 * 根据新ERP现货订单详情生成交货单详情
	 * @param sfIdtId
	 * @param sfDgnId
	 */
	public List<SfDgnDtlVo> createDgnDtlBySfIdtDtl(Long sfIdtId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("sfIdtId", sfIdtId);
		return sfDgnMapper.genDgnDtlBySfIdtDtl(map);
	}

	/**
	 * 根据交货单生成出库单
	 * @param 
	 */
	public SfGdnVo createSfGdnBySfDgn(SfDgnVo dgn) {
		return sfGdnMapper.genSfGdnBySfDgn(dgn);
	}

	/**
	 * 根据交货单生成出库单明细
	 * @param id, sfGdnId, 
	 */
	public List<SfGdnDtlVo> createSfGdnDtlBySfDgnDtl(SfDgnVo dgn) {
		return sfGdnMapper.genSfGdnDtlBySfDgnDtl(dgn);
	}

	/**
	 * 根据交货单code查询
	 * @param code
	 * @return
	 */
	public SfDgnVo selectDgnByCode(String code) {
		return sfDgnMapper.selectDgnByCode(code);
	}

	/**
	 * 根据到货通知单生成入库单
	 * @param id, sfGrnId 必传参数
	 */
	public Integer insertByRVD(SfRvdVo rvd) {
		return sfGrnMapper.insertByRVD(rvd);
	}

	/**
	 * 根据到货通知单生成入库单明细
	 * @param id, sfGrnId 必传参数
	 */
	public Integer insertDtlByRVD(SfRvdVo rvd) {
		return sfGrnMapper.insertDtlByRVD(rvd);
	}

	/**
	 * 根据出库单code查询
	 * @param code
	 * @return
	 */
	public SfGdnVo selectSfGdnByCode(String code) {
		return sfGdnMapper.selectGdnByCode(code);
	}

	/**
	 * 根据出库单查询详情
	 * @param code
	 * @return
	 */
	public List<SfGdnDtlVo> selectGdnDtlById(SfGdnVo sfGdnVo) {
		return sfGdnMapper.selectGdnDtlById(sfGdnVo);
	}

	/**
	 * 根据入库单ID查询
	 * @param code
	 * @return
	 */
	public SfGrnVo selectSfGrnById(Long id) {
		return sfGrnMapper.selectGrnById(id);
	}

	/**
	 * 根据新ERP调配单生成交货单
	 * @param tbnId
	 * @return
	 */
	public SfDgnVo createDgnByDrTbn(Long tbnId) {
		return sfDgnMapper.genDgnByDrTbn(tbnId);
	}
	
	/**
	 * 根据新ERP调配单生成交货单明细
	 * @param tbnId dgnId
	 * @return
	 */
	public List<SfDgnDtlVo> createDgnDtlByDrTbn(Map<String, Long> params) {
		return sfDgnMapper.genDgnDtlByDrTbn(params);
	}

	@Override
	public SfIdtVo getSfIdtByCode(String code) {
		return sfIdtMapper.selectSfIdtByCode(code);
	}

	/**
	 * 根据交货单查询交货单详情
	 * @param sfDgnVo
	 * @return
	 */
	public List<SfDgnDtlVo> createSfDgnDtlByDgn(SfDgnVo sfDgnVo) {
		return sfDgnMapper.genSfDgnDtlByDgn(sfDgnVo);
	}

	public List<SfGrnDtlVo> selectSfGrnDtlById(Long id) {
		return sfGrnMapper.selectGrnDtlByGrnId(id);
	}

	/**
	 * 更新新ERP现货订单状态
	 * @param map
	 * @return
	 */
	public int updateIdtDocState(Map map){
		return sfIdtMapper.updateIdtDocState(map);
	}

}
