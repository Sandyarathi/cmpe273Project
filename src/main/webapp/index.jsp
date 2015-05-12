<%--
  Created by IntelliJ IDEA.
  User: piyushmittal
  Date: 5/9/15
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Facebook Moments</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/ico" href="image/favicon.ico" />
</head>
<body style="background-color: #3B5999;">

<%
    HttpSession tok = request.getSession();
    tok.setAttribute("token",null);
%>

<section id="header" class="dark">
    <header>
        <h1>Welcome to Facebook Moments</h1>
        <p>A free site to find top moments from Facebook</p>
    </header>
    <footer>
        <a href="https://www.facebook.com/dialog/oauth?client_id=403024159903643&redirect_uri=http://localhost:8080/welcome.jsp&scope=user_posts,email,user_about_me,user_birthday,user_hometown,user_status,user_photos,user_friends,user_relationships,user_relationship_details,publish_pages,manage_pages,publish_actions,user_tagged_places,read_custom_friendlists" class="button scrolly">Login To Facebook</a>
    </footer>
</section>

<!-- script references -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>
