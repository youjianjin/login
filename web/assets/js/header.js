        $(document).ready(function(){
            $(".i-user").click(function(){
                var statu=$(this).attr("data-hide");
                if(statu=='show'){
                    $(this).children().eq(1).attr("class","icon-caret-down");
                    $(".i-menu").hide();
                    $(this).attr("data-hide","hide");
                }else{
                    $(this).children().eq(1).attr("class","icon-caret-up");
                    $(".i-menu").show();
                    $(this).attr("data-hide","show");
                }
            }).blur(function(){
                var statu=$(".i-user").attr("data-hide");
                if(statu=='show'){$(this).children().eq(1).attr("class","icon-caret-down");$(".i-menu").hide();$(".i-user").attr("data-hide","hide");}
            });
        });
