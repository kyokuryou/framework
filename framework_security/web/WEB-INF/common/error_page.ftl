<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>提示信息 - ${systemConfig.systemName}</title>
<#include "/WEB-INF/common/inc/head_inc.ftl"/>
</head>
<body class="error">
<div class="body">
    <div class="errorBox">
        <div class="errorDetail">
            <div class="errorContent">
            ${errorContent!"您的操作出现错误!"}
            </div>
            <div class="errorUrl">点击此处
                <a href="#" onclick="window.history.back(); return false;">返回</a>，或回到
                <a href="${base}/">首页</a></div>
        </div>
    </div>
</div>
</body>
</html>