<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
</head>
<body>
	
	<p>
	
    <a href="${pageContext.request.contextPath}/area/getAll.do">点击获取所有区域</a>
   <p>
	
	<a href="${pageContext.request.contextPath}/area/getChildArea.do?name=China">点击获取国内所有省份</a>
	
	  <p>
	
	<a href="${pageContext.request.contextPath}/company/getBranches.do?companyId=1">获取大唐公司信息</a>


</body>
</html>