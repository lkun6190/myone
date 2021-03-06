<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>老师管理</title>
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
	$(document)
			.ready(
					function() {

						jQuery.validator.addMethod("isNmberAndAlpha", function(
								value, element) {
							var reg = /^[A-Za-z0-9]+$/;
							return this.optional(element) || reg.test(value);

						}, "至少是数字与字母的组合!");

						jQuery.validator.addMethod("isKongge", function(value,
								element) {
							var reg = /\s+/;
							return this.optional(element) || !reg.test(value);

						}, "密码不能输入空格!");

						$("#inputForm")
								.validate(
										{
											rules : {
												loginName : {
													remote : "${ctx}/tea/checkTeaNo?oldTeaNo="
															+ encodeURIComponent('${zxsTea.teaNo}')
												},
												newPassword : {
													isNmberAndAlpha : true,
													isKongge : true,
													rangelength : [ 4, 16 ]
												},
												
											},
											messages : {
												loginName : {
													remote : "用户登录名已存在"
												},
												name : "姓名不能为空！",

												loginName : "登录名不能为空！",
												
												newPassword : "密码格式错误！",
												confirmNewPassword : {
													equalTo : "输入与上面相同的密码"
												},
												roleIdList : "用户角色必选！",
												rangelength : $.validator
														.format("密码长度在 {0} 至 {1} 之间.")
											},
											submitHandler : function(form) {
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												$("#messageBox").text(
														"输入有误，请先更正。");
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													error.insertAfter(element);
												}
											}
										});

						/*$("#btnSubmit").click(function() {
							if($("#newPassword").val()==$("#confirmNewPassword").val()){
								var pwds=$("#newPassword").val();
								var regs = new RegExp(/^(?![^a-zA-Z]+$)(?!\D+$)/);
								var regulars = /\s+/g
								if(!regs.test(pwds)){
									toastr.error("至少是数字与字母的组合");
									return false;
								}else if(regulars.test(pwds)){
									toastr.error("不能输入空格");
									return false;
								}else if(pwds.length < 8 ){
									toastr.error("至少输入八位数的密码");
									return false;
								}else if(pwds.length > 16 ){
									toastr.error("密码不能超过16位");
									return false;
								}

								var form = document.getElementById('inputForm');
								//在这里手工指定提交给哪个ACTION
								//form.action = "${ctx}/zbwjbab/baZbwjbab/uploadZbwj?lx="+lx+"&qtfjid="+qtfjid;
								//执行SUBMIT
								form.submit();
							}else{
								toastr.error("密码和确认密码请保持一致");
							}
						});*/

					});

	/*function CheckIntensity(pwd) {
	var Mcolor, Wcolor, Scolor, Color_Html;
	var m = 0;
	var Modes = 0;
	for (i = 0; i < pwd.length; i++) {
	    var charType = 0;
	    var t = pwd.charCodeAt(i);
	    if (t >= 48 && t <= 57) { charType = 1; }
	    else if (t >= 65 && t <= 90) { charType = 2; }
	    else if (t >= 97 && t <= 122) { charType = 4; }
	    else { charType = 4; }
	    Modes |= charType;
	}
	for (i = 0; i < 4; i++) {
	    if (Modes & 1) { m++; }
	    Modes >>>= 1;
	}
	if (pwd.length <= 4) { m = 1; }
	if (pwd.length <= 0) { m = 0; }
	switch (m) {
	    case 1:
	        Wcolor = "pwd pwd_Weak_c";
	        Mcolor = "pwd pwd_c";
	        Scolor = "pwd pwd_c pwd_c_r";
	        Color_Html = "弱";
	        break;
	    case 2:
	        Wcolor = "pwd pwd_Medium_c";
	        Mcolor = "pwd pwd_Medium_c";
	        Scolor = "pwd pwd_c pwd_c_r";
	        Color_Html = "中";
	        break;
	    case 3:
	        Wcolor = "pwd pwd_Strong_c";
	        Mcolor = "pwd pwd_Strong_c";
	        Scolor = "pwd pwd_Strong_c pwd_Strong_c_r";
	        Color_Html = "强";
	        break;
	    default:
	        Wcolor = "pwd pwd_c";
	        Mcolor = "pwd pwd_c pwd_f";
	        Scolor = "pwd pwd_c pwd_c_r";
	        Color_Html = "无";
	        break;
	}
	
	if (pwd.length < 8) {
	    $("#disp").css({ "display": "block" })
	}else {
	    $("#disp").css({ "display": "none" })
	}
	
	var reg = new RegExp(/^(?![^a-zA-Z]+$)(?!\D+$)/);

	if (!reg.test(pwd)){
	    $("#Table1").css({ "display": "block" })
	}else{
		$("#Table1").css({ "display": "none" })
	}
	
	var regular = /\s+/g

	if( regular.test(pwd)){
	    $("#Table2").css({ "display": "block" })
	}else {
	   $("#Table2").css({ "display": "none" })
	}
	
	if(pwd.length > 16){
	  $("#Table3").css({ "display": "block" })
	}else{
	  $("#Table3").css({ "display": "none" })
	}
	document.getElementById('pwd_Weak').className = Wcolor;
	document.getElementById('pwd_Medium').className = Mcolor;
	document.getElementById('pwd_Strong').className = Scolor;
	document.getElementById('pwd_Medium').innerHTML = Color_Html;
	}*/
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
		<li><a href="${ctx}/tea/list">老师列表</a></li>
		<li class="active"><a href="${ctx}/tea/form?id=${zxsTea.id}">老师<shiro:hasPermission
					name="tea:edit:view">${not empty zxsTea.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="tea:edit:view">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="zxsTea"
		action="${ctx}/tea/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="phone" htmlEscape="false"
					maxlength="255" class="input-xlarge" />
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo"
					selectMultiple="false" maxWidth="100" maxHeight="100" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">代理或机构:</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id"
					value="${zxsTea.office.id}" labelName="office.name"
					labelValue="${zxsTea.office.name}" title="所属代理或机构"
					url="/sys/office/treeData?type=2" cssClass="required"
					notAllowSelectParent="true" notAllowSelectRoot="${root_tmp }" />
					<span class="help-inline"><font>代理或机构必填</font> </span>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">身份证:</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="25" class="no" />
				<!-- 	<span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="teaName" htmlEscape="false" maxlength="50"
					class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登录名:</label>
			<div class="controls">
				<input id="oldTeaNo" name="oldTeaNo" type="hidden"
					value="${zxsTea.teaNo}">
				<form:input path="teaNo" htmlEscape="false" minlength="7" maxlength="50"
					class="required TeaNo" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="newPassword" name="newPassword" type="password"
					autocomplete="off" onKeyUp="CheckIntensity(this.value)" value=""
					maxlength="16" minlength="4"
					class="${empty zxsTea.id?'required':''}" /> <font color="red">4-16个字符，字母加数字，不能包含空格！</font>
				<c:if test="${empty zxsTea.id}">
					<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
				<c:if test="${not empty zxsTea.id}">
					<span class="help-inline">若不修改密码，请留空。</span>
				</c:if>

				<table id="disp" border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
						<td id="" class="" style="color:red;">至少输入四位数的密码</td>
					</tr>
				</table>
				<table id="Table1" border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
						<td id="Td1" class="" style="color:red;">可以是数字与字母的组合</td>
					</tr>
				</table>
				<table id="Table2" border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
						<td id="Td2" class="" style="color:red;">不能输入空格</td>
					</tr>
				</table>
				<table id="Table3" border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
						<td id="Td3" class="" style="color:red;">密码不能超过16位</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword"
					type="password" autocomplete="off" value="" equalTo="#newPassword" />
				<c:if test="${empty zxsTea.id}">
					<span class="help-inline"><font color="red">*</font> </span>
				</c:if>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="100"
					class="email" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="25" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">自动升级:</label>
			<div class="controls">
				<form:select path="autoLevel">
					<form:options items="${fns:getDictList('Y_N')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="120" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">出生年月日:</label>
			<div class="controls">
				<input id="birth" name="birth" type="text" maxlength="20"
					class="input-mini Wdate"
					value="<fmt:formatDate value="${zxsTea.birth}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">曾就读过的机构名:</label>
			<div class="controls">
				<form:input path="studiedName" htmlEscape="false" maxlength="100" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历:</label>
			<div class="controls">
				<form:input path="edu" htmlEscape="false" maxlength="100" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作经历:</label>
			<div class="controls">
				<form:input path="ex" htmlEscape="false" maxlength="60" />
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:select path="sex">
					<form:options items="${fns:getDictList('sex')}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>

		<div class="control-group">
			<label class="control-label">入职时间:</label>
			<div class="controls">
				<input id="birth" name="entryDate" type="text" maxlength="20"
					class="input-mini Wdate"
					value="<fmt:formatDate value="${zxsTea.entryDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">是否修读过珠心算课程:</label>
			<div class="controls">
				<form:select path="isStudied">
					<form:options items="${fns:getDictList('yes_no')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">珠算合格程度:</label>
			<div class="controls">
				<form:select path="zhusuanlv" class="input-medium">
					
					<form:options items="${fns:getDictList('zhusuanlv')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label">心算合格程度:</label>
			<div class="controls">
				<form:select path="xinsuanlv" class="input-medium">
					
					<form:options items="${fns:getDictList('zhusuanlv')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>


		<div class="control-group">
			<label class="control-label">是否激活:</label>
			<div class="controls">
				<form:select path="delFlag">
					<form:options items="${fns:getDictList('statu_del_flag')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<span class="help-inline"><font color="red">*</font>
					“是”代表此账号允许登录App，“否”则表示此账号不允许登录App</span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">功课历史:</label>
			<div class="controls">
			<form:textarea path="appPlayInfo.lessonsHis" htmlEscape="false" rows="7" style="width:600px;" disabled="true"
					maxlength="755" class="input-xlarge" />
					
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3"
					maxlength="255" class="input-xlarge" />
			</div>
		</div>
		<c:if test="${not empty zxsTea.id}">
			<div class="control-group">
				<label class="control-label">创建时间:</label>
				<div class="controls">
					<label class="lbl"><fmt:formatDate
							value="${zxsTea.createDate}" type="both" dateStyle="full" /></label>
				</div>
			</div>

		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="tea:edit:view">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>