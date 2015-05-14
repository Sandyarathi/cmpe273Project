<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays"
	import="com.restfb.DefaultFacebookClient"
	import="com.restfb.FacebookClient"
	import="com.restfb.types.Page"
	import="com.restfb.types.User"
	import="com.restfb.Connection"
	import="com.restfb.DefaultFacebookClient"
	import="com.restfb.Facebook"
	import="com.restfb.FacebookClient"
	import="com.restfb.Parameter"
	import="com.restfb.types.Post"
	import="com.restfb.FacebookClient.AccessToken"
	import="java.util.List"
	import="java.util.*"
	import="com.mongodb.BasicDBObject"
    import="com.mongodb.DB"
    import="com.mongodb.DBCollection"
    import="com.mongodb.AggregationOutput"
    import="com.mongodb.DBCursor"
    import="com.mongodb.MongoClient"
    import="java.net.UnknownHostException"
    import="com.sun.org.apache.bcel.internal.generic.NEW"
    import="com.mongodb.DBObject"
	import="com.mongodb.Mongo"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body background="pic.jpg";>
<% 

String MY_APP_ID="403024159903643";
String MY_APP_SECRET="15b0bf950c65802d807eb71ac932820a";
AccessToken accessToken =new DefaultFacebookClient().obtainAppAccessToken(MY_APP_ID, MY_APP_SECRET);


String permissions = "user_activities, user_birthday,user_checkins,user_events,user_groups,user_hometown,user_interests,user_location,user_status";
FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken());

User user = facebookClient.fetchObject("Your FB username", User.class);
Page pag = facebookClient.fetchObject("Your FB Page Name", Page.class);
%>
<h1 style="color:#FFF;">
<%
out.println("User name: " + user.getGender());
out.println("</br></br>Page: " + pag.getName());
%></h1><br><h2 style="color:#FFF;"><%
out.println("Page likes: " + pag.getLikes());
%></h2><br><br><%

Connection<User> myFriends = facebookClient.fetchConnection("Your FB username/friends", User.class);
Connection<Post> myFeed = facebookClient.fetchConnection("Your FB Page Name/feed", Post.class);
%>
<h3 style="color:#FFF">
<%
 out.println("Count of my friends: " + myFriends.getData().size());
 out.println("</br></br>First item in my feed: " + myFeed.getData().get(0).getMessage());
%></h3><br>
<br>
</body>
</html>