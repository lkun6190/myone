<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String colorStr = "#005580";
	session.invalidate();
	Cookie[] cookies = request.getCookies();
	if (null != cookies) {
		for (int i = 0; i < cookies.length; i++) {
			if ("JSESSIONID".equalsIgnoreCase(cookies[i].getName())) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}

			if ("theme".equals(cookies[i].getName())) {
				String str_color = cookies[i].getValue();
				if ("flat".equals(str_color)) {
					colorStr = "#08c";
				} else if ("cerulean".equals(str_color)) {//天蓝
					colorStr = "#157ab5";
				} else if ("readable".equals(str_color)) {//橙色
					colorStr = "#e78b24";
				} else if ("united".equals(str_color)) {//红色
					colorStr = "#dd4814";
				} else {
					colorStr = "#005580";
				}

			}
		}
	}
%>
<html>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${fns:getConfig('productName')}登录</title>
<meta name="decorator" content="blank" />
<link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css" />
<script src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if (self.frameElement && self.frameElement.tagName == "IFRAME"
			|| $('#left').length > 0 || $('.jbox').length > 0) {
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
</script>

<link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css" />

<script src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>

<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"></script>




<style>
body {
	background: url("${ctxStatic}/images/background4.png") no-repeat 0px
		-50px;
	background-size: 100%;
	padding: 0;
	margin: 0;
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

img {
	width: 100%;
}

.red {
	width: 7%;
	position: absolute;
	top: 10%;
	left: 9%;
}

.brand {
	text-align: center;
	font-size: 34px;
	margin-top: 10%;
}

.form-signin-heading {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
	text-align: center;
}

.tittle {
	width: 29%;
	position: absolute;
	top: 10%;
	left: 17%;
}

.login {
	width: 24%;
	position: absolute;
	top: 27%;
	right: 38%;
	max-width: 408px;
}

.go {
	width: 19%;
	position: absolute;
	top: 31%;
	right: 40.6%;
	max-width: 300px;
}

.go h3 {
	padding: 0;
	margin: 0;
	margin-bottom: 10px;
}

.whrit ul {
	padding: 30px 10px 20px 10px;
	text-align: center;
	overflow: hidden;
}

.whrit input {
	width: 73%;
	margin-bottom: 15px;
}

.whrit ul li {
	line-height: 40px;
	position: relative;
}

#Verification {
	width: 70px;
	margin-left: 2px;
	border-radius: 0;
	float: left;
	margin-top: 5px;
	margin-bottom: 0;
}

input[type="checkbox"] {
	vertical-align: sub;
	margin-top: 0;
}

.whrit .btn {
	text-align: center;
	outline: none;
	display: block;
	padding: 8px;
	font-size: 14px;
	margin-top: 20px;
	color: white;
	text-shadow: 0px 0px 0px;
	/* 	background: url("${ctxStatic}/images/but.png") center center; */
}

.whrit .forget {
	float: right;
	margin-right: 6px;
	color: #333;
	text-decoration: none;
	cursor: pointer;
}
</style>
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
body { <%--
	background: url("<%=basePath%>static/images/background4.png") no-repeat
		0px -50px; --%>
	background-size: 100%; <%--
	background: <%=colorStr%>; --%>
	padding: 0;
	margin: 0;
	padding: 0;
}

ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

img {
	width: 100%;
}

.red {
	width: 7%;
	position: absolute;
	top: 10%;
	left: 9%;
	max-width: 120px;
}

.tittle {
	width: 29%;
	position: absolute;
	top: 10%;
	left: 17%;
	border-bottom: 0;
	max-width: 757px;
}

.go h3 {
	padding: 0;
	margin: 0;
	margin-bottom: 10px;
}

.whrit ul {
	padding: 30px 10px 20px 10px;
	text-align: center;
	overflow: hidden;
}

.whrit input {
	width: 73%;
}

whrit ul li {
	line-height: 40px;
}

#Verification {
	width: 70px;
	margin-left: 2px;
	border-radius: 0;
	float: left;
}

