<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
<#include "/WEB-INF/common/inc/head_inc.ftl">
    <script type="text/javascript" src="${base}/defPlan/js/admin/admin_login.js"></script>
    <script type="text/javascript">
        using("messager");
    </script>
</head>
<body>
<div>1<@spring.message "${Request['login.failure']}"/>1</div>
<div class="login">
    <form id="loginForm" class="form" action="${base}/admin/loginVerify" method="post">
        <table>
            <tr>
                <th>
                    用户名:
                </th>
                <td>
                    <div class="formText">
                        <input type="text" name="j_username"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    密&nbsp;&nbsp;&nbsp;码:
                </th>
                <td>
                    <div class="formText">
                        <input type="password" name="j_password"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    验证码:
                </th>
                <td>
                    <div class="formTextS">
                        <input type="text" name="j_captcha"/>
                    </div>
                    <div class="captchaImage">
                        <img id="captchaImage" src="${base}/captcha.jpg" alt="换一张" style="cursor: pointer"/>
                        <a id="captchaChange" href="javascript:void(0);">换一张</a>
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    &nbsp;
                </th>
                <td>
                    <input type="checkbox" id="isSaveUsername"/><label for="isSaveUsername">&nbsp;记住用户名</label>
                </td>
            </tr>
            <tr>
                <th>
                    &nbsp;
                </th>
                <td>
                    <input type="submit" class="submitButton ignoreForm" value="登 陆"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>