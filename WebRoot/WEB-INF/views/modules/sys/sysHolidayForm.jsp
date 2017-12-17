<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节假日数据管理</title>
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
	<form:form id="inputForm" modelAttribute="sysHoliday" action="${ctx}/sys/sysHoliday/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<table class="table">
		<tbody>
		<tr>
		<td>
		<div class="form-group">
			<label class="control-label">hid：</label>
			<div class="controls">
				<form:input path="hid" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 <td>
		<td>
		<div class="form-group">
			<label class="control-label">hyear：</label>
			<div class="controls">
				<form:input path="hyear" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		  </tr>
		   <tr>
		<td>
		<div class="form-group">
			<label class="control-label">hmonth：</label>
			<div class="controls">
				<form:input path="hmonth" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		<td>
		<div class="form-group">
			<label class="control-label">hday：</label>
			<div class="controls">
				<form:input path="hday" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		  </tr>
		   <tr>
		<td>
		<div class="form-group">
			<label class="control-label">hdate：</label>
			<div class="controls">
				<form:input path="hdate" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		<td>
		<div class="form-group">
			<label class="control-label">1工作日、2节假日：</label>
			<div class="controls">
				<form:input path="htype" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		 <td>
		  </tr>
		   <tr>
		</tr>
		</tbody>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysHoliday:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>