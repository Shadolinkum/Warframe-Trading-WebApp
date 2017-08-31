<%-- 
    Document   : formhandlerwithbean
    Created on : Sep 8, 2016, 11:18:07 AM
    Author     : Gabriel
--%>
<jsp:useBean id="user" class="userp.UserData" scope="session" /> 
<!DOCTYPE html>
<title>Welcome Page</title>
<head>
    Welcome to the welcome page!<br><br>
</head>
<body>
    <% if(user.ageValidate()){ %>
        Sorry ...you are too young to proceed....  
    <% } else { %> 
        Welcome <%= user.getFname() + " " + user.getLname() %>
    <% } %>
</body>
</html>
