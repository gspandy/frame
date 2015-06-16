<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="../assets/js/jquery-2.0.3.min.js"></script>
<script src="../assets/js/ace-extra.min.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/typeahead-bs2.min.js"></script>
<script src="../assets/js/ace-elements.min.js"></script>
<script src="../assets/js/ace.min.js"></script>
<script src="../assets/alert/bootbox.min.js"></script>
<script type="text/javascript">
	$(function(){  
	    // 设置jQuery Ajax全局的参数  
	    $.ajaxSetup({  
	        type: "POST", 
	        contentType:"application/x-www-form-urlencoded;charset=utf-8",   
	        cache:false ,   
	        complete:function(xhr,textStatus){ 
	        	var errorStatus = xhr.getResponseHeader("errorStatus");
	        	 switch (errorStatus){  
	                case('500'):  
	                	bootBox_Confirm("服务器系统内部错误!");
	                    break;  
	                case('401'): 
	                	bootBox_Confirm("由于您长时间没有操作, session已过期, 请刷新页面!");
	                    break;  
	                case('403'):
	                	bootBox_Confirm("对不起，您没有操作求权限!");
	                    break;  
	                case('408'): 
	                	bootBox_Confirm("请求超时!");
	                    break;  
	            } 
	        } 
	    });  
	});  
	
	/*统一异常弹出方法*/
	function bootBox_Confirm(errorMessage){
		bootbox.dialog({
    		title:'温馨提示',
    		message:errorMessage,
    		size: 'small',
    		buttons:{
    		confirm: {  
                label: '确认',
                className:'btn btn-success'
            },  
            cancel: {  
                label: '取消',  
                className: 'btn btn-warning'
            }}
    	});  
	};
	
	//判断是否为空
	function Ext_isEmpty(v){
	    return v === null || v === undefined || v===''||(Ext_isArray(v) && !v.length);
	}
	//判断是否为数组
	function Ext_isArray(obj) {    
		return Object.prototype.toString.call(obj) === '[object Array]';     
	}
</script>
