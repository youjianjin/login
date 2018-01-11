/**
 * Created by Administrator on 2017/12/22.
 */
var regchk=[false,false,false,false,false];
$(document).ready(function(){
    var method=$.getUrlParam("method");
    if(method=='login' || method==null){lshow('.login');lhide('.regsuc');}
    if(method=='modsuc'){lshow('.regsuc');lhide('.login');}
    $("#regsucbtn").click(function(){window.location.href="/user/regsuc.json";});
    //
    $("#log_a_person").click(function(){
        var num=$("#log_type_num").val();
        if(num!="1"){$(this).removeClass("log_a_choose");$("#log_a_company").addClass("log_a_choose");$("#log_type_num").val("1");}
    });
    $("#log_a_company").click(function(){
        var num=$("#log_type_num").val();
        if(num!="2"){$(this).removeClass("log_a_choose");$("#log_a_person").addClass("log_a_choose");$("#log_type_num").val("2");}
    });
    //
    $("#lognext").click(function(){
        regUnHide();var reg_un=$("#reg_un").val(),ltype=$("#log_type_num").val();
        var mock=/^1[3|4|5|8][0-9]\d{4,8}$/;
        if(!(mock.test(reg_un))){lshow("#reg_un_1");lerror("#reg_un");regchk[0]=false;}else{
            $(this).attr("disabled",true);
            $.ajax({
                url:'user/checkmobileexist.json',
                data:{gmobile:reg_un,gltype:ltype},
                dataType:'json',
                success:function(data){
                    var map=data;
                    if(map.msg=='ok'){
                        showstep2();
                        setTimeout(function(){$("#lognext").removeAttr("disabled");},1000);
                        regchk[0]=true;
                    }
                    if(map.msg=='error'){
                        lshow("#reg_un_1");lerror("#reg_un");regchk[0]=false;
                        setTimeout(function(){$("#lognext").removeAttr("disabled");},1000);
                    }
                    if(map.msg=='error0'){
                        lshow("#reg_un_3");lerror("#reg_un");regchk[0]=false;
                        setTimeout(function(){$("#lognext").removeAttr("disabled");},1000);
                    }
                }
            });
        }
    });
    $("#logpre").click(function(){
        showstep1();
    });
    /* send ms to aliyun */
    $('#reg_mo_send').click(function(){
        regMoHide();
        checkRegVal($(this));
    });
    $("#logbtn").click(function(){
        ldisabled("#logbtn");
        checkRegMo("#logbtn");
    });
});
function checkRegVal(this1){
    regValHide();var usercode=$("#reg_val").val();
    if(usercode==null || usercode=="" || usercode.length!=4){
        lshow("#reg_val_1");lerror("#reg_val");regchk[1]=false;return false;
    }else{
        $.ajax({
            url:'user/checkRandCode.json',
            data:{usercode:usercode},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=="ok"){lshow("#reg_val_2");lsuccess("#reg_val");regchk[1]=true;sendRegMo(this1);}
                else{lshow("#reg_val_1");lerror("#reg_val");regchk[1]=false;return false;}
            }
        });
    }
}
function sendRegMo(this1){
    var count = 60,climit=count*1000;
    var userun=$("#reg_un").val(),userval=$("#reg_val").val(),grtype=$("#log_type_num").val();
    $.ajax({
        url:'user/sendSMS1.json',
        data:{userun:userun,userval:userval,grtype:grtype},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){
                if(map.tm<climit && map.tm>0){ValCode();lshow("#reg_mo_3");lerror("#reg_mo");}else{map.tm=0;}
                count=60-parseInt(map.tm/1000);
                var resend = setInterval(function(){
                    count--;
                    if(count>0){this1.html(count+"秒后可重新获取").attr('disabled','disabled');}
                    else {clearInterval(resend);this1.html("获取手机验证码").removeAttr('disabled');}
                }, 1000);
                if(map.code=='OK' || map.code=='ok'){}else{lshow("#reg_un_3");lerror("#reg_un");regchk[0]=false;showstep1();}//手机业务问题
            }
            if(map.msg=='error3'){ValCode();regUnHide();lshow("#reg_un_3");lerror("#reg_un");regchk[0]=false;showstep1();}
        }
    });
}
function checkRegMo(str){
    regMoHide();var usermo=$("#reg_mo").val();
    if(usermo==null || usermo=="" || usermo.length!=6){
        lshow("#reg_mo_1");lerror("#reg_mo");regchk[2]=false;lenabled(str);return false;
    }else{
        $.ajax({
            url:'user/checkUserMo.json',
            data:{usermo:usermo},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=='ok'){
                    lshow("#reg_mo_2");lsuccess("#reg_mo");regchk[2]=true;checkRegPwd(str,usermo);
                }else{
                    lshow("#reg_mo_1");lerror("#reg_mo");regchk[2]=false;lenabled(str);return false;
                }
            }
        });
    }
}
function checkRegPwd(str,usermo){
    var gm=$("#reg_un").val(),gl=$("#log_type_num").val();
    regPwdHide();var reg_pwd=$("#reg_pwd").val();
    if(reg_pwd==null || reg_pwd=="" || reg_pwd.length<6){lshow("#reg_pwd_1");lerror("#reg_pwd");regchk[3]=false;lenabled(str);return false;}
    else{
        lshow("#reg_pwd_2");lsuccess("#reg_pwd");regchk[3]=true;
    }
    regRePwdHide();var reg_repwd=$("#reg_repwd").val();
    if(reg_repwd==null || reg_repwd=="" || reg_repwd!=reg_pwd){lshow("#reg_repwd_1");lerror("#reg_repwd");regchk[4]=false;lenabled(str);return false;}
    else{
        lshow("#reg_repwd_2");lsuccess("#reg_repwd");regchk[4]=true;
        if(regchk[0]==true && regchk[1]==true && regchk[2]==true && regchk[3]==true && regchk[4]==true){
            $.ajax({
                url:'user/modifypassword.json',
                data:{gmobile:gm,gltype:gl,gmo:usermo,gpassword:reg_pwd,grepassword:reg_repwd},
                dataType:'json',
                success:function(data){
                    var map=data;
                    if(map.msg=='ok'){window.location.href='/loginOther.jsp?method=modsuc';}
                    if(map.msg=='error'){
                        ValCode();lenabled(str);
                    }
                    if(map.msg=='error1'){//gm
                        ValCode();
                        regMoHide();lshow("#reg_mo_1");lerror("#reg_mo");regchk[2]=false;lenabled(str);
                    }
                }
            });
        }
    }

}
function showstep2(){
    lshow(".login_step2");
    lhide(".login_step1");
}
function showstep1(){
    lshow(".login_step1");
    lhide(".login_step2");
}