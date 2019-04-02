<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <title>所有用户所有信息</title>
 </head>
 <style>
 .pager{
 position:fixed; bottom:100;left:40%;
 }
 </style>
  <script type="text/javascript">
  function fun1(){
    alert("已经到达最后一页了。。！！")
}
  function fun2(){
	    alert("没有上一页。。！！")
	}
  </script>
  <body>
  	<h3><center>所有用户所有信息</center></h3>
    <table width="300px" border="1" cellpadding="0" cellspacing="0" align="center">
    	<tr>
    	   <td>头像</td>
    		<td>编号</td>
    		<td>用户名</td>
    		<td>密码</td>
    		<td>操作</td>
    	</tr>
    	<c:forEach var="list"  items="${addLists}">
    	<tr>
    	 <td><img src="${basePath}${list.image}" width="50" height="50"></td>
    		<td>${list.id}</td>
    		<td>${list.username}</td>
    		<td>${list.password}</td>
    		<td><a href="modify.do?id=${list.id}">更新</a>    <a href="del.do?id=${list.id}">删除</a></td>
    	</tr>
    	</c:forEach> 
    </table>
    <div class="pager">
           <font size="2">第  
            ${page.pageNow} 页</font><font size="2">共 ${page.totalPageCount} 页</font> <a href="getAll.do?pageNow=1">首页</a>  
        <c:choose>  
            <c:when test="${page.pageNow - 1 >0}">  
                <a href="getAll.do?pageNow=${page.pageNow - 1}">上一页</a>  
            </c:when>  
            <c:when test="${page.pageNow - 1 <=0}">  
                <a href="getAll.do?pageNow=1" onclick="fun2()">上一页</a>  
            </c:when>  
        </c:choose>  
        <c:choose>   
            <c:when test="${page.pageNow + 1 <= page.totalPageCount}">  
                <a href="getAll.do?pageNow=${page.pageNow + 1}">下一页</a>  
            </c:when>  
            <c:when test="${page.pageNow + 1 >page.totalPageCount}">  
                <a href="getAll.do?pageNow=${page.totalPageCount}" onclick="fun1()">下一页</a>  
            </c:when>  
        </c:choose>  
        <c:choose>  
            <c:when test="${page.totalPageCount==0}">  
                <a href="getAll.do?pageNow=${page.pageNow}">尾页</a>  
            </c:when>  
            <c:otherwise>  
                <a href="getAll.do?pageNow=${page.totalPageCount}">尾页</a>  
            </c:otherwise>  
        </c:choose> 
       </div>
  </body>
  
</html>
