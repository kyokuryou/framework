<form id="alipaysubmit" name="alipaysubmit" action="https://mapi.alipay.com/gateway.do?_input_charset=" method="post">
<#list paramsMap?keys as mapKey>
    <input type="hidden" name="${mapKey_index}" value="${mapKey}"/>
</#list>
    <input type="submit" value="确定" style="display:none;">
</form>
<script>document.forms['alipaysubmit'].submit();</script>