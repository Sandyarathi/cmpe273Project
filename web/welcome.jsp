<%--
  Created by IntelliJ IDEA.
  User: piyushmittal
  Date: 5/11/15
  Time: 3:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page language="java" import="java.util.*" %>
<%@ page import="Facebook.FController" %>
<%@page import="com.restfb.DefaultFacebookClient" %>
<%@page import="com.restfb.Version" %>
<%@ page import="FacebookUser.UPost" %>
<%@page import="com.restfb.types.Photo" %>
<%@page import="com.restfb.types.User" %>
<%@page import="javax.swing.JOptionPane" %>
<%@page import="java.text.DateFormatSymbols" %>
<%@page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>Hello jQuery</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script src="../hello.js"></script>
</head>

<body>
<%
    HttpSession tok = request.getSession();
    FController fb = new FController();
    if (tok.getAttribute("token") == null)
    {
        fb.token = fb.getFacebookUserToken(request.getParameter("code"),fb.redirectUrl);
        tok.setAttribute("token", fb.token);
    }
    fb.fbClient = new DefaultFacebookClient(fb.token.getAccessToken(),Version.VERSION_2_2);
    //User me = fb.getInfo();
    //String profilePicture = "https://graph.facebook.com/" + me.getId() + "/picture?type=large";
%>
<div>
    <p class="greeting-id">The ID is </p>

</div>
</body>
</html>
