<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆会议系统</title>
</head>
<body>
	<h3> Login with Username and Password</h3>
	${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
	
	<form name='f' action="<c:url value="/j_spring_security_check" />" method='POST'>
		<table>
			<tr>
				<td> User: <td>
				<td><input type='text' name='j_username' value=''></td>
			</tr>
			<tr>
				<td> Password: <td>
				<td><input type='password' name='j_password' value=''></td>
			</tr>	
			<tr>
				<td colspan='2'> <input name="submit" type="submit" value="submit" /></td>
			</tr>
			<tr>
				<td colspan='2'> <input name="reset" type="reset" /></td>
			</tr>					
		</table>
	</form>
	
	<p>测试用户名	＝ 1</p>
	<p>测试密码	＝ 1</p>

</body>
</html>