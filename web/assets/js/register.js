/**
 * Created by Administrator on 2017/12/13.
 */
var regchk=[false,false,false,false,false,false,false,false,true];
regchk[5]=true;
$(document).ready(function(){
    //register
    $("#reg_name").blur(function(){checkRegName();});
    $("#reg_com").blur(function(){checkRegCom();});
    $("#reg_un").blur(function(){checkRegUn();});
    $("#reg_mail").blur(function(){checkRegMail();});
    $("#reg_val").blur(function(){checkRegVal();});
    $("#reg_mo").blur(function(){checkRegMo();});
    $("#reg_pwd").blur(function(){checkRegPwd();});
    $("#reg_repwd").blur(function(){checkRegRePwd();});
    $("#reg_chk").click(function(){checkRegChk();});
    $("#regbtn").click(function(){
       // alert("0"+regchk[0]+"1"+regchk[1]+"2"+regchk[2]+"3"+regchk[3]+"4"+regchk[4]+"5"+regchk[5]+"6"+regchk[6]+"7"+regchk[7]+"8"+regchk[8]);
        var regtype=$("#reg_type_num").val();
        if(regchk[2]==true && regchk[3]==true && regchk[4]==true && regchk[5]==true && regchk[6]==true && regchk[7]==true && regchk[8]==true){
            if(regtype=='1'){
                if(regchk[0]==true){
                    //alert("个人通过");
                    register($("#reg_name").val(),"",$("#reg_un").val(),$("#reg_mail").val(),$("#reg_pwd").val(),regtype);
                }else{checkRegName();}
            }else{
                if(regchk[1]==true){
                    //alert("企业通过");
                    register("",$("#reg_com").val(),$("#reg_un").val(),$("#reg_mail").val(),$("#reg_pwd").val(),regtype);
                }else{checkRegName();}
            }
        }else{
            if(regtype=='1'){checkRegName();}else{checkRegCom();}
            checkRegUn();checkRegMail();checkRegVal();checkRegMo();checkRegPwd();checkRegRePwd();checkRegChk();
        }
    });

    $("#reg_a_person").click(function(){
        var num=$("#reg_type_num").val();
        if(num!="1"){$(this).removeClass("log_a_choose");$("#reg_a_company").addClass("log_a_choose");lshow(".div_reg_name");lhide(".div_reg_com");$("#reg_type_num").val("1");}
    });
    $("#reg_a_company").click(function(){
        var num=$("#reg_type_num").val();
        if(num!="2"){$(this).removeClass("log_a_choose");$("#reg_a_person").addClass("log_a_choose");lshow(".div_reg_com");lhide(".div_reg_name");$("#reg_type_num").val("2");}
    });
    /* send ms to aliyun */
    $('#reg_mo_send').click(function(){
        regMoHide();
        if(regchk[2] && regchk[4]){
            var btn = $(this);
            var count = 60,climit=count*1000;
            var userun=$("#reg_un").val(),userval=$("#reg_val").val(),grtype=$("#reg_type_num").val();
            $.ajax({
                url:'user/sendSMS.json',
                data:{userun:userun,userval:userval,grtype:grtype},
                dataType:'json',
                success:function(data){
                    var map=data;
                    if(map.msg=='ok'){
                        if(map.tm<climit && map.tm>0){ValCode();lshow("#reg_mo_3");lerror("#reg_mo");}else{map.tm=0;}
                        count=60-parseInt(map.tm/1000);
                        var resend = setInterval(function(){
                            count--;
                            if(count>0){btn.html(count+"秒后可重新获取").attr('disabled','disabled');}
                            else {clearInterval(resend);btn.html("获取手机验证码").removeAttr('disabled');}
                        }, 1000);
                        if(map.code=='OK' || map.code=='ok'){}else{regUnHide();lshow("#reg_un_4");lerror("#reg_un");regchk[2]=false;}//手机业务问题
                    }
                    if(map.msg=='error3'){ValCode();regUnHide();lshow("#reg_un_3");lerror("#reg_un");regchk[2]=false;}
                }
            });
        }else{checkRegUn();checkRegVal();}
    });
});
function register(gu,gc,gm,ge,gp,gr){
    $.ajax({
        url:'user/register.json',
        data:{gusername:gu,gcompany:gc,gmobile:gm,gemail:ge,gpassword:gp,grtype:gr},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){window.location.href='/login.jsp?method=regsuc';}
            if(map.msg=='error'){
                ValCode();
            }
            if(map.msg=='error1'){//gm
                ValCode();
                checkRegUn();
            }
            if(map.msg=='error2'){//ge
                ValCode();
                checkRegMail();
            }
            if(map.msg=='error3'){//gc
                ValCode();
                checkRegName();
                checkRegCom();
            }
        }
    });
}
function checkRegName(){
    regNameHide();var reg_name=$("#reg_name").val();
    if(reg_name==null || reg_name==""){lshow("#reg_name_1");lerror("#reg_name");regchk[0]=false;}else{lshow("#reg_name_2");lsuccess("#reg_name");regchk[0]=true;}
}
function checkRegCom(){
    regComHide();var reg_com=$("#reg_com").val();
    if(reg_com==null || reg_com=="" || reg_com.length<2){lshow("#reg_com_1");lerror("#reg_com");regchk[1]=false;}
    else{
        $.ajax({
            url:'user/checkCompanyName.json',
            data:{gcompany:reg_com},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=="ok"){lshow("#reg_com_2");lsuccess("#reg_com");regchk[1]=true;}
                else if(map.msg=="error1"){lshow("#reg_com_3");lerror("#reg_com");regchk[1]=false;}
                else{lshow("#reg_com_1");lerror("#reg_com");regchk[1]=false;}
            }
        });
    }
}
function checkRegUn(){
    regUnHide();var reg_un=$("#reg_un").val();
    var mock=/^1[3|4|5|8][0-9]\d{4,8}$/;
    if(!(mock.test(reg_un))){lshow("#reg_un_1");lerror("#reg_un");regchk[2]=false;}else{lshow("#reg_un_2");lsuccess("#reg_un");regchk[2]=true;}
}
function checkRegMail(){
    regMailHide();var reg_mail=$("#reg_mail").val();
    if(reg_mail==null || reg_mail==""){
        lshow("#reg_mail_1");lerror("#reg_mail");regchk[3]=false;
    }else{
        var mailck = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!mailck.test(reg_mail)){
            lshow("#reg_mail_1");lerror("#reg_mail");regchk[3]=false;
        }else{
            $.ajax({
                url:'user/checkEmail.json',
                data:{gemail:reg_mail},
                dataType:'json',
                success:function(data){
                    var map=data;
                    if(map.msg=="ok"){lshow("#reg_mail_2");lsuccess("#reg_mail");regchk[3]=true;}
                    else{lshow("#reg_mail_3");lerror("#reg_mail");regchk[3]=false;}
                }
            });

        }
    }
}
function checkRegVal(){
    regValHide();var usercode=$("#reg_val").val();
    if(usercode==null || usercode=="" || usercode.length!=4){
        lshow("#reg_val_1");lerror("#reg_val");regchk[4]=false;
    }else{
        $.ajax({
            url:'user/checkRandCode.json',
            data:{usercode:usercode},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=="ok"){lshow("#reg_val_2");lsuccess("#reg_val");regchk[4]=true;}
                else{lshow("#reg_val_1");lerror("#reg_val");regchk[4]=false;}
            }
        });
    }
}
function checkRegMo(){
    regMoHide();var usermo=$("#reg_mo").val();
    if(usermo==null || usermo=="" || usermo.length!=6){
        lshow("#reg_mo_1");lerror("#reg_mo");regchk[5]=false;
    }else{
        $.ajax({
            url:'user/checkUserMo.json',
            data:{usermo:usermo},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=='ok'){
                    lshow("#reg_mo_2");lsuccess("#reg_mo");regchk[5]=true;
                }else{
                    //lshow("#reg_mo_1");lerror("#reg_mo");regchk[5]=false;
                    lshow("#reg_mo_2");lsuccess("#reg_mo");regchk[5]=true;
                }
            }
        });
    }
}
function checkRegPwd(){
    regPwdHide();var reg_pwd=$("#reg_pwd").val();
    if(reg_pwd==null || reg_pwd=="" || reg_pwd.length<6){lshow("#reg_pwd_1");lerror("#reg_pwd");regchk[6]=false;}else{lshow("#reg_pwd_2");lsuccess("#reg_pwd");regchk[6]=true;}
}
function checkRegRePwd(){
    regRePwdHide();var reg_repwd=$("#reg_repwd").val(),reg_pwd=$("#reg_pwd").val();
    if(reg_repwd==null || reg_repwd=="" || reg_repwd!=reg_pwd){lshow("#reg_repwd_1");lerror("#reg_repwd");regchk[7]=false;}else{lshow("#reg_repwd_2");lsuccess("#reg_repwd");regchk[7]=true;}
}
function checkRegChk(){
    regChkHide();var ischeck=$("#reg_chk").get(0).checked;
    if(ischeck){regchk[8]=true;$("#regbtn").removeAttr('disabled');}else{lshow("#reg_chk_1");regchk[8]=false;$("#regbtn").attr('disabled',true);}
}
//
function regNameHide(){lhide("#reg_name_1,#reg_name_2,#reg_name_3");}
function regComHide(){lhide("#reg_com_1,#reg_com_2,#reg_com_3");}
function regUnHide(){lhide("#reg_un_1,#reg_un_2,#reg_un_3,#reg_un_4");}
function regMailHide(){lhide("#reg_mail_1,#reg_mail_2,#reg_mail_3");}
function regValHide(){lhide("#reg_val_1,#reg_val_2");}
function regMoHide(){lhide("#reg_mo_1,#reg_mo_2,#reg_mo_3");}
function regPwdHide(){lhide("#reg_pwd_1,#reg_pwd_2,#reg_pwd_3");}
function regRePwdHide(){lhide("#reg_repwd_1,#reg_repwd_2,#reg_repwd_3");}
function regChkHide(){lhide("#reg_chk_1");}