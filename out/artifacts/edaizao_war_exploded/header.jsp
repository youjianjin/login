<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".i-user").click(function(){
                var statu=$(this).attr("data-hide");
                if(statu=='show'){
                    $(this).children().eq(1).attr("class","icon-caret-down");
                    $(".i-menu").hide();
                    $(this).attr("data-hide","hide");
                }else{
                    $(this).children().eq(1).attr("class","icon-caret-up");
                    $(".i-menu").show();
                    $(this).attr("data-hide","show");
                }
            }).blur(function(){
                var statu=$(".i-user").attr("data-hide");
                if(statu=='show'){$(this).children().eq(1).attr("class","icon-caret-down");$(".i-menu").hide();$(".i-user").attr("data-hide","hide");}
            });
        });
    </script>
</head>
<style type="text/css">
    .head{width:100%;height:60px;background-color: #0073ae;position: relative;}
    .headerlist a{display:block;width: 100px;height: 100%;margin: 0 auto;color: #fff;}
    .bannerimg1{height: 100px;margin-left: 30%;float: left;}
    .headerc{width:90%;height:60px;margin: 0 auto;display:flex;flex-direction: row;justify-content: space-between;align-items: center;}
    .headerlogo{height:40px;}
    .headerlist{display:flex;flex-direction: row;justify-content: space-around;align-items: center;font-size: 16px;}
    .headerlogo img{height:40px;width:auto;}
    .headerlist div{width:160px;text-align: center;height:60px;line-height:60px;box-sizing: border-box;-webkit-box-sizing: border-box;}
    .headerr{width:160px;height:60px;align-items: center;display: flex;color: #fff;}
    .msg{width:60px;height:42px;font-size: 30px;}
    .msg:hover{text-decoration: none;}
    .msg_icon{float: left;}
    .msg_num{width: 20px;height: 20px;font-size: 10px;float: left;background-color: #d43f3a;text-align: center;line-height: 20px;-webkit-border-radius: 50%;border-radius: 50%;margin-left: -5px;}
    .i-user{font-size: 24px;height: 60px;display: flex;align-items: center;width: 40px;}
    .headeruser{position: relative;width:40px;height: 60px;}
    .i-menu{width:140px;background-color: #fff;float: left;color: #000;border: 1px solid #ccc;padding-top: 6px;padding-bottom: 10px;margin-top: -10px;box-shadow: 0 2px 4px rgba(0,0,0,0.2);-webkit-box-shadow: 0 2px 4px rgba(0,0,0,0.2);display: none;}
    .i-menu>div{width:100%;height:26px;padding-top: 3px;padding-bottom: 3px;padding-left: 5px;line-height: 20px;}
    .i-menu>div:hover{background-color: #428bca;}
    .i-menu i{margin-left: 10px;margin-right: 15px;}
    .i-divider{width: 100%;border-bottom: 1px solid #ccc;height: 1px;margin-top: 8px;margin-bottom: 8px;display: block;}
    .i-menu:before{content: '';float: left;width: 10px;height:10px;background-color: #fff;border-left: 1px solid #ccc;border-top:1px solid #ccc;margin-top: -12px;margin-left: 5px;transform: rotate(45deg);}
</style>
<body>
<!-- header -->
<div class="head">
    <div class="headerc">
        <div class="headerlogo" style="color:#FFF;"><a href="#">管理系统</a></div>
        <div class="headerlist">
            <div><a href="#">项目库</a></div>
            <div><a href="#">文档库</a></div>
            <div><a href="#">问题</a></div>
            <div><a href="#">帮助</a></div>
        </div>
        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="grey">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-tasks"></i>
                        <span class="badge badge-grey">4</span>
                    </a>
                    <ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header"><i class="icon-ok"></i> 4 个任务进行中</li>
                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">Bug验证</span>
                                    <span class="pull-right">90%</span>
                                </div>
                                <div class="progress progress-mini progress-striped active">
                                    <div style="width:90%" class="progress-bar progress-bar-success"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#"> 查看全部任务<i class="icon-arrow-right"></i></a>
                        </li>
                    </ul>
                </li>
                <li class="purple">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-bell-alt icon-animated-bell"></i>
                        <span class="badge badge-important">8</span>
                    </a>
                    <ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header">
                            <i class="icon-warning-sign"></i> 8 个重要提醒
                        </li>
                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left"><i class="btn btn-xs no-hover btn-pink icon-comment"></i>新的订单生成</span>
                                    <span class="pull-right badge badge-info">+12</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#"> 查看全部重要提醒<i class="icon-arrow-right"></i></a>
                        </li>
                    </ul>
                </li>
                <li class="green">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-envelope icon-animated-vertical"></i><span class="badge badge-success">5</span>
                    </a>
                    <ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                        <li class="dropdown-header"><i class="icon-envelope-alt"></i> 5 个消息</li>
                        <li>
                            <a href="#">
                                <img src="assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
                                <span class="msg-body">
                                        <span class="msg-title"><span class="blue">Alex:</span>项目完成了 ...</span>
                                        <span class="msg-time"><i class="icon-time"></i><span>a moment ago</span></span>
                                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="inbox.html">查看全部消息<i class="icon-arrow-right"></i></a>
                        </li>
                    </ul>
                </li>

                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="assets/avatars/avatar4.png" alt="You" />
                        <span class="user-info"><small>欢迎,</small>${sessionScope.loginer.gmobile}</span>
                        <i class="icon-caret-down"></i>
                    </a>
                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <c:choose>
                                <c:when test="${sessionScope.loginer.gltype=='1'}">
                                    <a href="#"><i class="icon-user"></i>个人信息</a>
                                </c:when>
                                <c:when test="${sessionScope.loginer.gltype=='2'}">
                                    <c:choose>
                                        <c:when test="${sessionScope.loginer.glev1=='3'}">
                                            <a href="/sInfo.jsp"><i class="icon-user"></i>员工信息</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/cInfo.jsp"><i class="icon-user"></i>公司信息</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                        </li>
                        <li class="divider"></li><li>
                        <a href="/user/logout.json"><i class="icon-off"></i>退出</a>
                    </li>
                    </ul>
                </li>
            </ul><!-- /.ace-nav -->
        </div><!-- /.navbar-header -->

    </div>
</div>
</body>
</html>
