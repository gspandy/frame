package mb.mba.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mb.mba.bean.DataDictionaryBean;
import mb.mba.entity.DataDictionary;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dataDictionary")
public class DataDictionaryController {
    
    /**
     * 功能描述：获取数据字典
     * 作者：毛建强
     * 2015年5月26日
     */
    @RequestMapping(value="onLoadGetDictionary",method=RequestMethod.POST)
    @ResponseBody
    public List<DataDictionaryBean> getDictionary(@RequestBody List<DataDictionaryBean> dicList){
        //如果没传递，择返回空
        if(CollectionUtils.isEmpty(dicList)){
            return null;
        }
        
        Map<String,Object> map = new HashMap<String, Object>();
        //循环获取vo
        for(DataDictionaryBean dto:dicList){
              //根据vo的codetype去缓存中获取对应的数据字典
            List<DataDictionary> list = new ArrayList<DataDictionary>();
            DataDictionary data1= new DataDictionary();
            data1.setCode("1");
            data1.setName("寄售采购入库");
            DataDictionary data2= new DataDictionary();
            data2.setCode("2");
            data2.setName("寄售采购退货");
            DataDictionary data3= new DataDictionary();
            data3.setCode("3");
            data3.setName("寄售配发出库");
            list.add(data1);
            list.add(data2);
            list.add(data3);
            
            //如果有传递要获取的值
            if(CollectionUtils.isNotEmpty(dto.getIncludeType()) && dto.getIncludeType().size()>0){
                List<String> includeTypes =dto.getIncludeType();
                List<DataDictionary> errorList = new ArrayList<DataDictionary>();
                for(DataDictionary entity:list){
                    if(!includeTypes.contains(entity.getCode())){
                        errorList.add(entity);
                    }
                }
                if(errorList.size()>0){
                    list =  (List<DataDictionary>) CollectionUtils.removeAll(list, errorList);
                }
            }
            //是否 加全部
            if(dto.getAll()!=null && dto.getAll()){
                DataDictionary defaultData= new DataDictionary();
                defaultData.setCode(null);
                defaultData.setName("全部");
                list.add(0,defaultData);
            }
            
            dto.setList(list);
        }
        
       /* for(int i=0;i<codeNames.length; i++){
            List<DataDictionary> list = new ArrayList<DataDictionary>();
            DataDictionary data1= new DataDictionary();
            data1.setCode("1");
            data1.setName("寄售采购入库");
            DataDictionary data2= new DataDictionary();
            data2.setCode("2");
            data2.setName("寄售采购退货");
            
            DataDictionary data3= new DataDictionary();
            data3.setCode("3");
            data3.setName("寄售配发出库");
            list.add(data1);
            list.add(data2);
            list.add(data3);
            map.put(codeNames[i], list);
        }*/
        return dicList;
    } 
    
}
