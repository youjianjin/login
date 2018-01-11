/**
 * Created by Administrator on 2017/12/25.
 */
$(document).ready(function(){
    $("#btn_edit").click(function(){
        showedit();
        showsuccess("为了让客户对您有更全面的了解，请完善详细信息");
        $(this).hide();
    });
    $("#btn_save").click(function(){
        var gemail=$("#inputemail").val(),demail=$("#inputemail").attr("data-email");
        if(gemail==null || gemail==""){
            showerror("请输入邮箱");$("#inputemail").val(demail);return false;//reset
        }else{
            var mailck = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(!mailck.test(gemail)){
                showerror("邮箱格式错误");return false;
            }else{
                var gjobnum=$("#inputjobnum").val();
                var gqq=$("#inputqq").val();
                $(this).hide();
                modifystaffinfo(gemail,gjobnum,gqq);
            }
        }
    });
    $("#inputmobile,#inputemail,#inputqq,#inputjobnum").blur(function(){
        $("#btn_save").removeClass("hidden");
    });
    getstaffinfo();
});
function modifystaffinfo(gemail,gjobnum,gqq){
    $.ajax({
        url:'staff/modifystaffinfo.json',
        data:{gemail:gemail,gjobnum:gjobnum,gqq:gqq},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showsuccess("修改成功");getstaffinfo();}
            if(map.msg=='error0'){showerror("信息存在错误");}
            if(map.msg=='error1'){logtout();}
            if(map.msg=='error2'){showerror("该邮箱正在使用中");lshow("#btn_save");}
        }
    });
}
function getstaffinfo(){
    $.ajax({
        url:'staff/staffinfo.json',
        data:{},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showstaffinfo(map.ts,map.tsi);}
            if(map.msg=='error0'){showerror("信息存在错误");}
            if(map.msg=='error1'){logtout();}
        }
    });
}
function showstaffinfo(ts,tsi){
    if(ts==null || ts==""){logtout();}else{
        $("#inputemail").val(ts.gemail).attr("data-email",ts.gemail);$("#spanemail").html(shownull(ts.gemail));
    }
    if(tsi==null || tsi==""){
        $("#spanqq").html(shownull(null));
        $("#spanjobnum").html(shownull(null));
    }else{
        $("#inputqq").val(tsi.gqq);$("#spanqq").html(shownull(tsi.gqq));
        $("#inputjobnum").val(tsi.gjobnum);$("#spanjobnum").html(shownull(tsi.gjobnum));
    }
    showsave();
}
//
function showedit(){
    lshow("#inputmobile,#inputemail,#inputqq,#inputjobnum");
    lhide("#spanmobile,#spanemail,#spanqq,#spanjobnum");

}
function showsave(){
    lshow("#spanmobile,#spanemail,#spanqq,#spanjobnum");
    lhide("#inputmobile,#inputemail,#inputqq,#inputjobnum");
}