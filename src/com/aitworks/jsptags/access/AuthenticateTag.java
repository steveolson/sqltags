package com.aitworks.jsptags.access;  
import com.aitworks.jsptags.access.InitializeTag;
import com.aitworks.ldap.FindLDAPUser;
import com.aitworks.sqltags.utilities.SQLTags;  
import com.aitworks.sqltags.utilities.PrivilegeLoader;  
import com.aitworks.sqltags.utilities.Utilities;  
import com.aitworks.sqltags.utilities.RandomHash;  
import com.aitworks.sqltags.utilities.Initialize;  
import java.io.File;  
import java.io.IOException;  
import java.util.Properties;  
import java.sql.SQLException;  
import java.util.Hashtable;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.PageContext;  
import javax.servlet.jsp.tagext.BodyContent;  
import javax.servlet.jsp.tagext.BodyTagSupport;  
  
/**  
 * The <b>AuthenticateTag</b>   
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//---------------------------------------------------------------------------  
final public class AuthenticateTag extends InitializeTag{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   // Constructors   
   //***************************************************************************  
   /**  
    * The <b>AuthenticateTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public AuthenticateTag(){  
   //---------------------------------------------------------------------------  
   }//Constructor ENDS()  
  
  
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************  
   /**  
    * The <b>doAfterBody</b> method is called after the body of the  
    * tag has been processed.  
    * PCM-3  
    * @author  Booker Northington II  
    * @version 1.0  
    * @param   name the current field name.  
    * @see     BodyTagSupport  
    * @return  none  
    * @since   JDK1.3  
    */  
   //---------------------------------------------------------------------------  
   public int doAfterBody(){  
   //---------------------------------------------------------------------------  
      int returnCode=SKIP_BODY;  
      return returnCode;  
   }// doAfterBody() ENDS  
  
   /**  
    * This <b>doEndTag</b> is called when the end tag is hit.  
    * PCM-7  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  The loop status.  
    */  
   //---------------------------------------------------------------------------  
   public int doEndTag(){  
   //---------------------------------------------------------------------------  
      int returnCode=SKIP_BODY;  
  
      if(!badPassword)  
         returnCode=EVAL_PAGE;  
  
      /**  
       * Remove the prpoperties Hashtable from the session if true.  
       */  
      if(logout.equals("true"))  
         pageContext.removeAttribute(getName());  
  
      return returnCode;  
   }// doEndTag() ENDS  
  
  
   /**  
    * The <b>doStartTag</b> method is called when the start tag is encountered.    
    * PCM-8  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the eval state  
    */  
   //---------------------------------------------------------------------------  
   public int doStartTag() throws JspException{  
   //---------------------------------------------------------------------------  
      int returnCode=EVAL_BODY_TAG;  
      pageContext.setAttribute(getId(),this);  
      HttpSession session=(HttpSession)  
          pageContext.getAttribute(PageContext.SESSION);  
  
      //get Initialization Properites  
      if(!getName().equals(""))  
         init=(InitializeTag)session.getAttribute(getName());  
      else  
         init=null;  
  
      if(init!=null){  
	 initializationProperties=init.getInitializationProperties();  
	 initialize=init.getInitialize();  
  
	 // get the user and validate the password  
	 HttpServletRequest request=(HttpServletRequest)  
	    pageContext.getAttribute(PageContext.REQUEST);  
  
	 remoteUser=request.getRemoteUser();  
	 badPassword=validateUserPassword(session,remoteUser,initSrc);  
  
	 if(initialize!=null)  
	    initialize.setBadPassword(badPassword);  
  
	 /**  
	  * True if the password is expired or expiring. This is done  
	  * until passwordValid is placed within the session.  
	  */  
	 if(badPassword){  
	    /**  
        * This will be null if the user session has not been validated.  
        */  
	    if(pageContext.getSession().getAttribute("passwordValid")==null){  
	       msg=expiredOrExpiring;  
  
	       /**  
	        * True, if the password has expired.  
           */  
         if(msg.indexOf("19691231185959")>=0){  
            returnCode=SKIP_BODY;  
            badPassword=true;  
            log("AuthenticateTag.doStart(): password expired!");  
                  String expiredPage=(String)  
            initializationProperties.get("expiredPage");  
  
            if(expiredPage!=null)  
               utilities.forwardRequest(expiredPage,  
                  (HttpServletRequest)pageContext.getRequest(),  
                  (HttpServletResponse)pageContext.getResponse()  
               );  
            else{  
               StringBuffer message=new StringBuffer("AuthenticateTag.");  
               message.append("doStart(): The attribute \"expiredPage\"");  
               message.append(" has not be specified within the ");  
               message.append("properties file.");  
               log(message.toString());  
            }     
	       }  
	       else{  
		  /**  
		   * the password has not expired. set the session attribute and  
		   * let the user know if their password is expiring.  
		   */  
		  log(remoteUser+" has logged in.");  
		  pageContext.getSession().setAttribute("passwordValid",  
		     "passwordValid");  
		  badPassword=false;  
  
		  try{  
		     pageContext.getOut().println(  
			"<script>statusMessage(\""+msg+"\");</script>");  
		  }  
		  catch(IOException exception){  
		     log("AuthenticateTag.doStartTag(): "+exception);  
		  }  
  
		  expiring=true;  
	       }  
	    }  
	 }  
  
	 authenticateTableName=(String)  
	    initializationProperties.get("authenticate.privLoaderClass");  
      }  
      else{  
         returnCode=SKIP_PAGE;  
         log("AuthenticateTag could not load initialization properties.");  
         utilities.forwardRequest("noPropertiesFile.jsp",  
            (HttpServletRequest)pageContext.getRequest(),  
            (HttpServletResponse)pageContext.getResponse()  
         );  
      }  
  
      if(authenticateTableName!=null&&userPrivilegeHash==null){  
         loader=(PrivilegeLoader)utilities.factory(authenticateTableName);  
         loader.loadProperties(this);  
         userPrivilegeHash=loader.getUserPrivileges();  
      }  
  
      return returnCode;  
   }// doStartTag() END  
  
   /**  
    * <code>getAuthenticatePropertiesName</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this  
    * tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param id <code>String</code> The name of the scripting variable.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public String getAuthenticatePropertiesName(){  
   //---------------------------------------------------------------------------  
      return authenticate_properties;  
   }//getAuthenticatePropertiesName() ENDS  
  
   /**  
    * The <b>getAuthType</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getAuthType(){  
   //---------------------------------------------------------------------------  
      return authType;  
   }// getAuthType() ENDS  
  
   /**  
    * The <b>getExpirePage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getExpirePage(){  
   //---------------------------------------------------------------------------  
      return expirePage;  
   }// getExpirePage() ENDS  
  
   /**  
    * The <b>getId</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getId(){  
   //---------------------------------------------------------------------------  
      return id;  
   }// getId() ENDS  
  
   /**  
    * The <b>getInitEncrypted</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getInitEncrypted(){  
   //---------------------------------------------------------------------------  
      return initEncrypted;  
   }// getInitEncrypted() ENDS  
  
   /**  
    * The <b>getInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getInitSrc(){  
   //---------------------------------------------------------------------------  
      return initSrc;  
   }// getInitSrc() ENDS  
  
   /**  
    * The <b>getLoginPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getLoginPage(){  
   //---------------------------------------------------------------------------  
      return loginPage;  
   }// getLoginPage() ENDS  
  
   /**  
    * The <b>getLogout</b> method is used to return the logout attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getLogout(){  
   //---------------------------------------------------------------------------  
      return logout;  
   }// getLogout() ENDS  
  
   /**  
    * The <b>getMsg</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getMsg(){  
   //---------------------------------------------------------------------------  
      return msg;  
   }// getMsg() ENDS  
  
   /**  
    * The <b>getName</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getName(){  
   //---------------------------------------------------------------------------  
      return name;  
   }// getName() ENDS  
  
   /**  
    * <code>getRemoteUser</code>  
    * <p>  
    * This method returns the remoteUser.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return remoteUser<code>String</code> the remote user  
    */  
   //---------------------------------------------------------------------------  
   public String getRemoteUser(){  
   //---------------------------------------------------------------------------  
      return remoteUser;  
   }// getRemoteUser() ENDS  
  
   /**  
    * <code>getUserPermQuery</code>  
    * <p>  
    * This method returns the remoteUser.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return userPermQuery<code>String</code> the user permissions query.  
    */  
   //---------------------------------------------------------------------------  
   public String getUserPermQuery(){  
   //---------------------------------------------------------------------------  
      return userPermQuery;  
   }// getUserPermQuery() ENDS  
   /**  
    * <code>hasPrivilege</code>  
    * <p>  
    * This method checks to see if you have a certain privilege.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param privilege<code>String</code> a privilege.  
    * @return true<code>boolean</code>true if the user has that privilege   
    */  
   //---------------------------------------------------------------------------  
   public boolean hasPrivilege(String string){  
   //---------------------------------------------------------------------------  
      return privilegeHash.containsKey(string);  
   }// hasPrivilege() ENDS  
  
   /**  
    * <code>hasPrivilegesBeenLoaded</code>  
    * <p>  
    * This method checks to see if the privileges have been loaded.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return true<code>boolean</code>true if the privileges have been loaded  
    */  
   //---------------------------------------------------------------------------  
   public boolean hasPrivilegesBeenLoaded(){  
   //---------------------------------------------------------------------------  
      boolean loaded=true;  
  
      if(privilegeHash.size()<=0)  
         loaded=false;  
  
      return loaded;  
   }// hasPrivilegesBeenLoaded() ENDS  
  
   /**  
    * <code>isAuthorized</code>  
    * <p>  
    * This method checks to see if the user is authorized.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public boolean isAuthorized(){  
   //---------------------------------------------------------------------------  
      return authorized;  
   }// isAuthorized() ENDS  
     
   /**  
    * The <b>isPasswordExpiring</b> method is used to return the name attribute.  
    * PCM-1  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPasswordExpiring(){  
   //---------------------------------------------------------------------------  
      return expiring;  
   }// isPasswordExpiring() ENDS  
  
   /**  
    * The <b>isPasswordValid</b> method is used to return the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPasswordValid(){  
   //---------------------------------------------------------------------------  
      return badPassword;  
   }// isPasswordValid() ENDS  
  
   /**  
    * The <b>isPrivilegeAuthorized</b>   
    * PCM-1  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public boolean isPrivilegeAuthorized(String string){  
   //---------------------------------------------------------------------------  
      return userPrivilegeHash.containsKey(string);  
   }// isPrivilegeAuthorized() ENDS  
  
   /**  
    * <code>loadUserPermissionsTable</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param none  
    * @return void <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   public void loadUserPermissionsTable(){  
   //---------------------------------------------------------------------------  
      Hashtable permissions=(Hashtable)  
         pageContext.getSession().getAttribute("permissions");  
  
      if(!userPermQuery.equals("")){  
         if(permissions!=null){  
         }  
      }  
      else  
         log("AuthenticateTag.loadUserPermissionsTable: Query not set.");  
  
      return;  
   }  
  
   /**  
    * <code>log</code>  
    * <p>  
    * This method logs errors to netscapes error log.  
    * </p>  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param message <code>String</code> the error to log.  
    * @return true <code>boolean</code> If there are more records to fetch.  
    */  
   //---------------------------------------------------------------------------  
   public void log(String string){  
   //---------------------------------------------------------------------------  
      pageContext.getServletContext().log(string);  
      return;  
   }// log() ENDS  
  
   /**  
    * <code>putPrivilege</code>  
    * <p>  
    * This method places a privilege in the privilege hash table.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param privilege<code>String</code>The privilege   
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   public void putPrivilege(String privilege){  
   //---------------------------------------------------------------------------  
      privilegeHash.put(privilege,privilege);  
   }// putPrivilege() ENDS  
  
   /**  
    * <code>putPrivilegeHash</code>  
    * <p>  
    * This method sets the hashtablel privileges.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param privilegeiHash<code>Hashtable</code>The privilege hash table  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void putPrivilegeHash(String string){  
   //---------------------------------------------------------------------------  
      privilegeHash=utilities.getTokenHash(string,",");  
   }// putPrivilegeHash() ENDS  
  
   /**  
    * <code>setAuthenticatePropertiesName</code>  
    * <p>  
    * This method sets the name of the scripting variable associated with this  
    * tag.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param id <code>String</code> The name of the scripting variable.  
    * @return none <code>none</code> none.  
    */  
   //---------------------------------------------------------------------------  
   public void setAuthenticatePropertiesName(String sting){  
   //---------------------------------------------------------------------------  
      authenticate_properties=sting;  
   }//setAuthenticatePropertiesName() ENDS  
  
   /**  
    * The <b>setAuthType</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setAuthType(String string){  
   //---------------------------------------------------------------------------  
      authType=string;  
   }// setAuthType() ENDS  
  
   /**  
    * The <b>setExpirePage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setExpirePage(String string){  
   //---------------------------------------------------------------------------  
      expirePage=string;  
   }// setExpirePage() ENDS  
  
  
   /**  
    * The <b>setId</b> method sets the value of the name attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setId(String string){  
   //---------------------------------------------------------------------------  
      id=string;  
      return;  
   }// setId() ENDS  
  
   /**  
    * The <b>setInitEncrypted</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitEncrypted(String string){  
   //---------------------------------------------------------------------------  
      initEncrypted=string;  
      return;  
   }// setInitEncrypted() ENDS  
  
   /**  
    * The <b>setInitSrc</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param name the name value  
    * @return  none  
    */  
   //---------------------------------------------------------------------------  
   public void setInitSrc(String string){  
   //---------------------------------------------------------------------------  
      initSrc=string;  
      return;  
   }// setInitSrc() ENDS  
  
  
   /**  
    * The <b>setLoginPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setLoginPage(String string){  
   //---------------------------------------------------------------------------  
      loginPage=string;  
   }// setLoginPage() ENDS  
  
   /**  
    * The <b>setLogout</b> method is used to return the logout attribute.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setLogout(String string){  
   //---------------------------------------------------------------------------  
      logout=string.toLowerCase().trim();  
   }// setLogout() ENDS  
  
   /**  
    * The <b>setName</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setName(String string){  
   //---------------------------------------------------------------------------  
      name=string.trim();  
   }// setName() ENDS  
  
   /**  
    * <code>setPrivilegeHash</code>  
    * <p>  
    * This method sets the hashtable privileges.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param privilegeHash<code>Hashtable</code>The privilege hash table  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void setPrivilegeHash(Hashtable hashtable){  
   //---------------------------------------------------------------------------  
      privilegeHash=hashtable;  
   }// setPrivilegeHash() ENDS  
  
   /**  
    * <code>setPrivilegeQuery</code>  
    * <p>  
    * This method sets the hashtablel privileges.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param privilegeiQuery<code>String</code>The query to run.  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void setPrivilegeQuery(String string){  
   //---------------------------------------------------------------------------  
      privilegeQuery=string;  
   }// setPrivilegeQuery() ENDS  
  
   /**  
    * <code>setRemotePassword</code>  
    * <p>  
    * This method sets the remote password.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param remotePassword<code>String</code>The remote password.  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void setRemotePassword(String string){  
   //---------------------------------------------------------------------------  
      remotePassword=string;  
   }// setRemotePassword() ENDS  
  
   /**  
    * <code>setRemoteUser</code>  
    * <p>  
    * This method sets the remote user.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param remoteUser<code>String</code>The remoteUser  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void setRemoteUser(String string){  
   //---------------------------------------------------------------------------  
      remoteUser=string;  
   }// setRemoteUser() ENDS  
  
   /**  
    * <code>setUserPermQuery</code>  
    * <p>  
    * This method sets the user permissions query.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param userPermQuery<code>String</code>The user permission query.  
    * @return <code>none</code>  
    */  
  
   //---------------------------------------------------------------------------  
   public void setUserPermQuery(String string){  
   //---------------------------------------------------------------------------  
      userPermQuery=string;  
   }// setUserPermQuery() ENDS  
  
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
      StringBuffer buffer=new StringBuffer(cr+"*****AuthenticateTag: "+cr);  
      buffer.append(tab+"authenticate_properties="+authenticate_properties+cr);  
      buffer.append(tab+"authenticateTableName="+authenticateTableName+cr);  
      buffer.append(tab+"authorized="+authorized+cr);  
      buffer.append(tab+"authType="+authType+cr);  
      buffer.append(tab+"badPassword="+badPassword+cr);  
      buffer.append(tab+"utilities="+utilities+cr);  
      buffer.append(tab+"expirePage="+expirePage+cr);  
      buffer.append(tab+"expiring="+expiring+cr);  
      buffer.append(tab+"loginPage="+loginPage+cr);  
      buffer.append(tab+"id="+id+cr);  
      buffer.append(tab+"init="+init+cr);  
      buffer.append(tab+"initEncrypted="+initEncrypted+cr);  
      buffer.append(tab+"initialize="+initialize+cr);  
      buffer.append(tab+"initSrc="+initSrc+cr);  
      buffer.append(tab+"initializationProperties="+initializationProperties+cr);  
      buffer.append(tab+"loader="+loader+cr);  
      buffer.append(tab+"logout="+logout+cr);  
      buffer.append(tab+"msg="+msg+cr);  
      buffer.append(tab+"name="+name+cr);  
      buffer.append(tab+"privilegeHash="+privilegeHash+cr);  
      buffer.append(tab+"privilegeQuery="+privilegeQuery+cr);  
      buffer.append(tab+"remotePassword="+remotePassword+cr);  
      buffer.append(tab+"remoteUser="+remoteUser+cr);  
      buffer.append(tab+"userPermQuery="+userPermQuery+cr);  
      buffer.append(tab+"userPrivilegeHash="+userPrivilegeHash+cr);  
      return buffer.toString();  
   }// toString() ENDS  
  
  
   //******************************************************************************  
   //Protected Methods  
   //******************************************************************************  
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
   //Private Methods  
   //***************************************************************************  
   /**  
    * <code>validateUserPassword</code>  
    * <p>  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param  <code>none</code>  
    * @return <code>none</code>   
    */  
   //-------------------------------------------------------------------------  
   private boolean validateUserPassword(HttpSession session,String user,  
      String initSrc){   
   //-------------------------------------------------------------------------  
      boolean badPassword=false;  
      Object propertiesLoaded=session.getAttribute(initSrc);  
      Object passwordValid=session.getAttribute("passwordValid");  
  
      if(passwordValid==null&&propertiesLoaded!=null){  
	 FindLDAPUser findUser=new FindLDAPUser(session,user,initSrc);  
  
	 if(findUser.getTimeToChangePassword()){  
	    badPassword=true;  
	    expiredOrExpiring=findUser.getBrowserMessages();  
         }  
      }  
  
      return badPassword;  
   }  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private String          authenticate_properties="AUTHENTICATE_PROPERTIES";  
   private String          authenticateTableName="";  
   private boolean         authorized=false;  
   private String          authType="";  
   private boolean         badPassword=false;  
   private Utilities       utilities=new Utilities();  
   private String          expirePage="";  
   private String          expiredOrExpiring="";  
   private boolean         expiring=false;  
   private String          loginPage="";  
   private String          id="";  
   private InitializeTag   init;  
   private String          initEncrypted="false";  
   private Initialize      initialize;  
   private String          initSrc="";  
   private Hashtable       initializationProperties;  
   private PrivilegeLoader loader;  
   private String          logout="false";  
   private String          msg="";  
   private String          name="";  
   private Hashtable       privilegeHash=new Hashtable();  
   private String          privilegeQuery="";  
   private String          remotePassword="";  
   private String          remoteUser="";  
   private String          userPermQuery="";  
   private Hashtable       userPrivilegeHash;  
}//AuthenticateTag() ENDS  
