package com.aitworks.ldap;  
import java.io.IOException;  
import javax.servlet.jsp.JspWriter;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPConnection;  
  
/**  
 *  
 * DeleteLDAPUsers deletes an entry from LDAP  
 *  
 **/  
final public class DeleteLDAPUser{  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   loginid the loginid  
    * @param   password the password  
    * @param   dn the dn to use  
    * @param   out the clients display   
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public DeleteLDAPUser(String stringOne, String stringTwo,   
                         Object[] object, JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      loginid=stringOne;  
      password=stringTwo;  
      dnArray=object;  
      out=jspWriter;  
      int arraySize=dnArray.length;  
      connect();  
  
      for(int index=0;index<arraySize;index++){  
         dn=(String)dnArray[index];  
         deleteItem();  
      }  
  
      disconnect();  
   }// Constructor() ENDS  
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   loginid the loginid  
    * @param   password the password  
    * @param   dn the dn to use  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public DeleteLDAPUser(String stringOne,String stringTwo,String stringThree){  
   //-------------------------------------------------------------------------  
      loginid=stringOne;  
      password=stringTwo;  
      dn=stringThree;  
      connect();  
  
      deleteItem();  
      disconnect();  
   }// Constructor() ENDS  
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   port the port to use  
    * @param   ldapVersion the version of ldap  
    * @param   host the host name  
    * @param   loginid the loginid  
    * @param   password the password  
    * @param   dn the dn to use  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public DeleteLDAPUser(int valueOne, int valueTwo,String stringOne,String stringTwo,  
                         String stringThree){          
   //-------------------------------------------------------------------------  
      port=valueOne;  
      ldapVersion=valueTwo;  
      host=stringOne;  
      password=stringThree;  
      dn=stringTwo;  
      deleteItem();  
      disconnect();  
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
      StringBuffer buffer=new StringBuffer(cr+"*****DeleteLDAPUser: "+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"connection="+connection+cr);  
      buffer.append(tab+"dn="+dn+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"ldapVersion="+ldapVersion+cr);  
      buffer.append(tab+"loginid="+loginid+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"out="+out+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
   /**  
    * Class Helper. This method is used to establish the connection.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void connect(){          
   //-------------------------------------------------------------------------  
      try{  
         connection.connect(host,port);  
         connection.authenticate(loginid,password);  
      }  
      catch( LDAPException e){  
         System.err.println( "Error: " + e.toString() );  
      }          
   }//deleteItem() ENDS  
  
   /**  
    * Class Helper. This method is used to remove an item from LDAP..  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   connection the  connection to LDAP  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void deleteItem(){          
   //-------------------------------------------------------------------------  
      try{  
	 connection.delete(dn);  
	 writeToBrowser( "Entry: " + dn + " removed." );  
      }  
      catch( LDAPException e){  
	 if(e.getLDAPResultCode()==LDAPException.NO_SUCH_OBJECT)  
	    System.err.println( "Error: No such object" );  
	 else if (e.getLDAPResultCode()==LDAPException.INSUFFICIENT_ACCESS_RIGHTS)  
	    System.err.println( "Error: Insufficient rights" );  
	 else  
	    System.err.println( "Error: " + e.toString() );  
      }          
   }//deleteItem() ENDS  
  
   /**  
    * Class Helper. This method is used to release the connection.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void disconnect(){          
   //-------------------------------------------------------------------------  
      try{  
         connection.disconnect();  
      }  
      catch( LDAPException e){  
	    System.err.println( "Error: " + e.toString() );  
      }          
   }//deleteItem() ENDS  
  
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
   private String         authenticateName="cn=Directory Manager";  
   private String         authenticatePassword;  
   private LDAPConnection connection=new LDAPConnection();  
   private String         dn="";  
   private Object[]       dnArray;  
   private String         host="localhost";  
   private int            ldapVersion=LDAPConnection.LDAP_VERSION;  
   private String         loginid;  
   private String         password="o2byoung";  
   private int            port=LDAPConnection.DEFAULT_PORT;  
   private JspWriter      out;  
}//DeleteLDAPUser() ENDS  
