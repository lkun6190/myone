<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<script src="${ctxStatic}/js/jquery-1.8.0.min.js"></script>
	<script src="${ctxStatic}/js/jquery-1.11.1.min.js"></script>
	<meta name="decorator" content="default"/>
	<style>
			.pwd{width:40px;height:20px;line-height:14px;padding-top:2px;} 
			.pwd_f{color:#BBBBBB;} 
			.pwd_c{background-color:#F3F3F3;border-top:1px solid #D0D0D0;border-bottom:1px solid #D0D0D0;border-left:1px solid #D0D0D0;} 
			.pwd_Weak_c{background-color:#FF4545;border-top:1px solid #BB2B2B;border-bottom:1px solid #BB2B2B;border-left:1px solid #BB2B2B;} 
			.pwd_Medium_c{background-color:#FFD35E;border-top:1px solid #E9AE10;border-bottom:1px solid #E9AE10;border-left:1px solid #E9AE10;} 
			.pwd_Strong_c{background-color:#3ABB1C;border-top:1px solid #267A12;border-bottom:1px solid #267A12;border-left:1px solid #267A12;} 
			.pwd_c_r{border-right:1px solid #D0D0D0;} 
			.pwd_Weak_c_r{border-right:1px solid #BB2B2B;} 
			.pwd_Medium_c_r{border-right:1px solid #E9AE10;} 
			.pwd_Strong_c_r{border-right:1px solid #267A12;}
		    #disp
		    {
                display:none;
		    }
		    #Table1
		    {
                 display:none;
		    }
             #Table2
		    {
                 display:none;
		    }
			 #Table3
		    {
                 display:none;
		    }
		</style>
	<script type="text/javascript">
		$(document).ready(function() {
			/*$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});*/
		});
		
		
	</script>
	<script type="text/javascript">
	$(function() {
		$("#btn").click(function() {
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
				
				var oldPassword = encode64($("#oldPassword").val());  //对数据加密
				var newPassword = encode64($("#newPassword").val());
				$("#oldPassword").val(oldPassword);
				$("#newPassword").val(newPassword);
				var form = document.getElementById('inputForm');
				//在这里手工指定提交给哪个ACTION
				//form.action = "${ctx}/zbwjbab/baZbwjbab/uploadZbwj?lx="+lx+"&qtfjid="+qtfjid;
				//执行SUBMIT
				form.submit(); 
			}else{
				toastr.error("新密码和确认密码请保持一致");
			}
		});
	});
	
	// base64加密开始
	var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
			+ "wxyz0123456789+/" + "=";
	
	function encode64(input) {

		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;
		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
					+ keyStr.charAt(enc3) + keyStr.charAt(enc4);
			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";
		} while (i < input.length);

		return output;
	}
	// base64加密结束
	
	
	 function CheckIntensity(pwd) {
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
    }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">旧密码:</label>
			<div class="controls">
				<input id="oldPassword" name="oldPassword" type="password" autocomplete="off" value=""   maxlength="50" minlength="3" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新密码:</label>
			<div class="controls">
				<input style="float:left;" id="newPassword" name="newPassword" type="password"  onKeyUp="CheckIntensity(this.value)" autocomplete="off" value="" maxlength="50" minlength="3" class="required"/>
				<span class="help-inline" style="float:left;"><font color="red">*</font> </span>
			<table border="0" cellpadding="0" cellspacing="0" style="float:left; margin-left: 20px;margin-top: 2px;">
				<tr align="center"> 
					<td id="pwd_Weak" class="pwd pwd_c"> </td> 
					<td id="pwd_Medium" class="pwd pwd_c pwd_f">无</td> 
					<td id="pwd_Strong" class="pwd pwd_c pwd_c_r"> </td> 
				</tr> 
			</table> 
			<table id="disp" border="0" cellpadding="0" cellspacing="0"> 
			 <tr align="center"> 
			 <td id="" class="" style="color:red;">至少输入八位数的密码</td> 
			 </tr> 
			</table>
			 <table id="Table1" border="0" cellpadding="0" cellspacing="0"> 
			 <tr align="center"> 
			  <td id="Td1" class="" style="color:red;">至少是数字与字母的组合</td> 
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
			<label class="control-label">确认新密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" autocomplete="off" value="" maxlength="50" minlength="3" class="required" equalTo="#newPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="form-actions">
			<input  class="btn btn-primary" type="button" id="btn" value="保 存"/>
		</div>
		
	</form:form>
</body>
</html>