/**
 * Created by dingfada on 2017/12/16.
 */
var mouldValueArray=['10001','10002','10003','10004'];
var mouldTextArray=['注塑模具','橡胶模具','平面模具','多功能模具'];
function mouldOptions(id){
    $.each(mouldTextArray,function(i){
        $(id).append("<option value='"+mouldValueArray[i]+"'>"+mouldTextArray[i]+"</option>");
    });
}
function mouldText(id){
    try{var i=mouldValueArray.indexOf(id);return mouldTextArray[i];}catch(e) {return null;}
}