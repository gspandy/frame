package mb.mba.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import mb.mba.bean.TransactionBean;
import mb.mba.service.ITransactionService;
import mb.mba.util.JqGridPagesUtils;
import mb.mba.vo.JqGridBaseEntityVo;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @功能： 交易查询
 * @author    Administrator
 * @version   
 * @see       
 * @since
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {
    
    @Resource
    public ITransactionService transactionService;
   
   /**
    * 交易查询页面
    * @return
    */
    @RequestMapping("/index")
    public String index(){
        return "queryTransaction";
    }
    
    /**
     * 功能描述：查询出入库交易
     * 作者：毛建强
     * 2015年5月14日
     */
    @ResponseBody
    @RequestMapping(value="/queryTransaction",method=RequestMethod.POST)
    public JqGridBaseEntityVo<TransactionBean> queryTransaction(JqGridBaseEntityVo<TransactionBean> entity){
        //声明分页
        RowBounds rb = new RowBounds((entity.getPage()-1)*entity.getRows(),entity.getRows());
       
        TransactionBean dto = new TransactionBean();
        int totalCount = transactionService.queryListByParamsCount(dto);
        List<TransactionBean> list = null;
        //如果查询不到，择返回
        if(totalCount>0){
            //获取数据
            list = transactionService.queryListByParams(dto,rb);
            //设置分页条数
            JqGridPagesUtils.setPagingParams(entity, list, totalCount);
        }
        return entity;
    }
    
    /**
     * 功能描述：新增，修改，删除
     * 作者：毛建强
     * 2015年5月18日
     */
    @ResponseBody
    @RequestMapping(value="editTransaction",method=RequestMethod.POST)
   public Map<String,Object> edit(String oper,HttpServletResponse response){
       Map<String,Object> map = new HashMap<String,Object>();
       String errorMessage = "";
       if(oper.equals("edit")){
           map.put("status",true);
           map.put("message", null);
       }else{
           map.put("status",false);
           map.put("message", errorMessage+"后台执行异常！");
       }
       return map;
   }
    
}
