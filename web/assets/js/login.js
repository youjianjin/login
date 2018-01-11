/**
 * Created by Administrator on 2017/12/13.
 */
var logchk=[false,false,false];
$(document).ready(function(){
    //login
    $("#log_un").blur(function(){checkLogUn();});
    $("#log_pwd").blur(function(){checkLogPwd();});
    $("#log_val").blur(function(){checkLogVal();});
    $("#logbtn").click(function(){
        if(logchk[0]==true && logchk[1]==true && logchk[2]==true){
            login($("#log_un").val(),$("#log_pwd").val(),$("#log_val").val(),$("#log_type_num").val());
        }else{
            checkLogUn();checkLogPwd();checkLogVal();
        }
    });
    $("#log_a_person").click(function(){
        var num=$("#log_type_num").val();
        if(num!="1"){$(this).removeClass("log_a_choose");$("#log_a_company").addClass("log_a_choose");$("#log_type_num").val("1");}
    });
    $("#log_a_company").click(function(){
        var num=$("#log_type_num").val();
        if(num!="2"){$(this).removeClass("log_a_choose");$("#log_a_person").addClass("log_a_choose");$("#log_type_num").val("2");}
    });
});
//
function login(username,userpwd,usercode,ltype){
    var rand=Math.random();
    $.ajax({
        url:'user/login.json?params='+rand,
        data:{gmobile:username,gpassword:userpwd,gcode:usercode,gltype:ltype},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){
                window.location.href="/main.jsp";
            }else if(map.msg=='error2'){//gun gpwd error
                logUnHide();logPwHide();lshow("#log_un_3");lerror("#log_un");lerror("#log_pwd");logchk[0]=false;logchk[1]=false;ValCode();
            }else if(map.msg=='errorlock'){
                loglock();
            }
        }
    });
}
function checkLogUn(){
    logUnHide();var log_un=$("#log_un").val();
    var mock=/^1[3|4|5|8][0-9]\d{4,8}$/;
    if(!(mock.test(log_un))){lshow("#log_un_1");lerror("#log_un");logchk[0]=false;}else{lshow("#log_un_2");lsuccess("#log_un");logchk[0]=true;}
}
function checkLogPwd(){
    logPwHide();var log_pwd=$("#log_pwd").val();
    if(log_pwd==null || log_pwd=="" || log_pwd.length<6){lshow("#log_pwd_1");lerror("#log_pwd");logchk[1]=false;}else{lshow("#log_pwd_2");lsuccess("#log_pwd");logchk[1]=true;}
}
function checkLogVal(){
    logValHide();var usercode=$("#log_val").val();
    if(usercode==null || usercode=="" || usercode.length!=4){
        lshow("#log_val_1");lerror("#log_val");logchk[2]=false;
    }else{
        $.ajax({
            url:'user/checkRandCode.json',
            data:{usercode:usercode},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=="ok"){
                    lshow("#log_val_2");lsuccess("#log_val");logchk[2]=true;
                }else{
                    lshow("#log_val_1");lerror("#log_val");logchk[2]=false;
                }
            }
        });
    }
}
function logUnHide(){lhide("#log_un_1,#log_un_2,#log_un_3");}
function logPwHide(){lhide("#log_pwd_1,#log_pwd_2,#log_pwd_3");}
function logValHide(){lhide("#log_val_1,#log_val_2");}