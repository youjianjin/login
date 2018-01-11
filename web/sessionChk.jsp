<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
</head>
<body>
<%
    //页面没登录时的访问情况
    //空了就登录
    Object obj=request.getSession().getAttribute("loginer");
    if(obj==null){
        out.print("<script type='text/javascript'>window.location.href='/login.jsp?method=logtout';</script>");
    }else{

    }
%>
</body>
</html>
