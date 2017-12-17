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
<title>班级管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function() {
			top.$.jBox.confirm("确认要导出班级数据吗？", "系统提示", function(v, h, f) {
				if (v == "ok") {
					$("#searchForm").attr("action", "${ctx}/cla/export");
					$("#searchForm").submit();
				}
			}, {
				buttonsFocus : 1
			});
			top.$('.jbox-body .jbox-icon').css('top', '55px');
		});
		//新增
		$("#addry").click(function() {

			window.location = "${ctx}/cla/form";
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
					url : "${ctx}/cla/batchdelete",
					type : "get",
					data : {
						"datas" : chk_value
					},
					dataType : "json",
					contentType : "application/json",
					success : function(data) {
						toastr.success('删除数据成功');
						location = "${ctx}/cla/list?repage";

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
		$("#searchForm").attr("action", "${ctx}/cla/list");
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
		<form id="importForm" action="${ctx}/cla/import" method="post"
			enctype="multipart/form-data" class="form-search"
			style="padding-left:20px;text-align:center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width:330px" /><br /> <br /> <input id="btnImportSubmit"
				class="btn btn-primary" type="submit" value="   导    入   " /> <a
				href="${ctx}/cla/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li><a
			href="${ctx}/sys/office/list?id=<%=id %>&parentIds=<%=ids %>&offAid=<%=offAid %>">机构列表</a>
		</li>
		<li class="active"><a href="${ctx}/cla/list">班级列表</a>
		</li>
		<li><a href="${ctx}/tea/selfList">老师列表</a>
		</li>

		<%-- <shiro:hasPermission name="class:edit:view">
			<li><a href="${ctx}/cla/form">班级添加</a></li>
		</shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="zxsClass"
		action="${ctx}/cla/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
			callback="page();" />
		<ul class="ul-form">

			<li><label>班级名称：</label> <form:input path="classNo"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>
			<li><label>课室名称：</label> <form:input path="className"
					htmlEscape="false" maxlength="50" class="input-medium" />
			</li>

			<li><label class="control-label">负责老师：</label> <form:select
					path="zxsTeaIdTemp" class="input-medium">
					<form:option label="请选择老师" value="" />
					<form:options items="${teaList}" itemLabel="teaName" itemValue="id"
						htmlEscape="false" />
				</form:select>
			</li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" /> <shiro:hasPermission
					name="class:edit:view">
					<input id="addry" class="btn btn-primary" type="button" value="新增" />
				</shiro:hasPermission> <input id="btnExport" class="btn btn-primary" type="button"
				value="导出" /> <shiro:hasPermission name="class:edit:view">
					<input id="btnImport" class="btn btn-primary" type="button"
						value="导入" />
				</shiro:hasPermission> <shiro:hasPermission name="class:edit:view">
					<input id="deletery" class="btn btn-primary" type="button"
						value="删除" />
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
				<th width="25px" onclick="selectAll()"><label><input
						type="checkbox" id="zcheckbox" /> </label></th>
				<th style="width: 30px;">序号</th>

				<th>机构或学校</th>
				<th>班级名称</th>
				<th>课室名称</th>
				<th>上课名额</th>
				<th>上课时段</th>
				<th>上课日程</th>
				<th>负责老师</th>
				<shiro:hasPermission name="tea:edit:view">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="zxsClass" varStatus="ry">
				<tr>
					<td><input type="checkbox" name="ids" value="${zxsClass.id}" />
					</td>

					<td>${(ry.index + 1)+page.pageSize*(page.pageNo-1)}</td>

					<td>${zxsClass.office.name}</td>


					<td><a href="${ctx}/cla/form?id=${zxsClass.id}">${zxsClass.classNo}</a>
					</td>
					<td>${zxsClass.className}</td>
					<td>${zxsClass.classStuNum}</td>
					<td>${zxsClass.studyTimeStart}-${zxsClass.studyTimeEnd}</td>
					<td>${fn:replace(zxsClass.studyDate, "&", ", ")}</td>
					<td>${zxsClass.zxsTea.teaName}</td>

					<shiro:hasPermission name="tea:edit:view">
						<td><a href="${ctx}/cla/form?id=${zxsClass.id}">修改</a> <a
							href="${ctx}/cla/delete?id=${zxsClass.id}"
							onclick="return confirmx('确认要删除该班级吗？删除班级后班级的学生也会删除', this.href)">删除</a>
							<a href="${ctx}/stu/selfList?classStuId=${zxsClass.id}">进入学生列表</a>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>