<%@ page isELIgnored="false" language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
<!--
	function delUser(userId){
		document.getElementById("delUserId").value=userId;
		document.getElementById("delUser").submit();
	}
	function toUser(userId){
		document.getElementById("toUserId").value=userId;
		document.getElementById("toUser").submit();
	}
	function toAdmin(userId){
		document.getElementById("toAdminId").value=userId;
		document.getElementById("toAdmin").submit();
	}
//-->
</script>
<div id="headDiv">
	<div class="user">
		<c:out value="用户管理"></c:out>
	</div>
	
	<c:forEach items="${userList}" var="l">
	<c:if test="${l.role=='A' }">
	<c:if test="${l.id!=user.id }">
		<div class="userMgr" style="cursor:pointer;">
		<div class="userName">
			<img src="pic/admin.jpg" width="32px" height="32px" title="系统管理员" />
			<c:out value="${l.id }"></c:out>
		</div>
		<img title="删除用户" src="pic/del.jpg" width=24px height=24px onclick="delUser('${l.id }')"/>
		<input style="cursor:pointer;" type="button" value="更改为普通用户" class="moban" title="更改为普通用户" onclick="toUser('${l.id }')" />
		</div>
	</c:if>
	</c:if>
	</c:forEach>
	
	<c:forEach items="${userList}" var="l">
	<c:if test="${l.role=='N' }">
		<div class="userMgr" style="cursor:pointer;">
		<div class="userName">
			<img src="pic/user.png" width="32px" height="32px" title="普通用户"/>
			<c:out value="${l.id }"></c:out>
		</div>
		<img title="删除用户" src="pic/del.jpg" width=24px height=24px onclick="delUser('${l.id }')"/>
		<input style="cursor:pointer;" type="button" value="更改为管理员" class="moban" title="更改为管理员" onclick="toAdmin('${l.id }')" />
		</div>
	</c:if>
	</c:forEach>
	
	<form id="delUser" action="<c:url value="/delUser.htm"/>" method="post">
  	<input id="delUserId" name="userId" type="hidden" />
  	</form>
  	<form id="toUser" action="<c:url value="/toUser.htm"/>" method="post">
  	<input id="toUserId" name="userId" type="hidden" />
  	</form>
  	<form id="toAdmin" action="<c:url value="/toAdmin.htm"/>" method="post">
  	<input id="toAdminId" name="userId" type="hidden" />
  	</form>
</div>
