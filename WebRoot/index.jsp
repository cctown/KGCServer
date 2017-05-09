<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录系统</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<script type="text/javascript">
	function registry(){
	if(document.getElementById("id").value==""){
		alert("账号不能为空！");
	}
	else{
		if(document.getElementById("id").value=="admin"){
			alert("此账号不能使用！");
		}
		else{
			if(document.getElementById("password").value==""){
				alert("密码不能为空！");
			}
			else{
				form1.action="registry.htm";
				form1.submit();
			}
		}
	}
	}
	</script>
  </head>
  
  <body>
    <form id="form1" action="<c:url value="/loginCheck.htm"/>" method="post">
    <table width="100%" height="100%">
    	<tr valign="middle" align="center">
   	 		<td valign="middle">
   	 		<table width="300" height="93" border="0" align="center">
   	 			<c:if test="${!empty error}">
   	 			<tr>
   	 				<td colspan="2" align="center">
   	 				<P><font color="red"><c:out value="${error}"/></font></P>
   	 				</td>
   	 			</tr>
   	 			</c:if>
   	 			<c:if test="${!empty msg}">
   	 			<tr>
   	 				<td colspan="2" align="center">
   	 				<P><font color="blue"><c:out value="${msg}"/></font></P>
   	 				</td>
   	 			</tr>
   	 			</c:if>
   	 			<tr>
   	 				<td width="30%" valign="middle" height="30">账号：</td>
   	 				<td align="left" height="27" width="70%">
   	 					<input type="text" name="id" id="id" size="20" maxlength="20"
   	 					onmouseover="this.focus();"/>
   	 				</td>
   	 			</tr>
   	 			<tr>
   	 				<td width="30%" valign="middle" height="30">密码：</td>
   	 				<td align="left" height="27" width="70%">
   	 					<input type="password" name="password" id="password" size="20" maxlength="20"
   	 					onmouseover="this.focus();"/>
   	 				</td>
   	 			</tr>
   	 			<tr>
   	 				<td colspan="2" valign="middle" align="center" height="30">
   	 				<table>
   	 				<tr>
   	 				<td><input type="submit" value="登录" /></td>
   	 				<td><input type="button" value="注册" onclick="registry()"/></td>
   	 				</tr>
   	 				</table>
   	 			</td>
   	 		</tr>
   	 		</table>
   	 		</td>
   	 		</tr>
    </table>
    </form>
  </body>
</html>