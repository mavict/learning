<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'search.jsp' starting page</title>
    
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
     <form action="${pageContext.request.contextPath}/goodsAction_get.action" method="post">
     	关键字:<input type="text" name="goods.gname" />
        <input type="submit" value="搜索" />
     </form>
     <c:forEach items="${requestScope.goodsList}" var="goods">
     	商品编号:${goods.gid}<br/>
     	商品名称:${goods.gname}<br/>
     	图片:<img src="image/${goods.gpic}" /> <br/>
     	商品价格:${goods.gprice}<br/>
     	简单介绍:${goods.gremark}<br/>
     	<a href="${pageContext.request.contextPath}/goodsAction_get2.action?goods.gid=${goods.gid}">详细</a> <br/>
     </c:forEach>
  </body>
</html>
