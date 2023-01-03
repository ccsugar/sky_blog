// 用户注册
let signUpForm=$('#sign-up-form')
signUpForm.form({
    fields : {
        username : {
            identifier: 'username',
            rules: [{
                type : 'empty',
                prompt: '请输入用户名'
            }]
        },
        nickname : {
            identifier: 'nickname',
            rules: [{
                type : 'empty',
                prompt: '请输入昵称'
            }]
        },
        password : {
            identifier: 'password',
            rules: [{
                type : 'empty',
                prompt: '请输入密码'
            }]
        },
        email : {
            identifier: 'email',
            rules: [{
                type : 'email',
                prompt: '请输入正确格式邮箱'
            }]
        }
    }
});
$('#sign-up-btn').click(function(){
        if(signUpForm.form('is valid')){
            $(".negative.message").html(errorMsgs).css("display","none");
            var errorMsgs="";
            $.post(
                "/userSignUp",
                signUpForm.serialize(),
                function (msgs) {
                    if (msgs[0]==="fail"){
                        for(var i=1,len=msgs.length;i<len;++i){
                            errorMsgs=errorMsgs+msgs[i];
                            if(i<len-1){
                                errorMsgs=errorMsgs+"<br>"
                            }
                        }
                        $(".negative.message").html(errorMsgs).css("display","");
                    } else if (msgs[0]==="success"){
                        alert("注册成功,请登录")
                        $(".sign-up-wrapper").toggleClass("active");
                        $(".sign-in-wrapper").toggleClass("active");
                        $('#sign-in-form [name="username"]').val($('#sign-up-form [name="username"]').val())
                    } else {
                        errorMsgs="服务器端发生错误"
                        $(".negative.message").html(errorMsgs).css("display","");
                    }
                },
                "json"
            ).fail(
                function () {
                    errorMsgs="注册请求失败";
                    $(".negative.message").html(errorMsgs).css("display","");
                }
            );
        }
    })

// 用户登录
let signInForm=$('#sign-in-form')
signInForm.form({
    fields : {
        username : {
            identifier: 'username',
            rules: [{
                type : 'empty',
                prompt: '请输入用户名'
            }]
        },
        password : {
            identifier: 'password',
            rules: [{
                type : 'empty',
                prompt: '请输入密码'
            }]
        }
    }
});
$('#sign-in-btn').click(function(){
        if(signInForm.form('is valid')){
            $(".negative.message").html(errorMsgs).css("display","none");
            var errorMsgs="";
            $.post(
                "/userSignIn",
                signInForm.serialize(),
                function (msgs) {
                    if (msgs[0]==="fail"){
                        for(var i=1,len=msgs.length;i<len;++i){
                            errorMsgs=errorMsgs+msgs[i];
                            if(i<len-1){
                                errorMsgs=errorMsgs+"<br>"
                            }
                        }
                        $(".negative.message").html(errorMsgs).css("display","");
                    } else if(msgs[0]==="deplicated"){
                        alert("您之前已经登录过了!");
                        $('.sign-outer-wrapper').removeClass("active");
                        $('.sign-in-wrapper').toggleClass("active")
                        location.reload(true);
                    } else if (msgs[0]==="success"){
                        alert("登录成功!");
                        $('.sign-outer-wrapper').removeClass("active");
                        $('.sign-in-wrapper').toggleClass("active")
                        location.reload(true);
                    } else {
                        errorMsgs="服务器端发生错误"
                        $(".negative.message").html(errorMsgs).css("display","");
                    }
                },
                "json"
            ).fail(
                function () {
                    errorMsgs="登录请求失败";
                    $(".negative.message").html(errorMsgs).css("display","");
                }
            );
        }
    })

// 登录注册切换
$('.signBox-switcher a').click(function (){
        $(".sign-up-wrapper").toggleClass("active");
        $(".sign-in-wrapper").toggleClass("active");
    })

// 导航栏按钮触发注册界面
$('#nav-signUp-btn').click(function (){
        $('.sign-outer-wrapper,.sign-up-wrapper').toggleClass("active");
        $('#sign-wrapper').focus();
    })

// 导航栏按钮触发登录界面
$('#nav-signIn-btn').click(function (){
    $('.sign-outer-wrapper,.sign-in-wrapper').toggleClass("active");
    $('#sign-wrapper').focus();
})

// 导航栏按钮点击注销
$('#nav-signOut-btn').click(function (){
    $.get(
        "/userSignOut",
        function () {
            alert("注销成功!");
            location.reload(true);
        }
    )
})

// 点击登录注册界面遮罩层隐藏界面
$('.sign-outer-wrapper').focus(function () {
    $('.sign-outer-wrapper,.sign-in-wrapper,.sign-up-wrapper').removeClass('active');
})

function _scroll(){
    var scrollTop =$(window).scrollTop();
    if(scrollTop<10){
        $('.grid-header').addClass('top');
    }else{
        $('.grid-header').removeClass('top')   ;
    }
}
$(_scroll)
$(window).on('scroll',function() {
    _scroll()
});


