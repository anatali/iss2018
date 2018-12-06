<!DOCTYPE html>
<html>
<head>
	<title>${appname}</title>
	<meta charset="utf8">  
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="./css/base.css">
</head>
<body>	
<h3>LED PAGE</h3>
 		<p>
 		<%@ page import="java.util.Date" %>
  		<%
     		double localcounter = 0; 			
   		%>
   		<strong>Current Time is</strong>: <%=new Date() %>
   		<b>localcounter =</b> <%= localcounter++ %>
 		</p>
   		<p>  		
   		The <b>cmd</b> is:${cmd} --- <b>requestCounter</b>=${requestCounter} THE LED is: <i>${ledRep}</i>
   		</p>
</body>
</html>
