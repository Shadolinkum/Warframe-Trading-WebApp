<!DOCTYPE html>
<title>Welcome Page</title>
<head>
    Welcome to the welcome page!<br><br>
</head>
<body>
<% 
int age = Integer.parseInt(request.getParameter("age")); 
String fname = request.getParameter("fname") + " ";
String lname = request.getParameter("lname");
%>
<% if(age < 18){ %>
        Sorry ...you are too young to proceed....  
<% } else { %> 
        Welcome <%= fname + lname %>
<% } %>
</body>
</html>