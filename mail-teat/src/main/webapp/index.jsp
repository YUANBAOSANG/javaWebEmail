<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>发送邮件</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/RegisterServlet.do" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="username"><br/>
        密码：<input type="password" name="password"><br/>
        邮箱：<input type="text" name="email"><br/>
        头像(仅限jpg格式)：<input type="file" name="head"><br/>
        <input type="submit" value="注册">
    </form>

</body>
</html>
