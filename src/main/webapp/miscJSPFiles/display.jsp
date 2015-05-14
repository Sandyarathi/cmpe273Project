<%@ page import="java.io.*,java.util.*" page language="java" contentType="text/html;  charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"     "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean  id="user" class="user.LoginUser" scope="session" ></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display</title>
</head>
<body>
<% String title="Welcome : successful login";
out.println(title);%>
<h3><center>Your Name:vamsi123</center></h3><br>
Username<%=user.getUsername()%><br>
<%session.setMaxInactiveInterval(20);
pageContext.include("login.jsp");
%>
</body>
</html>