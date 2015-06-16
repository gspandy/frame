<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'fixed');
		} catch (e) {
		}
	</script>
	<div class="breadcrumbs" id="breadcrumbs">
		<div class="nav-search" id="nav-search">
			<%-- <form  class="form-search" "> --%>
				<span class="input-icon"> 
				<input type="text" placeholder="输入菜单名称查询" class="nav-search-input" id="nav-search-input" autocomplete="off" onkeydown="queryMenus()"/> 
				<i class="icon-search nav-search-icon"></i>
				</span>
			<%-- </form> --%>
		</div> 
	</div>
	<ul class="nav nav-list">
		<c:forEach items="${sessionScope.USERLOGIN_SESSION.authorityMenus}" var="item" varStatus="status">
	   		<c:choose>  
	        <c:when test="${status.first}">
	            <c:choose>  
			        <c:when test="${item.id eq 9999}">  
			            <li class="active">
			        </c:when>  
			        <c:otherwise>  
			            <li class="start">
			        </c:otherwise>  
			    </c:choose>
	        </c:when>  
	        <c:otherwise>  
	            <c:choose>  
			        <c:when test="${item.id eq requestScope.permissionMenu.parent.id}">  
			            <li class="active open">
			        </c:when> 
			         
			        <c:otherwise>  
			            <li>
			        </c:otherwise>  
			    </c:choose>
	        </c:otherwise>  
	        
	    	</c:choose>
	    		<a href="<c:url value='${item.url}'/>" class="dropdown-toggle">
				<i class='${ empty item.itemIcon?"icon-list": item.itemIcon}'></i>
				<span class="menu-text">${item.name}</span>
				<c:choose>  
			        <c:when test="${item.isLeaf}">  
			        </c:when>  
			        <c:otherwise>  
			           <b class="arrow icon-angle-down"></b>
			        </c:otherwise>  
			    </c:choose>
			</a>
			<c:forEach items="${item.childrens}" var="subItem" varStatus="subStatus">
				<c:if test="${subStatus.first}">
					<ul class="submenu">
				</c:if>
				<c:choose>  
			        <c:when test="${subItem.id eq requestScope.permissionMenu.id}">  
			            <li class="active">
			        </c:when>  
			        <c:otherwise>  
			            <li>
			        </c:otherwise>  
			    </c:choose>
		            	<a href="<c:url value='${ subItem.url}'/>">
		            	<i class="icon-double-angle-right"></i>${subItem.name }</a>
	         		</li>
				<c:if test="${subStatus.last}">
				</ul>
				</c:if>
			</c:forEach>
	   			</li>
	   </c:forEach>
	</ul>
	
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
			data-icon2="icon-double-angle-right"></i>
	</div>
	
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed');}catch(e){}
		
		//查找菜单事件 
		 function queryMenus(event){
			 var e = event || window.event || arguments.callee.caller.arguments[0];
			 var queryParam = $("#nav-search-input")[0].value;
             //回车事件
			 if(e && e.keyCode==13){ 
            	 if(queryParam!=null && queryParam!=""){
            		 if(queryParam.length<=1){
            			 bootBox_Confirm("菜单名称必须大于1个字！");
            			 return false;
            		 }
            		 var  menus =$("ul.nav-list").find("li");
            		 var submenus = $("ul.nav-list").find("ul.submenu");
            		 //重置打开的菜单
            		  for(var j=0;j<submenus.length;j++){
            			$(submenus[j]).css("display","");
            		 } 
            		 //循环匹配菜单
            		 for(var i=0;i<menus.length;i++){
            			 $(menus[i]).removeClass("open");
             			 $(menus[i]).removeClass("active");
            			 var menuText = menus[i].innerHTML;
            			 if(menuText.indexOf(queryParam)>=0){
            				 $(menus[i]).addClass("active");
            			 }
            		 } 
            	 }
            }
		} 
	</script>
</div>