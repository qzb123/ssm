<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改数据</title>
  </head>
  <body>
    <form action="<%=request.getContextPath() %>/update.do" method="post">
    	用户名：<input type="text" name="username" value="${add.username}">
		密码：<input type="password" name="password" value="${add.password}">
				<input type="hidden" name="id" value="${add.id }">
  				 <input type="submit" value="提交数据">
      </form>
  </body>
</html>