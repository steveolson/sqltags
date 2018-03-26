package com.aitworks.creditcard.authorizenet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import com.sun.net.ssl.*;
import java.util.*;

public class PaymentTag extends TagSupport{
 private   String hostName="https://secure.authorize.net/gateway/transact.dll";
 protected String onError="";
 protected String onSuccess="";
 protected String x_ADC_Delim_Character="";
 protected String x_ADC_Delim_Data="TRUE";
 protected String x_ADC_URL="FALSE";
 protected String x_Version="3.0";
 protected String x_Amount="";
 protected String x_Card_Num="";
 protected String x_Exp_Date="";
 protected String x_Auth_Code="";
 protected String x_Cust_ID="";
 protected String x_First_Name="";
 protected String x_Last_Name="";
 protected String x_Email="";
 protected String x_Address="";
 protected String x_City="";
 protected String x_State="";
 protected String x_Zip="";
 protected String x_Country="";
 protected String x_Bank_ABA_Code="";
 protected String x_Bank_Acct_Num="";
 protected String x_Bank_Acct_Type="";
 protected String x_Bank_Name="";
 protected String x_Method="CC";
 protected String x_Type="";
 protected String x_Login="";      
 protected String x_Password="";	
 protected String x_Test_Request="";
 protected String x_Trans_ID="";
 protected String x_response_code="";
 protected String x_response_subcode="";
 protected String x_response_reason_code="";
 protected String x_response_reason_text="";
 protected String x_avs_code="";
 protected String x_trans_id="";
 protected String x_auth_code="";
 protected String x_Company="";
 protected String x_Fax="";
 protected String x_Receipt_Link_URL="";
 protected String x_Receipt_Link_Method="";
 protected String x_Receipt_Link_Text="";
 protected String x_Description="";
 protected String x_Show_Form="";
// protected String x_Show_Form="PAYMENT_FORM"; // shows payment form
 protected String x_Invoice_Num="";
 protected String x_Phone="";
 protected String x_Ship_To_First_Name="";
 protected String x_Ship_To_Last_Name="";
 protected String x_Ship_To_Company="";
 protected String x_Ship_To_Address="";
 protected String x_Ship_To_City="";
 protected String x_Ship_To_State="";
 protected String x_Ship_To_Zip="";
 protected String x_Ship_To_Country="";
 protected String x_Receipt_Link="";
 protected String x_Receipt_Method="";
 private   ServletRequest request;
 private   JspWriter clientPage;
 private   Hashtable responseHashtable=new Hashtable();

 public PaymentTag(){
  responseHashtable.put("1","The transaction has been approved.");
  responseHashtable.put("2","The transaction has been declined.");
  responseHashtable.put("3","The transaction has been declined.");
  responseHashtable.put("4","The transaction has been declined.");
  responseHashtable.put("5","Invalid Amount.");
  responseHashtable.put("6","Invalid Credit Card Number.");
  responseHashtable.put("7","Invalid Credit Card Expiration Date.");
  responseHashtable.put("8","Credit Card Is Expired.");
  responseHashtable.put("9","Invalid ABA Code.");
  responseHashtable.put("10","Invalid Account Number.");
  responseHashtable.put("11","Duplicate Transaction.");
  responseHashtable.put("12","Authorization Code is Required but is not present.");
  responseHashtable.put("13","Invalid Merchant Login.");
  responseHashtable.put("14","Invalid Referrer URL.");
  responseHashtable.put("15","Invalid Transaction ID.");
  responseHashtable.put("16","Transaction Not Found.");
  responseHashtable.put("17",
    "The Merchant does not accept this type of Credit Card.");
  responseHashtable.put("18",
    "ACH Transactions are not accetpted by this Merchant.");
  responseHashtable.put("19",
    "An error occurred during processing. Please try again in 5 minutes.");
  responseHashtable.put("20",
    "An error occurred during processing. Please try again in 5 minutes.");
  responseHashtable.put("21",
    "An error occurred during processing. Please try again in 5 minutes.");
  responseHashtable.put("22",
    "An error occurred during processing. Please try again in 5 minutes.");
  responseHashtable.put("23",
    "An error occurred during processing. Please try again in 5 minutes.");
  responseHashtable.put("24",
    "Nova Bank Number or Terminal ID is incorrect. Call Merchant Service Providers.");
  responseHashtable.put("25",
    "An error occurred during processing. Please try again in t minutes.");
  responseHashtable.put("26",
    "An error occurred during processing. Please try again in t minutes.");
  responseHashtable.put("27",
    "Address provided does not match billing address of cardholder.");
  responseHashtable.put("28",
    "The Merchant does not accept this type of Credit Card.");
  responseHashtable.put("29",
    "Paymentech identification numbers are incorrect. Call Merchant Service Provider.");
  responseHashtable.put("30","Invalid configuration with Processor. Call Merchant Service Provider.");
  responseHashtable.put("31","FDC Merchant ID or TerminalID is incorrect. Call Merchant Service Provider.");
  responseHashtable.put("32","Merchant Password is Invalid Or Not Present.");
  responseHashtable.put("33","Field cannot be left blank");
  responseHashtable.put("34","VITAL identification numbers are incorrect.  Call Merchant Service Provider.");
  responseHashtable.put("35","An error occurred during processing. Call Merchant Service Provider.");
 }// Constructor ENDS


