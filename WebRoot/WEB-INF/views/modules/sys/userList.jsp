<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils"%>
<%
	String currId = UserUtils.getUser().getId();
%>
<html>
<head>
<title>用户管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function() {
			top.$.jBox.confirm("确认要导出用户数据吗？", "系统提示", function(v, h, f) {
				if (v == "ok") {
					$("#searchForm").attr("action", "${ctx}/sys/user/export");
					$("#searchForm").submit();
				}
			}, {
				buttonsFocus : 1
			});
			top.$('.jbox-body .jbox-icon').css('top', '55px');
		});
		$("#btnImport").click(function() {
			$.jBox($("#importBox").html(), {
				title : "导入数据",
				buttons : {
					"关闭" : true
				},
				bottomText : "导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"
			});
		});

		//停用
		$("#stopry").click(function() {
			var check_value = [];
			$('input[name="ids"]:checked').each(function() {
				check_value.push($(this).val());
			});

			if (check_value.length == 0) {
				toastr.error("未请选中记录，请先选中再停用!!!");
				return;
			}

			swal({
				title : "操作提示", //弹出框的title
				text : "确定停用吗？", //弹出框里面的提示文本
				type : "warning", //弹出框类型
				showCancelButton : true, //是否显示取消按钮
				confirmButtonColor : "#DD6B55",//确定按钮颜色
				cancelButtonText : "取消",//取消按钮文本
				confirmButtonText : "是的，确定停用！",//确定按钮上面的文档
				closeOnConfirm : true
			}, function() {
				var chk_value = [];
				$('input[name="ids"]:checked').each(function() {
					chk_value.push($(this).val());
				});

				$.ajax({
					url : "${ctx}/sys/user/batchstop",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('停用成功');
						location = "${ctx}/sys/user/list?repage";

					},
					error : function(data) {
						toastr.error("fail");
					}
				});

			});

		});

		//激活
		$("#startry").click(function() {
			var check_value = [];
			$('input[name="ids"]:checked').each(function() {
				check_value.push($(this).val());
			});

			if (check_value.length == 0) {
				toastr.error("未请选中记录，请先选中再激活!!!");
				return;
			}

			swal({
				title : "操作提示", //弹出框的title
				text : "确定激活吗？", //弹出框里面的提示文本
				type : "warning", //弹出框类型
				showCancelButton : true, //是否显示取消按钮
				confirmButtonColor : "#DD6B55",//确定按钮颜色
				cancelButtonText : "取消",//取消按钮文本
				confirmButtonText : "是的，确定激活！",//确定按钮上面的文档
				closeOnConfirm : true
			}, function() {
				var chk_value = [];
				$('input[name="ids"]:checked').each(function() {
					chk_value.push($(this).val());
				});

				$.ajax({
					url : "${ctx}/sys/user/batchstart",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('激活成功');
						location = "${ctx}/sys/user/list?repage";

					},
					error : function(data) {
						toastr.error("fail");
					}
				});

			});

		});

		//删除数据
		$("#deletery").click(function() {
			var check_value = [];
			$('input[name="ids"]:checked').each(function() {
				check_value.push($(this).val());
			});

			if (check_value.length == 0) {
				toastr.error("未请选中记录，请先选中再删除!!!");
				return;
			}

			swal({
				title : "操作提示", //弹出框的title
				text : "确定删除吗？", //弹出框里面的提示文本
				type : "warning", //弹出框类型
				showCancelButton : true, //是否显示取消按钮
				confirmButtonColor : "#DD6B55",//确定按钮颜色
				cancelButtonText : "取消",//取消按钮文本
				confirmButtonText : "是的，确定删除！",//确定按钮上面的文档
				closeOnConfirm : true
			}, function() {

				var chk_value = [];
				$('input[name="ids"]:checked').each(function() {
					chk_value.push($(this).val());
				});

				$.ajax({
					url : "${ctx}/sys/user/batchdelete",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('删除数据成功');
						location = "${ctx}/sys/user/list?repage";
					},
					error : function(data) {
						toastr.error("fail");
					}
				});

			});

		});

	});
	function selectAll() {
		var checklist = document.getElementsByName("ids");
		if (document.getElementById("zcheckbox").checked) {
			for ( var i = 0; i < checklist.length; i++) {
				checklist[i].checked = 1;
			}
		} else {
			for ( var j = 0; j < checklist.length; j++) {
				checklist[j].checked = 0;
			}
		}
	}

	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/sys/user/list");
		$("#searchForm").submit();
		return false;
	}
