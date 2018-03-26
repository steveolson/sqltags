/*
 * AuthSourceLDAP.java
 *
 * Created on June 19, 2002, 11:24 AM
 */

package com.aitworks.sqltags.utilities;
import com.aitworks.sqltags.contrib.LDAPUtilities;
import com.aitworks.sqltags.interfaces.*;
import java.util.*;
import java.sql.*;
import javax.servlet.jsp.*;

/**
 *
 * @author  jpoon
 */
public class AuthSourceLDAP implements AuthSource {
    
    /** Creates a new instance of AuthSourceLDAP */
    public AuthSourceLDAP() {
    }

    /**
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public String getAuthSource() {
        return AUTH_SOURCE;
    }
    
    /**
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public boolean changePassword(String remoteUser, String oldPassword, String newPassword) {
        if((ldapUtil=getLDAPUtil(remoteUser, oldPassword))==null) 
            return false;
                
        int result=ldapUtil.changePassword(newPassword);
        if(result==LDAPUtilities.FAILURE) {
            setError("AuthSourceLDAP:changePassword "+ldapUtil.getError());
            return false;
        }
        
        return true;                    
    }
    
    /**
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public String getError() {
        return this.error;
    }

   /**  
    * This method sets the authentication method
    * @author  John Poon
    * @param (String) authentication method.  
    * @version 1.0  
    * @return  None
    * @since   JDK1.3  
    */  
   //-------------------------------------------------------------------------  
   public void setConnection(Connection connection) { 
   //-------------------------------------------------------------------------  
       //doNothing
   }
   
    /**
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    private void setError(String error) {
        this.error=error;
    }
    
    /**
     * This method sets the properties
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
     * This method returns the authentication method
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  The authentication method
     * @since   JDK1.3
     */
    public int getDaysToExpire() {
        if(ldapUtil==null)
            return 0;
        return ldapUtil.getDaysToExpire();
    }
    
    /**
     * This method Authorize the remote user
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  (boolean) The result
     * @since   JDK1.3
     */
    public String validate(String remoteUser, String password) {
        if((ldapUtil=getLDAPUtil(remoteUser, password))==null) 
            return AuthSource.DENIED;
                
        int result=ldapUtil.validate();
        
        if(result==LDAPUtilities.AUTHORIZED) 
            return AuthSource.AUTHORIZED;          
        else if(result==LDAPUtilities.EXPIRED)
            return AuthSource.EXPIRED;

        setError(ldapUtil.getError());
        return AuthSource.DENIED;
    }
    
    /**
     * This method Authorize the remote user
     * @author  John Poon
     * @param none.
     * @version 1.0
     * @return  (boolean) The result
     * @since   JDK1.3
     */
    private LDAPUtilities getLDAPUtil(String remoteUser, String password) {
        setError("");
        
        if(properties==null) {
            setError("AuthSourceLDAP:getLDAPUtil: No Properties");
            return null;
        }
        String host=properties.getProperty("AuthSourceLDAP.host", "");
        String port=properties.getProperty("AuthSourceLDAP.port", "389");
        String org=properties.getProperty("AuthSourceLDAP.org", "");
        String ou=properties.getProperty("AuthSourceLDAP.ou", "People");
        if(host.equals("") || org.equals("")) {
            setError("AuthSourceLDAP:getLDAPUtil: Missing Properties (host or org)");
            return null;
        }
        
        int ldapPort;
        try {
            ldapPort=Integer.parseInt(port);
        }
        catch (Exception exception) {
            System.out.println("AuthSourceLDAP.getLDAPUtil: "+exception);
            ldapPort=389;
        }
        
        return ldapUtil=new LDAPUtilities(host, ldapPort, org, ou, remoteUser, password);         
    }
    
    /** Holds value of property pageContext. */
    private final static String     AUTH_SOURCE="LDAP";
    private String                  error="";
    private LDAPUtilities           ldapUtil=null;
    private Properties              properties=null;
    
}
