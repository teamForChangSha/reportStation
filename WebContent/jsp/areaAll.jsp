<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>所有数据</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    所有结果<br/>
    <table border="1">
    	<tr>
    		<td>编号</td>
    		<td>名称</td>
    		<td>级别</td>
    		<td>上一级别</td>
    	</tr>
    	<c:forEach var="list"  items="${areaList}">
    	<tr>
    		<td>${list.areaId}</td>
    		<td>${list.name}</td>
    		<td>${list.level}</td>  
    		<td>${list.parentId}</td>
    		<c:if test="${list.level eq 2}">
    		<td><a href="${pageContext.request.contextPath}/area/getChildArea.do?areaId=${list.areaId}">该省下的市</a></td>
    		</c:if>
    		  	
    		
    	</tr>
    	</c:forEach> 
    </table>
  </body>
</html>
