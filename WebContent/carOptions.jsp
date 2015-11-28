<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="automobile.Model" %> 
<%@ page import= "java.util.Locale" %>  
<%@ page import= "java.text.NumberFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="ch" scope="session" class="autoassist.ConfigHelper" />
<jsp:setProperty name="ch" property="*" />
<% Model latestModel = ch.processAttributes(request); %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= latestModel.getModelName() %></title>
</head>
<body>
<%	NumberFormat numf = NumberFormat.getInstance();
	numf.setMinimumFractionDigits(2);
	numf.setMinimumFractionDigits(2); 
%>
<h3>Your selection</h3>
<table border="1px">
<tr><td><%= latestModel.getModelName() %></td>
<td> Base Price</td><td align ='right'><%= numf.format(latestModel.getModelPrice()) %></td></tr>
<% 
	String setName = "";
	for (int i = 0; i < latestModel.getOptionSetSize(); i++) {
		setName = latestModel.getOptionSetName(i);
		out.print("<tr><td>");
		out.print(setName);
		out.print("</td><td>");
		out.print(latestModel.getOptionChoice(setName));
		out.print("</td><td align ='right'>");
		out.print(numf.format(latestModel.getOptionChoicePrice(setName)));
		out.print("</td></tr>");
	}
%>
<tr><td colspan=2><b>Total Price with these options</b></td>
	<td align = "right"><b><%=NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(latestModel.getTotalPrice()) %></b></td>
</table>
</body>
</html>