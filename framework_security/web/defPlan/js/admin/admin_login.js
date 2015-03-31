$(function () {
    var $loginForm = $("#loginForm");
    var $username = $loginForm.find("input[name='j_username']");
    var $password = $loginForm.find("input[name='j_password']");
    var $captcha = $loginForm.find("input[name='j_captcha']");
    var $captchaImage = $loginForm.find("#captchaImage");
    var $captchaChange = $loginForm.find("#captchaChange");
    var $isSaveUsername = $loginForm.find("#isSaveUsername");

    // 判断"记住用户名"功能是否默认选中,并自动填充登录用户名
    if ($.cookie("secUsername") != null) {
        $isSaveUsername.attr("checked", true);
        $username.val($.cookie("secUsername"));
        $password.focus();
    } else {
        $isSaveUsername.attr("checked", false);
        $username.focus();
    }

    // 提交表单验证,记住登录用户名
    $loginForm.submit(function () {
        if ($username.val() == "") {
            $.messager.alert('提示','请输入您的用户名!','error');
            return false;
        }
        if ($password.val() == "") {
            $.messager.alert('提示','请输入您的密码!','error');
            return false;
        }
        if ($captcha.val() == "") {
            $.messager.alert('提示','请输入您的验证码!','error');
            return false;
        }

        if ($isSaveUsername.attr("checked") == true) {
            $.cookie("secUsername", $username.val(), {expires:30});
        } else {
            $.cookie("secUsername", null);
        }

    });
    $captchaChange.click(function (){
        flushCaptcha();
        return false;
    });

    // 刷新验证码
    $captchaImage.click(function () {
        flushCaptcha();
    });

    function flushCaptcha(){
        var timestamp = (new Date()).valueOf();
        var imageSrc = $captchaImage.attr("src");
        if (imageSrc.indexOf("?") >= 0) {
            imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
        }
        imageSrc = imageSrc + "?timestamp=" + timestamp;
        $captchaImage.attr("src", imageSrc);
    }
});