/* $Id: LDAPUtilities.java,v 1.1 2002/06/18 18:46:16 solson Exp $
 * $Log: LDAPUtilities.java,v $
 * Revision 1.1  2002/06/18 18:46:16  solson
 * first cut, replaces aitworks-ldap package
 *
 * ====================================================================
 *
 * Applied Information Technologies, Inc.
 * Steve A. Olson
 *
 * Copyright (c) 2002 Applied Information Technologies, Inc.  
 * Copyright (c) 2002 Steve A. Olson
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by 
 *    Applied Information Technologies, Inc. (http://www.ait-inc.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Applied Information Technologies, Inc.", "AIT", "AITWorks", 
 *    "SQLTags", and "<SQLTags:>" must not be used to endorse or promote 
 *    products derived from this software without prior written permission. 
 *    For written permission, please contact support@ait-inc.com.
 *
 * 5. Products derived from this software may not be called "SQLTags" or
 *    "<SQLTags:>" nor may "SQLTags" or "<SQLTags:>" appear in their 
 *    names without prior written permission of the Applied Information 
 *    Technologies, Inc..
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL APPLIED INFORMATION TECHNOLOGIES, 
 * INC. OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of Applied Information Technologies, Inc.  For more
 * information on Applied Information Technologies, Inc., please see
 * <http://www.ait-inc.com/>.
 *
 */
/*
 * Created on June 17, 2002, 10:10 PM
 */
package com.aitworks.sqltags.contrib;
import netscape.ldap.*;
/**
 *
 * @author  solson
 */
public class LDAPUtilities {
    
    // Validate Return Codes 
    public static final int AUTHORIZED = 0;
    public static final int DENIED = 1;
    public static final int EXPIRED = 2;
    
    // ChangePassword Return Codes
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    
    private String host = "localhost";
    private int port = 389;
    private String uid;
    private String password;
    private String org;
    private String ou = "People";
    private int errorCode = -1;
    private String errorMessage = "(-1) Unspecified Failure";
    private boolean debug=false;
    private int secondsToExpire=0;
    
    /** Creates a new instance of LDAPUtilities */
    public LDAPUtilities() {
    }
    
    /** Creates a new instance of LDAPUtilities */
    public LDAPUtilities(String h, int port) {
        setHost(h);
        setPort(port);
    }
    
    /** Creates a new instance of LDAPUtilities */
    public LDAPUtilities(String h, int port, String o, String ou) {
        setHost(h);
        setPort(port);
        setO(o);
        setOu(ou);
    }
    
    /** Creates a new instance of LDAPUtilities */
    public LDAPUtilities(String h, int port, String o, String ou,String u,String p) {
        setHost(h);
        setPort(port);
        setO(o);
        setOu(ou);
        setUid(u);
        setPassword(p);
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        LDAPUtilities lu = new LDAPUtilities("localhost",389,"myOrg","People");
        lu.setDebug(true);
        int result = lu.changePassword("scott","tiger","tiger");
        if(result==LDAPUtilities.FAILURE){
            System.out.println("ChangePassword Failed: "+lu.getError());
        } else {
            System.out.println("Successfully Changed Password for: "+lu.getUid() );
        }
        
        LDAPUtilities lu2 = new LDAPUtilities("localhost",389,"myOrg","people","scott","tiger");
        lu2.setDebug(true);
        result = lu2.validate();
        if(result==LDAPUtilities.AUTHORIZED){
            System.out.println("Validation succeeded for: "+lu2.getUid());
            if(lu2.getDaysToExpire()>0){
                System.out.println("Account expires in about "+ lu2.getDaysToExpire()+" days.");
            }
        } else if(result==LDAPUtilities.DENIED){
            System.err.println("Validation failed for: "+lu2.getUid());
        } else if(result==LDAPUtilities.EXPIRED){
            System.out.println("Validation Expired for: "+lu2.getUid()+" in "+lu2.getDaysToExpire()+" days.");
        } else {
            System.out.println("Unknown return: "+result);
        }
        System.exit(0);
    }
    
    /* Validate(u,p)
     */
    public int validate(String h, int port, String o, String ou, String u, String p){
        setHost(h);
        setPort(port);
        setO(o);
        setOu(ou);
        setUid(u);
        setPassword(p);
        return validate();
    }
    /* Validate(u,p)
     */
    public int validate(String h, int port, String u, String p){
        setHost(h);
        setPort(port);
        setUid(u);
        setPassword(p);
        return validate();
    }
    /* Validate(u,p)
     */
    public int validate(String u, String p){
        setUid(u);
        setPassword(p);
        return validate();
    }
    
