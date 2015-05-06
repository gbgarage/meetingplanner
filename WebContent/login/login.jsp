<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆会议系统</title>

<style>
body
{
/* background-image:url('../pics/login.png'); */
background-repeat:no-repeat;
background-position: center;
background-attachment: fixed;
background-color: #323941;
}

input[type="text"] {
border: 2px solid #32829D;
background: #ffffff;
color:#32829D;
}

input[type="password"] {
border: 2px solid #32829D;
background: #ffffff;
color:#32829D;
}

input[type="submit"] {
border: none;
background: transparent;
color:#32829D;
}

input[type="submit"]:hover {
border: thin #999999 outset;
filter:alpha(opacity=50);-moz-opacity:.5;opacity:.5;
background: #FFFFFF;
color: #000000;
}
input[type="submit"]:focus  {
border: thin #000000 solid;
background: #FFFFFF;
color: #000000;
}
input[type="submit"]:focus:hover {
border: thin #000000 solid;
filter:alpha(opacity=100);-moz-opacity:1;opacity:1;
background: #FFFFFF;
color: #000000;
}
input[type="reset"] {
border: none;
background: transparent;
color:#32829D;
}
input[type="reset"]:hover {
border: thin #999999 outset;
filter:alpha(opacity=50);-moz-opacity:.5;opacity:.5;
background: #FFFFFF;
color: #000000;
}
input[type="reset"]:focus  {
border: thin #000000 solid;
background: #FFFFFF;
color: #000000;
}
input[type="reset"]:focus:hover {
border: thin #000000 solid;
filter:alpha(opacity=100);-moz-opacity:1;opacity:1;
background: #FFFFFF;
color: #000000;
}

#box
{
position: absolute;
width: 50%;
height: 30%;
left: 42%;
top: 65%;
}

#box_error
{
position: absolute;
width: 50%;
height: 30%;
left: 39%;
top: 65%;
}

#text
{
font-family:arial;
color:white;
font-size:14px;
}

</style>
</head>
<body>

	
	<div id="box">
		<form name='f' action="<c:url value="/j_spring_security_check" />" method='POST'>
			<table>
			    <tr>
			      <td align="right"><p id="text">Username:</p></td>
			      <td align="left">
					<input type="text" name="j_username">
				</td>
			    </tr>
			    <tr>
			      <td align="right"><p id="text">Password:</p></td>
			      <td align="left"><input type="password" name="j_password"></td>
			    </tr>
			    <tr>
			      <td align="right"><input type="submit" value="Log In"></td>
			      <td align="left"><input type="reset" value="Reset"></></td>
			    </tr>
			</table>
		</form>
	</div>
	
	<p style="color:white">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
	
</body>
</html>