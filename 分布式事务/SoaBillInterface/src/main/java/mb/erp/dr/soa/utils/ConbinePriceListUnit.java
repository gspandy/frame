package mb.erp.dr.soa.utils;

import java.util.List;

import mb.erp.dr.soa.vo.common.MsgListVo;

/**
 * 批量取新ERP价格时，部分没有取到价格，则需从老ERP取，并组合返回
 * @param newList
 * @param oldList
 * @return
 */
public class ConbinePriceListUnit {

	public static List<MsgListVo> conbinePriceFromNewErpAndOldByWunit(List<MsgListVo> newList, List<MsgListVo> oldList){
		if (oldList == null || oldList.size() == 0) {
			return newList;
		}
		for (MsgListVo nlist : newList) {
			if (nlist.getPrice() == 0 || nlist.getDiscRate() == 0) {
				for(MsgListVo olist : oldList){
					if (olist.getProdId().equals(nlist.getProdId())) {
						nlist.setPrice(olist.getPrice());
						nlist.setDiscRate(olist.getDiscRate());
					}
				}
			}
		}
		return newList;
	}
}