input[type="checkbox"] {
	vertical-align: sub;
	margin-top: 0;
}

.whrit .btn {
	width: 100%;
	text-align: center;
	outline: none;
	display: block;
	padding: 8px;
	font-size: 14px;
	margin-top: 8px;
	color: white;
	text-shadow: 0px 0px 0px; <%--
	background: url("<%=basePath%>static/images/but.png") center center;
	--%>
}

.whrit .forget {
	float: right;
	margin-right: 6px;
	color: #333;
	text-decoration: none;
	cursor: pointer;
}

#validateCode {
	float: left;
	margin-left: 39px;
}

.mid {
	display: block;
	float: left;
	width: 80px;
	margin-left: 4px
}

.validateCodeRefresh {
	display: block;
	float: left;
	width: 50px;
}

.ppppp {
	width: 45px;
	color: #999;
	float: left;
	text-align: right;
	line-height: 35px;
}

.header {
	height: 80px;
	padding-top: 20px;
}

.alert {
	position: relative;
	width: 300px;
	margin: 0 auto;
	*padding-bottom: 0px;
}

label.error {
	background: none;
	width: 270px;
	font-weight: normal;
	color: inherit;
	margin: 0;
}
</style>

</head>
<body>
	<div id="main">

		<form id="loginForm" class="form-signin" action="${ctx}/login"
			method="post"></form>
		<%-- <div class="header">

			<div id="messageBox"
				class="alert alert-error ${empty message ? 'hide' : ''}">
				<button data-dismiss="alert" class="close">×</button>
				<label id="loginError" class="error">${message}</label>

			</div>
		</div> --%>
		<div class="header">
			<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>

		</div>
		</div>
		
		<h1 class="form-signin-heading">珠心算教务管理系统</h1>
		<div class="login">
			<img src="<%=basePath%>static/images/gogin03.png"
				style="width:400px;height:320px" />
		</div>
		<div class="go">
			<%--  <div class="img" style="width:24px;float:left;margin-top:7px;margin-right:5px;">
                <img src="<%=basePath%>static/images/pepor.png" />
            </div>
             <h3>用户登录</h3> --%>



			<form id="loginForm" class="form-signin" action="${ctx}/login"
				method="post">
				<div class="whrit">
					<%--<input type="hidden" name="mobileLogin" value="true" />--%>
					<ul>
						<li><span class="ppppp">用户名</span><input type="text"
							class="username" id="username" name="username" placeholder="" /></li>
						<li><span class="ppppp">密 &nbsp 码</span><input
							type="password" class="password" id="password" name="password"
							autocomplete="off" placeholder="" /></li>
						<li>
							<div style="width: 100%;margin-left:5px;">
								<%--<img src="<%=basePath%>static/images/yanzhengma.png" style="height:31px"/>--%>
								<sys:validateCode name="validateCode"
									inputCssStyle="margin-bottom:0;" />
							</div>

						</li>
						<%--  <li style="clear:both;">
                        <span style="float:left;margin-left:6px;"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''} style="width:auto;"/>记住密码</span>
                        <a class="forget">忘记密码</a>
                    </li> --%>
					</ul>

					<input class="btn btn-large btn-primary" type="submit"
						style="background:<%=colorStr%>" value="登      录" />
				</div>
				<div id="themeSwitch" class="dropdown"
					style="float:right;margin-right:1px;">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"
						style="color:<%=colorStr%>"> <c:choose>
							<c:when test="${cookie.iparea.value=='1'}">中国大陆</c:when>
							<c:when test="${cookie.iparea.value=='2'}">香港</c:when>
							
							<c:otherwise>中国大陆</c:otherwise>
		
						</c:choose> <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"
							onclick="location='/rtedu/theme/default?url='+location.href">中国大陆</a></li>
						<li><a href="#"
							onclick="location='/rtedu/theme/cerulean?url='+location.href">香港</a></li>
					</ul>
					<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
				</div>
			</form>
		</div>


	</div>
</body>

</html>
