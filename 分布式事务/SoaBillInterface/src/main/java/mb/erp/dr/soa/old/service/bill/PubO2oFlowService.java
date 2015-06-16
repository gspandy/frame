package mb.erp.dr.soa.old.service.bill;

import mb.erp.dr.soa.constant.O2OBillConstant.AllocType;
import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.BaseBizVo;
import mb.erp.dr.soa.old.vo.BgrVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.PubO2oFlowVo;
import mb.erp.dr.soa.old.vo.ScnVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

/**
 * 老ERP单据流服务
 * @author cyyu
 *
 */
public interface PubO2oFlowService {

	/**
     * 结果信息表写入接口(预处理)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4Prepare(SfSchTaskExecOosVo vo,PubO2oFlowVo pFlowVo ,String remark,AllocType allocType,POF_DocStatus docStatus);
    
    /**
     * 结果信息表更新接口(预处理)
     * @param remark
     * @param b2cCode
     * @param pubFlowSeq 重处理需要判断当前重处理的是哪个流程
     */
    public void savePof4PrepareUpdate(Integer pubFlowSeq , String b2cCode , String remark,String batchNo);
    
    /**
     * 结果信息表写入接口(IDT)
     * @param vo IdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4Idt(SfSchTaskExecOosVo vo,IdtVo idtVo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(TBN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Tbn(BaseBizVo vo,TbnVo tbnVo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(ADN)
     * @param vo AdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Adn(AdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(GDN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Gdn(GdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(GRN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4Grn(GrnVo vo,String remark,POF_DocStatus docStatus);

    /**
     * 结果信息表写入接口(BGR)
     * @param vo
     * @param remark
     * @param docStatus
     */
    public void savePof4Bgr(BgrVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(Scn)
     * @param vo
     * @param remark
     * @param docStatus
     */
    public void savePof4Scn(ScnVo vo,String remark,POF_DocStatus docStatus);
    
	public void deleteSql(PubO2oFlowVo pvo);
}
