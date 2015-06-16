<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8" />
<title>账务中心后台管理系统</title>

<!-- 依赖jquery库 此处jquery一定要放前面-->
<link rel="stylesheet" href="../assets/css/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="../assets/css/datepicker.css" />
<link rel="stylesheet" href="../assets/css/ui.jqgrid.css" />

<jsp:include page="shared/importCss.jsp"/>
<jsp:include page="shared/importJs.jsp"/>

<script src="../assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="../assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="../assets/js/jqGrid/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="../js/dataDictionaryUtils.js"></script>
</head>

<body onload="getDictionaryData([{'codeType':'deal_Type','rendToId':'jiaoyifangshi',includeType:['1','2'],defaultValue:'',all:true}])">
	<!-- 引入页面头部 -->
	<jsp:include page="shared/pageHeader.jsp" />
	<!-- ============================主框架========================= -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>
			
			<!-- +++++++++++++++++++菜单列表 +++++++++++++++++++++++++++-->
			<jsp:include page="shared/menu.jsp"></jsp:include>

			<div class="main-content">
				<jsp:include page="shared/breadcrumb.jsp"></jsp:include>
				<div class="page-content">
					<form class="form-horizontal" role="form" id="searchform">
						<fieldset>
							<legend>查询条件</legend>
							<input id="docNum" name="docNum" type="hidden"/>
							<div class="form-group">
								<label class="col-md-1 control-label">起始时间</label>
								<div class="col-md-3 input-group">
									<input id="createTime" type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd">
									<span class="input-group-addon">
										<i class="icon-calendar bigger-110"></i>
									</span>
								</div>
							
								<label class="col-md-1 control-label">结束时间</label>
								<div class="col-md-3 input-group">
									<input id="modifyTime" type="text" class="form-control date-picker" data-date-format="yyyy-mm-dd">
									<span class="input-group-addon">
										<i class="icon-calendar bigger-110"></i>
									</span>
								</div>
								
								<label class="col-md-1 control-label">供货方</label>
								<div class="col-md-3" id="goodsp">
									<input id="venderCode" type="text" class="form-control" placeholder="">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-1 control-label">单据类型</label>
								<div class="col-md-3">
									<select class="form-control" id="docType">
										<option value="IN">入库单</option>
										<option value="OUT">出库单</option>
										<option value="3">收款单</option>
										<option value="4">付款单</option>
									</select>
								</div>
								
								<label class="col-md-1 control-label">交易方式</label>
								<div class="col-md-3">
									<select class="form-control" id="inoutwarehCode">
									    <option value="">--选择--</option>
									</select>
								</div>
							
								<label class="col-md-1 control-label">接收方</label>
								<div class="col-md-3">
									<input id="vendeeCode" type="text" class="form-control" placeholder="">
								</div>
							</div>
							
							<div class="col-md-offset-4 col-md-8">
								<div class="col-md-1">
