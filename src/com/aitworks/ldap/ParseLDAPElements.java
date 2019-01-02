package com.aitworks.ldap;  
import com.aitworks.sqltags.utilities.ServletHelper;  
import com.aitworks.sqltags.utilities.Utilities;  
import java.util.Calendar;  
import java.util.Enumeration;  
import java.util.Hashtable;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.PageContext;  
import netscape.ldap.LDAPAttribute;  
import netscape.ldap.LDAPAttributeSet;  
import netscape.ldap.LDAPEntry;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPSearchResults;  
  
/**  
 * Class <code>ParseLDAPElements</code> is used to find entries within the LDAP  
 * database. The methods within this class <em>usually</em> require  
 * the following attributes are set.  
 * <p>  
 * <li> authentication name</li>   
 * <li> authentication password</li>  
 * <li> port defaults to 389</li>  
 * <li> host defaults to localhost</li>  
 * </p>  
 * @author  Booker Northington II  
 * @version 1.0  
 * @param   none  
 * @since   JDK1.3  
 */  
final public class ParseLDAPElements{  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * Class <code>ParseLDAPElements</code> default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ParseLDAPElements(LDAPSearchResults ldapSearchResults,int value){  
   //-------------------------------------------------------------------------  
      graceDays=value;  
      parseElements(ldapSearchResults);  
   }  
  
   //***************************************************************************  
   //Finalize Method  
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
    * The <code>getPasswordStatus</code> method returns the reason, if any, why  
    * the password validation failed.  
    * <li> password status returns 0 if no errors were detected.  
    * <li> password status returns -1 if password has expired.   
    * <li> password status returns -2 if password is expiring.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  password status.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public int getPasswordStatus(){  
   //-------------------------------------------------------------------------  
      return passwordStatus;  
   }//getPasswordStatus() ENDS  
  
