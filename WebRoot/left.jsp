<%@ page  isELIgnored="false" language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <script type="text/javascript">
  	function showMoban(obj,id){
  		document.getElementById("showMobanName").value=obj.innerText;
  		document.getElementById("showMobanId").value=id;
  		document.getElementById("showMoban").submit();
  	}
  	function delMoban(obj,id){
  		document.getElementById("delMobanName").value=obj;
  		document.getElementById("delMobanId").value=id;
  		document.getElementById("delMoban").submit();
  	}
  </script>
  <div id="leftDiv">
  
  <div class="user">
  	<c:choose>
  	<c:when test="${user.role!='A' }"><img src="pic/user.png" width="32px" height="32px" title="普通用户" /></c:when>
  	<c:otherwise><img src="pic/admin.jpg" width="32px" height="32px" title="系统管理员" /></c:otherwise>
  	</c:choose>
  	<c:out value="${user.id}"/>
  </div>
  
  <ul id="browser" class="filetree">
		<li><span class="folder">系统模板</span>
			<ul>
				<c:forEach items="${mobanList}" var="l">
					<c:if test="${l.id=='admin' }">
					<li>
						<span style="cursor:pointer;" class="file" onclick="showMoban(this,'admin')" title="<c:out value="${l.dsc}"/>"><c:out value="${l.name}"/></span>
						<c:if test="${user.role=='A' }">
						<img style="cursor:pointer;" src="pic/del.jpg" width="14px" height="14px" onclick="delMoban('${l.name}','admin')" title="删除${l.name}模板"/>
						</c:if>
					</li>
					</c:if>
				</c:forEach>
			</ul>
		</li>
		<c:if test="${user.role!='A' }">
		<li><span class="folder">自定义模板</span>
			<ul>
				<c:forEach items="${mobanList}" var="l">
					<c:set var="tag" value="${user.id }" scope="session"/>
					<c:if test="${l.id==tag}">
					<li>
						<span style="cursor:pointer;" class="file" onclick="showMoban(this,'${user.id}')" title="<c:out value="${l.dsc}"/>"><c:out value="${l.name}"/></span>
						<img style="cursor:pointer;" src="pic/del.jpg" width="14px" height="14px" onclick="delMoban('${l.name}','${user.id}')" title="删除${l.name}模板"/>
					</li>
					</c:if>
				</c:forEach>
			</ul>
		</li>
		</c:if>
  </ul>
  <div class="icon">
  <c:if test="${user.role=='A' }">
  <form id="addMoban" action="<c:url value="/editNewMoban.htm"/>" method="post"></form>
  <form id="userMgr" action="<c:url value="/userMgr.htm"/>" method="post"></form>
  <img style="cursor:pointer;" src="pic/addmb.jpg" width="64px" height="64px" onclick="addMoban.submit()" title="新增模板"/>
  <img style="cursor:pointer;" src="pic/userMgr.png" width="64px" height="64px" onclick="userMgr.submit()" title="用户管理"/>
  </c:if>
  
  <form id="exit" action="<c:url value="/logoff.htm"/>" method="post"></form>
  <img style="cursor:pointer;" src="pic/logout.png" width="64px" height="64px" onclick="exit.submit()" title="退出账号"/>
  </div>
  
  <form id="showMoban" action="<c:url value="/showMoban.htm"/>" method="post">
  	<input id="showMobanName" name="mobanName" type="hidden" value=""/>
  	<input id="showMobanId" name="mobanId" type="hidden" />
  </form>
  <form id="delMoban" action="<c:url value="/delMoban.htm"/>" method="post">
  	<input id="delMobanName" name="mobanName" type="hidden" value=""/>
  	<input id="delMobanId" name="mobanId" type="hidden" />
  </form>
  </div>
