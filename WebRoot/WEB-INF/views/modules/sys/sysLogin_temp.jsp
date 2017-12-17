<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${fns:getConfig('productName')}登录</title>
<meta name="decorator" content="blank" />

<link rel="stylesheet" href="${ctxStatic}/css/xadmin.css" />
<script src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
		|| $('#left').length > 0 || $('.jbox').length > 0) {
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
</script>



<script src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>

<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"></script>

<script type="text/javascript">

	$(document)
		.ready(
			function() {

				$("#loginForm")
					.validate(
						{
							rules : {
								validateCode : {
									remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
								}
							},
							messages : {
								username : {
									required : "请填写用户名."
								},
								password : {
									required : "请填写密码."
								},
								validateCode : {
									remote : "验证码不正确.",
									required : "请填写验证码."
								}
							},
							errorLabelContainer : "#messageBox",
							errorPlacement : function(error,
								element) {
								error.appendTo($("#loginError")
									.parent());
							}
						});

				$(window).resize();

				$("#loginbtn").click(function() {
					$("#loginForm").submit();
				});

			});
</script>

<style>
body {
	background: url(${ctxStatic}/images/bg.png) no-repeat 0px -50px;
	background-size: 100%;
	padding: 0;
	margin: 0;
}
</style>
</head>
<body>


	<div class="login">

		<div class="header">
			<div id="messageBox"
				class="alert alert-error ${empty message ? 'hide' : ''}">
				<button data-dismiss="alert" class="close">×</button>
				<label id="loginError" class="error">${message}</label>

			</div>
		</div>


		<div class="message">珠心算-管理登录</div>
		<div id="darkbannerwrap"></div>


		<form id="loginForm" class="layui-form" action="${ctx}/login"
			method="post">
			<input name="username" placeholder="用户名" type="text"
				lay-verify="required" class="layui-input">
			<hr class="hr15">
			<input name="password" lay-verify="required" placeholder="密码"
				type="password" class="layui-input">
			<hr class="hr15">


			<div style="width: 100%;margin-left:5px;">

				<sys:validateCode name="validateCode"
					inputCssStyle="margin-bottom:0;" />
			</div>

			<hr class="hr15">


			<input value="登录" lay-submit lay-filter="login" style="width:100%;"
				type="submit">
			<hr class="hr20">

				 <div id="themeSwitch" class="dropdown"
					style="float:right;margin-right:1px;">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"
						> <c:choose>
							<c:when test="${iparea=='1'}">中国大陆</c:when>
							<c:when test="${iparea=='2'}">香港</c:when>
							
							<c:otherwise>中国大陆</c:otherwise>
		
						</c:choose> <b class="caret"></b></a>
					<ul class="dropdown-menu">  
						<li><a href="#"
							onclick="location='${app}/admin/ipToArea?type=1'">中国大陆</a></li>
						<li><a href="#"
							onclick="location='${app}/admin/ipToArea?type=2'">香港</a></li>
					</ul>
					<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
				</div>
 

		</form>
	</div>
</body>

</html>
