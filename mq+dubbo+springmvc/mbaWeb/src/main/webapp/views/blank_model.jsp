<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<head>
<meta charset="utf-8" />  
<title>账务中心后台管理系统</title>

<!-- 依赖jquery库 此处jquery一定要放前面-->
<link rel="stylesheet" href="../assets/css/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="../assets/css/datepicker.css" />
<link rel="stylesheet" href="../assets/css/ui.jqgrid.css" />

<jsp:include page="shared/importCss.jsp" />
<jsp:include page="shared/importJs.jsp" />
<!-- 引入jqGrid -->
<script src="../assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<script src="../assets/js/jqGrid/i18n/grid.locale-en.js"></script>
<!-- 引入数据字典类 -->
<script type="text/javascript" src="../js/dataDictionaryUtils.js"></script>
</head>

<body onload="">
	<!-- 引入页面头部 -->
	<jsp:include page="shared/pageHeader.jsp" />
	
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
</body>
</html>
