/**
 * Created by Administrator on 2017/12/19.
 */
$(document).ready(function(){
    getunlockstaff();
    $("#btn_edit").click(function(){
        showedit();
        showsuccess("设置公司管理员后，点击保存按钮即可完成");
        $(this).hide();
    });
    $("#inputm2").change(function(){
        $("#btn_save").removeClass("hidden");
    });
    $("#btn_save").click(function(){
        var oid=$("#inputm2 option:selected").val();
        if(oid==null || oid==""){showerror("请选择公司管理员");return false;}
        else{
            $(this).hide();
            $.ajax({
                url:'company/modifystafflev.json',
                data:{soid:oid},
                dataType:'json',
                success:function(data){
                    var map=data;
                    if(map.msg=='ok'){showsuccess("更新管理员成功");getunlockstaff();}
                    if(map.msg=='error0'){showerror("提交存在错误");}
                    if(map.msg=='error1'){logtout();}
                }
            });
        }
    });
});
function getunlockstaff(){
    $.ajax({
        url:'company/securestafflist.json',
        data:{},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=="ok"){
                showcompanyinfo(map.list);
            }
            if(map.msg=='error0'){
                showerror("当前信息存在错误！");
            }
            if(map.msg=='error1'){logtout();}
        }
    });
}
function showcompanyinfo(list){
    if(list==null || list==""){
        $("#spanm2").html("").html(shownull(""));
    }else{
        var shtml="<option value='0'>--- 未设置 ---</option>",num=0;
        $.each(list,function(i){
            if(list[i][2]=='2'){
                shtml+="<option value='"+list[i][0]+"' selected='selected'>"+list[i][1]+"</option>";
                num+=1;
                $("#spanm2").html("").html(list[i][1]);
            }
            if(list[i][2]=='3'){
                shtml+="<option value='"+list[i][0]+"'>"+list[i][1]+"</option>";
            }
        })
        $("#inputm2").html("").html(shtml);
        if(num<1){$("#spanm2").html("").html(shownull(""));}
    }
    showsave();
}
function showedit(){
    lshow("#inputm1,#inputm2");
    lhide("#spanm1,#spanm2");

}
function showsave(){
    lshow("#spanm1,#spanm2");
    lhide("#inputm1,#inputm2");
}