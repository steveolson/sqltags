/*
 * AuthStorageSession.java
 *
 * Created on June 19, 2002, 10:56 AM
 */

package com.aitworks.sqltags.utilities;
import com.aitworks.sqltags.interfaces.*;
import java.util.*;
import java.sql.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;

/**
 *
 * @author  jpoon
 */
public class AuthStorageSession implements AuthStorage {

    /** Creates a new instance of AuthStorageSession */
    public AuthStorageSession() { 
    }

    public AuthStorageSession(PageContext pageContext) {
        setPageContext(pageContext);
    }
    
    /**
     * This method returns the authentication code
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public String getAuthCode() {
        return authCode;
    }
    
    /**
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public String getAuthMethod() {
        return AUTH_NAME;
    }
    
    /**
     * This method returns the time to live in seconds
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  String time to live in seconds
     * @since   JDK1.3
     */
    public String getTimeToLiveSeconds() {
        return timeToLiveSeconds+"";
    }
    
    /**
     * This method sets the remote user
     * @author  John Poon
     * @param (String) remote user.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setRemoteUser(String remoteUser) {
        this.remoteUser=remoteUser;
    }
    
    /**
     * This method stores the remote user with given live time
     * @author  John Poon
     * @param   (String) time to live in seconds
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser, String authCode, String timeToLiveSeconds, Properties properties) {
        setProperties(properties);
        setRemoteUser(remoteUser);
        setAuthCode(authCode);
        setTimeToLiveSeconds(timeToLiveSeconds);
        store();
    }
    
    /**
     * This method stores the remote user with given live time
     * @author  John Poon
     * @param   (int) time to live in seconds
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser, String authCode, Properties properties) {
        setProperties(properties);
        setRemoteUser(remoteUser);
        setAuthCode(authCode);
        setTimeToLiveSeconds(this.DEFAULT_TTL);
        store();
    }
    
    /**
     * This method stores the remote user with given live time
     * @author  John Poon
     * @param   (int) time to live in seconds
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser, Properties properties) {
        setProperties(properties);
        setRemoteUser(remoteUser);
        setTimeToLiveSeconds(this.DEFAULT_TTL);
        store();
    }
    
    /**
     * This method sets the authentication method
     * @author  John Poon
     * @param (String) authentication method.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setProperties(Properties properties) {
        this.properties=properties;
    }
    
    /**
     * This method stores the remote user
     * @author  John Poon
     * @param   None
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser) {
        setRemoteUser(remoteUser);
        setTimeToLiveSeconds(this.DEFAULT_TTL);
        store();
    }
    
    /**
     * This method returns the remote user
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The remote user
     * @since   JDK1.3
     */
    public String getRemoteUser() {
        return remoteUser;
    }

    /**
     * This method sets the authentication method
     * @author  John Poon
     * @param (String) authentication method.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setConnection(Connection connection) {
        //doNothing
    }
    
    /**
     * This method stores the remote user with given live time
     * @author  John Poon
     * @param   (String) time to live in seconds
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser, String authCode, String timeToLiveSeconds) {
        setRemoteUser(remoteUser);
        setAuthCode(authCode);
        setTimeToLiveSeconds(timeToLiveSeconds);
        store();
    }
    
    /**
     * This method stores the remote user
     * @author  John Poon
     * @param   None
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store(String remoteUser, String authCode) {
        setRemoteUser(remoteUser);
        setAuthCode(authCode);
        setTimeToLiveSeconds(this.DEFAULT_TTL);
        store();
    }
    
    /**
     * This method stores the remote user
     * @author  John Poon
     * @param   None
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void store() {
        session=(HttpSession)getPageContext().getSession();
        session.setAttribute("remoteUser", getRemoteUser());
        session.setAttribute("authCode", getAuthCode());
    }

    /**
     * This method stores the remote user
     * @author  John Poon
     * @param   None
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void load() {
       setRemoteUser("");
       setAuthCode(AuthSource.DENIED);        
 
       if(pageContext==null) {
           return;
       }
       
       session=(HttpSession)getPageContext().getSession();
       setRemoteUser(Utilities.nvl((String)session.getAttribute("remoteUser"), ""));
       setAuthCode(Utilities.nvl((String)session.getAttribute("authCode"), AuthSource.DENIED));
    }
    
    
    /**
     * This method sets the authentication method
     * @author  John Poon
     * @param (String) authentication method.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setAuthCode(String authCode) {
        this.authCode=authCode;
    }
    
    /**
     * This method removes the remote user
     * @author  John Poon
     * @param   None
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void remove() {
        session.removeAttribute("remoteUser");
        session.removeAttribute("authCode");
        setRemoteUser("");
        setAuthCode(AuthSource.DENIED);
    }
    
    /**
     * This method sets the time to live in seconds
     * @author  John Poon
     * @param (String) remote user.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setTimeToLiveSeconds(String timeToLiveSeconds) {
        try {
            setTimeToLiveSeconds(Integer.parseInt(timeToLiveSeconds));
        }
        catch (Exception exception) {
            System.out.println("AuthStorageSession.setTimeToLiveSeconds: "+exception);
           setTimeToLiveSeconds(-1);
        }
    }
    
    /**
     * This method sets the time to live in seconds
     * @author  John Poon
     * @param (int) time to live in seconds.
     * @version 1.0
     * @return  None
     * @since   JDK1.3
     */
    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds=timeToLiveSeconds;
    }

    /** Getter for property pageContext.
     * @return Value of property pageContext.
     */
    public PageContext getPageContext() {
        return this.pageContext;
    }
    
    /** Setter for property pageContext.
     * @param pageContext New value of property pageContext.
     */
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
    
   //***************************************************************************  
   //                      Class variable section  
   //***************************************************************************  
   private final static String  AUTH_NAME="session";
   private final static int     DEFAULT_TTL=180;
   private String               authCode=AuthSource.DENIED;
   private PageContext          pageContext=null;
   private Properties           properties=null;
   private String               remoteUser="";
   private HttpSession          session=null;
   private int                  timeToLiveSeconds=DEFAULT_TTL;
}
