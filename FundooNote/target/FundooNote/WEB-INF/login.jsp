<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1 align="center">Login Form</h1>
	<form:form name="Login" modelAttribute="loginuser" action="login"
		method="POST">
		<form:label path="email" />Email
<form:input path="email" name="email" />
		<br>
		<form:label path="password" />Password
<form:password path="password" name="password" />
		<br>
		<div>${message}</div>
		<br>

		<input type="submit" value="Login">
		<br>
		<br>

		<b> ${passwordupdated} </b>
		<a href="Register">New User?? Sign Up</a>
		<br>
		<br>
		<a href="forgot">Forgot Password ?</a>
		<br>
		<br>
	</form:form>
	<a
		href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/MVCLogin2/googlelogin&response_type=code&client_id=948035486059-ha0v7orrbp2btbbs0lo0g55h40gah113.apps.googleusercontent.com">
		Google Log In </a>
	<br>
	<br>
	<a href="loginG">Gmail Log In</a>
	<br>
	<br>
	<a href="getToken">Generate JWT Token</a>
</body>
</html>
