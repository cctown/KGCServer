<%@ page isELIgnored="false" language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%List queryList = (List)request.getSession().getAttribute("queryList"); %>
<%List mb_showListR = (List)request.getSession().getAttribute("mb_showListR"); %>
<div id="rightDiv">
	<%if(queryList==null||queryList.size()==0) {%>
  	无相关结果 
  	<%}else{ %>
  	<table class="queryT">
  		<tr class="th">
  		<%for(int i=0;i<mb_showListR.size();i++){ %>
  		<%	Mb_showVo mb_show = (Mb_showVo)mb_showListR.get(i); %>
  		<th>
  		<%=mb_show.getCdsc() %>
  		</th>
  		<%} %>
  		</tr>
  		<%for(int i=0;i<queryList.size();i++){ %>
  		<%	if(i%2==0){ %>
  		<tr class="bgc">
  		<%	}else{ %>
  		<tr>
  		<%	} %>
  		<%	Map queryMap = (Map)queryList.get(i); %>
  		<%	for(int j=0;j<mb_showListR.size();j++){ %>
  		<%		Mb_showVo mb_show = (Mb_showVo)mb_showListR.get(j); %>
  		<td>
  		<%=queryMap.get(mb_show.getCname()) %>
  		</td>
  		<%	} %>
  		</tr>
  		<%} %>
  	</table>
  	<form id="pdf" action="<c:url value="/output.pdf"/>" method="post" target="_blank">
  	<input class="moban" type="submit" value="导出PDF报表"/>
  	</form>
  	<form id="excel" action="<c:url value="/output.xls"/>" method="post" target="_blank">
  	<input class="moban" type="submit" value="导出Excel报表"/>
  	</form>
  	<%} %>
 </div>
