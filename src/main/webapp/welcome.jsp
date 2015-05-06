<%--
  Created by IntelliJ IDEA.
  User: piyushmittal
  Date: 4/20/15
  Time: 9:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page language="java" import="java.util.*" %>
<%@ page import="FacebookSync.FacebookConnection" %>
<html>
<head>
    <title>Facebook Happy Moments</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery.scrolly.min.js"></script>
    <script src="/js/skel.min.js"></script>
    <script src="/js/init.js"></script>
    <noscript>
        <link rel="stylesheet" href="/css/skel.css" />
        <link rel="stylesheet" href="/css/style.css"/>
        <link rel="stylesheet" href="/css/style-wide.css"/>
    </noscript>
    <script>
        function myFunction(i) {
            var str = "div"+i;
            //alert (str);
            if (document.getElementById(str).style.display == "none")
            {
                document.getElementById(str).style.display = "block";
            }
            else
            { document.getElementById(str).style.display = "none";}

            //document.getElementById("example").style.color = "red";
        }
    </script>
</head>
<body style="background: url('/css/images/header.jpg');">
<section id="header" class="dark" style="text-align: left;  background-image: none; padding: 0 0 0 0" >


    <header>
        <p></p>
        <h1 style="text-align: center;vertical-align: text-top">Top Facebook Moments</h1>
         <p></p>

        <UL id="example">
            <%

                FacebookConnection fb = new FacebookConnection();
                HashMap <String,ArrayList<String>> hash = fb.hello();
                int i =0;
                for(Map.Entry<String,ArrayList<String>> entry : hash.entrySet()){

            %>
            <LI><span style="width: 100%; font-size: large"  class="button scrolly" onclick="myFunction(<%=i%>)"><%=entry.getKey()%></span></LI><UL>
            <div id ="div<%=i%>" style="display: none" >
            <%
                i++;
                for(String str : entry.getValue()){

            %>
            <LI><span><i><%=str%></i></span></LI>
            <%
                }
            %>
            </div>
        </UL>
            <%
                }
            %>
        </UL>

    </header>


</section>



</body>

</html>
