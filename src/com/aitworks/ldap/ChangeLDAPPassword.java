package com.aitworks.ldap;  
import com.aitworks.sqltags.utilities.RandomHash;  
import com.aitworks.sqltags.utilities.Utilities;  
import com.aitworks.sqltags.utilities.Initialize;  
import java.io.IOException;  
import java.util.Hashtable;  
import javax.servlet.http.HttpSession;  
import javax.servlet.jsp.JspWriter;  
import netscape.ldap.LDAPv3;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPSearchResults;  
  
final public class ChangeLDAPPassword{  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   //-------------------------------------------------------------------------  
   public ChangeLDAPPassword(){  
   //-------------------------------------------------------------------------  
   }//Constructor ENDS  
  
   /**  
    * Class Constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ChangeLDAPPassword(HttpSession httpSession){  
   //-------------------------------------------------------------------------  
      setDN();  
   }// Constructor Ends  
  
   /**  
    * Class Constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ChangeLDAPPassword(HttpSession httpSession,String stringOne,  
                                              String stringTwo){  
   //-------------------------------------------------------------------------  
      session=httpSession;  
      newPassword=stringTwo;  
      password=stringOne;  
      setDN();  
  
      if(checkPassword()){  
         validateUser();  
      }  
   }//Constructor ENDS  
  
   /**  
    * Class Constructor  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ChangeLDAPPassword(HttpSession httpSession,String stringOne,  
			      String stringTwo, String stringThree){  
   //-------------------------------------------------------------------------  
      session=httpSession;  
      newPassword=stringTwo;  
      password=stringOne;  
      initSrc=stringThree;  
      setDN();  
  
      if(checkPassword())  
         validateUser();  
  
   }//Constructor ENDS  
  
   //***************************************************************************  
   //Finalizer Method  
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
   public void changePassword(){  
   //-------------------------------------------------------------------------  
      modifyUser=new ModifyLDAPUser(loginid,unit,org);  
      modifyUser.setAuthenticateName(dn);  
      modifyUser.setAuthenticatePassword(password);  
      modifyUser.setHost(host);  
      modifyUser.setPort(port);  
      modifyUser.replaceAttribute("userpassword",newPassword);  
      userValid=modifyUser.modifyEntry();  
      String messages=modifyUser.getExceptionBuffer();  
  
      if(!messages.equals(""))  
         messageBuffer=messages;  
  
   }// changePassword() ENDS  
  
   //-------------------------------------------------------------------------  
   public boolean checkPassword(){  
   //-------------------------------------------------------------------------  
      boolean hasAlpha=false;  
      boolean hasNumeric=false;  
      int     newPasswordSize=newPassword.length();  
      boolean results=false;  
      String  validChars=new String(letters);  
      String  validNumber=new String(numbers);  
     
      // make sure new password has alpha and numeric characters.  
      for(int index=0;index<newPasswordSize;index++){  
         if(validChars.indexOf(newPassword.charAt(index))>-1)  
            hasAlpha=true;  
  
         if(validNumber.indexOf(newPassword.charAt(index))>-1)  
            hasNumeric=true;  
      
         if(hasAlpha&&hasNumeric)  
            break;  
      }   
      if(newPassword.equals(password)){  
	 messageBuffer=setMessageBuffer("The new password and the old password "+  
	       "cannot be the same. Password was not changed.");  
      }  
      else if(newPassword.indexOf(loginid)!=-1){  
	 messageBuffer="Password cannot contain user id.";   
      }   
      else if(!hasAlpha||!hasNumeric){  
	 messageBuffer=setMessageBuffer("The password must be a mixture of "+  
               "alpha numeric characters.");  
      }  
      else if(newPassword.length()<passwordMinLength){  
	 messageBuffer="Your password's length, must be greater than "+  
            (passwordMinLength-1)+".";  
      }  
      else if(isInputValid(password)&&isInputValid(newPassword)&&  
              isInputValid(dn)){  
         results=true;  
      }  
      else{  
	 messageBuffer="The password's and/or DN must contain a "+"valid value: "+  
               "   password == "+password+" "+  
               "   newPassword == "+newPassword+" "+  
               "   dn == "+dn;  
      }  
  
      return results;  
   }// checkPassword() ENDS  
  
   /**  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void clearMessageBuffer(){  
   //-------------------------------------------------------------------------  
      messageBuffer="";  
   }//clearMessageBuffer() ENDS  
  
   /**  
    * The <code>getMessages</code> method returns any messages encountered  
    * while processing.  
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
   }  
  
   /**  
    * The <code>setDN</code> method reads the initializtion parameters  
    * from the session and stores them within the class hashtable.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setDN(){  
   //-------------------------------------------------------------------------  
      Initialize initialize=(Initialize)session.getAttribute(initSrc);  
      Hashtable table=new Hashtable();  
  
      if(initialize!=null)  
         table=initialize.getInitializationProperties();  
  
      if(debug){  
	 System.out.println("\n<EncryptedProperties>");   
	 utilities.enumerateList(table);  
	 System.out.println("</EncryptedProperties>\n");   
      }  
  
      loginid=(String)session.getAttribute("remoteUser");  
      unit=(String)table.get("ou");  
      authenticateName="cn="+(String)table.get("authenticateName");  
      authenticatePassword=(String)table.get("authenticatePassword");  
      gracePeriod=utilities.stringToInt((String)table.get("gracePeriod"));  
      org=(String)table.get("org");  
      host=(String)table.get("host");  
      dn="uid="+loginid+", ou="+unit+", o="+org;  
      String temp=(String)table.get("port");  
  
      try{      
         port=Integer.parseInt(temp);  
      }   
      catch(NumberFormatException exception){  
         port=389;  
      }  
  
      temp=(String)table.get("passwordMinLength");  
  
      try{      
         passwordMinLength=Integer.parseInt(temp);  
      }   
      catch(NumberFormatException exception){  
         passwordMinLength=8;  
      }  
  
   }//setDN() END  
  
   //-------------------------------------------------------------------------  
   public void setEncryptedHash(Hashtable hashtable){  
   //-------------------------------------------------------------------------  
      encryptedHash=hashtable;  
      unit=(String)encryptedHash.get("ou");  
      org=(String)encryptedHash.get("org");  
      host=(String)encryptedHash.get("host");  
      authenticateName="cn="+(String)encryptedHash.get("authenticateName");  
      authenticatePassword=(String)encryptedHash.get("authenticatePassword");  
      dn="uid="+loginid+", ou="+unit+", o="+org;  
      gracePeriod=utilities.stringToInt((String)encryptedHash.get("gracePeriod"));  
      String temp=(String)encryptedHash.get("port");  
  
      try{      
         port=Integer.parseInt(temp);  
      }   
      catch(NumberFormatException exception){  
         port=389;  
      }  
  
      if(checkPassword())  
         validateUser();  
   }  
  
   //-------------------------------------------------------------------------  
   public void setLoginID(String string){  
   //-------------------------------------------------------------------------  
      loginid=string;  
   }  
  
   //-------------------------------------------------------------------------  
   public void setNewPassword(String string){  
   //-------------------------------------------------------------------------  
      newPassword=string;  
   }  
  
   //-------------------------------------------------------------------------  
   public void setPassword(String string){  
   //-------------------------------------------------------------------------  
      password=string;  
   }  
  
   /**  
    * This method validates the user.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void validateUser(){  
   //-------------------------------------------------------------------------  
      userValid=false;  
      boolean okToContinue=true;  
      LDAPConnection connection=new LDAPConnection();  
  
      if(newPassword.equals(password))  
			messageBuffer="The new password and old password must be different.";  
      else{  
			try{  
				connection.connect(host,port);  
				connection.authenticate(3,dn,password);  
				String searchFilter = "(uid="+loginid+")";  
				connection.search(dn,LDAPv3.SCOPE_SUB,searchFilter,attributeList,false);  
				changePassword();  
			}  
			catch(LDAPException exception){  
				switch(exception.getLDAPResultCode()){  
					case 32:  
						messageBuffer="Their is no such DN: "+dn+  
						" Operation Failed, invalid user!";  
						okToContinue=false;  
						break;  
					case 49:  
						messageBuffer="Your login password was not valid.";  
						okToContinue=false;  
						break;  
					default:  
						messageBuffer="Authentication failed for "+dn+": "+exception;  
						okToContinue=false;  
						break;  
				}  
			}  
  
			try{  
				connection.disconnect();  
			}  
			catch(LDAPException exception){  
				System.out.println("unable to disconnect: "+exception);  
			}  
      }  
   }// validateUser() ENDS  
  
   //-------------------------------------------------------------------------  
   public static void main(String[] args){   
   //-------------------------------------------------------------------------  
      ChangeLDAPPassword change=new ChangeLDAPPassword();  
      Hashtable encryptedHash=null;  
      Utilities utilities=new Utilities();  
      RandomHash randomHash=new RandomHash();  
      String file="";  
      String done="";  
  
      while(!done.toLowerCase().equals("q")){  
         // get command line parameters  
	 change.setLoginID(utilities.promptCommandLine("Enter loginid: "));  
	 change.setPassword(utilities.promptCommandLine("Enter current password: "));  
	 change.setNewPassword(utilities.promptCommandLine("Enter new password: "));  
         file=utilities.promptCommandLine("Location of encrypted file: ");  
  
         //read in the encryped file  
         encryptedHash=randomHash.readEncryptedFile(file);  
  
         //display the key/value pair contained within the encrypted file.  
         System.out.println("\n<EncryptedProperties>");   
         utilities.enumerateList(encryptedHash);  
         System.out.println("</EncryptedProperties>\n");   
  
         //execute the change  
         change.setEncryptedHash(encryptedHash);  
  
	 done=utilities.promptCommandLine("Find another user? 'q' to quit:");  
      }  
  
      System.out.println("done");   
   }//main() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ChangeLDAPPassword: "+cr);  
      buffer.append(tab+"attributeList="+attributeList+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"dn="+dn+cr);  
      buffer.append(tab+"encryptedHash="+encryptedHash+cr);  
      buffer.append(tab+"gracePeriod="+gracePeriod+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"initSrc="+initSrc+cr);  
      buffer.append(tab+"ldapPasswordExpiredControl="+ldapPasswordExpiredControl+cr);  
      buffer.append(tab+"ldapPasswordExpiringControl="+ldapPasswordExpiringControl+cr);  
      buffer.append(tab+"letters="+letters+cr);  
      buffer.append(tab+"loginid="+loginid+cr);  
      buffer.append(tab+"messageBuffer="+messageBuffer+cr);  
      buffer.append(tab+"modifyUser="+modifyUser+cr);  
      buffer.append(tab+"newPassword="+newPassword+cr);  
      buffer.append(tab+"numbers="+numbers+cr);  
      buffer.append(tab+"org="+org+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"passwordMinLength="+passwordMinLength+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"secondsToExpire="+secondsToExpire+cr);  
      buffer.append(tab+"userValid="+userValid+cr);  
      buffer.append(tab+"unit="+unit+cr);  
      buffer.append(tab+"utilities="+utilities+cr);  
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
         messageBuffer=setMessageBuffer("Blank input fields are not allowed.");  
         inputValid=false;  
      }  
  
      return inputValid;  
   }// isInputValid() ENDS  
  
   //-------------------------------------------------------------------------  
   private String setMessageBuffer(String string){  
   //-------------------------------------------------------------------------  
      StringBuffer temp=new StringBuffer(string);  
      return temp.toString();  
  
   }//getMessages() ENDS  
  
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
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private String[] attributeList={"uid", "givenName", "sn",  
					  "cn", "ojpMiddleName", "ojpSuffix",  
					  "ojpBadgeNumber", "displayName",  
					  "x500uniqueidentifier",  
					  "postalAddress", "roomNumber",  
					  "telephoneNumber",  
					  "passwordexpirationtime",  
					  "passwordExp",  
					  "passwordExpWarned",  
					  "passwordRetryCount",  
					  "retryCountResetTime",  
					  "passwordHistory",  
					  "passwordAllowChangeTime",  
					  "passwordMaxAge",  
					  "passwordExp",  
					  "passwordMinLength",  
					  "passwordKeepHistory",  
					  "passwordInHistory",  
					  "passwordChange",  
					  "passwordWarning",  
					  "passwordLockout",  
					  "passwordMaxFailure",  
					  "passwordResetDuration",  
					  "passwordUnlock",  
					  "passwordLockoutDuration",  
					  "passwordCheckSyntax",  
					  "passwordMustChange",  
					  "passwordStorageScheme",  
					  "passwordMinAge",  
					  "passwordResetFailureCount",  
					  "telephoneNumber",  
  
  
					  "telephoneNumber",  
					  "facsimileTelephoneNumber",  
					  "mobile", "pager", "mail",  
					  "mailalternateaddress",  
					  "ou", "description",  
					  "passwordMaxAge", "passwordWarning",  
					  "employeeType", "pin",  
					  "userPassword", "userCertificate",  
					  "manager", "secretary",  
					  "cACertificate",  
					  "certificateRevocationList",  
					  "ojpBuilding", "ojpApps",  
					  "ojpSecret1", "ojpSecret2",  
					  "ojpSecret3", "ojpSecret4",  
					  "ojpCentralPhone", "ojpStatus",  
					  "userpassword",  
					  "ojpSponsor", "comUid", "justiceApp"  
				       };  
  
   private String	  authenticateName;  
   private String	  authenticatePassword;  
   private boolean        debug=false;  
   private String         dn;  
   private Hashtable      encryptedHash;  
   private int            gracePeriod=0;  
   private String         host="";  
   private String         initSrc="";  
   private String         ldapPasswordExpiredControl="2.16.840.1.113730.3.4.4";  
   private String         ldapPasswordExpiringControl="2.16.840.1.113730.3.4.5";  
   private char[]         letters={'a','b','c','d','e','f','g',  
                                    'h','i','j','k','l','m','n',  
                                    'o','p','q','r','s','t','u',  
                                    'v','w','x','y','z',' ',  
                                    'A','B','C','D','E','F','G',  
                                    'H','I','J','K','L','M','N',  
                                    'O','P','Q','R','S','T','U',  
                                    'V','W','X','Y','Z'};  
   private String         loginid="";  
   private String         messageBuffer="";  
   private ModifyLDAPUser modifyUser;  
   private String         newPassword="";  
   private char[]         numbers={'0','1','2','3','4','5','6',  
                                    '7','8','9'};  
   private String         org="";  
   private JspWriter      out;  
   private String         password;  
   private int            passwordMinLength;  
   private int            port;  
   private int            secondsToExpire=-1;  
   private HttpSession    session=null;  
   private boolean        userValid=false;  
   private String         unit="";  
   private Utilities      utilities=new Utilities();  
   final static int       NO_PASSWORD_CONTROLS = 0;  
   final static int       PASSWORD_EXPIRED = -1;  
}//ChangeLDAPPassword() ENDS  