 /**
  * method doStartTag()
  * @parm none
  * @return 1 if operation was successful
  * This method responds to the stat tag event generated by the jsp.
  */
 public int doStartTag(){
  int returnValue=0;

  try{
   clientPage=pageContext.getOut();
   request=pageContext.getRequest();
   setRequestParameters();
   
   if(validatePayment())
    returnValue=1;
   else
    returnValue=0;
   
  }
  catch(IOException exception){
   System.out.println("AuthorizeTag1: \n\t"+exception);
  }
  catch(ClassNotFoundException exception){
   System.out.println("AuthorizeTag2: \n\t"+exception);
  }

  return returnValue;
 }// doStartTag() ENDS


 /**
  * method validatePayment()
  * @param none
  * @return true if Authorize net accepted the transaction.
  */
 public boolean validatePayment()throws 
                 MalformedURLException, 
                 StreamCorruptedException, 
                 IOException, 
                 ClassNotFoundException{
  URL url=new URL(hostName);
  URLConnection urlConnection=url.openConnection();
  urlConnection.setDoOutput(true);
  PrintWriter outp=new PrintWriter(urlConnection.getOutputStream());
  StringBuffer dataToSend=new StringBuffer();
  boolean returnValue=true;
 
  /** 
   * If this check fails, the transaction fails.
   *
   */
  if(x_ADC_Delim_Data.equals("TRUE") && 
   x_ADC_URL.equals("FALSE") && x_Amount != null &&
   x_Card_Num != null && x_Exp_Date != null && 
   x_Method.equals("CC") && x_Login != null && 
   x_Version.equals("3.0")){
    dataToSend.append("x_ADC_Delim_Data="+x_ADC_Delim_Data+ 
     "&x_ADC_URL="  +x_ADC_URL +"&x_Version="  +x_Version+ 
     "&x_Amount="   +x_Amount+ "&x_Card_Num=" +x_Card_Num+ 
     "&x_Exp_Date=" +x_Exp_Date+ "&x_Method="   +x_Method+ 
     "&x_Login="    +x_Login
    );
  }

  dataToSend=setOptionalParameters(dataToSend);
  outp.println(dataToSend.toString());
  outp.close();

  BufferedReader in = 
   new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

  StringBuffer inputLine=new StringBuffer();
  String temp;

  while((temp=in.readLine()) != null)
   inputLine.append(temp);

  in.close(); 
  StringTokenizer tok=new StringTokenizer(inputLine.toString(), ","); 

  if(tok.hasMoreTokens()){x_response_code=tok.nextToken();}
  if(tok.hasMoreTokens()){x_response_subcode=tok.nextToken();}
  if(tok.hasMoreTokens()){x_response_reason_code=tok.nextToken();}
  if(tok.hasMoreTokens()){x_response_reason_text=tok.nextToken();}
  if(tok.hasMoreTokens()){x_auth_code=tok.nextToken();}
  if(tok.hasMoreTokens()){x_avs_code=tok.nextToken();}
  if(tok.hasMoreTokens()){x_trans_id=tok.nextToken();}

  if(x_response_reason_text == null){
   x_response_reason_text=x_response_code;
   x_response_code="3";
  }
		

  StringBuffer answer=new StringBuffer();
  answer.append("<html><BODY text=\"white\" bgcolor=\"#141ee6\">");
  answer.append("<center><h2>Response From Authorize Net<h2></center>");
  answer.append("<FORM METHOD=\"post\" ACTION=\"authorizeNet.jsp\">");
  answer.append("<center>"+x_response_code+"\n");
  answer.append(x_response_subcode+"\n");
  answer.append(x_response_reason_code+"\n");
  answer.append(x_response_reason_text+"\n");
  answer.append(x_auth_code+"\n");
  answer.append(x_avs_code+"\n");
  answer.append(x_trans_id+"\n");
  answer.append("</center></form></body></html>");
  clientPage.println(answer.toString());

  if(x_response_code.equals("1")){
   return true;
  }
  else{
   return false;
  }
 }// ValidatePayment() ENDS