    public int validate(){
        int result = DENIED;
        if(isDebug()){
            System.out.println("validate(h="+getHost()
                                +",port="+getPort()
                                +",o="+getO()
                                +",ou="+getOu()
                                +",uid="+getUid()
                                +",password=********"
                                +")"
                                );
        }
        LDAPConnection ld = new LDAPConnection();
        try {
            ld.connect( 3, getHost(), getPort(), "uid="+getUid()
                               +",ou="+getOu()
                               +",o="+getOrg()
                               ,getPassword()
                               );
            
            if( ld.isAuthenticated() ){
                result = AUTHORIZED;
                // LDAPEntry ldapEntry = ld.read( ld.getAuthenticationDN());
                LDAPControl aCtl[] = ld.getResponseControls();
                for( int i=0; i<aCtl.length;i++) {
                    LDAPControl ctl = aCtl[i];
                    if( ctl.getID()==netscape.ldap.LDAPControl.PWEXPIRED){
                        result=EXPIRED;
                        if(isDebug()){
                            System.out.println("Expired.");
                        }
                    }
                    else if(ctl.getID()==netscape.ldap.LDAPControl.PWEXPIRING){
                        setSecondsToExpire(new String( ctl.getValue()) );
                        result=AUTHORIZED;
                        if(isDebug()){
                            System.out.println("Authorized, but expiring in "+getDaysToExpire()+" days.");
                        }
                    }
                    else {
                        result=AUTHORIZED;
                        if(isDebug()){
                            System.out.println("Authorized.");
                        }
                    }
                } // for LDAPControls ends
            } else {
                result = DENIED;
            }
        } catch (LDAPException e){
            result = DENIED;
            setError(e.getLDAPResultCode(),e.toString());
            if(isDebug()){
                System.out.println( "LDAP Exception: "+ e);
            }
        }
        finally {
            try {
                if(ld.isConnected()) {
                    ld.disconnect();
                }
            } catch (LDAPException e2){
                System.out.println("LDAP Disconnection Error: "+e2);
                // don't reset result .. this is only the disconnect ... 
            }
            return result;
        }
    } // validate() ENDS
    
    /* ChangePassword
     */
    public int changePassword(String h, int port, String o, String ou, String u,String p,String np){
        setHost(h);
        setPort(port);
        setO(o);
        setOu(ou);
        setUid(u);
        setPassword(p);
        return changePassword(np);
    }
    
    /* ChangePassword
     */
    public int changePassword(String h, int port, String u,String p,String np){
        setHost(h);
        setPort(port);
        setUid(u);
        setPassword(p);
        return changePassword(np);
    }
    
    /* ChangePassword
     */
    public int changePassword(String u,String p,String np){
        setUid(u);
        setPassword(p);
        return changePassword(np);
    }
    
    public int changePassword(String newPassword){
        int result = FAILURE;
        if(isDebug()){
            System.out.println("changePassword(h="+getHost()
                                +",port="+getPort()
                                +",o="+getO()
                                +",ou="+getOu()
                                +",uid="+getUid()
                                +",password=********"
                                +",newPassword=********"
                                +")"
                                );
        }
        LDAPConnection ld = new LDAPConnection();
        try {
            ld.connect( 3, getHost(), getPort(), "uid="+getUid()
                               +",ou="+getOu()
                               +",o="+getOrg()
                               ,getPassword()
                               );
            if( ld.isAuthenticated() ){
                LDAPAttribute newAttr = new LDAPAttribute("userPassword",newPassword);
                LDAPModification mod = new LDAPModification(LDAPModification.REPLACE,newAttr);
                ld.modify(ld.getAuthenticationDN(), mod);
                result = SUCCESS;
            } else {
                result = FAILURE;
                setError(-1,"Not Authenticated");
            }
        } catch (LDAPException e){
            result = FAILURE;
            setError(e.getLDAPResultCode(),e.toString());
            if(isDebug()){
                System.out.println( "LDAP Exception: "+ e);
            }
        }
        finally {
            try {
                if(ld.isConnected()) {
                    ld.disconnect();
                }
            } catch (LDAPException e2){
                System.out.println("LDAP Disconnection Error: "+e2);
                // don't reset result .. this is only the disconnect ... 
            }
            return result;
        }
    } // changePassword ENDS
    
    /* Getters
     */
    public int getErrorCode(){return errorCode;}
    public String getErrorMessage(){return errorMessage;}
    public String getError(){return getErrorMessage();}
    public String getHost(){return host;}
    public int getPort(){return port;}
    public String getUid(){return uid;}
    public String getPassword(){return password;}
    public String getOrg(){return org;}
    public String getO(){return getOrg();}
    public String getOu(){return ou;}
    public String getUnit(){return getOu();}
    public boolean getDebug(){return debug;}
    public boolean isDebug(){return debug;}
    public int getSecondsToExpire(){return secondsToExpire;}
    public int getDaysToExpire(){return secondsToExpire/(60*60*24);}
    
    /* Setters 
     */
    public void setErrorCode(int i){errorCode=i;}
    public void setErrorMessage(String s){errorMessage=s;}
    public void setError(int i,String s){errorCode=i;errorMessage="("+i+") "+s;}
    public void setHost(String h){ host=h;}
    public void setPort(int p){ port=p;}
    public void setUid(String u){ uid=u;}
    public void setPassword(String p){password=p;}
    public void setOrg(String o){ org=o;}
    public void setO(String o){setOrg(o);}
    public void setOu(String o){ou=o;}
    public void setUnit(String u){setOu(u);}
    public void setDebug(boolean b){debug=b;}
    public void setSecondsToExpire(int s){secondsToExpire=s;}
    public void setSecondsToExpire(String s){
        try{
            secondsToExpire = Integer.parseInt(s);
        } catch( Exception e){
            System.out.println("Conversion Error: Can't convert '"+ s +"': "+ e);
        }
    }
}
