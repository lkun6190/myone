<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils"%>
<%
	String offAid = UserUtils.getUser().getOffice().getId();
	String id = UserUtils.getUser().getOffice().getParentId();
	String ids = UserUtils.getUser().getOffice().getParentIds();
%>
<html>
<head>
<title>代理/机构管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
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
		<li><a
			href="${ctx}/sys/office/list?id=<%=id %>&parentIds=<%=ids %>&offAid=<%=offAid %>">机构列表</a>
		</li>
		<li class="active"><a
			href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission
					name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission>
		</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="office"
		action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div class="controls">
				<sys:treeselect id="office" name="parent.id"
					value="${office.parent.id}" labelName="parent.name"
					labelValue="${office.parent.name}" title="机构"
					url="/sys/office/treeData" extId="${office.id}" cssClass=""
					allowClear="${office.currentUser.admin}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所在区域:</label>
			<div class="controls">
				<sys:treeselect id="area" name="area.id" value="${office.area.id}"
					labelName="area.name" labelValue="${office.area.name}" title="区域"
					url="/sys/area/treeData" cssClass="required" />
			</div>
		</div>
		<input type="hidden" name="oldName" value="${office.name }"/>
		
		<div class="control-group">
			<label class="control-label">机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50"
					class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div class="controls">

				<form:select path="type" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

		</div>

		<div class="control-group">
			<label class="control-label">主负责人:</label>
			<div class="controls">
				<form:input path="primaryPerson" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副负责人:</label>
			<div class="controls">

				<form:input path="deputyPerson" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联络人:</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">简历/注册地:</label>
			<div class="controls">
				<form:input path="cv" htmlEscape="false" maxlength="50" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">加盟保证金:</label>
			<div class="controls">
				<form:input path="joinGold" htmlEscape="false" maxlength="8" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">加盟日期:</label>
			<div class="controls">

				<input id="joinDate" name="joinDate" type="text" maxlength="20"
					class="input-mini Wdate"
					value="<fmt:formatDate value="${office.joinDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3"
					maxlength="200" class="input-xlarge" />
			</div>
		</div>
		<c:if test="${empty office.id}">
			<div class="control-group">
				<label class="control-label">快速添加下级部门:</label>
				<div class="controls">
					<form:checkboxes path="childDeptList"
						items="${fns:getDictList('sys_office_common')}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>