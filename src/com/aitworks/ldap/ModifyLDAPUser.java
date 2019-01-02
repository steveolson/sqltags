package com.aitworks.ldap;  
import com.aitworks.sqltags.utilities.Utilities;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import netscape.ldap.LDAPAttribute;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPModification;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPModificationSet;  
  
final public class ModifyLDAPUser {  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * <b>ModifyLDAPUser</b> default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ModifyLDAPUser(){  
   //-------------------------------------------------------------------------  
   }// Constructor() ENDS  
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ModifyLDAPUser(String stringOne, String stringTwo, String stringThree){  
   //-------------------------------------------------------------------------  
      loginid=stringOne;  
      unit=stringTwo;  
      org=stringThree;  
   }// Constructor() ENDS  
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   nameVector users to add to rolodex  
    * @param   unit the unit were adding the user to  
    * @param   org the org were adding the user to  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ModifyLDAPUser(String stringOne, String stringTwo,   
                         String stringThree, JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      loginid=stringOne;  
      unit=stringTwo;  
      org=stringThree;  
      out=jspWriter;  
   }// Constructor() ENDS  
  
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
    * This method adds an attribute from LDAP  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   stringOne the attributes name  
    * @param   stringTwo the attributes value   
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void addAttribute(String stringOne, String stringTwo){  
   //-------------------------------------------------------------------------  
      LDAPAttribute attr=new LDAPAttribute(stringOne,stringTwo);  
      attrSet.add(LDAPModification.ADD, attr);  
   }//addAttribute() ENDS  
  
