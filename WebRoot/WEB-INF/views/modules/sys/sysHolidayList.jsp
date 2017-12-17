<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节假日数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

			 //编辑数据
            $("#btnEdit").click(function () {

                var chk_value  = [];

                $('input[name="ids"]:checked').each(function(){
                    chk_value.push($(this).val());
                });

                if( chk_value.length  == 0 ){

                    toastr.error("未请选中记录，请先选中再编辑!!!");
                    return ;
                }

                window.location = "${ctx}/sys/sysHoliday/form?id=" +  chk_value[0];

            }) ;

            //删除数据
            $("#btnDel").click(function () {

                var check_value  = [];
                $('input[name="ids"]:checked').each(function(){
                    check_value.push($(this).val());
                });

                if( check_value.length  == 0 ){
                    toastr.error("未请选中记录，请先选中再删除!!!");
                    return ;
                }

                swal({
                    title: "操作提示",  //弹出框的title
                    text: "确定删除吗？", //弹出框里面的提示文本
                    type: "warning",  //弹出框类型
                    showCancelButton: true, //是否显示取消按钮
                    confirmButtonColor: "#DD6B55",//确定按钮颜色
                    cancelButtonText: "取消",//取消按钮文本
                    confirmButtonText: "是的，确定删除！",//确定按钮上面的文档
                    closeOnConfirm: true
                }, function () {

                    var chk_value  = [];
                    $('input[name="ids"]:checked').each(function(){
                        chk_value.push($(this).val());
                    });

                    $.ajax({
                        url: "${ctx}/sys/sysHoliday/batchdelete",
                        type:"get",
                        data:{"datas": chk_value},
                        dataType:"json",
                        contentType:"application/json",
                        success:function(data){
                            toastr.success('删除数据成功');
                            location = "${ctx}/sys/sysHoliday/?repage";

                        },error:function(data){
                            toastr.error("fail");
                        }
                    });

                });

            });

			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="sysHoliday" action="${ctx}/sys/sysHoliday/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<shiro:hasPermission name="sys:sysHoliday:edit">
            <li class="btns" > <a href="${ctx}/sys/sysHoliday/form"><input  class="btn btn-primary" type="button" value="新增"/> </a>  </li>
            <li class="btns" ><input id="btnEdit" class="btn btn-primary" type="button" value="编辑"/>  </li>
             <li class="btns" ><input id="btnDel" class="btn btn-primary" type="button" value="删除"/>  </li></shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th></th>
                <th>序号</th>
				<shiro:hasPermission name="sys:sysHoliday:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysHoliday">
			<tr>
				<td><input type="checkbox" name="ids" value="${sysHoliday.id}"/> </td>
                <td>${status.index + 1} </td>
				<shiro:hasPermission name="sys:sysHoliday:edit"><td>
    				<a href="${ctx}/sys/sysHoliday/form?id=${sysHoliday.id}">修改</a>
					<a href="${ctx}/sys/sysHoliday/delete?id=${sysHoliday.id}" onclick="return confirmx('确认要删除该节假日数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>