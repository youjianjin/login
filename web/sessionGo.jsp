<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
</head>
<body>
<%
    //登录后再访问登录页面时
    //不为空就调用登录检测
    Object obj=request.getSession().getAttribute("loginer");
    if(obj==null){

    }else{
        out.print("<script type='text/javascript'>window.location.href='/main.jsp';</script>");
    }
%>
</body>
</html>