   /**  
    * The <code>isTimeToChangePassword</code> method check to see if it is time  
    * for this user to change their password.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  true if the password needs to be changed  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean isTimeToChangePassword(){  
   //-------------------------------------------------------------------------  
      return isTimeToChangePassword;  
   }  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ParseLDAPElements: "+cr);  
      //buffer.append(tab+"dn="+dn+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
   /**  
    * The <code>isTimeToChangePassword</code> method check to see if it is time  
    * for this user to change their password.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  true if the password needs to be changed  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void checkPasswordTime(){  
   //-------------------------------------------------------------------------  
      int warningSize=graceDays;  
      isTimeToChangePassword=false;  
      Calendar passwordExpirationTime=Calendar.getInstance();  
      warningSize=warningSize-(warningSize*2);  
      Calendar now=Calendar.getInstance();  
      Calendar gracePeriod=Calendar.getInstance();  
      String stringTime=(String)validAttributes.get("passwordexpirationtime");  
  
      passwordExpirationTime.set(Calendar.YEAR,  
         utilities.stringToInt(stringTime.substring(0,4)));  
      passwordExpirationTime.set(Calendar.MONTH,  
         utilities.stringToInt(stringTime.substring(4,6))-1);  
      passwordExpirationTime.set(Calendar.DAY_OF_MONTH,  
         utilities.stringToInt(stringTime.substring(6,8)));  
      passwordExpirationTime.set(Calendar.HOUR,  
         utilities.stringToInt(stringTime.substring(8,10)));  
  
      passwordExpirationTime.set(Calendar.MINUTE,  
         utilities.stringToInt(stringTime.substring(10,12)));  
      passwordExpirationTime.set(Calendar.SECOND,  
         utilities.stringToInt(stringTime.substring(12,14)));  
      gracePeriod=(Calendar)passwordExpirationTime.clone();  
      gracePeriod.add(Calendar.DATE,warningSize);  
  
      if(debug){  
	 System.out.println("warningSize: "+warningSize);  
	 System.out.println("now: "+now.getTime().toString());  
	 System.out.println("passwordExpires: "+  
            passwordExpirationTime.getTime().toString());  
	 System.out.println("gracePeriod: "+gracePeriod.getTime().toString());  
      }  
  
      if(passwordExpirationTime.before(now)){  
         isTimeToChangePassword=true;  
         browserMessages.append("Password expired ");  
         browserMessages.append(passwordExpirationTime.getTime().toString());  
         browserMessages.append(". Please contact your administrator.\n");  
         passwordStatus=-1;  
      }  
      else if(now.after(gracePeriod)||now.equals(gracePeriod)){  
         isTimeToChangePassword=true;  
         browserMessages.setLength(0);  
         int daysToExpire=0;  
         passwordStatus=-2;  
  
         while(now.before(passwordExpirationTime)){  
            daysToExpire++;  
            now.add(Calendar.DATE,1);  
         }  
  
         browserMessages.append("Your password wll expire in ");  
         browserMessages.append(daysToExpire+" days. Please change it now ");  
         browserMessages.append("or risk being locked out.");  
  
         if(daysToExpire==1){  
            StringBuffer temp=new StringBuffer  
               (stringTime.substring(8,stringTime.length()-2));  
            temp.insert(2,':');  
            browserMessages.setLength(0);  
            browserMessages.append("Your password expires today at ");  
            browserMessages.append(temp);  
            browserMessages.append(". Please change it now ");  
            browserMessages.append("or risk being locked out.");  
         }  
      }  
   }// checkPasswordTime() ENDS  
  
  
   /**  
    * Class Helper. This method writes to the browser.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void parseElements(LDAPSearchResults ldapSearchResults){          
   //-------------------------------------------------------------------------  
      LDAPEntry item=null;  
      LDAPAttribute attribute=null;  
      LDAPAttributeSet set=null;  
      Enumeration enum=null;  
      Enumeration valueEnum=null;  
      String name="";  
      String cr="\n";  
      String tab="\t";  
      messages=new StringBuffer();  
      buffer=new StringBuffer();  
      browserMessages=new StringBuffer();  
  
      while (ldapSearchResults.hasMoreElements() ) {  
	 try {   
	    item = ldapSearchResults.next();  
	    set = item.getAttributeSet();  
            enum=set.getAttributes();  
            messages.setLength(0);  
            browserMessages.setLength(0);  
  
            for(;enum.hasMoreElements();){  
               attribute=(LDAPAttribute)enum.nextElement();  
               name=attribute.getName();  
               valueEnum=attribute.getStringValues();  
  
               for(;valueEnum.hasMoreElements();)  
                  buffer.append((String)valueEnum.nextElement());  
   
               if(attribute!=null){  
                  messages.append(cr+tab+name+" = "+buffer.toString());  
                  browserMessages.append("\n   "+name+" = "+buffer.toString());  
               }  
  
               validAttributes.put(name,buffer.toString());  
               buffer.setLength(0);  
            }  
  
	    String stringTime=  
               (String)validAttributes.get("passwordexpirationtime");  
  
	    if(stringTime!=null&&!stringTime.equals(""))  
	       checkPasswordTime();  
	 }  
	 catch(NumberFormatException e){  
	 }  
	 catch(LDAPException e){  
	    System.out.println("Error: " + e.toString());  
	 }  
      }  
   }//parseElements() ENDS  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private String[] attributeList={"uid", "givenName", "sn",  
					  "cn", "ojpMiddleName", "ojpSuffix",  
					  "ojpBadgeNumber", "displayName",  
					  "x500uniqueidentifier",  
					  "postalAddress", "roomNumber",  
					  "telephoneNumber",  
					  "accountUnlockTime",  
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
   private StringBuffer        buffer=new StringBuffer();  
   private StringBuffer        browserMessages=new StringBuffer();  
   private boolean             debug=false;  
   private int                 graceDays=0;  
   private boolean             isTimeToChangePassword=false;  
   private StringBuffer        messages=new StringBuffer();  
   private int                 passwordStatus=0;  
   private Utilities           utilities=new Utilities();  
   private Hashtable           validAttributes=new Hashtable();  
}//ParseLDAPElements() ENDS  
