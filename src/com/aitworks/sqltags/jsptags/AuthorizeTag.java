package com.aitworks.sqltags.jsptags;  
import com.aitworks.sqltags.utilities.*;
import com.aitworks.sqltags.interfaces.*;
import java.io.*;  
import java.lang.reflect.*;
import java.util.*;  
import java.sql.*;   
import javax.servlet.http.*;  
import javax.servlet.jsp.*;   
  
/**  
 * The <b>AuthorizeTag</b>   
 * @author  Booker Northington II  
 * @version 1.0  
 * @since   JDK1.3  
 * @param   none  
 * @return    
 */  
//---------------------------------------------------------------------------  
final public class AuthorizeTag extends Initialize{  
//---------------------------------------------------------------------------  
   //***************************************************************************  
   // Constructors
   //***************************************************************************  
   /**  
    * The <b>AuthorizeTag</b> class constructor.  
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return    
    */  
   //---------------------------------------------------------------------------  
   public AuthorizeTag(){  
   //---------------------------------------------------------------------------  
   }//Constructor ENDS()  
    
   //***************************************************************************  
   //Public Methods  
   //***************************************************************************    
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
      try{  
         if(bodyContent!=null)  
            bodyContent.writeOut(bodyContent.getEnclosingWriter());  
      }  
      catch(IOException exception){  
        System.out.println("AuthorizeTag.doEndTag: "+exception);  
      }  
      return EVAL_PAGE;  
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
       pageContext.setAttribute(getId(),this);
       
       //get Connection 
       connection=getConnection();
       //SQLTagsRequest request=new SQLTagsRequest((HttpServletRequest)pageContext.getRequest());
       HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
       
       if(request.getAttribute("AuthorizeTag")!=null) {
           storage=((AuthorizeTag)request.getAttribute("AuthorizeTag")).getStorage();
           permissionTokens=((AuthorizeTag)request.getAttribute("AuthorizeTag")).getPermissionTokens();
           setInitializationProperties();
           return EVAL_BODY_TAG;
       }
       String username=Utilities.nvl(request.getParameter(getUserParameter()),"");
       String password=Utilities.nvl(request.getParameter(getPasswordParameter()),"");
       String newPassword=Utilities.nvl(request.getParameter(getNewPasswordParameter()),"");

