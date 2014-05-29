<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/amq_jquery_adapter.js"></script>
<script type="text/javascript" src="js/amq.js"></script>
<script type="text/javascript">
var amq = org.activemq.Amq;
amq.init({ 
  uri: 'amq', 
  logging: true,
  timeout: 20
});
var myHandler =
{
  rcvMessage: function(message)
  {
      //alert("received data:"+message.text);
      document.getElementById("msg").innerHTML += message.text + "<br>";
  }
};
<%
Date now = new Date();
%>
amq.addListener("<%=now.getTime()%>","topic://FirstTopic",myHandler.rcvMessage);
function go()
{
    var nickname = document.getElementById("nickname").value;
    var content = document.getElementById("keymsg").value;
    var ms = nickname + " : " +content;
    //alert("msg is "+ms);
    amq.sendMessage("topic://FirstTopic","<message>"+ms+"</message>","amq-msg-type=>'text'");
    }
</script>
        
</head>
<body>
<div style="height:400px;width:600px;border:block;overflow:auto" id="msg">
</div>
昵称：
<input type="text" id="nickname">
内容：
<input type="text" id="keymsg">
<button onclick="go()">submit</button>
</body>
</html>