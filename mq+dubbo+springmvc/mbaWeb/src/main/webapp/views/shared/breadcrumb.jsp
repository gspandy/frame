<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<!-- 菜单级数 -->
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i> <a
			href="${pageContext.request.contextPath}/login/main">首页</a></li>
		<c:if test="${not empty requestScope.permissionMenu.parent.name}">
			<li><a href="#">${requestScope.permissionMenu.parent.name}</a></li>
		</c:if>
		<li class="active"><a href="#">${requestScope.permissionMenu.name}</a></li>
	</ul>
	<!-- 菜单查询 -->
	<!-- <div class="nav-search" id="nav-search">
		<form class="form-search" onkeydown="qu">
			<span class="input-icon"> <input type="text"
				placeholder="Search ..." class="nav-search-input"
				id="nav-search-input" autocomplete="off" /> <i
				class="icon-search nav-search-icon"></i>
			</span>
		</form>
	</div> -->
</div>