<!-- 									<button type="button" class="btn btn-info" onclick="queryTransaction()"> -->
										<button id="btnSearch" name="btnSearch" type="button" class="btn btn-info">
										<i class="icon-ok bigger-110"></i>
										查询
									</button>
								</div>
								<div class="col-md-offset-2 col-md-3">
									<button type="reset" class="btn btn-default ">
										<i class="icon-undo bigger-110"></i>
										重置
									</button>
								</div>
							</div>
						</fieldset>
					</form>
					
					<div class="row">
						<div class="space-6"></div>
						<div class="col-xs-12">
							<table id="grid-table"></table>
							<div id="grid-pager"></div>

							<script type="text/javascript">
								var $path_base = "/";
							</script>
						</div>
					</div>
				</div>
			</div>
			
			<!--自定义皮肤地区  -->
			<jsp:include page="shared/customizeTools.jsp"></jsp:include>
		
		</div>
		
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>


	<!-- javascripts -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='../assets/js/jquery-2.0.3.min.js'>"
								+ "<"+"/script>");
	</script>
	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='../assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>


	<script type="text/javascript">
			//jquery 日期控件
			jQuery(function($) {
					$('.date-picker').datepicker({autoclose:true}).next().on(ace.click_event, function(){
						$(this).prev().focus();
					});
			});
			//showDetail
			function showDetail(docNum){
				var url="${pageContext.request.contextPath}/trades/dtltrades";
				var form=$("#searchform");
				if(docNum!='')form.find("#docNum").val(docNum);
				form.attr("action",url);
				form.submit();
			}
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-pager";
				
				jQuery(grid_selector).jqGrid({
					url:'${pageContext.request.contextPath}/trades/tradeslist',
					datatype: 'local',//当要加载数据的时候把datatype改成json或者XML: 
					mtype:'POST',
					altRows:true,
					postData: {    
						'userName':'zhangsan王',
						'password':'1234'   
				    }, 
					height: 250,
					emptyrecords:true,
					colNames:['单据编号','单据类型','单据日期','供货方','发货仓库','接收方','接收仓库','方式','原始单据类别','原始单据编号','配货方式','实际发货方','实际发货仓','传入SAP','操作'],
					colModel:[						
						{name:'docNum',index:'docNum', width:60, sortable:false,sorttype:"int", editable: false},
						{name:'docType',index:'docType', width:60, sortable:false,sorttype:"int", editable: false},
						{name:'docDate',index:'docDate',width:90, sortable:false,editable:true, sorttype:"date",formatter:'date',formatoptions:{srcformat:'u', newformat:'Y-m-d'},unformat: pickDate},
						{name:'venderCode',index:'venderCode', width:90,sortable:false,editable: false},
						{name:'venderWarehCode',index:'venderWarehCode', width:70, sortable:false,editable: false},
						{name:'vendeeCode',index:'vendeeCode', width:90, sortable:false,editable: false},
						{name:'vendeeWarehCode',index:'vendeeWarehCode', width:90, sortable:false,editable: false},
						{name:'inoutwarehCode',index:'inoutwarehCode', width:60, sortable:false,editable: false},				
						{name:'srcDocCode',index:'srcDocCode', width:60, sortable:false,editable: false},
						{name:'srcDocNum',index:'srcDocNum', width:60, sortable:false,editable: false},
						{name:'delivCode',index:'delivCode', width:60, sortable:false, editable: false},
						{name:'relTraderCode',index:'relTraderCode', width:60, sortable:false,editable: false},
						{name:'relTradeWarehCode',index:'relTradeWarehCode', width:60, sortable:false,editable: false},
						{name:'tosap',index:'tosap', width:60, sortable:false,editable: false},
						{name:'act',title:'详情', width:80, fixed:true, sortable:false, resize:false,
						formatter:function(cellvalue,options,rawObject) {return '<a href="#" onclick="showDetail(\''+ rawObject.docNum + '\')">详情</a>'}}
					], 
					jsonReader : {  
					    root: "gridModel",   // json中代表实际模型数据的入口  
					    page: "page",   // json中代表当前页码的数据  
					    total: "total", // json中代表页码总数的数据  
					    records: "records", // json中代表数据行总数的数据  
					    repeatitems: true, // 如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。  
					} ,
					viewrecords:true, //是否显示行数 
					rowNum:10,
					rowList:[10,20,30],
					pager : pager_selector,
					multiselect: true,
			        multiboxonly: true,
			        rownumbers: true,  
			        loadtext: "数据加载中...",  
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							//updateActionIcons(table);//更新操作列的样式
							updatePagerIcons(table);
							enableTooltips(table); 
						}, 0); 
					},
					editurl:"${pageContext.request.contextPath}/trades/tradesedit",
					caption: "交易列表",
					autowidth: true,
					//只有当点击复选框时，才客户选择
					beforeSelectRow: function (rowid, e) { 
					    var $myGrid = $(this), 
					        i = $.jgrid.getCellIndex($(e.target).closest('td')[0]), 
					        cm = $myGrid.jqGrid('getGridParam','colModel'); 
					    return (cm[i].name ==='cb'); 
					}
				});
				
				
				//switch element when editing inline
				function aceSwitch( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=checkbox]')
								.wrap('<label class="inline" />')
							.addClass('ace ace-switch ace-switch-5')
							.after('<span class="lbl"></span>');
					}, 0);
				}
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
								.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}
				
				//navButtons
				 jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						edit: true,
						editicon : 'icon-pencil blue',
						add: true,
						addicon : 'icon-plus-sign purple',
						del: true,
						delicon : 'icon-trash red',
						search: true,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: true,
						viewicon : 'icon-zoom-in grey',
					},
					{	
						editCaption:"Edit 页面", 
						closeAfterEdit:true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
							style_edit_form(form);
						},
						afterSubmit:function(response,postdata){
							var result= response.responseText;  
		                    if(result!=null && !result.status){  
		                        return [false,result.message] ;  
		                    }else if(result.status){  
		                    	$(this).jqGrid('setRowData',postdata.id,postdata,{ color : "#FF0000"});
		                    }  
						}
					},
					{
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						reloadAfterSubmit:false,//不重新加载数据，在成功后重新加载数据
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
							style_edit_form(form);
						},
						afterSubmit:function(response,postdata){
							var result= response.responseText;  
		                    if(result!=null && !result.status){  
		                        return [false,result.message] ;  
		                    }else if(result.status){  
		                    	$(this).trigger('reloadGrid');
		                    }  
						}
					},
					{
						//delete record form
						recreateForm: true,
						reloadAfterSubmit:false,//不重新加载数据，在成功后重新加载数据
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
							style_delete_form(form);
							
							form.data('styled', true);
						},
						afterSubmit:function(response,postdata){
							var result= response.responseText;  
							
		                    if(result!=null && !result.status){  
		                        return [false,result.message] ;  
		                    }else if(result.status){  
		                    	$(this).trigger('reloadGrid');
		                    }  
						}
					},
					{
						//search form
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this));
						}
						,
						multipleSearch: true,
						
					},
					{
						//view record form
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />');
						}
					}
				);
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
						.end().find('input[name=stock]')
							  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>');
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').remove();
					buttons.eq(0).append('<i class="icon-chevron-left"></i>');
					buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>');
				}
				
				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable');
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />');
					style_edit_form(form);
				}
			
				/*此处是用来更改样式的，更改操作列的样式*/
				/* function updateActionIcons(table) {
					var replacement = 
					{
						'ui-icon-pencil' : 'icon-pencil blue',
						'ui-icon-trash' : 'icon-trash red',
						'ui-icon-disk' : 'icon-ok green',
						'ui-icon-cancel' : 'icon-remove red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					});
					
				} */
				
				//分页的样式
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
						'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
						'ui-icon-seek-next' : 'icon-angle-right bigger-140',
						'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					});
				}
				//分页的样式
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			});
		</script>
	<div style="display: none">
		<script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540'
			language='JavaScript' charset='gb2312'></script>
	</div>
	
	
	<script type="text/javascript">
		//重置grid的大小	
		$(function(){
	        $(window).resize(function(){   
	     		$("#grid-table").setGridWidth($(window).width());
	   		 });
	   });
		$("#btnSearch").click(function () {
			 var rules = "";
			  $("#searchform input[id]").each(function (i) { 
			　　　　　　　　if ($(this).val()) {
			　　　　　　　　　　rules += "'" + $(this).attr("id") + "':'" + $(this).val() + "'";
			　　　　　　　　}
			　　　　});
			   $("#searchform select[id]").each(function (i) { 
			　　　　　　　　//console.log($(this).attr("id") + "':'" + $(this).val() + "'");
					if ($(this).val()) {
				　　　　　　rules += "'" + $(this).attr("id") + "':'" + $(this).val() + "'";
				　　　 }
			　　　　});
		　　　　  //var rules = "'createTime':'"+$("#starttime").val()+"','modifyTime':'"+$("#endtime").val()+"','docType':'"+$("#doctype").val()+"','inoutwarehCode':'"+$("#tradetype").val()+"','vendeeCode':'"+$("#venndee").val()+"','venderCode':'"+$("#vennder").val()+"'";
		  	   ParamJson = '{' + rules + '}';
	       	   var postData = $("#grid-table").jqGrid("getGridParam", "postData");
	           $.extend(postData, { Param: ParamJson });
		       $("#grid-table").jqGrid("setGridParam", {search: true,datatype:'json'}).trigger("reloadGrid");  //重载JQGrid
		　　});
		
		var queryTransaction = function(){
			//动态查询后台数据，load到前台表格中
			/* 方法一:
				$.ajax({
				url:"${pageContext.request.contextPath}/transaction/queryTransaction",
				type:'post',
				dataType:'json',
				timeout:1000*60,
				data:{'userName':'zhangsan王','password':'1234'},
				success:function(data, textStatus){
					if(data!=null){
						$("#grid-table").clearGridData();
						$("#grid-table").jqGrid('setGridParam',{
							dataType:'local',
							data:data
						}).trigger("reloadGrid");
					}
					
				}
			}); */
			
			/*方法2：*/
			$("#grid-table").jqGrid('setGridParam',{
				datatype:'json'
			}).trigger("reloadGrid");
		};
	</script>
</body>
</html>