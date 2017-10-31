<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1 align="center">Registration Form</h1>

<form:form name="Register" modelAttribute="user1" action="register" method="POST">
<form:label path="name"/>Name
<form:input path="name" name="name"/>
<form:errors path="name"/><br><br>

<%-- <form:label path="id"/>ID
<form:input path="id" name="id"/>
<form:errors path="id"/><br><br> --%>

<form:label path="mobile"/>Mobile Number
<form:input path="mobile" name="mobile"/>
<form:errors path="mobile"/><br><br>


<form:label path="email"/>Email
<form:input path="email"/>
<form:errors path="email"/><br><br>

<%-- <form:label path="age"/>Age
<form:input path="age" name="age"/>
<form:errors path="age"/><br><br> --%>


<%-- 
<form:label path="username"/>Username
<form:input path="username" name="username"/>
<form:errors path="username"/><br><br> --%>

<form:label path="password"/>Password
<form:password path="password" name="password"/>
<form:errors path="password"/><br><br>

<div>${reg}</div>
<input type="submit" value="Register">
</form:form>
<a href="lo">Login</a>

</body>
</html>