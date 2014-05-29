<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%> 
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>    
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.po.SysUser" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" media="screen" href="css/form.css">
<script type="text/javascript">
function init(){
	var url = ""+parent.window.location;
	var name = url.substring(url.lastIndexOf("/")+1,url.length);
	if(name=="main.html"){
		parent.window.location="login.jsp";
	}
}
</script>
<title>login</title>
</head>
<body onload="init()">
 <%if(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)!=null){ %>
  <span style="color:red"><%=session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) %></span>
  <%} %>


<div id="login">

<form id="login" action="j_spring_security_check" method="post">
    <h1><img width="250px" src="images/logon.jpg"/></h1>
    <fieldset id="inputs">
        <input type="text" required="" autofocus="" placeholder="Username" id="j_username" name="j_username">   
        <input type="password" required="" placeholder="Password" id="j_password" name="j_password">
    </fieldset>
    <fieldset id="actions">
        <input type="submit" value="登      录" id="submit">
        自动登录<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
    </fieldset>
</form>
</div>
</body>
</html>