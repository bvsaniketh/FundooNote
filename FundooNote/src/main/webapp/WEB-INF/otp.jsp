<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="changepassword">
Enter the new password : <input type="password" name="password"><br>
Confirm the new password : <input type="password" name="passwordconfirm"><br>
<input type="submit" value="Enter">
<br>
<b>${message}</b>
<%
System.out.println(session);
session.invalidate(); %>
<br>
${passwordupdationinvalid}
</form>
</body>
</html>