 //***************************************************************************
 //class helpers
 //***************************************************************************
 /**
  * This method sets the optional parameters passed which can be set.
  */
 private StringBuffer setOptionalParameters(StringBuffer dataToSend){
  if(!x_Password.equals(""))dataToSend.append("&x_Password=" + x_Password);
  if(!x_Trans_ID.equals(""))dataToSend.append("&x_Trans_ID=" + x_Trans_ID);
  if(!x_Auth_Code.equals(""))dataToSend.append("&x_Auth_Code=" + x_Auth_Code);
  if(!x_Type.equals(""))dataToSend.append("&x_Type=" + x_Type); 
  if(!x_Last_Name.equals(""))dataToSend.append("&x_Last_Name=" + x_Last_Name); 
  if(!x_Email.equals(""))dataToSend.append("&x_Email=" + x_Email); 
  if(!x_Address.equals(""))dataToSend.append("&x_Address=" + x_Address);
  if(!x_City.equals(""))dataToSend.append("&x_City=" + x_City); 
  if(!x_State.equals(""))dataToSend.append("&x_State=" + x_State); 
  if(!x_Zip.equals(""))dataToSend.append("&x_Zip=" + x_Zip); 
  if(!x_Country.equals(""))dataToSend.append("&x_Country=" + x_Country); 
  if(!x_City.equals(""))dataToSend.append("&x_City=" + x_City);
  if(!x_State.equals(""))dataToSend.append("&x_State=" + x_State);
  if(!x_Company.equals(""))dataToSend.append("&x_Company=" + x_Company);
  if(!x_Show_Form.equals(""))dataToSend.append("&x_Show_Form=" + x_Show_Form);
  if(!x_Fax.equals(""))dataToSend.append("&x_Fax=" + x_Fax);

  if(!x_First_Name.equals(""))
   dataToSend.append("&x_First_Name=" + x_First_Name); 

  if(!x_Test_Request.equals(""))
   dataToSend.append("&x_Test_Request=" + x_Test_Request);


  if(!x_Receipt_Link_URL.equals(""))
   dataToSend.append("&x_Receipt_Link_URL=" + x_Receipt_Link_URL);

  if(!x_Receipt_Link_Method.equals(""))
   dataToSend.append("&x_Receipt_Link_Method=" + x_Receipt_Link_Method);

  if(!x_Receipt_Link_Text.equals(""))
   dataToSend.append("&x_Receipt_Link_Text=" + x_Receipt_Link_Text);

  if(!x_Description.equals(""))
   dataToSend.append("&x_Description=" + x_Description);

  if(!x_Invoice_Num.equals(""))
   dataToSend.append("&x_Invoice_Num=" + x_Invoice_Num);

  if(!x_Ship_To_First_Name.equals(""))
   dataToSend.append("&x_Ship_To_First_Name=" + x_Ship_To_First_Name);

  if(!x_Ship_To_Last_Name.equals("")) 
   dataToSend.append("&x_Ship_To_Last_Name=" + x_Ship_To_Last_Name);

  if(!x_Ship_To_Company.equals(""))
   dataToSend.append("&x_Ship_To_Company=" + x_Ship_To_Company);

  if(!x_Ship_To_Address.equals(""))
   dataToSend.append("&x_Ship_To_Address=" + x_Ship_To_Address);

  if(!x_Ship_To_City.equals(""))
   dataToSend.append("&x_Ship_To_City=" + x_Ship_To_City);

  if(!x_Ship_To_State.equals(""))
   dataToSend.append("&x_Ship_To_State=" + x_Ship_To_State);

  if(!x_Ship_To_Zip.equals(""))
   dataToSend.append("&x_Ship_To_Zip=" + x_Ship_To_Zip);

  if(!x_Ship_To_Country.equals(""))
   dataToSend.append("&x_Ship_To_Country=" + x_Ship_To_Country);

  if(!x_Test_Request.equals(""))
   dataToSend.append("&x_Test_Request=" + x_Test_Request);

  return dataToSend;
 }// setOptionalParameters() ENDS

