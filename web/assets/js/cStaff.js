/**
 * Created by Administrator on 2017/12/19.
 */
var deptnum=0,deptarr=new Array(),staffarr=new Array();
$(document).ready(function(){
    getstafflist();
    $("#btn_adddept").click(function(e) {
        var msg="<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>部门：</label>" +
            "<input id='text_deptname' maxlength='20' placeholder='请输入部门名称' class='col-sm-7' type='text'></div>";
        bootbox.dialog({
            message: msg,
            title: '添加部门',
            buttons:
            {
                "success" :
                {
                    "label" : "<i class='icon-ok'></i> 添加",
                    "className" : "btn-sm btn-success",
                    "callback": function() {
                        adddept();
                    }
                }
            }
        });
    });
    $("#btn_addstaff").click(function(e){
        if(deptnum<1){showerror("请先添加部门才可以添加员工哦");return false;}
        else{
            var op="";
            $.each(deptarr,function(i){
                op+="<option value='"+deptarr[i][0]+"'>"+deptarr[i][1]+"</option>";
            })
            var msg="<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>姓名：</label>" +
                "<input id='sname' maxlength='20' placeholder='请输入员工姓名' class='col-sm-7' type='text'></div>"+
                "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>手机号：</label>" +
                "<input id='smobile' maxlength='20' placeholder='请输入员工手机号' class='col-sm-7' type='text'></div>"+
                "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>部门：</label>" +
                "<select id='sdept' class='col-sm-7'>"+op+"</select></div>"+
                "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>职位：</label>" +
                "<input id='sposition' maxlength='20' placeholder='选填' class='col-sm-7' type='text'></div>"+
                "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>工号：</label>" +
                "<input id='sjobnum' maxlength='20' placeholder='选填' class='col-sm-7' type='text'></div>";
            bootbox.dialog({
                message: msg,
                title: '添加员工',
                buttons:
                {
                    "success" :
                    {
                        "label" : "<i class='icon-ok'></i> 添加",
                        "className" : "btn-sm btn-success",
                        "callback": function() {
                            addstaff();
                        }
                    }
                }
            });
        }
    });
});
function modifydept(this1){
    var did=this1.attr("data-id"),deptname=this1.attr("data-name");
    var msg="<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>部门：</label>" +
        "<input id='text_deptname1' maxlength='20' placeholder='请输入部门名称' class='col-sm-7' type='text' value='"+deptname+"' data-id='"+did+"'></div>";
    bootbox.dialog({
        message: msg,
        title: '更新部门名称',
        buttons:
        {
            "success" :
            {
                "label" : "<i class='icon-ok'></i> 更新",
                "className" : "btn-sm btn-success",
                "callback": function() {
                    modifydept1();
                }
            }
        }
    });
}
function modifydept1(){
    var deptname=$("#text_deptname1").val(),deptid=$("#text_deptname1").attr("data-id");
    if(deptname==null || deptname==""){showerror("提交存在错误");return false;}else{
        $.ajax({
            url:'company/modifydept.json',
            data:{deptname:deptname,deptid:deptid},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=='ok'){showsuccess("部门更新成功");getstafflist();}
                if(map.msg=='error0'){showerror("提交存在错误");}
                if(map.msg=='error1'){logtout();}
            }
        });
    }
}
function adddept(){
    var deptname=$("#text_deptname").val();
    if(deptname==null || deptname==""){showerror("提交存在错误");return false;}else{
        $.ajax({
            url:'company/adddept.json',
            data:{deptname:deptname},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=='ok'){showsuccess("部门添加成功");getstafflist();}
                if(map.msg=='error0'){showerror("提交存在错误");}
                if(map.msg=='error1'){logtout();}
            }
        });
    }
}
function modifystaff(this1){
    var oid=this1.attr("data-id");//oid,did,gdept,siid,glev2,gusername,gmobile,gposition,gjobnum
    if(oid==null || oid==""){}else{
        var sname="",smobile="",sdid="",sposition="",sjobnum="";
        $.each(staffarr,function(i){
            if(oid==staffarr[i][0]){
                sname=staffarr[i][5],smobile=staffarr[i][6],sdid=staffarr[i][1],sposition=staffarr[i][7],sjobnum=staffarr[i][8];
                return true;
            }
        });
        //
        var op="";
        $.each(deptarr,function(j){
            if(deptarr[j][0]==sdid){
                op+="<option selected='selected' value='"+deptarr[j][0]+"'>"+deptarr[j][1]+"</option>";
            }else{
                op+="<option value='"+deptarr[j][0]+"'>"+deptarr[j][1]+"</option>";
            }
        });
        var msg="<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>姓名：</label>" +
            "<input maxlength='20' readonly class='col-sm-7' type='text' value='"+sname+"'></div>"+
            "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>手机号：</label>" +
            "<input maxlength='20' readonly class='col-sm-7' type='text' value='"+smobile+"'></div>"+
            "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>部门：</label>" +
            "<select id='sdept1' class='col-sm-7'>"+op+"</select></div>"+
            "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>职位：</label>" +
            "<input id='sposition1' maxlength='20' placeholder='选填' class='col-sm-7' type='text' value='"+sposition+"'></div>"+
            "<div class='form-group padding-tb20'><label class='col-sm-4 txt-right'>工号：</label>" +
            "<input id='sjobnum1' maxlength='20' placeholder='选填' class='col-sm-7' type='text' value='"+sjobnum+"'></div>";

        bootbox.dialog({
            message: msg,
            title: '更新员工',
            buttons:
            {
                "success" :
                {
                    "label" : "<i class='icon-ok'></i> 更新",
                    "className" : "btn-sm btn-success",
                    "callback": function() {
                        modifystaff1(oid);
                    }
                }
            }
        });
    }

}
function modifystaff1(oid){
    var sdept=$("#sdept1 option:selected").val();
    if(sdept==null || sdept==""){showerror("请选择部门");return false;}
    var sposition=$("#sposition1").val();
    var sjobnum=$("#sjobnum1").val();
    $.ajax({
        url:'company/modifystaff.json',
        data:{soid:oid,sdept:sdept,sposition:sposition,sjobnum:sjobnum},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showsuccess("员工更新成功");getstafflist();}
            if(map.msg=='error0'){showerror("提交存在错误");}
            if(map.msg=='error1'){logtout();}
            if(map.msg=='error3'){showerror("更新失败，没有该员工信息");}
        }
    });
}
function lockstaff(this1){
    var oid=this1.attr("data-id");
    if(oid==null || oid==""){showerror("提交存在错误");return false;}
    else{
        showsuccess("员工状态更新中");
        lhide(".stafflock");
        $.ajax({
            url:'company/lockstaff.json',
            data:{soid:oid},
            dataType:'json',
            success:function(data){
                var map=data;
                if(map.msg=='ok'){showsuccess("员工状态更新成功。如果停用员工属于管理员，将收回其管理权限");getstafflist();lshow(".stafflock");}
                if(map.msg=='error0'){showerror("提交存在错误");lshow(".stafflock");}
                if(map.msg=='error1'){logtout();}
                if(map.msg=='error2'){showerror("相同手机号码正在使用中");lshow(".stafflock");}
                if(map.msg=='error3'){showerror("更新失败，没有该员工信息");lshow(".stafflock");}
            }
        });
    }
}
function addstaff(){
    var sname=$("#sname").val();
    if(sname==null || sname==""){showerror("请填写员工姓名");return false;}
    var smobile=$("#smobile").val();
    if(smobile==null || smobile==""){showerror("请填写员工手机");return false;}
    var sdept=$("#sdept option:selected").val();
    if(sdept==null || sdept==""){showerror("请选择部门");return false;}
    var sposition=$("#sposition").val();
    var sjobnum=$("#sjobnum").val();
    $.ajax({
        url:'company/addstaff.json',
        data:{sname:sname,smobile:smobile,sdept:sdept,sposition:sposition,sjobnum:sjobnum},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showsuccess("员工添加成功");getstafflist();}
            if(map.msg=='error0'){showerror("提交存在错误");}
            if(map.msg=='error1'){logtout();}
            if(map.msg=='error2'){showerror("该手机号正在使用中");}
        }
    });

}
function getstafflist(){
    $.ajax({
        url:'company/stafflist.json',
        data:{},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showstafflist(map.list);}
            if(map.msg=='error0'){showerror("提交存在错误");}
            if(map.msg=='error1'){logtout();}
        }
    });
}
function showstafflist(list){
    if(list==null || list==""){showsuccess("点击员工管理开始添加部门，员工信息吧！");deptnum=0;}else{
        var dhtml="",darr=new Array(),sarr=new Array();
        $.each(list,function(i){
            var obj=list[i];
            darr.push([obj[1],obj[2]]);
            if(obj[0]!=null && obj[0]!=""){
                sarr.push([obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],obj[6],obj[7],obj[8]]);
            }
        });
        var uniqdarr=jsHashArray(darr);
        var uniqsarr=jsHashArray(sarr);
        $.each(uniqdarr,function(i){
            dhtml+="<div class='dept'><b class='deptname' data-id='"+uniqdarr[i][0]+"' data-name='"+uniqdarr[i][1]+"'>"+uniqdarr[i][1]+" <i class='icon-edit'></i></b></div>"+
                "<table class='table table-bordered table-hover'><thead><tr><th>ID</th><th>姓名</th><th>电话</th><th>职位</th><th>工号</th><th>状态</th><th>操作</th></tr></thead><tbody>";
            $.each(uniqsarr,function(j){
                if(uniqsarr[j][1]==uniqdarr[i][0]){
                    var islock="<span class='label label-sm label-danger'>停用</span>",isbtn="<button type='button' data-id='"+uniqsarr[j][0]+"' class='stafflock btn btn-xs btn-success'><i class='icon-ok'></i>启用</button>";
                    if(uniqsarr[j][4]=='1'){islock="<span class='label label-sm label-success'>正常</span>",isbtn="<button type='button' data-id='"+uniqsarr[j][0]+"' class='stafflock btn btn-xs btn-danger'><i class='icon-remove'></i>停用</button>";}
                    dhtml+="<tr><td>"+uniqsarr[j][0]+"</td><td>"+uniqsarr[j][5]+"</td><td>"+uniqsarr[j][6]+"</td><td>"+uniqsarr[j][7]+"</td><td>"+uniqsarr[j][8]+"</td>"+
                        "<td>"+islock+"</td>"+
                        "<td><button type='button' data-id='"+uniqsarr[j][0]+"' class='staffname btn btn-xs btn-info'><i class='icon-edit'></i>编辑</button>"+isbtn+"</td></tr>";
                }else{
                    return true;
                }
            });
            dhtml+="</tbody></table>";
        });
        $("#deptbox").html(dhtml);
        $("#deptbox").off('click','.deptname').on('click','.deptname',function(){
            modifydept($(this));
        });
        $("#deptbox").off('click','.staffname').on('click','.staffname',function(){
            modifystaff($(this));
        });
        $("#deptbox").off('click','.stafflock').on('click','.stafflock',function(){
            lockstaff($(this));
        });
        deptnum=list.length;
        deptarr=uniqdarr;
        staffarr=uniqsarr;
    }
}