package mb.erp.dr.soa.service.bill;

import mb.erp.dr.soa.constant.O2OBillConstant.POF_DocStatus;
import mb.erp.dr.soa.old.vo.AdnVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.IdtVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.DrTbnVo;
import mb.erp.dr.soa.vo.SfDgnVo;
import mb.erp.dr.soa.vo.SfGdnVo;
import mb.erp.dr.soa.vo.SfGrnVo;
import mb.erp.dr.soa.vo.SfIdtVo;
import mb.erp.dr.soa.vo.SfRvdVo;

/**
 * 新ERP单据流服务
 * @author cyyu
 *
 */
public interface NewPubO2oFlowService {
    
    /**
     * 结果信息表写入接口(IDT)
     * @param vo IdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4SfIdt(SfIdtVo sfIdtVo,IdtVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(IDT)
     * @param vo IdtVo
     * @param remark
     * @param allocType
     * @param docStatus
     */
    public void savePof4SfIdt(SfIdtVo sfIdtVo,AdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(TBN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4DrTbn(DrTbnVo drTbnVo,TbnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(DGN)
     * @param vo AdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfDgn(SfDgnVo sfDgnVo,AdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(DGN)
     * @param vo AdnVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfDgn(SfDgnVo sfDgnVo,GdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(GDN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfGdn(SfGdnVo sfGdnVo,GdnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(RVD)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfRvd(SfRvdVo sfRvdVo,GrnVo vo,String remark,POF_DocStatus docStatus);
    
    /**
     * 结果信息表写入接口(GRN)
     * @param vo SfSchTaskExecOosVo
     * @param remark
     * @param docStatus
     */
    public void savePof4SfGrn(SfGrnVo sfGrnVo,GrnVo vo,String remark,POF_DocStatus docStatus);
}
