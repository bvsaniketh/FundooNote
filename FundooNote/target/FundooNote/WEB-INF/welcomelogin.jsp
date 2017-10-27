<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 

  if(session.getAttribute("session")==null)
	{
	  System.out.println("Hi"+" "+session);
	  System.out.println("hello");
	RequestDispatcher rd=request.getRequestDispatcher("/");
	rd.forward(request,response);
	}
/*   else
  { */

%>
<h1 align="center">After Login</h1>
Welcome <h1>${logins.name}</h1> <br>

${logins.password}
<br>
${logins.user_id}
${logins.email}
<%=session.getAttribute("session" + " this is in welocome login") %>
<br>
<a href="logout">Logout</a>
<%-- <% session.invalidate();
}%> --%>
</body>
</html>