<%@ page isELIgnored="false" language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%List ms_tableList = (List)request.getSession().getAttribute("ms_tableList"); %>
<%List ms_columnList = (List)request.getSession().getAttribute("ms_columnList"); %>

<script type="text/javascript">
	//此处添加读取数据操作
	var tableArray=new Array();
	var columnArray=new Array();
	<%for(int i=0;i<ms_tableList.size();i++){%>
	<%	Ms_tableVo ms_table = (Ms_tableVo)ms_tableList.get(i); %>
	tableArray[<%=i%>]=new Array("<%=ms_table.getName()%>","<%=ms_table.getDsc()%>");
	<%}%>
	<%for(int i=0;i<ms_columnList.size();i++){%>
	<%	Ms_columnVo ms_column = (Ms_columnVo)ms_columnList.get(i); %>
	columnArray[<%=i%>]=new Array("<%=ms_column.getTname()%>","<%=ms_column.getCname()%>","<%=ms_column.getDsc()%>","<%=ms_column.getType()%>");
	<%}%>
</script>
<script type="text/javascript">
	function sql(){
		var i,s,table="",cdt="",show="";
		var div=document.getElementById("addCdt");
		var t=div.getElementsByTagName("input");
		for(i=0;i<t.length;i++){
			if(t[i].type=="checkbox"&&t[i].checked){
				if(t[i].id!=""&&t[i].name!=""){
					table+=t[i].id+",";
				}
				else if(t[i].name!=""){
					show+=t[i].name+",";
				}
			}
		}
		s=div.getElementsByTagName("select");
		for(i=0;i<s.length;i+=2){
			cdt+=s[i].value+" "+s[i].nextSibling.value+" "+s[i].nextSibling.nextSibling.value+",";
		}
		table=table.substring(0, table.length-1);
		show=show.substring(0, show.length-1);
		cdt=cdt.substring(0, cdt.length-1);
		document.getElementById("table").value=table;
		document.getElementById("show").value=show;
		document.getElementById("cdt").value=cdt;
		
		if(document.getElementById("name").value==''){
  			alert("请输入模板名");
  		}
  		else{
  			if(document.getElementById("dsc").value==''){
  	  			alert("请输入模板描述");
  	  		}
  			else{
  				if(show){
  			  		formAdd.submit();
  			  	}
  			  	else{
  			  		alert("请选择显示项");
  			  	}
  			}
  		}
	}
	function showDetail(obj){
		if(obj.checked){
			var div=document.createElement("div");//新建div
			div.id=obj.id+"div";
			div.innerHTML="<li>"+obj.name+"</li>";
			for(var i=0;i<columnArray.length;i++){
			if(columnArray[i][0]==obj.id){
				div.innerHTML+="<input type='checkbox' checked='checked' name="+columnArray[i][0]+"-"+columnArray[i][1]+">"+columnArray[i][2];
			}
			}
			div.innerHTML+="<br/>";
			div.innerHTML+="<img title='添加条件' onclick=addCdt("+"'"+obj.id+"'"+") src='pic/add.jpg' style='width:14px;height:14px;'>";
			obj.parentNode.appendChild(div);
		}
		else{
			var div=document.getElementById(obj.id+"div");
			div.parentNode.removeChild(div);
		}
	}
	function addCdt(table){
		var div=document.getElementById(table+"div");
		var br=document.createElement("br");
		div.appendChild(br);
		var img=document.createElement("img");
		img.title="删除条件";
		img.setAttribute("onclick", "delCdt(this);");
		img.src="pic/del.jpg";
		img.style.width="14px";
		img.style.height="14px";
		div.appendChild(img);
		
		var inputV=document.createElement("input");
		inputV.type="text";
		div.insertBefore(inputV,img);
		
		var selcdt=document.createElement("select");
		div.insertBefore(selcdt,inputV);
		
		var select=document.createElement("select");
		var option=document.createElement("option");
		option.value="";
		option.innerText="-请选择项目-";
		select.appendChild(option);
		select.setAttribute("onchange", "addAction(this);");
		div.insertBefore(select,selcdt);
		
		addOption(select,table);
	}
	function addOption(select,table){
		for(var i=0;i<columnArray.length;i++){
			if(columnArray[i][0]==table){
				var option=document.createElement("option");
				option.value=columnArray[i][0]+"-"+columnArray[i][1];
				option.innerText=columnArray[i][2];
				select.appendChild(option);
			}
		}
	}
	function delCdt(obj){
		obj.parentNode.removeChild(obj.previousSibling.previousSibling.previousSibling.previousSibling);
		obj.parentNode.removeChild(obj.previousSibling.previousSibling.previousSibling);
		obj.parentNode.removeChild(obj.previousSibling.previousSibling);
		obj.parentNode.removeChild(obj.previousSibling);
		obj.parentNode.removeChild(obj);
	}
	function addAction(select){
		select.nextSibling.length=0;
		if(select.value){
			var ary=select.value.split("-");
			for(var i=0;i<columnArray.length;i++){
			if(columnArray[i][0]==ary[0]&&columnArray[i][1]==ary[1]){
				if(columnArray[i][3]=="value"){
				select.nextSibling.innerHTML="<option value='>'>大于</option><option value='<'>小于</option><option value='='>等于</option>";
				}
				else{
				select.nextSibling.innerHTML="<option value='>'>大于</option><option value='<'>小于</option><option value='='>等于</option><option value='like'>like</option>";
				}
				break;
			}
			}
		}
	}
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
</script>
<div id="headDiv">
<div class="user">
		<c:out value="新增模板"></c:out>
</div>
<form id="formAdd" action="<c:url value="/addMoban.htm"/>" method="post">
	<label>模板名</label><input type="text" name="name" id="name" onblur="check(this,'admin')">
	<label>模板描述</label><input type="text" name="dsc" id="dsc">
	<br/>
	<br/>
	<input type="hidden" id="table" name="table">
	<input type="hidden" id="cdt" name="cdt">
	<input type="hidden" id="show" name="show">
	<input type="hidden" id="id" name="id" value="admin">
	<input type="button" value="增加模板" onclick="sql()" class="moban">
</form>
<div id="addCdt">
请选择要查询的表：
	<c:forEach items="${ms_tableList}" var="t">
		<input type="checkbox" name=<c:out value="${t.dsc}"/> id=<c:out value="${t.name}"/> onchange=showDetail(this)><c:out value="${t.dsc}"/>
	</c:forEach>
</div>
</div>
