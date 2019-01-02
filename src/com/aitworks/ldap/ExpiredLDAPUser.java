package com.aitworks.ldap;  
import com.aitworks.sqltags.utilities.Utilities;  
import netscape.ldap.LDAPv3;  
import netscape.ldap.LDAPAttribute;  
import netscape.ldap.LDAPAttributeSet;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPControl;  
import netscape.ldap.LDAPEntry;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPObjectClassSchema;  
import netscape.ldap.LDAPSchema;  
import netscape.ldap.LDAPSearchResults;  
import netscape.ldap.controls.LDAPPasswordExpiringControl;  
import java.io.IOException;  
import java.util.Enumeration;  
import java.util.Hashtable;  
import java.util.Vector;  
import javax.servlet.jsp.JspWriter;  
  
/**  
 * Class <b>ExpiredLDAPUser</b> is used to find entries within the LDAP  
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
final public class ExpiredLDAPUser{  
   //***************************************************************************  
   //Constructors  
   //***************************************************************************  
   /**  
    * Class <b>ExpiredLDAPUser</b> default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ExpiredLDAPUser(){  
   //-------------------------------------------------------------------------  
   }  
  
   /**  
    * <b>ExpiredLDAPUser</b> constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticateName the name used to authenticate ourself.  
    * @param   authenticatePassword the password used to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ExpiredLDAPUser(String stringOne,String stringTwo){   
   //-------------------------------------------------------------------------  
      authenticateName=stringOne;  
      authenticatePassword=stringTwo;  
      findEntry();  
   }  
  
   /**  
    * <b>ExpiredLDAPUser</b> 3 argument <em>constructor</em>.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticate the name to use for authentication  
    * @param   authenticatePassword the password to use for authentication  
    * @param   out browsers writer  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ExpiredLDAPUser(String stringOne,   
                       String stringTwo,JspWriter jspWriter){   
   //-------------------------------------------------------------------------  
      out=jspWriter;  
      authenticateName=stringOne;  
      authenticatePassword=stringTwo;  
      findEntry();  
   }  
  
   /**  
    * <b>ExpiredLDAPUser</b> 4 argument <em>constructor</em>.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   nameVector users to add to rolodex  
    * @param   unit the unit were adding the user to  
    * @param   org the org were adding the user to  
    * @param   out browsers writer  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public ExpiredLDAPUser(String stringOne, String stringTwo,   
                         String stringThree, JspWriter jspWriter){  
   //-------------------------------------------------------------------------  
      authenticateName=stringOne;  
      unit=stringTwo;  
      org=stringThree;  
      out=jspWriter;  
      findEntry();  
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
    * This method modifies an LDAP entry  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void findEntry(){  
   //-------------------------------------------------------------------------  
      String searchFilter = "(uid=booker)";  
      String baseDN = "o="+org;  
      int searchScope = LDAPv3.SCOPE_SUB;  
  
      try{   
	     connection=new LDAPConnection();  
	     connection.connect(host,port);  
         connection.authenticate(authenticateName,authenticatePassword);  
         LDAPSearchResults res=connection.search(baseDN,searchScope,  
                           searchFilter,attributeList,false);  
         parseElements(res);  
	     connection.disconnect();  
      }  
      catch(LDAPException e){  
         System.out.println("Error: " + e.toString());  
      }  
   }//findEntry() ENDS  
  
   /**  
    * The <b>getAttributeList</b> method obtains the attribute list for a  
    * given class object.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   objectName the class object name  
    * @return  a String array of attributes for the given class object.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String[] getAttributeList(String string){  
   //-------------------------------------------------------------------------  
      Vector vector=new Vector();  
  
      if(schema==null||connection==null)  
         setLDAPConnection();  
  
      // get class object name  
      LDAPObjectClassSchema table=schema.getObjectClass(string);  
  
      // get require attributes  
      Enumeration enum=table.getRequiredAttributes();  
  
      // add em to the vector  
      for(;enum.hasMoreElements();)  
	 vector.addElement((String)enum.nextElement());  
  
      // get optional attributes  
      enum=table.getOptionalAttributes();  
  
      // add em to the vector  
      for(;enum.hasMoreElements();)  
	 vector.addElement((String)enum.nextElement());  
  
      // get the vector size and create an array of that size  
      int vectorSize=vector.size();  
      String[] resultsArray=new String[vectorSize];  
  
      // store the attributes in an array.  
      for(int count=0;count<vectorSize;count++)  
	 resultsArray[count]=(String)vector.get(count);  
  
      return resultsArray;  
   }//getAttributeList() ENDS  
  
   /**  
    * The <b>getCompleteClassAttributeList</b> method creates an attribute  
    * list for <em>all</em> the objects within the ldap schema. It   
    * expects the authentication name and password to have already been set.    
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  a hashtable with the class object name as the key and an   
    *          array of strings as its values.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public Hashtable getCompleteClassAttributeList(){  
   //-------------------------------------------------------------------------  
      Hashtable results=new Hashtable();  
      Vector vector=new Vector();  
      String[] classNames=getSchemaClasses();  
      int size=classNames.length;  
  
  
      for(int index=0;index<size;index++){  
         // get class object name  
         LDAPObjectClassSchema table=schema.getObjectClass(classNames[index]);  
  
         // get require attributes  
         Enumeration enum=table.getRequiredAttributes();  
  
         // add em to the vector  
	 for(;enum.hasMoreElements();)  
            vector.addElement((String)enum.nextElement());  
  
         // get optional attributes  
         enum=table.getOptionalAttributes();  
  
         // add em to the vector  
         for(;enum.hasMoreElements();)  
            vector.addElement((String)enum.nextElement());  
  
         // get the vector size and create an array of that size  
         int vectorSize=vector.size();  
         String[] resultsArray=new String[vectorSize];  
  
         // store the attributes in an array.  
         for(int count=0;count<vectorSize;count++)  
            resultsArray[count]=(String)vector.get(count);  
  
         // place the table and the array in the hash  
         results.put(classNames[index],resultsArray);  
      }  
  
      return results;  
   }//getCompleteClassAttributeList() ENDS  
  
   /**  
    * The <b>getConnection</b> method returns the ldap connection.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public LDAPConnection getConnection(){  
   //-------------------------------------------------------------------------  
      return connection;  
   }// getConnection() ENDS  
  
   /**  
    * The <b>getMessages</b> method returns the results.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getMessages(){          
   //-------------------------------------------------------------------------  
      return messages.toString();  
   }  
     
   /**  
    * The <b>getSchemaClasses</b> method returns a list of <em>all</em> the  
    * classes within the ldap schema. It <em>expects</em> the authentication   
    * name adn password to have already been set.    
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  an array of Strings containing the <em>class object names</em>.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String[] getSchemaClasses(){  
   //-------------------------------------------------------------------------  
      if(connection==null||schema==null)  
         setLDAPConnection();  
  
      Enumeration enum=schema.getObjectClassNames();  
      Vector vector=new Vector();  
  
      for(;enum.hasMoreElements();)  
	 vector.addElement((String)enum.nextElement());  
  
      int size=vector.size();  
      String[] results=new String[size];  
  
      for(int index=0;index<size;index++)  
         results[index]=(String)vector.get(index);  
  
      return results;  
   }// getSchemaClasses() ENDS  
  
   /**  
    * The <b>ldapDisconnect</b> method disconnects you from the LDAP Server.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void ldapDisconnect() throws LDAPException{  
   //-------------------------------------------------------------------------  
      connection.disconnect();  
   }// LDAPDisconnect() ENDS  
  
   /**  
    * The <b>setAttributeList</b> method sets the attributes list. This  
    * list determins which attributes are returned when the <em>find</em>   
    * command is invoked.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   attributeList an array of String attribute names.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAttributeList(String[] string){  
   //-------------------------------------------------------------------------  
      attributeList=string;  
   }// setAttributeList() ENDS  
  
   /**  
    * The <b>setAuthenticateName</b> method sets name.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticateName(String string){  
   //-------------------------------------------------------------------------  
      authenticateName=string;  
   }//setAuthenticateName() ENDS  
  
   /**  
    * The <b>setAuthenticatePassword</b> method sets password  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticatePassword(String string){  
   //-------------------------------------------------------------------------  
      authenticatePassword=string;  
   }//setAuthenticateName() ENDS  
  
   /**  
    * The <b>setHost</b> method sets host.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setHost(String string){  
   //-------------------------------------------------------------------------  
      host=string;  
   }//sethost() ENDS  
  
   /**  
    * The <b>setLoginID</b> method is used to set the name your searching for.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setLoginID(String loginID){  
   //-------------------------------------------------------------------------  
      loginID=loginID;  
   }// setLoginID() ENDS  
  
   /**  
    * The <b>setLDAPConnection</b> method establishes a connection to the  
    * LDAP database. It expects the <em>authentication name and   
    * authentication password</em> to have been set.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setLDAPConnection(){  
   //-------------------------------------------------------------------------  
      try{  
	 if(schema==null){  
	    connection=new LDAPConnection();  
	    connection.connect(host,port);  
	    connection.authenticate(authenticateName,authenticatePassword);  
	    schema=new LDAPSchema();  
	    schema.fetchSchema(connection);  
	 }  
      }  
      catch(LDAPException exception){  
      }  
   }//setLDAPConnection() ENDS  
  
   /**  
    * The <b>setOrg</b> method sets org  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setOrg(String string){  
   //-------------------------------------------------------------------------  
      org=string;  
   }//setOrg() ENDS  
  
   /**  
    * The <b>setPassword</b> method sets password  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPassword(String string){  
   //-------------------------------------------------------------------------  
      password=string;  
   }//setPassword() ENDS  
  
   /**  
    * The <b>setPort</b> method sets port.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPort(String string){  
   //-------------------------------------------------------------------------  
      port=Integer.parseInt(string);  
   }//setPort() ENDS  
  
   /**  
    * The <b>setUid</b> method sets uid  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   value the new value  
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
    * @param   value the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setUnit(String string){  
   //-------------------------------------------------------------------------  
      unit=string;  
   }//setUnit() ENDS   
  
   /**  
    * Class constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   args command line args.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public static void main(String[] args){   
   //-------------------------------------------------------------------------  
      ExpiredLDAPUser expired=new ExpiredLDAPUser();  
      expired.findEntry();  
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
      StringBuffer buffer=new StringBuffer(cr+"*****ExpiredLDAPUser: "+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"attributeList="+attributeList+cr);  
      buffer.append(tab+"connection="+connection+cr);  
      buffer.append(tab+"loginID="+loginID+cr);  
      buffer.append(tab+"org="+org+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"schema="+schema+cr);  
      buffer.append(tab+"uid="+uid+cr);  
      buffer.append(tab+"unit="+unit+cr);  
      buffer.append(tab+"messages="+messages+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
   //******************************************************************************  
   //Private Methods  
   //******************************************************************************  
   /**  
    * Class Helper. This method writes to the browser.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   message the message were writing.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void parseElements(LDAPSearchResults results){          
   //-------------------------------------------------------------------------  
      LDAPEntry item=null;  
      LDAPAttribute attribute=null;  
      LDAPAttributeSet set=null;  
      Enumeration enum=null;  
      Enumeration valueEnum=null;  
      String name="";  
      String cr="\n";  
      String tab="\t";  
      StringBuffer buffer=new StringBuffer();  
  
      while (results.hasMoreElements() ) {  
	 try {   
	    item = results.next();  
	    set = item.getAttributeSet();  
            enum=set.getAttributes();  
            messages.setLength(0);  
            messages.append(cr+"-----------------------------------");  
  
            for(;enum.hasMoreElements();){  
               attribute=(LDAPAttribute)enum.nextElement();  
               name=attribute.getName();  
               valueEnum=attribute.getStringValues();  
  
               for(;valueEnum.hasMoreElements();)  
                  buffer.append((String)valueEnum.nextElement());  
                 
               if(attribute!=null)  
                  messages.append(cr+tab+name+" = "+buffer.toString());  
  
               buffer.setLength(0);  
            }  
            messages.append(cr+"-----------------------------------"+cr);  
	 }  
	 catch(LDAPException e){  
	    System.out.println("Error: " + e.toString());  
	 }  
      }  
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
	 out.println(message+"<br>");  
         out.flush();  
      }  
      catch(IOException e){  
      }  
   }  
  
   //***************************************************************************  
   //Variables  
   //***************************************************************************  
   private String              authenticateName="";  
   private String              authenticatePassword="";  
   private String[]            attributeList={"uid", "givenName", "sn",  
					  "cn", "ojpMiddleName", "ojpSuffix",  
					  "ojpBadgeNumber", "displayName",  
					  "x500uniqueidentifier",  
					  "postalAddress", "roomNumber",  
					  "telephoneNumber",  
					  "facsimileTelephoneNumber",  
					  "mobile", "pager", "mail",  
					  "mailalternateaddress",  
					  "ou", "description",  
					  "employeeType", "pin",  
					  "userPassword", "userCertificate",  
					  "manager", "secretary",  
					  "cACertificate",  
					  "certificateRevocationList",  
					  "ojpBuilding", "ojpApps",  
					  "ojpSecret1", "ojpSecret2",  
					  "ojpSecret3", "ojpSecret4",  
					  "ojpCentralPhone", "ojpStatus",  
					  "ojpSponsor", "comUid", "justiceApp"  
				       };  
   private LDAPConnection      connection=null;  
   private String              host="";  
   private String              loginID="";  
   private String              org="";  
   private JspWriter           out;  
   private String              password="";  
   private int                 port;  
   private LDAPSchema          schema=null;  
   private String              uid;  
   private String              unit;  
   private StringBuffer        messages=new StringBuffer();  
  
}//ExpiredLDAPUser() ENDS  