 /**
  * This method sets the parameters read from the form.
  * @param none
  * @return none
  */
 private void setRequestParameters(){
  if(request.getParameter("x_ADC_Delim_Character") != null)
   x_ADC_Delim_Character=(String)request.getParameter("x_ADC_Delim_Character");

  if(request.getParameter("x_AD_Delim_Data") != null)
   x_ADC_Delim_Data=(String)request.getParameter("x_ADC_Delim_Data");

  if(request.getParameter("x_ADC_URL") != null)
   x_ADC_URL=(String)request.getParameter("x_ADC_URL");

  if(request.getParameter("x_Amount") != null)
   x_Amount=(String)request.getParameter("x_Amount");

  if(request.getParameter("x_Card_Num") != null) 
    x_Card_Num=(String)request.getParameter("x_Card_Num");

  if(request.getParameter("x_Exp_Date") != null)
   x_Exp_Date=(String)request.getParameter("x_Exp_Date");

  if(request.getParameter("x_Auth_Code") != null)
   x_Auth_Code=(String)request.getParameter("x_Auth_Code");

  if(request.getParameter("x_Cust_ID") != null)
   x_Cust_ID=(String)request.getParameter("x_Cust_ID");

  if(request.getParameter("x_First_Name") != null)
    x_First_Name=(String)request.getParameter("x_First_Name");

  if(request.getParameter("x_Last_Name") != null)
    x_Last_Name=(String)request.getParameter("x_Last_Name");

  if(request.getParameter("x_Email")!=null)
   x_Email=(String)(request.getParameter("x_Email"));

  if(request.getParameter("x_Address") != null)
   x_Address=(String)request.getParameter("x_Address");
 
  if(request.getParameter("x_City") != null)
   x_City=(String)request.getParameter("x_City");

  if(request.getParameter("x_State") != null)
   x_State=(String)request.getParameter("x_State");

  if(request.getParameter("x_Zip") != null)
   x_Zip=(String)request.getParameter("x_Zip");

  if(request.getParameter("x_Country") != null)
   x_Country=(String)request.getParameter("x_Country");

  if(request.getParameter("x_Type") != null)
   x_Type=(String)request.getParameter("x_Type");

  if(request.getParameter("x_Login") != null)
   x_Login=(String)request.getParameter("x_Login");

  if(request.getParameter("x_Password") != null)
   x_Password=(String)request.getParameter("x_Password");

  if(request.getParameter("x_Test_Request") != null)
   x_Test_Request=(String)request.getParameter("x_Test_Request");

  if(request.getParameter("x_Trans_ID") != null)
   x_Trans_ID=(String)request.getParameter("x_Trans_ID");

  if(request.getParameter("x_Version") != null)
   x_Version=(String)request.getParameter("x_Version");

  if(request.getParameter("x_Bank_ABA_Code") != null)
   x_Bank_ABA_Code=(String)request.getParameter("x_Bank_ABA_Code");

  if(request.getParameter("x_Bank_Acct_Num") != null)
   x_Bank_Acct_Num=(String)request.getParameter("x_Bank_Acct_Num");

  if(request.getParameter("x_Bank_Acct_Type") != null)
   x_Bank_Acct_Type=(String)request.getParameter("x_Bank_Acct_Type");

  if(request.getParameter("x_Bank_Name") != null)
   x_Bank_Name=(String)request.getParameter("x_Bank_Name");

  if(request.getParameter("x_Method") != null)
   x_Method=(String)request.getParameter("x_Method");

  if(request.getParameter("x_Fax") != null)
   x_Fax=(String)request.getParameter("x_Fax");

  if(request.getParameter("x_Receipt_Link") != null)
   x_Receipt_Link=(String)request.getParameter("x_Receipt_Link");

  if(request.getParameter("x_Receipt_Method") != null)
   x_Receipt_Method=(String)request.getParameter("x_Receipt_Method");

  if(request.getParameter("x_Description") != null)
   x_Description=(String)request.getParameter("x_Description");

  if(request.getParameter("x_Show_Form") != null)
   x_Show_Form=(String)request.getParameter("x_Show_Form");

  if(request.getParameter("x_Invoice_Num") != null)
   x_Invoice_Num=(String)request.getParameter("x_Invoice_Num");

  if(request.getParameter("x_Phone") != null)
   x_Phone=(String)request.getParameter("x_Phone");

  if(request.getParameter("x_Ship_To_First_Name") != null)
   x_Ship_To_First_Name=(String)request.getParameter("x_Ship_To_First_Name");

  if(request.getParameter("x_Ship_To_Last_Name") != null)
   x_Ship_To_Last_Name=(String)request.getParameter("x_Ship_To_Last_Name");

  if(request.getParameter("x_Ship_To_Company") != null)
   x_Ship_To_Company=(String)request.getParameter("x_Ship_To_Company");

  if(request.getParameter("x_Ship_To_Address") != null)
   x_Ship_To_Address=(String)request.getParameter("x_Ship_To_Address");

  if(request.getParameter("x_Ship_To_City") != null)
   x_Ship_To_City=(String)request.getParameter("x_Ship_To_City");

  if(request.getParameter("x_Ship_To_State") != null)
   x_Ship_To_State=(String)request.getParameter("x_Ship_To_State");

  if(request.getParameter("x_Ship_To_Zip") != null)
   x_Ship_To_Zip=(String)request.getParameter("x_Ship_To_Zip");

  if(request.getParameter("x_Ship_To_Country") != null)
   x_Ship_To_Country=(String)request.getParameter("x_Ship_To_Country");
 }
}//PaymentTag() ENDS
