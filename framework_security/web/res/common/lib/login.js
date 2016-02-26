function checkLogin(errorOpt) {
    var errorText = {
        userName:"请输入您的用户名!",
        password:"请输入您的密码!",
        captcha:"请输入您的验证码!"
    };
    var $username = $("#username");
    var $password = $("#password");
    var $captcha = $("#captcha");
    var $captchaImage = $("#captchaImage");
    var $isSaveUsername = $("#isSaveUsername");
    errorText = $.extend({}, errorText, errorOpt);

    // 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
    if ($.cookie("adminUsername") != null) {
        $isSaveUsername.attr("checked", true);
        $username.val($.cookie("adminUsername"));
        $password.focus();
    } else {
        $isSaveUsername.attr("checked", false);
        $username.focus();
    }

    // 提交表单验证,记住登录用户名
    $("#loginForm").submit(function () {
        if ($username.val() == "") {
            alert(errorText.userName);
            return false;
        }
        if ($password.val() == "") {
            alert(errorText.password);
            return false;
        }
        if ($captcha.val() == "") {
            alert(errorText.captcha);
            return false;
        }
        if ($isSaveUsername.is(":checked")) {
            $.cookie("adminUsername", $username.val(), {expires:30});
        } else {
            $.cookie("adminUsername", null);
        }

    });

    // 刷新验证码
    $captchaImage.click(function () {
        var timestamp = (new Date()).valueOf();
        var imageSrc = $captchaImage.attr("src");
        if (imageSrc.indexOf("?") >= 0) {
            imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
        }
        imageSrc = imageSrc + "?timestamp=" + timestamp;
        $captchaImage.attr("src", imageSrc);
    });
}