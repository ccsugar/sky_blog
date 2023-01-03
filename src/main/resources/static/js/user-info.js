// 修改用户基本数据
let userBaseinfoForm=$('#user-baseinfo-form')
userBaseinfoForm.form({
    fields : {
        nickname : {
            identifier: 'nickname',
            rules: [{
                type : 'empty',
                prompt: '请输入昵称'
            }]
        },
        email : {
            identifier: 'email',
            rules: [{
                type : 'email',
                prompt: '请输入正确格式邮箱'
            }]
        },
        avatar : {
            identifier: 'avatar',
            rules: [{
                type : 'empty',
                prompt: '请输入头像链接'
            }]
        }
    }
});
$('#user-baseinfo-modify-btn').click(function(){
    var Msgs="";
    $(".negative.message span,.positive.message span").html("");
    $(".negative.message,.positive.message").removeClass("active");
    if(userBaseinfoForm.form('is valid')){
            $.post(
                "/userBaseinfoModify",
                userBaseinfoForm.serialize(),
                function (msgs) {
                    if (msgs[0]==="fail"){
                        for(var i=1,len=msgs.length;i<len;++i){
                            Msgs=Msgs+msgs[i];
                            if(i<len-1){
                                Msgs=Msgs+"<br>"
                            }
                        }
                        $("#user-baseinfo-form .negative.message").addClass("active");
                        $("#user-baseinfo-form .negative.message span").html(Msgs);
                    } else if (msgs[0]==="success"){
                        alert("基本信息修改成功!")
                        location.reload(true);
                        // $(".positive.message span").html("修改成功!");
                        // $(".positive.message").addClass("active")
                    } else if(msgs[0]==="expired") {
                        alert("登录已过期,请重新登录")
                        $('#sign-in-btn').trigger("click");
                    } else {
                        Msgs="服务器端发生错误"
                        $("#user-baseinfo-form .negative.message span").html(Msgs);
                        $("#user-baseinfo-form .negative.message").addClass("active");
                    }
                },
                "json"
            ).fail(
                function () {
                    Msgs="修改请求失败";
                    $("#user-baseinfo-form .negative.message").addClass("active");
                    $("#user-baseinfo-form .negative.message span").html(Msgs)
                }
            );
        }
    })

// 修改用户密码
let userPasswordForm=$('#user-password-form')
userPasswordForm.form({
    fields : {
        original_password : {
            identifier: 'original_password',
            rules: [{
                type : 'empty',
                prompt: '请输入原密码'
            }]
        },
        new_password : {
            identifier: 'new_password',
            rules: [{
                type : 'empty',
                prompt: '请输入新密码'
            }]
        },
        confirm_password : {
            identifier: 'confirm_password',
            rules: [{
                type : 'match[new_password]',
                prompt: '两次输入密码不一致!'
            }]
        }
    }
});
$('#user-password-modify-btn').click(function(){
    var Msgs="";
    $(".negative.message span,.positive.message span").html("");
    $(".negative.message,.positive.message").removeClass("active");
    if(userPasswordForm.form('is valid')){
        $.post(
            "/userPasswordModify",
            userPasswordForm.serialize(),
            function (msgs) {
                if (msgs[0]==="fail"){
                    for(var i=1,len=msgs.length;i<len;++i){
                        Msgs=Msgs+msgs[i];
                        if(i<len-1){
                            Msgs=Msgs+"<br>"
                        }
                    }
                    $("#user-password-form .negative.message").addClass("active");
                    $("#user-password-form .negative.message span").html(Msgs);
                } else if (msgs[0]==="success"){
                    alert("密码修改成功!")
                    location.reload(true);
                    // $(".positive.message span").html("修改成功!");
                    // $(".positive.message").addClass("active")
                } else if(msgs[0]==="expired") {
                    alert("登录已过期,请重新登录")
                    $('#sign-in-btn').trigger("click");
                } else {
                    Msgs="服务器端发生错误"
                    $("#user-password-form .negative.message span").html(Msgs);
                    $("#user-password-form .negative.message").addClass("active");
                }
            },
            "json"
        ).fail(
            function () {
                Msgs="修改请求失败";
                $("#user-password-form .negative.message").addClass("active");
                $("#user-password-form .negative.message span").html(Msgs)
            }
        );
    }
})