<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detail.jsp' starting page</title>
    
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
     	��Ʒ���:${goods.gid}<br/>
     	��Ʒ����:${goods.gname}<br/>
     	ͼƬ:<img src="image/${goods.gpic}" /> <br/>
     	��Ʒ�۸�:${goods.gprice}<br/>
     	�򵥽���:${goods.gremark}<br/>
     	��ϸ����:${goods.gxremark}<br/>
  </body>
</html>
