<%@page contentType="text/html" 
        import = "com.aitworks.creditcard.viakliks.*"
        %>
<html>
<head><title>JSP Page</title>
<%@taglib uri="/WEB-INF/lib/taglib.tld" prefix="viaklix" %>
<%
    // This is really for the input form more than for the
    // processTxn tag itself.
    //
  String hcyp_sport = request.getParameter("hcyp_sport");
  String ssl_amount= request.getParameter("ssl_amount");
  String ssl_test_mode= request.getParameter("ssl_test_mode");
  String ssl_show_form= request.getParameter("ssl_show_form");
  String ssl_card_number= request.getParameter("ssl_card_number");
  String ssl_exp_date= request.getParameter("ssl_exp_date");
  String ssl_result_format= request.getParameter("ssl_result_format");
  String ssl_salestax= request.getParameter("ssl_salestax");
  if(hcyp_sport==null)
    hcyp_sport="FoosBall";
  if(ssl_amount==null)
    ssl_amount="10.0";
  if(ssl_card_number==null)
    ssl_card_number="4427872641004797";
  if(ssl_exp_date==null)
    ssl_exp_date="1202";
  if(ssl_result_format==null)
    ssl_result_format="ASCII";
  if(ssl_salestax==null)
    ssl_salestax=".05";
  if(ssl_test_mode==null)
    ssl_test_mode="false";

 %>
</head>
<body>
<form method="post">
    <input type="hidden" name="ssl_result_format" value="ASCII">
    <input type="hidden" name="ssl_show_form" value="false">

<PRE>
    <input name="hcyp_sport" value="<%=hcyp_sport%>"> Sport
    <input name="ssl_amount" value="<%=ssl_amount%>"> amount
    <input name="ssl_card_number" value="<%=ssl_card_number%>"> Card Number
    <input name="ssl_exp_date" value="<%=ssl_exp_date%>"> exp date
    <input name="ssl_salestax" value="<%=ssl_salestax%>"> sales tax
    <select name="ssl_test_mode" value="false">
        <option value="<%=ssl_test_mode%>" selected><%=ssl_test_mode%></option>
        <option value="true">true</option>
        <option value="false">false</option>
    </select> test mode

    <input type="submit" name="f" value="OK">
</PRE>
</form>
<% if( request.getParameter("f")!=null) { %>

<viaklix:processTxn 
    id="tx" 
    ssl_merchant_id="400684"
    ssl_pin="8570"
    >
<%-- NOTE: anything StartingWith("ssl_") is automatically stuffed into the
     transaction list for ViaKlix ...
     So, unless you want to override request object (or they aren't there)
     then you don't need every attribute defined here ... however, if they
     don't come from request object then you must set them.

    ssl_result_format="<%= ssl_result_format %>"
    ssl_amount="<%= ssl_amount %>"
    ssl_test_mode="<%= ssl_test_mode %>"
    ssl_show_form="<%= ssl_show_form %>"
    ssl_card_number="<%= ssl_card_number %>"
    ssl_exp_date="<%= ssl_exp_date %>"
    ssl_salestax="<%= ssl_salestax %>"
  --%>

    <viaklix:approved>
        <B>Hurray, you've been approved!</B>
        <BR>
        <%= tx.getAttribute("ssl_result_message") %>
        <BR>
        <%= tx.getAttribute("ssl_txn_id") %>
    </viaklix:approved>

    <viaklix:declined>
        <FONT COLOR="RED">Boo Hoo, hou've been declined!</FONT>
        <BR>
        return='<%= tx.getAttribute("ssl_result") %>'
        <BR>
        message="<%= tx.getAttribute("ssl_result_message") %>"
    </viaklix:declined>

    <viaklix:failure>
        <FONT COLOR="RED">Something terrible has happened, transaction failed</FONT>
        <BR>
        <%= tx.getAttribute("exception") %>
    </viaklix:failure>

</viaklix:processTxn> 

<% } %>

</body>
</html>
