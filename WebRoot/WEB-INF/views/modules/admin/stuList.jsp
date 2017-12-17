<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>学生管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function() {
			top.$.jBox.confirm("确认要导出学生数据吗？", "系统提示", function(v, h, f) {
				if (v == "ok") {
					$("#searchForm").attr("action", "${ctx}/stu/export");
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
					url : "${ctx}/stu/batchstop",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('停用成功');
						location = "${ctx}/stu/list?repage";

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
					url : "${ctx}/stu/batchstart",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('激活成功');
						location = "${ctx}/stu/list?repage";

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
					url : "${ctx}/stu/batchdelete",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('删除数据成功');
						location = "${ctx}/stu/list?repage";

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
			for (var i = 0; i < checklist.length; i++) {
				checklist[i].checked = 1;
			}
		} else {
			for (var j = 0; j < checklist.length; j++) {
				checklist[j].checked = 0;
			}
		}
	}

	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/stu/list");
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
		<form id="importForm" action="${ctx}/stu/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left:20px;text-align:center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width:330px" /><br /> <br /> <input id="btnImportSubmit"
				class="btn btn-primary" type="submit" value="   导    入   " /> <a
				href="${ctx}/stu/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/stu/list">学生列表</a></li>
		<shiro:hasPermission name="stu:edit:view">
			<li><a href="${ctx}/stu/form">学生添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="zxsStu"
		action="${ctx}/stu/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
			callback="page();" />
		<ul class="ul-form">
			<li><label>代理或机构：</label> <sys:treeselect id="office"
					name="office.id" value="${zxsStu.office.id}"
					labelName="office.name" labelValue="${zxsStu.office.name}"
					title="代理或机构" url="/sys/office/treeData?type=2"
					cssClass="input-small" allowClear="true" /></li>

			<li><label>手机号码：</label> <form:input path="tel"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li><label>身份证号：</label> <form:input path="no"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>

			<li class="clearfix"></li>
			<li><label>登录名：</label> <form:input path="stuNo"
					htmlEscape="false" maxlength="50" class="input-medium" /></li>


			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label> <form:input
					path="stuName" htmlEscape="false" maxlength="50"
					class="input-medium" /></li>

			<c:if test="${temp==0}">
				<li><label class="control-label">班级：</label> <form:select
						path="zxsClass.id" class="input-medium">
						<form:option value="" label="请选择" />
						<form:options items="${zxsClassList}" itemLabel="classNo"
							itemValue="id" htmlEscape="false" />
					</form:select></li>
			</c:if>


			<%-- 	<li><label class="control-label">激活状态：</label> <form:select
					path="delFlag" class="input-medium">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('statu_del_flag')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li> --%>


			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /> <input
				id="btnExport" class="btn btn-primary" type="button" value="导出" />

				<shiro:hasPermission name="stu:edit:view">
					<input id="btnImport" class="btn btn-primary" type="button"
						value="导入" />
				</shiro:hasPermission> <shiro:hasPermission name="stu:edit:view">
					<input id="stopry" class="btn btn-primary" type="button" value="停用" />
				</shiro:hasPermission> <shiro:hasPermission name="stu:edit:view">
					<input id="startry" class="btn btn-primary" type="button"
						value="激活" />
				</shiro:hasPermission> <shiro:hasPermission name="stu:edit:view">
					<input id="deletery" class="btn btn-primary" type="button"
						value="删除" />
				</shiro:hasPermission></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="25px" onclick="selectAll()"><label><input
						type="checkbox" id="zcheckbox" /> </label></th>
				<th style="width: 30px;">序号</th>
				<th>机构或学校</th>
				<th>班级名称</th>
				<th>登录名</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>手机</th>

				<th>珠算级别</th>
				<th>心算级别</th>
				<c:if test="${temp==0}">
					<th>功课历史</th>
				</c:if>
				<th>在线状态</th>
				<th>自动升级</th>
				<th>账号激活状态</th>
				<th>最后登录时间</th>

				<shiro:hasPermission name="stu:edit:view">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="zxsStu" varStatus='ry'>
				<tr>
					<td><input type="checkbox" name="ids" value="${zxsStu.id}" />
					</td>

					<td>${(ry.index + 1)+page.pageSize*(page.pageNo-1)}</td>

					<td>${zxsStu.office.name}</td>

					<td>${zxsStu.zxsClass.classNo}</td>

					<td><c:choose>
							<c:when test="${temp==0}">
						${zxsStu.stuNo}
						</c:when>
							<c:otherwise>
								<a href="${ctx}/stu/form?id=${zxsStu.id}">${zxsStu.stuNo}</a>
							</c:otherwise>
						</c:choose></td>
						
					<td>${zxsStu.stuName}</td>
					<td>${zxsStu.sex==2?'女':'男'}</td>
					
					<td>${zxsStu.age }</td>
					<td>${zxsStu.tel}</td>

					<td>${fns:getDictLabel(zxsStu.zhusuanlv, 'zhusuanlv','初段')}</td>
					<td>${fns:getDictLabel(zxsStu.xinsuanlv, 'zhusuanlv','初段')}</td>

					<c:if test="${temp==0}">
						<td>${fn:replace(fn:replace(zxsStu.appPlayInfo.lessonsHis, "#", "<br/>"),"null","")}</td>
					</c:if>
					<td><c:choose>
							<c:when test="${zxsStu.statu==1}">
								<span style="color: red">在线</span>
							</c:when>
							<c:otherwise>
							未在线
						</c:otherwise>
						</c:choose></td>
						<td><c:choose><c:when test="${zxsStu.autoLevel==1}">
								是
							</c:when> <c:otherwise>
							否
						</c:otherwise></c:choose></td>
						
					<td><c:choose>
							<c:when test="${zxsStu.delFlag==0}">
								<span style="color: red">已激活</span>
							</c:when>
							<c:when test="${zxsStu.delFlag==2}">未激活</c:when>
						</c:choose></td>

					<td><fmt:formatDate value="${zxsStu.lastLoginDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>

					<shiro:hasPermission name="stu:edit:view">
						<td><a href="${ctx}/stu/form?id=${zxsStu.id}">修改</a> <a
							href="${ctx}/stu/delete?id=${zxsStu.id}"
							onclick="return confirmx('确认要删除该学生吗？', this.href)">删除</a> <c:choose>
								<c:when test="${zxsStu.delFlag==0}">

									<a href="${ctx}/stu/stop?del=2&id=${zxsStu.id}"
										onclick="return confirmx('确认要停用该学生吗？', this.href)">停用</a>
								</c:when>
								<c:when test="${zxsStu.delFlag==2}">
									<a href="${ctx}/stu/stop?del=0&id=${zxsStu.id}"
										onclick="return confirmx('确认要激活该学生吗？', this.href)">激活</a>

								</c:when>
							</c:choose></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>