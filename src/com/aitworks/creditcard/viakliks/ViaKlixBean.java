/* $Id: ViaKlixBean.java,v 1.3 2002/05/06 15:16:30 jpoon Exp $
 * $Log: ViaKlixBean.java,v $
 * Revision 1.3  2002/05/06 15:16:30  jpoon
 * change verbose to false
 *
 * Revision 1.2  2002/05/03 06:21:36  solson
 * completed first major pass.  Mostly working now.
 *
 * Revision 1.1  2002/05/02 23:49:41  solson
 * new ViaKlix Iimplementation
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
 */
package com.aitworks.creditcard.viakliks;
import java.io.*;
import java.net.MalformedURLException;
import java.net.*;
import java.util.*;
import javax.servlet.jsp.tagext.TagSupport;
import java.security.*;
/**
 * @author  solson
 */
public class ViaKlixBean extends TagSupport {

    // Public "Constants"
    public static final int APPROVED = 0;
    public static final int DECLINED = 1;
    public static final int FAILURE  = 2;
    public static final int UNKNOWN  = 3;
    
    // Private Fields
    private Hashtable attributes = new Hashtable();
    private int result = UNKNOWN;
    private boolean verbose = false;
    private Vector requiredAttributes = new Vector();
    
    /** Creates a new instance of ExecuteTransaction */
    public ViaKlixBean() {
        initialize();
    }
    
    private void initialize(){
        // Rea;;u everything in the method should come from the ViaKlixBean.properties file!
        // don't you knwo it!
        requiredAttributes.add("ssl_merchant_id");
        requiredAttributes.add("ssl_pin");
        requiredAttributes.add("ssl_amount");
        requiredAttributes.add("ssl_card_number");
        requiredAttributes.add("ssl_exp_date");
        
        // Defaults ...
        setAttribute("viaklix_url","https://www.viaklix.com/process.asp");
    }
    
    public int execute() throws ViaKlixBeanException {
        
        setResult(FAILURE);
        
        validate();
        
        String queryString = buildQueryString();
        
        // Send URL to ViaKlix for Processing ... 
        if(verbose)
            System.out.println("Posting to ViaKlix ... ");
        String urlOutput = postURL(getAttribute("viaklix_url"),queryString);
        if(verbose)
            System.out.println("done.");
        
        // Parse Results 
        // parseResult("ssl_result_message=APPROVED\nssl_txn_id=00000000-0000-0000-0000-000000000000\nssl_approval_code=123456\nssl_cvv2_response=P\nssl_avs_response=X\nssl_transaction_type=SALE\nssl_card_number=4427872641004797\n");
        parseResult(urlOutput);

        // Checking Return Status ... 
        String r = getAttribute("ssl_result");
        if( r==null) {
            setResult(FAILURE);
        }
        else {
            if( r.trim().equals("0"))
                setResult(APPROVED);
            else {
                setResult(DECLINED);
            }
        }
        return getResult();
    }
    
    private void validate() throws ViaKlixBeanException {
        Enumeration enum = requiredAttributes.elements();
        boolean hasMissingRequiredAttributes = false;
        String missingAttributes = new String("");
        String sep = "";
        
        // Check for Required Fields
        // System.out.println( "Checking for Required Attributes ... ");
        while( enum.hasMoreElements() ){
            String key = (String) enum.nextElement();
            if( !attributes.containsKey( key )) {
                hasMissingRequiredAttributes=true;
                missingAttributes= missingAttributes + sep + key;
                sep = ", ";
            }
        }
        if( hasMissingRequiredAttributes) {
            ViaKlixBeanException e = new ViaKlixBeanException("Missing required field(s): "+missingAttributes);
            setAttribute("exception",e.toString());
            throw e;
        }
    }
    
    private String buildQueryString(){
        Enumeration enum = attributes.keys();
        StringBuffer urlBuffer=new StringBuffer();
        String sep = "";
        String name = "";
        String value = "";
        
        // build the URL String for delivery to ViaKlix HTTPS .. 
        while(enum.hasMoreElements()){
            name=(String)enum.nextElement();
            value=URLEncoder.encode((String)attributes.get(name));
            urlBuffer.append(sep+name+"="+value);
            sep="&";
        }
        return urlBuffer.toString();
    }
    
    private String postURL(String inURL, String queryString) 
        throws ViaKlixBeanException
    {
        // Need the following for HTTPS ... 
        String input = new String();
        
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.setProperty("java.protocol.handler.pkgs"
                          ,"com.sun.net.ssl.internal.www.protocol"
                          );
        try {
            URL url=new URL(inURL);
            URLConnection urlConnection=url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStream out=urlConnection.getOutputStream();
            out.write( queryString.getBytes() );
            out.flush();
            out.close();
            setAttribute("query_string",queryString);

            InputStream in = urlConnection.getInputStream();
            int size = in.available();
            byte [] rawInput = new byte[size];
            in.read( rawInput );
            in.close();

            input = new String( rawInput );
            setAttribute("url_reply",input);
        } catch ( Exception ex) {
            ViaKlixBeanException e = 
                        new ViaKlixBeanException("Exception in PostURL("
                                                  + inURL
                                                  +", "
                                                  + queryString
                                                  +"): "
                                                  + ex.toString()
                                                 );
            setAttribute("exception",e.toString());
            throw e;
        }
        return input;
    }
    
    private void parseResult(String in){
        int start = 0;
        int end = 0;
        String line = "";
        String name = "";
        String value = "";
        do {
            end = in.indexOf("\n",start);
            if(end<0){
                // end = in.indexOf("\r",start);
                // if(end<0)
                    break;
            }
            
            line = in.substring(start,end);
            
            int equal = line.indexOf("=");
            if(equal>0){
                name = line.substring(0,equal);
                value = line.substring(equal+1);
                // System.out.println("PPV "+name+"="+value);
                attributes.put(name,value);
            } else {
                System.out.println("WARN: missing '=' sign, skipping: "+line);
            }
            start=end+1;
        } while (start<in.length());
    }
    
    public boolean isApproved() {
        if(getResult()==APPROVED)
            return true;
        return false;
    }
    
    public boolean isDeclined() {
        if(getResult()==DECLINED)
            return true;
        return false;
    }
    
    public boolean isFailure() {
        if(getResult()==FAILURE)
            return true;
        return false;
    }
    
    public void setResult(int result){
        this.result=result;
    }
    
    public int getResult(){
        return result;
    }
    
    public void setAttribute(String name, String value) {
        attributes.put(name,value);
    }
    
    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }
    
    public static void main(String argv[]) {
        ViaKlixBean vb = new ViaKlixBean();
        vb.verbose=false;
        vb.setAttribute("ssl_description","ViaKlixBean was Here.");
        vb.setAttribute("ssl_merchant_id","400684"); 
        vb.setAttribute("ssl_pin","8570");
        vb.setAttribute("ssl_amount","10.0");
        vb.setAttribute("ssl_test_mode","false");
        vb.setAttribute("ssl_show_form","false");
        vb.setAttribute("ssl_card_number","4427872641004797");
        vb.setAttribute("ssl_exp_date","1202");
        vb.setAttribute("ssl_result_format","ASCII");
        vb.setAttribute("ssl_salestax",".05");
        try {
            int result = vb.execute();
            System.out.println("survey says: "+ result);
            System.out.println(vb.getAttribute("ssl_result_message"));
            if(vb.verbose)
                System.out.println(vb.attributes.toString());
        } catch( ViaKlixBeanException e) {
            System.out.println("Execute Exception: "+e);
        }
    }
}