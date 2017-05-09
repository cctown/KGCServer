<%@ page isELIgnored="false" language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
<!--
function check(obj,idv){
  	$.ajax( {  
        type : "post",  
        url : "checkName.htm",
        data : {mbName : obj.value,
        		id : idv},
        success : function(msg) {  
            if(msg!="0"){
            	alert("模板名已经存在！");
            	document.getElementById("name").value="";
            }
        }  
    });  
  }
function sql(obj){
  	var i,j,s,t,b,table="",cdt="",show="";
  	var d=document.getElementsByTagName("div");
  	for(i=0;i<d.length;i++){
  		if(d[i].className=="query"){
  			b=d[i].getElementsByTagName("li");
  			s=d[i].getElementsByTagName("select");
  			t=d[i].getElementsByTagName("input");
  			for(j=0;j<b.length;j++){
  				table+=b[j].id+",";
  			}
  			for(j=0;j<s.length;j++){
  				cdt+=s[j].name+" "+s[j].value+" "+t[j].value+",";
  			}
  			for(j=0;j<t.length;j++){
  				if(t[j].type=="checkbox"&&t[j].checked){
  					show+=t[j].name+",";
  				}
  			}
  		}
  	}
  	table=table.substring(0, table.length-1);
  	cdt=cdt.substring(0, cdt.length-1);
  	if(show){
		show=show.substring(0, show.length-1);
	}
  	document.getElementById("table").value=table;
  	document.getElementById("cdt").value=cdt;
  	document.getElementById("show").value=show;
  	if(obj.value=="保存模板"){
  		if(document.getElementById("name").value==''){
  			alert("请输入模板名");
  		}
  		else{
  			if(document.getElementById("dsc").value==''){
  	  			alert("请输入模板描述");
  	  		}
  			else{
  				if(show){
  					formQuery.action="addMoban.htm";
  			  		formQuery.submit();
  			  	}
  			  	else{
  			  		alert("请选择显示项");
  			  	}
  			}
  		}
  	}
  	else{
  		if(show){
  	  		formQuery.submit();
  	  	}
  	  	else{
  	  		alert("请选择显示项");
  	  	}
  	}
}
//-->
</script>
  <div id="headDiv">
  	<c:if test="${!empty moban}">
  	<div class="user">◆  <c:out value="${moban.dsc}"/>
  	</div>
  	<c:forEach items="${mb_tableList}" var="t">
				<div class="query">
				<li id="<c:out value="${t.tname}"/>">
				<c:out value="${t.tdsc}"/>
				</li>
		 		<c:forEach items="${mb_cdtList}" var="c">
					 <c:if test="${t.tname==c.tname }">
					 	<span class="cname"><c:out value="${c.cdsc}"/></span>
					 	<select id="<c:out value="${c.tname}"/>-<c:out value="${c.cname}"/>" name="<c:out value="${c.tname}"/>-<c:out value="${c.cname}"/>">
					 	<c:choose>
					 	<c:when test="${c.type=='value'}">
					 	<option value=">">大于</option>
					 	<option value="<">小于</option>
					 	<option value="=">等于</option>
					 	</c:when>
					 	<c:otherwise>
					 	<option value=">">大于</option>
					 	<option value="<">小于</option>
					 	<option value="=">等于</option>
					 	<option value="like">like</option>
					 	</c:otherwise>
					 	</c:choose>
					 	</select>
					 	<script type="text/javascript">
					 	setSelect("<c:out value="${c.tname}"/>-<c:out value="${c.cname}"/>","<c:out value="${c.cdt}" escapeXml="false"/>");
					 	</script>
					 	<c:choose>
					 	<c:when test="${c.type=='value'}">
					 	<input type="text" value=<c:out value="${c.value}"/> onchange="isNum(this)">
					 	</c:when>
					 	<c:otherwise>
					 	<input type="text" value=<c:out value="${c.value}"/>>
					 	</c:otherwise>
					 	</c:choose>
					 	<img title="删除条件" src="pic/del.jpg" style="width:14px;height:14px" onclick="delCdt(this)"/>
					 	<br/>
					</c:if>
				</c:forEach>
				<c:forEach items="${mb_showList}" var="s">
					<c:if test="${t.tname==s.tname }">
						<c:set var="tag" value="0" scope="session"/>
						<c:forEach items="${mb_showListR}" var="r">
							<c:if test="${t.tname==r.tname }">
								<c:if test="${s.cname==r.cname }">
									<c:set var="tag" value="1" scope="session"/>
								</c:if>
							</c:if>
						</c:forEach>
						<c:choose>
						<c:when test="${tag==1 }">
							<input type="checkbox" checked="checked" name=<c:out value="${s.tname}"/>-<c:out value="${s.cname}"/>><c:out value="${s.cdsc}"/>
						</c:when>
						<c:otherwise>
							<input type="checkbox" name=<c:out value="${s.tname}"/>-<c:out value="${s.cname}"/>><c:out value="${s.cdsc}"/>
						</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				</div>
	</c:forEach>
			<form id="formQuery" action="<c:url value="/query.htm"/>" method="post">
			<input type="hidden" size="100" id="table" name="table" value="">
			<input type="hidden" size="100" id="cdt" name="cdt" value="">
			<input type="hidden" size="100" id="show" name="show" value="">
			<input type="button" onclick="sql(this)" value="开始查询" class="moban"><br/>
			<label>模板名</label>
			<c:if test="${user.role != 'A' }">
			<input type="text" name="name" id="name" onblur="check(this,'${user.id}')">
			</c:if>
			<c:if test="${user.role != 'N' }">
			<input type="text" name="name" id="name" onblur="check(this,'admin')">
			</c:if>
			<label>模板描述</label><input type="text" name="dsc" id="dsc">
			<c:if test="${user.role != 'A' }">
			<input type="hidden" id="id" name="id" value="${user.id}">
			</c:if>
			<c:if test="${user.role != 'N' }">
			<input type="hidden" id="id" name="id" value="admin">
			</c:if>
			<input type="button" onclick="sql(this)" value="保存模板" class="moban">
			</form>
		</c:if>
	</div>
