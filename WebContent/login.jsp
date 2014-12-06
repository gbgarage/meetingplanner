<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>用户登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="styles/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
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
	
	    .margin-base-vertical {
	        margin: 40px 0;
	    }
	    
	    .panel {
	    	background-color: rgba(255, 255, 255, 0.8);
	    }
	    
	    h1 {
            font-weight: 200;
            font-size: 30px;
        }
	    
		/* style for required labels */
		label .required {
			font-size: 0.83em;
			color:#760000;
		}
		
		input.radio, input.checkbox, input.submit {
		  	/* width: auto; */
		}
		
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
	</style>
	
  </head>
  
  <body>
  <div class="container">
  	<div class="row">
  		<div class="col-md-6 col-md-offset-3 panel panel-default">
			<form name="registerUserForm" class="margin-base-vertical">
				<p class="text-center">
					<span><img src="pics/ctrip.bmp"></span>
					<h2 class="margin-base-vertical">
						用户登录
					</h2>
				</p>
		    	
				<p class="input-group">
					<span class="input-group-addon"><span class="icon-user"></span></span>
					<input type="text" class="form-control input-lg" id="userName" name="author" placeholder="用户名">
				</p>
				
				<p class="input-group">
					<span class="input-group-addon"><span class="icon-lock"></span></span>
					<input type="password" class="form-control input-lg" id="psd" name="psd" placeholder="密码">
				</p>
				
				<p class="text-center">
					<input type="button" class="btn btn-success btn-lg policy_button01" onclick="login();" value="登    录">
					<small class="text-muted"><a href="#" onclick="register();">注册</a></small>
				</p>
				
     		</form>
  		</div>
     <div id="dialog" title="Basic dialog"></div>
  	</div>
  </div>
  
  <script type="text/javascript" charset="utf-8" src="script/jquery-1.8.2.js"></script>
  <script type="text/javascript" charset="utf-8" src="script/jquery-ui-1.10.3.custom.js"></script>
  <script type="text/javascript">
		function login(){
			var userNameStr = $('#userName').val();
			var userPsdStr = $('#psd').val();
					
			$('.policy_button01').blur();
			$.ajax({
                url: "system/userLogin.do",
                data: { user_name: userNameStr,user_pwd: userPsdStr},
                type: "post",
                success: function (text) {
                    var retObj = text;
                    
                    console.log(text);
                    
                    if (retObj.success) {
                    	window.location.href="index.jsp";
                    }
                    else{
                    	$("#dialog").html(retObj.msg);
                    	$("#dialog").dialog("open");
                    	//alert(retObj.msg);
                    }
                    
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
		}
		
		function register(){
			window.location.href="reg.jsp";
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
			

	    // 回车提交查询
	    $(document).keyup(function(event){
	    	  if(event.keyCode ==13){
				 login();
	    	  }
	   	});		    
		$("body").keydown(function() {
				if (event.keyCode == 13) {
				 login();
				}
			});
		});
	</script>
  </body>
</html>