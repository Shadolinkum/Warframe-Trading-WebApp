<%-- 
    Document   : response
    Created on : Sep 8, 2016, 10:40:26 AM
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="userp.UserData" scope="session"/>
<jsp:setProperty name="user" property="fname"/>
<jsp:setProperty name="user" property="lname"/>
<jsp:setProperty name="user" property="email"/>
<jsp:setProperty name="user" property="age"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <A HREF="formhandlerwithbean.jsp">Continue</A>
    </body>
</html>
