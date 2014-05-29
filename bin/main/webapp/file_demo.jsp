<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file_demo</title>
</head>
<body>
<form action="upload.do" enctype="multipart/form-data" method="post">
	<input type="file" id="file" name="file"/>
	<input type="submit" value="upload"/>
</form>

<a href="queryFile.do">下载专区</a>
</body>
</html>