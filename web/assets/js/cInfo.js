/**
 * Created by Administrator on 2017/12/19.
 */
var ciid="";
$(document).ready(function(){
    //
    mouldOptions("#inputmould");
    getcompanyinfo();
    $("#btn_edit").click(function(){
        showedit();
        showsuccess("为了让客户对贵公司有更全面的了解，请完善详细信息");
        $(this).hide();
        //$("#selcity1 option[data-code=120000]").attr("selected","selected");
    });
    //这里要判断是否改变了值???
    $("#inputname,#inputemail,#inputcontact,#inputaddress,#inputinfo").blur(function(){
        $("#btn_save").removeClass("hidden");
    });
    $("#inputcity>select,#inputmould").change(function(){
        $("#btn_save").removeClass("hidden");
    });
    $("#btn_save").click(function(){
        var l3=$("#inputcontact").val();
        if(l3==null || l3==""){showerror("请输入联系人");return false;}
        var l41=$("#selcity1").children("option:selected").attr("data-code");
        var l42=$("#selcity2").children("option:selected").attr("data-code");
        var l43=$("#selcity3").children("option:selected").attr("data-code");
        if(l41==null || l41=="" || l42==null || l42=="" || l43==null || l43==""){showerror("请选择地址");return false;}
        var l4=l41+"_"+l42+"_"+l43;
        var l5=$("#inputaddress").val();
        if(l5==null || l5==""){showerror("请输入详细地址");return false;}
        var l6=$("#inputinfo").val();
        if(l6==null || l6==""){showerror("请输入公司介绍");return false;}
        var l7=$("#inputmould").val();
        if(l7==null || l7==""){showerror("请选择公司业务");return false;}
        $(this).hide();
        modifycompanyinfo(l3,l4,l5,l6,l7);
    });
});
function modifycompanyinfo(l3,l4,l5,l6,l7){
    var l7n=l7.toString().replace(/[,]/g, "_");
    $.ajax({
        url:'company/modifycompany.json',
        data:{l3:l3,l4:l4,l5:l5,l6:l6,l7:l7n,ciid:ciid},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=='ok'){showsuccess("修改成功");getcompanyinfo();}
            if(map.msg=='error0'){showerror("提交存在错误");}
            if(map.msg=='error1'){logtout();}
        }
    });
}
function getcompanyinfo(){
    $.ajax({
        url:'company/companyinfo.json',
        data:{},
        dataType:'json',
        success:function(data){
            var map=data;
            if(map.msg=="ok"){
                showcompanyinfo(map.table1,map.table2,map.table3);
            }
            if(map.msg=='error0'){
                showerror("当前信息存在错误！");
            }
            if(map.msg=='error1'){logtout();}
        }
    });
}
function showcompanyinfo(t1,t2,t3){
    if(t1==null){logtout();}else{
        $("#inputmobile").val(t1.gmobile);$("#spanmobile").html(shownull(t1.gmobile));
        $("#inputemail").val(t1.gemail);$("#spanemail").html(shownull(t1.gemail));
    }
    if(t2==null){logtout();}else{
        $("#inputname").val(t2.gcompany);$("#spanname").html(shownull(t2.gcompany));
        $("#inputid").val(t2.oid);$("#spanid").html(shownull(t2.oid));
    }
    if(t3==null){
        $("#spancontact").html(shownull(null));
        $("#spancity").html(shownull(null));
        $("#spanaddress").html(shownull(null));
        $("#spaninfo").html(shownull(null));
        $("#spanmould").html(shownull(null));
        $(".chosen-select").chosen();$("#inputmould_chosen").hide();
        ciid="";
        $("#inputcity").distpicker({autoSelect:false,});
    }else{
        ciid=t3.oid;
        $("#inputcontact").val(t3.gcontact);$("#spancontact").html(t3.gcontact);
        var citys=t3.gcity.split("_");
        var lp=ChineseDistricts[86][citys[0]].toString();
        var lc=ChineseDistricts[citys[0]][citys[1]].toString();
        var ld=ChineseDistricts[citys[1]][citys[2]].toString();
        $("#spancity").html(lp+"  "+lc+"  "+ld+"  ");
        $("#inputcity").distpicker({province:lp,city:lc,disctrict:ld,});
        $("#inputaddress").val(t3.gaddress);$("#spanaddress").html(t3.gaddress);
        $("#inputinfo").val(t3.ginfo);$("#spaninfo").html(t3.ginfo);
        $("#spanmould").html("");
        var moulds=t3.gmould.split("_");
        $.each(moulds,function(i){
            $("#inputmould option[value="+moulds[i]+"]").attr("selected","selected");
            $("#spanmould").append("<span class='label label-info arrowed-right'>"+mouldText(moulds[i])+"</span>");
        });
        $(".chosen-select").chosen();$("#inputmould_chosen").hide();
    }
    showsave();
}
function showedit(){
    lshow("#inputname,#inputid,#inputmobile,#inputemail,#inputcity,#inputaddress,#inputcontact,#inputinfo,#inputmould,#inputmould_chosen");
    lhide("#spanname,#spanid,#spanmobile,#spanemail,#spancity,#spanaddress,#spancontact,#spaninfo,#spanmould");

}
function showsave(){
    lshow("#spanname,#spanid,#spanmobile,#spanemail,#spancity,#spanaddress,#spancontact,#spaninfo,#spanmould");
    lhide("#inputname,#inputid,#inputmobile,#inputemail,#inputcity,#inputaddress,#inputcontact,#inputinfo,#inputmould,#inputmould_chosen");
}