   /**  
    * This method removes an attribute from LDAP  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the attribute to remove  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void deleteAttribute(String string){  
   //-------------------------------------------------------------------------  
      LDAPAttribute attr=new LDAPAttribute(string);  
      attrSet.add(LDAPModification.DELETE, attr);  
   }// deleteAttribute() ENDS  
  
   /**  
    * This method modifies an LDAP entry  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getExceptionBuffer(){  
   //-------------------------------------------------------------------------  
      return exceptionBuffer.toString();  
   }  
  
   /**  
    * The <b>modifyACL</b> method is used to initialize LDAPS ACL list.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void modifyACL(){  
   //-------------------------------------------------------------------------  
      String entryDN="ou="+unit+", o="+org;  
      String aci="(target=\"ldap:///uid=*,ou="+unit+",o="+org+"\")"+  
         "(targetattr=\"*\")"+  
         "(version 3.0;"+  
         "acl \"All all to modify\";"+  
         "allow (write)userdn = \"ldap:///all\";)";  
  
      try{  
         connection=new LDAPConnection();  
         connection.connect(host,port);  
         connection.authenticate(authenticateName,authenticatePassword);  
  
         LDAPModification mod=new LDAPModification(LDAPModification.ADD,  
            new LDAPAttribute("aci", aci));  
         connection.modify(entryDN,mod);  
         System.out.println("Entry modified");  
     }  
     catch(LDAPException exception){  
         System.out.println("Could not modify entry: "+entryDN+" "+exception);  
     }  
   }//setUnit() ENDS  
  
   /**  
    * This method modifies an LDAP entry  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean modifyEntry(){  
   //-------------------------------------------------------------------------  
      boolean state=false;  
  
      if(checkForErrors())  
         return state;  
  
      try{  
         String dn="uid="+loginid+", ou="+unit+", o="+org;  
	 connection=new LDAPConnection();  
	 connection.connect(host,port);  
         connection.authenticate(authenticateName,authenticatePassword);  
	 connection.modify(dn,attrSet);  
	 connection.disconnect();  
         state=true;  
      }  
      catch(LDAPException e){  
         String error=e.toString();  
  
         if(error.indexOf("within password minimum age")!=1)  
            exceptionBuffer.append(  
               "Constraint violation: Your password is within minimum age."  
            );  
         else  
            exceptionBuffer.append(e.toString());  
         state=false;  
      }  
  
      return state;  
   }//modifyEntry() ENDS  
  
   /**  
    * This method replaces an attribute from LDAP  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   stringOne the attributes name  
    * @param   stringTwo the attributes new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void replaceAttribute(String stringOne, String stringTwo){  
   //-------------------------------------------------------------------------  
      LDAPAttribute attr=new LDAPAttribute(stringOne,stringTwo);  
      attrSet.add( LDAPModification.REPLACE, attr);  
   }  
  
   /**  
    * This method sets the name we will use to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the name use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticateName(String string){  
   //-------------------------------------------------------------------------  
      authenticateName=string;  
   }// setAuthenticateName() ENDS  
  
   /**  
    * This method sets the password used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the password use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticatePassword(String string){  
   //-------------------------------------------------------------------------  
      authenticatePassword=string;  
   }// setAuthenticatePassword() ENDS  
  
   /**  
    * This method sets the host used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the host name  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setHost(String string){  
   //-------------------------------------------------------------------------  
      host=string;  
   }// setHost() ENDS  
  
   /**  
    * This method sets the loginid used to authenticate ourself to the  
    * server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the port use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setLoginID(String string){  
   //-------------------------------------------------------------------------  
      loginid=string;  
   }// setLoginID() ENDS  
  
   /**  
    * The <b>setOrg</b> method sets org  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setOrg(String string){  
   //-------------------------------------------------------------------------  
      org=string;  
   }//setOrg() ENDS  
  
   /**  
    * THe <b>setPort</b> method sets the port.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the port use to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPort(int value){   
   //-------------------------------------------------------------------------  
      port=value;  
   }// setPort() ENDS  
  
   /**  
    * The <b>setPassword</b> method sets password  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPassword(String string){  
   //-------------------------------------------------------------------------  
      password=string;  
   }//setPassword() ENDS  
  
   /**  
    * The <b>setUid</b> method sets uid  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setUid(String string){  
   //-------------------------------------------------------------------------  
      uid=string;  
   }//setUid() ENDS  
  
   /**  
    * The <b>setUnit</b> method sets unit  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setUnit(String string){  
   //-------------------------------------------------------------------------  
      unit=string;  
   }//setUnit() ENDS  
  
   /**  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   args command line args  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public static void main(String[] args){   
   //-------------------------------------------------------------------------  
      Utilities utilities=new Utilities();  
      ModifyLDAPUser modify=new ModifyLDAPUser();  
      String done="";  
      String name="";  
      String value="";  
  
      while(!done.equals("q")){  
	     modify.setAuthenticateName("cn="+  
            utilities.promptCommandLine("Enter the cn (Directory Manager):"));  
  
	     modify.setAuthenticatePassword(  
            utilities.promptCommandLine("Enter the password (yourPassword):"));  
  
	     modify.setHost(utilities.promptCommandLine("Enter the host (localhost):"));  
	     modify.setUnit(utilities.promptCommandLine("Enter the unit (People):"));  
	     modify.setPort(  
            Integer.parseInt(utilities.promptCommandLine("Enter the port (389):")));  
	     modify.setOrg(utilities.promptCommandLine("Enter the org (sgsst.com):"));  
	     modify.setLoginID(utilities.promptCommandLine("Find what loginid (test)?"));  
	     name=utilities.promptCommandLine("Attribute to modify (telephoneNumber):");  
	     value=utilities.promptCommandLine("Attribute's new value (555-1212):");  
         modify.replaceAttribute(name, value);  
         modify.modifyEntry();  
	     done=done.toLowerCase();  
	     done=utilities.promptCommandLine("Enter 'q' to quit:");  
         System.out.println("\n\n\n\n\n");  
      }  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ModifyLDAPUser: "+cr);  
      buffer.append(tab+"attrSet="+attrSet+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"connection="+connection+cr);  
      buffer.append(tab+"debug="+debug+cr);  
      buffer.append(tab+"exceptionBuffer="+exceptionBuffer+cr);  
      buffer.append(tab+"loginid="+loginid+cr);  
      buffer.append(tab+"org="+org+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"uid="+uid+cr);  
      buffer.append(tab+"unit="+unit+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //******************************************************************************  
   //Private Methods  
   //******************************************************************************  
   /**  
    * Class Helper. This method ensure expected fields are not null.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private boolean checkForErrors(){          
   //-------------------------------------------------------------------------  
      boolean badInput=false;  
  
      if(loginid==null||unit==null||org==null){  
         if(loginid==null)  
	    writeToBrowser("loginid required. See API");  
         if(unit==null)  
	    writeToBrowser("unit required. See API");  
         if(org==null)  
	    writeToBrowser("org required. See API");  
	 badInput=true;  
      }  
  
      if(authenticateName==null||authenticatePassword==null){  
	 writeToBrowser("Name and password required. See API.");  
	 badInput=true;;  
      }  
  
  
      if(host==null){  
	 writeToBrowser("Hostname and port required. See API.");  
	 badInput=true;;  
      }  
  
      if(attrSet.size()==0){  
	 writeToBrowser("You did not give me anything to change.");  
	 badInput=true;;  
      }  
      return badInput;  
   }  
  
   /**  
    * Class Helper. This method writes to the browser.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void writeToBrowser(String message){          
   //-------------------------------------------------------------------------  
      try{  
	      
         if(debug)  
            System.out.println(message+"<br>");  
  
         if(out!=null){  
	    out.println(message+"<br>");  
            out.flush();  
         }  
      }  
      catch(IOException e){  
      }  
   }  
  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private LDAPModificationSet attrSet = new LDAPModificationSet();  
   private String              authenticateName="";  
   private String              authenticatePassword="";  
   private String              host="localhost";  
   private LDAPConnection      connection;  
   private boolean             debug=false;  
   private StringBuffer        exceptionBuffer=new StringBuffer();  
   private String              loginid;  
   private String              org;  
   private JspWriter           out;  
   private String              password="";  
   private int                 port;  
   private String              uid;  
   private String              unit="";  
}//ModifyLDAPUser() ENDS  
