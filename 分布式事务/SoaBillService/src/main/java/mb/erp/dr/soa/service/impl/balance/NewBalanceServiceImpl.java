package mb.erp.dr.soa.service.impl.balance;

import mb.erp.dr.soa.service.balance.NewBalanceService;

import org.springframework.stereotype.Service;

/**
 * 新ERP资金接口
 * 包含接口：增加余额、正数扣减余额、负数扣减余额、正数冻结金额、负数冻结金额、释放冻结
 * @author     余从玉
 * @version    1.0, 2014-12-18
 * @see         NewBalanceService
 * @since       全流通改造
 */
@Service
public class NewBalanceServiceImpl implements NewBalanceService {
	
//	private final Logger logger = LoggerFactory.getLogger(NewBalanceServiceImpl.class);
//	@Resource
//	private NewERPCommonService newERPCommonService;
//	/// <summary>
//    /// 金额加减
//    /// </summary>
//    /// <param name="pUnitID"></param>
//    /// <param name="pRelationId"></param>
//    /// <param name="docType"></param>
//    /// <param name="pDocNum"></param>
//    /// <param name="pCurrency"></param>
//    /// <param name="pTranAmt"></param>
//    /// <param name="tranType"></param>
//    /// <param name="pErrormsg"></param>
//    /// <returns></returns>
//	
//    public MsgVo Balance(String pUnitCode, String pRelationCode, String docType, String pDocNum, String pCurrency, Double pTranAmt, String tranType){
//    	MsgVo msg = new MsgVo(MSG_CODE.SUCCESS);
//        Long unitId = 0l;
//        Long relationId = 0l;
//        try{
//            unitId = newERPCommonService.getUnitIdByUnitCode(pUnitCode);
//            relationId = newERPCommonService.getUnitIdByUnitCode(pRelationCode);
//        }catch (Exception ex){
//            logger.error("交易失败，原因：{0} ", ex.getMessage());
//           throw new RuntimeException(ex.getMessage());
//        }
//        IBalanceStrategy strategy = BalanceFactory.Create(false, docType);
//        Tuple<String, String> tuple = UtilityTool.SplitDocNum(pDocNum, true);
//        return Balance_second(unitId.toString(), relationId.toString(), tuple.Item1, pCurrency, pTranAmt, tranType);
//    }
//
//    public Boolean Balance_second(Long uid, Long rId, String pDocNum, String pCurrency, Double pTranAmt, String tranType){
//        try{
//            if (pTranAmt == 0) throw new Util.APPException("参数错误:金额不能为零", Util.APPMessageType.DisplayToUser);
//
//            //获取帐户
//            FsclAcInfo unitInfo = null;
//            FsclAcInfo relationInfo = null;
//
////            var pUnitID = Convert.ToInt32(uid);
////            var pRelationId = Convert.ToInt32(rId);
//
//            bool bIsVenderSaveTran = GetSystem.GetSysParamValue("IS_VENDER_SAVE_TRAN").Equals("1");
//
//            if (bIsVenderSaveTran){
//                //锁定
//                FsclAcDAL.UpObjectCUByCpdUnitId(pUnitID, pUnitID, ConstString.CU);
//                unitInfo = FsclAcDAL.GetObjectCUByCpdUnitId(pUnitID, pUnitID, ConstString.CU);
//                if (unitInfo == null){
//                    var unitCode = BfOrgUtil.GetCode(pUnitID);
//                    //var relationCode = BfOrgUtil.GetCode(pRelationId);
//                    throw new Util.APPException(String.Format("供货方[{1}]往来账户不存在[{0}]", DocType, unitCode), Util.APPMessageType.DisplayToUser);
//                }
//            }
//
//            //锁定
//            FsclAcDAL.UpObjectCUByCpdUnitId(pUnitID, pRelationId, ConstString.CU);
//            relationInfo = FsclAcDAL.GetObjectCUByCpdUnitId(pUnitID, pRelationId, ConstString.CU);
//            if (relationInfo == null){
//                var unitCode = BfOrgUtil.GetCode(pUnitID);
//                var relationCode = BfOrgUtil.GetCode(pRelationId);
//                throw new Util.APPException(String.Format("供货方[{1}]购货方[{2}]往来账户不存在[{0}]", DocType, unitCode, relationCode), Util.APPMessageType.DisplayToUser);
//            }
//            IsBranch = BfOrgUtil.IsBranch(pUnitID);
//
//            //判断是否有足够金额 如不足使用信用额度
//            var money = CheckMoney(pTranAmt, unitInfo, relationInfo, pUnitID, pRelationId, pDocNum);
//            // pTranAmt = money;
//            MB.Util.TraceEx.Write(String.Format("-----------------    {0}", pTranAmt));
//            //  if (pTranAmt == 0) { LogUtil.Write(CLASSNAME, FUNCNAME, "全部使用信用额度，退出"); return true; }
//
//            var result = Update(unitInfo, relationInfo, pTranAmt, money, pDocNum, bIsVenderSaveTran);
//            if (!result) throw new Util.APPException(String.Format("往来帐{0} {1}可能已被其它用户修改,本次操作不成功！", unitInfo.CODE, relationInfo.CODE), Util.APPMessageType.DisplayToUser);
//
//            //2.事务
//            // 收款单:'RCB' 扣款单:'SDB ：    HQ01   计贷 ，对方 计借，金额 计 正数 ；   
//            // 借支单等其他单据         ：    HQ01   计借 ，对方 计贷，金额 计 负数 ；  
//            // 代理商账户上的钱 对美邦来说 是一种负债，所以,对美邦来讲，代理商存款增加，要记贷方。
//            //
//            // 对于企业来讲，银行存款是企业的一种资产，企业可以自由支配和使用。所以,对企业来讲，银行存款增加，应该记借方DR。
//            // 对于银行来讲，企业存款是一种负债，银行要随时准备支付给企业资金。所以,对银行来讲，企业存款增加，要记贷方CR。
//            //再简单点说，对于企业来讲，存款肯定是一种资产，企业把资产放在银行，银行就对企业承担了债务。这就像甲企业应收乙企业的帐款，对于乙企业来讲，就成了应付帐款。
//            //添加事务
//            result = SaveTran(unitInfo, relationInfo, pTranAmt, pDocNum, tranType, pCurrency, bIsVenderSaveTran);
//            if (!result) throw new Util.APPException("添加事务明细失败", Util.APPMessageType.DisplayToUser);
//
//            return true;
//        }catch (Exception ex){
//            logger.error("交易失败，原因：{0} ",ex.getMessage());
////            LogUtil.Write(CLASSNAME, FUNCNAME, pErrormsg, String.format("uid:{0} rId:{1} pDocNum:{2} pCurrency:{3} pTranAmt:{4} tranType:{5}", uid, rId, pDocNum, pCurrency, pTranAmt, tranType));
//            return false;
//        }
//    }
}
