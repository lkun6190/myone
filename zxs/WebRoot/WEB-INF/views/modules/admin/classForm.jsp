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
<title>班级编辑</title>
<script src="${ctxStatic}/jquery/jquery-1.11.1.min.js"></script>
<link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css"
	type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"></script>
<meta name="decorator" content="default" />
<style>
.pwd {
	width: 40px;
	height: 20px;
	line-height: 14px;
	padding-top: 2px;
}

.pwd_f {
	color: #BBBBBB;
}

.pwd_c {
	background-color: #F3F3F3;
	border-top: 1px solid #D0D0D0;
	border-bottom: 1px solid #D0D0D0;
	border-left: 1px solid #D0D0D0;
}

.pwd_Weak_c {
	background-color: #FF4545;
	border-top: 1px solid #BB2B2B;
	border-bottom: 1px solid #BB2B2B;
	border-left: 1px solid #BB2B2B;
}

.pwd_Medium_c {
	background-color: #FFD35E;
	border-top: 1px solid #E9AE10;
	border-bottom: 1px solid #E9AE10;
	border-left: 1px solid #E9AE10;
}

.pwd_Strong_c {
	background-color: #3ABB1C;
	border-top: 1px solid #267A12;
	border-bottom: 1px solid #267A12;
	border-left: 1px solid #267A12;
}

.pwd_c_r {
	border-right: 1px solid #D0D0D0;
}

.pwd_Weak_c_r {
	border-right: 1px solid #BB2B2B;
}

.pwd_Medium_c_r {
	border-right: 1px solid #E9AE10;
}

.pwd_Strong_c_r {
	border-right: 1px solid #267A12;
}

#disp {
	display: none;
}

#Table1 {
	display: none;
}

#Table2 {
	display: none;
}

#Table3 {
	display: none;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {

		jQuery.validator.addMethod("isNmberAndAlpha", function(value, element) {
			var reg = /^(?![^a-zA-Z]+$)(?!\D+$)/;
			return this.optional(element) || reg.test(value);

		}, "至少是数字与字母的组合!");

		jQuery.validator.addMethod("isKongge", function(value, element) {
			var reg = /\s+/;
			return this.optional(element) || !reg.test(value);

		}, "密码不能输入空格!");


		$(document).ready(
			function() {
				//$("#name").focus();
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
									/* 	error.appendTo(element.parent()
												.parent()); */
								
								} else {
									error.insertAfter(element);
								}
								
								
							}
						});
			});

	});
</script>
<style>
.form-horizontal .controls {
	overflow-x: inherit;
	overflow-y: inherit;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="${ctx}/sys/office/list?id=<%=id %>&parentIds=<%=ids %>&offAid=<%=offAid %>">机构列表</a></li>
		<%-- <li><a href="${ctx}/cla/list">班级列表</a></li>
		<li class="active"><a href="${ctx}/cla/form?id=${zxsClass.id}">班级<shiro:hasPermission
					name="class:edit:view">${not empty zxsClass.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="class:edit:view">查看</shiro:lacksPermission></a></li> --%>
		<li class="active"><a href="${ctx}/cla/list">班级列表</a></li>
		<li><a href="${ctx}/tea/list">老师列表</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="zxsClass"
		action="${ctx}/cla/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />

		<div class="control-group">
			<label class="control-label">机构或学校:</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" disabled="disabled"
					value="${zxsClass.office.id}" labelName="office.name"
					labelValue="${zxsClass.office.name}" title="所属代理或机构"
					url="/sys/office/treeData?type=2" cssClass="required"
					notAllowSelectParent="true" notAllowSelectRoot="true" />
			</div>
		</div>
		
<input id="oldClassNo" name="oldClassNo" type="hidden"
					value="${zxsClass.classNo}"/>
		<div class="control-group">
			<label class="control-label">班级名称:</label>
			<div class="controls">
				<form:input path="classNo" htmlEscape="false" maxlength="50"
					class="required" data-rule-required="true"
					data-msg-required="班级名称不能为空" />
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">课室名称:</label>
			<div class="controls">
				<form:input path="className" htmlEscape="false" maxlength="50"
					/>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">上课时间:</label>
			<div class="controls">
				<form:checkboxes path="studyDateIdList" items="${allStudyDate}"
					itemLabel="name" itemValue="id" htmlEscape="false" />

			</div>
		</div>




		<div class="control-group">
			<label class="control-label">开始时段:</label>
			<div class="controls">
				<input name="studyTimeStart" type="text" class="input-medium Wdate"
					id="studyTimeStartInput" value="${zxsClass.studyTimeStart}"
					onclick="WdatePicker({dateFmt:'HH:mm',isShowClear:false});" />
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">结束时段:</label>
			<div class="controls">
				<input name="studyTimeEnd" type="text" class="input-medium Wdate"
					id="studyTimeEndInput" value="${zxsClass.studyTimeEnd}"
					onclick="WdatePicker({dateFmt:'HH:mm',isShowClear:false});"
					/>

			</div>
		</div>
		<input type="hidden" name="oldTeaId" value="${zxsClass.zxsTea.id}" />
		<div class="control-group">
			<label class="control-label">机构或学校所有老师:</label>
			<div class="controls">
				<form:select path="zxsTea.id" class="input-medium">
					<form:option value="" label="请选择老师"></form:option>
					<form:options items="${teaList}" itemLabel="teaName" itemValue="id"
						htmlEscape="false" />
				</form:select>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3"
					maxlength="200" class="input-xlarge" />
			</div>
		</div>
		<c:if test="${not empty zxsClass.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate
							value="${zxsClass.createDate}" type="both" dateStyle="full" /></label>
				</div>
			</div>

		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="class:edit:view">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>