        try {
            //Initialize src from properties file
            setInitializationProperties();
            
            //get Class for StoraceClass
            String className=getProperty("AuthorizeTag.AuthStorageClass");
            if(className!=null){
                Class storageClass=Class.forName(className);
                if(storageClass!=null){
                    storage=(AuthStorage)storageClass.newInstance();
                    storage.setPageContext(pageContext);
                    storage.setProperties(getInitializationProperties());
                } else {
                    System.out.println("Warning: invalid parameter: AuthorizeTag.AuthStorageClass: "+className);
                }
            } else {
                System.out.println("Warning: missing parameter: AuthorizeTag.AuthStorageClass");
            }

            //get Class for SourceClass
            className=getProperty("AuthorizeTag.AuthSourceClass");
            if(className!=null){
                Class sourceClass=Class.forName(className);
                if(sourceClass!=null) {
                    source=(AuthSource)sourceClass.newInstance();
                    source.setProperties(getInitializationProperties());
                } else {
                    System.out.println("Warning: invalid parameter: AuthorizeTag.AuthSourceClass: "+className);
                }
            } else {
                System.out.println("Warning: missing parameter: AuthorizeTag.AuthSourceClass");
            }

            //get Class for ACLLoaderClass
            className=getProperty("AuthorizeTag.AuthACLLoaderClass");
            if(className!=null){
                Class ACLLoaderClass=Class.forName(className);
                if(ACLLoaderClass!=null){
                    permission=(AuthACLLoader)ACLLoaderClass.newInstance();
                    if(permission != null) {
                        permission.setConnection(connection);
                        permission.setProperties(getInitializationProperties());
                    }
                } else {
                    System.out.println("Warning: invalid parameter: AuthorizeTag.AuthACLLoaderClass: "+className);
                }
            } else {
                System.out.println("Warning: missing parameter: AuthorizeTag.AuthACLLoaderClass");
            }
            
            //load
            if(storage!=null){
                storage.load();
            }
            
            HttpSession session=(HttpSession)pageContext.getSession();
            if(session != null) { 
                if(session.getAttribute("permissionTokens")!=null)
                   permissionTokens=(Vector)session.getAttribute("permissionTokens");
                else {  
                    if(permission!=null) {
                        permissionTokens=permission.getPermissionTokens(storage.getRemoteUser());
                        if(!permission.getError().equals(""))
                            setErrorMsg(permission.getError());
                        else
                            session.setAttribute("permissionTokens", permissionTokens);
                    }
                }
            }
            
            if(request.getAttribute("AuthorizeTag")==null) {
                //LOGIN
                if(operation.equalsIgnoreCase(this.LOGIN)) {
                    login(username, password);
                }
                
                //CHANGE PASSWORD
                if(operation.equalsIgnoreCase(this.CHANGE_PASSWORD)) {
                    changePassword(username, password, newPassword);
                }
                
                //LOGOUT
                else if(operation.equalsIgnoreCase(this.LOGOUT)) {
                    //remove user
                    logout();
                }
                
                //REDIRECT
                else if(operation.equalsIgnoreCase(this.REDIRECT)) {
                    redirect();
                }
            }
            else {
                System.out.println("AuthorizeTag.doStart: ignored");
            }
            request.setAttribute("AuthorizeTag", this);
        }

        catch(Exception exception) {
            System.out.println("AuthorizeTag:doStart: "+exception);
        }
       
