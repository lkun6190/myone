<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>班级管理</title>
<meta name="decorator" content="default" />

<style>
.table-bordered th,.table-bordered td {
	padding: 8px;
}
</style>
</head>
<body>

	<form:form id="searchForm" modelAttribute="zxsClass"
		action="${ctx}/total/online" method="post"
		class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}"
			callback="page();" />
		<ul class="ul-form">


			<li><label>代理或机构：</label> <sys:treeselect id="office"
					name="office.id" value="${zxsTotal.office.id}" labelName="office.name"
					labelValue="${zxsTotal.office.name}" title="代理或机构"
					url="/sys/office/treeData?type=2" cssClass="input-small"
					allowClear="true" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" />
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<c:set var="leh">${fn:length(fn:split(zxsTotal.office.parentIds,","))}</c:set>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th>机构名称</th>

				<c:choose>
					<c:when test="${leh==1}">
						<th>一级代理数量</th>
						<th>二级代理数量</th>
						<th>机构学校数量</th>

					</c:when>
					<c:when test="${leh==2}">
						<th>二级代理数量</th>
						<th>机构学校数量</th>


					</c:when>
					<c:when test="${leh==3}">
						<th>机构学校数量</th>
					</c:when>
				</c:choose>



				<th>班级数量</th>
				<th>学生的数量</th>
				<th>老师的数量</th>
				<th>在线学生数量</th>
				<th>在线老师数量</th>
				<th>已激活学生数量</th>
				<th>已激活老师数量</th>
			</tr>
		</thead>
		<tbody>

			<tr>

				<td>${zxsTotal.office.name }</td>

				<c:choose>
					<c:when test="${leh==1}">
						<td>${zxsTotal.oneOfficeNum}</td>
						<td>${zxsTotal.twoOfficeNum}</td>
						<td>${zxsTotal.threeOfficeNum}</td>

					</c:when>
					<c:when test="${leh==2}">
						<td>${zxsTotal.oneOfficeNum}</td>
						<td>${zxsTotal.twoOfficeNum}</td>


					</c:when>
					<c:when test="${leh==3}">
						<td>${zxsTotal.oneOfficeNum}</td>
					</c:when>
				</c:choose>
				</td>



				<td>${zxsTotal.classNum}</td>
				<td>${zxsTotal.stuNum}</td>
				<td>${zxsTotal.teaNum}</td>
				<td>${zxsTotal.onlineStuNum}</td>
				<td>${zxsTotal.onlineTeaNum}</td>
				<td>${zxsTotal.flagStuNum}</td>
				<td>${zxsTotal.flagTeaNum}</td>
			</tr>

		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>