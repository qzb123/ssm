<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script src="${pageContext.request.contextPath}/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
//更换验证码
function changeCaptcha() {
    $("#captchaImg").attr("src","${pageContext.request.contextPath}/captchaServlet?t="+(new Date().getTime()));
}
</script>
  <head>
    <title>用户登录</title>
  </head>
  <body>
    <form action="<%=request.getContextPath() %>/login.do" method="post">
    	<label>用户名: </label><input type="text" name="username" ><br>
		<label>密码: </label><input type="password" name="password" ><br>
		<div class="form-group">
      <label for="code" class="sr-only">验证码</label>
      <input type="text" id="code"  name="code"  class="form-control" placeholder="验证码" onblur="checkCode()" >
</div>

<div>
      <img id="captchaImg" style="CURSOR: pointer" onclick="changeCaptcha()" title="看不清楚?请点击刷新验证码!" align='absmiddle' src="${pageContext.request.contextPath }/captchaServlet" height="18" width="55">
    <a href="javascript:;" onClick="changeCaptcha()" style="color: #666;">看不清楚</a> <span id="code_span" style="color: red"></span>
</div>
   			<input type="submit" value="登录">
      </form>
  </body>
</html>