        return EVAL_BODY_TAG;  
   }// doStartTag() END  
   
   /**  
    * The <b>getErrorMsg</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getErrorMsg(){  
   //---------------------------------------------------------------------------  
      return errorMsg;  
   }// getErrorMsg() ENDS 
   
   /**  
    * The <b>getExpiredPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getExpiredPage(){  
   //---------------------------------------------------------------------------  
      return expiredPage;  
   }// getExpiredPage() ENDS   
   
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
    * The <b>getLogoutPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getLogoutPage(){  
   //---------------------------------------------------------------------------  
      return logoutPage;  
   }// getLoginPage() ENDS  
   
   /**  
    * The <b>getOperation</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getOperation(){  
   //---------------------------------------------------------------------------  
      return operation;  
   }// getOperation() ENDS  

   /**  
    * <code>getPermissions</code>  
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
   public Enumeration getPermissions(){  
   //--------------------------------------------------------------------------- 
       return permissionTokens.elements();
   }//getPermissions() ENDS   

      /**  
    * <code>getPermissions</code>  
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
   public Vector getPermissionTokens(){  
   //--------------------------------------------------------------------------- 
       return permissionTokens;
   }//getPermissions() ENDS   
   
      /**  
    * <code>getPermissions</code>  
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
   public AuthStorage getStorage(){  
   //--------------------------------------------------------------------------- 
       return storage;
   }//getPermissions() ENDS 
   
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
       if (storage==null)
           return "";
       return storage.getRemoteUser();  
   }// getRemoteUser() ENDS   


   /**  
    * <code>hasPermission</code>  
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
   public boolean hasPermission(String token){  
   //--------------------------------------------------------------------------- 
       return permissionTokens.contains(token);
   }//hasPermission() ENDS 
   
   /**  
    * <code>changePassword</code>  
    * <p>  
    * This method changes the user's password.  
    * </p>  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   1.3  
    * @param <code>none</code>  
    * @return <code>none</code>  
    */  
   //---------------------------------------------------------------------------  
   private boolean changePassword(String username, String password, String newPassword){  
   //--------------------------------------------------------------------------- 
       setErrorMsg("");
       if( username==null || username.length()==0) {
           setErrorMsg("AuthorizeTag.changePassword: Invalid username: "+ username);
           return false;
       }
       if( password==null || password.length()==0) {
           setErrorMsg("AuthorizeTag.changePassword: Invalid password: "+ password);
           return false;
       }
       if( newPassword==null || newPassword.length()==0) {
           setErrorMsg("AuthorizeTag.changePassword: Invalid newPassword: "+ newPassword);
           return false;
       }
       // Extra checks .. password rules ... 
       // like you can only change your own password
       if(!username.equals( getRemoteUser())) {
           setErrorMsg("AuthorizeTag.changePassword: Attempted to change Password for another user!");
           return false;
       }
       if(source==null) {
           setErrorMsg("AuthorizeTag.changePassword:Invalid Source, aborted.");
           return false;
       }
       
       // OK here we go!
       if( !source.changePassword(username,password,newPassword)) {
           setErrorMsg( source.getError() );
           return false;
       }
       
       return login(username, newPassword);                     
   }// logout() ENDS   
   /**  
    * <code>login</code>  
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
   private boolean login(String username, String password){  
   //---------------------------------------------------------------------------  
       if(username.equals("")) {
           return false;
       }
       HttpSession session=(HttpSession)pageContext.getSession();
       String result=source.validate(username, password);
       
       if(result.equals(AuthSource.AUTHORIZED) || result.equals(AuthSource.EXPIRED)) {
           storage.setRemoteUser(username);
           storage.setAuthCode(result);
           storage.store();
           
           setDaysToExpire(source.getDaysToExpire()+"");
           
       }
       else {
           storage.remove();
           setErrorMsg(source.getError());
           
       }
       
       if(result.equals(AuthSource.AUTHORIZED) && permission != null) {
           permissionTokens=permission.getPermissionTokens(username);
           if(!permission.getError().equals("")) {
               setErrorMsg(permission.getError());
           }
           else {
               session.setAttribute("permissionTokens", permissionTokens);
           }
       }
       else {
           permissionTokens.setSize(0);
           session.removeAttribute("permissionTokens");
       }

      return true;
   }// login() ENDS  

   /**  
    * <code>logout</code>  
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
   private void logout(){  
   //--------------------------------------------------------------------------- 
        HttpSession session=(HttpSession)pageContext.getSession();
        storage.remove();
        permissionTokens.setSize(0);
        session.removeAttribute("permissionTokens");
   }// logout() ENDS   

   /**  
    * <code>logout</code>  
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
   private void redirect(){  
   //---------------------------------------------------------------------------  
      String redirectPage=""; 
      if(storage==null) {
          System.out.println("AuthorizeTag:redirect: null storage");
          return;
      }
      if(storage.getAuthCode().equals(AuthSource.AUTHORIZED))
          return;
      else if(storage.getAuthCode().equals(AuthSource.DENIED))
          redirectPage=getLoginPage();
      else if(storage.getAuthCode().equals(AuthSource.EXPIRED))
          redirectPage=getExpiredPage();
      else {
          setErrorMsg("AuthorizeTag.redirect: Unknown authorization code ");
          return ;
      }
      
      if(!redirectPage.equals("")) {
          try {
              ((HttpServletResponse)pageContext.getResponse()).sendRedirect(redirectPage);
          }
          catch (Exception exception) {
              try {
                  System.out.println("Failed to redirect, attempting w/ JavaScript");
                  pageContext.getOut().println("<script>document.location='"+redirectPage+"';</script>");
              }
              catch( Exception e) {
                  setErrorMsg("AuthorizeTag.redirect: "+exception);
              }
          }
      }
      else 
          setErrorMsg("AuthorizeTag.redirect: Missing redirect URL");
   }// redirect() ENDS   

   /**  
    * The <b>setAuthSource</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public String getAuthCode(){  
   //---------------------------------------------------------------------------  
       if (storage==null)
           return "";
      return storage.getAuthCode();  
   }// setAuthSource() ENDS

   /**  
    * The <b>setAuthSource</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   private Connection getConnection(){  
   //---------------------------------------------------------------------------  
       if(connection!=null)
           return connection;

       ConnectionTag connectionTag=(ConnectionTag)findAncestorWithClass(this,ConnectionTag.class);
       if(connectionTag!=null){
           connection=connectionTag.getConnection();
       }
       
       if(connection==null)
           System.out.println("AuthorizeTag.getConnection: Connection not found");
       
       return connection;
   }// setAuthSource() ENDS

      /**  
    * The <b>setErrorMsg</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setErrorMsg(String string){  
   //---------------------------------------------------------------------------  
      errorMsg=string;  
   }// setErrorMsg() ENDS    
   
   /**  
    * The <b>setExpiredPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setExpiredPage(String string){  
   //---------------------------------------------------------------------------  
      expiredPage=string;  
   }// setExpiredPage() ENDS      
  
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
    * The <b>setLogoutPage</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setLogoutPage(String string){  
   //---------------------------------------------------------------------------  
      logoutPage=string;  
   }// setLogoutPage() ENDS  

   /**  
    * The <b>setOperation</b>   
    * PCM-1  
    * @author  Booker Northington II  
    * @version 1.0  
    * @since   JDK1.3  
    * @param   none  
    * @return  the name value  
    */  
   //---------------------------------------------------------------------------  
   public void setOperation(String string){  
   //---------------------------------------------------------------------------  
      operation=string;  
   }// setLogoutPage() ENDS  
   
   
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
   private void setRemoteUser(String string){  
   //---------------------------------------------------------------------------  
      storage.setRemoteUser(string);  
   }
   
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
   public String getUserParameter() {
   //--------------------------------------------------------------------------- 
       return this.userParameter;
   }
   
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
   public void setUserParameter(String userParameter) {
   //--------------------------------------------------------------------------- 
       this.userParameter = userParameter;
   }
   
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
   public String getPasswordParameter() {
   //--------------------------------------------------------------------------- 
       return this.passwordParameter;
   }
   
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
   public void setPasswordParameter(String passwordParameter) {
   //--------------------------------------------------------------------------- 
       this.passwordParameter = passwordParameter;
   }
   
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
   public String getNewPasswordParameter() {
   //--------------------------------------------------------------------------- 
       return this.newPasswordParameter;
   }
   
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
   public void setNewPasswordParameter(String newPasswordParameter) {
   //--------------------------------------------------------------------------- 
       this.newPasswordParameter = newPasswordParameter;
   }
   
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
   public String getDaysToExpire() {
   //--------------------------------------------------------------------------- 
       return this.daysToExpire;
   }
   
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
   public void setDaysToExpire(String daysToExpire) {
   //--------------------------------------------------------------------------- 
       this.daysToExpire = daysToExpire;
   }
   
// setRemoteUser() ENDS  
  
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************   
   public final static String   LOGIN="login";
   public final static String   LOGOUT="logout";
   public final static String   REDIRECT="redirect";  
   public final static String   VERIFY="verify";
   public final static String   CHANGE_PASSWORD="change_password";
   private Connection           connection=null;
   private String               daysToExpire="-1";
   private String               errorMsg="";
   private String               expiredPage=""; 
   private String               loginPage="";
   private String               logoutPage=""; 
   private AuthStorage          storage;
   private AuthSource           source;
   private AuthACLLoader        permission;
   private Vector               permissionTokens=new Vector();
   private String               operation=this.VERIFY; 
   private String               status="";
   private String               userParameter="username";
   private String               passwordParameter="password";
   private String               newPasswordParameter="newPassword";
 

   
}//AuthorizeTag() ENDS  
