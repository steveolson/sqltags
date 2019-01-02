package com.aitworks.ldap;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPException;  
  
final public class LoginLDAPUser{  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * Class Constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public LoginLDAPUser(String stringOne, String stringTwo,JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      dn=stringOne;  
      password=stringTwo;  
      out=jspWriter;  
  
      if(isInputValid(password)&&isInputValid(dn))  
         validateUser();  
   }  
  
   /**  
    * Class Constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public LoginLDAPUser(String stringOne,String stringTwo,String stringThree,  
                                      int stringFour, JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      dn=stringOne;  
      password=stringTwo;  
      host=stringThree;  
      port=stringFour;  
      out=jspWriter;  
  
      if(isInputValid(password)&&isInputValid(dn))  
         validateUser();  
   }// Constructor Ends  
  
  
   //***************************************************************************  
   //Finalize Methods  
   //***************************************************************************  
   /**  
    * This is the finalizer method for this class  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   protected void finalize() throws Throwable{  
   //-------------------------------------------------------------------------  
      super.finalize();  
   }// finalizer() ENDS  
  
  
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************  
   /**  
    *   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean changePassword(String stringOne,String stringTwo, String stringThree){  
   //-------------------------------------------------------------------------  
      boolean success=true;  
      modifyUser=new ModifyLDAPUser(stringOne,stringTwo,stringThree);  
      modifyUser.replaceAttribute("userpassword",newPassword);  
      modifyUser.modifyEntry();  
  
      return success;  
   }// changePassword() ENDS  
  
   /**  
    *   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getMessages(){  
   //-------------------------------------------------------------------------  
      return messageBuffer;  
   }//getMessages() ENDS  
  
   /**  
    *   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean isUserValid(){  
   //-------------------------------------------------------------------------  
      return userValid;  
   }// isUserValid() ENDS  
  
   /**  
    *   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean isNewPasswordValid(String stringOne,String stringTwo){  
   //-------------------------------------------------------------------------  
      newPassword=stringOne;  
      boolean passwordValid=true;  
  
      if(newPassword.length()<6||stringTwo.length()<6){  
         messageBuffer="Password must be at least 6 characters.";  
         passwordValid=false;  
      }  
      else if(!newPassword.equals(stringTwo)){  
         messageBuffer="passwords do not match!";  
         passwordValid=false;  
      }  
  
      return passwordValid;  
   }//isNewPasswordValid() ENDS  
  
   /**  
    * <code>toString</code>  
    * <p>  
    * This method provides a default print for the class.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public String toString(){  
   //---------------------------------------------------------------------------  
      String cr="\n";  
      String tab="\t";  
      StringBuffer buffer=new StringBuffer(cr+"*****LoginLDAPUser: "+cr);  
      buffer.append(tab+"dn="+dn+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"messageBuffer="+messageBuffer+cr);  
      buffer.append(tab+"modifyUser="+modifyUser+cr);  
      buffer.append(tab+"newPassword="+newPassword+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"userValid="+userValid+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
   /**  
    * Class Helper. This method ensure expected fields are not null.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private boolean isInputValid(String string){          
   //-------------------------------------------------------------------------  
      boolean inputValid=true;  
  
      if(string==null||string.equals("")){  
         messageBuffer="Invalid input detected!";  
	 inputValid=false;  
      }  
  
      return inputValid;  
   }// isInputValid() ENDS  
  
   /**  
    * Class Helper. This method writes to the browser.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void writeToBrowser(String string){          
   //-------------------------------------------------------------------------  
      try{  
	 out.println(string+"<br>");  
         out.flush();  
      }  
      catch(IOException e){  
      }  
   }  
  
   /**  
    * This mehtod validates the user.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void validateUser(){  
   //-------------------------------------------------------------------------  
      userValid=false;  
      messageBuffer="";  
  
      try{  
         LDAPConnection connection=new LDAPConnection();  
         connection.connect(host,port);  
         connection.authenticate(3,dn,password);  
         connection.disconnect();  
         userValid=true;  
         messageBuffer="user validated successfully.";  
      }  
      catch(LDAPException exception){  
         switch(exception.getLDAPResultCode()){  
            case 32:  
               messageBuffer=dn+" is not a valid user";  
               break;  
            case 49:  
               messageBuffer="Invalid password.";  
               break;  
            default:  
               messageBuffer="Authentication failed for "+dn+": "+exception;  
               break;  
         }  
      }  
   }// validateUser() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private String         dn;  
   private String         host="sgsst.com";  
   private String         messageBuffer="user verified.";  
   private ModifyLDAPUser modifyUser;  
   private String         newPassword="";  
   private JspWriter      out;  
   private String         password;  
   private int            port=389;  
   private boolean        userValid=false;  
}   
