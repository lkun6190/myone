<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>附件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/file/sysFiles/">附件列表</a></li>
		<li class="active"><a href="${ctx}/file/sysFiles/form?id=${sysFiles.id}">附件<shiro:hasPermission name="file:sysFiles:edit">${not empty sysFiles.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="file:sysFiles:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysFiles" action="${ctx}/file/sysFiles/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table">
		<tbody>
		<tr>
		<td>
		<div class="form-group">
			<label class="control-label">文件路径：</label>
			<div class="controls">
				<form:input path="wjlj" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		<td>
		<div class="form-group">
			<label class="control-label">文件标题：</label>
			<div class="controls">
				<form:input path="wjbt" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		  </tr>
		   <tr>
		<td>
		<div class="form-group">
			<label class="control-label">文件类型：</label>
			<div class="controls">
				<form:input path="wjlx" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		<td>
		<div class="form-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		 <td>
		  </tr>
		   <tr>
		<td>
		<div class="form-group">
			<label class="control-label">附件归属表主键ID：</label>
			<div class="controls">
				<form:input path="zbid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		</tr>
		</tbody>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="file:sysFiles:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>