</script>
<style>
.table-bordered th,.table-bordered td {
	padding: 8px;
}
</style>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left:20px;text-align:center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width:330px" /><br /> <br /> <input id="btnImportSubmit"
				class="btn btn-primary" type="submit" value="   导    入   " /> <a
				href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a>
		</li>
		<shiro:hasPermission name="sys:user:edit">
			<li><a href="${ctx}/sys/user/form">用户添加</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user"
		action="${ctx}/sys/user/list" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
			callback="page();" />
		<ul class="ul-form">
			<li><label>代理或机构：</label> <sys:treeselect id="office"
					name="office.id" value="${user.office.id}" labelName="office.name"
					labelValue="${user.office.name}" title="代理或机构"
					url="/sys/office/treeData?type=2" cssClass="input-small"
					allowClear="true" />
			</li>

			<li><label>手机号码：</label> <form:input path="mobile"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>
			<li><label>身份证号：</label> <form:input path="no"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>




			<li class="clearfix"></li>
			<li><label>登录名：</label> <form:input path="loginName"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>


			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label> <form:input path="name"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>

			<li><label class="control-label">用户类型：</label> <form:select
					path="userType" class="input-medium">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('sys_office_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</li>


			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /> <input
				id="btnExport" class="btn btn-primary" type="button" value="导出" />

				<shiro:hasPermission name="sys:user:edit">
					<input id="btnImport" class="btn btn-primary" type="button"
						value="导入" />
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th style="width: 30px;">序号</th>
				<th>机构类型</th>
				<th>代理或机构</th>
				<th>登录名</th>
				<th>姓名</th>
				<th>身份</th>
				<th>手机</th>
				<th>最后登录时间</th>
				<th>创建时间</th>
				<th>账号激活状态</th>
				<shiro:hasPermission name="sys:user:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="user" varStatus="ry">
				<tr>


					<td>${(ry.index + 1)+page.pageSize*(page.pageNo-1)}</td>

					<td><c:set var="leh">${fn:length(fn:split(user.office.parentIds,","))}</c:set>

						<c:choose>
							<c:when test="${leh==1}">全球总管</c:when>
							<c:when test="${leh==2}">一级代理</c:when>
							<c:when test="${leh==3}">二级代理</c:when>
							<c:otherwise>机构或学校</c:otherwise>
						</c:choose>
					</td>
					<td>${user.office.name}</td>
					<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a>
					</td>
					<td>${user.name}</td>
					<td>${user.identfy }</td>
					<td>${user.mobile}</td>
					<td><fmt:formatDate value="${user.loginDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td><fmt:formatDate value="${user.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>

					<td><c:choose>
							<c:when test="${user.delFlag==0}">
								<span style="color: red">已激活</span>
							</c:when>
							<c:when test="${user.delFlag==2}">未激活</c:when>
						</c:choose>
					</td>

					<shiro:hasPermission name="sys:user:edit">
						<td><a href="${ctx}/sys/user/form?id=${user.id}">修改</a> <a
							href="${ctx}/sys/user/delete?id=${user.id}"
							onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a> <c:choose>
								<c:when test="${user.delFlag==0}">

									<a href="${ctx}/sys/user/stop?del=2&id=${user.id}"
										onclick="return confirmx('确认要停用该用户吗？', this.href)">停用</a>
								</c:when>
								<c:when test="${user.delFlag==2}">
									<a href="${ctx}/sys/user/stop?del=0&id=${user.id}"
										onclick="return confirmx('确认要激活该用户吗？', this.href)">激活</a>
								</c:when>
							</c:choose>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>