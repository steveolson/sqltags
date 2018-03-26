<%@ page import="java.sql.*, java.io.*, java.lang.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE> jsp test page </TITLE>
</HEAD>

<BODY text="white" bgcolor="#141ee6">
<center><h2>AIT-INC Authorize Net Test<h2></center>
<FORM METHOD="post" ACTION="authorizeNet.jsp">
<input type="hidden" name="x_Login" value="testdriver">
<input type="hidden" name="x_Email" value="booker@ait-inc.com">
<input type="hidden" name="x_Company" value="AIT-INC">
<input type="hidden" name="x_Fax" value="410 435 6878">
<input type="hidden" name="x_Receipt_Link_URL" value="http://www.authorize.net">
<input type="hidden" name="x_Receipt_Link_Method" value="Link">
<input type="hidden" name="x_Receipt_Link_Text" value="Continue">
<input type="hidden" name="x_Amount" value="1007.76">
<input type="hidden" name="x_Phone" value="210 969 4343">
<input type="hidden" name="x_Description" value="1 - Testdrive NotePad">
<!--<input type="hidden" name="x_Show_Form" value="PAYMENT_FORM"> -->
<input type="hidden" name="x_Invoice_Num" value="97032">
<input type="hidden" name="x_Card_Num" value="4007000000027">
<input type="hidden" name="x_Exp_Date" value="0901">
<input type="hidden" name="x_Cust_ID" value="560">
<input type="hidden" name="x_First_Name" value="Booker">
<input type="hidden" name="x_Last_Name" value="North">
<input type="hidden" name="x_Address" value="123 Main St">
<input type="hidden" name="x_City" value="Anywhere">
<input type="hidden" name="x_State" value="UT">
<input type="hidden" name="x_Zip" value="84058">
<input type="hidden" name="x_Country" value="USA">
<input type="hidden" name="x_Phone" value="555-555-1212">
<input type="hidden" name="x_Fax" value="555-555-1000">
<input type="hidden" name="x_EMail" value="test@authorizenet.com">
<input type="hidden" name="x_Ship_To_First_Name" value="Jane">
<input type="hidden" name="x_Ship_To_Last_Name" value="Doe">
<input type="hidden" name="x_Ship_To_Company" value="">
<input type="hidden" name="x_Ship_To_Address" value="321 Any Street">
<input type="hidden" name="x_Ship_To_City" value="Anywhere">
<input type="hidden" name="x_Ship_To_State" value="UT">
<input type="hidden" name="x_Ship_To_Zip" value="84059">
<input type="hidden" name="x_Ship_To_Country" value="USA">
<input type="hidden" name="x_Test_Request" value="TRUE">

<center><br><br><br><br>
  <input name="Press to submit test" type="image" src="./pushMe.jpg" border="0" value="Press to test">
</center>

</FORM>

<%
System.gc();
%>
</BODY>
</HTML>
