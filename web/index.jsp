<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="管理系统">
	<meta name="description" content="管理系统">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>BootStrap</title>
<style type="text/css">

</style>
</head>
<body>
<%
	//判定是否登录
	Object obj=request.getSession().getAttribute("loginer");
	if(obj==null){
		out.print("<script type='text/javascript'>window.location.href='/login.jsp';</script>");
	}else{
		out.print("<script type='text/javascript'>window.location.href='/user/logsuc.json';</script>");
	}
%>
</body>
</html>