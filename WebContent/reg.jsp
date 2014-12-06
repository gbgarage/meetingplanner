<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>用户注册</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="styles/bootstrap.min.css" rel="stylesheet" type="text/css" />
 	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet" />
	<style type="text/css">		
		html {
            background: url(pics/index_bg1.jpg) no-repeat center center fixed; 
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
	
		body {
	        padding-top: 100px;
            font-size: 16px;
            font-family: "Open Sans",serif;
            background:transparent;
	    }
	    
	    h1 {
            font-weight: 200;
            font-size: 30px;
        }
		
/* 		input.radio, input.checkbox, input.submit {
		  	width: auto;
		} */
		
		/* style form elements on focus */
		input:focus, textarea:focus {
			background: #ffc;
		}
		
		/* style error messages */
		label .feedback {
			position: absolute;
			left: 31em;
		  	right: 0;
			top: 0.5em;
			font-weight: bold;
			color:#760000;
			padding-left: 20px;
			background: url(pics/error.png) no-repeat left top;
		}
		
		/* :KLUDGE: Explicitly set the width for IE6- */
		* html .feedback{
		  width: 10em;
		}
		
		.margin-base-vertical {
	        margin: 40px 0;
	    }
	    
	    .panel {
	    	background-color: rgba(255, 255, 255, 0.8);
	    }
	</style>
	<link href="styles/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3 panel panel-default">
				<form name="registerUserForm" class="margin-base-vertical">
					<p class="text-center">
						<span><img src="pics/ctrip.bmp"></span>
						<h2 class="margin-base-vertical">注册</h2>
					</p>
					<p class="input-group">
						<span class="input-group-addon"><span class="icon-user"></span></span>
						<input type="text" class="form-control input-lg" id="userName" name="userName" placeholder="用户名(建议用邮箱名)">
					</p>
					<p class="input-group">
						<span class="input-group-addon"><span class="icon-lock"></span></span>
						<input type="password" class="form-control input-lg" id="psd" name="psd" placeholder="密码">
					</p>
					
					<p class="input-group">
						<span class="input-group-addon"><span class="icon-lock"></span></span>
						<input type="password" class="form-control input-lg" id="confirmPsd" name="confirmPsd" placeholder="确认密码">
					</p>

					<div>
						<label for="email">所属组</label> <input type="radio"
							style="width: auto;" value="1" name="userGroup" checked="checked" />android
						<input type="radio" style="width: auto;" value="2"
							name="userGroup" />ios <input type="radio" style="width: auto;"
							value="3" name="userGroup" />sender
					</div>

					<p class="text-center">
						<input type="button" class="btn btn-success btn-lg policy_button01" onclick="register();" value="注    册">
						<small class="text-muted"><a href="#" onclick="login();">返回登录</a></small>
					</p>
				</form>
			</div>
		</div>
		<div id="dialog" title="Basic dialog"></div>
	</div>

	<script type="text/javascript" charset="utf-8" src="script/jquery-1.8.2.js"></script>
	<script type="text/javascript" charset="utf-8" src="script/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript">
		function login(){
			window.location.href="login.jsp";
		}
		
		function register(){
			var userNameStr = $('#userName').val();
			var userPsdStr = $('#psd').val();
			var userPsdConfirmStr = $('#confirmPsd').val();
			var userGroupStr = $("input[name='userGroup']:checked").val();
			
			//alert(userGroupStr);
			$('.policy_button01').blur();
			
			if(userNameStr.length == 0 || userPsdStr.length == 0) {
				$("#dialog").html('请填写用户名和密码');
                $("#dialog").dialog("open");
				return false;
			} 
		
			if(userPsdStr != userPsdConfirmStr) {
				$("#dialog").html('请确认密码一致');
                $("#dialog").dialog("open");
				return false;
			}
			
			$.ajax({
                url: "userReg.do",
                data: { user_name: userNameStr,user_pwd: userPsdStr,
                		group_id: userGroupStr, role_id:3},
                type: "post",
                success: function (text) {
                    var retObj = text;
                    //alert(text);
                  if (retObj.success) {
                    	alert('注册成功,请登录');              		
                    	window.location.href="login.jsp";
                    }
                    else{
                    	alert(retObj.msg);
                    }
                    
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		
		$(function() {
			$( "#dialog" ).dialog({
				autoOpen: false,
				show: {
					effect: "blind",
					duration: 1000
				},
				hide: {
					effect: "explode",
					duration: 1000
				},
				title: "登录消息",
				closeText: "关闭"
			});
		});
		
	</script>
</body>
</html>
