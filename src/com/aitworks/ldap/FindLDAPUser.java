package com.aitworks.ldap;  
import com.aitworks.sqltags.utilities.Utilities;  
import com.aitworks.sqltags.utilities.ServletHelper;  
import com.aitworks.sqltags.utilities.Initialize;  
import java.io.IOException;  
import java.util.Calendar;  
import java.util.Enumeration;  
import java.util.Hashtable;  
import java.util.Properties;
import java.util.Vector;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpSession;  
import netscape.ldap.LDAPv3;  
import netscape.ldap.LDAPAttribute;  
import netscape.ldap.LDAPAttributeSet;  
import netscape.ldap.LDAPConnection;  
import netscape.ldap.LDAPEntry;  
import netscape.ldap.LDAPException;  
import netscape.ldap.LDAPObjectClassSchema;  
import netscape.ldap.LDAPSchema;  
import netscape.ldap.LDAPSearchResults;  
  
/**  
 * Class <code>FindLDAPUser</code> is used to find entries within the LDAP  
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
final public class FindLDAPUser{  
   //******************************************************************************  
   //Constructors  
   //******************************************************************************  
   /**  
    * Class <code>FindLDAPUser</code> default constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(){  
   //-------------------------------------------------------------------------  
      setParameterHashDefaultValues();  
   }  
  
   /**  
    * Class <code>FindLDAPUser</code> constructor used from the JSP page when your  
    * using the properties file to load the configuration parameters.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(PageContext pageContext){  
   //-------------------------------------------------------------------------  
      setParameterHashDefaultValues();  
      pageContext=pageContext;  
      Initialize initialize=  
         (Initialize)pageContext.getSession().getAttribute(initSrc);  
  
      if(initialize!=null)  
         ldapProperties=initialize.getInitializationProperties();  
  
      String validateName=(String)ldapProperties.get("authenticateName");  
  
      if(!validateName.startsWith("cn")||validateName.indexOf("=")==-1)  
         ldapProperties.put("authenticateName","cn="+validateName);  
      findEntry();  
   }//FindLDAPUser() ENDS  
  
   /**  
    * <code>FindLDAPUser</code> constructor.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticateName the name used to authenticate ourself.  
    * @param   authenticatePassword the password used to authenticate ourself.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(String stringOne,String stringTwo){   
   //-------------------------------------------------------------------------  
      setParameterHashDefaultValues();  
      parameterHash.put("authenticateName","cn="+stringOne);  
      parameterHash.put("authenticatePassword",stringTwo);  
      findEntry();  
   }  
  
   /**  
    * The <code>FindLDAPUser</code> 3 argument <em>constructor</em>.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   authenticate the name to use for authentication  
    * @param   authenticatePassword the password to use for authentication  
    * @param   out browsers writer  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(String stringOne,   
                       String stringTwo,JspWriter jspWriter){   
   //-------------------------------------------------------------------------  
      setParameterHashDefaultValues();  
      out=jspWriter;  
      parameterHash.put("authenticateName","cn="+stringOne);  
      parameterHash.put("authenticatePassword","cn="+stringTwo);  
      findEntry();  
   }  
   
   //-------------------------------------------------------------------------  
   public FindLDAPUser(HttpSession httpSession, String string,
                       Properties properties){  
   //------------------------------------------------------------------------- 
      this.properties=properties;
	   processRequest(httpSession,string);
	}//Constructor ENDS    
  
   /**  
    * The <code>FindLDAPUser</code> 2 argument <em>constructor</em>.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   loginID the name you want to lookup  
    * @param   unit the unit the person is in  
    * @param   org the org the person is in  
    * @param   out browsers writer  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(HttpSession httpSession, String stringOne, String stringTwo){  
   //-------------------------------------------------------------------------  
      initSrc=stringTwo;  
      processRequest(httpSession,stringOne);
   }//Constructor ENDS  
  
   /**  
    * The <code>FindLDAPUser</code> 4 argument <em>constructor</em>.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   loginID the name you want to lookup  
    * @param   unit the unit the person is in  
    * @param   org the org the person is in  
    * @param   out browsers writer  
    * @return  none  
    * @since   JDK1.3  
    */  
  
   //-------------------------------------------------------------------------  
   public FindLDAPUser(String stringOne, String stringTwo,   
                         String stringThree, JspWriter jspWriter, PageContext pageContext){  
   //-------------------------------------------------------------------------  
      setParameterHashDefaultValues();  
      pageContext=pageContext;  
      request=pageContext.getRequest();  
      response=pageContext.getResponse();  
      Initialize initialize=  
         (Initialize)pageContext.getSession().getAttribute(initSrc);  
  
      if(initialize!=null)  
         ldapProperties=initialize.getInitializationProperties();  
  
      String validateName=(String)ldapProperties.get("authenticateName");  
  
      if(!validateName.startsWith("cn")||validateName.indexOf("=")==-1)  
         ldapProperties.put("authenticateName","cn="+validateName);  
  
      if(ldapProperties!=null){  
         Enumeration enum=ldapProperties.keys();  
  
	 for(;enum.hasMoreElements();){  
	    String key=(String)enum.nextElement();  
	    String value=(String)ldapProperties.get(key);  
            parameterHash.put(key,value);  
	 }  
      }  
  
      out=jspWriter;  
      parameterHash.put("loginID",stringOne);  
      parameterHash.put("unit",stringTwo);  
      parameterHash.put("org",stringThree);  
  
      findEntry();  
   }//Constructor ENDS  
  
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
   //******************************************************************************  
   //Public Methods  
   //******************************************************************************  
   /**  
    * This <code>findEntry</code> method retrieves a user from the db.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void findEntry(){  
   //-------------------------------------------------------------------------  
      String baseDN = "o="+org;  
      int searchScope = LDAPv3.SCOPE_SUB;  
      String searchFilter = "(uid="+loginID+")";  
  
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
         System.out.println("FindLDAPUser.findEntry: " + e.toString());  
      }  
   }//findEntry() ENDS  
  
   /**  
    * The <code>getAttributeList</code> method obtains the attribute list for a  
    * given class object.   
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the class object name  
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
    * The <code>getAuthenticateName</code> method sets name.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getAuthenticateName(){  
   //-------------------------------------------------------------------------  
      return (String)parameterHash.get("authenticateName");  
   }//setAuthenticateName() ENDS  
  
   /**  
    * The <code>getBrowserMessages</code> method returns any messages   
    * encountered while validating the use.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public String getBrowserMessages(){  
   //-------------------------------------------------------------------------  
      return browserMessages.toString();  
   }// getBrowserMessage() ENDS  
  
   /**  
    * The <code>getCompleteClassAttributeList</code> method creates an attribute  
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
    * The <code>getConnection</code> method returns the ldap connection.  
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
    * The <code>getDaysToExpire</code> method returns the daysToExpire  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public int getDaysToExpire(){  
   //-------------------------------------------------------------------------  
      return daysToExpire;  
   }// getDaysToExpire() ENDS  
  
   /**  
    * The <code>getMessages</code> method returns the results.  
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
    * The <code>getPasswordStatus</code> method checks the status of the passord.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  <li><em>0</em> if password is ok.  
    *          <li><em>-1</em> if password has expired.  
    *          <li><em>-2</em> if password is going to expire.  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public int getPasswordStatus(){  
   //-------------------------------------------------------------------------  
      return passwordStatus;  
   }// getPasswordStatus()  
  
   /**  
    * The <code>getSchemaClasses</code> method returns a list of <em>all</em> the  
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
    * The <code>getTimeToChangePassword</code> method check to see if it is time  
    * for this user to change their password.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  true if the password needs to be changed  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public boolean getTimeToChangePassword(){  
   //-------------------------------------------------------------------------  
      return timeToChangePassword;  
   }// getTimeToChangePassword() ENDS  
  
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
   public void isTimeToChangePassword(){  
   //-------------------------------------------------------------------------  
      int warningSize=  
         utilities.stringToInt((String)parameterHash.get("gracePeriod"));  
      timeToChangePassword=false;  
      Calendar passwordExpirationTime=Calendar.getInstance();  
      warningSize=warningSize-(warningSize*2);  
      Calendar now=Calendar.getInstance();  
      Calendar gracePeriod=Calendar.getInstance();  
      String stringTime=(String)validAttributes.get("passwordexpirationtime");  
  
      if(debug){  
	     System.out.println("year = "  
            +utilities.stringToInt(stringTime.substring(0,4)));  
	     System.out.println("month = "  
            +utilities.stringToInt(stringTime.substring(4,6)));  
	     System.out.println("day = "  
            +utilities.stringToInt(stringTime.substring(6,8)));  
	     System.out.println("hour = "  
            +utilities.stringToInt(stringTime.substring(8,10)));  
	     System.out.println("minute = "  
            +utilities.stringToInt(stringTime.substring(10,12)));  
	     System.out.println("second = "  
            +utilities.stringToInt(stringTime.substring(12,14)));  
      }  
  
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
	 System.out.println("Expire="+passwordExpirationTime.getTime().toString());  
	 System.out.println("GracePeriod="+gracePeriod.getTime().toString());  
	 System.out.println("Now="+now.getTime().toString());  
      }  
  
      if(passwordExpirationTime.before(now)){  
         browserMessages.append("Password expired ");  
         browserMessages.append(passwordExpirationTime.getTime().toString());  
         browserMessages.append(". Please contact your administrator.\n");  
         passwordStatus=-1;   
         timeToChangePassword=true;  
         expiredOrExpiring=true;  
  
         if(debug)  
            System.out.println("\n"+browserMessages.toString()+"\n");  
      }  
      else if(now.after(gracePeriod)||now.equals(gracePeriod)){  
         browserMessages.setLength(0);  
         daysToExpire=0;  
         passwordStatus=-2;  
         timeToChangePassword=true;  
         expiredOrExpiring=false;  
  
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
  
         if(debug)  
            System.out.println("\n"+browserMessages.toString()+"\n");  
  
      }  
   }// isTimeToChangePassword() ENDS  
  
   /**  
    * The <code>ldapDisconnect</code> method disconnects you from the LDAP Server.  
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

   //-------------------------------------------------------------------------  
    public void processRequest(HttpSession httpSession, String string){  
   //------------------------------------------------------------------------- 
      session=httpSession;  
      remoteUser=string;  
      setParameterHashDefaultValues();  
      setDN();  
      findEntry();  
   }//processRequest() ENDS  
	  
   /**  
    * The <code>setAttributeList</code> method sets the attributes list. This  
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
    * The <code>setAuthenticateName</code> method sets name.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticateName(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("authenticateName","cn="+string);  
   }//setAuthenticateName() ENDS  
  
   /**  
    * The <code>setAuthenticatePassword</code> method sets password  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setAuthenticatePassword(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("authenticatePassword",string);  
   }//setAuthenticateName() ENDS  
  
   /**  
    * The <code>setClassVariables</code> method loads the values that are within the  
    * hashtable into local variables.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setClassVariables(){  
   //-------------------------------------------------------------------------  
      org=(String)parameterHash.get("org");  
      loginID=(String)parameterHash.get("loginID");  
      host=(String)parameterHash.get("host");  
      port=utilities.stringToInt((String)parameterHash.get("port"));  
      authenticateName=(String)parameterHash.get("authenticateName");  
      authenticatePassword=(String)parameterHash.get("authenticatePassword");  
   }//setClassVariables() ENDS  
  
   /**  
    * The <code>setHost</code> method sets host.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setHost(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("host",string);  
   }//sethost() ENDS  
  
   /**  
    * The <code>setLDAPConnection</code> method establishes a connection to the  
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
      setClassVariables();  
  
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
    * The <code>setLoginID</code> method is used to set the name your searching for.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none.  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setLoginID(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("loginID",string);  
   }// setLoginID() ENDS  
  
   /**  
    * The <code>setOrg</code> method sets org  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setOrg(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("org",string);  
   }//setOrg() ENDS  
  
   /**  
    * The <code>setPassword</code> method sets password  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPassword(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("password",string);  
   }//setPassword() ENDS  
  
   /**  
    * The <code>setPort</code> method sets port.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setPort(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("port",string);  
   }//setPort() ENDS  
  
   /**  
    * The <code>setUid</code> method sets uid  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setUid(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("uid",string);  
   }//setUid() ENDS  
  
   /**  
    * The <code>setUnit</code> method sets unit  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   string the new value  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setUnit(String string){  
   //-------------------------------------------------------------------------  
      parameterHash.put("unit",string);  
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
      Utilities utilities=new Utilities();  
      FindLDAPUser find=new FindLDAPUser();  
      String done="";  
  
      while(!done.toLowerCase().equals("q")){  
         String temp=utilities.nvl(utilities.promptCommandLine(  
            "Enter the cn (Directory Manager):"),"Directory Manager");  
         find.setAuthenticateName(temp);  
  
         find.setAuthenticatePassword(  
            utilities.nvl(  
               utilities.promptCommandLine(  
                  "Enter the password (thePassword):"),"o2byoung"  
            )  
         );  
  
         find.setHost(  
            utilities.nvl(  
               utilities.promptCommandLine(  
                  "Enter the host (localhost):"),"localhost"  
            )  
         );  
  
         find.setUnit(  
            utilities.nvl(  
               utilities.promptCommandLine("Enter the unit (People):"),"People"  
            )  
         );  
  
         find.setPort(  
            utilities.nvl(  
               utilities.promptCommandLine("Enter the port (389):"),"389"  
            )  
         );  
  
         find.setOrg(  
            utilities.nvl(  
               utilities.promptCommandLine(  
                  "Enter the org (sgsst.com):"),"sgsst.com"  
            )  
         );  
  
         find.setLoginID(  
            utilities.nvl(  
               utilities.promptCommandLine("Find what loginid (test)?"),"booker"  
            )  
         );  
  
         find.setClassVariables();  
         find.findEntry();  
         done=done.toLowerCase();  
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
      StringBuffer buffer=new StringBuffer(cr+"*****FindLDAPUser: "+cr);  
      buffer.append(tab+"attributeList="+attributeList+cr);  
      buffer.append(tab+"authenticateName="+authenticateName+cr);  
      buffer.append(tab+"authenticatePassword="+authenticatePassword+cr);  
      buffer.append(tab+"browserMessages="+browserMessages+cr);  
      buffer.append(tab+"connection="+connection+cr);  
      buffer.append(tab+"daysToExpire="+daysToExpire+cr);  
      buffer.append(tab+"debug="+debug+cr);  
      buffer.append(tab+"dn="+dn+cr);  
      buffer.append(tab+"expiredOrExpiring="+expiredOrExpiring+cr);  
      buffer.append(tab+"host="+host+cr);  
      buffer.append(tab+"initSrc="+initSrc+cr);  
      buffer.append(tab+"ldapPasswordExpiredControl="+ldapPasswordExpiredControl+cr);  
      buffer.append(tab+"ldapPasswordExpiringControl="+ldapPasswordExpiringControl+cr);  
      buffer.append(tab+"loginID="+loginID+cr);  
      buffer.append(tab+"messages="+messages+cr);  
      buffer.append(tab+"org="+org+cr);  
      buffer.append(tab+"out="+out+cr);  
      buffer.append(tab+"pageContext="+pageContext+cr);  
      buffer.append(tab+"parameterHash="+parameterHash+cr);  
      buffer.append(tab+"password="+password+cr);  
      buffer.append(tab+"passwordStatus="+passwordStatus+cr);  
      buffer.append(tab+"port="+port+cr);  
      buffer.append(tab+"remoteUser="+remoteUser+cr);  
      buffer.append(tab+"request="+request+cr);  
      buffer.append(tab+"response="+response+cr);  
      buffer.append(tab+"schema="+schema+cr);  
      buffer.append(tab+"session="+session+cr);  
      buffer.append(tab+"timeToChangePassword="+timeToChangePassword+cr);  
      buffer.append(tab+"uid="+uid+cr);  
      buffer.append(tab+"unit="+unit+cr);  
      buffer.append(tab+"utilities="+utilities+cr);  
      buffer.append(tab+"validAttributes="+validAttributes+cr);  
      buffer.append(tab+"PASSWORD_EXPIRED="+PASSWORD_EXPIRED+cr);  
      buffer.append(tab+"NO_PASSWORD_CONTROLS="+NO_PASSWORD_CONTROLS+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
  
  
   //***************************************************************************  
   //Private Methods  
   //***************************************************************************  
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
      StringBuffer buffer=new StringBuffer();  
      browserMessages=new StringBuffer();  
  
      while (ldapSearchResults.hasMoreElements() ) {  
	 try {   
	    item = ldapSearchResults.next();  
	    set = item.getAttributeSet();  
            enum=set.getAttributes();  
            messages.setLength(0);  
            messages.append(cr+"-----------------------------------");  
            browserMessages.setLength(0);  
            browserMessages.append("\n-----------------------------------");  
  
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
  
            browserMessages.append("\n-----------------------------------\n");  
            messages.append(cr+"-----------------------------------"+cr);  
  
	    if(stringTime!=null&&!stringTime.equals(""))  
	       isTimeToChangePassword();  
  
            if(out!=null)  
               out.println(browserMessages.toString());  
  
            if(debug)  
               System.out.println(messages.toString());  
  
	 }  
	 catch(IOException e){  
	 }  
	 catch(NumberFormatException e){  
	 }  
	 catch(LDAPException e){  
	    System.out.println("Error: " + e.toString());  
	 }  
      }  
      return;
   }//parseElements() ENDS  
  
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
   private void setDN(){  
   //-------------------------------------------------------------------------  
      loginID=remoteUser;  
      unit=(String)properties.getProperty("ou");  
      org=(String)properties.getProperty("org");  
      host=(String)properties.getProperty("host");  
      String gracePeriod=(String)properties.getProperty("gracePeriod");  
      authenticateName="cn="+(String)properties.getProperty("authenticateName");  
      authenticatePassword=  
        (String)properties.getProperty("authenticatePassword");  
      parameterHash.put("gracePeriod",gracePeriod);  
      dn="uid="+loginID+", ou="+unit+", o="+org;  
      String temp=(String)properties.getProperty("port");  
  
      try{      
         port=Integer.parseInt(temp);  
      }   
      catch(NumberFormatException exception){  
         port=389;  
      }  
   }//setDN() ENDS  
  
   /**  
    * The <code>setParameterHashDefaultValues</code> method loads the default ldap connection  
    * parameters into the hashtable.  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   none  
    * @return  none  
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   private void setParameterHashDefaultValues(){          
   //-------------------------------------------------------------------------  
      parameterHash.put("authenticateName","");  
      parameterHash.put("gracePeriod","");  
      parameterHash.put("host","");  
      parameterHash.put("loginID","");  
      parameterHash.put("password","");  
      parameterHash.put("uid","");  
      parameterHash.put("unit","");  
      parameterHash.put("org","");  
      parameterHash.put("authenticatePassword","");  
      parameterHash.put("port","");  
   }//setParameterHashDefaultValues() ENDS  
     
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
  
   //******************************************************************************  
   //Class Variables  
   //******************************************************************************  
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
   private String              authenticateName="";  
   private String              authenticatePassword="";  
   private StringBuffer        browserMessages=new StringBuffer();  
   private LDAPConnection      connection=null;  
   private int                 daysToExpire=0;  
   private boolean             debug=false;  
   private String              dn="";  
   private boolean             expiredOrExpiring=false;  
   private String              host="";  
   private String              initSrc="";  
   private String              ldapPasswordExpiredControl=  
                                  "2.16.840.1.113730.3.4.4";  
   private String              ldapPasswordExpiringControl=  
                                  "2.16.840.1.113730.3.4.5";  
   private Hashtable           ldapProperties=null;  
   private String              loginID="";  
   private StringBuffer        messages=new StringBuffer();  
   private String              org="";  
   private JspWriter           out=null;  
   private PageContext         pageContext;  
   private Hashtable           parameterHash=new Hashtable();  
   private String              password="";  
   private int                 passwordStatus=0;  
   private int                 port;  
   private Properties          properties;
   private String              remoteUser="";  
   private ServletRequest      request=null;  
   private ServletResponse     response=null;  
   private LDAPSchema          schema=null;  
   private HttpSession         session=null;  
   private boolean             timeToChangePassword=false;  
   private String              uid;  
   private String              unit;  
   private Utilities           utilities=new Utilities();  
   private Hashtable           validAttributes=new Hashtable();  
   final static int            PASSWORD_EXPIRED = -1;  
   final static int            NO_PASSWORD_CONTROLS = 0;  
}//FindLDAPUser() ENDS  
