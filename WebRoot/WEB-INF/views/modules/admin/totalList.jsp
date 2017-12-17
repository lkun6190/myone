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

	<form:form id="searchForm" modelAttribute="zxsTotal"
		action="${ctx}/total/list" method="post"
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

			<li><label class="control-label">年份：</label> <form:select
					path="year" class="input-medium">
					<form:option value="" label="请选择" />
					<form:options items="${yearList}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li><label class="control-label">月份：</label> <form:select
					path="month" class="input-medium">
					<form:option value="" label="请选择" />
					<form:options items="${monthList}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select></li>

			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" onclick="return page();" />
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />

	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年份</th>
				<th>月份</th>
				
					<c:choose>
					<c:when test="${hLength==1}">
						<th>一级代理数量</th>
						<th>二级代理数量</th>
						<th>机构学校数量</th>

					</c:when>
					<c:when test="${hLength==2}">
						<th>二级代理数量</th>
						<th>机构学校数量</th>


					</c:when>
					<c:when test="${hLength==3}">
						<th>机构学校数量</th>
					</c:when>
				</c:choose>

				
				<th>班级数量</th>
				<th>学生的数量</th>
				<th>老师的数量</th>

				<th>已激活学生数量</th>
				<th>已激活老师数量</th>

				<th>使用学生数量</th>
				<th>使用老师数量</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${page.list}" var="zxsTotal">
				<tr>
					<td>${zxsTotal.year}</td>
					<td>${zxsTotal.month}</td>
					
					
					<c:choose>
					<c:when test="${hLength==1}">
						<td>${zxsTotal.oneOfficeNum}</td>
						<td>${zxsTotal.twoOfficeNum}</td>
						<td>${zxsTotal.threeOfficeNum}</td>

					</c:when>
					<c:when test="${hLength==2}">
						<td>${zxsTotal.oneOfficeNum}</td>
						<td>${zxsTotal.twoOfficeNum}</td>


					</c:when>
					<c:when test="${hLength==3}">
						<td>${zxsTotal.oneOfficeNum}</td>
					</c:when>
				</c:choose>
					
					
					<td>${zxsTotal.classNum}</td>
					<td>${zxsTotal.stuNum}</td>
					<td>${zxsTotal.teaNum}</td>

					<td>${zxsTotal.flagStuNum}</td>
					<td>${zxsTotal.flagTeaNum}</td>

					<td>${zxsTotal.userStuNum}</td>
					<td>${zxsTotal.userTeaNum}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>