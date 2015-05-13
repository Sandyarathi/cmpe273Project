<%@ page import="java.io.*,java.util.*" language="java" contentType="text/html;  charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean  id="user" class="user.LoginUser" scope="session"></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>login</title>
</head>
<body>

<h1><center>Give your login details</center></h1>
<form method="post" action="check.jsp">
Username:<input type="text" name="username" size="20" value="<%=user.getUser() %>" >       <br>
Password:<input type="password" name="password" size="20" value=<%=user.getPassword()   %> ><br>
<input type="submit">
</form>

</body>
</html>