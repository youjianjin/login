<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
</head>
<body>
<%
    //1监测是否登录2监测权限3检测是否停用
    Object obj=request.getSession().getAttribute("loginer");
    if(obj==null){

    }else{

    }
%>
</body>
</html>
