<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>通用查询</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="css/jquery.treeview.css" />
    <script src="lib/jquery.js" type="text/javascript"></script>
	<script src="lib/jquery.cookie.js" type="text/javascript"></script>
	<script src="lib/jquery.treeview.js" type="text/javascript"></script>
	<script type="text/javascript" src="lib/demo.js"></script>
	<script type="text/javascript" src="./common.js"></script>
  </head>
  <body>
  <div id="main">
  <%@ include file="left.jsp" %>
  <%@ include file="head.jsp" %>
  <%@ include file="right.jsp" %>
  </div>
  </body>
  <script type="text/javascript">
  	<%String r = (String)request.getAttribute("right");%>
  	var r = <%=r%>;
  	if(r!=null){
  		document.getElementById("rightDiv").style.display="";
  	}
  	else{
  		document.getElementById("rightDiv").style.display="none";
  	}
  </script>
</html>
