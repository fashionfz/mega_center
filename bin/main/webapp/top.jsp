<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags'%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<style>
a{ text-decoration:none;}

html { overflow-y:hidden;overflow-x:hidden; }

</style>
<script type="text/javascript">
function logout(){
	parent.window.location="<%=basePath %>j_spring_security_logout";
}

function getName(){
	return $("#username").val();
}

</script>
</head>
<body leftmargin="0" topmargin="0" style="background-color: #ffffff">
<table cellpadding="0" cellspacing="0" width="100%" border="0px">
	<tr>
	    <td width="80%">
	        <table height="100%" border="0">
	           <tr>
	               <td width="420px"><img style="{margin-right:50px;}" height="70px" src="images/logon.jpg"/></td>
	               <td valign="bottom"><font style="font-size:30px" face=华文行楷 color=#000000>365管家</font></td>
	               <td valign="bottom"><font style="font-size:25px" color=#000000>®</font></td>
	               <td width="55%">&nbsp;</td>
                   <td valign="bottom">
	                   <font style="font-size:14px;margin-bottom:10px" face="Verdana,Helvetica" color="#000000">
	                     <img title="您好：<security:authentication property='principal.username'/>" style="{vertical-align:bottom;}" src="images/user.png"/>&nbsp;您好：   <security:authentication property="principal.authorities"></security:authentication>
	                     <security:authentication property="principal.username"></security:authentication>
	                     &nbsp;&nbsp;
	                     <a title="注  销" onclick="logout()" href="#" >注&nbsp;销</a>
	                  </font>
	                  <input type="hidden" id="username" value="<security:authentication property="principal.username"></security:authentication>"/>
                   </td>
               </tr>
            </table>	    
	    </td>
	</tr>
</table>
</body>
</html>