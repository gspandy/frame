package mb.erp.dr.soa.utils;

import mb.erp.dr.soa.constant.O2OBillConstant.NewBillType;


/**
 * 新ERP状态转换
 * @author gms
 *
 */
public class StatusChangeUtils {
	public static String getStatus(int docState,NewBillType newBillType){
		String status = "";
		if (newBillType.name().equals(NewBillType.IDT.name())) {
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 4:status = "已完成";break;
			case 8:status = "已撤销";break;
			case 16:status = "已挂起";break;
			case 65537:status = "配货中";break;
			case 65538:status = "已配货";break;
			case 65540:status = "已发货";break;
			case 196608:status = "发货中";break;
			case 327680:status = "收货中";break;
			case 393216:status = "已收货";break;
			case 458752:status = "过账中";break;
			}
		}else if(newBillType.name().equals(NewBillType.TBN.name())){
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 4:status = "已完成";break;
			case 8:status = "已撤销";break;
			case 16:status = "已挂起";break;
			case 393216:status = "配货中";break;
			case 458752:status = "已配货";break;
			case 69632:status = "已发货";break;
			case 196608:status = "发货中";break;
			case 327680:status = "收货中";break;
			case 77824:status = "已收货";break;
			case 524288:status = "过账中";break;
			}
		}else if(newBillType.name().equals(NewBillType.DGN.name())){
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 8:status = "已撤销";break;
			case 65536:status = "分拣中";break;
			case 131072:status = "分拣中完成";break;
			case 196608:status = "待出库";break;
			case 524288:status = "已出库";break;
			case 589824:status = "已冲单";break;
			case 1441792:status = "发货中";break;
			case 1507328:status = "过账中";break;
			}
		}else if(newBillType.name().equals(NewBillType.GDN.name())){
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 8:status = "已撤销";break;
			case 65536:status = "已发货";break;
			case 131072:status = "已冲单";break;
			case 589824:status = "待出库";break;
			case 1048576:status = "过账中";break;
			}
		}else if(newBillType.name().equals(NewBillType.RVD.name())){
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 8:status = "已撤销";break;
			case 65536:status = "已发货";break;
			case 131072:status = "收货中";break;
			case 196608:status = "已收货";break;
			case 262144:status = "已入库";break;
			case 524288:status = "已冲单";break;
			case 589824:status = "过账中";break;
			}
		}else if(newBillType.name().equals(NewBillType.GRN.name())){
			switch (docState) {
			case 0:status = "录入中";break;
			case 1:status = "已确认";break;
			case 2:status = "已审核";break;
			case 8:status = "已撤销";break;
			case 65536:status = "已收货";break;
			case 69632:status = "待入库";break;
			case 131072:status = "已冲单";break;
			case 327680:status = "分储中";break;
			case 393216:status = "分储完成";break;
			case 524288:status = "冲销已入库";break;
			case 589824:status = "过账中";break;
			}
		}
		return status;
	}
}
