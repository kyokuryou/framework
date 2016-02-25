<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/common/inc/head_inc.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加角色<#else>编辑角色</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>authority!save.action<#else>authority!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<ul class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="分配资源" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						角色名称:
					</th>
					<td>
						<input type="text" name="authority.name" class="formText {required: true, remote: 'authority!checkName.action?oldValue=${(authority.name?url)!}', messages: {remote: '角色名称已存在!'}}" value="${(authority.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						角色标识:
					</th>
					<td>
						<input type="text" name="authority.value" class="formText {required: true, minlength: 6, prefix: 'ROLE_', remote: 'authority!checkValue.action?oldValue=${(authority.value?url)!}', messages: {remote: '角色标识已存在!'}}" value="${(authority.value)!'ROLE_'}" title="角色标识长度不能小于6,且必须以ROLE_开头" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<input type="text" name="authority.description" class="formText" value="${(authority.description)!}" />
					</td>
				</tr>
				<#if (authority.isSystem)!false>
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示：</b>系统内置角色不允许修改!</span>
						</td>
					</tr>
				</#if>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<td colspan="2">
						<#list allResource as list>
							<div style="width: 30%; float: left;"><label><input type="checkbox" name="resourceIds" value="${list.id}" <#if (authority.resourceSet.contains(list) == true)!> checked="checked"</#if> />${(list.name)!}</label></div>
						</#list>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>