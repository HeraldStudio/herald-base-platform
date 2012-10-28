<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p>
        <%
        String error = request.getParameter("erroe");
        if (error != null && error == "0") {
            out.println("authorized");
        }
        if (error != null && error == "1") {
            out.println("not authorized");
        }
        %>
        </p>
        <p><a href="webform.xhtml">log on with web form</a></p>
        <p><a href="ajax.xhtml">log on with ajax</a></p>
    </body>
</html>
