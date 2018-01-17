<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/sessionGo.jsp"></jsp:include>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<meta name="keywords" content="管理系统">
	<meta name="description" content="管理系统。">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>BootStrap</title>
	<link href="/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/css/ace.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/css/index.css" rel="stylesheet" type="text/css">
	<link href="/assets/css/login.css" rel="stylesheet" type="text/css">
	<script src="/assets/js/jquery-2.0.3.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="/assets/js/html5shiv.js"></script>
	<script src="/assets/js/respond.min.js"></script>
	<![endif]-->
    <script src="/assets/js/common.js"></script>
    <script src="/assets/js/login.js"></script>
    <script src="/assets/js/register.js"></script>


</head>
<body>
	<div class="c">
        <div class="banner" style="color:#FFF;">
            管理系统
            <div class="bannerr">
            </div>
        </div>
        <input type="hidden" id="log_type_num" value="1">
        <!-------------------------- login -------------------------->
        <div class="login login-box">
            <div class="login_step1">
                <div class="div_log_50 text-center bigger-140 blue">
                    <span class="pull-left col-sm-6"><div id="log_a_person"><i class="icon-ok"></i> 个人登录</div></span>
                    <div class="div_log_divider"></div>
                    <span class="pull-right col-sm-6"><div id="log_a_company" class="log_a_choose"><i class="icon-ok"></i> 员工登录</div></span>
                </div>
                <div class="div_log_30">
                    <span class="input-icon"><input type="text"  id="log_un" maxlength="20" placeholder="请输入手机号码"><i class="icon-phone"></i></span>
                </div>
                <div class="div_log_30">
                    <span class="input-icon">
                        <span id="log_un_1"><i class="icon-exclamation-sign"></i>请输入正确的11位手机号码</span>
                        <span id="log_un_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="log_un_3"><i class="icon-remove"></i>账号或密码错误</span>
                    </span>
                </div>
                <div class="div_log_30">
                    <span class="input-icon"><input type="password" id="log_pwd" maxlength="32" placeholder="请输入密码"><i class="icon-lock"></i></span>
                </div>
                <div class="div_log_30">
                    <span class="input-icon">
                        <span id="log_pwd_1"><i class="icon-exclamation-sign"></i>密码的长度应该在6位以上</span>
                        <span id="log_pwd_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="log_pwd_3"><i class="icon-remove"></i>密码错误</span>
                    </span>
                </div>
                <div class="div_log_30">
                    <span class="input-icon">
                        <input type="text" maxlength="4" placeholder="请输入验证码"  id="log_val"/><i class="icon-leaf"></i>
                        <a href="javascript:void(0);" onclick="ValCode()"><img id="randCodeImage1" src="ValCode/generate.json"/></a>
                    </span>
                </div>
                <div class="div_log_50">
                    <span class="input-icon">
                        <span id="log_val_1"><i class="icon-remove"></i>验证码错误</span>
                        <span id="log_val_2"><i class="icon-ok"></i>验证码正确</span>
                    </span>
                </div>
                <div class="div_log_30"><button id="logbtn" class="btn btn-primary btn-sm"><i class="icon-key"></i>登录</button></div>
            </div>
            <div class="login_step3"><a href="javascript:" class="pull-left">忘记密码</a><a href="login.jsp?method=register" class="pull-right">注册用户</a></div>
            <div class="login_step4"><div class="dotline"></div></div>
        </div>
        <input type="hidden" id="reg_type_num" value="1">
        <!-------------------------- register -------------------------->
        <div class="register login-box">
            <div class="login_step1" style="display:none2;">
                <div class="div_log_50 text-center bigger-140 blue">
                    <span class="pull-left col-sm-6"><div id="reg_a_person"><i class="icon-ok"></i> 个人注册</div></span>
                    <div class="div_log_divider"></div>
                    <span class="pull-right col-sm-6"><div id="reg_a_company" class="log_a_choose"><i class="icon-ok"></i> 企业注册</div></span>
                </div>
                <div class="div_reg_name">
                    <div class="div_reg_40">
                        <span class="input-icon"><input type="text"  id="reg_name" maxlength="32" placeholder="请输入真实姓名"><i class="icon-user"></i></span>
                        <span class="input-icon">
                            <span id="reg_name_1"><i class="icon-exclamation-sign"></i>请输入真实姓名</span>
                            <span id="reg_name_2"><i class="icon-ok"></i>填写正确</span>
                            <span id="reg_name_3"><i class="icon-remove"></i>--</span>
                        </span>
                    </div>
                </div>
                <div class="div_reg_com">
                    <div class="div_reg_40">
                        <span class="input-icon"><input type="text"  id="reg_com" maxlength="32" placeholder="请输入企业名称"><i class="icon-group"></i></span>
                        <span class="input-icon">
                            <span id="reg_com_1"><i class="icon-exclamation-sign"></i>公司名称长度在2~32之间</span>
					        <span id="reg_com_2"><i class="icon-ok"></i>填写正确</span>
					        <span id="reg_com_3"><i class="icon-remove"></i>公司名称被注册</span>
                        </span>
                    </div>
                </div>
                <div class="div_reg_40">
                    <span class="input-icon"><input type="text"  id="reg_un" maxlength="20" placeholder="请输入手机号码"><i class="icon-phone"></i></span>
                    <span class="input-icon">
                        <span id="reg_un_1"><i class="icon-exclamation-sign"></i>请输入正确的11位手机号码</span>
                        <span id="reg_un_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="reg_un_3"><i class="icon-remove"></i>手机号已被注册</span>
                        <span id="reg_un_4"><i class="icon-remove"></i>手机账号有业务问题</span>
                    </span>
                </div>
                <div class="div_reg_40">
                    <span class="input-icon"><input type="text"  id="reg_mail" maxlength="32" placeholder="请输入邮箱"><i class="icon-envelope"></i></span>
                    <span class="input-icon">
                        <span id="reg_mail_1"><i class="icon-exclamation-sign"></i>邮箱格式错误</span>
					    <span id="reg_mail_2"><i class="icon-ok"></i>填写正确</span>
					    <span id="reg_mail_3"><i class="icon-remove"></i>邮箱已被注册</span>
                    </span>
                </div>
                <div class="div_reg_40">
					<span class="input-icon">
						<input type="text" maxlength="4" placeholder="请输入验证码"  id="reg_val"/><i class="icon-leaf"></i>
						<span><a href="javascript:void(0);" onclick="ValCode()"><img id="randCodeImage2" src="ValCode/generate.json"/></a></span>
					</span>
                    <span class="input-icon">
                        <span id="reg_val_1"><i class="icon-remove"></i>验证码错误</span>
                        <span id="reg_val_2"><i class="icon-ok"></i>验证码正确</span>
                    </span>
                </div>
                <div class="div_reg_40" style="display:none;">
					<span class="input-icon">
						<input type="text" maxlength="6" placeholder="请输入手机验证码"  id="reg_mo"/><i class="icon-comment-alt"></i>
						<button id="reg_mo_send" class="btn btn-xs btn-danger btn_mo">获取手机验证码</button>
					</span>
                    <span class="input-icon">
                        <span id="reg_mo_1"><i class="icon-remove"></i>手机验证码错误</span>
                        <span id="reg_mo_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="reg_mo_3"><i class="icon-remove"></i>操作频繁，请稍后再试！</span>
                    </span>
                </div>
                <div class="div_reg_40">
                    <span class="input-icon"><input type="password" id="reg_pwd" maxlength="32" placeholder="请输入密码"><i class="icon-lock"></i></span>
                    <span class="input-icon">
                        <span id="reg_pwd_1"><i class="icon-exclamation-sign"></i>密码的长度应该在6位以上</span>
                        <span id="reg_pwd_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="reg_pwd_3"><i class="icon-remove"></i>--</span>
                    </span>
                </div>
                <div class="div_reg_40">
                    <span class="input-icon"><input type="password" id="reg_repwd" maxlength="32" placeholder="请输入确认密码"><i class="icon-lock"></i></span>
                    <span class="input-icon">
                        <span id="reg_repwd_1"><i class="icon-exclamation-sign"></i>确认密码和密码不一致</span>
                        <span id="reg_repwd_2"><i class="icon-ok"></i>填写正确</span>
                        <span id="reg_repwd_3"><i class="icon-remove"></i>--</span>
                    </span>
                </div>
                <div class="div_reg_50">
                    <span class="input-icon">
                        <input type="checkbox" id="reg_chk" class="ace" checked="checked"><span class="lbl"></span>我已阅读并同意<a href="#">管理系统服务条款</a>
                    </span>
                    <span class="input-icon">
                        <span id="reg_chk_1"><i class="icon-exclamation-sign"></i>必须同意管理系统服务条款</span>
                    </span>
                </div>
                <div class="div_log_30"><button id="regbtn" class="btn btn-primary btn-sm"><i class="icon-key"></i>注册</button></div>
            </div>
            <div class="login_step3"><a href="javascript:" class="pull-left">关于我们</a><a href="login.jsp?method=login" class="pull-right">登录用户</a></div>
            <div class="login_step4"><div class="dotline"></div></div>
        </div>
        <!-------------------------- register div -------------------------->
        <div class="regsuc login-box">
            <div class="login_step1">
                <div class="div_reg_50">
                    <h2>恭喜，注册成功</h2>
                </div>
                <div class="div_reg_50">
                    <span>为了让客户对您有更全面的了解，接下来需要完善个人信息</span>
                </div>
                <div class="div_reg_50">
                    <button id="regsucbtn" class="btn btn-primary btn-sm"><i class="icon-edit"></i>完善信息</button>
                </div>
            </div>
        </div>
        <!-------------------------- login timeout -------------------------->
        <div class="logtout login-box">
            <div class="login_step1">
                <div class="div_reg_50">
                    <h2>登录超时</h2>
                </div>
                <div class="div_reg_50">
                    <span>登录时间太久了，为了您的账户安全，请重新登录</span>
                </div>
                <div class="div_reg_50">
                    <button id="logtoutbtn" class="btn btn-primary btn-sm"><i class="icon-key"></i>马上登录</button>
                </div>
            </div>
        </div>
        <!-------------------------- logout -------------------------->
        <div class="logout login-box">
            <div class="login_step1">
                <div class="div_reg_50">
                    <h2>退出</h2>
                </div>
                <div class="div_reg_50">
                    <span>退出系统后，您的账户信息安全了...</span>
                </div>
                <div class="div_reg_50">
                    <button id="logoutbtn" class="btn btn-primary btn-sm"><i class="icon-key"></i>重新登录</button>
                </div>
            </div>
        </div>
        <!-------------------------- islock -------------------------->
        <div class="loglock login-box">
            <div class="login_step1">
                <div class="div_reg_50">
                    <h2>停用</h2>
                </div>
                <div class="div_reg_50">
                    <span>该账号已被停用，请切换账号登录...</span>
                </div>
                <div class="div_reg_50">
                    <button id="loglockbtn" class="btn btn-primary btn-sm"><i class="icon-key"></i>切换账号</button>
                </div>
            </div>
        </div>
	</div>
</body>
</html>