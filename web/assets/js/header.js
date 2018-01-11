//this is a common javascript class
(function($){$.getUrlParam = function(name){var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");var r = window.location.search.substr(1).match(reg);if(r!=null) return unescape(r[2]); return null;}})(jQuery);
//
function lshow(str){$(str).show();}
function lhide(str){$(str).hide();}
function lsuccess(str){$(str).removeClass("input-error").addClass("input-success");}
function lerror(str){$(str).removeClass("input-success").addClass("input-error");}
function ldisabled(str){$(str).attr("disabled",true);}
function lenabled(str){$(str).removeAttr("disabled");}
function logtout(){window.location.href="/login.jsp?method=logtout";}
function loglock(){window.location.href="/login.jsp?method=loglock";}
//unique two dimensional array
function jsHashArray(arr){var hash = {};var result = [];for(var i=0,len=arr.length;i<len;i++){if(!hash[arr[i]]){result.push(arr[i]);hash[arr[i]] = true;}}return result;}