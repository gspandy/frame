package mb.mba.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mb.mba.core.entity.TradesDtlEntity;
import mb.mba.core.entity.TradesEntity;
import mb.mba.core.entity.auth.UserEntity;
import mb.mba.service.ITradeService;
import mb.mba.util.JqGridPagesUtils;
import mb.mba.vo.JqGridBaseEntityVo;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


/**
 * 
 * 类描述： 交易查询
 * @author:sun@mb.com
 * @version   
 * @2015年6月8日
 */
@Controller
@RequestMapping("/trades")
public class TradesController {
    
    @Resource
    public ITradeService iTradeService;
   
   /**
    * 交易查询页面
    * @return
    */
    @RequestMapping("/trades")
    public String index(){
        return "trades";
    }
    @RequestMapping("/dtltrades")
    public ModelAndView dtlindex(@RequestParam(value="docNum",required=false) String docNum){
    	ModelAndView mv=new ModelAndView("tradesdtl");
    	if(docNum!=null)mv.addObject("docNum", docNum);
        return mv;
    }
    /**
     * TODO 修改名称tradeslist,防止权限匹配
     * 功能描述： 查询出入库交易
     * @author:sun@mb.com
     * @2015年6月5日  
     * @param:
     * @version
     */
    @ResponseBody
    @RequestMapping(value="/tradeslist",method=RequestMethod.POST)
    public JqGridBaseEntityVo<TradesEntity> listTrades(JqGridBaseEntityVo<TradesEntity> page,UserEntity u,HttpServletRequest request,HttpServletResponse response){
        //声明分页
    	String json=request.getParameter("Param");
    	System.out.println(json);
     	TradesEntity entity=JSON.parseObject(json, TradesEntity.class);
    	
        RowBounds rb = new RowBounds((page.getPage()-1)*page.getRows(),page.getRows());
       
        int totalCount = iTradeService.queryTradesListCountByParams(entity);
        List<TradesEntity> list = null;
        //如果查询不到，择返回
        if(totalCount>0){
            //获取数据
            list = iTradeService.queryTradesListByParams(entity,rb);
            //设置分页条数
            JqGridPagesUtils.setPagingParams(page, list, totalCount);
        }
        return page;
    }
    /**
     * 
     * 功能描述： 出入库交易明细
     * @author:sun@mb.com
     * @2015年6月5日  
     * @param:
     * @version
     */
    @ResponseBody
    @RequestMapping(value="/dtltradeslist",method=RequestMethod.POST)
    public JqGridBaseEntityVo<TradesDtlEntity> listTradesDtls(JqGridBaseEntityVo<TradesDtlEntity> page,UserEntity u,HttpServletRequest request,HttpServletResponse response){
        //声明分页
    	String json=request.getParameter("Param");
    	System.out.println(json);
    	TradesEntity entity=JSON.parseObject(json, TradesEntity.class);
    	
        RowBounds rb = new RowBounds((page.getPage()-1)*page.getRows(),page.getRows());
       
        int totalCount = iTradeService.queryTradesDtlListCountByParams(entity);
        List<TradesDtlEntity> list = null;
        //如果查询不到，择返回
        if(totalCount>0){
            //获取数据
            list = iTradeService.queryTradesDtlListByParams(entity, rb);
            //设置分页条数
            JqGridPagesUtils.setPagingParams(page, list, totalCount);
        }
        return page;
    }
   
}
