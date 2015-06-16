/*
 * 文件名：MybatisTest.java 版权：Copyright 2014 MetersBonwe. All Rights Reserved. 描述：TODO 修改人：Weijf 修改时间：下午1:58:52 修改内容：
 */

package com.mtsbw.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mb.erp.dr.soa.constant.O2OBillConstant;
import mb.erp.dr.soa.constant.O2OBillConstant.BillType;
import mb.erp.dr.soa.old.dao.BalanceMapper;
import mb.erp.dr.soa.old.dao.IdtMapper;
import mb.erp.dr.soa.old.service.balance.BalanceService;
import mb.erp.dr.soa.old.service.bill.BillService;
import mb.erp.dr.soa.old.service.impl.bill.GdnService;
import mb.erp.dr.soa.old.service.impl.bill.GrnService;
import mb.erp.dr.soa.old.service.impl.bill.IdtService;
import mb.erp.dr.soa.old.service.wareh.WarehService;
import mb.erp.dr.soa.old.vo.GdnDtlVo;
import mb.erp.dr.soa.old.vo.GdnVo;
import mb.erp.dr.soa.old.vo.GrnDtlVo;
import mb.erp.dr.soa.old.vo.GrnVo;
import mb.erp.dr.soa.old.vo.TbnDtlVo;
import mb.erp.dr.soa.old.vo.TbnVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosDtlVo;
import mb.erp.dr.soa.vo.SfSchTaskExecOosVo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JUnit测试，对象消息
 * 
 * @author Weijf
 * @version
 * @see
 * @since
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-mybatis.xml")
public class MybatisTest111 {
    @Resource
    private IdtService idtService;
    @Resource
    private IdtMapper idtMapper;
    @Resource
    private GdnService gdnService;
//    @Resource
//    private GrnService grnService;
    @Resource
    private BalanceMapper balanceMapper;
    @Resource
    private BalanceService balanceService;
    @Resource
    private WarehService warehService;
    @Resource(type=GrnService.class)
    private BillService<GrnVo> grnService;
    @Resource
    private BillService<TbnVo> tbnService;
    @Test
    public void testInsertAndSendObj() {
        try {
//            int count = 3;
//            for (int i = 0; i < count; i++) {
//                MyTest mt = new MyTest();
//                mt.setStatus(i);
//                mt.setText("This is a test!!!");
//                myTestXaService.insertAndSendMybatis(mt);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TbnVo createTbn4Save(){
    	// 造数据---------------------------------------------------------
    	TbnVo tbn = new TbnVo();
    	tbn.setControlStatus("1"); // 规则编码
    	tbn.setVenderId("HQ01");
    	tbn.setAdnNum("845673462");
    	tbn.setVendeeId("A00361");
    	tbn.setDispWarehId("A00094W001");
    	tbn.setRcvWarehId("HQ01W008");
    	tbn.setOprId("HQ01U0010");
    	tbn.setInvsgId("HQ01U0010");
    	tbn.setDelivMthd("TRCK");
    	tbn.setDelivAddr("温州市鹿城仓储基地8棟");
    	tbn.setTaxRate(17d);
    	tbn.setCurrency("RMB");
    	tbn.setCrQty(10d);
    	tbn.setCrVal(500d);
    	tbn.setDelivQty(10d);
    	tbn.setDelivVal(500d);
    	tbn.setTbnType("BI");
    	
    	tbn.setDocDate(new Date()); // 非空
    	tbn.setReqdAt(new Date()); // 非空
    	tbn.setDispTime(new Date()); // 验证非空
    	tbn.setReasonCode("ABCD");// 验证非空
    	tbn.setBrandId("00000002");// 验证非空
    	// ----------------------------------
    	// 默认值
    	tbn.setProgress(O2OBillConstant.PROGRESS.PG);
    	tbn.setSuspended("F");
    	tbn.setCancelled("F");
    	tbn.setDataSource("01");
    	// ----------------------------------
    	tbn.setRemark("鹤壁，福州直发调配，货已提");
    	tbn.setIsOos("F");
    	
    	TbnDtlVo tbnDtlVo = new TbnDtlVo();
    	tbnDtlVo.setVenderId("HQ01");
    	tbnDtlVo.setProdId("14184970042");
    	tbnDtlVo.setUnitPrice(145d);
    	tbnDtlVo.setDiscRate(60d);
    	tbnDtlVo.setExpdQty(1d);
    	tbnDtlVo.setDelivQty(1d);
    	tbnDtlVo.setRcvQty(0d);
    	
    	TbnDtlVo tbnDtlVo1 = new TbnDtlVo();
    	tbnDtlVo1.setVenderId("HQ01");
    	tbnDtlVo1.setProdId("14184970046");
    	tbnDtlVo1.setUnitPrice(145d);
    	tbnDtlVo1.setDiscRate(60d);
    	tbnDtlVo1.setExpdQty(4d);
    	tbnDtlVo1.setDelivQty(4d);
    	tbnDtlVo1.setRcvQty(0d);
    	
    	TbnDtlVo tbnDtlVo2 = new TbnDtlVo();
    	tbnDtlVo2.setVenderId("HQ01");
    	tbnDtlVo2.setProdId("15304615030");
    	tbnDtlVo2.setUnitPrice(99d);
    	tbnDtlVo2.setDiscRate(60d);
    	tbnDtlVo2.setExpdQty(3d);
    	tbnDtlVo2.setDelivQty(3d);
    	tbnDtlVo2.setRcvQty(0d);
    	
    	List<TbnDtlVo> list = new ArrayList<TbnDtlVo>();
    	list.add(tbnDtlVo);
    	list.add(tbnDtlVo1);
    	list.add(tbnDtlVo2);
    	tbn.setTbnDtlVos(list);
    	// 造数据---------------------------------------------------------
    	
    	return tbn;
    }
    
    public GdnVo createGdn4Save(){
    	// 造数据---------------------------------------------------------
    	GdnVo gdn = new GdnVo();
    	gdn.setControlStatus("111111"); // 规则编码
    	gdn.setUnitId("A00024S001");
    	gdn.setDocDate(new Date());
    	gdn.setDelivMode("AGOF");
    	gdn.setWarehId("HQ01W020");
    	gdn.setRcvUnitId("A00612");
    	gdn.setOprId("HQ01U0018");
    	gdn.setCtrlrId("A00292U0015");
    	gdn.setContactId("A00292U0015");
    	gdn.setSrcDocType(BillType.AAD);
    	gdn.setSrcDocNum("123");
    	gdn.setCurrency("RMB");
    	gdn.setTtlQty(20d);
    	gdn.setTtlVal(1d);
    	gdn.setTaxRate(17d);
    	gdn.setTaxVal(1d);
    	gdn.setPsnVal(1d);
    	gdn.setAddtCost(1d);
    	gdn.setCost(1d);
    	gdn.setDelivMthd("OTHR");
    	gdn.setDelivAddr("shanghai");
    	gdn.setDelivPstd("335200");
    	gdn.setEfficient("F");
    	gdn.setStruck("F");
    	gdn.setCostChg("F");
    	gdn.setProgress("DD");
    	gdn.setSuspended("T");
    	gdn.setCancelled("F");
    	gdn.setRemark("河北深州--上海仓配发，请发快递");
    	gdn.setRcvWarehId("A00612S001");
    	gdn.setBrandId("00000001");
    	
    	GdnDtlVo tbnDtlVo = new GdnDtlVo();
    	tbnDtlVo.setUnitId("A00298");
    	tbnDtlVo.setProdId("14184970042");
    	tbnDtlVo.setUnitPrice(145d);
    	tbnDtlVo.setDiscRate(60d);
    	tbnDtlVo.setQuantity(3d);
    	tbnDtlVo.setUnitPrice(50d);
    	tbnDtlVo.setUnitAddtCost(1d);;
    	
    	GdnDtlVo tbnDtlVo1 = new GdnDtlVo();
    	tbnDtlVo1.setUnitId("A00298");
    	tbnDtlVo1.setProdId("14184970046");
    	tbnDtlVo1.setUnitPrice(145d);
    	tbnDtlVo1.setDiscRate(60d);
    	tbnDtlVo1.setQuantity(3d);
    	tbnDtlVo1.setUnitPrice(50d);
    	tbnDtlVo1.setUnitAddtCost(1d);;
    	
    	GdnDtlVo tbnDtlVo2 = new GdnDtlVo();
    	tbnDtlVo2.setUnitId("A00298");
    	tbnDtlVo2.setProdId("15304615030");
    	tbnDtlVo2.setUnitPrice(99d);
    	tbnDtlVo2.setDiscRate(60d);
    	tbnDtlVo2.setQuantity(3d);
    	tbnDtlVo2.setUnitPrice(50d);
    	tbnDtlVo2.setUnitAddtCost(1d);
    	
    	List<GdnDtlVo> list = new ArrayList<GdnDtlVo>();
    	list.add(tbnDtlVo);
    	list.add(tbnDtlVo1);
    	list.add(tbnDtlVo2);
    	gdn.setGdnDtlVos(list);
    	// 造数据---------------------------------------------------------
    	return gdn;
    }

    /**
     * 入库单
     * @return
     */
    public GrnVo createGrn4Save(){
    	// 造数据---------------------------------------------------------
    	GrnVo grn = new GrnVo();
    	List<String> rulesGroup = new ArrayList<String>();
//    	rulesGroup.add("rule1");
//    	rulesGroup.add("rule2");
    	grn.setRulesGroup(rulesGroup);
    	grn.setControlStatus("11"); // 规则编码
    	grn.setUnitId("A00298");
    	grn.setDocDate(new Date());
    	grn.setSrcDocType(BillType.AAD);
		grn.setRcptMode("ADJS");
		grn.setWarehId("HQ01W008");
		grn.setOprId("SA1");
		grn.setDispUnitId("HQ01");
		grn.setCurrency("RMB");
		grn.setTtlQty(95d);
		grn.setTtlVal(1234d);
		grn.setTaxRate(1d);
		grn.setTaxVal(1d);
		grn.setPsnVal(1d);
		grn.setAddtCost(1d);
		grn.setCost(431.8225d);
		grn.setRcptTime(new Date());
		grn.setEfficient("T");
		grn.setStruck("F");
		grn.setCostChg("F");
		grn.setProgress("PG");
		grn.setSuspended("F");
		grn.setCancelled("F");
		grn.setRemark("WMOS交易类型：606；交易代码：02；交易流水号：16355；操作代码：09");
		grn.setPdaProgress("CN");
		grn.setBrandId("00000001");
		grn.setDelivWarehId("HQ01W008");

    	GrnDtlVo grnDtlVo = new GrnDtlVo();
    	grnDtlVo.setUnitId("A00298");
    	grnDtlVo.setProdId("14184970042");
    	grnDtlVo.setUnitPrice(145d);
    	grnDtlVo.setDiscRate(60d);
    	grnDtlVo.setQuantity(7d);
    	grnDtlVo.setUnitPrice(50d);
    	grnDtlVo.setUnitAddtCost(1d);
    	grnDtlVo.setUnitCost(63d);
    	
    	GrnDtlVo grnDtlVo1 = new GrnDtlVo();
    	grnDtlVo1.setUnitId("A00298");
    	grnDtlVo1.setProdId("14184970046");
    	grnDtlVo1.setUnitPrice(145d);
    	grnDtlVo1.setDiscRate(60d);
    	grnDtlVo1.setQuantity(8d);
    	grnDtlVo1.setUnitPrice(50d);
    	grnDtlVo1.setUnitAddtCost(1d);
    	grnDtlVo1.setUnitCost(63d);
    	
    	GrnDtlVo grnDtlVo2 = new GrnDtlVo();
    	grnDtlVo2.setUnitId("A00298");
    	grnDtlVo2.setProdId("15304615030");
    	grnDtlVo2.setUnitPrice(99d);
    	grnDtlVo2.setDiscRate(60d);
    	grnDtlVo2.setQuantity(9d);
    	grnDtlVo2.setUnitPrice(50d);
    	grnDtlVo2.setUnitAddtCost(1d);
    	grnDtlVo2.setUnitCost(63d);
    	
    	List<GrnDtlVo> list = new ArrayList<GrnDtlVo>();
    	list.add(grnDtlVo);
    	list.add(grnDtlVo1);
    	list.add(grnDtlVo2);
    	grn.setGrnDtlVos(list);
    	// 造数据---------------------------------------------------------
    	return grn;
    }

    public static SfSchTaskExecOosVo createSfSchTaskExecOosVo(){
    	SfSchTaskExecOosVo vo = new SfSchTaskExecOosVo();
    	vo.setOsDocCode("111111");
    	vo.setDispWarehCode("23124312");
    	vo.setShopCode("2222222");
    	
    	SfSchTaskExecOosDtlVo dtlVo1 = new SfSchTaskExecOosDtlVo();
    	dtlVo1.setBrandId("brandId123");
    	dtlVo1.setId(351234l);
    	dtlVo1.setQty(3l);
    	
    	SfSchTaskExecOosDtlVo dtlVo2 = new SfSchTaskExecOosDtlVo();
    	dtlVo2.setBrandId("brandId123");
    	dtlVo2.setId(351234l);
    	dtlVo2.setQty(3l);
    	
    	SfSchTaskExecOosDtlVo dtlVo3 = new SfSchTaskExecOosDtlVo();
    	dtlVo3.setBrandId("brandId123");
    	dtlVo3.setId(351234l);
    	dtlVo3.setQty(3l);
    	
    	List<SfSchTaskExecOosDtlVo> list = new ArrayList<SfSchTaskExecOosDtlVo>();
    	list.add(dtlVo1);
    	list.add(dtlVo2);
    	list.add(dtlVo3);
    	
//    	vo.setDtlVos(list);
    	return vo;
    }
}
